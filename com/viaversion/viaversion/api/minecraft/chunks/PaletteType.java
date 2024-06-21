/*    */ package com.viaversion.viaversion.api.minecraft.chunks;
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
/*    */ public enum PaletteType
/*    */ {
/* 26 */   BLOCKS(4096, 8),
/* 27 */   BIOMES(64, 3);
/*    */   
/*    */   private final int size;
/*    */   private final int highestBitsPerValue;
/*    */   
/*    */   PaletteType(int size, int highestBitsPerValue) {
/* 33 */     this.size = size;
/* 34 */     this.highestBitsPerValue = highestBitsPerValue;
/*    */   }
/*    */   
/*    */   public int size() {
/* 38 */     return this.size;
/*    */   }
/*    */   
/*    */   public int highestBitsPerValue() {
/* 42 */     return this.highestBitsPerValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\PaletteType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */