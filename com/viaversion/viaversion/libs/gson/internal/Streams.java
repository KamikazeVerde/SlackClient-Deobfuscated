/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonIOException;
/*     */ import com.viaversion.viaversion.libs.gson.JsonNull;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
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
/*     */ 
/*     */ public final class Streams
/*     */ {
/*     */   private Streams() {
/*  38 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement parse(JsonReader reader) throws JsonParseException {
/*  45 */     boolean isEmpty = true;
/*     */     try {
/*  47 */       reader.peek();
/*  48 */       isEmpty = false;
/*  49 */       return (JsonElement)TypeAdapters.JSON_ELEMENT.read(reader);
/*  50 */     } catch (EOFException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  55 */       if (isEmpty) {
/*  56 */         return (JsonElement)JsonNull.INSTANCE;
/*     */       }
/*     */       
/*  59 */       throw new JsonSyntaxException(e);
/*  60 */     } catch (MalformedJsonException e) {
/*  61 */       throw new JsonSyntaxException(e);
/*  62 */     } catch (IOException e) {
/*  63 */       throw new JsonIOException(e);
/*  64 */     } catch (NumberFormatException e) {
/*  65 */       throw new JsonSyntaxException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(JsonElement element, JsonWriter writer) throws IOException {
/*  73 */     TypeAdapters.JSON_ELEMENT.write(writer, element);
/*     */   }
/*     */   
/*     */   public static Writer writerForAppendable(Appendable appendable) {
/*  77 */     return (appendable instanceof Writer) ? (Writer)appendable : new AppendableWriter(appendable);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class AppendableWriter
/*     */     extends Writer
/*     */   {
/*     */     private final Appendable appendable;
/*     */     
/*  86 */     private final CurrentWrite currentWrite = new CurrentWrite();
/*     */     
/*     */     AppendableWriter(Appendable appendable) {
/*  89 */       this.appendable = appendable;
/*     */     }
/*     */     
/*     */     public void write(char[] chars, int offset, int length) throws IOException {
/*  93 */       this.currentWrite.setChars(chars);
/*  94 */       this.appendable.append(this.currentWrite, offset, offset + length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() {}
/*     */ 
/*     */     
/*     */     public void close() {}
/*     */     
/*     */     public void write(int i) throws IOException {
/* 104 */       this.appendable.append((char)i);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(String str, int off, int len) throws IOException {
/* 109 */       Objects.requireNonNull(str);
/* 110 */       this.appendable.append(str, off, off + len);
/*     */     }
/*     */     
/*     */     public Writer append(CharSequence csq) throws IOException {
/* 114 */       this.appendable.append(csq);
/* 115 */       return this;
/*     */     }
/*     */     
/*     */     public Writer append(CharSequence csq, int start, int end) throws IOException {
/* 119 */       this.appendable.append(csq, start, end);
/* 120 */       return this;
/*     */     }
/*     */     
/*     */     private static class CurrentWrite
/*     */       implements CharSequence {
/*     */       private char[] chars;
/*     */       private String cachedString;
/*     */       
/*     */       private CurrentWrite() {}
/*     */       
/*     */       void setChars(char[] chars) {
/* 131 */         this.chars = chars;
/* 132 */         this.cachedString = null;
/*     */       }
/*     */       
/*     */       public int length() {
/* 136 */         return this.chars.length;
/*     */       }
/*     */       public char charAt(int i) {
/* 139 */         return this.chars[i];
/*     */       }
/*     */       public CharSequence subSequence(int start, int end) {
/* 142 */         return new String(this.chars, start, end - start);
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 147 */         if (this.cachedString == null) {
/* 148 */           this.cachedString = new String(this.chars);
/*     */         }
/* 150 */         return this.cachedString;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\Streams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */