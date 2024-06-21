/*    */ package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets.EntityPackets1_11_1;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets.ItemPackets1_11_1;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*    */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*    */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
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
/*    */ public class Protocol1_11To1_11_1
/*    */   extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
/*    */ {
/* 33 */   private final EntityPackets1_11_1 entityPackets = new EntityPackets1_11_1(this);
/* 34 */   private final ItemPackets1_11_1 itemRewriter = new ItemPackets1_11_1(this);
/*    */   
/*    */   public Protocol1_11To1_11_1() {
/* 37 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 42 */     this.entityPackets.register();
/* 43 */     this.itemRewriter.register();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(UserConnection user) {
/* 49 */     if (!user.has(ClientWorld.class)) {
/* 50 */       user.put((StorableObject)new ClientWorld(user));
/*    */     }
/*    */     
/* 53 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_11Types.EntityType.PLAYER));
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPackets1_11_1 getEntityRewriter() {
/* 58 */     return this.entityPackets;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemPackets1_11_1 getItemRewriter() {
/* 63 */     return this.itemRewriter;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11to1_11_1\Protocol1_11To1_11_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */