/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.BannerHandler;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.BedHandler;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.CommandBlockHandler;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.FlowerPotHandler;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.SkullHandler;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.SpawnerHandler;
/*    */ import java.util.HashMap;
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
/*    */ public class BlockEntityProvider
/*    */   implements Provider
/*    */ {
/* 40 */   private final Map<String, BlockEntityHandler> handlers = new HashMap<>();
/*    */   
/*    */   public BlockEntityProvider() {
/* 43 */     this.handlers.put("minecraft:flower_pot", new FlowerPotHandler());
/* 44 */     this.handlers.put("minecraft:bed", new BedHandler());
/* 45 */     this.handlers.put("minecraft:banner", new BannerHandler());
/* 46 */     this.handlers.put("minecraft:skull", new SkullHandler());
/* 47 */     this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
/* 48 */     this.handlers.put("minecraft:command_block", new CommandBlockHandler());
/*    */   }
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
/*    */   public int transform(UserConnection user, Position position, CompoundTag tag, boolean sendUpdate) throws Exception {
/* 62 */     Tag idTag = tag.get("id");
/* 63 */     if (idTag == null) return -1;
/*    */     
/* 65 */     String id = (String)idTag.getValue();
/* 66 */     BlockEntityHandler handler = this.handlers.get(id);
/* 67 */     if (handler == null) {
/* 68 */       if (Via.getManager().isDebug()) {
/* 69 */         Via.getPlatform().getLogger().warning("Unhandled BlockEntity " + id + " full tag: " + tag);
/*    */       }
/* 71 */       return -1;
/*    */     } 
/*    */     
/* 74 */     int newBlock = handler.transform(user, tag);
/*    */     
/* 76 */     if (sendUpdate && newBlock != -1) {
/* 77 */       sendBlockChange(user, position, newBlock);
/*    */     }
/*    */     
/* 80 */     return newBlock;
/*    */   }
/*    */   
/*    */   private void sendBlockChange(UserConnection user, Position position, int blockId) throws Exception {
/* 84 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_13.BLOCK_CHANGE, null, user);
/* 85 */     wrapper.write(Type.POSITION, position);
/* 86 */     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(blockId));
/*    */     
/* 88 */     wrapper.send(Protocol1_13To1_12_2.class);
/*    */   }
/*    */   
/*    */   public static interface BlockEntityHandler {
/*    */     int transform(UserConnection param1UserConnection, CompoundTag param1CompoundTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\BlockEntityProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */