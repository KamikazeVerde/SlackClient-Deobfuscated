/*     */ package net.optifine.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockFaceUV;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartRotation;
/*     */ import net.minecraft.client.renderer.block.model.BreakingFour;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ public class BlockModelUtils
/*     */ {
/*     */   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
/*     */   
/*     */   public static IBakedModel makeModelCube(String spriteName, int tintIndex) {
/*  32 */     TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(spriteName);
/*  33 */     return makeModelCube(textureatlassprite, tintIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModelCube(TextureAtlasSprite sprite, int tintIndex) {
/*  38 */     List list = new ArrayList();
/*  39 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  40 */     List<List<BakedQuad>> list1 = new ArrayList<>();
/*     */     
/*  42 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  44 */       EnumFacing enumfacing = aenumfacing[i];
/*  45 */       List<BakedQuad> list2 = new ArrayList();
/*  46 */       list2.add(makeBakedQuad(enumfacing, sprite, tintIndex));
/*  47 */       list1.add(list2);
/*     */     } 
/*     */     
/*  50 */     return (IBakedModel)new SimpleBakedModel(list, list1, true, true, sprite, ItemCameraTransforms.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IBakedModel joinModelsCube(IBakedModel modelBase, IBakedModel modelAdd) {
/*  56 */     List<BakedQuad> list = new ArrayList<>();
/*  57 */     list.addAll(modelBase.getGeneralQuads());
/*  58 */     list.addAll(modelAdd.getGeneralQuads());
/*  59 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  60 */     List<List> list1 = new ArrayList();
/*     */     
/*  62 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  64 */       EnumFacing enumfacing = aenumfacing[i];
/*  65 */       List list2 = new ArrayList();
/*  66 */       list2.addAll(modelBase.getFaceQuads(enumfacing));
/*  67 */       list2.addAll(modelAdd.getFaceQuads(enumfacing));
/*  68 */       list1.add(list2);
/*     */     } 
/*     */     
/*  71 */     boolean flag = modelBase.isAmbientOcclusion();
/*  72 */     boolean flag1 = modelBase.isBuiltInRenderer();
/*  73 */     TextureAtlasSprite textureatlassprite = modelBase.getTexture();
/*  74 */     ItemCameraTransforms itemcameratransforms = modelBase.getItemCameraTransforms();
/*  75 */     return (IBakedModel)new SimpleBakedModel(list, list1, flag, flag1, textureatlassprite, itemcameratransforms);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BakedQuad makeBakedQuad(EnumFacing facing, TextureAtlasSprite sprite, int tintIndex) {
/*  81 */     Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*  82 */     Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
/*  83 */     BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
/*  84 */     BlockPartFace blockpartface = new BlockPartFace(facing, tintIndex, "#" + facing.getName(), blockfaceuv);
/*  85 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/*  86 */     BlockPartRotation blockpartrotation = null;
/*  87 */     boolean flag = false;
/*  88 */     boolean flag1 = true;
/*  89 */     FaceBakery facebakery = new FaceBakery();
/*  90 */     BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, sprite, facing, modelrotation, blockpartrotation, flag, flag1);
/*  91 */     return bakedquad;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModel(String modelName, String spriteOldName, String spriteNewName) {
/*  96 */     TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
/*  97 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(spriteOldName);
/*  98 */     TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(spriteNewName);
/*  99 */     return makeModel(modelName, textureatlassprite, textureatlassprite1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModel(String modelName, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
/* 104 */     if (spriteOld != null && spriteNew != null) {
/*     */       
/* 106 */       ModelManager modelmanager = Config.getModelManager();
/*     */       
/* 108 */       if (modelmanager == null)
/*     */       {
/* 110 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 114 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(modelName, "normal");
/* 115 */       IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */       
/* 117 */       if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/*     */         
/* 119 */         IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
/* 120 */         EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */         
/* 122 */         for (int i = 0; i < aenumfacing.length; i++) {
/*     */           
/* 124 */           EnumFacing enumfacing = aenumfacing[i];
/* 125 */           List<BakedQuad> list = ibakedmodel1.getFaceQuads(enumfacing);
/* 126 */           replaceTexture(list, spriteOld, spriteNew);
/*     */         } 
/*     */         
/* 129 */         List<BakedQuad> list1 = ibakedmodel1.getGeneralQuads();
/* 130 */         replaceTexture(list1, spriteOld, spriteNew);
/* 131 */         return ibakedmodel1;
/*     */       } 
/*     */ 
/*     */       
/* 135 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceTexture(List<BakedQuad> quads, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
/* 147 */     List<BakedQuad> list = new ArrayList<>();
/*     */     
/* 149 */     for (BakedQuad bakedquad : quads) {
/*     */       BreakingFour breakingFour;
/* 151 */       if (bakedquad.getSprite() == spriteOld)
/*     */       {
/* 153 */         breakingFour = new BreakingFour(bakedquad, spriteNew);
/*     */       }
/*     */       
/* 156 */       list.add(breakingFour);
/*     */     } 
/*     */     
/* 159 */     quads.clear();
/* 160 */     quads.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void snapVertexPosition(Vector3f pos) {
/* 165 */     pos.setX(snapVertexCoord(pos.getX()));
/* 166 */     pos.setY(snapVertexCoord(pos.getY()));
/* 167 */     pos.setZ(snapVertexCoord(pos.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float snapVertexCoord(float x) {
/* 172 */     return (x > -1.0E-6F && x < 1.0E-6F) ? 0.0F : ((x > 0.999999F && x < 1.000001F) ? 1.0F : x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB aabb, Block.EnumOffsetType offsetType, BlockPos pos) {
/* 177 */     int i = pos.getX();
/* 178 */     int j = pos.getZ();
/* 179 */     long k = (i * 3129871) ^ j * 116129781L;
/* 180 */     k = k * k * 42317861L + k * 11L;
/* 181 */     double d0 = (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 182 */     double d1 = (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 183 */     double d2 = 0.0D;
/*     */     
/* 185 */     if (offsetType == Block.EnumOffsetType.XYZ)
/*     */     {
/* 187 */       d2 = (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */     }
/*     */     
/* 190 */     return aabb.offset(d0, d2, d1);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\model\BlockModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */