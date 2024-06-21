/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ final class ComponentSerializerImpl
/*     */   extends TypeAdapter<Component>
/*     */ {
/*     */   static final String TEXT = "text";
/*     */   static final String TRANSLATE = "translate";
/*     */   static final String TRANSLATE_WITH = "with";
/*     */   static final String SCORE = "score";
/*     */   static final String SCORE_NAME = "name";
/*     */   static final String SCORE_OBJECTIVE = "objective";
/*     */   static final String SCORE_VALUE = "value";
/*     */   static final String SELECTOR = "selector";
/*     */   static final String KEYBIND = "keybind";
/*     */   static final String EXTRA = "extra";
/*     */   static final String NBT = "nbt";
/*     */   static final String NBT_INTERPRET = "interpret";
/*     */   static final String NBT_BLOCK = "block";
/*     */   static final String NBT_ENTITY = "entity";
/*     */   static final String NBT_STORAGE = "storage";
/*     */   static final String SEPARATOR = "separator";
/*  74 */   static final Type COMPONENT_LIST_TYPE = (new TypeToken<List<Component>>() {  }).getType(); private final Gson gson;
/*     */   
/*     */   static TypeAdapter<Component> create(Gson gson) {
/*  77 */     return (new ComponentSerializerImpl(gson)).nullSafe();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentSerializerImpl(Gson gson) {
/*  83 */     this.gson = gson;
/*     */   }
/*     */   public BuildableComponent<?, ?> read(JsonReader in) throws IOException {
/*     */     BuildableComponent<?, ?> buildableComponent;
/*     */     StorageNBTComponent.Builder builder;
/*  88 */     JsonToken token = in.peek();
/*  89 */     if (token == JsonToken.STRING || token == JsonToken.NUMBER || token == JsonToken.BOOLEAN)
/*  90 */       return (BuildableComponent<?, ?>)Component.text(readString(in)); 
/*  91 */     if (token == JsonToken.BEGIN_ARRAY) {
/*  92 */       ComponentBuilder<?, ?> parent = null;
/*  93 */       in.beginArray();
/*  94 */       while (in.hasNext()) {
/*  95 */         BuildableComponent<?, ?> child = read(in);
/*  96 */         if (parent == null) {
/*  97 */           parent = child.toBuilder(); continue;
/*     */         } 
/*  99 */         parent.append((Component)child);
/*     */       } 
/*     */       
/* 102 */       if (parent == null) {
/* 103 */         throw notSureHowToDeserialize(in.getPath());
/*     */       }
/* 105 */       in.endArray();
/* 106 */       return parent.build();
/* 107 */     }  if (token != JsonToken.BEGIN_OBJECT) {
/* 108 */       throw notSureHowToDeserialize(in.getPath());
/*     */     }
/*     */ 
/*     */     
/* 112 */     JsonObject style = new JsonObject();
/* 113 */     List<Component> extra = Collections.emptyList();
/*     */ 
/*     */     
/* 116 */     String text = null;
/* 117 */     String translate = null;
/* 118 */     List<Component> translateWith = null;
/* 119 */     String scoreName = null;
/* 120 */     String scoreObjective = null;
/* 121 */     String scoreValue = null;
/* 122 */     String selector = null;
/* 123 */     String keybind = null;
/* 124 */     String nbt = null;
/* 125 */     boolean nbtInterpret = false;
/* 126 */     BlockNBTComponent.Pos nbtBlock = null;
/* 127 */     String nbtEntity = null;
/* 128 */     Key nbtStorage = null;
/* 129 */     Component separator = null;
/*     */     
/* 131 */     in.beginObject();
/* 132 */     while (in.hasNext()) {
/* 133 */       String fieldName = in.nextName();
/* 134 */       if (fieldName.equals("text")) {
/* 135 */         text = readString(in); continue;
/* 136 */       }  if (fieldName.equals("translate")) {
/* 137 */         translate = in.nextString(); continue;
/* 138 */       }  if (fieldName.equals("with")) {
/* 139 */         translateWith = (List<Component>)this.gson.fromJson(in, COMPONENT_LIST_TYPE); continue;
/* 140 */       }  if (fieldName.equals("score")) {
/* 141 */         in.beginObject();
/* 142 */         while (in.hasNext()) {
/* 143 */           String scoreFieldName = in.nextName();
/* 144 */           if (scoreFieldName.equals("name")) {
/* 145 */             scoreName = in.nextString(); continue;
/* 146 */           }  if (scoreFieldName.equals("objective")) {
/* 147 */             scoreObjective = in.nextString(); continue;
/* 148 */           }  if (scoreFieldName.equals("value")) {
/* 149 */             scoreValue = in.nextString(); continue;
/*     */           } 
/* 151 */           in.skipValue();
/*     */         } 
/*     */         
/* 154 */         if (scoreName == null || scoreObjective == null) {
/* 155 */           throw new JsonParseException("A score component requires a name and objective");
/*     */         }
/* 157 */         in.endObject(); continue;
/* 158 */       }  if (fieldName.equals("selector")) {
/* 159 */         selector = in.nextString(); continue;
/* 160 */       }  if (fieldName.equals("keybind")) {
/* 161 */         keybind = in.nextString(); continue;
/* 162 */       }  if (fieldName.equals("nbt")) {
/* 163 */         nbt = in.nextString(); continue;
/* 164 */       }  if (fieldName.equals("interpret")) {
/* 165 */         nbtInterpret = in.nextBoolean(); continue;
/* 166 */       }  if (fieldName.equals("block")) {
/* 167 */         nbtBlock = (BlockNBTComponent.Pos)this.gson.fromJson(in, SerializerFactory.BLOCK_NBT_POS_TYPE); continue;
/* 168 */       }  if (fieldName.equals("entity")) {
/* 169 */         nbtEntity = in.nextString(); continue;
/* 170 */       }  if (fieldName.equals("storage")) {
/* 171 */         nbtStorage = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE); continue;
/* 172 */       }  if (fieldName.equals("extra")) {
/* 173 */         extra = (List<Component>)this.gson.fromJson(in, COMPONENT_LIST_TYPE); continue;
/* 174 */       }  if (fieldName.equals("separator")) {
/* 175 */         buildableComponent = read(in); continue;
/*     */       } 
/* 177 */       style.add(fieldName, (JsonElement)this.gson.fromJson(in, JsonElement.class));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (text != null) {
/* 183 */       TextComponent.Builder builder1 = Component.text().content(text);
/* 184 */     } else if (translate != null) {
/* 185 */       if (translateWith != null) {
/* 186 */         TranslatableComponent.Builder builder1 = Component.translatable().key(translate).args(translateWith);
/*     */       } else {
/* 188 */         TranslatableComponent.Builder builder1 = Component.translatable().key(translate);
/*     */       } 
/* 190 */     } else if (scoreName != null && scoreObjective != null) {
/* 191 */       if (scoreValue == null) {
/* 192 */         ScoreComponent.Builder builder1 = Component.score().name(scoreName).objective(scoreObjective);
/*     */       } else {
/* 194 */         ScoreComponent.Builder builder1 = Component.score().name(scoreName).objective(scoreObjective).value(scoreValue);
/*     */       } 
/* 196 */     } else if (selector != null) {
/* 197 */       SelectorComponent.Builder builder1 = Component.selector().pattern(selector).separator((ComponentLike)buildableComponent);
/* 198 */     } else if (keybind != null) {
/* 199 */       KeybindComponent.Builder builder1 = Component.keybind().keybind(keybind);
/* 200 */     } else if (nbt != null) {
/* 201 */       if (nbtBlock != null) {
/* 202 */         BlockNBTComponent.Builder builder1 = ((BlockNBTComponent.Builder)nbt(Component.blockNBT(), nbt, nbtInterpret, (Component)buildableComponent)).pos(nbtBlock);
/* 203 */       } else if (nbtEntity != null) {
/* 204 */         EntityNBTComponent.Builder builder1 = ((EntityNBTComponent.Builder)nbt(Component.entityNBT(), nbt, nbtInterpret, (Component)buildableComponent)).selector(nbtEntity);
/* 205 */       } else if (nbtStorage != null) {
/* 206 */         builder = ((StorageNBTComponent.Builder)nbt(Component.storageNBT(), nbt, nbtInterpret, (Component)buildableComponent)).storage(nbtStorage);
/*     */       } else {
/* 208 */         throw notSureHowToDeserialize(in.getPath());
/*     */       } 
/*     */     } else {
/* 211 */       throw notSureHowToDeserialize(in.getPath());
/*     */     } 
/*     */     
/* 214 */     builder.style((Style)this.gson.fromJson((JsonElement)style, SerializerFactory.STYLE_TYPE))
/* 215 */       .append(extra);
/* 216 */     in.endObject();
/* 217 */     return builder.build();
/*     */   }
/*     */   
/*     */   private static String readString(JsonReader in) throws IOException {
/* 221 */     JsonToken peek = in.peek();
/* 222 */     if (peek == JsonToken.STRING || peek == JsonToken.NUMBER)
/* 223 */       return in.nextString(); 
/* 224 */     if (peek == JsonToken.BOOLEAN) {
/* 225 */       return String.valueOf(in.nextBoolean());
/*     */     }
/* 227 */     throw new JsonParseException("Token of type " + peek + " cannot be interpreted as a string");
/*     */   }
/*     */ 
/*     */   
/*     */   private static <C extends NBTComponent<C, B>, B extends com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder<C, B>> B nbt(B builder, String nbt, boolean interpret, @Nullable Component separator) {
/* 232 */     return (B)builder
/* 233 */       .nbtPath(nbt)
/* 234 */       .interpret(interpret)
/* 235 */       .separator((ComponentLike)separator);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Component value) throws IOException {
/* 240 */     out.beginObject();
/*     */     
/* 242 */     if (value.hasStyling()) {
/* 243 */       JsonElement style = this.gson.toJsonTree(value.style(), SerializerFactory.STYLE_TYPE);
/* 244 */       if (style.isJsonObject()) {
/* 245 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)style.getAsJsonObject().entrySet()) {
/* 246 */           out.name(entry.getKey());
/* 247 */           this.gson.toJson(entry.getValue(), out);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 252 */     if (!value.children().isEmpty()) {
/* 253 */       out.name("extra");
/* 254 */       this.gson.toJson(value.children(), COMPONENT_LIST_TYPE, out);
/*     */     } 
/*     */     
/* 257 */     if (value instanceof TextComponent) {
/* 258 */       out.name("text");
/* 259 */       out.value(((TextComponent)value).content());
/* 260 */     } else if (value instanceof TranslatableComponent) {
/* 261 */       TranslatableComponent translatable = (TranslatableComponent)value;
/* 262 */       out.name("translate");
/* 263 */       out.value(translatable.key());
/* 264 */       if (!translatable.args().isEmpty()) {
/* 265 */         out.name("with");
/* 266 */         this.gson.toJson(translatable.args(), COMPONENT_LIST_TYPE, out);
/*     */       } 
/* 268 */     } else if (value instanceof ScoreComponent) {
/* 269 */       ScoreComponent score = (ScoreComponent)value;
/* 270 */       out.name("score");
/* 271 */       out.beginObject();
/* 272 */       out.name("name");
/* 273 */       out.value(score.name());
/* 274 */       out.name("objective");
/* 275 */       out.value(score.objective());
/* 276 */       if (score.value() != null) {
/* 277 */         out.name("value");
/* 278 */         out.value(score.value());
/*     */       } 
/* 280 */       out.endObject();
/* 281 */     } else if (value instanceof SelectorComponent) {
/* 282 */       SelectorComponent selector = (SelectorComponent)value;
/* 283 */       out.name("selector");
/* 284 */       out.value(selector.pattern());
/* 285 */       serializeSeparator(out, selector.separator());
/* 286 */     } else if (value instanceof KeybindComponent) {
/* 287 */       out.name("keybind");
/* 288 */       out.value(((KeybindComponent)value).keybind());
/* 289 */     } else if (value instanceof NBTComponent) {
/* 290 */       NBTComponent<?, ?> nbt = (NBTComponent<?, ?>)value;
/* 291 */       out.name("nbt");
/* 292 */       out.value(nbt.nbtPath());
/* 293 */       out.name("interpret");
/* 294 */       out.value(nbt.interpret());
/* 295 */       serializeSeparator(out, nbt.separator());
/* 296 */       if (value instanceof BlockNBTComponent) {
/* 297 */         out.name("block");
/* 298 */         this.gson.toJson(((BlockNBTComponent)value).pos(), SerializerFactory.BLOCK_NBT_POS_TYPE, out);
/* 299 */       } else if (value instanceof EntityNBTComponent) {
/* 300 */         out.name("entity");
/* 301 */         out.value(((EntityNBTComponent)value).selector());
/* 302 */       } else if (value instanceof StorageNBTComponent) {
/* 303 */         out.name("storage");
/* 304 */         this.gson.toJson(((StorageNBTComponent)value).storage(), SerializerFactory.KEY_TYPE, out);
/*     */       } else {
/* 306 */         throw notSureHowToSerialize(value);
/*     */       } 
/*     */     } else {
/* 309 */       throw notSureHowToSerialize(value);
/*     */     } 
/*     */     
/* 312 */     out.endObject();
/*     */   }
/*     */   
/*     */   private void serializeSeparator(JsonWriter out, @Nullable Component separator) throws IOException {
/* 316 */     if (separator != null) {
/* 317 */       out.name("separator");
/* 318 */       write(out, separator);
/*     */     } 
/*     */   }
/*     */   
/*     */   static JsonParseException notSureHowToDeserialize(Object element) {
/* 323 */     return new JsonParseException("Don't know how to turn " + element + " into a Component");
/*     */   }
/*     */   
/*     */   private static IllegalArgumentException notSureHowToSerialize(Component component) {
/* 327 */     return new IllegalArgumentException("Don't know how to serialize " + component + " as a Component");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\ComponentSerializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */