/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*    */ import org.bukkit.block.Block;
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
/*    */ public class BlockListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public BlockListener(Plugin plugin) {
/* 33 */     super(plugin, Protocol1_9To1_8.class);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*    */   public void placeBlock(BlockPlaceEvent e) {
/* 38 */     if (isOnPipe(e.getPlayer())) {
/* 39 */       Block b = e.getBlockPlaced();
/* 40 */       EntityTracker1_9 tracker = (EntityTracker1_9)getUserConnection(e.getPlayer()).getEntityTracker(Protocol1_9To1_8.class);
/* 41 */       tracker.addBlockInteraction(new Position(b.getX(), b.getY(), b.getZ()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_9to1_8\BlockListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */