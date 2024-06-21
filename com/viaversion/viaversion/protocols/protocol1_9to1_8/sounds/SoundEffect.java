/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;
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
/*     */ 
/*     */ public enum SoundEffect
/*     */ {
/*  26 */   MOB_HORSE_ZOMBIE_IDLE("mob.horse.zombie.idle", "entity.zombie_horse.ambient", SoundCategory.NEUTRAL),
/*  27 */   NOTE_SNARE("note.snare", "block.note.snare", SoundCategory.RECORD),
/*  28 */   RANDOM_WOOD_CLICK("random.wood_click", "block.wood_button.click_on", SoundCategory.BLOCK),
/*  29 */   DIG_GRAVEL("dig.gravel", "block.gravel.place", SoundCategory.BLOCK),
/*  30 */   RANDOM_BOWHIT("random.bowhit", "block.tripwire.detach", SoundCategory.NEUTRAL),
/*  31 */   DIG_GLASS("dig.glass", "block.glass.break", SoundCategory.BLOCK),
/*  32 */   MOB_ZOMBIE_SAY("mob.zombie.say", "entity.zombie.ambient", SoundCategory.HOSTILE),
/*  33 */   MOB_PIG_DEATH("mob.pig.death", "entity.pig.death", SoundCategory.NEUTRAL),
/*  34 */   MOB_HORSE_DONKEY_HIT("mob.horse.donkey.hit", "entity.donkey.hurt", SoundCategory.NEUTRAL),
/*  35 */   GAME_NEUTRAL_SWIM("game.neutral.swim", "entity.player.swim", SoundCategory.NEUTRAL),
/*  36 */   GAME_PLAYER_SWIM("game.player.swim", "entity.player.swim", SoundCategory.PLAYER),
/*  37 */   MOB_ENDERMEN_IDLE("mob.endermen.idle", "entity.endermen.ambient", SoundCategory.HOSTILE),
/*  38 */   PORTAL_PORTAL("portal.portal", "block.portal.ambient", SoundCategory.BLOCK),
/*  39 */   RANDOM_FIZZ("random.fizz", "entity.generic.extinguish_fire", SoundCategory.BLOCK),
/*  40 */   NOTE_HARP("note.harp", "block.note.harp", SoundCategory.RECORD),
/*  41 */   STEP_SNOW("step.snow", "block.snow.step", SoundCategory.NEUTRAL),
/*  42 */   RANDOM_SUCCESSFUL_HIT("random.successful_hit", "entity.arrow.hit_player", SoundCategory.PLAYER),
/*  43 */   MOB_ZOMBIEPIG_ZPIGHURT("mob.zombiepig.zpighurt", "entity.zombie_pig.hurt", SoundCategory.HOSTILE),
/*  44 */   MOB_WOLF_HOWL("mob.wolf.howl", "entity.wolf.howl", SoundCategory.NEUTRAL),
/*  45 */   FIREWORKS_LAUNCH("fireworks.launch", "entity.firework.launch", SoundCategory.AMBIENT),
/*  46 */   MOB_COW_HURT("mob.cow.hurt", "entity.cow.death", SoundCategory.NEUTRAL),
/*  47 */   FIREWORKS_LARGEBLAST("fireworks.largeBlast", "entity.firework.large_blast", SoundCategory.AMBIENT),
/*  48 */   MOB_BLAZE_HIT("mob.blaze.hit", "entity.blaze.hurt", SoundCategory.HOSTILE),
/*  49 */   MOB_VILLAGER_DEATH("mob.villager.death", "entity.villager.death", SoundCategory.NEUTRAL),
/*  50 */   MOB_BLAZE_DEATH("mob.blaze.death", "entity.blaze.death", SoundCategory.HOSTILE),
/*  51 */   MOB_HORSE_ZOMBIE_DEATH("mob.horse.zombie.death", "entity.zombie_horse.death", SoundCategory.NEUTRAL),
/*  52 */   MOB_SILVERFISH_KILL("mob.silverfish.kill", "entity.endermite.death", SoundCategory.HOSTILE),
/*  53 */   MOB_WOLF_PANTING("mob.wolf.panting", "entity.wolf.pant", SoundCategory.NEUTRAL),
/*  54 */   NOTE_BASS("note.bass", "block.note.bass", SoundCategory.RECORD),
/*  55 */   DIG_STONE("dig.stone", "block.glass.place", SoundCategory.BLOCK),
/*  56 */   MOB_ENDERMEN_STARE("mob.endermen.stare", "entity.endermen.stare", SoundCategory.HOSTILE),
/*  57 */   GAME_PLAYER_SWIM_SPLASH("game.player.swim.splash", "entity.generic.splash", SoundCategory.BLOCK),
/*  58 */   MOB_SLIME_SMALL("mob.slime.small", "block.slime.hit", SoundCategory.HOSTILE),
/*  59 */   MOB_GHAST_DEATH("mob.ghast.death", "entity.ghast.death", SoundCategory.HOSTILE),
/*  60 */   MOB_GUARDIAN_ATTACK("mob.guardian.attack", "entity.guardian.attack", SoundCategory.HOSTILE),
/*  61 */   RANDOM_CLICK("random.click", "block.wood_pressureplate.click_on", SoundCategory.BLOCK),
/*  62 */   MOB_ZOMBIEPIG_ZPIG("mob.zombiepig.zpig", "entity.zombie_pig.ambient", SoundCategory.HOSTILE),
/*  63 */   GAME_PLAYER_DIE("game.player.die", "entity.player.death", SoundCategory.PLAYER),
/*  64 */   FIREWORKS_TWINKLE_FAR("fireworks.twinkle_far", "entity.firework.twinkle_far", SoundCategory.AMBIENT),
/*  65 */   MOB_GUARDIAN_LAND_IDLE("mob.guardian.land.idle", "entity.guardian.ambient_land", SoundCategory.HOSTILE),
/*  66 */   DIG_GRASS("dig.grass", "block.grass.place", SoundCategory.BLOCK),
/*  67 */   MOB_SKELETON_STEP("mob.skeleton.step", "entity.skeleton.step", SoundCategory.HOSTILE),
/*  68 */   MOB_WITHER_DEATH("mob.wither.death", "entity.wither.death", SoundCategory.HOSTILE),
/*  69 */   MOB_WOLF_HURT("mob.wolf.hurt", "entity.wolf.hurt", SoundCategory.NEUTRAL),
/*  70 */   MOB_HORSE_LEATHER("mob.horse.leather", "entity.horse.saddle", SoundCategory.NEUTRAL),
/*  71 */   MOB_BAT_LOOP("mob.bat.loop", "entity.bat.loop", SoundCategory.NEUTRAL),
/*  72 */   MOB_GHAST_SCREAM("mob.ghast.scream", "entity.ghast.hurt", SoundCategory.HOSTILE),
/*  73 */   GAME_PLAYER_HURT("game.player.hurt", "entity.player.death", SoundCategory.PLAYER),
/*  74 */   GAME_NEUTRAL_DIE("game.neutral.die", "entity.player.death", SoundCategory.NEUTRAL),
/*  75 */   MOB_CREEPER_DEATH("mob.creeper.death", "entity.creeper.death", SoundCategory.HOSTILE),
/*  76 */   MOB_HORSE_GALLOP("mob.horse.gallop", "entity.horse.gallop", SoundCategory.NEUTRAL),
/*  77 */   MOB_WITHER_SPAWN("mob.wither.spawn", "entity.wither.spawn", SoundCategory.HOSTILE),
/*  78 */   MOB_ENDERMEN_HIT("mob.endermen.hit", "entity.endermen.hurt", SoundCategory.HOSTILE),
/*  79 */   MOB_CREEPER_SAY("mob.creeper.say", "entity.creeper.hurt", SoundCategory.HOSTILE),
/*  80 */   MOB_HORSE_WOOD("mob.horse.wood", "entity.horse.step_wood", SoundCategory.NEUTRAL),
/*  81 */   MOB_ZOMBIE_UNFECT("mob.zombie.unfect", "entity.zombie_villager.converted", SoundCategory.HOSTILE),
/*  82 */   RANDOM_ANVIL_USE("random.anvil_use", "block.anvil.use", SoundCategory.BLOCK),
/*  83 */   RANDOM_CHESTCLOSED("random.chestclosed", "block.chest.close", SoundCategory.BLOCK),
/*  84 */   MOB_SHEEP_SHEAR("mob.sheep.shear", "entity.sheep.shear", SoundCategory.NEUTRAL),
/*  85 */   RANDOM_POP("random.pop", "entity.item.pickup", SoundCategory.PLAYER),
/*  86 */   MOB_BAT_DEATH("mob.bat.death", "entity.bat.death", SoundCategory.NEUTRAL),
/*  87 */   DIG_WOOD("dig.wood", "block.ladder.break", SoundCategory.BLOCK),
/*  88 */   MOB_HORSE_DONKEY_DEATH("mob.horse.donkey.death", "entity.donkey.death", SoundCategory.NEUTRAL),
/*  89 */   FIREWORKS_BLAST("fireworks.blast", "entity.firework.blast", SoundCategory.AMBIENT),
/*  90 */   MOB_ZOMBIEPIG_ZPIGANGRY("mob.zombiepig.zpigangry", "entity.zombie_pig.angry", SoundCategory.HOSTILE),
/*  91 */   GAME_HOSTILE_SWIM("game.hostile.swim", "entity.player.swim", SoundCategory.HOSTILE),
/*  92 */   MOB_GUARDIAN_FLOP("mob.guardian.flop", "entity.guardian.flop", SoundCategory.HOSTILE),
/*  93 */   MOB_VILLAGER_YES("mob.villager.yes", "entity.villager.yes", SoundCategory.NEUTRAL),
/*  94 */   MOB_GHAST_CHARGE("mob.ghast.charge", "entity.ghast.warn", SoundCategory.HOSTILE),
/*  95 */   CREEPER_PRIMED("creeper.primed", "entity.creeper.primed", SoundCategory.HOSTILE),
/*  96 */   DIG_SAND("dig.sand", "block.sand.break", SoundCategory.BLOCK),
/*  97 */   MOB_CHICKEN_SAY("mob.chicken.say", "entity.chicken.ambient", SoundCategory.NEUTRAL),
/*  98 */   RANDOM_DOOR_CLOSE("random.door_close", "block.wooden_door.close", SoundCategory.BLOCK),
/*  99 */   MOB_GUARDIAN_ELDER_DEATH("mob.guardian.elder.death", "entity.elder_guardian.death", SoundCategory.HOSTILE),
/* 100 */   FIREWORKS_TWINKLE("fireworks.twinkle", "entity.firework.twinkle", SoundCategory.AMBIENT),
/* 101 */   MOB_HORSE_SKELETON_DEATH("mob.horse.skeleton.death", "entity.skeleton_horse.death", SoundCategory.NEUTRAL),
/* 102 */   AMBIENT_WEATHER_RAIN("ambient.weather.rain", "weather.rain.above", SoundCategory.WEATHER),
/* 103 */   PORTAL_TRIGGER("portal.trigger", "block.portal.trigger", SoundCategory.BLOCK),
/* 104 */   RANDOM_CHESTOPEN("random.chestopen", "block.chest.open", SoundCategory.BLOCK),
/* 105 */   MOB_HORSE_LAND("mob.horse.land", "entity.horse.land", SoundCategory.NEUTRAL),
/* 106 */   MOB_SILVERFISH_STEP("mob.silverfish.step", "entity.silverfish.step", SoundCategory.HOSTILE),
/* 107 */   MOB_BAT_TAKEOFF("mob.bat.takeoff", "entity.bat.takeoff", SoundCategory.NEUTRAL),
/* 108 */   MOB_VILLAGER_NO("mob.villager.no", "entity.villager.no", SoundCategory.NEUTRAL),
/* 109 */   GAME_HOSTILE_HURT_FALL_BIG("game.hostile.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.HOSTILE),
/* 110 */   MOB_IRONGOLEM_WALK("mob.irongolem.walk", "entity.irongolem.step", SoundCategory.NEUTRAL),
/* 111 */   NOTE_HAT("note.hat", "block.note.hat", SoundCategory.RECORD),
/* 112 */   MOB_ZOMBIE_METAL("mob.zombie.metal", "entity.zombie.attack_iron_door", SoundCategory.HOSTILE),
/* 113 */   MOB_VILLAGER_HAGGLE("mob.villager.haggle", "entity.villager.trading", SoundCategory.NEUTRAL),
/* 114 */   MOB_GHAST_FIREBALL("mob.ghast.fireball", "entity.blaze.shoot", SoundCategory.HOSTILE),
/* 115 */   MOB_IRONGOLEM_DEATH("mob.irongolem.death", "entity.irongolem.death", SoundCategory.NEUTRAL),
/* 116 */   RANDOM_BREAK("random.break", "item.shield.break", SoundCategory.PLAYER),
/* 117 */   MOB_ZOMBIE_REMEDY("mob.zombie.remedy", "entity.zombie_villager.cure", SoundCategory.HOSTILE),
/* 118 */   RANDOM_BOW("random.bow", "entity.splash_potion.throw", SoundCategory.NEUTRAL),
/* 119 */   MOB_VILLAGER_IDLE("mob.villager.idle", "entity.villager.ambient", SoundCategory.NEUTRAL),
/* 120 */   STEP_CLOTH("step.cloth", "block.cloth.fall", SoundCategory.NEUTRAL),
/* 121 */   MOB_SILVERFISH_HIT("mob.silverfish.hit", "entity.endermite.hurt", SoundCategory.HOSTILE),
/* 122 */   LIQUID_LAVA("liquid.lava", "block.lava.ambient", SoundCategory.BLOCK),
/* 123 */   GAME_NEUTRAL_HURT_FALL_BIG("game.neutral.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.NEUTRAL),
/* 124 */   FIRE_FIRE("fire.fire", "block.fire.ambient", SoundCategory.BLOCK),
/* 125 */   MOB_ZOMBIE_WOOD("mob.zombie.wood", "entity.zombie.attack_door_wood", SoundCategory.HOSTILE),
/* 126 */   MOB_CHICKEN_STEP("mob.chicken.step", "entity.chicken.step", SoundCategory.NEUTRAL),
/* 127 */   MOB_GUARDIAN_LAND_HIT("mob.guardian.land.hit", "entity.guardian.hurt_land", SoundCategory.HOSTILE),
/* 128 */   MOB_CHICKEN_PLOP("mob.chicken.plop", "entity.donkey.chest", SoundCategory.NEUTRAL),
/* 129 */   MOB_ENDERDRAGON_WINGS("mob.enderdragon.wings", "entity.enderdragon.flap", SoundCategory.HOSTILE),
/* 130 */   STEP_GRASS("step.grass", "block.grass.hit", SoundCategory.NEUTRAL),
/* 131 */   MOB_HORSE_BREATHE("mob.horse.breathe", "entity.horse.breathe", SoundCategory.NEUTRAL),
/* 132 */   GAME_PLAYER_HURT_FALL_BIG("game.player.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.PLAYER),
/* 133 */   MOB_HORSE_DONKEY_IDLE("mob.horse.donkey.idle", "entity.donkey.ambient", SoundCategory.NEUTRAL),
/* 134 */   MOB_SPIDER_STEP("mob.spider.step", "entity.spider.step", SoundCategory.HOSTILE),
/* 135 */   GAME_NEUTRAL_HURT("game.neutral.hurt", "entity.player.death", SoundCategory.NEUTRAL),
/* 136 */   MOB_COW_SAY("mob.cow.say", "entity.cow.ambient", SoundCategory.NEUTRAL),
/* 137 */   MOB_HORSE_JUMP("mob.horse.jump", "entity.horse.jump", SoundCategory.NEUTRAL),
/* 138 */   MOB_HORSE_SOFT("mob.horse.soft", "entity.horse.step", SoundCategory.NEUTRAL),
/* 139 */   GAME_NEUTRAL_SWIM_SPLASH("game.neutral.swim.splash", "entity.generic.splash", SoundCategory.NEUTRAL),
/* 140 */   MOB_GUARDIAN_HIT("mob.guardian.hit", "entity.guardian.hurt", SoundCategory.HOSTILE),
/* 141 */   MOB_ENDERDRAGON_END("mob.enderdragon.end", "entity.enderdragon.death", SoundCategory.HOSTILE),
/* 142 */   MOB_ZOMBIE_STEP("mob.zombie.step", "entity.zombie.step", SoundCategory.HOSTILE),
/* 143 */   MOB_ENDERDRAGON_GROWL("mob.enderdragon.growl", "entity.enderdragon.growl", SoundCategory.HOSTILE),
/* 144 */   MOB_WOLF_SHAKE("mob.wolf.shake", "entity.wolf.shake", SoundCategory.NEUTRAL),
/* 145 */   MOB_ENDERMEN_DEATH("mob.endermen.death", "entity.endermen.death", SoundCategory.HOSTILE),
/* 146 */   RANDOM_ANVIL_LAND("random.anvil_land", "block.anvil.land", SoundCategory.BLOCK),
/* 147 */   GAME_HOSTILE_HURT("game.hostile.hurt", "entity.player.death", SoundCategory.HOSTILE),
/* 148 */   MINECART_INSIDE("minecart.inside", "entity.minecart.inside", SoundCategory.PLAYER),
/* 149 */   MOB_SLIME_BIG("mob.slime.big", "entity.slime.death", SoundCategory.HOSTILE),
/* 150 */   LIQUID_WATER("liquid.water", "block.water.ambient", SoundCategory.BLOCK),
/* 151 */   MOB_PIG_SAY("mob.pig.say", "entity.pig.ambient", SoundCategory.NEUTRAL),
/* 152 */   MOB_WITHER_SHOOT("mob.wither.shoot", "entity.wither.shoot", SoundCategory.HOSTILE),
/* 153 */   ITEM_FIRECHARGE_USE("item.fireCharge.use", "entity.blaze.shoot", SoundCategory.BLOCK),
/* 154 */   STEP_SAND("step.sand", "block.sand.fall", SoundCategory.NEUTRAL),
/* 155 */   MOB_IRONGOLEM_HIT("mob.irongolem.hit", "entity.irongolem.hurt", SoundCategory.NEUTRAL),
/* 156 */   MOB_HORSE_DEATH("mob.horse.death", "entity.horse.death", SoundCategory.NEUTRAL),
/* 157 */   MOB_BAT_HURT("mob.bat.hurt", "entity.bat.hurt", SoundCategory.NEUTRAL),
/* 158 */   MOB_GHAST_AFFECTIONATE_SCREAM("mob.ghast.affectionate_scream", "entity.ghast.scream", SoundCategory.HOSTILE),
/* 159 */   MOB_GUARDIAN_ELDER_IDLE("mob.guardian.elder.idle", "entity.elder_guardian.ambient", SoundCategory.HOSTILE),
/* 160 */   MOB_ZOMBIEPIG_ZPIGDEATH("mob.zombiepig.zpigdeath", "entity.zombie_pig.death", SoundCategory.HOSTILE),
/* 161 */   AMBIENT_WEATHER_THUNDER("ambient.weather.thunder", "entity.lightning.thunder", SoundCategory.WEATHER),
/* 162 */   MINECART_BASE("minecart.base", "entity.minecart.riding", SoundCategory.NEUTRAL),
/* 163 */   STEP_LADDER("step.ladder", "block.ladder.hit", SoundCategory.NEUTRAL),
/* 164 */   MOB_HORSE_DONKEY_ANGRY("mob.horse.donkey.angry", "entity.donkey.angry", SoundCategory.NEUTRAL),
/* 165 */   AMBIENT_CAVE_CAVE("ambient.cave.cave", "ambient.cave", SoundCategory.AMBIENT),
/* 166 */   FIREWORKS_BLAST_FAR("fireworks.blast_far", "entity.firework.blast_far", SoundCategory.AMBIENT),
/* 167 */   GAME_NEUTRAL_HURT_FALL_SMALL("game.neutral.hurt.fall.small", "entity.generic.small_fall", SoundCategory.NEUTRAL),
/* 168 */   GAME_HOSTILE_SWIM_SPLASH("game.hostile.swim.splash", "entity.generic.splash", SoundCategory.HOSTILE),
/* 169 */   RANDOM_DRINK("random.drink", "entity.generic.drink", SoundCategory.PLAYER),
/* 170 */   GAME_HOSTILE_DIE("game.hostile.die", "entity.player.death", SoundCategory.HOSTILE),
/* 171 */   MOB_CAT_HISS("mob.cat.hiss", "entity.cat.hiss", SoundCategory.NEUTRAL),
/* 172 */   NOTE_BD("note.bd", "block.note.basedrum", SoundCategory.RECORD),
/* 173 */   MOB_SPIDER_SAY("mob.spider.say", "entity.spider.hurt", SoundCategory.HOSTILE),
/* 174 */   STEP_STONE("step.stone", "block.anvil.hit", SoundCategory.NEUTRAL, true),
/* 175 */   RANDOM_LEVELUP("random.levelup", "entity.player.levelup", SoundCategory.PLAYER),
/* 176 */   LIQUID_LAVAPOP("liquid.lavapop", "block.lava.pop", SoundCategory.BLOCK),
/* 177 */   MOB_SHEEP_SAY("mob.sheep.say", "entity.sheep.ambient", SoundCategory.NEUTRAL),
/* 178 */   MOB_SKELETON_SAY("mob.skeleton.say", "entity.skeleton.ambient", SoundCategory.HOSTILE),
/* 179 */   MOB_BLAZE_BREATHE("mob.blaze.breathe", "entity.blaze.ambient", SoundCategory.HOSTILE),
/* 180 */   MOB_BAT_IDLE("mob.bat.idle", "entity.bat.ambient", SoundCategory.NEUTRAL),
/* 181 */   MOB_MAGMACUBE_BIG("mob.magmacube.big", "entity.magmacube.squish", SoundCategory.HOSTILE),
/* 182 */   MOB_HORSE_IDLE("mob.horse.idle", "entity.horse.ambient", SoundCategory.NEUTRAL),
/* 183 */   GAME_HOSTILE_HURT_FALL_SMALL("game.hostile.hurt.fall.small", "entity.generic.small_fall", SoundCategory.HOSTILE),
/* 184 */   MOB_HORSE_ZOMBIE_HIT("mob.horse.zombie.hit", "entity.zombie_horse.hurt", SoundCategory.NEUTRAL),
/* 185 */   MOB_IRONGOLEM_THROW("mob.irongolem.throw", "entity.irongolem.attack", SoundCategory.NEUTRAL),
/* 186 */   DIG_CLOTH("dig.cloth", "block.cloth.place", SoundCategory.BLOCK),
/* 187 */   STEP_GRAVEL("step.gravel", "block.gravel.hit", SoundCategory.NEUTRAL),
/* 188 */   MOB_SILVERFISH_SAY("mob.silverfish.say", "entity.silverfish.ambient", SoundCategory.HOSTILE),
/* 189 */   MOB_CAT_PURR("mob.cat.purr", "entity.cat.purr", SoundCategory.NEUTRAL),
/* 190 */   MOB_ZOMBIE_INFECT("mob.zombie.infect", "entity.zombie.infect", SoundCategory.HOSTILE),
/* 191 */   RANDOM_EAT("random.eat", "entity.generic.eat", SoundCategory.PLAYER),
/* 192 */   MOB_WOLF_BARK("mob.wolf.bark", "entity.wolf.ambient", SoundCategory.NEUTRAL),
/* 193 */   GAME_TNT_PRIMED("game.tnt.primed", "entity.creeper.primed", SoundCategory.BLOCK),
/* 194 */   MOB_SHEEP_STEP("mob.sheep.step", "entity.sheep.step", SoundCategory.NEUTRAL),
/* 195 */   MOB_ZOMBIE_DEATH("mob.zombie.death", "entity.zombie.death", SoundCategory.HOSTILE),
/* 196 */   RANDOM_DOOR_OPEN("random.door_open", "block.wooden_door.open", SoundCategory.BLOCK),
/* 197 */   MOB_ENDERMEN_PORTAL("mob.endermen.portal", "entity.endermen.teleport", SoundCategory.HOSTILE),
/* 198 */   MOB_HORSE_ANGRY("mob.horse.angry", "entity.horse.angry", SoundCategory.NEUTRAL),
/* 199 */   MOB_WOLF_GROWL("mob.wolf.growl", "entity.wolf.growl", SoundCategory.NEUTRAL),
/* 200 */   DIG_SNOW("dig.snow", "block.snow.place", SoundCategory.BLOCK),
/* 201 */   TILE_PISTON_OUT("tile.piston.out", "block.piston.extend", SoundCategory.BLOCK),
/* 202 */   RANDOM_BURP("random.burp", "entity.player.burp", SoundCategory.PLAYER),
/* 203 */   MOB_COW_STEP("mob.cow.step", "entity.cow.step", SoundCategory.NEUTRAL),
/* 204 */   MOB_WITHER_HURT("mob.wither.hurt", "entity.wither.hurt", SoundCategory.HOSTILE),
/* 205 */   MOB_GUARDIAN_LAND_DEATH("mob.guardian.land.death", "entity.elder_guardian.death_land", SoundCategory.HOSTILE),
/* 206 */   MOB_CHICKEN_HURT("mob.chicken.hurt", "entity.chicken.death", SoundCategory.NEUTRAL),
/* 207 */   MOB_WOLF_STEP("mob.wolf.step", "entity.wolf.step", SoundCategory.NEUTRAL),
/* 208 */   MOB_WOLF_DEATH("mob.wolf.death", "entity.wolf.death", SoundCategory.NEUTRAL),
/* 209 */   MOB_WOLF_WHINE("mob.wolf.whine", "entity.wolf.whine", SoundCategory.NEUTRAL),
/* 210 */   NOTE_PLING("note.pling", "block.note.pling", SoundCategory.RECORD),
/* 211 */   GAME_PLAYER_HURT_FALL_SMALL("game.player.hurt.fall.small", "entity.generic.small_fall", SoundCategory.PLAYER),
/* 212 */   MOB_CAT_PURREOW("mob.cat.purreow", "entity.cat.purreow", SoundCategory.NEUTRAL),
/* 213 */   FIREWORKS_LARGEBLAST_FAR("fireworks.largeBlast_far", "entity.firework.large_blast_far", SoundCategory.AMBIENT),
/* 214 */   MOB_SKELETON_HURT("mob.skeleton.hurt", "entity.skeleton.hurt", SoundCategory.HOSTILE),
/* 215 */   MOB_SPIDER_DEATH("mob.spider.death", "entity.spider.death", SoundCategory.HOSTILE),
/* 216 */   RANDOM_ANVIL_BREAK("random.anvil_break", "block.anvil.destroy", SoundCategory.BLOCK),
/* 217 */   MOB_WITHER_IDLE("mob.wither.idle", "entity.wither.ambient", SoundCategory.HOSTILE),
/* 218 */   MOB_GUARDIAN_ELDER_HIT("mob.guardian.elder.hit", "entity.elder_guardian.hurt", SoundCategory.HOSTILE),
/* 219 */   MOB_ENDERMEN_SCREAM("mob.endermen.scream", "entity.endermen.scream", SoundCategory.HOSTILE),
/* 220 */   MOB_CAT_HITT("mob.cat.hitt", "entity.cat.hurt", SoundCategory.NEUTRAL),
/* 221 */   MOB_MAGMACUBE_SMALL("mob.magmacube.small", "entity.small_magmacube.squish", SoundCategory.HOSTILE),
/* 222 */   FIRE_IGNITE("fire.ignite", "item.flintandsteel.use", SoundCategory.BLOCK, true),
/* 223 */   MOB_ENDERDRAGON_HIT("mob.enderdragon.hit", "entity.enderdragon.hurt", SoundCategory.HOSTILE),
/* 224 */   MOB_ZOMBIE_HURT("mob.zombie.hurt", "entity.zombie.hurt", SoundCategory.HOSTILE),
/* 225 */   RANDOM_EXPLODE("random.explode", "block.end_gateway.spawn", SoundCategory.BLOCK),
/* 226 */   MOB_SLIME_ATTACK("mob.slime.attack", "entity.slime.attack", SoundCategory.HOSTILE),
/* 227 */   MOB_MAGMACUBE_JUMP("mob.magmacube.jump", "entity.magmacube.jump", SoundCategory.HOSTILE),
/* 228 */   RANDOM_SPLASH("random.splash", "entity.bobber.splash", SoundCategory.PLAYER),
/* 229 */   MOB_HORSE_SKELETON_HIT("mob.horse.skeleton.hit", "entity.skeleton_horse.hurt", SoundCategory.NEUTRAL),
/* 230 */   MOB_GHAST_MOAN("mob.ghast.moan", "entity.ghast.ambient", SoundCategory.HOSTILE),
/* 231 */   MOB_GUARDIAN_CURSE("mob.guardian.curse", "entity.elder_guardian.curse", SoundCategory.HOSTILE),
/* 232 */   GAME_POTION_SMASH("game.potion.smash", "block.glass.break", SoundCategory.NEUTRAL),
/* 233 */   NOTE_BASSATTACK("note.bassattack", "block.note.bass", SoundCategory.RECORD),
/* 234 */   GUI_BUTTON_PRESS("gui.button.press", "block.wood_pressureplate.click_on", SoundCategory.MASTER),
/* 235 */   RANDOM_ORB("random.orb", "entity.experience_orb.pickup", SoundCategory.PLAYER),
/* 236 */   MOB_ZOMBIE_WOODBREAK("mob.zombie.woodbreak", "entity.zombie.break_door_wood", SoundCategory.HOSTILE),
/* 237 */   MOB_HORSE_ARMOR("mob.horse.armor", "entity.horse.armor", SoundCategory.NEUTRAL),
/* 238 */   TILE_PISTON_IN("tile.piston.in", "block.piston.contract", SoundCategory.BLOCK),
/* 239 */   MOB_CAT_MEOW("mob.cat.meow", "entity.cat.ambient", SoundCategory.NEUTRAL),
/* 240 */   MOB_PIG_STEP("mob.pig.step", "entity.pig.step", SoundCategory.NEUTRAL),
/* 241 */   STEP_WOOD("step.wood", "block.wood.step", SoundCategory.NEUTRAL),
/* 242 */   PORTAL_TRAVEL("portal.travel", "block.portal.travel", SoundCategory.PLAYER),
/* 243 */   MOB_GUARDIAN_DEATH("mob.guardian.death", "entity.guardian.death", SoundCategory.HOSTILE),
/* 244 */   MOB_SKELETON_DEATH("mob.skeleton.death", "entity.skeleton.death", SoundCategory.HOSTILE),
/* 245 */   MOB_HORSE_HIT("mob.horse.hit", "entity.horse.hurt", SoundCategory.NEUTRAL),
/* 246 */   MOB_VILLAGER_HIT("mob.villager.hit", "entity.villager.hurt", SoundCategory.NEUTRAL),
/* 247 */   MOB_HORSE_SKELETON_IDLE("mob.horse.skeleton.idle", "entity.skeleton_horse.ambient", SoundCategory.NEUTRAL),
/* 248 */   RECORDS_CHIRP("records.chirp", "record.chirp", SoundCategory.RECORD),
/* 249 */   MOB_RABBIT_HURT("mob.rabbit.hurt", "entity.rabbit.hurt", SoundCategory.NEUTRAL),
/* 250 */   RECORDS_STAL("records.stal", "record.stal", SoundCategory.RECORD),
/* 251 */   MUSIC_GAME_NETHER("music.game.nether", "music.nether", SoundCategory.MUSIC),
/* 252 */   MUSIC_MENU("music.menu", "music.menu", SoundCategory.MUSIC),
/* 253 */   RECORDS_MELLOHI("records.mellohi", "record.mellohi", SoundCategory.RECORD),
/* 254 */   RECORDS_CAT("records.cat", "record.cat", SoundCategory.RECORD),
/* 255 */   RECORDS_FAR("records.far", "record.far", SoundCategory.RECORD),
/* 256 */   MUSIC_GAME_END_DRAGON("music.game.end.dragon", "music.dragon", SoundCategory.MUSIC),
/* 257 */   MOB_RABBIT_DEATH("mob.rabbit.death", "entity.rabbit.death", SoundCategory.NEUTRAL),
/* 258 */   MOB_RABBIT_IDLE("mob.rabbit.idle", "entity.rabbit.ambient", SoundCategory.NEUTRAL),
/* 259 */   MUSIC_GAME_END("music.game.end", "music.end", SoundCategory.MUSIC),
/* 260 */   MUSIC_GAME("music.game", "music.game", SoundCategory.MUSIC),
/* 261 */   MOB_GUARDIAN_IDLE("mob.guardian.idle", "entity.elder_guardian.ambient", SoundCategory.HOSTILE),
/* 262 */   RECORDS_WARD("records.ward", "record.ward", SoundCategory.RECORD),
/* 263 */   RECORDS_13("records.13", "record.13", SoundCategory.RECORD),
/* 264 */   MOB_RABBIT_HOP("mob.rabbit.hop", "entity.rabbit.jump", SoundCategory.NEUTRAL),
/* 265 */   RECORDS_STRAD("records.strad", "record.strad", SoundCategory.RECORD),
/* 266 */   RECORDS_11("records.11", "record.11", SoundCategory.RECORD),
/* 267 */   RECORDS_MALL("records.mall", "record.mall", SoundCategory.RECORD),
/* 268 */   RECORDS_BLOCKS("records.blocks", "record.blocks", SoundCategory.RECORD),
/* 269 */   RECORDS_WAIT("records.wait", "record.wait", SoundCategory.RECORD),
/* 270 */   MUSIC_GAME_END_CREDITS("music.game.end.credits", "music.credits", SoundCategory.MUSIC),
/* 271 */   MUSIC_GAME_CREATIVE("music.game.creative", "music.creative", SoundCategory.MUSIC);
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final String newName;
/*     */   private final SoundCategory category;
/*     */   private final boolean breaksound;
/*     */   private static final Map<String, SoundEffect> effects;
/*     */   
/*     */   static {
/* 281 */     effects = new HashMap<>();
/* 282 */     for (SoundEffect e : values()) {
/* 283 */       effects.put(e.getName(), e);
/*     */     }
/*     */   }
/*     */   
/*     */   SoundEffect(String name, String newname, SoundCategory cat) {
/* 288 */     this.category = cat;
/* 289 */     this.newName = newname;
/* 290 */     this.name = name;
/* 291 */     this.breaksound = name.startsWith("dig.");
/*     */   }
/*     */   
/*     */   SoundEffect(String name, String newname, SoundCategory cat, boolean shouldIgnore) {
/* 295 */     this.category = cat;
/* 296 */     this.newName = newname;
/* 297 */     this.name = name;
/* 298 */     this.breaksound = (name.startsWith("dig.") || shouldIgnore);
/*     */   }
/*     */   
/*     */   public static SoundEffect getByName(String name) {
/* 302 */     name = name.toLowerCase(Locale.ROOT);
/* 303 */     return effects.get(name);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 307 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getNewName() {
/* 311 */     return this.newName;
/*     */   }
/*     */   
/*     */   public SoundCategory getCategory() {
/* 315 */     return this.category;
/*     */   }
/*     */   
/*     */   public boolean isBreaksound() {
/* 319 */     return this.breaksound;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\sounds\SoundEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */