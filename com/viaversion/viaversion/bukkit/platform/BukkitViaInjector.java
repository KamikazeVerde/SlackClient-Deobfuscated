/*     */ package com.viaversion.viaversion.bukkit.platform;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
/*     */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*     */ import com.viaversion.viaversion.platform.LegacyViaInjector;
/*     */ import com.viaversion.viaversion.platform.WrappedChannelInitializer;
/*     */ import com.viaversion.viaversion.util.ReflectionUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
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
/*     */ 
/*     */ public class BukkitViaInjector
/*     */   extends LegacyViaInjector
/*     */ {
/*  39 */   private static final boolean HAS_WORLD_VERSION_PROTOCOL_VERSION = (PaperViaInjector.hasClass("net.minecraft.SharedConstants") && 
/*  40 */     PaperViaInjector.hasClass("net.minecraft.WorldVersion") && 
/*  41 */     !PaperViaInjector.hasClass("com.mojang.bridge.game.GameVersion"));
/*     */ 
/*     */   
/*     */   public void inject() throws ReflectiveOperationException {
/*  45 */     if (PaperViaInjector.PAPER_INJECTION_METHOD) {
/*  46 */       PaperViaInjector.setPaperChannelInitializeListener();
/*     */       
/*     */       return;
/*     */     } 
/*  50 */     super.inject();
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninject() throws ReflectiveOperationException {
/*  55 */     if (PaperViaInjector.PAPER_INJECTION_METHOD) {
/*  56 */       PaperViaInjector.removePaperChannelInitializeListener();
/*     */       
/*     */       return;
/*     */     } 
/*  60 */     super.uninject();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerProtocolVersion() throws ReflectiveOperationException {
/*  65 */     if (PaperViaInjector.PAPER_PROTOCOL_METHOD)
/*     */     {
/*  67 */       return Bukkit.getUnsafe().getProtocolVersion();
/*     */     }
/*     */     
/*  70 */     return HAS_WORLD_VERSION_PROTOCOL_VERSION ? cursedProtocolDetection() : veryCursedProtocolDetection();
/*     */   }
/*     */ 
/*     */   
/*     */   private int cursedProtocolDetection() throws ReflectiveOperationException {
/*  75 */     Class<?> sharedConstantsClass = Class.forName("net.minecraft.SharedConstants");
/*  76 */     Class<?> worldVersionClass = Class.forName("net.minecraft.WorldVersion");
/*  77 */     Method getWorldVersionMethod = null;
/*  78 */     for (Method method : sharedConstantsClass.getDeclaredMethods()) {
/*  79 */       if (method.getReturnType() == worldVersionClass && (method.getParameterTypes()).length == 0) {
/*  80 */         getWorldVersionMethod = method;
/*     */         break;
/*     */       } 
/*     */     } 
/*  84 */     Preconditions.checkNotNull(getWorldVersionMethod, "Failed to get world version method");
/*     */     
/*  86 */     Object worldVersion = getWorldVersionMethod.invoke(null, new Object[0]);
/*  87 */     for (Method method : worldVersionClass.getDeclaredMethods()) {
/*  88 */       if (method.getReturnType() == int.class && (method.getParameterTypes()).length == 0) {
/*  89 */         return ((Integer)method.invoke(worldVersion, new Object[0])).intValue();
/*     */       }
/*     */     } 
/*  92 */     throw new IllegalAccessException("Failed to find protocol version method in WorldVersion");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int veryCursedProtocolDetection() throws ReflectiveOperationException {
/*  98 */     Class<?> serverClazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
/*  99 */     Object server = ReflectionUtil.invokeStatic(serverClazz, "getServer");
/* 100 */     Preconditions.checkNotNull(server, "Failed to get server instance");
/*     */ 
/*     */     
/* 103 */     Class<?> pingClazz = NMSUtil.nms("ServerPing", "net.minecraft.network.protocol.status.ServerPing");
/*     */ 
/*     */ 
/*     */     
/* 107 */     Object ping = null;
/* 108 */     for (Field field : serverClazz.getDeclaredFields()) {
/* 109 */       if (field.getType() == pingClazz) {
/* 110 */         field.setAccessible(true);
/* 111 */         ping = field.get(server);
/*     */         break;
/*     */       } 
/*     */     } 
/* 115 */     Preconditions.checkNotNull(ping, "Failed to get server ping");
/*     */ 
/*     */     
/* 118 */     Class<?> serverDataClass = NMSUtil.nms("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData");
/*     */ 
/*     */ 
/*     */     
/* 122 */     Object serverData = null;
/* 123 */     for (Field field : pingClazz.getDeclaredFields()) {
/* 124 */       if (field.getType() == serverDataClass) {
/* 125 */         field.setAccessible(true);
/* 126 */         serverData = field.get(ping);
/*     */         break;
/*     */       } 
/*     */     } 
/* 130 */     Preconditions.checkNotNull(serverData, "Failed to get server data");
/*     */ 
/*     */     
/* 133 */     for (Field field : serverDataClass.getDeclaredFields()) {
/* 134 */       if (field.getType() == int.class) {
/*     */ 
/*     */ 
/*     */         
/* 138 */         field.setAccessible(true);
/* 139 */         int protocolVersion = ((Integer)field.get(serverData)).intValue();
/* 140 */         if (protocolVersion != -1)
/* 141 */           return protocolVersion; 
/*     */       } 
/*     */     } 
/* 144 */     throw new RuntimeException("Failed to get server");
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getServerConnection() throws ReflectiveOperationException {
/* 149 */     Class<?> serverClass = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
/*     */ 
/*     */ 
/*     */     
/* 153 */     Class<?> connectionClass = NMSUtil.nms("ServerConnection", "net.minecraft.server.network.ServerConnection");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     Object server = ReflectionUtil.invokeStatic(serverClass, "getServer");
/* 159 */     for (Method method : serverClass.getDeclaredMethods()) {
/* 160 */       if (method.getReturnType() == connectionClass && (method.getParameterTypes()).length == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 165 */         Object connection = method.invoke(server, new Object[0]);
/* 166 */         if (connection != null)
/* 167 */           return connection; 
/*     */       } 
/*     */     } 
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> oldInitializer) {
/* 175 */     return (WrappedChannelInitializer)new BukkitChannelInitializer(oldInitializer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void blame(ChannelHandler bootstrapAcceptor) throws ReflectiveOperationException {
/* 181 */     ClassLoader classLoader = bootstrapAcceptor.getClass().getClassLoader();
/* 182 */     if (classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
/* 183 */       PluginDescriptionFile description = (PluginDescriptionFile)ReflectionUtil.get(classLoader, "description", PluginDescriptionFile.class);
/* 184 */       throw new RuntimeException("Unable to inject, due to " + bootstrapAcceptor.getClass().getName() + ", try without the plugin " + description.getName() + "?");
/*     */     } 
/* 186 */     throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lateProtocolVersionSetting() {
/* 192 */     return (!PaperViaInjector.PAPER_PROTOCOL_METHOD && !HAS_WORLD_VERSION_PROTOCOL_VERSION);
/*     */   }
/*     */   
/*     */   public boolean isBinded() {
/* 196 */     if (PaperViaInjector.PAPER_INJECTION_METHOD) {
/* 197 */       return true;
/*     */     }
/*     */     try {
/* 200 */       Object connection = getServerConnection();
/* 201 */       if (connection == null) {
/* 202 */         return false;
/*     */       }
/*     */       
/* 205 */       for (Field field : connection.getClass().getDeclaredFields()) {
/* 206 */         if (List.class.isAssignableFrom(field.getType())) {
/*     */ 
/*     */ 
/*     */           
/* 210 */           field.setAccessible(true);
/* 211 */           List<?> value = (List)field.get(connection);
/*     */           
/* 213 */           synchronized (value) {
/* 214 */             if (!value.isEmpty() && value.get(0) instanceof io.netty.channel.ChannelFuture)
/* 215 */               return true; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 219 */     } catch (ReflectiveOperationException e) {
/* 220 */       e.printStackTrace();
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\BukkitViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */