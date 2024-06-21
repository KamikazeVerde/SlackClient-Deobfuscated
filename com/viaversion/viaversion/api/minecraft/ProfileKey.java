/*    */ package com.viaversion.viaversion.api.minecraft;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ProfileKey
/*    */ {
/*    */   private final long expiresAt;
/*    */   private final byte[] publicKey;
/*    */   private final byte[] keySignature;
/*    */   
/*    */   public ProfileKey(long expiresAt, byte[] publicKey, byte[] keySignature) {
/* 31 */     this.expiresAt = expiresAt;
/* 32 */     this.publicKey = publicKey;
/* 33 */     this.keySignature = keySignature;
/*    */   }
/*    */   
/*    */   public long expiresAt() {
/* 37 */     return this.expiresAt;
/*    */   }
/*    */   
/*    */   public byte[] publicKey() {
/* 41 */     return this.publicKey;
/*    */   }
/*    */   
/*    */   public byte[] keySignature() {
/* 45 */     return this.keySignature;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\ProfileKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */