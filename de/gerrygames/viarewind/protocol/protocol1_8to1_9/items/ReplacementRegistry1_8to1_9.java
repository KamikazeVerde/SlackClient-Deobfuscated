/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.items;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import de.gerrygames.viarewind.replacement.Replacement;
/*    */ import de.gerrygames.viarewind.replacement.ReplacementRegistry;
/*    */ import de.gerrygames.viarewind.storage.BlockState;
/*    */ 
/*    */ public class ReplacementRegistry1_8to1_9 {
/*  9 */   private static final ReplacementRegistry registry = new ReplacementRegistry();
/*    */   
/*    */   static {
/* 12 */     registry.registerItem(198, new Replacement(50, 0, "End Rod"));
/* 13 */     registry.registerItem(434, new Replacement(391, "Beetroot"));
/* 14 */     registry.registerItem(435, new Replacement(361, "Beetroot Seeds"));
/* 15 */     registry.registerItem(436, new Replacement(282, "Beetroot Soup"));
/* 16 */     registry.registerItem(432, new Replacement(322, "Chorus Fruit"));
/* 17 */     registry.registerItem(433, new Replacement(393, "Popped Chorus Fruit"));
/* 18 */     registry.registerItem(437, new Replacement(373, "Dragons Breath"));
/* 19 */     registry.registerItem(443, new Replacement(299, "Elytra"));
/* 20 */     registry.registerItem(426, new Replacement(410, "End Crystal"));
/* 21 */     registry.registerItem(442, new Replacement(425, "Shield"));
/* 22 */     registry.registerItem(439, new Replacement(262, "Spectral Arrow"));
/* 23 */     registry.registerItem(440, new Replacement(262, "Tipped Arrow"));
/* 24 */     registry.registerItem(444, new Replacement(333, "Spruce Boat"));
/* 25 */     registry.registerItem(445, new Replacement(333, "Birch Boat"));
/* 26 */     registry.registerItem(446, new Replacement(333, "Jungle Boat"));
/* 27 */     registry.registerItem(447, new Replacement(333, "Acacia Boat"));
/* 28 */     registry.registerItem(448, new Replacement(333, "Dark Oak Boat"));
/* 29 */     registry.registerItem(204, new Replacement(43, 7, "Purpur Double Slab"));
/* 30 */     registry.registerItem(205, new Replacement(44, 7, "Purpur Slab"));
/*    */     
/* 32 */     registry.registerBlock(209, new Replacement(119));
/* 33 */     registry.registerBlock(198, 0, new Replacement(50, 5));
/* 34 */     registry.registerBlock(198, 1, new Replacement(50, 5));
/* 35 */     registry.registerBlock(198, 2, new Replacement(50, 4));
/* 36 */     registry.registerBlock(198, 3, new Replacement(50, 3));
/* 37 */     registry.registerBlock(198, 4, new Replacement(50, 2));
/* 38 */     registry.registerBlock(198, 5, new Replacement(50, 1));
/* 39 */     registry.registerBlock(204, new Replacement(43, 7));
/* 40 */     registry.registerBlock(205, 0, new Replacement(44, 7));
/* 41 */     registry.registerBlock(205, 8, new Replacement(44, 15));
/* 42 */     registry.registerBlock(207, new Replacement(141));
/* 43 */     registry.registerBlock(137, new Replacement(137, 0));
/*    */     
/* 45 */     registry.registerItemBlock(199, new Replacement(35, 10, "Chorus Plant"));
/* 46 */     registry.registerItemBlock(200, new Replacement(35, 2, "Chorus Flower"));
/* 47 */     registry.registerItemBlock(201, new Replacement(155, 0, "Purpur Block"));
/* 48 */     registry.registerItemBlock(202, new Replacement(155, 2, "Purpur Pillar"));
/* 49 */     registry.registerItemBlock(203, 0, new Replacement(156, 0, "Purpur Stairs"));
/* 50 */     registry.registerItemBlock(203, 1, new Replacement(156, 1, "Purpur Stairs"));
/* 51 */     registry.registerItemBlock(203, 2, new Replacement(156, 2, "Purpur Stairs"));
/* 52 */     registry.registerItemBlock(203, 3, new Replacement(156, 3, "Purpur Stairs"));
/* 53 */     registry.registerItemBlock(203, 4, new Replacement(156, 4, "Purpur Stairs"));
/* 54 */     registry.registerItemBlock(203, 5, new Replacement(156, 5, "Purpur Stairs"));
/* 55 */     registry.registerItemBlock(203, 6, new Replacement(156, 6, "Purpur Stairs"));
/* 56 */     registry.registerItemBlock(203, 7, new Replacement(156, 7, "Purpur Stairs"));
/* 57 */     registry.registerItemBlock(203, 8, new Replacement(156, 8, "Purpur Stairs"));
/* 58 */     registry.registerItemBlock(206, new Replacement(121, 0, "Endstone Bricks"));
/* 59 */     registry.registerItemBlock(207, new Replacement(141, "Beetroot Block"));
/* 60 */     registry.registerItemBlock(208, new Replacement(2, 0, "Grass Path"));
/* 61 */     registry.registerItemBlock(209, new Replacement(90, "End Gateway"));
/* 62 */     registry.registerItemBlock(210, new Replacement(137, 0, "Repeating Command Block"));
/* 63 */     registry.registerItemBlock(211, new Replacement(137, 0, "Chain Command Block"));
/* 64 */     registry.registerItemBlock(212, new Replacement(79, 0, "Frosted Ice"));
/* 65 */     registry.registerItemBlock(214, new Replacement(87, 0, "Nether Wart Block"));
/* 66 */     registry.registerItemBlock(215, new Replacement(112, 0, "Red Nether Brick"));
/* 67 */     registry.registerItemBlock(217, new Replacement(166, 0, "Structure Void"));
/* 68 */     registry.registerItemBlock(255, new Replacement(137, 0, "Structure Block"));
/* 69 */     registry.registerItemBlock(397, 5, new Replacement(397, 0, "Dragon Head"));
/*    */   }
/*    */   
/*    */   public static Item replace(Item item) {
/* 73 */     return registry.replace(item);
/*    */   }
/*    */   
/*    */   public static int replace(int raw) {
/* 77 */     int data = BlockState.extractData(raw);
/* 78 */     Replacement replace = registry.replace(BlockState.extractId(raw), data);
/* 79 */     if (replace == null) return raw; 
/* 80 */     return BlockState.stateToRaw(replace.getId(), replace.replaceData(data));
/*    */   }
/*    */   
/*    */   public static Replacement getReplacement(int id, int data) {
/* 84 */     return registry.replace(id, data);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\items\ReplacementRegistry1_8to1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */