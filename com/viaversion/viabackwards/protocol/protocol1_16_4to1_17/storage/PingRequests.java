/*    */ package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
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
/*    */ public final class PingRequests
/*    */   implements StorableObject
/*    */ {
/* 25 */   private final IntSet ids = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   public void addId(short id) {
/* 28 */     this.ids.add(id);
/*    */   }
/*    */   
/*    */   public boolean removeId(short id) {
/* 32 */     return this.ids.remove(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_4to1_17\storage\PingRequests.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */