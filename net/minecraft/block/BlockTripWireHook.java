/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWireHook extends Block {
/*  24 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  25 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  26 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  27 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*     */ 
/*     */   
/*     */   public BlockTripWireHook() {
/*  31 */     super(Material.circuits);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.FALSE).withProperty((IProperty)ATTACHED, Boolean.FALSE).withProperty((IProperty)SUSPENDED, Boolean.FALSE));
/*  33 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  34 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  43 */     return state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  69 */     return (side.getAxis().isHorizontal() && worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  74 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  76 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
/*     */       {
/*  78 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  91 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.FALSE).withProperty((IProperty)ATTACHED, Boolean.FALSE).withProperty((IProperty)SUSPENDED, Boolean.FALSE);
/*     */     
/*  93 */     if (facing.getAxis().isHorizontal())
/*     */     {
/*  95 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */     
/*  98 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 106 */     func_176260_a(worldIn, pos, state, false, false, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 114 */     if (neighborBlock != this)
/*     */     {
/* 116 */       if (checkForDrop(worldIn, pos, state)) {
/*     */         
/* 118 */         EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */         
/* 120 */         if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().isNormalCube()) {
/*     */           
/* 122 */           dropBlockAsItem(worldIn, pos, state, 0);
/* 123 */           worldIn.setBlockToAir(pos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_176260_a(World worldIn, BlockPos pos, IBlockState hookState, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, IBlockState p_176260_7_) {
/*     */     int k, m;
/* 131 */     EnumFacing enumfacing = (EnumFacing)hookState.getValue((IProperty)FACING);
/* 132 */     int flag = ((Boolean)hookState.getValue((IProperty)ATTACHED)).booleanValue();
/* 133 */     boolean flag1 = ((Boolean)hookState.getValue((IProperty)POWERED)).booleanValue();
/* 134 */     boolean flag2 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/* 135 */     boolean flag3 = !p_176260_4_;
/* 136 */     boolean flag4 = false;
/* 137 */     int i = 0;
/* 138 */     IBlockState[] aiblockstate = new IBlockState[42];
/*     */     
/* 140 */     for (int j = 1; j < 42; j++) {
/*     */       
/* 142 */       BlockPos blockpos = pos.offset(enumfacing, j);
/* 143 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 145 */       if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/*     */         
/* 147 */         if (iblockstate.getValue((IProperty)FACING) == enumfacing.getOpposite())
/*     */         {
/* 149 */           i = j;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 155 */       if (iblockstate.getBlock() != Blocks.tripwire && j != p_176260_6_) {
/*     */         
/* 157 */         aiblockstate[j] = null;
/* 158 */         flag3 = false;
/*     */       }
/*     */       else {
/*     */         
/* 162 */         if (j == p_176260_6_)
/*     */         {
/* 164 */           iblockstate = (IBlockState)Objects.firstNonNull(p_176260_7_, iblockstate);
/*     */         }
/*     */         
/* 167 */         int flag5 = !((Boolean)iblockstate.getValue((IProperty)BlockTripWire.DISARMED)).booleanValue() ? 1 : 0;
/* 168 */         boolean flag6 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.POWERED)).booleanValue();
/* 169 */         boolean flag7 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.SUSPENDED)).booleanValue();
/* 170 */         int n = flag3 & ((flag7 == flag2) ? 1 : 0);
/* 171 */         m = flag4 | ((flag5 && flag6) ? 1 : 0);
/* 172 */         aiblockstate[j] = iblockstate;
/*     */         
/* 174 */         if (j == p_176260_6_) {
/*     */           
/* 176 */           worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 177 */           k = n & flag5;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     k &= (i > 1) ? 1 : 0;
/* 183 */     m &= k;
/* 184 */     IBlockState iblockstate1 = getDefaultState().withProperty((IProperty)ATTACHED, Boolean.valueOf(k)).withProperty((IProperty)POWERED, Boolean.valueOf(m));
/*     */     
/* 186 */     if (i > 0) {
/*     */       
/* 188 */       BlockPos blockpos1 = pos.offset(enumfacing, i);
/* 189 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 190 */       worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing1), 3);
/* 191 */       func_176262_b(worldIn, blockpos1, enumfacing1);
/* 192 */       func_180694_a(worldIn, blockpos1, k, m, flag, flag1);
/*     */     } 
/*     */     
/* 195 */     func_180694_a(worldIn, pos, k, m, flag, flag1);
/*     */     
/* 197 */     if (!p_176260_4_) {
/*     */       
/* 199 */       worldIn.setBlockState(pos, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing), 3);
/*     */       
/* 201 */       if (p_176260_5_)
/*     */       {
/* 203 */         func_176262_b(worldIn, pos, enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 207 */     if (flag != k)
/*     */     {
/* 209 */       for (int n = 1; n < i; n++) {
/*     */         
/* 211 */         BlockPos blockpos2 = pos.offset(enumfacing, n);
/* 212 */         IBlockState iblockstate2 = aiblockstate[n];
/*     */         
/* 214 */         if (iblockstate2 != null && worldIn.getBlockState(blockpos2).getBlock() != Blocks.air)
/*     */         {
/* 216 */           worldIn.setBlockState(blockpos2, iblockstate2.withProperty((IProperty)ATTACHED, Boolean.valueOf(k)), 3);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 231 */     func_176260_a(worldIn, pos, state, false, true, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_180694_a(World worldIn, BlockPos pos, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
/* 236 */     if (p_180694_4_ && !p_180694_6_) {
/*     */       
/* 238 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
/*     */     }
/* 240 */     else if (!p_180694_4_ && p_180694_6_) {
/*     */       
/* 242 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
/*     */     }
/* 244 */     else if (p_180694_3_ && !p_180694_5_) {
/*     */       
/* 246 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
/*     */     }
/* 248 */     else if (!p_180694_3_ && p_180694_5_) {
/*     */       
/* 250 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (worldIn.rand.nextFloat() * 0.2F + 0.9F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_176262_b(World worldIn, BlockPos p_176262_2_, EnumFacing p_176262_3_) {
/* 256 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_, this);
/* 257 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_.offset(p_176262_3_.getOpposite()), this);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 262 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*     */       
/* 264 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 265 */       worldIn.setBlockToAir(pos);
/* 266 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 277 */     float f = 0.1875F;
/*     */     
/* 279 */     switch ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 282 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 286 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 290 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 294 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 300 */     boolean flag = ((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue();
/* 301 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 303 */     if (flag || flag1)
/*     */     {
/* 305 */       func_176260_a(worldIn, pos, state, true, false, -1, (IBlockState)null);
/*     */     }
/*     */     
/* 308 */     if (flag1) {
/*     */       
/* 310 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 311 */       worldIn.notifyNeighborsOfStateChange(pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite()), this);
/*     */     } 
/*     */     
/* 314 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 319 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 324 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 332 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 337 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 345 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 353 */     int i = 0;
/* 354 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 356 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 358 */       i |= 0x8;
/*     */     }
/*     */     
/* 361 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 363 */       i |= 0x4;
/*     */     }
/*     */     
/* 366 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 371 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED, (IProperty)ATTACHED, (IProperty)SUSPENDED });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockTripWireHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */