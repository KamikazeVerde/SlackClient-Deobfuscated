/*    */ package com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
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
/*    */ 
/*    */ public class Protocol1_16_4To1_16_3
/*    */   extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2>
/*    */ {
/*    */   public Protocol1_16_4To1_16_3() {
/* 29 */     super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 34 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.EDIT_BOOK, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 37 */             map(Type.FLAT_VAR_INT_ITEM);
/* 38 */             map((Type)Type.BOOLEAN);
/* 39 */             handler(wrapper -> {
/*    */                   int slot = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*    */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf((slot == 40) ? 1 : 0));
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_4to1_16_3\Protocol1_16_4To1_16_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */