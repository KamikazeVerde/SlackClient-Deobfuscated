/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
/*     */ 
/*     */ public class ExtendedBlockStorage {
/*     */   private final int yBase;
/*     */   private byte[] blockLSBArray;
/*     */   private NibbleArray blockMSBArray;
/*     */   private NibbleArray blockMetadataArray;
/*     */   private NibbleArray blocklightArray;
/*     */   private NibbleArray skylightArray;
/*     */   
/*     */   public ExtendedBlockStorage(int paramInt, boolean paramBoolean) {
/*  14 */     this.yBase = paramInt;
/*  15 */     this.blockLSBArray = new byte[4096];
/*  16 */     this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length);
/*  17 */     this.blocklightArray = new NibbleArray(this.blockLSBArray.length);
/*  18 */     if (paramBoolean) {
/*  19 */       this.skylightArray = new NibbleArray(this.blockLSBArray.length);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getExtBlockMetadata(int paramInt1, int paramInt2, int paramInt3) {
/*  24 */     return this.blockMetadataArray.get(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public void setExtBlockMetadata(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  28 */     this.blockMetadataArray.set(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public int getYLocation() {
/*  32 */     return this.yBase;
/*     */   }
/*     */   
/*     */   public void setExtSkylightValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  36 */     this.skylightArray.set(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public int getExtSkylightValue(int paramInt1, int paramInt2, int paramInt3) {
/*  40 */     return this.skylightArray.get(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public void setExtBlocklightValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  44 */     this.blocklightArray.set(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public int getExtBlocklightValue(int paramInt1, int paramInt2, int paramInt3) {
/*  48 */     return this.blocklightArray.get(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public byte[] getBlockLSBArray() {
/*  52 */     return this.blockLSBArray;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  56 */     return (this.blockMSBArray == null);
/*     */   }
/*     */   
/*     */   public void clearMSBArray() {
/*  60 */     this.blockMSBArray = null;
/*     */   }
/*     */   
/*     */   public NibbleArray getBlockMSBArray() {
/*  64 */     return this.blockMSBArray;
/*     */   }
/*     */   
/*     */   public NibbleArray getMetadataArray() {
/*  68 */     return this.blockMetadataArray;
/*     */   }
/*     */   
/*     */   public NibbleArray getBlocklightArray() {
/*  72 */     return this.blocklightArray;
/*     */   }
/*     */   
/*     */   public NibbleArray getSkylightArray() {
/*  76 */     return this.skylightArray;
/*     */   }
/*     */   
/*     */   public void setBlockLSBArray(byte[] paramArrayOfByte) {
/*  80 */     this.blockLSBArray = paramArrayOfByte;
/*     */   }
/*     */   
/*     */   public void setBlockMSBArray(NibbleArray paramNibbleArray) {
/*  84 */     this.blockMSBArray = paramNibbleArray;
/*     */   }
/*     */   
/*     */   public void setBlockMetadataArray(NibbleArray paramNibbleArray) {
/*  88 */     this.blockMetadataArray = paramNibbleArray;
/*     */   }
/*     */   
/*     */   public void setBlocklightArray(NibbleArray paramNibbleArray) {
/*  92 */     this.blocklightArray = paramNibbleArray;
/*     */   }
/*     */   
/*     */   public void setSkylightArray(NibbleArray paramNibbleArray) {
/*  96 */     this.skylightArray = paramNibbleArray;
/*     */   }
/*     */   
/*     */   public NibbleArray createBlockMSBArray() {
/* 100 */     this.blockMSBArray = new NibbleArray(this.blockLSBArray.length);
/* 101 */     return this.blockMSBArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\chunks\ExtendedBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */