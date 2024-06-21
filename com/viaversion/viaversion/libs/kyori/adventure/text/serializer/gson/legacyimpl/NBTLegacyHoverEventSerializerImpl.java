/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringIO;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ final class NBTLegacyHoverEventSerializerImpl
/*     */   implements LegacyHoverEventSerializer
/*     */ {
/*  41 */   static final NBTLegacyHoverEventSerializerImpl INSTANCE = new NBTLegacyHoverEventSerializerImpl();
/*  42 */   private static final TagStringIO SNBT_IO = TagStringIO.get();
/*  43 */   private static final Codec<CompoundBinaryTag, String, IOException, IOException> SNBT_CODEC = Codec.codec(SNBT_IO::asCompound, SNBT_IO::asString); static { Objects.requireNonNull(SNBT_IO); Objects.requireNonNull(SNBT_IO); }
/*     */ 
/*     */ 
/*     */   
/*     */   static final String ITEM_TYPE = "id";
/*     */   
/*     */   static final String ITEM_COUNT = "Count";
/*     */   
/*     */   static final String ITEM_TAG = "tag";
/*     */   
/*     */   static final String ENTITY_NAME = "name";
/*     */   static final String ENTITY_TYPE = "type";
/*     */   static final String ENTITY_ID = "id";
/*     */   
/*     */   public HoverEvent.ShowItem deserializeShowItem(@NotNull Component input) throws IOException {
/*  58 */     assertTextComponent(input);
/*  59 */     CompoundBinaryTag contents = (CompoundBinaryTag)SNBT_CODEC.decode(((TextComponent)input).content());
/*  60 */     CompoundBinaryTag tag = contents.getCompound("tag");
/*  61 */     return HoverEvent.ShowItem.of(
/*  62 */         Key.key(contents.getString("id")), contents
/*  63 */         .getByte("Count", (byte)1), 
/*  64 */         (tag == CompoundBinaryTag.empty()) ? null : BinaryTagHolder.encode(tag, SNBT_CODEC));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HoverEvent.ShowEntity deserializeShowEntity(@NotNull Component input, Codec.Decoder<Component, String, ? extends RuntimeException> componentCodec) throws IOException {
/*  70 */     assertTextComponent(input);
/*  71 */     CompoundBinaryTag contents = (CompoundBinaryTag)SNBT_CODEC.decode(((TextComponent)input).content());
/*  72 */     return HoverEvent.ShowEntity.of(
/*  73 */         Key.key(contents.getString("type")), 
/*  74 */         UUID.fromString(contents.getString("id")), (Component)componentCodec
/*  75 */         .decode(contents.getString("name")));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void assertTextComponent(Component component) {
/*  80 */     if (!(component instanceof TextComponent) || !component.children().isEmpty()) {
/*  81 */       throw new IllegalArgumentException("Legacy events must be single Component instances");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Component serializeShowItem(HoverEvent.ShowItem input) throws IOException {
/*  89 */     CompoundBinaryTag.Builder builder = (CompoundBinaryTag.Builder)((CompoundBinaryTag.Builder)CompoundBinaryTag.builder().putString("id", input.item().asString())).putByte("Count", (byte)input.count());
/*  90 */     BinaryTagHolder nbt = input.nbt();
/*  91 */     if (nbt != null) {
/*  92 */       builder.put("tag", (BinaryTag)nbt.get(SNBT_CODEC));
/*     */     }
/*  94 */     return (Component)Component.text((String)SNBT_CODEC.encode(builder.build()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Component serializeShowEntity(HoverEvent.ShowEntity input, Codec.Encoder<Component, String, ? extends RuntimeException> componentCodec) throws IOException {
/* 101 */     CompoundBinaryTag.Builder builder = (CompoundBinaryTag.Builder)((CompoundBinaryTag.Builder)CompoundBinaryTag.builder().putString("id", input.id().toString())).putString("type", input.type().asString());
/* 102 */     Component name = input.name();
/* 103 */     if (name != null) {
/* 104 */       builder.putString("name", (String)componentCodec.encode(name));
/*     */     }
/* 106 */     return (Component)Component.text((String)SNBT_CODEC.encode(builder.build()));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\legacyimpl\NBTLegacyHoverEventSerializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */