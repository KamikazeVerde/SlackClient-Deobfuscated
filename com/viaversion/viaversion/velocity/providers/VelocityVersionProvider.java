/*    */ package com.viaversion.viaversion.velocity.providers;
/*    */ 
/*    */ import com.velocitypowered.api.network.ProtocolVersion;
/*    */ import com.velocitypowered.api.proxy.ServerConnection;
/*    */ import com.viaversion.viaversion.VelocityPlugin;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
/*    */ import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ import java.util.stream.IntStream;
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
/*    */ public class VelocityVersionProvider
/*    */   extends BaseVersionProvider
/*    */ {
/*    */   private static Method getAssociation;
/*    */   
/*    */   static {
/*    */     try {
/* 37 */       getAssociation = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection").getMethod("getAssociation", new Class[0]);
/* 38 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/* 39 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getClosestServerProtocol(UserConnection user) throws Exception {
/* 45 */     return user.isClientSide() ? getBackProtocol(user) : getFrontProtocol(user);
/*    */   }
/*    */ 
/*    */   
/*    */   private int getBackProtocol(UserConnection user) throws Exception {
/* 50 */     ChannelHandler mcHandler = user.getChannel().pipeline().get("handler");
/* 51 */     return Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(((ServerConnection)getAssociation
/* 52 */         .invoke(mcHandler, new Object[0])).getServerInfo().getName());
/*    */   }
/*    */   
/*    */   private int getFrontProtocol(UserConnection user) throws Exception {
/* 56 */     int playerVersion = user.getProtocolInfo().getProtocolVersion();
/*    */ 
/*    */     
/* 59 */     IntStream versions = ProtocolVersion.SUPPORTED_VERSIONS.stream().mapToInt(ProtocolVersion::getProtocol);
/*    */ 
/*    */     
/* 62 */     if (VelocityViaInjector.getPlayerInfoForwardingMode != null && ((Enum)VelocityViaInjector.getPlayerInfoForwardingMode
/* 63 */       .invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0]))
/* 64 */       .name().equals("MODERN")) {
/* 65 */       versions = versions.filter(ver -> (ver >= ProtocolVersion.v1_13.getVersion()));
/*    */     }
/* 67 */     int[] compatibleProtocols = versions.toArray();
/*    */ 
/*    */     
/* 70 */     if (Arrays.binarySearch(compatibleProtocols, playerVersion) >= 0) {
/* 71 */       return playerVersion;
/*    */     }
/*    */     
/* 74 */     if (playerVersion < compatibleProtocols[0]) {
/* 75 */       return compatibleProtocols[0];
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     for (int i = compatibleProtocols.length - 1; i >= 0; i--) {
/* 83 */       int protocol = compatibleProtocols[i];
/* 84 */       if (playerVersion > protocol && ProtocolVersion.isRegistered(protocol)) {
/* 85 */         return protocol;
/*    */       }
/*    */     } 
/* 88 */     Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + playerVersion);
/* 89 */     return playerVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\providers\VelocityVersionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */