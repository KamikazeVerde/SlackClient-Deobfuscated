/*     */ package com.viaversion.viaversion.bungee.handlers;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.bungee.util.BungeePipelineUtil;
/*     */ import com.viaversion.viaversion.exception.CancelEncoderException;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ public class BungeeEncodeHandler
/*     */   extends MessageToMessageEncoder<ByteBuf>
/*     */ {
/*     */   private final UserConnection info;
/*     */   private boolean handledCompression;
/*     */   
/*     */   public BungeeEncodeHandler(UserConnection info) {
/*  36 */     this.info = info;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/*  41 */     if (!ctx.channel().isActive()) {
/*  42 */       throw CancelEncoderException.generate(null);
/*     */     }
/*     */     
/*  45 */     if (!this.info.checkClientboundPacket()) throw CancelEncoderException.generate(null); 
/*  46 */     if (!this.info.shouldTransformPacket()) {
/*  47 */       out.add(bytebuf.retain());
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*     */     try {
/*  53 */       boolean needsCompress = handleCompressionOrder(ctx, transformedBuf);
/*  54 */       this.info.transformClientbound(transformedBuf, CancelEncoderException::generate);
/*     */       
/*  56 */       if (needsCompress) {
/*  57 */         recompress(ctx, transformedBuf);
/*     */       }
/*     */       
/*  60 */       out.add(transformedBuf.retain());
/*     */     } finally {
/*  62 */       transformedBuf.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) {
/*  67 */     boolean needsCompress = false;
/*  68 */     if (!this.handledCompression && 
/*  69 */       ctx.pipeline().names().indexOf("compress") > ctx.pipeline().names().indexOf("via-encoder")) {
/*     */       
/*  71 */       ByteBuf decompressed = BungeePipelineUtil.decompress(ctx, buf);
/*     */ 
/*     */       
/*  74 */       if (buf != decompressed) {
/*     */         try {
/*  76 */           buf.clear().writeBytes(decompressed);
/*     */         } finally {
/*  78 */           decompressed.release();
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  83 */       ChannelHandler dec = ctx.pipeline().get("via-decoder");
/*  84 */       ChannelHandler enc = ctx.pipeline().get("via-encoder");
/*  85 */       ctx.pipeline().remove(dec);
/*  86 */       ctx.pipeline().remove(enc);
/*  87 */       ctx.pipeline().addAfter("decompress", "via-decoder", dec);
/*  88 */       ctx.pipeline().addAfter("compress", "via-encoder", enc);
/*  89 */       needsCompress = true;
/*  90 */       this.handledCompression = true;
/*     */     } 
/*     */     
/*  93 */     return needsCompress;
/*     */   }
/*     */   
/*     */   private void recompress(ChannelHandlerContext ctx, ByteBuf buf) {
/*  97 */     ByteBuf compressed = BungeePipelineUtil.compress(ctx, buf);
/*     */     try {
/*  99 */       buf.clear().writeBytes(compressed);
/*     */     } finally {
/* 101 */       compressed.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 107 */     if (cause instanceof com.viaversion.viaversion.exception.CancelCodecException)
/* 108 */       return;  super.exceptionCaught(ctx, cause);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\handlers\BungeeEncodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */