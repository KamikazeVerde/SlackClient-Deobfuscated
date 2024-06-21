/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.VillagerData;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
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
/*     */ public class MetadataRewriter1_14To1_13_2
/*     */   extends EntityRewriter<ClientboundPackets1_13, Protocol1_14To1_13_2>
/*     */ {
/*     */   public MetadataRewriter1_14To1_13_2(Protocol1_14To1_13_2 protocol) {
/*  43 */     super((Protocol)protocol);
/*  44 */     mapTypes((EntityType[])Entity1_13Types.EntityType.values(), Entity1_14Types.class);
/*  45 */     mapEntityType((EntityType)Entity1_13Types.EntityType.OCELOT, (EntityType)Entity1_14Types.CAT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
/*  50 */     metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
/*     */     
/*  52 */     EntityTracker1_14 tracker = (EntityTracker1_14)tracker(connection);
/*     */     
/*  54 */     if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
/*  55 */       ((Protocol1_14To1_13_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
/*  56 */     } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
/*     */       
/*  58 */       int data = ((Integer)metadata.getValue()).intValue();
/*  59 */       metadata.setValue(Integer.valueOf(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(data)));
/*  60 */     } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
/*  61 */       rewriteParticle((Particle)metadata.getValue());
/*     */     } 
/*     */     
/*  64 */     if (type == null) {
/*     */       return;
/*     */     }
/*  67 */     if (metadata.id() > 5) {
/*  68 */       metadata.setId(metadata.id() + 1);
/*     */     }
/*  70 */     if (metadata.id() == 8 && type.isOrHasParent((EntityType)Entity1_14Types.LIVINGENTITY)) {
/*  71 */       float v = ((Number)metadata.getValue()).floatValue();
/*  72 */       if (Float.isNaN(v) && Via.getConfig().is1_14HealthNaNFix()) {
/*  73 */         metadata.setValue(Float.valueOf(1.0F));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  78 */     if (metadata.id() > 11 && type.isOrHasParent((EntityType)Entity1_14Types.LIVINGENTITY)) {
/*  79 */       metadata.setId(metadata.id() + 1);
/*     */     }
/*     */     
/*  82 */     if (type.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_INSENTIENT) && 
/*  83 */       metadata.id() == 13) {
/*  84 */       tracker.setInsentientData(entityId, 
/*  85 */           (byte)(((Number)metadata.getValue()).byteValue() & 0xFFFFFFFB | tracker.getInsentientData(entityId) & 0x4));
/*  86 */       metadata.setValue(Byte.valueOf(tracker.getInsentientData(entityId)));
/*     */     } 
/*     */ 
/*     */     
/*  90 */     if (type.isOrHasParent((EntityType)Entity1_14Types.PLAYER)) {
/*  91 */       if (entityId != tracker.clientEntityId()) {
/*  92 */         if (metadata.id() == 0) {
/*  93 */           byte flags = ((Number)metadata.getValue()).byteValue();
/*     */           
/*  95 */           tracker.setEntityFlags(entityId, flags);
/*  96 */         } else if (metadata.id() == 7) {
/*  97 */           tracker.setRiptide(entityId, ((((Number)metadata.getValue()).byteValue() & 0x4) != 0));
/*     */         } 
/*  99 */         if (metadata.id() == 0 || metadata.id() == 7) {
/* 100 */           metadatas.add(new Metadata(6, Types1_14.META_TYPES.poseType, Integer.valueOf(recalculatePlayerPose(entityId, tracker))));
/*     */         }
/*     */       } 
/* 103 */     } else if (type.isOrHasParent((EntityType)Entity1_14Types.ZOMBIE)) {
/* 104 */       if (metadata.id() == 16) {
/* 105 */         tracker.setInsentientData(entityId, 
/* 106 */             (byte)(tracker.getInsentientData(entityId) & 0xFFFFFFFB | (((Boolean)metadata.getValue()).booleanValue() ? 4 : 0)));
/* 107 */         metadatas.remove(metadata);
/* 108 */         metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, Byte.valueOf(tracker.getInsentientData(entityId))));
/* 109 */       } else if (metadata.id() > 16) {
/* 110 */         metadata.setId(metadata.id() - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     if (type.isOrHasParent((EntityType)Entity1_14Types.MINECART_ABSTRACT)) {
/* 115 */       if (metadata.id() == 10) {
/*     */         
/* 117 */         int data = ((Integer)metadata.getValue()).intValue();
/* 118 */         metadata.setValue(Integer.valueOf(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */       } 
/* 120 */     } else if (type.is((EntityType)Entity1_14Types.HORSE)) {
/* 121 */       if (metadata.id() == 18) {
/* 122 */         DataItem dataItem; metadatas.remove(metadata);
/*     */         
/* 124 */         int armorType = ((Integer)metadata.getValue()).intValue();
/* 125 */         Item armorItem = null;
/* 126 */         if (armorType == 1) {
/* 127 */           dataItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(727), (byte)1, (short)0, null);
/* 128 */         } else if (armorType == 2) {
/* 129 */           dataItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(728), (byte)1, (short)0, null);
/* 130 */         } else if (armorType == 3) {
/* 131 */           dataItem = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(729), (byte)1, (short)0, null);
/*     */         } 
/*     */         
/* 134 */         PacketWrapper equipmentPacket = PacketWrapper.create((PacketType)ClientboundPackets1_14.ENTITY_EQUIPMENT, null, connection);
/* 135 */         equipmentPacket.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 136 */         equipmentPacket.write((Type)Type.VAR_INT, Integer.valueOf(4));
/* 137 */         equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, dataItem);
/* 138 */         equipmentPacket.scheduleSend(Protocol1_14To1_13_2.class);
/*     */       } 
/* 140 */     } else if (type.is((EntityType)Entity1_14Types.VILLAGER)) {
/* 141 */       if (metadata.id() == 15)
/*     */       {
/* 143 */         metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId(((Integer)metadata.getValue()).intValue()), 0));
/*     */       }
/* 145 */     } else if (type.is((EntityType)Entity1_14Types.ZOMBIE_VILLAGER)) {
/* 146 */       if (metadata.id() == 18)
/*     */       {
/* 148 */         metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId(((Integer)metadata.getValue()).intValue()), 0));
/*     */       }
/* 150 */     } else if (type.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_ARROW)) {
/* 151 */       if (metadata.id() >= 9) {
/* 152 */         metadata.setId(metadata.id() + 1);
/*     */       }
/* 154 */     } else if (type.is((EntityType)Entity1_14Types.FIREWORK_ROCKET)) {
/* 155 */       if (metadata.id() == 8) {
/* 156 */         metadata.setMetaType(Types1_14.META_TYPES.optionalVarIntType);
/* 157 */         if (metadata.getValue().equals(Integer.valueOf(0))) {
/* 158 */           metadata.setValue(null);
/*     */         }
/*     */       } 
/* 161 */     } else if (type.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_SKELETON) && 
/* 162 */       metadata.id() == 14) {
/* 163 */       tracker.setInsentientData(entityId, 
/* 164 */           (byte)(tracker.getInsentientData(entityId) & 0xFFFFFFFB | (((Boolean)metadata.getValue()).booleanValue() ? 4 : 0)));
/* 165 */       metadatas.remove(metadata);
/* 166 */       metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, Byte.valueOf(tracker.getInsentientData(entityId))));
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if (type.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_ILLAGER_BASE) && 
/* 171 */       metadata.id() == 14) {
/* 172 */       tracker.setInsentientData(entityId, 
/* 173 */           (byte)(tracker.getInsentientData(entityId) & 0xFFFFFFFB | ((((Number)metadata.getValue()).byteValue() != 0) ? 4 : 0)));
/* 174 */       metadatas.remove(metadata);
/* 175 */       metadatas.add(new Metadata(13, Types1_14.META_TYPES.byteType, Byte.valueOf(tracker.getInsentientData(entityId))));
/*     */     } 
/*     */ 
/*     */     
/* 179 */     if ((type.is((EntityType)Entity1_14Types.WITCH) || type.is((EntityType)Entity1_14Types.RAVAGER) || type.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_ILLAGER_BASE)) && 
/* 180 */       metadata.id() >= 14) {
/* 181 */       metadata.setId(metadata.id() + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 188 */     return Entity1_14Types.getTypeFromId(type);
/*     */   }
/*     */   
/*     */   private static boolean isSneaking(byte flags) {
/* 192 */     return ((flags & 0x2) != 0);
/*     */   }
/*     */   
/*     */   private static boolean isSwimming(byte flags) {
/* 196 */     return ((flags & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getNewProfessionId(int old) {
/* 201 */     switch (old) {
/*     */       case 0:
/* 203 */         return 5;
/*     */       case 1:
/* 205 */         return 9;
/*     */       case 2:
/* 207 */         return 4;
/*     */       case 3:
/* 209 */         return 1;
/*     */       case 4:
/* 211 */         return 2;
/*     */       case 5:
/* 213 */         return 11;
/*     */     } 
/* 215 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isFallFlying(int entityFlags) {
/* 220 */     return ((entityFlags & 0x80) != 0);
/*     */   }
/*     */   
/*     */   public static int recalculatePlayerPose(int entityId, EntityTracker1_14 tracker) {
/* 224 */     byte flags = tracker.getEntityFlags(entityId);
/*     */     
/* 226 */     int pose = 0;
/* 227 */     if (isFallFlying(flags)) {
/* 228 */       pose = 1;
/* 229 */     } else if (tracker.isSleeping(entityId)) {
/* 230 */       pose = 2;
/* 231 */     } else if (isSwimming(flags)) {
/* 232 */       pose = 3;
/* 233 */     } else if (tracker.isRiptide(entityId)) {
/* 234 */       pose = 4;
/* 235 */     } else if (isSneaking(flags)) {
/* 236 */       pose = 5;
/*     */     } 
/* 238 */     return pose;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\metadata\MetadataRewriter1_14To1_13_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */