/*    */ package com.viaversion.viaversion.bungee.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.exception.CancelDecoderException;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
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
/*    */ 
/*    */ @Sharable
/*    */ public class BungeeDecodeHandler
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection info;
/*    */   
/*    */   public BungeeDecodeHandler(UserConnection info) {
/* 34 */     this.info = info;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 39 */     if (!ctx.channel().isActive()) {
/* 40 */       throw CancelDecoderException.generate(null);
/*    */     }
/*    */     
/* 43 */     if (!this.info.checkServerboundPacket()) throw CancelDecoderException.generate(null); 
/* 44 */     if (!this.info.shouldTransformPacket()) {
/* 45 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 51 */       this.info.transformServerbound(transformedBuf, CancelDecoderException::generate);
/* 52 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 54 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 60 */     if (cause instanceof com.viaversion.viaversion.exception.CancelCodecException)
/* 61 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\handlers\BungeeDecodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */