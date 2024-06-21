/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.internal.;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
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
/*     */ public final class ArrayTypeAdapter<E>
/*     */   extends TypeAdapter<Object>
/*     */ {
/*  37 */   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  39 */         Type type = typeToken.getType();
/*  40 */         if (!(type instanceof java.lang.reflect.GenericArrayType) && (!(type instanceof Class) || !((Class)type).isArray())) {
/*  41 */           return null;
/*     */         }
/*     */         
/*  44 */         Type componentType = .Gson.Types.getArrayComponentType(type);
/*  45 */         TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
/*     */ 
/*     */ 
/*     */         
/*  49 */         TypeAdapter<T> arrayAdapter = new ArrayTypeAdapter(gson, componentTypeAdapter, .Gson.Types.getRawType(componentType));
/*  50 */         return arrayAdapter;
/*     */       }
/*     */     };
/*     */   
/*     */   private final Class<E> componentType;
/*     */   private final TypeAdapter<E> componentTypeAdapter;
/*     */   
/*     */   public ArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
/*  58 */     this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, componentTypeAdapter, componentType);
/*     */     
/*  60 */     this.componentType = componentType;
/*     */   }
/*     */   
/*     */   public Object read(JsonReader in) throws IOException {
/*  64 */     if (in.peek() == JsonToken.NULL) {
/*  65 */       in.nextNull();
/*  66 */       return null;
/*     */     } 
/*     */     
/*  69 */     ArrayList<E> list = new ArrayList<>();
/*  70 */     in.beginArray();
/*  71 */     while (in.hasNext()) {
/*  72 */       E instance = (E)this.componentTypeAdapter.read(in);
/*  73 */       list.add(instance);
/*     */     } 
/*  75 */     in.endArray();
/*     */     
/*  77 */     int size = list.size();
/*     */     
/*  79 */     if (this.componentType.isPrimitive()) {
/*  80 */       Object object = Array.newInstance(this.componentType, size);
/*  81 */       for (int i = 0; i < size; i++) {
/*  82 */         Array.set(object, i, list.get(i));
/*     */       }
/*  84 */       return object;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     E[] array = (E[])Array.newInstance(this.componentType, size);
/*  90 */     return list.toArray(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Object array) throws IOException {
/*  95 */     if (array == null) {
/*  96 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/* 100 */     out.beginArray();
/* 101 */     for (int i = 0, length = Array.getLength(array); i < length; i++) {
/*     */       
/* 103 */       E value = (E)Array.get(array, i);
/* 104 */       this.componentTypeAdapter.write(out, value);
/*     */     } 
/* 106 */     out.endArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\ArrayTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */