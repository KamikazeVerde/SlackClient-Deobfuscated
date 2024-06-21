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
/*    */ public class BlockChangeRecord1_8
/*    */   implements BlockChangeRecord
/*    */ {
/*    */   private final byte sectionX;
/*    */   private final short y;
/*    */   private final byte sectionZ;
/*    */   private int blockId;
/*    */   
/*    */   public BlockChangeRecord1_8(byte sectionX, short y, byte sectionZ, int blockId) {
/* 32 */     this.sectionX = sectionX;
/* 33 */     this.y = y;
/* 34 */     this.sectionZ = sectionZ;
/* 35 */     this.blockId = blockId;
/*    */   }
/*    */   
/*    */   public BlockChangeRecord1_8(int sectionX, int y, int sectionZ, int blockId) {
/* 39 */     this((byte)sectionX, (short)y, (byte)sectionZ, blockId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getSectionX() {
/* 46 */     return this.sectionX;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getSectionY() {
/* 51 */     return (byte)(this.y & 0xF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getY(int chunkSectionY) {
/* 59 */     return this.y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getSectionZ() {
/* 66 */     return this.sectionZ;
/*    */   }
/*    */   
/*    */   public int getBlockId() {
/* 70 */     return this.blockId;
/*    */   }
/*    */   
/*    */   public void setBlockId(int blockId) {
/* 74 */     this.blockId = blockId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\BlockChangeRecord1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */