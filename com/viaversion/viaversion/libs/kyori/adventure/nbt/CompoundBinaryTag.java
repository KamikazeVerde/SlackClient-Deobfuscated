/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public interface CompoundBinaryTag
/*     */   extends BinaryTag, CompoundTagSetter<CompoundBinaryTag>, Iterable<Map.Entry<String, ? extends BinaryTag>>
/*     */ {
/*     */   @NotNull
/*     */   static CompoundBinaryTag empty() {
/*  45 */     return CompoundBinaryTagImpl.EMPTY;
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
/*     */   static CompoundBinaryTag from(@NotNull Map<String, ? extends BinaryTag> tags) {
/*  58 */     if (tags.isEmpty()) return empty(); 
/*  59 */     return new CompoundBinaryTagImpl(new HashMap<>(tags));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder builder() {
/*  69 */     return new CompoundTagBuilder();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default BinaryTagType<CompoundBinaryTag> type() {
/*  74 */     return BinaryTagTypes.COMPOUND;
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
/*     */   Set<String> keySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   BinaryTag get(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean getBoolean(@NotNull String key) {
/* 105 */     return getBoolean(key, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean getBoolean(@NotNull String key, boolean defaultValue) {
/* 121 */     return (getByte(key) != 0 || defaultValue);
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
/*     */   default byte getByte(@NotNull String key) {
/* 133 */     return getByte(key, (byte)0);
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
/*     */   
/*     */   byte getByte(@NotNull String paramString, byte paramByte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default short getShort(@NotNull String key) {
/* 156 */     return getShort(key, (short)0);
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
/*     */   
/*     */   short getShort(@NotNull String paramString, short paramShort);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int getInt(@NotNull String key) {
/* 179 */     return getInt(key, 0);
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
/*     */   
/*     */   int getInt(@NotNull String paramString, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long getLong(@NotNull String key) {
/* 202 */     return getLong(key, 0L);
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
/*     */   
/*     */   long getLong(@NotNull String paramString, long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default float getFloat(@NotNull String key) {
/* 225 */     return getFloat(key, 0.0F);
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
/*     */   
/*     */   float getFloat(@NotNull String paramString, float paramFloat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double getDouble(@NotNull String key) {
/* 248 */     return getDouble(key, 0.0D);
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
/*     */   double getDouble(@NotNull String paramString, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getByteArray(@NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getByteArray(@NotNull String paramString, byte[] paramArrayOfbyte);
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
/*     */   default String getString(@NotNull String key) {
/* 291 */     return getString(key, "");
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
/*     */   String getString(@NotNull String paramString1, @NotNull String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default ListBinaryTag getList(@NotNull String key) {
/* 314 */     return getList(key, ListBinaryTag.empty());
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
/*     */   
/*     */   @NotNull
/*     */   ListBinaryTag getList(@NotNull String paramString, @NotNull ListBinaryTag paramListBinaryTag);
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
/*     */   default ListBinaryTag getList(@NotNull String key, @NotNull BinaryTagType<? extends BinaryTag> expectedType) {
/* 339 */     return getList(key, expectedType, ListBinaryTag.empty());
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
/*     */   
/*     */   @NotNull
/*     */   ListBinaryTag getList(@NotNull String paramString, @NotNull BinaryTagType<? extends BinaryTag> paramBinaryTagType, @NotNull ListBinaryTag paramListBinaryTag);
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
/*     */   default CompoundBinaryTag getCompound(@NotNull String key) {
/* 364 */     return getCompound(key, empty());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   CompoundBinaryTag getCompound(@NotNull String paramString, @NotNull CompoundBinaryTag paramCompoundBinaryTag);
/*     */   
/*     */   int[] getIntArray(@NotNull String paramString);
/*     */   
/*     */   int[] getIntArray(@NotNull String paramString, int[] paramArrayOfint);
/*     */   
/*     */   long[] getLongArray(@NotNull String paramString);
/*     */   
/*     */   long[] getLongArray(@NotNull String paramString, long[] paramArrayOflong);
/*     */   
/*     */   public static interface Builder extends CompoundTagSetter<Builder> {
/*     */     @NotNull
/*     */     CompoundBinaryTag build();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\CompoundBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */