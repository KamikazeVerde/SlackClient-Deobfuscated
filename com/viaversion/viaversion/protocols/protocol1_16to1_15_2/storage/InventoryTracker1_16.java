/*    */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage;
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
/*    */ public class InventoryTracker1_16
/*    */   implements StorableObject
/*    */ {
/*    */   private boolean inventoryOpen = false;
/*    */   
/*    */   public boolean isInventoryOpen() {
/* 26 */     return this.inventoryOpen;
/*    */   }
/*    */   
/*    */   public void setInventoryOpen(boolean inventoryOpen) {
/* 30 */     this.inventoryOpen = inventoryOpen;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\storage\InventoryTracker1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */