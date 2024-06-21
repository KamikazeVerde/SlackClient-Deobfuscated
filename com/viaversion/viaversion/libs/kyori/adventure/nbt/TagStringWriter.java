/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
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
/*     */ final class TagStringWriter
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final Appendable out;
/*     */   private final String indent;
/*     */   private int level;
/*     */   private boolean needsSeparator;
/*     */   private boolean legacy;
/*     */   
/*     */   TagStringWriter(Appendable out, String indent) {
/*  46 */     this.out = out;
/*  47 */     this.indent = indent;
/*     */   }
/*     */   
/*     */   public TagStringWriter legacy(boolean legacy) {
/*  51 */     this.legacy = legacy;
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TagStringWriter writeTag(BinaryTag tag) throws IOException {
/*  58 */     BinaryTagType<?> type = tag.type();
/*  59 */     if (type == BinaryTagTypes.COMPOUND)
/*  60 */       return writeCompound((CompoundBinaryTag)tag); 
/*  61 */     if (type == BinaryTagTypes.LIST)
/*  62 */       return writeList((ListBinaryTag)tag); 
/*  63 */     if (type == BinaryTagTypes.BYTE_ARRAY)
/*  64 */       return writeByteArray((ByteArrayBinaryTag)tag); 
/*  65 */     if (type == BinaryTagTypes.INT_ARRAY)
/*  66 */       return writeIntArray((IntArrayBinaryTag)tag); 
/*  67 */     if (type == BinaryTagTypes.LONG_ARRAY)
/*  68 */       return writeLongArray((LongArrayBinaryTag)tag); 
/*  69 */     if (type == BinaryTagTypes.STRING)
/*  70 */       return value(((StringBinaryTag)tag).value(), false); 
/*  71 */     if (type == BinaryTagTypes.BYTE)
/*  72 */       return value(Byte.toString(((ByteBinaryTag)tag).value()), 'b'); 
/*  73 */     if (type == BinaryTagTypes.SHORT)
/*  74 */       return value(Short.toString(((ShortBinaryTag)tag).value()), 's'); 
/*  75 */     if (type == BinaryTagTypes.INT)
/*  76 */       return value(Integer.toString(((IntBinaryTag)tag).value()), 'i'); 
/*  77 */     if (type == BinaryTagTypes.LONG)
/*  78 */       return value(Long.toString(((LongBinaryTag)tag).value()), Character.toUpperCase('l')); 
/*  79 */     if (type == BinaryTagTypes.FLOAT)
/*  80 */       return value(Float.toString(((FloatBinaryTag)tag).value()), 'f'); 
/*  81 */     if (type == BinaryTagTypes.DOUBLE) {
/*  82 */       return value(Double.toString(((DoubleBinaryTag)tag).value()), 'd');
/*     */     }
/*  84 */     throw new IOException("Unknown tag type: " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TagStringWriter writeCompound(CompoundBinaryTag tag) throws IOException {
/*  90 */     beginCompound();
/*  91 */     for (Map.Entry<String, ? extends BinaryTag> entry : (Iterable<Map.Entry<String, ? extends BinaryTag>>)tag) {
/*  92 */       key(entry.getKey());
/*  93 */       writeTag(entry.getValue());
/*     */     } 
/*  95 */     endCompound();
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeList(ListBinaryTag tag) throws IOException {
/* 100 */     beginList();
/* 101 */     int idx = 0;
/* 102 */     boolean lineBreaks = (prettyPrinting() && breakListElement(tag.elementType()));
/* 103 */     for (BinaryTag el : tag) {
/* 104 */       printAndResetSeparator(!lineBreaks);
/* 105 */       if (lineBreaks) {
/* 106 */         newlineIndent();
/*     */       }
/* 108 */       if (this.legacy) {
/* 109 */         this.out.append(String.valueOf(idx++));
/* 110 */         appendSeparator(':');
/*     */       } 
/*     */       
/* 113 */       writeTag(el);
/*     */     } 
/* 115 */     endList(lineBreaks);
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeByteArray(ByteArrayBinaryTag tag) throws IOException {
/* 120 */     if (this.legacy) {
/* 121 */       throw new IOException("Legacy Mojangson only supports integer arrays!");
/*     */     }
/* 123 */     beginArray('b');
/*     */     
/* 125 */     char byteArrayType = Character.toUpperCase('b');
/* 126 */     byte[] value = ByteArrayBinaryTagImpl.value(tag);
/* 127 */     for (int i = 0, length = value.length; i < length; i++) {
/* 128 */       printAndResetSeparator(true);
/* 129 */       value(Byte.toString(value[i]), byteArrayType);
/*     */     } 
/* 131 */     endArray();
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeIntArray(IntArrayBinaryTag tag) throws IOException {
/* 136 */     if (this.legacy) {
/* 137 */       beginList();
/*     */     } else {
/* 139 */       beginArray('i');
/*     */     } 
/*     */     
/* 142 */     int[] value = IntArrayBinaryTagImpl.value(tag);
/* 143 */     for (int i = 0, length = value.length; i < length; i++) {
/* 144 */       printAndResetSeparator(true);
/* 145 */       value(Integer.toString(value[i]), 'i');
/*     */     } 
/* 147 */     endArray();
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeLongArray(LongArrayBinaryTag tag) throws IOException {
/* 152 */     if (this.legacy) {
/* 153 */       throw new IOException("Legacy Mojangson only supports integer arrays!");
/*     */     }
/* 155 */     beginArray('l');
/*     */     
/* 157 */     long[] value = LongArrayBinaryTagImpl.value(tag);
/* 158 */     for (int i = 0, length = value.length; i < length; i++) {
/* 159 */       printAndResetSeparator(true);
/* 160 */       value(Long.toString(value[i]), 'l');
/*     */     } 
/* 162 */     endArray();
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TagStringWriter beginCompound() throws IOException {
/* 169 */     printAndResetSeparator(false);
/* 170 */     this.level++;
/* 171 */     this.out.append('{');
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter endCompound() throws IOException {
/* 176 */     this.level--;
/* 177 */     newlineIndent();
/* 178 */     this.out.append('}');
/* 179 */     this.needsSeparator = true;
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter key(String key) throws IOException {
/* 184 */     printAndResetSeparator(false);
/* 185 */     newlineIndent();
/* 186 */     writeMaybeQuoted(key, false);
/* 187 */     appendSeparator(':');
/* 188 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter value(String value, char valueType) throws IOException {
/* 192 */     if (valueType == '\000') {
/* 193 */       writeMaybeQuoted(value, true);
/*     */     } else {
/* 195 */       this.out.append(value);
/* 196 */       if (valueType != 'i') {
/* 197 */         this.out.append(valueType);
/*     */       }
/*     */     } 
/* 200 */     this.needsSeparator = true;
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter beginList() throws IOException {
/* 205 */     printAndResetSeparator(false);
/* 206 */     this.level++;
/* 207 */     this.out.append('[');
/* 208 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter endList(boolean lineBreak) throws IOException {
/* 212 */     this.level--;
/* 213 */     if (lineBreak) {
/* 214 */       newlineIndent();
/*     */     }
/* 216 */     this.out.append(']');
/* 217 */     this.needsSeparator = true;
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter beginArray(char type) throws IOException {
/* 222 */     (beginList()).out
/* 223 */       .append(Character.toUpperCase(type))
/* 224 */       .append(';');
/*     */     
/* 226 */     if (prettyPrinting()) {
/* 227 */       this.out.append(' ');
/*     */     }
/*     */     
/* 230 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter endArray() throws IOException {
/* 234 */     return endList(false);
/*     */   }
/*     */   
/*     */   private void writeMaybeQuoted(String content, boolean requireQuotes) throws IOException {
/* 238 */     if (!requireQuotes) {
/* 239 */       for (int i = 0; i < content.length(); i++) {
/* 240 */         if (!Tokens.id(content.charAt(i))) {
/* 241 */           requireQuotes = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 246 */     if (requireQuotes) {
/* 247 */       this.out.append('"');
/* 248 */       this.out.append(escape(content, '"'));
/* 249 */       this.out.append('"');
/*     */     } else {
/* 251 */       this.out.append(content);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String escape(String content, char quoteChar) {
/* 256 */     StringBuilder output = new StringBuilder(content.length());
/* 257 */     for (int i = 0; i < content.length(); i++) {
/* 258 */       char c = content.charAt(i);
/* 259 */       if (c == quoteChar || c == '\\') {
/* 260 */         output.append('\\');
/*     */       }
/* 262 */       output.append(c);
/*     */     } 
/* 264 */     return output.toString();
/*     */   }
/*     */   
/*     */   private void printAndResetSeparator(boolean pad) throws IOException {
/* 268 */     if (this.needsSeparator) {
/* 269 */       this.out.append(',');
/* 270 */       if (pad && prettyPrinting()) {
/* 271 */         this.out.append(' ');
/*     */       }
/* 273 */       this.needsSeparator = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean breakListElement(BinaryTagType<?> type) {
/* 281 */     return (type == BinaryTagTypes.COMPOUND || type == BinaryTagTypes.LIST || type == BinaryTagTypes.BYTE_ARRAY || type == BinaryTagTypes.INT_ARRAY || type == BinaryTagTypes.LONG_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean prettyPrinting() {
/* 289 */     return (this.indent.length() > 0);
/*     */   }
/*     */   
/*     */   private void newlineIndent() throws IOException {
/* 293 */     if (prettyPrinting()) {
/* 294 */       this.out.append(Tokens.NEWLINE);
/* 295 */       for (int i = 0; i < this.level; i++) {
/* 296 */         this.out.append(this.indent);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Appendable appendSeparator(char separatorChar) throws IOException {
/* 302 */     this.out.append(separatorChar);
/* 303 */     if (prettyPrinting()) {
/* 304 */       this.out.append(' ');
/*     */     }
/* 306 */     return this.out;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 311 */     if (this.level != 0) {
/* 312 */       throw new IllegalStateException("Document finished with unbalanced start and end objects");
/*     */     }
/* 314 */     if (this.out instanceof Writer)
/* 315 */       ((Writer)this.out).flush(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\TagStringWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */