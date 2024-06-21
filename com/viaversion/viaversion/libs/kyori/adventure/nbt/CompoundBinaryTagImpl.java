/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.Debug.Renderer;
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
/*     */ @Renderer(text = "\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"", childrenArray = "this.tags.entrySet().toArray()", hasChildren = "!this.tags.isEmpty()")
/*     */ final class CompoundBinaryTagImpl
/*     */   extends AbstractBinaryTag
/*     */   implements CompoundBinaryTag
/*     */ {
/*  42 */   static final CompoundBinaryTag EMPTY = new CompoundBinaryTagImpl(Collections.emptyMap());
/*     */   private final Map<String, BinaryTag> tags;
/*     */   private final int hashCode;
/*     */   
/*     */   CompoundBinaryTagImpl(Map<String, BinaryTag> tags) {
/*  47 */     this.tags = Collections.unmodifiableMap(tags);
/*  48 */     this.hashCode = tags.hashCode();
/*     */   }
/*     */   
/*     */   public boolean contains(@NotNull String key, @NotNull BinaryTagType<?> type) {
/*  52 */     BinaryTag tag = this.tags.get(key);
/*  53 */     return (tag != null && type.test(tag.type()));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<String> keySet() {
/*  58 */     return Collections.unmodifiableSet(this.tags.keySet());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BinaryTag get(String key) {
/*  63 */     return this.tags.get(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompoundBinaryTag put(@NotNull String key, @NotNull BinaryTag tag) {
/*  68 */     return edit(map -> map.put(key, tag));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompoundBinaryTag put(@NotNull CompoundBinaryTag tag) {
/*  73 */     return edit(map -> {
/*     */           for (String key : tag.keySet()) {
/*     */             map.put(key, tag.get(key));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompoundBinaryTag put(@NotNull Map<String, ? extends BinaryTag> tags) {
/*  82 */     return edit(map -> map.putAll(tags));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompoundBinaryTag remove(@NotNull String key, @Nullable Consumer<? super BinaryTag> removed) {
/*  87 */     if (!this.tags.containsKey(key)) {
/*  88 */       return this;
/*     */     }
/*  90 */     return edit(map -> {
/*     */           BinaryTag tag = (BinaryTag)map.remove(key);
/*     */           if (removed != null) {
/*     */             removed.accept(tag);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(@NotNull String key, byte defaultValue) {
/* 100 */     if (contains(key, BinaryTagTypes.BYTE)) {
/* 101 */       return ((NumberBinaryTag)this.tags.get(key)).byteValue();
/*     */     }
/* 103 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(@NotNull String key, short defaultValue) {
/* 108 */     if (contains(key, BinaryTagTypes.SHORT)) {
/* 109 */       return ((NumberBinaryTag)this.tags.get(key)).shortValue();
/*     */     }
/* 111 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(@NotNull String key, int defaultValue) {
/* 116 */     if (contains(key, BinaryTagTypes.INT)) {
/* 117 */       return ((NumberBinaryTag)this.tags.get(key)).intValue();
/*     */     }
/* 119 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(@NotNull String key, long defaultValue) {
/* 124 */     if (contains(key, BinaryTagTypes.LONG)) {
/* 125 */       return ((NumberBinaryTag)this.tags.get(key)).longValue();
/*     */     }
/* 127 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(@NotNull String key, float defaultValue) {
/* 132 */     if (contains(key, BinaryTagTypes.FLOAT)) {
/* 133 */       return ((NumberBinaryTag)this.tags.get(key)).floatValue();
/*     */     }
/* 135 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(@NotNull String key, double defaultValue) {
/* 140 */     if (contains(key, BinaryTagTypes.DOUBLE)) {
/* 141 */       return ((NumberBinaryTag)this.tags.get(key)).doubleValue();
/*     */     }
/* 143 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(@NotNull String key) {
/* 148 */     if (contains(key, BinaryTagTypes.BYTE_ARRAY)) {
/* 149 */       return ((ByteArrayBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 151 */     return new byte[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(@NotNull String key, byte[] defaultValue) {
/* 156 */     if (contains(key, BinaryTagTypes.BYTE_ARRAY)) {
/* 157 */       return ((ByteArrayBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 159 */     return defaultValue;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getString(@NotNull String key, @NotNull String defaultValue) {
/* 164 */     if (contains(key, BinaryTagTypes.STRING)) {
/* 165 */       return ((StringBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 167 */     return defaultValue;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ListBinaryTag getList(@NotNull String key, @NotNull ListBinaryTag defaultValue) {
/* 172 */     if (contains(key, BinaryTagTypes.LIST)) {
/* 173 */       return (ListBinaryTag)this.tags.get(key);
/*     */     }
/* 175 */     return defaultValue;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ListBinaryTag getList(@NotNull String key, @NotNull BinaryTagType<? extends BinaryTag> expectedType, @NotNull ListBinaryTag defaultValue) {
/* 180 */     if (contains(key, BinaryTagTypes.LIST)) {
/* 181 */       ListBinaryTag tag = (ListBinaryTag)this.tags.get(key);
/* 182 */       if (expectedType.test(tag.elementType())) {
/* 183 */         return tag;
/*     */       }
/*     */     } 
/* 186 */     return defaultValue;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompoundBinaryTag getCompound(@NotNull String key, @NotNull CompoundBinaryTag defaultValue) {
/* 191 */     if (contains(key, BinaryTagTypes.COMPOUND)) {
/* 192 */       return (CompoundBinaryTag)this.tags.get(key);
/*     */     }
/* 194 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getIntArray(@NotNull String key) {
/* 199 */     if (contains(key, BinaryTagTypes.INT_ARRAY)) {
/* 200 */       return ((IntArrayBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 202 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getIntArray(@NotNull String key, int[] defaultValue) {
/* 207 */     if (contains(key, BinaryTagTypes.INT_ARRAY)) {
/* 208 */       return ((IntArrayBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 210 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] getLongArray(@NotNull String key) {
/* 215 */     if (contains(key, BinaryTagTypes.LONG_ARRAY)) {
/* 216 */       return ((LongArrayBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 218 */     return new long[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] getLongArray(@NotNull String key, long[] defaultValue) {
/* 223 */     if (contains(key, BinaryTagTypes.LONG_ARRAY)) {
/* 224 */       return ((LongArrayBinaryTag)this.tags.get(key)).value();
/*     */     }
/* 226 */     return defaultValue;
/*     */   }
/*     */   
/*     */   private CompoundBinaryTag edit(Consumer<Map<String, BinaryTag>> consumer) {
/* 230 */     Map<String, BinaryTag> tags = new HashMap<>(this.tags);
/* 231 */     consumer.accept(tags);
/* 232 */     return new CompoundBinaryTagImpl(tags);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 237 */     return (this == that || (that instanceof CompoundBinaryTagImpl && this.tags.equals(((CompoundBinaryTagImpl)that).tags)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 242 */     return this.hashCode;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 247 */     return Stream.of(ExaminableProperty.of("tags", this.tags));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<Map.Entry<String, ? extends BinaryTag>> iterator() {
/* 253 */     return this.tags.entrySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(@NotNull Consumer<? super Map.Entry<String, ? extends BinaryTag>> action) {
/* 258 */     this.tags.entrySet().forEach(Objects.<Consumer>requireNonNull(action, "action"));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\CompoundBinaryTagImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */