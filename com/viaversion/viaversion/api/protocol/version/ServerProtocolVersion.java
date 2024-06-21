/*    */ package com.viaversion.viaversion.api.protocol.version;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
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
/*    */ 
/*    */ public interface ServerProtocolVersion
/*    */ {
/*    */   int lowestSupportedVersion();
/*    */   
/*    */   int highestSupportedVersion();
/*    */   
/*    */   IntSortedSet supportedVersions();
/*    */   
/*    */   default boolean isKnown() {
/* 60 */     return (lowestSupportedVersion() != -1 && highestSupportedVersion() != -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\version\ServerProtocolVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */