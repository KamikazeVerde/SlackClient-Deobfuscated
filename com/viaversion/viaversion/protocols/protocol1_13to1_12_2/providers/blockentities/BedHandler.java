/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
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
/*    */ 
/*    */ 
/*    */ public class BedHandler
/*    */   implements BlockEntityProvider.BlockEntityHandler
/*    */ {
/*    */   public int transform(UserConnection user, CompoundTag tag) {
/* 33 */     BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
/* 34 */     Position position = new Position((int)getLong((NumberTag)tag.get("x")), (short)(int)getLong((NumberTag)tag.get("y")), (int)getLong((NumberTag)tag.get("z")));
/*    */     
/* 36 */     if (!storage.contains(position)) {
/* 37 */       Via.getPlatform().getLogger().warning("Received an bed color update packet, but there is no bed! O_o " + tag);
/* 38 */       return -1;
/*    */     } 
/*    */ 
/*    */     
/* 42 */     int blockId = storage.get(position).getOriginal() - 972 + 748;
/*    */     
/* 44 */     Tag color = tag.get("color");
/* 45 */     if (color instanceof NumberTag) {
/* 46 */       blockId += ((NumberTag)color).asInt() * 16;
/*    */     }
/*    */     
/* 49 */     return blockId;
/*    */   }
/*    */   
/*    */   private long getLong(NumberTag tag) {
/* 53 */     return tag.asLong();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\blockentities\BedHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */