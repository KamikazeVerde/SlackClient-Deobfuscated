/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockHopper extends BlockContainer {
/*  31 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(EnumFacing p_apply_1_)
/*     */         {
/*  35 */           return (p_apply_1_ != EnumFacing.UP);
/*     */         }
/*     */       });
/*  38 */   public static final PropertyBool ENABLED = PropertyBool.create("enabled");
/*     */ 
/*     */   
/*     */   public BlockHopper() {
/*  42 */     super(Material.iron, MapColor.stoneColor);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)ENABLED, Boolean.TRUE));
/*  44 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  45 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  50 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  60 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  61 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  62 */     float f = 0.125F;
/*  63 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  64 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  65 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  66 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  67 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  68 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  69 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  70 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  71 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  80 */     EnumFacing enumfacing = facing.getOpposite();
/*     */     
/*  82 */     if (enumfacing == EnumFacing.UP)
/*     */     {
/*  84 */       enumfacing = EnumFacing.DOWN;
/*     */     }
/*     */     
/*  87 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)ENABLED, Boolean.TRUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  95 */     return (TileEntity)new TileEntityHopper();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 103 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/* 105 */     if (stack.hasDisplayName()) {
/*     */       
/* 107 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 109 */       if (tileentity instanceof TileEntityHopper)
/*     */       {
/* 111 */         ((TileEntityHopper)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 118 */     updateState(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 123 */     if (worldIn.isRemote)
/*     */     {
/* 125 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 129 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 131 */     if (tileentity instanceof TileEntityHopper) {
/*     */       
/* 133 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 134 */       playerIn.triggerAchievement(StatList.field_181732_P);
/*     */     } 
/*     */     
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 146 */     updateState(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 151 */     boolean flag = !worldIn.isBlockPowered(pos);
/*     */     
/* 153 */     if (flag != ((Boolean)state.getValue((IProperty)ENABLED)).booleanValue())
/*     */     {
/* 155 */       worldIn.setBlockState(pos, state.withProperty((IProperty)ENABLED, Boolean.valueOf(flag)), 4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 161 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 163 */     if (tileentity instanceof TileEntityHopper) {
/*     */       
/* 165 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 166 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 169 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 177 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 200 */     return EnumFacing.getFront(meta & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled(int meta) {
/* 209 */     return ((meta & 0x8) != 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 219 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 224 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 232 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)ENABLED, Boolean.valueOf(isEnabled(meta)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 240 */     int i = 0;
/* 241 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 243 */     if (!((Boolean)state.getValue((IProperty)ENABLED)).booleanValue())
/*     */     {
/* 245 */       i |= 0x8;
/*     */     }
/*     */     
/* 248 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 253 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)ENABLED });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */