/*     */ package com.viaversion.viaversion.protocol;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.debug.DebugHandler;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ 
/*     */ 
/*     */ public class ProtocolPipelineImpl
/*     */   extends AbstractSimpleProtocol
/*     */   implements ProtocolPipeline
/*     */ {
/*     */   private final UserConnection userConnection;
/*     */   private List<Protocol> protocolList;
/*     */   private Set<Class<? extends Protocol>> protocolSet;
/*     */   
/*     */   public ProtocolPipelineImpl(UserConnection userConnection) {
/*  49 */     this.userConnection = userConnection;
/*  50 */     userConnection.getProtocolInfo().setPipeline(this);
/*  51 */     registerPackets();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  56 */     this.protocolList = new CopyOnWriteArrayList<>();
/*     */     
/*  58 */     this.protocolSet = Sets.newSetFromMap(new ConcurrentHashMap<>());
/*     */ 
/*     */     
/*  61 */     Protocol baseProtocol = Via.getManager().getProtocolManager().getBaseProtocol();
/*  62 */     this.protocolList.add(baseProtocol);
/*  63 */     this.protocolSet.add(baseProtocol.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/*  68 */     throw new UnsupportedOperationException("ProtocolPipeline can only be initialized once");
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Protocol protocol) {
/*  73 */     this.protocolList.add(protocol);
/*  74 */     this.protocolSet.add(protocol.getClass());
/*  75 */     protocol.init(this.userConnection);
/*     */     
/*  77 */     if (!protocol.isBaseProtocol()) {
/*  78 */       moveBaseProtocolsToTail();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Collection<Protocol> protocols) {
/*  84 */     this.protocolList.addAll(protocols);
/*  85 */     for (Protocol protocol : protocols) {
/*  86 */       protocol.init(this.userConnection);
/*  87 */       this.protocolSet.add(protocol.getClass());
/*     */     } 
/*     */     
/*  90 */     moveBaseProtocolsToTail();
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveBaseProtocolsToTail() {
/*  95 */     List<Protocol> baseProtocols = null;
/*  96 */     for (Protocol protocol : this.protocolList) {
/*  97 */       if (protocol.isBaseProtocol()) {
/*  98 */         if (baseProtocols == null) {
/*  99 */           baseProtocols = new ArrayList<>();
/*     */         }
/*     */         
/* 102 */         baseProtocols.add(protocol);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (baseProtocols != null) {
/* 107 */       this.protocolList.removeAll(baseProtocols);
/* 108 */       this.protocolList.addAll(baseProtocols);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
/* 114 */     int originalID = packetWrapper.getId();
/*     */     
/* 116 */     DebugHandler debugHandler = Via.getManager().debugHandler();
/* 117 */     if (debugHandler.enabled() && !debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
/* 118 */       logPacket(direction, state, packetWrapper, originalID);
/*     */     }
/*     */ 
/*     */     
/* 122 */     packetWrapper.apply(direction, state, 0, this.protocolList, (direction == Direction.CLIENTBOUND));
/* 123 */     super.transform(direction, state, packetWrapper);
/*     */     
/* 125 */     if (debugHandler.enabled() && debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
/* 126 */       logPacket(direction, state, packetWrapper, originalID);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int originalID) {
/* 132 */     int clientProtocol = this.userConnection.getProtocolInfo().getProtocolVersion();
/* 133 */     ViaPlatform<?> platform = Via.getPlatform();
/*     */     
/* 135 */     String actualUsername = packetWrapper.user().getProtocolInfo().getUsername();
/* 136 */     String username = (actualUsername != null) ? (actualUsername + " ") : "";
/*     */     
/* 138 */     platform.getLogger().log(Level.INFO, "{0}{1} {2}: {3} ({4}) -> {5} ({6}) [{7}] {8}", new Object[] { username, direction, state, 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 143 */           Integer.valueOf(originalID), 
/* 144 */           AbstractSimpleProtocol.toNiceHex(originalID), 
/* 145 */           Integer.valueOf(packetWrapper.getId()), 
/* 146 */           AbstractSimpleProtocol.toNiceHex(packetWrapper.getId()), 
/* 147 */           Integer.toString(clientProtocol), packetWrapper });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Class<? extends Protocol> protocolClass) {
/* 154 */     return this.protocolSet.contains(protocolClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <P extends Protocol> P getProtocol(Class<P> pipeClass) {
/* 159 */     for (Protocol protocol : this.protocolList) {
/* 160 */       if (protocol.getClass() == pipeClass) {
/* 161 */         return (P)protocol;
/*     */       }
/*     */     } 
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Protocol> pipes() {
/* 169 */     return this.protocolList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNonBaseProtocols() {
/* 174 */     for (Protocol protocol : this.protocolList) {
/* 175 */       if (!protocol.isBaseProtocol()) {
/* 176 */         return true;
/*     */       }
/*     */     } 
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanPipes() {
/* 184 */     registerPackets();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\ProtocolPipelineImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */