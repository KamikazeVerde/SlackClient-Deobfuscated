/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import de.gerrygames.viarewind.replacement.Replacement;
/*     */ import de.gerrygames.viarewind.replacement.ReplacementRegistry;
/*     */ import de.gerrygames.viarewind.storage.BlockState;
/*     */ 
/*     */ public class ReplacementRegistry1_7_6_10to1_8 {
/*   9 */   private static final ReplacementRegistry registry = new ReplacementRegistry();
/*     */   
/*     */   static {
/*  12 */     registry.registerBlock(176, new Replacement(63));
/*  13 */     registry.registerBlock(177, new Replacement(68));
/*  14 */     registry.registerBlock(193, new Replacement(64));
/*  15 */     registry.registerBlock(194, new Replacement(64));
/*  16 */     registry.registerBlock(195, new Replacement(64));
/*  17 */     registry.registerBlock(196, new Replacement(64));
/*  18 */     registry.registerBlock(197, new Replacement(64));
/*  19 */     registry.registerBlock(77, 5, new Replacement(69, 6));
/*  20 */     registry.registerBlock(77, 13, new Replacement(69, 14));
/*  21 */     registry.registerBlock(77, 0, new Replacement(69, 0));
/*  22 */     registry.registerBlock(77, 8, new Replacement(69, 8));
/*  23 */     registry.registerBlock(143, 5, new Replacement(69, 6));
/*  24 */     registry.registerBlock(143, 13, new Replacement(69, 14));
/*  25 */     registry.registerBlock(143, 0, new Replacement(69, 0));
/*  26 */     registry.registerBlock(143, 8, new Replacement(69, 8));
/*  27 */     registry.registerBlock(178, new Replacement(151));
/*  28 */     registry.registerBlock(182, 0, new Replacement(44, 1));
/*  29 */     registry.registerBlock(182, 8, new Replacement(44, 9));
/*     */     
/*  31 */     registry.registerItem(425, new Replacement(323, "Banner"));
/*  32 */     registry.registerItem(409, new Replacement(406, "Prismarine Shard"));
/*  33 */     registry.registerItem(410, new Replacement(406, "Prismarine Crystal"));
/*  34 */     registry.registerItem(416, new Replacement(280, "Armor Stand"));
/*  35 */     registry.registerItem(423, new Replacement(363, "Raw Mutton"));
/*  36 */     registry.registerItem(424, new Replacement(364, "Cooked Mutton"));
/*  37 */     registry.registerItem(411, new Replacement(365, "Raw Rabbit"));
/*  38 */     registry.registerItem(412, new Replacement(366, "Cooked Rabbit"));
/*  39 */     registry.registerItem(413, new Replacement(282, "Rabbit Stew"));
/*  40 */     registry.registerItem(414, new Replacement(375, "Rabbit's Foot"));
/*  41 */     registry.registerItem(415, new Replacement(334, "Rabbit Hide"));
/*  42 */     registry.registerItem(373, 8203, new Replacement(373, 0, "Potion of Leaping"));
/*  43 */     registry.registerItem(373, 8235, new Replacement(373, 0, "Potion of Leaping"));
/*  44 */     registry.registerItem(373, 8267, new Replacement(373, 0, "Potion of Leaping"));
/*  45 */     registry.registerItem(373, 16395, new Replacement(373, 0, "Splash Potion of Leaping"));
/*  46 */     registry.registerItem(373, 16427, new Replacement(373, 0, "Splash Potion of Leaping"));
/*  47 */     registry.registerItem(373, 16459, new Replacement(373, 0, "Splash Potion of Leaping"));
/*  48 */     registry.registerItem(383, 30, new Replacement(383, "Spawn ArmorStand"));
/*  49 */     registry.registerItem(383, 67, new Replacement(383, "Spawn Endermite"));
/*  50 */     registry.registerItem(383, 68, new Replacement(383, "Spawn Guardian"));
/*  51 */     registry.registerItem(383, 101, new Replacement(383, "Spawn Rabbit"));
/*  52 */     registry.registerItem(19, 1, new Replacement(19, 0, "Wet Sponge"));
/*  53 */     registry.registerItem(182, new Replacement(44, 1, "Red Sandstone Slab"));
/*     */     
/*  55 */     registry.registerItemBlock(166, new Replacement(20, "Barrier"));
/*  56 */     registry.registerItemBlock(167, new Replacement(96, "Iron Trapdoor"));
/*  57 */     registry.registerItemBlock(1, 1, new Replacement(1, 0, "Granite"));
/*  58 */     registry.registerItemBlock(1, 2, new Replacement(1, 0, "Polished Granite"));
/*  59 */     registry.registerItemBlock(1, 3, new Replacement(1, 0, "Diorite"));
/*  60 */     registry.registerItemBlock(1, 4, new Replacement(1, 0, "Polished Diorite"));
/*  61 */     registry.registerItemBlock(1, 5, new Replacement(1, 0, "Andesite"));
/*  62 */     registry.registerItemBlock(1, 6, new Replacement(1, 0, "Polished Andesite"));
/*  63 */     registry.registerItemBlock(168, 0, new Replacement(1, 0, "Prismarine"));
/*  64 */     registry.registerItemBlock(168, 1, new Replacement(98, 0, "Prismarine Bricks"));
/*  65 */     registry.registerItemBlock(168, 2, new Replacement(98, 1, "Dark Prismarine"));
/*  66 */     registry.registerItemBlock(169, new Replacement(89, "Sea Lantern"));
/*  67 */     registry.registerItemBlock(165, new Replacement(95, 5, "Slime Block"));
/*  68 */     registry.registerItemBlock(179, 0, new Replacement(24, "Red Sandstone"));
/*  69 */     registry.registerItemBlock(179, 1, new Replacement(24, "Chiseled Red Sandstone"));
/*  70 */     registry.registerItemBlock(179, 2, new Replacement(24, "Smooth Sandstone"));
/*  71 */     registry.registerItemBlock(181, new Replacement(43, 1, "Double Red Sandstone Slab"));
/*  72 */     registry.registerItemBlock(180, new Replacement(128, "Red Sandstone Stairs"));
/*  73 */     registry.registerItemBlock(188, new Replacement(85, "Spruce Fence"));
/*  74 */     registry.registerItemBlock(189, new Replacement(85, "Birch Fence"));
/*  75 */     registry.registerItemBlock(190, new Replacement(85, "Jungle Fence"));
/*  76 */     registry.registerItemBlock(191, new Replacement(85, "Dark Oak Fence"));
/*  77 */     registry.registerItemBlock(192, new Replacement(85, "Acacia Fence"));
/*  78 */     registry.registerItemBlock(183, new Replacement(107, "Spruce Fence Gate"));
/*  79 */     registry.registerItemBlock(184, new Replacement(107, "Birch Fence Gate"));
/*  80 */     registry.registerItemBlock(185, new Replacement(107, "Jungle Fence Gate"));
/*  81 */     registry.registerItemBlock(186, new Replacement(107, "Dark Oak Fence Gate"));
/*  82 */     registry.registerItemBlock(187, new Replacement(107, "Acacia Fence Gate"));
/*  83 */     registry.registerItemBlock(427, new Replacement(324, "Spruce Door"));
/*  84 */     registry.registerItemBlock(428, new Replacement(324, "Birch Door"));
/*  85 */     registry.registerItemBlock(429, new Replacement(324, "Jungle Door"));
/*  86 */     registry.registerItemBlock(430, new Replacement(324, "Dark Oak Door"));
/*  87 */     registry.registerItemBlock(431, new Replacement(324, "Acacia Door"));
/*  88 */     registry.registerItemBlock(157, new Replacement(28, "Activator Rail"));
/*     */   }
/*     */   
/*     */   public static Item replace(Item item) {
/*  92 */     return registry.replace(item);
/*     */   }
/*     */   
/*     */   public static int replace(int raw) {
/*  96 */     int data = BlockState.extractData(raw);
/*  97 */     Replacement replace = registry.replace(BlockState.extractId(raw), data);
/*  98 */     return (replace != null) ? BlockState.stateToRaw(replace.getId(), replace.replaceData(data)) : raw;
/*     */   }
/*     */   
/*     */   public static Replacement getReplacement(int id, int data) {
/* 102 */     return registry.replace(id, data);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\items\ReplacementRegistry1_7_6_10to1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */