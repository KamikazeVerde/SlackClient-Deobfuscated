/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class Lang
/*     */ {
/*  20 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  21 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  25 */     Map map = I18n.getLocaleProperties();
/*  26 */     List<String> list = new ArrayList<>();
/*  27 */     String s = "optifine/lang/";
/*  28 */     String s1 = "en_US";
/*  29 */     String s2 = ".lang";
/*  30 */     list.add(s + s1 + s2);
/*     */     
/*  32 */     if (!(Config.getGameSettings()).language.equals(s1))
/*     */     {
/*  34 */       list.add(s + (Config.getGameSettings()).language + s2);
/*     */     }
/*     */     
/*  37 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  38 */     loadResources((IResourcePack)Config.getDefaultResourcePack(), astring, map);
/*  39 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  41 */     for (int i = 0; i < airesourcepack.length; i++) {
/*     */       
/*  43 */       IResourcePack iresourcepack = airesourcepack[i];
/*  44 */       loadResources(iresourcepack, astring, map);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadResources(IResourcePack rp, String[] files, Map localeProperties) {
/*     */     try {
/*  52 */       for (int i = 0; i < files.length; i++) {
/*     */         
/*  54 */         String s = files[i];
/*  55 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  57 */         if (rp.resourceExists(resourcelocation))
/*     */         {
/*  59 */           InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */           
/*  61 */           if (inputstream != null)
/*     */           {
/*  63 */             loadLocaleData(inputstream, localeProperties);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*  68 */     } catch (IOException ioexception) {
/*     */       
/*  70 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadLocaleData(InputStream is, Map<String, String> localeProperties) throws IOException {
/*  76 */     for (String s : IOUtils.readLines(is, Charsets.UTF_8)) {
/*     */       
/*  78 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */         
/*  80 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/*  82 */         if (astring != null && astring.length == 2) {
/*     */           
/*  84 */           String s1 = astring[0];
/*  85 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/*  86 */           localeProperties.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(String key) {
/*  94 */     return I18n.format(key, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(String key, String def) {
/*  99 */     String s = I18n.format(key, new Object[0]);
/* 100 */     return (s != null && !s.equals(key)) ? s : def;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOn() {
/* 105 */     return I18n.format("options.on", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOff() {
/* 110 */     return I18n.format("options.off", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFast() {
/* 115 */     return I18n.format("options.graphics.fast", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFancy() {
/* 120 */     return I18n.format("options.graphics.fancy", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDefault() {
/* 125 */     return I18n.format("generator.default", new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\Lang.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */