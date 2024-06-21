/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CharBuffer
/*     */ {
/*     */   private final CharSequence sequence;
/*     */   private int index;
/*     */   
/*     */   CharBuffer(CharSequence sequence) {
/*  34 */     this.sequence = sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char peek() {
/*  43 */     return this.sequence.charAt(this.index);
/*     */   }
/*     */   
/*     */   public char peek(int offset) {
/*  47 */     return this.sequence.charAt(this.index + offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char take() {
/*  56 */     return this.sequence.charAt(this.index++);
/*     */   }
/*     */   
/*     */   public boolean advance() {
/*  60 */     this.index++;
/*  61 */     return hasMore();
/*     */   }
/*     */   
/*     */   public boolean hasMore() {
/*  65 */     return (this.index < this.sequence.length());
/*     */   }
/*     */   
/*     */   public boolean hasMore(int offset) {
/*  69 */     return (this.index + offset < this.sequence.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence takeUntil(char until) throws StringTagParseException {
/*  80 */     until = Character.toLowerCase(until);
/*  81 */     int endIdx = -1;
/*  82 */     for (int idx = this.index; idx < this.sequence.length(); idx++) {
/*  83 */       if (this.sequence.charAt(idx) == '\\') {
/*  84 */         idx++;
/*  85 */       } else if (Character.toLowerCase(this.sequence.charAt(idx)) == until) {
/*  86 */         endIdx = idx;
/*     */         break;
/*     */       } 
/*     */     } 
/*  90 */     if (endIdx == -1) {
/*  91 */       throw makeError("No occurrence of " + until + " was found");
/*     */     }
/*     */     
/*  94 */     CharSequence result = this.sequence.subSequence(this.index, endIdx);
/*  95 */     this.index = endIdx + 1;
/*  96 */     return result;
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
/*     */   public CharBuffer expect(char expectedChar) throws StringTagParseException {
/* 109 */     skipWhitespace();
/* 110 */     if (!hasMore()) {
/* 111 */       throw makeError("Expected character '" + expectedChar + "' but got EOF");
/*     */     }
/* 113 */     if (peek() != expectedChar) {
/* 114 */       throw makeError("Expected character '" + expectedChar + "' but got '" + peek() + "'");
/*     */     }
/* 116 */     take();
/* 117 */     return this;
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
/*     */   public boolean takeIf(char token) {
/* 129 */     skipWhitespace();
/* 130 */     if (hasMore() && peek() == token) {
/* 131 */       advance();
/* 132 */       return true;
/*     */     } 
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   public CharBuffer skipWhitespace() {
/* 138 */     for (; hasMore() && Character.isWhitespace(peek()); advance());
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public StringTagParseException makeError(String message) {
/* 143 */     return new StringTagParseException(message, this.sequence, this.index);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\CharBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */