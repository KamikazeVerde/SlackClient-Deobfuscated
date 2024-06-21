/*    */ package de.florianmichael.vialoadingbase.platform.providers;
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
/*    */ public class VLBMovementTransmitterProvider
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
/*    */   
/*    */   public void sendPlayer(UserConnection userConnection) {
/* 42 */     if (userConnection.getProtocolInfo().getState() != State.PLAY)
/* 43 */       return;  if (userConnection.getEntityTracker(Protocol1_9To1_8.class).clientEntityId() == -1)
/*    */       return; 
/* 45 */     MovementTracker movementTracker = (MovementTracker)userConnection.get(MovementTracker.class);
/* 46 */     movementTracker.incrementIdlePacket();
/*    */     
/*    */     try {
/* 49 */       PacketWrapper c03 = PacketWrapper.create((PacketType)ServerboundPackets1_8.PLAYER_MOVEMENT, userConnection);
/* 50 */       c03.write((Type)Type.BOOLEAN, Boolean.valueOf(movementTracker.isGround()));
/* 51 */       c03.scheduleSendToServer(Protocol1_9To1_8.class);
/* 52 */     } catch (Throwable e) {
/* 53 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\providers\VLBMovementTransmitterProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */