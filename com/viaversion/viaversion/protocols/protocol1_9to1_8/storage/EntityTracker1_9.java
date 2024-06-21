/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTracker1_9
/*     */   extends EntityTrackerBase
/*     */ {
/*     */   public static final String WITHER_TRANSLATABLE = "{\"translate\":\"entity.WitherBoss.name\"}";
/*     */   public static final String DRAGON_TRANSLATABLE = "{\"translate\":\"entity.EnderDragon.name\"}";
/*  56 */   private final Int2ObjectMap<UUID> uuidMap = (Int2ObjectMap<UUID>)Int2ObjectSyncMap.hashmap();
/*  57 */   private final Int2ObjectMap<List<Metadata>> metadataBuffer = (Int2ObjectMap<List<Metadata>>)Int2ObjectSyncMap.hashmap();
/*  58 */   private final Int2ObjectMap<Integer> vehicleMap = (Int2ObjectMap<Integer>)Int2ObjectSyncMap.hashmap();
/*  59 */   private final Int2ObjectMap<BossBar> bossBarMap = (Int2ObjectMap<BossBar>)Int2ObjectSyncMap.hashmap();
/*  60 */   private final IntSet validBlocking = Int2ObjectSyncMap.hashset();
/*  61 */   private final Set<Integer> knownHolograms = (Set<Integer>)Int2ObjectSyncMap.hashset();
/*  62 */   private final Set<Position> blockInteractions = Collections.newSetFromMap(CacheBuilder.newBuilder()
/*  63 */       .maximumSize(1000L)
/*  64 */       .expireAfterAccess(250L, TimeUnit.MILLISECONDS)
/*  65 */       .build()
/*  66 */       .asMap());
/*     */   private boolean blocking = false;
/*     */   private boolean autoTeam = false;
/*  69 */   private Position currentlyDigging = null;
/*     */   private boolean teamExists = false;
/*     */   private GameMode gameMode;
/*     */   private String currentTeam;
/*     */   private int heldItemSlot;
/*  74 */   private Item itemInSecondHand = null;
/*     */   
/*     */   public EntityTracker1_9(UserConnection user) {
/*  77 */     super(user, (EntityType)Entity1_10Types.EntityType.PLAYER);
/*     */   }
/*     */   
/*     */   public UUID getEntityUUID(int id) {
/*  81 */     UUID uuid = (UUID)this.uuidMap.get(id);
/*  82 */     if (uuid == null) {
/*  83 */       uuid = UUID.randomUUID();
/*  84 */       this.uuidMap.put(id, uuid);
/*     */     } 
/*     */     
/*  87 */     return uuid;
/*     */   }
/*     */   
/*     */   public void setSecondHand(Item item) {
/*  91 */     setSecondHand(clientEntityId(), item);
/*     */   }
/*     */   
/*     */   public void setSecondHand(int entityID, Item item) {
/*  95 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_EQUIPMENT, null, user());
/*  96 */     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityID));
/*  97 */     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*  98 */     wrapper.write(Type.ITEM, this.itemInSecondHand = item);
/*     */     try {
/* 100 */       wrapper.scheduleSend(Protocol1_9To1_8.class);
/* 101 */     } catch (Exception e) {
/* 102 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getItemInSecondHand() {
/* 107 */     return this.itemInSecondHand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void syncShieldWithSword() {
/* 115 */     boolean swordInHand = hasSwordInHand();
/*     */ 
/*     */     
/* 118 */     if (!swordInHand || this.itemInSecondHand == null)
/*     */     {
/*     */       
/* 121 */       setSecondHand(swordInHand ? (Item)new DataItem(442, (byte)1, (short)0, null) : null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSwordInHand() {
/* 131 */     InventoryTracker inventoryTracker = (InventoryTracker)user().get(InventoryTracker.class);
/*     */ 
/*     */     
/* 134 */     int inventorySlot = this.heldItemSlot + 36;
/* 135 */     int itemIdentifier = inventoryTracker.getItemId((short)0, (short)inventorySlot);
/*     */     
/* 137 */     return Protocol1_9To1_8.isSword(itemIdentifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntity(int entityId) {
/* 142 */     super.removeEntity(entityId);
/*     */     
/* 144 */     this.vehicleMap.remove(entityId);
/* 145 */     this.uuidMap.remove(entityId);
/* 146 */     this.validBlocking.remove(entityId);
/* 147 */     this.knownHolograms.remove(Integer.valueOf(entityId));
/* 148 */     this.metadataBuffer.remove(entityId);
/*     */     
/* 150 */     BossBar bar = (BossBar)this.bossBarMap.remove(entityId);
/* 151 */     if (bar != null) {
/* 152 */       bar.hide();
/*     */       
/* 154 */       ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleRemove(user(), bar.getId());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean interactedBlockRecently(int x, int y, int z) {
/* 159 */     return this.blockInteractions.contains(new Position(x, y, z));
/*     */   }
/*     */   
/*     */   public void addBlockInteraction(Position p) {
/* 163 */     this.blockInteractions.add(p);
/*     */   }
/*     */   
/*     */   public void handleMetadata(int entityId, List<Metadata> metadataList) {
/* 167 */     EntityType type = entityType(entityId);
/* 168 */     if (type == null) {
/*     */       return;
/*     */     }
/*     */     
/* 172 */     for (Metadata metadata : new ArrayList(metadataList)) {
/*     */       
/* 174 */       if (type == Entity1_10Types.EntityType.WITHER && 
/* 175 */         metadata.id() == 10) {
/* 176 */         metadataList.remove(metadata);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 181 */       if (type == Entity1_10Types.EntityType.ENDER_DRAGON && 
/* 182 */         metadata.id() == 11) {
/* 183 */         metadataList.remove(metadata);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 188 */       if (type == Entity1_10Types.EntityType.SKELETON && 
/* 189 */         getMetaByIndex(metadataList, 12) == null) {
/* 190 */         metadataList.add(new Metadata(12, (MetaType)MetaType1_9.Boolean, Boolean.valueOf(true)));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 195 */       if (type == Entity1_10Types.EntityType.HORSE)
/*     */       {
/* 197 */         if (metadata.id() == 16 && ((Integer)metadata.getValue()).intValue() == Integer.MIN_VALUE) {
/* 198 */           metadata.setValue(Integer.valueOf(0));
/*     */         }
/*     */       }
/* 201 */       if (type == Entity1_10Types.EntityType.PLAYER) {
/* 202 */         if (metadata.id() == 0) {
/*     */           
/* 204 */           byte data = ((Byte)metadata.getValue()).byteValue();
/* 205 */           if (entityId != getProvidedEntityId() && Via.getConfig().isShieldBlocking()) {
/* 206 */             if ((data & 0x10) == 16) {
/* 207 */               if (this.validBlocking.contains(entityId)) {
/* 208 */                 DataItem dataItem = new DataItem(442, (byte)1, (short)0, null);
/* 209 */                 setSecondHand(entityId, (Item)dataItem);
/*     */               } else {
/* 211 */                 setSecondHand(entityId, (Item)null);
/*     */               } 
/*     */             } else {
/* 214 */               setSecondHand(entityId, (Item)null);
/*     */             } 
/*     */           }
/*     */         } 
/* 218 */         if (metadata.id() == 12 && Via.getConfig().isLeftHandedHandling()) {
/* 219 */           metadataList.add(new Metadata(13, (MetaType)MetaType1_9.Byte, 
/*     */ 
/*     */                 
/* 222 */                 Byte.valueOf((byte)(((((Byte)metadata.getValue()).byteValue() & 0x80) != 0) ? 0 : 1))));
/*     */         }
/*     */       } 
/*     */       
/* 226 */       if (type == Entity1_10Types.EntityType.ARMOR_STAND && Via.getConfig().isHologramPatch() && 
/* 227 */         metadata.id() == 0 && getMetaByIndex(metadataList, 10) != null) {
/* 228 */         Metadata meta = getMetaByIndex(metadataList, 10);
/* 229 */         byte data = ((Byte)metadata.getValue()).byteValue();
/*     */         
/*     */         Metadata displayName, displayNameVisible;
/*     */         
/* 233 */         if ((data & 0x20) == 32 && (((Byte)meta.getValue()).byteValue() & 0x1) == 1 && (
/* 234 */           displayName = getMetaByIndex(metadataList, 2)) != null && !((String)displayName.getValue()).isEmpty() && (
/* 235 */           displayNameVisible = getMetaByIndex(metadataList, 3)) != null && ((Boolean)displayNameVisible.getValue()).booleanValue() && 
/* 236 */           !this.knownHolograms.contains(Integer.valueOf(entityId))) {
/* 237 */           this.knownHolograms.add(Integer.valueOf(entityId));
/*     */           
/*     */           try {
/* 240 */             PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_POSITION, null, user());
/* 241 */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 242 */             wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 243 */             wrapper.write((Type)Type.SHORT, Short.valueOf((short)(int)(128.0D * Via.getConfig().getHologramYOffset() * 32.0D)));
/* 244 */             wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 245 */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/* 246 */             wrapper.scheduleSend(Protocol1_9To1_8.class);
/* 247 */           } catch (Exception exception) {}
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       if (Via.getConfig().isBossbarPatch() && (
/* 255 */         type == Entity1_10Types.EntityType.ENDER_DRAGON || type == Entity1_10Types.EntityType.WITHER)) {
/* 256 */         if (metadata.id() == 2) {
/* 257 */           BossBar bar = (BossBar)this.bossBarMap.get(entityId);
/* 258 */           String title = (String)metadata.getValue();
/* 259 */           title = title.isEmpty() ? ((type == Entity1_10Types.EntityType.ENDER_DRAGON) ? "{\"translate\":\"entity.EnderDragon.name\"}" : "{\"translate\":\"entity.WitherBoss.name\"}") : title;
/* 260 */           if (bar == null) {
/* 261 */             bar = Via.getAPI().legacyAPI().createLegacyBossBar(title, BossColor.PINK, BossStyle.SOLID);
/* 262 */             this.bossBarMap.put(entityId, bar);
/* 263 */             bar.addConnection(user());
/* 264 */             bar.show();
/*     */ 
/*     */             
/* 267 */             ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(user(), bar.getId()); continue;
/*     */           } 
/* 269 */           bar.setTitle(title); continue;
/*     */         } 
/* 271 */         if (metadata.id() == 6 && !Via.getConfig().isBossbarAntiflicker()) {
/* 272 */           BossBar bar = (BossBar)this.bossBarMap.get(entityId);
/*     */           
/* 274 */           float maxHealth = (type == Entity1_10Types.EntityType.ENDER_DRAGON) ? 200.0F : 300.0F;
/* 275 */           float health = Math.max(0.0F, Math.min(((Float)metadata.getValue()).floatValue() / maxHealth, 1.0F));
/* 276 */           if (bar == null) {
/* 277 */             String title = (type == Entity1_10Types.EntityType.ENDER_DRAGON) ? "{\"translate\":\"entity.EnderDragon.name\"}" : "{\"translate\":\"entity.WitherBoss.name\"}";
/* 278 */             bar = Via.getAPI().legacyAPI().createLegacyBossBar(title, health, BossColor.PINK, BossStyle.SOLID);
/* 279 */             this.bossBarMap.put(entityId, bar);
/* 280 */             bar.addConnection(user());
/* 281 */             bar.show();
/*     */             
/* 283 */             ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(user(), bar.getId()); continue;
/*     */           } 
/* 285 */           bar.setHealth(health);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Metadata getMetaByIndex(List<Metadata> list, int index) {
/* 294 */     for (Metadata meta : list) {
/* 295 */       if (index == meta.id())
/* 296 */         return meta; 
/*     */     } 
/* 298 */     return null;
/*     */   }
/*     */   
/*     */   public void sendTeamPacket(boolean add, boolean now) {
/* 302 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.TEAMS, null, user());
/* 303 */     wrapper.write(Type.STRING, "viaversion");
/* 304 */     if (add) {
/*     */       
/* 306 */       if (!this.teamExists) {
/* 307 */         wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 308 */         wrapper.write(Type.STRING, "viaversion");
/* 309 */         wrapper.write(Type.STRING, "§f");
/* 310 */         wrapper.write(Type.STRING, "");
/* 311 */         wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 312 */         wrapper.write(Type.STRING, "");
/* 313 */         wrapper.write(Type.STRING, "never");
/* 314 */         wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)15));
/*     */       } else {
/* 316 */         wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)3));
/*     */       } 
/* 318 */       wrapper.write(Type.STRING_ARRAY, new String[] { user().getProtocolInfo().getUsername() });
/*     */     } else {
/* 320 */       wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)1));
/*     */     } 
/* 322 */     this.teamExists = add;
/*     */     try {
/* 324 */       if (now) {
/* 325 */         wrapper.send(Protocol1_9To1_8.class);
/*     */       } else {
/* 327 */         wrapper.scheduleSend(Protocol1_9To1_8.class);
/*     */       } 
/* 329 */     } catch (Exception e) {
/* 330 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addMetadataToBuffer(int entityID, List<Metadata> metadataList) {
/* 335 */     List<Metadata> metadata = (List<Metadata>)this.metadataBuffer.get(entityID);
/* 336 */     if (metadata != null) {
/* 337 */       metadata.addAll(metadataList);
/*     */     } else {
/* 339 */       this.metadataBuffer.put(entityID, metadataList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendMetadataBuffer(int entityId) {
/* 344 */     List<Metadata> metadataList = (List<Metadata>)this.metadataBuffer.get(entityId);
/* 345 */     if (metadataList != null) {
/* 346 */       PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_METADATA, null, user());
/* 347 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 348 */       wrapper.write(Types1_9.METADATA_LIST, metadataList);
/* 349 */       ((MetadataRewriter1_9To1_8)((Protocol1_9To1_8)Via.getManager().getProtocolManager().getProtocol(Protocol1_9To1_8.class)).get(MetadataRewriter1_9To1_8.class))
/* 350 */         .handleMetadata(entityId, metadataList, user());
/* 351 */       handleMetadata(entityId, metadataList);
/* 352 */       if (!metadataList.isEmpty()) {
/*     */         try {
/* 354 */           wrapper.scheduleSend(Protocol1_9To1_8.class);
/* 355 */         } catch (Exception e) {
/* 356 */           e.printStackTrace();
/*     */         } 
/*     */       }
/* 359 */       this.metadataBuffer.remove(entityId);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getProvidedEntityId() {
/*     */     try {
/* 365 */       return ((EntityIdProvider)Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(user());
/* 366 */     } catch (Exception e) {
/* 367 */       return clientEntityId();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<Integer, UUID> getUuidMap() {
/* 372 */     return (Map)this.uuidMap;
/*     */   }
/*     */   
/*     */   public Map<Integer, List<Metadata>> getMetadataBuffer() {
/* 376 */     return (Map)this.metadataBuffer;
/*     */   }
/*     */   
/*     */   public Map<Integer, Integer> getVehicleMap() {
/* 380 */     return (Map)this.vehicleMap;
/*     */   }
/*     */   
/*     */   public Map<Integer, BossBar> getBossBarMap() {
/* 384 */     return (Map)this.bossBarMap;
/*     */   }
/*     */   
/*     */   public Set<Integer> getValidBlocking() {
/* 388 */     return (Set<Integer>)this.validBlocking;
/*     */   }
/*     */   
/*     */   public Set<Integer> getKnownHolograms() {
/* 392 */     return this.knownHolograms;
/*     */   }
/*     */   
/*     */   public Set<Position> getBlockInteractions() {
/* 396 */     return this.blockInteractions;
/*     */   }
/*     */   
/*     */   public boolean isBlocking() {
/* 400 */     return this.blocking;
/*     */   }
/*     */   
/*     */   public void setBlocking(boolean blocking) {
/* 404 */     this.blocking = blocking;
/*     */   }
/*     */   
/*     */   public boolean isAutoTeam() {
/* 408 */     return this.autoTeam;
/*     */   }
/*     */   
/*     */   public void setAutoTeam(boolean autoTeam) {
/* 412 */     this.autoTeam = autoTeam;
/*     */   }
/*     */   
/*     */   public Position getCurrentlyDigging() {
/* 416 */     return this.currentlyDigging;
/*     */   }
/*     */   
/*     */   public void setCurrentlyDigging(Position currentlyDigging) {
/* 420 */     this.currentlyDigging = currentlyDigging;
/*     */   }
/*     */   
/*     */   public boolean isTeamExists() {
/* 424 */     return this.teamExists;
/*     */   }
/*     */   
/*     */   public GameMode getGameMode() {
/* 428 */     return this.gameMode;
/*     */   }
/*     */   
/*     */   public void setGameMode(GameMode gameMode) {
/* 432 */     this.gameMode = gameMode;
/*     */   }
/*     */   
/*     */   public String getCurrentTeam() {
/* 436 */     return this.currentTeam;
/*     */   }
/*     */   
/*     */   public void setCurrentTeam(String currentTeam) {
/* 440 */     this.currentTeam = currentTeam;
/*     */   }
/*     */   
/*     */   public void setHeldItemSlot(int heldItemSlot) {
/* 444 */     this.heldItemSlot = heldItemSlot;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\storage\EntityTracker1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */