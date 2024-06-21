/*    */ package com.viaversion.viaversion;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.ViaAPI;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
/*    */ import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
/*    */ import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
/*    */ import com.viaversion.viaversion.legacy.LegacyAPI;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.SortedSet;
/*    */ import java.util.TreeSet;
/*    */ import java.util.UUID;
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
/*    */ public abstract class ViaAPIBase<T>
/*    */   implements ViaAPI<T>
/*    */ {
/* 35 */   private final LegacyAPI<T> legacy = new LegacyAPI();
/*    */ 
/*    */   
/*    */   public ServerProtocolVersion getServerVersion() {
/* 39 */     return Via.getManager().getProtocolManager().getServerProtocolVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlayerVersion(UUID uuid) {
/* 44 */     UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
/* 45 */     return (connection != null) ? connection.getProtocolInfo().getProtocolVersion() : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 50 */     return Via.getPlatform().getPluginVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInjected(UUID uuid) {
/* 55 */     return Via.getManager().getConnectionManager().isClientConnected(uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserConnection getConnection(UUID uuid) {
/* 60 */     return Via.getManager().getConnectionManager().getConnectedClient(uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(UUID uuid, ByteBuf packet) throws IllegalArgumentException {
/* 65 */     if (!isInjected(uuid)) {
/* 66 */       throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
/*    */     }
/*    */     
/* 69 */     UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(uuid);
/* 70 */     user.scheduleSendRawPacket(packet);
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<Integer> getSupportedVersions() {
/* 75 */     SortedSet<Integer> outputSet = new TreeSet<>(Via.getManager().getProtocolManager().getSupportedVersions());
/* 76 */     BlockedProtocolVersions blockedVersions = Via.getPlatform().getConf().blockedProtocolVersions();
/* 77 */     outputSet.removeIf(blockedVersions::contains);
/* 78 */     return outputSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<Integer> getFullSupportedVersions() {
/* 83 */     return Via.getManager().getProtocolManager().getSupportedVersions();
/*    */   }
/*    */ 
/*    */   
/*    */   public LegacyViaAPI<T> legacyAPI() {
/* 88 */     return (LegacyViaAPI<T>)this.legacy;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\ViaAPIBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */