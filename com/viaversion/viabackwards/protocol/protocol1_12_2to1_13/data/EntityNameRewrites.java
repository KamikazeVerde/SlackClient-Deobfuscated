/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityNameRewrites
/*    */ {
/* 25 */   private static final Map<String, String> ENTITY_NAMES = new HashMap<>();
/*    */ 
/*    */   
/*    */   static {
/* 29 */     reg("commandblock_minecart", "command_block_minecart");
/* 30 */     reg("ender_crystal", "end_crystal");
/* 31 */     reg("evocation_fangs", "evoker_fangs");
/* 32 */     reg("evocation_illager", "evoker");
/* 33 */     reg("eye_of_ender_signal", "eye_of_ender");
/* 34 */     reg("fireworks_rocket", "firework_rocket");
/* 35 */     reg("illusion_illager", "illusioner");
/* 36 */     reg("snowman", "snow_golem");
/* 37 */     reg("villager_golem", "iron_golem");
/* 38 */     reg("vindication_illager", "vindicator");
/* 39 */     reg("xp_bottle", "experience_bottle");
/* 40 */     reg("xp_orb", "experience_orb");
/*    */   }
/*    */ 
/*    */   
/*    */   private static void reg(String past, String future) {
/* 45 */     ENTITY_NAMES.put("minecraft:" + future, "minecraft:" + past);
/*    */   }
/*    */   
/*    */   public static String rewrite(String entName) {
/* 49 */     String entityName = ENTITY_NAMES.get(entName);
/* 50 */     if (entityName != null) {
/* 51 */       return entityName;
/*    */     }
/* 53 */     entityName = ENTITY_NAMES.get("minecraft:" + entName);
/* 54 */     if (entityName != null) {
/* 55 */       return entityName;
/*    */     }
/* 57 */     return entName;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\data\EntityNameRewrites.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */