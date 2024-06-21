/*    */ package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage;
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
/*    */ public class ItemTransaction
/*    */ {
/*    */   private final short windowId;
/*    */   private final short slotId;
/*    */   private final short actionId;
/*    */   
/*    */   public ItemTransaction(short windowId, short slotId, short actionId) {
/* 26 */     this.windowId = windowId;
/* 27 */     this.slotId = slotId;
/* 28 */     this.actionId = actionId;
/*    */   }
/*    */   
/*    */   public short getWindowId() {
/* 32 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public short getSlotId() {
/* 36 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public short getActionId() {
/* 40 */     return this.actionId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "ItemTransaction{windowId=" + this.windowId + ", slotId=" + this.slotId + ", actionId=" + this.actionId + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12to1_11_1\storage\ItemTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */