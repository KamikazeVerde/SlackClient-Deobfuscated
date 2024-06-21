/*    */ package com.viaversion.viaversion.protocol;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
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
/*    */ public class BlockedProtocolVersionsImpl
/*    */   implements BlockedProtocolVersions
/*    */ {
/*    */   private final IntSet singleBlockedVersions;
/*    */   private final int blocksBelow;
/*    */   private final int blocksAbove;
/*    */   
/*    */   public BlockedProtocolVersionsImpl(IntSet singleBlockedVersions, int blocksBelow, int blocksAbove) {
/* 29 */     this.singleBlockedVersions = singleBlockedVersions;
/* 30 */     this.blocksBelow = blocksBelow;
/* 31 */     this.blocksAbove = blocksAbove;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(int protocolVersion) {
/* 36 */     return ((this.blocksBelow != -1 && protocolVersion < this.blocksBelow) || (this.blocksAbove != -1 && protocolVersion > this.blocksAbove) || this.singleBlockedVersions
/*    */       
/* 38 */       .contains(protocolVersion));
/*    */   }
/*    */ 
/*    */   
/*    */   public int blocksBelow() {
/* 43 */     return this.blocksBelow;
/*    */   }
/*    */ 
/*    */   
/*    */   public int blocksAbove() {
/* 48 */     return this.blocksAbove;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntSet singleBlockedVersions() {
/* 53 */     return this.singleBlockedVersions;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\BlockedProtocolVersionsImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */