/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PlayerLookTargetProvider;
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
/*    */ public class TabCompleteTracker
/*    */   implements StorableObject
/*    */ {
/*    */   private int transactionId;
/*    */   private String input;
/*    */   private String lastTabComplete;
/*    */   private long timeToSend;
/*    */   
/*    */   public void sendPacketToServer(UserConnection connection) {
/* 37 */     if (this.lastTabComplete == null || this.timeToSend > System.currentTimeMillis())
/* 38 */       return;  PacketWrapper wrapper = PacketWrapper.create((PacketType)ServerboundPackets1_12_1.TAB_COMPLETE, null, connection);
/* 39 */     wrapper.write(Type.STRING, this.lastTabComplete);
/* 40 */     wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/* 41 */     Position playerLookTarget = ((PlayerLookTargetProvider)Via.getManager().getProviders().get(PlayerLookTargetProvider.class)).getPlayerLookTarget(connection);
/* 42 */     wrapper.write(Type.OPTIONAL_POSITION, playerLookTarget);
/*    */     try {
/* 44 */       wrapper.scheduleSendToServer(Protocol1_13To1_12_2.class);
/* 45 */     } catch (Exception e) {
/* 46 */       e.printStackTrace();
/*    */     } 
/* 48 */     this.lastTabComplete = null;
/*    */   }
/*    */   
/*    */   public int getTransactionId() {
/* 52 */     return this.transactionId;
/*    */   }
/*    */   
/*    */   public void setTransactionId(int transactionId) {
/* 56 */     this.transactionId = transactionId;
/*    */   }
/*    */   
/*    */   public String getInput() {
/* 60 */     return this.input;
/*    */   }
/*    */   
/*    */   public void setInput(String input) {
/* 64 */     this.input = input;
/*    */   }
/*    */   
/*    */   public String getLastTabComplete() {
/* 68 */     return this.lastTabComplete;
/*    */   }
/*    */   
/*    */   public void setLastTabComplete(String lastTabComplete) {
/* 72 */     this.lastTabComplete = lastTabComplete;
/*    */   }
/*    */   
/*    */   public long getTimeToSend() {
/* 76 */     return this.timeToSend;
/*    */   }
/*    */   
/*    */   public void setTimeToSend(long timeToSend) {
/* 80 */     this.timeToSend = timeToSend;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\storage\TabCompleteTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */