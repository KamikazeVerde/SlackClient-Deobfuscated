/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;
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
/*    */ public class KeepAliveTracker
/*    */   implements StorableObject
/*    */ {
/* 24 */   private long keepAlive = 2147483647L;
/*    */   
/*    */   public long getKeepAlive() {
/* 27 */     return this.keepAlive;
/*    */   }
/*    */   
/*    */   public void setKeepAlive(long keepAlive) {
/* 31 */     this.keepAlive = keepAlive;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return "KeepAliveTracker{keepAlive=" + this.keepAlive + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_1to1_12_2\KeepAliveTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */