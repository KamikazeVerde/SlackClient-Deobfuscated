/*     */ package com.viaversion.viaversion.api.data;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.TagData;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
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
/*     */ public class MappingDataBase
/*     */   implements MappingData
/*     */ {
/*     */   protected final String unmappedVersion;
/*     */   protected final String mappedVersion;
/*     */   protected BiMappings itemMappings;
/*     */   protected FullMappings argumentTypeMappings;
/*     */   protected FullMappings entityMappings;
/*     */   protected ParticleMappings particleMappings;
/*     */   protected Mappings blockMappings;
/*     */   protected Mappings blockStateMappings;
/*     */   protected Mappings blockEntityMappings;
/*     */   protected Mappings soundMappings;
/*     */   protected Mappings statisticsMappings;
/*     */   protected Mappings enchantmentMappings;
/*     */   protected Mappings paintingMappings;
/*     */   protected Mappings menuMappings;
/*     */   protected Map<RegistryType, List<TagData>> tags;
/*     */   
/*     */   public MappingDataBase(String unmappedVersion, String mappedVersion) {
/*  59 */     this.unmappedVersion = unmappedVersion;
/*  60 */     this.mappedVersion = mappedVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  65 */     if (Via.getManager().isDebug()) {
/*  66 */       getLogger().info("Loading " + this.unmappedVersion + " -> " + this.mappedVersion + " mappings...");
/*     */     }
/*     */     
/*  69 */     CompoundTag data = readNBTFile("mappings-" + this.unmappedVersion + "to" + this.mappedVersion + ".nbt");
/*  70 */     this.blockMappings = loadMappings(data, "blocks");
/*  71 */     this.blockStateMappings = loadMappings(data, "blockstates");
/*  72 */     this.blockEntityMappings = loadMappings(data, "blockentities");
/*  73 */     this.soundMappings = loadMappings(data, "sounds");
/*  74 */     this.statisticsMappings = loadMappings(data, "statistics");
/*  75 */     this.menuMappings = loadMappings(data, "menus");
/*  76 */     this.enchantmentMappings = loadMappings(data, "enchantments");
/*  77 */     this.paintingMappings = loadMappings(data, "paintings");
/*  78 */     this.itemMappings = loadBiMappings(data, "items");
/*     */     
/*  80 */     CompoundTag unmappedIdentifierData = MappingDataLoader.loadNBT("identifiers-" + this.unmappedVersion + ".nbt", true);
/*  81 */     CompoundTag mappedIdentifierData = MappingDataLoader.loadNBT("identifiers-" + this.mappedVersion + ".nbt", true);
/*  82 */     if (unmappedIdentifierData != null && mappedIdentifierData != null) {
/*  83 */       this.entityMappings = loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "entities");
/*  84 */       this.argumentTypeMappings = loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "argumenttypes");
/*     */       
/*  86 */       ListTag unmappedParticles = (ListTag)unmappedIdentifierData.get("particles");
/*  87 */       ListTag mappedParticles = (ListTag)mappedIdentifierData.get("particles");
/*  88 */       if (unmappedParticles != null && mappedParticles != null) {
/*  89 */         Mappings particleMappings = loadMappings(data, "particles");
/*  90 */         if (particleMappings == null) {
/*  91 */           particleMappings = new IdentityMappings(unmappedParticles.size(), mappedParticles.size());
/*     */         }
/*     */         
/*  94 */         List<String> identifiers = (List<String>)unmappedParticles.getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList());
/*  95 */         List<String> mappedIdentifiers = (List<String>)mappedParticles.getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList());
/*  96 */         this.particleMappings = new ParticleMappings(identifiers, mappedIdentifiers, particleMappings);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     CompoundTag tagsTag = (CompoundTag)data.get("tags");
/* 101 */     if (tagsTag != null) {
/* 102 */       this.tags = new EnumMap<>(RegistryType.class);
/* 103 */       loadTags(RegistryType.ITEM, tagsTag);
/* 104 */       loadTags(RegistryType.BLOCK, tagsTag);
/*     */     } 
/*     */     
/* 107 */     loadExtras(data);
/*     */   }
/*     */   
/*     */   protected CompoundTag readNBTFile(String name) {
/* 111 */     return MappingDataLoader.loadNBT(name);
/*     */   }
/*     */   
/*     */   protected Mappings loadMappings(CompoundTag data, String key) {
/* 115 */     return MappingDataLoader.loadMappings(data, key);
/*     */   }
/*     */   
/*     */   protected FullMappings loadFullMappings(CompoundTag data, CompoundTag unmappedIdentifiers, CompoundTag mappedIdentifiers, String key) {
/* 119 */     return MappingDataLoader.loadFullMappings(data, unmappedIdentifiers, mappedIdentifiers, key);
/*     */   }
/*     */   
/*     */   protected BiMappings loadBiMappings(CompoundTag data, String key) {
/* 123 */     Mappings mappings = loadMappings(data, key);
/* 124 */     return (mappings != null) ? BiMappings.of(mappings) : null;
/*     */   }
/*     */   
/*     */   private void loadTags(RegistryType type, CompoundTag data) {
/* 128 */     CompoundTag tag = (CompoundTag)data.get(type.resourceLocation());
/* 129 */     if (tag == null) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     List<TagData> tagsList = new ArrayList<>(this.tags.size());
/* 134 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)tag.entrySet()) {
/* 135 */       IntArrayTag entries = (IntArrayTag)entry.getValue();
/* 136 */       tagsList.add(new TagData(entry.getKey(), entries.getValue()));
/*     */     } 
/*     */     
/* 139 */     this.tags.put(type, tagsList);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNewBlockStateId(int id) {
/* 144 */     return checkValidity(id, this.blockStateMappings.getNewId(id), "blockstate");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNewBlockId(int id) {
/* 149 */     return checkValidity(id, this.blockMappings.getNewId(id), "block");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNewItemId(int id) {
/* 154 */     return checkValidity(id, this.itemMappings.getNewId(id), "item");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOldItemId(int id) {
/* 159 */     return this.itemMappings.inverse().getNewIdOrDefault(id, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNewParticleId(int id) {
/* 164 */     return checkValidity(id, this.particleMappings.getNewId(id), "particles");
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TagData> getTags(RegistryType type) {
/* 169 */     return (this.tags != null) ? this.tags.get(type) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiMappings getItemMappings() {
/* 174 */     return this.itemMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleMappings getParticleMappings() {
/* 179 */     return this.particleMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getBlockMappings() {
/* 184 */     return this.blockMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getBlockEntityMappings() {
/* 189 */     return this.blockEntityMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getBlockStateMappings() {
/* 194 */     return this.blockStateMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getSoundMappings() {
/* 199 */     return this.soundMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getStatisticsMappings() {
/* 204 */     return this.statisticsMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getMenuMappings() {
/* 209 */     return this.menuMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getEnchantmentMappings() {
/* 214 */     return this.enchantmentMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullMappings getEntityMappings() {
/* 219 */     return this.entityMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullMappings getArgumentTypeMappings() {
/* 224 */     return this.argumentTypeMappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mappings getPaintingMappings() {
/* 229 */     return this.paintingMappings;
/*     */   }
/*     */   
/*     */   protected Logger getLogger() {
/* 233 */     return Via.getPlatform().getLogger();
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
/*     */   protected int checkValidity(int id, int mappedId, String type) {
/* 245 */     if (mappedId == -1) {
/* 246 */       if (!Via.getConfig().isSuppressConversionWarnings()) {
/* 247 */         getLogger().warning(String.format("Missing %s %s for %s %s %d", new Object[] { this.mappedVersion, type, this.unmappedVersion, type, Integer.valueOf(id) }));
/*     */       }
/* 249 */       return 0;
/*     */     } 
/* 251 */     return mappedId;
/*     */   }
/*     */   
/*     */   protected void loadExtras(CompoundTag data) {}
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\MappingDataBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */