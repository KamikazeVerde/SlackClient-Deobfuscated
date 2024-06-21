/*    */ package com.viaversion.viaversion.protocol;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets;
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
/*    */ public class ServerProtocolVersionSingleton
/*    */   implements ServerProtocolVersion
/*    */ {
/*    */   private final int protocolVersion;
/*    */   
/*    */   public ServerProtocolVersionSingleton(int protocolVersion) {
/* 28 */     this.protocolVersion = protocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public int lowestSupportedVersion() {
/* 33 */     return this.protocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public int highestSupportedVersion() {
/* 38 */     return this.protocolVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntSortedSet supportedVersions() {
/* 43 */     return IntSortedSets.singleton(this.protocolVersion);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\ServerProtocolVersionSingleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */