/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ public class StatisticMappings
/*     */ {
/*  25 */   public static final Map<String, Integer> CUSTOM_STATS = new HashMap<>();
/*     */   
/*     */   static {
/*  28 */     CUSTOM_STATS.put("stat.leaveGame", Integer.valueOf(0));
/*  29 */     CUSTOM_STATS.put("stat.playOneMinute", Integer.valueOf(1));
/*  30 */     CUSTOM_STATS.put("stat.timeSinceDeath", Integer.valueOf(2));
/*  31 */     CUSTOM_STATS.put("stat.sneakTime", Integer.valueOf(4));
/*  32 */     CUSTOM_STATS.put("stat.walkOneCm", Integer.valueOf(5));
/*  33 */     CUSTOM_STATS.put("stat.crouchOneCm", Integer.valueOf(6));
/*  34 */     CUSTOM_STATS.put("stat.sprintOneCm", Integer.valueOf(7));
/*  35 */     CUSTOM_STATS.put("stat.swimOneCm", Integer.valueOf(18));
/*  36 */     CUSTOM_STATS.put("stat.fallOneCm", Integer.valueOf(9));
/*  37 */     CUSTOM_STATS.put("stat.climbOneCm", Integer.valueOf(10));
/*  38 */     CUSTOM_STATS.put("stat.flyOneCm", Integer.valueOf(11));
/*  39 */     CUSTOM_STATS.put("stat.diveOneCm", Integer.valueOf(12));
/*  40 */     CUSTOM_STATS.put("stat.minecartOneCm", Integer.valueOf(13));
/*  41 */     CUSTOM_STATS.put("stat.boatOneCm", Integer.valueOf(14));
/*  42 */     CUSTOM_STATS.put("stat.pigOneCm", Integer.valueOf(15));
/*  43 */     CUSTOM_STATS.put("stat.horseOneCm", Integer.valueOf(16));
/*  44 */     CUSTOM_STATS.put("stat.aviateOneCm", Integer.valueOf(17));
/*  45 */     CUSTOM_STATS.put("stat.jump", Integer.valueOf(19));
/*  46 */     CUSTOM_STATS.put("stat.drop", Integer.valueOf(20));
/*  47 */     CUSTOM_STATS.put("stat.damageDealt", Integer.valueOf(21));
/*  48 */     CUSTOM_STATS.put("stat.damageTaken", Integer.valueOf(22));
/*  49 */     CUSTOM_STATS.put("stat.deaths", Integer.valueOf(23));
/*  50 */     CUSTOM_STATS.put("stat.mobKills", Integer.valueOf(24));
/*  51 */     CUSTOM_STATS.put("stat.animalsBred", Integer.valueOf(25));
/*  52 */     CUSTOM_STATS.put("stat.playerKills", Integer.valueOf(26));
/*  53 */     CUSTOM_STATS.put("stat.fishCaught", Integer.valueOf(27));
/*  54 */     CUSTOM_STATS.put("stat.talkedToVillager", Integer.valueOf(28));
/*  55 */     CUSTOM_STATS.put("stat.tradedWithVillager", Integer.valueOf(29));
/*  56 */     CUSTOM_STATS.put("stat.cakeSlicesEaten", Integer.valueOf(30));
/*  57 */     CUSTOM_STATS.put("stat.cauldronFilled", Integer.valueOf(31));
/*  58 */     CUSTOM_STATS.put("stat.cauldronUsed", Integer.valueOf(32));
/*  59 */     CUSTOM_STATS.put("stat.armorCleaned", Integer.valueOf(33));
/*  60 */     CUSTOM_STATS.put("stat.bannerCleaned", Integer.valueOf(34));
/*  61 */     CUSTOM_STATS.put("stat.brewingstandInter", Integer.valueOf(35));
/*  62 */     CUSTOM_STATS.put("stat.beaconInteraction", Integer.valueOf(36));
/*  63 */     CUSTOM_STATS.put("stat.dropperInspected", Integer.valueOf(37));
/*  64 */     CUSTOM_STATS.put("stat.hopperInspected", Integer.valueOf(38));
/*  65 */     CUSTOM_STATS.put("stat.dispenserInspecte", Integer.valueOf(39));
/*  66 */     CUSTOM_STATS.put("stat.noteblockPlayed", Integer.valueOf(40));
/*  67 */     CUSTOM_STATS.put("stat.noteblockTuned", Integer.valueOf(41));
/*  68 */     CUSTOM_STATS.put("stat.flowerPotted", Integer.valueOf(42));
/*  69 */     CUSTOM_STATS.put("stat.trappedChestTriggered", Integer.valueOf(43));
/*  70 */     CUSTOM_STATS.put("stat.enderchestOpened", Integer.valueOf(44));
/*  71 */     CUSTOM_STATS.put("stat.itemEnchanted", Integer.valueOf(45));
/*  72 */     CUSTOM_STATS.put("stat.recordPlayed", Integer.valueOf(46));
/*  73 */     CUSTOM_STATS.put("stat.furnaceInteraction", Integer.valueOf(47));
/*  74 */     CUSTOM_STATS.put("stat.craftingTableInteraction", Integer.valueOf(48));
/*  75 */     CUSTOM_STATS.put("stat.chestOpened", Integer.valueOf(49));
/*  76 */     CUSTOM_STATS.put("stat.sleepInBed", Integer.valueOf(50));
/*  77 */     CUSTOM_STATS.put("stat.shulkerBoxOpened", Integer.valueOf(51));
/*  78 */     CUSTOM_STATS.put("achievement.openInventory", Integer.valueOf(-1));
/*  79 */     CUSTOM_STATS.put("achievement.mineWood", Integer.valueOf(-1));
/*  80 */     CUSTOM_STATS.put("achievement.buildWorkBench", Integer.valueOf(-1));
/*  81 */     CUSTOM_STATS.put("achievement.buildPickaxe", Integer.valueOf(-1));
/*  82 */     CUSTOM_STATS.put("achievement.buildFurnace", Integer.valueOf(-1));
/*  83 */     CUSTOM_STATS.put("achievement.acquireIron", Integer.valueOf(-1));
/*  84 */     CUSTOM_STATS.put("achievement.buildHoe", Integer.valueOf(-1));
/*  85 */     CUSTOM_STATS.put("achievement.makeBread", Integer.valueOf(-1));
/*  86 */     CUSTOM_STATS.put("achievement.bakeCake", Integer.valueOf(-1));
/*  87 */     CUSTOM_STATS.put("achievement.buildBetterPickaxe", Integer.valueOf(-1));
/*  88 */     CUSTOM_STATS.put("achievement.cookFish", Integer.valueOf(-1));
/*  89 */     CUSTOM_STATS.put("achievement.onARail", Integer.valueOf(-1));
/*  90 */     CUSTOM_STATS.put("achievement.buildSword", Integer.valueOf(-1));
/*  91 */     CUSTOM_STATS.put("achievement.killEnemy", Integer.valueOf(-1));
/*  92 */     CUSTOM_STATS.put("achievement.killCow", Integer.valueOf(-1));
/*  93 */     CUSTOM_STATS.put("achievement.flyPig", Integer.valueOf(-1));
/*  94 */     CUSTOM_STATS.put("achievement.snipeSkeleton", Integer.valueOf(-1));
/*  95 */     CUSTOM_STATS.put("achievement.diamonds", Integer.valueOf(-1));
/*  96 */     CUSTOM_STATS.put("achievement.diamondsToYou", Integer.valueOf(-1));
/*  97 */     CUSTOM_STATS.put("achievement.portal", Integer.valueOf(-1));
/*  98 */     CUSTOM_STATS.put("achievement.ghast", Integer.valueOf(-1));
/*  99 */     CUSTOM_STATS.put("achievement.blazeRod", Integer.valueOf(-1));
/* 100 */     CUSTOM_STATS.put("achievement.potion", Integer.valueOf(-1));
/* 101 */     CUSTOM_STATS.put("achievement.theEnd", Integer.valueOf(-1));
/* 102 */     CUSTOM_STATS.put("achievement.theEnd2", Integer.valueOf(-1));
/* 103 */     CUSTOM_STATS.put("achievement.enchantments", Integer.valueOf(-1));
/* 104 */     CUSTOM_STATS.put("achievement.overkill", Integer.valueOf(-1));
/* 105 */     CUSTOM_STATS.put("achievement.bookcase", Integer.valueOf(-1));
/* 106 */     CUSTOM_STATS.put("achievement.breedCow", Integer.valueOf(-1));
/* 107 */     CUSTOM_STATS.put("achievement.spawnWither", Integer.valueOf(-1));
/* 108 */     CUSTOM_STATS.put("achievement.killWither", Integer.valueOf(-1));
/* 109 */     CUSTOM_STATS.put("achievement.fullBeacon", Integer.valueOf(-1));
/* 110 */     CUSTOM_STATS.put("achievement.exploreAllBiomes", Integer.valueOf(-1));
/* 111 */     CUSTOM_STATS.put("achievement.overpowered", Integer.valueOf(-1));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\StatisticMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */