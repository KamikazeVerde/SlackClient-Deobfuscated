/*    */ package com.viaversion.viaversion.bukkit.handlers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.bukkit.platform.PaperViaInjector;
/*    */ import com.viaversion.viaversion.connection.UserConnectionImpl;
/*    */ import com.viaversion.viaversion.platform.WrappedChannelInitializer;
/*    */ import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import io.netty.channel.ChannelPipeline;
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
/*    */ public final class BukkitChannelInitializer
/*    */   extends ChannelInitializer<Channel>
/*    */   implements WrappedChannelInitializer
/*    */ {
/*    */   public static final String VIA_ENCODER = "via-encoder";
/*    */   public static final String VIA_DECODER = "via-decoder";
/*    */   public static final String MINECRAFT_ENCODER = "encoder";
/*    */   public static final String MINECRAFT_DECODER = "decoder";
/*    */   public static final String MINECRAFT_COMPRESSOR = "compress";
/*    */   public static final String MINECRAFT_DECOMPRESSOR = "decompress";
/* 39 */   public static final Object COMPRESSION_ENABLED_EVENT = paperCompressionEnabledEvent();
/*    */   
/*    */   private static final Method INIT_CHANNEL_METHOD;
/*    */   private final ChannelInitializer<Channel> original;
/*    */   
/*    */   static {
/*    */     try {
/* 46 */       INIT_CHANNEL_METHOD = ChannelInitializer.class.getDeclaredMethod("initChannel", new Class[] { Channel.class });
/* 47 */       INIT_CHANNEL_METHOD.setAccessible(true);
/* 48 */     } catch (ReflectiveOperationException e) {
/* 49 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static Object paperCompressionEnabledEvent() {
/*    */     try {
/* 55 */       Class<?> eventClass = Class.forName("io.papermc.paper.network.ConnectionEvent");
/* 56 */       return eventClass.getDeclaredField("COMPRESSION_THRESHOLD_SET").get(null);
/* 57 */     } catch (ReflectiveOperationException e) {
/* 58 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public BukkitChannelInitializer(ChannelInitializer<Channel> oldInit) {
/* 63 */     this.original = oldInit;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public ChannelInitializer<Channel> getOriginal() {
/* 68 */     return this.original;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initChannel(Channel channel) throws Exception {
/* 74 */     INIT_CHANNEL_METHOD.invoke(this.original, new Object[] { channel });
/* 75 */     afterChannelInitialize(channel);
/*    */   }
/*    */   
/*    */   public static void afterChannelInitialize(Channel channel) {
/* 79 */     UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
/* 80 */     new ProtocolPipelineImpl((UserConnection)userConnectionImpl);
/*    */     
/* 82 */     if (PaperViaInjector.PAPER_PACKET_LIMITER) {
/* 83 */       userConnectionImpl.setPacketLimiterEnabled(false);
/*    */     }
/*    */ 
/*    */     
/* 87 */     ChannelPipeline pipeline = channel.pipeline();
/* 88 */     pipeline.addBefore("encoder", "via-encoder", (ChannelHandler)new BukkitEncodeHandler((UserConnection)userConnectionImpl));
/* 89 */     pipeline.addBefore("decoder", "via-decoder", (ChannelHandler)new BukkitDecodeHandler((UserConnection)userConnectionImpl));
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelInitializer<Channel> original() {
/* 94 */     return this.original;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\handlers\BukkitChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */