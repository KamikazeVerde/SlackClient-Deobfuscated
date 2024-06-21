/*    */ package com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.storage.PlayerHandStorage;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
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
/*    */ public class Protocol1_16_3To1_16_4
/*    */   extends BackwardsProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2>
/*    */ {
/*    */   public Protocol1_16_3To1_16_4() {
/* 31 */     super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 36 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.EDIT_BOOK, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 39 */             map(Type.FLAT_VAR_INT_ITEM);
/* 40 */             map((Type)Type.BOOLEAN);
/* 41 */             handler(wrapper -> {
/*    */                   int slot = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */                   
/*    */                   if (slot == 1) {
/*    */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(40));
/*    */                   } else {
/*    */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(((PlayerHandStorage)wrapper.user().get(PlayerHandStorage.class)).getCurrentHand()));
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/* 52 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.HELD_ITEM_CHANGE, wrapper -> {
/*    */           short slot = ((Short)wrapper.passthrough((Type)Type.SHORT)).shortValue();
/*    */           ((PlayerHandStorage)wrapper.user().get(PlayerHandStorage.class)).setCurrentHand(slot);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(UserConnection user) {
/* 60 */     user.put((StorableObject)new PlayerHandStorage());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_3to1_16_4\Protocol1_16_3To1_16_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */