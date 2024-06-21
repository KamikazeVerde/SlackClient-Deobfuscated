/*    */ package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.type.types.Particle;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
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
/*    */ public class MetadataRewriter1_15To1_14_4
/*    */   extends EntityRewriter<ClientboundPackets1_14, Protocol1_15To1_14_4>
/*    */ {
/*    */   public MetadataRewriter1_15To1_14_4(Protocol1_15To1_14_4 protocol) {
/* 36 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {
/* 41 */     if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
/* 42 */       ((Protocol1_15To1_14_4)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
/* 43 */     } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
/*    */       
/* 45 */       int data = ((Integer)metadata.getValue()).intValue();
/* 46 */       metadata.setValue(Integer.valueOf(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(data)));
/* 47 */     } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
/* 48 */       rewriteParticle((Particle)metadata.getValue());
/*    */     } 
/*    */     
/* 51 */     if (type == null)
/*    */       return; 
/* 53 */     if (type.isOrHasParent((EntityType)Entity1_15Types.MINECART_ABSTRACT) && metadata
/* 54 */       .id() == 10) {
/*    */       
/* 56 */       int data = ((Integer)metadata.getValue()).intValue();
/* 57 */       metadata.setValue(Integer.valueOf(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(data)));
/*    */     } 
/*    */ 
/*    */     
/* 61 */     if (metadata.id() > 11 && type.isOrHasParent((EntityType)Entity1_15Types.LIVINGENTITY)) {
/* 62 */       metadata.setId(metadata.id() + 1);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 69 */     if (type.isOrHasParent((EntityType)Entity1_15Types.WOLF)) {
/* 70 */       if (metadata.id() == 18) {
/* 71 */         metadatas.remove(metadata);
/* 72 */       } else if (metadata.id() > 18) {
/* 73 */         metadata.setId(metadata.id() - 1);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int newEntityId(int id) {
/* 80 */     return EntityPackets.getNewEntityId(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType typeFromId(int type) {
/* 85 */     return Entity1_15Types.getTypeFromId(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_15to1_14_4\metadata\MetadataRewriter1_15To1_14_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */