/*     */ package com.viaversion.viaversion.handlers;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelProgressivePromise;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import java.net.SocketAddress;
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
/*     */ public class ChannelHandlerContextWrapper
/*     */   implements ChannelHandlerContext
/*     */ {
/*     */   private final ChannelHandlerContext base;
/*     */   private final ViaCodecHandler handler;
/*     */   
/*     */   public ChannelHandlerContextWrapper(ChannelHandlerContext base, ViaCodecHandler handler) {
/*  39 */     this.base = base;
/*  40 */     this.handler = handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel channel() {
/*  45 */     return this.base.channel();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor executor() {
/*  50 */     return this.base.executor();
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  55 */     return this.base.name();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandler handler() {
/*  60 */     return this.base.handler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRemoved() {
/*  65 */     return this.base.isRemoved();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelRegistered() {
/*  70 */     this.base.fireChannelRegistered();
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelUnregistered() {
/*  76 */     this.base.fireChannelUnregistered();
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelActive() {
/*  82 */     this.base.fireChannelActive();
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelInactive() {
/*  88 */     this.base.fireChannelInactive();
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireExceptionCaught(Throwable throwable) {
/*  94 */     this.base.fireExceptionCaught(throwable);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireUserEventTriggered(Object o) {
/* 100 */     this.base.fireUserEventTriggered(o);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelRead(Object o) {
/* 106 */     this.base.fireChannelRead(o);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelReadComplete() {
/* 112 */     this.base.fireChannelReadComplete();
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext fireChannelWritabilityChanged() {
/* 118 */     this.base.fireChannelWritabilityChanged();
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress socketAddress) {
/* 124 */     return this.base.bind(socketAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress socketAddress) {
/* 129 */     return this.base.connect(socketAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1) {
/* 134 */     return this.base.connect(socketAddress, socketAddress1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect() {
/* 139 */     return this.base.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 144 */     return this.base.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister() {
/* 149 */     return this.base.deregister();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
/* 154 */     return this.base.bind(socketAddress, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
/* 159 */     return this.base.connect(socketAddress, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) {
/* 164 */     return this.base.connect(socketAddress, socketAddress1, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture disconnect(ChannelPromise channelPromise) {
/* 169 */     return this.base.disconnect(channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(ChannelPromise channelPromise) {
/* 174 */     return this.base.close(channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture deregister(ChannelPromise channelPromise) {
/* 179 */     return this.base.deregister(channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext read() {
/* 184 */     this.base.read();
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object o) {
/* 190 */     if (o instanceof ByteBuf && 
/* 191 */       transform((ByteBuf)o)) return this.base.newFailedFuture(new Throwable());
/*     */     
/* 193 */     return this.base.write(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture write(Object o, ChannelPromise channelPromise) {
/* 198 */     if (o instanceof ByteBuf && 
/* 199 */       transform((ByteBuf)o)) return this.base.newFailedFuture(new Throwable());
/*     */     
/* 201 */     return this.base.write(o, channelPromise);
/*     */   }
/*     */   
/*     */   public boolean transform(ByteBuf buf) {
/*     */     try {
/* 206 */       this.handler.transform(buf);
/* 207 */       return false;
/* 208 */     } catch (Exception e) {
/*     */       try {
/* 210 */         this.handler.exceptionCaught(this.base, e);
/* 211 */       } catch (Exception e1) {
/* 212 */         this.base.fireExceptionCaught(e1);
/*     */       } 
/* 214 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelHandlerContext flush() {
/* 220 */     this.base.flush();
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
/* 226 */     ChannelFuture future = write(o, channelPromise);
/* 227 */     flush();
/* 228 */     return future;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object o) {
/* 233 */     ChannelFuture future = write(o);
/* 234 */     flush();
/* 235 */     return future;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPipeline pipeline() {
/* 240 */     return this.base.pipeline();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 245 */     return this.base.alloc();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPromise newPromise() {
/* 250 */     return this.base.newPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise() {
/* 255 */     return this.base.newProgressivePromise();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newSucceededFuture() {
/* 260 */     return this.base.newSucceededFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable throwable) {
/* 265 */     return this.base.newFailedFuture(throwable);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelPromise voidPromise() {
/* 270 */     return this.base.voidPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
/* 275 */     return this.base.attr(attributeKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\handlers\ChannelHandlerContextWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */