/*     */ package com.viaversion.viaversion.bukkit.listeners;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
/*     */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public class JoinListener
/*     */   implements Listener
/*     */ {
/*     */   private static final Method GET_HANDLE;
/*     */   private static final Field CONNECTION;
/*     */   private static final Field NETWORK_MANAGER;
/*     */   private static final Field CHANNEL;
/*     */   
/*     */   static {
/*  45 */     Method getHandleMethod = null;
/*  46 */     Field gamePacketListenerField = null, connectionField = null, channelField = null;
/*     */     try {
/*  48 */       getHandleMethod = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
/*  49 */       gamePacketListenerField = findField(getHandleMethod.getReturnType(), new String[] { "PlayerConnection", "ServerGamePacketListenerImpl" });
/*  50 */       connectionField = findField(gamePacketListenerField.getType(), new String[] { "NetworkManager", "Connection" });
/*  51 */       channelField = findField(connectionField.getType(), Class.forName("io.netty.channel.Channel"));
/*  52 */     } catch (NoSuchMethodException|NoSuchFieldException|ClassNotFoundException e) {
/*  53 */       Via.getPlatform().getLogger().log(Level.WARNING, "Couldn't find reflection methods/fields to access Channel from player.\nLogin race condition fixer will be disabled.\n Some plugins that use ViaAPI on join event may work incorrectly.", e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     GET_HANDLE = getHandleMethod;
/*  60 */     CONNECTION = gamePacketListenerField;
/*  61 */     NETWORK_MANAGER = connectionField;
/*  62 */     CHANNEL = channelField;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Field findField(Class<?> clazz, String... types) throws NoSuchFieldException {
/*  67 */     for (Field field : clazz.getDeclaredFields()) {
/*  68 */       String fieldTypeName = field.getType().getSimpleName(); String[] arrayOfString; int i; byte b;
/*  69 */       for (arrayOfString = types, i = arrayOfString.length, b = 0; b < i; ) { String type = arrayOfString[b];
/*  70 */         if (!fieldTypeName.equals(type)) {
/*     */           b++;
/*     */           continue;
/*     */         } 
/*  74 */         if (!Modifier.isPublic(field.getModifiers())) {
/*  75 */           field.setAccessible(true);
/*     */         }
/*  77 */         return field; }
/*     */     
/*     */     } 
/*  80 */     throw new NoSuchFieldException(types[0]); } private static Field findField(Class<?> clazz, Class<?> fieldType) throws NoSuchFieldException {
/*     */     Field[] arrayOfField;
/*     */     int i;
/*     */     byte b;
/*  84 */     for (arrayOfField = clazz.getDeclaredFields(), i = arrayOfField.length, b = 0; b < i; ) { Field field = arrayOfField[b];
/*  85 */       if (field.getType() != fieldType) {
/*     */         b++;
/*     */         continue;
/*     */       } 
/*  89 */       if (!Modifier.isPublic(field.getModifiers())) {
/*  90 */         field.setAccessible(true);
/*     */       }
/*  92 */       return field; }
/*     */     
/*  94 */     throw new NoSuchFieldException(fieldType.getSimpleName());
/*     */   }
/*     */   @EventHandler(priority = EventPriority.LOWEST)
/*     */   public void onJoin(PlayerJoinEvent e) {
/*     */     Channel channel;
/*  99 */     if (CHANNEL == null)
/* 100 */       return;  Player player = e.getPlayer();
/*     */ 
/*     */     
/*     */     try {
/* 104 */       channel = getChannel(player);
/* 105 */     } catch (Exception ex) {
/* 106 */       Via.getPlatform().getLogger().log(Level.WARNING, ex, () -> "Could not find Channel for logging-in player " + player.getUniqueId());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 111 */     if (!channel.isOpen())
/*     */       return; 
/* 113 */     UserConnection user = getUserConnection(channel);
/* 114 */     if (user == null) {
/* 115 */       Via.getPlatform().getLogger().log(Level.WARNING, "Could not find UserConnection for logging-in player {0}", player
/*     */           
/* 117 */           .getUniqueId());
/*     */       return;
/*     */     } 
/* 120 */     ProtocolInfo info = user.getProtocolInfo();
/* 121 */     info.setUuid(player.getUniqueId());
/* 122 */     info.setUsername(player.getName());
/* 123 */     Via.getManager().getConnectionManager().onLoginSuccess(user);
/*     */   }
/*     */   @Nullable
/*     */   private UserConnection getUserConnection(Channel channel) {
/* 127 */     BukkitEncodeHandler encoder = (BukkitEncodeHandler)channel.pipeline().get(BukkitEncodeHandler.class);
/* 128 */     return (encoder != null) ? encoder.connection() : null;
/*     */   }
/*     */   
/*     */   private Channel getChannel(Player player) throws Exception {
/* 132 */     Object entityPlayer = GET_HANDLE.invoke(player, new Object[0]);
/* 133 */     Object pc = CONNECTION.get(entityPlayer);
/* 134 */     Object nm = NETWORK_MANAGER.get(pc);
/* 135 */     return (Channel)CHANNEL.get(nm);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\JoinListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */