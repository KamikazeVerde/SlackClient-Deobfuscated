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
/*     */ public class ByteArrayTag
/*     */   extends Tag
/*     */ {
/*     */   public static final int ID = 7;
/*  15 */   private static final byte[] EMPTY_ARRAY = new byte[0];
/*     */ 
/*     */   
/*     */   private byte[] value;
/*     */ 
/*     */   
/*     */   public ByteArrayTag() {
/*  22 */     this(EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayTag(byte[] value) {
/*  31 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getValue() {
/*  36 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(byte[] value) {
/*  45 */     if (value == null) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getValue(int index) {
/*  59 */     return this.value[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int index, byte value) {
/*  69 */     this.value[index] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*  78 */     return this.value.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*  83 */     tagLimiter.countInt();
/*  84 */     this.value = new byte[in.readInt()];
/*  85 */     tagLimiter.countBytes(this.value.length);
/*  86 */     in.readFully(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/*  91 */     out.writeInt(this.value.length);
/*  92 */     out.write(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  97 */     if (this == o) return true; 
/*  98 */     if (o == null || getClass() != o.getClass()) return false; 
/*  99 */     ByteArrayTag that = (ByteArrayTag)o;
/* 100 */     return Arrays.equals(this.value, that.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Arrays.hashCode(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteArrayTag clone() {
/* 110 */     return new ByteArrayTag(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 115 */     return 7;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\ByteArrayTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */