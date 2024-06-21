/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTorch extends Block {
/*  24 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(EnumFacing p_apply_1_)
/*     */         {
/*  28 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   protected BlockTorch() {
/*  34 */     super(Material.circuits);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  36 */     setTickRandomly(true);
/*  37 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(World worldIn, BlockPos pos) {
/*  60 */     if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos))
/*     */     {
/*  62 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  66 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  67 */     return (block instanceof BlockFence || block == Blocks.glass || block == Blocks.cobblestone_wall || block == Blocks.stained_glass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  73 */     for (EnumFacing enumfacing : FACING.getAllowedValues()) {
/*     */       
/*  75 */       if (canPlaceAt(worldIn, pos, enumfacing))
/*     */       {
/*  77 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
/*  86 */     BlockPos blockpos = pos.offset(facing.getOpposite());
/*  87 */     boolean flag = facing.getAxis().isHorizontal();
/*  88 */     return ((flag && worldIn.isBlockNormalCube(blockpos, true)) || (facing.equals(EnumFacing.UP) && canPlaceOn(worldIn, blockpos)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  97 */     if (canPlaceAt(worldIn, pos, facing))
/*     */     {
/*  99 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */ 
/*     */     
/* 103 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 105 */       if (worldIn.isBlockNormalCube(pos.offset(enumfacing.getOpposite()), true))
/*     */       {
/* 107 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 117 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 125 */     onNeighborChangeInternal(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
/* 130 */     if (!checkForDrop(worldIn, pos, state))
/*     */     {
/* 132 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 136 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 137 */     EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
/* 138 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 139 */     boolean flag = false;
/*     */     
/* 141 */     if (enumfacing$axis.isHorizontal() && !worldIn.isBlockNormalCube(pos.offset(enumfacing1), true)) {
/*     */       
/* 143 */       flag = true;
/*     */     }
/* 145 */     else if (enumfacing$axis.isVertical() && !canPlaceOn(worldIn, pos.offset(enumfacing1))) {
/*     */       
/* 147 */       flag = true;
/*     */     } 
/*     */     
/* 150 */     if (flag) {
/*     */       
/* 152 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 153 */       worldIn.setBlockToAir(pos);
/* 154 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 165 */     if (state.getBlock() == this && canPlaceAt(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING)))
/*     */     {
/* 167 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 171 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 173 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 174 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 189 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 190 */     float f = 0.15F;
/*     */     
/* 192 */     if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 194 */       setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */     }
/* 196 */     else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 198 */       setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */     }
/* 200 */     else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 202 */       setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */     }
/* 204 */     else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 206 */       setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 210 */       f = 0.1F;
/* 211 */       setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */     
/* 214 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 219 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 220 */     double d0 = pos.getX() + 0.5D;
/* 221 */     double d1 = pos.getY() + 0.7D;
/* 222 */     double d2 = pos.getZ() + 0.5D;
/* 223 */     double d3 = 0.22D;
/* 224 */     double d4 = 0.27D;
/*     */     
/* 226 */     if (enumfacing.getAxis().isHorizontal()) {
/*     */       
/* 228 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 229 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/* 230 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 234 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/* 235 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 241 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 249 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 251 */     switch (meta)
/*     */     
/*     */     { case 1:
/* 254 */         iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.EAST);
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
/* 274 */         return iblockstate;case 2: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.WEST); return iblockstate;case 3: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH); return iblockstate;case 4: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH); return iblockstate; }  iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP); return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 282 */     int i = 0;
/*     */     
/* 284 */     switch ((EnumFacing)state.getValue((IProperty)FACING))
/*     */     
/*     */     { case EAST:
/* 287 */         i |= 0x1;
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
/* 308 */         return i;case WEST: i |= 0x2; return i;case SOUTH: i |= 0x3; return i;case NORTH: i |= 0x4; return i; }  i |= 0x5; return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 313 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */