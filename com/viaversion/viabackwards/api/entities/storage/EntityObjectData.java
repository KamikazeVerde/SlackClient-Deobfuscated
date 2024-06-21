/*    */ package com.viaversion.viabackwards.api.entities.storage;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
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
/*    */ public class EntityObjectData
/*    */   extends EntityData
/*    */ {
/*    */   private final int objectData;
/*    */   
/*    */   public EntityObjectData(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId, int objectData) {
/* 26 */     super(protocol, key, id, replacementId);
/* 27 */     this.objectData = objectData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isObjectType() {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int objectData() {
/* 37 */     return this.objectData;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\entities\storage\EntityObjectData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */