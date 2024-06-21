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
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockStoneSlabNew
/*     */   extends BlockSlab {
/*  23 */   public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
/*  24 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */ 
/*     */   
/*     */   public BlockStoneSlabNew() {
/*  28 */     super(Material.rock);
/*  29 */     IBlockState iblockstate = this.blockState.getBaseState();
/*     */     
/*  31 */     if (isDouble()) {
/*     */       
/*  33 */       iblockstate = iblockstate.withProperty((IProperty)SEAMLESS, Boolean.FALSE);
/*     */     }
/*     */     else {
/*     */       
/*  37 */       iblockstate = iblockstate.withProperty((IProperty)HALF, BlockSlab.EnumBlockHalf.BOTTOM);
/*     */     } 
/*     */     
/*  40 */     setDefaultState(iblockstate.withProperty((IProperty)VARIANT, EnumType.RED_SANDSTONE));
/*  41 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  49 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".red_sandstone.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  59 */     return Item.getItemFromBlock(Blocks.stone_slab2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  67 */     return Item.getItemFromBlock(Blocks.stone_slab2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(int meta) {
/*  75 */     return getUnlocalizedName() + "." + EnumType.byMetadata(meta).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<?> getVariantProperty() {
/*  80 */     return (IProperty<?>)VARIANT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getVariant(ItemStack stack) {
/*  85 */     return EnumType.byMetadata(stack.getMetadata() & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  93 */     if (itemIn != Item.getItemFromBlock(Blocks.double_stone_slab2))
/*     */     {
/*  95 */       for (EnumType blockstoneslabnew$enumtype : EnumType.values())
/*     */       {
/*  97 */         list.add(new ItemStack(itemIn, 1, blockstoneslabnew$enumtype.getMetadata()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 107 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta & 0x7));
/*     */     
/* 109 */     if (isDouble()) {
/*     */       
/* 111 */       iblockstate = iblockstate.withProperty((IProperty)SEAMLESS, Boolean.valueOf(((meta & 0x8) != 0)));
/*     */     }
/*     */     else {
/*     */       
/* 115 */       iblockstate = iblockstate.withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
/*     */     } 
/*     */     
/* 118 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 126 */     int i = 0;
/* 127 */     i |= ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */     
/* 129 */     if (isDouble()) {
/*     */       
/* 131 */       if (((Boolean)state.getValue((IProperty)SEAMLESS)).booleanValue())
/*     */       {
/* 133 */         i |= 0x8;
/*     */       }
/*     */     }
/* 136 */     else if (state.getValue((IProperty)HALF) == BlockSlab.EnumBlockHalf.TOP) {
/*     */       
/* 138 */       i |= 0x8;
/*     */     } 
/*     */     
/* 141 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 146 */     return isDouble() ? new BlockState(this, new IProperty[] { (IProperty)SEAMLESS, (IProperty)VARIANT }) : new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/* 154 */     return ((EnumType)state.getValue((IProperty)VARIANT)).func_181068_c();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 163 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 168 */     RED_SANDSTONE(0, "red_sandstone", BlockSand.EnumType.RED_SAND.getMapColor());
/*     */     private final MapColor field_181069_e;
/* 170 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 218 */       for (EnumType blockstoneslabnew$enumtype : values())
/*     */       {
/* 220 */         META_LOOKUP[blockstoneslabnew$enumtype.getMetadata()] = blockstoneslabnew$enumtype;
/*     */       }
/*     */     }
/*     */     
/*     */     EnumType(int p_i46391_3_, String p_i46391_4_, MapColor p_i46391_5_) {
/*     */       this.meta = p_i46391_3_;
/*     */       this.name = p_i46391_4_;
/*     */       this.field_181069_e = p_i46391_5_;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public MapColor func_181068_c() {
/*     */       return this.field_181069_e;
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
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockStoneSlabNew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */