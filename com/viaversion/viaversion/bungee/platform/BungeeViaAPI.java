/*    */ package com.viaversion.viaversion.bungee.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaAPIBase;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.md_5.bungee.api.config.ServerInfo;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*    */ 
/*    */ public class BungeeViaAPI
/*    */   extends ViaAPIBase<ProxiedPlayer>
/*    */ {
/*    */   public int getPlayerVersion(ProxiedPlayer player) {
/* 31 */     return getPlayerVersion(player.getUniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(ProxiedPlayer player, ByteBuf packet) throws IllegalArgumentException {
/* 36 */     sendRawPacket(player.getUniqueId(), packet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void probeServer(ServerInfo serverInfo) {
/* 45 */     ((ProtocolDetectorService)Via.proxyPlatform().protocolDetectorService()).probeServer(serverInfo);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\platform\BungeeViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */