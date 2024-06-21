/*     */ package com.viaversion.viaversion.api.minecraft.chunks;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class NibbleArray
/*     */ {
/*     */   private final byte[] handle;
/*     */   
/*     */   public NibbleArray(int length) {
/*  31 */     if (length == 0 || length % 2 != 0) {
/*  32 */       throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
/*     */     }
/*     */     
/*  35 */     this.handle = new byte[length / 2];
/*     */   }
/*     */   
/*     */   public NibbleArray(byte[] handle) {
/*  39 */     if (handle.length == 0 || handle.length % 2 != 0) {
/*  40 */       throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
/*     */     }
/*     */     
/*  43 */     this.handle = handle;
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
/*     */   public byte get(int x, int y, int z) {
/*  55 */     return get(ChunkSection.index(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte get(int index) {
/*  65 */     byte value = this.handle[index / 2];
/*  66 */     if (index % 2 == 0) {
/*  67 */       return (byte)(value & 0xF);
/*     */     }
/*  69 */     return (byte)(value >> 4 & 0xF);
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
/*     */   public void set(int x, int y, int z, int value) {
/*  82 */     set(ChunkSection.index(x, y, z), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int index, int value) {
/*  92 */     if (index % 2 == 0) {
/*  93 */       index /= 2;
/*  94 */       this.handle[index] = (byte)(this.handle[index] & 0xF0 | value & 0xF);
/*     */     } else {
/*  96 */       index /= 2;
/*  97 */       this.handle[index] = (byte)(this.handle[index] & 0xF | (value & 0xF) << 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 107 */     return this.handle.length * 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int actualSize() {
/* 116 */     return this.handle.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(byte value) {
/* 125 */     value = (byte)(value & 0xF);
/* 126 */     Arrays.fill(this.handle, (byte)(value << 4 | value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getHandle() {
/* 135 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHandle(byte[] handle) {
/* 144 */     if (handle.length != this.handle.length) {
/* 145 */       throw new IllegalArgumentException("Length of handle must equal to size of nibble array!");
/*     */     }
/*     */     
/* 148 */     System.arraycopy(handle, 0, this.handle, 0, handle.length);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\NibbleArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */