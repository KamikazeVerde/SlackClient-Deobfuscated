/*     */ package net.optifine.override;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.ArrayCache;
/*     */ 
/*     */ public class ChunkCacheOF
/*     */   implements IBlockAccess {
/*     */   private final ChunkCache chunkCache;
/*     */   private final int posX;
/*     */   private final int posY;
/*     */   private final int posZ;
/*     */   private final int sizeX;
/*     */   private final int sizeY;
/*     */   private final int sizeZ;
/*     */   private final int sizeXY;
/*     */   private int[] combinedLights;
/*     */   private IBlockState[] blockStates;
/*     */   private final int arraySize;
/*  30 */   private final boolean dynamicLights = Config.isDynamicLights();
/*  31 */   private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
/*  32 */   private static final ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);
/*     */ 
/*     */   
/*     */   public ChunkCacheOF(ChunkCache chunkCache, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  36 */     this.chunkCache = chunkCache;
/*  37 */     int i = posFromIn.getX() - subIn >> 4;
/*  38 */     int j = posFromIn.getY() - subIn >> 4;
/*  39 */     int k = posFromIn.getZ() - subIn >> 4;
/*  40 */     int l = posToIn.getX() + subIn >> 4;
/*  41 */     int i1 = posToIn.getY() + subIn >> 4;
/*  42 */     int j1 = posToIn.getZ() + subIn >> 4;
/*  43 */     this.sizeX = l - i + 1 << 4;
/*  44 */     this.sizeY = i1 - j + 1 << 4;
/*  45 */     this.sizeZ = j1 - k + 1 << 4;
/*  46 */     this.sizeXY = this.sizeX * this.sizeY;
/*  47 */     this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
/*  48 */     this.posX = i << 4;
/*  49 */     this.posY = j << 4;
/*  50 */     this.posZ = k << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getPositionIndex(BlockPos pos) {
/*  55 */     int i = pos.getX() - this.posX;
/*     */     
/*  57 */     if (i >= 0 && i < this.sizeX) {
/*     */       
/*  59 */       int j = pos.getY() - this.posY;
/*     */       
/*  61 */       if (j >= 0 && j < this.sizeY) {
/*     */         
/*  63 */         int k = pos.getZ() - this.posZ;
/*  64 */         return (k >= 0 && k < this.sizeZ) ? (k * this.sizeXY + j * this.sizeX + i) : -1;
/*     */       } 
/*     */ 
/*     */       
/*  68 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  73 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  79 */     int i = getPositionIndex(pos);
/*     */     
/*  81 */     if (i >= 0 && i < this.arraySize && this.combinedLights != null) {
/*     */       
/*  83 */       int j = this.combinedLights[i];
/*     */       
/*  85 */       if (j == -1) {
/*     */         
/*  87 */         j = getCombinedLightRaw(pos, lightValue);
/*  88 */         this.combinedLights[i] = j;
/*     */       } 
/*     */       
/*  91 */       return j;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     return getCombinedLightRaw(pos, lightValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getCombinedLightRaw(BlockPos pos, int lightValue) {
/* 101 */     int i = this.chunkCache.getCombinedLight(pos, lightValue);
/*     */     
/* 103 */     if (this.dynamicLights && !getBlockState(pos).getBlock().isOpaqueCube())
/*     */     {
/* 105 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/* 108 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/* 113 */     int i = getPositionIndex(pos);
/*     */     
/* 115 */     if (i >= 0 && i < this.arraySize && this.blockStates != null) {
/*     */       
/* 117 */       IBlockState iblockstate = this.blockStates[i];
/*     */       
/* 119 */       if (iblockstate == null) {
/*     */         
/* 121 */         iblockstate = this.chunkCache.getBlockState(pos);
/* 122 */         this.blockStates[i] = iblockstate;
/*     */       } 
/*     */       
/* 125 */       return iblockstate;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return this.chunkCache.getBlockState(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderStart() {
/* 135 */     if (this.combinedLights == null)
/*     */     {
/* 137 */       this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize);
/*     */     }
/*     */     
/* 140 */     Arrays.fill(this.combinedLights, -1);
/*     */     
/* 142 */     if (this.blockStates == null)
/*     */     {
/* 144 */       this.blockStates = (IBlockState[])cacheBlockStates.allocate(this.arraySize);
/*     */     }
/*     */     
/* 147 */     Arrays.fill((Object[])this.blockStates, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFinish() {
/* 152 */     cacheCombinedLights.free(this.combinedLights);
/* 153 */     this.combinedLights = null;
/* 154 */     cacheBlockStates.free(this.blockStates);
/* 155 */     this.blockStates = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedLevelsInChunkCache() {
/* 163 */     return this.chunkCache.extendedLevelsInChunkCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
/* 168 */     return this.chunkCache.getBiomeGenForCoords(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 173 */     return this.chunkCache.getStrongPower(pos, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/* 178 */     return this.chunkCache.getTileEntity(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 183 */     return this.chunkCache.getWorldType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAirBlock(BlockPos pos) {
/* 194 */     return this.chunkCache.isAirBlock(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
/* 199 */     return Reflector.callBoolean(this.chunkCache, Reflector.ForgeChunkCache_isSideSolid, new Object[] { pos, side, Boolean.valueOf(_default) });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\override\ChunkCacheOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */