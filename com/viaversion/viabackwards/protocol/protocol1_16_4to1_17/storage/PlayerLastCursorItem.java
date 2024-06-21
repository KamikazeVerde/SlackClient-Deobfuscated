/*    */ package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
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
/*    */ public class PlayerLastCursorItem
/*    */   implements StorableObject
/*    */ {
/*    */   private Item lastCursorItem;
/*    */   
/*    */   public Item getLastCursorItem() {
/* 28 */     return copyItem(this.lastCursorItem);
/*    */   }
/*    */   
/*    */   public void setLastCursorItem(Item item) {
/* 32 */     this.lastCursorItem = copyItem(item);
/*    */   }
/*    */   
/*    */   public void setLastCursorItem(Item item, int amount) {
/* 36 */     this.lastCursorItem = copyItem(item);
/* 37 */     this.lastCursorItem.setAmount(amount);
/*    */   }
/*    */   
/*    */   public boolean isSet() {
/* 41 */     return (this.lastCursorItem != null);
/*    */   }
/*    */   
/*    */   private static Item copyItem(Item item) {
/* 45 */     if (item == null) {
/* 46 */       return null;
/*    */     }
/* 48 */     DataItem dataItem = new DataItem(item);
/* 49 */     dataItem.setTag((dataItem.tag() == null) ? null : dataItem.tag().clone());
/* 50 */     return (Item)dataItem;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_4to1_17\storage\PlayerLastCursorItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */