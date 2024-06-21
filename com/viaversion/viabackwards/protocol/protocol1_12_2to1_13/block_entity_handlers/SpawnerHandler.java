/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityNameRewrites;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
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
/*    */ public class SpawnerHandler
/*    */   implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
/*    */ {
/*    */   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
/* 32 */     Tag dataTag = tag.get("SpawnData");
/* 33 */     if (dataTag instanceof CompoundTag) {
/* 34 */       CompoundTag data = (CompoundTag)dataTag;
/* 35 */       Tag idTag = data.get("id");
/* 36 */       if (idTag instanceof StringTag) {
/* 37 */         StringTag s = (StringTag)idTag;
/* 38 */         s.setValue(EntityNameRewrites.rewrite(s.getValue()));
/*    */       } 
/*    */     } 
/* 41 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\block_entity_handlers\SpawnerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */