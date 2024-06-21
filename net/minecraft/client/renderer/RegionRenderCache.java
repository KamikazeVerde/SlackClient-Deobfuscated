/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.DynamicLights;
/*     */ 
/*     */ public class RegionRenderCache
/*     */   extends ChunkCache {
/*  18 */   private static final IBlockState DEFAULT_STATE = Blocks.air.getDefaultState();
/*     */   private final BlockPos position;
/*     */   private int[] combinedLights;
/*     */   private IBlockState[] blockStates;
/*  22 */   private static ArrayDeque<int[]> cacheLights = (ArrayDeque)new ArrayDeque<>();
/*  23 */   private static ArrayDeque<IBlockState[]> cacheStates = (ArrayDeque)new ArrayDeque<>();
/*  24 */   private static int maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);
/*     */ 
/*     */   
/*     */   public RegionRenderCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  28 */     super(worldIn, posFromIn, posToIn, subIn);
/*  29 */     this.position = posFromIn.subtract(new Vec3i(subIn, subIn, subIn));
/*  30 */     int i = 8000;
/*  31 */     this.combinedLights = allocateLights(8000);
/*  32 */     Arrays.fill(this.combinedLights, -1);
/*  33 */     this.blockStates = allocateStates(8000);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/*  38 */     int i = (pos.getX() >> 4) - this.chunkX;
/*  39 */     int j = (pos.getZ() >> 4) - this.chunkZ;
/*  40 */     return this.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.QUEUED);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  45 */     int i = getPositionIndex(pos);
/*  46 */     int j = this.combinedLights[i];
/*     */     
/*  48 */     if (j == -1) {
/*     */       
/*  50 */       j = super.getCombinedLight(pos, lightValue);
/*     */       
/*  52 */       if (Config.isDynamicLights() && !getBlockState(pos).getBlock().isOpaqueCube())
/*     */       {
/*  54 */         j = DynamicLights.getCombinedLight(pos, j);
/*     */       }
/*     */       
/*  57 */       this.combinedLights[i] = j;
/*     */     } 
/*     */     
/*  60 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  65 */     int i = getPositionIndex(pos);
/*  66 */     IBlockState iblockstate = this.blockStates[i];
/*     */     
/*  68 */     if (iblockstate == null) {
/*     */       
/*  70 */       iblockstate = getBlockStateRaw(pos);
/*  71 */       this.blockStates[i] = iblockstate;
/*     */     } 
/*     */     
/*  74 */     return iblockstate;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState getBlockStateRaw(BlockPos pos) {
/*  79 */     return super.getBlockState(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getPositionIndex(BlockPos p_175630_1_) {
/*  84 */     int i = p_175630_1_.getX() - this.position.getX();
/*  85 */     int j = p_175630_1_.getY() - this.position.getY();
/*  86 */     int k = p_175630_1_.getZ() - this.position.getZ();
/*  87 */     return i * 400 + k * 20 + j;
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeBuffers() {
/*  92 */     freeLights(this.combinedLights);
/*  93 */     freeStates(this.blockStates);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] allocateLights(int p_allocateLights_0_) {
/*  98 */     synchronized (cacheLights) {
/*     */       
/* 100 */       int[] aint = cacheLights.pollLast();
/*     */       
/* 102 */       if (aint == null || aint.length < p_allocateLights_0_)
/*     */       {
/* 104 */         aint = new int[p_allocateLights_0_];
/*     */       }
/*     */       
/* 107 */       return aint;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void freeLights(int[] p_freeLights_0_) {
/* 113 */     synchronized (cacheLights) {
/*     */       
/* 115 */       if (cacheLights.size() < maxCacheSize)
/*     */       {
/* 117 */         cacheLights.add(p_freeLights_0_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IBlockState[] allocateStates(int p_allocateStates_0_) {
/* 124 */     synchronized (cacheStates) {
/*     */       
/* 126 */       IBlockState[] aiblockstate = cacheStates.pollLast();
/*     */       
/* 128 */       if (aiblockstate != null && aiblockstate.length >= p_allocateStates_0_) {
/*     */         
/* 130 */         Arrays.fill((Object[])aiblockstate, (Object)null);
/*     */       }
/*     */       else {
/*     */         
/* 134 */         aiblockstate = new IBlockState[p_allocateStates_0_];
/*     */       } 
/*     */       
/* 137 */       return aiblockstate;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void freeStates(IBlockState[] p_freeStates_0_) {
/* 143 */     synchronized (cacheStates) {
/*     */       
/* 145 */       if (cacheStates.size() < maxCacheSize)
/*     */       {
/* 147 */         cacheStates.add(p_freeStates_0_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\RegionRenderCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */