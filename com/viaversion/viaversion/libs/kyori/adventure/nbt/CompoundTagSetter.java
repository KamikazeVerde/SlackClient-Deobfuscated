/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface CompoundTagSetter<R>
/*     */ {
/*     */   @NotNull
/*     */   R put(@NotNull String paramString, @NotNull BinaryTag paramBinaryTag);
/*     */   
/*     */   @NotNull
/*     */   R put(@NotNull CompoundBinaryTag paramCompoundBinaryTag);
/*     */   
/*     */   @NotNull
/*     */   R put(@NotNull Map<String, ? extends BinaryTag> paramMap);
/*     */   
/*     */   @NotNull
/*     */   default R remove(@NotNull String key) {
/*  74 */     return remove(key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   R remove(@NotNull String paramString, @Nullable Consumer<? super BinaryTag> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putBoolean(@NotNull String key, boolean value) {
/*  98 */     return put(key, value ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putByte(@NotNull String key, byte value) {
/* 110 */     return put(key, ByteBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putShort(@NotNull String key, short value) {
/* 122 */     return put(key, ShortBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putInt(@NotNull String key, int value) {
/* 134 */     return put(key, IntBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putLong(@NotNull String key, long value) {
/* 146 */     return put(key, LongBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putFloat(@NotNull String key, float value) {
/* 158 */     return put(key, FloatBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putDouble(@NotNull String key, double value) {
/* 170 */     return put(key, DoubleBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putByteArray(@NotNull String key, byte[] value) {
/* 182 */     return put(key, ByteArrayBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putString(@NotNull String key, @NotNull String value) {
/* 194 */     return put(key, StringBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putIntArray(@NotNull String key, int[] value) {
/* 206 */     return put(key, IntArrayBinaryTag.of(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default R putLongArray(@NotNull String key, long[] value) {
/* 218 */     return put(key, LongArrayBinaryTag.of(value));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\CompoundTagSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */