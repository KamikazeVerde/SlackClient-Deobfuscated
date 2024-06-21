/*    */ package com.viaversion.viaversion.api.protocol.version;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
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
/*    */ public class VersionRange
/*    */ {
/*    */   private final String baseVersion;
/*    */   private final int rangeFrom;
/*    */   private final int rangeTo;
/*    */   
/*    */   public VersionRange(String baseVersion, int rangeFrom, int rangeTo) {
/* 40 */     Preconditions.checkNotNull(baseVersion);
/* 41 */     Preconditions.checkArgument((rangeFrom >= 0));
/* 42 */     Preconditions.checkArgument((rangeTo > rangeFrom));
/* 43 */     this.baseVersion = baseVersion;
/* 44 */     this.rangeFrom = rangeFrom;
/* 45 */     this.rangeTo = rangeTo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String baseVersion() {
/* 54 */     return this.baseVersion;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int rangeFrom() {
/* 63 */     return this.rangeFrom;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int rangeTo() {
/* 72 */     return this.rangeTo;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public String getBaseVersion() {
/* 77 */     return this.baseVersion;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getRangeFrom() {
/* 82 */     return this.rangeFrom;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getRangeTo() {
/* 87 */     return this.rangeTo;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\version\VersionRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */