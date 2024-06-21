/*    */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
/*    */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
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
/*    */ public class TranslationMappings
/*    */   extends ComponentRewriter<ClientboundPackets1_15>
/*    */ {
/* 30 */   private final Map<String, String> mappings = new HashMap<>();
/*    */   
/*    */   public TranslationMappings(Protocol1_16To1_15_2 protocol) {
/* 33 */     super((Protocol)protocol);
/* 34 */     this.mappings.put("attribute.name.generic.armorToughness", "attribute.name.generic.armor_toughness");
/* 35 */     this.mappings.put("attribute.name.generic.attackDamage", "attribute.name.generic.attack_damage");
/* 36 */     this.mappings.put("attribute.name.generic.attackSpeed", "attribute.name.generic.attack_speed");
/* 37 */     this.mappings.put("attribute.name.generic.followRange", "attribute.name.generic.follow_range");
/* 38 */     this.mappings.put("attribute.name.generic.knockbackResistance", "attribute.name.generic.knockback_resistance");
/* 39 */     this.mappings.put("attribute.name.generic.maxHealth", "attribute.name.generic.max_health");
/* 40 */     this.mappings.put("attribute.name.generic.movementSpeed", "attribute.name.generic.movement_speed");
/* 41 */     this.mappings.put("attribute.name.horse.jumpStrength", "attribute.name.horse.jump_strength");
/* 42 */     this.mappings.put("attribute.name.zombie.spawnReinforcements", "attribute.name.zombie.spawn_reinforcements");
/* 43 */     this.mappings.put("block.minecraft.banner", "Banner");
/* 44 */     this.mappings.put("block.minecraft.wall_banner", "Wall Banner");
/* 45 */     this.mappings.put("block.minecraft.bed", "Bed");
/* 46 */     this.mappings.put("block.minecraft.bed.not_valid", "block.minecraft.spawn.not_valid");
/* 47 */     this.mappings.put("block.minecraft.bed.set_spawn", "block.minecraft.set_spawn");
/* 48 */     this.mappings.put("block.minecraft.flowing_water", "Flowing Water");
/* 49 */     this.mappings.put("block.minecraft.flowing_lava", "Flowing Lava");
/* 50 */     this.mappings.put("block.minecraft.two_turtle_eggs", "Two Turtle Eggs");
/* 51 */     this.mappings.put("block.minecraft.three_turtle_eggs", "Three Turtle Eggs");
/* 52 */     this.mappings.put("block.minecraft.four_turtle_eggs", "Four Turtle Eggs");
/* 53 */     this.mappings.put("item.minecraft.skeleton_skull", "block.minecraft.skeleton_skull");
/* 54 */     this.mappings.put("item.minecraft.wither_skeleton_skull", "block.minecraft.skeleton_wall_skull");
/* 55 */     this.mappings.put("item.minecraft.zombie_head", "block.minecraft.zombie_head");
/* 56 */     this.mappings.put("item.minecraft.creeper_head", "block.minecraft.creeper_head");
/* 57 */     this.mappings.put("item.minecraft.dragon_head", "block.minecraft.dragon_head");
/* 58 */     this.mappings.put("entity.minecraft.zombie_pigman", "Zombie Pigman");
/* 59 */     this.mappings.put("item.minecraft.zombie_pigman_spawn_egg", "Zombie Pigman Spawn Egg");
/* 60 */     this.mappings.put("death.fell.accident.water", "%1$s fell out of the water");
/* 61 */     this.mappings.put("death.attack.netherBed.message", "death.attack.badRespawnPoint.message");
/* 62 */     this.mappings.put("death.attack.netherBed.link", "death.attack.badRespawnPoint.link");
/* 63 */     this.mappings.put("advancements.husbandry.break_diamond_hoe.title", "Serious Dedication");
/* 64 */     this.mappings.put("advancements.husbandry.break_diamond_hoe.description", "Completely use up a diamond hoe, and then reevaluate your life choices");
/* 65 */     this.mappings.put("biome.minecraft.nether", "Nether");
/* 66 */     this.mappings.put("key.swapHands", "key.swapOffhand");
/*    */   }
/*    */ 
/*    */   
/*    */   public void processText(JsonElement element) {
/* 71 */     super.processText(element);
/* 72 */     if (element == null || !element.isJsonObject()) {
/*    */       return;
/*    */     }
/* 75 */     JsonObject object = element.getAsJsonObject();
/* 76 */     JsonObject score = object.getAsJsonObject("score");
/* 77 */     if (score == null || object.has("text"))
/*    */       return; 
/* 79 */     JsonPrimitive value = score.getAsJsonPrimitive("value");
/* 80 */     if (value != null) {
/* 81 */       object.remove("score");
/* 82 */       object.add("text", (JsonElement)value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleTranslate(JsonObject object, String translate) {
/* 89 */     String mappedTranslation = this.mappings.get(translate);
/* 90 */     if (mappedTranslation != null)
/* 91 */       object.addProperty("translate", mappedTranslation); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\data\TranslationMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */