/*    */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
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
/*    */ 
/*    */ public class WindowTracker
/*    */   implements StorableObject
/*    */ {
/*    */   private String inventory;
/* 25 */   private int entityId = -1;
/*    */   
/*    */   public String getInventory() {
/* 28 */     return this.inventory;
/*    */   }
/*    */   
/*    */   public void setInventory(String inventory) {
/* 32 */     this.inventory = inventory;
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 36 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public void setEntityId(int entityId) {
/* 40 */     this.entityId = entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "WindowTracker{inventory='" + this.inventory + '\'' + ", entityId=" + this.entityId + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\storage\WindowTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */