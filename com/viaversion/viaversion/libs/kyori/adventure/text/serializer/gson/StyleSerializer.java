/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
/*     */ import java.io.IOException;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
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
/*     */ final class StyleSerializer
/*     */   extends TypeAdapter<Style>
/*     */ {
/*  51 */   private static final TextDecoration[] DECORATIONS = new TextDecoration[] { TextDecoration.BOLD, TextDecoration.ITALIC, TextDecoration.UNDERLINED, TextDecoration.STRIKETHROUGH, TextDecoration.OBFUSCATED };
/*     */   
/*     */   static final String FONT = "font";
/*     */   
/*     */   static final String COLOR = "color";
/*     */   
/*     */   static final String INSERTION = "insertion";
/*     */   static final String CLICK_EVENT = "clickEvent";
/*     */   static final String CLICK_EVENT_ACTION = "action";
/*     */   static final String CLICK_EVENT_VALUE = "value";
/*     */   
/*     */   static {
/*  63 */     Set<TextDecoration> knownDecorations = EnumSet.allOf(TextDecoration.class);
/*  64 */     for (TextDecoration decoration : DECORATIONS) {
/*  65 */       knownDecorations.remove(decoration);
/*     */     }
/*  67 */     if (!knownDecorations.isEmpty()) {
/*  68 */       throw new IllegalStateException("Gson serializer is missing some text decorations: " + knownDecorations);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final String HOVER_EVENT = "hoverEvent";
/*     */   
/*     */   static final String HOVER_EVENT_ACTION = "action";
/*     */   static final String HOVER_EVENT_CONTENTS = "contents";
/*     */   @Deprecated
/*     */   static final String HOVER_EVENT_VALUE = "value";
/*     */   private final LegacyHoverEventSerializer legacyHover;
/*     */   private final boolean emitLegacyHover;
/*     */   private final Gson gson;
/*     */   
/*     */   static TypeAdapter<Style> create(@Nullable LegacyHoverEventSerializer legacyHover, boolean emitLegacyHover, Gson gson) {
/*  84 */     return (new StyleSerializer(legacyHover, emitLegacyHover, gson)).nullSafe();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StyleSerializer(@Nullable LegacyHoverEventSerializer legacyHover, boolean emitLegacyHover, Gson gson) {
/*  92 */     this.legacyHover = legacyHover;
/*  93 */     this.emitLegacyHover = emitLegacyHover;
/*  94 */     this.gson = gson;
/*     */   }
/*     */ 
/*     */   
/*     */   public Style read(JsonReader in) throws IOException {
/*  99 */     in.beginObject();
/* 100 */     Style.Builder style = Style.style();
/*     */     
/* 102 */     while (in.hasNext()) {
/* 103 */       String fieldName = in.nextName();
/* 104 */       if (fieldName.equals("font")) {
/* 105 */         style.font((Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE)); continue;
/* 106 */       }  if (fieldName.equals("color")) {
/* 107 */         TextColorWrapper color = (TextColorWrapper)this.gson.fromJson(in, SerializerFactory.COLOR_WRAPPER_TYPE);
/* 108 */         if (color.color != null) {
/* 109 */           style.color(color.color); continue;
/* 110 */         }  if (color.decoration != null)
/* 111 */           style.decoration(color.decoration, TextDecoration.State.TRUE);  continue;
/*     */       } 
/* 113 */       if (TextDecoration.NAMES.keys().contains(fieldName)) {
/* 114 */         style.decoration((TextDecoration)TextDecoration.NAMES.value(fieldName), readBoolean(in)); continue;
/* 115 */       }  if (fieldName.equals("insertion")) {
/* 116 */         style.insertion(in.nextString()); continue;
/* 117 */       }  if (fieldName.equals("clickEvent")) {
/* 118 */         in.beginObject();
/* 119 */         ClickEvent.Action action = null;
/* 120 */         String value = null;
/* 121 */         while (in.hasNext()) {
/* 122 */           String clickEventField = in.nextName();
/* 123 */           if (clickEventField.equals("action")) {
/* 124 */             action = (ClickEvent.Action)this.gson.fromJson(in, SerializerFactory.CLICK_ACTION_TYPE); continue;
/* 125 */           }  if (clickEventField.equals("value")) {
/* 126 */             value = (in.peek() == JsonToken.NULL) ? null : in.nextString(); continue;
/*     */           } 
/* 128 */           in.skipValue();
/*     */         } 
/*     */         
/* 131 */         if (action != null && action.readable() && value != null) {
/* 132 */           style.clickEvent(ClickEvent.clickEvent(action, value));
/*     */         }
/* 134 */         in.endObject(); continue;
/* 135 */       }  if (fieldName.equals("hoverEvent")) {
/* 136 */         JsonObject hoverEventObject = (JsonObject)this.gson.fromJson(in, JsonObject.class);
/* 137 */         if (hoverEventObject != null) {
/* 138 */           JsonPrimitive serializedAction = hoverEventObject.getAsJsonPrimitive("action");
/* 139 */           if (serializedAction == null) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 144 */           HoverEvent.Action<Object> action = (HoverEvent.Action<Object>)this.gson.fromJson((JsonElement)serializedAction, SerializerFactory.HOVER_ACTION_TYPE);
/* 145 */           if (action.readable()) {
/*     */             Object value;
/* 147 */             if (hoverEventObject.has("contents")) {
/* 148 */               JsonElement rawValue = hoverEventObject.get("contents");
/* 149 */               Class<?> actionType = action.type();
/* 150 */               if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(actionType)) {
/* 151 */                 value = this.gson.fromJson(rawValue, SerializerFactory.COMPONENT_TYPE);
/* 152 */               } else if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(actionType)) {
/* 153 */                 value = this.gson.fromJson(rawValue, SerializerFactory.SHOW_ITEM_TYPE);
/* 154 */               } else if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(actionType)) {
/* 155 */                 value = this.gson.fromJson(rawValue, SerializerFactory.SHOW_ENTITY_TYPE);
/*     */               } else {
/* 157 */                 value = null;
/*     */               } 
/* 159 */             } else if (hoverEventObject.has("value")) {
/* 160 */               Component rawValue = (Component)this.gson.fromJson(hoverEventObject.get("value"), SerializerFactory.COMPONENT_TYPE);
/* 161 */               value = legacyHoverEventContents(action, rawValue);
/*     */             } else {
/* 163 */               value = null;
/*     */             } 
/*     */             
/* 166 */             if (value != null)
/* 167 */               style.hoverEvent((HoverEventSource)HoverEvent.hoverEvent(action, value)); 
/*     */           } 
/*     */         } 
/*     */         continue;
/*     */       } 
/* 172 */       in.skipValue();
/*     */     } 
/*     */ 
/*     */     
/* 176 */     in.endObject();
/* 177 */     return style.build();
/*     */   }
/*     */   
/*     */   private boolean readBoolean(JsonReader in) throws IOException {
/* 181 */     JsonToken peek = in.peek();
/* 182 */     if (peek == JsonToken.BOOLEAN)
/* 183 */       return in.nextBoolean(); 
/* 184 */     if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
/* 185 */       return Boolean.parseBoolean(in.nextString());
/*     */     }
/* 187 */     throw new JsonParseException("Token of type " + peek + " cannot be interpreted as a boolean");
/*     */   }
/*     */ 
/*     */   
/*     */   private Object legacyHoverEventContents(HoverEvent.Action<?> action, Component rawValue) {
/* 192 */     if (action == HoverEvent.Action.SHOW_TEXT)
/* 193 */       return rawValue; 
/* 194 */     if (this.legacyHover != null) {
/*     */       try {
/* 196 */         if (action == HoverEvent.Action.SHOW_ENTITY)
/* 197 */           return this.legacyHover.deserializeShowEntity(rawValue, (Codec.Decoder)decoder()); 
/* 198 */         if (action == HoverEvent.Action.SHOW_ITEM) {
/* 199 */           return this.legacyHover.deserializeShowItem(rawValue);
/*     */         }
/* 201 */       } catch (IOException ex) {
/* 202 */         throw new JsonParseException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 206 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private Codec.Decoder<Component, String, JsonParseException> decoder() {
/* 210 */     return string -> (Component)this.gson.fromJson(string, SerializerFactory.COMPONENT_TYPE);
/*     */   }
/*     */   
/*     */   private Codec.Encoder<Component, String, JsonParseException> encoder() {
/* 214 */     return component -> this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Style value) throws IOException {
/* 219 */     out.beginObject();
/*     */     
/* 221 */     for (int i = 0, length = DECORATIONS.length; i < length; i++) {
/* 222 */       TextDecoration decoration = DECORATIONS[i];
/* 223 */       TextDecoration.State state = value.decoration(decoration);
/* 224 */       if (state != TextDecoration.State.NOT_SET) {
/* 225 */         String name = (String)TextDecoration.NAMES.key(decoration);
/* 226 */         assert name != null;
/* 227 */         out.name(name);
/* 228 */         out.value((state == TextDecoration.State.TRUE));
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     TextColor color = value.color();
/* 233 */     if (color != null) {
/* 234 */       out.name("color");
/* 235 */       this.gson.toJson(color, SerializerFactory.COLOR_TYPE, out);
/*     */     } 
/*     */     
/* 238 */     String insertion = value.insertion();
/* 239 */     if (insertion != null) {
/* 240 */       out.name("insertion");
/* 241 */       out.value(insertion);
/*     */     } 
/*     */     
/* 244 */     ClickEvent clickEvent = value.clickEvent();
/* 245 */     if (clickEvent != null) {
/* 246 */       out.name("clickEvent");
/* 247 */       out.beginObject();
/* 248 */       out.name("action");
/* 249 */       this.gson.toJson(clickEvent.action(), SerializerFactory.CLICK_ACTION_TYPE, out);
/* 250 */       out.name("value");
/* 251 */       out.value(clickEvent.value());
/* 252 */       out.endObject();
/*     */     } 
/*     */     
/* 255 */     HoverEvent<?> hoverEvent = value.hoverEvent();
/* 256 */     if (hoverEvent != null) {
/* 257 */       out.name("hoverEvent");
/* 258 */       out.beginObject();
/* 259 */       out.name("action");
/* 260 */       HoverEvent.Action<?> action = hoverEvent.action();
/* 261 */       this.gson.toJson(action, SerializerFactory.HOVER_ACTION_TYPE, out);
/* 262 */       out.name("contents");
/* 263 */       if (action == HoverEvent.Action.SHOW_ITEM) {
/* 264 */         this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ITEM_TYPE, out);
/* 265 */       } else if (action == HoverEvent.Action.SHOW_ENTITY) {
/* 266 */         this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ENTITY_TYPE, out);
/* 267 */       } else if (action == HoverEvent.Action.SHOW_TEXT) {
/* 268 */         this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
/*     */       } else {
/* 270 */         throw new JsonParseException("Don't know how to serialize " + hoverEvent.value());
/*     */       } 
/* 272 */       if (this.emitLegacyHover) {
/* 273 */         out.name("value");
/* 274 */         serializeLegacyHoverEvent(hoverEvent, out);
/*     */       } 
/*     */       
/* 277 */       out.endObject();
/*     */     } 
/*     */     
/* 280 */     Key font = value.font();
/* 281 */     if (font != null) {
/* 282 */       out.name("font");
/* 283 */       this.gson.toJson(font, SerializerFactory.KEY_TYPE, out);
/*     */     } 
/*     */     
/* 286 */     out.endObject();
/*     */   }
/*     */   
/*     */   private void serializeLegacyHoverEvent(HoverEvent<?> hoverEvent, JsonWriter out) throws IOException {
/* 290 */     if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
/* 291 */       this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
/* 292 */     } else if (this.legacyHover != null) {
/* 293 */       Component serialized = null;
/*     */       try {
/* 295 */         if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
/* 296 */           serialized = this.legacyHover.serializeShowEntity((HoverEvent.ShowEntity)hoverEvent.value(), (Codec.Encoder)encoder());
/* 297 */         } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
/* 298 */           serialized = this.legacyHover.serializeShowItem((HoverEvent.ShowItem)hoverEvent.value());
/*     */         } 
/* 300 */       } catch (IOException ex) {
/* 301 */         throw new JsonSyntaxException(ex);
/*     */       } 
/* 303 */       if (serialized != null) {
/* 304 */         this.gson.toJson(serialized, SerializerFactory.COMPONENT_TYPE, out);
/*     */       } else {
/* 306 */         out.nullValue();
/*     */       } 
/*     */     } else {
/* 309 */       out.nullValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\StyleSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */