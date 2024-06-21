/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ final class TagStringReader
/*     */ {
/*     */   private static final int MAX_DEPTH = 512;
/*  33 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*  34 */   private static final int[] EMPTY_INT_ARRAY = new int[0];
/*  35 */   private static final long[] EMPTY_LONG_ARRAY = new long[0];
/*     */   
/*     */   private final CharBuffer buffer;
/*     */   private boolean acceptLegacy;
/*     */   private int depth;
/*     */   
/*     */   TagStringReader(CharBuffer buffer) {
/*  42 */     this.buffer = buffer;
/*     */   }
/*     */   
/*     */   public CompoundBinaryTag compound() throws StringTagParseException {
/*  46 */     this.buffer.expect('{');
/*  47 */     if (this.buffer.takeIf('}')) {
/*  48 */       return CompoundBinaryTag.empty();
/*     */     }
/*     */     
/*  51 */     CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
/*  52 */     while (this.buffer.hasMore()) {
/*  53 */       builder.put(key(), tag());
/*  54 */       if (separatorOrCompleteWith('}')) {
/*  55 */         return builder.build();
/*     */       }
/*     */     } 
/*  58 */     throw this.buffer.makeError("Unterminated compound tag!");
/*     */   }
/*     */   
/*     */   public ListBinaryTag list() throws StringTagParseException {
/*  62 */     ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.builder();
/*  63 */     this.buffer.expect('[');
/*  64 */     boolean prefixedIndex = (this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':');
/*  65 */     if (!prefixedIndex && this.buffer.takeIf(']')) {
/*  66 */       return ListBinaryTag.empty();
/*     */     }
/*  68 */     while (this.buffer.hasMore()) {
/*  69 */       if (prefixedIndex) {
/*  70 */         this.buffer.takeUntil(':');
/*     */       }
/*     */       
/*  73 */       BinaryTag next = tag();
/*     */       
/*  75 */       builder.add(next);
/*  76 */       if (separatorOrCompleteWith(']')) {
/*  77 */         return builder.build();
/*     */       }
/*     */     } 
/*  80 */     throw this.buffer.makeError("Reached end of file without end of list tag!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryTag array(char elementType) throws StringTagParseException {
/*  91 */     this.buffer.expect('[')
/*  92 */       .expect(elementType)
/*  93 */       .expect(';');
/*     */     
/*  95 */     elementType = Character.toLowerCase(elementType);
/*  96 */     if (elementType == 'b')
/*  97 */       return ByteArrayBinaryTag.of(byteArray()); 
/*  98 */     if (elementType == 'i')
/*  99 */       return IntArrayBinaryTag.of(intArray()); 
/* 100 */     if (elementType == 'l') {
/* 101 */       return LongArrayBinaryTag.of(longArray());
/*     */     }
/* 103 */     throw this.buffer.makeError("Type " + elementType + " is not a valid element type in an array!");
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] byteArray() throws StringTagParseException {
/* 108 */     if (this.buffer.takeIf(']')) {
/* 109 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 112 */     List<Byte> bytes = new ArrayList<>();
/* 113 */     while (this.buffer.hasMore()) {
/* 114 */       CharSequence value = this.buffer.skipWhitespace().takeUntil('b');
/*     */       try {
/* 116 */         bytes.add(Byte.valueOf(value.toString()));
/* 117 */       } catch (NumberFormatException ex) {
/* 118 */         throw this.buffer.makeError("All elements of a byte array must be bytes!");
/*     */       } 
/*     */       
/* 121 */       if (separatorOrCompleteWith(']')) {
/* 122 */         byte[] result = new byte[bytes.size()];
/* 123 */         for (int i = 0; i < bytes.size(); i++) {
/* 124 */           result[i] = ((Byte)bytes.get(i)).byteValue();
/*     */         }
/* 126 */         return result;
/*     */       } 
/*     */     } 
/* 129 */     throw this.buffer.makeError("Reached end of document without array close");
/*     */   }
/*     */   
/*     */   private int[] intArray() throws StringTagParseException {
/* 133 */     if (this.buffer.takeIf(']')) {
/* 134 */       return EMPTY_INT_ARRAY;
/*     */     }
/*     */     
/* 137 */     IntStream.Builder builder = IntStream.builder();
/* 138 */     while (this.buffer.hasMore()) {
/* 139 */       BinaryTag value = tag();
/* 140 */       if (!(value instanceof IntBinaryTag)) {
/* 141 */         throw this.buffer.makeError("All elements of an int array must be ints!");
/*     */       }
/* 143 */       builder.add(((IntBinaryTag)value).intValue());
/* 144 */       if (separatorOrCompleteWith(']')) {
/* 145 */         return builder.build().toArray();
/*     */       }
/*     */     } 
/* 148 */     throw this.buffer.makeError("Reached end of document without array close");
/*     */   }
/*     */   
/*     */   private long[] longArray() throws StringTagParseException {
/* 152 */     if (this.buffer.takeIf(']')) {
/* 153 */       return EMPTY_LONG_ARRAY;
/*     */     }
/*     */     
/* 156 */     LongStream.Builder longs = LongStream.builder();
/* 157 */     while (this.buffer.hasMore()) {
/* 158 */       CharSequence value = this.buffer.skipWhitespace().takeUntil('l');
/*     */       try {
/* 160 */         longs.add(Long.parseLong(value.toString()));
/* 161 */       } catch (NumberFormatException ex) {
/* 162 */         throw this.buffer.makeError("All elements of a long array must be longs!");
/*     */       } 
/*     */       
/* 165 */       if (separatorOrCompleteWith(']')) {
/* 166 */         return longs.build().toArray();
/*     */       }
/*     */     } 
/* 169 */     throw this.buffer.makeError("Reached end of document without array close");
/*     */   }
/*     */   
/*     */   public String key() throws StringTagParseException {
/* 173 */     this.buffer.skipWhitespace();
/* 174 */     char starChar = this.buffer.peek();
/*     */     try {
/* 176 */       if (starChar == '\'' || starChar == '"') {
/* 177 */         return unescape(this.buffer.takeUntil(this.buffer.take()).toString());
/*     */       }
/*     */       
/* 180 */       StringBuilder builder = new StringBuilder();
/* 181 */       while (this.buffer.hasMore()) {
/* 182 */         char peek = this.buffer.peek();
/* 183 */         if (!Tokens.id(peek)) {
/* 184 */           if (this.acceptLegacy) {
/*     */             
/* 186 */             if (peek == '\\') {
/* 187 */               this.buffer.take(); continue;
/*     */             } 
/* 189 */             if (peek != ':') {
/* 190 */               builder.append(this.buffer.take());
/*     */               continue;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         } 
/* 196 */         builder.append(this.buffer.take());
/*     */       } 
/* 198 */       return builder.toString();
/*     */     } finally {
/* 200 */       this.buffer.expect(':');
/*     */     } 
/*     */   }
/*     */   
/*     */   public BinaryTag tag() throws StringTagParseException {
/* 205 */     if (this.depth++ > 512)
/* 206 */       throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");  try {
/*     */       CompoundBinaryTag compoundBinaryTag;
/*     */       ListBinaryTag listBinaryTag;
/* 209 */       char startToken = this.buffer.skipWhitespace().peek();
/* 210 */       switch (startToken) {
/*     */         case '{':
/* 212 */           return compound();
/*     */ 
/*     */         
/*     */         case '[':
/* 216 */           if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
/* 217 */             return array(this.buffer.peek(1));
/*     */           }
/* 219 */           return list();
/*     */ 
/*     */         
/*     */         case '"':
/*     */         case '\'':
/* 224 */           this.buffer.advance();
/* 225 */           return StringBinaryTag.of(unescape(this.buffer.takeUntil(startToken).toString()));
/*     */       } 
/* 227 */       return scalar();
/*     */     } finally {
/*     */       
/* 230 */       this.depth--;
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
/*     */   private BinaryTag scalar() {
/* 242 */     StringBuilder builder = new StringBuilder();
/* 243 */     int noLongerNumericAt = -1;
/* 244 */     while (this.buffer.hasMore()) {
/* 245 */       char current = this.buffer.peek();
/* 246 */       if (current == '\\') {
/* 247 */         this.buffer.advance();
/* 248 */         current = this.buffer.take();
/* 249 */       } else if (Tokens.id(current)) {
/* 250 */         this.buffer.advance();
/*     */       } else {
/*     */         break;
/*     */       } 
/* 254 */       builder.append(current);
/* 255 */       if (noLongerNumericAt == -1 && !Tokens.numeric(current)) {
/* 256 */         noLongerNumericAt = builder.length();
/*     */       }
/*     */     } 
/*     */     
/* 260 */     int length = builder.length();
/* 261 */     String built = builder.toString();
/* 262 */     if (noLongerNumericAt == length && length > 1) {
/* 263 */       char last = built.charAt(length - 1); try {
/*     */         float floatValue; double doubleValue;
/* 265 */         switch (Character.toLowerCase(last)) {
/*     */           case 'b':
/* 267 */             return ByteBinaryTag.of(Byte.parseByte(built.substring(0, length - 1)));
/*     */           case 's':
/* 269 */             return ShortBinaryTag.of(Short.parseShort(built.substring(0, length - 1)));
/*     */           case 'i':
/* 271 */             return IntBinaryTag.of(Integer.parseInt(built.substring(0, length - 1)));
/*     */           case 'l':
/* 273 */             return LongBinaryTag.of(Long.parseLong(built.substring(0, length - 1)));
/*     */           case 'f':
/* 275 */             floatValue = Float.parseFloat(built.substring(0, length - 1));
/* 276 */             if (Float.isFinite(floatValue)) {
/* 277 */               return FloatBinaryTag.of(floatValue);
/*     */             }
/*     */             break;
/*     */           case 'd':
/* 281 */             doubleValue = Double.parseDouble(built.substring(0, length - 1));
/* 282 */             if (Double.isFinite(doubleValue)) {
/* 283 */               return DoubleBinaryTag.of(doubleValue);
/*     */             }
/*     */             break;
/*     */         } 
/* 287 */       } catch (NumberFormatException numberFormatException) {}
/*     */     
/*     */     }
/* 290 */     else if (noLongerNumericAt == -1) {
/*     */       try {
/* 292 */         return IntBinaryTag.of(Integer.parseInt(built));
/* 293 */       } catch (NumberFormatException ex) {
/* 294 */         if (built.indexOf('.') != -1) {
/*     */           try {
/* 296 */             return DoubleBinaryTag.of(Double.parseDouble(built));
/* 297 */           } catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (built.equalsIgnoreCase("true"))
/* 305 */       return ByteBinaryTag.ONE; 
/* 306 */     if (built.equalsIgnoreCase("false")) {
/* 307 */       return ByteBinaryTag.ZERO;
/*     */     }
/* 309 */     return StringBinaryTag.of(built);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean separatorOrCompleteWith(char endCharacter) throws StringTagParseException {
/* 314 */     if (this.buffer.takeIf(endCharacter)) {
/* 315 */       return true;
/*     */     }
/* 317 */     this.buffer.expect(',');
/* 318 */     return this.buffer.takeIf(endCharacter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String unescape(String withEscapes) {
/* 328 */     int escapeIdx = withEscapes.indexOf('\\');
/* 329 */     if (escapeIdx == -1) {
/* 330 */       return withEscapes;
/*     */     }
/* 332 */     int lastEscape = 0;
/* 333 */     StringBuilder output = new StringBuilder(withEscapes.length());
/*     */     do {
/* 335 */       output.append(withEscapes, lastEscape, escapeIdx);
/* 336 */       lastEscape = escapeIdx + 1;
/* 337 */     } while ((escapeIdx = withEscapes.indexOf('\\', lastEscape + 1)) != -1);
/* 338 */     output.append(withEscapes.substring(lastEscape));
/* 339 */     return output.toString();
/*     */   }
/*     */   
/*     */   public void legacy(boolean acceptLegacy) {
/* 343 */     this.acceptLegacy = acceptLegacy;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\TagStringReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */