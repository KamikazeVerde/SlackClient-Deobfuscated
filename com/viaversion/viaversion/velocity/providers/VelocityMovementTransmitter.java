/*    */ package com.viaversion.viaversion.velocity.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
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
/*    */ public class VelocityMovementTransmitter
/*    */   extends MovementTransmitterProvider
/*    */ {
/*    */   public Object getFlyingPacket() {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getGroundPacket() {
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public void sendPlayer(UserConnection userConnection) {
/* 41 */     if (userConnection.getProtocolInfo().getState() == State.PLAY) {
/* 42 */       PacketWrapper wrapper = PacketWrapper.create((PacketType)ServerboundPackets1_8.PLAYER_MOVEMENT, null, userConnection);
/* 43 */       MovementTracker tracker = (MovementTracker)userConnection.get(MovementTracker.class);
/* 44 */       wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(tracker.isGround()));
/*    */       try {
/* 46 */         wrapper.scheduleSendToServer(Protocol1_9To1_8.class);
/* 47 */       } catch (Exception e) {
/* 48 */         e.printStackTrace();
/*    */       } 
/* 50 */       tracker.incrementIdlePacket();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\providers\VelocityMovementTransmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */