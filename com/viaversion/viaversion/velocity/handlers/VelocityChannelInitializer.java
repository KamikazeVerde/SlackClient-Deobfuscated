/*    */ package com.viaversion.viaversion.velocity.handlers;
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
/*    */ 
/*    */ public class VelocityChannelInitializer
/*    */   extends ChannelInitializer<Channel>
/*    */ {
/*    */   public static final String MINECRAFT_ENCODER = "minecraft-encoder";
/*    */   public static final String MINECRAFT_DECODER = "minecraft-decoder";
/*    */   public static final String VIA_ENCODER = "via-encoder";
/*    */   public static final String VIA_DECODER = "via-decoder";
/*    */   public static final Object COMPRESSION_ENABLED_EVENT;
/*    */   private static final Method INIT_CHANNEL;
/*    */   private final ChannelInitializer<?> original;
/*    */   private final boolean clientSide;
/*    */   
/*    */   static {
/*    */     try {
/* 40 */       INIT_CHANNEL = ChannelInitializer.class.getDeclaredMethod("initChannel", new Class[] { Channel.class });
/* 41 */       INIT_CHANNEL.setAccessible(true);
/*    */       
/* 43 */       Class<?> eventClass = Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
/* 44 */       COMPRESSION_ENABLED_EVENT = eventClass.getDeclaredField("COMPRESSION_ENABLED").get(null);
/* 45 */     } catch (ReflectiveOperationException e) {
/* 46 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public VelocityChannelInitializer(ChannelInitializer<?> original, boolean clientSide) {
/* 51 */     this.original = original;
/* 52 */     this.clientSide = clientSide;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initChannel(Channel channel) throws Exception {
/* 57 */     INIT_CHANNEL.invoke(this.original, new Object[] { channel });
/*    */     
/* 59 */     UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel, this.clientSide);
/* 60 */     new ProtocolPipelineImpl((UserConnection)userConnectionImpl);
/*    */ 
/*    */     
/* 63 */     channel.pipeline().addBefore("minecraft-encoder", "via-encoder", (ChannelHandler)new VelocityEncodeHandler((UserConnection)userConnectionImpl));
/* 64 */     channel.pipeline().addBefore("minecraft-decoder", "via-decoder", (ChannelHandler)new VelocityDecodeHandler((UserConnection)userConnectionImpl));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\handlers\VelocityChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */