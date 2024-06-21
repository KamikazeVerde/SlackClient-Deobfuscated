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
/*     */ public class ShortTag
/*     */   extends NumberTag
/*     */ {
/*     */   public static final int ID = 2;
/*     */   private short value;
/*     */   
/*     */   public ShortTag() {
/*  20 */     this((short)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortTag(short value) {
/*  29 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Short getValue() {
/*  38 */     return Short.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(short value) {
/*  47 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  52 */     tagLimiter.countShort();
/*  53 */     this.value = in.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  58 */     out.writeShort(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  63 */     if (this == o) return true; 
/*  64 */     if (o == null || getClass() != o.getClass()) return false; 
/*  65 */     ShortTag shortTag = (ShortTag)o;
/*  66 */     return (this.value == shortTag.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ShortTag clone() {
/*  76 */     return new ShortTag(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte asByte() {
/*  81 */     return (byte)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public short asShort() {
/*  86 */     return this.value;
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
/* 111 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\ShortTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */