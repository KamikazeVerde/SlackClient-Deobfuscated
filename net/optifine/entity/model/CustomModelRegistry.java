/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class CustomModelRegistry
/*     */ {
/*  12 */   private static Map<String, ModelAdapter> mapModelAdapters = makeMapModelAdapters();
/*     */ 
/*     */   
/*     */   private static Map<String, ModelAdapter> makeMapModelAdapters() {
/*  16 */     Map<String, ModelAdapter> map = new LinkedHashMap<>();
/*  17 */     addModelAdapter(map, new ModelAdapterArmorStand());
/*  18 */     addModelAdapter(map, new ModelAdapterBat());
/*  19 */     addModelAdapter(map, new ModelAdapterBlaze());
/*  20 */     addModelAdapter(map, new ModelAdapterBoat());
/*  21 */     addModelAdapter(map, new ModelAdapterCaveSpider());
/*  22 */     addModelAdapter(map, new ModelAdapterChicken());
/*  23 */     addModelAdapter(map, new ModelAdapterCow());
/*  24 */     addModelAdapter(map, new ModelAdapterCreeper());
/*  25 */     addModelAdapter(map, new ModelAdapterDragon());
/*  26 */     addModelAdapter(map, new ModelAdapterEnderCrystal());
/*  27 */     addModelAdapter(map, new ModelAdapterEnderman());
/*  28 */     addModelAdapter(map, new ModelAdapterEndermite());
/*  29 */     addModelAdapter(map, new ModelAdapterGhast());
/*  30 */     addModelAdapter(map, new ModelAdapterGuardian());
/*  31 */     addModelAdapter(map, new ModelAdapterHorse());
/*  32 */     addModelAdapter(map, new ModelAdapterIronGolem());
/*  33 */     addModelAdapter(map, new ModelAdapterLeadKnot());
/*  34 */     addModelAdapter(map, new ModelAdapterMagmaCube());
/*  35 */     addModelAdapter(map, new ModelAdapterMinecart());
/*  36 */     addModelAdapter(map, new ModelAdapterMinecartTnt());
/*  37 */     addModelAdapter(map, new ModelAdapterMinecartMobSpawner());
/*  38 */     addModelAdapter(map, new ModelAdapterMooshroom());
/*  39 */     addModelAdapter(map, new ModelAdapterOcelot());
/*  40 */     addModelAdapter(map, new ModelAdapterPig());
/*  41 */     addModelAdapter(map, new ModelAdapterPigZombie());
/*  42 */     addModelAdapter(map, new ModelAdapterRabbit());
/*  43 */     addModelAdapter(map, new ModelAdapterSheep());
/*  44 */     addModelAdapter(map, new ModelAdapterSilverfish());
/*  45 */     addModelAdapter(map, new ModelAdapterSkeleton());
/*  46 */     addModelAdapter(map, new ModelAdapterSlime());
/*  47 */     addModelAdapter(map, new ModelAdapterSnowman());
/*  48 */     addModelAdapter(map, new ModelAdapterSpider());
/*  49 */     addModelAdapter(map, new ModelAdapterSquid());
/*  50 */     addModelAdapter(map, new ModelAdapterVillager());
/*  51 */     addModelAdapter(map, new ModelAdapterWitch());
/*  52 */     addModelAdapter(map, new ModelAdapterWither());
/*  53 */     addModelAdapter(map, new ModelAdapterWitherSkull());
/*  54 */     addModelAdapter(map, new ModelAdapterWolf());
/*  55 */     addModelAdapter(map, new ModelAdapterZombie());
/*  56 */     addModelAdapter(map, new ModelAdapterSheepWool());
/*  57 */     addModelAdapter(map, new ModelAdapterBanner());
/*  58 */     addModelAdapter(map, new ModelAdapterBook());
/*  59 */     addModelAdapter(map, new ModelAdapterChest());
/*  60 */     addModelAdapter(map, new ModelAdapterChestLarge());
/*  61 */     addModelAdapter(map, new ModelAdapterEnderChest());
/*  62 */     addModelAdapter(map, new ModelAdapterHeadHumanoid());
/*  63 */     addModelAdapter(map, new ModelAdapterHeadSkeleton());
/*  64 */     addModelAdapter(map, new ModelAdapterSign());
/*  65 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
/*  70 */     addModelAdapter(map, modelAdapter, modelAdapter.getName());
/*  71 */     String[] astring = modelAdapter.getAliases();
/*     */     
/*  73 */     if (astring != null)
/*     */     {
/*  75 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  77 */         String s = astring[i];
/*  78 */         addModelAdapter(map, modelAdapter, s);
/*     */       } 
/*     */     }
/*     */     
/*  82 */     ModelBase modelbase = modelAdapter.makeModel();
/*  83 */     String[] astring1 = modelAdapter.getModelRendererNames();
/*     */     
/*  85 */     for (int j = 0; j < astring1.length; j++) {
/*     */       
/*  87 */       String s1 = astring1[j];
/*  88 */       ModelRenderer modelrenderer = modelAdapter.getModelRenderer(modelbase, s1);
/*     */       
/*  90 */       if (modelrenderer == null)
/*     */       {
/*  92 */         Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + s1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String name) {
/*  99 */     if (map.containsKey(name))
/*     */     {
/* 101 */       Config.warn("Model adapter already registered for id: " + name + ", class: " + modelAdapter.getEntityClass().getName());
/*     */     }
/*     */     
/* 104 */     map.put(name, modelAdapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelAdapter getModelAdapter(String name) {
/* 109 */     return mapModelAdapters.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getModelNames() {
/* 114 */     Set<String> set = mapModelAdapters.keySet();
/* 115 */     String[] astring = set.<String>toArray(new String[set.size()]);
/* 116 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\entity\model\CustomModelRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */