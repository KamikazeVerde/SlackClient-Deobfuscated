/*    */ package com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage;
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
/*    */ public class ResourcePackTracker
/*    */   implements StorableObject
/*    */ {
/* 23 */   private String lastHash = "";
/*    */   
/*    */   public String getLastHash() {
/* 26 */     return this.lastHash;
/*    */   }
/*    */   
/*    */   public void setLastHash(String lastHash) {
/* 30 */     this.lastHash = lastHash;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 35 */     return "ResourcePackTracker{lastHash='" + this.lastHash + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_10to1_9_3\storage\ResourcePackTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */