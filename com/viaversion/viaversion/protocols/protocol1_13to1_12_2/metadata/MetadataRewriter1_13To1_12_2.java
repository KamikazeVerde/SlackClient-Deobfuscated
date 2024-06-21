/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
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
/*     */ 
/*     */ public class MetadataRewriter1_13To1_12_2
/*     */   extends EntityRewriter<ClientboundPackets1_12_1, Protocol1_13To1_12_2>
/*     */ {
/*     */   public MetadataRewriter1_13To1_12_2(Protocol1_13To1_12_2 protocol) {
/*  39 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
/*  45 */     if (metadata.metaType().typeId() > 4) {
/*  46 */       metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId() + 1));
/*     */     } else {
/*  48 */       metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
/*     */     } 
/*     */ 
/*     */     
/*  52 */     if (metadata.id() == 2) {
/*  53 */       if (metadata.getValue() != null && !((String)metadata.getValue()).isEmpty()) {
/*  54 */         metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, ChatRewriter.legacyTextToJson((String)metadata.getValue()));
/*     */       } else {
/*  56 */         metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, null);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (type == Entity1_13Types.EntityType.ENDERMAN && metadata.id() == 12) {
/*  62 */       int stateId = ((Integer)metadata.getValue()).intValue();
/*  63 */       int id = stateId & 0xFFF;
/*  64 */       int data = stateId >> 12 & 0xF;
/*  65 */       metadata.setValue(Integer.valueOf(id << 4 | data & 0xF));
/*     */     } 
/*     */ 
/*     */     
/*  69 */     if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
/*  70 */       metadata.setMetaType(Types1_13.META_TYPES.itemType);
/*  71 */       ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
/*  72 */     } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
/*     */       
/*  74 */       metadata.setValue(Integer.valueOf(WorldPackets.toNewId(((Integer)metadata.getValue()).intValue())));
/*     */     } 
/*     */ 
/*     */     
/*  78 */     if (type == null) {
/*     */       return;
/*     */     }
/*  81 */     if (type == Entity1_13Types.EntityType.WOLF && metadata.id() == 17) {
/*  82 */       metadata.setValue(Integer.valueOf(15 - ((Integer)metadata.getValue()).intValue()));
/*     */     }
/*     */ 
/*     */     
/*  86 */     if (type.isOrHasParent((EntityType)Entity1_13Types.EntityType.ZOMBIE) && 
/*  87 */       metadata.id() > 14) {
/*  88 */       metadata.setId(metadata.id() + 1);
/*     */     }
/*     */ 
/*     */     
/*  92 */     if (type.isOrHasParent((EntityType)Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.id() == 9) {
/*     */       
/*  94 */       int oldId = ((Integer)metadata.getValue()).intValue();
/*  95 */       int combined = (oldId & 0xFFF) << 4 | oldId >> 12 & 0xF;
/*  96 */       int newId = WorldPackets.toNewId(combined);
/*  97 */       metadata.setValue(Integer.valueOf(newId));
/*     */     } 
/*     */ 
/*     */     
/* 101 */     if (type == Entity1_13Types.EntityType.AREA_EFFECT_CLOUD) {
/* 102 */       if (metadata.id() == 9) {
/* 103 */         int particleId = ((Integer)metadata.getValue()).intValue();
/* 104 */         Metadata parameter1Meta = metaByIndex(10, metadatas);
/* 105 */         Metadata parameter2Meta = metaByIndex(11, metadatas);
/* 106 */         int parameter1 = (parameter1Meta != null) ? ((Integer)parameter1Meta.getValue()).intValue() : 0;
/* 107 */         int parameter2 = (parameter2Meta != null) ? ((Integer)parameter2Meta.getValue()).intValue() : 0;
/*     */         
/* 109 */         Particle particle = ParticleRewriter.rewriteParticle(particleId, new Integer[] { Integer.valueOf(parameter1), Integer.valueOf(parameter2) });
/* 110 */         if (particle != null && particle.getId() != -1) {
/* 111 */           metadatas.add(new Metadata(9, Types1_13.META_TYPES.particleType, particle));
/*     */         }
/*     */       } 
/*     */       
/* 115 */       if (metadata.id() >= 9) {
/* 116 */         metadatas.remove(metadata);
/*     */       }
/*     */     } 
/* 119 */     if (metadata.id() == 0) {
/* 120 */       metadata.setValue(Byte.valueOf((byte)(((Byte)metadata.getValue()).byteValue() & 0xFFFFFFEF)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int newEntityId(int id) {
/* 128 */     return EntityTypeRewriter.getNewId(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 133 */     return (EntityType)Entity1_13Types.getTypeFromId(type, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType objectTypeFromId(int type) {
/* 138 */     return (EntityType)Entity1_13Types.getTypeFromId(type, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\metadata\MetadataRewriter1_13To1_12_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */