/*     */ package cc.slack.utils.player;
/*     */ 
/*     */ import cc.slack.utils.client.mc;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public final class InventoryUtil
/*     */   extends mc
/*     */ {
/*  15 */   public static final List<Block> BLOCK_BLACKLIST = Arrays.asList(new Block[] { Blocks.enchanting_table, (Block)Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, (Block)Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch, Blocks.gravel, (Block)Blocks.cactus, Blocks.bed, Blocks.lever, Blocks.standing_sign, Blocks.wall_sign, Blocks.jukebox, Blocks.oak_fence, Blocks.spruce_fence, Blocks.birch_fence, Blocks.jungle_fence, Blocks.dark_oak_fence, Blocks.oak_fence_gate, Blocks.spruce_fence_gate, Blocks.birch_fence_gate, Blocks.jungle_fence_gate, Blocks.dark_oak_fence_gate, Blocks.nether_brick_fence, Blocks.trapdoor, Blocks.melon_block, Blocks.brewing_stand, (Block)Blocks.cauldron, (Block)Blocks.skull, (Block)Blocks.hopper, Blocks.carpet, (Block)Blocks.redstone_wire, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, (Block)Blocks.daylight_detector });
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
/*     */   public static int findItem(int startSlot, int endSlot, Item item) {
/*  69 */     for (int i = startSlot; i < endSlot; i++) {
/*  70 */       ItemStack stack = (mc.getPlayer()).inventoryContainer.getSlot(i).getStack();
/*     */       
/*  72 */       if (stack != null && stack.getItem() == item)
/*  73 */         return i; 
/*     */     } 
/*  75 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isHotbarFull() {
/*  79 */     for (int i = 36; i < 45; i++) {
/*  80 */       if ((mc.getPlayer()).inventoryContainer.getSlot(i).getStack() == null)
/*  81 */         return false; 
/*     */     } 
/*  83 */     return true;
/*     */   }
/*     */   
/*     */   public static int pickHotarBlock(boolean biggestStack) {
/*  87 */     if (biggestStack) {
/*  88 */       int currentStackSize = 0;
/*  89 */       int currentSlot = 36;
/*  90 */       for (int i = 36; i < 45; i++) {
/*  91 */         ItemStack itemStack = (mc.getPlayer()).inventoryContainer.getSlot(i).getStack();
/*     */         
/*  93 */         if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > currentStackSize) {
/*  94 */           Block block = ((ItemBlock)itemStack.getItem()).getBlock();
/*     */           
/*  96 */           if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block)) {
/*  97 */             currentStackSize = itemStack.stackSize;
/*  98 */             currentSlot = i;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       if (currentStackSize > 0) {
/* 104 */         return currentSlot - 36;
/*     */       }
/*     */     } else {
/* 107 */       for (int i = 36; i < 45; i++) {
/* 108 */         ItemStack itemStack = (mc.getPlayer()).inventoryContainer.getSlot(i).getStack();
/*     */         
/* 110 */         if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
/* 111 */           Block block = ((ItemBlock)itemStack.getItem()).getBlock();
/*     */           
/* 113 */           if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block))
/* 114 */             return i - 36; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 118 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\InventoryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */