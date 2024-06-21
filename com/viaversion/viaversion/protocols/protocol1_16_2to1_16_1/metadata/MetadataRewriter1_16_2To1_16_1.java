/*    */ package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.type.types.Particle;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
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
/*    */ public class MetadataRewriter1_16_2To1_16_1
/*    */   extends EntityRewriter<ClientboundPackets1_16, Protocol1_16_2To1_16_1>
/*    */ {
/*    */   public MetadataRewriter1_16_2To1_16_1(Protocol1_16_2To1_16_1 protocol) {
/* 36 */     super((Protocol)protocol);
/* 37 */     mapTypes((EntityType[])Entity1_16Types.values(), Entity1_16_2Types.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
/* 42 */     if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
/* 43 */       ((Protocol1_16_2To1_16_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
/* 44 */     } else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
/* 45 */       int data = ((Integer)metadata.getValue()).intValue();
/* 46 */       metadata.setValue(Integer.valueOf(((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId(data)));
/* 47 */     } else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
/* 48 */       rewriteParticle((Particle)metadata.getValue());
/*    */     } 
/*    */     
/* 51 */     if (type == null)
/*    */       return; 
/* 53 */     if (type.isOrHasParent((EntityType)Entity1_16_2Types.MINECART_ABSTRACT) && metadata
/* 54 */       .id() == 10) {
/*    */       
/* 56 */       int data = ((Integer)metadata.getValue()).intValue();
/* 57 */       metadata.setValue(Integer.valueOf(((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId(data)));
/*    */     } 
/*    */     
/* 60 */     if (type.isOrHasParent((EntityType)Entity1_16_2Types.ABSTRACT_PIGLIN)) {
/* 61 */       if (metadata.id() == 15) {
/* 62 */         metadata.setId(16);
/* 63 */       } else if (metadata.id() == 16) {
/* 64 */         metadata.setId(15);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType typeFromId(int type) {
/* 71 */     return Entity1_16_2Types.getTypeFromId(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_2to1_16_1\metadata\MetadataRewriter1_16_2To1_16_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */