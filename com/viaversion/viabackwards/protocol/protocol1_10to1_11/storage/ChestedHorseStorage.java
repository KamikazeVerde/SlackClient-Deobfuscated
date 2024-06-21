/*    */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage;
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
/*    */ public class ChestedHorseStorage
/*    */ {
/*    */   private boolean chested;
/*    */   private int liamaStrength;
/* 24 */   private int liamaCarpetColor = -1;
/*    */   private int liamaVariant;
/*    */   
/*    */   public boolean isChested() {
/* 28 */     return this.chested;
/*    */   }
/*    */   
/*    */   public void setChested(boolean chested) {
/* 32 */     this.chested = chested;
/*    */   }
/*    */   
/*    */   public int getLiamaStrength() {
/* 36 */     return this.liamaStrength;
/*    */   }
/*    */   
/*    */   public void setLiamaStrength(int liamaStrength) {
/* 40 */     this.liamaStrength = liamaStrength;
/*    */   }
/*    */   
/*    */   public int getLiamaCarpetColor() {
/* 44 */     return this.liamaCarpetColor;
/*    */   }
/*    */   
/*    */   public void setLiamaCarpetColor(int liamaCarpetColor) {
/* 48 */     this.liamaCarpetColor = liamaCarpetColor;
/*    */   }
/*    */   
/*    */   public int getLiamaVariant() {
/* 52 */     return this.liamaVariant;
/*    */   }
/*    */   
/*    */   public void setLiamaVariant(int liamaVariant) {
/* 56 */     this.liamaVariant = liamaVariant;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 61 */     return "ChestedHorseStorage{chested=" + this.chested + ", liamaStrength=" + this.liamaStrength + ", liamaCarpetColor=" + this.liamaCarpetColor + ", liamaVariant=" + this.liamaVariant + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\storage\ChestedHorseStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */