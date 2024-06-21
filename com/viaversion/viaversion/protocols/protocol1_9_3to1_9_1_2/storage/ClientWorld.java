/*    */ package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Environment;
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
/*    */ public class ClientWorld
/*    */   extends StoredObject
/*    */ {
/*    */   private Environment environment;
/*    */   
/*    */   public ClientWorld(UserConnection connection) {
/* 32 */     super(connection);
/*    */   }
/*    */   
/*    */   public Environment getEnvironment() {
/* 36 */     return this.environment;
/*    */   }
/*    */   
/*    */   public void setEnvironment(int environmentId) {
/* 40 */     this.environment = Environment.getEnvironmentById(environmentId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_3to1_9_1_2\storage\ClientWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */