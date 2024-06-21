/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*    */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*    */ import de.gerrygames.viarewind.utils.PacketUtil;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public abstract class EntityReplacement1_7to1_8 implements EntityReplacement {
/*    */   protected final UserConnection user;
/*    */   
/*    */   public EntityReplacement1_7to1_8(UserConnection user) {
/* 18 */     this.user = user;
/*    */   }
/*    */   
/*    */   protected void sendTeleportWithHead(int entityId, double locX, double locY, double locZ, float yaw, float pitch, float headYaw) {
/* 22 */     sendTeleport(entityId, locX, locY, locZ, yaw, pitch);
/* 23 */     sendHeadYaw(entityId, headYaw);
/*    */   }
/*    */   
/*    */   protected void sendTeleport(int entityId, double locX, double locY, double locZ, float yaw, float pitch) {
/* 27 */     PacketWrapper teleport = PacketWrapper.create((PacketType)ClientboundPackets1_7.ENTITY_TELEPORT, null, this.user);
/* 28 */     teleport.write((Type)Type.INT, Integer.valueOf(entityId));
/* 29 */     teleport.write((Type)Type.INT, Integer.valueOf((int)(locX * 32.0D)));
/* 30 */     teleport.write((Type)Type.INT, Integer.valueOf((int)(locY * 32.0D)));
/* 31 */     teleport.write((Type)Type.INT, Integer.valueOf((int)(locZ * 32.0D)));
/* 32 */     teleport.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(yaw / 360.0F * 256.0F)));
/* 33 */     teleport.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(pitch / 360.0F * 256.0F)));
/*    */     
/* 35 */     PacketUtil.sendPacket(teleport, Protocol1_7_6_10TO1_8.class, true, true);
/*    */   }
/*    */   
/*    */   protected void sendHeadYaw(int entityId, float headYaw) {
/* 39 */     PacketWrapper head = PacketWrapper.create((PacketType)ClientboundPackets1_7.ENTITY_HEAD_LOOK, null, this.user);
/* 40 */     head.write((Type)Type.INT, Integer.valueOf(entityId));
/* 41 */     head.write((Type)Type.BYTE, Byte.valueOf((byte)(int)(headYaw / 360.0F * 256.0F)));
/* 42 */     PacketUtil.sendPacket(head, Protocol1_7_6_10TO1_8.class, true, true);
/*    */   }
/*    */   
/*    */   protected void sendSpawn(int entityId, int type, double locX, double locY, double locZ) {
/* 46 */     PacketWrapper spawn = PacketWrapper.create((PacketType)ClientboundPackets1_7.SPAWN_MOB, null, this.user);
/* 47 */     spawn.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 48 */     spawn.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)type));
/* 49 */     spawn.write((Type)Type.INT, Integer.valueOf((int)(locX * 32.0D)));
/* 50 */     spawn.write((Type)Type.INT, Integer.valueOf((int)(locY * 32.0D)));
/* 51 */     spawn.write((Type)Type.INT, Integer.valueOf((int)(locZ * 32.0D)));
/* 52 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 53 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 54 */     spawn.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 55 */     spawn.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 56 */     spawn.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 57 */     spawn.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 58 */     spawn.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
/*    */     
/* 60 */     PacketUtil.sendPacket(spawn, Protocol1_7_6_10TO1_8.class, true, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\entityreplacements\EntityReplacement1_7to1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */