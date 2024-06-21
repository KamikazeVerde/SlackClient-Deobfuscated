/*    */ package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.storage;
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
/*    */   private PlayerMessageSignature lastSignature;
/*    */   private int size;
/*    */   private int unacknowledged;
/*    */   
/*    */   public boolean add(PlayerMessageSignature signature) {
/* 31 */     if (signature.equals(this.lastSignature)) {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     this.lastSignature = signature;
/* 36 */     PlayerMessageSignature toPush = signature;
/* 37 */     for (int i = 0; i < this.size; i++) {
/* 38 */       PlayerMessageSignature entry = this.signatures[i];
/* 39 */       this.signatures[i] = toPush;
/* 40 */       toPush = entry;
/* 41 */       if (entry.uuid().equals(signature.uuid())) {
/* 42 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 46 */     if (this.size < this.signatures.length) {
/* 47 */       this.signatures[this.size++] = toPush;
/*    */     }
/* 49 */     return true;
/*    */   }
/*    */   
/*    */   public PlayerMessageSignature[] lastSignatures() {
/* 53 */     return Arrays.<PlayerMessageSignature>copyOf(this.signatures, this.size);
/*    */   }
/*    */   
/*    */   public int tickUnacknowledged() {
/* 57 */     return this.unacknowledged++;
/*    */   }
/*    */   
/*    */   public void resetUnacknowledgedCount() {
/* 61 */     this.unacknowledged = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_3to1_19_1\storage\ReceivedMessagesStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */