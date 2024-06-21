/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraftforge.client.model.ISmartItemModel;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemModelMesher
/*     */ {
/*  20 */   private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
/*  21 */   private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
/*  22 */   private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
/*     */   
/*     */   private final ModelManager modelManager;
/*     */   
/*     */   public ItemModelMesher(ModelManager modelManager) {
/*  27 */     this.modelManager = modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleIcon(Item item) {
/*  32 */     return getParticleIcon(item, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleIcon(Item item, int meta) {
/*  37 */     return getItemModel(new ItemStack(item, 1, meta)).getTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getItemModel(ItemStack stack) {
/*  42 */     Item item = stack.getItem();
/*  43 */     IBakedModel ibakedmodel = getItemModel(item, getMetadata(stack));
/*     */     
/*  45 */     if (ibakedmodel == null) {
/*     */       
/*  47 */       ItemMeshDefinition itemmeshdefinition = this.shapers.get(item);
/*     */       
/*  49 */       if (itemmeshdefinition != null)
/*     */       {
/*  51 */         ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
/*     */       }
/*     */     } 
/*     */     
/*  55 */     if (Reflector.ForgeHooksClient.exists() && ibakedmodel instanceof ISmartItemModel)
/*     */     {
/*  57 */       ibakedmodel = ((ISmartItemModel)ibakedmodel).handleItemState(stack);
/*     */     }
/*     */     
/*  60 */     if (ibakedmodel == null)
/*     */     {
/*  62 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/*  65 */     if (Config.isCustomItems())
/*     */     {
/*  67 */       ibakedmodel = CustomItems.getCustomItemModel(stack, ibakedmodel, null, true);
/*     */     }
/*     */     
/*  70 */     return ibakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getMetadata(ItemStack stack) {
/*  75 */     return stack.isItemStackDamageable() ? 0 : stack.getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBakedModel getItemModel(Item item, int meta) {
/*  80 */     return this.simpleShapesCache.get(Integer.valueOf(getIndex(item, meta)));
/*     */   }
/*     */ 
/*     */   
/*     */   private int getIndex(Item item, int meta) {
/*  85 */     return Item.getIdFromItem(item) << 16 | meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(Item item, int meta, ModelResourceLocation location) {
/*  90 */     this.simpleShapes.put(Integer.valueOf(getIndex(item, meta)), location);
/*  91 */     this.simpleShapesCache.put(Integer.valueOf(getIndex(item, meta)), this.modelManager.getModel(location));
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(Item item, ItemMeshDefinition definition) {
/*  96 */     this.shapers.put(item, definition);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelManager getModelManager() {
/* 101 */     return this.modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuildCache() {
/* 106 */     this.simpleShapesCache.clear();
/*     */     
/* 108 */     for (Map.Entry<Integer, ModelResourceLocation> entry : this.simpleShapes.entrySet())
/*     */     {
/* 110 */       this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\ItemModelMesher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */