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
/*    */ 
/*    */ public interface ByteArrayBinaryTag
/*    */   extends ArrayBinaryTag, Iterable<Byte>
/*    */ {
/*    */   @NotNull
/*    */   static ByteArrayBinaryTag of(byte... value) {
/* 42 */     return new ByteArrayBinaryTagImpl(value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   default BinaryTagType<ByteArrayBinaryTag> type() {
/* 47 */     return BinaryTagTypes.BYTE_ARRAY;
/*    */   }
/*    */   
/*    */   byte[] value();
/*    */   
/*    */   int size();
/*    */   
/*    */   byte get(int paramInt);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ByteArrayBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */