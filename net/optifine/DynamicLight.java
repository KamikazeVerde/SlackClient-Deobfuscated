/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class DynamicLight
/*     */ {
/*  20 */   private Entity entity = null;
/*  21 */   private double offsetY = 0.0D;
/*  22 */   private double lastPosX = -2.147483648E9D;
/*  23 */   private double lastPosY = -2.147483648E9D;
/*  24 */   private double lastPosZ = -2.147483648E9D;
/*  25 */   private int lastLightLevel = 0;
/*     */   private boolean underwater = false;
/*  27 */   private long timeCheckMs = 0L;
/*  28 */   private Set<BlockPos> setLitChunkPos = new HashSet<>();
/*  29 */   private BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();
/*     */ 
/*     */   
/*     */   public DynamicLight(Entity entity) {
/*  33 */     this.entity = entity;
/*  34 */     this.offsetY = entity.getEyeHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(RenderGlobal renderGlobal) {
/*  39 */     if (Config.isDynamicLightsFast()) {
/*     */       
/*  41 */       long i = System.currentTimeMillis();
/*     */       
/*  43 */       if (i < this.timeCheckMs + 500L) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  48 */       this.timeCheckMs = i;
/*     */     } 
/*     */     
/*  51 */     double d6 = this.entity.posX - 0.5D;
/*  52 */     double d0 = this.entity.posY - 0.5D + this.offsetY;
/*  53 */     double d1 = this.entity.posZ - 0.5D;
/*  54 */     int j = DynamicLights.getLightLevel(this.entity);
/*  55 */     double d2 = d6 - this.lastPosX;
/*  56 */     double d3 = d0 - this.lastPosY;
/*  57 */     double d4 = d1 - this.lastPosZ;
/*  58 */     double d5 = 0.1D;
/*     */     
/*  60 */     if (Math.abs(d2) > d5 || Math.abs(d3) > d5 || Math.abs(d4) > d5 || this.lastLightLevel != j) {
/*     */       
/*  62 */       this.lastPosX = d6;
/*  63 */       this.lastPosY = d0;
/*  64 */       this.lastPosZ = d1;
/*  65 */       this.lastLightLevel = j;
/*  66 */       this.underwater = false;
/*  67 */       WorldClient worldClient = renderGlobal.getWorld();
/*     */       
/*  69 */       if (worldClient != null) {
/*     */         
/*  71 */         this.blockPosMutable.func_181079_c(MathHelper.floor_double(d6), MathHelper.floor_double(d0), MathHelper.floor_double(d1));
/*  72 */         IBlockState iblockstate = worldClient.getBlockState((BlockPos)this.blockPosMutable);
/*  73 */         Block block = iblockstate.getBlock();
/*  74 */         this.underwater = (block == Blocks.water);
/*     */       } 
/*     */       
/*  77 */       Set<BlockPos> set = new HashSet<>();
/*     */       
/*  79 */       if (j > 0) {
/*     */         
/*  81 */         EnumFacing enumfacing2 = ((MathHelper.floor_double(d6) & 0xF) >= 8) ? EnumFacing.EAST : EnumFacing.WEST;
/*  82 */         EnumFacing enumfacing = ((MathHelper.floor_double(d0) & 0xF) >= 8) ? EnumFacing.UP : EnumFacing.DOWN;
/*  83 */         EnumFacing enumfacing1 = ((MathHelper.floor_double(d1) & 0xF) >= 8) ? EnumFacing.SOUTH : EnumFacing.NORTH;
/*  84 */         BlockPos blockpos = new BlockPos(d6, d0, d1);
/*  85 */         RenderChunk renderchunk = renderGlobal.getRenderChunk(blockpos);
/*  86 */         BlockPos blockpos1 = getChunkPos(renderchunk, blockpos, enumfacing2);
/*  87 */         RenderChunk renderchunk1 = renderGlobal.getRenderChunk(blockpos1);
/*  88 */         BlockPos blockpos2 = getChunkPos(renderchunk, blockpos, enumfacing1);
/*  89 */         RenderChunk renderchunk2 = renderGlobal.getRenderChunk(blockpos2);
/*  90 */         BlockPos blockpos3 = getChunkPos(renderchunk1, blockpos1, enumfacing1);
/*  91 */         RenderChunk renderchunk3 = renderGlobal.getRenderChunk(blockpos3);
/*  92 */         BlockPos blockpos4 = getChunkPos(renderchunk, blockpos, enumfacing);
/*  93 */         RenderChunk renderchunk4 = renderGlobal.getRenderChunk(blockpos4);
/*  94 */         BlockPos blockpos5 = getChunkPos(renderchunk4, blockpos4, enumfacing2);
/*  95 */         RenderChunk renderchunk5 = renderGlobal.getRenderChunk(blockpos5);
/*  96 */         BlockPos blockpos6 = getChunkPos(renderchunk4, blockpos4, enumfacing1);
/*  97 */         RenderChunk renderchunk6 = renderGlobal.getRenderChunk(blockpos6);
/*  98 */         BlockPos blockpos7 = getChunkPos(renderchunk5, blockpos5, enumfacing1);
/*  99 */         RenderChunk renderchunk7 = renderGlobal.getRenderChunk(blockpos7);
/* 100 */         updateChunkLight(renderchunk, this.setLitChunkPos, set);
/* 101 */         updateChunkLight(renderchunk1, this.setLitChunkPos, set);
/* 102 */         updateChunkLight(renderchunk2, this.setLitChunkPos, set);
/* 103 */         updateChunkLight(renderchunk3, this.setLitChunkPos, set);
/* 104 */         updateChunkLight(renderchunk4, this.setLitChunkPos, set);
/* 105 */         updateChunkLight(renderchunk5, this.setLitChunkPos, set);
/* 106 */         updateChunkLight(renderchunk6, this.setLitChunkPos, set);
/* 107 */         updateChunkLight(renderchunk7, this.setLitChunkPos, set);
/*     */       } 
/*     */       
/* 110 */       updateLitChunks(renderGlobal);
/* 111 */       this.setLitChunkPos = set;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos getChunkPos(RenderChunk renderChunk, BlockPos pos, EnumFacing facing) {
/* 117 */     return (renderChunk != null) ? renderChunk.func_181701_a(facing) : pos.offset(facing, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateChunkLight(RenderChunk renderChunk, Set<BlockPos> setPrevPos, Set<BlockPos> setNewPos) {
/* 122 */     if (renderChunk != null) {
/*     */       
/* 124 */       CompiledChunk compiledchunk = renderChunk.getCompiledChunk();
/*     */       
/* 126 */       if (compiledchunk != null && !compiledchunk.isEmpty())
/*     */       {
/* 128 */         renderChunk.setNeedsUpdate(true);
/*     */       }
/*     */       
/* 131 */       BlockPos blockpos = renderChunk.getPosition();
/*     */       
/* 133 */       if (setPrevPos != null)
/*     */       {
/* 135 */         setPrevPos.remove(blockpos);
/*     */       }
/*     */       
/* 138 */       if (setNewPos != null)
/*     */       {
/* 140 */         setNewPos.add(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateLitChunks(RenderGlobal renderGlobal) {
/* 147 */     for (BlockPos blockpos : this.setLitChunkPos) {
/*     */       
/* 149 */       RenderChunk renderchunk = renderGlobal.getRenderChunk(blockpos);
/* 150 */       updateChunkLight(renderchunk, null, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getEntity() {
/* 156 */     return this.entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosX() {
/* 161 */     return this.lastPosX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosY() {
/* 166 */     return this.lastPosY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosZ() {
/* 171 */     return this.lastPosZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastLightLevel() {
/* 176 */     return this.lastLightLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderwater() {
/* 181 */     return this.underwater;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOffsetY() {
/* 186 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\DynamicLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */