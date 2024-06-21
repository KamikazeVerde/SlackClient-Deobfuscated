/*     */ package com.viaversion.viaversion.protocol.packet;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
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
/*     */ public class VersionedPacketTransformerImpl<C extends ClientboundPacketType, S extends ServerboundPacketType>
/*     */   implements VersionedPacketTransformer<C, S>
/*     */ {
/*     */   private final int inputProtocolVersion;
/*     */   private final Class<C> clientboundPacketsClass;
/*     */   private final Class<S> serverboundPacketsClass;
/*     */   
/*     */   public VersionedPacketTransformerImpl(ProtocolVersion inputVersion, Class<C> clientboundPacketsClass, Class<S> serverboundPacketsClass) {
/*  45 */     Preconditions.checkNotNull(inputVersion);
/*  46 */     Preconditions.checkArgument((clientboundPacketsClass != null || serverboundPacketsClass != null), "Either the clientbound or serverbound packets class has to be non-null");
/*     */     
/*  48 */     this.inputProtocolVersion = inputVersion.getVersion();
/*  49 */     this.clientboundPacketsClass = clientboundPacketsClass;
/*  50 */     this.serverboundPacketsClass = serverboundPacketsClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean send(PacketWrapper packet) throws Exception {
/*  55 */     validatePacket(packet);
/*  56 */     return transformAndSendPacket(packet, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean send(UserConnection connection, C packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/*  61 */     return createAndSend(connection, (PacketType)packetType, packetWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean send(UserConnection connection, S packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/*  66 */     return createAndSend(connection, (PacketType)packetType, packetWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean scheduleSend(PacketWrapper packet) throws Exception {
/*  71 */     validatePacket(packet);
/*  72 */     return transformAndSendPacket(packet, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean scheduleSend(UserConnection connection, C packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/*  77 */     return scheduleCreateAndSend(connection, (PacketType)packetType, packetWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean scheduleSend(UserConnection connection, S packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/*  82 */     return scheduleCreateAndSend(connection, (PacketType)packetType, packetWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapper transform(PacketWrapper packet) throws Exception {
/*  87 */     validatePacket(packet);
/*  88 */     transformPacket(packet);
/*  89 */     return packet.isCancelled() ? null : packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapper transform(UserConnection connection, C packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/*  94 */     return createAndTransform(connection, (PacketType)packetType, packetWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapper transform(UserConnection connection, S packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/*  99 */     return createAndTransform(connection, (PacketType)packetType, packetWriter);
/*     */   }
/*     */   
/*     */   private void validatePacket(PacketWrapper packet) {
/* 103 */     if (packet.user() == null) {
/* 104 */       throw new IllegalArgumentException("PacketWrapper does not have a targetted UserConnection");
/*     */     }
/* 106 */     if (packet.getPacketType() == null) {
/* 107 */       throw new IllegalArgumentException("PacketWrapper does not have a valid packet type");
/*     */     }
/*     */ 
/*     */     
/* 111 */     Class<? extends PacketType> expectedPacketClass = (packet.getPacketType().direction() == Direction.CLIENTBOUND) ? (Class)this.clientboundPacketsClass : (Class)this.serverboundPacketsClass;
/* 112 */     if (packet.getPacketType().getClass() != expectedPacketClass) {
/* 113 */       throw new IllegalArgumentException("PacketWrapper packet type is of the wrong packet class");
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean transformAndSendPacket(PacketWrapper packet, boolean currentThread) throws Exception {
/* 118 */     transformPacket(packet);
/* 119 */     if (packet.isCancelled()) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     if (currentThread) {
/* 124 */       if (packet.getPacketType().direction() == Direction.CLIENTBOUND) {
/* 125 */         packet.sendRaw();
/*     */       } else {
/* 127 */         packet.sendToServerRaw();
/*     */       }
/*     */     
/* 130 */     } else if (packet.getPacketType().direction() == Direction.CLIENTBOUND) {
/* 131 */       packet.scheduleSendRaw();
/*     */     } else {
/* 133 */       packet.scheduleSendToServerRaw();
/*     */     } 
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformPacket(PacketWrapper packet) throws Exception {
/* 142 */     PacketType packetType = packet.getPacketType();
/* 143 */     UserConnection connection = packet.user();
/* 144 */     boolean clientbound = (packetType.direction() == Direction.CLIENTBOUND);
/* 145 */     int serverProtocolVersion = clientbound ? this.inputProtocolVersion : connection.getProtocolInfo().getServerProtocolVersion();
/* 146 */     int clientProtocolVersion = clientbound ? connection.getProtocolInfo().getProtocolVersion() : this.inputProtocolVersion;
/*     */ 
/*     */     
/* 149 */     List<ProtocolPathEntry> path = Via.getManager().getProtocolManager().getProtocolPath(clientProtocolVersion, serverProtocolVersion);
/* 150 */     List<Protocol> protocolList = null;
/* 151 */     if (path != null) {
/* 152 */       protocolList = new ArrayList<>(path.size());
/* 153 */       for (ProtocolPathEntry entry : path) {
/* 154 */         protocolList.add(entry.protocol());
/*     */       }
/* 156 */     } else if (serverProtocolVersion != clientProtocolVersion) {
/* 157 */       throw new RuntimeException("No protocol path between client version " + clientProtocolVersion + " and server version " + serverProtocolVersion);
/*     */     } 
/*     */     
/* 160 */     if (protocolList != null) {
/*     */       
/* 162 */       packet.resetReader();
/*     */       
/*     */       try {
/* 165 */         packet.apply(packetType.direction(), State.PLAY, 0, protocolList, clientbound);
/* 166 */       } catch (Exception e) {
/* 167 */         throw new Exception("Exception trying to transform packet between client version " + clientProtocolVersion + " and server version " + serverProtocolVersion + ". Are you sure you used the correct input version and packet write types?", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean createAndSend(UserConnection connection, PacketType packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/* 174 */     PacketWrapper packet = PacketWrapper.create(packetType, connection);
/* 175 */     packetWriter.accept(packet);
/* 176 */     return send(packet);
/*     */   }
/*     */   
/*     */   private boolean scheduleCreateAndSend(UserConnection connection, PacketType packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/* 180 */     PacketWrapper packet = PacketWrapper.create(packetType, connection);
/* 181 */     packetWriter.accept(packet);
/* 182 */     return scheduleSend(packet);
/*     */   }
/*     */   
/*     */   private PacketWrapper createAndTransform(UserConnection connection, PacketType packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
/* 186 */     PacketWrapper packet = PacketWrapper.create(packetType, connection);
/* 187 */     packetWriter.accept(packet);
/* 188 */     return transform(packet);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\packet\VersionedPacketTransformerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */