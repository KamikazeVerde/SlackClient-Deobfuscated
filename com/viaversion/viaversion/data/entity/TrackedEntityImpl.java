/*    */ package com.viaversion.viaversion.data.entity;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*    */ import com.viaversion.viaversion.api.data.entity.TrackedEntity;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
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
/*    */ 
/*    */ public final class TrackedEntityImpl
/*    */   implements TrackedEntity
/*    */ {
/*    */   private final EntityType entityType;
/*    */   private StoredEntityData data;
/*    */   private boolean sentMetadata;
/*    */   
/*    */   public TrackedEntityImpl(EntityType entityType) {
/* 30 */     this.entityType = entityType;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType entityType() {
/* 35 */     return this.entityType;
/*    */   }
/*    */ 
/*    */   
/*    */   public StoredEntityData data() {
/* 40 */     if (this.data == null) {
/* 41 */       this.data = new StoredEntityDataImpl(this.entityType);
/*    */     }
/* 43 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasData() {
/* 48 */     return (this.data != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasSentMetadata() {
/* 53 */     return this.sentMetadata;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sentMetadata(boolean sentMetadata) {
/* 58 */     this.sentMetadata = sentMetadata;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\data\entity\TrackedEntityImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */