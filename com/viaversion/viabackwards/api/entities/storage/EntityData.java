/*     */ package com.viaversion.viabackwards.api.entities.storage;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import java.util.Locale;
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
/*     */ public class EntityData
/*     */ {
/*     */   private final BackwardsProtocol<?, ?, ?, ?> protocol;
/*     */   private final int id;
/*     */   private final int replacementId;
/*     */   private final String key;
/*  33 */   private NameVisibility nameVisibility = NameVisibility.NONE;
/*     */   private MetaCreator defaultMeta;
/*     */   
/*     */   public EntityData(BackwardsProtocol<?, ?, ?, ?> protocol, EntityType type, int replacementId) {
/*  37 */     this(protocol, type.name(), type.getId(), replacementId);
/*     */   }
/*     */   
/*     */   public EntityData(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId) {
/*  41 */     this.protocol = protocol;
/*  42 */     this.id = id;
/*  43 */     this.replacementId = replacementId;
/*  44 */     this.key = key.toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   public EntityData jsonName() {
/*  48 */     this.nameVisibility = NameVisibility.JSON;
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   public EntityData plainName() {
/*  53 */     this.nameVisibility = NameVisibility.PLAIN;
/*  54 */     return this;
/*     */   }
/*     */   
/*     */   public EntityData spawnMetadata(MetaCreator handler) {
/*  58 */     this.defaultMeta = handler;
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasBaseMeta() {
/*  63 */     return (this.defaultMeta != null);
/*     */   }
/*     */   
/*     */   public int typeId() {
/*  67 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object mobName() {
/*  74 */     if (this.nameVisibility == NameVisibility.NONE) {
/*  75 */       return null;
/*     */     }
/*     */     
/*  78 */     String name = this.protocol.getMappingData().mappedEntityName(this.key);
/*  79 */     if (name == null) {
/*  80 */       ViaBackwards.getPlatform().getLogger().warning("Entity name for " + this.key + " not found in protocol " + this.protocol.getClass().getSimpleName());
/*  81 */       name = this.key;
/*     */     } 
/*  83 */     return (this.nameVisibility == NameVisibility.JSON) ? ChatRewriter.legacyTextToJson(name) : name;
/*     */   }
/*     */   
/*     */   public int replacementId() {
/*  87 */     return this.replacementId;
/*     */   }
/*     */   
/*     */   public MetaCreator defaultMeta() {
/*  91 */     return this.defaultMeta;
/*     */   }
/*     */   
/*     */   public boolean isObjectType() {
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public int objectData() {
/*  99 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return "EntityData{id=" + this.id + ", mobName='" + this.key + '\'' + ", replacementId=" + this.replacementId + ", defaultMeta=" + this.defaultMeta + '}';
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
/*     */   
/*     */   private enum NameVisibility
/*     */   {
/* 119 */     PLAIN,
/* 120 */     JSON,
/* 121 */     NONE;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface MetaCreator {
/*     */     void createMeta(WrappedMetadata param1WrappedMetadata);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\entities\storage\EntityData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */