/*     */ package com.viaversion.viaversion.api.data;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.opennbt.NBTIO;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MappingDataLoader
/*     */ {
/*     */   private static final byte DIRECT_ID = 0;
/*     */   private static final byte SHIFTS_ID = 1;
/*     */   private static final byte CHANGES_ID = 2;
/*     */   private static final byte IDENTITY_ID = 3;
/*  58 */   private static final Map<String, CompoundTag> MAPPINGS_CACHE = new HashMap<>();
/*     */   
/*     */   private static boolean cacheValid = true;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void enableMappingsCache() {}
/*     */   
/*     */   public static void clearCache() {
/*  67 */     MAPPINGS_CACHE.clear();
/*  68 */     cacheValid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject loadFromDataDir(String name) {
/*  77 */     File file = new File(Via.getPlatform().getDataFolder(), name);
/*  78 */     if (!file.exists()) {
/*  79 */       return loadData(name);
/*     */     }
/*     */ 
/*     */     
/*  83 */     try (FileReader reader = new FileReader(file)) {
/*  84 */       return (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
/*  85 */     } catch (JsonSyntaxException e) {
/*     */       
/*  87 */       Via.getPlatform().getLogger().warning(name + " is badly formatted!");
/*  88 */       throw new RuntimeException(e);
/*  89 */     } catch (IOException|com.viaversion.viaversion.libs.gson.JsonIOException e) {
/*  90 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject loadData(String name) {
/* 100 */     InputStream stream = getResource(name);
/* 101 */     if (stream == null) {
/* 102 */       return null;
/*     */     }
/*     */     
/* 105 */     try (InputStreamReader reader = new InputStreamReader(stream)) {
/* 106 */       return (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
/* 107 */     } catch (IOException e) {
/* 108 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static CompoundTag loadNBT(String name, boolean cache) {
/* 113 */     if (!cacheValid) {
/* 114 */       return loadNBTFromFile(name);
/*     */     }
/*     */     
/* 117 */     CompoundTag data = MAPPINGS_CACHE.get(name);
/* 118 */     if (data != null) {
/* 119 */       return data;
/*     */     }
/*     */     
/* 122 */     data = loadNBTFromFile(name);
/*     */     
/* 124 */     if (cache && data != null) {
/* 125 */       MAPPINGS_CACHE.put(name, data);
/*     */     }
/* 127 */     return data;
/*     */   }
/*     */   
/*     */   public static CompoundTag loadNBT(String name) {
/* 131 */     return loadNBT(name, false);
/*     */   }
/*     */   
/*     */   private static CompoundTag loadNBTFromFile(String name) {
/* 135 */     InputStream resource = getResource(name);
/* 136 */     if (resource == null) {
/* 137 */       return null;
/*     */     }
/*     */     
/* 140 */     try (InputStream stream = resource) {
/* 141 */       return NBTIO.readTag(stream);
/* 142 */     } catch (IOException e) {
/* 143 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Mappings loadMappings(CompoundTag mappingsTag, String key) {
/* 148 */     return loadMappings(mappingsTag, key, size -> { int[] array = new int[size]; Arrays.fill(array, -1); return array; }(array, id, mappedId) -> array[id] = mappedId, IntArrayMappings::of);
/*     */   }
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
/*     */   @Beta
/*     */   public static <M extends Mappings, V> Mappings loadMappings(CompoundTag mappingsTag, String key, MappingHolderSupplier<V> holderSupplier, AddConsumer<V> addConsumer, MappingsSupplier<M, V> mappingsSupplier) {
/*     */     V mappings;
/* 163 */     CompoundTag tag = (CompoundTag)mappingsTag.get(key);
/* 164 */     if (tag == null) {
/* 165 */       return null;
/*     */     }
/*     */     
/* 168 */     ByteTag serializationStragetyTag = (ByteTag)tag.get("id");
/* 169 */     IntTag mappedSizeTag = (IntTag)tag.get("mappedSize");
/* 170 */     byte strategy = serializationStragetyTag.asByte();
/*     */     
/* 172 */     if (strategy == 0) {
/* 173 */       IntArrayTag valuesTag = (IntArrayTag)tag.get("val");
/* 174 */       return IntArrayMappings.of(valuesTag.getValue(), mappedSizeTag.asInt());
/* 175 */     }  if (strategy == 1)
/* 176 */     { IntArrayTag shiftsAtTag = (IntArrayTag)tag.get("at");
/* 177 */       IntArrayTag shiftsTag = (IntArrayTag)tag.get("to");
/* 178 */       IntTag sizeTag = (IntTag)tag.get("size");
/* 179 */       int[] shiftsAt = shiftsAtTag.getValue();
/* 180 */       int[] shiftsTo = shiftsTag.getValue();
/* 181 */       int size = sizeTag.asInt();
/* 182 */       mappings = holderSupplier.get(size);
/*     */ 
/*     */       
/* 185 */       if (shiftsAt[0] != 0) {
/* 186 */         int to = shiftsAt[0];
/* 187 */         for (int id = 0; id < to; id++) {
/* 188 */           addConsumer.addTo(mappings, id, id);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 193 */       for (int i = 0; i < shiftsAt.length; i++) {
/* 194 */         int from = shiftsAt[i];
/* 195 */         int to = (i == shiftsAt.length - 1) ? size : shiftsAt[i + 1];
/* 196 */         int mappedId = shiftsTo[i];
/* 197 */         for (int id = from; id < to; id++) {
/* 198 */           addConsumer.addTo(mappings, id, mappedId++);
/*     */         }
/*     */       }  }
/* 201 */     else if (strategy == 2)
/* 202 */     { IntArrayTag changesAtTag = (IntArrayTag)tag.get("at");
/* 203 */       IntArrayTag valuesTag = (IntArrayTag)tag.get("val");
/* 204 */       IntTag sizeTag = (IntTag)tag.get("size");
/* 205 */       boolean fillBetween = (tag.get("nofill") == null);
/* 206 */       int[] changesAt = changesAtTag.getValue();
/* 207 */       int[] values = valuesTag.getValue();
/* 208 */       mappings = holderSupplier.get(sizeTag.asInt());
/*     */       
/* 210 */       for (int i = 0; i < changesAt.length; i++) {
/* 211 */         int id = changesAt[i];
/* 212 */         if (fillBetween) {
/*     */           
/* 214 */           int previousId = (i != 0) ? (changesAt[i - 1] + 1) : 0;
/* 215 */           for (int identity = previousId; identity < id; identity++) {
/* 216 */             addConsumer.addTo(mappings, identity, identity);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 221 */         addConsumer.addTo(mappings, id, values[i]);
/*     */       }  }
/* 223 */     else { if (strategy == 3) {
/* 224 */         IntTag sizeTag = (IntTag)tag.get("size");
/* 225 */         return new IdentityMappings(sizeTag.asInt(), mappedSizeTag.asInt());
/*     */       } 
/* 227 */       throw new IllegalArgumentException("Unknown serialization strategy: " + strategy); }
/*     */     
/* 229 */     return (Mappings)mappingsSupplier.create(mappings, mappedSizeTag.asInt());
/*     */   }
/*     */   
/*     */   public static FullMappings loadFullMappings(CompoundTag mappingsTag, CompoundTag unmappedIdentifiers, CompoundTag mappedIdentifiers, String key) {
/* 233 */     ListTag unmappedElements = (ListTag)unmappedIdentifiers.get(key);
/* 234 */     ListTag mappedElements = (ListTag)mappedIdentifiers.get(key);
/* 235 */     if (unmappedElements == null || mappedElements == null) {
/* 236 */       return null;
/*     */     }
/*     */     
/* 239 */     Mappings mappings = loadMappings(mappingsTag, key);
/* 240 */     if (mappings == null) {
/* 241 */       mappings = new IdentityMappings(unmappedElements.size(), mappedElements.size());
/*     */     }
/*     */     
/* 244 */     return new FullMappingsBase((List<String>)unmappedElements
/* 245 */         .getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList()), (List<String>)mappedElements
/* 246 */         .getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList()), mappings);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void mapIdentifiers(int[] output, JsonObject unmappedIdentifiers, JsonObject mappedIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
/* 253 */     Object2IntMap<String> newIdentifierMap = indexedObjectToMap(mappedIdentifiers);
/* 254 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)unmappedIdentifiers.entrySet()) {
/* 255 */       int id = Integer.parseInt(entry.getKey());
/* 256 */       int mappedId = mapIdentifierEntry(id, ((JsonElement)entry.getValue()).getAsString(), newIdentifierMap, diffIdentifiers, warnOnMissing);
/* 257 */       if (mappedId != -1) {
/* 258 */         output[id] = mappedId;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int mapIdentifierEntry(int id, String val, Object2IntMap<String> mappedIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
/* 264 */     int mappedId = mappedIdentifiers.getInt(val);
/* 265 */     if (mappedId == -1) {
/*     */       
/* 267 */       if (diffIdentifiers != null) {
/* 268 */         JsonElement diffElement = diffIdentifiers.get(val);
/* 269 */         if (diffElement != null || (diffElement = diffIdentifiers.get(Integer.toString(id))) != null) {
/* 270 */           String mappedName = diffElement.getAsString();
/* 271 */           if (mappedName.isEmpty()) {
/* 272 */             return -1;
/*     */           }
/*     */           
/* 275 */           mappedId = mappedIdentifiers.getInt(mappedName);
/*     */         } 
/*     */       } 
/*     */       
/* 279 */       if (mappedId == -1) {
/* 280 */         if ((warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
/* 281 */           Via.getPlatform().getLogger().warning("No key for " + val + " :( ");
/*     */         }
/* 283 */         return -1;
/*     */       } 
/*     */     } 
/* 286 */     return mappedId;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void mapIdentifiers(int[] output, JsonArray unmappedIdentifiers, JsonArray mappedIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
/* 291 */     Object2IntMap<String> newIdentifierMap = arrayToMap(mappedIdentifiers);
/* 292 */     for (int id = 0; id < unmappedIdentifiers.size(); id++) {
/* 293 */       JsonElement unmappedIdentifier = unmappedIdentifiers.get(id);
/* 294 */       int mappedId = mapIdentifierEntry(id, unmappedIdentifier.getAsString(), newIdentifierMap, diffIdentifiers, warnOnMissing);
/* 295 */       if (mappedId != -1) {
/* 296 */         output[id] = mappedId;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object2IntMap<String> indexedObjectToMap(JsonObject object) {
/* 308 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(object.size(), 0.99F);
/* 309 */     object2IntOpenHashMap.defaultReturnValue(-1);
/* 310 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)object.entrySet()) {
/* 311 */       object2IntOpenHashMap.put(((JsonElement)entry.getValue()).getAsString(), Integer.parseInt(entry.getKey()));
/*     */     }
/* 313 */     return (Object2IntMap<String>)object2IntOpenHashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object2IntMap<String> arrayToMap(JsonArray array) {
/* 323 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(array.size(), 0.99F);
/* 324 */     object2IntOpenHashMap.defaultReturnValue(-1);
/* 325 */     for (int i = 0; i < array.size(); i++) {
/* 326 */       object2IntOpenHashMap.put(array.get(i).getAsString(), i);
/*     */     }
/* 328 */     return (Object2IntMap<String>)object2IntOpenHashMap;
/*     */   }
/*     */   
/*     */   public static InputStream getResource(String name) {
/* 332 */     return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface MappingsSupplier<T extends Mappings, V> {
/*     */     T create(V param1V, int param1Int);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface MappingHolderSupplier<T> {
/*     */     T get(int param1Int);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface AddConsumer<T> {
/*     */     void addTo(T param1T, int param1Int1, int param1Int2);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\MappingDataLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */