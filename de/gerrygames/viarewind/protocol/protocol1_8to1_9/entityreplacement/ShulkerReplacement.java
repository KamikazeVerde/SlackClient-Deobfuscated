/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
/*    */ import de.gerrygames.viarewind.utils.PacketUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ShulkerReplacement extends EntityReplacement1_8to1_9 {
/*    */   private final int entityId;
/* 21 */   private final List<Metadata> datawatcher = new ArrayList<>();
/*    */   private double locX;
/*    */   
/*    */   public ShulkerReplacement(int entityId, UserConnection user) {
/* 25 */     super(user);
/* 26 */     this.entityId = entityId;
/* 27 */     spawn();
/*    */   }
/*    */   private double locY; private double locZ;
/*    */   public void setLocation(double x, double y, double z) {
/* 31 */     this.locX = x;
/* 32 */     this.locY = y;
/* 33 */     this.locZ = z;
/* 34 */     updateLocation();
/*    */   }
/*    */   
/*    */   public void relMove(double x, double y, double z) {
/* 38 */     this.locX += x;
/* 39 */     this.locY += y;
/* 40 */     this.locZ += z;
/* 41 */     updateLocation();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setYawPitch(float yaw, float pitch) {}
/*    */ 
/*    */   
/*    */   public void setHeadYaw(float yaw) {}
/*    */   
/*    */   public void updateMetadata(List<Metadata> metadataList) {
/* 51 */     for (Metadata metadata : metadataList) {
/* 52 */       this.datawatcher.removeIf(m -> (m.id() == metadata.id()));
/* 53 */       this.datawatcher.add(metadata);
/*    */     } 
/* 55 */     updateMetadata();
/*    */   }
/*    */   
/*    */   public void updateLocation() {
/* 59 */     sendTeleport(this.entityId, this.locX, this.locY, this.locZ, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void updateMetadata() {
/* 63 */     PacketWrapper metadataPacket = PacketWrapper.create((PacketType)ClientboundPackets1_8.ENTITY_METADATA, null, this.user);
/* 64 */     metadataPacket.write((Type)Type.VAR_INT, Integer.valueOf(this.entityId));
/*    */     
/* 66 */     List<Metadata> metadataList = new ArrayList<>();
/* 67 */     for (Metadata metadata : this.datawatcher) {
/* 68 */       if (metadata.id() == 11 || metadata.id() == 12 || metadata.id() == 13)
/* 69 */         continue;  metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
/*    */     } 
/* 71 */     metadataList.add(new Metadata(11, (MetaType)MetaType1_9.VarInt, Integer.valueOf(2)));
/*    */     
/* 73 */     MetadataRewriter.transform(Entity1_10Types.EntityType.MAGMA_CUBE, metadataList);
/*    */     
/* 75 */     metadataPacket.write(Types1_8.METADATA_LIST, metadataList);
/*    */     
/* 77 */     PacketUtil.sendPacket(metadataPacket, Protocol1_8TO1_9.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawn() {
/* 82 */     sendSpawn(this.entityId, 62);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void despawn() {
/* 88 */     PacketWrapper despawn = PacketWrapper.create((PacketType)ClientboundPackets1_7.DESTROY_ENTITIES, null, this.user);
/* 89 */     despawn.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.entityId });
/*    */     
/* 91 */     PacketUtil.sendPacket(despawn, Protocol1_8TO1_9.class, true, true);
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 95 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\entityreplacement\ShulkerReplacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */