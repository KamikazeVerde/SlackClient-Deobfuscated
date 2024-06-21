/*    */ package com.viaversion.viabackwards.listener;
/*    */ 
/*    */ import com.viaversion.viabackwards.BukkitPlugin;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.player.PlayerDropItemEvent;
/*    */ import org.bukkit.inventory.ItemStack;
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
/*    */ 
/*    */ 
/*    */ public class PlayerItemDropListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public PlayerItemDropListener(BukkitPlugin plugin) {
/* 33 */     super((Plugin)plugin, Protocol1_13To1_13_1.class);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*    */   public void onItemDrop(PlayerDropItemEvent event) {
/* 38 */     Player player = event.getPlayer();
/* 39 */     if (!isOnPipe(player)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 44 */     int slot = player.getInventory().getHeldItemSlot();
/* 45 */     ItemStack item = player.getInventory().getItem(slot);
/* 46 */     player.getInventory().setItem(slot, item);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\listener\PlayerItemDropListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */