/*    */ package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage;
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
/*    */ public final class NonceStorage
/*    */   implements StorableObject
/*    */ {
/*    */   private final byte[] nonce;
/*    */   
/*    */   public NonceStorage(byte[] nonce) {
/* 27 */     this.nonce = nonce;
/*    */   }
/*    */   
/*    */   public byte[] nonce() {
/* 31 */     return this.nonce;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\storage\NonceStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */