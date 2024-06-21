/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCache;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.ViewFrustum;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.CustomBlockLayers;
/*     */ import net.optifine.override.ChunkCacheOF;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.render.AabbFrame;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ 
/*     */ public class RenderChunk
/*     */ {
/*     */   private final World world;
/*     */   private final RenderGlobal renderGlobal;
/*     */   public static int renderChunksUpdated;
/*     */   private BlockPos position;
/*  54 */   public CompiledChunk compiledChunk = CompiledChunk.DUMMY;
/*  55 */   private final ReentrantLock lockCompileTask = new ReentrantLock();
/*  56 */   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
/*  57 */   private ChunkCompileTaskGenerator compileTask = null;
/*  58 */   private final Set<TileEntity> field_181056_j = Sets.newHashSet();
/*     */   private final int index;
/*  60 */   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
/*  61 */   private final VertexBuffer[] vertexBuffers = new VertexBuffer[(EnumWorldBlockLayer.values()).length];
/*     */   public AxisAlignedBB boundingBox;
/*  63 */   private int frameIndex = -1;
/*     */   private boolean needsUpdate = true;
/*  65 */   private EnumMap<EnumFacing, BlockPos> field_181702_p = null;
/*  66 */   private BlockPos[] positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
/*  67 */   public static final EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
/*  68 */   private final EnumWorldBlockLayer[] blockLayersSingle = new EnumWorldBlockLayer[1];
/*  69 */   private final boolean isMipmaps = Config.isMipmaps();
/*  70 */   private final boolean fixBlockLayer = !Reflector.BetterFoliageClient.exists();
/*     */   private boolean playerUpdate = false;
/*     */   public int regionX;
/*     */   public int regionZ;
/*  74 */   private final RenderChunk[] renderChunksOfset16 = new RenderChunk[6];
/*     */   private boolean renderChunksOffset16Updated = false;
/*     */   private Chunk chunk;
/*  77 */   private RenderChunk[] renderChunkNeighbours = new RenderChunk[EnumFacing.VALUES.length];
/*  78 */   private RenderChunk[] renderChunkNeighboursValid = new RenderChunk[EnumFacing.VALUES.length];
/*     */   private boolean renderChunkNeighboursUpated = false;
/*  80 */   private RenderGlobal.ContainerLocalRenderInformation renderInfo = new RenderGlobal.ContainerLocalRenderInformation(this, null, 0);
/*     */   
/*     */   public AabbFrame boundingBoxParent;
/*     */   
/*     */   public RenderChunk(World worldIn, RenderGlobal renderGlobalIn, BlockPos blockPosIn, int indexIn) {
/*  85 */     this.world = worldIn;
/*  86 */     this.renderGlobal = renderGlobalIn;
/*  87 */     this.index = indexIn;
/*     */     
/*  89 */     if (!blockPosIn.equals(getPosition()))
/*     */     {
/*  91 */       setPosition(blockPosIn);
/*     */     }
/*     */     
/*  94 */     if (OpenGlHelper.useVbo())
/*     */     {
/*  96 */       for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++)
/*     */       {
/*  98 */         this.vertexBuffers[i] = new VertexBuffer(DefaultVertexFormats.BLOCK);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setFrameIndex(int frameIndexIn) {
/* 105 */     if (this.frameIndex == frameIndexIn)
/*     */     {
/* 107 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 111 */     this.frameIndex = frameIndexIn;
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexBuffer getVertexBufferByLayer(int layer) {
/* 118 */     return this.vertexBuffers[layer];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(BlockPos pos) {
/* 123 */     stopCompileTask();
/* 124 */     this.position = pos;
/* 125 */     int i = 8;
/* 126 */     this.regionX = pos.getX() >> i << i;
/* 127 */     this.regionZ = pos.getZ() >> i << i;
/* 128 */     this.boundingBox = new AxisAlignedBB(pos, pos.add(16, 16, 16));
/* 129 */     initModelviewMatrix();
/*     */     
/* 131 */     for (int j = 0; j < this.positionOffsets16.length; j++)
/*     */     {
/* 133 */       this.positionOffsets16[j] = null;
/*     */     }
/*     */     
/* 136 */     this.renderChunksOffset16Updated = false;
/* 137 */     this.renderChunkNeighboursUpated = false;
/*     */     
/* 139 */     for (int k = 0; k < this.renderChunkNeighbours.length; k++) {
/*     */       
/* 141 */       RenderChunk renderchunk = this.renderChunkNeighbours[k];
/*     */       
/* 143 */       if (renderchunk != null)
/*     */       {
/* 145 */         renderchunk.renderChunkNeighboursUpated = false;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     this.chunk = null;
/* 150 */     this.boundingBoxParent = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resortTransparency(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 155 */     CompiledChunk compiledchunk = generator.getCompiledChunk();
/*     */     
/* 157 */     if (compiledchunk.getState() != null && !compiledchunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
/*     */       
/* 159 */       WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
/* 160 */       preRenderBlocks(worldrenderer, this.position);
/* 161 */       worldrenderer.setVertexState(compiledchunk.getState());
/* 162 */       postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, x, y, z, worldrenderer, compiledchunk);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 168 */     CompiledChunk compiledchunk = new CompiledChunk();
/* 169 */     int i = 1;
/* 170 */     BlockPos blockpos = new BlockPos((Vec3i)this.position);
/* 171 */     BlockPos blockpos1 = blockpos.add(15, 15, 15);
/* 172 */     generator.getLock().lock();
/*     */ 
/*     */     
/*     */     try {
/* 176 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 181 */       generator.setCompiledChunk(compiledchunk);
/*     */     }
/*     */     finally {
/*     */       
/* 185 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/* 188 */     VisGraph lvt_10_1_ = new VisGraph();
/* 189 */     HashSet<TileEntity> lvt_11_1_ = Sets.newHashSet();
/*     */     
/* 191 */     if (!isChunkRegionEmpty(blockpos)) {
/*     */       
/* 193 */       renderChunksUpdated++;
/* 194 */       ChunkCacheOF chunkcacheof = makeChunkCacheOF(blockpos);
/* 195 */       chunkcacheof.renderStart();
/* 196 */       boolean[] aboolean = new boolean[ENUM_WORLD_BLOCK_LAYERS.length];
/* 197 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 198 */       boolean flag = Reflector.ForgeBlock_canRenderInLayer.exists();
/* 199 */       boolean flag1 = Reflector.ForgeHooksClient_setRenderLayer.exists();
/*     */       
/* 201 */       for (Object e : BlockPosM.getAllInBoxMutable(blockpos, blockpos1)) {
/*     */         EnumWorldBlockLayer[] aenumworldblocklayer;
/* 203 */         BlockPosM blockposm = (BlockPosM)e;
/* 204 */         IBlockState iblockstate = chunkcacheof.getBlockState((BlockPos)blockposm);
/* 205 */         Block block = iblockstate.getBlock();
/*     */         
/* 207 */         if (block.isOpaqueCube())
/*     */         {
/* 209 */           lvt_10_1_.func_178606_a((BlockPos)blockposm);
/*     */         }
/*     */         
/* 212 */         if (ReflectorForge.blockHasTileEntity(iblockstate)) {
/*     */           
/* 214 */           TileEntity tileentity = chunkcacheof.getTileEntity(new BlockPos((Vec3i)blockposm));
/* 215 */           TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tileentity);
/*     */           
/* 217 */           if (tileentity != null && tileentityspecialrenderer != null) {
/*     */             
/* 219 */             compiledchunk.addTileEntity(tileentity);
/*     */             
/* 221 */             if (tileentityspecialrenderer.func_181055_a())
/*     */             {
/* 223 */               lvt_11_1_.add(tileentity);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 230 */         if (flag) {
/*     */           
/* 232 */           aenumworldblocklayer = ENUM_WORLD_BLOCK_LAYERS;
/*     */         }
/*     */         else {
/*     */           
/* 236 */           aenumworldblocklayer = this.blockLayersSingle;
/* 237 */           aenumworldblocklayer[0] = block.getBlockLayer();
/*     */         } 
/*     */         
/* 240 */         for (int j = 0; j < aenumworldblocklayer.length; j++) {
/*     */           
/* 242 */           EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[j];
/*     */           
/* 244 */           if (flag) {
/*     */             
/* 246 */             boolean flag2 = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, new Object[] { enumworldblocklayer });
/*     */             
/* 248 */             if (!flag2) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 254 */           if (flag1)
/*     */           {
/* 256 */             Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { enumworldblocklayer });
/*     */           }
/*     */           
/* 259 */           enumworldblocklayer = fixBlockLayer(iblockstate, enumworldblocklayer);
/* 260 */           int k = enumworldblocklayer.ordinal();
/*     */           
/* 262 */           if (block.getRenderType() != -1) {
/*     */             
/* 264 */             WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(k);
/* 265 */             worldrenderer.setBlockLayer(enumworldblocklayer);
/* 266 */             RenderEnv renderenv = worldrenderer.getRenderEnv(iblockstate, (BlockPos)blockposm);
/* 267 */             renderenv.setRegionRenderCacheBuilder(generator.getRegionRenderCacheBuilder());
/*     */             
/* 269 */             if (!compiledchunk.isLayerStarted(enumworldblocklayer)) {
/*     */               
/* 271 */               compiledchunk.setLayerStarted(enumworldblocklayer);
/* 272 */               preRenderBlocks(worldrenderer, blockpos);
/*     */             } 
/*     */             
/* 275 */             aboolean[k] = aboolean[k] | blockrendererdispatcher.renderBlock(iblockstate, (BlockPos)blockposm, (IBlockAccess)chunkcacheof, worldrenderer);
/*     */             
/* 277 */             if (renderenv.isOverlaysRendered()) {
/*     */               
/* 279 */               postRenderOverlays(generator.getRegionRenderCacheBuilder(), compiledchunk, aboolean);
/* 280 */               renderenv.setOverlaysRendered(false);
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 285 */         if (flag1)
/*     */         {
/* 287 */           Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { null });
/*     */         }
/*     */       } 
/*     */       
/* 291 */       for (EnumWorldBlockLayer enumworldblocklayer1 : ENUM_WORLD_BLOCK_LAYERS) {
/*     */         
/* 293 */         if (aboolean[enumworldblocklayer1.ordinal()])
/*     */         {
/* 295 */           compiledchunk.setLayerUsed(enumworldblocklayer1);
/*     */         }
/*     */         
/* 298 */         if (compiledchunk.isLayerStarted(enumworldblocklayer1)) {
/*     */           
/* 300 */           if (Config.isShaders())
/*     */           {
/* 302 */             SVertexBuilder.calcNormalChunkLayer(generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1));
/*     */           }
/*     */           
/* 305 */           WorldRenderer worldrenderer1 = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1);
/* 306 */           postRenderBlocks(enumworldblocklayer1, x, y, z, worldrenderer1, compiledchunk);
/*     */           
/* 308 */           if (worldrenderer1.animatedSprites != null)
/*     */           {
/* 310 */             compiledchunk.setAnimatedSprites(enumworldblocklayer1, (BitSet)worldrenderer1.animatedSprites.clone());
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 315 */           compiledchunk.setAnimatedSprites(enumworldblocklayer1, null);
/*     */         } 
/*     */       } 
/*     */       
/* 319 */       chunkcacheof.renderFinish();
/*     */     } 
/*     */     
/* 322 */     compiledchunk.setVisibility(lvt_10_1_.computeVisibility());
/* 323 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 327 */       Set<TileEntity> set = Sets.newHashSet(lvt_11_1_);
/* 328 */       Set<TileEntity> set1 = Sets.newHashSet(this.field_181056_j);
/* 329 */       set.removeAll(this.field_181056_j);
/* 330 */       set1.removeAll(lvt_11_1_);
/* 331 */       this.field_181056_j.clear();
/* 332 */       this.field_181056_j.addAll(lvt_11_1_);
/* 333 */       this.renderGlobal.func_181023_a(set1, set);
/*     */     }
/*     */     finally {
/*     */       
/* 337 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finishCompileTask() {
/* 343 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 347 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE)
/*     */       {
/* 349 */         this.compileTask.finish();
/* 350 */         this.compileTask = null;
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 355 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLockCompileTask() {
/* 361 */     return this.lockCompileTask;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator;
/* 366 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 371 */       finishCompileTask();
/* 372 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
/* 373 */       chunkcompiletaskgenerator = this.compileTask;
/*     */     }
/*     */     finally {
/*     */       
/* 377 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 380 */     return chunkcompiletaskgenerator;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator1;
/* 385 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 390 */       if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING) {
/*     */         
/* 392 */         ChunkCompileTaskGenerator chunkcompiletaskgenerator2 = null;
/* 393 */         return chunkcompiletaskgenerator2;
/*     */       } 
/*     */       
/* 396 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
/*     */         
/* 398 */         this.compileTask.finish();
/* 399 */         this.compileTask = null;
/*     */       } 
/*     */       
/* 402 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
/* 403 */       this.compileTask.setCompiledChunk(this.compiledChunk);
/* 404 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.compileTask;
/* 405 */       chunkcompiletaskgenerator1 = chunkcompiletaskgenerator;
/*     */     }
/*     */     finally {
/*     */       
/* 409 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 412 */     return chunkcompiletaskgenerator1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void preRenderBlocks(WorldRenderer worldRendererIn, BlockPos pos) {
/* 417 */     worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/*     */     
/* 419 */     if (Config.isRenderRegions()) {
/*     */       
/* 421 */       int i = 8;
/* 422 */       int j = pos.getX() >> i << i;
/* 423 */       int k = pos.getY() >> i << i;
/* 424 */       int l = pos.getZ() >> i << i;
/* 425 */       j = this.regionX;
/* 426 */       l = this.regionZ;
/* 427 */       worldRendererIn.setTranslation(-j, -k, -l);
/*     */     }
/*     */     else {
/*     */       
/* 431 */       worldRendererIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderBlocks(EnumWorldBlockLayer layer, float x, float y, float z, WorldRenderer worldRendererIn, CompiledChunk compiledChunkIn) {
/* 437 */     if (layer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunkIn.isLayerEmpty(layer)) {
/*     */       
/* 439 */       worldRendererIn.func_181674_a(x, y, z);
/* 440 */       compiledChunkIn.setState(worldRendererIn.func_181672_a());
/*     */     } 
/*     */     
/* 443 */     worldRendererIn.finishDrawing();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initModelviewMatrix() {
/* 448 */     GlStateManager.pushMatrix();
/* 449 */     GlStateManager.loadIdentity();
/* 450 */     float f = 1.000001F;
/* 451 */     GlStateManager.translate(-8.0F, -8.0F, -8.0F);
/* 452 */     GlStateManager.scale(f, f, f);
/* 453 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 454 */     GlStateManager.getFloat(2982, this.modelviewMatrix);
/* 455 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void multModelviewMatrix() {
/* 460 */     GlStateManager.multMatrix(this.modelviewMatrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/* 465 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/* 470 */     this.lockCompiledChunk.lock();
/*     */ 
/*     */     
/*     */     try {
/* 474 */       this.compiledChunk = compiledChunkIn;
/*     */     }
/*     */     finally {
/*     */       
/* 478 */       this.lockCompiledChunk.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopCompileTask() {
/* 484 */     finishCompileTask();
/* 485 */     this.compiledChunk = CompiledChunk.DUMMY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlResources() {
/* 490 */     stopCompileTask();
/*     */     
/* 492 */     for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++) {
/*     */       
/* 494 */       if (this.vertexBuffers[i] != null)
/*     */       {
/* 496 */         this.vertexBuffers[i].deleteGlBuffers();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 503 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedsUpdate(boolean needsUpdateIn) {
/* 508 */     this.needsUpdate = needsUpdateIn;
/*     */     
/* 510 */     if (needsUpdateIn) {
/*     */       
/* 512 */       if (isWorldPlayerUpdate())
/*     */       {
/* 514 */         this.playerUpdate = true;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 519 */       this.playerUpdate = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNeedsUpdate() {
/* 525 */     return this.needsUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos func_181701_a(EnumFacing p_181701_1_) {
/* 530 */     return getPositionOffset16(p_181701_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPositionOffset16(EnumFacing p_getPositionOffset16_1_) {
/* 535 */     int i = p_getPositionOffset16_1_.getIndex();
/* 536 */     BlockPos blockpos = this.positionOffsets16[i];
/*     */     
/* 538 */     if (blockpos == null) {
/*     */       
/* 540 */       blockpos = getPosition().offset(p_getPositionOffset16_1_, 16);
/* 541 */       this.positionOffsets16[i] = blockpos;
/*     */     } 
/*     */     
/* 544 */     return blockpos;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWorldPlayerUpdate() {
/* 549 */     if (this.world instanceof WorldClient) {
/*     */       
/* 551 */       WorldClient worldclient = (WorldClient)this.world;
/* 552 */       return worldclient.isPlayerUpdate();
/*     */     } 
/*     */ 
/*     */     
/* 556 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 562 */     return this.playerUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   protected RegionRenderCache createRegionRenderCache(World p_createRegionRenderCache_1_, BlockPos p_createRegionRenderCache_2_, BlockPos p_createRegionRenderCache_3_, int p_createRegionRenderCache_4_) {
/* 567 */     return new RegionRenderCache(p_createRegionRenderCache_1_, p_createRegionRenderCache_2_, p_createRegionRenderCache_3_, p_createRegionRenderCache_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumWorldBlockLayer fixBlockLayer(IBlockState p_fixBlockLayer_1_, EnumWorldBlockLayer p_fixBlockLayer_2_) {
/* 572 */     if (CustomBlockLayers.isActive()) {
/*     */       
/* 574 */       EnumWorldBlockLayer enumworldblocklayer = CustomBlockLayers.getRenderLayer(p_fixBlockLayer_1_);
/*     */       
/* 576 */       if (enumworldblocklayer != null)
/*     */       {
/* 578 */         return enumworldblocklayer;
/*     */       }
/*     */     } 
/*     */     
/* 582 */     if (!this.fixBlockLayer)
/*     */     {
/* 584 */       return p_fixBlockLayer_2_;
/*     */     }
/*     */ 
/*     */     
/* 588 */     if (this.isMipmaps) {
/*     */       
/* 590 */       if (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT)
/*     */       {
/* 592 */         Block block = p_fixBlockLayer_1_.getBlock();
/*     */         
/* 594 */         if (block instanceof net.minecraft.block.BlockRedstoneWire)
/*     */         {
/* 596 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 599 */         if (block instanceof net.minecraft.block.BlockCactus)
/*     */         {
/* 601 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 604 */         return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */       }
/*     */     
/* 607 */     } else if (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT_MIPPED) {
/*     */       
/* 609 */       return EnumWorldBlockLayer.CUTOUT;
/*     */     } 
/*     */     
/* 612 */     return p_fixBlockLayer_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void postRenderOverlays(RegionRenderCacheBuilder p_postRenderOverlays_1_, CompiledChunk p_postRenderOverlays_2_, boolean[] p_postRenderOverlays_3_) {
/* 618 */     postRenderOverlay(EnumWorldBlockLayer.CUTOUT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 619 */     postRenderOverlay(EnumWorldBlockLayer.CUTOUT_MIPPED, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 620 */     postRenderOverlay(EnumWorldBlockLayer.TRANSLUCENT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderOverlay(EnumWorldBlockLayer p_postRenderOverlay_1_, RegionRenderCacheBuilder p_postRenderOverlay_2_, CompiledChunk p_postRenderOverlay_3_, boolean[] p_postRenderOverlay_4_) {
/* 625 */     WorldRenderer worldrenderer = p_postRenderOverlay_2_.getWorldRendererByLayer(p_postRenderOverlay_1_);
/*     */     
/* 627 */     if (worldrenderer.isDrawing()) {
/*     */       
/* 629 */       p_postRenderOverlay_3_.setLayerStarted(p_postRenderOverlay_1_);
/* 630 */       p_postRenderOverlay_4_[p_postRenderOverlay_1_.ordinal()] = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkCacheOF makeChunkCacheOF(BlockPos p_makeChunkCacheOF_1_) {
/* 636 */     BlockPos blockpos = p_makeChunkCacheOF_1_.add(-1, -1, -1);
/* 637 */     BlockPos blockpos1 = p_makeChunkCacheOF_1_.add(16, 16, 16);
/* 638 */     RegionRenderCache regionRenderCache = createRegionRenderCache(this.world, blockpos, blockpos1, 1);
/*     */     
/* 640 */     if (Reflector.MinecraftForgeClient_onRebuildChunk.exists())
/*     */     {
/* 642 */       Reflector.call(Reflector.MinecraftForgeClient_onRebuildChunk, new Object[] { this.world, p_makeChunkCacheOF_1_, regionRenderCache });
/*     */     }
/*     */     
/* 645 */     ChunkCacheOF chunkcacheof = new ChunkCacheOF((ChunkCache)regionRenderCache, blockpos, blockpos1, 1);
/* 646 */     return chunkcacheof;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunkOffset16(ViewFrustum p_getRenderChunkOffset16_1_, EnumFacing p_getRenderChunkOffset16_2_) {
/* 651 */     if (!this.renderChunksOffset16Updated) {
/*     */       
/* 653 */       for (int i = 0; i < EnumFacing.VALUES.length; i++) {
/*     */         
/* 655 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 656 */         BlockPos blockpos = func_181701_a(enumfacing);
/* 657 */         this.renderChunksOfset16[i] = p_getRenderChunkOffset16_1_.getRenderChunk(blockpos);
/*     */       } 
/*     */       
/* 660 */       this.renderChunksOffset16Updated = true;
/*     */     } 
/*     */     
/* 663 */     return this.renderChunksOfset16[p_getRenderChunkOffset16_2_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getChunk() {
/* 668 */     return getChunk(this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   private Chunk getChunk(BlockPos p_getChunk_1_) {
/* 673 */     Chunk chunk = this.chunk;
/*     */     
/* 675 */     if (chunk != null && chunk.isLoaded())
/*     */     {
/* 677 */       return chunk;
/*     */     }
/*     */ 
/*     */     
/* 681 */     chunk = this.world.getChunkFromBlockCoords(p_getChunk_1_);
/* 682 */     this.chunk = chunk;
/* 683 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChunkRegionEmpty() {
/* 689 */     return isChunkRegionEmpty(this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChunkRegionEmpty(BlockPos p_isChunkRegionEmpty_1_) {
/* 694 */     int i = p_isChunkRegionEmpty_1_.getY();
/* 695 */     int j = i + 15;
/* 696 */     return getChunk(p_isChunkRegionEmpty_1_).getAreLevelsEmpty(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderChunkNeighbour(EnumFacing p_setRenderChunkNeighbour_1_, RenderChunk p_setRenderChunkNeighbour_2_) {
/* 701 */     this.renderChunkNeighbours[p_setRenderChunkNeighbour_1_.ordinal()] = p_setRenderChunkNeighbour_2_;
/* 702 */     this.renderChunkNeighboursValid[p_setRenderChunkNeighbour_1_.ordinal()] = p_setRenderChunkNeighbour_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunkNeighbour(EnumFacing p_getRenderChunkNeighbour_1_) {
/* 707 */     if (!this.renderChunkNeighboursUpated)
/*     */     {
/* 709 */       updateRenderChunkNeighboursValid();
/*     */     }
/*     */     
/* 712 */     return this.renderChunkNeighboursValid[p_getRenderChunkNeighbour_1_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderGlobal.ContainerLocalRenderInformation getRenderInfo() {
/* 717 */     return this.renderInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateRenderChunkNeighboursValid() {
/* 722 */     int i = getPosition().getX();
/* 723 */     int j = getPosition().getZ();
/* 724 */     int k = EnumFacing.NORTH.ordinal();
/* 725 */     int l = EnumFacing.SOUTH.ordinal();
/* 726 */     int i1 = EnumFacing.WEST.ordinal();
/* 727 */     int j1 = EnumFacing.EAST.ordinal();
/* 728 */     this.renderChunkNeighboursValid[k] = (this.renderChunkNeighbours[k].getPosition().getZ() == j - 16) ? this.renderChunkNeighbours[k] : null;
/* 729 */     this.renderChunkNeighboursValid[l] = (this.renderChunkNeighbours[l].getPosition().getZ() == j + 16) ? this.renderChunkNeighbours[l] : null;
/* 730 */     this.renderChunkNeighboursValid[i1] = (this.renderChunkNeighbours[i1].getPosition().getX() == i - 16) ? this.renderChunkNeighbours[i1] : null;
/* 731 */     this.renderChunkNeighboursValid[j1] = (this.renderChunkNeighbours[j1].getPosition().getX() == i + 16) ? this.renderChunkNeighbours[j1] : null;
/* 732 */     this.renderChunkNeighboursUpated = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBoundingBoxInFrustum(ICamera p_isBoundingBoxInFrustum_1_, int p_isBoundingBoxInFrustum_2_) {
/* 737 */     return getBoundingBoxParent().isBoundingBoxInFrustumFully(p_isBoundingBoxInFrustum_1_, p_isBoundingBoxInFrustum_2_) ? true : p_isBoundingBoxInFrustum_1_.isBoundingBoxInFrustum(this.boundingBox);
/*     */   }
/*     */ 
/*     */   
/*     */   public AabbFrame getBoundingBoxParent() {
/* 742 */     if (this.boundingBoxParent == null) {
/*     */       
/* 744 */       BlockPos blockpos = getPosition();
/* 745 */       int i = blockpos.getX();
/* 746 */       int j = blockpos.getY();
/* 747 */       int k = blockpos.getZ();
/* 748 */       int l = 5;
/* 749 */       int i1 = i >> l << l;
/* 750 */       int j1 = j >> l << l;
/* 751 */       int k1 = k >> l << l;
/*     */       
/* 753 */       if (i1 != i || j1 != j || k1 != k) {
/*     */         
/* 755 */         AabbFrame aabbframe = this.renderGlobal.getRenderChunk(new BlockPos(i1, j1, k1)).getBoundingBoxParent();
/*     */         
/* 757 */         if (aabbframe != null && aabbframe.minX == i1 && aabbframe.minY == j1 && aabbframe.minZ == k1)
/*     */         {
/* 759 */           this.boundingBoxParent = aabbframe;
/*     */         }
/*     */       } 
/*     */       
/* 763 */       if (this.boundingBoxParent == null) {
/*     */         
/* 765 */         int l1 = 1 << l;
/* 766 */         this.boundingBoxParent = new AabbFrame(i1, j1, k1, (i1 + l1), (j1 + l1), (k1 + l1));
/*     */       } 
/*     */     } 
/*     */     
/* 770 */     return this.boundingBoxParent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 775 */     return "pos: " + getPosition() + ", frameIndex: " + this.frameIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\chunk\RenderChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */