/*    */ package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
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
/*    */ public class EntityTypeMapping
/*    */ {
/*    */   public static int getOldEntityId(int entityId) {
/* 26 */     if (entityId == 4) return Entity1_14Types.PUFFERFISH.getId(); 
/* 27 */     return (entityId >= 5) ? (entityId - 1) : entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14_4to1_15\data\EntityTypeMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */