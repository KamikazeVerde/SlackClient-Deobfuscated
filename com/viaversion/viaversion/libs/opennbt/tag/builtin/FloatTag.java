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
/*     */ public class FloatTag
/*     */   extends NumberTag
/*     */ {
/*     */   public static final int ID = 5;
/*     */   private float value;
/*     */   
/*     */   public FloatTag() {
/*  20 */     this(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatTag(float value) {
/*  29 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Float getValue() {
/*  38 */     return Float.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(float value) {
/*  47 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  52 */     tagLimiter.countFloat();
/*  53 */     this.value = in.readFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  58 */     out.writeFloat(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  63 */     if (this == o) return true; 
/*  64 */     if (o == null || getClass() != o.getClass()) return false; 
/*  65 */     FloatTag floatTag = (FloatTag)o;
/*  66 */     return (this.value == floatTag.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return Float.hashCode(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final FloatTag clone() {
/*  76 */     return new FloatTag(this.value);
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
/* 101 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double asDouble() {
/* 106 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 111 */     return 5;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\FloatTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */