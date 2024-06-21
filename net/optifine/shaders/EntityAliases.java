/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.config.MacroProcessor;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public class EntityAliases
/*     */ {
/*  19 */   private static int[] entityAliases = null;
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   
/*     */   public static int getEntityAliasId(int entityId) {
/*  24 */     if (entityAliases == null)
/*     */     {
/*  26 */       return -1;
/*     */     }
/*  28 */     if (entityId >= 0 && entityId < entityAliases.length) {
/*     */       
/*  30 */       int i = entityAliases[entityId];
/*  31 */       return i;
/*     */     } 
/*     */ 
/*     */     
/*  35 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  41 */     if (updateOnResourcesReloaded) {
/*     */       
/*  43 */       updateOnResourcesReloaded = false;
/*  44 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  50 */     reset();
/*     */     
/*  52 */     if (shaderPack != null)
/*     */     {
/*  54 */       if (Reflector.Loader_getActiveModList.exists() && Config.getResourceManager() == null) {
/*     */         
/*  56 */         Config.dbg("[Shaders] Delayed loading of entity mappings after resources are loaded");
/*  57 */         updateOnResourcesReloaded = true;
/*     */       }
/*     */       else {
/*     */         
/*  61 */         List<Integer> list = new ArrayList<>();
/*  62 */         String s = "/shaders/entity.properties";
/*  63 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  65 */         if (inputstream != null)
/*     */         {
/*  67 */           loadEntityAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  70 */         loadModEntityAliases(list);
/*     */         
/*  72 */         if (list.size() > 0)
/*     */         {
/*  74 */           entityAliases = toArray(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModEntityAliases(List<Integer> listEntityAliases) {
/*  82 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  84 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  86 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/*  90 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/entity.properties");
/*  91 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  92 */         loadEntityAliases(inputstream, resourcelocation.toString(), listEntityAliases);
/*     */       }
/*  94 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadEntityAliases(InputStream in, String path, List<Integer> listEntityAliases) {
/* 103 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 107 */         in = MacroProcessor.process(in, path);
/* 108 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 109 */         propertiesOrdered.load(in);
/* 110 */         in.close();
/* 111 */         Config.dbg("[Shaders] Parsing entity mappings: " + path);
/* 112 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 114 */         for (Object e : propertiesOrdered.keySet())
/*     */         {
/* 116 */           String s = (String)e;
/* 117 */           String s1 = propertiesOrdered.getProperty(s);
/* 118 */           String s2 = "entity.";
/*     */           
/* 120 */           if (!s.startsWith(s2)) {
/*     */             
/* 122 */             Config.warn("[Shaders] Invalid entity ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 126 */           String s3 = StrUtils.removePrefix(s, s2);
/* 127 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 129 */           if (i < 0) {
/*     */             
/* 131 */             Config.warn("[Shaders] Invalid entity alias ID: " + i);
/*     */             
/*     */             continue;
/*     */           } 
/* 135 */           int[] aint = connectedparser.parseEntities(s1);
/*     */           
/* 137 */           if (aint != null && aint.length >= 1) {
/*     */             
/* 139 */             for (int j = 0; j < aint.length; j++) {
/*     */               
/* 141 */               int k = aint[j];
/* 142 */               addToList(listEntityAliases, k, i);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 147 */           Config.warn("[Shaders] Invalid entity ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 153 */       catch (IOException var15) {
/*     */         
/* 155 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<Integer> list, int index, int val) {
/* 162 */     while (list.size() <= index)
/*     */     {
/* 164 */       list.add(Integer.valueOf(-1));
/*     */     }
/*     */     
/* 167 */     list.set(index, Integer.valueOf(val));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] toArray(List<Integer> list) {
/* 172 */     int[] aint = new int[list.size()];
/*     */     
/* 174 */     for (int i = 0; i < aint.length; i++)
/*     */     {
/* 176 */       aint[i] = ((Integer)list.get(i)).intValue();
/*     */     }
/*     */     
/* 179 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 184 */     entityAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\EntityAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */