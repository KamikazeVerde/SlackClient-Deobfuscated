/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ public class EntityTracker
/*     */   extends StoredObject implements ClientEntityIdChangeListener {
/*  24 */   private final Map<Integer, Entity1_10Types.EntityType> clientEntityTypes = new ConcurrentHashMap<>();
/*  25 */   private final Map<Integer, List<Metadata>> metadataBuffer = new ConcurrentHashMap<>();
/*  26 */   private final Map<Integer, Integer> vehicles = new ConcurrentHashMap<>();
/*  27 */   private final Map<Integer, EntityReplacement> entityReplacements = new ConcurrentHashMap<>();
/*  28 */   private final Map<Integer, UUID> playersByEntityId = new HashMap<>();
/*  29 */   private final Map<UUID, Integer> playersByUniqueId = new HashMap<>();
/*  30 */   private final Map<UUID, Item[]> playerEquipment = (Map)new HashMap<>();
/*  31 */   private int gamemode = 0;
/*  32 */   private int playerId = -1;
/*  33 */   private int spectating = -1;
/*  34 */   private int dimension = 0;
/*     */   
/*     */   public EntityTracker(UserConnection user) {
/*  37 */     super(user);
/*     */   }
/*     */   
/*     */   public void removeEntity(int entityId) {
/*  41 */     this.clientEntityTypes.remove(Integer.valueOf(entityId));
/*  42 */     if (this.entityReplacements.containsKey(Integer.valueOf(entityId))) {
/*  43 */       ((EntityReplacement)this.entityReplacements.remove(Integer.valueOf(entityId))).despawn();
/*     */     }
/*  45 */     if (this.playersByEntityId.containsKey(Integer.valueOf(entityId))) {
/*  46 */       UUID playerId = this.playersByEntityId.remove(Integer.valueOf(entityId));
/*  47 */       this.playersByUniqueId.remove(playerId);
/*  48 */       this.playerEquipment.remove(playerId);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPlayer(Integer entityId, UUID uuid) {
/*  53 */     this.playersByUniqueId.put(uuid, entityId);
/*  54 */     this.playersByEntityId.put(entityId, uuid);
/*     */   }
/*     */   
/*     */   public UUID getPlayerUUID(int entityId) {
/*  58 */     return this.playersByEntityId.get(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public int getPlayerEntityId(UUID uuid) {
/*  62 */     return ((Integer)this.playersByUniqueId.getOrDefault(uuid, Integer.valueOf(-1))).intValue();
/*     */   }
/*     */   
/*     */   public Item getPlayerEquipment(UUID uuid, int slot) {
/*  66 */     Item[] items = this.playerEquipment.get(uuid);
/*  67 */     if (items == null || slot < 0 || slot >= items.length) return null; 
/*  68 */     return items[slot];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerEquipment(UUID uuid, Item equipment, int slot) {
/*  73 */     Item[] items = this.playerEquipment.computeIfAbsent(uuid, it -> new Item[5]);
/*  74 */     if (slot < 0 || slot >= items.length)
/*  75 */       return;  items[slot] = equipment;
/*     */   }
/*     */   
/*     */   public Map<Integer, Entity1_10Types.EntityType> getClientEntityTypes() {
/*  79 */     return this.clientEntityTypes;
/*     */   }
/*     */   
/*     */   public void addMetadataToBuffer(int entityID, List<Metadata> metadataList) {
/*  83 */     if (this.metadataBuffer.containsKey(Integer.valueOf(entityID))) {
/*  84 */       ((List<Metadata>)this.metadataBuffer.get(Integer.valueOf(entityID))).addAll(metadataList);
/*  85 */     } else if (!metadataList.isEmpty()) {
/*  86 */       this.metadataBuffer.put(Integer.valueOf(entityID), metadataList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEntityReplacement(EntityReplacement entityReplacement) {
/*  91 */     this.entityReplacements.put(Integer.valueOf(entityReplacement.getEntityId()), entityReplacement);
/*     */   }
/*     */   
/*     */   public EntityReplacement getEntityReplacement(int entityId) {
/*  95 */     return this.entityReplacements.get(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public List<Metadata> getBufferedMetadata(int entityId) {
/*  99 */     return this.metadataBuffer.get(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public void sendMetadataBuffer(int entityId) {
/* 103 */     if (!this.metadataBuffer.containsKey(Integer.valueOf(entityId)))
/* 104 */       return;  if (this.entityReplacements.containsKey(Integer.valueOf(entityId))) {
/* 105 */       ((EntityReplacement)this.entityReplacements.get(Integer.valueOf(entityId))).updateMetadata(this.metadataBuffer.remove(Integer.valueOf(entityId)));
/*     */     } else {
/* 107 */       Entity1_10Types.EntityType type = getClientEntityTypes().get(Integer.valueOf(entityId));
/* 108 */       PacketWrapper wrapper = PacketWrapper.create(28, null, getUser());
/* 109 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 110 */       wrapper.write(Types1_8.METADATA_LIST, this.metadataBuffer.get(Integer.valueOf(entityId)));
/* 111 */       MetadataRewriter.transform(type, this.metadataBuffer.get(Integer.valueOf(entityId)));
/* 112 */       if (!((List)this.metadataBuffer.get(Integer.valueOf(entityId))).isEmpty()) {
/* 113 */         PacketUtil.sendPacket(wrapper, Protocol1_7_6_10TO1_8.class);
/*     */       }
/*     */       
/* 116 */       this.metadataBuffer.remove(Integer.valueOf(entityId));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getVehicle(int passengerId) {
/* 121 */     for (Map.Entry<Integer, Integer> vehicle : this.vehicles.entrySet()) {
/* 122 */       if (((Integer)vehicle.getValue()).intValue() == passengerId) return ((Integer)vehicle.getValue()).intValue(); 
/*     */     } 
/* 124 */     return -1;
/*     */   }
/*     */   
/*     */   public int getPassenger(int vehicleId) {
/* 128 */     return ((Integer)this.vehicles.getOrDefault(Integer.valueOf(vehicleId), Integer.valueOf(-1))).intValue();
/*     */   }
/*     */   
/*     */   public void setPassenger(int vehicleId, int passengerId) {
/* 132 */     if (vehicleId == this.spectating && this.spectating != this.playerId)
/*     */       
/* 134 */       try { PacketWrapper sneakPacket = PacketWrapper.create(11, null, getUser());
/* 135 */         sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(this.playerId));
/* 136 */         sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 137 */         sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */         
/* 139 */         PacketWrapper unsneakPacket = PacketWrapper.create(11, null, getUser());
/* 140 */         unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(this.playerId));
/* 141 */         unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(1));
/* 142 */         unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */         
/* 144 */         PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class, true, true);
/*     */         
/* 146 */         setSpectating(this.playerId); }
/* 147 */       catch (Exception ex) { ex.printStackTrace(); }
/*     */        
/* 149 */     if (vehicleId == -1) {
/* 150 */       int oldVehicleId = getVehicle(passengerId);
/* 151 */       this.vehicles.remove(Integer.valueOf(oldVehicleId));
/* 152 */     } else if (passengerId == -1) {
/* 153 */       this.vehicles.remove(Integer.valueOf(vehicleId));
/*     */     } else {
/* 155 */       this.vehicles.put(Integer.valueOf(vehicleId), Integer.valueOf(passengerId));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSpectating() {
/* 160 */     return this.spectating;
/*     */   }
/*     */   
/*     */   public boolean setSpectating(int spectating) {
/* 164 */     if (spectating != this.playerId && getPassenger(spectating) != -1) {
/*     */       
/* 166 */       PacketWrapper sneakPacket = PacketWrapper.create(11, null, getUser());
/* 167 */       sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(this.playerId));
/* 168 */       sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 169 */       sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */       
/* 171 */       PacketWrapper unsneakPacket = PacketWrapper.create(11, null, getUser());
/* 172 */       unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(this.playerId));
/* 173 */       unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(1));
/* 174 */       unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */       
/* 176 */       PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class, true, true);
/*     */       
/* 178 */       setSpectating(this.playerId);
/* 179 */       return false;
/*     */     } 
/*     */     
/* 182 */     if (this.spectating != spectating && this.spectating != this.playerId) {
/* 183 */       PacketWrapper unmount = PacketWrapper.create(27, null, getUser());
/* 184 */       unmount.write((Type)Type.INT, Integer.valueOf(this.playerId));
/* 185 */       unmount.write((Type)Type.INT, Integer.valueOf(-1));
/* 186 */       unmount.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/* 187 */       PacketUtil.sendPacket(unmount, Protocol1_7_6_10TO1_8.class);
/*     */     } 
/* 189 */     this.spectating = spectating;
/* 190 */     if (spectating != this.playerId) {
/* 191 */       PacketWrapper mount = PacketWrapper.create(27, null, getUser());
/* 192 */       mount.write((Type)Type.INT, Integer.valueOf(this.playerId));
/* 193 */       mount.write((Type)Type.INT, Integer.valueOf(this.spectating));
/* 194 */       mount.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/* 195 */       PacketUtil.sendPacket(mount, Protocol1_7_6_10TO1_8.class);
/*     */     } 
/* 197 */     return true;
/*     */   }
/*     */   
/*     */   public int getGamemode() {
/* 201 */     return this.gamemode;
/*     */   }
/*     */   
/*     */   public void setGamemode(int gamemode) {
/* 205 */     this.gamemode = gamemode;
/*     */   }
/*     */   
/*     */   public int getPlayerId() {
/* 209 */     return this.playerId;
/*     */   }
/*     */   
/*     */   public void setPlayerId(int playerId) {
/* 213 */     this.playerId = this.spectating = playerId;
/*     */   }
/*     */   
/*     */   public void clearEntities() {
/* 217 */     this.clientEntityTypes.clear();
/* 218 */     this.entityReplacements.clear();
/* 219 */     this.vehicles.clear();
/* 220 */     this.metadataBuffer.clear();
/*     */   }
/*     */   
/*     */   public int getDimension() {
/* 224 */     return this.dimension;
/*     */   }
/*     */   
/*     */   public void setDimension(int dimension) {
/* 228 */     this.dimension = dimension;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientEntityId(int playerEntityId) {
/* 233 */     if (this.spectating == this.playerId) {
/* 234 */       this.spectating = playerEntityId;
/*     */     }
/* 236 */     this.clientEntityTypes.remove(Integer.valueOf(this.playerId));
/* 237 */     this.playerId = playerEntityId;
/* 238 */     this.clientEntityTypes.put(Integer.valueOf(this.playerId), Entity1_10Types.EntityType.ENTITY_HUMAN);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\EntityTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */