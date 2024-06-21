/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class EndermiteReplacement extends EntityReplacement1_7to1_8 {
/*     */   private final int entityId;
/*  19 */   private final List<Metadata> datawatcher = new ArrayList<>();
/*     */   private double locX;
/*     */   private double locY;
/*     */   private double locZ;
/*     */   
/*     */   public EndermiteReplacement(int entityId, UserConnection user) {
/*  25 */     super(user);
/*  26 */     this.entityId = entityId;
/*  27 */     spawn();
/*     */   }
/*     */   private float yaw; private float pitch; private float headYaw;
/*     */   public void setLocation(double x, double y, double z) {
/*  31 */     this.locX = x;
/*  32 */     this.locY = y;
/*  33 */     this.locZ = z;
/*  34 */     updateLocation();
/*     */   }
/*     */   
/*     */   public void relMove(double x, double y, double z) {
/*  38 */     this.locX += x;
/*  39 */     this.locY += y;
/*  40 */     this.locZ += z;
/*  41 */     updateLocation();
/*     */   }
/*     */   
/*     */   public void setYawPitch(float yaw, float pitch) {
/*  45 */     if (this.yaw != yaw || this.pitch != pitch) {
/*  46 */       this.yaw = yaw;
/*  47 */       this.pitch = pitch;
/*  48 */       updateLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHeadYaw(float yaw) {
/*  53 */     if (this.headYaw != yaw) {
/*  54 */       this.headYaw = yaw;
/*  55 */       updateLocation();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateMetadata(List<Metadata> metadataList) {
/*  60 */     for (Metadata metadata : metadataList) {
/*  61 */       this.datawatcher.removeIf(m -> (m.id() == metadata.id()));
/*  62 */       this.datawatcher.add(metadata);
/*     */     } 
/*  64 */     updateMetadata();
/*     */   }
/*     */   
/*     */   public void updateLocation() {
/*  68 */     sendTeleportWithHead(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch, this.headYaw);
/*     */   }
/*     */   
/*     */   public void updateMetadata() {
/*  72 */     PacketWrapper metadataPacket = PacketWrapper.create((PacketType)ClientboundPackets1_7.ENTITY_METADATA, this.user);
/*  73 */     metadataPacket.write((Type)Type.INT, Integer.valueOf(this.entityId));
/*     */     
/*  75 */     List<Metadata> metadataList = new ArrayList<>();
/*  76 */     for (Metadata metadata : this.datawatcher) {
/*  77 */       metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
/*     */     }
/*     */     
/*  80 */     MetadataRewriter.transform(Entity1_10Types.EntityType.SQUID, metadataList);
/*     */     
/*  82 */     metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
/*     */     
/*  84 */     PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10TO1_8.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawn() {
/*  89 */     sendSpawn(this.entityId, 60, this.locX, this.locY, this.locZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void despawn() {
/*  94 */     PacketWrapper despawn = PacketWrapper.create((PacketType)ClientboundPackets1_7.DESTROY_ENTITIES, null, this.user);
/*  95 */     despawn.write(Types1_7_6_10.INT_ARRAY, new int[] { this.entityId });
/*     */     
/*  97 */     PacketUtil.sendPacket(despawn, Protocol1_7_6_10TO1_8.class, true, true);
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/* 101 */     return this.entityId;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\entityreplacements\EndermiteReplacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */