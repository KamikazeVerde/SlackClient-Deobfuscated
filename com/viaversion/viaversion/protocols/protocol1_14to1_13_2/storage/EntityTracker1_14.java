/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
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
/*     */ 
/*     */ 
/*     */ public class EntityTracker1_14
/*     */   extends EntityTrackerBase
/*     */ {
/*  27 */   private final Int2ObjectMap<Byte> insentientData = (Int2ObjectMap<Byte>)Int2ObjectSyncMap.hashmap();
/*     */   
/*  29 */   private final Int2ObjectMap<Byte> sleepingAndRiptideData = (Int2ObjectMap<Byte>)Int2ObjectSyncMap.hashmap();
/*  30 */   private final Int2ObjectMap<Byte> playerEntityFlags = (Int2ObjectMap<Byte>)Int2ObjectSyncMap.hashmap();
/*     */   
/*     */   private int latestTradeWindowId;
/*     */   private boolean forceSendCenterChunk = true;
/*     */   
/*     */   public EntityTracker1_14(UserConnection user) {
/*  36 */     super(user, (EntityType)Entity1_14Types.PLAYER);
/*     */   }
/*     */   private int chunkCenterX; private int chunkCenterZ;
/*     */   
/*     */   public void removeEntity(int entityId) {
/*  41 */     super.removeEntity(entityId);
/*     */     
/*  43 */     this.insentientData.remove(entityId);
/*  44 */     this.sleepingAndRiptideData.remove(entityId);
/*  45 */     this.playerEntityFlags.remove(entityId);
/*     */   }
/*     */   
/*     */   public byte getInsentientData(int entity) {
/*  49 */     Byte val = (Byte)this.insentientData.get(entity);
/*  50 */     return (val == null) ? 0 : val.byteValue();
/*     */   }
/*     */   
/*     */   public void setInsentientData(int entity, byte value) {
/*  54 */     this.insentientData.put(entity, Byte.valueOf(value));
/*     */   }
/*     */   
/*     */   private static byte zeroIfNull(Byte val) {
/*  58 */     if (val == null) return 0; 
/*  59 */     return val.byteValue();
/*     */   }
/*     */   
/*     */   public boolean isSleeping(int player) {
/*  63 */     return ((zeroIfNull((Byte)this.sleepingAndRiptideData.get(player)) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setSleeping(int player, boolean value) {
/*  67 */     byte newValue = (byte)(zeroIfNull((Byte)this.sleepingAndRiptideData.get(player)) & 0xFFFFFFFE | (value ? 1 : 0));
/*  68 */     if (newValue == 0) {
/*  69 */       this.sleepingAndRiptideData.remove(player);
/*     */     } else {
/*  71 */       this.sleepingAndRiptideData.put(player, Byte.valueOf(newValue));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isRiptide(int player) {
/*  76 */     return ((zeroIfNull((Byte)this.sleepingAndRiptideData.get(player)) & 0x2) != 0);
/*     */   }
/*     */   
/*     */   public void setRiptide(int player, boolean value) {
/*  80 */     byte newValue = (byte)(zeroIfNull((Byte)this.sleepingAndRiptideData.get(player)) & 0xFFFFFFFD | (value ? 2 : 0));
/*  81 */     if (newValue == 0) {
/*  82 */       this.sleepingAndRiptideData.remove(player);
/*     */     } else {
/*  84 */       this.sleepingAndRiptideData.put(player, Byte.valueOf(newValue));
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte getEntityFlags(int player) {
/*  89 */     return zeroIfNull((Byte)this.playerEntityFlags.get(player));
/*     */   }
/*     */   
/*     */   public void setEntityFlags(int player, byte data) {
/*  93 */     this.playerEntityFlags.put(player, Byte.valueOf(data));
/*     */   }
/*     */   
/*     */   public int getLatestTradeWindowId() {
/*  97 */     return this.latestTradeWindowId;
/*     */   }
/*     */   
/*     */   public void setLatestTradeWindowId(int latestTradeWindowId) {
/* 101 */     this.latestTradeWindowId = latestTradeWindowId;
/*     */   }
/*     */   
/*     */   public boolean isForceSendCenterChunk() {
/* 105 */     return this.forceSendCenterChunk;
/*     */   }
/*     */   
/*     */   public void setForceSendCenterChunk(boolean forceSendCenterChunk) {
/* 109 */     this.forceSendCenterChunk = forceSendCenterChunk;
/*     */   }
/*     */   
/*     */   public int getChunkCenterX() {
/* 113 */     return this.chunkCenterX;
/*     */   }
/*     */   
/*     */   public void setChunkCenterX(int chunkCenterX) {
/* 117 */     this.chunkCenterX = chunkCenterX;
/*     */   }
/*     */   
/*     */   public int getChunkCenterZ() {
/* 121 */     return this.chunkCenterZ;
/*     */   }
/*     */   
/*     */   public void setChunkCenterZ(int chunkCenterZ) {
/* 125 */     this.chunkCenterZ = chunkCenterZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\storage\EntityTracker1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */