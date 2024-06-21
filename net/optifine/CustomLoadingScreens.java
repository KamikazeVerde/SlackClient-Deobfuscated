/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import net.minecraft.network.PacketThreadUtil;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.StrUtils;
/*     */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ public class CustomLoadingScreens
/*     */ {
/*  17 */   private static CustomLoadingScreen[] screens = null;
/*  18 */   private static int screensMinDimensionId = 0;
/*     */ 
/*     */   
/*     */   public static CustomLoadingScreen getCustomLoadingScreen() {
/*  22 */     if (screens == null)
/*     */     {
/*  24 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  28 */     int i = PacketThreadUtil.lastDimensionId;
/*  29 */     int j = i - screensMinDimensionId;
/*  30 */     CustomLoadingScreen customloadingscreen = null;
/*     */     
/*  32 */     if (j >= 0 && j < screens.length)
/*     */     {
/*  34 */       customloadingscreen = screens[j];
/*     */     }
/*     */     
/*  37 */     return customloadingscreen;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  43 */     screens = null;
/*  44 */     screensMinDimensionId = 0;
/*  45 */     Pair<CustomLoadingScreen[], Integer> pair = parseScreens();
/*  46 */     screens = (CustomLoadingScreen[])pair.getLeft();
/*  47 */     screensMinDimensionId = ((Integer)pair.getRight()).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
/*  52 */     String s = "optifine/gui/loading/background";
/*  53 */     String s1 = ".png";
/*  54 */     String[] astring = ResUtils.collectFiles(s, s1);
/*  55 */     Map<Integer, String> map = new HashMap<>();
/*     */     
/*  57 */     for (String s2 : astring) {
/*  58 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/*  59 */       int j = Config.parseInt(s3, -2147483648);
/*     */       
/*  61 */       if (j == Integer.MIN_VALUE) {
/*  62 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*     */       } else {
/*  64 */         map.put(Integer.valueOf(j), s2);
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     Set<Integer> set = map.keySet();
/*  69 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/*  70 */     Arrays.sort((Object[])ainteger);
/*     */     
/*  72 */     if (ainteger.length <= 0)
/*     */     {
/*  74 */       return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*     */     }
/*     */ 
/*     */     
/*  78 */     String s5 = "optifine/gui/loading/loading.properties";
/*  79 */     Properties properties = ResUtils.readProperties(s5, "CustomLoadingScreens");
/*  80 */     int k = ainteger[0].intValue();
/*  81 */     int l = ainteger[ainteger.length - 1].intValue();
/*  82 */     int i1 = l - k + 1;
/*  83 */     CustomLoadingScreen[] acustomloadingscreen = new CustomLoadingScreen[i1];
/*     */     
/*  85 */     for (int j1 = 0; j1 < ainteger.length; j1++) {
/*     */       
/*  87 */       Integer integer = ainteger[j1];
/*  88 */       String s4 = map.get(integer);
/*  89 */       acustomloadingscreen[integer.intValue() - k] = CustomLoadingScreen.parseScreen(s4, integer.intValue(), properties);
/*     */     } 
/*     */     
/*  92 */     return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair(acustomloadingscreen, Integer.valueOf(k));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void warn(String str) {
/*  98 */     Config.warn("CustomLoadingScreen: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbg(String str) {
/* 103 */     Config.dbg("CustomLoadingScreen: " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomLoadingScreens.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */