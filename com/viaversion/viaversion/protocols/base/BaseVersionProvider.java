/*    */ package com.viaversion.viaversion.protocols.base;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.version.VersionProvider;
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
/*    */ public class BaseVersionProvider
/*    */   implements VersionProvider
/*    */ {
/*    */   public int getClosestServerProtocol(UserConnection connection) throws Exception {
/* 28 */     return Via.getAPI().getServerVersion().lowestSupportedVersion();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\BaseVersionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */