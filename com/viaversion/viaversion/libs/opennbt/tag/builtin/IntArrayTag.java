/*     */ package com.viaversion.viaversion.libs.opennbt.tag.builtin;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntArrayTag
/*     */   extends Tag
/*     */ {
/*     */   public static final int ID = 11;
/*  15 */   private static final int[] EMPTY_ARRAY = new int[0];
/*     */ 
/*     */   
/*     */   private int[] value;
/*     */ 
/*     */   
/*     */   public IntArrayTag() {
/*  22 */     this(EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayTag(int[] value) {
/*  31 */     if (value == null) {
/*  32 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  34 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getValue() {
/*  39 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int[] value) {
/*  48 */     if (value == null) {
/*  49 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  51 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue(int index) {
/*  61 */     return this.value[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int index, int value) {
/*  71 */     this.value[index] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*  80 */     return this.value.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  85 */     tagLimiter.countInt();
/*  86 */     this.value = new int[in.readInt()];
/*  87 */     tagLimiter.countBytes(4 * this.value.length);
/*  88 */     for (int index = 0; index < this.value.length; index++) {
/*  89 */       this.value[index] = in.readInt();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  95 */     out.writeInt(this.value.length);
/*  96 */     for (int i : this.value) {
/*  97 */       out.writeInt(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 103 */     if (this == o) return true; 
/* 104 */     if (o == null || getClass() != o.getClass()) return false; 
/* 105 */     IntArrayTag that = (IntArrayTag)o;
/* 106 */     return Arrays.equals(this.value, that.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Arrays.hashCode(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntArrayTag clone() {
/* 116 */     return new IntArrayTag((int[])this.value.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 121 */     return 11;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\IntArrayTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */