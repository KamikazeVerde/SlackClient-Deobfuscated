/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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
/*    */ public class Protocol1_12_1To1_12_2
/*    */   extends BackwardsProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12_1, ServerboundPackets1_12_1, ServerboundPackets1_12_1>
/*    */ {
/*    */   public Protocol1_12_1To1_12_2() {
/* 31 */     super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 36 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.KEEP_ALIVE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 39 */             handler(packetWrapper -> {
/*    */                   Long keepAlive = (Long)packetWrapper.read((Type)Type.LONG);
/*    */                   
/*    */                   ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(keepAlive.longValue());
/*    */                   packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(keepAlive.hashCode()));
/*    */                 });
/*    */           }
/*    */         });
/* 47 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.KEEP_ALIVE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 50 */             handler(packetWrapper -> {
/*    */                   int keepAlive = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*    */                   long realKeepAlive = ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).getKeepAlive();
/*    */                   if (keepAlive != Long.hashCode(realKeepAlive)) {
/*    */                     packetWrapper.cancel();
/*    */                     return;
/*    */                   } 
/*    */                   packetWrapper.write((Type)Type.LONG, Long.valueOf(realKeepAlive));
/*    */                   ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(2147483647L);
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(UserConnection userConnection) {
/* 67 */     userConnection.put(new KeepAliveTracker());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_1to1_12_2\Protocol1_12_1To1_12_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */