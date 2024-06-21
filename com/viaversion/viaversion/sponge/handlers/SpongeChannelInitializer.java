/*    */ package com.viaversion.viaversion.sponge.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.connection.UserConnectionImpl;
/*    */ import com.viaversion.viaversion.platform.WrappedChannelInitializer;
/*    */ import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*    */ 
/*    */ public class SpongeChannelInitializer
/*    */   extends ChannelInitializer<Channel>
/*    */   implements WrappedChannelInitializer
/*    */ {
/*    */   private static final Method INIT_CHANNEL_METHOD;
/*    */   private final ChannelInitializer<Channel> original;
/*    */   
/*    */   static {
/*    */     try {
/* 39 */       INIT_CHANNEL_METHOD = ChannelInitializer.class.getDeclaredMethod("initChannel", new Class[] { Channel.class });
/* 40 */       INIT_CHANNEL_METHOD.setAccessible(true);
/* 41 */     } catch (NoSuchMethodException e) {
/* 42 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public SpongeChannelInitializer(ChannelInitializer<Channel> oldInit) {
/* 47 */     this.original = oldInit;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initChannel(Channel channel) throws Exception {
/* 53 */     if (Via.getAPI().getServerVersion().isKnown() && channel instanceof io.netty.channel.socket.SocketChannel) {
/*    */       
/* 55 */       UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
/*    */       
/* 57 */       new ProtocolPipelineImpl((UserConnection)userConnectionImpl);
/*    */       
/* 59 */       INIT_CHANNEL_METHOD.invoke(this.original, new Object[] { channel });
/*    */       
/* 61 */       MessageToByteEncoder encoder = new SpongeEncodeHandler((UserConnection)userConnectionImpl, (MessageToByteEncoder)channel.pipeline().get("encoder"));
/* 62 */       ByteToMessageDecoder decoder = new SpongeDecodeHandler((UserConnection)userConnectionImpl, (ByteToMessageDecoder)channel.pipeline().get("decoder"));
/*    */       
/* 64 */       channel.pipeline().replace("encoder", "encoder", (ChannelHandler)encoder);
/* 65 */       channel.pipeline().replace("decoder", "decoder", (ChannelHandler)decoder);
/*    */     } else {
/* 67 */       INIT_CHANNEL_METHOD.invoke(this.original, new Object[] { channel });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelInitializer<Channel> getOriginal() {
/* 73 */     return this.original;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelInitializer<Channel> original() {
/* 78 */     return this.original;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\handlers\SpongeChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */