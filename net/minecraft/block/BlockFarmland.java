/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockFarmland
/*     */   extends Block
/*     */ {
/*  22 */   public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
/*     */ 
/*     */   
/*     */   protected BlockFarmland() {
/*  26 */     super(Material.ground);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)MOISTURE, Integer.valueOf(0)));
/*  28 */     setTickRandomly(true);
/*  29 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  30 */     setLightOpacity(255);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  35 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  53 */     int i = ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */     
/*  55 */     if (!hasWater(worldIn, pos) && !worldIn.canLightningStrike(pos.up())) {
/*     */       
/*  57 */       if (i > 0)
/*     */       {
/*  59 */         worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(i - 1)), 2);
/*     */       }
/*  61 */       else if (!hasCrops(worldIn, pos))
/*     */       {
/*  63 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       }
/*     */     
/*  66 */     } else if (i < 7) {
/*     */       
/*  68 */       worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(7)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/*  79 */     if (entityIn instanceof net.minecraft.entity.EntityLivingBase) {
/*     */       
/*  81 */       if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F) {
/*     */         
/*  83 */         if (!(entityIn instanceof net.minecraft.entity.player.EntityPlayer) && !worldIn.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  88 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       } 
/*     */       
/*  91 */       super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasCrops(World worldIn, BlockPos pos) {
/*  97 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  98 */     return (block instanceof BlockCrops || block instanceof BlockStem);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasWater(World worldIn, BlockPos pos) {
/* 103 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
/*     */       
/* 105 */       if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial() == Material.water)
/*     */       {
/* 107 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 119 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     
/* 121 */     if (worldIn.getBlockState(pos.up()).getBlock().getMaterial().isSolid())
/*     */     {
/* 123 */       worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*     */     Block block;
/* 129 */     switch (side) {
/*     */       
/*     */       case UP:
/* 132 */         return true;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/* 138 */         block = worldIn.getBlockState(pos).getBlock();
/* 139 */         return (!block.isOpaqueCube() && block != Blocks.farmland);
/*     */     } 
/*     */     
/* 142 */     return super.shouldSideBeRendered(worldIn, pos, side);
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
/* 153 */     return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 161 */     return Item.getItemFromBlock(Blocks.dirt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 169 */     return getDefaultState().withProperty((IProperty)MOISTURE, Integer.valueOf(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 177 */     return ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 182 */     return new BlockState(this, new IProperty[] { (IProperty)MOISTURE });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockFarmland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */