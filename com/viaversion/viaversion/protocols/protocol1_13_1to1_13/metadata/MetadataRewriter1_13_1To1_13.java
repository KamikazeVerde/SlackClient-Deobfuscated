/*    */ package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.type.types.Particle;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
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
/*    */ public class MetadataRewriter1_13_1To1_13
/*    */   extends EntityRewriter<ClientboundPackets1_13, Protocol1_13_1To1_13>
/*    */ {
/*    */   public MetadataRewriter1_13_1To1_13(Protocol1_13_1To1_13 protocol) {
/* 35 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
/* 41 */     if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
/* 42 */       ((Protocol1_13_1To1_13)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
/* 43 */     } else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
/*    */       
/* 45 */       int data = ((Integer)metadata.getValue()).intValue();
/* 46 */       metadata.setValue(Integer.valueOf(((Protocol1_13_1To1_13)this.protocol).getMappingData().getNewBlockStateId(data)));
/* 47 */     } else if (metadata.metaType() == Types1_13.META_TYPES.particleType) {
/* 48 */       rewriteParticle((Particle)metadata.getValue());
/*    */     } 
/*    */     
/* 51 */     if (type == null)
/*    */       return; 
/* 53 */     if (type.isOrHasParent((EntityType)Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.id() == 9) {
/*    */       
/* 55 */       int data = ((Integer)metadata.getValue()).intValue();
/* 56 */       metadata.setValue(Integer.valueOf(((Protocol1_13_1To1_13)this.protocol).getMappingData().getNewBlockStateId(data)));
/* 57 */     } else if (type.isOrHasParent((EntityType)Entity1_13Types.EntityType.ABSTRACT_ARROW) && metadata.id() >= 7) {
/* 58 */       metadata.setId(metadata.id() + 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType typeFromId(int type) {
/* 64 */     return (EntityType)Entity1_13Types.getTypeFromId(type, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType objectTypeFromId(int type) {
/* 69 */     return (EntityType)Entity1_13Types.getTypeFromId(type, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13_1to1_13\metadata\MetadataRewriter1_13_1To1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */