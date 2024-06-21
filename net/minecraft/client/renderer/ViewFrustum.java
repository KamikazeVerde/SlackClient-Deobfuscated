/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.render.VboRegion;
/*     */ 
/*     */ public class ViewFrustum
/*     */ {
/*     */   protected final RenderGlobal renderGlobal;
/*     */   protected final World world;
/*     */   protected int countChunksY;
/*     */   protected int countChunksX;
/*     */   protected int countChunksZ;
/*     */   public RenderChunk[] renderChunks;
/*  24 */   private Map<ChunkCoordIntPair, VboRegion[]> mapVboRegions = (Map)new HashMap<>();
/*     */ 
/*     */   
/*     */   public ViewFrustum(World worldIn, int renderDistanceChunks, RenderGlobal p_i46246_3_, IRenderChunkFactory renderChunkFactory) {
/*  28 */     this.renderGlobal = p_i46246_3_;
/*  29 */     this.world = worldIn;
/*  30 */     setCountChunksXYZ(renderDistanceChunks);
/*  31 */     createRenderChunks(renderChunkFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createRenderChunks(IRenderChunkFactory renderChunkFactory) {
/*  36 */     int i = this.countChunksX * this.countChunksY * this.countChunksZ;
/*  37 */     this.renderChunks = new RenderChunk[i];
/*  38 */     int j = 0;
/*     */     
/*  40 */     for (int k = 0; k < this.countChunksX; k++) {
/*     */       
/*  42 */       for (int l = 0; l < this.countChunksY; l++) {
/*     */         
/*  44 */         for (int i1 = 0; i1 < this.countChunksZ; i1++) {
/*     */           
/*  46 */           int j1 = (i1 * this.countChunksY + l) * this.countChunksX + k;
/*  47 */           BlockPos blockpos = new BlockPos(k * 16, l * 16, i1 * 16);
/*  48 */           this.renderChunks[j1] = renderChunkFactory.makeRenderChunk(this.world, this.renderGlobal, blockpos, j++);
/*     */           
/*  50 */           if (Config.isVbo() && Config.isRenderRegions())
/*     */           {
/*  52 */             updateVboRegion(this.renderChunks[j1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     for (int k1 = 0; k1 < this.renderChunks.length; k1++) {
/*     */       
/*  60 */       RenderChunk renderchunk1 = this.renderChunks[k1];
/*     */       
/*  62 */       for (int l1 = 0; l1 < EnumFacing.VALUES.length; l1++) {
/*     */         
/*  64 */         EnumFacing enumfacing = EnumFacing.VALUES[l1];
/*  65 */         BlockPos blockpos1 = renderchunk1.func_181701_a(enumfacing);
/*  66 */         RenderChunk renderchunk = getRenderChunk(blockpos1);
/*  67 */         renderchunk1.setRenderChunkNeighbour(enumfacing, renderchunk);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlResources() {
/*  74 */     for (RenderChunk renderchunk : this.renderChunks)
/*     */     {
/*  76 */       renderchunk.deleteGlResources();
/*     */     }
/*     */     
/*  79 */     deleteVboRegions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCountChunksXYZ(int renderDistanceChunks) {
/*  84 */     int i = renderDistanceChunks * 2 + 1;
/*  85 */     this.countChunksX = i;
/*  86 */     this.countChunksY = 16;
/*  87 */     this.countChunksZ = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChunkPositions(double viewEntityX, double viewEntityZ) {
/*  92 */     int i = MathHelper.floor_double(viewEntityX) - 8;
/*  93 */     int j = MathHelper.floor_double(viewEntityZ) - 8;
/*  94 */     int k = this.countChunksX * 16;
/*     */     
/*  96 */     for (int l = 0; l < this.countChunksX; l++) {
/*     */       
/*  98 */       int i1 = func_178157_a(i, k, l);
/*     */       
/* 100 */       for (int j1 = 0; j1 < this.countChunksZ; j1++) {
/*     */         
/* 102 */         int k1 = func_178157_a(j, k, j1);
/*     */         
/* 104 */         for (int l1 = 0; l1 < this.countChunksY; l1++) {
/*     */           
/* 106 */           int i2 = l1 * 16;
/* 107 */           RenderChunk renderchunk = this.renderChunks[(j1 * this.countChunksY + l1) * this.countChunksX + l];
/* 108 */           BlockPos blockpos = renderchunk.getPosition();
/*     */           
/* 110 */           if (blockpos.getX() != i1 || blockpos.getY() != i2 || blockpos.getZ() != k1) {
/*     */             
/* 112 */             BlockPos blockpos1 = new BlockPos(i1, i2, k1);
/*     */             
/* 114 */             if (!blockpos1.equals(renderchunk.getPosition()))
/*     */             {
/* 116 */               renderchunk.setPosition(blockpos1);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_178157_a(int p_178157_1_, int p_178157_2_, int p_178157_3_) {
/* 126 */     int i = p_178157_3_ * 16;
/* 127 */     int j = i - p_178157_1_ + p_178157_2_ / 2;
/*     */     
/* 129 */     if (j < 0)
/*     */     {
/* 131 */       j -= p_178157_2_ - 1;
/*     */     }
/*     */     
/* 134 */     return i - j / p_178157_2_ * p_178157_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markBlocksForUpdate(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
/* 139 */     int i = MathHelper.bucketInt(fromX, 16);
/* 140 */     int j = MathHelper.bucketInt(fromY, 16);
/* 141 */     int k = MathHelper.bucketInt(fromZ, 16);
/* 142 */     int l = MathHelper.bucketInt(toX, 16);
/* 143 */     int i1 = MathHelper.bucketInt(toY, 16);
/* 144 */     int j1 = MathHelper.bucketInt(toZ, 16);
/*     */     
/* 146 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 148 */       int l1 = k1 % this.countChunksX;
/*     */       
/* 150 */       if (l1 < 0)
/*     */       {
/* 152 */         l1 += this.countChunksX;
/*     */       }
/*     */       
/* 155 */       for (int i2 = j; i2 <= i1; i2++) {
/*     */         
/* 157 */         int j2 = i2 % this.countChunksY;
/*     */         
/* 159 */         if (j2 < 0)
/*     */         {
/* 161 */           j2 += this.countChunksY;
/*     */         }
/*     */         
/* 164 */         for (int k2 = k; k2 <= j1; k2++) {
/*     */           
/* 166 */           int l2 = k2 % this.countChunksZ;
/*     */           
/* 168 */           if (l2 < 0)
/*     */           {
/* 170 */             l2 += this.countChunksZ;
/*     */           }
/*     */           
/* 173 */           int i3 = (l2 * this.countChunksY + j2) * this.countChunksX + l1;
/* 174 */           RenderChunk renderchunk = this.renderChunks[i3];
/* 175 */           renderchunk.setNeedsUpdate(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunk(BlockPos pos) {
/* 183 */     int i = pos.getX() >> 4;
/* 184 */     int j = pos.getY() >> 4;
/* 185 */     int k = pos.getZ() >> 4;
/*     */     
/* 187 */     if (j >= 0 && j < this.countChunksY) {
/*     */       
/* 189 */       i %= this.countChunksX;
/*     */       
/* 191 */       if (i < 0)
/*     */       {
/* 193 */         i += this.countChunksX;
/*     */       }
/*     */       
/* 196 */       k %= this.countChunksZ;
/*     */       
/* 198 */       if (k < 0)
/*     */       {
/* 200 */         k += this.countChunksZ;
/*     */       }
/*     */       
/* 203 */       int l = (k * this.countChunksY + j) * this.countChunksX + i;
/* 204 */       return this.renderChunks[l];
/*     */     } 
/*     */ 
/*     */     
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateVboRegion(RenderChunk p_updateVboRegion_1_) {
/* 214 */     BlockPos blockpos = p_updateVboRegion_1_.getPosition();
/* 215 */     int i = blockpos.getX() >> 8 << 8;
/* 216 */     int j = blockpos.getZ() >> 8 << 8;
/* 217 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/* 218 */     EnumWorldBlockLayer[] aenumworldblocklayer = RenderChunk.ENUM_WORLD_BLOCK_LAYERS;
/* 219 */     VboRegion[] avboregion = this.mapVboRegions.get(chunkcoordintpair);
/*     */     
/* 221 */     if (avboregion == null) {
/*     */       
/* 223 */       avboregion = new VboRegion[aenumworldblocklayer.length];
/*     */       
/* 225 */       for (int k = 0; k < aenumworldblocklayer.length; k++)
/*     */       {
/* 227 */         avboregion[k] = new VboRegion(aenumworldblocklayer[k]);
/*     */       }
/*     */       
/* 230 */       this.mapVboRegions.put(chunkcoordintpair, avboregion);
/*     */     } 
/*     */     
/* 233 */     for (int l = 0; l < aenumworldblocklayer.length; l++) {
/*     */       
/* 235 */       VboRegion vboregion = avboregion[l];
/*     */       
/* 237 */       if (vboregion != null)
/*     */       {
/* 239 */         p_updateVboRegion_1_.getVertexBufferByLayer(l).setVboRegion(vboregion);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteVboRegions() {
/* 246 */     for (ChunkCoordIntPair chunkcoordintpair : this.mapVboRegions.keySet()) {
/*     */       
/* 248 */       VboRegion[] avboregion = this.mapVboRegions.get(chunkcoordintpair);
/*     */       
/* 250 */       for (int i = 0; i < avboregion.length; i++) {
/*     */         
/* 252 */         VboRegion vboregion = avboregion[i];
/*     */         
/* 254 */         if (vboregion != null)
/*     */         {
/* 256 */           vboregion.deleteGlBuffers();
/*     */         }
/*     */         
/* 259 */         avboregion[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     this.mapVboRegions.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\ViewFrustum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */