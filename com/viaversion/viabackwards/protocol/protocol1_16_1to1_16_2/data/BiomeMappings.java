/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BiomeMappings
/*     */ {
/*  32 */   private static final Object2IntMap<String> MODERN_TO_LEGACY_ID = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*  33 */   private static final Object2IntMap<String> LEGACY_BIOMES = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */   
/*     */   static {
/*  36 */     LEGACY_BIOMES.defaultReturnValue(-1);
/*  37 */     MODERN_TO_LEGACY_ID.defaultReturnValue(-1);
/*     */     
/*  39 */     add(0, "ocean");
/*  40 */     add(1, "plains");
/*  41 */     add(2, "desert");
/*  42 */     add(3, "mountains");
/*  43 */     add(4, "forest");
/*  44 */     add(5, "taiga");
/*  45 */     add(6, "swamp");
/*  46 */     add(7, "river");
/*  47 */     add(8, "nether");
/*  48 */     add(9, "the_end");
/*  49 */     add(10, "frozen_ocean");
/*  50 */     add(11, "frozen_river");
/*  51 */     add(12, "snowy_tundra");
/*  52 */     add(13, "snowy_mountains");
/*  53 */     add(14, "mushroom_fields");
/*  54 */     add(15, "mushroom_field_shore");
/*  55 */     add(16, "beach");
/*  56 */     add(17, "desert_hills");
/*  57 */     add(18, "wooded_hills");
/*  58 */     add(19, "taiga_hills");
/*  59 */     add(20, "mountain_edge");
/*  60 */     add(21, "jungle");
/*  61 */     add(22, "jungle_hills");
/*  62 */     add(23, "jungle_edge");
/*  63 */     add(24, "deep_ocean");
/*  64 */     add(25, "stone_shore");
/*  65 */     add(26, "snowy_beach");
/*  66 */     add(27, "birch_forest");
/*  67 */     add(28, "birch_forest_hills");
/*  68 */     add(29, "dark_forest");
/*  69 */     add(30, "snowy_taiga");
/*  70 */     add(31, "snowy_taiga_hills");
/*  71 */     add(32, "giant_tree_taiga");
/*  72 */     add(33, "giant_tree_taiga_hills");
/*  73 */     add(34, "wooded_mountains");
/*  74 */     add(35, "savanna");
/*  75 */     add(36, "savanna_plateau");
/*  76 */     add(37, "badlands");
/*  77 */     add(38, "wooded_badlands_plateau");
/*  78 */     add(39, "badlands_plateau");
/*  79 */     add(40, "small_end_islands");
/*  80 */     add(41, "end_midlands");
/*  81 */     add(42, "end_highlands");
/*  82 */     add(43, "end_barrens");
/*  83 */     add(44, "warm_ocean");
/*  84 */     add(45, "lukewarm_ocean");
/*  85 */     add(46, "cold_ocean");
/*  86 */     add(47, "deep_warm_ocean");
/*  87 */     add(48, "deep_lukewarm_ocean");
/*  88 */     add(49, "deep_cold_ocean");
/*  89 */     add(50, "deep_frozen_ocean");
/*  90 */     add(127, "the_void");
/*  91 */     add(129, "sunflower_plains");
/*  92 */     add(130, "desert_lakes");
/*  93 */     add(131, "gravelly_mountains");
/*  94 */     add(132, "flower_forest");
/*  95 */     add(133, "taiga_mountains");
/*  96 */     add(134, "swamp_hills");
/*  97 */     add(140, "ice_spikes");
/*  98 */     add(149, "modified_jungle");
/*  99 */     add(151, "modified_jungle_edge");
/* 100 */     add(155, "tall_birch_forest");
/* 101 */     add(156, "tall_birch_hills");
/* 102 */     add(157, "dark_forest_hills");
/* 103 */     add(158, "snowy_taiga_mountains");
/* 104 */     add(160, "giant_spruce_taiga");
/* 105 */     add(161, "giant_spruce_taiga_hills");
/* 106 */     add(162, "modified_gravelly_mountains");
/* 107 */     add(163, "shattered_savanna");
/* 108 */     add(164, "shattered_savanna_plateau");
/* 109 */     add(165, "eroded_badlands");
/* 110 */     add(166, "modified_wooded_badlands_plateau");
/* 111 */     add(167, "modified_badlands_plateau");
/* 112 */     add(168, "bamboo_jungle");
/* 113 */     add(169, "bamboo_jungle_hills");
/*     */ 
/*     */     
/* 116 */     for (ObjectIterator<Object2IntMap.Entry<String>> objectIterator = LEGACY_BIOMES.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<String> entry = objectIterator.next();
/* 117 */       MODERN_TO_LEGACY_ID.put(entry.getKey(), entry.getIntValue()); }
/*     */ 
/*     */     
/* 120 */     JsonObject mappings = VBMappingDataLoader.loadFromDataDir("biome-mappings.json");
/* 121 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)mappings.entrySet()) {
/* 122 */       int legacyBiome = LEGACY_BIOMES.getInt(((JsonElement)entry.getValue()).getAsString());
/* 123 */       if (legacyBiome == -1) {
/* 124 */         ViaBackwards.getPlatform().getLogger().warning("Unknown legacy biome: " + ((JsonElement)entry.getValue()).getAsString());
/*     */         
/*     */         continue;
/*     */       } 
/* 128 */       MODERN_TO_LEGACY_ID.put(entry.getKey(), legacyBiome);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void add(int id, String biome) {
/* 133 */     LEGACY_BIOMES.put(biome, id);
/*     */   }
/*     */   
/*     */   public static int toLegacyBiome(String biome) {
/* 137 */     int legacyBiome = MODERN_TO_LEGACY_ID.getInt(Key.stripMinecraftNamespace(biome));
/* 138 */     if (legacyBiome == -1) {
/* 139 */       if (!Via.getConfig().isSuppressConversionWarnings()) {
/* 140 */         ViaBackwards.getPlatform().getLogger().warning("Biome with id " + biome + " has no legacy biome mapping (custom datapack?)");
/*     */       }
/* 142 */       return 1;
/*     */     } 
/* 144 */     return legacyBiome;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_1to1_16_2\data\BiomeMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */