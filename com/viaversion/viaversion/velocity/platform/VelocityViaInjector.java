/*     */ package com.viaversion.viaversion.velocity.platform;
/*     */ 
/*     */ import com.velocitypowered.api.network.ProtocolVersion;
/*     */ import com.viaversion.viaversion.VelocityPlugin;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.util.ReflectionUtil;
/*     */ import com.viaversion.viaversion.velocity.handlers.VelocityChannelInitializer;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class VelocityViaInjector
/*     */   implements ViaInjector
/*     */ {
/*     */   public static Method getPlayerInfoForwardingMode;
/*     */   
/*     */   static {
/*     */     try {
/*  39 */       getPlayerInfoForwardingMode = Class.forName("com.velocitypowered.proxy.config.VelocityConfiguration").getMethod("getPlayerInfoForwardingMode", new Class[0]);
/*  40 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/*  41 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private ChannelInitializer getInitializer() throws Exception {
/*  46 */     Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
/*  47 */     Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
/*  48 */     return (ChannelInitializer)ReflectionUtil.invoke(channelInitializerHolder, "get");
/*     */   }
/*     */   
/*     */   private ChannelInitializer getBackendInitializer() throws Exception {
/*  52 */     Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
/*  53 */     Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
/*  54 */     return (ChannelInitializer)ReflectionUtil.invoke(channelInitializerHolder, "get");
/*     */   }
/*     */ 
/*     */   
/*     */   public void inject() throws Exception {
/*  59 */     Via.getPlatform().getLogger().info("Replacing channel initializers; you can safely ignore the following two warnings.");
/*     */     
/*  61 */     Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
/*  62 */     Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
/*  63 */     ChannelInitializer originalInitializer = getInitializer();
/*  64 */     channelInitializerHolder.getClass().getMethod("set", new Class[] { ChannelInitializer.class
/*  65 */         }).invoke(channelInitializerHolder, new Object[] { new VelocityChannelInitializer(originalInitializer, false) });
/*     */     
/*  67 */     Object backendInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
/*  68 */     ChannelInitializer backendInitializer = getBackendInitializer();
/*  69 */     backendInitializerHolder.getClass().getMethod("set", new Class[] { ChannelInitializer.class
/*  70 */         }).invoke(backendInitializerHolder, new Object[] { new VelocityChannelInitializer(backendInitializer, true) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninject() {
/*  75 */     Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Velocity without a reboot!");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerProtocolVersion() throws Exception {
/*  80 */     return getLowestSupportedProtocolVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSortedSet getServerProtocolVersions() throws Exception {
/*  85 */     int lowestSupportedProtocolVersion = getLowestSupportedProtocolVersion();
/*     */     
/*  87 */     IntLinkedOpenHashSet intLinkedOpenHashSet = new IntLinkedOpenHashSet();
/*  88 */     for (ProtocolVersion version : ProtocolVersion.SUPPORTED_VERSIONS) {
/*  89 */       if (version.getProtocol() >= lowestSupportedProtocolVersion) {
/*  90 */         intLinkedOpenHashSet.add(version.getProtocol());
/*     */       }
/*     */     } 
/*  93 */     return (IntSortedSet)intLinkedOpenHashSet;
/*     */   }
/*     */   
/*     */   public static int getLowestSupportedProtocolVersion() {
/*     */     try {
/*  98 */       if (getPlayerInfoForwardingMode != null && ((Enum)getPlayerInfoForwardingMode
/*  99 */         .invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0]))
/* 100 */         .name().equals("MODERN")) {
/* 101 */         return ProtocolVersion.v1_13.getVersion();
/*     */       }
/* 103 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */     
/* 105 */     return ProtocolVersion.MINIMUM_VERSION.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 110 */     JsonObject data = new JsonObject();
/*     */     try {
/* 112 */       data.addProperty("currentInitializer", getInitializer().getClass().getName());
/* 113 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 116 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\platform\VelocityViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */