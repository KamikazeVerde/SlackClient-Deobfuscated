/*     */ package com.viaversion.viaversion.libs.gson.stream;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class JsonWriter
/*     */   implements Closeable, Flushable
/*     */ {
/* 139 */   private static final Pattern VALID_JSON_NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][-+]?[0-9]+)?");
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
/* 154 */   private static final String[] REPLACEMENT_CHARS = new String[128]; static {
/* 155 */     for (int i = 0; i <= 31; i++) {
/* 156 */       REPLACEMENT_CHARS[i] = String.format("\\u%04x", new Object[] { Integer.valueOf(i) });
/*     */     } 
/* 158 */     REPLACEMENT_CHARS[34] = "\\\"";
/* 159 */     REPLACEMENT_CHARS[92] = "\\\\";
/* 160 */     REPLACEMENT_CHARS[9] = "\\t";
/* 161 */     REPLACEMENT_CHARS[8] = "\\b";
/* 162 */     REPLACEMENT_CHARS[10] = "\\n";
/* 163 */     REPLACEMENT_CHARS[13] = "\\r";
/* 164 */     REPLACEMENT_CHARS[12] = "\\f";
/* 165 */   } private static final String[] HTML_SAFE_REPLACEMENT_CHARS = (String[])REPLACEMENT_CHARS.clone(); static {
/* 166 */     HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
/* 167 */     HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
/* 168 */     HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
/* 169 */     HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
/* 170 */     HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
/*     */   }
/*     */ 
/*     */   
/*     */   private final Writer out;
/*     */   
/* 176 */   private int[] stack = new int[32];
/* 177 */   private int stackSize = 0; private String indent;
/*     */   public JsonWriter(Writer out) {
/* 179 */     push(6);
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
/* 191 */     this.separator = ":";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     this.serializeNulls = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     this.out = Objects.<Writer>requireNonNull(out, "out == null");
/*     */   }
/*     */ 
/*     */   
/*     */   private String separator;
/*     */   
/*     */   private boolean lenient;
/*     */   private boolean htmlSafe;
/*     */   private String deferredName;
/*     */   private boolean serializeNulls;
/*     */   
/*     */   public final void setIndent(String indent) {
/* 219 */     if (indent.length() == 0) {
/* 220 */       this.indent = null;
/* 221 */       this.separator = ":";
/*     */     } else {
/* 223 */       this.indent = indent;
/* 224 */       this.separator = ": ";
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
/*     */   public final void setLenient(boolean lenient) {
/* 239 */     this.lenient = lenient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLenient() {
/* 246 */     return this.lenient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setHtmlSafe(boolean htmlSafe) {
/* 257 */     this.htmlSafe = htmlSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isHtmlSafe() {
/* 265 */     return this.htmlSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSerializeNulls(boolean serializeNulls) {
/* 273 */     this.serializeNulls = serializeNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getSerializeNulls() {
/* 281 */     return this.serializeNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter beginArray() throws IOException {
/* 291 */     writeDeferredName();
/* 292 */     return open(1, '[');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter endArray() throws IOException {
/* 301 */     return close(1, 2, ']');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter beginObject() throws IOException {
/* 311 */     writeDeferredName();
/* 312 */     return open(3, '{');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter endObject() throws IOException {
/* 321 */     return close(3, 5, '}');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonWriter open(int empty, char openBracket) throws IOException {
/* 329 */     beforeValue();
/* 330 */     push(empty);
/* 331 */     this.out.write(openBracket);
/* 332 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonWriter close(int empty, int nonempty, char closeBracket) throws IOException {
/* 341 */     int context = peek();
/* 342 */     if (context != nonempty && context != empty) {
/* 343 */       throw new IllegalStateException("Nesting problem.");
/*     */     }
/* 345 */     if (this.deferredName != null) {
/* 346 */       throw new IllegalStateException("Dangling name: " + this.deferredName);
/*     */     }
/*     */     
/* 349 */     this.stackSize--;
/* 350 */     if (context == nonempty) {
/* 351 */       newline();
/*     */     }
/* 353 */     this.out.write(closeBracket);
/* 354 */     return this;
/*     */   }
/*     */   
/*     */   private void push(int newTop) {
/* 358 */     if (this.stackSize == this.stack.length) {
/* 359 */       this.stack = Arrays.copyOf(this.stack, this.stackSize * 2);
/*     */     }
/* 361 */     this.stack[this.stackSize++] = newTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int peek() {
/* 368 */     if (this.stackSize == 0) {
/* 369 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 371 */     return this.stack[this.stackSize - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void replaceTop(int topOfStack) {
/* 378 */     this.stack[this.stackSize - 1] = topOfStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter name(String name) throws IOException {
/* 388 */     Objects.requireNonNull(name, "name == null");
/* 389 */     if (this.deferredName != null) {
/* 390 */       throw new IllegalStateException();
/*     */     }
/* 392 */     if (this.stackSize == 0) {
/* 393 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 395 */     this.deferredName = name;
/* 396 */     return this;
/*     */   }
/*     */   
/*     */   private void writeDeferredName() throws IOException {
/* 400 */     if (this.deferredName != null) {
/* 401 */       beforeName();
/* 402 */       string(this.deferredName);
/* 403 */       this.deferredName = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(String value) throws IOException {
/* 414 */     if (value == null) {
/* 415 */       return nullValue();
/*     */     }
/* 417 */     writeDeferredName();
/* 418 */     beforeValue();
/* 419 */     string(value);
/* 420 */     return this;
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
/*     */   public JsonWriter jsonValue(String value) throws IOException {
/* 435 */     if (value == null) {
/* 436 */       return nullValue();
/*     */     }
/* 438 */     writeDeferredName();
/* 439 */     beforeValue();
/* 440 */     this.out.append(value);
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter nullValue() throws IOException {
/* 450 */     if (this.deferredName != null) {
/* 451 */       if (this.serializeNulls) {
/* 452 */         writeDeferredName();
/*     */       } else {
/* 454 */         this.deferredName = null;
/* 455 */         return this;
/*     */       } 
/*     */     }
/* 458 */     beforeValue();
/* 459 */     this.out.write("null");
/* 460 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(boolean value) throws IOException {
/* 469 */     writeDeferredName();
/* 470 */     beforeValue();
/* 471 */     this.out.write(value ? "true" : "false");
/* 472 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(Boolean value) throws IOException {
/* 482 */     if (value == null) {
/* 483 */       return nullValue();
/*     */     }
/* 485 */     writeDeferredName();
/* 486 */     beforeValue();
/* 487 */     this.out.write(value.booleanValue() ? "true" : "false");
/* 488 */     return this;
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
/*     */   public JsonWriter value(float value) throws IOException {
/* 503 */     writeDeferredName();
/* 504 */     if (!this.lenient && (Float.isNaN(value) || Float.isInfinite(value))) {
/* 505 */       throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
/*     */     }
/* 507 */     beforeValue();
/* 508 */     this.out.append(Float.toString(value));
/* 509 */     return this;
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
/*     */   public JsonWriter value(double value) throws IOException {
/* 522 */     writeDeferredName();
/* 523 */     if (!this.lenient && (Double.isNaN(value) || Double.isInfinite(value))) {
/* 524 */       throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
/*     */     }
/* 526 */     beforeValue();
/* 527 */     this.out.append(Double.toString(value));
/* 528 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter value(long value) throws IOException {
/* 537 */     writeDeferredName();
/* 538 */     beforeValue();
/* 539 */     this.out.write(Long.toString(value));
/* 540 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTrustedNumberType(Class<? extends Number> c) {
/* 550 */     return (c == Integer.class || c == Long.class || c == Double.class || c == Float.class || c == Byte.class || c == Short.class || c == BigDecimal.class || c == BigInteger.class || c == AtomicInteger.class || c == AtomicLong.class);
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
/*     */   public JsonWriter value(Number value) throws IOException {
/* 566 */     if (value == null) {
/* 567 */       return nullValue();
/*     */     }
/*     */     
/* 570 */     writeDeferredName();
/* 571 */     String string = value.toString();
/* 572 */     if (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN")) {
/* 573 */       if (!this.lenient) {
/* 574 */         throw new IllegalArgumentException("Numeric values must be finite, but was " + string);
/*     */       }
/*     */     } else {
/* 577 */       Class<? extends Number> numberClass = (Class)value.getClass();
/*     */       
/* 579 */       if (!isTrustedNumberType(numberClass) && !VALID_JSON_NUMBER_PATTERN.matcher(string).matches()) {
/* 580 */         throw new IllegalArgumentException("String created by " + numberClass + " is not a valid JSON number: " + string);
/*     */       }
/*     */     } 
/*     */     
/* 584 */     beforeValue();
/* 585 */     this.out.append(string);
/* 586 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 594 */     if (this.stackSize == 0) {
/* 595 */       throw new IllegalStateException("JsonWriter is closed.");
/*     */     }
/* 597 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 606 */     this.out.close();
/*     */     
/* 608 */     int size = this.stackSize;
/* 609 */     if (size > 1 || (size == 1 && this.stack[size - 1] != 7)) {
/* 610 */       throw new IOException("Incomplete document");
/*     */     }
/* 612 */     this.stackSize = 0;
/*     */   }
/*     */   
/*     */   private void string(String value) throws IOException {
/* 616 */     String[] replacements = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
/* 617 */     this.out.write(34);
/* 618 */     int last = 0;
/* 619 */     int length = value.length();
/* 620 */     for (int i = 0; i < length; i++) {
/* 621 */       String replacement; char c = value.charAt(i);
/*     */       
/* 623 */       if (c < '') {
/* 624 */         replacement = replacements[c];
/* 625 */         if (replacement == null) {
/*     */           continue;
/*     */         }
/* 628 */       } else if (c == ' ') {
/* 629 */         replacement = "\\u2028";
/* 630 */       } else if (c == ' ') {
/* 631 */         replacement = "\\u2029";
/*     */       } else {
/*     */         continue;
/*     */       } 
/* 635 */       if (last < i) {
/* 636 */         this.out.write(value, last, i - last);
/*     */       }
/* 638 */       this.out.write(replacement);
/* 639 */       last = i + 1; continue;
/*     */     } 
/* 641 */     if (last < length) {
/* 642 */       this.out.write(value, last, length - last);
/*     */     }
/* 644 */     this.out.write(34);
/*     */   }
/*     */   
/*     */   private void newline() throws IOException {
/* 648 */     if (this.indent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 652 */     this.out.write(10);
/* 653 */     for (int i = 1, size = this.stackSize; i < size; i++) {
/* 654 */       this.out.write(this.indent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beforeName() throws IOException {
/* 663 */     int context = peek();
/* 664 */     if (context == 5) {
/* 665 */       this.out.write(44);
/* 666 */     } else if (context != 3) {
/* 667 */       throw new IllegalStateException("Nesting problem.");
/*     */     } 
/* 669 */     newline();
/* 670 */     replaceTop(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beforeValue() throws IOException {
/* 680 */     switch (peek()) {
/*     */       case 7:
/* 682 */         if (!this.lenient) {
/* 683 */           throw new IllegalStateException("JSON must have only one top-level value.");
/*     */         }
/*     */ 
/*     */       
/*     */       case 6:
/* 688 */         replaceTop(7);
/*     */         return;
/*     */       
/*     */       case 1:
/* 692 */         replaceTop(2);
/* 693 */         newline();
/*     */         return;
/*     */       
/*     */       case 2:
/* 697 */         this.out.append(',');
/* 698 */         newline();
/*     */         return;
/*     */       
/*     */       case 4:
/* 702 */         this.out.append(this.separator);
/* 703 */         replaceTop(5);
/*     */         return;
/*     */     } 
/*     */     
/* 707 */     throw new IllegalStateException("Nesting problem.");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\stream\JsonWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */