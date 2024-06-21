/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
/*    */ import com.viaversion.viaversion.util.PipelineUtil;
/*    */ import io.netty.channel.ChannelHandlerContext;
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
/*    */ public abstract class MovementTransmitterProvider
/*    */   implements Provider
/*    */ {
/*    */   public abstract Object getFlyingPacket();
/*    */   
/*    */   public abstract Object getGroundPacket();
/*    */   
/*    */   public void sendPlayer(UserConnection userConnection) {
/* 33 */     ChannelHandlerContext context = PipelineUtil.getContextBefore("decoder", userConnection.getChannel().pipeline());
/* 34 */     if (context != null) {
/* 35 */       if (((MovementTracker)userConnection.get(MovementTracker.class)).isGround()) {
/* 36 */         context.fireChannelRead(getGroundPacket());
/*    */       } else {
/* 38 */         context.fireChannelRead(getFlyingPacket());
/*    */       } 
/* 40 */       ((MovementTracker)userConnection.get(MovementTracker.class)).incrementIdlePacket();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\providers\MovementTransmitterProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */