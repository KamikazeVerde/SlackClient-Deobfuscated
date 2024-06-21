/*    */ package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
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
/*    */ public final class ChatSessionStorage
/*    */   implements StorableObject
/*    */ {
/* 25 */   private final UUID uuid = UUID.randomUUID();
/*    */   
/*    */   public UUID uuid() {
/* 28 */     return this.uuid;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_1to1_19_3\storage\ChatSessionStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */