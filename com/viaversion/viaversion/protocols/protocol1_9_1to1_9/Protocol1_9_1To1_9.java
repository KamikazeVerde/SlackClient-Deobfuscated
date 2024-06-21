/*    */ package com.viaversion.viaversion.protocols.protocol1_9_1to1_9;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
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
/*    */ public class Protocol1_9_1To1_9
/*    */   extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9, ServerboundPackets1_9, ServerboundPackets1_9>
/*    */ {
/*    */   public Protocol1_9_1To1_9() {
/* 29 */     super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 36 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 39 */             map((Type)Type.INT);
/* 40 */             map((Type)Type.UNSIGNED_BYTE);
/*    */             
/* 42 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 43 */             map((Type)Type.UNSIGNED_BYTE);
/* 44 */             map((Type)Type.UNSIGNED_BYTE);
/* 45 */             map(Type.STRING);
/* 46 */             map((Type)Type.BOOLEAN);
/*    */           }
/*    */         });
/*    */     
/* 50 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SOUND, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 53 */             map((Type)Type.VAR_INT);
/*    */             
/* 55 */             handler(wrapper -> {
/*    */                   int sound = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*    */                   if (sound >= 415)
/*    */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(sound + 1)); 
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_1to1_9\Protocol1_9_1To1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */