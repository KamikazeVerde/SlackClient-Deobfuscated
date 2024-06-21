/*     */ package com.viaversion.viaversion.bukkit.handlers;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*     */ import com.viaversion.viaversion.exception.CancelCodecException;
/*     */ import com.viaversion.viaversion.exception.CancelEncoderException;
/*     */ import com.viaversion.viaversion.exception.InformativeException;
/*     */ import com.viaversion.viaversion.util.PipelineUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public final class BukkitEncodeHandler
/*     */   extends MessageToMessageEncoder<ByteBuf>
/*     */ {
/*     */   private final UserConnection connection;
/*  38 */   private boolean handledCompression = (BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT != null);
/*     */   
/*     */   public BukkitEncodeHandler(UserConnection connection) {
/*  41 */     this.connection = connection;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/*  46 */     if (!this.connection.checkClientboundPacket()) {
/*  47 */       throw CancelEncoderException.generate(null);
/*     */     }
/*  49 */     if (!this.connection.shouldTransformPacket()) {
/*  50 */       out.add(bytebuf.retain());
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*     */     try {
/*  56 */       boolean needsCompression = (!this.handledCompression && handleCompressionOrder(ctx, transformedBuf));
/*  57 */       this.connection.transformClientbound(transformedBuf, CancelEncoderException::generate);
/*  58 */       if (needsCompression) {
/*  59 */         recompress(ctx, transformedBuf);
/*     */       }
/*     */       
/*  62 */       out.add(transformedBuf.retain());
/*     */     } finally {
/*  64 */       transformedBuf.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
/*  69 */     ChannelPipeline pipeline = ctx.pipeline();
/*  70 */     List<String> names = pipeline.names();
/*  71 */     int compressorIndex = names.indexOf("compress");
/*  72 */     if (compressorIndex == -1) {
/*  73 */       return false;
/*     */     }
/*     */     
/*  76 */     this.handledCompression = true;
/*  77 */     if (compressorIndex > names.indexOf("via-encoder")) {
/*     */       
/*  79 */       ByteBuf decompressed = PipelineUtil.callDecode((ByteToMessageDecoder)pipeline.get("decompress"), ctx, buf).get(0);
/*     */       try {
/*  81 */         buf.clear().writeBytes(decompressed);
/*     */       } finally {
/*  83 */         decompressed.release();
/*     */       } 
/*     */       
/*  86 */       pipeline.addAfter("compress", "via-encoder", pipeline.remove("via-encoder"));
/*  87 */       pipeline.addAfter("decompress", "via-decoder", pipeline.remove("via-decoder"));
/*  88 */       return true;
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   private void recompress(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
/*  94 */     ByteBuf compressed = ctx.alloc().buffer();
/*     */     try {
/*  96 */       PipelineUtil.callEncode((MessageToByteEncoder)ctx.pipeline().get("compress"), ctx, buf, compressed);
/*  97 */       buf.clear().writeBytes(compressed);
/*     */     } finally {
/*  99 */       compressed.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 105 */     if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     super.exceptionCaught(ctx, cause);
/* 110 */     if (NMSUtil.isDebugPropertySet()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 115 */     InformativeException exception = (InformativeException)PipelineUtil.getCause(cause, InformativeException.class);
/* 116 */     if (exception != null && exception.shouldBePrinted()) {
/* 117 */       cause.printStackTrace();
/* 118 */       exception.setShouldBePrinted(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public UserConnection connection() {
/* 123 */     return this.connection;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\handlers\BukkitEncodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */