/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ public final class EntityTypeUtil
/*     */ {
/*  35 */   private static final EntityType[] EMPTY_ARRAY = new EntityType[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityType[] toOrderedArray(EntityType[] values) {
/*  44 */     List<EntityType> types = new ArrayList<>();
/*  45 */     for (EntityType type : values) {
/*  46 */       if (type.getId() != -1) {
/*  47 */         types.add(type);
/*     */       }
/*     */     } 
/*     */     
/*  51 */     types.sort(Comparator.comparingInt(EntityType::getId));
/*  52 */     return types.<EntityType>toArray(EMPTY_ARRAY);
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
/*     */   public static <T extends EntityType> void initialize(T[] values, EntityType[] typesToFill, Protocol<?, ?, ?, ?> protocol, EntityIdSetter<T> idSetter) {
/*  65 */     for (T type : values) {
/*  66 */       if (!type.isAbstractType()) {
/*     */ 
/*     */ 
/*     */         
/*  70 */         int id = protocol.getMappingData().getEntityMappings().mappedId(type.identifier());
/*  71 */         Preconditions.checkArgument((id != -1), "Entity type %s has no id", new Object[] { type.identifier() });
/*  72 */         idSetter.setId(type, id);
/*  73 */         typesToFill[id] = (EntityType)type;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static EntityType[] createSizedArray(EntityType[] values) {
/*  78 */     int count = 0;
/*  79 */     for (EntityType type : values) {
/*  80 */       if (!type.isAbstractType()) {
/*  81 */         count++;
/*     */       }
/*     */     } 
/*  84 */     return new EntityType[count];
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
/*     */   public static EntityType getTypeFromId(EntityType[] values, int typeId, EntityType fallback) {
/*     */     EntityType type;
/*  97 */     if (typeId < 0 || typeId >= values.length || (type = values[typeId]) == null) {
/*  98 */       Via.getPlatform().getLogger().severe("Could not find " + fallback.getClass().getSimpleName() + " type id " + typeId);
/*  99 */       return fallback;
/*     */     } 
/* 101 */     return type;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface EntityIdSetter<T extends EntityType> {
/*     */     void setId(T param1T, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\EntityTypeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */