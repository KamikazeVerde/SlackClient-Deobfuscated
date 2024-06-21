/*    */ package com.viaversion.viaversion.sponge.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.exception.CancelDecoderException;
/*    */ import com.viaversion.viaversion.util.PipelineUtil;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ 
/*    */ public class SpongeDecodeHandler
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   private final ByteToMessageDecoder minecraftDecoder;
/*    */   private final UserConnection info;
/*    */   
/*    */   public SpongeDecodeHandler(UserConnection info, ByteToMessageDecoder minecraftDecoder) {
/* 36 */     this.info = info;
/* 37 */     this.minecraftDecoder = minecraftDecoder;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> list) throws Exception {
/* 42 */     if (!this.info.checkServerboundPacket()) {
/* 43 */       bytebuf.clear();
/* 44 */       throw CancelDecoderException.generate(null);
/*    */     } 
/*    */     
/* 47 */     ByteBuf transformedBuf = null;
/*    */     try {
/* 49 */       if (this.info.shouldTransformPacket()) {
/* 50 */         transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/* 51 */         this.info.transformServerbound(transformedBuf, CancelDecoderException::generate);
/*    */       } 
/*    */       
/*    */       try {
/* 55 */         list.addAll(PipelineUtil.callDecode(this.minecraftDecoder, ctx, (transformedBuf == null) ? bytebuf : transformedBuf));
/* 56 */       } catch (InvocationTargetException e) {
/* 57 */         if (e.getCause() instanceof Exception)
/* 58 */           throw (Exception)e.getCause(); 
/* 59 */         if (e.getCause() instanceof Error) {
/* 60 */           throw (Error)e.getCause();
/*    */         }
/*    */       } 
/*    */     } finally {
/* 64 */       if (transformedBuf != null) {
/* 65 */         transformedBuf.release();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 72 */     if (cause instanceof com.viaversion.viaversion.exception.CancelCodecException)
/* 73 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\handlers\SpongeDecodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */