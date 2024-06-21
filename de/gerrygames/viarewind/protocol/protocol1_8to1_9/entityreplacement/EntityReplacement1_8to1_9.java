/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*    */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*    */ import de.gerrygames.viarewind.utils.PacketUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class EntityReplacement1_8to1_9 implements EntityReplacement {
/*    */   protected final UserConnection user;
/*    */   
/*    */   protected EntityReplacement1_8to1_9(UserConnection user) {
/* 20 */     this.user = user;
/*    */   }
/*    */   
/*    */   protected void sendTeleportWithHead(int entityId, double locX, double locY, double locZ, float yaw, float pitch, float headYaw) {
/* 24 */     sendTeleport(entityId, locX, locY, locZ, yaw, pitch);
/* 25 */     sendHeadYaw(entityId, headYaw);
/*    */   }
/*    */   
/*    */   protected void sendTeleport(int entityId, double locX, double locY, double locZ, float yaw, float pitch) {
/* 29 */     PacketWrapper teleport = PacketWrapper.create((PacketType)ClientboundPackets1_8.ENTITY_TELEPORT, null, this.user);
/* 30 */     teleport.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 31 */     teleport.write((Type)Type.INT, Integer.valueOf((int)(locX * 32.0D)));
/* 32 */     teleport.write((Type)Type.INT, Integer.valueOf((int)(locY * 32.0D)));
/* 33 */     teleport.write((Type)Type.INT, Integer.valueOf((int)(locZ * 32.0D)));
/* 34 */     teleport.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(yaw / 360.0F * 256.0F)));
/* 35 */     teleport.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(pitch / 360.0F * 256.0F)));
/* 36 */     teleport.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*    */     
/* 38 */     PacketUtil.sendPacket(teleport, Protocol1_8TO1_9.class, true, true);
/*    */   }
/*    */   
/*    */   protected void sendHeadYaw(int entityId, float headYaw) {
/* 42 */     PacketWrapper head = PacketWrapper.create((PacketType)ClientboundPackets1_8.ENTITY_HEAD_LOOK, null, this.user);
/* 43 */     head.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 44 */     head.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(headYaw / 360.0F * 256.0F)));
/* 45 */     PacketUtil.sendPacket(head, Protocol1_8TO1_9.class, true, true);
/*    */   }
/*    */   
/*    */   protected void sendSpawn(int entityId, int type) {
/* 49 */     PacketWrapper spawn = PacketWrapper.create((PacketType)ClientboundPackets1_8.SPAWN_MOB, null, this.user);
/* 50 */     spawn.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 51 */     spawn.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)type));
/* 52 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/* 53 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/* 54 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/* 55 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 56 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 57 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 58 */     spawn.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 59 */     spawn.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 60 */     spawn.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 61 */     List<Metadata> list = new ArrayList<>();
/* 62 */     spawn.write(Types1_8.METADATA_LIST, list);
/*    */     
/* 64 */     PacketUtil.sendPacket(spawn, Protocol1_8TO1_9.class, true, true);
/*    */   }
/*    */   
/*    */   protected void sendSpawnEntity(int entityId, int type) {
/* 68 */     PacketWrapper spawn = PacketWrapper.create((PacketType)ClientboundPackets1_8.SPAWN_ENTITY, null, this.user);
/* 69 */     spawn.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 70 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)type));
/* 71 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/* 72 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/* 73 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/* 74 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 75 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 76 */     spawn.write((Type)Type.INT, Integer.valueOf(0));
/*    */     
/* 78 */     PacketUtil.sendPacket(spawn, Protocol1_8TO1_9.class, true, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\entityreplacement\EntityReplacement1_8to1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */