/*     */ package com.viaversion.viaversion.bukkit.compat;
/*     */ 
/*     */ import com.viaversion.viaversion.ViaVersionPlugin;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventException;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
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
/*     */ public final class ProtocolSupportCompat
/*     */ {
/*     */   public static void registerPSConnectListener(ViaVersionPlugin plugin) {
/*  32 */     Via.getPlatform().getLogger().info("Registering ProtocolSupport compat connection listener");
/*     */     
/*     */     try {
/*  35 */       Class<? extends Event> connectionOpenEvent = (Class)Class.forName("protocolsupport.api.events.ConnectionOpenEvent");
/*  36 */       Bukkit.getPluginManager().registerEvent(connectionOpenEvent, new Listener() {  }, EventPriority.HIGH, (listener, event) -> {
/*     */             try {
/*     */               Object connection = event.getClass().getMethod("getConnection", new Class[0]).invoke(event, new Object[0]);
/*     */               
/*     */               ProtocolSupportConnectionListener connectListener = new ProtocolSupportConnectionListener(connection);
/*     */               ProtocolSupportConnectionListener.ADD_PACKET_LISTENER_METHOD.invoke(connection, new Object[] { connectListener });
/*  42 */             } catch (ReflectiveOperationException e) {
/*     */               Via.getPlatform().getLogger().log(Level.WARNING, "Error when handling ProtocolSupport event", e);
/*     */             } 
/*     */           }(Plugin)plugin);
/*  46 */     } catch (ClassNotFoundException e) {
/*  47 */       Via.getPlatform().getLogger().log(Level.WARNING, "Unable to register ProtocolSupport listener", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isMultiplatformPS() {
/*     */     try {
/*  53 */       Class.forName("protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder");
/*  54 */       return true;
/*  55 */     } catch (ClassNotFoundException e) {
/*  56 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static HandshakeProtocolType handshakeVersionMethod() {
/*  61 */     Class<?> clazz = null;
/*     */     
/*     */     try {
/*  64 */       clazz = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
/*     */ 
/*     */ 
/*     */       
/*  68 */       clazz.getMethod("getProtocolVersion", new Class[0]);
/*  69 */       return HandshakeProtocolType.MAPPED;
/*  70 */     } catch (ClassNotFoundException e) {
/*  71 */       throw new RuntimeException(e);
/*  72 */     } catch (NoSuchMethodException noSuchMethodException) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  77 */         if (clazz.getMethod("b", new Class[0]).getReturnType() == int.class)
/*  78 */           return HandshakeProtocolType.OBFUSCATED_B; 
/*  79 */         if (clazz.getMethod("c", new Class[0]).getReturnType() == int.class) {
/*  80 */           return HandshakeProtocolType.OBFUSCATED_C;
/*     */         }
/*  82 */         throw new UnsupportedOperationException("Protocol version method not found in " + clazz.getSimpleName());
/*  83 */       } catch (ReflectiveOperationException e) {
/*  84 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   enum HandshakeProtocolType {
/*  90 */     MAPPED("getProtocolVersion"),
/*  91 */     OBFUSCATED_B("b"),
/*  92 */     OBFUSCATED_C("c");
/*     */     
/*     */     private final String methodName;
/*     */     
/*     */     HandshakeProtocolType(String methodName) {
/*  97 */       this.methodName = methodName;
/*     */     }
/*     */     
/*     */     public String methodName() {
/* 101 */       return this.methodName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\compat\ProtocolSupportCompat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */