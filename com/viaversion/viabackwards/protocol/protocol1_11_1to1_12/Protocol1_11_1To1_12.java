/*    */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.data.MappingData;
/*    */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*    */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
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
/*    */ public class Protocol1_11_1To1_12
/*    */   extends BackwardsProtocol<ClientboundPackets1_12, ClientboundPackets1_9_3, ServerboundPackets1_12, ServerboundPackets1_9_3>
/*    */ {
/* 42 */   private static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.12", "1.11");
/* 43 */   private final EntityPackets1_12 entityPackets = new EntityPackets1_12(this);
/* 44 */   private final BlockItemPackets1_12 blockItemPackets = new BlockItemPackets1_12(this);
/*    */   
/*    */   public Protocol1_11_1To1_12() {
/* 47 */     super(ClientboundPackets1_12.class, ClientboundPackets1_9_3.class, ServerboundPackets1_12.class, ServerboundPackets1_9_3.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 52 */     this.blockItemPackets.register();
/* 53 */     this.entityPackets.register();
/* 54 */     (new SoundPackets1_12(this)).register();
/* 55 */     (new ChatPackets1_12(this)).register();
/*    */     
/* 57 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12.TITLE, wrapper -> {
/*    */           int action = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */           
/*    */           if (action >= 0 && action <= 2) {
/*    */             JsonElement component = (JsonElement)wrapper.read(Type.COMPONENT);
/*    */             wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(component.toString()));
/*    */           } 
/*    */         });
/* 65 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_12.ADVANCEMENTS);
/* 66 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_12.UNLOCK_RECIPES);
/* 67 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_12.SELECT_ADVANCEMENTS_TAB);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(UserConnection user) {
/* 73 */     if (!user.has(ClientWorld.class)) {
/* 74 */       user.put((StorableObject)new ClientWorld(user));
/*    */     }
/*    */     
/* 77 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_12Types.EntityType.PLAYER));
/*    */     
/* 79 */     user.put((StorableObject)new ShoulderTracker(user));
/*    */   }
/*    */ 
/*    */   
/*    */   public BackwardsMappings getMappingData() {
/* 84 */     return MAPPINGS;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPackets1_12 getEntityRewriter() {
/* 89 */     return this.entityPackets;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockItemPackets1_12 getItemRewriter() {
/* 94 */     return this.blockItemPackets;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasMappingDataToLoad() {
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\Protocol1_11_1To1_12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */