/*    */ package com.viaversion.viaversion.protocols.protocol1_14_1to1_14;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*    */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata.MetadataRewriter1_14_1To1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets.EntityPackets;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
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
/*    */ public class Protocol1_14_1To1_14
/*    */   extends AbstractProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14>
/*    */ {
/* 31 */   private final MetadataRewriter1_14_1To1_14 metadataRewriter = new MetadataRewriter1_14_1To1_14(this);
/*    */   
/*    */   public Protocol1_14_1To1_14() {
/* 34 */     super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 39 */     this.metadataRewriter.register();
/*    */     
/* 41 */     EntityPackets.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(UserConnection userConnection) {
/* 46 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(userConnection, (EntityType)Entity1_14Types.PLAYER));
/*    */   }
/*    */ 
/*    */   
/*    */   public MetadataRewriter1_14_1To1_14 getEntityRewriter() {
/* 51 */     return this.metadataRewriter;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14_1to1_14\Protocol1_14_1To1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */