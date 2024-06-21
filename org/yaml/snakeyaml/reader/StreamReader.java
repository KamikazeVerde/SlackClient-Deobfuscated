/*     */ package org.yaml.snakeyaml.reader;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Arrays;
/*     */ import org.yaml.snakeyaml.error.Mark;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.scanner.Constant;
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
/*     */ public class StreamReader
/*     */ {
/*     */   private String name;
/*     */   private final Reader stream;
/*     */   private int[] dataWindow;
/*     */   private int dataLength;
/*  45 */   private int pointer = 0;
/*     */   private boolean eof;
/*  47 */   private int index = 0;
/*  48 */   private int line = 0;
/*  49 */   private int column = 0;
/*     */   
/*     */   private final char[] buffer;
/*     */   
/*     */   private static final int BUFFER_SIZE = 1025;
/*     */   
/*     */   public StreamReader(String stream) {
/*  56 */     this(new StringReader(stream));
/*  57 */     this.name = "'string'";
/*     */   }
/*     */   
/*     */   public StreamReader(Reader reader) {
/*  61 */     if (reader == null) {
/*  62 */       throw new NullPointerException("Reader must be provided.");
/*     */     }
/*  64 */     this.name = "'reader'";
/*  65 */     this.dataWindow = new int[0];
/*  66 */     this.dataLength = 0;
/*  67 */     this.stream = reader;
/*  68 */     this.eof = false;
/*  69 */     this.buffer = new char[1025];
/*     */   }
/*     */   
/*     */   public static boolean isPrintable(String data) {
/*  73 */     int length = data.length();
/*  74 */     for (int offset = 0; offset < length; ) {
/*  75 */       int codePoint = data.codePointAt(offset);
/*     */       
/*  77 */       if (!isPrintable(codePoint)) {
/*  78 */         return false;
/*     */       }
/*     */       
/*  81 */       offset += Character.charCount(codePoint);
/*     */     } 
/*     */     
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isPrintable(int c) {
/*  88 */     return ((c >= 32 && c <= 126) || c == 9 || c == 10 || c == 13 || c == 133 || (c >= 160 && c <= 55295) || (c >= 57344 && c <= 65533) || (c >= 65536 && c <= 1114111));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Mark getMark() {
/*  94 */     return new Mark(this.name, this.index, this.line, this.column, this.dataWindow, this.pointer);
/*     */   }
/*     */   
/*     */   public void forward() {
/*  98 */     forward(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forward(int length) {
/* 108 */     for (int i = 0; i < length && ensureEnoughData(); i++) {
/* 109 */       int c = this.dataWindow[this.pointer++];
/* 110 */       this.index++;
/* 111 */       if (Constant.LINEBR.has(c) || (c == 13 && 
/* 112 */         ensureEnoughData() && this.dataWindow[this.pointer] != 10)) {
/* 113 */         this.line++;
/* 114 */         this.column = 0;
/* 115 */       } else if (c != 65279) {
/* 116 */         this.column++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int peek() {
/* 122 */     return ensureEnoughData() ? this.dataWindow[this.pointer] : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int peek(int index) {
/* 132 */     return ensureEnoughData(index) ? this.dataWindow[this.pointer + index] : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String prefix(int length) {
/* 142 */     if (length == 0)
/* 143 */       return ""; 
/* 144 */     if (ensureEnoughData(length)) {
/* 145 */       return new String(this.dataWindow, this.pointer, length);
/*     */     }
/* 147 */     return new String(this.dataWindow, this.pointer, Math.min(length, this.dataLength - this.pointer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String prefixForward(int length) {
/* 158 */     String prefix = prefix(length);
/* 159 */     this.pointer += length;
/* 160 */     this.index += length;
/*     */     
/* 162 */     this.column += length;
/* 163 */     return prefix;
/*     */   }
/*     */   
/*     */   private boolean ensureEnoughData() {
/* 167 */     return ensureEnoughData(0);
/*     */   }
/*     */   
/*     */   private boolean ensureEnoughData(int size) {
/* 171 */     if (!this.eof && this.pointer + size >= this.dataLength) {
/* 172 */       update();
/*     */     }
/* 174 */     return (this.pointer + size < this.dataLength);
/*     */   }
/*     */   
/*     */   private void update() {
/*     */     try {
/* 179 */       int read = this.stream.read(this.buffer, 0, 1024);
/* 180 */       if (read > 0) {
/* 181 */         int cpIndex = this.dataLength - this.pointer;
/* 182 */         this.dataWindow = Arrays.copyOfRange(this.dataWindow, this.pointer, this.dataLength + read);
/*     */         
/* 184 */         if (Character.isHighSurrogate(this.buffer[read - 1])) {
/* 185 */           if (this.stream.read(this.buffer, read, 1) == -1) {
/* 186 */             this.eof = true;
/*     */           } else {
/* 188 */             read++;
/*     */           } 
/*     */         }
/*     */         
/* 192 */         int nonPrintable = 32;
/* 193 */         for (int i = 0; i < read; cpIndex++) {
/* 194 */           int codePoint = Character.codePointAt(this.buffer, i);
/* 195 */           this.dataWindow[cpIndex] = codePoint;
/* 196 */           if (isPrintable(codePoint)) {
/* 197 */             i += Character.charCount(codePoint);
/*     */           } else {
/* 199 */             nonPrintable = codePoint;
/* 200 */             i = read;
/*     */           } 
/*     */         } 
/*     */         
/* 204 */         this.dataLength = cpIndex;
/* 205 */         this.pointer = 0;
/* 206 */         if (nonPrintable != 32) {
/* 207 */           throw new ReaderException(this.name, cpIndex - 1, nonPrintable, "special characters are not allowed");
/*     */         }
/*     */       } else {
/*     */         
/* 211 */         this.eof = true;
/*     */       } 
/* 213 */     } catch (IOException ioe) {
/* 214 */       throw new YAMLException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumn() {
/* 220 */     return this.column;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 227 */     return this.index;
/*     */   }
/*     */   
/*     */   public int getLine() {
/* 231 */     return this.line;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\reader\StreamReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */