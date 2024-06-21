/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import de.gerrygames.viarewind.netty.EmptyChannelHandler;
/*     */ import de.gerrygames.viarewind.netty.ForwardMessageToByteEncoder;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.CompressionSendStorage;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
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
/*     */ public class CompressionHandlerProvider
/*     */   implements Provider {
/*     */   public void handleSetCompression(UserConnection user, int threshold) {
/*  25 */     ChannelPipeline pipeline = user.getChannel().pipeline();
/*  26 */     if (user.isClientSide()) {
/*  27 */       pipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", getEncoder(threshold));
/*  28 */       pipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", getDecoder(threshold));
/*     */     } else {
/*  30 */       CompressionSendStorage storage = (CompressionSendStorage)user.get(CompressionSendStorage.class);
/*  31 */       storage.setRemoveCompression(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleTransform(UserConnection user) {
/*  36 */     CompressionSendStorage storage = (CompressionSendStorage)user.get(CompressionSendStorage.class);
/*  37 */     if (storage.isRemoveCompression()) {
/*  38 */       ChannelPipeline pipeline = user.getChannel().pipeline();
/*     */       
/*  40 */       String compressor = null;
/*  41 */       String decompressor = null;
/*  42 */       if (pipeline.get("compress") != null) {
/*  43 */         compressor = "compress";
/*  44 */         decompressor = "decompress";
/*  45 */       } else if (pipeline.get("compression-encoder") != null) {
/*  46 */         compressor = "compression-encoder";
/*  47 */         decompressor = "compression-decoder";
/*     */       } 
/*  49 */       if (compressor != null) {
/*  50 */         pipeline.replace(decompressor, decompressor, (ChannelHandler)new EmptyChannelHandler());
/*  51 */         pipeline.replace(compressor, compressor, (ChannelHandler)new ForwardMessageToByteEncoder());
/*     */       } else {
/*  53 */         throw new IllegalStateException("Couldn't remove compression for 1.7!");
/*     */       } 
/*     */       
/*  56 */       storage.setRemoveCompression(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ChannelHandler getEncoder(int threshold) {
/*  61 */     return (ChannelHandler)new Compressor(threshold);
/*     */   }
/*     */   
/*     */   protected ChannelHandler getDecoder(int threshold) {
/*  65 */     return (ChannelHandler)new Decompressor(threshold);
/*     */   }
/*     */   
/*     */   private static class Decompressor
/*     */     extends MessageToMessageDecoder<ByteBuf> {
/*     */     private final Inflater inflater;
/*     */     private final int threshold;
/*     */     
/*     */     public Decompressor(int var1) {
/*  74 */       this.threshold = var1;
/*  75 */       this.inflater = new Inflater();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  80 */       if (!in.isReadable())
/*     */         return; 
/*  82 */       int outLength = Type.VAR_INT.readPrimitive(in);
/*  83 */       if (outLength == 0) {
/*  84 */         out.add(in.readBytes(in.readableBytes()));
/*     */         
/*     */         return;
/*     */       } 
/*  88 */       if (outLength < this.threshold)
/*  89 */         throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + this.threshold); 
/*  90 */       if (outLength > 2097152) {
/*  91 */         throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of " + 2097152);
/*     */       }
/*     */       
/*  94 */       ByteBuf temp = in;
/*  95 */       if (!in.hasArray()) {
/*  96 */         temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
/*     */       } else {
/*  98 */         in.retain();
/*     */       } 
/* 100 */       ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer(outLength, outLength);
/*     */       try {
/* 102 */         this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
/* 103 */         output.writerIndex(output.writerIndex() + this.inflater.inflate(output
/* 104 */               .array(), output.arrayOffset(), outLength));
/* 105 */         out.add(output.retain());
/*     */       } finally {
/* 107 */         output.release();
/* 108 */         temp.release();
/* 109 */         this.inflater.reset();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Compressor
/*     */     extends MessageToByteEncoder<ByteBuf> {
/*     */     private final Deflater deflater;
/*     */     private final int threshold;
/*     */     
/*     */     public Compressor(int var1) {
/* 120 */       this.threshold = var1;
/* 121 */       this.deflater = new Deflater();
/*     */     }
/*     */     
/*     */     protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 125 */       int frameLength = in.readableBytes();
/* 126 */       if (frameLength < this.threshold) {
/* 127 */         out.writeByte(0);
/* 128 */         out.writeBytes(in);
/*     */         
/*     */         return;
/*     */       } 
/* 132 */       Type.VAR_INT.writePrimitive(out, frameLength);
/*     */       
/* 134 */       ByteBuf temp = in;
/* 135 */       if (!in.hasArray()) {
/* 136 */         temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
/*     */       } else {
/* 138 */         in.retain();
/*     */       } 
/* 140 */       ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer();
/*     */       try {
/* 142 */         this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
/* 143 */         this.deflater.finish();
/*     */         
/* 145 */         while (!this.deflater.finished()) {
/* 146 */           output.ensureWritable(4096);
/* 147 */           output.writerIndex(output.writerIndex() + this.deflater.deflate(output.array(), output
/* 148 */                 .arrayOffset() + output.writerIndex(), output.writableBytes()));
/*     */         } 
/* 150 */         out.writeBytes(output);
/*     */       } finally {
/* 152 */         output.release();
/* 153 */         temp.release();
/* 154 */         this.deflater.reset();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\provider\CompressionHandlerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */