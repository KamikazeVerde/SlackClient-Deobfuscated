/*      */ package net.minecraft.client.renderer.entity;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockHugeMushroom;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.BlockQuartz;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.block.BlockStoneSlabNew;
/*      */ import net.minecraft.block.BlockTallGrass;
/*      */ import net.minecraft.block.BlockWall;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*      */ import net.minecraft.client.renderer.ItemModelMesher;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*      */ import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemFishFood;
/*      */ import net.minecraft.item.ItemPotion;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.CustomItems;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ 
/*      */ public class RenderItem
/*      */   implements IResourceManagerReloadListener {
/*   72 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*      */   
/*      */   private boolean field_175058_l = true;
/*      */   
/*      */   public float zLevel;
/*      */   private final ItemModelMesher itemModelMesher;
/*      */   private final TextureManager textureManager;
/*      */   private boolean renderItemGui = false;
/*   80 */   public ModelManager modelManager = null;
/*      */   
/*      */   private boolean renderModelHasEmissive = false;
/*      */   private boolean renderModelEmissive = false;
/*      */   
/*      */   public RenderItem(TextureManager textureManager, ModelManager modelManager) {
/*   86 */     this.textureManager = textureManager;
/*   87 */     this.modelManager = modelManager;
/*      */     
/*   89 */     if (Reflector.ItemModelMesherForge_Constructor.exists()) {
/*      */       
/*   91 */       this.itemModelMesher = (ItemModelMesher)Reflector.newInstance(Reflector.ItemModelMesherForge_Constructor, new Object[] { modelManager });
/*      */     }
/*      */     else {
/*      */       
/*   95 */       this.itemModelMesher = new ItemModelMesher(modelManager);
/*      */     } 
/*      */     
/*   98 */     registerItems();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175039_a(boolean p_175039_1_) {
/*  103 */     this.field_175058_l = p_175039_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemModelMesher getItemModelMesher() {
/*  108 */     return this.itemModelMesher;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerItem(Item itm, int subType, String identifier) {
/*  113 */     this.itemModelMesher.register(itm, subType, new ModelResourceLocation(identifier, "inventory"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerBlock(Block blk, int subType, String identifier) {
/*  118 */     registerItem(Item.getItemFromBlock(blk), subType, identifier);
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerBlock(Block blk, String identifier) {
/*  123 */     registerBlock(blk, 0, identifier);
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerItem(Item itm, String identifier) {
/*  128 */     registerItem(itm, 0, identifier);
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderModel(IBakedModel model, ItemStack stack) {
/*  133 */     renderModel(model, -1, stack);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderModel(IBakedModel model, int color) {
/*  138 */     renderModel(model, color, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderModel(IBakedModel model, int color, ItemStack stack) {
/*  143 */     Tessellator tessellator = Tessellator.getInstance();
/*  144 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  145 */     boolean flag = Minecraft.getMinecraft().getTextureMapBlocks().isTextureBound();
/*  146 */     boolean flag1 = (Config.isMultiTexture() && flag);
/*      */     
/*  148 */     if (flag1)
/*      */     {
/*  150 */       worldrenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
/*      */     }
/*      */     
/*  153 */     worldrenderer.begin(7, DefaultVertexFormats.ITEM);
/*      */     
/*  155 */     for (EnumFacing enumfacing : EnumFacing.VALUES)
/*      */     {
/*  157 */       renderQuads(worldrenderer, model.getFaceQuads(enumfacing), color, stack);
/*      */     }
/*      */     
/*  160 */     renderQuads(worldrenderer, model.getGeneralQuads(), color, stack);
/*  161 */     tessellator.draw();
/*      */     
/*  163 */     if (flag1) {
/*      */       
/*  165 */       worldrenderer.setBlockLayer(null);
/*  166 */       GlStateManager.bindCurrentTexture();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItem(ItemStack stack, IBakedModel model) {
/*  172 */     if (stack != null) {
/*      */       
/*  174 */       GlStateManager.pushMatrix();
/*  175 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*      */       
/*  177 */       if (model.isBuiltInRenderer()) {
/*      */         
/*  179 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*  180 */         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  181 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  182 */         GlStateManager.enableRescaleNormal();
/*  183 */         TileEntityItemStackRenderer.instance.renderByItem(stack);
/*      */       }
/*      */       else {
/*      */         
/*  187 */         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*      */         
/*  189 */         if (Config.isCustomItems())
/*      */         {
/*  191 */           model = CustomItems.getCustomItemModel(stack, model, null, false);
/*      */         }
/*      */         
/*  194 */         this.renderModelHasEmissive = false;
/*  195 */         renderModel(model, stack);
/*      */         
/*  197 */         if (this.renderModelHasEmissive) {
/*      */           
/*  199 */           float f = OpenGlHelper.lastBrightnessX;
/*  200 */           float f1 = OpenGlHelper.lastBrightnessY;
/*  201 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, f1);
/*  202 */           this.renderModelEmissive = true;
/*  203 */           renderModel(model, stack);
/*  204 */           this.renderModelEmissive = false;
/*  205 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*      */         } 
/*      */         
/*  208 */         if (stack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomEffect(this, stack, model)))
/*      */         {
/*  210 */           renderEffect(model);
/*      */         }
/*      */       } 
/*      */       
/*  214 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderEffect(IBakedModel model) {
/*  220 */     if (!Config.isCustomItems() || CustomItems.isUseGlint())
/*      */     {
/*  222 */       if (!Config.isShaders() || !Shaders.isShadowPass) {
/*      */         
/*  224 */         GlStateManager.depthMask(false);
/*  225 */         GlStateManager.depthFunc(514);
/*  226 */         GlStateManager.disableLighting();
/*  227 */         GlStateManager.blendFunc(768, 1);
/*  228 */         this.textureManager.bindTexture(RES_ITEM_GLINT);
/*      */         
/*  230 */         if (Config.isShaders() && !this.renderItemGui)
/*      */         {
/*  232 */           ShadersRender.renderEnchantedGlintBegin();
/*      */         }
/*      */         
/*  235 */         GlStateManager.matrixMode(5890);
/*  236 */         GlStateManager.pushMatrix();
/*  237 */         GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  238 */         float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  239 */         GlStateManager.translate(f, 0.0F, 0.0F);
/*  240 */         GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
/*  241 */         renderModel(model, -8372020);
/*  242 */         GlStateManager.popMatrix();
/*  243 */         GlStateManager.pushMatrix();
/*  244 */         GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  245 */         float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
/*  246 */         GlStateManager.translate(-f1, 0.0F, 0.0F);
/*  247 */         GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
/*  248 */         renderModel(model, -8372020);
/*  249 */         GlStateManager.popMatrix();
/*  250 */         GlStateManager.matrixMode(5888);
/*  251 */         GlStateManager.blendFunc(770, 771);
/*  252 */         GlStateManager.enableLighting();
/*  253 */         GlStateManager.depthFunc(515);
/*  254 */         GlStateManager.depthMask(true);
/*  255 */         this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*      */         
/*  257 */         if (Config.isShaders() && !this.renderItemGui)
/*      */         {
/*  259 */           ShadersRender.renderEnchantedGlintEnd();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void putQuadNormal(WorldRenderer renderer, BakedQuad quad) {
/*  267 */     Vec3i vec3i = quad.getFace().getDirectionVec();
/*  268 */     renderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderQuad(WorldRenderer renderer, BakedQuad quad, int color) {
/*  273 */     if (this.renderModelEmissive) {
/*      */       
/*  275 */       if (quad.getQuadEmissive() == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  280 */       quad = quad.getQuadEmissive();
/*      */     }
/*  282 */     else if (quad.getQuadEmissive() != null) {
/*      */       
/*  284 */       this.renderModelHasEmissive = true;
/*      */     } 
/*      */     
/*  287 */     if (renderer.isMultiTexture()) {
/*      */       
/*  289 */       renderer.addVertexData(quad.getVertexDataSingle());
/*      */     }
/*      */     else {
/*      */       
/*  293 */       renderer.addVertexData(quad.getVertexData());
/*      */     } 
/*      */     
/*  296 */     renderer.putSprite(quad.getSprite());
/*      */     
/*  298 */     if (Reflector.IColoredBakedQuad.exists() && Reflector.IColoredBakedQuad.isInstance(quad)) {
/*      */       
/*  300 */       forgeHooksClient_putQuadColor(renderer, quad, color);
/*      */     }
/*      */     else {
/*      */       
/*  304 */       renderer.putColor4(color);
/*      */     } 
/*      */     
/*  307 */     putQuadNormal(renderer, quad);
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderQuads(WorldRenderer renderer, List<BakedQuad> quads, int color, ItemStack stack) {
/*  312 */     boolean flag = (color == -1 && stack != null);
/*  313 */     int i = 0;
/*      */     
/*  315 */     for (int j = quads.size(); i < j; i++) {
/*      */       
/*  317 */       BakedQuad bakedquad = quads.get(i);
/*  318 */       int k = color;
/*      */       
/*  320 */       if (flag && bakedquad.hasTintIndex()) {
/*      */         
/*  322 */         k = stack.getItem().getColorFromItemStack(stack, bakedquad.getTintIndex());
/*      */         
/*  324 */         if (Config.isCustomColors())
/*      */         {
/*  326 */           k = CustomColors.getColorFromItemStack(stack, bakedquad.getTintIndex(), k);
/*      */         }
/*      */         
/*  329 */         if (EntityRenderer.anaglyphEnable)
/*      */         {
/*  331 */           k = TextureUtil.anaglyphColor(k);
/*      */         }
/*      */         
/*  334 */         k |= 0xFF000000;
/*      */       } 
/*      */       
/*  337 */       renderQuad(renderer, bakedquad, k);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldRenderItemIn3D(ItemStack stack) {
/*  343 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  344 */     return (ibakedmodel == null) ? false : ibakedmodel.isGui3d();
/*      */   }
/*      */ 
/*      */   
/*      */   private void preTransform(ItemStack stack) {
/*  349 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  350 */     Item item = stack.getItem();
/*      */     
/*  352 */     if (item != null) {
/*      */       
/*  354 */       boolean flag = ibakedmodel.isGui3d();
/*      */       
/*  356 */       if (!flag)
/*      */       {
/*  358 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*      */       }
/*      */       
/*  361 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181564_a(ItemStack p_181564_1_, ItemCameraTransforms.TransformType p_181564_2_) {
/*  367 */     if (p_181564_1_ != null) {
/*      */       
/*  369 */       IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(p_181564_1_);
/*  370 */       renderItemModelTransform(p_181564_1_, ibakedmodel, p_181564_2_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType) {
/*  376 */     if (stack != null && entityToRenderFor != null) {
/*      */       
/*  378 */       IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*      */       
/*  380 */       if (entityToRenderFor instanceof EntityPlayer) {
/*      */         
/*  382 */         EntityPlayer entityplayer = (EntityPlayer)entityToRenderFor;
/*  383 */         Item item = stack.getItem();
/*  384 */         ModelResourceLocation modelresourcelocation = null;
/*      */         
/*  386 */         if (item == Items.fishing_rod && entityplayer.fishEntity != null) {
/*      */           
/*  388 */           modelresourcelocation = new ModelResourceLocation("fishing_rod_cast", "inventory");
/*      */         }
/*  390 */         else if (item == Items.bow && entityplayer.getItemInUse() != null) {
/*      */           
/*  392 */           int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();
/*      */           
/*  394 */           if (i >= 18)
/*      */           {
/*  396 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_2", "inventory");
/*      */           }
/*  398 */           else if (i > 13)
/*      */           {
/*  400 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_1", "inventory");
/*      */           }
/*  402 */           else if (i > 0)
/*      */           {
/*  404 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_0", "inventory");
/*      */           }
/*      */         
/*  407 */         } else if (Reflector.ForgeItem_getModel.exists()) {
/*      */           
/*  409 */           modelresourcelocation = (ModelResourceLocation)Reflector.call(item, Reflector.ForgeItem_getModel, new Object[] { stack, entityplayer, Integer.valueOf(entityplayer.getItemInUseCount()) });
/*      */         } 
/*      */         
/*  412 */         if (modelresourcelocation != null) {
/*      */           
/*  414 */           ibakedmodel = this.itemModelMesher.getModelManager().getModel(modelresourcelocation);
/*      */           
/*  416 */           if (Config.isCustomItems())
/*      */           {
/*  418 */             ibakedmodel = CustomItems.getCustomItemModel(stack, ibakedmodel, (ResourceLocation)modelresourcelocation, true);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  423 */       renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderItemModelTransform(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
/*  429 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  430 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*  431 */     preTransform(stack);
/*  432 */     GlStateManager.enableRescaleNormal();
/*  433 */     GlStateManager.alphaFunc(516, 0.1F);
/*  434 */     GlStateManager.enableBlend();
/*  435 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  436 */     GlStateManager.pushMatrix();
/*      */     
/*  438 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*      */       
/*  440 */       model = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { model, cameraTransformType });
/*      */     }
/*      */     else {
/*      */       
/*  444 */       ItemCameraTransforms itemcameratransforms = model.getItemCameraTransforms();
/*  445 */       itemcameratransforms.func_181689_a(cameraTransformType);
/*      */       
/*  447 */       if (func_183005_a(itemcameratransforms.func_181688_b(cameraTransformType)))
/*      */       {
/*  449 */         GlStateManager.cullFace(1028);
/*      */       }
/*      */     } 
/*      */     
/*  453 */     renderItem(stack, model);
/*  454 */     GlStateManager.cullFace(1029);
/*  455 */     GlStateManager.popMatrix();
/*  456 */     GlStateManager.disableRescaleNormal();
/*  457 */     GlStateManager.disableBlend();
/*  458 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  459 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_183005_a(ItemTransformVec3f p_183005_1_) {
/*  464 */     return ((p_183005_1_.scale.x < 0.0F)) ^ ((p_183005_1_.scale.y < 0.0F)) ^ ((p_183005_1_.scale.z < 0.0F) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemIntoGUI(ItemStack stack, int x, int y) {
/*  469 */     this.renderItemGui = true;
/*  470 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  471 */     GlStateManager.pushMatrix();
/*  472 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  473 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*  474 */     GlStateManager.enableRescaleNormal();
/*  475 */     GlStateManager.enableAlpha();
/*  476 */     GlStateManager.alphaFunc(516, 0.1F);
/*  477 */     GlStateManager.enableBlend();
/*  478 */     GlStateManager.blendFunc(770, 771);
/*  479 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  480 */     setupGuiTransform(x, y, ibakedmodel.isGui3d());
/*      */     
/*  482 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*      */       
/*  484 */       ibakedmodel = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { ibakedmodel, ItemCameraTransforms.TransformType.GUI });
/*      */     }
/*      */     else {
/*      */       
/*  488 */       ibakedmodel.getItemCameraTransforms().func_181689_a(ItemCameraTransforms.TransformType.GUI);
/*      */     } 
/*      */     
/*  491 */     renderItem(stack, ibakedmodel);
/*  492 */     GlStateManager.disableAlpha();
/*  493 */     GlStateManager.disableRescaleNormal();
/*  494 */     GlStateManager.disableLighting();
/*  495 */     GlStateManager.popMatrix();
/*  496 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  497 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*  498 */     this.renderItemGui = false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d) {
/*  503 */     GlStateManager.translate(xPosition, yPosition, 100.0F + this.zLevel);
/*  504 */     GlStateManager.translate(8.0F, 8.0F, 0.0F);
/*  505 */     GlStateManager.scale(1.0F, 1.0F, -1.0F);
/*  506 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*      */     
/*  508 */     if (isGui3d) {
/*      */       
/*  510 */       GlStateManager.scale(40.0F, 40.0F, 40.0F);
/*  511 */       GlStateManager.rotate(210.0F, 1.0F, 0.0F, 0.0F);
/*  512 */       GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  513 */       GlStateManager.enableLighting();
/*      */     }
/*      */     else {
/*      */       
/*  517 */       GlStateManager.scale(64.0F, 64.0F, 64.0F);
/*  518 */       GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*  519 */       GlStateManager.disableLighting();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemAndEffectIntoGUI(final ItemStack stack, int xPosition, int yPosition) {
/*  525 */     if (stack != null && stack.getItem() != null) {
/*      */       
/*  527 */       this.zLevel += 50.0F;
/*      */ 
/*      */       
/*      */       try {
/*  531 */         renderItemIntoGUI(stack, xPosition, yPosition);
/*      */       }
/*  533 */       catch (Throwable throwable) {
/*      */         
/*  535 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
/*  536 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
/*  537 */         crashreportcategory.addCrashSectionCallable("Item Type", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  541 */                 return String.valueOf(stack.getItem());
/*      */               }
/*      */             });
/*  544 */         crashreportcategory.addCrashSectionCallable("Item Aux", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  548 */                 return String.valueOf(stack.getMetadata());
/*      */               }
/*      */             });
/*  551 */         crashreportcategory.addCrashSectionCallable("Item NBT", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  555 */                 return String.valueOf(stack.getTagCompound());
/*      */               }
/*      */             });
/*  558 */         crashreportcategory.addCrashSectionCallable("Item Foil", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  562 */                 return String.valueOf(stack.hasEffect());
/*      */               }
/*      */             });
/*  565 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  568 */       this.zLevel -= 50.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition) {
/*  574 */     renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
/*  582 */     if (stack != null) {
/*      */       
/*  584 */       if (stack.stackSize != 1 || text != null) {
/*      */         
/*  586 */         String s = (text == null) ? String.valueOf(stack.stackSize) : text;
/*      */         
/*  588 */         if (text == null && stack.stackSize < 1)
/*      */         {
/*  590 */           s = ChatFormatting.RED + String.valueOf(stack.stackSize);
/*      */         }
/*      */         
/*  593 */         GlStateManager.disableLighting();
/*  594 */         GlStateManager.disableDepth();
/*  595 */         GlStateManager.disableBlend();
/*  596 */         fr.drawStringWithShadow(s, (xPosition + 19 - 2 - fr.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
/*  597 */         GlStateManager.enableLighting();
/*  598 */         GlStateManager.enableDepth();
/*  599 */         GlStateManager.enableBlend();
/*      */       } 
/*      */       
/*  602 */       if (ReflectorForge.isItemDamaged(stack)) {
/*      */         
/*  604 */         int j1 = (int)Math.round(13.0D - stack.getItemDamage() * 13.0D / stack.getMaxDamage());
/*  605 */         int i = (int)Math.round(255.0D - stack.getItemDamage() * 255.0D / stack.getMaxDamage());
/*      */         
/*  607 */         if (Reflector.ForgeItem_getDurabilityForDisplay.exists()) {
/*      */           
/*  609 */           double d0 = Reflector.callDouble(stack.getItem(), Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack });
/*  610 */           j1 = (int)Math.round(13.0D - d0 * 13.0D);
/*  611 */           i = (int)Math.round(255.0D - d0 * 255.0D);
/*      */         } 
/*      */         
/*  614 */         GlStateManager.disableLighting();
/*  615 */         GlStateManager.disableDepth();
/*  616 */         GlStateManager.disableTexture2D();
/*  617 */         GlStateManager.disableAlpha();
/*  618 */         GlStateManager.disableBlend();
/*  619 */         Tessellator tessellator = Tessellator.getInstance();
/*  620 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  621 */         func_181565_a(worldrenderer, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
/*  622 */         func_181565_a(worldrenderer, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
/*  623 */         int j = 255 - i;
/*  624 */         int k = i;
/*  625 */         int l = 0;
/*      */         
/*  627 */         if (Config.isCustomColors()) {
/*      */           
/*  629 */           int i1 = CustomColors.getDurabilityColor(i);
/*      */           
/*  631 */           if (i1 >= 0) {
/*      */             
/*  633 */             j = i1 >> 16 & 0xFF;
/*  634 */             k = i1 >> 8 & 0xFF;
/*  635 */             l = i1 >> 0 & 0xFF;
/*      */           } 
/*      */         } 
/*      */         
/*  639 */         func_181565_a(worldrenderer, xPosition + 2, yPosition + 13, j1, 1, j, k, l, 255);
/*  640 */         GlStateManager.enableBlend();
/*  641 */         GlStateManager.enableAlpha();
/*  642 */         GlStateManager.enableTexture2D();
/*  643 */         GlStateManager.enableLighting();
/*  644 */         GlStateManager.enableDepth();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_181565_a(WorldRenderer p_181565_1_, int p_181565_2_, int p_181565_3_, int p_181565_4_, int p_181565_5_, int p_181565_6_, int p_181565_7_, int p_181565_8_, int p_181565_9_) {
/*  651 */     p_181565_1_.begin(7, DefaultVertexFormats.field_181706_f);
/*  652 */     p_181565_1_.pos((p_181565_2_ + 0), (p_181565_3_ + 0), 0.0D).func_181669_b(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
/*  653 */     p_181565_1_.pos((p_181565_2_ + 0), (p_181565_3_ + p_181565_5_), 0.0D).func_181669_b(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
/*  654 */     p_181565_1_.pos((p_181565_2_ + p_181565_4_), (p_181565_3_ + p_181565_5_), 0.0D).func_181669_b(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
/*  655 */     p_181565_1_.pos((p_181565_2_ + p_181565_4_), (p_181565_3_ + 0), 0.0D).func_181669_b(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
/*  656 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerItems() {
/*  661 */     registerBlock(Blocks.anvil, "anvil_intact");
/*  662 */     registerBlock(Blocks.anvil, 1, "anvil_slightly_damaged");
/*  663 */     registerBlock(Blocks.anvil, 2, "anvil_very_damaged");
/*  664 */     registerBlock(Blocks.carpet, EnumDyeColor.BLACK.getMetadata(), "black_carpet");
/*  665 */     registerBlock(Blocks.carpet, EnumDyeColor.BLUE.getMetadata(), "blue_carpet");
/*  666 */     registerBlock(Blocks.carpet, EnumDyeColor.BROWN.getMetadata(), "brown_carpet");
/*  667 */     registerBlock(Blocks.carpet, EnumDyeColor.CYAN.getMetadata(), "cyan_carpet");
/*  668 */     registerBlock(Blocks.carpet, EnumDyeColor.GRAY.getMetadata(), "gray_carpet");
/*  669 */     registerBlock(Blocks.carpet, EnumDyeColor.GREEN.getMetadata(), "green_carpet");
/*  670 */     registerBlock(Blocks.carpet, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_carpet");
/*  671 */     registerBlock(Blocks.carpet, EnumDyeColor.LIME.getMetadata(), "lime_carpet");
/*  672 */     registerBlock(Blocks.carpet, EnumDyeColor.MAGENTA.getMetadata(), "magenta_carpet");
/*  673 */     registerBlock(Blocks.carpet, EnumDyeColor.ORANGE.getMetadata(), "orange_carpet");
/*  674 */     registerBlock(Blocks.carpet, EnumDyeColor.PINK.getMetadata(), "pink_carpet");
/*  675 */     registerBlock(Blocks.carpet, EnumDyeColor.PURPLE.getMetadata(), "purple_carpet");
/*  676 */     registerBlock(Blocks.carpet, EnumDyeColor.RED.getMetadata(), "red_carpet");
/*  677 */     registerBlock(Blocks.carpet, EnumDyeColor.SILVER.getMetadata(), "silver_carpet");
/*  678 */     registerBlock(Blocks.carpet, EnumDyeColor.WHITE.getMetadata(), "white_carpet");
/*  679 */     registerBlock(Blocks.carpet, EnumDyeColor.YELLOW.getMetadata(), "yellow_carpet");
/*  680 */     registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.MOSSY.getMetadata(), "mossy_cobblestone_wall");
/*  681 */     registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.NORMAL.getMetadata(), "cobblestone_wall");
/*  682 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
/*  683 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.DIRT.getMetadata(), "dirt");
/*  684 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata(), "podzol");
/*  685 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.FERN.getMeta(), "double_fern");
/*  686 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.GRASS.getMeta(), "double_grass");
/*  687 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta(), "paeonia");
/*  688 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.ROSE.getMeta(), "double_rose");
/*  689 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta(), "sunflower");
/*  690 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta(), "syringa");
/*  691 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_leaves");
/*  692 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_leaves");
/*  693 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.OAK.getMetadata(), "oak_leaves");
/*  694 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_leaves");
/*  695 */     registerBlock((Block)Blocks.leaves2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_leaves");
/*  696 */     registerBlock((Block)Blocks.leaves2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_leaves");
/*  697 */     registerBlock(Blocks.log, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_log");
/*  698 */     registerBlock(Blocks.log, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_log");
/*  699 */     registerBlock(Blocks.log, BlockPlanks.EnumType.OAK.getMetadata(), "oak_log");
/*  700 */     registerBlock(Blocks.log, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_log");
/*  701 */     registerBlock(Blocks.log2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_log");
/*  702 */     registerBlock(Blocks.log2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_log");
/*  703 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CHISELED_STONEBRICK.getMetadata(), "chiseled_brick_monster_egg");
/*  704 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.COBBLESTONE.getMetadata(), "cobblestone_monster_egg");
/*  705 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CRACKED_STONEBRICK.getMetadata(), "cracked_brick_monster_egg");
/*  706 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.MOSSY_STONEBRICK.getMetadata(), "mossy_brick_monster_egg");
/*  707 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONE.getMetadata(), "stone_monster_egg");
/*  708 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONEBRICK.getMetadata(), "stone_brick_monster_egg");
/*  709 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_planks");
/*  710 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_planks");
/*  711 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_planks");
/*  712 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_planks");
/*  713 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.OAK.getMetadata(), "oak_planks");
/*  714 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_planks");
/*  715 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.BRICKS.getMetadata(), "prismarine_bricks");
/*  716 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.DARK.getMetadata(), "dark_prismarine");
/*  717 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.ROUGH.getMetadata(), "prismarine");
/*  718 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.CHISELED.getMetadata(), "chiseled_quartz_block");
/*  719 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.DEFAULT.getMetadata(), "quartz_block");
/*  720 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.LINES_Y.getMetadata(), "quartz_column");
/*  721 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.ALLIUM.getMeta(), "allium");
/*  722 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta(), "blue_orchid");
/*  723 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta(), "houstonia");
/*  724 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta(), "orange_tulip");
/*  725 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta(), "oxeye_daisy");
/*  726 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta(), "pink_tulip");
/*  727 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.POPPY.getMeta(), "poppy");
/*  728 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.RED_TULIP.getMeta(), "red_tulip");
/*  729 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta(), "white_tulip");
/*  730 */     registerBlock((Block)Blocks.sand, BlockSand.EnumType.RED_SAND.getMetadata(), "red_sand");
/*  731 */     registerBlock((Block)Blocks.sand, BlockSand.EnumType.SAND.getMetadata(), "sand");
/*  732 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.CHISELED.getMetadata(), "chiseled_sandstone");
/*  733 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.DEFAULT.getMetadata(), "sandstone");
/*  734 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.SMOOTH.getMetadata(), "smooth_sandstone");
/*  735 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.CHISELED.getMetadata(), "chiseled_red_sandstone");
/*  736 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.DEFAULT.getMetadata(), "red_sandstone");
/*  737 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.SMOOTH.getMetadata(), "smooth_red_sandstone");
/*  738 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_sapling");
/*  739 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_sapling");
/*  740 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_sapling");
/*  741 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_sapling");
/*  742 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.OAK.getMetadata(), "oak_sapling");
/*  743 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_sapling");
/*  744 */     registerBlock(Blocks.sponge, 0, "sponge");
/*  745 */     registerBlock(Blocks.sponge, 1, "sponge_wet");
/*  746 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass");
/*  747 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass");
/*  748 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass");
/*  749 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass");
/*  750 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass");
/*  751 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass");
/*  752 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass");
/*  753 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass");
/*  754 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass");
/*  755 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass");
/*  756 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass");
/*  757 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass");
/*  758 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.RED.getMetadata(), "red_stained_glass");
/*  759 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass");
/*  760 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass");
/*  761 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass");
/*  762 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass_pane");
/*  763 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass_pane");
/*  764 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass_pane");
/*  765 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass_pane");
/*  766 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass_pane");
/*  767 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass_pane");
/*  768 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass_pane");
/*  769 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass_pane");
/*  770 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass_pane");
/*  771 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass_pane");
/*  772 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass_pane");
/*  773 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass_pane");
/*  774 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.RED.getMetadata(), "red_stained_glass_pane");
/*  775 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass_pane");
/*  776 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass_pane");
/*  777 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass_pane");
/*  778 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLACK.getMetadata(), "black_stained_hardened_clay");
/*  779 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLUE.getMetadata(), "blue_stained_hardened_clay");
/*  780 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BROWN.getMetadata(), "brown_stained_hardened_clay");
/*  781 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_hardened_clay");
/*  782 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GRAY.getMetadata(), "gray_stained_hardened_clay");
/*  783 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GREEN.getMetadata(), "green_stained_hardened_clay");
/*  784 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_hardened_clay");
/*  785 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIME.getMetadata(), "lime_stained_hardened_clay");
/*  786 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_hardened_clay");
/*  787 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_hardened_clay");
/*  788 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PINK.getMetadata(), "pink_stained_hardened_clay");
/*  789 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_hardened_clay");
/*  790 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.RED.getMetadata(), "red_stained_hardened_clay");
/*  791 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.SILVER.getMetadata(), "silver_stained_hardened_clay");
/*  792 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.WHITE.getMetadata(), "white_stained_hardened_clay");
/*  793 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_hardened_clay");
/*  794 */     registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE.getMetadata(), "andesite");
/*  795 */     registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata(), "andesite_smooth");
/*  796 */     registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE.getMetadata(), "diorite");
/*  797 */     registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata(), "diorite_smooth");
/*  798 */     registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE.getMetadata(), "granite");
/*  799 */     registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata(), "granite_smooth");
/*  800 */     registerBlock(Blocks.stone, BlockStone.EnumType.STONE.getMetadata(), "stone");
/*  801 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CRACKED.getMetadata(), "cracked_stonebrick");
/*  802 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.DEFAULT.getMetadata(), "stonebrick");
/*  803 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CHISELED.getMetadata(), "chiseled_stonebrick");
/*  804 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.MOSSY.getMetadata(), "mossy_stonebrick");
/*  805 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.BRICK.getMetadata(), "brick_slab");
/*  806 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata(), "cobblestone_slab");
/*  807 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.WOOD.getMetadata(), "old_wood_slab");
/*  808 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata(), "nether_brick_slab");
/*  809 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.QUARTZ.getMetadata(), "quartz_slab");
/*  810 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.SAND.getMetadata(), "sandstone_slab");
/*  811 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata(), "stone_brick_slab");
/*  812 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.STONE.getMetadata(), "stone_slab");
/*  813 */     registerBlock((Block)Blocks.stone_slab2, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata(), "red_sandstone_slab");
/*  814 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.DEAD_BUSH.getMeta(), "dead_bush");
/*  815 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.FERN.getMeta(), "fern");
/*  816 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.GRASS.getMeta(), "tall_grass");
/*  817 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_slab");
/*  818 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_slab");
/*  819 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_slab");
/*  820 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_slab");
/*  821 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.OAK.getMetadata(), "oak_slab");
/*  822 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_slab");
/*  823 */     registerBlock(Blocks.wool, EnumDyeColor.BLACK.getMetadata(), "black_wool");
/*  824 */     registerBlock(Blocks.wool, EnumDyeColor.BLUE.getMetadata(), "blue_wool");
/*  825 */     registerBlock(Blocks.wool, EnumDyeColor.BROWN.getMetadata(), "brown_wool");
/*  826 */     registerBlock(Blocks.wool, EnumDyeColor.CYAN.getMetadata(), "cyan_wool");
/*  827 */     registerBlock(Blocks.wool, EnumDyeColor.GRAY.getMetadata(), "gray_wool");
/*  828 */     registerBlock(Blocks.wool, EnumDyeColor.GREEN.getMetadata(), "green_wool");
/*  829 */     registerBlock(Blocks.wool, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_wool");
/*  830 */     registerBlock(Blocks.wool, EnumDyeColor.LIME.getMetadata(), "lime_wool");
/*  831 */     registerBlock(Blocks.wool, EnumDyeColor.MAGENTA.getMetadata(), "magenta_wool");
/*  832 */     registerBlock(Blocks.wool, EnumDyeColor.ORANGE.getMetadata(), "orange_wool");
/*  833 */     registerBlock(Blocks.wool, EnumDyeColor.PINK.getMetadata(), "pink_wool");
/*  834 */     registerBlock(Blocks.wool, EnumDyeColor.PURPLE.getMetadata(), "purple_wool");
/*  835 */     registerBlock(Blocks.wool, EnumDyeColor.RED.getMetadata(), "red_wool");
/*  836 */     registerBlock(Blocks.wool, EnumDyeColor.SILVER.getMetadata(), "silver_wool");
/*  837 */     registerBlock(Blocks.wool, EnumDyeColor.WHITE.getMetadata(), "white_wool");
/*  838 */     registerBlock(Blocks.wool, EnumDyeColor.YELLOW.getMetadata(), "yellow_wool");
/*  839 */     registerBlock(Blocks.acacia_stairs, "acacia_stairs");
/*  840 */     registerBlock(Blocks.activator_rail, "activator_rail");
/*  841 */     registerBlock((Block)Blocks.beacon, "beacon");
/*  842 */     registerBlock(Blocks.bedrock, "bedrock");
/*  843 */     registerBlock(Blocks.birch_stairs, "birch_stairs");
/*  844 */     registerBlock(Blocks.bookshelf, "bookshelf");
/*  845 */     registerBlock(Blocks.brick_block, "brick_block");
/*  846 */     registerBlock(Blocks.brick_block, "brick_block");
/*  847 */     registerBlock(Blocks.brick_stairs, "brick_stairs");
/*  848 */     registerBlock((Block)Blocks.brown_mushroom, "brown_mushroom");
/*  849 */     registerBlock((Block)Blocks.cactus, "cactus");
/*  850 */     registerBlock(Blocks.clay, "clay");
/*  851 */     registerBlock(Blocks.coal_block, "coal_block");
/*  852 */     registerBlock(Blocks.coal_ore, "coal_ore");
/*  853 */     registerBlock(Blocks.cobblestone, "cobblestone");
/*  854 */     registerBlock(Blocks.crafting_table, "crafting_table");
/*  855 */     registerBlock(Blocks.dark_oak_stairs, "dark_oak_stairs");
/*  856 */     registerBlock((Block)Blocks.daylight_detector, "daylight_detector");
/*  857 */     registerBlock((Block)Blocks.deadbush, "dead_bush");
/*  858 */     registerBlock(Blocks.detector_rail, "detector_rail");
/*  859 */     registerBlock(Blocks.diamond_block, "diamond_block");
/*  860 */     registerBlock(Blocks.diamond_ore, "diamond_ore");
/*  861 */     registerBlock(Blocks.dispenser, "dispenser");
/*  862 */     registerBlock(Blocks.dropper, "dropper");
/*  863 */     registerBlock(Blocks.emerald_block, "emerald_block");
/*  864 */     registerBlock(Blocks.emerald_ore, "emerald_ore");
/*  865 */     registerBlock(Blocks.enchanting_table, "enchanting_table");
/*  866 */     registerBlock(Blocks.end_portal_frame, "end_portal_frame");
/*  867 */     registerBlock(Blocks.end_stone, "end_stone");
/*  868 */     registerBlock(Blocks.oak_fence, "oak_fence");
/*  869 */     registerBlock(Blocks.spruce_fence, "spruce_fence");
/*  870 */     registerBlock(Blocks.birch_fence, "birch_fence");
/*  871 */     registerBlock(Blocks.jungle_fence, "jungle_fence");
/*  872 */     registerBlock(Blocks.dark_oak_fence, "dark_oak_fence");
/*  873 */     registerBlock(Blocks.acacia_fence, "acacia_fence");
/*  874 */     registerBlock(Blocks.oak_fence_gate, "oak_fence_gate");
/*  875 */     registerBlock(Blocks.spruce_fence_gate, "spruce_fence_gate");
/*  876 */     registerBlock(Blocks.birch_fence_gate, "birch_fence_gate");
/*  877 */     registerBlock(Blocks.jungle_fence_gate, "jungle_fence_gate");
/*  878 */     registerBlock(Blocks.dark_oak_fence_gate, "dark_oak_fence_gate");
/*  879 */     registerBlock(Blocks.acacia_fence_gate, "acacia_fence_gate");
/*  880 */     registerBlock(Blocks.furnace, "furnace");
/*  881 */     registerBlock(Blocks.glass, "glass");
/*  882 */     registerBlock(Blocks.glass_pane, "glass_pane");
/*  883 */     registerBlock(Blocks.glowstone, "glowstone");
/*  884 */     registerBlock(Blocks.golden_rail, "golden_rail");
/*  885 */     registerBlock(Blocks.gold_block, "gold_block");
/*  886 */     registerBlock(Blocks.gold_ore, "gold_ore");
/*  887 */     registerBlock((Block)Blocks.grass, "grass");
/*  888 */     registerBlock(Blocks.gravel, "gravel");
/*  889 */     registerBlock(Blocks.hardened_clay, "hardened_clay");
/*  890 */     registerBlock(Blocks.hay_block, "hay_block");
/*  891 */     registerBlock(Blocks.heavy_weighted_pressure_plate, "heavy_weighted_pressure_plate");
/*  892 */     registerBlock((Block)Blocks.hopper, "hopper");
/*  893 */     registerBlock(Blocks.ice, "ice");
/*  894 */     registerBlock(Blocks.iron_bars, "iron_bars");
/*  895 */     registerBlock(Blocks.iron_block, "iron_block");
/*  896 */     registerBlock(Blocks.iron_ore, "iron_ore");
/*  897 */     registerBlock(Blocks.iron_trapdoor, "iron_trapdoor");
/*  898 */     registerBlock(Blocks.jukebox, "jukebox");
/*  899 */     registerBlock(Blocks.jungle_stairs, "jungle_stairs");
/*  900 */     registerBlock(Blocks.ladder, "ladder");
/*  901 */     registerBlock(Blocks.lapis_block, "lapis_block");
/*  902 */     registerBlock(Blocks.lapis_ore, "lapis_ore");
/*  903 */     registerBlock(Blocks.lever, "lever");
/*  904 */     registerBlock(Blocks.light_weighted_pressure_plate, "light_weighted_pressure_plate");
/*  905 */     registerBlock(Blocks.lit_pumpkin, "lit_pumpkin");
/*  906 */     registerBlock(Blocks.melon_block, "melon_block");
/*  907 */     registerBlock(Blocks.mossy_cobblestone, "mossy_cobblestone");
/*  908 */     registerBlock((Block)Blocks.mycelium, "mycelium");
/*  909 */     registerBlock(Blocks.netherrack, "netherrack");
/*  910 */     registerBlock(Blocks.nether_brick, "nether_brick");
/*  911 */     registerBlock(Blocks.nether_brick_fence, "nether_brick_fence");
/*  912 */     registerBlock(Blocks.nether_brick_stairs, "nether_brick_stairs");
/*  913 */     registerBlock(Blocks.noteblock, "noteblock");
/*  914 */     registerBlock(Blocks.oak_stairs, "oak_stairs");
/*  915 */     registerBlock(Blocks.obsidian, "obsidian");
/*  916 */     registerBlock(Blocks.packed_ice, "packed_ice");
/*  917 */     registerBlock((Block)Blocks.piston, "piston");
/*  918 */     registerBlock(Blocks.pumpkin, "pumpkin");
/*  919 */     registerBlock(Blocks.quartz_ore, "quartz_ore");
/*  920 */     registerBlock(Blocks.quartz_stairs, "quartz_stairs");
/*  921 */     registerBlock(Blocks.rail, "rail");
/*  922 */     registerBlock(Blocks.redstone_block, "redstone_block");
/*  923 */     registerBlock(Blocks.redstone_lamp, "redstone_lamp");
/*  924 */     registerBlock(Blocks.redstone_ore, "redstone_ore");
/*  925 */     registerBlock(Blocks.redstone_torch, "redstone_torch");
/*  926 */     registerBlock((Block)Blocks.red_mushroom, "red_mushroom");
/*  927 */     registerBlock(Blocks.sandstone_stairs, "sandstone_stairs");
/*  928 */     registerBlock(Blocks.red_sandstone_stairs, "red_sandstone_stairs");
/*  929 */     registerBlock(Blocks.sea_lantern, "sea_lantern");
/*  930 */     registerBlock(Blocks.slime_block, "slime");
/*  931 */     registerBlock(Blocks.snow, "snow");
/*  932 */     registerBlock(Blocks.snow_layer, "snow_layer");
/*  933 */     registerBlock(Blocks.soul_sand, "soul_sand");
/*  934 */     registerBlock(Blocks.spruce_stairs, "spruce_stairs");
/*  935 */     registerBlock((Block)Blocks.sticky_piston, "sticky_piston");
/*  936 */     registerBlock(Blocks.stone_brick_stairs, "stone_brick_stairs");
/*  937 */     registerBlock(Blocks.stone_button, "stone_button");
/*  938 */     registerBlock(Blocks.stone_pressure_plate, "stone_pressure_plate");
/*  939 */     registerBlock(Blocks.stone_stairs, "stone_stairs");
/*  940 */     registerBlock(Blocks.tnt, "tnt");
/*  941 */     registerBlock(Blocks.torch, "torch");
/*  942 */     registerBlock(Blocks.trapdoor, "trapdoor");
/*  943 */     registerBlock((Block)Blocks.tripwire_hook, "tripwire_hook");
/*  944 */     registerBlock(Blocks.vine, "vine");
/*  945 */     registerBlock(Blocks.waterlily, "waterlily");
/*  946 */     registerBlock(Blocks.web, "web");
/*  947 */     registerBlock(Blocks.wooden_button, "wooden_button");
/*  948 */     registerBlock(Blocks.wooden_pressure_plate, "wooden_pressure_plate");
/*  949 */     registerBlock((Block)Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION.getMeta(), "dandelion");
/*  950 */     registerBlock((Block)Blocks.chest, "chest");
/*  951 */     registerBlock(Blocks.trapped_chest, "trapped_chest");
/*  952 */     registerBlock(Blocks.ender_chest, "ender_chest");
/*  953 */     registerItem(Items.iron_shovel, "iron_shovel");
/*  954 */     registerItem(Items.iron_pickaxe, "iron_pickaxe");
/*  955 */     registerItem(Items.iron_axe, "iron_axe");
/*  956 */     registerItem(Items.flint_and_steel, "flint_and_steel");
/*  957 */     registerItem(Items.apple, "apple");
/*  958 */     registerItem((Item)Items.bow, 0, "bow");
/*  959 */     registerItem((Item)Items.bow, 1, "bow_pulling_0");
/*  960 */     registerItem((Item)Items.bow, 2, "bow_pulling_1");
/*  961 */     registerItem((Item)Items.bow, 3, "bow_pulling_2");
/*  962 */     registerItem(Items.arrow, "arrow");
/*  963 */     registerItem(Items.coal, 0, "coal");
/*  964 */     registerItem(Items.coal, 1, "charcoal");
/*  965 */     registerItem(Items.diamond, "diamond");
/*  966 */     registerItem(Items.iron_ingot, "iron_ingot");
/*  967 */     registerItem(Items.gold_ingot, "gold_ingot");
/*  968 */     registerItem(Items.iron_sword, "iron_sword");
/*  969 */     registerItem(Items.wooden_sword, "wooden_sword");
/*  970 */     registerItem(Items.wooden_shovel, "wooden_shovel");
/*  971 */     registerItem(Items.wooden_pickaxe, "wooden_pickaxe");
/*  972 */     registerItem(Items.wooden_axe, "wooden_axe");
/*  973 */     registerItem(Items.stone_sword, "stone_sword");
/*  974 */     registerItem(Items.stone_shovel, "stone_shovel");
/*  975 */     registerItem(Items.stone_pickaxe, "stone_pickaxe");
/*  976 */     registerItem(Items.stone_axe, "stone_axe");
/*  977 */     registerItem(Items.diamond_sword, "diamond_sword");
/*  978 */     registerItem(Items.diamond_shovel, "diamond_shovel");
/*  979 */     registerItem(Items.diamond_pickaxe, "diamond_pickaxe");
/*  980 */     registerItem(Items.diamond_axe, "diamond_axe");
/*  981 */     registerItem(Items.stick, "stick");
/*  982 */     registerItem(Items.bowl, "bowl");
/*  983 */     registerItem(Items.mushroom_stew, "mushroom_stew");
/*  984 */     registerItem(Items.golden_sword, "golden_sword");
/*  985 */     registerItem(Items.golden_shovel, "golden_shovel");
/*  986 */     registerItem(Items.golden_pickaxe, "golden_pickaxe");
/*  987 */     registerItem(Items.golden_axe, "golden_axe");
/*  988 */     registerItem(Items.string, "string");
/*  989 */     registerItem(Items.feather, "feather");
/*  990 */     registerItem(Items.gunpowder, "gunpowder");
/*  991 */     registerItem(Items.wooden_hoe, "wooden_hoe");
/*  992 */     registerItem(Items.stone_hoe, "stone_hoe");
/*  993 */     registerItem(Items.iron_hoe, "iron_hoe");
/*  994 */     registerItem(Items.diamond_hoe, "diamond_hoe");
/*  995 */     registerItem(Items.golden_hoe, "golden_hoe");
/*  996 */     registerItem(Items.wheat_seeds, "wheat_seeds");
/*  997 */     registerItem(Items.wheat, "wheat");
/*  998 */     registerItem(Items.bread, "bread");
/*  999 */     registerItem((Item)Items.leather_helmet, "leather_helmet");
/* 1000 */     registerItem((Item)Items.leather_chestplate, "leather_chestplate");
/* 1001 */     registerItem((Item)Items.leather_leggings, "leather_leggings");
/* 1002 */     registerItem((Item)Items.leather_boots, "leather_boots");
/* 1003 */     registerItem((Item)Items.chainmail_helmet, "chainmail_helmet");
/* 1004 */     registerItem((Item)Items.chainmail_chestplate, "chainmail_chestplate");
/* 1005 */     registerItem((Item)Items.chainmail_leggings, "chainmail_leggings");
/* 1006 */     registerItem((Item)Items.chainmail_boots, "chainmail_boots");
/* 1007 */     registerItem((Item)Items.iron_helmet, "iron_helmet");
/* 1008 */     registerItem((Item)Items.iron_chestplate, "iron_chestplate");
/* 1009 */     registerItem((Item)Items.iron_leggings, "iron_leggings");
/* 1010 */     registerItem((Item)Items.iron_boots, "iron_boots");
/* 1011 */     registerItem((Item)Items.diamond_helmet, "diamond_helmet");
/* 1012 */     registerItem((Item)Items.diamond_chestplate, "diamond_chestplate");
/* 1013 */     registerItem((Item)Items.diamond_leggings, "diamond_leggings");
/* 1014 */     registerItem((Item)Items.diamond_boots, "diamond_boots");
/* 1015 */     registerItem((Item)Items.golden_helmet, "golden_helmet");
/* 1016 */     registerItem((Item)Items.golden_chestplate, "golden_chestplate");
/* 1017 */     registerItem((Item)Items.golden_leggings, "golden_leggings");
/* 1018 */     registerItem((Item)Items.golden_boots, "golden_boots");
/* 1019 */     registerItem(Items.flint, "flint");
/* 1020 */     registerItem(Items.porkchop, "porkchop");
/* 1021 */     registerItem(Items.cooked_porkchop, "cooked_porkchop");
/* 1022 */     registerItem(Items.painting, "painting");
/* 1023 */     registerItem(Items.golden_apple, "golden_apple");
/* 1024 */     registerItem(Items.golden_apple, 1, "golden_apple");
/* 1025 */     registerItem(Items.sign, "sign");
/* 1026 */     registerItem(Items.oak_door, "oak_door");
/* 1027 */     registerItem(Items.spruce_door, "spruce_door");
/* 1028 */     registerItem(Items.birch_door, "birch_door");
/* 1029 */     registerItem(Items.jungle_door, "jungle_door");
/* 1030 */     registerItem(Items.acacia_door, "acacia_door");
/* 1031 */     registerItem(Items.dark_oak_door, "dark_oak_door");
/* 1032 */     registerItem(Items.bucket, "bucket");
/* 1033 */     registerItem(Items.water_bucket, "water_bucket");
/* 1034 */     registerItem(Items.lava_bucket, "lava_bucket");
/* 1035 */     registerItem(Items.minecart, "minecart");
/* 1036 */     registerItem(Items.saddle, "saddle");
/* 1037 */     registerItem(Items.iron_door, "iron_door");
/* 1038 */     registerItem(Items.redstone, "redstone");
/* 1039 */     registerItem(Items.snowball, "snowball");
/* 1040 */     registerItem(Items.boat, "boat");
/* 1041 */     registerItem(Items.leather, "leather");
/* 1042 */     registerItem(Items.milk_bucket, "milk_bucket");
/* 1043 */     registerItem(Items.brick, "brick");
/* 1044 */     registerItem(Items.clay_ball, "clay_ball");
/* 1045 */     registerItem(Items.reeds, "reeds");
/* 1046 */     registerItem(Items.paper, "paper");
/* 1047 */     registerItem(Items.book, "book");
/* 1048 */     registerItem(Items.slime_ball, "slime_ball");
/* 1049 */     registerItem(Items.chest_minecart, "chest_minecart");
/* 1050 */     registerItem(Items.furnace_minecart, "furnace_minecart");
/* 1051 */     registerItem(Items.egg, "egg");
/* 1052 */     registerItem(Items.compass, "compass");
/* 1053 */     registerItem((Item)Items.fishing_rod, "fishing_rod");
/* 1054 */     registerItem((Item)Items.fishing_rod, 1, "fishing_rod_cast");
/* 1055 */     registerItem(Items.clock, "clock");
/* 1056 */     registerItem(Items.glowstone_dust, "glowstone_dust");
/* 1057 */     registerItem(Items.fish, ItemFishFood.FishType.COD.getMetadata(), "cod");
/* 1058 */     registerItem(Items.fish, ItemFishFood.FishType.SALMON.getMetadata(), "salmon");
/* 1059 */     registerItem(Items.fish, ItemFishFood.FishType.CLOWNFISH.getMetadata(), "clownfish");
/* 1060 */     registerItem(Items.fish, ItemFishFood.FishType.PUFFERFISH.getMetadata(), "pufferfish");
/* 1061 */     registerItem(Items.cooked_fish, ItemFishFood.FishType.COD.getMetadata(), "cooked_cod");
/* 1062 */     registerItem(Items.cooked_fish, ItemFishFood.FishType.SALMON.getMetadata(), "cooked_salmon");
/* 1063 */     registerItem(Items.dye, EnumDyeColor.BLACK.getDyeDamage(), "dye_black");
/* 1064 */     registerItem(Items.dye, EnumDyeColor.RED.getDyeDamage(), "dye_red");
/* 1065 */     registerItem(Items.dye, EnumDyeColor.GREEN.getDyeDamage(), "dye_green");
/* 1066 */     registerItem(Items.dye, EnumDyeColor.BROWN.getDyeDamage(), "dye_brown");
/* 1067 */     registerItem(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), "dye_blue");
/* 1068 */     registerItem(Items.dye, EnumDyeColor.PURPLE.getDyeDamage(), "dye_purple");
/* 1069 */     registerItem(Items.dye, EnumDyeColor.CYAN.getDyeDamage(), "dye_cyan");
/* 1070 */     registerItem(Items.dye, EnumDyeColor.SILVER.getDyeDamage(), "dye_silver");
/* 1071 */     registerItem(Items.dye, EnumDyeColor.GRAY.getDyeDamage(), "dye_gray");
/* 1072 */     registerItem(Items.dye, EnumDyeColor.PINK.getDyeDamage(), "dye_pink");
/* 1073 */     registerItem(Items.dye, EnumDyeColor.LIME.getDyeDamage(), "dye_lime");
/* 1074 */     registerItem(Items.dye, EnumDyeColor.YELLOW.getDyeDamage(), "dye_yellow");
/* 1075 */     registerItem(Items.dye, EnumDyeColor.LIGHT_BLUE.getDyeDamage(), "dye_light_blue");
/* 1076 */     registerItem(Items.dye, EnumDyeColor.MAGENTA.getDyeDamage(), "dye_magenta");
/* 1077 */     registerItem(Items.dye, EnumDyeColor.ORANGE.getDyeDamage(), "dye_orange");
/* 1078 */     registerItem(Items.dye, EnumDyeColor.WHITE.getDyeDamage(), "dye_white");
/* 1079 */     registerItem(Items.bone, "bone");
/* 1080 */     registerItem(Items.sugar, "sugar");
/* 1081 */     registerItem(Items.cake, "cake");
/* 1082 */     registerItem(Items.bed, "bed");
/* 1083 */     registerItem(Items.repeater, "repeater");
/* 1084 */     registerItem(Items.cookie, "cookie");
/* 1085 */     registerItem((Item)Items.shears, "shears");
/* 1086 */     registerItem(Items.melon, "melon");
/* 1087 */     registerItem(Items.pumpkin_seeds, "pumpkin_seeds");
/* 1088 */     registerItem(Items.melon_seeds, "melon_seeds");
/* 1089 */     registerItem(Items.beef, "beef");
/* 1090 */     registerItem(Items.cooked_beef, "cooked_beef");
/* 1091 */     registerItem(Items.chicken, "chicken");
/* 1092 */     registerItem(Items.cooked_chicken, "cooked_chicken");
/* 1093 */     registerItem(Items.rabbit, "rabbit");
/* 1094 */     registerItem(Items.cooked_rabbit, "cooked_rabbit");
/* 1095 */     registerItem(Items.mutton, "mutton");
/* 1096 */     registerItem(Items.cooked_mutton, "cooked_mutton");
/* 1097 */     registerItem(Items.rabbit_foot, "rabbit_foot");
/* 1098 */     registerItem(Items.rabbit_hide, "rabbit_hide");
/* 1099 */     registerItem(Items.rabbit_stew, "rabbit_stew");
/* 1100 */     registerItem(Items.rotten_flesh, "rotten_flesh");
/* 1101 */     registerItem(Items.ender_pearl, "ender_pearl");
/* 1102 */     registerItem(Items.blaze_rod, "blaze_rod");
/* 1103 */     registerItem(Items.ghast_tear, "ghast_tear");
/* 1104 */     registerItem(Items.gold_nugget, "gold_nugget");
/* 1105 */     registerItem(Items.nether_wart, "nether_wart");
/* 1106 */     this.itemModelMesher.register((Item)Items.potionitem, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1110 */             return ItemPotion.isSplash(stack.getMetadata()) ? new ModelResourceLocation("bottle_splash", "inventory") : new ModelResourceLocation("bottle_drinkable", "inventory");
/*      */           }
/*      */         });
/* 1113 */     registerItem(Items.glass_bottle, "glass_bottle");
/* 1114 */     registerItem(Items.spider_eye, "spider_eye");
/* 1115 */     registerItem(Items.fermented_spider_eye, "fermented_spider_eye");
/* 1116 */     registerItem(Items.blaze_powder, "blaze_powder");
/* 1117 */     registerItem(Items.magma_cream, "magma_cream");
/* 1118 */     registerItem(Items.brewing_stand, "brewing_stand");
/* 1119 */     registerItem(Items.cauldron, "cauldron");
/* 1120 */     registerItem(Items.ender_eye, "ender_eye");
/* 1121 */     registerItem(Items.speckled_melon, "speckled_melon");
/* 1122 */     this.itemModelMesher.register(Items.spawn_egg, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1126 */             return new ModelResourceLocation("spawn_egg", "inventory");
/*      */           }
/*      */         });
/* 1129 */     registerItem(Items.experience_bottle, "experience_bottle");
/* 1130 */     registerItem(Items.fire_charge, "fire_charge");
/* 1131 */     registerItem(Items.writable_book, "writable_book");
/* 1132 */     registerItem(Items.emerald, "emerald");
/* 1133 */     registerItem(Items.item_frame, "item_frame");
/* 1134 */     registerItem(Items.flower_pot, "flower_pot");
/* 1135 */     registerItem(Items.carrot, "carrot");
/* 1136 */     registerItem(Items.potato, "potato");
/* 1137 */     registerItem(Items.baked_potato, "baked_potato");
/* 1138 */     registerItem(Items.poisonous_potato, "poisonous_potato");
/* 1139 */     registerItem((Item)Items.map, "map");
/* 1140 */     registerItem(Items.golden_carrot, "golden_carrot");
/* 1141 */     registerItem(Items.skull, 0, "skull_skeleton");
/* 1142 */     registerItem(Items.skull, 1, "skull_wither");
/* 1143 */     registerItem(Items.skull, 2, "skull_zombie");
/* 1144 */     registerItem(Items.skull, 3, "skull_char");
/* 1145 */     registerItem(Items.skull, 4, "skull_creeper");
/* 1146 */     registerItem(Items.carrot_on_a_stick, "carrot_on_a_stick");
/* 1147 */     registerItem(Items.nether_star, "nether_star");
/* 1148 */     registerItem(Items.pumpkin_pie, "pumpkin_pie");
/* 1149 */     registerItem(Items.firework_charge, "firework_charge");
/* 1150 */     registerItem(Items.comparator, "comparator");
/* 1151 */     registerItem(Items.netherbrick, "netherbrick");
/* 1152 */     registerItem(Items.quartz, "quartz");
/* 1153 */     registerItem(Items.tnt_minecart, "tnt_minecart");
/* 1154 */     registerItem(Items.hopper_minecart, "hopper_minecart");
/* 1155 */     registerItem((Item)Items.armor_stand, "armor_stand");
/* 1156 */     registerItem(Items.iron_horse_armor, "iron_horse_armor");
/* 1157 */     registerItem(Items.golden_horse_armor, "golden_horse_armor");
/* 1158 */     registerItem(Items.diamond_horse_armor, "diamond_horse_armor");
/* 1159 */     registerItem(Items.lead, "lead");
/* 1160 */     registerItem(Items.name_tag, "name_tag");
/* 1161 */     this.itemModelMesher.register(Items.banner, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1165 */             return new ModelResourceLocation("banner", "inventory");
/*      */           }
/*      */         });
/* 1168 */     registerItem(Items.record_13, "record_13");
/* 1169 */     registerItem(Items.record_cat, "record_cat");
/* 1170 */     registerItem(Items.record_blocks, "record_blocks");
/* 1171 */     registerItem(Items.record_chirp, "record_chirp");
/* 1172 */     registerItem(Items.record_far, "record_far");
/* 1173 */     registerItem(Items.record_mall, "record_mall");
/* 1174 */     registerItem(Items.record_mellohi, "record_mellohi");
/* 1175 */     registerItem(Items.record_stal, "record_stal");
/* 1176 */     registerItem(Items.record_strad, "record_strad");
/* 1177 */     registerItem(Items.record_ward, "record_ward");
/* 1178 */     registerItem(Items.record_11, "record_11");
/* 1179 */     registerItem(Items.record_wait, "record_wait");
/* 1180 */     registerItem(Items.prismarine_shard, "prismarine_shard");
/* 1181 */     registerItem(Items.prismarine_crystals, "prismarine_crystals");
/* 1182 */     this.itemModelMesher.register((Item)Items.enchanted_book, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1186 */             return new ModelResourceLocation("enchanted_book", "inventory");
/*      */           }
/*      */         });
/* 1189 */     this.itemModelMesher.register((Item)Items.filled_map, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1193 */             return new ModelResourceLocation("filled_map", "inventory");
/*      */           }
/*      */         });
/* 1196 */     registerBlock(Blocks.command_block, "command_block");
/* 1197 */     registerItem(Items.fireworks, "fireworks");
/* 1198 */     registerItem(Items.command_block_minecart, "command_block_minecart");
/* 1199 */     registerBlock(Blocks.barrier, "barrier");
/* 1200 */     registerBlock(Blocks.mob_spawner, "mob_spawner");
/* 1201 */     registerItem(Items.written_book, "written_book");
/* 1202 */     registerBlock(Blocks.brown_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "brown_mushroom_block");
/* 1203 */     registerBlock(Blocks.red_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "red_mushroom_block");
/* 1204 */     registerBlock(Blocks.dragon_egg, "dragon_egg");
/*      */     
/* 1206 */     if (Reflector.ModelLoader_onRegisterItems.exists())
/*      */     {
/* 1208 */       Reflector.call(Reflector.ModelLoader_onRegisterItems, new Object[] { this.itemModelMesher });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 1214 */     this.itemModelMesher.rebuildCache();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void forgeHooksClient_putQuadColor(WorldRenderer p_forgeHooksClient_putQuadColor_0_, BakedQuad p_forgeHooksClient_putQuadColor_1_, int p_forgeHooksClient_putQuadColor_2_) {
/* 1219 */     float f = (p_forgeHooksClient_putQuadColor_2_ & 0xFF);
/* 1220 */     float f1 = (p_forgeHooksClient_putQuadColor_2_ >>> 8 & 0xFF);
/* 1221 */     float f2 = (p_forgeHooksClient_putQuadColor_2_ >>> 16 & 0xFF);
/* 1222 */     float f3 = (p_forgeHooksClient_putQuadColor_2_ >>> 24 & 0xFF);
/* 1223 */     int[] aint = p_forgeHooksClient_putQuadColor_1_.getVertexData();
/* 1224 */     int i = aint.length / 4;
/*      */     
/* 1226 */     for (int j = 0; j < 4; j++) {
/*      */       
/* 1228 */       int k = aint[3 + i * j];
/* 1229 */       float f4 = (k & 0xFF);
/* 1230 */       float f5 = (k >>> 8 & 0xFF);
/* 1231 */       float f6 = (k >>> 16 & 0xFF);
/* 1232 */       float f7 = (k >>> 24 & 0xFF);
/* 1233 */       int l = Math.min(255, (int)(f * f4 / 255.0F));
/* 1234 */       int i1 = Math.min(255, (int)(f1 * f5 / 255.0F));
/* 1235 */       int j1 = Math.min(255, (int)(f2 * f6 / 255.0F));
/* 1236 */       int k1 = Math.min(255, (int)(f3 * f7 / 255.0F));
/* 1237 */       p_forgeHooksClient_putQuadColor_0_.putColorRGBA(p_forgeHooksClient_putQuadColor_0_.getColorIndex(4 - j), l, i1, j1, k1);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */