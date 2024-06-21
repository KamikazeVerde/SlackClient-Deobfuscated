/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ public interface ByteBinaryTag
/*    */   extends NumberBinaryTag
/*    */ {
/* 39 */   public static final ByteBinaryTag ZERO = new ByteBinaryTagImpl((byte)0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public static final ByteBinaryTag ONE = new ByteBinaryTagImpl((byte)1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ByteBinaryTag of(byte value) {
/* 56 */     if (value == 0)
/* 57 */       return ZERO; 
/* 58 */     if (value == 1) {
/* 59 */       return ONE;
/*    */     }
/* 61 */     return new ByteBinaryTagImpl(value);
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   default BinaryTagType<ByteBinaryTag> type() {
/* 67 */     return BinaryTagTypes.BYTE;
/*    */   }
/*    */   
/*    */   byte value();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ByteBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */