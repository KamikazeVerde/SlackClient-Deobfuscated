/*    */ package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
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
/*    */ public class DifficultyStorage
/*    */   extends StoredObject
/*    */ {
/*    */   private byte difficulty;
/*    */   
/*    */   public DifficultyStorage(UserConnection user) {
/* 27 */     super(user);
/*    */   }
/*    */   
/*    */   public byte getDifficulty() {
/* 31 */     return this.difficulty;
/*    */   }
/*    */   
/*    */   public void setDifficulty(byte difficulty) {
/* 35 */     this.difficulty = difficulty;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_2to1_14\storage\DifficultyStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */