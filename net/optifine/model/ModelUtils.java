/*     */ package net.optifine.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class ModelUtils
/*     */ {
/*     */   public static void dbgModel(IBakedModel model) {
/*  15 */     if (model != null) {
/*     */       
/*  17 */       Config.dbg("Model: " + model + ", ao: " + model.isAmbientOcclusion() + ", gui3d: " + model.isGui3d() + ", builtIn: " + model.isBuiltInRenderer() + ", particle: " + model.getTexture());
/*  18 */       EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */       
/*  20 */       for (int i = 0; i < aenumfacing.length; i++) {
/*     */         
/*  22 */         EnumFacing enumfacing = aenumfacing[i];
/*  23 */         List list = model.getFaceQuads(enumfacing);
/*  24 */         dbgQuads(enumfacing.getName(), list, "  ");
/*     */       } 
/*     */       
/*  27 */       List list1 = model.getGeneralQuads();
/*  28 */       dbgQuads("General", list1, "  ");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbgQuads(String name, List quads, String prefix) {
/*  34 */     for (Object e : quads) {
/*     */       
/*  36 */       BakedQuad bakedquad = (BakedQuad)e;
/*  37 */       dbgQuad(name, bakedquad, prefix);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgQuad(String name, BakedQuad quad, String prefix) {
/*  43 */     Config.dbg(prefix + "Quad: " + quad.getClass().getName() + ", type: " + name + ", face: " + quad.getFace() + ", tint: " + quad.getTintIndex() + ", sprite: " + quad.getSprite());
/*  44 */     dbgVertexData(quad.getVertexData(), "  " + prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgVertexData(int[] vd, String prefix) {
/*  49 */     int i = vd.length / 4;
/*  50 */     Config.dbg(prefix + "Length: " + vd.length + ", step: " + i);
/*     */     
/*  52 */     for (int j = 0; j < 4; j++) {
/*     */       
/*  54 */       int k = j * i;
/*  55 */       float f = Float.intBitsToFloat(vd[k + 0]);
/*  56 */       float f1 = Float.intBitsToFloat(vd[k + 1]);
/*  57 */       float f2 = Float.intBitsToFloat(vd[k + 2]);
/*  58 */       int l = vd[k + 3];
/*  59 */       float f3 = Float.intBitsToFloat(vd[k + 4]);
/*  60 */       float f4 = Float.intBitsToFloat(vd[k + 5]);
/*  61 */       Config.dbg(prefix + j + " xyz: " + f + "," + f1 + "," + f2 + " col: " + l + " u,v: " + f3 + "," + f4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel duplicateModel(IBakedModel model) {
/*  67 */     List list = duplicateQuadList(model.getGeneralQuads());
/*  68 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  69 */     List<List> list1 = new ArrayList();
/*     */     
/*  71 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  73 */       EnumFacing enumfacing = aenumfacing[i];
/*  74 */       List list2 = model.getFaceQuads(enumfacing);
/*  75 */       List list3 = duplicateQuadList(list2);
/*  76 */       list1.add(list3);
/*     */     } 
/*     */     
/*  79 */     SimpleBakedModel simplebakedmodel = new SimpleBakedModel(list, list1, model.isAmbientOcclusion(), model.isGui3d(), model.getTexture(), model.getItemCameraTransforms());
/*  80 */     return (IBakedModel)simplebakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List duplicateQuadList(List lists) {
/*  85 */     List<BakedQuad> list = new ArrayList();
/*     */     
/*  87 */     for (Object e : lists) {
/*     */       
/*  89 */       BakedQuad bakedquad = (BakedQuad)e;
/*  90 */       BakedQuad bakedquad1 = duplicateQuad(bakedquad);
/*  91 */       list.add(bakedquad1);
/*     */     } 
/*     */     
/*  94 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad duplicateQuad(BakedQuad quad) {
/*  99 */     BakedQuad bakedquad = new BakedQuad((int[])quad.getVertexData().clone(), quad.getTintIndex(), quad.getFace(), quad.getSprite());
/* 100 */     return bakedquad;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\model\ModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */