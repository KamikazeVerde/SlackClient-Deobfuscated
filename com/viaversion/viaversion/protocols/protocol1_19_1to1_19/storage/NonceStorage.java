/*    */ package com.viaversion.viaversion.protocols.protocol1_19_1to1_19.storage;
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
/*    */ 
/*    */ public final class NonceStorage
/*    */   implements StorableObject
/*    */ {
/*    */   private final byte[] nonce;
/*    */   
/*    */   public NonceStorage(byte[] nonce) {
/* 28 */     this.nonce = nonce;
/*    */   }
/*    */   
/*    */   public byte[] nonce() {
/* 32 */     return this.nonce;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_1to1_19\storage\NonceStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */