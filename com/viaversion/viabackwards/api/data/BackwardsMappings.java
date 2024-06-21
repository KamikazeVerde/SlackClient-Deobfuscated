/*     */ package com.viaversion.viabackwards.api.data;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.data.BiMappings;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*     */ import com.viaversion.viaversion.api.data.Mappings;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
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
/*     */ public class BackwardsMappings
/*     */   extends MappingDataBase
/*     */ {
/*     */   private final Class<? extends Protocol<?, ?, ?, ?>> vvProtocolClass;
/*     */   protected Int2ObjectMap<MappedItem> backwardsItemMappings;
/*     */   private Map<String, String> backwardsSoundMappings;
/*     */   private Map<String, String> entityNames;
/*     */   
/*     */   public BackwardsMappings(String unmappedVersion, String mappedVersion) {
/*  49 */     this(unmappedVersion, mappedVersion, (Class<? extends Protocol<?, ?, ?, ?>>)null);
/*     */   }
/*     */   
/*     */   public BackwardsMappings(String unmappedVersion, String mappedVersion, Class<? extends Protocol<?, ?, ?, ?>> vvProtocolClass) {
/*  53 */     super(unmappedVersion, mappedVersion);
/*  54 */     Preconditions.checkArgument((vvProtocolClass == null || !vvProtocolClass.isAssignableFrom(BackwardsProtocol.class)));
/*  55 */     this.vvProtocolClass = vvProtocolClass;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadExtras(CompoundTag data) {
/*  60 */     CompoundTag itemNames = (CompoundTag)data.get("itemnames");
/*  61 */     if (itemNames != null) {
/*  62 */       Preconditions.checkNotNull(this.itemMappings);
/*  63 */       this.backwardsItemMappings = (Int2ObjectMap<MappedItem>)new Int2ObjectOpenHashMap(itemNames.size());
/*     */       
/*  65 */       CompoundTag extraItemData = (CompoundTag)data.get("itemdata");
/*  66 */       for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)itemNames.entrySet()) {
/*  67 */         StringTag name = (StringTag)entry.getValue();
/*  68 */         int id = Integer.parseInt(entry.getKey());
/*  69 */         Integer customModelData = null;
/*  70 */         if (extraItemData != null && extraItemData.contains(entry.getKey())) {
/*  71 */           CompoundTag entryTag = (CompoundTag)extraItemData.get(entry.getKey());
/*  72 */           NumberTag customModelDataTag = (NumberTag)entryTag.get("custom_model_data");
/*  73 */           customModelData = (customModelDataTag != null) ? Integer.valueOf(customModelDataTag.asInt()) : null;
/*     */         } 
/*     */         
/*  76 */         this.backwardsItemMappings.put(id, new MappedItem(getNewItemId(id), name.getValue(), customModelData));
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     CompoundTag entityNames = (CompoundTag)data.get("entitynames");
/*  81 */     if (entityNames != null) {
/*  82 */       this.entityNames = new HashMap<>(entityNames.size());
/*  83 */       for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)entityNames.entrySet()) {
/*  84 */         StringTag mappedTag = (StringTag)entry.getValue();
/*  85 */         this.entityNames.put(entry.getKey(), mappedTag.getValue());
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     CompoundTag soundNames = (CompoundTag)data.get("soundnames");
/*  90 */     if (soundNames != null) {
/*  91 */       this.backwardsSoundMappings = new HashMap<>(soundNames.size());
/*  92 */       for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)soundNames.entrySet()) {
/*  93 */         StringTag mappedTag = (StringTag)entry.getValue();
/*  94 */         this.backwardsSoundMappings.put(entry.getKey(), mappedTag.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiMappings loadBiMappings(CompoundTag data, String key) {
/* 101 */     if (key.equals("items") && this.vvProtocolClass != null) {
/* 102 */       Mappings mappings = loadMappings(data, key);
/* 103 */       MappingData mappingData = Via.getManager().getProtocolManager().getProtocol(this.vvProtocolClass).getMappingData();
/* 104 */       if (mappingData != null && mappingData.getItemMappings() != null) {
/* 105 */         return (BiMappings)ItemMappings.of(mappings, (Mappings)mappingData.getItemMappings());
/*     */       }
/*     */     } 
/* 108 */     return super.loadBiMappings(data, key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNewItemId(int id) {
/* 117 */     return this.itemMappings.getNewId(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNewBlockId(int id) {
/* 123 */     return this.blockMappings.getNewId(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOldItemId(int id) {
/* 129 */     return checkValidity(id, this.itemMappings.inverse().getNewId(id), "item");
/*     */   }
/*     */   
/*     */   public MappedItem getMappedItem(int id) {
/* 133 */     return (this.backwardsItemMappings != null) ? (MappedItem)this.backwardsItemMappings.get(id) : null;
/*     */   }
/*     */   
/*     */   public String getMappedNamedSound(String id) {
/* 137 */     if (this.backwardsSoundMappings == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     return this.backwardsSoundMappings.get(Key.stripMinecraftNamespace(id));
/*     */   }
/*     */   
/*     */   public String mappedEntityName(String entityName) {
/* 144 */     if (this.entityNames == null) {
/* 145 */       ViaBackwards.getPlatform().getLogger().severe("No entity mappings found when requesting them for " + entityName);
/* 146 */       (new Exception()).printStackTrace();
/* 147 */       return null;
/*     */     } 
/* 149 */     return this.entityNames.get(entityName);
/*     */   }
/*     */   
/*     */   public Int2ObjectMap<MappedItem> getBackwardsItemMappings() {
/* 153 */     return this.backwardsItemMappings;
/*     */   }
/*     */   
/*     */   public Map<String, String> getBackwardsSoundMappings() {
/* 157 */     return this.backwardsSoundMappings;
/*     */   }
/*     */   
/*     */   public Class<? extends Protocol<?, ?, ?, ?>> getViaVersionProtocolClass() {
/* 161 */     return this.vvProtocolClass;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Logger getLogger() {
/* 166 */     return ViaBackwards.getPlatform().getLogger();
/*     */   }
/*     */ 
/*     */   
/*     */   protected CompoundTag readNBTFile(String name) {
/* 171 */     return VBMappingDataLoader.loadNBTFromDir(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */