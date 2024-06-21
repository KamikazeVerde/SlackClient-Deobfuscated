/*     */ package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ArmorType;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerChangedWorldEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemBreakEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArmorListener
/*     */   extends ViaBukkitListener
/*     */ {
/*  46 */   private static final UUID ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
/*     */   
/*     */   public ArmorListener(Plugin plugin) {
/*  49 */     super(plugin, Protocol1_9To1_8.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendArmorUpdate(Player player) {
/*  54 */     if (!isOnPipe(player))
/*     */       return; 
/*  56 */     int armor = 0;
/*  57 */     for (ItemStack stack : player.getInventory().getArmorContents()) {
/*  58 */       armor += ArmorType.findById(stack.getTypeId()).getArmorPoints();
/*     */     }
/*     */     
/*  61 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_PROPERTIES, null, getUserConnection(player));
/*     */     try {
/*  63 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(player.getEntityId()));
/*  64 */       wrapper.write((Type)Type.INT, Integer.valueOf(1));
/*  65 */       wrapper.write(Type.STRING, "generic.armor");
/*  66 */       wrapper.write((Type)Type.DOUBLE, Double.valueOf(0.0D));
/*  67 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*  68 */       wrapper.write(Type.UUID, ARMOR_ATTRIBUTE);
/*  69 */       wrapper.write((Type)Type.DOUBLE, Double.valueOf(armor));
/*  70 */       wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/*     */       
/*  72 */       wrapper.scheduleSend(Protocol1_9To1_8.class);
/*  73 */     } catch (Exception e) {
/*  74 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onInventoryClick(InventoryClickEvent e) {
/*  80 */     HumanEntity human = e.getWhoClicked();
/*  81 */     if (human instanceof Player && e.getInventory() instanceof org.bukkit.inventory.CraftingInventory) {
/*  82 */       Player player = (Player)human;
/*  83 */       if (e.getCurrentItem() != null && 
/*  84 */         ArmorType.isArmor(e.getCurrentItem().getTypeId())) {
/*  85 */         sendDelayedArmorUpdate(player);
/*     */         
/*     */         return;
/*     */       } 
/*  89 */       if (e.getRawSlot() >= 5 && e.getRawSlot() <= 8) {
/*  90 */         sendDelayedArmorUpdate(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onInteract(PlayerInteractEvent e) {
/*  97 */     if (e.getItem() != null && (
/*  98 */       e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/*  99 */       Player player = e.getPlayer();
/*     */       
/* 101 */       Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> sendArmorUpdate(player), 3L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onItemBreak(PlayerItemBreakEvent e) {
/* 108 */     sendDelayedArmorUpdate(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onJoin(PlayerJoinEvent e) {
/* 113 */     sendDelayedArmorUpdate(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onRespawn(PlayerRespawnEvent e) {
/* 118 */     sendDelayedArmorUpdate(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onWorldChange(PlayerChangedWorldEvent e) {
/* 123 */     sendArmorUpdate(e.getPlayer());
/*     */   }
/*     */   
/*     */   public void sendDelayedArmorUpdate(Player player) {
/* 127 */     if (!isOnPipe(player))
/* 128 */       return;  Via.getPlatform().runSync(() -> sendArmorUpdate(player));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\protocol1_9to1_8\ArmorListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */