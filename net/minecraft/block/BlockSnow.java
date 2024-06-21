/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSnow
/*     */   extends Block {
/*  26 */   public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
/*     */ 
/*     */   
/*     */   protected BlockSnow() {
/*  30 */     super(Material.snow);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LAYERS, Integer.valueOf(1)));
/*  32 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  33 */     setTickRandomly(true);
/*  34 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  35 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  40 */     return (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LAYERS)).intValue() < 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  45 */     int i = ((Integer)state.getValue((IProperty)LAYERS)).intValue() - 1;
/*  46 */     float f = 0.125F;
/*  47 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, (pos.getY() + i * f), pos.getZ() + this.maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  68 */     getBoundsForLayers(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  73 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  74 */     getBoundsForLayers(((Integer)iblockstate.getValue((IProperty)LAYERS)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getBoundsForLayers(int p_150154_1_) {
/*  79 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, p_150154_1_ / 8.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  84 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*  85 */     Block block = iblockstate.getBlock();
/*  86 */     return (block != Blocks.ice && block != Blocks.packed_ice) ? ((block.getMaterial() == Material.leaves) ? true : ((block == this && ((Integer)iblockstate.getValue((IProperty)LAYERS)).intValue() >= 7) ? true : ((block.isOpaqueCube() && block.blockMaterial.blocksMovement())))) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  94 */     checkAndDropBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  99 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*     */       
/* 101 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 102 */       worldIn.setBlockToAir(pos);
/* 103 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 113 */     spawnAsEntity(worldIn, pos, new ItemStack(Items.snowball, ((Integer)state.getValue((IProperty)LAYERS)).intValue() + 1, 0));
/* 114 */     worldIn.setBlockToAir(pos);
/* 115 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 125 */     return Items.snowball;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 138 */     if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) {
/*     */       
/* 140 */       dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 141 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 147 */     return (side == EnumFacing.UP) ? true : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 155 */     return getDefaultState().withProperty((IProperty)LAYERS, Integer.valueOf((meta & 0x7) + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/* 163 */     return (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LAYERS)).intValue() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 171 */     return ((Integer)state.getValue((IProperty)LAYERS)).intValue() - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 176 */     return new BlockState(this, new IProperty[] { (IProperty)LAYERS });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */