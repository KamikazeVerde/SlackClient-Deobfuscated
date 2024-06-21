/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.BetterSnow;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.model.BlockModelCustomizer;
/*     */ import net.optifine.model.ListQuadsOverlay;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class BlockModelRenderer
/*     */ {
/*  33 */   private static float aoLightValueOpaque = 0.2F;
/*     */   private static boolean separateAoLightValue = false;
/*  35 */   private static final EnumWorldBlockLayer[] OVERLAY_LAYERS = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.TRANSLUCENT };
/*     */ 
/*     */   
/*     */   public BlockModelRenderer() {
/*  39 */     if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists())
/*     */     {
/*  41 */       Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.FALSE);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
/*  47 */     Block block = blockStateIn.getBlock();
/*  48 */     block.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
/*  49 */     return renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/*  54 */     boolean flag = (Minecraft.isAmbientOcclusionEnabled() && blockStateIn.getBlock().getLightValue() == 0 && modelIn.isAmbientOcclusion());
/*     */ 
/*     */     
/*     */     try {
/*  58 */       if (Config.isShaders())
/*     */       {
/*  60 */         SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccessIn, worldRendererIn);
/*     */       }
/*     */       
/*  63 */       RenderEnv renderenv = worldRendererIn.getRenderEnv(blockStateIn, blockPosIn);
/*  64 */       modelIn = BlockModelCustomizer.getRenderModel(modelIn, blockStateIn, renderenv);
/*  65 */       boolean flag1 = flag ? renderModelSmooth(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides) : renderModelFlat(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides);
/*     */       
/*  67 */       if (flag1)
/*     */       {
/*  69 */         renderOverlayModels(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides, 0L, renderenv, flag);
/*     */       }
/*     */       
/*  72 */       if (Config.isShaders())
/*     */       {
/*  74 */         SVertexBuilder.popEntity(worldRendererIn);
/*     */       }
/*     */       
/*  77 */       return flag1;
/*     */     }
/*  79 */     catch (Throwable throwable) {
/*     */       
/*  81 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block model");
/*  82 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
/*  83 */       CrashReportCategory.addBlockInfo(crashreportcategory, blockPosIn, blockStateIn);
/*  84 */       crashreportcategory.addCrashSection("Using AO", Boolean.valueOf(flag));
/*  85 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelAmbientOcclusion(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/*  91 */     IBlockState iblockstate = blockAccessIn.getBlockState(blockPosIn);
/*  92 */     return renderModelSmooth(blockAccessIn, modelIn, iblockstate, blockPosIn, worldRendererIn, checkSides);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean renderModelSmooth(IBlockAccess p_renderModelSmooth_1_, IBakedModel p_renderModelSmooth_2_, IBlockState p_renderModelSmooth_3_, BlockPos p_renderModelSmooth_4_, WorldRenderer p_renderModelSmooth_5_, boolean p_renderModelSmooth_6_) {
/*  97 */     boolean flag = false;
/*  98 */     Block block = p_renderModelSmooth_3_.getBlock();
/*  99 */     RenderEnv renderenv = p_renderModelSmooth_5_.getRenderEnv(p_renderModelSmooth_3_, p_renderModelSmooth_4_);
/* 100 */     EnumWorldBlockLayer enumworldblocklayer = p_renderModelSmooth_5_.getBlockLayer();
/*     */     
/* 102 */     for (EnumFacing enumfacing : EnumFacing.VALUES) {
/*     */       
/* 104 */       List<BakedQuad> list = p_renderModelSmooth_2_.getFaceQuads(enumfacing);
/*     */       
/* 106 */       if (!list.isEmpty()) {
/*     */         
/* 108 */         BlockPos blockpos = p_renderModelSmooth_4_.offset(enumfacing);
/*     */         
/* 110 */         if (!p_renderModelSmooth_6_ || block.shouldSideBeRendered(p_renderModelSmooth_1_, blockpos, enumfacing)) {
/*     */           
/* 112 */           list = BlockModelCustomizer.getRenderQuads(list, p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, enumfacing, enumworldblocklayer, 0L, renderenv);
/* 113 */           renderQuadsSmooth(p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, p_renderModelSmooth_5_, list, renderenv);
/* 114 */           flag = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     List<BakedQuad> list1 = p_renderModelSmooth_2_.getGeneralQuads();
/*     */     
/* 121 */     if (list1.size() > 0) {
/*     */       
/* 123 */       list1 = BlockModelCustomizer.getRenderQuads(list1, p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, null, enumworldblocklayer, 0L, renderenv);
/* 124 */       renderQuadsSmooth(p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, p_renderModelSmooth_5_, list1, renderenv);
/* 125 */       flag = true;
/*     */     } 
/*     */     
/* 128 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelStandard(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/* 133 */     IBlockState iblockstate = blockAccessIn.getBlockState(blockPosIn);
/* 134 */     return renderModelFlat(blockAccessIn, modelIn, iblockstate, blockPosIn, worldRendererIn, checkSides);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelFlat(IBlockAccess p_renderModelFlat_1_, IBakedModel p_renderModelFlat_2_, IBlockState p_renderModelFlat_3_, BlockPos p_renderModelFlat_4_, WorldRenderer p_renderModelFlat_5_, boolean p_renderModelFlat_6_) {
/* 139 */     boolean flag = false;
/* 140 */     Block block = p_renderModelFlat_3_.getBlock();
/* 141 */     RenderEnv renderenv = p_renderModelFlat_5_.getRenderEnv(p_renderModelFlat_3_, p_renderModelFlat_4_);
/* 142 */     EnumWorldBlockLayer enumworldblocklayer = p_renderModelFlat_5_.getBlockLayer();
/*     */     
/* 144 */     for (EnumFacing enumfacing : EnumFacing.VALUES) {
/*     */       
/* 146 */       List<BakedQuad> list = p_renderModelFlat_2_.getFaceQuads(enumfacing);
/*     */       
/* 148 */       if (!list.isEmpty()) {
/*     */         
/* 150 */         BlockPos blockpos = p_renderModelFlat_4_.offset(enumfacing);
/*     */         
/* 152 */         if (!p_renderModelFlat_6_ || block.shouldSideBeRendered(p_renderModelFlat_1_, blockpos, enumfacing)) {
/*     */           
/* 154 */           int i = block.getMixedBrightnessForBlock(p_renderModelFlat_1_, blockpos);
/* 155 */           list = BlockModelCustomizer.getRenderQuads(list, p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, enumfacing, enumworldblocklayer, 0L, renderenv);
/* 156 */           renderQuadsFlat(p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, enumfacing, i, false, p_renderModelFlat_5_, list, renderenv);
/* 157 */           flag = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     List<BakedQuad> list1 = p_renderModelFlat_2_.getGeneralQuads();
/*     */     
/* 164 */     if (list1.size() > 0) {
/*     */       
/* 166 */       list1 = BlockModelCustomizer.getRenderQuads(list1, p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, null, enumworldblocklayer, 0L, renderenv);
/* 167 */       renderQuadsFlat(p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, null, -1, true, p_renderModelFlat_5_, list1, renderenv);
/* 168 */       flag = true;
/*     */     } 
/*     */     
/* 171 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderQuadsSmooth(IBlockAccess p_renderQuadsSmooth_1_, IBlockState p_renderQuadsSmooth_2_, BlockPos p_renderQuadsSmooth_3_, WorldRenderer p_renderQuadsSmooth_4_, List<BakedQuad> p_renderQuadsSmooth_5_, RenderEnv p_renderQuadsSmooth_6_) {
/* 176 */     Block block = p_renderQuadsSmooth_2_.getBlock();
/* 177 */     float[] afloat = p_renderQuadsSmooth_6_.getQuadBounds();
/* 178 */     BitSet bitset = p_renderQuadsSmooth_6_.getBoundsFlags();
/* 179 */     AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = p_renderQuadsSmooth_6_.getAoFace();
/* 180 */     double d0 = p_renderQuadsSmooth_3_.getX();
/* 181 */     double d1 = p_renderQuadsSmooth_3_.getY();
/* 182 */     double d2 = p_renderQuadsSmooth_3_.getZ();
/* 183 */     Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*     */     
/* 185 */     if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
/*     */       
/* 187 */       long i = MathHelper.getPositionRandom((Vec3i)p_renderQuadsSmooth_3_);
/* 188 */       d0 += (((float)(i >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 189 */       d2 += (((float)(i >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/*     */       
/* 191 */       if (block$enumoffsettype == Block.EnumOffsetType.XYZ)
/*     */       {
/* 193 */         d1 += (((float)(i >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     for (BakedQuad bakedquad : p_renderQuadsSmooth_5_) {
/*     */       
/* 199 */       fillQuadBounds(block, bakedquad.getVertexData(), bakedquad.getFace(), afloat, bitset);
/* 200 */       blockmodelrenderer$ambientocclusionface.updateVertexBrightness(p_renderQuadsSmooth_1_, block, p_renderQuadsSmooth_3_, bakedquad.getFace(), afloat, bitset);
/*     */       
/* 202 */       if ((bakedquad.getSprite()).isEmissive)
/*     */       {
/* 204 */         blockmodelrenderer$ambientocclusionface.setMaxBlockLight();
/*     */       }
/*     */       
/* 207 */       if (p_renderQuadsSmooth_4_.isMultiTexture()) {
/*     */         
/* 209 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexDataSingle());
/*     */       }
/*     */       else {
/*     */         
/* 213 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 216 */       p_renderQuadsSmooth_4_.putSprite(bakedquad.getSprite());
/* 217 */       p_renderQuadsSmooth_4_.putBrightness4(blockmodelrenderer$ambientocclusionface.vertexBrightness[0], blockmodelrenderer$ambientocclusionface.vertexBrightness[1], blockmodelrenderer$ambientocclusionface.vertexBrightness[2], blockmodelrenderer$ambientocclusionface.vertexBrightness[3]);
/* 218 */       int j = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, p_renderQuadsSmooth_6_);
/*     */       
/* 220 */       if (!bakedquad.hasTintIndex() && j == -1) {
/*     */         
/* 222 */         if (separateAoLightValue) {
/*     */           
/* 224 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 225 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 226 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 227 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         }
/*     */         else {
/*     */           
/* 231 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 232 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 233 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 234 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         } 
/*     */       } else {
/*     */         int k;
/*     */ 
/*     */ 
/*     */         
/* 241 */         if (j != -1) {
/*     */           
/* 243 */           k = j;
/*     */         }
/*     */         else {
/*     */           
/* 247 */           k = block.colorMultiplier(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, bakedquad.getTintIndex());
/*     */         } 
/*     */         
/* 250 */         if (EntityRenderer.anaglyphEnable)
/*     */         {
/* 252 */           k = TextureUtil.anaglyphColor(k);
/*     */         }
/*     */         
/* 255 */         float f = (k >> 16 & 0xFF) / 255.0F;
/* 256 */         float f1 = (k >> 8 & 0xFF) / 255.0F;
/* 257 */         float f2 = (k & 0xFF) / 255.0F;
/*     */         
/* 259 */         if (separateAoLightValue) {
/*     */           
/* 261 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 262 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 263 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 264 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         }
/*     */         else {
/*     */           
/* 268 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f2, 4);
/* 269 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f2, 3);
/* 270 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f2, 2);
/* 271 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f2, 1);
/*     */         } 
/*     */       } 
/*     */       
/* 275 */       p_renderQuadsSmooth_4_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillQuadBounds(Block blockIn, int[] vertexData, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) {
/* 281 */     float f = 32.0F;
/* 282 */     float f1 = 32.0F;
/* 283 */     float f2 = 32.0F;
/* 284 */     float f3 = -32.0F;
/* 285 */     float f4 = -32.0F;
/* 286 */     float f5 = -32.0F;
/* 287 */     int i = vertexData.length / 4;
/*     */     
/* 289 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 291 */       float f6 = Float.intBitsToFloat(vertexData[j * i]);
/* 292 */       float f7 = Float.intBitsToFloat(vertexData[j * i + 1]);
/* 293 */       float f8 = Float.intBitsToFloat(vertexData[j * i + 2]);
/* 294 */       f = Math.min(f, f6);
/* 295 */       f1 = Math.min(f1, f7);
/* 296 */       f2 = Math.min(f2, f8);
/* 297 */       f3 = Math.max(f3, f6);
/* 298 */       f4 = Math.max(f4, f7);
/* 299 */       f5 = Math.max(f5, f8);
/*     */     } 
/*     */     
/* 302 */     if (quadBounds != null) {
/*     */       
/* 304 */       quadBounds[EnumFacing.WEST.getIndex()] = f;
/* 305 */       quadBounds[EnumFacing.EAST.getIndex()] = f3;
/* 306 */       quadBounds[EnumFacing.DOWN.getIndex()] = f1;
/* 307 */       quadBounds[EnumFacing.UP.getIndex()] = f4;
/* 308 */       quadBounds[EnumFacing.NORTH.getIndex()] = f2;
/* 309 */       quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
/* 310 */       int k = EnumFacing.VALUES.length;
/* 311 */       quadBounds[EnumFacing.WEST.getIndex() + k] = 1.0F - f;
/* 312 */       quadBounds[EnumFacing.EAST.getIndex() + k] = 1.0F - f3;
/* 313 */       quadBounds[EnumFacing.DOWN.getIndex() + k] = 1.0F - f1;
/* 314 */       quadBounds[EnumFacing.UP.getIndex() + k] = 1.0F - f4;
/* 315 */       quadBounds[EnumFacing.NORTH.getIndex() + k] = 1.0F - f2;
/* 316 */       quadBounds[EnumFacing.SOUTH.getIndex() + k] = 1.0F - f5;
/*     */     } 
/*     */     
/* 319 */     float f9 = 1.0E-4F;
/* 320 */     float f10 = 0.9999F;
/*     */     
/* 322 */     switch (facingIn) {
/*     */       
/*     */       case DOWN:
/* 325 */         boundsFlags.set(1, (f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F));
/* 326 */         boundsFlags.set(0, ((f1 < 1.0E-4F || blockIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case UP:
/* 330 */         boundsFlags.set(1, (f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F));
/* 331 */         boundsFlags.set(0, ((f4 > 0.9999F || blockIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 335 */         boundsFlags.set(1, (f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F));
/* 336 */         boundsFlags.set(0, ((f2 < 1.0E-4F || blockIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 340 */         boundsFlags.set(1, (f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F));
/* 341 */         boundsFlags.set(0, ((f5 > 0.9999F || blockIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case WEST:
/* 345 */         boundsFlags.set(1, (f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F));
/* 346 */         boundsFlags.set(0, ((f < 1.0E-4F || blockIn.isFullCube()) && f == f3));
/*     */         break;
/*     */       
/*     */       case EAST:
/* 350 */         boundsFlags.set(1, (f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F));
/* 351 */         boundsFlags.set(0, ((f3 > 0.9999F || blockIn.isFullCube()) && f == f3));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderQuadsFlat(IBlockAccess p_renderQuadsFlat_1_, IBlockState p_renderQuadsFlat_2_, BlockPos p_renderQuadsFlat_3_, EnumFacing p_renderQuadsFlat_4_, int p_renderQuadsFlat_5_, boolean p_renderQuadsFlat_6_, WorldRenderer p_renderQuadsFlat_7_, List<BakedQuad> p_renderQuadsFlat_8_, RenderEnv p_renderQuadsFlat_9_) {
/* 357 */     Block block = p_renderQuadsFlat_2_.getBlock();
/* 358 */     BitSet bitset = p_renderQuadsFlat_9_.getBoundsFlags();
/* 359 */     double d0 = p_renderQuadsFlat_3_.getX();
/* 360 */     double d1 = p_renderQuadsFlat_3_.getY();
/* 361 */     double d2 = p_renderQuadsFlat_3_.getZ();
/* 362 */     Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*     */     
/* 364 */     if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
/*     */       
/* 366 */       int i = p_renderQuadsFlat_3_.getX();
/* 367 */       int j = p_renderQuadsFlat_3_.getZ();
/* 368 */       long k = (i * 3129871) ^ j * 116129781L;
/* 369 */       k = k * k * 42317861L + k * 11L;
/* 370 */       d0 += (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 371 */       d2 += (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/*     */       
/* 373 */       if (block$enumoffsettype == Block.EnumOffsetType.XYZ)
/*     */       {
/* 375 */         d1 += (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */       }
/*     */     } 
/*     */     
/* 379 */     for (BakedQuad bakedquad : p_renderQuadsFlat_8_) {
/*     */       
/* 381 */       if (p_renderQuadsFlat_6_) {
/*     */         
/* 383 */         fillQuadBounds(block, bakedquad.getVertexData(), bakedquad.getFace(), null, bitset);
/* 384 */         p_renderQuadsFlat_5_ = bitset.get(0) ? block.getMixedBrightnessForBlock(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_.offset(bakedquad.getFace())) : block.getMixedBrightnessForBlock(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_);
/*     */       } 
/*     */       
/* 387 */       if ((bakedquad.getSprite()).isEmissive)
/*     */       {
/* 389 */         p_renderQuadsFlat_5_ |= 0xF0;
/*     */       }
/*     */       
/* 392 */       if (p_renderQuadsFlat_7_.isMultiTexture()) {
/*     */         
/* 394 */         p_renderQuadsFlat_7_.addVertexData(bakedquad.getVertexDataSingle());
/*     */       }
/*     */       else {
/*     */         
/* 398 */         p_renderQuadsFlat_7_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 401 */       p_renderQuadsFlat_7_.putSprite(bakedquad.getSprite());
/* 402 */       p_renderQuadsFlat_7_.putBrightness4(p_renderQuadsFlat_5_, p_renderQuadsFlat_5_, p_renderQuadsFlat_5_, p_renderQuadsFlat_5_);
/* 403 */       int i1 = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, p_renderQuadsFlat_9_);
/*     */       
/* 405 */       if (bakedquad.hasTintIndex() || i1 != -1) {
/*     */         int l;
/*     */ 
/*     */         
/* 409 */         if (i1 != -1) {
/*     */           
/* 411 */           l = i1;
/*     */         }
/*     */         else {
/*     */           
/* 415 */           l = block.colorMultiplier(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, bakedquad.getTintIndex());
/*     */         } 
/*     */         
/* 418 */         if (EntityRenderer.anaglyphEnable)
/*     */         {
/* 420 */           l = TextureUtil.anaglyphColor(l);
/*     */         }
/*     */         
/* 423 */         float f = (l >> 16 & 0xFF) / 255.0F;
/* 424 */         float f1 = (l >> 8 & 0xFF) / 255.0F;
/* 425 */         float f2 = (l & 0xFF) / 255.0F;
/* 426 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 4);
/* 427 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 3);
/* 428 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 2);
/* 429 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 1);
/*     */       } 
/*     */       
/* 432 */       p_renderQuadsFlat_7_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderModelBrightnessColor(IBakedModel bakedModel, float p_178262_2_, float p_178262_3_, float p_178262_4_, float p_178262_5_) {
/* 438 */     for (EnumFacing enumfacing : EnumFacing.VALUES)
/*     */     {
/* 440 */       renderModelBrightnessColorQuads(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, bakedModel.getFaceQuads(enumfacing));
/*     */     }
/*     */     
/* 443 */     renderModelBrightnessColorQuads(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, bakedModel.getGeneralQuads());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderModelBrightness(IBakedModel p_178266_1_, IBlockState p_178266_2_, float p_178266_3_, boolean p_178266_4_) {
/* 448 */     Block block = p_178266_2_.getBlock();
/* 449 */     block.setBlockBoundsForItemRender();
/* 450 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 451 */     int i = block.getRenderColor(block.getStateForEntityRender(p_178266_2_));
/*     */     
/* 453 */     if (EntityRenderer.anaglyphEnable)
/*     */     {
/* 455 */       i = TextureUtil.anaglyphColor(i);
/*     */     }
/*     */     
/* 458 */     float f = (i >> 16 & 0xFF) / 255.0F;
/* 459 */     float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 460 */     float f2 = (i & 0xFF) / 255.0F;
/*     */     
/* 462 */     if (!p_178266_4_)
/*     */     {
/* 464 */       GlStateManager.color(p_178266_3_, p_178266_3_, p_178266_3_, 1.0F);
/*     */     }
/*     */     
/* 467 */     renderModelBrightnessColor(p_178266_1_, p_178266_3_, f, f1, f2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderModelBrightnessColorQuads(float p_178264_1_, float p_178264_2_, float p_178264_3_, float p_178264_4_, List<BakedQuad> p_178264_5_) {
/* 472 */     Tessellator tessellator = Tessellator.getInstance();
/* 473 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 475 */     for (BakedQuad bakedquad : p_178264_5_) {
/*     */       
/* 477 */       worldrenderer.begin(7, DefaultVertexFormats.ITEM);
/* 478 */       worldrenderer.addVertexData(bakedquad.getVertexData());
/* 479 */       worldrenderer.putSprite(bakedquad.getSprite());
/*     */       
/* 481 */       if (bakedquad.hasTintIndex()) {
/*     */         
/* 483 */         worldrenderer.putColorRGB_F4(p_178264_2_ * p_178264_1_, p_178264_3_ * p_178264_1_, p_178264_4_ * p_178264_1_);
/*     */       }
/*     */       else {
/*     */         
/* 487 */         worldrenderer.putColorRGB_F4(p_178264_1_, p_178264_1_, p_178264_1_);
/*     */       } 
/*     */       
/* 490 */       Vec3i vec3i = bakedquad.getFace().getDirectionVec();
/* 491 */       worldrenderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 492 */       tessellator.draw();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float fixAoLightValue(float p_fixAoLightValue_0_) {
/* 498 */     return (p_fixAoLightValue_0_ == 0.2F) ? aoLightValueOpaque : p_fixAoLightValue_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateAoLightValue() {
/* 503 */     aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
/* 504 */     separateAoLightValue = (Config.isShaders() && Shaders.isSeparateAo());
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderOverlayModels(IBlockAccess p_renderOverlayModels_1_, IBakedModel p_renderOverlayModels_2_, IBlockState p_renderOverlayModels_3_, BlockPos p_renderOverlayModels_4_, WorldRenderer p_renderOverlayModels_5_, boolean p_renderOverlayModels_6_, long p_renderOverlayModels_7_, RenderEnv p_renderOverlayModels_9_, boolean p_renderOverlayModels_10_) {
/* 509 */     if (p_renderOverlayModels_9_.isOverlaysRendered())
/*     */     {
/* 511 */       for (int i = 0; i < OVERLAY_LAYERS.length; i++) {
/*     */         
/* 513 */         EnumWorldBlockLayer enumworldblocklayer = OVERLAY_LAYERS[i];
/* 514 */         ListQuadsOverlay listquadsoverlay = p_renderOverlayModels_9_.getListQuadsOverlay(enumworldblocklayer);
/*     */         
/* 516 */         if (listquadsoverlay.size() > 0) {
/*     */           
/* 518 */           RegionRenderCacheBuilder regionrendercachebuilder = p_renderOverlayModels_9_.getRegionRenderCacheBuilder();
/*     */           
/* 520 */           if (regionrendercachebuilder != null) {
/*     */             
/* 522 */             WorldRenderer worldrenderer = regionrendercachebuilder.getWorldRendererByLayer(enumworldblocklayer);
/*     */             
/* 524 */             if (!worldrenderer.isDrawing()) {
/*     */               
/* 526 */               worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 527 */               worldrenderer.setTranslation(p_renderOverlayModels_5_.getXOffset(), p_renderOverlayModels_5_.getYOffset(), p_renderOverlayModels_5_.getZOffset());
/*     */             } 
/*     */             
/* 530 */             for (int j = 0; j < listquadsoverlay.size(); j++) {
/*     */               
/* 532 */               BakedQuad bakedquad = listquadsoverlay.getQuad(j);
/* 533 */               List<BakedQuad> list = listquadsoverlay.getListQuadsSingle(bakedquad);
/* 534 */               IBlockState iblockstate = listquadsoverlay.getBlockState(j);
/*     */               
/* 536 */               if (bakedquad.getQuadEmissive() != null)
/*     */               {
/* 538 */                 listquadsoverlay.addQuad(bakedquad.getQuadEmissive(), iblockstate);
/*     */               }
/*     */               
/* 541 */               p_renderOverlayModels_9_.reset(iblockstate, p_renderOverlayModels_4_);
/*     */               
/* 543 */               if (p_renderOverlayModels_10_) {
/*     */                 
/* 545 */                 renderQuadsSmooth(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, worldrenderer, list, p_renderOverlayModels_9_);
/*     */               }
/*     */               else {
/*     */                 
/* 549 */                 int k = iblockstate.getBlock().getMixedBrightnessForBlock(p_renderOverlayModels_1_, p_renderOverlayModels_4_.offset(bakedquad.getFace()));
/* 550 */                 renderQuadsFlat(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, bakedquad.getFace(), k, false, worldrenderer, list, p_renderOverlayModels_9_);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 555 */           listquadsoverlay.clear();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 560 */     if (Config.isBetterSnow() && !p_renderOverlayModels_9_.isBreakingAnimation() && BetterSnow.shouldRender(p_renderOverlayModels_1_, p_renderOverlayModels_3_, p_renderOverlayModels_4_)) {
/*     */       
/* 562 */       IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
/* 563 */       IBlockState iblockstate1 = BetterSnow.getStateSnowLayer();
/* 564 */       renderModel(p_renderOverlayModels_1_, ibakedmodel, iblockstate1, p_renderOverlayModels_4_, p_renderOverlayModels_5_, p_renderOverlayModels_6_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AmbientOcclusionFace
/*     */   {
/*     */     public AmbientOcclusionFace() {
/* 575 */       this(null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 580 */     private final float[] vertexColorMultiplier = new float[4];
/* 581 */     private final int[] vertexBrightness = new int[4];
/*     */ 
/*     */     
/*     */     public void setMaxBlockLight()
/*     */     {
/* 586 */       int i = 240;
/* 587 */       this.vertexBrightness[0] = this.vertexBrightness[0] | i;
/* 588 */       this.vertexBrightness[1] = this.vertexBrightness[1] | i;
/* 589 */       this.vertexBrightness[2] = this.vertexBrightness[2] | i;
/* 590 */       this.vertexBrightness[3] = this.vertexBrightness[3] | i;
/* 591 */       this.vertexColorMultiplier[0] = 1.0F;
/* 592 */       this.vertexColorMultiplier[1] = 1.0F;
/* 593 */       this.vertexColorMultiplier[2] = 1.0F;
/* 594 */       this.vertexColorMultiplier[3] = 1.0F; } public void updateVertexBrightness(IBlockAccess blockAccessIn, Block blockIn, BlockPos blockPosIn, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) { float f4; int i1, j1; float f26;
/*     */       int k1;
/*     */       float f27;
/*     */       int l1;
/*     */       float f28;
/* 599 */       BlockPos blockpos = boundsFlags.get(0) ? blockPosIn.offset(facingIn) : blockPosIn;
/* 600 */       BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(facingIn);
/* 601 */       BlockPos blockpos1 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[0]);
/* 602 */       BlockPos blockpos2 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[1]);
/* 603 */       BlockPos blockpos3 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 604 */       BlockPos blockpos4 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 605 */       int i = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos1);
/* 606 */       int j = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos2);
/* 607 */       int k = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos3);
/* 608 */       int l = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos4);
/* 609 */       float f = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos1).getBlock().getAmbientOcclusionLightValue());
/* 610 */       float f1 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos2).getBlock().getAmbientOcclusionLightValue());
/* 611 */       float f2 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos3).getBlock().getAmbientOcclusionLightValue());
/* 612 */       float f3 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos4).getBlock().getAmbientOcclusionLightValue());
/* 613 */       boolean flag = blockAccessIn.getBlockState(blockpos1.offset(facingIn)).getBlock().isTranslucent();
/* 614 */       boolean flag1 = blockAccessIn.getBlockState(blockpos2.offset(facingIn)).getBlock().isTranslucent();
/* 615 */       boolean flag2 = blockAccessIn.getBlockState(blockpos3.offset(facingIn)).getBlock().isTranslucent();
/* 616 */       boolean flag3 = blockAccessIn.getBlockState(blockpos4.offset(facingIn)).getBlock().isTranslucent();
/*     */ 
/*     */ 
/*     */       
/* 620 */       if (!flag2 && !flag) {
/*     */         
/* 622 */         f4 = f;
/* 623 */         i1 = i;
/*     */       }
/*     */       else {
/*     */         
/* 627 */         BlockPos blockpos5 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 628 */         f4 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos5).getBlock().getAmbientOcclusionLightValue());
/* 629 */         i1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos5);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 635 */       if (!flag3 && !flag) {
/*     */         
/* 637 */         f26 = f;
/* 638 */         j1 = i;
/*     */       }
/*     */       else {
/*     */         
/* 642 */         BlockPos blockpos6 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 643 */         f26 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos6).getBlock().getAmbientOcclusionLightValue());
/* 644 */         j1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos6);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 650 */       if (!flag2 && !flag1) {
/*     */         
/* 652 */         f27 = f1;
/* 653 */         k1 = j;
/*     */       }
/*     */       else {
/*     */         
/* 657 */         BlockPos blockpos7 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 658 */         f27 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos7).getBlock().getAmbientOcclusionLightValue());
/* 659 */         k1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos7);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 665 */       if (!flag3 && !flag1) {
/*     */         
/* 667 */         f28 = f1;
/* 668 */         l1 = j;
/*     */       }
/*     */       else {
/*     */         
/* 672 */         BlockPos blockpos8 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 673 */         f28 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos8).getBlock().getAmbientOcclusionLightValue());
/* 674 */         l1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos8);
/*     */       } 
/*     */       
/* 677 */       int i3 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);
/*     */       
/* 679 */       if (boundsFlags.get(0) || !blockAccessIn.getBlockState(blockPosIn.offset(facingIn)).getBlock().isOpaqueCube())
/*     */       {
/* 681 */         i3 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(facingIn));
/*     */       }
/*     */       
/* 684 */       float f5 = boundsFlags.get(0) ? blockAccessIn.getBlockState(blockpos).getBlock().getAmbientOcclusionLightValue() : blockAccessIn.getBlockState(blockPosIn).getBlock().getAmbientOcclusionLightValue();
/* 685 */       f5 = BlockModelRenderer.fixAoLightValue(f5);
/* 686 */       BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(facingIn);
/*     */       
/* 688 */       if (boundsFlags.get(1) && blockmodelrenderer$enumneighborinfo.field_178289_i) {
/*     */         
/* 690 */         float f29 = (f3 + f + f26 + f5) * 0.25F;
/* 691 */         float f30 = (f2 + f + f4 + f5) * 0.25F;
/* 692 */         float f31 = (f2 + f1 + f27 + f5) * 0.25F;
/* 693 */         float f32 = (f3 + f1 + f28 + f5) * 0.25F;
/* 694 */         float f10 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[1]).field_178229_m];
/* 695 */         float f11 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[3]).field_178229_m];
/* 696 */         float f12 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[5]).field_178229_m];
/* 697 */         float f13 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[7]).field_178229_m];
/* 698 */         float f14 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[1]).field_178229_m];
/* 699 */         float f15 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[3]).field_178229_m];
/* 700 */         float f16 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[5]).field_178229_m];
/* 701 */         float f17 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[7]).field_178229_m];
/* 702 */         float f18 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[1]).field_178229_m];
/* 703 */         float f19 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[3]).field_178229_m];
/* 704 */         float f20 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[5]).field_178229_m];
/* 705 */         float f21 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[7]).field_178229_m];
/* 706 */         float f22 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[1]).field_178229_m];
/* 707 */         float f23 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[3]).field_178229_m];
/* 708 */         float f24 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[5]).field_178229_m];
/* 709 */         float f25 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[7]).field_178229_m];
/* 710 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178191_g] = f29 * f10 + f30 * f11 + f31 * f12 + f32 * f13;
/* 711 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178200_h] = f29 * f14 + f30 * f15 + f31 * f16 + f32 * f17;
/* 712 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178201_i] = f29 * f18 + f30 * f19 + f31 * f20 + f32 * f21;
/* 713 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178198_j] = f29 * f22 + f30 * f23 + f31 * f24 + f32 * f25;
/* 714 */         int i2 = getAoBrightness(l, i, j1, i3);
/* 715 */         int j2 = getAoBrightness(k, i, i1, i3);
/* 716 */         int k2 = getAoBrightness(k, j, k1, i3);
/* 717 */         int l2 = getAoBrightness(l, j, l1, i3);
/* 718 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178191_g] = getVertexBrightness(i2, j2, k2, l2, f10, f11, f12, f13);
/* 719 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178200_h] = getVertexBrightness(i2, j2, k2, l2, f14, f15, f16, f17);
/* 720 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178201_i] = getVertexBrightness(i2, j2, k2, l2, f18, f19, f20, f21);
/* 721 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178198_j] = getVertexBrightness(i2, j2, k2, l2, f22, f23, f24, f25);
/*     */       }
/*     */       else {
/*     */         
/* 725 */         float f6 = (f3 + f + f26 + f5) * 0.25F;
/* 726 */         float f7 = (f2 + f + f4 + f5) * 0.25F;
/* 727 */         float f8 = (f2 + f1 + f27 + f5) * 0.25F;
/* 728 */         float f9 = (f3 + f1 + f28 + f5) * 0.25F;
/* 729 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178191_g] = getAoBrightness(l, i, j1, i3);
/* 730 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178200_h] = getAoBrightness(k, i, i1, i3);
/* 731 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178201_i] = getAoBrightness(k, j, k1, i3);
/* 732 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178198_j] = getAoBrightness(l, j, l1, i3);
/* 733 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178191_g] = f6;
/* 734 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178200_h] = f7;
/* 735 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178201_i] = f8;
/* 736 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178198_j] = f9;
/*     */       }  }
/*     */ 
/*     */ 
/*     */     
/*     */     private int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_) {
/* 742 */       if (p_147778_1_ == 0)
/*     */       {
/* 744 */         p_147778_1_ = p_147778_4_;
/*     */       }
/*     */       
/* 747 */       if (p_147778_2_ == 0)
/*     */       {
/* 749 */         p_147778_2_ = p_147778_4_;
/*     */       }
/*     */       
/* 752 */       if (p_147778_3_ == 0)
/*     */       {
/* 754 */         p_147778_3_ = p_147778_4_;
/*     */       }
/*     */       
/* 757 */       return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 0xFF00FF;
/*     */     }
/*     */ 
/*     */     
/*     */     private int getVertexBrightness(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_) {
/* 762 */       int i = (int)((p_178203_1_ >> 16 & 0xFF) * p_178203_5_ + (p_178203_2_ >> 16 & 0xFF) * p_178203_6_ + (p_178203_3_ >> 16 & 0xFF) * p_178203_7_ + (p_178203_4_ >> 16 & 0xFF) * p_178203_8_) & 0xFF;
/* 763 */       int j = (int)((p_178203_1_ & 0xFF) * p_178203_5_ + (p_178203_2_ & 0xFF) * p_178203_6_ + (p_178203_3_ & 0xFF) * p_178203_7_ + (p_178203_4_ & 0xFF) * p_178203_8_) & 0xFF;
/* 764 */       return i << 16 | j;
/*     */     }
/*     */     
/*     */     public AmbientOcclusionFace(BlockModelRenderer p_i46235_1_) {}
/*     */   }
/*     */   
/* 770 */   public enum EnumNeighborInfo { DOWN((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.5F, false, new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0]),
/* 771 */     UP((String)new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH }, 1.0F, false, new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0]),
/* 772 */     NORTH((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST }),
/* 773 */     SOUTH((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST }),
/* 774 */     WEST((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH }),
/* 775 */     EAST((String)new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH });
/*     */     
/*     */     protected final EnumFacing[] field_178276_g;
/*     */     protected final float field_178288_h;
/*     */     protected final boolean field_178289_i;
/*     */     protected final BlockModelRenderer.Orientation[] field_178286_j;
/*     */     protected final BlockModelRenderer.Orientation[] field_178287_k;
/*     */     protected final BlockModelRenderer.Orientation[] field_178284_l;
/*     */     protected final BlockModelRenderer.Orientation[] field_178285_m;
/* 784 */     private static final EnumNeighborInfo[] field_178282_n = new EnumNeighborInfo[6];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EnumNeighborInfo(EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, BlockModelRenderer.Orientation[] p_i46236_6_, BlockModelRenderer.Orientation[] p_i46236_7_, BlockModelRenderer.Orientation[] p_i46236_8_, BlockModelRenderer.Orientation[] p_i46236_9_) {
/*     */       this.field_178276_g = p_i46236_3_;
/*     */       this.field_178288_h = p_i46236_4_;
/*     */       this.field_178289_i = p_i46236_5_;
/*     */       this.field_178286_j = p_i46236_6_;
/*     */       this.field_178287_k = p_i46236_7_;
/*     */       this.field_178284_l = p_i46236_8_;
/*     */       this.field_178285_m = p_i46236_9_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 803 */       field_178282_n[EnumFacing.DOWN.getIndex()] = DOWN;
/* 804 */       field_178282_n[EnumFacing.UP.getIndex()] = UP;
/* 805 */       field_178282_n[EnumFacing.NORTH.getIndex()] = NORTH;
/* 806 */       field_178282_n[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 807 */       field_178282_n[EnumFacing.WEST.getIndex()] = WEST;
/* 808 */       field_178282_n[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     public static EnumNeighborInfo getNeighbourInfo(EnumFacing p_178273_0_) {
/*     */       return field_178282_n[p_178273_0_.getIndex()];
/*     */     } }
/*     */   
/* 814 */   public enum Orientation { DOWN((String)EnumFacing.DOWN, false),
/* 815 */     UP((String)EnumFacing.UP, false),
/* 816 */     NORTH((String)EnumFacing.NORTH, false),
/* 817 */     SOUTH((String)EnumFacing.SOUTH, false),
/* 818 */     WEST((String)EnumFacing.WEST, false),
/* 819 */     EAST((String)EnumFacing.EAST, false),
/* 820 */     FLIP_DOWN((String)EnumFacing.DOWN, true),
/* 821 */     FLIP_UP((String)EnumFacing.UP, true),
/* 822 */     FLIP_NORTH((String)EnumFacing.NORTH, true),
/* 823 */     FLIP_SOUTH((String)EnumFacing.SOUTH, true),
/* 824 */     FLIP_WEST((String)EnumFacing.WEST, true),
/* 825 */     FLIP_EAST((String)EnumFacing.EAST, true);
/*     */     
/*     */     protected final int field_178229_m;
/*     */ 
/*     */     
/*     */     Orientation(EnumFacing p_i46233_3_, boolean p_i46233_4_) {
/* 831 */       this.field_178229_m = p_i46233_3_.getIndex() + (p_i46233_4_ ? (EnumFacing.values()).length : 0);
/*     */     } }
/*     */ 
/*     */   
/*     */   enum VertexTranslations
/*     */   {
/* 837 */     DOWN(0, 1, 2, 3),
/* 838 */     UP(2, 3, 0, 1),
/* 839 */     NORTH(3, 0, 1, 2),
/* 840 */     SOUTH(0, 1, 2, 3),
/* 841 */     WEST(3, 0, 1, 2),
/* 842 */     EAST(1, 2, 3, 0);
/*     */     
/*     */     private final int field_178191_g;
/*     */     private final int field_178200_h;
/*     */     private final int field_178201_i;
/*     */     private final int field_178198_j;
/* 848 */     private static final VertexTranslations[] field_178199_k = new VertexTranslations[6];
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
/*     */     static {
/* 864 */       field_178199_k[EnumFacing.DOWN.getIndex()] = DOWN;
/* 865 */       field_178199_k[EnumFacing.UP.getIndex()] = UP;
/* 866 */       field_178199_k[EnumFacing.NORTH.getIndex()] = NORTH;
/* 867 */       field_178199_k[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 868 */       field_178199_k[EnumFacing.WEST.getIndex()] = WEST;
/* 869 */       field_178199_k[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     
/*     */     VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
/*     */       this.field_178191_g = p_i46234_3_;
/*     */       this.field_178200_h = p_i46234_4_;
/*     */       this.field_178201_i = p_i46234_5_;
/*     */       this.field_178198_j = p_i46234_6_;
/*     */     }
/*     */     
/*     */     public static VertexTranslations getVertexTranslations(EnumFacing p_178184_0_) {
/*     */       return field_178199_k[p_178184_0_.getIndex()];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\BlockModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */