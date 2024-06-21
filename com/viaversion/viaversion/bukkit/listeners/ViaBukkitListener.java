/*    */ package com.viaversion.viaversion.bukkit.listeners;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaListener;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public class ViaBukkitListener
/*    */   extends ViaListener
/*    */   implements Listener
/*    */ {
/*    */   private final Plugin plugin;
/*    */   
/*    */   public ViaBukkitListener(Plugin plugin, Class<? extends Protocol> requiredPipeline) {
/* 31 */     super(requiredPipeline);
/* 32 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected UserConnection getUserConnection(Player player) {
/* 42 */     return getUserConnection(player.getUniqueId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isOnPipe(Player player) {
/* 52 */     return isOnPipe(player.getUniqueId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 60 */     if (isRegistered()) {
/*    */       return;
/*    */     }
/*    */     
/* 64 */     setRegistered(true);
/* 65 */     this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
/*    */   }
/*    */   
/*    */   public Plugin getPlugin() {
/* 69 */     return this.plugin;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\ViaBukkitListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */