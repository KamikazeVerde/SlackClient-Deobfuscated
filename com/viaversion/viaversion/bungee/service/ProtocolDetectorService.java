/*    */ package com.viaversion.viaversion.bungee.service;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
/*    */ import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
/*    */ import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.ServerPing;
/*    */ import net.md_5.bungee.api.config.ServerInfo;
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
/*    */ public final class ProtocolDetectorService
/*    */   extends AbstractProtocolDetectorService
/*    */ {
/*    */   public void probeServer(ServerInfo serverInfo) {
/* 34 */     String serverName = serverInfo.getName();
/* 35 */     serverInfo.ping((serverPing, throwable) -> {
/*    */           if (throwable != null || serverPing == null || serverPing.getVersion() == null || serverPing.getVersion().getProtocol() <= 0) {
/*    */             return;
/*    */           }
/*    */           int oldProtocolVersion = serverProtocolVersion(serverName);
/*    */           if (oldProtocolVersion == serverPing.getVersion().getProtocol()) {
/*    */             return;
/*    */           }
/*    */           setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
/*    */           if (((BungeeViaConfig)Via.getConfig()).isBungeePingSave()) {
/*    */             Map<String, Integer> servers = ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
/*    */             Integer protocol = servers.get(serverName);
/*    */             if (protocol != null && protocol.intValue() == serverPing.getVersion().getProtocol()) {
/*    */               return;
/*    */             }
/*    */             synchronized (Via.getPlatform().getConfigurationProvider()) {
/*    */               servers.put(serverName, Integer.valueOf(serverPing.getVersion().getProtocol()));
/*    */             } 
/*    */             Via.getPlatform().getConfigurationProvider().saveConfig();
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void probeAllServers() {
/* 67 */     Collection<ServerInfo> servers = ProxyServer.getInstance().getServers().values();
/* 68 */     Set<String> serverNames = new HashSet<>(servers.size());
/* 69 */     for (ServerInfo serverInfo : servers) {
/* 70 */       probeServer(serverInfo);
/* 71 */       serverNames.add(serverInfo.getName());
/*    */     } 
/*    */ 
/*    */     
/* 75 */     this.lock.writeLock().lock();
/*    */     try {
/* 77 */       this.detectedProtocolIds.keySet().retainAll(serverNames);
/*    */     } finally {
/* 79 */       this.lock.writeLock().unlock();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<String, Integer> configuredServers() {
/* 85 */     return ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int lowestSupportedProtocolVersion() {
/* 90 */     return BungeeVersionProvider.getLowestSupportedVersion();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\service\ProtocolDetectorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */