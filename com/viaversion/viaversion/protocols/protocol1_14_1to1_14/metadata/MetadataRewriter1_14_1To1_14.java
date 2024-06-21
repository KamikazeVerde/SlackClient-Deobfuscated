/*    */ package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
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
/*    */ public class MetadataRewriter1_14_1To1_14
/*    */   extends EntityRewriter<ClientboundPackets1_14, Protocol1_14_1To1_14>
/*    */ {
/*    */   public MetadataRewriter1_14_1To1_14(Protocol1_14_1To1_14 protocol) {
/* 32 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
/* 37 */     if (type == null)
/*    */       return; 
/* 39 */     if ((type == Entity1_14Types.VILLAGER || type == Entity1_14Types.WANDERING_TRADER) && 
/* 40 */       metadata.id() >= 15) {
/* 41 */       metadata.setId(metadata.id() + 1);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityType typeFromId(int type) {
/* 48 */     return Entity1_14Types.getTypeFromId(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14_1to1_14\metadata\MetadataRewriter1_14_1To1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */