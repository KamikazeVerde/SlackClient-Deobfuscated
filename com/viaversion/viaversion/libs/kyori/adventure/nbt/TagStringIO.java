/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TagStringIO
/*     */ {
/*  37 */   private static final TagStringIO INSTANCE = new TagStringIO(new Builder());
/*     */   
/*     */   private final boolean acceptLegacy;
/*     */   
/*     */   private final boolean emitLegacy;
/*     */   private final String indent;
/*     */   
/*     */   @NotNull
/*     */   public static TagStringIO get() {
/*  46 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Builder builder() {
/*  56 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TagStringIO(@NotNull Builder builder) {
/*  64 */     this.acceptLegacy = builder.acceptLegacy;
/*  65 */     this.emitLegacy = builder.emitLegacy;
/*  66 */     this.indent = builder.indent;
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
/*     */   public CompoundBinaryTag asCompound(String input) throws IOException {
/*     */     try {
/*  82 */       CharBuffer buffer = new CharBuffer(input);
/*  83 */       TagStringReader parser = new TagStringReader(buffer);
/*  84 */       parser.legacy(this.acceptLegacy);
/*  85 */       CompoundBinaryTag tag = parser.compound();
/*  86 */       if (buffer.skipWhitespace().hasMore()) {
/*  87 */         throw new IOException("Document had trailing content after first CompoundTag");
/*     */       }
/*  89 */       return tag;
/*  90 */     } catch (StringTagParseException ex) {
/*  91 */       throw new IOException(ex);
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
/*     */   public String asString(CompoundBinaryTag input) throws IOException {
/* 104 */     StringBuilder sb = new StringBuilder();
/* 105 */     TagStringWriter emit = new TagStringWriter(sb, this.indent); 
/* 106 */     try { emit.legacy(this.emitLegacy);
/* 107 */       emit.writeTag(input);
/* 108 */       emit.close(); } catch (Throwable throwable) { try { emit.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 109 */      return sb.toString();
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
/*     */   public void toWriter(CompoundBinaryTag input, Writer dest) throws IOException {
/* 123 */     TagStringWriter emit = new TagStringWriter(dest, this.indent); try {
/* 124 */       emit.legacy(this.emitLegacy);
/* 125 */       emit.writeTag(input);
/* 126 */       emit.close();
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         emit.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */   public static class Builder { private boolean acceptLegacy = true; private boolean emitLegacy = false;
/* 137 */     private String indent = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Builder indent(int spaces) {
/* 152 */       if (spaces == 0) {
/* 153 */         this.indent = "";
/* 154 */       } else if ((this.indent.length() > 0 && this.indent.charAt(0) != ' ') || spaces != this.indent.length()) {
/* 155 */         char[] indent = new char[spaces];
/* 156 */         Arrays.fill(indent, ' ');
/* 157 */         this.indent = String.copyValueOf(indent);
/*     */       } 
/* 159 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Builder indentTab(int tabs) {
/* 172 */       if (tabs == 0) {
/* 173 */         this.indent = "";
/* 174 */       } else if ((this.indent.length() > 0 && this.indent.charAt(0) != '\t') || tabs != this.indent.length()) {
/* 175 */         char[] indent = new char[tabs];
/* 176 */         Arrays.fill(indent, '\t');
/* 177 */         this.indent = String.copyValueOf(indent);
/*     */       } 
/* 179 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Builder acceptLegacy(boolean legacy) {
/* 195 */       this.acceptLegacy = legacy;
/* 196 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Builder emitLegacy(boolean legacy) {
/* 207 */       this.emitLegacy = legacy;
/* 208 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public TagStringIO build() {
/* 218 */       return new TagStringIO(this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\TagStringIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */