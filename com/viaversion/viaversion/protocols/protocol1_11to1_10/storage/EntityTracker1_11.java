/*    */ package com.viaversion.viaversion.protocols.protocol1_11to1_10.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTracker1_11
/*    */   extends EntityTrackerBase
/*    */ {
/* 27 */   private final IntSet holograms = Int2ObjectSyncMap.hashset();
/*    */   
/*    */   public EntityTracker1_11(UserConnection user) {
/* 30 */     super(user, (EntityType)Entity1_11Types.EntityType.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeEntity(int entityId) {
/* 35 */     super.removeEntity(entityId);
/*    */     
/* 37 */     removeHologram(entityId);
/*    */   }
/*    */   
/*    */   public boolean addHologram(int entId) {
/* 41 */     return this.holograms.add(entId);
/*    */   }
/*    */   
/*    */   public boolean isHologram(int entId) {
/* 45 */     return this.holograms.contains(entId);
/*    */   }
/*    */   
/*    */   public void removeHologram(int entId) {
/* 49 */     this.holograms.remove(entId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\storage\EntityTracker1_11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */