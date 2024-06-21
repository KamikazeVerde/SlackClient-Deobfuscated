/*     */ package com.viaversion.viaversion.bukkit.listeners.multiversion;
/*     */ 
/*     */ import com.viaversion.viaversion.ViaVersionPlugin;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.WeakHashMap;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerToggleSneakEvent;
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
/*     */ 
/*     */ public class PlayerSneakListener
/*     */   extends ViaBukkitListener
/*     */ {
/*     */   private static final float STANDING_HEIGHT = 1.8F;
/*     */   private static final float HEIGHT_1_14 = 1.5F;
/*     */   private static final float HEIGHT_1_9 = 1.6F;
/*     */   private static final float DEFAULT_WIDTH = 0.6F;
/*     */   private final boolean is1_9Fix;
/*     */   private final boolean is1_14Fix;
/*     */   private Map<Player, Boolean> sneaking;
/*     */   private Set<UUID> sneakingUuids;
/*     */   private final Method getHandle;
/*     */   private Method setSize;
/*     */   private boolean useCache;
/*     */   
/*     */   public PlayerSneakListener(ViaVersionPlugin plugin, boolean is1_9Fix, boolean is1_14Fix) throws ReflectiveOperationException {
/*  56 */     super((Plugin)plugin, null);
/*  57 */     this.is1_9Fix = is1_9Fix;
/*  58 */     this.is1_14Fix = is1_14Fix;
/*     */     
/*  60 */     String packageName = plugin.getServer().getClass().getPackage().getName();
/*  61 */     this.getHandle = Class.forName(packageName + ".entity.CraftPlayer").getMethod("getHandle", new Class[0]);
/*     */     
/*  63 */     Class<?> entityPlayerClass = Class.forName(packageName
/*  64 */         .replace("org.bukkit.craftbukkit", "net.minecraft.server") + ".EntityPlayer");
/*     */     try {
/*  66 */       this.setSize = entityPlayerClass.getMethod("setSize", new Class[] { float.class, float.class });
/*  67 */     } catch (NoSuchMethodException e) {
/*     */       
/*  69 */       this.setSize = entityPlayerClass.getMethod("a", new Class[] { float.class, float.class });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  74 */     if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
/*  75 */       this.sneaking = new WeakHashMap<>();
/*  76 */       this.useCache = true;
/*  77 */       plugin.getServer().getScheduler().runTaskTimer((Plugin)plugin, new Runnable()
/*     */           {
/*     */             public void run() {
/*  80 */               for (Map.Entry<Player, Boolean> entry : (Iterable<Map.Entry<Player, Boolean>>)PlayerSneakListener.this.sneaking.entrySet()) {
/*  81 */                 PlayerSneakListener.this.setHeight(entry.getKey(), ((Boolean)entry.getValue()).booleanValue() ? 1.5F : 1.6F);
/*     */               }
/*     */             }
/*     */           }1L, 1L);
/*     */     } 
/*     */ 
/*     */     
/*  88 */     if (is1_14Fix) {
/*  89 */       this.sneakingUuids = new HashSet<>();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void playerToggleSneak(PlayerToggleSneakEvent event) {
/*  95 */     Player player = event.getPlayer();
/*  96 */     UserConnection userConnection = getUserConnection(player);
/*  97 */     if (userConnection == null)
/*  98 */       return;  ProtocolInfo info = userConnection.getProtocolInfo();
/*  99 */     if (info == null)
/*     */       return; 
/* 101 */     int protocolVersion = info.getProtocolVersion();
/* 102 */     if (this.is1_14Fix && protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
/* 103 */       setHeight(player, event.isSneaking() ? 1.5F : 1.8F);
/* 104 */       if (event.isSneaking()) {
/* 105 */         this.sneakingUuids.add(player.getUniqueId());
/*     */       } else {
/* 107 */         this.sneakingUuids.remove(player.getUniqueId());
/*     */       } 
/* 109 */       if (!this.useCache)
/* 110 */         return;  if (event.isSneaking())
/* 111 */       { this.sneaking.put(player, Boolean.valueOf(true)); }
/*     */       else
/* 113 */       { this.sneaking.remove(player); } 
/* 114 */     } else if (this.is1_9Fix && protocolVersion >= ProtocolVersion.v1_9.getVersion()) {
/* 115 */       setHeight(player, event.isSneaking() ? 1.6F : 1.8F);
/* 116 */       if (!this.useCache)
/* 117 */         return;  if (event.isSneaking()) {
/* 118 */         this.sneaking.put(player, Boolean.valueOf(false));
/*     */       } else {
/* 120 */         this.sneaking.remove(player);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void playerDamage(EntityDamageEvent event) {
/* 126 */     if (!this.is1_14Fix)
/* 127 */       return;  if (event.getCause() != EntityDamageEvent.DamageCause.SUFFOCATION)
/* 128 */       return;  if (event.getEntityType() != EntityType.PLAYER)
/*     */       return; 
/* 130 */     Player player = (Player)event.getEntity();
/* 131 */     if (!this.sneakingUuids.contains(player.getUniqueId())) {
/*     */       return;
/*     */     }
/*     */     
/* 135 */     double y = player.getEyeLocation().getY() + 0.045D;
/* 136 */     y -= (int)y;
/* 137 */     if (y < 0.09D) {
/* 138 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void playerQuit(PlayerQuitEvent event) {
/* 144 */     if (this.sneaking != null)
/* 145 */       this.sneaking.remove(event.getPlayer()); 
/* 146 */     if (this.sneakingUuids != null)
/* 147 */       this.sneakingUuids.remove(event.getPlayer().getUniqueId()); 
/*     */   }
/*     */   
/*     */   private void setHeight(Player player, float height) {
/*     */     try {
/* 152 */       this.setSize.invoke(this.getHandle.invoke(player, new Object[0]), new Object[] { Float.valueOf(0.6F), Float.valueOf(height) });
/* 153 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 154 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\multiversion\PlayerSneakListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */