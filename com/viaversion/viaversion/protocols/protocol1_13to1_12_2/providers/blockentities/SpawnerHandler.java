/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityNameRewriter;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
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
/*    */   implements BlockEntityProvider.BlockEntityHandler
/*    */ {
/*    */   public int transform(UserConnection user, CompoundTag tag) {
/* 30 */     Tag data = tag.get("SpawnData");
/* 31 */     if (data instanceof CompoundTag) {
/* 32 */       Tag id = ((CompoundTag)data).get("id");
/* 33 */       if (id instanceof StringTag) {
/* 34 */         ((StringTag)id).setValue(EntityNameRewriter.rewrite(((StringTag)id).getValue()));
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 40 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\blockentities\SpawnerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */