/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
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
/*    */ 
/*    */ public class BannerHandler
/*    */   implements BlockEntityProvider.BlockEntityHandler
/*    */ {
/*    */   private static final int WALL_BANNER_START = 7110;
/*    */   private static final int WALL_BANNER_STOP = 7173;
/*    */   private static final int BANNER_START = 6854;
/*    */   private static final int BANNER_STOP = 7109;
/*    */   
/*    */   public int transform(UserConnection user, CompoundTag tag) {
/* 42 */     BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
/* 43 */     Position position = new Position((int)getLong((NumberTag)tag.get("x")), (short)(int)getLong((NumberTag)tag.get("y")), (int)getLong((NumberTag)tag.get("z")));
/*    */     
/* 45 */     if (!storage.contains(position)) {
/* 46 */       Via.getPlatform().getLogger().warning("Received an banner color update packet, but there is no banner! O_o " + tag);
/* 47 */       return -1;
/*    */     } 
/*    */     
/* 50 */     int blockId = storage.get(position).getOriginal();
/*    */     
/* 52 */     Tag base = tag.get("Base");
/* 53 */     int color = 0;
/* 54 */     if (base instanceof NumberTag) {
/* 55 */       color = ((NumberTag)base).asInt();
/*    */     }
/*    */     
/* 58 */     if (blockId >= 6854 && blockId <= 7109) {
/* 59 */       blockId += (15 - color) * 16;
/*    */     }
/* 61 */     else if (blockId >= 7110 && blockId <= 7173) {
/* 62 */       blockId += (15 - color) * 4;
/*    */     } else {
/* 64 */       Via.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + tag);
/*    */     } 
/*    */     
/* 67 */     Tag patterns = tag.get("Patterns");
/* 68 */     if (patterns instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag) {
/* 69 */       for (Tag pattern : patterns) {
/* 70 */         if (pattern instanceof CompoundTag) {
/* 71 */           Tag c = ((CompoundTag)pattern).get("Color");
/* 72 */           if (c instanceof IntTag) {
/* 73 */             ((IntTag)c).setValue(15 - ((Integer)c.getValue()).intValue());
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 79 */     Tag name = tag.get("CustomName");
/* 80 */     if (name instanceof StringTag) {
/* 81 */       ((StringTag)name).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)name).getValue()));
/*    */     }
/*    */     
/* 84 */     return blockId;
/*    */   }
/*    */   
/*    */   private long getLong(NumberTag tag) {
/* 88 */     return tag.asLong();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\blockentities\BannerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */