/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*    */ 
/*    */ import org.jetbrains.annotations.Debug.Renderer;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ @Renderer(text = "asHexString()")
/*    */ final class TextColorImpl
/*    */   implements TextColor
/*    */ {
/*    */   private final int value;
/*    */   
/*    */   TextColorImpl(int value) {
/* 34 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int value() {
/* 39 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 44 */     if (this == other) return true; 
/* 45 */     if (!(other instanceof TextColorImpl)) return false; 
/* 46 */     TextColorImpl that = (TextColorImpl)other;
/* 47 */     return (this.value == that.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return asHexString();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\TextColorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */