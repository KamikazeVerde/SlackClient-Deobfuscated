/*     */ package com.viaversion.viaversion.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import com.viaversion.viaversion.util.ReflectionUtil;
/*     */ import com.viaversion.viaversion.util.SynchronizedListWrapper;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public abstract class LegacyViaInjector
/*     */   implements ViaInjector
/*     */ {
/*  38 */   protected final List<ChannelFuture> injectedFutures = new ArrayList<>();
/*  39 */   protected final List<Pair<Field, Object>> injectedLists = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public void inject() throws ReflectiveOperationException {
/*  43 */     Object connection = getServerConnection();
/*  44 */     if (connection == null) {
/*  45 */       throw new RuntimeException("Failed to find the core component 'ServerConnection'");
/*     */     }
/*     */ 
/*     */     
/*  49 */     for (Field field : connection.getClass().getDeclaredFields()) {
/*     */       
/*  51 */       if (List.class.isAssignableFrom(field.getType()) && field.getGenericType().getTypeName().contains(ChannelFuture.class.getName())) {
/*     */ 
/*     */ 
/*     */         
/*  55 */         field.setAccessible(true);
/*  56 */         List<ChannelFuture> list = (List<ChannelFuture>)field.get(connection);
/*  57 */         SynchronizedListWrapper synchronizedListWrapper = new SynchronizedListWrapper(list, o -> {
/*     */               
/*     */               try {
/*     */                 injectChannelFuture((ChannelFuture)o);
/*  61 */               } catch (ReflectiveOperationException e) {
/*     */                 throw new RuntimeException(e);
/*     */               } 
/*     */             });
/*     */ 
/*     */         
/*  67 */         synchronized (list) {
/*     */           
/*  69 */           for (ChannelFuture future : list) {
/*  70 */             injectChannelFuture(future);
/*     */           }
/*     */           
/*  73 */           field.set(connection, synchronizedListWrapper);
/*     */         } 
/*     */         
/*  76 */         this.injectedLists.add(new Pair(field, connection));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void injectChannelFuture(ChannelFuture future) throws ReflectiveOperationException {
/*  81 */     List<String> names = future.channel().pipeline().names();
/*  82 */     ChannelHandler bootstrapAcceptor = null;
/*     */     
/*  84 */     for (String name : names) {
/*  85 */       ChannelHandler handler = future.channel().pipeline().get(name);
/*     */       try {
/*  87 */         ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class);
/*  88 */         bootstrapAcceptor = handler;
/*     */         break;
/*  90 */       } catch (ReflectiveOperationException reflectiveOperationException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (bootstrapAcceptor == null)
/*     */     {
/*  97 */       bootstrapAcceptor = future.channel().pipeline().first();
/*     */     }
/*     */     
/*     */     try {
/* 101 */       ChannelInitializer<Channel> oldInitializer = (ChannelInitializer<Channel>)ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
/* 102 */       ReflectionUtil.set(bootstrapAcceptor, "childHandler", createChannelInitializer(oldInitializer));
/* 103 */       this.injectedFutures.add(future);
/* 104 */     } catch (NoSuchFieldException ignored) {
/* 105 */       blame(bootstrapAcceptor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninject() throws ReflectiveOperationException {
/* 112 */     for (ChannelFuture future : this.injectedFutures) {
/*     */       
/* 114 */       ChannelPipeline pipeline = future.channel().pipeline();
/* 115 */       ChannelHandler bootstrapAcceptor = pipeline.first();
/* 116 */       if (bootstrapAcceptor == null) {
/* 117 */         Via.getPlatform().getLogger().info("Empty pipeline, nothing to uninject");
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 122 */       for (String name : pipeline.names()) {
/* 123 */         ChannelHandler handler = pipeline.get(name);
/* 124 */         if (handler == null) {
/* 125 */           Via.getPlatform().getLogger().warning("Could not get handler " + name);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         try {
/* 130 */           if (ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class) instanceof WrappedChannelInitializer) {
/* 131 */             bootstrapAcceptor = handler;
/*     */             break;
/*     */           } 
/* 134 */         } catch (ReflectiveOperationException reflectiveOperationException) {}
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 139 */         ChannelInitializer<Channel> initializer = (ChannelInitializer<Channel>)ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
/* 140 */         if (initializer instanceof WrappedChannelInitializer) {
/* 141 */           ReflectionUtil.set(bootstrapAcceptor, "childHandler", ((WrappedChannelInitializer)initializer).original());
/*     */         }
/* 143 */       } catch (Exception e) {
/* 144 */         Via.getPlatform().getLogger().severe("Failed to remove injection handler, reload won't work with connections, please reboot!");
/* 145 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     this.injectedFutures.clear();
/*     */     
/* 151 */     for (Pair<Field, Object> pair : this.injectedLists) {
/*     */       try {
/* 153 */         Field field = (Field)pair.key();
/* 154 */         Object o = field.get(pair.value());
/* 155 */         if (o instanceof SynchronizedListWrapper) {
/* 156 */           List<ChannelFuture> originalList = ((SynchronizedListWrapper)o).originalList();
/* 157 */           synchronized (originalList) {
/* 158 */             field.set(pair.value(), originalList);
/*     */           } 
/*     */         } 
/* 161 */       } catch (ReflectiveOperationException e) {
/* 162 */         Via.getPlatform().getLogger().severe("Failed to remove injection, reload won't work with connections, please reboot!");
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     this.injectedLists.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean lateProtocolVersionSetting() {
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 176 */     JsonObject data = new JsonObject();
/*     */ 
/*     */     
/* 179 */     JsonArray injectedChannelInitializers = new JsonArray();
/* 180 */     data.add("injectedChannelInitializers", (JsonElement)injectedChannelInitializers);
/* 181 */     for (ChannelFuture future : this.injectedFutures) {
/* 182 */       JsonObject futureInfo = new JsonObject();
/* 183 */       injectedChannelInitializers.add((JsonElement)futureInfo);
/*     */       
/* 185 */       futureInfo.addProperty("futureClass", future.getClass().getName());
/* 186 */       futureInfo.addProperty("channelClass", future.channel().getClass().getName());
/*     */ 
/*     */       
/* 189 */       JsonArray pipeline = new JsonArray();
/* 190 */       futureInfo.add("pipeline", (JsonElement)pipeline);
/* 191 */       for (String pipeName : future.channel().pipeline().names()) {
/* 192 */         JsonObject handlerInfo = new JsonObject();
/* 193 */         pipeline.add((JsonElement)handlerInfo);
/*     */         
/* 195 */         handlerInfo.addProperty("name", pipeName);
/* 196 */         ChannelHandler channelHandler = future.channel().pipeline().get(pipeName);
/* 197 */         if (channelHandler == null) {
/* 198 */           handlerInfo.addProperty("status", "INVALID");
/*     */           
/*     */           continue;
/*     */         } 
/* 202 */         handlerInfo.addProperty("class", channelHandler.getClass().getName());
/*     */         try {
/* 204 */           Object child = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
/* 205 */           handlerInfo.addProperty("childClass", child.getClass().getName());
/* 206 */           if (child instanceof WrappedChannelInitializer) {
/* 207 */             handlerInfo.addProperty("oldInit", ((WrappedChannelInitializer)child).original().getClass().getName());
/*     */           }
/* 209 */         } catch (ReflectiveOperationException reflectiveOperationException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     JsonObject wrappedLists = new JsonObject();
/* 217 */     JsonObject currentLists = new JsonObject();
/*     */     try {
/* 219 */       for (Pair<Field, Object> pair : this.injectedLists) {
/* 220 */         Field field = (Field)pair.key();
/* 221 */         Object list = field.get(pair.value());
/*     */         
/* 223 */         currentLists.addProperty(field.getName(), list.getClass().getName());
/*     */         
/* 225 */         if (list instanceof SynchronizedListWrapper) {
/* 226 */           wrappedLists.addProperty(field.getName(), ((SynchronizedListWrapper)list).originalList().getClass().getName());
/*     */         }
/*     */       } 
/* 229 */       data.add("wrappedLists", (JsonElement)wrappedLists);
/* 230 */       data.add("currentLists", (JsonElement)currentLists);
/* 231 */     } catch (ReflectiveOperationException reflectiveOperationException) {}
/*     */ 
/*     */     
/* 234 */     return data;
/*     */   }
/*     */   
/*     */   protected abstract Object getServerConnection() throws ReflectiveOperationException;
/*     */   
/*     */   protected abstract WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> paramChannelInitializer);
/*     */   
/*     */   protected abstract void blame(ChannelHandler paramChannelHandler) throws ReflectiveOperationException;
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\platform\LegacyViaInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */