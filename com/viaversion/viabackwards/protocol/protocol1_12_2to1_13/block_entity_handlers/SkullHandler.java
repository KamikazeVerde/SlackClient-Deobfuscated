/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*    */ public class SkullHandler
/*    */   implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
/*    */ {
/*    */   private static final int SKULL_START = 5447;
/*    */   
/*    */   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
/* 31 */     int diff = blockId - 5447;
/* 32 */     int pos = diff % 20;
/* 33 */     byte type = (byte)(int)Math.floor((diff / 20.0F));
/*    */ 
/*    */     
/* 36 */     tag.put("SkullType", (Tag)new ByteTag(type));
/*    */ 
/*    */     
/* 39 */     if (pos < 4) {
/* 40 */       return tag;
/*    */     }
/*    */ 
/*    */     
/* 44 */     tag.put("Rot", (Tag)new ByteTag((byte)(pos - 4 & 0xFF)));
/*    */     
/* 46 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\block_entity_handlers\SkullHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */