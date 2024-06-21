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
/*     */ public class ItemAliases
/*     */ {
/*  19 */   private static int[] itemAliases = null;
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   private static final int NO_ALIAS = -2147483648;
/*     */   
/*     */   public static int getItemAliasId(int itemId) {
/*  25 */     if (itemAliases == null)
/*     */     {
/*  27 */       return itemId;
/*     */     }
/*  29 */     if (itemId >= 0 && itemId < itemAliases.length) {
/*     */       
/*  31 */       int i = itemAliases[itemId];
/*  32 */       return (i == Integer.MIN_VALUE) ? itemId : i;
/*     */     } 
/*     */ 
/*     */     
/*  36 */     return itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  42 */     if (updateOnResourcesReloaded) {
/*     */       
/*  44 */       updateOnResourcesReloaded = false;
/*  45 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  51 */     reset();
/*     */     
/*  53 */     if (shaderPack != null)
/*     */     {
/*  55 */       if (Reflector.Loader_getActiveModList.exists() && Config.getResourceManager() == null) {
/*     */         
/*  57 */         Config.dbg("[Shaders] Delayed loading of item mappings after resources are loaded");
/*  58 */         updateOnResourcesReloaded = true;
/*     */       }
/*     */       else {
/*     */         
/*  62 */         List<Integer> list = new ArrayList<>();
/*  63 */         String s = "/shaders/item.properties";
/*  64 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  66 */         if (inputstream != null)
/*     */         {
/*  68 */           loadItemAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  71 */         loadModItemAliases(list);
/*     */         
/*  73 */         if (list.size() > 0)
/*     */         {
/*  75 */           itemAliases = toArray(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModItemAliases(List<Integer> listItemAliases) {
/*  83 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  85 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  87 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/*  91 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/item.properties");
/*  92 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  93 */         loadItemAliases(inputstream, resourcelocation.toString(), listItemAliases);
/*     */       }
/*  95 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadItemAliases(InputStream in, String path, List<Integer> listItemAliases) {
/* 104 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 108 */         in = MacroProcessor.process(in, path);
/* 109 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 110 */         propertiesOrdered.load(in);
/* 111 */         in.close();
/* 112 */         Config.dbg("[Shaders] Parsing item mappings: " + path);
/* 113 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 115 */         for (Object e : propertiesOrdered.keySet())
/*     */         {
/* 117 */           String s = (String)e;
/* 118 */           String s1 = propertiesOrdered.getProperty(s);
/* 119 */           String s2 = "item.";
/*     */           
/* 121 */           if (!s.startsWith(s2)) {
/*     */             
/* 123 */             Config.warn("[Shaders] Invalid item ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 127 */           String s3 = StrUtils.removePrefix(s, s2);
/* 128 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 130 */           if (i < 0) {
/*     */             
/* 132 */             Config.warn("[Shaders] Invalid item alias ID: " + i);
/*     */             
/*     */             continue;
/*     */           } 
/* 136 */           int[] aint = connectedparser.parseItems(s1);
/*     */           
/* 138 */           if (aint != null && aint.length >= 1) {
/*     */             
/* 140 */             for (int j = 0; j < aint.length; j++) {
/*     */               
/* 142 */               int k = aint[j];
/* 143 */               addToList(listItemAliases, k, i);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 148 */           Config.warn("[Shaders] Invalid item ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 154 */       catch (IOException var15) {
/*     */         
/* 156 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<Integer> list, int index, int val) {
/* 163 */     while (list.size() <= index)
/*     */     {
/* 165 */       list.add(Integer.valueOf(-2147483648));
/*     */     }
/*     */     
/* 168 */     list.set(index, Integer.valueOf(val));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] toArray(List<Integer> list) {
/* 173 */     int[] aint = new int[list.size()];
/*     */     
/* 175 */     for (int i = 0; i < aint.length; i++)
/*     */     {
/* 177 */       aint[i] = ((Integer)list.get(i)).intValue();
/*     */     }
/*     */     
/* 180 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 185 */     itemAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ItemAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */