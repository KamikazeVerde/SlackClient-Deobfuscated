/*     */ package com.viaversion.viaversion.protocols.protocol1_11to1_10;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
/*     */ public class EntityIdRewriter
/*     */ {
/*  28 */   private static final BiMap<String, String> oldToNewNames = (BiMap<String, String>)HashBiMap.create();
/*     */   
/*     */   static {
/*  31 */     oldToNewNames.put("AreaEffectCloud", "minecraft:area_effect_cloud");
/*  32 */     oldToNewNames.put("ArmorStand", "minecraft:armor_stand");
/*  33 */     oldToNewNames.put("Arrow", "minecraft:arrow");
/*  34 */     oldToNewNames.put("Bat", "minecraft:bat");
/*  35 */     oldToNewNames.put("Blaze", "minecraft:blaze");
/*  36 */     oldToNewNames.put("Boat", "minecraft:boat");
/*  37 */     oldToNewNames.put("CaveSpider", "minecraft:cave_spider");
/*  38 */     oldToNewNames.put("Chicken", "minecraft:chicken");
/*  39 */     oldToNewNames.put("Cow", "minecraft:cow");
/*  40 */     oldToNewNames.put("Creeper", "minecraft:creeper");
/*  41 */     oldToNewNames.put("Donkey", "minecraft:donkey");
/*  42 */     oldToNewNames.put("DragonFireball", "minecraft:dragon_fireball");
/*  43 */     oldToNewNames.put("ElderGuardian", "minecraft:elder_guardian");
/*  44 */     oldToNewNames.put("EnderCrystal", "minecraft:ender_crystal");
/*  45 */     oldToNewNames.put("EnderDragon", "minecraft:ender_dragon");
/*  46 */     oldToNewNames.put("Enderman", "minecraft:enderman");
/*  47 */     oldToNewNames.put("Endermite", "minecraft:endermite");
/*  48 */     oldToNewNames.put("EntityHorse", "minecraft:horse");
/*  49 */     oldToNewNames.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
/*  50 */     oldToNewNames.put("FallingSand", "minecraft:falling_block");
/*  51 */     oldToNewNames.put("Fireball", "minecraft:fireball");
/*  52 */     oldToNewNames.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
/*  53 */     oldToNewNames.put("Ghast", "minecraft:ghast");
/*  54 */     oldToNewNames.put("Giant", "minecraft:giant");
/*  55 */     oldToNewNames.put("Guardian", "minecraft:guardian");
/*  56 */     oldToNewNames.put("Husk", "minecraft:husk");
/*  57 */     oldToNewNames.put("Item", "minecraft:item");
/*  58 */     oldToNewNames.put("ItemFrame", "minecraft:item_frame");
/*  59 */     oldToNewNames.put("LavaSlime", "minecraft:magma_cube");
/*  60 */     oldToNewNames.put("LeashKnot", "minecraft:leash_knot");
/*  61 */     oldToNewNames.put("MinecartChest", "minecraft:chest_minecart");
/*  62 */     oldToNewNames.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
/*  63 */     oldToNewNames.put("MinecartFurnace", "minecraft:furnace_minecart");
/*  64 */     oldToNewNames.put("MinecartHopper", "minecraft:hopper_minecart");
/*  65 */     oldToNewNames.put("MinecartRideable", "minecraft:minecart");
/*  66 */     oldToNewNames.put("MinecartSpawner", "minecraft:spawner_minecart");
/*  67 */     oldToNewNames.put("MinecartTNT", "minecraft:tnt_minecart");
/*  68 */     oldToNewNames.put("Mule", "minecraft:mule");
/*  69 */     oldToNewNames.put("MushroomCow", "minecraft:mooshroom");
/*  70 */     oldToNewNames.put("Ozelot", "minecraft:ocelot");
/*  71 */     oldToNewNames.put("Painting", "minecraft:painting");
/*  72 */     oldToNewNames.put("Pig", "minecraft:pig");
/*  73 */     oldToNewNames.put("PigZombie", "minecraft:zombie_pigman");
/*  74 */     oldToNewNames.put("PolarBear", "minecraft:polar_bear");
/*  75 */     oldToNewNames.put("PrimedTnt", "minecraft:tnt");
/*  76 */     oldToNewNames.put("Rabbit", "minecraft:rabbit");
/*  77 */     oldToNewNames.put("Sheep", "minecraft:sheep");
/*  78 */     oldToNewNames.put("Shulker", "minecraft:shulker");
/*  79 */     oldToNewNames.put("ShulkerBullet", "minecraft:shulker_bullet");
/*  80 */     oldToNewNames.put("Silverfish", "minecraft:silverfish");
/*  81 */     oldToNewNames.put("Skeleton", "minecraft:skeleton");
/*  82 */     oldToNewNames.put("SkeletonHorse", "minecraft:skeleton_horse");
/*  83 */     oldToNewNames.put("Slime", "minecraft:slime");
/*  84 */     oldToNewNames.put("SmallFireball", "minecraft:small_fireball");
/*  85 */     oldToNewNames.put("Snowball", "minecraft:snowball");
/*  86 */     oldToNewNames.put("SnowMan", "minecraft:snowman");
/*  87 */     oldToNewNames.put("SpectralArrow", "minecraft:spectral_arrow");
/*  88 */     oldToNewNames.put("Spider", "minecraft:spider");
/*  89 */     oldToNewNames.put("Squid", "minecraft:squid");
/*  90 */     oldToNewNames.put("Stray", "minecraft:stray");
/*  91 */     oldToNewNames.put("ThrownEgg", "minecraft:egg");
/*  92 */     oldToNewNames.put("ThrownEnderpearl", "minecraft:ender_pearl");
/*  93 */     oldToNewNames.put("ThrownExpBottle", "minecraft:xp_bottle");
/*  94 */     oldToNewNames.put("ThrownPotion", "minecraft:potion");
/*  95 */     oldToNewNames.put("Villager", "minecraft:villager");
/*  96 */     oldToNewNames.put("VillagerGolem", "minecraft:villager_golem");
/*  97 */     oldToNewNames.put("Witch", "minecraft:witch");
/*  98 */     oldToNewNames.put("WitherBoss", "minecraft:wither");
/*  99 */     oldToNewNames.put("WitherSkeleton", "minecraft:wither_skeleton");
/* 100 */     oldToNewNames.put("WitherSkull", "minecraft:wither_skull");
/* 101 */     oldToNewNames.put("Wolf", "minecraft:wolf");
/* 102 */     oldToNewNames.put("XPOrb", "minecraft:xp_orb");
/* 103 */     oldToNewNames.put("Zombie", "minecraft:zombie");
/* 104 */     oldToNewNames.put("ZombieHorse", "minecraft:zombie_horse");
/* 105 */     oldToNewNames.put("ZombieVillager", "minecraft:zombie_villager");
/*     */   }
/*     */   
/*     */   public static void toClient(CompoundTag tag) {
/* 109 */     toClient(tag, false);
/*     */   }
/*     */   
/*     */   public static void toClient(CompoundTag tag, boolean backwards) {
/* 113 */     Tag idTag = tag.get("id");
/* 114 */     if (idTag instanceof StringTag) {
/* 115 */       StringTag id = (StringTag)idTag;
/* 116 */       String newName = backwards ? (String)oldToNewNames.inverse().get(id.getValue()) : (String)oldToNewNames.get(id.getValue());
/* 117 */       if (newName != null) {
/* 118 */         id.setValue(newName);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void toClientSpawner(CompoundTag tag) {
/* 124 */     toClientSpawner(tag, false);
/*     */   }
/*     */   
/*     */   public static void toClientSpawner(CompoundTag tag, boolean backwards) {
/* 128 */     if (tag == null)
/*     */       return; 
/* 130 */     Tag spawnDataTag = tag.get("SpawnData");
/* 131 */     if (spawnDataTag != null) {
/* 132 */       toClient((CompoundTag)spawnDataTag, backwards);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void toClientItem(Item item) {
/* 137 */     toClientItem(item, false);
/*     */   }
/*     */   
/*     */   public static void toClientItem(Item item, boolean backwards) {
/* 141 */     if (hasEntityTag(item)) {
/* 142 */       toClient((CompoundTag)item.tag().get("EntityTag"), backwards);
/*     */     }
/* 144 */     if (item != null && item.amount() <= 0) item.setAmount(1); 
/*     */   }
/*     */   
/*     */   public static void toServerItem(Item item) {
/* 148 */     toServerItem(item, false);
/*     */   }
/*     */   
/*     */   public static void toServerItem(Item item, boolean backwards) {
/* 152 */     if (!hasEntityTag(item))
/*     */       return; 
/* 154 */     CompoundTag entityTag = (CompoundTag)item.tag().get("EntityTag");
/* 155 */     Tag idTag = entityTag.get("id");
/* 156 */     if (idTag instanceof StringTag) {
/* 157 */       StringTag id = (StringTag)idTag;
/* 158 */       String newName = backwards ? (String)oldToNewNames.get(id.getValue()) : (String)oldToNewNames.inverse().get(id.getValue());
/* 159 */       if (newName != null) {
/* 160 */         id.setValue(newName);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean hasEntityTag(Item item) {
/* 166 */     if (item == null || item.identifier() != 383) return false;
/*     */     
/* 168 */     CompoundTag tag = item.tag();
/* 169 */     if (tag == null) return false;
/*     */     
/* 171 */     Tag entityTag = tag.get("EntityTag");
/* 172 */     return (entityTag instanceof CompoundTag && ((CompoundTag)entityTag).get("id") instanceof StringTag);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\EntityIdRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */