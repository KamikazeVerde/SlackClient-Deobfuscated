/*     */ package com.viaversion.viaversion.bungee.handlers;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.bungee.storage.BungeeStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.event.ServerConnectEvent;
/*     */ import net.md_5.bungee.api.event.ServerConnectedEvent;
/*     */ import net.md_5.bungee.api.event.ServerSwitchEvent;
/*     */ import net.md_5.bungee.api.plugin.Listener;
/*     */ import net.md_5.bungee.api.score.Team;
/*     */ import net.md_5.bungee.event.EventHandler;
/*     */ import net.md_5.bungee.protocol.packet.PluginMessage;
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
/*     */ public class BungeeServerHandler
/*     */   implements Listener
/*     */ {
/*     */   private static Method getHandshake;
/*     */   private static Method getRegisteredChannels;
/*     */   private static Method getBrandMessage;
/*     */   private static Method setProtocol;
/*  61 */   private static Method getEntityMap = null;
/*  62 */   private static Method setVersion = null;
/*  63 */   private static Field entityRewrite = null;
/*  64 */   private static Field channelWrapper = null;
/*     */   
/*     */   static {
/*     */     try {
/*  68 */       getHandshake = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getHandshake", new Class[0]);
/*  69 */       getRegisteredChannels = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getRegisteredChannels", new Class[0]);
/*  70 */       getBrandMessage = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getBrandMessage", new Class[0]);
/*  71 */       setProtocol = Class.forName("net.md_5.bungee.protocol.packet.Handshake").getDeclaredMethod("setProtocolVersion", new Class[] { int.class });
/*  72 */       getEntityMap = Class.forName("net.md_5.bungee.entitymap.EntityMap").getDeclaredMethod("getEntityMap", new Class[] { int.class });
/*  73 */       setVersion = Class.forName("net.md_5.bungee.netty.ChannelWrapper").getDeclaredMethod("setVersion", new Class[] { int.class });
/*  74 */       channelWrapper = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("ch");
/*  75 */       channelWrapper.setAccessible(true);
/*  76 */       entityRewrite = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("entityRewrite");
/*  77 */       entityRewrite.setAccessible(true);
/*  78 */     } catch (Exception e) {
/*  79 */       Via.getPlatform().getLogger().severe("Error initializing BungeeServerHandler, try updating BungeeCord or ViaVersion!");
/*  80 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 120)
/*     */   public void onServerConnect(ServerConnectEvent e) {
/*  87 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId());
/*  92 */     if (user == null)
/*  93 */       return;  if (!user.has(BungeeStorage.class)) {
/*  94 */       user.put((StorableObject)new BungeeStorage(e.getPlayer()));
/*     */     }
/*     */     
/*  97 */     int protocolId = Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(e.getTarget().getName());
/*  98 */     List<ProtocolPathEntry> protocols = Via.getManager().getProtocolManager().getProtocolPath(user.getProtocolInfo().getProtocolVersion(), protocolId);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       Object handshake = getHandshake.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
/* 104 */       setProtocol.invoke(handshake, new Object[] { Integer.valueOf((protocols == null) ? user.getProtocolInfo().getProtocolVersion() : protocolId) });
/* 105 */     } catch (InvocationTargetException|IllegalAccessException e1) {
/* 106 */       e1.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = -120)
/*     */   public void onServerConnected(ServerConnectedEvent e) {
/*     */     try {
/* 113 */       checkServerChange(e, Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId()));
/* 114 */     } catch (Exception e1) {
/* 115 */       e1.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = -120)
/*     */   public void onServerSwitch(ServerSwitchEvent e) {
/*     */     int playerId;
/* 122 */     UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId());
/* 123 */     if (userConnection == null)
/*     */       return; 
/*     */     try {
/* 126 */       playerId = ((EntityIdProvider)Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(userConnection);
/* 127 */     } catch (Exception ex) {
/*     */       return;
/*     */     } 
/*     */     
/* 131 */     for (EntityTracker tracker : userConnection.getEntityTrackers()) {
/* 132 */       tracker.setClientEntityId(playerId);
/*     */     }
/*     */ 
/*     */     
/* 136 */     for (StorableObject object : userConnection.getStoredObjects().values()) {
/* 137 */       if (object instanceof ClientEntityIdChangeListener) {
/* 138 */         ((ClientEntityIdChangeListener)object).setClientEntityId(playerId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkServerChange(ServerConnectedEvent e, UserConnection user) throws Exception {
/* 144 */     if (user == null) {
/*     */       return;
/*     */     }
/* 147 */     if (user.has(BungeeStorage.class)) {
/* 148 */       BungeeStorage storage = (BungeeStorage)user.get(BungeeStorage.class);
/* 149 */       ProxiedPlayer player = storage.getPlayer();
/*     */       
/* 151 */       if (e.getServer() != null && 
/* 152 */         !e.getServer().getInfo().getName().equals(storage.getCurrentServer())) {
/*     */         
/* 154 */         EntityTracker1_9 oldEntityTracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
/* 155 */         if (oldEntityTracker != null && 
/* 156 */           oldEntityTracker.isAutoTeam() && oldEntityTracker.isTeamExists()) {
/* 157 */           oldEntityTracker.sendTeamPacket(false, true);
/*     */         }
/*     */ 
/*     */         
/* 161 */         String serverName = e.getServer().getInfo().getName();
/*     */         
/* 163 */         storage.setCurrentServer(serverName);
/*     */         
/* 165 */         int protocolId = Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(serverName);
/*     */         
/* 167 */         if (protocolId <= ProtocolVersion.v1_8.getVersion() && 
/* 168 */           storage.getBossbar() != null) {
/*     */           
/* 170 */           if (user.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
/* 171 */             for (UUID uuid : storage.getBossbar()) {
/* 172 */               PacketWrapper packetWrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.BOSSBAR, null, user);
/* 173 */               packetWrapper.write(Type.UUID, uuid);
/* 174 */               packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/* 175 */               packetWrapper.send(Protocol1_9To1_8.class);
/*     */             } 
/*     */           }
/* 178 */           storage.getBossbar().clear();
/*     */         } 
/*     */ 
/*     */         
/* 182 */         ProtocolInfo info = user.getProtocolInfo();
/* 183 */         int previousServerProtocol = info.getServerProtocolVersion();
/*     */ 
/*     */         
/* 186 */         List<ProtocolPathEntry> protocolPath = Via.getManager().getProtocolManager().getProtocolPath(info.getProtocolVersion(), protocolId);
/* 187 */         ProtocolPipeline pipeline = user.getProtocolInfo().getPipeline();
/* 188 */         user.clearStoredObjects(true);
/* 189 */         pipeline.cleanPipes();
/* 190 */         if (protocolPath == null) {
/*     */           
/* 192 */           protocolId = info.getProtocolVersion();
/*     */         } else {
/* 194 */           List<Protocol> protocols = new ArrayList<>(protocolPath.size());
/* 195 */           for (ProtocolPathEntry entry : protocolPath) {
/* 196 */             protocols.add(entry.protocol());
/*     */           }
/* 198 */           pipeline.add(protocols);
/*     */         } 
/*     */         
/* 201 */         info.setServerProtocolVersion(protocolId);
/*     */         
/* 203 */         pipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(protocolId));
/*     */ 
/*     */         
/* 206 */         int id1_13 = ProtocolVersion.v1_13.getVersion();
/* 207 */         boolean toNewId = (previousServerProtocol < id1_13 && protocolId >= id1_13);
/* 208 */         boolean toOldId = (previousServerProtocol >= id1_13 && protocolId < id1_13);
/* 209 */         if (previousServerProtocol != -1 && (toNewId || toOldId)) {
/* 210 */           Collection<String> registeredChannels = (Collection<String>)getRegisteredChannels.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
/* 211 */           if (!registeredChannels.isEmpty()) {
/* 212 */             Collection<String> newChannels = new HashSet<>();
/* 213 */             for (Iterator<String> iterator = registeredChannels.iterator(); iterator.hasNext(); ) {
/* 214 */               String channel = iterator.next();
/* 215 */               String oldChannel = channel;
/* 216 */               if (toNewId) {
/* 217 */                 channel = InventoryPackets.getNewPluginChannelId(channel);
/*     */               } else {
/* 219 */                 channel = InventoryPackets.getOldPluginChannelId(channel);
/*     */               } 
/* 221 */               if (channel == null) {
/* 222 */                 iterator.remove();
/*     */                 continue;
/*     */               } 
/* 225 */               if (!oldChannel.equals(channel)) {
/* 226 */                 iterator.remove();
/* 227 */                 newChannels.add(channel);
/*     */               } 
/*     */             } 
/* 230 */             registeredChannels.addAll(newChannels);
/*     */           } 
/* 232 */           PluginMessage brandMessage = (PluginMessage)getBrandMessage.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
/* 233 */           if (brandMessage != null) {
/* 234 */             String channel = brandMessage.getTag();
/* 235 */             if (toNewId) {
/* 236 */               channel = InventoryPackets.getNewPluginChannelId(channel);
/*     */             } else {
/* 238 */               channel = InventoryPackets.getOldPluginChannelId(channel);
/*     */             } 
/* 240 */             if (channel != null) {
/* 241 */               brandMessage.setTag(channel);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 246 */         user.put((StorableObject)storage);
/*     */         
/* 248 */         user.setActive((protocolPath != null));
/*     */ 
/*     */         
/* 251 */         for (Protocol protocol : pipeline.pipes()) {
/* 252 */           protocol.init(user);
/*     */         }
/*     */         
/* 255 */         EntityTracker1_9 newTracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
/* 256 */         if (newTracker != null && 
/* 257 */           Via.getConfig().isAutoTeam()) {
/* 258 */           String currentTeam = null;
/* 259 */           for (Team team : player.getScoreboard().getTeams()) {
/* 260 */             if (team.getPlayers().contains(info.getUsername())) {
/* 261 */               currentTeam = team.getName();
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 267 */           newTracker.setAutoTeam(true);
/* 268 */           if (currentTeam == null) {
/*     */             
/* 270 */             newTracker.sendTeamPacket(true, true);
/* 271 */             newTracker.setCurrentTeam("viaversion");
/*     */           } else {
/*     */             
/* 274 */             newTracker.setAutoTeam(Via.getConfig().isAutoTeam());
/* 275 */             newTracker.setCurrentTeam(currentTeam);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 280 */         Object wrapper = channelWrapper.get(player);
/* 281 */         setVersion.invoke(wrapper, new Object[] { Integer.valueOf(protocolId) });
/*     */         
/* 283 */         Object entityMap = getEntityMap.invoke(null, new Object[] { Integer.valueOf(protocolId) });
/* 284 */         entityRewrite.set(player, entityMap);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\handlers\BungeeServerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */