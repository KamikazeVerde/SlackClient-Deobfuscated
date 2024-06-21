/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.internal.;
/*     */ import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
/*     */ import com.viaversion.viaversion.libs.gson.internal.JsonReaderInternalAccess;
/*     */ import com.viaversion.viaversion.libs.gson.internal.ObjectConstructor;
/*     */ import com.viaversion.viaversion.libs.gson.internal.Streams;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class MapTypeAdapterFactory
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private final ConstructorConstructor constructorConstructor;
/*     */   final boolean complexMapKeySerialization;
/*     */   
/*     */   public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean complexMapKeySerialization) {
/* 111 */     this.constructorConstructor = constructorConstructor;
/* 112 */     this.complexMapKeySerialization = complexMapKeySerialization;
/*     */   }
/*     */   
/*     */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 116 */     Type type = typeToken.getType();
/*     */     
/* 118 */     Class<? super T> rawType = typeToken.getRawType();
/* 119 */     if (!Map.class.isAssignableFrom(rawType)) {
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     Type[] keyAndValueTypes = .Gson.Types.getMapKeyAndValueTypes(type, rawType);
/* 124 */     TypeAdapter<?> keyAdapter = getKeyAdapter(gson, keyAndValueTypes[0]);
/* 125 */     TypeAdapter<?> valueAdapter = gson.getAdapter(TypeToken.get(keyAndValueTypes[1]));
/* 126 */     ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken);
/*     */ 
/*     */ 
/*     */     
/* 130 */     TypeAdapter<T> result = (TypeAdapter)new Adapter<>(gson, keyAndValueTypes[0], keyAdapter, keyAndValueTypes[1], valueAdapter, (ObjectConstructor)constructor);
/*     */     
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TypeAdapter<?> getKeyAdapter(Gson context, Type keyType) {
/* 139 */     return (keyType == boolean.class || keyType == Boolean.class) ? 
/* 140 */       TypeAdapters.BOOLEAN_AS_STRING : 
/* 141 */       context.getAdapter(TypeToken.get(keyType));
/*     */   }
/*     */   
/*     */   private final class Adapter<K, V>
/*     */     extends TypeAdapter<Map<K, V>>
/*     */   {
/*     */     private final TypeAdapter<K> keyTypeAdapter;
/*     */     private final TypeAdapter<V> valueTypeAdapter;
/*     */     private final ObjectConstructor<? extends Map<K, V>> constructor;
/*     */     
/*     */     public Adapter(Gson context, Type keyType, TypeAdapter<K> keyTypeAdapter, Type valueType, TypeAdapter<V> valueTypeAdapter, ObjectConstructor<? extends Map<K, V>> constructor) {
/* 152 */       this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, keyTypeAdapter, keyType);
/*     */       
/* 154 */       this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, valueTypeAdapter, valueType);
/*     */       
/* 156 */       this.constructor = constructor;
/*     */     }
/*     */     
/*     */     public Map<K, V> read(JsonReader in) throws IOException {
/* 160 */       JsonToken peek = in.peek();
/* 161 */       if (peek == JsonToken.NULL) {
/* 162 */         in.nextNull();
/* 163 */         return null;
/*     */       } 
/*     */       
/* 166 */       Map<K, V> map = (Map<K, V>)this.constructor.construct();
/*     */       
/* 168 */       if (peek == JsonToken.BEGIN_ARRAY) {
/* 169 */         in.beginArray();
/* 170 */         while (in.hasNext()) {
/* 171 */           in.beginArray();
/* 172 */           K key = (K)this.keyTypeAdapter.read(in);
/* 173 */           V value = (V)this.valueTypeAdapter.read(in);
/* 174 */           V replaced = map.put(key, value);
/* 175 */           if (replaced != null) {
/* 176 */             throw new JsonSyntaxException("duplicate key: " + key);
/*     */           }
/* 178 */           in.endArray();
/*     */         } 
/* 180 */         in.endArray();
/*     */       } else {
/* 182 */         in.beginObject();
/* 183 */         while (in.hasNext()) {
/* 184 */           JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
/* 185 */           K key = (K)this.keyTypeAdapter.read(in);
/* 186 */           V value = (V)this.valueTypeAdapter.read(in);
/* 187 */           V replaced = map.put(key, value);
/* 188 */           if (replaced != null) {
/* 189 */             throw new JsonSyntaxException("duplicate key: " + key);
/*     */           }
/*     */         } 
/* 192 */         in.endObject();
/*     */       } 
/* 194 */       return map;
/*     */     }
/*     */     public void write(JsonWriter out, Map<K, V> map) throws IOException {
/*     */       int i;
/* 198 */       if (map == null) {
/* 199 */         out.nullValue();
/*     */         
/*     */         return;
/*     */       } 
/* 203 */       if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
/* 204 */         out.beginObject();
/* 205 */         for (Map.Entry<K, V> entry : map.entrySet()) {
/* 206 */           out.name(String.valueOf(entry.getKey()));
/* 207 */           this.valueTypeAdapter.write(out, entry.getValue());
/*     */         } 
/* 209 */         out.endObject();
/*     */         
/*     */         return;
/*     */       } 
/* 213 */       boolean hasComplexKeys = false;
/* 214 */       List<JsonElement> keys = new ArrayList<>(map.size());
/*     */       
/* 216 */       List<V> values = new ArrayList<>(map.size());
/* 217 */       for (Map.Entry<K, V> entry : map.entrySet()) {
/* 218 */         JsonElement keyElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
/* 219 */         keys.add(keyElement);
/* 220 */         values.add(entry.getValue());
/* 221 */         i = hasComplexKeys | ((keyElement.isJsonArray() || keyElement.isJsonObject()) ? 1 : 0);
/*     */       } 
/*     */       
/* 224 */       if (i != 0) {
/* 225 */         out.beginArray();
/* 226 */         for (int j = 0, size = keys.size(); j < size; j++) {
/* 227 */           out.beginArray();
/* 228 */           Streams.write(keys.get(j), out);
/* 229 */           this.valueTypeAdapter.write(out, values.get(j));
/* 230 */           out.endArray();
/*     */         } 
/* 232 */         out.endArray();
/*     */       } else {
/* 234 */         out.beginObject();
/* 235 */         for (int j = 0, size = keys.size(); j < size; j++) {
/* 236 */           JsonElement keyElement = keys.get(j);
/* 237 */           out.name(keyToString(keyElement));
/* 238 */           this.valueTypeAdapter.write(out, values.get(j));
/*     */         } 
/* 240 */         out.endObject();
/*     */       } 
/*     */     }
/*     */     
/*     */     private String keyToString(JsonElement keyElement) {
/* 245 */       if (keyElement.isJsonPrimitive()) {
/* 246 */         JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
/* 247 */         if (primitive.isNumber())
/* 248 */           return String.valueOf(primitive.getAsNumber()); 
/* 249 */         if (primitive.isBoolean())
/* 250 */           return Boolean.toString(primitive.getAsBoolean()); 
/* 251 */         if (primitive.isString()) {
/* 252 */           return primitive.getAsString();
/*     */         }
/* 254 */         throw new AssertionError();
/*     */       } 
/* 256 */       if (keyElement.isJsonNull()) {
/* 257 */         return "null";
/*     */       }
/* 259 */       throw new AssertionError();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\MapTypeAdapterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */