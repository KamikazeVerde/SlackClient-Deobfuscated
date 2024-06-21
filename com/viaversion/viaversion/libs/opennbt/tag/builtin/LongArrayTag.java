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
/*     */ public class LongArrayTag
/*     */   extends Tag
/*     */ {
/*     */   public static final int ID = 12;
/*  15 */   private static final long[] EMPTY_ARRAY = new long[0];
/*     */ 
/*     */   
/*     */   private long[] value;
/*     */ 
/*     */   
/*     */   public LongArrayTag() {
/*  22 */     this(EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayTag(long[] value) {
/*  31 */     if (value == null) {
/*  32 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  34 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] getValue() {
/*  39 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(long[] value) {
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
/*     */   public long getValue(int index) {
/*  61 */     return this.value[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int index, long value) {
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
/*  86 */     this.value = new long[in.readInt()];
/*  87 */     tagLimiter.countBytes(8 * this.value.length);
/*  88 */     for (int index = 0; index < this.value.length; index++) {
/*  89 */       this.value[index] = in.readLong();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  95 */     out.writeInt(this.value.length);
/*  96 */     for (long l : this.value) {
/*  97 */       out.writeLong(l);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 103 */     if (this == o) return true; 
/* 104 */     if (o == null || getClass() != o.getClass()) return false; 
/* 105 */     LongArrayTag that = (LongArrayTag)o;
/* 106 */     return Arrays.equals(this.value, that.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Arrays.hashCode(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongArrayTag clone() {
/* 116 */     return new LongArrayTag((long[])this.value.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 121 */     return 12;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\LongArrayTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */