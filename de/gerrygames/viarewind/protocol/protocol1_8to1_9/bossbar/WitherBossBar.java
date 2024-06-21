/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.bossbar;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class WitherBossBar implements BossBar {
/*  19 */   private static int highestId = 2147473647;
/*     */   
/*     */   private final UUID uuid;
/*     */   
/*     */   private String title;
/*     */   
/*     */   private float health;
/*     */   private boolean visible = false;
/*     */   private final UserConnection connection;
/*  28 */   private final int entityId = highestId++; private double locX; private double locY;
/*     */   private double locZ;
/*     */   
/*     */   public WitherBossBar(UserConnection connection, UUID uuid, String title, float health) {
/*  32 */     this.connection = connection;
/*  33 */     this.uuid = uuid;
/*  34 */     this.title = title;
/*  35 */     this.health = health;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  40 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setTitle(String title) {
/*  45 */     this.title = title;
/*  46 */     if (this.visible) updateMetadata(); 
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHealth() {
/*  52 */     return this.health;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setHealth(float health) {
/*  57 */     this.health = health;
/*  58 */     if (this.health <= 0.0F) this.health = 1.0E-4F; 
/*  59 */     if (this.visible) updateMetadata(); 
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossColor getColor() {
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setColor(BossColor bossColor) {
/*  70 */     throw new UnsupportedOperationException(getClass().getName() + " does not support color");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossStyle getStyle() {
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar setStyle(BossStyle bossStyle) {
/*  80 */     throw new UnsupportedOperationException(getClass().getName() + " does not support styles");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar addPlayer(UUID uuid) {
/*  85 */     throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar addConnection(UserConnection userConnection) {
/*  90 */     throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar removePlayer(UUID uuid) {
/*  95 */     throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar removeConnection(UserConnection userConnection) {
/* 100 */     throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar addFlag(BossFlag bossFlag) {
/* 105 */     throw new UnsupportedOperationException(getClass().getName() + " does not support flags");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar removeFlag(BossFlag bossFlag) {
/* 110 */     throw new UnsupportedOperationException(getClass().getName() + " does not support flags");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFlag(BossFlag bossFlag) {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UUID> getPlayers() {
/* 120 */     return Collections.singleton(this.connection.getProtocolInfo().getUuid());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UserConnection> getConnections() {
/* 125 */     throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar show() {
/* 130 */     if (!this.visible) {
/* 131 */       this.visible = true;
/* 132 */       spawnWither();
/*     */     } 
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossBar hide() {
/* 139 */     if (this.visible) {
/* 140 */       this.visible = false;
/* 141 */       despawnWither();
/*     */     } 
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 148 */     return this.visible;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getId() {
/* 153 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public void setLocation(double x, double y, double z) {
/* 157 */     this.locX = x;
/* 158 */     this.locY = y;
/* 159 */     this.locZ = z;
/* 160 */     updateLocation();
/*     */   }
/*     */   
/*     */   private void spawnWither() {
/* 164 */     PacketWrapper packetWrapper = PacketWrapper.create(15, null, this.connection);
/* 165 */     packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.entityId));
/* 166 */     packetWrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)64));
/* 167 */     packetWrapper.write((Type)Type.INT, Integer.valueOf((int)(this.locX * 32.0D)));
/* 168 */     packetWrapper.write((Type)Type.INT, Integer.valueOf((int)(this.locY * 32.0D)));
/* 169 */     packetWrapper.write((Type)Type.INT, Integer.valueOf((int)(this.locZ * 32.0D)));
/* 170 */     packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 171 */     packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 172 */     packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 173 */     packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 174 */     packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 175 */     packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/*     */     
/* 177 */     List<Metadata> metadata = new ArrayList<>();
/* 178 */     metadata.add(new Metadata(0, (MetaType)MetaType1_8.Byte, Byte.valueOf((byte)32)));
/* 179 */     metadata.add(new Metadata(2, (MetaType)MetaType1_8.String, this.title));
/* 180 */     metadata.add(new Metadata(3, (MetaType)MetaType1_8.Byte, Byte.valueOf((byte)1)));
/* 181 */     metadata.add(new Metadata(6, (MetaType)MetaType1_8.Float, Float.valueOf(this.health * 300.0F)));
/*     */     
/* 183 */     packetWrapper.write(Types1_8.METADATA_LIST, metadata);
/*     */     
/* 185 */     PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
/*     */   }
/*     */   
/*     */   private void updateLocation() {
/* 189 */     PacketWrapper packetWrapper = PacketWrapper.create(24, null, this.connection);
/* 190 */     packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.entityId));
/* 191 */     packetWrapper.write((Type)Type.INT, Integer.valueOf((int)(this.locX * 32.0D)));
/* 192 */     packetWrapper.write((Type)Type.INT, Integer.valueOf((int)(this.locY * 32.0D)));
/* 193 */     packetWrapper.write((Type)Type.INT, Integer.valueOf((int)(this.locZ * 32.0D)));
/* 194 */     packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 195 */     packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 196 */     packetWrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */     
/* 198 */     PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
/*     */   }
/*     */   
/*     */   private void updateMetadata() {
/* 202 */     PacketWrapper packetWrapper = PacketWrapper.create(28, null, this.connection);
/* 203 */     packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.entityId));
/*     */     
/* 205 */     List<Metadata> metadata = new ArrayList<>();
/* 206 */     metadata.add(new Metadata(2, (MetaType)MetaType1_8.String, this.title));
/* 207 */     metadata.add(new Metadata(6, (MetaType)MetaType1_8.Float, Float.valueOf(this.health * 300.0F)));
/*     */     
/* 209 */     packetWrapper.write(Types1_8.METADATA_LIST, metadata);
/*     */     
/* 211 */     PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
/*     */   }
/*     */   
/*     */   private void despawnWither() {
/* 215 */     PacketWrapper packetWrapper = PacketWrapper.create(19, null, this.connection);
/* 216 */     packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.entityId });
/*     */     
/* 218 */     PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
/*     */   }
/*     */   
/*     */   public void setPlayerLocation(double posX, double posY, double posZ, float yaw, float pitch) {
/* 222 */     double yawR = Math.toRadians(yaw);
/* 223 */     double pitchR = Math.toRadians(pitch);
/*     */     
/* 225 */     posX -= Math.cos(pitchR) * Math.sin(yawR) * 48.0D;
/* 226 */     posY -= Math.sin(pitchR) * 48.0D;
/* 227 */     posZ += Math.cos(pitchR) * Math.cos(yawR) * 48.0D;
/*     */     
/* 229 */     setLocation(posX, posY, posZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\bossbar\WitherBossBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */