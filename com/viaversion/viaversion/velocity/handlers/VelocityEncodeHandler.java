/*    */ package com.viaversion.viaversion.velocity.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.exception.CancelEncoderException;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import java.util.List;
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
/*    */ @Sharable
/*    */ public class VelocityEncodeHandler
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection info;
/*    */   
/*    */   public VelocityEncodeHandler(UserConnection info) {
/* 34 */     this.info = info;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 39 */     if (!this.info.checkOutgoingPacket()) throw CancelEncoderException.generate(null); 
/* 40 */     if (!this.info.shouldTransformPacket()) {
/* 41 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 47 */       this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
/* 48 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 50 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 56 */     if (cause instanceof com.viaversion.viaversion.exception.CancelCodecException)
/* 57 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\handlers\VelocityEncodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */