/*     */ package com.viaversion.viaversion.rewriter;
/*     */ 
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.TagData;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class TagRewriter<C extends ClientboundPacketType>
/*     */ {
/*  42 */   private static final int[] EMPTY_ARRAY = new int[0];
/*     */   private final Protocol<C, ?, ?, ?> protocol;
/*  44 */   private final Map<RegistryType, List<TagData>> newTags = new EnumMap<>(RegistryType.class);
/*  45 */   private final Map<RegistryType, Map<String, String>> toRename = new EnumMap<>(RegistryType.class);
/*  46 */   private final Set<String> toRemove = new HashSet<>();
/*     */   
/*     */   public TagRewriter(Protocol<C, ?, ?, ?> protocol) {
/*  49 */     this.protocol = protocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadFromMappingData() {
/*  56 */     for (RegistryType type : RegistryType.getValues()) {
/*  57 */       List<TagData> tags = this.protocol.getMappingData().getTags(type);
/*  58 */       if (tags != null) {
/*  59 */         getOrComputeNewTags(type).addAll(tags);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeTags(String registryKey) {
/*  65 */     this.toRemove.add(registryKey);
/*     */   }
/*     */   
/*     */   public void renameTag(RegistryType type, String registryKey, String renameTo) {
/*  69 */     ((Map<String, String>)this.toRename.computeIfAbsent(type, t -> new HashMap<>())).put(registryKey, renameTo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmptyTag(RegistryType tagType, String tagId) {
/*  79 */     getOrComputeNewTags(tagType).add(new TagData(tagId, EMPTY_ARRAY));
/*     */   }
/*     */   
/*     */   public void addEmptyTags(RegistryType tagType, String... tagIds) {
/*  83 */     List<TagData> tagList = getOrComputeNewTags(tagType);
/*  84 */     for (String id : tagIds) {
/*  85 */       tagList.add(new TagData(id, EMPTY_ARRAY));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityTag(String tagId, EntityType... entities) {
/*  96 */     int[] ids = new int[entities.length];
/*  97 */     for (int i = 0; i < entities.length; i++) {
/*  98 */       ids[i] = entities[i].getId();
/*     */     }
/* 100 */     addTagRaw(RegistryType.ENTITY, tagId, ids);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTag(RegistryType tagType, String tagId, int... unmappedIds) {
/* 111 */     List<TagData> newTags = getOrComputeNewTags(tagType);
/* 112 */     IdRewriteFunction rewriteFunction = getRewriter(tagType);
/* 113 */     if (rewriteFunction != null) {
/* 114 */       for (int i = 0; i < unmappedIds.length; i++) {
/* 115 */         int unmappedId = unmappedIds[i];
/* 116 */         unmappedIds[i] = rewriteFunction.rewrite(unmappedId);
/*     */       } 
/*     */     }
/* 119 */     newTags.add(new TagData(tagId, unmappedIds));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTagRaw(RegistryType tagType, String tagId, int... ids) {
/* 130 */     getOrComputeNewTags(tagType).add(new TagData(tagId, ids));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(C packetType, RegistryType readUntilType) {
/* 140 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, getHandler(readUntilType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerGeneric(C packetType) {
/* 149 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, getGenericHandler());
/*     */   }
/*     */   
/*     */   public PacketHandler getHandler(RegistryType readUntilType) {
/* 153 */     return wrapper -> {
/*     */         for (RegistryType type : RegistryType.getValues()) {
/*     */           handle(wrapper, getRewriter(type), getNewTags(type), this.toRename.get(type));
/*     */           if (type == readUntilType) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler getGenericHandler() {
/* 166 */     return wrapper -> {
/*     */         int length = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */         int editedLength = length;
/*     */         for (int i = 0; i < length; i++) {
/*     */           String registryKey = (String)wrapper.read(Type.STRING);
/*     */           if (this.toRemove.contains(registryKey)) {
/*     */             wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(--editedLength));
/*     */             int tagsSize = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */             for (int j = 0; j < tagsSize; j++) {
/*     */               wrapper.read(Type.STRING);
/*     */               wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             } 
/*     */           } else {
/*     */             wrapper.write(Type.STRING, registryKey);
/*     */             registryKey = Key.stripMinecraftNamespace(registryKey);
/*     */             RegistryType type = RegistryType.getByKey(registryKey);
/*     */             if (type != null) {
/*     */               handle(wrapper, getRewriter(type), getNewTags(type), this.toRename.get(type));
/*     */             } else {
/*     */               handle(wrapper, null, null, null);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(PacketWrapper wrapper, IdRewriteFunction rewriteFunction, List<TagData> newTags) throws Exception {
/* 195 */     handle(wrapper, rewriteFunction, newTags, null);
/*     */   }
/*     */   
/*     */   public void handle(PacketWrapper wrapper, IdRewriteFunction rewriteFunction, List<TagData> newTags, Map<String, String> tagsToRename) throws Exception {
/* 199 */     int tagsSize = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/* 200 */     wrapper.write((Type)Type.VAR_INT, Integer.valueOf((newTags != null) ? (tagsSize + newTags.size()) : tagsSize));
/*     */     
/* 202 */     for (int i = 0; i < tagsSize; i++) {
/* 203 */       String key = (String)wrapper.read(Type.STRING);
/* 204 */       if (tagsToRename != null) {
/* 205 */         String renamedKey = tagsToRename.get(key);
/* 206 */         if (renamedKey != null) {
/* 207 */           key = renamedKey;
/*     */         }
/*     */       } 
/* 210 */       wrapper.write(Type.STRING, key);
/*     */       
/* 212 */       int[] ids = (int[])wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/* 213 */       if (rewriteFunction != null) {
/*     */         
/* 215 */         IntArrayList intArrayList = new IntArrayList(ids.length);
/* 216 */         for (int id : ids) {
/* 217 */           int mappedId = rewriteFunction.rewrite(id);
/* 218 */           if (mappedId != -1) {
/* 219 */             intArrayList.add(mappedId);
/*     */           }
/*     */         } 
/*     */         
/* 223 */         wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, intArrayList.toArray(EMPTY_ARRAY));
/*     */       } else {
/*     */         
/* 226 */         wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, ids);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 231 */     if (newTags != null) {
/* 232 */       for (TagData tag : newTags) {
/* 233 */         wrapper.write(Type.STRING, tag.identifier());
/* 234 */         wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.entries());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public List<TagData> getNewTags(RegistryType tagType) {
/* 240 */     return this.newTags.get(tagType);
/*     */   }
/*     */   
/*     */   public List<TagData> getOrComputeNewTags(RegistryType tagType) {
/* 244 */     return this.newTags.computeIfAbsent(tagType, type -> new ArrayList());
/*     */   }
/*     */   
/*     */   public IdRewriteFunction getRewriter(RegistryType tagType) {
/* 248 */     MappingData mappingData = this.protocol.getMappingData();
/* 249 */     switch (tagType) {
/*     */       case BLOCK:
/* 251 */         return (mappingData != null && mappingData.getBlockMappings() != null) ? mappingData::getNewBlockId : null;
/*     */       case ITEM:
/* 253 */         return (mappingData != null && mappingData.getItemMappings() != null) ? mappingData::getNewItemId : null;
/*     */       case ENTITY:
/* 255 */         return (this.protocol.getEntityRewriter() != null) ? (id -> this.protocol.getEntityRewriter().newEntityId(id)) : null;
/*     */     } 
/*     */ 
/*     */     
/* 259 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\TagRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */