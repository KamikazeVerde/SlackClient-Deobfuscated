/*    */ package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*    */ public final class InventoryStateIds
/*    */   implements StorableObject
/*    */ {
/* 25 */   private final Int2IntMap ids = (Int2IntMap)new Int2IntOpenHashMap();
/*    */ 
/*    */   
/*    */   public InventoryStateIds() {
/* 29 */     this.ids.defaultReturnValue(2147483647);
/*    */   }
/*    */   
/*    */   public void setStateId(short containerId, int id) {
/* 33 */     this.ids.put(containerId, id);
/*    */   }
/*    */   
/*    */   public int removeStateId(short containerId) {
/* 37 */     return this.ids.remove(containerId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_17to1_17_1\storage\InventoryStateIds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */