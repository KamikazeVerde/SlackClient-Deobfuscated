/*    */ package com.viaversion.viaversion.sponge.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.exception.CancelEncoderException;
/*    */ import com.viaversion.viaversion.handlers.ChannelHandlerContextWrapper;
/*    */ import com.viaversion.viaversion.handlers.ViaCodecHandler;
/*    */ import com.viaversion.viaversion.util.PipelineUtil;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ public class SpongeEncodeHandler
/*    */   extends MessageToByteEncoder<Object>
/*    */   implements ViaCodecHandler
/*    */ {
/*    */   private final UserConnection info;
/*    */   private final MessageToByteEncoder<?> minecraftEncoder;
/*    */   
/*    */   public SpongeEncodeHandler(UserConnection info, MessageToByteEncoder<?> minecraftEncoder) {
/* 36 */     this.info = info;
/* 37 */     this.minecraftEncoder = minecraftEncoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf bytebuf) throws Exception {
/* 43 */     if (!(o instanceof ByteBuf)) {
/*    */       
/*    */       try {
/* 46 */         PipelineUtil.callEncode(this.minecraftEncoder, (ChannelHandlerContext)new ChannelHandlerContextWrapper(ctx, this), o, bytebuf);
/* 47 */       } catch (InvocationTargetException e) {
/* 48 */         if (e.getCause() instanceof Exception)
/* 49 */           throw (Exception)e.getCause(); 
/* 50 */         if (e.getCause() instanceof Error) {
/* 51 */           throw (Error)e.getCause();
/*    */         }
/*    */       } 
/*    */     } else {
/* 55 */       bytebuf.writeBytes((ByteBuf)o);
/*    */     } 
/* 57 */     transform(bytebuf);
/*    */   }
/*    */ 
/*    */   
/*    */   public void transform(ByteBuf bytebuf) throws Exception {
/* 62 */     if (!this.info.checkClientboundPacket()) throw CancelEncoderException.generate(null); 
/* 63 */     if (!this.info.shouldTransformPacket())
/* 64 */       return;  this.info.transformClientbound(bytebuf, CancelEncoderException::generate);
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 69 */     if (cause instanceof com.viaversion.viaversion.exception.CancelCodecException)
/* 70 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\handlers\SpongeEncodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */