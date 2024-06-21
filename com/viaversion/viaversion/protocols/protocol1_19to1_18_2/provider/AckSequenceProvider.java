/*    */ package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
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
/*    */ 
/*    */ public class AckSequenceProvider
/*    */   implements Provider
/*    */ {
/*    */   public void handleSequence(UserConnection connection, int sequence) throws Exception {
/* 30 */     PacketWrapper ackPacket = PacketWrapper.create((PacketType)ClientboundPackets1_19.BLOCK_CHANGED_ACK, connection);
/* 31 */     ackPacket.write((Type)Type.VAR_INT, Integer.valueOf(sequence));
/* 32 */     ackPacket.scheduleSend(Protocol1_19To1_18_2.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\provider\AckSequenceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */