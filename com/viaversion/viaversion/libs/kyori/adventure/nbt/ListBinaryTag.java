/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
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
/*     */ public interface ListBinaryTag
/*     */   extends ListTagSetter<ListBinaryTag, BinaryTag>, BinaryTag, Iterable<BinaryTag>
/*     */ {
/*     */   @NotNull
/*     */   static ListBinaryTag empty() {
/*  46 */     return ListBinaryTagImpl.EMPTY;
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
/*     */   static ListBinaryTag from(@NotNull Iterable<? extends BinaryTag> tags) {
/*  60 */     return builder().add(tags).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder<BinaryTag> builder() {
/*  70 */     return new ListTagBuilder<>();
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
/*     */   static <T extends BinaryTag> Builder<T> builder(@NotNull BinaryTagType<T> type) {
/*  83 */     if (type == BinaryTagTypes.END) throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END); 
/*  84 */     return new ListTagBuilder<>(type);
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
/*     */   @NotNull
/*     */   static ListBinaryTag of(@NotNull BinaryTagType<? extends BinaryTag> type, @NotNull List<BinaryTag> tags) {
/*  99 */     if (tags.isEmpty()) return empty(); 
/* 100 */     if (type == BinaryTagTypes.END) throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END); 
/* 101 */     return new ListBinaryTagImpl(type, tags);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default BinaryTagType<ListBinaryTag> type() {
/* 106 */     return BinaryTagTypes.LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   default BinaryTagType<? extends BinaryTag> listType() {
/* 118 */     return elementType();
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
/*     */   BinaryTagType<? extends BinaryTag> elementType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   BinaryTag get(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ListBinaryTag set(int paramInt, @NotNull BinaryTag paramBinaryTag, @Nullable Consumer<? super BinaryTag> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ListBinaryTag remove(int paramInt, @Nullable Consumer<? super BinaryTag> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default byte getByte(int index) {
/* 176 */     return getByte(index, (byte)0);
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
/*     */   default byte getByte(int index, byte defaultValue) {
/* 188 */     BinaryTag tag = get(index);
/* 189 */     if (tag.type().numeric()) {
/* 190 */       return ((NumberBinaryTag)tag).byteValue();
/*     */     }
/* 192 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default short getShort(int index) {
/* 203 */     return getShort(index, (short)0);
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
/*     */   default short getShort(int index, short defaultValue) {
/* 215 */     BinaryTag tag = get(index);
/* 216 */     if (tag.type().numeric()) {
/* 217 */       return ((NumberBinaryTag)tag).shortValue();
/*     */     }
/* 219 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int getInt(int index) {
/* 230 */     return getInt(index, 0);
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
/*     */   default int getInt(int index, int defaultValue) {
/* 242 */     BinaryTag tag = get(index);
/* 243 */     if (tag.type().numeric()) {
/* 244 */       return ((NumberBinaryTag)tag).intValue();
/*     */     }
/* 246 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long getLong(int index) {
/* 257 */     return getLong(index, 0L);
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
/*     */   default long getLong(int index, long defaultValue) {
/* 269 */     BinaryTag tag = get(index);
/* 270 */     if (tag.type().numeric()) {
/* 271 */       return ((NumberBinaryTag)tag).longValue();
/*     */     }
/* 273 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default float getFloat(int index) {
/* 284 */     return getFloat(index, 0.0F);
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
/*     */   default float getFloat(int index, float defaultValue) {
/* 296 */     BinaryTag tag = get(index);
/* 297 */     if (tag.type().numeric()) {
/* 298 */       return ((NumberBinaryTag)tag).floatValue();
/*     */     }
/* 300 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double getDouble(int index) {
/* 311 */     return getDouble(index, 0.0D);
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
/*     */   default double getDouble(int index, double defaultValue) {
/* 323 */     BinaryTag tag = get(index);
/* 324 */     if (tag.type().numeric()) {
/* 325 */       return ((NumberBinaryTag)tag).doubleValue();
/*     */     }
/* 327 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default byte[] getByteArray(int index) {
/* 338 */     BinaryTag tag = get(index);
/* 339 */     if (tag.type() == BinaryTagTypes.BYTE_ARRAY) {
/* 340 */       return ((ByteArrayBinaryTag)tag).value();
/*     */     }
/* 342 */     return new byte[0];
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
/*     */   default byte[] getByteArray(int index, byte[] defaultValue) {
/* 354 */     BinaryTag tag = get(index);
/* 355 */     if (tag.type() == BinaryTagTypes.BYTE_ARRAY) {
/* 356 */       return ((ByteArrayBinaryTag)tag).value();
/*     */     }
/* 358 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default String getString(int index) {
/* 369 */     return getString(index, "");
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
/*     */   default String getString(int index, @NotNull String defaultValue) {
/* 381 */     BinaryTag tag = get(index);
/* 382 */     if (tag.type() == BinaryTagTypes.STRING) {
/* 383 */       return ((StringBinaryTag)tag).value();
/*     */     }
/* 385 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default ListBinaryTag getList(int index) {
/* 396 */     return getList(index, null, empty());
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
/*     */   default ListBinaryTag getList(int index, @Nullable BinaryTagType<?> elementType) {
/* 408 */     return getList(index, elementType, empty());
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
/*     */   default ListBinaryTag getList(int index, @NotNull ListBinaryTag defaultValue) {
/* 420 */     return getList(index, null, defaultValue);
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
/*     */   @NotNull
/*     */   default ListBinaryTag getList(int index, @Nullable BinaryTagType<?> elementType, @NotNull ListBinaryTag defaultValue) {
/* 435 */     BinaryTag tag = get(index);
/* 436 */     if (tag.type() == BinaryTagTypes.LIST) {
/* 437 */       ListBinaryTag list = (ListBinaryTag)tag;
/* 438 */       if (elementType == null || list.elementType() == elementType) {
/* 439 */         return list;
/*     */       }
/*     */     } 
/* 442 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default CompoundBinaryTag getCompound(int index) {
/* 453 */     return getCompound(index, CompoundBinaryTag.empty());
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
/*     */   default CompoundBinaryTag getCompound(int index, @NotNull CompoundBinaryTag defaultValue) {
/* 465 */     BinaryTag tag = get(index);
/* 466 */     if (tag.type() == BinaryTagTypes.COMPOUND) {
/* 467 */       return (CompoundBinaryTag)tag;
/*     */     }
/* 469 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int[] getIntArray(int index) {
/* 480 */     BinaryTag tag = get(index);
/* 481 */     if (tag.type() == BinaryTagTypes.INT_ARRAY) {
/* 482 */       return ((IntArrayBinaryTag)tag).value();
/*     */     }
/* 484 */     return new int[0];
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
/*     */   default int[] getIntArray(int index, int[] defaultValue) {
/* 496 */     BinaryTag tag = get(index);
/* 497 */     if (tag.type() == BinaryTagTypes.INT_ARRAY) {
/* 498 */       return ((IntArrayBinaryTag)tag).value();
/*     */     }
/* 500 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long[] getLongArray(int index) {
/* 511 */     BinaryTag tag = get(index);
/* 512 */     if (tag.type() == BinaryTagTypes.LONG_ARRAY) {
/* 513 */       return ((LongArrayBinaryTag)tag).value();
/*     */     }
/* 515 */     return new long[0];
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
/*     */   default long[] getLongArray(int index, long[] defaultValue) {
/* 527 */     BinaryTag tag = get(index);
/* 528 */     if (tag.type() == BinaryTagTypes.LONG_ARRAY) {
/* 529 */       return ((LongArrayBinaryTag)tag).value();
/*     */     }
/* 531 */     return defaultValue;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   Stream<BinaryTag> stream();
/*     */   
/*     */   public static interface Builder<T extends BinaryTag> extends ListTagSetter<Builder<T>, T> {
/*     */     @NotNull
/*     */     ListBinaryTag build();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ListBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */