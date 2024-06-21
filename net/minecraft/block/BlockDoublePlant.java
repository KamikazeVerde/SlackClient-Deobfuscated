/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public class BlockDoublePlant
/*     */   extends BlockBush implements IGrowable {
/*  28 */   public static final PropertyEnum<EnumPlantType> VARIANT = PropertyEnum.create("variant", EnumPlantType.class);
/*  29 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*  30 */   public static final PropertyEnum<EnumFacing> field_181084_N = (PropertyEnum<EnumFacing>)BlockDirectional.FACING;
/*     */ 
/*     */   
/*     */   public BlockDoublePlant() {
/*  34 */     super(Material.vine);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumPlantType.SUNFLOWER).withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)field_181084_N, (Comparable)EnumFacing.NORTH));
/*  36 */     setHardness(0.0F);
/*  37 */     setStepSound(soundTypeGrass);
/*  38 */     setUnlocalizedName("doublePlant");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  43 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPlantType getVariant(IBlockAccess worldIn, BlockPos pos) {
/*  48 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  50 */     if (iblockstate.getBlock() == this) {
/*     */       
/*  52 */       iblockstate = getActualState(iblockstate, worldIn, pos);
/*  53 */       return (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */     } 
/*     */ 
/*     */     
/*  57 */     return EnumPlantType.FERN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  63 */     return (super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  71 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  73 */     if (iblockstate.getBlock() != this)
/*     */     {
/*  75 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  79 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)getActualState(iblockstate, (IBlockAccess)worldIn, pos).getValue((IProperty)VARIANT);
/*  80 */     return (blockdoubleplant$enumplanttype == EnumPlantType.FERN || blockdoubleplant$enumplanttype == EnumPlantType.GRASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  86 */     if (!canBlockStay(worldIn, pos, state)) {
/*     */       
/*  88 */       boolean flag = (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER);
/*  89 */       BlockPos blockpos = flag ? pos : pos.up();
/*  90 */       BlockPos blockpos1 = flag ? pos.down() : pos;
/*  91 */       Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
/*  92 */       Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;
/*     */       
/*  94 */       if (block == this)
/*     */       {
/*  96 */         worldIn.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/*     */       }
/*     */       
/*  99 */       if (block1 == this) {
/*     */         
/* 101 */         worldIn.setBlockState(blockpos1, Blocks.air.getDefaultState(), 3);
/*     */         
/* 103 */         if (!flag)
/*     */         {
/* 105 */           dropBlockAsItem(worldIn, blockpos1, state, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 113 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER)
/*     */     {
/* 115 */       return (worldIn.getBlockState(pos.down()).getBlock() == this);
/*     */     }
/*     */ 
/*     */     
/* 119 */     IBlockState iblockstate = worldIn.getBlockState(pos.up());
/* 120 */     return (iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 131 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER)
/*     */     {
/* 133 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 137 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/* 138 */     return (blockdoubleplant$enumplanttype == EnumPlantType.FERN) ? null : ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? ((rand.nextInt(8) == 0) ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 148 */     return (state.getValue((IProperty)HALF) != EnumBlockHalf.UPPER && state.getValue((IProperty)VARIANT) != EnumPlantType.GRASS) ? ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 153 */     EnumPlantType blockdoubleplant$enumplanttype = getVariant(worldIn, pos);
/* 154 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN) ? 16777215 : BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeAt(World worldIn, BlockPos lowerPos, EnumPlantType variant, int flags) {
/* 159 */     worldIn.setBlockState(lowerPos, getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, variant), flags);
/* 160 */     worldIn.setBlockState(lowerPos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 168 */     worldIn.setBlockState(pos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 173 */     if (worldIn.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.shears || state.getValue((IProperty)HALF) != EnumBlockHalf.LOWER || !onHarvest(worldIn, pos, state, player))
/*     */     {
/* 175 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 181 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*     */       
/* 183 */       if (worldIn.getBlockState(pos.down()).getBlock() == this)
/*     */       {
/* 185 */         if (!player.capabilities.isCreativeMode) {
/*     */           
/* 187 */           IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 188 */           EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */           
/* 190 */           if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS) {
/*     */             
/* 192 */             worldIn.destroyBlock(pos.down(), true);
/*     */           }
/* 194 */           else if (!worldIn.isRemote) {
/*     */             
/* 196 */             if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears)
/*     */             {
/* 198 */               onHarvest(worldIn, pos, iblockstate, player);
/* 199 */               worldIn.setBlockToAir(pos.down());
/*     */             }
/*     */             else
/*     */             {
/* 203 */               worldIn.destroyBlock(pos.down(), true);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 208 */             worldIn.setBlockToAir(pos.down());
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 213 */           worldIn.setBlockToAir(pos.down());
/*     */         }
/*     */       
/*     */       }
/* 217 */     } else if (player.capabilities.isCreativeMode && worldIn.getBlockState(pos.up()).getBlock() == this) {
/*     */       
/* 219 */       worldIn.setBlockState(pos.up(), Blocks.air.getDefaultState(), 2);
/*     */     } 
/*     */     
/* 222 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onHarvest(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 227 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/*     */     
/* 229 */     if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS)
/*     */     {
/* 231 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 235 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 236 */     int i = ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).getMeta();
/* 237 */     spawnAsEntity(worldIn, pos, new ItemStack(Blocks.tallgrass, 2, i));
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 247 */     for (EnumPlantType blockdoubleplant$enumplanttype : EnumPlantType.values())
/*     */     {
/* 249 */       list.add(new ItemStack(itemIn, 1, blockdoubleplant$enumplanttype.getMeta()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 255 */     return getVariant((IBlockAccess)worldIn, pos).getMeta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 263 */     EnumPlantType blockdoubleplant$enumplanttype = getVariant((IBlockAccess)worldIn, pos);
/* 264 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 274 */     spawnAsEntity(worldIn, pos, new ItemStack(this, 1, getVariant((IBlockAccess)worldIn, pos).getMeta()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 282 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER) : getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, EnumPlantType.byMetadata(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 291 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*     */       
/* 293 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */       
/* 295 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 297 */         state = state.withProperty((IProperty)VARIANT, iblockstate.getValue((IProperty)VARIANT));
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 309 */     return (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) ? (0x8 | ((EnumFacing)state.getValue((IProperty)field_181084_N)).getHorizontalIndex()) : ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 314 */     return new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT, (IProperty)field_181084_N });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block.EnumOffsetType getOffsetType() {
/* 322 */     return Block.EnumOffsetType.XZ;
/*     */   }
/*     */   
/*     */   public enum EnumBlockHalf
/*     */     implements IStringSerializable {
/* 327 */     UPPER,
/* 328 */     LOWER;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 332 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 337 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumPlantType
/*     */     implements IStringSerializable {
/* 343 */     SUNFLOWER(0, "sunflower"),
/* 344 */     SYRINGA(1, "syringa"),
/* 345 */     GRASS(2, "double_grass", "grass"),
/* 346 */     FERN(3, "double_fern", "fern"),
/* 347 */     ROSE(4, "double_rose", "rose"),
/* 348 */     PAEONIA(5, "paeonia");
/*     */     
/* 350 */     private static final EnumPlantType[] META_LOOKUP = new EnumPlantType[(values()).length];
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
/*     */     
/*     */     static {
/* 398 */       for (EnumPlantType blockdoubleplant$enumplanttype : values())
/*     */       {
/* 400 */         META_LOOKUP[blockdoubleplant$enumplanttype.getMeta()] = blockdoubleplant$enumplanttype;
/*     */       }
/*     */     }
/*     */     
/*     */     EnumPlantType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMeta() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumPlantType byMetadata(int meta) {
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockDoublePlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */