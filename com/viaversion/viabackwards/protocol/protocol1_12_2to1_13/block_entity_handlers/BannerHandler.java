/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;
/*    */ 
/*    */ import com.viaversion.viabackwards.ViaBackwards;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
/*    */ 
/*    */ 
/*    */ public class BannerHandler
/*    */   implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
/*    */ {
/*    */   private static final int WALL_BANNER_START = 7110;
/*    */   private static final int WALL_BANNER_STOP = 7173;
/*    */   private static final int BANNER_START = 6854;
/*    */   private static final int BANNER_STOP = 7109;
/*    */   
/*    */   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
/* 39 */     if (blockId >= 6854 && blockId <= 7109) {
/* 40 */       int color = blockId - 6854 >> 4;
/* 41 */       tag.put("Base", (Tag)new IntTag(15 - color));
/*    */     
/*    */     }
/* 44 */     else if (blockId >= 7110 && blockId <= 7173) {
/* 45 */       int color = blockId - 7110 >> 2;
/* 46 */       tag.put("Base", (Tag)new IntTag(15 - color));
/*    */     } else {
/* 48 */       ViaBackwards.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + tag);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     Tag patternsTag = tag.get("Patterns");
/* 53 */     if (patternsTag instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag) {
/* 54 */       for (Tag pattern : patternsTag) {
/* 55 */         if (!(pattern instanceof CompoundTag))
/*    */           continue; 
/* 57 */         IntTag c = (IntTag)((CompoundTag)pattern).get("Color");
/* 58 */         c.setValue(15 - c.asInt());
/*    */       } 
/*    */     }
/*    */     
/* 62 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\block_entity_handlers\BannerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */