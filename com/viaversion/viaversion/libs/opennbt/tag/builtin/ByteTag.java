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
/*     */ public class ByteTag
/*     */   extends NumberTag
/*     */ {
/*     */   public static final int ID = 1;
/*     */   private byte value;
/*     */   
/*     */   public ByteTag() {
/*  20 */     this((byte)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteTag(byte value) {
/*  29 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Byte getValue() {
/*  38 */     return Byte.valueOf(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(byte value) {
/*  47 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  52 */     tagLimiter.countByte();
/*  53 */     this.value = in.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  58 */     out.writeByte(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  63 */     if (this == o) return true; 
/*  64 */     if (o == null || getClass() != o.getClass()) return false; 
/*  65 */     ByteTag byteTag = (ByteTag)o;
/*  66 */     return (this.value == byteTag.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteTag clone() {
/*  76 */     return new ByteTag(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte asByte() {
/*  81 */     return this.value;
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
/* 111 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\ByteTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */