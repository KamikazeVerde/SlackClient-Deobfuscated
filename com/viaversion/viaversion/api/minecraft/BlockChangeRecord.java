/*    */ package com.viaversion.viaversion.api.minecraft;
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
/*    */ 
/*    */ 
/*    */ public interface BlockChangeRecord
/*    */ {
/*    */   byte getSectionX();
/*    */   
/*    */   byte getSectionY();
/*    */   
/*    */   byte getSectionZ();
/*    */   
/*    */   short getY(int paramInt);
/*    */   
/*    */   default short getY() {
/* 62 */     return getY(-1);
/*    */   }
/*    */   
/*    */   int getBlockId();
/*    */   
/*    */   void setBlockId(int paramInt);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\BlockChangeRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */