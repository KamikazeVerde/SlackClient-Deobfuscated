/*    */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.data.MappingData;
/*    */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
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
/*    */ public class Protocol1_10To1_11
/*    */   extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
/*    */ {
/* 37 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.11", "1.10");
/* 38 */   private final EntityPackets1_11 entityPackets = new EntityPackets1_11(this);
/*    */   private BlockItemPackets1_11 blockItemPackets;
/*    */   
/*    */   public Protocol1_10To1_11() {
/* 42 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 47 */     (this.blockItemPackets = new BlockItemPackets1_11(this)).register();
/* 48 */     this.entityPackets.register();
/* 49 */     (new PlayerPackets1_11()).register(this);
/*    */     
/* 51 */     SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter(this);
/* 52 */     soundRewriter.registerNamedSound((ClientboundPacketType)ClientboundPackets1_9_3.NAMED_SOUND);
/* 53 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_9_3.SOUND);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(UserConnection user) {
/* 59 */     if (!user.has(ClientWorld.class)) {
/* 60 */       user.put((StorableObject)new ClientWorld(user));
/*    */     }
/*    */     
/* 63 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_11Types.EntityType.PLAYER));
/*    */     
/* 65 */     if (!user.has(WindowTracker.class)) {
/* 66 */       user.put((StorableObject)new WindowTracker());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BackwardsMappings getMappingData() {
/* 72 */     return MAPPINGS;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPackets1_11 getEntityRewriter() {
/* 77 */     return this.entityPackets;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockItemPackets1_11 getItemRewriter() {
/* 82 */     return this.blockItemPackets;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasMappingDataToLoad() {
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\Protocol1_10To1_11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */