/*    */ package de.florianmichael.vialoadingbase.netty.handler;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
/*    */ import com.viaversion.viaversion.exception.CancelCodecException;
/*    */ import com.viaversion.viaversion.exception.CancelDecoderException;
/*    */ import com.viaversion.viaversion.exception.InformativeException;
/*    */ import com.viaversion.viaversion.util.PipelineUtil;
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
/*    */ public class VLBViaDecodeHandler
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection user;
/*    */   
/*    */   public VLBViaDecodeHandler(UserConnection user) {
/* 39 */     this.user = user;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 44 */     if (!this.user.checkIncomingPacket()) throw CancelDecoderException.generate(null); 
/* 45 */     if (!this.user.shouldTransformPacket()) {
/* 46 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 52 */       this.user.transformIncoming(transformedBuf, CancelDecoderException::generate);
/*    */       
/* 54 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 56 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 62 */     if (PipelineUtil.containsCause(cause, CancelCodecException.class))
/*    */       return; 
/* 64 */     if ((PipelineUtil.containsCause(cause, InformativeException.class) && 
/* 65 */       this.user.getProtocolInfo().getState() != State.HANDSHAKE) || 
/* 66 */       Via.getManager().debugHandler().enabled())
/* 67 */       cause.printStackTrace(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\netty\handler\VLBViaDecodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */