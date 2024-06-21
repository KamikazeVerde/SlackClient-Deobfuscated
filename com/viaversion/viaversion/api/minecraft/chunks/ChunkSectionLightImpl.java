/*     */ package com.viaversion.viaversion.api.minecraft.chunks;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ public class ChunkSectionLightImpl
/*     */   implements ChunkSectionLight
/*     */ {
/*  35 */   private NibbleArray blockLight = new NibbleArray(4096);
/*     */   
/*     */   private NibbleArray skyLight;
/*     */   
/*     */   public void setBlockLight(byte[] data) {
/*  40 */     if (data.length != 2048) throw new IllegalArgumentException("Data length != 2048"); 
/*  41 */     if (this.blockLight == null) {
/*  42 */       this.blockLight = new NibbleArray(data);
/*     */     } else {
/*  44 */       this.blockLight.setHandle(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkyLight(byte[] data) {
/*  50 */     if (data.length != 2048) throw new IllegalArgumentException("Data length != 2048"); 
/*  51 */     if (this.skyLight == null) {
/*  52 */       this.skyLight = new NibbleArray(data);
/*     */     } else {
/*  54 */       this.skyLight.setHandle(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getBlockLight() {
/*  60 */     return (this.blockLight == null) ? null : this.blockLight.getHandle();
/*     */   }
/*     */ 
/*     */   
/*     */   public NibbleArray getBlockLightNibbleArray() {
/*  65 */     return this.blockLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getSkyLight() {
/*  70 */     return (this.skyLight == null) ? null : this.skyLight.getHandle();
/*     */   }
/*     */ 
/*     */   
/*     */   public NibbleArray getSkyLightNibbleArray() {
/*  75 */     return this.skyLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readBlockLight(ByteBuf input) {
/*  80 */     if (this.blockLight == null) {
/*  81 */       this.blockLight = new NibbleArray(4096);
/*     */     }
/*  83 */     input.readBytes(this.blockLight.getHandle());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSkyLight(ByteBuf input) {
/*  88 */     if (this.skyLight == null) {
/*  89 */       this.skyLight = new NibbleArray(4096);
/*     */     }
/*  91 */     input.readBytes(this.skyLight.getHandle());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBlockLight(ByteBuf output) {
/*  96 */     output.writeBytes(this.blockLight.getHandle());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSkyLight(ByteBuf output) {
/* 101 */     output.writeBytes(this.skyLight.getHandle());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSkyLight() {
/* 106 */     return (this.skyLight != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasBlockLight() {
/* 111 */     return (this.blockLight != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\ChunkSectionLightImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */