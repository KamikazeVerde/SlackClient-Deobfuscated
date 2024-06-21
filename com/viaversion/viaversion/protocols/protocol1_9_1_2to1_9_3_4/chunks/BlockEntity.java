/*    */ package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockEntity
/*    */ {
/* 34 */   private static final Map<String, Integer> types = new HashMap<>();
/*    */   
/*    */   static {
/* 37 */     types.put("MobSpawner", Integer.valueOf(1));
/* 38 */     types.put("Control", Integer.valueOf(2));
/* 39 */     types.put("Beacon", Integer.valueOf(3));
/* 40 */     types.put("Skull", Integer.valueOf(4));
/* 41 */     types.put("FlowerPot", Integer.valueOf(5));
/* 42 */     types.put("Banner", Integer.valueOf(6));
/* 43 */     types.put("UNKNOWN", Integer.valueOf(7));
/* 44 */     types.put("EndGateway", Integer.valueOf(8));
/* 45 */     types.put("Sign", Integer.valueOf(9));
/*    */   }
/*    */   
/*    */   public static void handle(List<CompoundTag> tags, UserConnection connection) {
/* 49 */     for (CompoundTag tag : tags) {
/*    */       try {
/* 51 */         if (!tag.contains("id")) {
/* 52 */           throw new Exception("NBT tag not handled because the id key is missing");
/*    */         }
/* 54 */         String id = (String)tag.get("id").getValue();
/* 55 */         if (!types.containsKey(id)) {
/* 56 */           throw new Exception("Not handled id: " + id);
/*    */         }
/* 58 */         int newId = ((Integer)types.get(id)).intValue();
/* 59 */         if (newId == -1) {
/*    */           continue;
/*    */         }
/* 62 */         int x = ((NumberTag)tag.get("x")).asInt();
/* 63 */         int y = ((NumberTag)tag.get("y")).asInt();
/* 64 */         int z = ((NumberTag)tag.get("z")).asInt();
/*    */         
/* 66 */         Position pos = new Position(x, (short)y, z);
/*    */         
/* 68 */         updateBlockEntity(pos, (short)newId, tag, connection);
/* 69 */       } catch (Exception e) {
/* 70 */         if (Via.getManager().isDebug()) {
/* 71 */           Via.getPlatform().getLogger().warning("Block Entity: " + e.getMessage() + ": " + tag);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void updateBlockEntity(Position pos, short id, CompoundTag tag, UserConnection connection) throws Exception {
/* 78 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, null, connection);
/* 79 */     wrapper.write(Type.POSITION, pos);
/* 80 */     wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(id));
/* 81 */     wrapper.write(Type.NBT, tag);
/* 82 */     wrapper.scheduleSend(Protocol1_9_1_2To1_9_3_4.class, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_1_2to1_9_3_4\chunks\BlockEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */