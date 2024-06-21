/*    */ package com.viaversion.viaversion.bukkit.listeners.protocol1_19_4To1_19_3;
/*    */ 
/*    */ import com.viaversion.viaversion.ViaVersionPlugin;
/*    */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.EquipmentSlot;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
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
/*    */ public final class ArmorToggleListener
/*    */   extends ViaBukkitListener
/*    */ {
/*    */   public ArmorToggleListener(ViaVersionPlugin plugin) {
/* 35 */     super((Plugin)plugin, Protocol1_19_4To1_19_3.class);
/*    */   }
/*    */   
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void itemUse(PlayerInteractEvent event) {
/* 40 */     Player player = event.getPlayer();
/* 41 */     ItemStack item = event.getItem();
/* 42 */     if (item == null || event.getHand() == null || !isOnPipe(player)) {
/*    */       return;
/*    */     }
/*    */     
/* 46 */     EquipmentSlot armorItemSlot = item.getType().getEquipmentSlot();
/* 47 */     if (armorItemSlot != EquipmentSlot.HAND && armorItemSlot != EquipmentSlot.OFF_HAND) {
/* 48 */       PlayerInventory inventory = player.getInventory();
/* 49 */       ItemStack armor = inventory.getItem(armorItemSlot);
/*    */       
/* 51 */       if (armor != null && armor.getType() != Material.AIR && !armor.equals(item)) {
/* 52 */         inventory.setItem(event.getHand(), inventory.getItem(event.getHand()));
/* 53 */         inventory.setItem(armorItemSlot, armor);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_19_4To1_19_3\ArmorToggleListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */