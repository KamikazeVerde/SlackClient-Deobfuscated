/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
/*     */ import com.viaversion.viaversion.libs.gson.JsonDeserializer;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSerializationContext;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSerializer;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.internal.;
/*     */ import com.viaversion.viaversion.libs.gson.internal.Streams;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
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
/*     */ public final class TreeTypeAdapter<T>
/*     */   extends SerializationDelegatingTypeAdapter<T>
/*     */ {
/*     */   private final JsonSerializer<T> serializer;
/*     */   private final JsonDeserializer<T> deserializer;
/*     */   final Gson gson;
/*     */   private final TypeToken<T> typeToken;
/*     */   private final TypeAdapterFactory skipPast;
/*  47 */   private final GsonContextImpl context = new GsonContextImpl();
/*     */   
/*     */   private final boolean nullSafe;
/*     */   
/*     */   private volatile TypeAdapter<T> delegate;
/*     */ 
/*     */   
/*     */   public TreeTypeAdapter(JsonSerializer<T> serializer, JsonDeserializer<T> deserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory skipPast, boolean nullSafe) {
/*  55 */     this.serializer = serializer;
/*  56 */     this.deserializer = deserializer;
/*  57 */     this.gson = gson;
/*  58 */     this.typeToken = typeToken;
/*  59 */     this.skipPast = skipPast;
/*  60 */     this.nullSafe = nullSafe;
/*     */   }
/*     */ 
/*     */   
/*     */   public TreeTypeAdapter(JsonSerializer<T> serializer, JsonDeserializer<T> deserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory skipPast) {
/*  65 */     this(serializer, deserializer, gson, typeToken, skipPast, true);
/*     */   }
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/*  69 */     if (this.deserializer == null) {
/*  70 */       return (T)delegate().read(in);
/*     */     }
/*  72 */     JsonElement value = Streams.parse(in);
/*  73 */     if (this.nullSafe && value.isJsonNull()) {
/*  74 */       return null;
/*     */     }
/*  76 */     return (T)this.deserializer.deserialize(value, this.typeToken.getType(), this.context);
/*     */   }
/*     */   
/*     */   public void write(JsonWriter out, T value) throws IOException {
/*  80 */     if (this.serializer == null) {
/*  81 */       delegate().write(out, value);
/*     */       return;
/*     */     } 
/*  84 */     if (this.nullSafe && value == null) {
/*  85 */       out.nullValue();
/*     */       return;
/*     */     } 
/*  88 */     JsonElement tree = this.serializer.serialize(value, this.typeToken.getType(), this.context);
/*  89 */     Streams.write(tree, out);
/*     */   }
/*     */ 
/*     */   
/*     */   private TypeAdapter<T> delegate() {
/*  94 */     TypeAdapter<T> d = this.delegate;
/*  95 */     return (d != null) ? 
/*  96 */       d : (
/*  97 */       this.delegate = this.gson.getDelegateAdapter(this.skipPast, this.typeToken));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeAdapter<T> getSerializationDelegate() {
/* 106 */     return (this.serializer != null) ? this : delegate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newFactory(TypeToken<?> exactType, Object typeAdapter) {
/* 113 */     return new SingleTypeFactory(typeAdapter, exactType, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> exactType, Object typeAdapter) {
/* 123 */     boolean matchRawType = (exactType.getType() == exactType.getRawType());
/* 124 */     return new SingleTypeFactory(typeAdapter, exactType, matchRawType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> hierarchyType, Object typeAdapter) {
/* 133 */     return new SingleTypeFactory(typeAdapter, null, false, hierarchyType);
/*     */   }
/*     */   
/*     */   private static final class SingleTypeFactory
/*     */     implements TypeAdapterFactory {
/*     */     private final TypeToken<?> exactType;
/*     */     private final boolean matchRawType;
/*     */     private final Class<?> hierarchyType;
/*     */     private final JsonSerializer<?> serializer;
/*     */     private final JsonDeserializer<?> deserializer;
/*     */     
/*     */     SingleTypeFactory(Object typeAdapter, TypeToken<?> exactType, boolean matchRawType, Class<?> hierarchyType) {
/* 145 */       this
/*     */         
/* 147 */         .serializer = (typeAdapter instanceof JsonSerializer) ? (JsonSerializer)typeAdapter : null;
/* 148 */       this
/*     */         
/* 150 */         .deserializer = (typeAdapter instanceof JsonDeserializer) ? (JsonDeserializer)typeAdapter : null;
/* 151 */       .Gson.Preconditions.checkArgument((this.serializer != null || this.deserializer != null));
/* 152 */       this.exactType = exactType;
/* 153 */       this.matchRawType = matchRawType;
/* 154 */       this.hierarchyType = hierarchyType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/* 162 */       boolean matches = (this.exactType != null) ? ((this.exactType.equals(type) || (this.matchRawType && this.exactType.getType() == type.getRawType()))) : this.hierarchyType.isAssignableFrom(type.getRawType());
/* 163 */       return matches ? 
/* 164 */         new TreeTypeAdapter<>((JsonSerializer)this.serializer, (JsonDeserializer)this.deserializer, gson, type, this) : 
/*     */         
/* 166 */         null;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
/*     */     public JsonElement serialize(Object src) {
/* 172 */       return TreeTypeAdapter.this.gson.toJsonTree(src);
/*     */     } private GsonContextImpl() {}
/*     */     public JsonElement serialize(Object src, Type typeOfSrc) {
/* 175 */       return TreeTypeAdapter.this.gson.toJsonTree(src, typeOfSrc);
/*     */     }
/*     */     
/*     */     public <R> R deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
/* 179 */       return (R)TreeTypeAdapter.this.gson.fromJson(json, typeOfT);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\TreeTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */