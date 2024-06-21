/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
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
/*    */ public class EntityIdProvider
/*    */   implements Provider
/*    */ {
/*    */   public int getEntityId(UserConnection user) throws Exception {
/* 27 */     return user.getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\providers\EntityIdProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */