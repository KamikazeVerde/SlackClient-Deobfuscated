/*     */ package com.viaversion.viaversion.bungee.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.bungee.handlers.BungeeChannelInitializer;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.util.ReflectionUtil;
/*     */ import com.viaversion.viaversion.util.SetWrapper;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.md_5.bungee.api.ProxyServer;
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
/*     */ public class BungeeViaInjector
/*     */   implements ViaInjector
/*     */ {
/*     */   private static final Field LISTENERS_FIELD;
/*  41 */   private final List<Channel> injectedChannels = new ArrayList<>();
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       LISTENERS_FIELD = ProxyServer.getInstance().getClass().getDeclaredField("listeners");
/*  46 */       LISTENERS_FIELD.setAccessible(true);
/*  47 */     } catch (ReflectiveOperationException e) {
/*  48 */       throw new RuntimeException("Unable to access listeners field.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() throws ReflectiveOperationException {
/*  55 */     Set<Channel> listeners = (Set<Channel>)LISTENERS_FIELD.get(ProxyServer.getInstance());
/*     */ 
/*     */     
/*  58 */     SetWrapper setWrapper = new SetWrapper(listeners, channel -> {
/*     */           try {
/*     */             injectChannel(channel);
/*  61 */           } catch (Exception e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */     
/*  66 */     LISTENERS_FIELD.set(ProxyServer.getInstance(), setWrapper);
/*     */ 
/*     */     
/*  69 */     for (Channel channel : listeners) {
/*  70 */       injectChannel(channel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninject() {
/*  76 */     Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Bungee without a reboot!");
/*     */   }
/*     */ 
/*     */   
/*     */   private void injectChannel(Channel channel) throws ReflectiveOperationException {
/*  81 */     List<String> names = channel.pipeline().names();
/*  82 */     ChannelHandler bootstrapAcceptor = null;
/*     */     
/*  84 */     for (String name : names) {
/*  85 */       ChannelHandler handler = channel.pipeline().get(name);
/*     */       try {
/*  87 */         ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class);
/*  88 */         bootstrapAcceptor = handler;
/*  89 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (bootstrapAcceptor == null) {
/*  96 */       bootstrapAcceptor = channel.pipeline().first();
/*     */     }
/*     */     
/*  99 */     if (bootstrapAcceptor.getClass().getName().equals("net.md_5.bungee.query.QueryHandler")) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 104 */       ChannelInitializer<Channel> oldInit = (ChannelInitializer<Channel>)ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
/* 105 */       BungeeChannelInitializer bungeeChannelInitializer = new BungeeChannelInitializer(oldInit);
/*     */       
/* 107 */       ReflectionUtil.set(bootstrapAcceptor, "childHandler", bungeeChannelInitializer);
/* 108 */       this.injectedChannels.add(channel);
/* 109 */     } catch (NoSuchFieldException e) {
/* 110 */       throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerProtocolVersion() throws Exception {
/* 116 */     return ((Integer)getBungeeSupportedVersions().get(0)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSortedSet getServerProtocolVersions() throws Exception {
/* 121 */     return (IntSortedSet)new IntLinkedOpenHashSet(getBungeeSupportedVersions());
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Integer> getBungeeSupportedVersions() throws Exception {
/* 126 */     return (List<Integer>)ReflectionUtil.getStatic(Class.forName("net.md_5.bungee.protocol.ProtocolConstants"), "SUPPORTED_VERSION_IDS", List.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 131 */     JsonObject data = new JsonObject();
/*     */ 
/*     */     
/* 134 */     JsonArray injectedChannelInitializers = new JsonArray();
/* 135 */     for (Channel channel : this.injectedChannels) {
/* 136 */       JsonObject channelInfo = new JsonObject();
/* 137 */       channelInfo.addProperty("channelClass", channel.getClass().getName());
/*     */ 
/*     */       
/* 140 */       JsonArray pipeline = new JsonArray();
/* 141 */       for (String pipeName : channel.pipeline().names()) {
/* 142 */         JsonObject handlerInfo = new JsonObject();
/* 143 */         handlerInfo.addProperty("name", pipeName);
/*     */         
/* 145 */         ChannelHandler channelHandler = channel.pipeline().get(pipeName);
/* 146 */         if (channelHandler == null) {
/* 147 */           handlerInfo.addProperty("status", "INVALID");
/*     */           
/*     */           continue;
/*     */         } 
/* 151 */         handlerInfo.addProperty("class", channelHandler.getClass().getName());
/*     */         
/*     */         try {
/* 154 */           Object child = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
/* 155 */           handlerInfo.addProperty("childClass", child.getClass().getName());
/* 156 */           if (child instanceof BungeeChannelInitializer) {
/* 157 */             handlerInfo.addProperty("oldInit", ((BungeeChannelInitializer)child).getOriginal().getClass().getName());
/*     */           }
/* 159 */         } catch (ReflectiveOperationException reflectiveOperationException) {}
/*     */ 
/*     */ 
/*     */         
/* 163 */         pipeline.add((JsonElement)handlerInfo);
/*     */       } 
/* 165 */       channelInfo.add("pipeline", (JsonElement)pipeline);
/*     */       
/* 167 */       injectedChannelInitializers.add((JsonElement)channelInfo);
/*     */     } 
/*     */     
/* 170 */     data.add("injectedChannelInitializers", (JsonElement)injectedChannelInitializers);
/*     */     
/*     */     try {
/* 173 */       Object list = LISTENERS_FIELD.get(ProxyServer.getInstance());
/* 174 */       data.addProperty("currentList", list.getClass().getName());
/* 175 */       if (list instanceof SetWrapper) {
/* 176 */         data.addProperty("wrappedList", ((SetWrapper)list).originalSet().getClass().getName());
/*     */       }
/* 178 */     } catch (ReflectiveOperationException reflectiveOperationException) {}
/*     */ 
/*     */ 
/*     */     
/* 182 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\platform\BungeeViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */