/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamedSoundRewriter
/*     */ {
/*  25 */   private static final Map<String, String> oldToNew = new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/*  29 */     oldToNew.put("block.cloth.break", "block.wool.break");
/*  30 */     oldToNew.put("block.cloth.fall", "block.wool.fall");
/*  31 */     oldToNew.put("block.cloth.hit", "block.wool.hit");
/*  32 */     oldToNew.put("block.cloth.place", "block.wool.place");
/*  33 */     oldToNew.put("block.cloth.step", "block.wool.step");
/*  34 */     oldToNew.put("block.enderchest.close", "block.ender_chest.close");
/*  35 */     oldToNew.put("block.enderchest.open", "block.ender_chest.open");
/*  36 */     oldToNew.put("block.metal_pressureplate.click_off", "block.metal_pressure_plate.click_off");
/*  37 */     oldToNew.put("block.metal_pressureplate.click_on", "block.metal_pressure_plate.click_on");
/*  38 */     oldToNew.put("block.note.basedrum", "block.note_block.basedrum");
/*  39 */     oldToNew.put("block.note.bass", "block.note_block.bass");
/*  40 */     oldToNew.put("block.note.bell", "block.note_block.bell");
/*  41 */     oldToNew.put("block.note.chime", "block.note_block.chime");
/*  42 */     oldToNew.put("block.note.flute", "block.note_block.flute");
/*  43 */     oldToNew.put("block.note.guitar", "block.note_block.guitar");
/*  44 */     oldToNew.put("block.note.harp", "block.note_block.harp");
/*  45 */     oldToNew.put("block.note.hat", "block.note_block.hat");
/*  46 */     oldToNew.put("block.note.pling", "block.note_block.pling");
/*  47 */     oldToNew.put("block.note.snare", "block.note_block.snare");
/*  48 */     oldToNew.put("block.note.xylophone", "block.note_block.xylophone");
/*  49 */     oldToNew.put("block.slime.break", "block.slime_block.break");
/*  50 */     oldToNew.put("block.slime.fall", "block.slime_block.fall");
/*  51 */     oldToNew.put("block.slime.hit", "block.slime_block.hit");
/*  52 */     oldToNew.put("block.slime.place", "block.slime_block.place");
/*  53 */     oldToNew.put("block.slime.step", "block.slime_block.step");
/*  54 */     oldToNew.put("block.stone_pressureplate.click_off", "block.stone_pressure_plate.click_off");
/*  55 */     oldToNew.put("block.stone_pressureplate.click_on", "block.stone_pressure_plate.click_on");
/*  56 */     oldToNew.put("block.waterlily.place", "block.lily_pad.place");
/*  57 */     oldToNew.put("block.wood_pressureplate.click_off", "block.wooden_pressure_plate.click_off");
/*  58 */     oldToNew.put("block.wood_button.click_on", "block.wooden_button.click_on");
/*  59 */     oldToNew.put("block.wood_button.click_off", "block.wooden_button.click_off");
/*  60 */     oldToNew.put("block.wood_pressureplate.click_on", "block.wooden_pressure_plate.click_on");
/*  61 */     oldToNew.put("entity.armorstand.break", "entity.armor_stand.break");
/*  62 */     oldToNew.put("entity.armorstand.fall", "entity.armor_stand.fall");
/*  63 */     oldToNew.put("entity.armorstand.hit", "entity.armor_stand.hit");
/*  64 */     oldToNew.put("entity.armorstand.place", "entity.armor_stand.place");
/*  65 */     oldToNew.put("entity.bobber.retrieve", "entity.fishing_bobber.retrieve");
/*  66 */     oldToNew.put("entity.bobber.splash", "entity.fishing_bobber.splash");
/*  67 */     oldToNew.put("entity.bobber.throw", "entity.fishing_bobber.throw");
/*  68 */     oldToNew.put("entity.enderdragon.ambient", "entity.ender_dragon.ambient");
/*  69 */     oldToNew.put("entity.enderdragon.death", "entity.ender_dragon.death");
/*  70 */     oldToNew.put("entity.enderdragon.flap", "entity.ender_dragon.flap");
/*  71 */     oldToNew.put("entity.enderdragon.growl", "entity.ender_dragon.growl");
/*  72 */     oldToNew.put("entity.enderdragon.hurt", "entity.ender_dragon.hurt");
/*  73 */     oldToNew.put("entity.enderdragon.shoot", "entity.ender_dragon.shoot");
/*  74 */     oldToNew.put("entity.enderdragon_fireball.explode", "entity.dragon_fireball.explode");
/*  75 */     oldToNew.put("entity.endereye.death", "entity.ender_eye.death");
/*  76 */     oldToNew.put("entity.endereye.launch", "entity.ender_eye.launch");
/*  77 */     oldToNew.put("entity.endermen.ambient", "entity.enderman.ambient");
/*  78 */     oldToNew.put("entity.endermen.death", "entity.enderman.death");
/*  79 */     oldToNew.put("entity.endermen.hurt", "entity.enderman.hurt");
/*  80 */     oldToNew.put("entity.endermen.scream", "entity.enderman.scream");
/*  81 */     oldToNew.put("entity.endermen.stare", "entity.enderman.stare");
/*  82 */     oldToNew.put("entity.endermen.teleport", "entity.enderman.teleport");
/*  83 */     oldToNew.put("entity.enderpearl.throw", "entity.ender_pearl.throw");
/*  84 */     oldToNew.put("entity.evocation_illager.ambient", "entity.evoker.ambient");
/*  85 */     oldToNew.put("entity.evocation_illager.cast_spell", "entity.evoker.cast_spell");
/*  86 */     oldToNew.put("entity.evocation_illager.death", "entity.evoker.death");
/*  87 */     oldToNew.put("entity.evocation_illager.hurt", "entity.evoker.hurt");
/*  88 */     oldToNew.put("entity.evocation_illager.prepare_attack", "entity.evoker.prepare_attack");
/*  89 */     oldToNew.put("entity.evocation_illager.prepare_summon", "entity.evoker.prepare_summon");
/*  90 */     oldToNew.put("entity.evocation_illager.prepare_wololo", "entity.evoker.prepare_wololo");
/*  91 */     oldToNew.put("entity.firework.blast", "entity.firework_rocket.blast");
/*  92 */     oldToNew.put("entity.firework.blast_far", "entity.firework_rocket.blast_far");
/*  93 */     oldToNew.put("entity.firework.large_blast", "entity.firework_rocket.large_blast");
/*  94 */     oldToNew.put("entity.firework.large_blast_far", "entity.firework_rocket.large_blast_far");
/*  95 */     oldToNew.put("entity.firework.launch", "entity.firework_rocket.launch");
/*  96 */     oldToNew.put("entity.firework.shoot", "entity.firework_rocket.shoot");
/*  97 */     oldToNew.put("entity.firework.twinkle", "entity.firework_rocket.twinkle");
/*  98 */     oldToNew.put("entity.firework.twinkle_far", "entity.firework_rocket.twinkle_far");
/*  99 */     oldToNew.put("entity.illusion_illager.ambient", "entity.illusioner.ambient");
/* 100 */     oldToNew.put("entity.illusion_illager.cast_spell", "entity.illusioner.cast_spell");
/* 101 */     oldToNew.put("entity.illusion_illager.death", "entity.illusioner.death");
/* 102 */     oldToNew.put("entity.illusion_illager.hurt", "entity.illusioner.hurt");
/* 103 */     oldToNew.put("entity.illusion_illager.mirror_move", "entity.illusioner.mirror_move");
/* 104 */     oldToNew.put("entity.illusion_illager.prepare_blindness", "entity.illusioner.prepare_blindness");
/* 105 */     oldToNew.put("entity.illusion_illager.prepare_mirror", "entity.illusioner.prepare_mirror");
/* 106 */     oldToNew.put("entity.irongolem.attack", "entity.iron_golem.attack");
/* 107 */     oldToNew.put("entity.irongolem.death", "entity.iron_golem.death");
/* 108 */     oldToNew.put("entity.irongolem.hurt", "entity.iron_golem.hurt");
/* 109 */     oldToNew.put("entity.irongolem.step", "entity.iron_golem.step");
/* 110 */     oldToNew.put("entity.itemframe.add_item", "entity.item_frame.add_item");
/* 111 */     oldToNew.put("entity.itemframe.break", "entity.item_frame.break");
/* 112 */     oldToNew.put("entity.itemframe.place", "entity.item_frame.place");
/* 113 */     oldToNew.put("entity.itemframe.remove_item", "entity.item_frame.remove_item");
/* 114 */     oldToNew.put("entity.itemframe.rotate_item", "entity.item_frame.rotate_item");
/* 115 */     oldToNew.put("entity.leashknot.break", "entity.leash_knot.break");
/* 116 */     oldToNew.put("entity.leashknot.place", "entity.leash_knot.place");
/* 117 */     oldToNew.put("entity.lightning.impact", "entity.lightning_bolt.impact");
/* 118 */     oldToNew.put("entity.lightning.thunder", "entity.lightning_bolt.thunder");
/* 119 */     oldToNew.put("entity.lingeringpotion.throw", "entity.lingering_potion.throw");
/* 120 */     oldToNew.put("entity.magmacube.death", "entity.magma_cube.death");
/* 121 */     oldToNew.put("entity.magmacube.hurt", "entity.magma_cube.hurt");
/* 122 */     oldToNew.put("entity.magmacube.jump", "entity.magma_cube.jump");
/* 123 */     oldToNew.put("entity.magmacube.squish", "entity.magma_cube.squish");
/* 124 */     oldToNew.put("entity.parrot.imitate.enderdragon", "entity.parrot.imitate.ender_dragon");
/* 125 */     oldToNew.put("entity.parrot.imitate.evocation_illager", "entity.parrot.imitate.evoker");
/* 126 */     oldToNew.put("entity.parrot.imitate.illusion_illager", "entity.parrot.imitate.illusioner");
/* 127 */     oldToNew.put("entity.parrot.imitate.magmacube", "entity.parrot.imitate.magma_cube");
/* 128 */     oldToNew.put("entity.parrot.imitate.vindication_illager", "entity.parrot.imitate.vindicator");
/* 129 */     oldToNew.put("entity.player.splash.highspeed", "entity.player.splash.high_speed");
/* 130 */     oldToNew.put("entity.polar_bear.baby_ambient", "entity.polar_bear.ambient_baby");
/* 131 */     oldToNew.put("entity.small_magmacube.death", "entity.magma_cube.death_small");
/* 132 */     oldToNew.put("entity.small_magmacube.hurt", "entity.magma_cube.hurt_small");
/* 133 */     oldToNew.put("entity.small_magmacube.squish", "entity.magma_cube.squish_small");
/* 134 */     oldToNew.put("entity.small_slime.death", "entity.slime.death_small");
/* 135 */     oldToNew.put("entity.small_slime.hurt", "entity.slime.hurt_small");
/* 136 */     oldToNew.put("entity.small_slime.jump", "entity.slime.jump_small");
/* 137 */     oldToNew.put("entity.small_slime.squish", "entity.slime.squish_small");
/* 138 */     oldToNew.put("entity.snowman.ambient", "entity.snow_golem.ambient");
/* 139 */     oldToNew.put("entity.snowman.death", "entity.snow_golem.death");
/* 140 */     oldToNew.put("entity.snowman.hurt", "entity.snow_golem.hurt");
/* 141 */     oldToNew.put("entity.snowman.shoot", "entity.snow_golem.shoot");
/* 142 */     oldToNew.put("entity.villager.trading", "entity.villager.trade");
/* 143 */     oldToNew.put("entity.vindication_illager.ambient", "entity.vindicator.ambient");
/* 144 */     oldToNew.put("entity.vindication_illager.death", "entity.vindicator.death");
/* 145 */     oldToNew.put("entity.vindication_illager.hurt", "entity.vindicator.hurt");
/* 146 */     oldToNew.put("entity.zombie.attack_door_wood", "entity.zombie.attack_wooden_door");
/* 147 */     oldToNew.put("entity.zombie.break_door_wood", "entity.zombie.break_wooden_door");
/* 148 */     oldToNew.put("entity.zombie_pig.ambient", "entity.zombie_pigman.ambient");
/* 149 */     oldToNew.put("entity.zombie_pig.angry", "entity.zombie_pigman.angry");
/* 150 */     oldToNew.put("entity.zombie_pig.death", "entity.zombie_pigman.death");
/* 151 */     oldToNew.put("entity.zombie_pig.hurt", "entity.zombie_pigman.hurt");
/* 152 */     oldToNew.put("record.11", "music_disc.11");
/* 153 */     oldToNew.put("record.13", "music_disc.13");
/* 154 */     oldToNew.put("record.blocks", "music_disc.blocks");
/* 155 */     oldToNew.put("record.cat", "music_disc.cat");
/* 156 */     oldToNew.put("record.chirp", "music_disc.chirp");
/* 157 */     oldToNew.put("record.far", "music_disc.far");
/* 158 */     oldToNew.put("record.mall", "music_disc.mall");
/* 159 */     oldToNew.put("record.mellohi", "music_disc.mellohi");
/* 160 */     oldToNew.put("record.stal", "music_disc.stal");
/* 161 */     oldToNew.put("record.strad", "music_disc.strad");
/* 162 */     oldToNew.put("record.wait", "music_disc.wait");
/* 163 */     oldToNew.put("record.ward", "music_disc.ward");
/*     */   }
/*     */   
/*     */   public static String getNewId(String old) {
/* 167 */     String newId = oldToNew.get(old);
/* 168 */     return (newId != null) ? newId : old.toLowerCase(Locale.ROOT);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\NamedSoundRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */