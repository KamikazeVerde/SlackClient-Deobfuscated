/*     */ package com.viaversion.viaversion.protocols.base;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
/*     */ import com.viaversion.viaversion.api.protocol.version.VersionProvider;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
/*     */ import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.util.ChatColorUtil;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
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
/*     */ public class BaseProtocol1_7
/*     */   extends AbstractProtocol
/*     */ {
/*     */   protected void registerPackets() {
/*  51 */     registerClientbound(ClientboundStatusPackets.STATUS_RESPONSE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  54 */             map(Type.STRING);
/*  55 */             handler(wrapper -> {
/*     */                   ProtocolInfo info = wrapper.user().getProtocolInfo();
/*     */                   
/*     */                   String originalStatus = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   try {
/*     */                     JsonObject jsonObject1;
/*     */                     
/*     */                     JsonObject version;
/*     */                     
/*     */                     JsonElement json = (JsonElement)GsonUtil.getGson().fromJson(originalStatus, JsonElement.class);
/*     */                     
/*     */                     int protocolVersion = 0;
/*     */                     
/*     */                     if (json.isJsonObject()) {
/*     */                       if (json.getAsJsonObject().has("version")) {
/*     */                         version = json.getAsJsonObject().get("version").getAsJsonObject();
/*     */                         
/*     */                         if (version.has("protocol")) {
/*     */                           protocolVersion = Long.valueOf(version.get("protocol").getAsLong()).intValue();
/*     */                         }
/*     */                       } else {
/*     */                         json.getAsJsonObject().add("version", (JsonElement)(version = new JsonObject()));
/*     */                       } 
/*     */                     } else {
/*     */                       jsonObject1 = new JsonObject();
/*     */                       
/*     */                       jsonObject1.getAsJsonObject().add("version", (JsonElement)(version = new JsonObject()));
/*     */                     } 
/*     */                     
/*     */                     if (Via.getConfig().isSendSupportedVersions()) {
/*     */                       version.add("supportedVersions", GsonUtil.getGson().toJsonTree(Via.getAPI().getSupportedVersions()));
/*     */                     }
/*     */                     
/*     */                     if (!Via.getAPI().getServerVersion().isKnown()) {
/*     */                       ProtocolManagerImpl protocolManager = (ProtocolManagerImpl)Via.getManager().getProtocolManager();
/*     */                       protocolManager.setServerProtocol((ServerProtocolVersion)new ServerProtocolVersionSingleton(ProtocolVersion.getProtocol(protocolVersion).getVersion()));
/*     */                     } 
/*     */                     VersionProvider versionProvider = (VersionProvider)Via.getManager().getProviders().get(VersionProvider.class);
/*     */                     if (versionProvider == null) {
/*     */                       wrapper.user().setActive(false);
/*     */                       return;
/*     */                     } 
/*     */                     int closestServerProtocol = versionProvider.getClosestServerProtocol(wrapper.user());
/*     */                     List<ProtocolPathEntry> protocols = null;
/*     */                     if (info.getProtocolVersion() >= closestServerProtocol || Via.getPlatform().isOldClientsAllowed()) {
/*     */                       protocols = Via.getManager().getProtocolManager().getProtocolPath(info.getProtocolVersion(), closestServerProtocol);
/*     */                     }
/*     */                     if (protocols != null) {
/*     */                       if (protocolVersion == closestServerProtocol || protocolVersion == 0) {
/*     */                         ProtocolVersion prot = ProtocolVersion.getProtocol(info.getProtocolVersion());
/*     */                         version.addProperty("protocol", Integer.valueOf(prot.getOriginalVersion()));
/*     */                       } 
/*     */                     } else {
/*     */                       wrapper.user().setActive(false);
/*     */                     } 
/*     */                     if (Via.getConfig().blockedProtocolVersions().contains(info.getProtocolVersion())) {
/*     */                       version.addProperty("protocol", Integer.valueOf(-1));
/*     */                     }
/*     */                     wrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson((JsonElement)jsonObject1));
/* 115 */                   } catch (JsonParseException e) {
/*     */                     e.printStackTrace();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 123 */     registerClientbound(ClientboundLoginPackets.GAME_PROFILE, wrapper -> {
/*     */           ProtocolInfo info = wrapper.user().getProtocolInfo();
/*     */ 
/*     */           
/*     */           info.setState(State.PLAY);
/*     */ 
/*     */           
/*     */           UUID uuid = passthroughLoginUUID(wrapper);
/*     */ 
/*     */           
/*     */           info.setUuid(uuid);
/*     */ 
/*     */           
/*     */           String username = (String)wrapper.passthrough(Type.STRING);
/*     */ 
/*     */           
/*     */           info.setUsername(username);
/*     */           
/*     */           Via.getManager().getConnectionManager().onLoginSuccess(wrapper.user());
/*     */           
/*     */           if (!info.getPipeline().hasNonBaseProtocols()) {
/*     */             wrapper.user().setActive(false);
/*     */           }
/*     */           
/*     */           if (Via.getManager().isDebug()) {
/*     */             Via.getPlatform().getLogger().log(Level.INFO, "{0} logged in with protocol {1}, Route: {2}", new Object[] { username, Integer.valueOf(info.getProtocolVersion()), Joiner.on(", ").join(info.getPipeline().pipes(), ", ", new Object[0]) });
/*     */           }
/*     */         });
/*     */     
/* 152 */     registerServerbound(ServerboundLoginPackets.HELLO, wrapper -> {
/*     */           int protocol = wrapper.user().getProtocolInfo().getProtocolVersion();
/*     */           if (Via.getConfig().blockedProtocolVersions().contains(protocol)) {
/*     */             if (!wrapper.user().getChannel().isOpen()) {
/*     */               return;
/*     */             }
/*     */             if (!wrapper.user().shouldApplyBlockProtocol()) {
/*     */               return;
/*     */             }
/*     */             PacketWrapper disconnectPacket = PacketWrapper.create((PacketType)ClientboundLoginPackets.LOGIN_DISCONNECT, wrapper.user());
/*     */             Protocol1_9To1_8.FIX_JSON.write(disconnectPacket, ChatColorUtil.translateAlternateColorCodes(Via.getConfig().getBlockedDisconnectMsg()));
/*     */             wrapper.cancel();
/*     */             ChannelFuture future = disconnectPacket.sendFuture(BaseProtocol.class);
/*     */             future.addListener(());
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean isBaseProtocol() {
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   public static String addDashes(String trimmedUUID) {
/* 175 */     StringBuilder idBuff = new StringBuilder(trimmedUUID);
/* 176 */     idBuff.insert(20, '-');
/* 177 */     idBuff.insert(16, '-');
/* 178 */     idBuff.insert(12, '-');
/* 179 */     idBuff.insert(8, '-');
/* 180 */     return idBuff.toString();
/*     */   }
/*     */   
/*     */   protected UUID passthroughLoginUUID(PacketWrapper wrapper) throws Exception {
/* 184 */     String uuidString = (String)wrapper.passthrough(Type.STRING);
/* 185 */     if (uuidString.length() == 32)
/*     */     {
/* 187 */       uuidString = addDashes(uuidString);
/*     */     }
/* 189 */     return UUID.fromString(uuidString);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\BaseProtocol1_7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */