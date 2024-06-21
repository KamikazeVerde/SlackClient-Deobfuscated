/*    */ package com.viaversion.viaversion.bukkit.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaAPIBase;
/*    */ import com.viaversion.viaversion.ViaVersionPlugin;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.bukkit.util.ProtocolSupportUtil;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public class BukkitViaAPI
/*    */   extends ViaAPIBase<Player>
/*    */ {
/*    */   private final ViaVersionPlugin plugin;
/*    */   
/*    */   public BukkitViaAPI(ViaVersionPlugin plugin) {
/* 34 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlayerVersion(Player player) {
/* 39 */     return getPlayerVersion(player.getUniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlayerVersion(UUID uuid) {
/* 44 */     UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
/* 45 */     if (connection != null) {
/* 46 */       return connection.getProtocolInfo().getProtocolVersion();
/*    */     }
/*    */     
/* 49 */     if (isProtocolSupport()) {
/* 50 */       Player player = Bukkit.getPlayer(uuid);
/* 51 */       if (player != null) {
/* 52 */         return ProtocolSupportUtil.getProtocolVersion(player);
/*    */       }
/*    */     } 
/* 55 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
/* 60 */     sendRawPacket(player.getUniqueId(), packet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isProtocolSupport() {
/* 69 */     return this.plugin.isProtocolSupport();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\BukkitViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */