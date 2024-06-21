/*      */ package com.viaversion.viaversion.libs.gson.stream;
/*      */ 
/*      */ import com.viaversion.viaversion.libs.gson.internal.JsonReaderInternalAccess;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
/*      */ import java.io.Closeable;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.util.Arrays;
/*      */ import java.util.Objects;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JsonReader
/*      */   implements Closeable
/*      */ {
/*      */   private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
/*      */   private static final int PEEKED_NONE = 0;
/*      */   private static final int PEEKED_BEGIN_OBJECT = 1;
/*      */   private static final int PEEKED_END_OBJECT = 2;
/*      */   private static final int PEEKED_BEGIN_ARRAY = 3;
/*      */   private static final int PEEKED_END_ARRAY = 4;
/*      */   private static final int PEEKED_TRUE = 5;
/*      */   private static final int PEEKED_FALSE = 6;
/*      */   private static final int PEEKED_NULL = 7;
/*      */   private static final int PEEKED_SINGLE_QUOTED = 8;
/*      */   private static final int PEEKED_DOUBLE_QUOTED = 9;
/*      */   private static final int PEEKED_UNQUOTED = 10;
/*      */   private static final int PEEKED_BUFFERED = 11;
/*      */   private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
/*      */   private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
/*      */   private static final int PEEKED_UNQUOTED_NAME = 14;
/*      */   private static final int PEEKED_LONG = 15;
/*      */   private static final int PEEKED_NUMBER = 16;
/*      */   private static final int PEEKED_EOF = 17;
/*      */   private static final int NUMBER_CHAR_NONE = 0;
/*      */   private static final int NUMBER_CHAR_SIGN = 1;
/*      */   private static final int NUMBER_CHAR_DIGIT = 2;
/*      */   private static final int NUMBER_CHAR_DECIMAL = 3;
/*      */   private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
/*      */   private static final int NUMBER_CHAR_EXP_E = 5;
/*      */   private static final int NUMBER_CHAR_EXP_SIGN = 6;
/*      */   private static final int NUMBER_CHAR_EXP_DIGIT = 7;
/*      */   private final Reader in;
/*      */   private boolean lenient = false;
/*      */   static final int BUFFER_SIZE = 1024;
/*  239 */   private final char[] buffer = new char[1024];
/*  240 */   private int pos = 0;
/*  241 */   private int limit = 0;
/*      */   
/*  243 */   private int lineNumber = 0;
/*  244 */   private int lineStart = 0;
/*      */   
/*  246 */   int peeked = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long peekedLong;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int peekedNumberLength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String peekedString;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  270 */   private int[] stack = new int[32];
/*  271 */   private int stackSize = 0; private String[] pathNames; private int[] pathIndices;
/*      */   public JsonReader(Reader in) {
/*  273 */     this.stack[this.stackSize++] = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  284 */     this.pathNames = new String[32];
/*  285 */     this.pathIndices = new int[32];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  291 */     this.in = Objects.<Reader>requireNonNull(in, "in == null");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setLenient(boolean lenient) {
/*  334 */     this.lenient = lenient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isLenient() {
/*  341 */     return this.lenient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginArray() throws IOException {
/*  349 */     int p = this.peeked;
/*  350 */     if (p == 0) {
/*  351 */       p = doPeek();
/*      */     }
/*  353 */     if (p == 3) {
/*  354 */       push(1);
/*  355 */       this.pathIndices[this.stackSize - 1] = 0;
/*  356 */       this.peeked = 0;
/*      */     } else {
/*  358 */       throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endArray() throws IOException {
/*  367 */     int p = this.peeked;
/*  368 */     if (p == 0) {
/*  369 */       p = doPeek();
/*      */     }
/*  371 */     if (p == 4) {
/*  372 */       this.stackSize--;
/*  373 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  374 */       this.peeked = 0;
/*      */     } else {
/*  376 */       throw new IllegalStateException("Expected END_ARRAY but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginObject() throws IOException {
/*  385 */     int p = this.peeked;
/*  386 */     if (p == 0) {
/*  387 */       p = doPeek();
/*      */     }
/*  389 */     if (p == 1) {
/*  390 */       push(3);
/*  391 */       this.peeked = 0;
/*      */     } else {
/*  393 */       throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endObject() throws IOException {
/*  402 */     int p = this.peeked;
/*  403 */     if (p == 0) {
/*  404 */       p = doPeek();
/*      */     }
/*  406 */     if (p == 2) {
/*  407 */       this.stackSize--;
/*  408 */       this.pathNames[this.stackSize] = null;
/*  409 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  410 */       this.peeked = 0;
/*      */     } else {
/*  412 */       throw new IllegalStateException("Expected END_OBJECT but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() throws IOException {
/*  420 */     int p = this.peeked;
/*  421 */     if (p == 0) {
/*  422 */       p = doPeek();
/*      */     }
/*  424 */     return (p != 2 && p != 4 && p != 17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonToken peek() throws IOException {
/*  431 */     int p = this.peeked;
/*  432 */     if (p == 0) {
/*  433 */       p = doPeek();
/*      */     }
/*      */     
/*  436 */     switch (p) {
/*      */       case 1:
/*  438 */         return JsonToken.BEGIN_OBJECT;
/*      */       case 2:
/*  440 */         return JsonToken.END_OBJECT;
/*      */       case 3:
/*  442 */         return JsonToken.BEGIN_ARRAY;
/*      */       case 4:
/*  444 */         return JsonToken.END_ARRAY;
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*  448 */         return JsonToken.NAME;
/*      */       case 5:
/*      */       case 6:
/*  451 */         return JsonToken.BOOLEAN;
/*      */       case 7:
/*  453 */         return JsonToken.NULL;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  458 */         return JsonToken.STRING;
/*      */       case 15:
/*      */       case 16:
/*  461 */         return JsonToken.NUMBER;
/*      */       case 17:
/*  463 */         return JsonToken.END_DOCUMENT;
/*      */     } 
/*  465 */     throw new AssertionError();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int doPeek() throws IOException {
/*  471 */     int peekStack = this.stack[this.stackSize - 1];
/*  472 */     if (peekStack == 1)
/*  473 */     { this.stack[this.stackSize - 1] = 2; }
/*  474 */     else if (peekStack == 2)
/*      */     
/*  476 */     { int i = nextNonWhitespace(true);
/*  477 */       switch (i) {
/*      */         case 93:
/*  479 */           return this.peeked = 4;
/*      */         case 59:
/*  481 */           checkLenient(); break;
/*      */         case 44:
/*      */           break;
/*      */         default:
/*  485 */           throw syntaxError("Unterminated array");
/*      */       }  }
/*  487 */     else { if (peekStack == 3 || peekStack == 5) {
/*  488 */         this.stack[this.stackSize - 1] = 4;
/*      */         
/*  490 */         if (peekStack == 5) {
/*  491 */           int j = nextNonWhitespace(true);
/*  492 */           switch (j) {
/*      */             case 125:
/*  494 */               return this.peeked = 2;
/*      */             case 59:
/*  496 */               checkLenient(); break;
/*      */             case 44:
/*      */               break;
/*      */             default:
/*  500 */               throw syntaxError("Unterminated object");
/*      */           } 
/*      */         } 
/*  503 */         int i = nextNonWhitespace(true);
/*  504 */         switch (i) {
/*      */           case 34:
/*  506 */             return this.peeked = 13;
/*      */           case 39:
/*  508 */             checkLenient();
/*  509 */             return this.peeked = 12;
/*      */           case 125:
/*  511 */             if (peekStack != 5) {
/*  512 */               return this.peeked = 2;
/*      */             }
/*  514 */             throw syntaxError("Expected name");
/*      */         } 
/*      */         
/*  517 */         checkLenient();
/*  518 */         this.pos--;
/*  519 */         if (isLiteral((char)i)) {
/*  520 */           return this.peeked = 14;
/*      */         }
/*  522 */         throw syntaxError("Expected name");
/*      */       } 
/*      */       
/*  525 */       if (peekStack == 4) {
/*  526 */         this.stack[this.stackSize - 1] = 5;
/*      */         
/*  528 */         int i = nextNonWhitespace(true);
/*  529 */         switch (i) {
/*      */           case 58:
/*      */             break;
/*      */           case 61:
/*  533 */             checkLenient();
/*  534 */             if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
/*  535 */               this.pos++;
/*      */             }
/*      */             break;
/*      */           default:
/*  539 */             throw syntaxError("Expected ':'");
/*      */         } 
/*  541 */       } else if (peekStack == 6) {
/*  542 */         if (this.lenient) {
/*  543 */           consumeNonExecutePrefix();
/*      */         }
/*  545 */         this.stack[this.stackSize - 1] = 7;
/*  546 */       } else if (peekStack == 7) {
/*  547 */         int i = nextNonWhitespace(false);
/*  548 */         if (i == -1) {
/*  549 */           return this.peeked = 17;
/*      */         }
/*  551 */         checkLenient();
/*  552 */         this.pos--;
/*      */       }
/*  554 */       else if (peekStack == 8) {
/*  555 */         throw new IllegalStateException("JsonReader is closed");
/*      */       }  }
/*      */     
/*  558 */     int c = nextNonWhitespace(true);
/*  559 */     switch (c) {
/*      */       case 93:
/*  561 */         if (peekStack == 1) {
/*  562 */           return this.peeked = 4;
/*      */         }
/*      */ 
/*      */       
/*      */       case 44:
/*      */       case 59:
/*  568 */         if (peekStack == 1 || peekStack == 2) {
/*  569 */           checkLenient();
/*  570 */           this.pos--;
/*  571 */           return this.peeked = 7;
/*      */         } 
/*  573 */         throw syntaxError("Unexpected value");
/*      */       
/*      */       case 39:
/*  576 */         checkLenient();
/*  577 */         return this.peeked = 8;
/*      */       case 34:
/*  579 */         return this.peeked = 9;
/*      */       case 91:
/*  581 */         return this.peeked = 3;
/*      */       case 123:
/*  583 */         return this.peeked = 1;
/*      */     } 
/*  585 */     this.pos--;
/*      */ 
/*      */     
/*  588 */     int result = peekKeyword();
/*  589 */     if (result != 0) {
/*  590 */       return result;
/*      */     }
/*      */     
/*  593 */     result = peekNumber();
/*  594 */     if (result != 0) {
/*  595 */       return result;
/*      */     }
/*      */     
/*  598 */     if (!isLiteral(this.buffer[this.pos])) {
/*  599 */       throw syntaxError("Expected value");
/*      */     }
/*      */     
/*  602 */     checkLenient();
/*  603 */     return this.peeked = 10;
/*      */   }
/*      */   private int peekKeyword() throws IOException {
/*      */     String keyword, keywordUpper;
/*      */     int peeking;
/*  608 */     char c = this.buffer[this.pos];
/*      */ 
/*      */ 
/*      */     
/*  612 */     if (c == 't' || c == 'T') {
/*  613 */       keyword = "true";
/*  614 */       keywordUpper = "TRUE";
/*  615 */       peeking = 5;
/*  616 */     } else if (c == 'f' || c == 'F') {
/*  617 */       keyword = "false";
/*  618 */       keywordUpper = "FALSE";
/*  619 */       peeking = 6;
/*  620 */     } else if (c == 'n' || c == 'N') {
/*  621 */       keyword = "null";
/*  622 */       keywordUpper = "NULL";
/*  623 */       peeking = 7;
/*      */     } else {
/*  625 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  629 */     int length = keyword.length();
/*  630 */     for (int i = 1; i < length; i++) {
/*  631 */       if (this.pos + i >= this.limit && !fillBuffer(i + 1)) {
/*  632 */         return 0;
/*      */       }
/*  634 */       c = this.buffer[this.pos + i];
/*  635 */       if (c != keyword.charAt(i) && c != keywordUpper.charAt(i)) {
/*  636 */         return 0;
/*      */       }
/*      */     } 
/*      */     
/*  640 */     if ((this.pos + length < this.limit || fillBuffer(length + 1)) && 
/*  641 */       isLiteral(this.buffer[this.pos + length])) {
/*  642 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  646 */     this.pos += length;
/*  647 */     return this.peeked = peeking;
/*      */   }
/*      */   
/*      */   private int peekNumber() throws IOException {
/*      */     int j;
/*  652 */     char[] buffer = this.buffer;
/*  653 */     int p = this.pos;
/*  654 */     int l = this.limit;
/*      */     
/*  656 */     long value = 0L;
/*  657 */     boolean negative = false;
/*  658 */     boolean fitsInLong = true;
/*  659 */     int last = 0;
/*      */     
/*  661 */     int i = 0;
/*      */ 
/*      */     
/*  664 */     for (;; i++) {
/*  665 */       if (p + i == l) {
/*  666 */         if (i == buffer.length)
/*      */         {
/*      */           
/*  669 */           return 0;
/*      */         }
/*  671 */         if (!fillBuffer(i + 1)) {
/*      */           break;
/*      */         }
/*  674 */         p = this.pos;
/*  675 */         l = this.limit;
/*      */       } 
/*      */       
/*  678 */       char c = buffer[p + i];
/*  679 */       switch (c) {
/*      */         case '-':
/*  681 */           if (last == 0) {
/*  682 */             negative = true;
/*  683 */             last = 1; break;
/*      */           } 
/*  685 */           if (last == 5) {
/*  686 */             last = 6;
/*      */             break;
/*      */           } 
/*  689 */           return 0;
/*      */         
/*      */         case '+':
/*  692 */           if (last == 5) {
/*  693 */             last = 6;
/*      */             break;
/*      */           } 
/*  696 */           return 0;
/*      */         
/*      */         case 'E':
/*      */         case 'e':
/*  700 */           if (last == 2 || last == 4) {
/*  701 */             last = 5;
/*      */             break;
/*      */           } 
/*  704 */           return 0;
/*      */         
/*      */         case '.':
/*  707 */           if (last == 2) {
/*  708 */             last = 3;
/*      */             break;
/*      */           } 
/*  711 */           return 0;
/*      */         
/*      */         default:
/*  714 */           if (c < '0' || c > '9') {
/*  715 */             if (!isLiteral(c)) {
/*      */               break;
/*      */             }
/*  718 */             return 0;
/*      */           } 
/*  720 */           if (last == 1 || last == 0) {
/*  721 */             value = -(c - 48);
/*  722 */             last = 2; break;
/*  723 */           }  if (last == 2) {
/*  724 */             if (value == 0L) {
/*  725 */               return 0;
/*      */             }
/*  727 */             long newValue = value * 10L - (c - 48);
/*  728 */             j = fitsInLong & ((value > -922337203685477580L || (value == -922337203685477580L && newValue < value)) ? 1 : 0);
/*      */             
/*  730 */             value = newValue; break;
/*  731 */           }  if (last == 3) {
/*  732 */             last = 4; break;
/*  733 */           }  if (last == 5 || last == 6) {
/*  734 */             last = 7;
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/*  742 */     if (last == 2 && j != 0 && (value != Long.MIN_VALUE || negative) && (value != 0L || !negative)) {
/*  743 */       this.peekedLong = negative ? value : -value;
/*  744 */       this.pos += i;
/*  745 */       return this.peeked = 15;
/*  746 */     }  if (last == 2 || last == 4 || last == 7) {
/*      */       
/*  748 */       this.peekedNumberLength = i;
/*  749 */       return this.peeked = 16;
/*      */     } 
/*  751 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiteral(char c) throws IOException {
/*  757 */     switch (c) {
/*      */       case '#':
/*      */       case '/':
/*      */       case ';':
/*      */       case '=':
/*      */       case '\\':
/*  763 */         checkLenient();
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\f':
/*      */       case '\r':
/*      */       case ' ':
/*      */       case ',':
/*      */       case ':':
/*      */       case '[':
/*      */       case ']':
/*      */       case '{':
/*      */       case '}':
/*  775 */         return false;
/*      */     } 
/*  777 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextName() throws IOException {
/*      */     String result;
/*  788 */     int p = this.peeked;
/*  789 */     if (p == 0) {
/*  790 */       p = doPeek();
/*      */     }
/*      */     
/*  793 */     if (p == 14) {
/*  794 */       result = nextUnquotedValue();
/*  795 */     } else if (p == 12) {
/*  796 */       result = nextQuotedValue('\'');
/*  797 */     } else if (p == 13) {
/*  798 */       result = nextQuotedValue('"');
/*      */     } else {
/*  800 */       throw new IllegalStateException("Expected a name but was " + peek() + locationString());
/*      */     } 
/*  802 */     this.peeked = 0;
/*  803 */     this.pathNames[this.stackSize - 1] = result;
/*  804 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nextString() throws IOException {
/*      */     String result;
/*  816 */     int p = this.peeked;
/*  817 */     if (p == 0) {
/*  818 */       p = doPeek();
/*      */     }
/*      */     
/*  821 */     if (p == 10) {
/*  822 */       result = nextUnquotedValue();
/*  823 */     } else if (p == 8) {
/*  824 */       result = nextQuotedValue('\'');
/*  825 */     } else if (p == 9) {
/*  826 */       result = nextQuotedValue('"');
/*  827 */     } else if (p == 11) {
/*  828 */       result = this.peekedString;
/*  829 */       this.peekedString = null;
/*  830 */     } else if (p == 15) {
/*  831 */       result = Long.toString(this.peekedLong);
/*  832 */     } else if (p == 16) {
/*  833 */       result = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  834 */       this.pos += this.peekedNumberLength;
/*      */     } else {
/*  836 */       throw new IllegalStateException("Expected a string but was " + peek() + locationString());
/*      */     } 
/*  838 */     this.peeked = 0;
/*  839 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  840 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nextBoolean() throws IOException {
/*  851 */     int p = this.peeked;
/*  852 */     if (p == 0) {
/*  853 */       p = doPeek();
/*      */     }
/*  855 */     if (p == 5) {
/*  856 */       this.peeked = 0;
/*  857 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  858 */       return true;
/*  859 */     }  if (p == 6) {
/*  860 */       this.peeked = 0;
/*  861 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  862 */       return false;
/*      */     } 
/*  864 */     throw new IllegalStateException("Expected a boolean but was " + peek() + locationString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void nextNull() throws IOException {
/*  875 */     int p = this.peeked;
/*  876 */     if (p == 0) {
/*  877 */       p = doPeek();
/*      */     }
/*  879 */     if (p == 7) {
/*  880 */       this.peeked = 0;
/*  881 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*      */     } else {
/*  883 */       throw new IllegalStateException("Expected null but was " + peek() + locationString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double nextDouble() throws IOException {
/*  899 */     int p = this.peeked;
/*  900 */     if (p == 0) {
/*  901 */       p = doPeek();
/*      */     }
/*      */     
/*  904 */     if (p == 15) {
/*  905 */       this.peeked = 0;
/*  906 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  907 */       return this.peekedLong;
/*      */     } 
/*      */     
/*  910 */     if (p == 16) {
/*  911 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  912 */       this.pos += this.peekedNumberLength;
/*  913 */     } else if (p == 8 || p == 9) {
/*  914 */       this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*  915 */     } else if (p == 10) {
/*  916 */       this.peekedString = nextUnquotedValue();
/*  917 */     } else if (p != 11) {
/*  918 */       throw new IllegalStateException("Expected a double but was " + peek() + locationString());
/*      */     } 
/*      */     
/*  921 */     this.peeked = 11;
/*  922 */     double result = Double.parseDouble(this.peekedString);
/*  923 */     if (!this.lenient && (Double.isNaN(result) || Double.isInfinite(result))) {
/*  924 */       throw new MalformedJsonException("JSON forbids NaN and infinities: " + result + 
/*  925 */           locationString());
/*      */     }
/*  927 */     this.peekedString = null;
/*  928 */     this.peeked = 0;
/*  929 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  930 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long nextLong() throws IOException {
/*  944 */     int p = this.peeked;
/*  945 */     if (p == 0) {
/*  946 */       p = doPeek();
/*      */     }
/*      */     
/*  949 */     if (p == 15) {
/*  950 */       this.peeked = 0;
/*  951 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  952 */       return this.peekedLong;
/*      */     } 
/*      */     
/*  955 */     if (p == 16) {
/*  956 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/*  957 */       this.pos += this.peekedNumberLength;
/*  958 */     } else if (p == 8 || p == 9 || p == 10) {
/*  959 */       if (p == 10) {
/*  960 */         this.peekedString = nextUnquotedValue();
/*      */       } else {
/*  962 */         this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*      */       } 
/*      */       try {
/*  965 */         long l = Long.parseLong(this.peekedString);
/*  966 */         this.peeked = 0;
/*  967 */         this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  968 */         return l;
/*  969 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     else {
/*      */       
/*  973 */       throw new IllegalStateException("Expected a long but was " + peek() + locationString());
/*      */     } 
/*      */     
/*  976 */     this.peeked = 11;
/*  977 */     double asDouble = Double.parseDouble(this.peekedString);
/*  978 */     long result = (long)asDouble;
/*  979 */     if (result != asDouble) {
/*  980 */       throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
/*      */     }
/*  982 */     this.peekedString = null;
/*  983 */     this.peeked = 0;
/*  984 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*  985 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String nextQuotedValue(char quote) throws IOException {
/* 1000 */     char[] buffer = this.buffer;
/* 1001 */     StringBuilder builder = null;
/*      */     while (true) {
/* 1003 */       int p = this.pos;
/* 1004 */       int l = this.limit;
/*      */       
/* 1006 */       int start = p;
/* 1007 */       while (p < l) {
/* 1008 */         int c = buffer[p++];
/*      */         
/* 1010 */         if (c == quote) {
/* 1011 */           this.pos = p;
/* 1012 */           int len = p - start - 1;
/* 1013 */           if (builder == null) {
/* 1014 */             return new String(buffer, start, len);
/*      */           }
/* 1016 */           builder.append(buffer, start, len);
/* 1017 */           return builder.toString();
/*      */         } 
/* 1019 */         if (c == 92) {
/* 1020 */           this.pos = p;
/* 1021 */           int len = p - start - 1;
/* 1022 */           if (builder == null) {
/* 1023 */             int estimatedLength = (len + 1) * 2;
/* 1024 */             builder = new StringBuilder(Math.max(estimatedLength, 16));
/*      */           } 
/* 1026 */           builder.append(buffer, start, len);
/* 1027 */           builder.append(readEscapeCharacter());
/* 1028 */           p = this.pos;
/* 1029 */           l = this.limit;
/* 1030 */           start = p; continue;
/* 1031 */         }  if (c == 10) {
/* 1032 */           this.lineNumber++;
/* 1033 */           this.lineStart = p;
/*      */         } 
/*      */       } 
/*      */       
/* 1037 */       if (builder == null) {
/* 1038 */         int estimatedLength = (p - start) * 2;
/* 1039 */         builder = new StringBuilder(Math.max(estimatedLength, 16));
/*      */       } 
/* 1041 */       builder.append(buffer, start, p - start);
/* 1042 */       this.pos = p;
/* 1043 */       if (!fillBuffer(1)) {
/* 1044 */         throw syntaxError("Unterminated string");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String nextUnquotedValue() throws IOException {
/* 1054 */     StringBuilder builder = null;
/* 1055 */     int i = 0;
/*      */ 
/*      */     
/*      */     label34: while (true) {
/* 1059 */       for (; this.pos + i < this.limit; i++)
/* 1060 */       { switch (this.buffer[this.pos + i])
/*      */         { case '#':
/*      */           case '/':
/*      */           case ';':
/*      */           case '=':
/*      */           case '\\':
/* 1066 */             checkLenient(); break label34;
/*      */           case '\t': break label34;
/*      */           case '\n': break label34;
/*      */           case '\f': break label34;
/*      */           case '\r': break label34;
/*      */           case ' ': break label34;
/*      */           case ',':
/*      */             break label34;
/*      */           case ':':
/*      */             break label34;
/*      */           case '[':
/*      */             break label34;
/*      */           case ']':
/*      */             break label34;
/*      */           case '{':
/*      */             break label34;
/*      */           case '}':
/* 1083 */             break label34; }  }  if (i < this.buffer.length) {
/* 1084 */         if (fillBuffer(i + 1)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1092 */       if (builder == null) {
/* 1093 */         builder = new StringBuilder(Math.max(i, 16));
/*      */       }
/* 1095 */       builder.append(this.buffer, this.pos, i);
/* 1096 */       this.pos += i;
/* 1097 */       i = 0;
/* 1098 */       if (!fillBuffer(1)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1103 */     String result = (null == builder) ? new String(this.buffer, this.pos, i) : builder.append(this.buffer, this.pos, i).toString();
/* 1104 */     this.pos += i;
/* 1105 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private void skipQuotedValue(char quote) throws IOException {
/* 1110 */     char[] buffer = this.buffer;
/*      */     while (true) {
/* 1112 */       int p = this.pos;
/* 1113 */       int l = this.limit;
/*      */       
/* 1115 */       while (p < l) {
/* 1116 */         int c = buffer[p++];
/* 1117 */         if (c == quote) {
/* 1118 */           this.pos = p; return;
/*      */         } 
/* 1120 */         if (c == 92) {
/* 1121 */           this.pos = p;
/* 1122 */           readEscapeCharacter();
/* 1123 */           p = this.pos;
/* 1124 */           l = this.limit; continue;
/* 1125 */         }  if (c == 10) {
/* 1126 */           this.lineNumber++;
/* 1127 */           this.lineStart = p;
/*      */         } 
/*      */       } 
/* 1130 */       this.pos = p;
/* 1131 */       if (!fillBuffer(1))
/* 1132 */         throw syntaxError("Unterminated string"); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void skipUnquotedValue() throws IOException {
/*      */     do {
/* 1138 */       int i = 0;
/* 1139 */       for (; this.pos + i < this.limit; i++) {
/* 1140 */         switch (this.buffer[this.pos + i]) {
/*      */           case '#':
/*      */           case '/':
/*      */           case ';':
/*      */           case '=':
/*      */           case '\\':
/* 1146 */             checkLenient();
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */           case ',':
/*      */           case ':':
/*      */           case '[':
/*      */           case ']':
/*      */           case '{':
/*      */           case '}':
/* 1158 */             this.pos += i;
/*      */             return;
/*      */         } 
/*      */       } 
/* 1162 */       this.pos += i;
/* 1163 */     } while (fillBuffer(1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextInt() throws IOException {
/* 1177 */     int p = this.peeked;
/* 1178 */     if (p == 0) {
/* 1179 */       p = doPeek();
/*      */     }
/*      */ 
/*      */     
/* 1183 */     if (p == 15) {
/* 1184 */       int i = (int)this.peekedLong;
/* 1185 */       if (this.peekedLong != i) {
/* 1186 */         throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
/*      */       }
/* 1188 */       this.peeked = 0;
/* 1189 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1190 */       return i;
/*      */     } 
/*      */     
/* 1193 */     if (p == 16) {
/* 1194 */       this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
/* 1195 */       this.pos += this.peekedNumberLength;
/* 1196 */     } else if (p == 8 || p == 9 || p == 10) {
/* 1197 */       if (p == 10) {
/* 1198 */         this.peekedString = nextUnquotedValue();
/*      */       } else {
/* 1200 */         this.peekedString = nextQuotedValue((p == 8) ? 39 : 34);
/*      */       } 
/*      */       try {
/* 1203 */         int i = Integer.parseInt(this.peekedString);
/* 1204 */         this.peeked = 0;
/* 1205 */         this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1206 */         return i;
/* 1207 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */     else {
/*      */       
/* 1211 */       throw new IllegalStateException("Expected an int but was " + peek() + locationString());
/*      */     } 
/*      */     
/* 1214 */     this.peeked = 11;
/* 1215 */     double asDouble = Double.parseDouble(this.peekedString);
/* 1216 */     int result = (int)asDouble;
/* 1217 */     if (result != asDouble) {
/* 1218 */       throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
/*      */     }
/* 1220 */     this.peekedString = null;
/* 1221 */     this.peeked = 0;
/* 1222 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/* 1223 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 1230 */     this.peeked = 0;
/* 1231 */     this.stack[0] = 8;
/* 1232 */     this.stackSize = 1;
/* 1233 */     this.in.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipValue() throws IOException {
/* 1252 */     int count = 0;
/*      */     do {
/* 1254 */       int p = this.peeked;
/* 1255 */       if (p == 0) {
/* 1256 */         p = doPeek();
/*      */       }
/*      */       
/* 1259 */       switch (p) {
/*      */         case 3:
/* 1261 */           push(1);
/* 1262 */           count++;
/*      */           break;
/*      */         case 1:
/* 1265 */           push(3);
/* 1266 */           count++;
/*      */           break;
/*      */         case 4:
/* 1269 */           this.stackSize--;
/* 1270 */           count--;
/*      */           break;
/*      */         
/*      */         case 2:
/* 1274 */           if (count == 0) {
/* 1275 */             this.pathNames[this.stackSize - 1] = null;
/*      */           }
/* 1277 */           this.stackSize--;
/* 1278 */           count--;
/*      */           break;
/*      */         case 10:
/* 1281 */           skipUnquotedValue();
/*      */           break;
/*      */         case 8:
/* 1284 */           skipQuotedValue('\'');
/*      */           break;
/*      */         case 9:
/* 1287 */           skipQuotedValue('"');
/*      */           break;
/*      */         case 14:
/* 1290 */           skipUnquotedValue();
/*      */           
/* 1292 */           if (count == 0) {
/* 1293 */             this.pathNames[this.stackSize - 1] = "<skipped>";
/*      */           }
/*      */           break;
/*      */         case 12:
/* 1297 */           skipQuotedValue('\'');
/*      */           
/* 1299 */           if (count == 0) {
/* 1300 */             this.pathNames[this.stackSize - 1] = "<skipped>";
/*      */           }
/*      */           break;
/*      */         case 13:
/* 1304 */           skipQuotedValue('"');
/*      */           
/* 1306 */           if (count == 0) {
/* 1307 */             this.pathNames[this.stackSize - 1] = "<skipped>";
/*      */           }
/*      */           break;
/*      */         case 16:
/* 1311 */           this.pos += this.peekedNumberLength;
/*      */           break;
/*      */         
/*      */         case 17:
/*      */           return;
/*      */       } 
/*      */       
/* 1318 */       this.peeked = 0;
/* 1319 */     } while (count > 0);
/*      */     
/* 1321 */     this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*      */   }
/*      */   
/*      */   private void push(int newTop) {
/* 1325 */     if (this.stackSize == this.stack.length) {
/* 1326 */       int newLength = this.stackSize * 2;
/* 1327 */       this.stack = Arrays.copyOf(this.stack, newLength);
/* 1328 */       this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
/* 1329 */       this.pathNames = Arrays.<String>copyOf(this.pathNames, newLength);
/*      */     } 
/* 1331 */     this.stack[this.stackSize++] = newTop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillBuffer(int minimum) throws IOException {
/* 1340 */     char[] buffer = this.buffer;
/* 1341 */     this.lineStart -= this.pos;
/* 1342 */     if (this.limit != this.pos) {
/* 1343 */       this.limit -= this.pos;
/* 1344 */       System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
/*      */     } else {
/* 1346 */       this.limit = 0;
/*      */     } 
/*      */     
/* 1349 */     this.pos = 0;
/*      */     int total;
/* 1351 */     while ((total = this.in.read(buffer, this.limit, buffer.length - this.limit)) != -1) {
/* 1352 */       this.limit += total;
/*      */ 
/*      */       
/* 1355 */       if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == '﻿') {
/* 1356 */         this.pos++;
/* 1357 */         this.lineStart++;
/* 1358 */         minimum++;
/*      */       } 
/*      */       
/* 1361 */       if (this.limit >= minimum) {
/* 1362 */         return true;
/*      */       }
/*      */     } 
/* 1365 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextNonWhitespace(boolean throwOnEof) throws IOException {
/* 1383 */     char[] buffer = this.buffer;
/* 1384 */     int p = this.pos;
/* 1385 */     int l = this.limit;
/*      */     while (true) {
/* 1387 */       if (p == l) {
/* 1388 */         this.pos = p;
/* 1389 */         if (!fillBuffer(1)) {
/*      */           break;
/*      */         }
/* 1392 */         p = this.pos;
/* 1393 */         l = this.limit;
/*      */       } 
/*      */       
/* 1396 */       int c = buffer[p++];
/* 1397 */       if (c == 10) {
/* 1398 */         this.lineNumber++;
/* 1399 */         this.lineStart = p; continue;
/*      */       } 
/* 1401 */       if (c == 32 || c == 13 || c == 9) {
/*      */         continue;
/*      */       }
/*      */       
/* 1405 */       if (c == 47) {
/* 1406 */         this.pos = p;
/* 1407 */         if (p == l) {
/* 1408 */           this.pos--;
/* 1409 */           boolean charsLoaded = fillBuffer(2);
/* 1410 */           this.pos++;
/* 1411 */           if (!charsLoaded) {
/* 1412 */             return c;
/*      */           }
/*      */         } 
/*      */         
/* 1416 */         checkLenient();
/* 1417 */         char peek = buffer[this.pos];
/* 1418 */         switch (peek) {
/*      */           
/*      */           case '*':
/* 1421 */             this.pos++;
/* 1422 */             if (!skipTo("*/")) {
/* 1423 */               throw syntaxError("Unterminated comment");
/*      */             }
/* 1425 */             p = this.pos + 2;
/* 1426 */             l = this.limit;
/*      */             continue;
/*      */ 
/*      */           
/*      */           case '/':
/* 1431 */             this.pos++;
/* 1432 */             skipToEndOfLine();
/* 1433 */             p = this.pos;
/* 1434 */             l = this.limit;
/*      */             continue;
/*      */         } 
/*      */         
/* 1438 */         return c;
/*      */       } 
/* 1440 */       if (c == 35) {
/* 1441 */         this.pos = p;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1447 */         checkLenient();
/* 1448 */         skipToEndOfLine();
/* 1449 */         p = this.pos;
/* 1450 */         l = this.limit; continue;
/*      */       } 
/* 1452 */       this.pos = p;
/* 1453 */       return c;
/*      */     } 
/*      */     
/* 1456 */     if (throwOnEof) {
/* 1457 */       throw new EOFException("End of input" + locationString());
/*      */     }
/* 1459 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkLenient() throws IOException {
/* 1464 */     if (!this.lenient) {
/* 1465 */       throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void skipToEndOfLine() throws IOException {
/* 1475 */     while (this.pos < this.limit || fillBuffer(1)) {
/* 1476 */       char c = this.buffer[this.pos++];
/* 1477 */       if (c == '\n') {
/* 1478 */         this.lineNumber++;
/* 1479 */         this.lineStart = this.pos; break;
/*      */       } 
/* 1481 */       if (c == '\r') {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipTo(String toFind) throws IOException {
/* 1491 */     int length = toFind.length();
/*      */     
/* 1493 */     for (; this.pos + length <= this.limit || fillBuffer(length); this.pos++) {
/* 1494 */       if (this.buffer[this.pos] == '\n') {
/* 1495 */         this.lineNumber++;
/* 1496 */         this.lineStart = this.pos + 1;
/*      */       } else {
/*      */         
/* 1499 */         int c = 0; while (true) { if (c < length) {
/* 1500 */             if (this.buffer[this.pos + c] != toFind.charAt(c))
/*      */               break;  c++;
/*      */             continue;
/*      */           } 
/* 1504 */           return true; } 
/*      */       } 
/* 1506 */     }  return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1510 */     return getClass().getSimpleName() + locationString();
/*      */   }
/*      */   
/*      */   String locationString() {
/* 1514 */     int line = this.lineNumber + 1;
/* 1515 */     int column = this.pos - this.lineStart + 1;
/* 1516 */     return " at line " + line + " column " + column + " path " + getPath();
/*      */   }
/*      */   
/*      */   private String getPath(boolean usePreviousPath) {
/* 1520 */     StringBuilder result = (new StringBuilder()).append('$');
/* 1521 */     for (int i = 0; i < this.stackSize; i++) {
/* 1522 */       int pathIndex; switch (this.stack[i]) {
/*      */         case 1:
/*      */         case 2:
/* 1525 */           pathIndex = this.pathIndices[i];
/*      */           
/* 1527 */           if (usePreviousPath && pathIndex > 0 && i == this.stackSize - 1) {
/* 1528 */             pathIndex--;
/*      */           }
/* 1530 */           result.append('[').append(pathIndex).append(']');
/*      */           break;
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/* 1535 */           result.append('.');
/* 1536 */           if (this.pathNames[i] != null) {
/* 1537 */             result.append(this.pathNames[i]);
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1546 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPreviousPath() {
/* 1563 */     return getPath(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/* 1581 */     return getPath(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char readEscapeCharacter() throws IOException {
/*      */     char result;
/*      */     int i, end;
/* 1595 */     if (this.pos == this.limit && !fillBuffer(1)) {
/* 1596 */       throw syntaxError("Unterminated escape sequence");
/*      */     }
/*      */     
/* 1599 */     char escaped = this.buffer[this.pos++];
/* 1600 */     switch (escaped) {
/*      */       case 'u':
/* 1602 */         if (this.pos + 4 > this.limit && !fillBuffer(4)) {
/* 1603 */           throw syntaxError("Unterminated escape sequence");
/*      */         }
/*      */         
/* 1606 */         result = Character.MIN_VALUE;
/* 1607 */         for (i = this.pos, end = i + 4; i < end; i++) {
/* 1608 */           char c = this.buffer[i];
/* 1609 */           result = (char)(result << 4);
/* 1610 */           if (c >= '0' && c <= '9') {
/* 1611 */             result = (char)(result + c - 48);
/* 1612 */           } else if (c >= 'a' && c <= 'f') {
/* 1613 */             result = (char)(result + c - 97 + 10);
/* 1614 */           } else if (c >= 'A' && c <= 'F') {
/* 1615 */             result = (char)(result + c - 65 + 10);
/*      */           } else {
/* 1617 */             throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
/*      */           } 
/*      */         } 
/* 1620 */         this.pos += 4;
/* 1621 */         return result;
/*      */       
/*      */       case 't':
/* 1624 */         return '\t';
/*      */       
/*      */       case 'b':
/* 1627 */         return '\b';
/*      */       
/*      */       case 'n':
/* 1630 */         return '\n';
/*      */       
/*      */       case 'r':
/* 1633 */         return '\r';
/*      */       
/*      */       case 'f':
/* 1636 */         return '\f';
/*      */       
/*      */       case '\n':
/* 1639 */         this.lineNumber++;
/* 1640 */         this.lineStart = this.pos;
/*      */ 
/*      */       
/*      */       case '"':
/*      */       case '\'':
/*      */       case '/':
/*      */       case '\\':
/* 1647 */         return escaped;
/*      */     } 
/*      */     
/* 1650 */     throw syntaxError("Invalid escape sequence");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IOException syntaxError(String message) throws IOException {
/* 1659 */     throw new MalformedJsonException(message + locationString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consumeNonExecutePrefix() throws IOException {
/* 1667 */     nextNonWhitespace(true);
/* 1668 */     this.pos--;
/*      */     
/* 1670 */     if (this.pos + 5 > this.limit && !fillBuffer(5)) {
/*      */       return;
/*      */     }
/*      */     
/* 1674 */     int p = this.pos;
/* 1675 */     char[] buf = this.buffer;
/* 1676 */     if (buf[p] != ')' || buf[p + 1] != ']' || buf[p + 2] != '}' || buf[p + 3] != '\'' || buf[p + 4] != '\n') {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1681 */     this.pos += 5;
/*      */   }
/*      */   
/*      */   static {
/* 1685 */     JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
/*      */         public void promoteNameToValue(JsonReader reader) throws IOException {
/* 1687 */           if (reader instanceof JsonTreeReader) {
/* 1688 */             ((JsonTreeReader)reader).promoteNameToValue();
/*      */             return;
/*      */           } 
/* 1691 */           int p = reader.peeked;
/* 1692 */           if (p == 0) {
/* 1693 */             p = reader.doPeek();
/*      */           }
/* 1695 */           if (p == 13) {
/* 1696 */             reader.peeked = 9;
/* 1697 */           } else if (p == 12) {
/* 1698 */             reader.peeked = 8;
/* 1699 */           } else if (p == 14) {
/* 1700 */             reader.peeked = 10;
/*      */           } else {
/* 1702 */             throw new IllegalStateException("Expected a name but was " + reader
/* 1703 */                 .peek() + reader.locationString());
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\stream\JsonReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */