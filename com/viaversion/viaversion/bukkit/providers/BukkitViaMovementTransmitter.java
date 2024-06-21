/*     */ package com.viaversion.viaversion.bukkit.providers;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
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
/*     */ public class BukkitViaMovementTransmitter
/*     */   extends MovementTransmitterProvider
/*     */ {
/*     */   private static boolean USE_NMS = true;
/*     */   private Object idlePacket;
/*     */   private Object idlePacket2;
/*     */   private Method getHandle;
/*     */   private Field connection;
/*     */   private Method handleFlying;
/*     */   
/*     */   public BukkitViaMovementTransmitter() {
/*     */     Class<?> idlePacketClass;
/*  42 */     USE_NMS = Via.getConfig().isNMSPlayerTicking();
/*     */ 
/*     */     
/*     */     try {
/*  46 */       idlePacketClass = NMSUtil.nms("PacketPlayInFlying");
/*  47 */     } catch (ClassNotFoundException e) {
/*     */       return;
/*     */     } 
/*     */     try {
/*  51 */       this.idlePacket = idlePacketClass.newInstance();
/*  52 */       this.idlePacket2 = idlePacketClass.newInstance();
/*     */       
/*  54 */       Field flying = idlePacketClass.getDeclaredField("f");
/*  55 */       flying.setAccessible(true);
/*     */       
/*  57 */       flying.set(this.idlePacket2, Boolean.valueOf(true));
/*  58 */     } catch (NoSuchFieldException|InstantiationException|IllegalArgumentException|IllegalAccessException e) {
/*  59 */       throw new RuntimeException("Couldn't make player idle packet, help!", e);
/*     */     } 
/*  61 */     if (USE_NMS) {
/*     */       try {
/*  63 */         this.getHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
/*  64 */       } catch (NoSuchMethodException|ClassNotFoundException e) {
/*  65 */         throw new RuntimeException("Couldn't find CraftPlayer", e);
/*     */       } 
/*     */       
/*     */       try {
/*  69 */         this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
/*  70 */       } catch (NoSuchFieldException|ClassNotFoundException e) {
/*  71 */         throw new RuntimeException("Couldn't find Player Connection", e);
/*     */       } 
/*     */       
/*     */       try {
/*  75 */         this.handleFlying = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", new Class[] { idlePacketClass });
/*  76 */       } catch (NoSuchMethodException|ClassNotFoundException e) {
/*  77 */         throw new RuntimeException("Couldn't find CraftPlayer", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFlyingPacket() {
/*  84 */     if (this.idlePacket == null)
/*  85 */       throw new NullPointerException("Could not locate flying packet"); 
/*  86 */     return this.idlePacket;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getGroundPacket() {
/*  91 */     if (this.idlePacket == null)
/*  92 */       throw new NullPointerException("Could not locate flying packet"); 
/*  93 */     return this.idlePacket2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPlayer(UserConnection info) {
/*  98 */     if (USE_NMS) {
/*  99 */       Player player = Bukkit.getPlayer(info.getProtocolInfo().getUuid());
/* 100 */       if (player != null) {
/*     */         
/*     */         try {
/* 103 */           Object entityPlayer = this.getHandle.invoke(player, new Object[0]);
/* 104 */           Object pc = this.connection.get(entityPlayer);
/* 105 */           if (pc != null) {
/* 106 */             this.handleFlying.invoke(pc, new Object[] { ((MovementTracker)info.get(MovementTracker.class)).isGround() ? this.idlePacket2 : this.idlePacket });
/*     */             
/* 108 */             ((MovementTracker)info.get(MovementTracker.class)).incrementIdlePacket();
/*     */           } 
/* 110 */         } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 111 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } else {
/* 115 */       super.sendPlayer(info);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\providers\BukkitViaMovementTransmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */