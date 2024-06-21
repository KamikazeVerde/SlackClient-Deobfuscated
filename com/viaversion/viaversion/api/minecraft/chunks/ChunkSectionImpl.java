/*    */ package com.viaversion.viaversion.api.minecraft.chunks;
/*    */ 
/*    */ import java.util.EnumMap;
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
/*    */ public class ChunkSectionImpl
/*    */   implements ChunkSection
/*    */ {
/* 30 */   private final EnumMap<PaletteType, DataPalette> palettes = new EnumMap<>(PaletteType.class);
/*    */   
/*    */   private ChunkSectionLight light;
/*    */   private int nonAirBlocksCount;
/*    */   
/*    */   public ChunkSectionImpl() {}
/*    */   
/*    */   public ChunkSectionImpl(boolean holdsLight) {
/* 38 */     addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096));
/* 39 */     if (holdsLight) {
/* 40 */       this.light = new ChunkSectionLightImpl();
/*    */     }
/*    */   }
/*    */   
/*    */   public ChunkSectionImpl(boolean holdsLight, int expectedPaletteLength) {
/* 45 */     addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096, expectedPaletteLength));
/* 46 */     if (holdsLight) {
/* 47 */       this.light = new ChunkSectionLightImpl();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNonAirBlocksCount() {
/* 53 */     return this.nonAirBlocksCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNonAirBlocksCount(int nonAirBlocksCount) {
/* 58 */     this.nonAirBlocksCount = nonAirBlocksCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkSectionLight getLight() {
/* 63 */     return this.light;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLight(ChunkSectionLight light) {
/* 68 */     this.light = light;
/*    */   }
/*    */ 
/*    */   
/*    */   public DataPalette palette(PaletteType type) {
/* 73 */     return this.palettes.get(type);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addPalette(PaletteType type, DataPalette palette) {
/* 78 */     this.palettes.put(type, palette);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removePalette(PaletteType type) {
/* 83 */     this.palettes.remove(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\ChunkSectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */