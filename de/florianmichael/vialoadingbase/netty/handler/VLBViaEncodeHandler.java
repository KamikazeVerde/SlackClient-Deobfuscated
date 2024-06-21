/*    */ package de.florianmichael.vialoadingbase.netty.handler;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.exception.CancelCodecException;
/*    */ import com.viaversion.viaversion.exception.CancelEncoderException;
/*    */ import com.viaversion.viaversion.util.PipelineUtil;
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
/*    */ public class VLBViaEncodeHandler
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection user;
/*    */   
/*    */   public VLBViaEncodeHandler(UserConnection user) {
/* 36 */     this.user = user;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 41 */     if (!this.user.checkOutgoingPacket()) throw CancelEncoderException.generate(null); 
/* 42 */     if (!this.user.shouldTransformPacket()) {
/* 43 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 47 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 49 */       this.user.transformOutgoing(transformedBuf, CancelEncoderException::generate);
/*    */       
/* 51 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 53 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 59 */     if (PipelineUtil.containsCause(cause, CancelCodecException.class))
/* 60 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\netty\handler\VLBViaEncodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */