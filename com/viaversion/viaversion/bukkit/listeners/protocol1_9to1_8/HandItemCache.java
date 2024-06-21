/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
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
/*    */ public class HandItemCache
/*    */   extends BukkitRunnable
/*    */ {
/* 34 */   private final Map<UUID, Item> handCache = new ConcurrentHashMap<>();
/*    */ 
/*    */   
/*    */   public void run() {
/* 38 */     List<UUID> players = new ArrayList<>(this.handCache.keySet());
/*    */     
/* 40 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 41 */       this.handCache.put(p.getUniqueId(), convert(p.getItemInHand()));
/* 42 */       players.remove(p.getUniqueId());
/*    */     } 
/*    */     
/* 45 */     for (UUID uuid : players) {
/* 46 */       this.handCache.remove(uuid);
/*    */     }
/*    */   }
/*    */   
/*    */   public Item getHandItem(UUID player) {
/* 51 */     return this.handCache.get(player);
/*    */   }
/*    */   
/*    */   public static Item convert(ItemStack itemInHand) {
/* 55 */     if (itemInHand == null) return (Item)new DataItem(0, (byte)0, (short)0, null); 
/* 56 */     return (Item)new DataItem(itemInHand.getTypeId(), (byte)itemInHand.getAmount(), itemInHand.getDurability(), null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_9to1_8\HandItemCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */