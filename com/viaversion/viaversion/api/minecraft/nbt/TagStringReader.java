/*     */ package com.viaversion.viaversion.api.minecraft.nbt;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.LongStream;
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
/*     */ final class TagStringReader
/*     */ {
/*     */   private static final int MAX_DEPTH = 512;
/*  51 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*  52 */   private static final int[] EMPTY_INT_ARRAY = new int[0];
/*  53 */   private static final long[] EMPTY_LONG_ARRAY = new long[0];
/*     */   
/*     */   private final CharBuffer buffer;
/*     */   private boolean acceptLegacy = true;
/*     */   private int depth;
/*     */   
/*     */   TagStringReader(CharBuffer buffer) {
/*  60 */     this.buffer = buffer;
/*     */   }
/*     */   
/*     */   public CompoundTag compound() throws StringTagParseException {
/*  64 */     this.buffer.expect('{');
/*  65 */     CompoundTag compoundTag = new CompoundTag();
/*  66 */     if (this.buffer.takeIf('}')) {
/*  67 */       return compoundTag;
/*     */     }
/*     */     
/*  70 */     while (this.buffer.hasMore()) {
/*  71 */       compoundTag.put(key(), tag());
/*  72 */       if (separatorOrCompleteWith('}')) {
/*  73 */         return compoundTag;
/*     */       }
/*     */     } 
/*  76 */     throw this.buffer.makeError("Unterminated compound tag!");
/*     */   }
/*     */   
/*     */   public ListTag list() throws StringTagParseException {
/*  80 */     ListTag listTag = new ListTag();
/*  81 */     this.buffer.expect('[');
/*  82 */     boolean prefixedIndex = (this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':');
/*  83 */     if (!prefixedIndex && this.buffer.takeIf(']')) {
/*  84 */       return listTag;
/*     */     }
/*  86 */     while (this.buffer.hasMore()) {
/*  87 */       if (prefixedIndex) {
/*  88 */         this.buffer.takeUntil(':');
/*     */       }
/*     */       
/*  91 */       Tag next = tag();
/*  92 */       listTag.add(next);
/*  93 */       if (separatorOrCompleteWith(']')) {
/*  94 */         return listTag;
/*     */       }
/*     */     } 
/*  97 */     throw this.buffer.makeError("Reached end of file without end of list tag!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag array(char elementType) throws StringTagParseException {
/* 106 */     this.buffer.expect('[')
/* 107 */       .expect(elementType)
/* 108 */       .expect(';');
/*     */     
/* 110 */     elementType = Character.toLowerCase(elementType);
/* 111 */     if (elementType == 'b')
/* 112 */       return (Tag)new ByteArrayTag(byteArray()); 
/* 113 */     if (elementType == 'i')
/* 114 */       return (Tag)new IntArrayTag(intArray()); 
/* 115 */     if (elementType == 'l') {
/* 116 */       return (Tag)new LongArrayTag(longArray());
/*     */     }
/* 118 */     throw this.buffer.makeError("Type " + elementType + " is not a valid element type in an array!");
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] byteArray() throws StringTagParseException {
/* 123 */     if (this.buffer.takeIf(']')) {
/* 124 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 127 */     IntArrayList intArrayList = new IntArrayList();
/* 128 */     while (this.buffer.hasMore()) {
/* 129 */       CharSequence value = this.buffer.skipWhitespace().takeUntil('b');
/*     */       try {
/* 131 */         intArrayList.add(Byte.parseByte(value.toString()));
/* 132 */       } catch (NumberFormatException ex) {
/* 133 */         throw this.buffer.makeError("All elements of a byte array must be bytes!");
/*     */       } 
/*     */       
/* 136 */       if (separatorOrCompleteWith(']')) {
/* 137 */         byte[] result = new byte[intArrayList.size()];
/* 138 */         for (int i = 0; i < intArrayList.size(); i++) {
/* 139 */           result[i] = (byte)intArrayList.getInt(i);
/*     */         }
/* 141 */         return result;
/*     */       } 
/*     */     } 
/* 144 */     throw this.buffer.makeError("Reached end of document without array close");
/*     */   }
/*     */   
/*     */   private int[] intArray() throws StringTagParseException {
/* 148 */     if (this.buffer.takeIf(']')) {
/* 149 */       return EMPTY_INT_ARRAY;
/*     */     }
/*     */     
/* 152 */     IntStream.Builder builder = IntStream.builder();
/* 153 */     while (this.buffer.hasMore()) {
/* 154 */       Tag value = tag();
/* 155 */       if (!(value instanceof IntTag)) {
/* 156 */         throw this.buffer.makeError("All elements of an int array must be ints!");
/*     */       }
/* 158 */       builder.add(((NumberTag)value).asInt());
/* 159 */       if (separatorOrCompleteWith(']')) {
/* 160 */         return builder.build().toArray();
/*     */       }
/*     */     } 
/* 163 */     throw this.buffer.makeError("Reached end of document without array close");
/*     */   }
/*     */   
/*     */   private long[] longArray() throws StringTagParseException {
/* 167 */     if (this.buffer.takeIf(']')) {
/* 168 */       return EMPTY_LONG_ARRAY;
/*     */     }
/*     */     
/* 171 */     LongStream.Builder longs = LongStream.builder();
/* 172 */     while (this.buffer.hasMore()) {
/* 173 */       CharSequence value = this.buffer.skipWhitespace().takeUntil('l');
/*     */       try {
/* 175 */         longs.add(Long.parseLong(value.toString()));
/* 176 */       } catch (NumberFormatException ex) {
/* 177 */         throw this.buffer.makeError("All elements of a long array must be longs!");
/*     */       } 
/*     */       
/* 180 */       if (separatorOrCompleteWith(']')) {
/* 181 */         return longs.build().toArray();
/*     */       }
/*     */     } 
/* 184 */     throw this.buffer.makeError("Reached end of document without array close");
/*     */   }
/*     */   
/*     */   public String key() throws StringTagParseException {
/* 188 */     this.buffer.skipWhitespace();
/* 189 */     char starChar = this.buffer.peek();
/*     */     try {
/* 191 */       if (starChar == '\'' || starChar == '"') {
/* 192 */         return unescape(this.buffer.takeUntil(this.buffer.take()).toString());
/*     */       }
/*     */       
/* 195 */       StringBuilder builder = new StringBuilder();
/* 196 */       while (this.buffer.hasMore()) {
/* 197 */         char peek = this.buffer.peek();
/* 198 */         if (!Tokens.id(peek)) {
/* 199 */           if (this.acceptLegacy) {
/*     */             
/* 201 */             if (peek == '\\') {
/* 202 */               this.buffer.take(); continue;
/*     */             } 
/* 204 */             if (peek != ':') {
/* 205 */               builder.append(this.buffer.take());
/*     */               continue;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         } 
/* 211 */         builder.append(this.buffer.take());
/*     */       } 
/* 213 */       return builder.toString();
/*     */     } finally {
/* 215 */       this.buffer.expect(':');
/*     */     } 
/*     */   }
/*     */   
/*     */   public Tag tag() throws StringTagParseException {
/* 220 */     if (this.depth++ > 512)
/* 221 */       throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");  try {
/*     */       CompoundTag compoundTag;
/*     */       ListTag listTag;
/* 224 */       char startToken = this.buffer.skipWhitespace().peek();
/* 225 */       switch (startToken) {
/*     */         case '{':
/* 227 */           return (Tag)compound();
/*     */         
/*     */         case '[':
/* 230 */           if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
/* 231 */             return array(this.buffer.peek(1));
/*     */           }
/* 233 */           return (Tag)list();
/*     */ 
/*     */         
/*     */         case '"':
/*     */         case '\'':
/* 238 */           this.buffer.advance();
/* 239 */           return (Tag)new StringTag(unescape(this.buffer.takeUntil(startToken).toString()));
/*     */       } 
/* 241 */       return scalar();
/*     */     } finally {
/*     */       
/* 244 */       this.depth--;
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
/*     */   private Tag scalar() {
/* 256 */     StringBuilder builder = new StringBuilder();
/* 257 */     int noLongerNumericAt = -1;
/* 258 */     while (this.buffer.hasMore()) {
/* 259 */       char current = this.buffer.peek();
/* 260 */       if (current == '\\') {
/* 261 */         this.buffer.advance();
/* 262 */         current = this.buffer.take();
/* 263 */       } else if (Tokens.id(current)) {
/* 264 */         this.buffer.advance();
/*     */       } else {
/*     */         break;
/*     */       } 
/* 268 */       builder.append(current);
/* 269 */       if (noLongerNumericAt == -1 && !Tokens.numeric(current)) {
/* 270 */         noLongerNumericAt = builder.length();
/*     */       }
/*     */     } 
/*     */     
/* 274 */     int length = builder.length();
/* 275 */     String built = builder.toString();
/* 276 */     if (noLongerNumericAt == length) {
/* 277 */       char last = built.charAt(length - 1); try {
/*     */         float floatValue; double doubleValue;
/* 279 */         switch (Character.toLowerCase(last)) {
/*     */           case 'b':
/* 281 */             return (Tag)new ByteTag(Byte.parseByte(built.substring(0, length - 1)));
/*     */           case 's':
/* 283 */             return (Tag)new ShortTag(Short.parseShort(built.substring(0, length - 1)));
/*     */           case 'i':
/* 285 */             return (Tag)new IntTag(Integer.parseInt(built.substring(0, length - 1)));
/*     */           case 'l':
/* 287 */             return (Tag)new LongTag(Long.parseLong(built.substring(0, length - 1)));
/*     */           case 'f':
/* 289 */             floatValue = Float.parseFloat(built.substring(0, length - 1));
/* 290 */             if (Float.isFinite(floatValue)) {
/* 291 */               return (Tag)new FloatTag(floatValue);
/*     */             }
/*     */             break;
/*     */           case 'd':
/* 295 */             doubleValue = Double.parseDouble(built.substring(0, length - 1));
/* 296 */             if (Double.isFinite(doubleValue)) {
/* 297 */               return (Tag)new DoubleTag(doubleValue);
/*     */             }
/*     */             break;
/*     */         } 
/* 301 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/* 303 */     else if (noLongerNumericAt == -1) {
/*     */       try {
/* 305 */         return (Tag)new IntTag(Integer.parseInt(built));
/* 306 */       } catch (NumberFormatException ex) {
/* 307 */         if (built.indexOf('.') != -1) {
/*     */           try {
/* 309 */             return (Tag)new DoubleTag(Double.parseDouble(built));
/* 310 */           } catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 317 */     if (built.equalsIgnoreCase("true"))
/* 318 */       return (Tag)new ByteTag((byte)1); 
/* 319 */     if (built.equalsIgnoreCase("false")) {
/* 320 */       return (Tag)new ByteTag((byte)0);
/*     */     }
/* 322 */     return (Tag)new StringTag(built);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean separatorOrCompleteWith(char endCharacter) throws StringTagParseException {
/* 327 */     if (this.buffer.takeIf(endCharacter)) {
/* 328 */       return true;
/*     */     }
/* 330 */     this.buffer.expect(',');
/* 331 */     return this.buffer.takeIf(endCharacter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String unescape(String withEscapes) {
/* 341 */     int escapeIdx = withEscapes.indexOf('\\');
/* 342 */     if (escapeIdx == -1) {
/* 343 */       return withEscapes;
/*     */     }
/* 345 */     int lastEscape = 0;
/* 346 */     StringBuilder output = new StringBuilder(withEscapes.length());
/*     */     do {
/* 348 */       output.append(withEscapes, lastEscape, escapeIdx);
/* 349 */       lastEscape = escapeIdx + 1;
/* 350 */     } while ((escapeIdx = withEscapes.indexOf('\\', lastEscape + 1)) != -1);
/* 351 */     output.append(withEscapes.substring(lastEscape));
/* 352 */     return output.toString();
/*     */   }
/*     */   
/*     */   public void legacy(boolean acceptLegacy) {
/* 356 */     this.acceptLegacy = acceptLegacy;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\nbt\TagStringReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */