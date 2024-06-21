/*    */ package com.viaversion.viaversion.protocols.protocol1_11to1_10;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
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
/*    */ public class BlockEntityRewriter
/*    */ {
/* 24 */   private static BiMap<String, String> oldToNewNames = (BiMap<String, String>)HashBiMap.create();
/*    */ 
/*    */   
/*    */   static {
/* 28 */     rewrite("Furnace", "furnace");
/* 29 */     rewrite("Chest", "chest");
/* 30 */     rewrite("EnderChest", "ender_chest");
/* 31 */     rewrite("RecordPlayer", "jukebox");
/* 32 */     rewrite("Trap", "dispenser");
/* 33 */     rewrite("Dropper", "dropper");
/* 34 */     rewrite("Sign", "sign");
/* 35 */     rewrite("MobSpawner", "mob_spawner");
/* 36 */     rewrite("Music", "noteblock");
/* 37 */     rewrite("Piston", "piston");
/* 38 */     rewrite("Cauldron", "brewing_stand");
/* 39 */     rewrite("EnchantTable", "enchanting_table");
/* 40 */     rewrite("Airportal", "end_portal");
/* 41 */     rewrite("Beacon", "beacon");
/* 42 */     rewrite("Skull", "skull");
/* 43 */     rewrite("DLDetector", "daylight_detector");
/* 44 */     rewrite("Hopper", "hopper");
/* 45 */     rewrite("Comparator", "comparator");
/* 46 */     rewrite("FlowerPot", "flower_pot");
/* 47 */     rewrite("Banner", "banner");
/* 48 */     rewrite("Structure", "structure_block");
/* 49 */     rewrite("EndGateway", "end_gateway");
/* 50 */     rewrite("Control", "command_block");
/*    */   }
/*    */   
/*    */   private static void rewrite(String oldName, String newName) {
/* 54 */     oldToNewNames.put(oldName, "minecraft:" + newName);
/*    */   }
/*    */   
/*    */   public static BiMap<String, String> inverse() {
/* 58 */     return oldToNewNames.inverse();
/*    */   }
/*    */   
/*    */   public static String toNewIdentifier(String oldId) {
/* 62 */     String newName = (String)oldToNewNames.get(oldId);
/* 63 */     if (newName != null) {
/* 64 */       return newName;
/*    */     }
/* 66 */     return oldId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\BlockEntityRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */