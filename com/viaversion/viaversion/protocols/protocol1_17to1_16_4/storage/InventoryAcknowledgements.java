/*    */ package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntList;
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
/*    */ public final class InventoryAcknowledgements
/*    */   implements StorableObject
/*    */ {
/* 25 */   private final IntList ids = (IntList)new IntArrayList();
/*    */   
/*    */   public void addId(int id) {
/* 28 */     this.ids.add(id);
/*    */   }
/*    */   
/*    */   public boolean removeId(int id) {
/* 32 */     return this.ids.rem(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_17to1_16_4\storage\InventoryAcknowledgements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */