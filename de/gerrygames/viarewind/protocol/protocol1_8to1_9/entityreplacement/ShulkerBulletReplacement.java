/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*    */ import de.gerrygames.viarewind.utils.PacketUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ShulkerBulletReplacement extends EntityReplacement1_8to1_9 {
/*    */   private final int entityId;
/* 16 */   private final List<Metadata> datawatcher = new ArrayList<>();
/*    */   private double locX;
/*    */   private double locY;
/*    */   private double locZ;
/*    */   
/*    */   public ShulkerBulletReplacement(int entityId, UserConnection user) {
/* 22 */     super(user);
/* 23 */     this.entityId = entityId;
/* 24 */     spawn();
/*    */   }
/*    */   private float yaw; private float pitch; private float headYaw;
/*    */   public void setLocation(double x, double y, double z) {
/* 28 */     if (x != this.locX || y != this.locY || z != this.locZ) {
/* 29 */       this.locX = x;
/* 30 */       this.locY = y;
/* 31 */       this.locZ = z;
/* 32 */       updateLocation();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void relMove(double x, double y, double z) {
/* 37 */     if (x == 0.0D && y == 0.0D && z == 0.0D)
/* 38 */       return;  this.locX += x;
/* 39 */     this.locY += y;
/* 40 */     this.locZ += z;
/* 41 */     updateLocation();
/*    */   }
/*    */   
/*    */   public void setYawPitch(float yaw, float pitch) {
/* 45 */     if (this.yaw != yaw && this.pitch != pitch) {
/* 46 */       this.yaw = yaw;
/* 47 */       this.pitch = pitch;
/* 48 */       updateLocation();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setHeadYaw(float yaw) {
/* 53 */     this.headYaw = yaw;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateMetadata(List<Metadata> metadataList) {}
/*    */   
/*    */   public void updateLocation() {
/* 60 */     sendTeleportWithHead(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch, this.headYaw);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawn() {
/* 65 */     sendSpawnEntity(this.entityId, 66);
/*    */   }
/*    */ 
/*    */   
/*    */   public void despawn() {
/* 70 */     PacketWrapper despawn = PacketWrapper.create((PacketType)ClientboundPackets1_8.DESTROY_ENTITIES, null, this.user);
/* 71 */     despawn.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.entityId });
/*    */     
/* 73 */     PacketUtil.sendPacket(despawn, Protocol1_8TO1_9.class, true, true);
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 77 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\entityreplacement\ShulkerBulletReplacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */