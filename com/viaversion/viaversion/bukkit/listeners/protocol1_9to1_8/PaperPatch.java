/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
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
/*    */ public class PaperPatch
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public PaperPatch(Plugin plugin) {
/* 34 */     super(plugin, Protocol1_9To1_8.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
/*    */   public void onPlace(BlockPlaceEvent e) {
/* 46 */     if (isOnPipe(e.getPlayer())) {
/* 47 */       Material block = e.getBlockPlaced().getType();
/* 48 */       if (isPlacable(block)) {
/*    */         return;
/*    */       }
/* 51 */       Location location = e.getPlayer().getLocation();
/* 52 */       Block locationBlock = location.getBlock();
/*    */       
/* 54 */       if (locationBlock.equals(e.getBlock())) {
/* 55 */         e.setCancelled(true);
/*    */       }
/* 57 */       else if (locationBlock.getRelative(BlockFace.UP).equals(e.getBlock())) {
/* 58 */         e.setCancelled(true);
/*    */       } else {
/* 60 */         Location diff = location.clone().subtract(e.getBlock().getLocation().add(0.5D, 0.0D, 0.5D));
/*    */         
/* 62 */         if (Math.abs(diff.getX()) <= 0.8D && Math.abs(diff.getZ()) <= 0.8D) {
/*    */           
/* 64 */           if (diff.getY() <= 0.1D && diff.getY() >= -0.1D) {
/* 65 */             e.setCancelled(true);
/*    */             return;
/*    */           } 
/* 68 */           BlockFace relative = e.getBlockAgainst().getFace(e.getBlock());
/*    */           
/* 70 */           if (relative == BlockFace.UP && 
/* 71 */             diff.getY() < 1.0D && diff.getY() >= 0.0D) {
/* 72 */             e.setCancelled(true);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isPlacable(Material material) {
/* 82 */     if (!material.isSolid()) return true;
/*    */     
/* 84 */     switch (material.getId()) {
/*    */       case 63:
/*    */       case 68:
/*    */       case 176:
/*    */       case 177:
/* 89 */         return true;
/*    */     } 
/* 91 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_9to1_8\PaperPatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */