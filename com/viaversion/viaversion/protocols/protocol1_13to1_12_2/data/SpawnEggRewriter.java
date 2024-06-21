/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.Optional;
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
/*    */ public class SpawnEggRewriter
/*    */ {
/* 25 */   private static final BiMap<String, Integer> spawnEggs = (BiMap<String, Integer>)HashBiMap.create();
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 30 */     registerSpawnEgg("minecraft:bat");
/* 31 */     registerSpawnEgg("minecraft:blaze");
/* 32 */     registerSpawnEgg("minecraft:cave_spider");
/* 33 */     registerSpawnEgg("minecraft:chicken");
/* 34 */     registerSpawnEgg("minecraft:cow");
/* 35 */     registerSpawnEgg("minecraft:creeper");
/* 36 */     registerSpawnEgg("minecraft:donkey");
/* 37 */     registerSpawnEgg("minecraft:elder_guardian");
/* 38 */     registerSpawnEgg("minecraft:enderman");
/* 39 */     registerSpawnEgg("minecraft:endermite");
/* 40 */     registerSpawnEgg("minecraft:evocation_illager");
/* 41 */     registerSpawnEgg("minecraft:ghast");
/* 42 */     registerSpawnEgg("minecraft:guardian");
/* 43 */     registerSpawnEgg("minecraft:horse");
/* 44 */     registerSpawnEgg("minecraft:husk");
/* 45 */     registerSpawnEgg("minecraft:llama");
/* 46 */     registerSpawnEgg("minecraft:magma_cube");
/* 47 */     registerSpawnEgg("minecraft:mooshroom");
/* 48 */     registerSpawnEgg("minecraft:mule");
/* 49 */     registerSpawnEgg("minecraft:ocelot");
/*    */     
/* 51 */     registerSpawnEgg("minecraft:parrot");
/* 52 */     registerSpawnEgg("minecraft:pig");
/* 53 */     registerSpawnEgg("minecraft:polar_bear");
/* 54 */     registerSpawnEgg("minecraft:rabbit");
/* 55 */     registerSpawnEgg("minecraft:sheep");
/* 56 */     registerSpawnEgg("minecraft:shulker");
/* 57 */     registerSpawnEgg("minecraft:silverfish");
/* 58 */     registerSpawnEgg("minecraft:skeleton");
/* 59 */     registerSpawnEgg("minecraft:skeleton_horse");
/* 60 */     registerSpawnEgg("minecraft:slime");
/* 61 */     registerSpawnEgg("minecraft:spider");
/* 62 */     registerSpawnEgg("minecraft:squid");
/* 63 */     registerSpawnEgg("minecraft:stray");
/* 64 */     registerSpawnEgg("minecraft:vex");
/* 65 */     registerSpawnEgg("minecraft:villager");
/* 66 */     registerSpawnEgg("minecraft:vindication_illager");
/* 67 */     registerSpawnEgg("minecraft:witch");
/* 68 */     registerSpawnEgg("minecraft:wither_skeleton");
/* 69 */     registerSpawnEgg("minecraft:wolf");
/* 70 */     registerSpawnEgg("minecraft:zombie");
/* 71 */     registerSpawnEgg("minecraft:zombie_horse");
/* 72 */     registerSpawnEgg("minecraft:zombie_pigman");
/* 73 */     registerSpawnEgg("minecraft:zombie_villager");
/*    */   }
/*    */   
/*    */   private static void registerSpawnEgg(String key) {
/* 77 */     spawnEggs.put(key, Integer.valueOf(spawnEggs.size()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getSpawnEggId(String entityIdentifier) {
/* 83 */     if (!spawnEggs.containsKey(entityIdentifier))
/*    */     {
/* 85 */       return -1; } 
/* 86 */     return 0x17F0000 | ((Integer)spawnEggs.get(entityIdentifier)).intValue() & 0xFFFF;
/*    */   }
/*    */   
/*    */   public static Optional<String> getEntityId(int spawnEggId) {
/* 90 */     if (spawnEggId >> 16 != 383) return Optional.empty(); 
/* 91 */     return Optional.ofNullable(spawnEggs.inverse().get(Integer.valueOf(spawnEggId & 0xFFFF)));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\SpawnEggRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */