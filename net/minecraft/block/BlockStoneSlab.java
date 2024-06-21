/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockStoneSlab
/*     */   extends BlockSlab {
/*  22 */   public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
/*  23 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */ 
/*     */   
/*     */   public BlockStoneSlab() {
/*  27 */     super(Material.rock);
/*  28 */     IBlockState iblockstate = this.blockState.getBaseState();
/*     */     
/*  30 */     if (isDouble()) {
/*     */       
/*  32 */       iblockstate = iblockstate.withProperty((IProperty)SEAMLESS, Boolean.FALSE);
/*     */     }
/*     */     else {
/*     */       
/*  36 */       iblockstate = iblockstate.withProperty((IProperty)HALF, BlockSlab.EnumBlockHalf.BOTTOM);
/*     */     } 
/*     */     
/*  39 */     setDefaultState(iblockstate.withProperty((IProperty)VARIANT, EnumType.STONE));
/*  40 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  50 */     return Item.getItemFromBlock(Blocks.stone_slab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  58 */     return Item.getItemFromBlock(Blocks.stone_slab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(int meta) {
/*  66 */     return getUnlocalizedName() + "." + EnumType.byMetadata(meta).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<?> getVariantProperty() {
/*  71 */     return (IProperty<?>)VARIANT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getVariant(ItemStack stack) {
/*  76 */     return EnumType.byMetadata(stack.getMetadata() & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  84 */     if (itemIn != Item.getItemFromBlock(Blocks.double_stone_slab))
/*     */     {
/*  86 */       for (EnumType blockstoneslab$enumtype : EnumType.values()) {
/*     */         
/*  88 */         if (blockstoneslab$enumtype != EnumType.WOOD)
/*     */         {
/*  90 */           list.add(new ItemStack(itemIn, 1, blockstoneslab$enumtype.getMetadata()));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 101 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta & 0x7));
/*     */     
/* 103 */     if (isDouble()) {
/*     */       
/* 105 */       iblockstate = iblockstate.withProperty((IProperty)SEAMLESS, Boolean.valueOf(((meta & 0x8) != 0)));
/*     */     }
/*     */     else {
/*     */       
/* 109 */       iblockstate = iblockstate.withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
/*     */     } 
/*     */     
/* 112 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 120 */     int i = 0;
/* 121 */     i |= ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */     
/* 123 */     if (isDouble()) {
/*     */       
/* 125 */       if (((Boolean)state.getValue((IProperty)SEAMLESS)).booleanValue())
/*     */       {
/* 127 */         i |= 0x8;
/*     */       }
/*     */     }
/* 130 */     else if (state.getValue((IProperty)HALF) == BlockSlab.EnumBlockHalf.TOP) {
/*     */       
/* 132 */       i |= 0x8;
/*     */     } 
/*     */     
/* 135 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 140 */     return isDouble() ? new BlockState(this, new IProperty[] { (IProperty)SEAMLESS, (IProperty)VARIANT }) : new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 149 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/* 157 */     return ((EnumType)state.getValue((IProperty)VARIANT)).func_181074_c();
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 162 */     STONE(0, MapColor.stoneColor, "stone"),
/* 163 */     SAND(1, MapColor.sandColor, "sandstone", (MapColor)"sand"),
/* 164 */     WOOD(2, MapColor.woodColor, "wood_old", (MapColor)"wood"),
/* 165 */     COBBLESTONE(3, MapColor.stoneColor, "cobblestone", (MapColor)"cobble"),
/* 166 */     BRICK(4, MapColor.redColor, "brick"),
/* 167 */     SMOOTHBRICK(5, MapColor.stoneColor, "stone_brick", (MapColor)"smoothStoneBrick"),
/* 168 */     NETHERBRICK(6, MapColor.netherrackColor, "nether_brick", (MapColor)"netherBrick"),
/* 169 */     QUARTZ(7, MapColor.quartzColor, "quartz");
/*     */     
/* 171 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
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
/*     */     private final int meta;
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
/*     */     private final MapColor field_181075_k;
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
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String unlocalizedName;
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
/*     */     static {
/* 226 */       for (EnumType blockstoneslab$enumtype : values())
/*     */       {
/* 228 */         META_LOOKUP[blockstoneslab$enumtype.getMetadata()] = blockstoneslab$enumtype;
/*     */       }
/*     */     }
/*     */     
/*     */     EnumType(int p_i46382_3_, MapColor p_i46382_4_, String p_i46382_5_, String p_i46382_6_) {
/*     */       this.meta = p_i46382_3_;
/*     */       this.field_181075_k = p_i46382_4_;
/*     */       this.name = p_i46382_5_;
/*     */       this.unlocalizedName = p_i46382_6_;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public MapColor func_181074_c() {
/*     */       return this.field_181075_k;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockStoneSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */