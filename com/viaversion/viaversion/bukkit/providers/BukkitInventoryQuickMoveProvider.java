/*     */ package com.viaversion.viaversion.bukkit.providers;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1.BukkitInventoryUpdateTask;
/*     */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.ItemTransaction;
/*     */ import com.viaversion.viaversion.util.ReflectionUtil;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ 
/*     */ public class BukkitInventoryQuickMoveProvider
/*     */   extends InventoryQuickMoveProvider
/*     */ {
/*  44 */   private final Map<UUID, BukkitInventoryUpdateTask> updateTasks = new ConcurrentHashMap<>();
/*     */   
/*     */   private final boolean supported;
/*     */   
/*     */   private Class<?> windowClickPacketClass;
/*     */   private Object clickTypeEnum;
/*     */   private Method nmsItemMethod;
/*     */   private Method craftPlayerHandle;
/*     */   private Field connection;
/*     */   private Method packetMethod;
/*     */   
/*     */   public BukkitInventoryQuickMoveProvider() {
/*  56 */     this.supported = isSupported();
/*  57 */     setupReflection();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean registerQuickMoveAction(short windowId, short slotId, short actionId, UserConnection userConnection) {
/*  62 */     if (!this.supported) {
/*  63 */       return false;
/*     */     }
/*  65 */     if (slotId < 0) {
/*  66 */       return false;
/*     */     }
/*  68 */     if (windowId == 0)
/*     */     {
/*     */       
/*  71 */       if (slotId >= 36 && slotId <= 45) {
/*  72 */         int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
/*     */         
/*  74 */         if (protocolId == ProtocolVersion.v1_8.getVersion()) {
/*  75 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*  79 */     ProtocolInfo info = userConnection.getProtocolInfo();
/*  80 */     UUID uuid = info.getUuid();
/*  81 */     BukkitInventoryUpdateTask updateTask = this.updateTasks.get(uuid);
/*  82 */     boolean registered = (updateTask != null);
/*  83 */     if (!registered) {
/*  84 */       updateTask = new BukkitInventoryUpdateTask(this, uuid);
/*  85 */       this.updateTasks.put(uuid, updateTask);
/*     */     } 
/*     */     
/*  88 */     updateTask.addItem(windowId, slotId, actionId);
/*  89 */     if (!registered && Via.getPlatform().isPluginEnabled()) {
/*  90 */       Via.getPlatform().runSync((Runnable)updateTask);
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public Object buildWindowClickPacket(Player p, ItemTransaction storage) {
/*  96 */     if (!this.supported) {
/*  97 */       return null;
/*     */     }
/*  99 */     InventoryView inv = p.getOpenInventory();
/* 100 */     short slotId = storage.getSlotId();
/* 101 */     Inventory tinv = inv.getTopInventory();
/* 102 */     InventoryType tinvtype = (tinv == null) ? null : tinv.getType();
/* 103 */     if (tinvtype != null) {
/* 104 */       int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
/* 105 */       if (protocolId == ProtocolVersion.v1_8.getVersion() && 
/* 106 */         tinvtype == InventoryType.BREWING)
/*     */       {
/* 108 */         if (slotId >= 5 && slotId <= 40) {
/* 109 */           slotId = (short)(slotId - 1);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 114 */     ItemStack itemstack = null;
/*     */     
/* 116 */     if (slotId <= inv.countSlots()) {
/* 117 */       itemstack = inv.getItem(slotId);
/*     */     }
/*     */     else {
/*     */       
/* 121 */       String cause = "Too many inventory slots: slotId: " + slotId + " invSlotCount: " + inv.countSlots() + " invType: " + inv.getType() + " topInvType: " + tinvtype;
/* 122 */       Via.getPlatform().getLogger().severe("Failed to get an item to create a window click packet. Please report this issue to the ViaVersion Github: " + cause);
/*     */     } 
/* 124 */     Object packet = null;
/*     */     try {
/* 126 */       packet = this.windowClickPacketClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 127 */       Object nmsItem = (itemstack == null) ? null : this.nmsItemMethod.invoke(null, new Object[] { itemstack });
/* 128 */       ReflectionUtil.set(packet, "a", Integer.valueOf(storage.getWindowId()));
/* 129 */       ReflectionUtil.set(packet, "slot", Integer.valueOf(slotId));
/* 130 */       ReflectionUtil.set(packet, "button", Integer.valueOf(0));
/* 131 */       ReflectionUtil.set(packet, "d", Short.valueOf(storage.getActionId()));
/* 132 */       ReflectionUtil.set(packet, "item", nmsItem);
/* 133 */       int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
/* 134 */       if (protocolId == ProtocolVersion.v1_8.getVersion()) {
/* 135 */         ReflectionUtil.set(packet, "shift", Integer.valueOf(1));
/* 136 */       } else if (protocolId >= ProtocolVersion.v1_9.getVersion()) {
/* 137 */         ReflectionUtil.set(packet, "shift", this.clickTypeEnum);
/*     */       } 
/* 139 */     } catch (Exception e) {
/* 140 */       Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to create a window click packet. Please report this issue to the ViaVersion Github: " + e.getMessage(), e);
/*     */     } 
/* 142 */     return packet;
/*     */   }
/*     */   
/*     */   public boolean sendPacketToServer(Player p, Object packet) {
/* 146 */     if (packet == null)
/*     */     {
/* 148 */       return true;
/*     */     }
/*     */     try {
/* 151 */       Object entityPlayer = this.craftPlayerHandle.invoke(p, new Object[0]);
/* 152 */       Object playerConnection = this.connection.get(entityPlayer);
/*     */       
/* 154 */       this.packetMethod.invoke(playerConnection, new Object[] { packet });
/* 155 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 156 */       e.printStackTrace();
/* 157 */       return false;
/*     */     } 
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   public void onTaskExecuted(UUID uuid) {
/* 163 */     this.updateTasks.remove(uuid);
/*     */   }
/*     */   
/*     */   private void setupReflection() {
/* 167 */     if (!this.supported) {
/*     */       return;
/*     */     }
/*     */     try {
/* 171 */       this.windowClickPacketClass = NMSUtil.nms("PacketPlayInWindowClick");
/* 172 */       int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
/* 173 */       if (protocolId >= ProtocolVersion.v1_9.getVersion()) {
/* 174 */         Class<?> eclassz = NMSUtil.nms("InventoryClickType");
/* 175 */         Object[] constants = eclassz.getEnumConstants();
/* 176 */         this.clickTypeEnum = constants[1];
/*     */       } 
/* 178 */       Class<?> craftItemStack = NMSUtil.obc("inventory.CraftItemStack");
/* 179 */       this.nmsItemMethod = craftItemStack.getDeclaredMethod("asNMSCopy", new Class[] { ItemStack.class });
/* 180 */     } catch (Exception e) {
/* 181 */       throw new RuntimeException("Couldn't find required inventory classes", e);
/*     */     } 
/*     */     try {
/* 184 */       this.craftPlayerHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
/* 185 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/* 186 */       throw new RuntimeException("Couldn't find CraftPlayer", e);
/*     */     } 
/*     */     try {
/* 189 */       this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
/* 190 */     } catch (NoSuchFieldException|ClassNotFoundException e) {
/* 191 */       throw new RuntimeException("Couldn't find Player Connection", e);
/*     */     } 
/*     */     try {
/* 194 */       this.packetMethod = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", new Class[] { this.windowClickPacketClass });
/* 195 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/* 196 */       throw new RuntimeException("Couldn't find CraftPlayer", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isSupported() {
/* 201 */     int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
/* 202 */     if (protocolId >= ProtocolVersion.v1_8.getVersion() && protocolId <= ProtocolVersion.v1_11_1.getVersion()) {
/* 203 */       return true;
/*     */     }
/*     */     
/* 206 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\providers\BukkitInventoryQuickMoveProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */