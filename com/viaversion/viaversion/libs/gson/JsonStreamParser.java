/*     */ package com.viaversion.viaversion.libs.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.internal.Streams;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public final class JsonStreamParser
/*     */   implements Iterator<JsonElement>
/*     */ {
/*     */   private final JsonReader parser;
/*     */   private final Object lock;
/*     */   
/*     */   public JsonStreamParser(String json) {
/*  60 */     this(new StringReader(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonStreamParser(Reader reader) {
/*  68 */     this.parser = new JsonReader(reader);
/*  69 */     this.parser.setLenient(true);
/*  70 */     this.lock = new Object();
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
/*     */   public JsonElement next() throws JsonParseException {
/*  84 */     if (!hasNext()) {
/*  85 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     try {
/*  89 */       return Streams.parse(this.parser);
/*  90 */     } catch (StackOverflowError e) {
/*  91 */       throw new JsonParseException("Failed parsing JSON source to Json", e);
/*  92 */     } catch (OutOfMemoryError e) {
/*  93 */       throw new JsonParseException("Failed parsing JSON source to Json", e);
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
/*     */   public boolean hasNext() {
/* 105 */     synchronized (this.lock) {
/*     */       
/* 107 */       return (this.parser.peek() != JsonToken.END_DOCUMENT);
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
/*     */   public void remove() {
/* 123 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\JsonStreamParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */