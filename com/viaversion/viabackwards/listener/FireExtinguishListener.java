/*    */ package com.viaversion.viabackwards.listener;
/*    */ 
/*    */ import com.viaversion.viabackwards.BukkitPlugin;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
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
/*    */ public class FireExtinguishListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public FireExtinguishListener(BukkitPlugin plugin) {
/* 34 */     super((Plugin)plugin, Protocol1_15_2To1_16.class);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*    */   public void onFireExtinguish(PlayerInteractEvent event) {
/* 39 */     if (event.getAction() != Action.LEFT_CLICK_BLOCK)
/*    */       return; 
/* 41 */     Block block = event.getClickedBlock();
/* 42 */     if (block == null)
/*    */       return; 
/* 44 */     Player player = event.getPlayer();
/* 45 */     if (!isOnPipe(player))
/*    */       return; 
/* 47 */     Block relative = block.getRelative(event.getBlockFace());
/* 48 */     if (relative.getType() == Material.FIRE) {
/* 49 */       event.setCancelled(true);
/* 50 */       relative.setType(Material.AIR);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\listener\FireExtinguishListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */