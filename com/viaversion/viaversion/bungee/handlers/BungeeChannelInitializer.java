/*    */ package com.viaversion.viaversion.bungee.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.connection.UserConnectionImpl;
/*    */ import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class BungeeChannelInitializer
/*    */   extends ChannelInitializer<Channel>
/*    */ {
/*    */   private final ChannelInitializer<Channel> original;
/*    */   private Method method;
/*    */   
/*    */   public BungeeChannelInitializer(ChannelInitializer<Channel> oldInit) {
/* 32 */     this.original = oldInit;
/*    */     try {
/* 34 */       this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", new Class[] { Channel.class });
/* 35 */       this.method.setAccessible(true);
/* 36 */     } catch (NoSuchMethodException e) {
/* 37 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initChannel(Channel socketChannel) throws Exception {
/* 43 */     if (!socketChannel.isActive()) {
/*    */       return;
/*    */     }
/*    */     
/* 47 */     UserConnectionImpl userConnectionImpl = new UserConnectionImpl(socketChannel);
/*    */     
/* 49 */     new ProtocolPipelineImpl((UserConnection)userConnectionImpl);
/*    */     
/* 51 */     this.method.invoke(this.original, new Object[] { socketChannel });
/*    */     
/* 53 */     if (!socketChannel.isActive())
/* 54 */       return;  if (socketChannel.pipeline().get("packet-encoder") == null)
/* 55 */       return;  if (socketChannel.pipeline().get("packet-decoder") == null)
/*    */       return; 
/* 57 */     BungeeEncodeHandler encoder = new BungeeEncodeHandler((UserConnection)userConnectionImpl);
/* 58 */     BungeeDecodeHandler decoder = new BungeeDecodeHandler((UserConnection)userConnectionImpl);
/*    */     
/* 60 */     socketChannel.pipeline().addBefore("packet-encoder", "via-encoder", (ChannelHandler)encoder);
/* 61 */     socketChannel.pipeline().addBefore("packet-decoder", "via-decoder", (ChannelHandler)decoder);
/*    */   }
/*    */   
/*    */   public ChannelInitializer<Channel> getOriginal() {
/* 65 */     return this.original;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\handlers\BungeeChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */