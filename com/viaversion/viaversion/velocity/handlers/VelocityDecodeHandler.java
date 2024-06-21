/*    */ package com.viaversion.viaversion.velocity.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.exception.CancelDecoderException;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ @Sharable
/*    */ public class VelocityDecodeHandler
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection info;
/*    */   
/*    */   public VelocityDecodeHandler(UserConnection info) {
/* 35 */     this.info = info;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 40 */     if (!this.info.checkIncomingPacket()) throw CancelDecoderException.generate(null); 
/* 41 */     if (!this.info.shouldTransformPacket()) {
/* 42 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 48 */       this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
/* 49 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 51 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 57 */     if (cause instanceof com.viaversion.viaversion.exception.CancelCodecException)
/* 58 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
/* 64 */     if (event != VelocityChannelInitializer.COMPRESSION_ENABLED_EVENT) {
/* 65 */       super.userEventTriggered(ctx, event);
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 71 */     ChannelPipeline pipeline = ctx.pipeline();
/*    */     
/* 73 */     ChannelHandler encoder = pipeline.get("via-encoder");
/* 74 */     pipeline.remove(encoder);
/* 75 */     pipeline.addBefore("minecraft-encoder", "via-encoder", encoder);
/*    */     
/* 77 */     ChannelHandler decoder = pipeline.get("via-decoder");
/* 78 */     pipeline.remove(decoder);
/* 79 */     pipeline.addBefore("minecraft-decoder", "via-decoder", decoder);
/*    */     
/* 81 */     super.userEventTriggered(ctx, event);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\handlers\VelocityDecodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */