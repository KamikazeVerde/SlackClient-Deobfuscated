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
/*     */ public class IntTag
/*     */   extends NumberTag
/*     */ {
/*     */   public static final int ID = 3;
/*     */   private int value;
/*     */   
/*     */   public IntTag() {
/*  20 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntTag(int value) {
/*  29 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Integer getValue() {
/*  38 */     return Integer.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int value) {
/*  47 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  52 */     tagLimiter.countInt();
/*  53 */     this.value = in.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  58 */     out.writeInt(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  63 */     if (this == o) return true; 
/*  64 */     if (o == null || getClass() != o.getClass()) return false; 
/*  65 */     IntTag intTag = (IntTag)o;
/*  66 */     return (this.value == intTag.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntTag clone() {
/*  76 */     return new IntTag(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte asByte() {
/*  81 */     return (byte)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public short asShort() {
/*  86 */     return (short)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int asInt() {
/*  91 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long asLong() {
/*  96 */     return this.value;
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
/* 111 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\IntTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */