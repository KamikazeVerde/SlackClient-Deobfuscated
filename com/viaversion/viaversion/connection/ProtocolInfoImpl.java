/*     */ package com.viaversion.viaversion.connection;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import java.util.UUID;
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
/*     */ public class ProtocolInfoImpl
/*     */   implements ProtocolInfo
/*     */ {
/*     */   private final UserConnection connection;
/*  29 */   private State state = State.HANDSHAKE;
/*  30 */   private int protocolVersion = -1;
/*  31 */   private int serverProtocolVersion = -1;
/*     */   private String username;
/*     */   private UUID uuid;
/*     */   private ProtocolPipeline pipeline;
/*     */   
/*     */   public ProtocolInfoImpl(UserConnection connection) {
/*  37 */     this.connection = connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public State getState() {
/*  42 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(State state) {
/*  47 */     this.state = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProtocolVersion() {
/*  52 */     return this.protocolVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProtocolVersion(int protocolVersion) {
/*  58 */     ProtocolVersion protocol = ProtocolVersion.getProtocol(protocolVersion);
/*  59 */     this.protocolVersion = protocol.getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerProtocolVersion() {
/*  64 */     return this.serverProtocolVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServerProtocolVersion(int serverProtocolVersion) {
/*  69 */     ProtocolVersion protocol = ProtocolVersion.getProtocol(serverProtocolVersion);
/*  70 */     this.serverProtocolVersion = protocol.getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  75 */     return this.username;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/*  80 */     this.username = username;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUuid() {
/*  85 */     return this.uuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUuid(UUID uuid) {
/*  90 */     this.uuid = uuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolPipeline getPipeline() {
/*  95 */     return this.pipeline;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPipeline(ProtocolPipeline pipeline) {
/* 100 */     this.pipeline = pipeline;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserConnection getUser() {
/* 105 */     return this.connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return "ProtocolInfo{state=" + this.state + ", protocolVersion=" + this.protocolVersion + ", serverProtocolVersion=" + this.serverProtocolVersion + ", username='" + this.username + '\'' + ", uuid=" + this.uuid + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\connection\ProtocolInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */