/*     */ package com.viaversion.viaversion.api.minecraft.nbt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public CharSequence takeUntil(char until) throws StringTagParseException {
/*  79 */     until = Character.toLowerCase(until);
/*  80 */     int endIdx = -1;
/*  81 */     for (int idx = this.index; idx < this.sequence.length(); idx++) {
/*  82 */       if (this.sequence.charAt(idx) == '\\') {
/*  83 */         idx++;
/*  84 */       } else if (Character.toLowerCase(this.sequence.charAt(idx)) == until) {
/*  85 */         endIdx = idx;
/*     */         break;
/*     */       } 
/*     */     } 
/*  89 */     if (endIdx == -1) {
/*  90 */       throw makeError("No occurrence of " + until + " was found");
/*     */     }
/*     */     
/*  93 */     CharSequence result = this.sequence.subSequence(this.index, endIdx);
/*  94 */     this.index = endIdx + 1;
/*  95 */     return result;
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
/* 108 */     skipWhitespace();
/* 109 */     if (!hasMore()) {
/* 110 */       throw makeError("Expected character '" + expectedChar + "' but got EOF");
/*     */     }
/* 112 */     if (peek() != expectedChar) {
/* 113 */       throw makeError("Expected character '" + expectedChar + "' but got '" + peek() + "'");
/*     */     }
/* 115 */     take();
/* 116 */     return this;
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
/* 128 */     skipWhitespace();
/* 129 */     if (hasMore() && peek() == token) {
/* 130 */       advance();
/* 131 */       return true;
/*     */     } 
/* 133 */     return false;
/*     */   }
/*     */   
/*     */   public CharBuffer skipWhitespace() {
/* 137 */     for (; hasMore() && Character.isWhitespace(peek()); advance());
/* 138 */     return this;
/*     */   }
/*     */   
/*     */   public StringTagParseException makeError(String message) {
/* 142 */     return new StringTagParseException(message, this.sequence, this.index);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\nbt\CharBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */