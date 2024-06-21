/*    */ package com.viaversion.viaversion.bukkit.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
/*    */ import io.netty.channel.Channel;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Proxy;
/*    */ import net.kyori.adventure.key.Key;
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
/*    */ public final class PaperViaInjector
/*    */ {
/* 27 */   public static final boolean PAPER_INJECTION_METHOD = hasPaperInjectionMethod();
/* 28 */   public static final boolean PAPER_PROTOCOL_METHOD = hasServerProtocolMethod();
/* 29 */   public static final boolean PAPER_PACKET_LIMITER = hasPacketLimiter();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setPaperChannelInitializeListener() throws ReflectiveOperationException {
/* 37 */     Class<?> listenerClass = Class.forName("io.papermc.paper.network.ChannelInitializeListener");
/* 38 */     Object channelInitializeListener = Proxy.newProxyInstance(BukkitViaInjector.class.getClassLoader(), new Class[] { listenerClass }, (proxy, method, args) -> {
/*    */           if (method.getName().equals("afterInitChannel")) {
/*    */             BukkitChannelInitializer.afterChannelInitialize((Channel)args[0]);
/*    */             
/*    */             return null;
/*    */           } 
/*    */           return method.invoke(proxy, args);
/*    */         });
/* 46 */     Class<?> holderClass = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
/* 47 */     Method addListenerMethod = holderClass.getDeclaredMethod("addListener", new Class[] { Key.class, listenerClass });
/* 48 */     addListenerMethod.invoke(null, new Object[] { Key.key("viaversion", "injector"), channelInitializeListener });
/*    */   }
/*    */   
/*    */   public static void removePaperChannelInitializeListener() throws ReflectiveOperationException {
/* 52 */     Class<?> holderClass = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
/* 53 */     Method addListenerMethod = holderClass.getDeclaredMethod("removeListener", new Class[] { Key.class });
/* 54 */     addListenerMethod.invoke(null, new Object[] { Key.key("viaversion", "injector") });
/*    */   }
/*    */   
/*    */   private static boolean hasServerProtocolMethod() {
/*    */     try {
/* 59 */       Class.forName("org.bukkit.UnsafeValues").getDeclaredMethod("getProtocolVersion", new Class[0]);
/* 60 */       return true;
/* 61 */     } catch (ReflectiveOperationException e) {
/* 62 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   private static boolean hasPaperInjectionMethod() {
/* 67 */     return hasClass("io.papermc.paper.network.ChannelInitializeListener");
/*    */   }
/*    */   
/*    */   private static boolean hasPacketLimiter() {
/* 71 */     return (hasClass("com.destroystokyo.paper.PaperConfig$PacketLimit") || hasClass("io.papermc.paper.configuration.GlobalConfiguration$PacketLimiter"));
/*    */   }
/*    */   
/*    */   public static boolean hasClass(String className) {
/*    */     try {
/* 76 */       Class.forName(className);
/* 77 */       return true;
/* 78 */     } catch (ReflectiveOperationException e) {
/* 79 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\PaperViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */