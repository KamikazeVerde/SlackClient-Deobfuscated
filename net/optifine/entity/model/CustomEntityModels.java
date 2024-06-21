/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.IModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ 
/*     */ public class CustomEntityModels
/*     */ {
/*     */   private static boolean active = false;
/*  31 */   private static Map<Class, Render> originalEntityRenderMap = null;
/*  32 */   private static Map<Class, TileEntitySpecialRenderer> originalTileEntityRenderMap = null;
/*     */ 
/*     */   
/*     */   public static void update() {
/*  36 */     Map<Class<?>, Render> map = getEntityRenderMap();
/*  37 */     Map<Class<?>, TileEntitySpecialRenderer> map1 = getTileEntityRenderMap();
/*     */     
/*  39 */     if (map == null) {
/*     */       
/*  41 */       Config.warn("Entity render map not found, custom entity models are DISABLED.");
/*     */     }
/*  43 */     else if (map1 == null) {
/*     */       
/*  45 */       Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
/*     */     }
/*     */     else {
/*     */       
/*  49 */       active = false;
/*  50 */       map.clear();
/*  51 */       map1.clear();
/*  52 */       map.putAll((Map)originalEntityRenderMap);
/*  53 */       map1.putAll((Map)originalTileEntityRenderMap);
/*     */       
/*  55 */       if (Config.isCustomEntityModels()) {
/*     */         
/*  57 */         ResourceLocation[] aresourcelocation = getModelLocations();
/*     */         
/*  59 */         for (int i = 0; i < aresourcelocation.length; i++) {
/*     */           
/*  61 */           ResourceLocation resourcelocation = aresourcelocation[i];
/*  62 */           Config.dbg("CustomEntityModel: " + resourcelocation.getResourcePath());
/*  63 */           IEntityRenderer ientityrenderer = parseEntityRender(resourcelocation);
/*     */           
/*  65 */           if (ientityrenderer != null) {
/*     */             
/*  67 */             Class<?> oclass = ientityrenderer.getEntityClass();
/*     */             
/*  69 */             if (oclass != null) {
/*     */               
/*  71 */               if (ientityrenderer instanceof Render) {
/*     */                 
/*  73 */                 map.put(oclass, (Render)ientityrenderer);
/*     */               }
/*  75 */               else if (ientityrenderer instanceof TileEntitySpecialRenderer) {
/*     */                 
/*  77 */                 map1.put(oclass, (TileEntitySpecialRenderer)ientityrenderer);
/*     */               }
/*     */               else {
/*     */                 
/*  81 */                 Config.warn("Unknown renderer type: " + ientityrenderer.getClass().getName());
/*     */               } 
/*     */               
/*  84 */               active = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Class, Render> getEntityRenderMap() {
/*  94 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/*  95 */     Map<Class<?>, Render> map = rendermanager.getEntityRenderMap();
/*     */     
/*  97 */     if (map == null)
/*     */     {
/*  99 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 103 */     if (originalEntityRenderMap == null)
/*     */     {
/* 105 */       originalEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/* 108 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<Class, TileEntitySpecialRenderer> getTileEntityRenderMap() {
/* 114 */     Map<Class<?>, TileEntitySpecialRenderer> map = TileEntityRendererDispatcher.instance.mapSpecialRenderers;
/*     */     
/* 116 */     if (originalTileEntityRenderMap == null)
/*     */     {
/* 118 */       originalTileEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/* 121 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation[] getModelLocations() {
/* 126 */     String s = "optifine/cem/";
/* 127 */     String s1 = ".jem";
/* 128 */     List<ResourceLocation> list = new ArrayList<>();
/* 129 */     String[] astring = CustomModelRegistry.getModelNames();
/*     */     
/* 131 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 133 */       String s2 = astring[i];
/* 134 */       String s3 = s + s2 + s1;
/* 135 */       ResourceLocation resourcelocation = new ResourceLocation(s3);
/*     */       
/* 137 */       if (Config.hasResource(resourcelocation))
/*     */       {
/* 139 */         list.add(resourcelocation);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[list.size()]);
/* 144 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(ResourceLocation location) {
/*     */     try {
/* 151 */       JsonObject jsonobject = CustomEntityModelParser.loadJson(location);
/* 152 */       IEntityRenderer ientityrenderer = parseEntityRender(jsonobject, location.getResourcePath());
/* 153 */       return ientityrenderer;
/*     */     }
/* 155 */     catch (IOException ioexception) {
/*     */       
/* 157 */       Config.error("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 158 */       return null;
/*     */     }
/* 160 */     catch (JsonParseException jsonparseexception) {
/*     */       
/* 162 */       Config.error("" + jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/* 163 */       return null;
/*     */     }
/* 165 */     catch (Exception exception) {
/*     */       
/* 167 */       exception.printStackTrace();
/* 168 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(JsonObject obj, String path) {
/* 174 */     CustomEntityRenderer customentityrenderer = CustomEntityModelParser.parseEntityRender(obj, path);
/* 175 */     String s = customentityrenderer.getName();
/* 176 */     ModelAdapter modeladapter = CustomModelRegistry.getModelAdapter(s);
/* 177 */     checkNull(modeladapter, "Entity not found: " + s);
/* 178 */     Class oclass = modeladapter.getEntityClass();
/* 179 */     checkNull(oclass, "Entity class not found: " + s);
/* 180 */     IEntityRenderer ientityrenderer = makeEntityRender(modeladapter, customentityrenderer);
/*     */     
/* 182 */     if (ientityrenderer == null)
/*     */     {
/* 184 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 188 */     ientityrenderer.setEntityClass(oclass);
/* 189 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer) {
/* 195 */     ResourceLocation resourcelocation = cer.getTextureLocation();
/* 196 */     CustomModelRenderer[] acustommodelrenderer = cer.getCustomModelRenderers();
/* 197 */     float f = cer.getShadowSize();
/*     */     
/* 199 */     if (f < 0.0F)
/*     */     {
/* 201 */       f = modelAdapter.getShadowSize();
/*     */     }
/*     */     
/* 204 */     ModelBase modelbase = modelAdapter.makeModel();
/*     */     
/* 206 */     if (modelbase == null)
/*     */     {
/* 208 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 212 */     ModelResolver modelresolver = new ModelResolver(modelAdapter, modelbase, acustommodelrenderer);
/*     */     
/* 214 */     if (!modifyModel(modelAdapter, modelbase, acustommodelrenderer, modelresolver))
/*     */     {
/* 216 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 220 */     IEntityRenderer ientityrenderer = modelAdapter.makeEntityRender(modelbase, f);
/*     */     
/* 222 */     if (ientityrenderer == null)
/*     */     {
/* 224 */       throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
/*     */     }
/*     */ 
/*     */     
/* 228 */     if (resourcelocation != null)
/*     */     {
/* 230 */       ientityrenderer.setLocationTextureCustom(resourcelocation);
/*     */     }
/*     */     
/* 233 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
/* 241 */     for (int i = 0; i < modelRenderers.length; i++) {
/*     */       
/* 243 */       CustomModelRenderer custommodelrenderer = modelRenderers[i];
/*     */       
/* 245 */       if (!modifyModel(modelAdapter, model, custommodelrenderer, mr))
/*     */       {
/* 247 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
/* 256 */     String s = customModelRenderer.getModelPart();
/* 257 */     ModelRenderer modelrenderer = modelAdapter.getModelRenderer(model, s);
/*     */     
/* 259 */     if (modelrenderer == null) {
/*     */       
/* 261 */       Config.warn("Model part not found: " + s + ", model: " + model);
/* 262 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 266 */     if (!customModelRenderer.isAttach()) {
/*     */       
/* 268 */       if (modelrenderer.cubeList != null)
/*     */       {
/* 270 */         modelrenderer.cubeList.clear();
/*     */       }
/*     */       
/* 273 */       if (modelrenderer.spriteList != null)
/*     */       {
/* 275 */         modelrenderer.spriteList.clear();
/*     */       }
/*     */       
/* 278 */       if (modelrenderer.childModels != null) {
/*     */         
/* 280 */         ModelRenderer[] amodelrenderer = modelAdapter.getModelRenderers(model);
/* 281 */         Set<ModelRenderer> set = Collections.newSetFromMap(new IdentityHashMap<>());
/* 282 */         set.addAll(Arrays.asList(amodelrenderer));
/* 283 */         List<ModelRenderer> list = modelrenderer.childModels;
/* 284 */         Iterator<ModelRenderer> iterator = list.iterator();
/*     */         
/* 286 */         while (iterator.hasNext()) {
/*     */           
/* 288 */           ModelRenderer modelrenderer1 = iterator.next();
/*     */           
/* 290 */           if (!set.contains(modelrenderer1))
/*     */           {
/* 292 */             iterator.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     modelrenderer.addChild(customModelRenderer.getModelRenderer());
/* 299 */     ModelUpdater modelupdater = customModelRenderer.getModelUpdater();
/*     */     
/* 301 */     if (modelupdater != null) {
/*     */       
/* 303 */       modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
/* 304 */       modelResolver.setPartModelRenderer(modelrenderer);
/*     */       
/* 306 */       if (!modelupdater.initialize((IModelResolver)modelResolver))
/*     */       {
/* 308 */         return false;
/*     */       }
/*     */       
/* 311 */       customModelRenderer.getModelRenderer().setModelUpdater(modelupdater);
/*     */     } 
/*     */     
/* 314 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 320 */     if (obj == null)
/*     */     {
/* 322 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 328 */     return active;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\entity\model\CustomEntityModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */