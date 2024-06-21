/*    */ package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
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
/*    */ public class WorldPackets1_13_2
/*    */ {
/*    */   public static void register(Protocol1_13_1To1_13_2 protocol) {
/* 28 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 31 */             map((Type)Type.INT);
/* 32 */             map((Type)Type.BOOLEAN);
/* 33 */             map((Type)Type.FLOAT);
/* 34 */             map((Type)Type.FLOAT);
/* 35 */             map((Type)Type.FLOAT);
/* 36 */             map((Type)Type.FLOAT);
/* 37 */             map((Type)Type.FLOAT);
/* 38 */             map((Type)Type.FLOAT);
/* 39 */             map((Type)Type.FLOAT);
/* 40 */             map((Type)Type.INT);
/*    */             
/* 42 */             handler(wrapper -> {
/*    */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*    */                   if (id == 27)
/*    */                     wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM)); 
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_1to1_13_2\packets\WorldPackets1_13_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */