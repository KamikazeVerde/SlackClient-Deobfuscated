/*    */ package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
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
/*    */ public final class BackSignEditStorage
/*    */   implements StorableObject
/*    */ {
/*    */   private final Position position;
/*    */   
/*    */   public BackSignEditStorage(Position position) {
/* 28 */     this.position = position;
/*    */   }
/*    */   
/*    */   public Position position() {
/* 32 */     return this.position;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_4to1_20\storage\BackSignEditStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */