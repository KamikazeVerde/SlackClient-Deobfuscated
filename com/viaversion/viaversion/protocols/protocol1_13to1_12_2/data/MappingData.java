/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.io.CharStreams;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.data.BiMappings;
/*     */ import com.viaversion.viaversion.api.data.Int2IntMapBiMappings;
/*     */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*     */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*     */ import com.viaversion.viaversion.api.data.Mappings;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import com.viaversion.viaversion.util.Int2IntBiHashMap;
/*     */ import com.viaversion.viaversion.util.Int2IntBiMap;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
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
/*     */ public class MappingData
/*     */   extends MappingDataBase
/*     */ {
/*  48 */   private final Map<String, int[]> blockTags = (Map)new HashMap<>();
/*  49 */   private final Map<String, int[]> itemTags = (Map)new HashMap<>();
/*  50 */   private final Map<String, int[]> fluidTags = (Map)new HashMap<>();
/*  51 */   private final BiMap<Short, String> oldEnchantmentsIds = (BiMap<Short, String>)HashBiMap.create();
/*  52 */   private final Map<String, String> translateMapping = new HashMap<>();
/*  53 */   private final Map<String, String> mojangTranslation = new HashMap<>();
/*  54 */   private final BiMap<String, String> channelMappings = (BiMap<String, String>)HashBiMap.create();
/*     */   
/*     */   public MappingData() {
/*  57 */     super("1.12", "1.13");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadExtras(CompoundTag data) {
/*  62 */     loadTags(this.blockTags, (CompoundTag)data.get("block_tags"));
/*  63 */     loadTags(this.itemTags, (CompoundTag)data.get("item_tags"));
/*  64 */     loadTags(this.fluidTags, (CompoundTag)data.get("fluid_tags"));
/*     */     
/*  66 */     CompoundTag legacyEnchantments = (CompoundTag)data.get("legacy_enchantments");
/*  67 */     loadEnchantments((Map<Short, String>)this.oldEnchantmentsIds, legacyEnchantments);
/*     */ 
/*     */     
/*  70 */     if (Via.getConfig().isSnowCollisionFix()) {
/*  71 */       this.blockMappings.setNewId(1248, 3416);
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (Via.getConfig().isInfestedBlocksFix()) {
/*  76 */       this.blockMappings.setNewId(1552, 1);
/*  77 */       this.blockMappings.setNewId(1553, 14);
/*  78 */       this.blockMappings.setNewId(1554, 3983);
/*  79 */       this.blockMappings.setNewId(1555, 3984);
/*  80 */       this.blockMappings.setNewId(1556, 3985);
/*  81 */       this.blockMappings.setNewId(1557, 3986);
/*     */     } 
/*     */     
/*  84 */     JsonObject object = MappingDataLoader.loadFromDataDir("channelmappings-1.13.json");
/*  85 */     if (object != null) {
/*  86 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)object.entrySet()) {
/*  87 */         String oldChannel = entry.getKey();
/*  88 */         String newChannel = ((JsonElement)entry.getValue()).getAsString();
/*  89 */         if (!isValid1_13Channel(newChannel)) {
/*  90 */           Via.getPlatform().getLogger().warning("Channel '" + newChannel + "' is not a valid 1.13 plugin channel, please check your configuration!");
/*     */           continue;
/*     */         } 
/*  93 */         this.channelMappings.put(oldChannel, newChannel);
/*     */       } 
/*     */     }
/*     */     
/*  97 */     Map<String, String> translateData = (Map<String, String>)GsonUtil.getGson().fromJson(new InputStreamReader(MappingData.class
/*  98 */           .getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json")), (new TypeToken<Map<String, String>>() {
/*     */         
/* 100 */         }).getType());
/*     */     try {
/*     */       String[] lines;
/* 103 */       try (Reader reader = new InputStreamReader(MappingData.class.getClassLoader()
/* 104 */             .getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8)) {
/* 105 */         lines = CharStreams.toString(reader).split("\n");
/*     */       } 
/* 107 */       for (String line : lines) {
/* 108 */         if (!line.isEmpty()) {
/*     */ 
/*     */ 
/*     */           
/* 112 */           String[] keyAndTranslation = line.split("=", 2);
/* 113 */           if (keyAndTranslation.length == 2)
/*     */           
/*     */           { 
/*     */             
/* 117 */             String key = keyAndTranslation[0];
/* 118 */             String translation = keyAndTranslation[1].replaceAll("%(\\d\\$)?d", "%$1s").trim();
/* 119 */             this.mojangTranslation.put(key, translation);
/*     */             
/* 121 */             String dataValue = translateData.get(key);
/* 122 */             if (dataValue != null)
/* 123 */               this.translateMapping.put(key, dataValue);  } 
/*     */         } 
/*     */       } 
/* 126 */     } catch (IOException e) {
/* 127 */       String[] lines; lines.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Mappings loadMappings(CompoundTag data, String key) {
/* 134 */     if (key.equals("blocks"))
/* 135 */       return super.loadMappings(data, "blockstates"); 
/* 136 */     if (key.equals("blockstates")) {
/* 137 */       return null;
/*     */     }
/* 139 */     return super.loadMappings(data, key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiMappings loadBiMappings(CompoundTag data, String key) {
/* 146 */     if (key.equals("items")) {
/* 147 */       return (BiMappings)MappingDataLoader.loadMappings(data, "items", size -> { Int2IntBiHashMap map = new Int2IntBiHashMap(size); map.defaultReturnValue(-1); return map; }Int2IntBiHashMap::put, (v, mappedSize) -> Int2IntMapBiMappings.of((Int2IntBiMap)v));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     return super.loadBiMappings(data, key);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String validateNewChannel(String newId) {
/* 158 */     if (!isValid1_13Channel(newId)) {
/* 159 */       return null;
/*     */     }
/* 161 */     int separatorIndex = newId.indexOf(':');
/*     */     
/* 163 */     if (separatorIndex == -1)
/* 164 */       return "minecraft:" + newId; 
/* 165 */     if (separatorIndex == 0) {
/* 166 */       return "minecraft" + newId;
/*     */     }
/* 168 */     return newId;
/*     */   }
/*     */   
/*     */   public static boolean isValid1_13Channel(String channelId) {
/* 172 */     return channelId.matches("([0-9a-z_.-]+:)?[0-9a-z_/.-]+");
/*     */   }
/*     */   
/*     */   private void loadTags(Map<String, int[]> output, CompoundTag newTags) {
/* 176 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)newTags.entrySet()) {
/* 177 */       IntArrayTag ids = (IntArrayTag)entry.getValue();
/* 178 */       output.put(Key.namespaced(entry.getKey()), ids.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadEnchantments(Map<Short, String> output, CompoundTag enchantments) {
/* 183 */     for (Map.Entry<String, Tag> enty : (Iterable<Map.Entry<String, Tag>>)enchantments.entrySet()) {
/* 184 */       output.put(Short.valueOf(Short.parseShort(enty.getKey())), ((StringTag)enty.getValue()).getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<String, int[]> getBlockTags() {
/* 189 */     return this.blockTags;
/*     */   }
/*     */   
/*     */   public Map<String, int[]> getItemTags() {
/* 193 */     return this.itemTags;
/*     */   }
/*     */   
/*     */   public Map<String, int[]> getFluidTags() {
/* 197 */     return this.fluidTags;
/*     */   }
/*     */   
/*     */   public BiMap<Short, String> getOldEnchantmentsIds() {
/* 201 */     return this.oldEnchantmentsIds;
/*     */   }
/*     */   
/*     */   public Map<String, String> getTranslateMapping() {
/* 205 */     return this.translateMapping;
/*     */   }
/*     */   
/*     */   public Map<String, String> getMojangTranslation() {
/* 209 */     return this.mojangTranslation;
/*     */   }
/*     */   
/*     */   public BiMap<String, String> getChannelMappings() {
/* 213 */     return this.channelMappings;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */