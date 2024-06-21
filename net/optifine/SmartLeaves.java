/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.model.ModelUtils;
/*     */ 
/*     */ public class SmartLeaves {
/*  21 */   private static IBakedModel modelLeavesCullAcacia = null;
/*  22 */   private static IBakedModel modelLeavesCullBirch = null;
/*  23 */   private static IBakedModel modelLeavesCullDarkOak = null;
/*  24 */   private static IBakedModel modelLeavesCullJungle = null;
/*  25 */   private static IBakedModel modelLeavesCullOak = null;
/*  26 */   private static IBakedModel modelLeavesCullSpruce = null;
/*  27 */   private static List generalQuadsCullAcacia = null;
/*  28 */   private static List generalQuadsCullBirch = null;
/*  29 */   private static List generalQuadsCullDarkOak = null;
/*  30 */   private static List generalQuadsCullJungle = null;
/*  31 */   private static List generalQuadsCullOak = null;
/*  32 */   private static List generalQuadsCullSpruce = null;
/*  33 */   private static IBakedModel modelLeavesDoubleAcacia = null;
/*  34 */   private static IBakedModel modelLeavesDoubleBirch = null;
/*  35 */   private static IBakedModel modelLeavesDoubleDarkOak = null;
/*  36 */   private static IBakedModel modelLeavesDoubleJungle = null;
/*  37 */   private static IBakedModel modelLeavesDoubleOak = null;
/*  38 */   private static IBakedModel modelLeavesDoubleSpruce = null;
/*     */ 
/*     */   
/*     */   public static IBakedModel getLeavesModel(IBakedModel model, IBlockState stateIn) {
/*  42 */     if (!Config.isTreesSmart())
/*     */     {
/*  44 */       return model;
/*     */     }
/*     */ 
/*     */     
/*  48 */     List list = model.getGeneralQuads();
/*  49 */     return (list == generalQuadsCullAcacia) ? modelLeavesDoubleAcacia : ((list == generalQuadsCullBirch) ? modelLeavesDoubleBirch : ((list == generalQuadsCullDarkOak) ? modelLeavesDoubleDarkOak : ((list == generalQuadsCullJungle) ? modelLeavesDoubleJungle : ((list == generalQuadsCullOak) ? modelLeavesDoubleOak : ((list == generalQuadsCullSpruce) ? modelLeavesDoubleSpruce : model)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSameLeaves(IBlockState state1, IBlockState state2) {
/*  55 */     if (state1 == state2)
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  61 */     Block block = state1.getBlock();
/*  62 */     Block block1 = state2.getBlock();
/*  63 */     return (block != block1) ? false : ((block instanceof BlockOldLeaf) ? ((BlockPlanks.EnumType)state1.getValue((IProperty)BlockOldLeaf.VARIANT)).equals(state2.getValue((IProperty)BlockOldLeaf.VARIANT)) : ((block instanceof BlockNewLeaf) ? ((BlockPlanks.EnumType)state1.getValue((IProperty)BlockNewLeaf.VARIANT)).equals(state2.getValue((IProperty)BlockNewLeaf.VARIANT)) : false));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateLeavesModels() {
/*  69 */     List list = new ArrayList();
/*  70 */     modelLeavesCullAcacia = getModelCull("acacia", list);
/*  71 */     modelLeavesCullBirch = getModelCull("birch", list);
/*  72 */     modelLeavesCullDarkOak = getModelCull("dark_oak", list);
/*  73 */     modelLeavesCullJungle = getModelCull("jungle", list);
/*  74 */     modelLeavesCullOak = getModelCull("oak", list);
/*  75 */     modelLeavesCullSpruce = getModelCull("spruce", list);
/*  76 */     generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
/*  77 */     generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
/*  78 */     generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
/*  79 */     generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
/*  80 */     generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
/*  81 */     generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
/*  82 */     modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
/*  83 */     modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
/*  84 */     modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
/*  85 */     modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
/*  86 */     modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
/*  87 */     modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
/*     */     
/*  89 */     if (list.size() > 0)
/*     */     {
/*  91 */       Config.dbg("Enable face culling: " + Config.arrayToString(list.toArray()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getGeneralQuadsSafe(IBakedModel model) {
/*  97 */     return (model == null) ? null : model.getGeneralQuads();
/*     */   }
/*     */ 
/*     */   
/*     */   static IBakedModel getModelCull(String type, List<String> updatedTypes) {
/* 102 */     ModelManager modelmanager = Config.getModelManager();
/*     */     
/* 104 */     if (modelmanager == null)
/*     */     {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 110 */     ResourceLocation resourcelocation = new ResourceLocation("blockstates/" + type + "_leaves.json");
/*     */     
/* 112 */     if (Config.getDefiningResourcePack(resourcelocation) != Config.getDefaultResourcePack())
/*     */     {
/* 114 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 118 */     ResourceLocation resourcelocation1 = new ResourceLocation("models/block/" + type + "_leaves.json");
/*     */     
/* 120 */     if (Config.getDefiningResourcePack(resourcelocation1) != Config.getDefaultResourcePack())
/*     */     {
/* 122 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 126 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(type + "_leaves", "normal");
/* 127 */     IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */     
/* 129 */     if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/*     */       
/* 131 */       List list = ibakedmodel.getGeneralQuads();
/*     */       
/* 133 */       if (list.size() == 0)
/*     */       {
/* 135 */         return ibakedmodel;
/*     */       }
/* 137 */       if (list.size() != 6)
/*     */       {
/* 139 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 143 */       for (Object e : list) {
/*     */         
/* 145 */         BakedQuad bakedquad = (BakedQuad)e;
/* 146 */         List<BakedQuad> list1 = ibakedmodel.getFaceQuads(bakedquad.getFace());
/*     */         
/* 148 */         if (list1.size() > 0)
/*     */         {
/* 150 */           return null;
/*     */         }
/*     */         
/* 153 */         list1.add(bakedquad);
/*     */       } 
/*     */       
/* 156 */       list.clear();
/* 157 */       updatedTypes.add(type + "_leaves");
/* 158 */       return ibakedmodel;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IBakedModel getModelDoubleFace(IBakedModel model) {
/* 172 */     if (model == null)
/*     */     {
/* 174 */       return null;
/*     */     }
/* 176 */     if (model.getGeneralQuads().size() > 0) {
/*     */       
/* 178 */       Config.warn("SmartLeaves: Model is not cube, general quads: " + model.getGeneralQuads().size() + ", model: " + model);
/* 179 */       return model;
/*     */     } 
/*     */ 
/*     */     
/* 183 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */     
/* 185 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/* 187 */       EnumFacing enumfacing = aenumfacing[i];
/* 188 */       List<BakedQuad> list = model.getFaceQuads(enumfacing);
/*     */       
/* 190 */       if (list.size() != 1) {
/*     */         
/* 192 */         Config.warn("SmartLeaves: Model is not cube, side: " + enumfacing + ", quads: " + list.size() + ", model: " + model);
/* 193 */         return model;
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     IBakedModel ibakedmodel = ModelUtils.duplicateModel(model);
/* 198 */     List[] alist = new List[aenumfacing.length];
/*     */     
/* 200 */     for (int k = 0; k < aenumfacing.length; k++) {
/*     */       
/* 202 */       EnumFacing enumfacing1 = aenumfacing[k];
/* 203 */       List<BakedQuad> list1 = ibakedmodel.getFaceQuads(enumfacing1);
/* 204 */       BakedQuad bakedquad = list1.get(0);
/* 205 */       BakedQuad bakedquad1 = new BakedQuad((int[])bakedquad.getVertexData().clone(), bakedquad.getTintIndex(), bakedquad.getFace(), bakedquad.getSprite());
/* 206 */       int[] aint = bakedquad1.getVertexData();
/* 207 */       int[] aint1 = (int[])aint.clone();
/* 208 */       int j = aint.length / 4;
/* 209 */       System.arraycopy(aint, 0 * j, aint1, 3 * j, j);
/* 210 */       System.arraycopy(aint, 1 * j, aint1, 2 * j, j);
/* 211 */       System.arraycopy(aint, 2 * j, aint1, 1 * j, j);
/* 212 */       System.arraycopy(aint, 3 * j, aint1, 0 * j, j);
/* 213 */       System.arraycopy(aint1, 0, aint, 0, aint1.length);
/* 214 */       list1.add(bakedquad1);
/*     */     } 
/*     */     
/* 217 */     return ibakedmodel;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\SmartLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */