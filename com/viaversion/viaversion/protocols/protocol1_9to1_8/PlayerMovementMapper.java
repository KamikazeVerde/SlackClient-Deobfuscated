/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ 
/*    */ public class PlayerMovementMapper
/*    */   implements PacketHandler
/*    */ {
/*    */   public void handle(PacketWrapper wrapper) throws Exception {
/* 28 */     MovementTracker tracker = (MovementTracker)wrapper.user().get(MovementTracker.class);
/* 29 */     tracker.incrementIdlePacket();
/*    */     
/* 31 */     if (wrapper.is((Type)Type.BOOLEAN, 0))
/* 32 */       tracker.setGround(((Boolean)wrapper.get((Type)Type.BOOLEAN, 0)).booleanValue()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\PlayerMovementMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */