/*    */ package com.viaversion.viabackwards.listener;
/*    */ 
/*    */ import com.viaversion.viabackwards.BukkitPlugin;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Lectern;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.BookMeta;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
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
/*    */ public class LecternInteractListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public LecternInteractListener(BukkitPlugin plugin) {
/* 36 */     super((Plugin)plugin, Protocol1_13_2To1_14.class);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*    */   public void onLecternInteract(PlayerInteractEvent event) {
/* 41 */     Block block = event.getClickedBlock();
/* 42 */     if (block == null || block.getType() != Material.LECTERN)
/*    */       return; 
/* 44 */     Player player = event.getPlayer();
/* 45 */     if (!isOnPipe(player))
/*    */       return; 
/* 47 */     Lectern lectern = (Lectern)block.getState();
/* 48 */     ItemStack book = lectern.getInventory().getItem(0);
/* 49 */     if (book == null)
/*    */       return; 
/* 51 */     BookMeta meta = (BookMeta)book.getItemMeta();
/*    */ 
/*    */     
/* 54 */     ItemStack newBook = new ItemStack(Material.WRITTEN_BOOK);
/* 55 */     BookMeta newBookMeta = (BookMeta)newBook.getItemMeta();
/* 56 */     newBookMeta.setPages(meta.getPages());
/* 57 */     newBookMeta.setAuthor("an upsidedown person");
/* 58 */     newBookMeta.setTitle("buk");
/* 59 */     newBook.setItemMeta((ItemMeta)newBookMeta);
/* 60 */     player.openBook(newBook);
/*    */     
/* 62 */     event.setCancelled(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\listener\LecternInteractListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */