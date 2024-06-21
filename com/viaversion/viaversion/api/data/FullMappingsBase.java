/*     */ package com.viaversion.viaversion.api.data;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.List;
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
/*     */ public class FullMappingsBase
/*     */   implements FullMappings
/*     */ {
/*  32 */   private static final String[] EMPTY_ARRAY = new String[0];
/*     */   private final Object2IntMap<String> stringToId;
/*     */   private final Object2IntMap<String> mappedStringToId;
/*     */   private final String[] idToString;
/*     */   private final String[] mappedIdToString;
/*     */   private final Mappings mappings;
/*     */   
/*     */   public FullMappingsBase(List<String> unmappedIdentifiers, List<String> mappedIdentifiers, Mappings mappings) {
/*  40 */     this.mappings = mappings;
/*  41 */     this.stringToId = toInverseMap(unmappedIdentifiers);
/*  42 */     this.mappedStringToId = toInverseMap(mappedIdentifiers);
/*  43 */     this.idToString = unmappedIdentifiers.<String>toArray(EMPTY_ARRAY);
/*  44 */     this.mappedIdToString = mappedIdentifiers.<String>toArray(EMPTY_ARRAY);
/*     */   }
/*     */   
/*     */   private FullMappingsBase(Object2IntMap<String> stringToId, Object2IntMap<String> mappedStringToId, String[] idToString, String[] mappedIdToString, Mappings mappings) {
/*  48 */     this.stringToId = stringToId;
/*  49 */     this.mappedStringToId = mappedStringToId;
/*  50 */     this.idToString = idToString;
/*  51 */     this.mappedIdToString = mappedIdToString;
/*  52 */     this.mappings = mappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings mappings() {
/*  57 */     return this.mappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public int id(String identifier) {
/*  62 */     return this.stringToId.getInt(Key.stripMinecraftNamespace(identifier));
/*     */   }
/*     */ 
/*     */   
/*     */   public int mappedId(String mappedIdentifier) {
/*  67 */     return this.mappedStringToId.getInt(Key.stripMinecraftNamespace(mappedIdentifier));
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(int id) {
/*  72 */     String identifier = this.idToString[id];
/*  73 */     return Key.namespaced(identifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public String mappedIdentifier(int mappedId) {
/*  78 */     String identifier = this.mappedIdToString[mappedId];
/*  79 */     return Key.namespaced(identifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public String mappedIdentifier(String identifier) {
/*  84 */     int id = id(identifier);
/*  85 */     if (id == -1) {
/*  86 */       return null;
/*     */     }
/*     */     
/*  89 */     int mappedId = this.mappings.getNewId(id);
/*  90 */     return (mappedId != -1) ? mappedIdentifier(mappedId) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNewId(int id) {
/*  95 */     return this.mappings.getNewId(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNewId(int id, int mappedId) {
/* 100 */     this.mappings.setNewId(id, mappedId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 105 */     return this.mappings.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int mappedSize() {
/* 110 */     return this.mappings.mappedSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullMappings inverse() {
/* 115 */     return new FullMappingsBase(this.mappedStringToId, this.stringToId, this.mappedIdToString, this.idToString, this.mappings.inverse());
/*     */   }
/*     */   
/*     */   private static Object2IntMap<String> toInverseMap(List<String> list) {
/* 119 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(list.size());
/* 120 */     object2IntOpenHashMap.defaultReturnValue(-1);
/* 121 */     for (int i = 0; i < list.size(); i++) {
/* 122 */       object2IntOpenHashMap.put(list.get(i), i);
/*     */     }
/* 124 */     return (Object2IntMap<String>)object2IntOpenHashMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\FullMappingsBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */