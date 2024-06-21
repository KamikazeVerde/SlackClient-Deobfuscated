/*    */ package com.viaversion.viaversion.bukkit.tasks.protocol1_19to1_18_2;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;
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
/*    */ public final class AckSequenceTask
/*    */   implements Runnable
/*    */ {
/*    */   private final UserConnection connection;
/*    */   private final SequenceStorage sequenceStorage;
/*    */   
/*    */   public AckSequenceTask(UserConnection connection, SequenceStorage sequenceStorage) {
/* 33 */     this.connection = connection;
/* 34 */     this.sequenceStorage = sequenceStorage;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 39 */     int sequence = this.sequenceStorage.setSequenceId(-1);
/*    */     try {
/* 41 */       PacketWrapper ackPacket = PacketWrapper.create((PacketType)ClientboundPackets1_19.BLOCK_CHANGED_ACK, this.connection);
/* 42 */       ackPacket.write((Type)Type.VAR_INT, Integer.valueOf(sequence));
/* 43 */       ackPacket.scheduleSend(Protocol1_19To1_18_2.class);
/* 44 */     } catch (Exception e) {
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\tasks\protocol1_19to1_18_2\AckSequenceTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */