/*    */ package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
/*    */ import java.util.Arrays;
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
/*    */ public final class ReceivedMessagesStorage
/*    */   implements StorableObject
/*    */ {
/* 25 */   private final PlayerMessageSignature[] signatures = new PlayerMessageSignature[5];
/*    */   private int size;
/*    */   private int unacknowledged;
/*    */   
/*    */   public void add(PlayerMessageSignature signature) {
/* 30 */     PlayerMessageSignature toPush = signature;
/* 31 */     for (int i = 0; i < this.size; i++) {
/* 32 */       PlayerMessageSignature entry = this.signatures[i];
/* 33 */       this.signatures[i] = toPush;
/* 34 */       toPush = entry;
/* 35 */       if (entry.uuid().equals(signature.uuid())) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 40 */     if (this.size < this.signatures.length) {
/* 41 */       this.signatures[this.size++] = toPush;
/*    */     }
/*    */   }
/*    */   
/*    */   public PlayerMessageSignature[] lastSignatures() {
/* 46 */     return Arrays.<PlayerMessageSignature>copyOf(this.signatures, this.size);
/*    */   }
/*    */   
/*    */   public int tickUnacknowledged() {
/* 50 */     return this.unacknowledged++;
/*    */   }
/*    */   
/*    */   public void resetUnacknowledgedCount() {
/* 54 */     this.unacknowledged = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19to1_19_1\storage\ReceivedMessagesStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */