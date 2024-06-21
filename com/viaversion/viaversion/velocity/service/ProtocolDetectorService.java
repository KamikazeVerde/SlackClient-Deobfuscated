/*    */ package com.viaversion.viaversion.velocity.service;
/*    */ 
/*    */ import com.velocitypowered.api.proxy.server.RegisteredServer;
/*    */ import com.velocitypowered.api.proxy.server.ServerPing;
/*    */ import com.viaversion.viaversion.VelocityPlugin;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
/*    */ import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public final class ProtocolDetectorService
/*    */   extends AbstractProtocolDetectorService
/*    */ {
/*    */   public void probeAllServers() {
/* 35 */     Collection<RegisteredServer> servers = VelocityPlugin.PROXY.getAllServers();
/* 36 */     Set<String> serverNames = new HashSet<>(servers.size());
/* 37 */     for (RegisteredServer server : servers) {
/* 38 */       probeServer(server);
/* 39 */       serverNames.add(server.getServerInfo().getName());
/*    */     } 
/*    */ 
/*    */     
/* 43 */     this.lock.writeLock().lock();
/*    */     try {
/* 45 */       this.detectedProtocolIds.keySet().retainAll(serverNames);
/*    */     } finally {
/* 47 */       this.lock.writeLock().unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void probeServer(RegisteredServer server) {
/* 52 */     String serverName = server.getServerInfo().getName();
/* 53 */     server.ping().thenAccept(serverPing -> {
/*    */           if (serverPing == null || serverPing.getVersion() == null) {
/*    */             return;
/*    */           }
/*    */           int oldProtocolVersion = serverProtocolVersion(serverName);
/*    */           if (oldProtocolVersion != -1 && oldProtocolVersion == serverPing.getVersion().getProtocol()) {
/*    */             return;
/*    */           }
/*    */           setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
/*    */           if (((VelocityViaConfig)Via.getConfig()).isVelocityPingSave()) {
/*    */             Map<String, Integer> servers = configuredServers();
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
/*    */   protected Map<String, Integer> configuredServers() {
/* 84 */     return ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int lowestSupportedProtocolVersion() {
/*    */     try {
/* 90 */       return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
/* 91 */     } catch (Exception e) {
/* 92 */       e.printStackTrace();
/* 93 */       return ProtocolVersion.v1_8.getVersion();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\service\ProtocolDetectorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */