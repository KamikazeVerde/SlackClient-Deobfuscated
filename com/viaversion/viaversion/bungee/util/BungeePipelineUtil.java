/*    */ package com.viaversion.viaversion.bungee.util;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
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
/*    */ public class BungeePipelineUtil
/*    */ {
/*    */   private static Method DECODE_METHOD;
/*    */   private static Method ENCODE_METHOD;
/*    */   
/*    */   static {
/*    */     try {
/* 35 */       DECODE_METHOD = MessageToMessageDecoder.class.getDeclaredMethod("decode", new Class[] { ChannelHandlerContext.class, Object.class, List.class });
/* 36 */       DECODE_METHOD.setAccessible(true);
/* 37 */     } catch (NoSuchMethodException e) {
/* 38 */       e.printStackTrace();
/*    */     } 
/*    */     try {
/* 41 */       ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", new Class[] { ChannelHandlerContext.class, Object.class, ByteBuf.class });
/* 42 */       ENCODE_METHOD.setAccessible(true);
/* 43 */     } catch (NoSuchMethodException e) {
/* 44 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static List<Object> callDecode(MessageToMessageDecoder decoder, ChannelHandlerContext ctx, ByteBuf input) throws InvocationTargetException {
/* 49 */     List<Object> output = new ArrayList();
/*    */     try {
/* 51 */       DECODE_METHOD.invoke(decoder, new Object[] { ctx, input, output });
/* 52 */     } catch (IllegalAccessException e) {
/* 53 */       e.printStackTrace();
/*    */     } 
/* 55 */     return output;
/*    */   }
/*    */   
/*    */   public static ByteBuf callEncode(MessageToByteEncoder encoder, ChannelHandlerContext ctx, ByteBuf input) throws InvocationTargetException {
/* 59 */     ByteBuf output = ctx.alloc().buffer();
/*    */     try {
/* 61 */       ENCODE_METHOD.invoke(encoder, new Object[] { ctx, input, output });
/* 62 */     } catch (IllegalAccessException e) {
/* 63 */       e.printStackTrace();
/*    */     } 
/* 65 */     return output;
/*    */   }
/*    */   
/*    */   public static ByteBuf decompress(ChannelHandlerContext ctx, ByteBuf bytebuf) {
/*    */     try {
/* 70 */       return (ByteBuf)callDecode((MessageToMessageDecoder)ctx.pipeline().get("decompress"), ctx.pipeline().context("decompress"), bytebuf).get(0);
/* 71 */     } catch (InvocationTargetException e) {
/* 72 */       e.printStackTrace();
/* 73 */       return ctx.alloc().buffer();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ByteBuf compress(ChannelHandlerContext ctx, ByteBuf bytebuf) {
/*    */     try {
/* 79 */       return callEncode((MessageToByteEncoder)ctx.pipeline().get("compress"), ctx.pipeline().context("compress"), bytebuf);
/* 80 */     } catch (InvocationTargetException e) {
/* 81 */       e.printStackTrace();
/* 82 */       return ctx.alloc().buffer();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bunge\\util\BungeePipelineUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */