/*     */ package com.viaversion.viaversion.legacy.bossbar;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.MapMaker;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class CommonBoss
/*     */   implements BossBar
/*     */ {
/*     */   private final UUID uuid;
/*     */   private final Map<UUID, UserConnection> connections;
/*     */   private final Set<BossFlag> flags;
/*     */   private String title;
/*     */   private float health;
/*     */   private BossColor color;
/*     */   private BossStyle style;
/*     */   private boolean visible;
/*     */   
/*     */   public CommonBoss(String title, float health, BossColor color, BossStyle style) {
/*  50 */     Preconditions.checkNotNull(title, "Title cannot be null");
/*  51 */     Preconditions.checkArgument((health >= 0.0F && health <= 1.0F), "Health must be between 0 and 1. Input: " + health);
/*     */     
/*  53 */     this.uuid = UUID.randomUUID();
/*  54 */     this.title = title;
/*  55 */     this.health = health;
/*  56 */     this.color = (color == null) ? BossColor.PURPLE : color;
/*  57 */     this.style = (style == null) ? BossStyle.SOLID : style;
/*  58 */     this.connections = (new MapMaker()).weakValues().makeMap();
/*  59 */     this.flags = new HashSet<>();
/*  60 */     this.visible = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setTitle(String title) {
/*  65 */     Preconditions.checkNotNull(title);
/*  66 */     this.title = title;
/*  67 */     sendPacket(UpdateAction.UPDATE_TITLE);
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setHealth(float health) {
/*  73 */     Preconditions.checkArgument((health >= 0.0F && health <= 1.0F), "Health must be between 0 and 1. Input: " + health);
/*  74 */     this.health = health;
/*  75 */     sendPacket(UpdateAction.UPDATE_HEALTH);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossColor getColor() {
/*  81 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setColor(BossColor color) {
/*  86 */     Preconditions.checkNotNull(color);
/*  87 */     this.color = color;
/*  88 */     sendPacket(UpdateAction.UPDATE_STYLE);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setStyle(BossStyle style) {
/*  94 */     Preconditions.checkNotNull(style);
/*  95 */     this.style = style;
/*  96 */     sendPacket(UpdateAction.UPDATE_STYLE);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar addPlayer(UUID player) {
/* 102 */     UserConnection client = Via.getManager().getConnectionManager().getConnectedClient(player);
/* 103 */     if (client != null) {
/* 104 */       addConnection(client);
/*     */     }
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar addConnection(UserConnection conn) {
/* 111 */     if (this.connections.put(conn.getProtocolInfo().getUuid(), conn) == null && this.visible) {
/* 112 */       sendPacketConnection(conn, getPacket(UpdateAction.ADD, conn));
/*     */     }
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar removePlayer(UUID uuid) {
/* 119 */     UserConnection client = this.connections.remove(uuid);
/* 120 */     if (client != null) {
/* 121 */       sendPacketConnection(client, getPacket(UpdateAction.REMOVE, client));
/*     */     }
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar removeConnection(UserConnection conn) {
/* 128 */     removePlayer(conn.getProtocolInfo().getUuid());
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar addFlag(BossFlag flag) {
/* 134 */     Preconditions.checkNotNull(flag);
/* 135 */     if (!hasFlag(flag)) {
/* 136 */       this.flags.add(flag);
/*     */     }
/* 138 */     sendPacket(UpdateAction.UPDATE_FLAGS);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar removeFlag(BossFlag flag) {
/* 144 */     Preconditions.checkNotNull(flag);
/* 145 */     if (hasFlag(flag)) {
/* 146 */       this.flags.remove(flag);
/*     */     }
/* 148 */     sendPacket(UpdateAction.UPDATE_FLAGS);
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFlag(BossFlag flag) {
/* 154 */     Preconditions.checkNotNull(flag);
/* 155 */     return this.flags.contains(flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UUID> getPlayers() {
/* 160 */     return Collections.unmodifiableSet(this.connections.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UserConnection> getConnections() {
/* 165 */     return Collections.unmodifiableSet(new HashSet<>(this.connections.values()));
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar show() {
/* 170 */     setVisible(true);
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar hide() {
/* 176 */     setVisible(false);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 182 */     return this.visible;
/*     */   }
/*     */   
/*     */   private void setVisible(boolean value) {
/* 186 */     if (this.visible != value) {
/* 187 */       this.visible = value;
/* 188 */       sendPacket(value ? UpdateAction.ADD : UpdateAction.REMOVE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getId() {
/* 194 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public UUID getUuid() {
/* 198 */     return this.uuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 203 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHealth() {
/* 208 */     return this.health;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossStyle getStyle() {
/* 213 */     return this.style;
/*     */   }
/*     */   
/*     */   public Set<BossFlag> getFlags() {
/* 217 */     return this.flags;
/*     */   }
/*     */   
/*     */   private void sendPacket(UpdateAction action) {
/* 221 */     for (UserConnection conn : new ArrayList(this.connections.values())) {
/* 222 */       PacketWrapper wrapper = getPacket(action, conn);
/* 223 */       sendPacketConnection(conn, wrapper);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendPacketConnection(UserConnection conn, PacketWrapper wrapper) {
/* 228 */     if (conn.getProtocolInfo() == null || !conn.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
/* 229 */       this.connections.remove(conn.getProtocolInfo().getUuid());
/*     */       return;
/*     */     } 
/*     */     try {
/* 233 */       wrapper.scheduleSend(Protocol1_9To1_8.class);
/* 234 */     } catch (Exception e) {
/* 235 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private PacketWrapper getPacket(UpdateAction action, UserConnection connection) {
/*     */     try {
/* 241 */       PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9.BOSSBAR, null, connection);
/* 242 */       wrapper.write(Type.UUID, this.uuid);
/* 243 */       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(action.getId()));
/* 244 */       switch (action) {
/*     */         case ADD:
/* 246 */           Protocol1_9To1_8.FIX_JSON.write(wrapper, this.title);
/* 247 */           wrapper.write((Type)Type.FLOAT, Float.valueOf(this.health));
/* 248 */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.color.getId()));
/* 249 */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.style.getId()));
/* 250 */           wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)flagToBytes()));
/*     */           break;
/*     */ 
/*     */         
/*     */         case UPDATE_HEALTH:
/* 255 */           wrapper.write((Type)Type.FLOAT, Float.valueOf(this.health));
/*     */           break;
/*     */         case UPDATE_TITLE:
/* 258 */           Protocol1_9To1_8.FIX_JSON.write(wrapper, this.title);
/*     */           break;
/*     */         case UPDATE_STYLE:
/* 261 */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.color.getId()));
/* 262 */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.style.getId()));
/*     */           break;
/*     */         case UPDATE_FLAGS:
/* 265 */           wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)flagToBytes()));
/*     */           break;
/*     */       } 
/*     */       
/* 269 */       return wrapper;
/* 270 */     } catch (Exception e) {
/* 271 */       e.printStackTrace();
/*     */       
/* 273 */       return null;
/*     */     } 
/*     */   }
/*     */   private int flagToBytes() {
/* 277 */     int bitmask = 0;
/* 278 */     for (BossFlag flag : this.flags) {
/* 279 */       bitmask |= flag.getId();
/*     */     }
/* 281 */     return bitmask;
/*     */   }
/*     */   
/*     */   private enum UpdateAction
/*     */   {
/* 286 */     ADD(0),
/* 287 */     REMOVE(1),
/* 288 */     UPDATE_HEALTH(2),
/* 289 */     UPDATE_TITLE(3),
/* 290 */     UPDATE_STYLE(4),
/* 291 */     UPDATE_FLAGS(5);
/*     */     
/*     */     private final int id;
/*     */     
/*     */     UpdateAction(int id) {
/* 296 */       this.id = id;
/*     */     }
/*     */     
/*     */     public int getId() {
/* 300 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\legacy\bossbar\CommonBoss.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */