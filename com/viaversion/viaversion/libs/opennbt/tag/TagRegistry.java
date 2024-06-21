/*     */ package com.viaversion.viaversion.libs.opennbt.tag;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
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
/*     */ import java.util.function.Supplier;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TagRegistry
/*     */ {
/*     */   private static final int HIGHEST_ID = 12;
/*  27 */   private static final Class<? extends Tag>[] idToTag = (Class<? extends Tag>[])new Class[13];
/*  28 */   private static final Supplier<? extends Tag>[] instanceSuppliers = (Supplier<? extends Tag>[])new Supplier[13];
/*  29 */   private static final Object2IntMap<Class<? extends Tag>> tagToId = (Object2IntMap<Class<? extends Tag>>)new Object2IntOpenHashMap();
/*     */   
/*     */   static {
/*  32 */     tagToId.defaultReturnValue(-1);
/*     */     
/*  34 */     register(1, (Class)ByteTag.class, ByteTag::new);
/*  35 */     register(2, (Class)ShortTag.class, ShortTag::new);
/*  36 */     register(3, (Class)IntTag.class, IntTag::new);
/*  37 */     register(4, (Class)LongTag.class, LongTag::new);
/*  38 */     register(5, (Class)FloatTag.class, FloatTag::new);
/*  39 */     register(6, (Class)DoubleTag.class, DoubleTag::new);
/*  40 */     register(7, (Class)ByteArrayTag.class, ByteArrayTag::new);
/*  41 */     register(8, (Class)StringTag.class, StringTag::new);
/*  42 */     register(9, (Class)ListTag.class, ListTag::new);
/*  43 */     register(10, (Class)CompoundTag.class, CompoundTag::new);
/*  44 */     register(11, (Class)IntArrayTag.class, IntArrayTag::new);
/*  45 */     register(12, (Class)LongArrayTag.class, LongArrayTag::new);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(int id, Class<? extends Tag> tag, Supplier<? extends Tag> supplier) throws TagRegisterException {
/*  56 */     if (id < 0 || id > 12) {
/*  57 */       throw new TagRegisterException("Tag ID must be between 0 and 12");
/*     */     }
/*  59 */     if (idToTag[id] != null) {
/*  60 */       throw new TagRegisterException("Tag ID \"" + id + "\" is already in use.");
/*     */     }
/*  62 */     if (tagToId.containsKey(tag)) {
/*  63 */       throw new TagRegisterException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
/*     */     }
/*     */     
/*  66 */     instanceSuppliers[id] = supplier;
/*  67 */     idToTag[id] = tag;
/*  68 */     tagToId.put(tag, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregister(int id) {
/*  77 */     tagToId.removeInt(getClassFor(id));
/*  78 */     idToTag[id] = null;
/*  79 */     instanceSuppliers[id] = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Class<? extends Tag> getClassFor(int id) {
/*  90 */     return (id >= 0 && id < idToTag.length) ? idToTag[id] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIdFor(Class<? extends Tag> clazz) {
/* 100 */     return tagToId.getInt(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tag createInstance(int id) throws TagCreateException {
/* 111 */     Supplier<? extends Tag> supplier = (id > 0 && id < instanceSuppliers.length) ? instanceSuppliers[id] : null;
/* 112 */     if (supplier == null) {
/* 113 */       throw new TagCreateException("Could not find tag with ID \"" + id + "\".");
/*     */     }
/*     */     
/* 116 */     return supplier.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\TagRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */