/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDoor extends Block {
/*  28 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  29 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  30 */   public static final PropertyEnum<EnumHingePosition> HINGE = PropertyEnum.create("hinge", EnumHingePosition.class);
/*  31 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  32 */   public static final PropertyEnum<EnumDoorHalf> HALF = PropertyEnum.create("half", EnumDoorHalf.class);
/*     */ 
/*     */   
/*     */   protected BlockDoor(Material materialIn) {
/*  36 */     super(materialIn);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.FALSE).withProperty((IProperty)HINGE, EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.FALSE).withProperty((IProperty)HALF, EnumDoorHalf.LOWER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  45 */     return StatCollector.translateToLocal((getUnlocalizedName() + ".name").replaceAll("tile", "item"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  58 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  68 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  69 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  74 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  75 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  80 */     setBoundBasedOnMeta(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBoundBasedOnMeta(int combinedMeta) {
/*  85 */     float f = 0.1875F;
/*  86 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  87 */     EnumFacing enumfacing = getFacing(combinedMeta);
/*  88 */     boolean flag = isOpen(combinedMeta);
/*  89 */     boolean flag1 = isHingeLeft(combinedMeta);
/*     */     
/*  91 */     if (flag) {
/*     */       
/*  93 */       if (enumfacing == EnumFacing.EAST) {
/*     */         
/*  95 */         if (!flag1)
/*     */         {
/*  97 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         }
/*     */         else
/*     */         {
/* 101 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       
/* 104 */       } else if (enumfacing == EnumFacing.SOUTH) {
/*     */         
/* 106 */         if (!flag1)
/*     */         {
/* 108 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         else
/*     */         {
/* 112 */           setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         }
/*     */       
/* 115 */       } else if (enumfacing == EnumFacing.WEST) {
/*     */         
/* 117 */         if (!flag1)
/*     */         {
/* 119 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         else
/*     */         {
/* 123 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         }
/*     */       
/* 126 */       } else if (enumfacing == EnumFacing.NORTH) {
/*     */         
/* 128 */         if (!flag1)
/*     */         {
/* 130 */           setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         }
/*     */         else
/*     */         {
/* 134 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       
/*     */       } 
/* 138 */     } else if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 140 */       setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     }
/* 142 */     else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 144 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */     }
/* 146 */     else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 148 */       setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/* 150 */     else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 152 */       setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 158 */     if (this.blockMaterial == Material.iron)
/*     */     {
/* 160 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 164 */     BlockPos blockpos = (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 165 */     IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
/*     */     
/* 167 */     if (iblockstate.getBlock() != this)
/*     */     {
/* 169 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 173 */     state = iblockstate.cycleProperty((IProperty)OPEN);
/* 174 */     worldIn.setBlockState(blockpos, state, 2);
/* 175 */     worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 176 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
/* 184 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 186 */     if (iblockstate.getBlock() == this) {
/*     */       
/* 188 */       BlockPos blockpos = (iblockstate.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 189 */       IBlockState iblockstate1 = (pos == blockpos) ? iblockstate : worldIn.getBlockState(blockpos);
/*     */       
/* 191 */       if (iblockstate1.getBlock() == this && ((Boolean)iblockstate1.getValue((IProperty)OPEN)).booleanValue() != open) {
/*     */         
/* 193 */         worldIn.setBlockState(blockpos, iblockstate1.withProperty((IProperty)OPEN, Boolean.valueOf(open)), 2);
/* 194 */         worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 195 */         worldIn.playAuxSFXAtEntity(null, open ? 1003 : 1006, pos, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 205 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/*     */       
/* 207 */       BlockPos blockpos = pos.down();
/* 208 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 210 */       if (iblockstate.getBlock() != this)
/*     */       {
/* 212 */         worldIn.setBlockToAir(pos);
/*     */       }
/* 214 */       else if (neighborBlock != this)
/*     */       {
/* 216 */         onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 221 */       boolean flag1 = false;
/* 222 */       BlockPos blockpos1 = pos.up();
/* 223 */       IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */       
/* 225 */       if (iblockstate1.getBlock() != this) {
/*     */         
/* 227 */         worldIn.setBlockToAir(pos);
/* 228 */         flag1 = true;
/*     */       } 
/*     */       
/* 231 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*     */         
/* 233 */         worldIn.setBlockToAir(pos);
/* 234 */         flag1 = true;
/*     */         
/* 236 */         if (iblockstate1.getBlock() == this)
/*     */         {
/* 238 */           worldIn.setBlockToAir(blockpos1);
/*     */         }
/*     */       } 
/*     */       
/* 242 */       if (flag1) {
/*     */         
/* 244 */         if (!worldIn.isRemote)
/*     */         {
/* 246 */           dropBlockAsItem(worldIn, pos, state, 0);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 251 */         boolean flag = (worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1));
/*     */         
/* 253 */         if ((flag || neighborBlock.canProvidePower()) && neighborBlock != this && flag != ((Boolean)iblockstate1.getValue((IProperty)POWERED)).booleanValue()) {
/*     */           
/* 255 */           worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)POWERED, Boolean.valueOf(flag)), 2);
/*     */           
/* 257 */           if (flag != ((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/*     */             
/* 259 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 260 */             worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 261 */             worldIn.playAuxSFXAtEntity(null, flag ? 1003 : 1006, pos, 0);
/*     */           } 
/*     */         } 
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
/* 275 */     return (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) ? null : getItem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 286 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 287 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 292 */     return (pos.getY() >= 255) ? false : ((World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up())));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 297 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
/* 302 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 303 */     int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 304 */     boolean flag = isTop(i);
/* 305 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/* 306 */     int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
/* 307 */     int k = flag ? j : i;
/* 308 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
/* 309 */     int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
/* 310 */     int i1 = flag ? i : l;
/* 311 */     boolean flag1 = ((i1 & 0x1) != 0);
/* 312 */     boolean flag2 = ((i1 & 0x2) != 0);
/* 313 */     return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 321 */     return getItem();
/*     */   }
/*     */ 
/*     */   
/*     */   private Item getItem() {
/* 326 */     return (this == Blocks.iron_door) ? Items.iron_door : ((this == Blocks.spruce_door) ? Items.spruce_door : ((this == Blocks.birch_door) ? Items.birch_door : ((this == Blocks.jungle_door) ? Items.jungle_door : ((this == Blocks.acacia_door) ? Items.acacia_door : ((this == Blocks.dark_oak_door) ? Items.dark_oak_door : Items.oak_door)))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 331 */     BlockPos blockpos = pos.down();
/*     */     
/* 333 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this)
/*     */     {
/* 335 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 341 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 350 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) {
/*     */       
/* 352 */       IBlockState iblockstate = worldIn.getBlockState(pos.up());
/*     */       
/* 354 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 356 */         state = state.withProperty((IProperty)HINGE, iblockstate.getValue((IProperty)HINGE)).withProperty((IProperty)POWERED, iblockstate.getValue((IProperty)POWERED));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 361 */       IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */       
/* 363 */       if (iblockstate1.getBlock() == this)
/*     */       {
/* 365 */         state = state.withProperty((IProperty)FACING, iblockstate1.getValue((IProperty)FACING)).withProperty((IProperty)OPEN, iblockstate1.getValue((IProperty)OPEN));
/*     */       }
/*     */     } 
/*     */     
/* 369 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 377 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.UPPER).withProperty((IProperty)HINGE, ((meta & 0x1) > 0) ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x2) > 0))) : getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3).rotateYCCW()).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 385 */     int i = 0;
/*     */     
/* 387 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/*     */       
/* 389 */       i |= 0x8;
/*     */       
/* 391 */       if (state.getValue((IProperty)HINGE) == EnumHingePosition.RIGHT)
/*     */       {
/* 393 */         i |= 0x1;
/*     */       }
/*     */       
/* 396 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 398 */         i |= 0x2;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 403 */       i |= ((EnumFacing)state.getValue((IProperty)FACING)).rotateY().getHorizontalIndex();
/*     */       
/* 405 */       if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */       {
/* 407 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 411 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int removeHalfBit(int meta) {
/* 416 */     return meta & 0x7;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
/* 421 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
/* 426 */     return getFacing(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int combinedMeta) {
/* 431 */     return EnumFacing.getHorizontal(combinedMeta & 0x3).rotateYCCW();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isOpen(int combinedMeta) {
/* 436 */     return ((combinedMeta & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isTop(int meta) {
/* 441 */     return ((meta & 0x8) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isHingeLeft(int combinedMeta) {
/* 446 */     return ((combinedMeta & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 451 */     return new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)FACING, (IProperty)OPEN, (IProperty)HINGE, (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum EnumDoorHalf
/*     */     implements IStringSerializable {
/* 456 */     UPPER,
/* 457 */     LOWER;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 461 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 466 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumHingePosition
/*     */     implements IStringSerializable {
/* 472 */     LEFT,
/* 473 */     RIGHT;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 477 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 482 */       return (this == LEFT) ? "left" : "right";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */