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
/*    */ public interface DataPalette
/*    */ {
/*    */   int index(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   int idAt(int paramInt);
/*    */   
/*    */   default int idAt(int sectionX, int sectionY, int sectionZ) {
/* 54 */     return idAt(index(sectionX, sectionY, sectionZ));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void setIdAt(int paramInt1, int paramInt2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void setIdAt(int sectionX, int sectionY, int sectionZ, int id) {
/* 76 */     setIdAt(index(sectionX, sectionY, sectionZ), id);
/*    */   }
/*    */   
/*    */   int idByIndex(int paramInt);
/*    */   
/*    */   void setIdByIndex(int paramInt1, int paramInt2);
/*    */   
/*    */   int paletteIndexAt(int paramInt);
/*    */   
/*    */   void setPaletteIndexAt(int paramInt1, int paramInt2);
/*    */   
/*    */   void addId(int paramInt);
/*    */   
/*    */   void replaceId(int paramInt1, int paramInt2);
/*    */   
/*    */   int size();
/*    */   
/*    */   void clear();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\DataPalette.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */