/*    */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*    */ public class MappingData
/*    */   extends MappingDataBase
/*    */ {
/* 26 */   private final BiMap<String, String> attributeMappings = (BiMap<String, String>)HashBiMap.create();
/*    */   
/*    */   public MappingData() {
/* 29 */     super("1.15", "1.16");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/* 34 */     this.attributeMappings.put("generic.maxHealth", "minecraft:generic.max_health");
/* 35 */     this.attributeMappings.put("zombie.spawnReinforcements", "minecraft:zombie.spawn_reinforcements");
/* 36 */     this.attributeMappings.put("horse.jumpStrength", "minecraft:horse.jump_strength");
/* 37 */     this.attributeMappings.put("generic.followRange", "minecraft:generic.follow_range");
/* 38 */     this.attributeMappings.put("generic.knockbackResistance", "minecraft:generic.knockback_resistance");
/* 39 */     this.attributeMappings.put("generic.movementSpeed", "minecraft:generic.movement_speed");
/* 40 */     this.attributeMappings.put("generic.flyingSpeed", "minecraft:generic.flying_speed");
/* 41 */     this.attributeMappings.put("generic.attackDamage", "minecraft:generic.attack_damage");
/* 42 */     this.attributeMappings.put("generic.attackKnockback", "minecraft:generic.attack_knockback");
/* 43 */     this.attributeMappings.put("generic.attackSpeed", "minecraft:generic.attack_speed");
/* 44 */     this.attributeMappings.put("generic.armorToughness", "minecraft:generic.armor_toughness");
/*    */   }
/*    */   
/*    */   public BiMap<String, String> getAttributeMappings() {
/* 48 */     return this.attributeMappings;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */