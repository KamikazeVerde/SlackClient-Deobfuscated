/*    */ package com.viaversion.viaversion.bukkit.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*    */ import com.viaversion.viaversion.exception.CancelCodecException;
/*    */ import com.viaversion.viaversion.exception.CancelDecoderException;
/*    */ import com.viaversion.viaversion.exception.InformativeException;
/*    */ import com.viaversion.viaversion.util.PipelineUtil;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public final class BukkitDecodeHandler
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection connection;
/*    */   
/*    */   public BukkitDecodeHandler(UserConnection connection) {
/* 38 */     this.connection = connection;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 43 */     if (!this.connection.checkServerboundPacket()) {
/* 44 */       throw CancelDecoderException.generate(null);
/*    */     }
/* 46 */     if (!this.connection.shouldTransformPacket()) {
/* 47 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 53 */       this.connection.transformIncoming(transformedBuf, CancelDecoderException::generate);
/* 54 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 56 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 62 */     if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
/*    */       return;
/*    */     }
/*    */     
/* 66 */     super.exceptionCaught(ctx, cause);
/* 67 */     if (NMSUtil.isDebugPropertySet()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 72 */     InformativeException exception = (InformativeException)PipelineUtil.getCause(cause, InformativeException.class);
/* 73 */     if (exception != null && exception.shouldBePrinted()) {
/* 74 */       cause.printStackTrace();
/* 75 */       exception.setShouldBePrinted(false);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
/* 81 */     if (BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT == null || event != BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT) {
/* 82 */       super.userEventTriggered(ctx, event);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 87 */     ChannelPipeline pipeline = ctx.pipeline();
/* 88 */     pipeline.addAfter("compress", "via-encoder", pipeline.remove("via-encoder"));
/* 89 */     pipeline.addAfter("decompress", "via-decoder", pipeline.remove("via-decoder"));
/* 90 */     super.userEventTriggered(ctx, event);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\handlers\BukkitDecodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */