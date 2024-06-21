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
/*    */ public class SkullHandler
/*    */   implements BlockEntityProvider.BlockEntityHandler
/*    */ {
/*    */   private static final int SKULL_WALL_START = 5447;
/*    */   private static final int SKULL_END = 5566;
/*    */   
/*    */   public int transform(UserConnection user, CompoundTag tag) {
/* 35 */     BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
/* 36 */     Position position = new Position((int)getLong((NumberTag)tag.get("x")), (short)(int)getLong((NumberTag)tag.get("y")), (int)getLong((NumberTag)tag.get("z")));
/*    */     
/* 38 */     if (!storage.contains(position)) {
/* 39 */       Via.getPlatform().getLogger().warning("Received an head update packet, but there is no head! O_o " + tag);
/* 40 */       return -1;
/*    */     } 
/*    */     
/* 43 */     int id = storage.get(position).getOriginal();
/* 44 */     if (id >= 5447 && id <= 5566) {
/* 45 */       Tag skullType = tag.get("SkullType");
/* 46 */       if (skullType instanceof NumberTag) {
/* 47 */         id += ((NumberTag)skullType).asInt() * 20;
/*    */       }
/* 49 */       Tag rot = tag.get("Rot");
/* 50 */       if (rot instanceof NumberTag) {
/* 51 */         id += ((NumberTag)rot).asInt();
/*    */       }
/*    */     } else {
/* 54 */       Via.getPlatform().getLogger().warning("Why does this block have the skull block entity? " + tag);
/* 55 */       return -1;
/*    */     } 
/*    */     
/* 58 */     return id;
/*    */   }
/*    */   
/*    */   private long getLong(NumberTag tag) {
/* 62 */     return tag.asLong();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\blockentities\SkullHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */