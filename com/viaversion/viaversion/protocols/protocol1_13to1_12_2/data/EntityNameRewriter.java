/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
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
/*    */ public class EntityNameRewriter
/*    */ {
/* 40 */   private static final Map<String, String> entityNames = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 46 */     reg("commandblock_minecart", "command_block_minecart");
/* 47 */     reg("ender_crystal", "end_crystal");
/* 48 */     reg("evocation_fangs", "evoker_fangs");
/* 49 */     reg("evocation_illager", "evoker");
/* 50 */     reg("eye_of_ender_signal", "eye_of_ender");
/* 51 */     reg("fireworks_rocket", "firework_rocket");
/* 52 */     reg("illusion_illager", "illusioner");
/* 53 */     reg("snowman", "snow_golem");
/* 54 */     reg("villager_golem", "iron_golem");
/* 55 */     reg("vindication_illager", "vindicator");
/* 56 */     reg("xp_bottle", "experience_bottle");
/* 57 */     reg("xp_orb", "experience_orb");
/*    */   }
/*    */ 
/*    */   
/*    */   private static void reg(String past, String future) {
/* 62 */     entityNames.put("minecraft:" + past, "minecraft:" + future);
/*    */   }
/*    */   
/*    */   public static String rewrite(String entName) {
/* 66 */     String entityName = entityNames.get(entName);
/* 67 */     if (entityName != null) {
/* 68 */       return entityName;
/*    */     }
/* 70 */     entityName = entityNames.get("minecraft:" + entName);
/* 71 */     if (entityName != null) {
/* 72 */       return entityName;
/*    */     }
/* 74 */     return entName;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\EntityNameRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */