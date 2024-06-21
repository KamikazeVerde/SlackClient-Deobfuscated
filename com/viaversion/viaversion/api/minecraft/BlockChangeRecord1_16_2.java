/*    */ package com.viaversion.viaversion.api.minecraft;
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
/*    */ public class BlockChangeRecord1_16_2
/*    */   implements BlockChangeRecord
/*    */ {
/*    */   private final byte sectionX;
/*    */   private final byte sectionY;
/*    */   private final byte sectionZ;
/*    */   private int blockId;
/*    */   
/*    */   public BlockChangeRecord1_16_2(byte sectionX, byte sectionY, byte sectionZ, int blockId) {
/* 34 */     this.sectionX = sectionX;
/* 35 */     this.sectionY = sectionY;
/* 36 */     this.sectionZ = sectionZ;
/* 37 */     this.blockId = blockId;
/*    */   }
/*    */   
/*    */   public BlockChangeRecord1_16_2(int sectionX, int sectionY, int sectionZ, int blockId) {
/* 41 */     this((byte)sectionX, (byte)sectionY, (byte)sectionZ, blockId);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getSectionX() {
/* 46 */     return this.sectionX;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getSectionY() {
/* 51 */     return this.sectionY;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getSectionZ() {
/* 56 */     return this.sectionZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getY(int chunkSectionY) {
/* 61 */     Preconditions.checkArgument((chunkSectionY >= 0), "Invalid chunkSectionY: " + chunkSectionY);
/* 62 */     return (short)((chunkSectionY << 4) + this.sectionY);
/*    */   }
/*    */   
/*    */   public int getBlockId() {
/* 66 */     return this.blockId;
/*    */   }
/*    */   
/*    */   public void setBlockId(int blockId) {
/* 70 */     this.blockId = blockId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\BlockChangeRecord1_16_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */