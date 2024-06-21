/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import de.gerrygames.viarewind.utils.math.AABB;
/*     */ import de.gerrygames.viarewind.utils.math.Vector3d;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ArmorStandReplacement extends EntityReplacement1_7to1_8 {
/*     */   private final int entityId;
/*  23 */   private final List<Metadata> datawatcher = new ArrayList<>();
/*  24 */   private int[] entityIds = null;
/*     */   private double locX;
/*  26 */   private State currentState = null; private double locY; private double locZ;
/*     */   private boolean invisible = false;
/*     */   private boolean nameTagVisible = false;
/*  29 */   private String name = null; private float yaw;
/*     */   private float pitch;
/*     */   private float headYaw;
/*     */   private boolean small = false;
/*     */   private boolean marker = false;
/*     */   
/*     */   public int getEntityId() {
/*  36 */     return this.entityId;
/*     */   }
/*     */   
/*     */   private enum State {
/*  40 */     HOLOGRAM, ZOMBIE;
/*     */   }
/*     */   
/*     */   public ArmorStandReplacement(int entityId, UserConnection user) {
/*  44 */     super(user);
/*  45 */     this.entityId = entityId;
/*     */   }
/*     */   
/*     */   public void setLocation(double x, double y, double z) {
/*  49 */     if (x != this.locX || y != this.locY || z != this.locZ) {
/*  50 */       this.locX = x;
/*  51 */       this.locY = y;
/*  52 */       this.locZ = z;
/*  53 */       updateLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void relMove(double x, double y, double z) {
/*  58 */     if (x == 0.0D && y == 0.0D && z == 0.0D)
/*  59 */       return;  this.locX += x;
/*  60 */     this.locY += y;
/*  61 */     this.locZ += z;
/*  62 */     updateLocation();
/*     */   }
/*     */   
/*     */   public void setYawPitch(float yaw, float pitch) {
/*  66 */     if ((this.yaw != yaw && this.pitch != pitch) || this.headYaw != yaw) {
/*  67 */       this.yaw = yaw;
/*  68 */       this.headYaw = yaw;
/*  69 */       this.pitch = pitch;
/*  70 */       updateLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHeadYaw(float yaw) {
/*  75 */     if (this.headYaw != yaw) {
/*  76 */       this.headYaw = yaw;
/*  77 */       updateLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateMetadata(List<Metadata> metadataList) {
/*  82 */     for (Metadata metadata : metadataList) {
/*  83 */       this.datawatcher.removeIf(m -> (m.id() == metadata.id()));
/*  84 */       this.datawatcher.add(metadata);
/*     */     } 
/*  86 */     updateState();
/*     */   }
/*     */   
/*     */   public void updateState() {
/*  90 */     byte flags = 0;
/*  91 */     byte armorStandFlags = 0;
/*  92 */     for (Metadata metadata : this.datawatcher) {
/*  93 */       if (metadata.id() == 0 && metadata.metaType() == MetaType1_8.Byte) {
/*  94 */         flags = ((Number)metadata.getValue()).byteValue(); continue;
/*  95 */       }  if (metadata.id() == 2 && metadata.metaType() == MetaType1_8.String) {
/*  96 */         this.name = metadata.getValue().toString();
/*  97 */         if (this.name != null && this.name.equals("")) this.name = null;  continue;
/*  98 */       }  if (metadata.id() == 10 && metadata.metaType() == MetaType1_8.Byte) {
/*  99 */         armorStandFlags = ((Number)metadata.getValue()).byteValue(); continue;
/* 100 */       }  if (metadata.id() == 3 && metadata.metaType() == MetaType1_8.Byte) {
/* 101 */         this.nameTagVisible = (((Number)metadata.getValue()).byteValue() != 0);
/*     */       }
/*     */     } 
/* 104 */     this.invisible = ((flags & 0x20) != 0);
/* 105 */     this.small = ((armorStandFlags & 0x1) != 0);
/* 106 */     this.marker = ((armorStandFlags & 0x10) != 0);
/*     */     
/* 108 */     State prevState = this.currentState;
/* 109 */     if (this.invisible && this.name != null) {
/* 110 */       this.currentState = State.HOLOGRAM;
/*     */     } else {
/* 112 */       this.currentState = State.ZOMBIE;
/*     */     } 
/*     */     
/* 115 */     if (this.currentState != prevState) {
/* 116 */       despawn();
/* 117 */       spawn();
/*     */     } else {
/* 119 */       updateMetadata();
/* 120 */       updateLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateLocation() {
/* 125 */     if (this.entityIds == null)
/*     */       return; 
/* 127 */     if (this.currentState == State.ZOMBIE) {
/* 128 */       updateZombieLocation();
/* 129 */     } else if (this.currentState == State.HOLOGRAM) {
/* 130 */       updateHologramLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateZombieLocation() {
/* 135 */     sendTeleportWithHead(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch, this.headYaw);
/*     */   }
/*     */   
/*     */   private void updateHologramLocation() {
/* 139 */     PacketWrapper detach = PacketWrapper.create((PacketType)ClientboundPackets1_7.ATTACH_ENTITY, null, this.user);
/* 140 */     detach.write((Type)Type.INT, Integer.valueOf(this.entityIds[1]));
/* 141 */     detach.write((Type)Type.INT, Integer.valueOf(-1));
/* 142 */     detach.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/* 143 */     PacketUtil.sendPacket(detach, Protocol1_7_6_10TO1_8.class, true, true);
/*     */ 
/*     */     
/* 146 */     sendTeleport(this.entityIds[0], this.locX, this.locY + (this.marker ? 54.85D : (this.small ? 56.0D : 57.0D)), this.locZ, 0.0F, 0.0F);
/* 147 */     sendTeleport(this.entityIds[1], this.locX, this.locY + 56.75D, this.locZ, 0.0F, 0.0F);
/*     */     
/* 149 */     PacketWrapper attach = PacketWrapper.create((PacketType)ClientboundPackets1_7.ATTACH_ENTITY, null, this.user);
/* 150 */     attach.write((Type)Type.INT, Integer.valueOf(this.entityIds[1]));
/* 151 */     attach.write((Type)Type.INT, Integer.valueOf(this.entityIds[0]));
/* 152 */     attach.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/* 153 */     PacketUtil.sendPacket(attach, Protocol1_7_6_10TO1_8.class, true, true);
/*     */   }
/*     */   
/*     */   public void updateMetadata() {
/* 157 */     if (this.entityIds == null)
/*     */       return; 
/* 159 */     PacketWrapper metadataPacket = PacketWrapper.create((PacketType)ClientboundPackets1_7.ENTITY_METADATA, null, this.user);
/*     */     
/* 161 */     if (this.currentState == State.ZOMBIE) {
/* 162 */       writeZombieMeta(metadataPacket);
/* 163 */     } else if (this.currentState == State.HOLOGRAM) {
/* 164 */       writeHologramMeta(metadataPacket);
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 169 */     PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10TO1_8.class, true, true);
/*     */   }
/*     */   
/*     */   private void writeZombieMeta(PacketWrapper metadataPacket) {
/* 173 */     metadataPacket.write((Type)Type.INT, Integer.valueOf(this.entityIds[0]));
/*     */     
/* 175 */     List<Metadata> metadataList = new ArrayList<>();
/* 176 */     for (Metadata metadata : this.datawatcher) {
/* 177 */       if (metadata.id() < 0 || metadata.id() > 9)
/* 178 */         continue;  metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
/*     */     } 
/* 180 */     if (this.small) metadataList.add(new Metadata(12, (MetaType)MetaType1_8.Byte, Byte.valueOf((byte)1))); 
/* 181 */     MetadataRewriter.transform(Entity1_10Types.EntityType.ZOMBIE, metadataList);
/*     */     
/* 183 */     metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
/*     */   }
/*     */   
/*     */   private void writeHologramMeta(PacketWrapper metadataPacket) {
/* 187 */     metadataPacket.write((Type)Type.INT, Integer.valueOf(this.entityIds[1]));
/*     */     
/* 189 */     List<Metadata> metadataList = new ArrayList<>();
/* 190 */     metadataList.add(new Metadata(12, (MetaType)MetaType1_7_6_10.Int, Integer.valueOf(-1700000)));
/* 191 */     metadataList.add(new Metadata(10, (MetaType)MetaType1_7_6_10.String, this.name));
/* 192 */     metadataList.add(new Metadata(11, (MetaType)MetaType1_7_6_10.Byte, Byte.valueOf((byte)1)));
/*     */     
/* 194 */     metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
/*     */   }
/*     */   
/*     */   public void spawn() {
/* 198 */     if (this.entityIds != null) despawn();
/*     */     
/* 200 */     if (this.currentState == State.ZOMBIE) {
/* 201 */       spawnZombie();
/* 202 */     } else if (this.currentState == State.HOLOGRAM) {
/* 203 */       spawnHologram();
/*     */     } 
/*     */     
/* 206 */     updateMetadata();
/* 207 */     updateLocation();
/*     */   }
/*     */   
/*     */   private void spawnZombie() {
/* 211 */     sendSpawn(this.entityId, 54, this.locX, this.locY, this.locZ);
/*     */     
/* 213 */     this.entityIds = new int[] { this.entityId };
/*     */   }
/*     */   
/*     */   private void spawnHologram() {
/* 217 */     int[] entityIds = { this.entityId, additionalEntityId() };
/*     */     
/* 219 */     PacketWrapper spawnSkull = PacketWrapper.create((PacketType)ClientboundPackets1_7.SPAWN_ENTITY, null, this.user);
/* 220 */     spawnSkull.write((Type)Type.VAR_INT, Integer.valueOf(entityIds[0]));
/* 221 */     spawnSkull.write((Type)Type.BYTE, Byte.valueOf((byte)66));
/* 222 */     spawnSkull.write((Type)Type.INT, Integer.valueOf((int)(this.locX * 32.0D)));
/* 223 */     spawnSkull.write((Type)Type.INT, Integer.valueOf((int)(this.locY * 32.0D)));
/* 224 */     spawnSkull.write((Type)Type.INT, Integer.valueOf((int)(this.locZ * 32.0D)));
/* 225 */     spawnSkull.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 226 */     spawnSkull.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 227 */     spawnSkull.write((Type)Type.INT, Integer.valueOf(0));
/* 228 */     PacketUtil.sendPacket(spawnSkull, Protocol1_7_6_10TO1_8.class, true, true);
/*     */     
/* 230 */     sendSpawn(entityIds[1], 100, this.locX, this.locY, this.locZ);
/*     */     
/* 232 */     this.entityIds = entityIds;
/*     */   }
/*     */   
/*     */   private int additionalEntityId() {
/* 236 */     return 2147467647 - this.entityId;
/*     */   }
/*     */   
/*     */   public AABB getBoundingBox() {
/* 240 */     double w = this.small ? 0.25D : 0.5D;
/* 241 */     double h = this.small ? 0.9875D : 1.975D;
/* 242 */     Vector3d min = new Vector3d(this.locX - w / 2.0D, this.locY, this.locZ - w / 2.0D);
/* 243 */     Vector3d max = new Vector3d(this.locX + w / 2.0D, this.locY + h, this.locZ + w / 2.0D);
/* 244 */     return new AABB(min, max);
/*     */   }
/*     */   
/*     */   public void despawn() {
/* 248 */     if (this.entityIds == null)
/* 249 */       return;  PacketWrapper despawn = PacketWrapper.create((PacketType)ClientboundPackets1_7.DESTROY_ENTITIES, null, this.user);
/* 250 */     despawn.write((Type)Type.BYTE, Byte.valueOf((byte)this.entityIds.length));
/* 251 */     for (int id : this.entityIds) {
/* 252 */       despawn.write((Type)Type.INT, Integer.valueOf(id));
/*     */     }
/* 254 */     this.entityIds = null;
/* 255 */     PacketUtil.sendPacket(despawn, Protocol1_7_6_10TO1_8.class, true, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\entityreplacements\ArmorStandReplacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */