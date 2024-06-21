/*    */ package com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
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
/*    */ public class Protocol1_12_2To1_12_1
/*    */   extends AbstractProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12_1, ServerboundPackets1_12_1, ServerboundPackets1_12_1>
/*    */ {
/*    */   public Protocol1_12_2To1_12_1() {
/* 29 */     super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 34 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.KEEP_ALIVE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 37 */             map((Type)Type.VAR_INT, (Type)Type.LONG);
/*    */           }
/*    */         });
/*    */     
/* 41 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.KEEP_ALIVE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 44 */             map((Type)Type.LONG, (Type)Type.VAR_INT);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12_2to1_12_1\Protocol1_12_2To1_12_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */