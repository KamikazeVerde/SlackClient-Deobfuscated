/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonNull;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public final class JsonTreeWriter
/*     */   extends JsonWriter
/*     */ {
/*  35 */   private static final Writer UNWRITABLE_WRITER = new Writer() {
/*     */       public void write(char[] buffer, int offset, int counter) {
/*  37 */         throw new AssertionError();
/*     */       }
/*     */       public void flush() {
/*  40 */         throw new AssertionError();
/*     */       }
/*     */       public void close() {
/*  43 */         throw new AssertionError();
/*     */       }
/*     */     };
/*     */   
/*  47 */   private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");
/*     */ 
/*     */   
/*  50 */   private final List<JsonElement> stack = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private String pendingName;
/*     */ 
/*     */   
/*  56 */   private JsonElement product = (JsonElement)JsonNull.INSTANCE;
/*     */   
/*     */   public JsonTreeWriter() {
/*  59 */     super(UNWRITABLE_WRITER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement get() {
/*  66 */     if (!this.stack.isEmpty()) {
/*  67 */       throw new IllegalStateException("Expected one JSON element but was " + this.stack);
/*     */     }
/*  69 */     return this.product;
/*     */   }
/*     */   
/*     */   private JsonElement peek() {
/*  73 */     return this.stack.get(this.stack.size() - 1);
/*     */   }
/*     */   
/*     */   private void put(JsonElement value) {
/*  77 */     if (this.pendingName != null) {
/*  78 */       if (!value.isJsonNull() || getSerializeNulls()) {
/*  79 */         JsonObject object = (JsonObject)peek();
/*  80 */         object.add(this.pendingName, value);
/*     */       } 
/*  82 */       this.pendingName = null;
/*  83 */     } else if (this.stack.isEmpty()) {
/*  84 */       this.product = value;
/*     */     } else {
/*  86 */       JsonElement element = peek();
/*  87 */       if (element instanceof JsonArray) {
/*  88 */         ((JsonArray)element).add(value);
/*     */       } else {
/*  90 */         throw new IllegalStateException();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public JsonWriter beginArray() throws IOException {
/*  96 */     JsonArray array = new JsonArray();
/*  97 */     put((JsonElement)array);
/*  98 */     this.stack.add(array);
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter endArray() throws IOException {
/* 103 */     if (this.stack.isEmpty() || this.pendingName != null) {
/* 104 */       throw new IllegalStateException();
/*     */     }
/* 106 */     JsonElement element = peek();
/* 107 */     if (element instanceof JsonArray) {
/* 108 */       this.stack.remove(this.stack.size() - 1);
/* 109 */       return this;
/*     */     } 
/* 111 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public JsonWriter beginObject() throws IOException {
/* 115 */     JsonObject object = new JsonObject();
/* 116 */     put((JsonElement)object);
/* 117 */     this.stack.add(object);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter endObject() throws IOException {
/* 122 */     if (this.stack.isEmpty() || this.pendingName != null) {
/* 123 */       throw new IllegalStateException();
/*     */     }
/* 125 */     JsonElement element = peek();
/* 126 */     if (element instanceof JsonObject) {
/* 127 */       this.stack.remove(this.stack.size() - 1);
/* 128 */       return this;
/*     */     } 
/* 130 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public JsonWriter name(String name) throws IOException {
/* 134 */     Objects.requireNonNull(name, "name == null");
/* 135 */     if (this.stack.isEmpty() || this.pendingName != null) {
/* 136 */       throw new IllegalStateException();
/*     */     }
/* 138 */     JsonElement element = peek();
/* 139 */     if (element instanceof JsonObject) {
/* 140 */       this.pendingName = name;
/* 141 */       return this;
/*     */     } 
/* 143 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public JsonWriter value(String value) throws IOException {
/* 147 */     if (value == null) {
/* 148 */       return nullValue();
/*     */     }
/* 150 */     put((JsonElement)new JsonPrimitive(value));
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter jsonValue(String value) throws IOException {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public JsonWriter nullValue() throws IOException {
/* 159 */     put((JsonElement)JsonNull.INSTANCE);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter value(boolean value) throws IOException {
/* 164 */     put((JsonElement)new JsonPrimitive(Boolean.valueOf(value)));
/* 165 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter value(Boolean value) throws IOException {
/* 169 */     if (value == null) {
/* 170 */       return nullValue();
/*     */     }
/* 172 */     put((JsonElement)new JsonPrimitive(value));
/* 173 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter value(float value) throws IOException {
/* 177 */     if (!isLenient() && (Float.isNaN(value) || Float.isInfinite(value))) {
/* 178 */       throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
/*     */     }
/* 180 */     put((JsonElement)new JsonPrimitive(Float.valueOf(value)));
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter value(double value) throws IOException {
/* 185 */     if (!isLenient() && (Double.isNaN(value) || Double.isInfinite(value))) {
/* 186 */       throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
/*     */     }
/* 188 */     put((JsonElement)new JsonPrimitive(Double.valueOf(value)));
/* 189 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter value(long value) throws IOException {
/* 193 */     put((JsonElement)new JsonPrimitive(Long.valueOf(value)));
/* 194 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriter value(Number value) throws IOException {
/* 198 */     if (value == null) {
/* 199 */       return nullValue();
/*     */     }
/*     */     
/* 202 */     if (!isLenient()) {
/* 203 */       double d = value.doubleValue();
/* 204 */       if (Double.isNaN(d) || Double.isInfinite(d)) {
/* 205 */         throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     put((JsonElement)new JsonPrimitive(value));
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {}
/*     */   
/*     */   public void close() throws IOException {
/* 217 */     if (!this.stack.isEmpty()) {
/* 218 */       throw new IOException("Incomplete document");
/*     */     }
/* 220 */     this.stack.add(SENTINEL_CLOSED);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\JsonTreeWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */