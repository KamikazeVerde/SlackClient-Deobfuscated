/*    */ package com.viaversion.viaversion.protocol;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
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
/*    */ public class ServerProtocolVersionRange
/*    */   implements ServerProtocolVersion
/*    */ {
/*    */   private final int lowestSupportedVersion;
/*    */   private final int highestSupportedVersion;
/*    */   private final IntSortedSet supportedVersions;
/*    */   
/*    */   public ServerProtocolVersionRange(int lowestSupportedVersion, int highestSupportedVersion, IntSortedSet supportedVersions) {
/* 29 */     this.lowestSupportedVersion = lowestSupportedVersion;
/* 30 */     this.highestSupportedVersion = highestSupportedVersion;
/* 31 */     this.supportedVersions = supportedVersions;
/*    */   }
/*    */ 
/*    */   
/*    */   public int lowestSupportedVersion() {
/* 36 */     return this.lowestSupportedVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public int highestSupportedVersion() {
/* 41 */     return this.highestSupportedVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntSortedSet supportedVersions() {
/* 46 */     return this.supportedVersions;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\ServerProtocolVersionRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */