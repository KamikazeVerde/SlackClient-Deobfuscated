/*     */ package com.viaversion.viaversion.libs.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.internal.Streams;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
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
/*     */ public final class JsonParser
/*     */ {
/*     */   public static JsonElement parseString(String json) throws JsonSyntaxException {
/*  51 */     return parseReader(new StringReader(json));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
/*     */     try {
/*  69 */       JsonReader jsonReader = new JsonReader(reader);
/*  70 */       JsonElement element = parseReader(jsonReader);
/*  71 */       if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
/*  72 */         throw new JsonSyntaxException("Did not consume the entire document.");
/*     */       }
/*  74 */       return element;
/*  75 */     } catch (MalformedJsonException e) {
/*  76 */       throw new JsonSyntaxException(e);
/*  77 */     } catch (IOException e) {
/*  78 */       throw new JsonIOException(e);
/*  79 */     } catch (NumberFormatException e) {
/*  80 */       throw new JsonSyntaxException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
/*  99 */     boolean lenient = reader.isLenient();
/* 100 */     reader.setLenient(true);
/*     */     try {
/* 102 */       return Streams.parse(reader);
/* 103 */     } catch (StackOverflowError e) {
/* 104 */       throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
/* 105 */     } catch (OutOfMemoryError e) {
/* 106 */       throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
/*     */     } finally {
/* 108 */       reader.setLenient(lenient);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonElement parse(String json) throws JsonSyntaxException {
/* 115 */     return parseString(json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
/* 121 */     return parseReader(json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
/* 127 */     return parseReader(json);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\JsonParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */