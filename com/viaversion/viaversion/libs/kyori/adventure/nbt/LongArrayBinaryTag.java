/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.PrimitiveIterator;
/*    */ import java.util.Spliterator;
/*    */ import java.util.function.LongConsumer;
/*    */ import java.util.stream.LongStream;
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
/*    */ public interface LongArrayBinaryTag
/*    */   extends ArrayBinaryTag, Iterable<Long>
/*    */ {
/*    */   @NotNull
/*    */   static LongArrayBinaryTag of(long... value) {
/* 47 */     return new LongArrayBinaryTagImpl(value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   default BinaryTagType<LongArrayBinaryTag> type() {
/* 52 */     return BinaryTagTypes.LONG_ARRAY;
/*    */   }
/*    */   
/*    */   long[] value();
/*    */   
/*    */   int size();
/*    */   
/*    */   long get(int paramInt);
/*    */   
/*    */   PrimitiveIterator.OfLong iterator();
/*    */   
/*    */   Spliterator.OfLong spliterator();
/*    */   
/*    */   @NotNull
/*    */   LongStream stream();
/*    */   
/*    */   void forEachLong(@NotNull LongConsumer paramLongConsumer);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\LongArrayBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */