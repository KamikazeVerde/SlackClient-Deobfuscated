/*     */ package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class AchievementTranslationMapping
/*     */ {
/*  26 */   private static final Object2ObjectOpenHashMap<String, String> ACHIEVEMENTS = new Object2ObjectOpenHashMap(150, 0.99F);
/*  27 */   private static final Set<String> SPECIAL_ACHIEVEMENTS = new HashSet<>(10);
/*     */   
/*     */   static {
/*  30 */     add("chat.type.achievement", "%s has just earned the achievement %s");
/*  31 */     add("chat.type.achievement.taken", "%s has lost the achievement %s");
/*  32 */     add("stats.tooltip.type.achievement", "Achievement");
/*  33 */     add("stats.tooltip.type.statistic", "Statistic");
/*  34 */     add("stat.generalButton", "General");
/*  35 */     add("stat.blocksButton", "Blocks");
/*  36 */     add("stat.itemsButton", "Items");
/*  37 */     add("stat.mobsButton", "Mobs");
/*  38 */     add("stat.used", "Times Used");
/*  39 */     add("stat.mined", "Times Mined");
/*  40 */     add("stat.depleted", "Times Depleted");
/*  41 */     add("stat.crafted", "Times Crafted");
/*  42 */     add("stat.entityKills", "You killed %s %s");
/*  43 */     add("stat.entityKilledBy", "%s killed you %s time(s)");
/*  44 */     add("stat.entityKills.none", "You have never killed %s");
/*  45 */     add("stat.entityKilledBy.none", "You have never been killed by %s");
/*  46 */     add("stat.startGame", "Times Played");
/*  47 */     add("stat.createWorld", "Worlds Created");
/*  48 */     add("stat.loadWorld", "Saves Loaded");
/*  49 */     add("stat.joinMultiplayer", "Multiplayer Joins");
/*  50 */     add("stat.leaveGame", "Games Quit");
/*  51 */     add("stat.playOneMinute", "Minutes Played");
/*  52 */     add("stat.timeSinceDeath", "Since Last Death");
/*  53 */     add("stat.sneakTime", "Sneak Time");
/*  54 */     add("stat.walkOneCm", "Distance Walked");
/*  55 */     add("stat.crouchOneCm", "Distance Crouched");
/*  56 */     add("stat.sprintOneCm", "Distance Sprinted");
/*  57 */     add("stat.fallOneCm", "Distance Fallen");
/*  58 */     add("stat.swimOneCm", "Distance Swum");
/*  59 */     add("stat.flyOneCm", "Distance Flown");
/*  60 */     add("stat.climbOneCm", "Distance Climbed");
/*  61 */     add("stat.diveOneCm", "Distance Dove");
/*  62 */     add("stat.minecartOneCm", "Distance by Minecart");
/*  63 */     add("stat.boatOneCm", "Distance by Boat");
/*  64 */     add("stat.pigOneCm", "Distance by Pig");
/*  65 */     add("stat.horseOneCm", "Distance by Horse");
/*  66 */     add("stat.aviateOneCm", "Distance by Elytra");
/*  67 */     add("stat.jump", "Jumps");
/*  68 */     add("stat.drop", "Items Dropped");
/*  69 */     add("stat.dropped", "Dropped");
/*  70 */     add("stat.pickup", "Picked Up");
/*  71 */     add("stat.damageDealt", "Damage Dealt");
/*  72 */     add("stat.damageTaken", "Damage Taken");
/*  73 */     add("stat.deaths", "Number of Deaths");
/*  74 */     add("stat.mobKills", "Mob Kills");
/*  75 */     add("stat.animalsBred", "Animals Bred");
/*  76 */     add("stat.playerKills", "Player Kills");
/*  77 */     add("stat.fishCaught", "Fish Caught");
/*  78 */     add("stat.treasureFished", "Treasure Fished");
/*  79 */     add("stat.junkFished", "Junk Fished");
/*  80 */     add("stat.talkedToVillager", "Talked to Villagers");
/*  81 */     add("stat.tradedWithVillager", "Traded with Villagers");
/*  82 */     add("stat.cakeSlicesEaten", "Cake Slices Eaten");
/*  83 */     add("stat.cauldronFilled", "Cauldrons Filled");
/*  84 */     add("stat.cauldronUsed", "Water Taken from Cauldron");
/*  85 */     add("stat.armorCleaned", "Armor Pieces Cleaned");
/*  86 */     add("stat.bannerCleaned", "Banners Cleaned");
/*  87 */     add("stat.brewingstandInteraction", "Interactions with Brewing Stand");
/*  88 */     add("stat.beaconInteraction", "Interactions with Beacon");
/*  89 */     add("stat.dropperInspected", "Droppers Searched");
/*  90 */     add("stat.hopperInspected", "Hoppers Searched");
/*  91 */     add("stat.dispenserInspected", "Dispensers Searched");
/*  92 */     add("stat.noteblockPlayed", "Note Blocks Played");
/*  93 */     add("stat.noteblockTuned", "Note Blocks Tuned");
/*  94 */     add("stat.flowerPotted", "Plants Potted");
/*  95 */     add("stat.trappedChestTriggered", "Trapped Chests Triggered");
/*  96 */     add("stat.enderchestOpened", "Ender Chests Opened");
/*  97 */     add("stat.itemEnchanted", "Items Enchanted");
/*  98 */     add("stat.recordPlayed", "Records Played");
/*  99 */     add("stat.furnaceInteraction", "Interactions with Furnace");
/* 100 */     add("stat.workbenchInteraction", "Interactions with Crafting Table");
/* 101 */     add("stat.chestOpened", "Chests Opened");
/* 102 */     add("stat.shulkerBoxOpened", "Shulker Boxes Opened");
/* 103 */     add("stat.sleepInBed", "Times Slept in a Bed");
/* 104 */     add("stat.mineBlock", "%1$s Mined");
/* 105 */     add("stat.craftItem", "%1$s Crafted");
/* 106 */     add("stat.useItem", "%1$s Used");
/* 107 */     add("stat.breakItem", "%1$s Depleted");
/* 108 */     add("achievement.get", "Achievement get!");
/* 109 */     add("achievement.taken", "Taken!");
/* 110 */     add("achievement.unknown", "???");
/* 111 */     add("achievement.requires", "Requires '%1$s'");
/* 112 */     add("achievement.openInventory", "Taking Inventory");
/* 113 */     add("achievement.openInventory.desc", "Press 'E' to open your inventory");
/* 114 */     add("achievement.mineWood", "Getting Wood");
/* 115 */     add("achievement.mineWood.desc", "Attack a tree until a block of wood pops out");
/* 116 */     add("achievement.buildWorkBench", "Benchmarking");
/* 117 */     add("achievement.buildWorkBench.desc", "Craft a workbench with four blocks of planks");
/* 118 */     add("achievement.buildPickaxe", "Time to Mine!");
/* 119 */     add("achievement.buildPickaxe.desc", "Use planks and sticks to make a pickaxe");
/* 120 */     add("achievement.buildFurnace", "Hot Topic");
/* 121 */     add("achievement.buildFurnace.desc", "Construct a furnace out of eight cobblestone blocks");
/* 122 */     add("achievement.acquireIron", "Acquire Hardware");
/* 123 */     add("achievement.acquireIron.desc", "Smelt an iron ingot");
/* 124 */     add("achievement.buildHoe", "Time to Farm!");
/* 125 */     add("achievement.buildHoe.desc", "Use planks and sticks to make a hoe");
/* 126 */     add("achievement.makeBread", "Bake Bread");
/* 127 */     add("achievement.makeBread.desc", "Turn wheat into bread");
/* 128 */     add("achievement.bakeCake", "The Lie");
/* 129 */     add("achievement.bakeCake.desc", "Wheat, sugar, milk and eggs!");
/* 130 */     add("achievement.buildBetterPickaxe", "Getting an Upgrade");
/* 131 */     add("achievement.buildBetterPickaxe.desc", "Construct a better pickaxe");
/* 132 */     addSpecial("achievement.overpowered", "Overpowered");
/* 133 */     add("achievement.overpowered.desc", "Eat a Notch apple");
/* 134 */     add("achievement.cookFish", "Delicious Fish");
/* 135 */     add("achievement.cookFish.desc", "Catch and cook fish!");
/* 136 */     addSpecial("achievement.onARail", "On A Rail");
/* 137 */     add("achievement.onARail.desc", "Travel by minecart at least 1 km from where you started");
/* 138 */     add("achievement.buildSword", "Time to Strike!");
/* 139 */     add("achievement.buildSword.desc", "Use planks and sticks to make a sword");
/* 140 */     add("achievement.killEnemy", "Monster Hunter");
/* 141 */     add("achievement.killEnemy.desc", "Attack and destroy a monster");
/* 142 */     add("achievement.killCow", "Cow Tipper");
/* 143 */     add("achievement.killCow.desc", "Harvest some leather");
/* 144 */     add("achievement.breedCow", "Repopulation");
/* 145 */     add("achievement.breedCow.desc", "Breed two cows with wheat");
/* 146 */     addSpecial("achievement.flyPig", "When Pigs Fly");
/* 147 */     add("achievement.flyPig.desc", "Fly a pig off a cliff");
/* 148 */     addSpecial("achievement.snipeSkeleton", "Sniper Duel");
/* 149 */     add("achievement.snipeSkeleton.desc", "Kill a skeleton with an arrow from more than 50 meters");
/* 150 */     add("achievement.diamonds", "DIAMONDS!");
/* 151 */     add("achievement.diamonds.desc", "Acquire diamonds with your iron tools");
/* 152 */     add("achievement.diamondsToYou", "Diamonds to you!");
/* 153 */     add("achievement.diamondsToYou.desc", "Throw diamonds at another player");
/* 154 */     add("achievement.portal", "We Need to Go Deeper");
/* 155 */     add("achievement.portal.desc", "Build a portal to the Nether");
/* 156 */     addSpecial("achievement.ghast", "Return to Sender");
/* 157 */     add("achievement.ghast.desc", "Destroy a Ghast with a fireball");
/* 158 */     add("achievement.blazeRod", "Into Fire");
/* 159 */     add("achievement.blazeRod.desc", "Relieve a Blaze of its rod");
/* 160 */     add("achievement.potion", "Local Brewery");
/* 161 */     add("achievement.potion.desc", "Brew a potion");
/* 162 */     addSpecial("achievement.theEnd", "The End?");
/* 163 */     add("achievement.theEnd.desc", "Locate the End");
/* 164 */     addSpecial("achievement.theEnd2", "The End.");
/* 165 */     add("achievement.theEnd2.desc", "Defeat the Ender Dragon");
/* 166 */     add("achievement.spawnWither", "The Beginning?");
/* 167 */     add("achievement.spawnWither.desc", "Spawn the Wither");
/* 168 */     add("achievement.killWither", "The Beginning.");
/* 169 */     add("achievement.killWither.desc", "Kill the Wither");
/* 170 */     addSpecial("achievement.fullBeacon", "Beaconator");
/* 171 */     add("achievement.fullBeacon.desc", "Create a full beacon");
/* 172 */     addSpecial("achievement.exploreAllBiomes", "Adventuring Time");
/* 173 */     add("achievement.exploreAllBiomes.desc", "Discover all biomes");
/* 174 */     add("achievement.enchantments", "Enchanter");
/* 175 */     add("achievement.enchantments.desc", "Use a book, obsidian and diamonds to construct an enchantment table");
/* 176 */     addSpecial("achievement.overkill", "Overkill");
/* 177 */     add("achievement.overkill.desc", "Deal nine hearts of damage in a single hit");
/* 178 */     add("achievement.bookcase", "Librarian");
/* 179 */     add("achievement.bookcase.desc", "Build some bookshelves to improve your enchantment table");
/*     */   }
/*     */   
/*     */   private static void add(String key, String value) {
/* 183 */     ACHIEVEMENTS.put(key, value);
/*     */   }
/*     */   
/*     */   private static void addSpecial(String key, String value) {
/* 187 */     add(key, value);
/* 188 */     SPECIAL_ACHIEVEMENTS.add(key);
/*     */   }
/*     */   
/*     */   public static String get(String key) {
/* 192 */     return (String)ACHIEVEMENTS.get(key);
/*     */   }
/*     */   
/*     */   public static boolean isSpecial(String key) {
/* 196 */     return SPECIAL_ACHIEVEMENTS.contains(key);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12to1_11_1\data\AchievementTranslationMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */