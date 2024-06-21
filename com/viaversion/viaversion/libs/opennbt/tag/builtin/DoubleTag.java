/*     */ package com.viaversion.viaversion.libs.opennbt.tag.builtin;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleTag
/*     */   extends NumberTag
/*     */ {
/*     */   public static final int ID = 6;
/*     */   private double value;
/*     */   
/*     */   public DoubleTag() {
/*  20 */     this(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleTag(double value) {
/*  29 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Double getValue() {
/*  38 */     return Double.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(double value) {
/*  47 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  52 */     tagLimiter.countDouble();
/*  53 */     this.value = in.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  58 */     out.writeDouble(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  63 */     if (this == o) return true; 
/*  64 */     if (o == null || getClass() != o.getClass()) return false; 
/*  65 */     DoubleTag doubleTag = (DoubleTag)o;
/*  66 */     return (this.value == doubleTag.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return Double.hashCode(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleTag clone() {
/*  76 */     return new DoubleTag(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte asByte() {
/*  81 */     return (byte)(int)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public short asShort() {
/*  86 */     return (short)(int)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int asInt() {
/*  91 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long asLong() {
/*  96 */     return (long)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public float asFloat() {
/* 101 */     return (float)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double asDouble() {
/* 106 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 111 */     return 6;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\DoubleTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */