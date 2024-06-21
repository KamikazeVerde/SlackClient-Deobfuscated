/*     */ package net.optifine.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.BetterGrass;
/*     */ import net.optifine.ConnectedTextures;
/*     */ import net.optifine.NaturalTextures;
/*     */ import net.optifine.SmartLeaves;
/*     */ import net.optifine.render.RenderEnv;
/*     */ 
/*     */ public class BlockModelCustomizer
/*     */ {
/*  21 */   private static final List<BakedQuad> NO_QUADS = (List<BakedQuad>)ImmutableList.of();
/*     */ 
/*     */   
/*     */   public static IBakedModel getRenderModel(IBakedModel modelIn, IBlockState stateIn, RenderEnv renderEnv) {
/*  25 */     if (renderEnv.isSmartLeaves())
/*     */     {
/*  27 */       modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn);
/*     */     }
/*     */     
/*  30 */     return modelIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<BakedQuad> getRenderQuads(List<BakedQuad> quads, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, EnumWorldBlockLayer layer, long rand, RenderEnv renderEnv) {
/*  35 */     if (enumfacing != null) {
/*     */       
/*  37 */       if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.getBlockState(posIn.offset(enumfacing)), stateIn))
/*     */       {
/*  39 */         return NO_QUADS;
/*     */       }
/*     */       
/*  42 */       if (!renderEnv.isBreakingAnimation(quads) && Config.isBetterGrass())
/*     */       {
/*  44 */         quads = BetterGrass.getFaceQuads(worldIn, stateIn, posIn, enumfacing, quads);
/*     */       }
/*     */     } 
/*     */     
/*  48 */     List<BakedQuad> list = renderEnv.getListQuadsCustomizer();
/*  49 */     list.clear();
/*     */     
/*  51 */     for (int i = 0; i < quads.size(); i++) {
/*     */       
/*  53 */       BakedQuad bakedquad = quads.get(i);
/*  54 */       BakedQuad[] abakedquad = getRenderQuads(bakedquad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
/*     */       
/*  56 */       if (i == 0 && quads.size() == 1 && abakedquad.length == 1 && abakedquad[0] == bakedquad && bakedquad.getQuadEmissive() == null)
/*     */       {
/*  58 */         return quads;
/*     */       }
/*     */       
/*  61 */       for (int j = 0; j < abakedquad.length; j++) {
/*     */         
/*  63 */         BakedQuad bakedquad1 = abakedquad[j];
/*  64 */         list.add(bakedquad1);
/*     */         
/*  66 */         if (bakedquad1.getQuadEmissive() != null) {
/*     */           
/*  68 */           renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(bakedquad1.getQuadEmissive(), stateIn);
/*  69 */           renderEnv.setOverlaysRendered(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumWorldBlockLayer getEmissiveLayer(EnumWorldBlockLayer layer) {
/*  79 */     return (layer != null && layer != EnumWorldBlockLayer.SOLID) ? layer : EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getRenderQuads(BakedQuad quad, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, long rand, RenderEnv renderEnv) {
/*  84 */     if (renderEnv.isBreakingAnimation(quad))
/*     */     {
/*  86 */       return renderEnv.getArrayQuadsCtm(quad);
/*     */     }
/*     */ 
/*     */     
/*  90 */     BakedQuad bakedquad = quad;
/*     */     
/*  92 */     if (Config.isConnectedTextures()) {
/*     */       
/*  94 */       BakedQuad[] abakedquad = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
/*     */       
/*  96 */       if (abakedquad.length != 1 || abakedquad[0] != quad)
/*     */       {
/*  98 */         return abakedquad;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     if (Config.isNaturalTextures()) {
/*     */       
/* 104 */       quad = NaturalTextures.getNaturalTexture(posIn, quad);
/*     */       
/* 106 */       if (quad != bakedquad)
/*     */       {
/* 108 */         return renderEnv.getArrayQuadsCtm(quad);
/*     */       }
/*     */     } 
/*     */     
/* 112 */     return renderEnv.getArrayQuadsCtm(quad);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\model\BlockModelCustomizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */