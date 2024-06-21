/*    */ package com.viaversion.viaversion.api.minecraft;
/*    */ 
/*    */ import java.util.UUID;
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
/*    */ public final class PlayerMessageSignature
/*    */ {
/*    */   private final UUID uuid;
/*    */   private final byte[] signatureBytes;
/*    */   
/*    */   public PlayerMessageSignature(UUID uuid, byte[] signatureBytes) {
/* 32 */     this.uuid = uuid;
/* 33 */     this.signatureBytes = signatureBytes;
/*    */   }
/*    */   
/*    */   public UUID uuid() {
/* 37 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public byte[] signatureBytes() {
/* 41 */     return this.signatureBytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\PlayerMessageSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */