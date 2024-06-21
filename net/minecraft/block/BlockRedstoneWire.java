/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneWire
/*     */   extends Block {
/*  30 */   public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
/*  31 */   public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
/*  32 */   public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
/*  33 */   public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
/*  34 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*     */   private boolean canProvidePower = true;
/*  36 */   private final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   public BlockRedstoneWire() {
/*  40 */     super(Material.circuits);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, EnumAttachPosition.NONE).withProperty((IProperty)EAST, EnumAttachPosition.NONE).withProperty((IProperty)SOUTH, EnumAttachPosition.NONE).withProperty((IProperty)WEST, EnumAttachPosition.NONE).withProperty((IProperty)POWER, Integer.valueOf(0)));
/*  42 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  51 */     state = state.withProperty((IProperty)WEST, getAttachPosition(worldIn, pos, EnumFacing.WEST));
/*  52 */     state = state.withProperty((IProperty)EAST, getAttachPosition(worldIn, pos, EnumFacing.EAST));
/*  53 */     state = state.withProperty((IProperty)NORTH, getAttachPosition(worldIn, pos, EnumFacing.NORTH));
/*  54 */     state = state.withProperty((IProperty)SOUTH, getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
/*  55 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
/*  60 */     BlockPos blockpos = pos.offset(direction);
/*  61 */     Block block = worldIn.getBlockState(pos.offset(direction)).getBlock();
/*     */     
/*  63 */     if (!canConnectTo(worldIn.getBlockState(blockpos), direction) && (block.isBlockNormalCube() || !canConnectUpwardsTo(worldIn.getBlockState(blockpos.down())))) {
/*     */       
/*  65 */       Block block1 = worldIn.getBlockState(pos.up()).getBlock();
/*  66 */       return (!block1.isBlockNormalCube() && block.isBlockNormalCube() && canConnectUpwardsTo(worldIn.getBlockState(blockpos.up()))) ? EnumAttachPosition.UP : EnumAttachPosition.NONE;
/*     */     } 
/*     */ 
/*     */     
/*  70 */     return EnumAttachPosition.SIDE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  94 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  95 */     return (iblockstate.getBlock() != this) ? super.colorMultiplier(worldIn, pos, renderPass) : colorMultiplier(((Integer)iblockstate.getValue((IProperty)POWER)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 100 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) || worldIn.getBlockState(pos.down()).getBlock() == Blocks.glowstone);
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState updateSurroundingRedstone(World worldIn, BlockPos pos, IBlockState state) {
/* 105 */     state = calculateCurrentChanges(worldIn, pos, pos, state);
/* 106 */     List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
/* 107 */     this.blocksNeedingUpdate.clear();
/*     */     
/* 109 */     for (BlockPos blockpos : list)
/*     */     {
/* 111 */       worldIn.notifyNeighborsOfStateChange(blockpos, this);
/*     */     }
/*     */     
/* 114 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState calculateCurrentChanges(World worldIn, BlockPos pos1, BlockPos pos2, IBlockState state) {
/* 119 */     IBlockState iblockstate = state;
/* 120 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/* 121 */     int j = 0;
/* 122 */     j = getMaxCurrentStrength(worldIn, pos2, j);
/* 123 */     this.canProvidePower = false;
/* 124 */     int k = worldIn.isBlockIndirectlyGettingPowered(pos1);
/* 125 */     this.canProvidePower = true;
/*     */     
/* 127 */     if (k > 0 && k > j - 1)
/*     */     {
/* 129 */       j = k;
/*     */     }
/*     */     
/* 132 */     int l = 0;
/*     */     
/* 134 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 136 */       BlockPos blockpos = pos1.offset(enumfacing);
/* 137 */       boolean flag = (blockpos.getX() != pos2.getX() || blockpos.getZ() != pos2.getZ());
/*     */       
/* 139 */       if (flag)
/*     */       {
/* 141 */         l = getMaxCurrentStrength(worldIn, blockpos, l);
/*     */       }
/*     */       
/* 144 */       if (worldIn.getBlockState(blockpos).getBlock().isNormalCube() && !worldIn.getBlockState(pos1.up()).getBlock().isNormalCube()) {
/*     */         
/* 146 */         if (flag && pos1.getY() >= pos2.getY())
/*     */         {
/* 148 */           l = getMaxCurrentStrength(worldIn, blockpos.up(), l); } 
/*     */         continue;
/*     */       } 
/* 151 */       if (!worldIn.getBlockState(blockpos).getBlock().isNormalCube() && flag && pos1.getY() <= pos2.getY())
/*     */       {
/* 153 */         l = getMaxCurrentStrength(worldIn, blockpos.down(), l);
/*     */       }
/*     */     } 
/*     */     
/* 157 */     if (l > j) {
/*     */       
/* 159 */       j = l - 1;
/*     */     }
/* 161 */     else if (j > 0) {
/*     */       
/* 163 */       j--;
/*     */     }
/*     */     else {
/*     */       
/* 167 */       j = 0;
/*     */     } 
/*     */     
/* 170 */     if (k > j - 1)
/*     */     {
/* 172 */       j = k;
/*     */     }
/*     */     
/* 175 */     if (i != j) {
/*     */       
/* 177 */       state = state.withProperty((IProperty)POWER, Integer.valueOf(j));
/*     */       
/* 179 */       if (worldIn.getBlockState(pos1) == iblockstate)
/*     */       {
/* 181 */         worldIn.setBlockState(pos1, state, 2);
/*     */       }
/*     */       
/* 184 */       this.blocksNeedingUpdate.add(pos1);
/*     */       
/* 186 */       for (EnumFacing enumfacing1 : EnumFacing.values())
/*     */       {
/* 188 */         this.blocksNeedingUpdate.add(pos1.offset(enumfacing1));
/*     */       }
/*     */     } 
/*     */     
/* 192 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
/* 201 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 203 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */       
/* 205 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 207 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 214 */     if (!worldIn.isRemote) {
/*     */       
/* 216 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 218 */       for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL)
/*     */       {
/* 220 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */       
/* 223 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/* 225 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 228 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 230 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 232 */         if (worldIn.getBlockState(blockpos).getBlock().isNormalCube()) {
/*     */           
/* 234 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
/*     */           
/*     */           continue;
/*     */         } 
/* 238 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 246 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 248 */     if (!worldIn.isRemote) {
/*     */       
/* 250 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 252 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */       
/* 255 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 257 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/* 259 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 262 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 264 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 266 */         if (worldIn.getBlockState(blockpos).getBlock().isNormalCube()) {
/*     */           
/* 268 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
/*     */           
/*     */           continue;
/*     */         } 
/* 272 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getMaxCurrentStrength(World worldIn, BlockPos pos, int strength) {
/* 280 */     if (worldIn.getBlockState(pos).getBlock() != this)
/*     */     {
/* 282 */       return strength;
/*     */     }
/*     */ 
/*     */     
/* 286 */     int i = ((Integer)worldIn.getBlockState(pos).getValue((IProperty)POWER)).intValue();
/* 287 */     return (i > strength) ? i : strength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 296 */     if (!worldIn.isRemote)
/*     */     {
/* 298 */       if (canPlaceBlockAt(worldIn, pos)) {
/*     */         
/* 300 */         updateSurroundingRedstone(worldIn, pos, state);
/*     */       }
/*     */       else {
/*     */         
/* 304 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 305 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 317 */     return Items.redstone;
/*     */   }
/*     */ 
/*     */   
/*     */   public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 322 */     return !this.canProvidePower ? 0 : isProvidingWeakPower(worldIn, pos, state, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 327 */     if (!this.canProvidePower)
/*     */     {
/* 329 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 333 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */     
/* 335 */     if (i == 0)
/*     */     {
/* 337 */       return 0;
/*     */     }
/* 339 */     if (side == EnumFacing.UP)
/*     */     {
/* 341 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 345 */     EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 347 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 349 */       if (func_176339_d(worldIn, pos, enumfacing))
/*     */       {
/* 351 */         enumset.add(enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 355 */     if (side.getAxis().isHorizontal() && enumset.isEmpty())
/*     */     {
/* 357 */       return i;
/*     */     }
/* 359 */     if (enumset.contains(side) && !enumset.contains(side.rotateYCCW()) && !enumset.contains(side.rotateY()))
/*     */     {
/* 361 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 365 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_176339_d(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 373 */     BlockPos blockpos = pos.offset(side);
/* 374 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 375 */     Block block = iblockstate.getBlock();
/* 376 */     boolean flag = block.isNormalCube();
/* 377 */     boolean flag1 = worldIn.getBlockState(pos.up()).getBlock().isNormalCube();
/* 378 */     return (!flag1 && flag && canConnectUpwardsTo(worldIn, blockpos.up())) ? true : (canConnectTo(iblockstate, side) ? true : ((block == Blocks.powered_repeater && iblockstate.getValue((IProperty)BlockRedstoneDiode.FACING) == side) ? true : ((!flag && canConnectUpwardsTo(worldIn, blockpos.down())))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
/* 383 */     return canConnectUpwardsTo(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockState state) {
/* 388 */     return canConnectTo(state, (EnumFacing)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectTo(IBlockState blockState, EnumFacing side) {
/* 393 */     Block block = blockState.getBlock();
/*     */     
/* 395 */     if (block == Blocks.redstone_wire)
/*     */     {
/* 397 */       return true;
/*     */     }
/* 399 */     if (Blocks.unpowered_repeater.isAssociated(block)) {
/*     */       
/* 401 */       EnumFacing enumfacing = (EnumFacing)blockState.getValue((IProperty)BlockRedstoneRepeater.FACING);
/* 402 */       return (enumfacing == side || enumfacing.getOpposite() == side);
/*     */     } 
/*     */ 
/*     */     
/* 406 */     return (block.canProvidePower() && side != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 415 */     return this.canProvidePower;
/*     */   }
/*     */ 
/*     */   
/*     */   private int colorMultiplier(int powerLevel) {
/* 420 */     float f = powerLevel / 15.0F;
/* 421 */     float f1 = f * 0.6F + 0.4F;
/*     */     
/* 423 */     if (powerLevel == 0)
/*     */     {
/* 425 */       f1 = 0.3F;
/*     */     }
/*     */     
/* 428 */     float f2 = f * f * 0.7F - 0.5F;
/* 429 */     float f3 = f * f * 0.6F - 0.7F;
/*     */     
/* 431 */     if (f2 < 0.0F)
/*     */     {
/* 433 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 436 */     if (f3 < 0.0F)
/*     */     {
/* 438 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 441 */     int i = MathHelper.clamp_int((int)(f1 * 255.0F), 0, 255);
/* 442 */     int j = MathHelper.clamp_int((int)(f2 * 255.0F), 0, 255);
/* 443 */     int k = MathHelper.clamp_int((int)(f3 * 255.0F), 0, 255);
/* 444 */     return 0xFF000000 | i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 449 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */     
/* 451 */     if (i != 0) {
/*     */       
/* 453 */       double d0 = pos.getX() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 454 */       double d1 = (pos.getY() + 0.0625F);
/* 455 */       double d2 = pos.getZ() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 456 */       float f = i / 15.0F;
/* 457 */       float f1 = f * 0.6F + 0.4F;
/* 458 */       float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
/* 459 */       float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
/* 460 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, f1, f2, f3, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 469 */     return Items.redstone;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 474 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 482 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 490 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 495 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)POWER });
/*     */   }
/*     */   
/*     */   enum EnumAttachPosition
/*     */     implements IStringSerializable {
/* 500 */     UP("up"),
/* 501 */     SIDE("side"),
/* 502 */     NONE("none");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumAttachPosition(String name) {
/* 508 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 513 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 518 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockRedstoneWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */