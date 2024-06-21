/*     */ package com.viaversion.viaversion.libs.opennbt.conversion;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ByteTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.DoubleTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.FloatTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ListTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.LongArrayTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.ShortTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.builtin.StringTagConverter;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ConverterRegistry {
/*  30 */   private static final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> tagToConverter = new HashMap<>();
/*  31 */   private static final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap<>();
/*     */   
/*     */   static {
/*  34 */     register(ByteTag.class, Byte.class, (TagConverter<ByteTag, Byte>)new ByteTagConverter());
/*  35 */     register(ShortTag.class, Short.class, (TagConverter<ShortTag, Short>)new ShortTagConverter());
/*  36 */     register(IntTag.class, Integer.class, (TagConverter<IntTag, Integer>)new IntTagConverter());
/*  37 */     register(LongTag.class, Long.class, (TagConverter<LongTag, Long>)new LongTagConverter());
/*  38 */     register(FloatTag.class, Float.class, (TagConverter<FloatTag, Float>)new FloatTagConverter());
/*  39 */     register(DoubleTag.class, Double.class, (TagConverter<DoubleTag, Double>)new DoubleTagConverter());
/*  40 */     register(ByteArrayTag.class, (Class)byte[].class, (TagConverter<ByteArrayTag, byte>)new ByteArrayTagConverter());
/*  41 */     register(StringTag.class, String.class, (TagConverter<StringTag, String>)new StringTagConverter());
/*  42 */     register(ListTag.class, List.class, (TagConverter<ListTag, List>)new ListTagConverter());
/*  43 */     register(CompoundTag.class, Map.class, (TagConverter<CompoundTag, Map>)new CompoundTagConverter());
/*  44 */     register(IntArrayTag.class, (Class)int[].class, (TagConverter<IntArrayTag, int>)new IntArrayTagConverter());
/*  45 */     register(LongArrayTag.class, (Class)long[].class, (TagConverter<LongArrayTag, long>)new LongArrayTagConverter());
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
/*     */   public static <T extends Tag, V> void register(Class<T> tag, Class<V> type, TagConverter<T, V> converter) throws ConverterRegisterException {
/*  59 */     if (tagToConverter.containsKey(tag)) {
/*  60 */       throw new ConverterRegisterException("Type conversion to tag " + tag.getName() + " is already registered.");
/*     */     }
/*     */     
/*  63 */     if (typeToConverter.containsKey(type)) {
/*  64 */       throw new ConverterRegisterException("Tag conversion to type " + type.getName() + " is already registered.");
/*     */     }
/*     */     
/*  67 */     tagToConverter.put(tag, converter);
/*  68 */     typeToConverter.put(type, converter);
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
/*     */   public static <T extends Tag, V> void unregister(Class<T> tag, Class<V> type) {
/*  80 */     tagToConverter.remove(tag);
/*  81 */     typeToConverter.remove(type);
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
/*     */   public static <T extends Tag, V> V convertToValue(T tag) throws ConversionException {
/*  94 */     if (tag == null || tag.getValue() == null) {
/*  95 */       return null;
/*     */     }
/*     */     
/*  98 */     if (!tagToConverter.containsKey(tag.getClass())) {
/*  99 */       throw new ConversionException("Tag type " + tag.getClass().getName() + " has no converter.");
/*     */     }
/*     */     
/* 102 */     TagConverter<T, ?> converter = (TagConverter<T, ?>)tagToConverter.get(tag.getClass());
/* 103 */     return (V)converter.convert(tag);
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
/*     */   public static <V, T extends Tag> T convertToTag(V value) throws ConversionException {
/* 116 */     if (value == null) {
/* 117 */       return null;
/*     */     }
/*     */     
/* 120 */     TagConverter<T, V> converter = (TagConverter<T, V>)typeToConverter.get(value.getClass());
/* 121 */     if (converter == null) {
/* 122 */       for (Class<?> clazz : getAllClasses(value.getClass())) {
/* 123 */         if (typeToConverter.containsKey(clazz)) {
/*     */           try {
/* 125 */             converter = (TagConverter<T, V>)typeToConverter.get(clazz);
/*     */             break;
/* 127 */           } catch (ClassCastException classCastException) {}
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 133 */     if (converter == null) {
/* 134 */       throw new ConversionException("Value type " + value.getClass().getName() + " has no converter.");
/*     */     }
/*     */     
/* 137 */     return converter.convert(value);
/*     */   }
/*     */   
/*     */   private static Set<Class<?>> getAllClasses(Class<?> clazz) {
/* 141 */     Set<Class<?>> ret = new LinkedHashSet<>();
/* 142 */     Class<?> c = clazz;
/* 143 */     while (c != null) {
/* 144 */       ret.add(c);
/* 145 */       ret.addAll(getAllSuperInterfaces(c));
/* 146 */       c = c.getSuperclass();
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if (ret.contains(Serializable.class)) {
/* 151 */       ret.remove(Serializable.class);
/* 152 */       ret.add(Serializable.class);
/*     */     } 
/*     */     
/* 155 */     return ret;
/*     */   }
/*     */   
/*     */   private static Set<Class<?>> getAllSuperInterfaces(Class<?> clazz) {
/* 159 */     Set<Class<?>> ret = new HashSet<>();
/* 160 */     for (Class<?> c : clazz.getInterfaces()) {
/* 161 */       ret.add(c);
/* 162 */       ret.addAll(getAllSuperInterfaces(c));
/*     */     } 
/*     */     
/* 165 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\ConverterRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */