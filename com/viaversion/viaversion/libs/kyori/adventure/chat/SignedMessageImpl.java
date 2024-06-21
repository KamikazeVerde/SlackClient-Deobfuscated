/*    */ package com.viaversion.viaversion.libs.kyori.adventure.chat;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import java.security.SecureRandom;
/*    */ import java.time.Instant;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ 
/*    */ final class SignedMessageImpl
/*    */   implements SignedMessage
/*    */ {
/* 35 */   static final SecureRandom RANDOM = new SecureRandom();
/*    */   
/*    */   private final Instant instant;
/*    */   private final long salt;
/*    */   private final String message;
/*    */   private final Component unsignedContent;
/*    */   
/*    */   SignedMessageImpl(String message, Component unsignedContent) {
/* 43 */     this.instant = Instant.now();
/* 44 */     this.salt = RANDOM.nextLong();
/* 45 */     this.message = message;
/* 46 */     this.unsignedContent = unsignedContent;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Instant timestamp() {
/* 51 */     return this.instant;
/*    */   }
/*    */ 
/*    */   
/*    */   public long salt() {
/* 56 */     return this.salt;
/*    */   }
/*    */ 
/*    */   
/*    */   public SignedMessage.Signature signature() {
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Component unsignedContent() {
/* 66 */     return this.unsignedContent;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String message() {
/* 71 */     return this.message;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Identity identity() {
/* 76 */     return Identity.nil();
/*    */   }
/*    */   
/*    */   static final class SignatureImpl
/*    */     implements SignedMessage.Signature {
/*    */     final byte[] signature;
/*    */     
/*    */     SignatureImpl(byte[] signature) {
/* 84 */       this.signature = signature;
/*    */     }
/*    */ 
/*    */     
/*    */     public byte[] bytes() {
/* 89 */       return this.signature;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\chat\SignedMessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */