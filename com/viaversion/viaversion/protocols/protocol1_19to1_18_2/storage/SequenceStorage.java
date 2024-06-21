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
/*    */ public final class SequenceStorage
/*    */   implements StorableObject
/*    */ {
/* 24 */   private final Object lock = new Object();
/* 25 */   private int sequenceId = -1;
/*    */   
/*    */   public int sequenceId() {
/* 28 */     synchronized (this.lock) {
/* 29 */       return this.sequenceId;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int setSequenceId(int sequenceId) {
/* 34 */     synchronized (this.lock) {
/* 35 */       int previousSequence = this.sequenceId;
/* 36 */       this.sequenceId = sequenceId;
/* 37 */       return previousSequence;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\storage\SequenceStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */