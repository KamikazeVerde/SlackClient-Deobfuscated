/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.ToNumberPolicy;
/*     */ import com.viaversion.viaversion.libs.gson.ToNumberStrategy;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
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
/*     */ public final class ObjectTypeAdapter
/*     */   extends TypeAdapter<Object>
/*     */ {
/*  44 */   private static final TypeAdapterFactory DOUBLE_FACTORY = newFactory((ToNumberStrategy)ToNumberPolicy.DOUBLE);
/*     */   
/*     */   private final Gson gson;
/*     */   private final ToNumberStrategy toNumberStrategy;
/*     */   
/*     */   private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
/*  50 */     this.gson = gson;
/*  51 */     this.toNumberStrategy = toNumberStrategy;
/*     */   }
/*     */   
/*     */   private static TypeAdapterFactory newFactory(final ToNumberStrategy toNumberStrategy) {
/*  55 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/*  58 */           if (type.getRawType() == Object.class) {
/*  59 */             return new ObjectTypeAdapter(gson, toNumberStrategy);
/*     */           }
/*  61 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
/*  67 */     if (toNumberStrategy == ToNumberPolicy.DOUBLE) {
/*  68 */       return DOUBLE_FACTORY;
/*     */     }
/*  70 */     return newFactory(toNumberStrategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException {
/*  79 */     switch (peeked) {
/*     */       case BEGIN_ARRAY:
/*  81 */         in.beginArray();
/*  82 */         return new ArrayList();
/*     */       case BEGIN_OBJECT:
/*  84 */         in.beginObject();
/*  85 */         return new LinkedTreeMap();
/*     */     } 
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readTerminal(JsonReader in, JsonToken peeked) throws IOException {
/*  93 */     switch (peeked) {
/*     */       case STRING:
/*  95 */         return in.nextString();
/*     */       case NUMBER:
/*  97 */         return this.toNumberStrategy.readNumber(in);
/*     */       case BOOLEAN:
/*  99 */         return Boolean.valueOf(in.nextBoolean());
/*     */       case NULL:
/* 101 */         in.nextNull();
/* 102 */         return null;
/*     */     } 
/*     */     
/* 105 */     throw new IllegalStateException("Unexpected token: " + peeked);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read(JsonReader in) throws IOException {
/* 112 */     JsonToken peeked = in.peek();
/*     */     
/* 114 */     Object current = tryBeginNesting(in, peeked);
/* 115 */     if (current == null) {
/* 116 */       return readTerminal(in, peeked);
/*     */     }
/*     */     
/* 119 */     Deque<Object> stack = new ArrayDeque();
/*     */     
/*     */     while (true) {
/* 122 */       while (in.hasNext()) {
/* 123 */         String name = null;
/*     */         
/* 125 */         if (current instanceof Map) {
/* 126 */           name = in.nextName();
/*     */         }
/*     */         
/* 129 */         peeked = in.peek();
/* 130 */         Object value = tryBeginNesting(in, peeked);
/* 131 */         boolean isNesting = (value != null);
/*     */         
/* 133 */         if (value == null) {
/* 134 */           value = readTerminal(in, peeked);
/*     */         }
/*     */         
/* 137 */         if (current instanceof List) {
/*     */           
/* 139 */           List<Object> list = (List<Object>)current;
/* 140 */           list.add(value);
/*     */         } else {
/*     */           
/* 143 */           Map<String, Object> map = (Map<String, Object>)current;
/* 144 */           map.put(name, value);
/*     */         } 
/*     */         
/* 147 */         if (isNesting) {
/* 148 */           stack.addLast(current);
/* 149 */           current = value;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 154 */       if (current instanceof List) {
/* 155 */         in.endArray();
/*     */       } else {
/* 157 */         in.endObject();
/*     */       } 
/*     */       
/* 160 */       if (stack.isEmpty()) {
/* 161 */         return current;
/*     */       }
/*     */       
/* 164 */       current = stack.removeLast();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Object value) throws IOException {
/* 170 */     if (value == null) {
/* 171 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 176 */     TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
/* 177 */     if (typeAdapter instanceof ObjectTypeAdapter) {
/* 178 */       out.beginObject();
/* 179 */       out.endObject();
/*     */       
/*     */       return;
/*     */     } 
/* 183 */     typeAdapter.write(out, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\ObjectTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */