/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.RandomEntities;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class TextureManager
/*     */   implements ITickable, IResourceManagerReloadListener
/*     */ {
/*  28 */   private static final Logger logger = LogManager.getLogger();
/*  29 */   private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
/*  30 */   private final List<ITickable> listTickables = Lists.newArrayList();
/*  31 */   private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
/*     */   
/*     */   private IResourceManager theResourceManager;
/*     */   
/*     */   public TextureManager(IResourceManager resourceManager) {
/*  36 */     this.theResourceManager = resourceManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation resource) {
/*  41 */     if (Config.isRandomEntities())
/*     */     {
/*  43 */       resource = RandomEntities.getTextureLocation(resource);
/*     */     }
/*     */     
/*  46 */     if (Config.isCustomGuis())
/*     */     {
/*  48 */       resource = CustomGuis.getTextureLocation(resource);
/*     */     }
/*     */     
/*  51 */     ITextureObject itextureobject = this.mapTextureObjects.get(resource);
/*     */     
/*  53 */     if (EmissiveTextures.isActive())
/*     */     {
/*  55 */       itextureobject = EmissiveTextures.getEmissiveTexture(itextureobject, this.mapTextureObjects);
/*     */     }
/*     */     
/*  58 */     if (itextureobject == null) {
/*     */       
/*  60 */       itextureobject = new SimpleTexture(resource);
/*  61 */       loadTexture(resource, itextureobject);
/*     */     } 
/*     */     
/*  64 */     if (Config.isShaders()) {
/*     */       
/*  66 */       ShadersTex.bindTexture(itextureobject);
/*     */     }
/*     */     else {
/*     */       
/*  70 */       TextureUtil.bindTexture(itextureobject.getGlTextureId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
/*  76 */     if (loadTexture(textureLocation, textureObj)) {
/*     */       
/*  78 */       this.listTickables.add(textureObj);
/*  79 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
/*  89 */     boolean flag = true;
/*     */ 
/*     */     
/*     */     try {
/*  93 */       textureObj.loadTexture(this.theResourceManager);
/*     */     }
/*  95 */     catch (IOException ioexception) {
/*     */       
/*  97 */       logger.warn("Failed to load texture: " + textureLocation, ioexception);
/*  98 */       textureObj = TextureUtil.missingTexture;
/*  99 */       this.mapTextureObjects.put(textureLocation, textureObj);
/* 100 */       flag = false;
/*     */     }
/* 102 */     catch (Throwable throwable) {
/*     */       
/* 104 */       final ITextureObject textureObjf = textureObj;
/* 105 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
/* 106 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
/* 107 */       crashreportcategory.addCrashSection("Resource location", textureLocation);
/* 108 */       crashreportcategory.addCrashSectionCallable("Texture object class", new Callable<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 112 */               return textureObjf.getClass().getName();
/*     */             }
/*     */           });
/* 115 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 118 */     this.mapTextureObjects.put(textureLocation, textureObj);
/* 119 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextureObject getTexture(ResourceLocation textureLocation) {
/* 124 */     return this.mapTextureObjects.get(textureLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
/* 129 */     if (name.equals("logo"))
/*     */     {
/* 131 */       texture = Config.getMojangLogoTexture(texture);
/*     */     }
/*     */     
/* 134 */     Integer integer = this.mapTextureCounters.get(name);
/*     */     
/* 136 */     if (integer == null) {
/*     */       
/* 138 */       integer = Integer.valueOf(1);
/*     */     }
/*     */     else {
/*     */       
/* 142 */       integer = Integer.valueOf(integer.intValue() + 1);
/*     */     } 
/*     */     
/* 145 */     this.mapTextureCounters.put(name, integer);
/* 146 */     ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", new Object[] { name, integer }));
/* 147 */     loadTexture(resourcelocation, texture);
/* 148 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 153 */     for (ITickable itickable : this.listTickables)
/*     */     {
/* 155 */       itickable.tick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteTexture(ResourceLocation textureLocation) {
/* 161 */     ITextureObject itextureobject = getTexture(textureLocation);
/*     */     
/* 163 */     if (itextureobject != null) {
/*     */       
/* 165 */       this.mapTextureObjects.remove(textureLocation);
/* 166 */       TextureUtil.deleteTexture(itextureobject.getGlTextureId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 172 */     Config.dbg("*** Reloading textures ***");
/* 173 */     Config.log("Resource packs: " + Config.getResourcePackNames());
/* 174 */     Iterator<ResourceLocation> iterator = this.mapTextureObjects.keySet().iterator();
/*     */     
/* 176 */     while (iterator.hasNext()) {
/*     */       
/* 178 */       ResourceLocation resourcelocation = iterator.next();
/* 179 */       String s = resourcelocation.getResourcePath();
/*     */       
/* 181 */       if (s.startsWith("mcpatcher/") || s.startsWith("optifine/") || EmissiveTextures.isEmissive(resourcelocation)) {
/*     */         
/* 183 */         ITextureObject itextureobject = this.mapTextureObjects.get(resourcelocation);
/*     */         
/* 185 */         if (itextureobject instanceof AbstractTexture) {
/*     */           
/* 187 */           AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
/* 188 */           abstracttexture.deleteGlTexture();
/*     */         } 
/*     */         
/* 191 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     EmissiveTextures.update();
/*     */     
/* 197 */     for (Object e : new HashSet(this.mapTextureObjects.entrySet())) {
/*     */       
/* 199 */       Map.Entry<ResourceLocation, ITextureObject> entry = (Map.Entry<ResourceLocation, ITextureObject>)e;
/* 200 */       loadTexture(entry.getKey(), entry.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadBannerTextures() {
/* 206 */     for (Object e : new HashSet(this.mapTextureObjects.entrySet())) {
/*     */       
/* 208 */       Map.Entry<ResourceLocation, ITextureObject> entry = (Map.Entry<ResourceLocation, ITextureObject>)e;
/* 209 */       ResourceLocation resourcelocation = entry.getKey();
/* 210 */       ITextureObject itextureobject = entry.getValue();
/*     */       
/* 212 */       if (itextureobject instanceof LayeredColorMaskTexture)
/*     */       {
/* 214 */         loadTexture(resourcelocation, itextureobject);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\texture\TextureManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */