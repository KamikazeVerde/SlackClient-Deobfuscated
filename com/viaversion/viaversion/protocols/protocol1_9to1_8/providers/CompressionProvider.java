/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import java.util.List;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.Inflater;
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
/*     */ public class CompressionProvider
/*     */   implements Provider
/*     */ {
/*     */   public void handlePlayCompression(UserConnection user, int threshold) {
/*  37 */     if (!user.isClientSide()) throw new IllegalStateException("PLAY state Compression packet is unsupported");
/*     */     
/*  39 */     ChannelPipeline pipe = user.getChannel().pipeline();
/*     */     
/*  41 */     if (threshold < 0) {
/*  42 */       if (pipe.get("compress") != null) {
/*  43 */         pipe.remove("compress");
/*  44 */         pipe.remove("decompress");
/*     */       }
/*     */     
/*  47 */     } else if (pipe.get("compress") == null) {
/*  48 */       pipe.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", getEncoder(threshold));
/*  49 */       pipe.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", getDecoder(threshold));
/*     */     } else {
/*  51 */       ((CompressionHandler)pipe.get("compress")).setCompressionThreshold(threshold);
/*  52 */       ((CompressionHandler)pipe.get("decompress")).setCompressionThreshold(threshold);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected CompressionHandler getEncoder(int threshold) {
/*  58 */     return new Compressor(threshold);
/*     */   }
/*     */   
/*     */   protected CompressionHandler getDecoder(int threshold) {
/*  62 */     return new Decompressor(threshold);
/*     */   }
/*     */   
/*     */   public static interface CompressionHandler
/*     */     extends ChannelHandler {
/*     */     void setCompressionThreshold(int param1Int);
/*     */   }
/*     */   
/*     */   private static class Decompressor extends MessageToMessageDecoder<ByteBuf> implements CompressionHandler {
/*     */     private final Inflater inflater;
/*     */     private int threshold;
/*     */     
/*     */     public Decompressor(int var1) {
/*  75 */       this.threshold = var1;
/*  76 */       this.inflater = new Inflater();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  81 */       if (!in.isReadable())
/*     */         return; 
/*  83 */       int outLength = Type.VAR_INT.readPrimitive(in);
/*  84 */       if (outLength == 0) {
/*  85 */         out.add(in.readBytes(in.readableBytes()));
/*     */         
/*     */         return;
/*     */       } 
/*  89 */       if (outLength < this.threshold)
/*  90 */         throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + this.threshold); 
/*  91 */       if (outLength > 2097152) {
/*  92 */         throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of " + 2097152);
/*     */       }
/*     */       
/*  95 */       ByteBuf temp = in;
/*  96 */       if (!in.hasArray()) {
/*  97 */         temp = ctx.alloc().heapBuffer().writeBytes(in);
/*     */       } else {
/*  99 */         in.retain();
/*     */       } 
/* 101 */       ByteBuf output = ctx.alloc().heapBuffer(outLength, outLength);
/*     */       try {
/* 103 */         this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
/* 104 */         output.writerIndex(output.writerIndex() + this.inflater.inflate(output
/* 105 */               .array(), output.arrayOffset(), outLength));
/* 106 */         out.add(output.retain());
/*     */       } finally {
/* 108 */         output.release();
/* 109 */         temp.release();
/* 110 */         this.inflater.reset();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCompressionThreshold(int threshold) {
/* 116 */       this.threshold = threshold;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Compressor
/*     */     extends MessageToByteEncoder<ByteBuf> implements CompressionHandler {
/*     */     private final Deflater deflater;
/*     */     private int threshold;
/*     */     
/*     */     public Compressor(int var1) {
/* 126 */       this.threshold = var1;
/* 127 */       this.deflater = new Deflater();
/*     */     }
/*     */     
/*     */     protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 131 */       int frameLength = in.readableBytes();
/* 132 */       if (frameLength < this.threshold) {
/* 133 */         out.writeByte(0);
/* 134 */         out.writeBytes(in);
/*     */         
/*     */         return;
/*     */       } 
/* 138 */       Type.VAR_INT.writePrimitive(out, frameLength);
/*     */       
/* 140 */       ByteBuf temp = in;
/* 141 */       if (!in.hasArray()) {
/* 142 */         temp = ctx.alloc().heapBuffer().writeBytes(in);
/*     */       } else {
/* 144 */         in.retain();
/*     */       } 
/* 146 */       ByteBuf output = ctx.alloc().heapBuffer();
/*     */       try {
/* 148 */         this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
/* 149 */         this.deflater.finish();
/*     */         
/* 151 */         while (!this.deflater.finished()) {
/* 152 */           output.ensureWritable(4096);
/* 153 */           output.writerIndex(output.writerIndex() + this.deflater.deflate(output.array(), output
/* 154 */                 .arrayOffset() + output.writerIndex(), output.writableBytes()));
/*     */         } 
/* 156 */         out.writeBytes(output);
/*     */       } finally {
/* 158 */         output.release();
/* 159 */         temp.release();
/* 160 */         this.deflater.reset();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCompressionThreshold(int threshold) {
/* 166 */       this.threshold = threshold;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\providers\CompressionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */