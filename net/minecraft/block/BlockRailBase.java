/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockRailBase
/*     */   extends Block
/*     */ {
/*     */   protected final boolean isPowered;
/*     */   
/*     */   public static boolean isRailBlock(World worldIn, BlockPos pos) {
/*  26 */     return isRailBlock(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isRailBlock(IBlockState state) {
/*  31 */     Block block = state.getBlock();
/*  32 */     return (block == Blocks.rail || block == Blocks.golden_rail || block == Blocks.detector_rail || block == Blocks.activator_rail);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockRailBase(boolean isPowered) {
/*  37 */     super(Material.circuits);
/*  38 */     this.isPowered = isPowered;
/*  39 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  40 */     setCreativeTab(CreativeTabs.tabTransport);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  45 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/*  64 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  65 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  70 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  71 */     EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() == this) ? (EnumRailDirection)iblockstate.getValue(getShapeProperty()) : null;
/*     */     
/*  73 */     if (blockrailbase$enumraildirection != null && blockrailbase$enumraildirection.isAscending()) {
/*     */       
/*  75 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  79 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  90 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  95 */     if (!worldIn.isRemote) {
/*     */       
/*  97 */       state = func_176564_a(worldIn, pos, state, true);
/*     */       
/*  99 */       if (this.isPowered)
/*     */       {
/* 101 */         onNeighborBlockChange(worldIn, pos, state, this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 111 */     if (!worldIn.isRemote) {
/*     */       
/* 113 */       EnumRailDirection blockrailbase$enumraildirection = (EnumRailDirection)state.getValue(getShapeProperty());
/* 114 */       boolean flag = false;
/*     */       
/* 116 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()))
/*     */       {
/* 118 */         flag = true;
/*     */       }
/*     */       
/* 121 */       if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.east())) {
/*     */         
/* 123 */         flag = true;
/*     */       }
/* 125 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.west())) {
/*     */         
/* 127 */         flag = true;
/*     */       }
/* 129 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.north())) {
/*     */         
/* 131 */         flag = true;
/*     */       }
/* 133 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.south())) {
/*     */         
/* 135 */         flag = true;
/*     */       } 
/*     */       
/* 138 */       if (flag) {
/*     */         
/* 140 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 141 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 145 */         onNeighborChangedInternal(worldIn, pos, state, neighborBlock);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onNeighborChangedInternal(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {}
/*     */ 
/*     */   
/*     */   protected IBlockState func_176564_a(World worldIn, BlockPos p_176564_2_, IBlockState p_176564_3_, boolean p_176564_4_) {
/* 156 */     return worldIn.isRemote ? p_176564_3_ : (new Rail(worldIn, p_176564_2_, p_176564_3_)).func_180364_a(worldIn.isBlockPowered(p_176564_2_), p_176564_4_).getBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 161 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 166 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 171 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 173 */     if (((EnumRailDirection)state.getValue(getShapeProperty())).isAscending())
/*     */     {
/* 175 */       worldIn.notifyNeighborsOfStateChange(pos.up(), this);
/*     */     }
/*     */     
/* 178 */     if (this.isPowered) {
/*     */       
/* 180 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 181 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract IProperty<EnumRailDirection> getShapeProperty();
/*     */   
/*     */   public enum EnumRailDirection
/*     */     implements IStringSerializable {
/* 189 */     NORTH_SOUTH(0, "north_south"),
/* 190 */     EAST_WEST(1, "east_west"),
/* 191 */     ASCENDING_EAST(2, "ascending_east"),
/* 192 */     ASCENDING_WEST(3, "ascending_west"),
/* 193 */     ASCENDING_NORTH(4, "ascending_north"),
/* 194 */     ASCENDING_SOUTH(5, "ascending_south"),
/* 195 */     SOUTH_EAST(6, "south_east"),
/* 196 */     SOUTH_WEST(7, "south_west"),
/* 197 */     NORTH_WEST(8, "north_west"),
/* 198 */     NORTH_EAST(9, "north_east");
/*     */     
/* 200 */     private static final EnumRailDirection[] META_LOOKUP = new EnumRailDirection[(values()).length];
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */     
/*     */     EnumRailDirection(int meta, String name) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isAscending() {
/*     */       return (this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST);
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumRailDirection byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length) {
/*     */         meta = 0;
/*     */       }
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/* 241 */       for (EnumRailDirection blockrailbase$enumraildirection : values())
/*     */       {
/* 243 */         META_LOOKUP[blockrailbase$enumraildirection.getMetadata()] = blockrailbase$enumraildirection; } 
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public class Rail
/*     */   {
/*     */     private final World world;
/*     */     private final BlockPos pos;
/* 255 */     private final List<BlockPos> field_150657_g = Lists.newArrayList();
/*     */     private final BlockRailBase block;
/*     */     
/*     */     public Rail(World worldIn, BlockPos pos, IBlockState state) {
/* 259 */       this.world = worldIn;
/* 260 */       this.pos = pos;
/* 261 */       this.state = state;
/* 262 */       this.block = (BlockRailBase)state.getBlock();
/* 263 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue(BlockRailBase.this.getShapeProperty());
/* 264 */       this.isPowered = this.block.isPowered;
/* 265 */       func_180360_a(blockrailbase$enumraildirection);
/*     */     }
/*     */     private IBlockState state; private final boolean isPowered;
/*     */     
/*     */     private void func_180360_a(BlockRailBase.EnumRailDirection p_180360_1_) {
/* 270 */       this.field_150657_g.clear();
/*     */       
/* 272 */       switch (p_180360_1_) {
/*     */         
/*     */         case NORTH_SOUTH:
/* 275 */           this.field_150657_g.add(this.pos.north());
/* 276 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case EAST_WEST:
/* 280 */           this.field_150657_g.add(this.pos.west());
/* 281 */           this.field_150657_g.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case ASCENDING_EAST:
/* 285 */           this.field_150657_g.add(this.pos.west());
/* 286 */           this.field_150657_g.add(this.pos.east().up());
/*     */           break;
/*     */         
/*     */         case ASCENDING_WEST:
/* 290 */           this.field_150657_g.add(this.pos.west().up());
/* 291 */           this.field_150657_g.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case ASCENDING_NORTH:
/* 295 */           this.field_150657_g.add(this.pos.north().up());
/* 296 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case ASCENDING_SOUTH:
/* 300 */           this.field_150657_g.add(this.pos.north());
/* 301 */           this.field_150657_g.add(this.pos.south().up());
/*     */           break;
/*     */         
/*     */         case SOUTH_EAST:
/* 305 */           this.field_150657_g.add(this.pos.east());
/* 306 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case SOUTH_WEST:
/* 310 */           this.field_150657_g.add(this.pos.west());
/* 311 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case NORTH_WEST:
/* 315 */           this.field_150657_g.add(this.pos.west());
/* 316 */           this.field_150657_g.add(this.pos.north());
/*     */           break;
/*     */         
/*     */         case NORTH_EAST:
/* 320 */           this.field_150657_g.add(this.pos.east());
/* 321 */           this.field_150657_g.add(this.pos.north());
/*     */           break;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void func_150651_b() {
/* 327 */       for (int i = 0; i < this.field_150657_g.size(); i++) {
/*     */         
/* 329 */         Rail blockrailbase$rail = findRailAt(this.field_150657_g.get(i));
/*     */         
/* 331 */         if (blockrailbase$rail != null && blockrailbase$rail.func_150653_a(this)) {
/*     */           
/* 333 */           this.field_150657_g.set(i, blockrailbase$rail.pos);
/*     */         }
/*     */         else {
/*     */           
/* 337 */           this.field_150657_g.remove(i--);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean hasRailAt(BlockPos pos) {
/* 344 */       return (BlockRailBase.isRailBlock(this.world, pos) || BlockRailBase.isRailBlock(this.world, pos.up()) || BlockRailBase.isRailBlock(this.world, pos.down()));
/*     */     }
/*     */ 
/*     */     
/*     */     private Rail findRailAt(BlockPos pos) {
/* 349 */       IBlockState iblockstate = this.world.getBlockState(pos);
/*     */       
/* 351 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */         
/* 353 */         BlockRailBase.this.getClass(); return new Rail(this.world, pos, iblockstate);
/*     */       } 
/*     */ 
/*     */       
/* 357 */       BlockPos lvt_2_1_ = pos.up();
/* 358 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/*     */       
/* 360 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */         
/* 362 */         BlockRailBase.this.getClass(); return new Rail(this.world, lvt_2_1_, iblockstate);
/*     */       } 
/*     */ 
/*     */       
/* 366 */       lvt_2_1_ = pos.down();
/* 367 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/* 368 */       BlockRailBase.this.getClass(); return BlockRailBase.isRailBlock(iblockstate) ? new Rail(this.world, lvt_2_1_, iblockstate) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean func_150653_a(Rail p_150653_1_) {
/* 375 */       return func_180363_c(p_150653_1_.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_180363_c(BlockPos p_180363_1_) {
/* 380 */       for (int i = 0; i < this.field_150657_g.size(); i++) {
/*     */         
/* 382 */         BlockPos blockpos = this.field_150657_g.get(i);
/*     */         
/* 384 */         if (blockpos.getX() == p_180363_1_.getX() && blockpos.getZ() == p_180363_1_.getZ())
/*     */         {
/* 386 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 390 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int countAdjacentRails() {
/* 395 */       int i = 0;
/*     */       
/* 397 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 399 */         if (hasRailAt(this.pos.offset(enumfacing)))
/*     */         {
/* 401 */           i++;
/*     */         }
/*     */       } 
/*     */       
/* 405 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_150649_b(Rail rail) {
/* 410 */       return (func_150653_a(rail) || this.field_150657_g.size() != 2);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_150645_c(Rail p_150645_1_) {
/* 415 */       this.field_150657_g.add(p_150645_1_.pos);
/* 416 */       BlockPos blockpos = this.pos.north();
/* 417 */       BlockPos blockpos1 = this.pos.south();
/* 418 */       BlockPos blockpos2 = this.pos.west();
/* 419 */       BlockPos blockpos3 = this.pos.east();
/* 420 */       boolean flag = func_180363_c(blockpos);
/* 421 */       boolean flag1 = func_180363_c(blockpos1);
/* 422 */       boolean flag2 = func_180363_c(blockpos2);
/* 423 */       boolean flag3 = func_180363_c(blockpos3);
/* 424 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 426 */       if (flag || flag1)
/*     */       {
/* 428 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 431 */       if (flag2 || flag3)
/*     */       {
/* 433 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 436 */       if (!this.isPowered) {
/*     */         
/* 438 */         if (flag1 && flag3 && !flag && !flag2)
/*     */         {
/* 440 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 443 */         if (flag1 && flag2 && !flag && !flag3)
/*     */         {
/* 445 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 448 */         if (flag && flag2 && !flag1 && !flag3)
/*     */         {
/* 450 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 453 */         if (flag && flag3 && !flag1 && !flag2)
/*     */         {
/* 455 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 459 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*     */         
/* 461 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up()))
/*     */         {
/* 463 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 466 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up()))
/*     */         {
/* 468 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 472 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*     */         
/* 474 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up()))
/*     */         {
/* 476 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 479 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up()))
/*     */         {
/* 481 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 485 */       if (blockrailbase$enumraildirection == null)
/*     */       {
/* 487 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 490 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/* 491 */       this.world.setBlockState(this.pos, this.state, 3);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_180361_d(BlockPos p_180361_1_) {
/* 496 */       Rail blockrailbase$rail = findRailAt(p_180361_1_);
/*     */       
/* 498 */       if (blockrailbase$rail == null)
/*     */       {
/* 500 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 504 */       blockrailbase$rail.func_150651_b();
/* 505 */       return blockrailbase$rail.func_150649_b(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Rail func_180364_a(boolean p_180364_1_, boolean p_180364_2_) {
/* 511 */       BlockPos blockpos = this.pos.north();
/* 512 */       BlockPos blockpos1 = this.pos.south();
/* 513 */       BlockPos blockpos2 = this.pos.west();
/* 514 */       BlockPos blockpos3 = this.pos.east();
/* 515 */       boolean flag = func_180361_d(blockpos);
/* 516 */       boolean flag1 = func_180361_d(blockpos1);
/* 517 */       boolean flag2 = func_180361_d(blockpos2);
/* 518 */       boolean flag3 = func_180361_d(blockpos3);
/* 519 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 521 */       if ((flag || flag1) && !flag2 && !flag3)
/*     */       {
/* 523 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 526 */       if ((flag2 || flag3) && !flag && !flag1)
/*     */       {
/* 528 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 531 */       if (!this.isPowered) {
/*     */         
/* 533 */         if (flag1 && flag3 && !flag && !flag2)
/*     */         {
/* 535 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 538 */         if (flag1 && flag2 && !flag && !flag3)
/*     */         {
/* 540 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 543 */         if (flag && flag2 && !flag1 && !flag3)
/*     */         {
/* 545 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 548 */         if (flag && flag3 && !flag1 && !flag2)
/*     */         {
/* 550 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 554 */       if (blockrailbase$enumraildirection == null) {
/*     */         
/* 556 */         if (flag || flag1)
/*     */         {
/* 558 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         }
/*     */         
/* 561 */         if (flag2 || flag3)
/*     */         {
/* 563 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         }
/*     */         
/* 566 */         if (!this.isPowered)
/*     */         {
/* 568 */           if (p_180364_1_) {
/*     */             
/* 570 */             if (flag1 && flag3)
/*     */             {
/* 572 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */             
/* 575 */             if (flag2 && flag1)
/*     */             {
/* 577 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 580 */             if (flag3 && flag)
/*     */             {
/* 582 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 585 */             if (flag && flag2)
/*     */             {
/* 587 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 592 */             if (flag && flag2)
/*     */             {
/* 594 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */             
/* 597 */             if (flag3 && flag)
/*     */             {
/* 599 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 602 */             if (flag2 && flag1)
/*     */             {
/* 604 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 607 */             if (flag1 && flag3)
/*     */             {
/* 609 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 615 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*     */         
/* 617 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up()))
/*     */         {
/* 619 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 622 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up()))
/*     */         {
/* 624 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 628 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*     */         
/* 630 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up()))
/*     */         {
/* 632 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 635 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up()))
/*     */         {
/* 637 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 641 */       if (blockrailbase$enumraildirection == null)
/*     */       {
/* 643 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 646 */       func_180360_a(blockrailbase$enumraildirection);
/* 647 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/*     */       
/* 649 */       if (p_180364_2_ || this.world.getBlockState(this.pos) != this.state) {
/*     */         
/* 651 */         this.world.setBlockState(this.pos, this.state, 3);
/*     */         
/* 653 */         for (int i = 0; i < this.field_150657_g.size(); i++) {
/*     */           
/* 655 */           Rail blockrailbase$rail = findRailAt(this.field_150657_g.get(i));
/*     */           
/* 657 */           if (blockrailbase$rail != null) {
/*     */             
/* 659 */             blockrailbase$rail.func_150651_b();
/*     */             
/* 661 */             if (blockrailbase$rail.func_150649_b(this))
/*     */             {
/* 663 */               blockrailbase$rail.func_150645_c(this);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 669 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getBlockState() {
/* 674 */       return this.state;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockRailBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */