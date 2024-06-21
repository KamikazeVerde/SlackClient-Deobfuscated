/*     */ package com.viaversion.viaversion.api.minecraft.nbt;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
/*     */   private int level;
/*     */   private boolean needsSeparator;
/*     */   
/*     */   public TagStringWriter(Appendable out) {
/*  62 */     this.out = out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TagStringWriter writeTag(Tag tag) throws IOException {
/*  68 */     if (tag instanceof CompoundTag)
/*  69 */       return writeCompound((CompoundTag)tag); 
/*  70 */     if (tag instanceof ListTag)
/*  71 */       return writeList((ListTag)tag); 
/*  72 */     if (tag instanceof ByteArrayTag)
/*  73 */       return writeByteArray((ByteArrayTag)tag); 
/*  74 */     if (tag instanceof IntArrayTag)
/*  75 */       return writeIntArray((IntArrayTag)tag); 
/*  76 */     if (tag instanceof LongArrayTag)
/*  77 */       return writeLongArray((LongArrayTag)tag); 
/*  78 */     if (tag instanceof StringTag)
/*  79 */       return value(((StringTag)tag).getValue(), false); 
/*  80 */     if (tag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag)
/*  81 */       return value(Byte.toString(((NumberTag)tag).asByte()), 'b'); 
/*  82 */     if (tag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag)
/*  83 */       return value(Short.toString(((NumberTag)tag).asShort()), 's'); 
/*  84 */     if (tag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag)
/*  85 */       return value(Integer.toString(((NumberTag)tag).asInt()), 'i'); 
/*  86 */     if (tag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag)
/*  87 */       return value(Long.toString(((NumberTag)tag).asLong()), Character.toUpperCase('l')); 
/*  88 */     if (tag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag)
/*  89 */       return value(Float.toString(((NumberTag)tag).asFloat()), 'f'); 
/*  90 */     if (tag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag) {
/*  91 */       return value(Double.toString(((NumberTag)tag).asDouble()), 'd');
/*     */     }
/*  93 */     throw new IOException("Unknown tag type: " + tag.getClass().getSimpleName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TagStringWriter writeCompound(CompoundTag tag) throws IOException {
/*  99 */     beginCompound();
/* 100 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)tag.entrySet()) {
/* 101 */       key(entry.getKey());
/* 102 */       writeTag(entry.getValue());
/*     */     } 
/* 104 */     endCompound();
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeList(ListTag tag) throws IOException {
/* 109 */     beginList();
/* 110 */     for (Tag el : tag) {
/* 111 */       printAndResetSeparator();
/* 112 */       writeTag(el);
/*     */     } 
/* 114 */     endList();
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeByteArray(ByteArrayTag tag) throws IOException {
/* 119 */     beginArray('b');
/*     */     
/* 121 */     byte[] value = tag.getValue();
/* 122 */     for (int i = 0, length = value.length; i < length; i++) {
/* 123 */       printAndResetSeparator();
/* 124 */       value(Byte.toString(value[i]), 'b');
/*     */     } 
/* 126 */     endArray();
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeIntArray(IntArrayTag tag) throws IOException {
/* 131 */     beginArray('i');
/*     */     
/* 133 */     int[] value = tag.getValue();
/* 134 */     for (int i = 0, length = value.length; i < length; i++) {
/* 135 */       printAndResetSeparator();
/* 136 */       value(Integer.toString(value[i]), 'i');
/*     */     } 
/* 138 */     endArray();
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter writeLongArray(LongArrayTag tag) throws IOException {
/* 143 */     beginArray('l');
/*     */     
/* 145 */     long[] value = tag.getValue();
/* 146 */     for (int i = 0, length = value.length; i < length; i++) {
/* 147 */       printAndResetSeparator();
/* 148 */       value(Long.toString(value[i]), 'l');
/*     */     } 
/* 150 */     endArray();
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TagStringWriter beginCompound() throws IOException {
/* 157 */     printAndResetSeparator();
/* 158 */     this.level++;
/* 159 */     this.out.append('{');
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter endCompound() throws IOException {
/* 164 */     this.out.append('}');
/* 165 */     this.level--;
/* 166 */     this.needsSeparator = true;
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter key(String key) throws IOException {
/* 171 */     printAndResetSeparator();
/* 172 */     writeMaybeQuoted(key, false);
/* 173 */     this.out.append(':');
/* 174 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter value(String value, char valueType) throws IOException {
/* 178 */     if (valueType == '\000') {
/* 179 */       writeMaybeQuoted(value, true);
/*     */     } else {
/* 181 */       this.out.append(value);
/* 182 */       if (valueType != 'i') {
/* 183 */         this.out.append(valueType);
/*     */       }
/*     */     } 
/* 186 */     this.needsSeparator = true;
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter beginList() throws IOException {
/* 191 */     printAndResetSeparator();
/* 192 */     this.level++;
/* 193 */     this.out.append('[');
/* 194 */     return this;
/*     */   }
/*     */   
/*     */   public TagStringWriter endList() throws IOException {
/* 198 */     this.out.append(']');
/* 199 */     this.level--;
/* 200 */     this.needsSeparator = true;
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter beginArray(char type) throws IOException {
/* 205 */     (beginList()).out
/* 206 */       .append(type)
/* 207 */       .append(';');
/* 208 */     return this;
/*     */   }
/*     */   
/*     */   private TagStringWriter endArray() throws IOException {
/* 212 */     return endList();
/*     */   }
/*     */   
/*     */   private void writeMaybeQuoted(String content, boolean requireQuotes) throws IOException {
/* 216 */     if (!requireQuotes) {
/* 217 */       for (int i = 0; i < content.length(); i++) {
/* 218 */         if (!Tokens.id(content.charAt(i))) {
/* 219 */           requireQuotes = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 224 */     if (requireQuotes) {
/* 225 */       this.out.append('"');
/* 226 */       this.out.append(escape(content, '"'));
/* 227 */       this.out.append('"');
/*     */     } else {
/* 229 */       this.out.append(content);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String escape(String content, char quoteChar) {
/* 234 */     StringBuilder output = new StringBuilder(content.length());
/* 235 */     for (int i = 0; i < content.length(); i++) {
/* 236 */       char c = content.charAt(i);
/* 237 */       if (c == quoteChar || c == '\\') {
/* 238 */         output.append('\\');
/*     */       }
/* 240 */       output.append(c);
/*     */     } 
/* 242 */     return output.toString();
/*     */   }
/*     */   
/*     */   private void printAndResetSeparator() throws IOException {
/* 246 */     if (this.needsSeparator) {
/* 247 */       this.out.append(',');
/* 248 */       this.needsSeparator = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 254 */     if (this.level != 0) {
/* 255 */       throw new IllegalStateException("Document finished with unbalanced start and end objects");
/*     */     }
/* 257 */     if (this.out instanceof Writer)
/* 258 */       ((Writer)this.out).flush(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\nbt\TagStringWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */