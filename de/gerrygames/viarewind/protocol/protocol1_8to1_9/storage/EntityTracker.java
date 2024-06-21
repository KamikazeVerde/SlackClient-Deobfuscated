/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
/*     */ import com.viaversion.viaversion.api.minecraft.Vector;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ public class EntityTracker extends StoredObject implements ClientEntityIdChangeListener {
/*  23 */   private final Map<Integer, List<Integer>> vehicleMap = new ConcurrentHashMap<>();
/*  24 */   private final Map<Integer, Entity1_10Types.EntityType> clientEntityTypes = new ConcurrentHashMap<>();
/*  25 */   private final Map<Integer, List<Metadata>> metadataBuffer = new ConcurrentHashMap<>();
/*  26 */   private final Map<Integer, EntityReplacement> entityReplacements = new ConcurrentHashMap<>();
/*  27 */   private final Map<Integer, Vector> entityOffsets = new ConcurrentHashMap<>();
/*     */   private int playerId;
/*  29 */   private int playerGamemode = 0;
/*     */   
/*     */   public EntityTracker(UserConnection user) {
/*  32 */     super(user);
/*     */   }
/*     */   
/*     */   public void setPlayerId(int entityId) {
/*  36 */     this.playerId = entityId;
/*     */   }
/*     */   
/*     */   public int getPlayerId() {
/*  40 */     return this.playerId;
/*     */   }
/*     */   
/*     */   public int getPlayerGamemode() {
/*  44 */     return this.playerGamemode;
/*     */   }
/*     */   
/*     */   public void setPlayerGamemode(int playerGamemode) {
/*  48 */     this.playerGamemode = playerGamemode;
/*     */   }
/*     */   
/*     */   public void removeEntity(int entityId) {
/*  52 */     this.vehicleMap.remove(Integer.valueOf(entityId));
/*  53 */     this.vehicleMap.forEach((vehicle, passengers) -> passengers.remove(Integer.valueOf(entityId)));
/*  54 */     this.vehicleMap.entrySet().removeIf(entry -> ((List)entry.getValue()).isEmpty());
/*  55 */     this.clientEntityTypes.remove(Integer.valueOf(entityId));
/*  56 */     this.entityOffsets.remove(Integer.valueOf(entityId));
/*  57 */     if (this.entityReplacements.containsKey(Integer.valueOf(entityId))) {
/*  58 */       ((EntityReplacement)this.entityReplacements.remove(Integer.valueOf(entityId))).despawn();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetEntityOffset(int entityId) {
/*  63 */     this.entityOffsets.remove(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public Vector getEntityOffset(int entityId) {
/*  67 */     return this.entityOffsets.get(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public void addToEntityOffset(int entityId, short relX, short relY, short relZ) {
/*  71 */     this.entityOffsets.compute(Integer.valueOf(entityId), (key, offset) -> (offset == null) ? new Vector(relX, relY, relZ) : new Vector(offset.blockX() + relX, offset.blockY() + relY, offset.blockZ() + relZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityOffset(int entityId, short relX, short relY, short relZ) {
/*  81 */     this.entityOffsets.compute(Integer.valueOf(entityId), (key, offset) -> new Vector(relX, relY, relZ));
/*     */   }
/*     */   
/*     */   public void setEntityOffset(int entityId, Vector offset) {
/*  85 */     this.entityOffsets.put(Integer.valueOf(entityId), offset);
/*     */   }
/*     */   
/*     */   public List<Integer> getPassengers(int entityId) {
/*  89 */     return this.vehicleMap.getOrDefault(Integer.valueOf(entityId), new ArrayList<>());
/*     */   }
/*     */   
/*     */   public void setPassengers(int entityId, List<Integer> passengers) {
/*  93 */     this.vehicleMap.put(Integer.valueOf(entityId), passengers);
/*     */   }
/*     */   
/*     */   public void addEntityReplacement(EntityReplacement entityReplacement) {
/*  97 */     this.entityReplacements.put(Integer.valueOf(entityReplacement.getEntityId()), entityReplacement);
/*     */   }
/*     */   
/*     */   public EntityReplacement getEntityReplacement(int entityId) {
/* 101 */     return this.entityReplacements.get(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public Map<Integer, Entity1_10Types.EntityType> getClientEntityTypes() {
/* 105 */     return this.clientEntityTypes;
/*     */   }
/*     */   
/*     */   public void addMetadataToBuffer(int entityID, List<Metadata> metadataList) {
/* 109 */     if (this.metadataBuffer.containsKey(Integer.valueOf(entityID))) {
/* 110 */       ((List<Metadata>)this.metadataBuffer.get(Integer.valueOf(entityID))).addAll(metadataList);
/* 111 */     } else if (!metadataList.isEmpty()) {
/* 112 */       this.metadataBuffer.put(Integer.valueOf(entityID), metadataList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Metadata> getBufferedMetadata(int entityId) {
/* 117 */     return this.metadataBuffer.get(Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public boolean isInsideVehicle(int entityId) {
/* 121 */     for (List<Integer> vehicle : this.vehicleMap.values()) {
/* 122 */       if (vehicle.contains(Integer.valueOf(entityId))) return true; 
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public int getVehicle(int passenger) {
/* 128 */     for (Map.Entry<Integer, List<Integer>> vehicle : this.vehicleMap.entrySet()) {
/* 129 */       if (((List)vehicle.getValue()).contains(Integer.valueOf(passenger))) return ((Integer)vehicle.getKey()).intValue(); 
/*     */     } 
/* 131 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean isPassenger(int vehicle, int passenger) {
/* 135 */     return (this.vehicleMap.containsKey(Integer.valueOf(vehicle)) && ((List)this.vehicleMap.get(Integer.valueOf(vehicle))).contains(Integer.valueOf(passenger)));
/*     */   }
/*     */   
/*     */   public void sendMetadataBuffer(int entityId) {
/* 139 */     if (!this.metadataBuffer.containsKey(Integer.valueOf(entityId)))
/* 140 */       return;  if (this.entityReplacements.containsKey(Integer.valueOf(entityId))) {
/* 141 */       ((EntityReplacement)this.entityReplacements.get(Integer.valueOf(entityId))).updateMetadata(this.metadataBuffer.remove(Integer.valueOf(entityId)));
/*     */     } else {
/* 143 */       PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_8.ENTITY_METADATA, getUser());
/* 144 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 145 */       wrapper.write(Types1_8.METADATA_LIST, this.metadataBuffer.get(Integer.valueOf(entityId)));
/* 146 */       MetadataRewriter.transform(getClientEntityTypes().get(Integer.valueOf(entityId)), this.metadataBuffer.get(Integer.valueOf(entityId)));
/* 147 */       if (!((List)this.metadataBuffer.get(Integer.valueOf(entityId))).isEmpty()) {
/*     */         try {
/* 149 */           wrapper.send(Protocol1_8TO1_9.class);
/* 150 */         } catch (Exception ex) {
/* 151 */           ex.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/* 155 */       this.metadataBuffer.remove(Integer.valueOf(entityId));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientEntityId(int playerEntityId) {
/* 161 */     this.clientEntityTypes.remove(Integer.valueOf(this.playerId));
/* 162 */     this.playerId = playerEntityId;
/* 163 */     this.clientEntityTypes.put(Integer.valueOf(this.playerId), Entity1_10Types.EntityType.ENTITY_HUMAN);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\EntityTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */