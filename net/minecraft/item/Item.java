/*      */ package net.minecraft.item;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockWall;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.RegistryNamespaced;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ public class Item
/*      */ {
/*   49 */   public static final RegistryNamespaced<ResourceLocation, Item> itemRegistry = new RegistryNamespaced();
/*   50 */   private static final Map<Block, Item> BLOCK_TO_ITEM = Maps.newHashMap();
/*   51 */   protected static final UUID itemModifierUUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*      */   
/*      */   private CreativeTabs tabToDisplayOn;
/*      */   
/*   55 */   protected static Random itemRand = new Random();
/*      */ 
/*      */   
/*   58 */   protected int maxStackSize = 64;
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxDamage;
/*      */ 
/*      */   
/*      */   protected boolean bFull3D;
/*      */ 
/*      */   
/*      */   protected boolean hasSubtypes;
/*      */ 
/*      */   
/*      */   private Item containerItem;
/*      */ 
/*      */   
/*      */   private String potionEffect;
/*      */ 
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getIdFromItem(Item itemIn) {
/*   82 */     return (itemIn == null) ? 0 : itemRegistry.getIDForObject(itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemById(int id) {
/*   87 */     return (Item)itemRegistry.getObjectById(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemFromBlock(Block blockIn) {
/*   92 */     return BLOCK_TO_ITEM.get(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Item getByNameOrId(String id) {
/*  101 */     Item item = (Item)itemRegistry.getObject(new ResourceLocation(id));
/*      */     
/*  103 */     if (item == null) {
/*      */       
/*      */       try {
/*      */         
/*  107 */         return getItemById(Integer.parseInt(id));
/*      */       }
/*  109 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  115 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/*  123 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setMaxStackSize(int maxStackSize) {
/*  128 */     this.maxStackSize = maxStackSize;
/*  129 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  140 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(ItemStack stack, Block block) {
/*  145 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  153 */     return itemStackIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  162 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemStackLimit() {
/*  170 */     return this.maxStackSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetadata(int damage) {
/*  179 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  184 */     return this.hasSubtypes;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item setHasSubtypes(boolean hasSubtypes) {
/*  189 */     this.hasSubtypes = hasSubtypes;
/*  190 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  198 */     return this.maxDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setMaxDamage(int maxDamageIn) {
/*  206 */     this.maxDamage = maxDamageIn;
/*  207 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDamageable() {
/*  212 */     return (this.maxDamage > 0 && !this.hasSubtypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  224 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  232 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockIn) {
/*  240 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/*  248 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setFull3D() {
/*  256 */     this.bFull3D = true;
/*  257 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFull3D() {
/*  265 */     return this.bFull3D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldRotateAroundWhenRendering() {
/*  274 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setUnlocalizedName(String unlocalizedName) {
/*  282 */     this.unlocalizedName = unlocalizedName;
/*  283 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedNameInefficiently(ItemStack stack) {
/*  292 */     String s = getUnlocalizedName(stack);
/*  293 */     return (s == null) ? "" : StatCollector.translateToLocal(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  301 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName(ItemStack stack) {
/*  310 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setContainerItem(Item containerItem) {
/*  315 */     this.containerItem = containerItem;
/*  316 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getShareTag() {
/*  324 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item getContainerItem() {
/*  329 */     return this.containerItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasContainerItem() {
/*  337 */     return (this.containerItem != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  342 */     return 16777215;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMap() {
/*  365 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction(ItemStack stack) {
/*  373 */     return EnumAction.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration(ItemStack stack) {
/*  381 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setPotionEffect(String potionEffect) {
/*  398 */     this.potionEffect = potionEffect;
/*  399 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPotionEffect(ItemStack stack) {
/*  404 */     return this.potionEffect;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionIngredient(ItemStack stack) {
/*  409 */     return (getPotionEffect(stack) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemStackDisplayName(ItemStack stack) {
/*  424 */     return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect(ItemStack stack) {
/*  429 */     return stack.isItemEnchanted();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity(ItemStack stack) {
/*  437 */     return stack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemTool(ItemStack stack) {
/*  445 */     return (getItemStackLimit() == 1 && isDamageable());
/*      */   }
/*      */ 
/*      */   
/*      */   protected MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
/*  450 */     float f = playerIn.rotationPitch;
/*  451 */     float f1 = playerIn.rotationYaw;
/*  452 */     double d0 = playerIn.posX;
/*  453 */     double d1 = playerIn.posY + playerIn.getEyeHeight();
/*  454 */     double d2 = playerIn.posZ;
/*  455 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/*  456 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/*  457 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/*  458 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/*  459 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/*  460 */     float f6 = f3 * f4;
/*  461 */     float f7 = f2 * f4;
/*  462 */     double d3 = 5.0D;
/*  463 */     Vec3 vec31 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
/*  464 */     return worldIn.rayTraceBlocks(vec3, vec31, useLiquids, !useLiquids, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemEnchantability() {
/*  472 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/*  482 */     subItems.add(new ItemStack(itemIn, 1, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTab() {
/*  490 */     return this.tabToDisplayOn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setCreativeTab(CreativeTabs tab) {
/*  498 */     this.tabToDisplayOn = tab;
/*  499 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canItemEditBlocks() {
/*  508 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/*  519 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/*  524 */     return (Multimap<String, AttributeModifier>)HashMultimap.create();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerItems() {
/*  529 */     registerItemBlock(Blocks.stone, (new ItemMultiTexture(Blocks.stone, Blocks.stone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  533 */               return BlockStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  535 */           })).setUnlocalizedName("stone"));
/*  536 */     registerItemBlock((Block)Blocks.grass, new ItemColored((Block)Blocks.grass, false));
/*  537 */     registerItemBlock(Blocks.dirt, (new ItemMultiTexture(Blocks.dirt, Blocks.dirt, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  541 */               return BlockDirt.DirtType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  543 */           })).setUnlocalizedName("dirt"));
/*  544 */     registerItemBlock(Blocks.cobblestone);
/*  545 */     registerItemBlock(Blocks.planks, (new ItemMultiTexture(Blocks.planks, Blocks.planks, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  549 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  551 */           })).setUnlocalizedName("wood"));
/*  552 */     registerItemBlock(Blocks.sapling, (new ItemMultiTexture(Blocks.sapling, Blocks.sapling, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  556 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  558 */           })).setUnlocalizedName("sapling"));
/*  559 */     registerItemBlock(Blocks.bedrock);
/*  560 */     registerItemBlock((Block)Blocks.sand, (new ItemMultiTexture((Block)Blocks.sand, (Block)Blocks.sand, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  564 */               return BlockSand.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  566 */           })).setUnlocalizedName("sand"));
/*  567 */     registerItemBlock(Blocks.gravel);
/*  568 */     registerItemBlock(Blocks.gold_ore);
/*  569 */     registerItemBlock(Blocks.iron_ore);
/*  570 */     registerItemBlock(Blocks.coal_ore);
/*  571 */     registerItemBlock(Blocks.log, (new ItemMultiTexture(Blocks.log, Blocks.log, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  575 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  577 */           })).setUnlocalizedName("log"));
/*  578 */     registerItemBlock(Blocks.log2, (new ItemMultiTexture(Blocks.log2, Blocks.log2, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  582 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata() + 4).getUnlocalizedName();
/*      */             }
/*  584 */           })).setUnlocalizedName("log"));
/*  585 */     registerItemBlock((Block)Blocks.leaves, (new ItemLeaves(Blocks.leaves)).setUnlocalizedName("leaves"));
/*  586 */     registerItemBlock((Block)Blocks.leaves2, (new ItemLeaves(Blocks.leaves2)).setUnlocalizedName("leaves"));
/*  587 */     registerItemBlock(Blocks.sponge, (new ItemMultiTexture(Blocks.sponge, Blocks.sponge, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  591 */               return ((p_apply_1_.getMetadata() & 0x1) == 1) ? "wet" : "dry";
/*      */             }
/*  593 */           })).setUnlocalizedName("sponge"));
/*  594 */     registerItemBlock(Blocks.glass);
/*  595 */     registerItemBlock(Blocks.lapis_ore);
/*  596 */     registerItemBlock(Blocks.lapis_block);
/*  597 */     registerItemBlock(Blocks.dispenser);
/*  598 */     registerItemBlock(Blocks.sandstone, (new ItemMultiTexture(Blocks.sandstone, Blocks.sandstone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  602 */               return BlockSandStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  604 */           })).setUnlocalizedName("sandStone"));
/*  605 */     registerItemBlock(Blocks.noteblock);
/*  606 */     registerItemBlock(Blocks.golden_rail);
/*  607 */     registerItemBlock(Blocks.detector_rail);
/*  608 */     registerItemBlock((Block)Blocks.sticky_piston, new ItemPiston((Block)Blocks.sticky_piston));
/*  609 */     registerItemBlock(Blocks.web);
/*  610 */     registerItemBlock((Block)Blocks.tallgrass, (new ItemColored((Block)Blocks.tallgrass, true)).setSubtypeNames(new String[] { "shrub", "grass", "fern" }));
/*  611 */     registerItemBlock((Block)Blocks.deadbush);
/*  612 */     registerItemBlock((Block)Blocks.piston, new ItemPiston((Block)Blocks.piston));
/*  613 */     registerItemBlock(Blocks.wool, (new ItemCloth(Blocks.wool)).setUnlocalizedName("cloth"));
/*  614 */     registerItemBlock((Block)Blocks.yellow_flower, (new ItemMultiTexture((Block)Blocks.yellow_flower, (Block)Blocks.yellow_flower, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  618 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.YELLOW, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  620 */           })).setUnlocalizedName("flower"));
/*  621 */     registerItemBlock((Block)Blocks.red_flower, (new ItemMultiTexture((Block)Blocks.red_flower, (Block)Blocks.red_flower, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  625 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  627 */           })).setUnlocalizedName("rose"));
/*  628 */     registerItemBlock((Block)Blocks.brown_mushroom);
/*  629 */     registerItemBlock((Block)Blocks.red_mushroom);
/*  630 */     registerItemBlock(Blocks.gold_block);
/*  631 */     registerItemBlock(Blocks.iron_block);
/*  632 */     registerItemBlock((Block)Blocks.stone_slab, (new ItemSlab((Block)Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab)).setUnlocalizedName("stoneSlab"));
/*  633 */     registerItemBlock(Blocks.brick_block);
/*  634 */     registerItemBlock(Blocks.tnt);
/*  635 */     registerItemBlock(Blocks.bookshelf);
/*  636 */     registerItemBlock(Blocks.mossy_cobblestone);
/*  637 */     registerItemBlock(Blocks.obsidian);
/*  638 */     registerItemBlock(Blocks.torch);
/*  639 */     registerItemBlock(Blocks.mob_spawner);
/*  640 */     registerItemBlock(Blocks.oak_stairs);
/*  641 */     registerItemBlock((Block)Blocks.chest);
/*  642 */     registerItemBlock(Blocks.diamond_ore);
/*  643 */     registerItemBlock(Blocks.diamond_block);
/*  644 */     registerItemBlock(Blocks.crafting_table);
/*  645 */     registerItemBlock(Blocks.farmland);
/*  646 */     registerItemBlock(Blocks.furnace);
/*  647 */     registerItemBlock(Blocks.lit_furnace);
/*  648 */     registerItemBlock(Blocks.ladder);
/*  649 */     registerItemBlock(Blocks.rail);
/*  650 */     registerItemBlock(Blocks.stone_stairs);
/*  651 */     registerItemBlock(Blocks.lever);
/*  652 */     registerItemBlock(Blocks.stone_pressure_plate);
/*  653 */     registerItemBlock(Blocks.wooden_pressure_plate);
/*  654 */     registerItemBlock(Blocks.redstone_ore);
/*  655 */     registerItemBlock(Blocks.redstone_torch);
/*  656 */     registerItemBlock(Blocks.stone_button);
/*  657 */     registerItemBlock(Blocks.snow_layer, new ItemSnow(Blocks.snow_layer));
/*  658 */     registerItemBlock(Blocks.ice);
/*  659 */     registerItemBlock(Blocks.snow);
/*  660 */     registerItemBlock((Block)Blocks.cactus);
/*  661 */     registerItemBlock(Blocks.clay);
/*  662 */     registerItemBlock(Blocks.jukebox);
/*  663 */     registerItemBlock(Blocks.oak_fence);
/*  664 */     registerItemBlock(Blocks.spruce_fence);
/*  665 */     registerItemBlock(Blocks.birch_fence);
/*  666 */     registerItemBlock(Blocks.jungle_fence);
/*  667 */     registerItemBlock(Blocks.dark_oak_fence);
/*  668 */     registerItemBlock(Blocks.acacia_fence);
/*  669 */     registerItemBlock(Blocks.pumpkin);
/*  670 */     registerItemBlock(Blocks.netherrack);
/*  671 */     registerItemBlock(Blocks.soul_sand);
/*  672 */     registerItemBlock(Blocks.glowstone);
/*  673 */     registerItemBlock(Blocks.lit_pumpkin);
/*  674 */     registerItemBlock(Blocks.trapdoor);
/*  675 */     registerItemBlock(Blocks.monster_egg, (new ItemMultiTexture(Blocks.monster_egg, Blocks.monster_egg, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  679 */               return BlockSilverfish.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  681 */           })).setUnlocalizedName("monsterStoneEgg"));
/*  682 */     registerItemBlock(Blocks.stonebrick, (new ItemMultiTexture(Blocks.stonebrick, Blocks.stonebrick, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  686 */               return BlockStoneBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  688 */           })).setUnlocalizedName("stonebricksmooth"));
/*  689 */     registerItemBlock(Blocks.brown_mushroom_block);
/*  690 */     registerItemBlock(Blocks.red_mushroom_block);
/*  691 */     registerItemBlock(Blocks.iron_bars);
/*  692 */     registerItemBlock(Blocks.glass_pane);
/*  693 */     registerItemBlock(Blocks.melon_block);
/*  694 */     registerItemBlock(Blocks.vine, new ItemColored(Blocks.vine, false));
/*  695 */     registerItemBlock(Blocks.oak_fence_gate);
/*  696 */     registerItemBlock(Blocks.spruce_fence_gate);
/*  697 */     registerItemBlock(Blocks.birch_fence_gate);
/*  698 */     registerItemBlock(Blocks.jungle_fence_gate);
/*  699 */     registerItemBlock(Blocks.dark_oak_fence_gate);
/*  700 */     registerItemBlock(Blocks.acacia_fence_gate);
/*  701 */     registerItemBlock(Blocks.brick_stairs);
/*  702 */     registerItemBlock(Blocks.stone_brick_stairs);
/*  703 */     registerItemBlock((Block)Blocks.mycelium);
/*  704 */     registerItemBlock(Blocks.waterlily, new ItemLilyPad(Blocks.waterlily));
/*  705 */     registerItemBlock(Blocks.nether_brick);
/*  706 */     registerItemBlock(Blocks.nether_brick_fence);
/*  707 */     registerItemBlock(Blocks.nether_brick_stairs);
/*  708 */     registerItemBlock(Blocks.enchanting_table);
/*  709 */     registerItemBlock(Blocks.end_portal_frame);
/*  710 */     registerItemBlock(Blocks.end_stone);
/*  711 */     registerItemBlock(Blocks.dragon_egg);
/*  712 */     registerItemBlock(Blocks.redstone_lamp);
/*  713 */     registerItemBlock((Block)Blocks.wooden_slab, (new ItemSlab((Block)Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab)).setUnlocalizedName("woodSlab"));
/*  714 */     registerItemBlock(Blocks.sandstone_stairs);
/*  715 */     registerItemBlock(Blocks.emerald_ore);
/*  716 */     registerItemBlock(Blocks.ender_chest);
/*  717 */     registerItemBlock((Block)Blocks.tripwire_hook);
/*  718 */     registerItemBlock(Blocks.emerald_block);
/*  719 */     registerItemBlock(Blocks.spruce_stairs);
/*  720 */     registerItemBlock(Blocks.birch_stairs);
/*  721 */     registerItemBlock(Blocks.jungle_stairs);
/*  722 */     registerItemBlock(Blocks.command_block);
/*  723 */     registerItemBlock((Block)Blocks.beacon);
/*  724 */     registerItemBlock(Blocks.cobblestone_wall, (new ItemMultiTexture(Blocks.cobblestone_wall, Blocks.cobblestone_wall, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  728 */               return BlockWall.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  730 */           })).setUnlocalizedName("cobbleWall"));
/*  731 */     registerItemBlock(Blocks.wooden_button);
/*  732 */     registerItemBlock(Blocks.anvil, (new ItemAnvilBlock(Blocks.anvil)).setUnlocalizedName("anvil"));
/*  733 */     registerItemBlock(Blocks.trapped_chest);
/*  734 */     registerItemBlock(Blocks.light_weighted_pressure_plate);
/*  735 */     registerItemBlock(Blocks.heavy_weighted_pressure_plate);
/*  736 */     registerItemBlock((Block)Blocks.daylight_detector);
/*  737 */     registerItemBlock(Blocks.redstone_block);
/*  738 */     registerItemBlock(Blocks.quartz_ore);
/*  739 */     registerItemBlock((Block)Blocks.hopper);
/*  740 */     registerItemBlock(Blocks.quartz_block, (new ItemMultiTexture(Blocks.quartz_block, Blocks.quartz_block, new String[] { "default", "chiseled", "lines" })).setUnlocalizedName("quartzBlock"));
/*  741 */     registerItemBlock(Blocks.quartz_stairs);
/*  742 */     registerItemBlock(Blocks.activator_rail);
/*  743 */     registerItemBlock(Blocks.dropper);
/*  744 */     registerItemBlock(Blocks.stained_hardened_clay, (new ItemCloth(Blocks.stained_hardened_clay)).setUnlocalizedName("clayHardenedStained"));
/*  745 */     registerItemBlock(Blocks.barrier);
/*  746 */     registerItemBlock(Blocks.iron_trapdoor);
/*  747 */     registerItemBlock(Blocks.hay_block);
/*  748 */     registerItemBlock(Blocks.carpet, (new ItemCloth(Blocks.carpet)).setUnlocalizedName("woolCarpet"));
/*  749 */     registerItemBlock(Blocks.hardened_clay);
/*  750 */     registerItemBlock(Blocks.coal_block);
/*  751 */     registerItemBlock(Blocks.packed_ice);
/*  752 */     registerItemBlock(Blocks.acacia_stairs);
/*  753 */     registerItemBlock(Blocks.dark_oak_stairs);
/*  754 */     registerItemBlock(Blocks.slime_block);
/*  755 */     registerItemBlock((Block)Blocks.double_plant, (new ItemDoublePlant((Block)Blocks.double_plant, (Block)Blocks.double_plant, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  759 */               return BlockDoublePlant.EnumPlantType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  761 */           })).setUnlocalizedName("doublePlant"));
/*  762 */     registerItemBlock((Block)Blocks.stained_glass, (new ItemCloth((Block)Blocks.stained_glass)).setUnlocalizedName("stainedGlass"));
/*  763 */     registerItemBlock((Block)Blocks.stained_glass_pane, (new ItemCloth((Block)Blocks.stained_glass_pane)).setUnlocalizedName("stainedGlassPane"));
/*  764 */     registerItemBlock(Blocks.prismarine, (new ItemMultiTexture(Blocks.prismarine, Blocks.prismarine, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  768 */               return BlockPrismarine.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  770 */           })).setUnlocalizedName("prismarine"));
/*  771 */     registerItemBlock(Blocks.sea_lantern);
/*  772 */     registerItemBlock(Blocks.red_sandstone, (new ItemMultiTexture(Blocks.red_sandstone, Blocks.red_sandstone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  776 */               return BlockRedSandstone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  778 */           })).setUnlocalizedName("redSandStone"));
/*  779 */     registerItemBlock(Blocks.red_sandstone_stairs);
/*  780 */     registerItemBlock((Block)Blocks.stone_slab2, (new ItemSlab((Block)Blocks.stone_slab2, Blocks.stone_slab2, Blocks.double_stone_slab2)).setUnlocalizedName("stoneSlab2"));
/*  781 */     registerItem(256, "iron_shovel", (new ItemSpade(ToolMaterial.IRON)).setUnlocalizedName("shovelIron"));
/*  782 */     registerItem(257, "iron_pickaxe", (new ItemPickaxe(ToolMaterial.IRON)).setUnlocalizedName("pickaxeIron"));
/*  783 */     registerItem(258, "iron_axe", (new ItemAxe(ToolMaterial.IRON)).setUnlocalizedName("hatchetIron"));
/*  784 */     registerItem(259, "flint_and_steel", (new ItemFlintAndSteel()).setUnlocalizedName("flintAndSteel"));
/*  785 */     registerItem(260, "apple", (new ItemFood(4, 0.3F, false)).setUnlocalizedName("apple"));
/*  786 */     registerItem(261, "bow", (new ItemBow()).setUnlocalizedName("bow"));
/*  787 */     registerItem(262, "arrow", (new Item()).setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat));
/*  788 */     registerItem(263, "coal", (new ItemCoal()).setUnlocalizedName("coal"));
/*  789 */     registerItem(264, "diamond", (new Item()).setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials));
/*  790 */     registerItem(265, "iron_ingot", (new Item()).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials));
/*  791 */     registerItem(266, "gold_ingot", (new Item()).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials));
/*  792 */     registerItem(267, "iron_sword", (new ItemSword(ToolMaterial.IRON)).setUnlocalizedName("swordIron"));
/*  793 */     registerItem(268, "wooden_sword", (new ItemSword(ToolMaterial.WOOD)).setUnlocalizedName("swordWood"));
/*  794 */     registerItem(269, "wooden_shovel", (new ItemSpade(ToolMaterial.WOOD)).setUnlocalizedName("shovelWood"));
/*  795 */     registerItem(270, "wooden_pickaxe", (new ItemPickaxe(ToolMaterial.WOOD)).setUnlocalizedName("pickaxeWood"));
/*  796 */     registerItem(271, "wooden_axe", (new ItemAxe(ToolMaterial.WOOD)).setUnlocalizedName("hatchetWood"));
/*  797 */     registerItem(272, "stone_sword", (new ItemSword(ToolMaterial.STONE)).setUnlocalizedName("swordStone"));
/*  798 */     registerItem(273, "stone_shovel", (new ItemSpade(ToolMaterial.STONE)).setUnlocalizedName("shovelStone"));
/*  799 */     registerItem(274, "stone_pickaxe", (new ItemPickaxe(ToolMaterial.STONE)).setUnlocalizedName("pickaxeStone"));
/*  800 */     registerItem(275, "stone_axe", (new ItemAxe(ToolMaterial.STONE)).setUnlocalizedName("hatchetStone"));
/*  801 */     registerItem(276, "diamond_sword", (new ItemSword(ToolMaterial.EMERALD)).setUnlocalizedName("swordDiamond"));
/*  802 */     registerItem(277, "diamond_shovel", (new ItemSpade(ToolMaterial.EMERALD)).setUnlocalizedName("shovelDiamond"));
/*  803 */     registerItem(278, "diamond_pickaxe", (new ItemPickaxe(ToolMaterial.EMERALD)).setUnlocalizedName("pickaxeDiamond"));
/*  804 */     registerItem(279, "diamond_axe", (new ItemAxe(ToolMaterial.EMERALD)).setUnlocalizedName("hatchetDiamond"));
/*  805 */     registerItem(280, "stick", (new Item()).setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials));
/*  806 */     registerItem(281, "bowl", (new Item()).setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials));
/*  807 */     registerItem(282, "mushroom_stew", (new ItemSoup(6)).setUnlocalizedName("mushroomStew"));
/*  808 */     registerItem(283, "golden_sword", (new ItemSword(ToolMaterial.GOLD)).setUnlocalizedName("swordGold"));
/*  809 */     registerItem(284, "golden_shovel", (new ItemSpade(ToolMaterial.GOLD)).setUnlocalizedName("shovelGold"));
/*  810 */     registerItem(285, "golden_pickaxe", (new ItemPickaxe(ToolMaterial.GOLD)).setUnlocalizedName("pickaxeGold"));
/*  811 */     registerItem(286, "golden_axe", (new ItemAxe(ToolMaterial.GOLD)).setUnlocalizedName("hatchetGold"));
/*  812 */     registerItem(287, "string", (new ItemReed(Blocks.tripwire)).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials));
/*  813 */     registerItem(288, "feather", (new Item()).setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials));
/*  814 */     registerItem(289, "gunpowder", (new Item()).setUnlocalizedName("sulphur").setPotionEffect("+14&13-13").setCreativeTab(CreativeTabs.tabMaterials));
/*  815 */     registerItem(290, "wooden_hoe", (new ItemHoe(ToolMaterial.WOOD)).setUnlocalizedName("hoeWood"));
/*  816 */     registerItem(291, "stone_hoe", (new ItemHoe(ToolMaterial.STONE)).setUnlocalizedName("hoeStone"));
/*  817 */     registerItem(292, "iron_hoe", (new ItemHoe(ToolMaterial.IRON)).setUnlocalizedName("hoeIron"));
/*  818 */     registerItem(293, "diamond_hoe", (new ItemHoe(ToolMaterial.EMERALD)).setUnlocalizedName("hoeDiamond"));
/*  819 */     registerItem(294, "golden_hoe", (new ItemHoe(ToolMaterial.GOLD)).setUnlocalizedName("hoeGold"));
/*  820 */     registerItem(295, "wheat_seeds", (new ItemSeeds(Blocks.wheat, Blocks.farmland)).setUnlocalizedName("seeds"));
/*  821 */     registerItem(296, "wheat", (new Item()).setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials));
/*  822 */     registerItem(297, "bread", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("bread"));
/*  823 */     registerItem(298, "leather_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 0)).setUnlocalizedName("helmetCloth"));
/*  824 */     registerItem(299, "leather_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 1)).setUnlocalizedName("chestplateCloth"));
/*  825 */     registerItem(300, "leather_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 2)).setUnlocalizedName("leggingsCloth"));
/*  826 */     registerItem(301, "leather_boots", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 3)).setUnlocalizedName("bootsCloth"));
/*  827 */     registerItem(302, "chainmail_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 0)).setUnlocalizedName("helmetChain"));
/*  828 */     registerItem(303, "chainmail_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 1)).setUnlocalizedName("chestplateChain"));
/*  829 */     registerItem(304, "chainmail_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 2)).setUnlocalizedName("leggingsChain"));
/*  830 */     registerItem(305, "chainmail_boots", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 3)).setUnlocalizedName("bootsChain"));
/*  831 */     registerItem(306, "iron_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 0)).setUnlocalizedName("helmetIron"));
/*  832 */     registerItem(307, "iron_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 1)).setUnlocalizedName("chestplateIron"));
/*  833 */     registerItem(308, "iron_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 2)).setUnlocalizedName("leggingsIron"));
/*  834 */     registerItem(309, "iron_boots", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 3)).setUnlocalizedName("bootsIron"));
/*  835 */     registerItem(310, "diamond_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)).setUnlocalizedName("helmetDiamond"));
/*  836 */     registerItem(311, "diamond_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 1)).setUnlocalizedName("chestplateDiamond"));
/*  837 */     registerItem(312, "diamond_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 2)).setUnlocalizedName("leggingsDiamond"));
/*  838 */     registerItem(313, "diamond_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 3)).setUnlocalizedName("bootsDiamond"));
/*  839 */     registerItem(314, "golden_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 0)).setUnlocalizedName("helmetGold"));
/*  840 */     registerItem(315, "golden_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 1)).setUnlocalizedName("chestplateGold"));
/*  841 */     registerItem(316, "golden_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 2)).setUnlocalizedName("leggingsGold"));
/*  842 */     registerItem(317, "golden_boots", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 3)).setUnlocalizedName("bootsGold"));
/*  843 */     registerItem(318, "flint", (new Item()).setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials));
/*  844 */     registerItem(319, "porkchop", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("porkchopRaw"));
/*  845 */     registerItem(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("porkchopCooked"));
/*  846 */     registerItem(321, "painting", (new ItemHangingEntity((Class)EntityPainting.class)).setUnlocalizedName("painting"));
/*  847 */     registerItem(322, "golden_apple", (new ItemAppleGold(4, 1.2F, false)).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("appleGold"));
/*  848 */     registerItem(323, "sign", (new ItemSign()).setUnlocalizedName("sign"));
/*  849 */     registerItem(324, "wooden_door", (new ItemDoor(Blocks.oak_door)).setUnlocalizedName("doorOak"));
/*  850 */     Item item = (new ItemBucket(Blocks.air)).setUnlocalizedName("bucket").setMaxStackSize(16);
/*  851 */     registerItem(325, "bucket", item);
/*  852 */     registerItem(326, "water_bucket", (new ItemBucket((Block)Blocks.flowing_water)).setUnlocalizedName("bucketWater").setContainerItem(item));
/*  853 */     registerItem(327, "lava_bucket", (new ItemBucket((Block)Blocks.flowing_lava)).setUnlocalizedName("bucketLava").setContainerItem(item));
/*  854 */     registerItem(328, "minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.RIDEABLE)).setUnlocalizedName("minecart"));
/*  855 */     registerItem(329, "saddle", (new ItemSaddle()).setUnlocalizedName("saddle"));
/*  856 */     registerItem(330, "iron_door", (new ItemDoor(Blocks.iron_door)).setUnlocalizedName("doorIron"));
/*  857 */     registerItem(331, "redstone", (new ItemRedstone()).setUnlocalizedName("redstone").setPotionEffect("-5+6-7"));
/*  858 */     registerItem(332, "snowball", (new ItemSnowball()).setUnlocalizedName("snowball"));
/*  859 */     registerItem(333, "boat", (new ItemBoat()).setUnlocalizedName("boat"));
/*  860 */     registerItem(334, "leather", (new Item()).setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials));
/*  861 */     registerItem(335, "milk_bucket", (new ItemBucketMilk()).setUnlocalizedName("milk").setContainerItem(item));
/*  862 */     registerItem(336, "brick", (new Item()).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials));
/*  863 */     registerItem(337, "clay_ball", (new Item()).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials));
/*  864 */     registerItem(338, "reeds", (new ItemReed((Block)Blocks.reeds)).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials));
/*  865 */     registerItem(339, "paper", (new Item()).setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc));
/*  866 */     registerItem(340, "book", (new ItemBook()).setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc));
/*  867 */     registerItem(341, "slime_ball", (new Item()).setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc));
/*  868 */     registerItem(342, "chest_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.CHEST)).setUnlocalizedName("minecartChest"));
/*  869 */     registerItem(343, "furnace_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.FURNACE)).setUnlocalizedName("minecartFurnace"));
/*  870 */     registerItem(344, "egg", (new ItemEgg()).setUnlocalizedName("egg"));
/*  871 */     registerItem(345, "compass", (new Item()).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools));
/*  872 */     registerItem(346, "fishing_rod", (new ItemFishingRod()).setUnlocalizedName("fishingRod"));
/*  873 */     registerItem(347, "clock", (new Item()).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools));
/*  874 */     registerItem(348, "glowstone_dust", (new Item()).setUnlocalizedName("yellowDust").setPotionEffect("+5-6-7").setCreativeTab(CreativeTabs.tabMaterials));
/*  875 */     registerItem(349, "fish", (new ItemFishFood(false)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  876 */     registerItem(350, "cooked_fish", (new ItemFishFood(true)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  877 */     registerItem(351, "dye", (new ItemDye()).setUnlocalizedName("dyePowder"));
/*  878 */     registerItem(352, "bone", (new Item()).setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc));
/*  879 */     registerItem(353, "sugar", (new Item()).setUnlocalizedName("sugar").setPotionEffect("-0+1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabMaterials));
/*  880 */     registerItem(354, "cake", (new ItemReed(Blocks.cake)).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood));
/*  881 */     registerItem(355, "bed", (new ItemBed()).setMaxStackSize(1).setUnlocalizedName("bed"));
/*  882 */     registerItem(356, "repeater", (new ItemReed((Block)Blocks.unpowered_repeater)).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone));
/*  883 */     registerItem(357, "cookie", (new ItemFood(2, 0.1F, false)).setUnlocalizedName("cookie"));
/*  884 */     registerItem(358, "filled_map", (new ItemMap()).setUnlocalizedName("map"));
/*  885 */     registerItem(359, "shears", (new ItemShears()).setUnlocalizedName("shears"));
/*  886 */     registerItem(360, "melon", (new ItemFood(2, 0.3F, false)).setUnlocalizedName("melon"));
/*  887 */     registerItem(361, "pumpkin_seeds", (new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland)).setUnlocalizedName("seeds_pumpkin"));
/*  888 */     registerItem(362, "melon_seeds", (new ItemSeeds(Blocks.melon_stem, Blocks.farmland)).setUnlocalizedName("seeds_melon"));
/*  889 */     registerItem(363, "beef", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("beefRaw"));
/*  890 */     registerItem(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("beefCooked"));
/*  891 */     registerItem(365, "chicken", (new ItemFood(2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw"));
/*  892 */     registerItem(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).setUnlocalizedName("chickenCooked"));
/*  893 */     registerItem(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("rottenFlesh"));
/*  894 */     registerItem(368, "ender_pearl", (new ItemEnderPearl()).setUnlocalizedName("enderPearl"));
/*  895 */     registerItem(369, "blaze_rod", (new Item()).setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials).setFull3D());
/*  896 */     registerItem(370, "ghast_tear", (new Item()).setUnlocalizedName("ghastTear").setPotionEffect("+0-1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  897 */     registerItem(371, "gold_nugget", (new Item()).setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials));
/*  898 */     registerItem(372, "nether_wart", (new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand)).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4"));
/*  899 */     registerItem(373, "potion", (new ItemPotion()).setUnlocalizedName("potion"));
/*  900 */     registerItem(374, "glass_bottle", (new ItemGlassBottle()).setUnlocalizedName("glassBottle"));
/*  901 */     registerItem(375, "spider_eye", (new ItemFood(2, 0.8F, false)).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setUnlocalizedName("spiderEye").setPotionEffect("-0-1+2-3&4-4+13"));
/*  902 */     registerItem(376, "fermented_spider_eye", (new Item()).setUnlocalizedName("fermentedSpiderEye").setPotionEffect("-0+3-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  903 */     registerItem(377, "blaze_powder", (new Item()).setUnlocalizedName("blazePowder").setPotionEffect("+0-1-2+3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  904 */     registerItem(378, "magma_cream", (new Item()).setUnlocalizedName("magmaCream").setPotionEffect("+0+1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  905 */     registerItem(379, "brewing_stand", (new ItemReed(Blocks.brewing_stand)).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing));
/*  906 */     registerItem(380, "cauldron", (new ItemReed((Block)Blocks.cauldron)).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing));
/*  907 */     registerItem(381, "ender_eye", (new ItemEnderEye()).setUnlocalizedName("eyeOfEnder"));
/*  908 */     registerItem(382, "speckled_melon", (new Item()).setUnlocalizedName("speckledMelon").setPotionEffect("+0-1+2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  909 */     registerItem(383, "spawn_egg", (new ItemMonsterPlacer()).setUnlocalizedName("monsterPlacer"));
/*  910 */     registerItem(384, "experience_bottle", (new ItemExpBottle()).setUnlocalizedName("expBottle"));
/*  911 */     registerItem(385, "fire_charge", (new ItemFireball()).setUnlocalizedName("fireball"));
/*  912 */     registerItem(386, "writable_book", (new ItemWritableBook()).setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc));
/*  913 */     registerItem(387, "written_book", (new ItemEditableBook()).setUnlocalizedName("writtenBook").setMaxStackSize(16));
/*  914 */     registerItem(388, "emerald", (new Item()).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials));
/*  915 */     registerItem(389, "item_frame", (new ItemHangingEntity((Class)EntityItemFrame.class)).setUnlocalizedName("frame"));
/*  916 */     registerItem(390, "flower_pot", (new ItemReed(Blocks.flower_pot)).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations));
/*  917 */     registerItem(391, "carrot", (new ItemSeedFood(3, 0.6F, Blocks.carrots, Blocks.farmland)).setUnlocalizedName("carrots"));
/*  918 */     registerItem(392, "potato", (new ItemSeedFood(1, 0.3F, Blocks.potatoes, Blocks.farmland)).setUnlocalizedName("potato"));
/*  919 */     registerItem(393, "baked_potato", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("potatoBaked"));
/*  920 */     registerItem(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).setPotionEffect(Potion.poison.id, 5, 0, 0.6F).setUnlocalizedName("potatoPoisonous"));
/*  921 */     registerItem(395, "map", (new ItemEmptyMap()).setUnlocalizedName("emptyMap"));
/*  922 */     registerItem(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).setUnlocalizedName("carrotGolden").setPotionEffect("-0+1+2-3+13&4-4").setCreativeTab(CreativeTabs.tabBrewing));
/*  923 */     registerItem(397, "skull", (new ItemSkull()).setUnlocalizedName("skull"));
/*  924 */     registerItem(398, "carrot_on_a_stick", (new ItemCarrotOnAStick()).setUnlocalizedName("carrotOnAStick"));
/*  925 */     registerItem(399, "nether_star", (new ItemSimpleFoiled()).setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials));
/*  926 */     registerItem(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood));
/*  927 */     registerItem(401, "fireworks", (new ItemFirework()).setUnlocalizedName("fireworks"));
/*  928 */     registerItem(402, "firework_charge", (new ItemFireworkCharge()).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc));
/*  929 */     registerItem(403, "enchanted_book", (new ItemEnchantedBook()).setMaxStackSize(1).setUnlocalizedName("enchantedBook"));
/*  930 */     registerItem(404, "comparator", (new ItemReed((Block)Blocks.unpowered_comparator)).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone));
/*  931 */     registerItem(405, "netherbrick", (new Item()).setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials));
/*  932 */     registerItem(406, "quartz", (new Item()).setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials));
/*  933 */     registerItem(407, "tnt_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.TNT)).setUnlocalizedName("minecartTnt"));
/*  934 */     registerItem(408, "hopper_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.HOPPER)).setUnlocalizedName("minecartHopper"));
/*  935 */     registerItem(409, "prismarine_shard", (new Item()).setUnlocalizedName("prismarineShard").setCreativeTab(CreativeTabs.tabMaterials));
/*  936 */     registerItem(410, "prismarine_crystals", (new Item()).setUnlocalizedName("prismarineCrystals").setCreativeTab(CreativeTabs.tabMaterials));
/*  937 */     registerItem(411, "rabbit", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("rabbitRaw"));
/*  938 */     registerItem(412, "cooked_rabbit", (new ItemFood(5, 0.6F, true)).setUnlocalizedName("rabbitCooked"));
/*  939 */     registerItem(413, "rabbit_stew", (new ItemSoup(10)).setUnlocalizedName("rabbitStew"));
/*  940 */     registerItem(414, "rabbit_foot", (new Item()).setUnlocalizedName("rabbitFoot").setPotionEffect("+0+1-2+3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  941 */     registerItem(415, "rabbit_hide", (new Item()).setUnlocalizedName("rabbitHide").setCreativeTab(CreativeTabs.tabMaterials));
/*  942 */     registerItem(416, "armor_stand", (new ItemArmorStand()).setUnlocalizedName("armorStand").setMaxStackSize(16));
/*  943 */     registerItem(417, "iron_horse_armor", (new Item()).setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  944 */     registerItem(418, "golden_horse_armor", (new Item()).setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  945 */     registerItem(419, "diamond_horse_armor", (new Item()).setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  946 */     registerItem(420, "lead", (new ItemLead()).setUnlocalizedName("leash"));
/*  947 */     registerItem(421, "name_tag", (new ItemNameTag()).setUnlocalizedName("nameTag"));
/*  948 */     registerItem(422, "command_block_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.COMMAND_BLOCK)).setUnlocalizedName("minecartCommandBlock").setCreativeTab(null));
/*  949 */     registerItem(423, "mutton", (new ItemFood(2, 0.3F, true)).setUnlocalizedName("muttonRaw"));
/*  950 */     registerItem(424, "cooked_mutton", (new ItemFood(6, 0.8F, true)).setUnlocalizedName("muttonCooked"));
/*  951 */     registerItem(425, "banner", (new ItemBanner()).setUnlocalizedName("banner"));
/*  952 */     registerItem(427, "spruce_door", (new ItemDoor(Blocks.spruce_door)).setUnlocalizedName("doorSpruce"));
/*  953 */     registerItem(428, "birch_door", (new ItemDoor(Blocks.birch_door)).setUnlocalizedName("doorBirch"));
/*  954 */     registerItem(429, "jungle_door", (new ItemDoor(Blocks.jungle_door)).setUnlocalizedName("doorJungle"));
/*  955 */     registerItem(430, "acacia_door", (new ItemDoor(Blocks.acacia_door)).setUnlocalizedName("doorAcacia"));
/*  956 */     registerItem(431, "dark_oak_door", (new ItemDoor(Blocks.dark_oak_door)).setUnlocalizedName("doorDarkOak"));
/*  957 */     registerItem(2256, "record_13", (new ItemRecord("13")).setUnlocalizedName("record"));
/*  958 */     registerItem(2257, "record_cat", (new ItemRecord("cat")).setUnlocalizedName("record"));
/*  959 */     registerItem(2258, "record_blocks", (new ItemRecord("blocks")).setUnlocalizedName("record"));
/*  960 */     registerItem(2259, "record_chirp", (new ItemRecord("chirp")).setUnlocalizedName("record"));
/*  961 */     registerItem(2260, "record_far", (new ItemRecord("far")).setUnlocalizedName("record"));
/*  962 */     registerItem(2261, "record_mall", (new ItemRecord("mall")).setUnlocalizedName("record"));
/*  963 */     registerItem(2262, "record_mellohi", (new ItemRecord("mellohi")).setUnlocalizedName("record"));
/*  964 */     registerItem(2263, "record_stal", (new ItemRecord("stal")).setUnlocalizedName("record"));
/*  965 */     registerItem(2264, "record_strad", (new ItemRecord("strad")).setUnlocalizedName("record"));
/*  966 */     registerItem(2265, "record_ward", (new ItemRecord("ward")).setUnlocalizedName("record"));
/*  967 */     registerItem(2266, "record_11", (new ItemRecord("11")).setUnlocalizedName("record"));
/*  968 */     registerItem(2267, "record_wait", (new ItemRecord("wait")).setUnlocalizedName("record"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void registerItemBlock(Block blockIn) {
/*  976 */     registerItemBlock(blockIn, new ItemBlock(blockIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void registerItemBlock(Block blockIn, Item itemIn) {
/*  984 */     registerItem(Block.getIdFromBlock(blockIn), (ResourceLocation)Block.blockRegistry.getNameForObject(blockIn), itemIn);
/*  985 */     BLOCK_TO_ITEM.put(blockIn, itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, String textualID, Item itemIn) {
/*  990 */     registerItem(id, new ResourceLocation(textualID), itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, ResourceLocation textualID, Item itemIn) {
/*  995 */     itemRegistry.register(id, textualID, itemIn);
/*      */   }
/*      */   
/*      */   public enum ToolMaterial
/*      */   {
/* 1000 */     WOOD(0, 59, 2.0F, 0.0F, 15),
/* 1001 */     STONE(1, 131, 4.0F, 1.0F, 5),
/* 1002 */     IRON(2, 250, 6.0F, 2.0F, 14),
/* 1003 */     EMERALD(3, 1561, 8.0F, 3.0F, 10),
/* 1004 */     GOLD(0, 32, 12.0F, 0.0F, 22);
/*      */     
/*      */     private final int harvestLevel;
/*      */     
/*      */     private final int maxUses;
/*      */     private final float efficiencyOnProperMaterial;
/*      */     private final float damageVsEntity;
/*      */     private final int enchantability;
/*      */     
/*      */     ToolMaterial(int harvestLevel, int maxUses, float efficiency, float damageVsEntity, int enchantability) {
/* 1014 */       this.harvestLevel = harvestLevel;
/* 1015 */       this.maxUses = maxUses;
/* 1016 */       this.efficiencyOnProperMaterial = efficiency;
/* 1017 */       this.damageVsEntity = damageVsEntity;
/* 1018 */       this.enchantability = enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxUses() {
/* 1023 */       return this.maxUses;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getEfficiencyOnProperMaterial() {
/* 1028 */       return this.efficiencyOnProperMaterial;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getDamageVsEntity() {
/* 1033 */       return this.damageVsEntity;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHarvestLevel() {
/* 1038 */       return this.harvestLevel;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getEnchantability() {
/* 1043 */       return this.enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public Item getRepairItem() {
/* 1048 */       return (this == WOOD) ? Item.getItemFromBlock(Blocks.planks) : ((this == STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : ((this == GOLD) ? Items.gold_ingot : ((this == IRON) ? Items.iron_ingot : ((this == EMERALD) ? Items.diamond : null))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */