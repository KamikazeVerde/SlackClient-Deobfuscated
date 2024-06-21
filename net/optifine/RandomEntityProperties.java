/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ 
/*     */ public class RandomEntityProperties
/*     */ {
/*  12 */   public String name = null;
/*  13 */   public String basePath = null;
/*  14 */   public ResourceLocation[] resourceLocations = null;
/*  15 */   public RandomEntityRule[] rules = null;
/*     */ 
/*     */   
/*     */   public RandomEntityProperties(String path, ResourceLocation[] variants) {
/*  19 */     ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
/*  20 */     this.name = connectedparser.parseName(path);
/*  21 */     this.basePath = connectedparser.parseBasePath(path);
/*  22 */     this.resourceLocations = variants;
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomEntityProperties(Properties props, String path, ResourceLocation baseResLoc) {
/*  27 */     ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
/*  28 */     this.name = connectedparser.parseName(path);
/*  29 */     this.basePath = connectedparser.parseBasePath(path);
/*  30 */     this.rules = parseRules(props, path, baseResLoc, connectedparser);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc, IRandomEntity randomEntity) {
/*  35 */     if (this.rules != null)
/*     */     {
/*  37 */       for (int i = 0; i < this.rules.length; i++) {
/*     */         
/*  39 */         RandomEntityRule randomentityrule = this.rules[i];
/*     */         
/*  41 */         if (randomentityrule.matches(randomEntity))
/*     */         {
/*  43 */           return randomentityrule.getTextureLocation(loc, randomEntity.getId());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  48 */     if (this.resourceLocations != null) {
/*     */       
/*  50 */       int j = randomEntity.getId();
/*  51 */       int k = j % this.resourceLocations.length;
/*  52 */       return this.resourceLocations[k];
/*     */     } 
/*     */ 
/*     */     
/*  56 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RandomEntityRule[] parseRules(Properties props, String pathProps, ResourceLocation baseResLoc, ConnectedParser cp) {
/*  62 */     List<RandomEntityRule> list = new ArrayList();
/*  63 */     int i = props.size();
/*     */     
/*  65 */     for (int j = 0; j < i; j++) {
/*     */       
/*  67 */       int k = j + 1;
/*  68 */       String s = props.getProperty("textures." + k);
/*     */       
/*  70 */       if (s == null)
/*     */       {
/*  72 */         s = props.getProperty("skins." + k);
/*     */       }
/*     */       
/*  75 */       if (s != null) {
/*     */         
/*  77 */         RandomEntityRule randomentityrule = new RandomEntityRule(props, pathProps, baseResLoc, k, s, cp);
/*     */         
/*  79 */         if (randomentityrule.isValid(pathProps))
/*     */         {
/*  81 */           list.add(randomentityrule);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     RandomEntityRule[] arandomentityrule = list.<RandomEntityRule>toArray(new RandomEntityRule[list.size()]);
/*  87 */     return arandomentityrule;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/*  92 */     if (this.resourceLocations == null && this.rules == null) {
/*     */       
/*  94 */       Config.warn("No skins specified: " + path);
/*  95 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     if (this.rules != null)
/*     */     {
/* 101 */       for (int i = 0; i < this.rules.length; i++) {
/*     */         
/* 103 */         RandomEntityRule randomentityrule = this.rules[i];
/*     */         
/* 105 */         if (!randomentityrule.isValid(path))
/*     */         {
/* 107 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 112 */     if (this.resourceLocations != null)
/*     */     {
/* 114 */       for (int j = 0; j < this.resourceLocations.length; j++) {
/*     */         
/* 116 */         ResourceLocation resourcelocation = this.resourceLocations[j];
/*     */         
/* 118 */         if (!Config.hasResource(resourcelocation)) {
/*     */           
/* 120 */           Config.warn("Texture not found: " + resourcelocation.getResourcePath());
/* 121 */           return false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 132 */     return (this.rules != null) ? false : ((this.resourceLocations == null));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\RandomEntityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */