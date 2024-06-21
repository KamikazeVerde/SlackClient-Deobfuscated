/*    */ package com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class Protocol1_14_3To1_14_4
/*    */   extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14>
/*    */ {
/*    */   public Protocol1_14_3To1_14_4() {
/* 29 */     super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 35 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING, (ClientboundPacketType)ClientboundPackets1_14.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 38 */             map(Type.POSITION1_14);
/* 39 */             map((Type)Type.VAR_INT);
/* 40 */             handler(wrapper -> {
/*    */                   int status = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */                   
/*    */                   boolean allGood = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*    */                   if (allGood && status == 0) {
/*    */                     wrapper.cancel();
/*    */                   }
/*    */                 });
/*    */           }
/*    */         });
/* 50 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_14.TRADE_LIST, wrapper -> {
/*    */           wrapper.passthrough((Type)Type.VAR_INT);
/*    */           int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*    */           for (int i = 0; i < size; i++) {
/*    */             wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
/*    */             wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
/*    */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue())
/*    */               wrapper.passthrough(Type.FLAT_VAR_INT_ITEM); 
/*    */             wrapper.passthrough((Type)Type.BOOLEAN);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             wrapper.passthrough((Type)Type.FLOAT);
/*    */             wrapper.read((Type)Type.INT);
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14_3to1_14_4\Protocol1_14_3To1_14_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */