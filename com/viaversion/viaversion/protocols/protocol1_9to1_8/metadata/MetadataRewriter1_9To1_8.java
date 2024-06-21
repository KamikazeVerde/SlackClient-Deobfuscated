/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.EulerAngle;
/*     */ import com.viaversion.viaversion.api.minecraft.Vector;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
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
/*     */ public class MetadataRewriter1_9To1_8
/*     */   extends EntityRewriter<ClientboundPackets1_8, Protocol1_9To1_8>
/*     */ {
/*     */   public MetadataRewriter1_9To1_8(Protocol1_9To1_8 protocol) {
/*  39 */     super((Protocol)protocol); } protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
/*     */     String owner;
/*     */     UUID toWrite;
/*     */     Vector vector;
/*     */     EulerAngle angle;
/*  44 */     MetaIndex metaIndex = MetaIndex.searchIndex(type, metadata.id());
/*  45 */     if (metaIndex == null) {
/*  46 */       throw new Exception("Could not find valid metadata");
/*     */     }
/*     */     
/*  49 */     if (metaIndex.getNewType() == null) {
/*  50 */       metadatas.remove(metadata);
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     metadata.setId(metaIndex.getNewIndex());
/*  55 */     metadata.setMetaTypeUnsafe((MetaType)metaIndex.getNewType());
/*     */     
/*  57 */     Object value = metadata.getValue();
/*  58 */     switch (metaIndex.getNewType()) {
/*     */       
/*     */       case Byte:
/*  61 */         if (metaIndex.getOldType() == MetaType1_8.Byte) {
/*  62 */           metadata.setValue(value);
/*     */         }
/*  64 */         if (metaIndex.getOldType() == MetaType1_8.Int) {
/*  65 */           metadata.setValue(Byte.valueOf(((Integer)value).byteValue()));
/*     */         }
/*     */         
/*  68 */         if (metaIndex == MetaIndex.ENTITY_STATUS && type == Entity1_10Types.EntityType.PLAYER) {
/*  69 */           Byte val = Byte.valueOf((byte)0);
/*  70 */           if ((((Byte)value).byteValue() & 0x10) == 16) {
/*  71 */             val = Byte.valueOf((byte)1);
/*     */           }
/*  73 */           int newIndex = MetaIndex.PLAYER_HAND.getNewIndex();
/*  74 */           MetaType1_9 metaType1_9 = MetaIndex.PLAYER_HAND.getNewType();
/*  75 */           metadatas.add(new Metadata(newIndex, (MetaType)metaType1_9, val));
/*     */         } 
/*     */         return;
/*     */       case OptUUID:
/*  79 */         owner = (String)value;
/*  80 */         toWrite = null;
/*  81 */         if (!owner.isEmpty()) {
/*     */           try {
/*  83 */             toWrite = UUID.fromString(owner);
/*  84 */           } catch (Exception exception) {}
/*     */         }
/*     */         
/*  87 */         metadata.setValue(toWrite);
/*     */         return;
/*     */       
/*     */       case VarInt:
/*  91 */         if (metaIndex.getOldType() == MetaType1_8.Byte) {
/*  92 */           metadata.setValue(Integer.valueOf(((Byte)value).intValue()));
/*     */         }
/*  94 */         if (metaIndex.getOldType() == MetaType1_8.Short) {
/*  95 */           metadata.setValue(Integer.valueOf(((Short)value).intValue()));
/*     */         }
/*  97 */         if (metaIndex.getOldType() == MetaType1_8.Int) {
/*  98 */           metadata.setValue(value);
/*     */         }
/*     */         return;
/*     */       case Float:
/*     */       case String:
/* 103 */         metadata.setValue(value);
/*     */         return;
/*     */       case Boolean:
/* 106 */         if (metaIndex == MetaIndex.AGEABLE_AGE) {
/* 107 */           metadata.setValue(Boolean.valueOf((((Byte)value).byteValue() < 0)));
/*     */         } else {
/* 109 */           metadata.setValue(Boolean.valueOf((((Byte)value).byteValue() != 0)));
/*     */         }  return;
/*     */       case Slot:
/* 112 */         metadata.setValue(value);
/* 113 */         ItemRewriter.toClient((Item)metadata.getValue());
/*     */         return;
/*     */       case Position:
/* 116 */         vector = (Vector)value;
/* 117 */         metadata.setValue(vector);
/*     */         return;
/*     */       case Vector3F:
/* 120 */         angle = (EulerAngle)value;
/* 121 */         metadata.setValue(angle);
/*     */         return;
/*     */       case Chat:
/* 124 */         value = Protocol1_9To1_8.fixJson(value.toString());
/* 125 */         metadata.setValue(value);
/*     */         return;
/*     */       
/*     */       case BlockID:
/* 129 */         metadata.setValue(Integer.valueOf(((Number)value).intValue()));
/*     */         return;
/*     */     } 
/* 132 */     metadatas.remove(metadata);
/* 133 */     throw new Exception("Unhandled MetaDataType: " + metaIndex.getNewType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 139 */     return (EntityType)Entity1_10Types.getTypeFromId(type, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType objectTypeFromId(int type) {
/* 144 */     return (EntityType)Entity1_10Types.getTypeFromId(type, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\metadata\MetadataRewriter1_9To1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */