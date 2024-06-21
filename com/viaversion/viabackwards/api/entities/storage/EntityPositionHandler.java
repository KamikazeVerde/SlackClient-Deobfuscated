/*     */ package com.viaversion.viabackwards.api.entities.storage;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriterBase;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityPositionHandler
/*     */ {
/*     */   public static final double RELATIVE_MOVE_FACTOR = 4096.0D;
/*     */   private final EntityRewriterBase<?, ?> entityRewriter;
/*     */   private final Class<? extends EntityPositionStorage> storageClass;
/*     */   private final Supplier<? extends EntityPositionStorage> storageSupplier;
/*     */   private boolean warnedForMissingEntity;
/*     */   
/*     */   public EntityPositionHandler(EntityRewriterBase<?, ?> entityRewriter, Class<? extends EntityPositionStorage> storageClass, Supplier<? extends EntityPositionStorage> storageSupplier) {
/*  39 */     this.entityRewriter = entityRewriter;
/*  40 */     this.storageClass = storageClass;
/*  41 */     this.storageSupplier = storageSupplier;
/*     */   }
/*     */   
/*     */   public void cacheEntityPosition(PacketWrapper wrapper, boolean create, boolean relative) throws Exception {
/*  45 */     cacheEntityPosition(wrapper, ((Double)wrapper
/*  46 */         .get((Type)Type.DOUBLE, 0)).doubleValue(), ((Double)wrapper.get((Type)Type.DOUBLE, 1)).doubleValue(), ((Double)wrapper.get((Type)Type.DOUBLE, 2)).doubleValue(), create, relative);
/*     */   }
/*     */   public void cacheEntityPosition(PacketWrapper wrapper, double x, double y, double z, boolean create, boolean relative) throws Exception {
/*     */     EntityPositionStorage positionStorage;
/*  50 */     int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*  51 */     StoredEntityData storedEntity = this.entityRewriter.tracker(wrapper.user()).entityData(entityId);
/*  52 */     if (storedEntity == null) {
/*  53 */       if (Via.getManager().isDebug()) {
/*  54 */         ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + entityId + " missing at position: " + x + " - " + y + " - " + z + " in " + this.storageClass.getSimpleName());
/*  55 */         if (entityId == -1 && x == 0.0D && y == 0.0D && z == 0.0D) {
/*  56 */           ViaBackwards.getPlatform().getLogger().warning("DO NOT REPORT THIS TO VIA, THIS IS A PLUGIN ISSUE");
/*  57 */         } else if (!this.warnedForMissingEntity) {
/*  58 */           this.warnedForMissingEntity = true;
/*  59 */           ViaBackwards.getPlatform().getLogger().warning("This is very likely caused by a plugin sending a teleport packet for an entity outside of the player's range.");
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  66 */     if (create) {
/*  67 */       positionStorage = this.storageSupplier.get();
/*  68 */       storedEntity.put(positionStorage);
/*     */     } else {
/*  70 */       positionStorage = (EntityPositionStorage)storedEntity.get(this.storageClass);
/*  71 */       if (positionStorage == null) {
/*  72 */         ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + entityId + " missing " + this.storageClass.getSimpleName());
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  77 */     positionStorage.setCoordinates(x, y, z, relative);
/*     */   }
/*     */   
/*     */   public EntityPositionStorage getStorage(UserConnection user, int entityId) {
/*  81 */     StoredEntityData storedEntity = this.entityRewriter.tracker(user).entityData(entityId);
/*     */     EntityPositionStorage entityStorage;
/*  83 */     if (storedEntity == null || (entityStorage = (EntityPositionStorage)storedEntity.get(EntityPositionStorage.class)) == null) {
/*  84 */       ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + entityId + " in " + this.storageClass.getSimpleName());
/*  85 */       return null;
/*     */     } 
/*  87 */     return entityStorage;
/*     */   }
/*     */   
/*     */   public static void writeFacingAngles(PacketWrapper wrapper, double x, double y, double z, double targetX, double targetY, double targetZ) {
/*  91 */     double dX = targetX - x;
/*  92 */     double dY = targetY - y;
/*  93 */     double dZ = targetZ - z;
/*  94 */     double r = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*  95 */     double yaw = -Math.atan2(dX, dZ) / Math.PI * 180.0D;
/*  96 */     if (yaw < 0.0D) {
/*  97 */       yaw = 360.0D + yaw;
/*     */     }
/*  99 */     double pitch = -Math.asin(dY / r) / Math.PI * 180.0D;
/*     */     
/* 101 */     wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(yaw * 256.0D / 360.0D)));
/* 102 */     wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(pitch * 256.0D / 360.0D)));
/*     */   }
/*     */   
/*     */   public static void writeFacingDegrees(PacketWrapper wrapper, double x, double y, double z, double targetX, double targetY, double targetZ) {
/* 106 */     double dX = targetX - x;
/* 107 */     double dY = targetY - y;
/* 108 */     double dZ = targetZ - z;
/* 109 */     double r = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/* 110 */     double yaw = -Math.atan2(dX, dZ) / Math.PI * 180.0D;
/* 111 */     if (yaw < 0.0D) {
/* 112 */       yaw = 360.0D + yaw;
/*     */     }
/* 114 */     double pitch = -Math.asin(dY / r) / Math.PI * 180.0D;
/*     */     
/* 116 */     wrapper.write((Type)Type.FLOAT, Float.valueOf((float)yaw));
/* 117 */     wrapper.write((Type)Type.FLOAT, Float.valueOf((float)pitch));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\entities\storage\EntityPositionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */