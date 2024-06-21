/*    */ package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.EntityPackets1_14_1;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*    */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
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
/*    */ public class Protocol1_14To1_14_1
/*    */   extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14>
/*    */ {
/* 30 */   private final EntityPackets1_14_1 entityRewriter = new EntityPackets1_14_1(this);
/*    */   
/*    */   public Protocol1_14To1_14_1() {
/* 33 */     super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 38 */     this.entityRewriter.register();
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(UserConnection user) {
/* 43 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_15Types.PLAYER));
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPackets1_14_1 getEntityRewriter() {
/* 48 */     return this.entityRewriter;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14to1_14_1\Protocol1_14To1_14_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */