/*    */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.type.types.Particle;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
/*    */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetadataRewriter1_16To1_15_2
/*    */   extends EntityRewriter<ClientboundPackets1_15, Protocol1_16To1_15_2>
/*    */ {
/*    */   public MetadataRewriter1_16To1_15_2(Protocol1_16To1_15_2 protocol) {
/* 36 */     super((Protocol)protocol);
/* 37 */     mapEntityType((EntityType)Entity1_15Types.ZOMBIE_PIGMAN, (EntityType)Entity1_16Types.ZOMBIFIED_PIGLIN);
/* 38 */     mapTypes((EntityType[])Entity1_15Types.values(), Entity1_16Types.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
/* 43 */     metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
/* 44 */     if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
/* 45 */       ((Protocol1_16To1_15_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
/* 46 */     } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
/* 47 */       int data = ((Integer)metadata.getValue()).intValue();
/* 48 */       metadata.setValue(Integer.valueOf(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId(data)));
/* 49 */     } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
/* 50 */       rewriteParticle((Particle)metadata.getValue());
/*    */     } 
/*    */     
/* 53 */     if (type == null)
/*    */       return; 
/* 55 */     if (type.isOrHasParent((EntityType)Entity1_16Types.MINECART_ABSTRACT) && metadata
/* 56 */       .id() == 10) {
/*    */       
/* 58 */       int data = ((Integer)metadata.getValue()).intValue();
/* 59 */       metadata.setValue(Integer.valueOf(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId(data)));
/*    */     } 
/*    */     
/* 62 */     if (type.isOrHasParent((EntityType)Entity1_16Types.ABSTRACT_ARROW)) {
/* 63 */       if (metadata.id() == 8) {
/* 64 */         metadatas.remove(metadata);
/* 65 */       } else if (metadata.id() > 8) {
/* 66 */         metadata.setId(metadata.id() - 1);
/*    */       } 
/*    */     }
/*    */     
/* 70 */     if (type == Entity1_16Types.WOLF && 
/* 71 */       metadata.id() == 16) {
/* 72 */       byte mask = ((Byte)metadata.value()).byteValue();
/* 73 */       int angerTime = ((mask & 0x2) != 0) ? Integer.MAX_VALUE : 0;
/* 74 */       metadatas.add(new Metadata(20, Types1_16.META_TYPES.varIntType, Integer.valueOf(angerTime)));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityType typeFromId(int type) {
/* 81 */     return Entity1_16Types.getTypeFromId(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\metadata\MetadataRewriter1_16To1_15_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */