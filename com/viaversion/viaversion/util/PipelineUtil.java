/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PipelineUtil
/*     */ {
/*     */   private static final Method DECODE_METHOD;
/*     */   private static final Method ENCODE_METHOD;
/*     */   private static final Method MTM_DECODE;
/*     */   
/*     */   static {
/*     */     try {
/*  44 */       DECODE_METHOD = ByteToMessageDecoder.class.getDeclaredMethod("decode", new Class[] { ChannelHandlerContext.class, ByteBuf.class, List.class });
/*  45 */       DECODE_METHOD.setAccessible(true);
/*  46 */       ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", new Class[] { ChannelHandlerContext.class, Object.class, ByteBuf.class });
/*  47 */       ENCODE_METHOD.setAccessible(true);
/*  48 */       MTM_DECODE = MessageToMessageDecoder.class.getDeclaredMethod("decode", new Class[] { ChannelHandlerContext.class, Object.class, List.class });
/*  49 */       MTM_DECODE.setAccessible(true);
/*  50 */     } catch (NoSuchMethodException e) {
/*  51 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
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
/*     */   public static List<Object> callDecode(ByteToMessageDecoder decoder, ChannelHandlerContext ctx, Object input) throws InvocationTargetException {
/*  65 */     List<Object> output = new ArrayList();
/*     */     try {
/*  67 */       DECODE_METHOD.invoke(decoder, new Object[] { ctx, input, output });
/*  68 */     } catch (IllegalAccessException e) {
/*  69 */       e.printStackTrace();
/*     */     } 
/*  71 */     return output;
/*     */   }
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
/*     */   public static void callEncode(MessageToByteEncoder encoder, ChannelHandlerContext ctx, Object msg, ByteBuf output) throws InvocationTargetException {
/*     */     try {
/*  85 */       ENCODE_METHOD.invoke(encoder, new Object[] { ctx, msg, output });
/*  86 */     } catch (IllegalAccessException e) {
/*  87 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<Object> callDecode(MessageToMessageDecoder decoder, ChannelHandlerContext ctx, Object msg) throws InvocationTargetException {
/*  92 */     List<Object> output = new ArrayList();
/*     */     try {
/*  94 */       MTM_DECODE.invoke(decoder, new Object[] { ctx, msg, output });
/*  95 */     } catch (IllegalAccessException e) {
/*  96 */       e.printStackTrace();
/*     */     } 
/*  98 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsCause(Throwable t, Class<?> c) {
/* 109 */     while (t != null) {
/* 110 */       if (c.isAssignableFrom(t.getClass())) {
/* 111 */         return true;
/*     */       }
/*     */       
/* 114 */       t = t.getCause();
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T getCause(Throwable t, Class<T> c) {
/* 127 */     while (t != null) {
/* 128 */       if (c.isAssignableFrom(t.getClass()))
/*     */       {
/* 130 */         return (T)t;
/*     */       }
/*     */       
/* 133 */       t = t.getCause();
/*     */     } 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChannelHandlerContext getContextBefore(String name, ChannelPipeline pipeline) {
/* 146 */     boolean mark = false;
/* 147 */     for (String s : pipeline.names()) {
/* 148 */       if (mark) {
/* 149 */         return pipeline.context(pipeline.get(s));
/*     */       }
/* 151 */       if (s.equalsIgnoreCase(name))
/* 152 */         mark = true; 
/*     */     } 
/* 154 */     return null;
/*     */   }
/*     */   
/*     */   public static ChannelHandlerContext getPreviousContext(String name, ChannelPipeline pipeline) {
/* 158 */     String previous = null;
/* 159 */     for (String entry : pipeline.toMap().keySet()) {
/* 160 */       if (entry.equals(name)) {
/* 161 */         return pipeline.context(previous);
/*     */       }
/* 163 */       previous = entry;
/*     */     } 
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\PipelineUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */