/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockPistonStructureHelper;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonBase
/*     */   extends Block {
/*  27 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  28 */   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
/*     */ 
/*     */   
/*     */   private final boolean isSticky;
/*     */ 
/*     */   
/*     */   public BlockPistonBase(boolean isSticky) {
/*  35 */     super(Material.piston);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)EXTENDED, Boolean.FALSE));
/*  37 */     this.isSticky = isSticky;
/*  38 */     setStepSound(soundTypePiston);
/*  39 */     setHardness(0.5F);
/*  40 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  56 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)getFacingFromEntity(worldIn, pos, placer)), 2);
/*     */     
/*  58 */     if (!worldIn.isRemote)
/*     */     {
/*  60 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  69 */     if (!worldIn.isRemote)
/*     */     {
/*  71 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  77 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null)
/*     */     {
/*  79 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  89 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacingFromEntity(worldIn, pos, placer)).withProperty((IProperty)EXTENDED, Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForMove(World worldIn, BlockPos pos, IBlockState state) {
/*  94 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  95 */     boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */     
/*  97 */     if (flag && !((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/*  99 */       if ((new BlockPistonStructureHelper(worldIn, pos, enumfacing, true)).canMove())
/*     */       {
/* 101 */         worldIn.addBlockEvent(pos, this, 0, enumfacing.getIndex());
/*     */       }
/*     */     }
/* 104 */     else if (!flag && ((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 106 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.FALSE), 2);
/* 107 */       worldIn.addBlockEvent(pos, this, 1, enumfacing.getIndex());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldBeExtended(World worldIn, BlockPos pos, EnumFacing facing) {
/* 113 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/* 115 */       if (enumfacing != facing && worldIn.isSidePowered(pos.offset(enumfacing), enumfacing))
/*     */       {
/* 117 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     if (worldIn.isSidePowered(pos, EnumFacing.DOWN))
/*     */     {
/* 123 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 127 */     BlockPos blockpos = pos.up();
/*     */     
/* 129 */     for (EnumFacing enumfacing1 : EnumFacing.values()) {
/*     */       
/* 131 */       if (enumfacing1 != EnumFacing.DOWN && worldIn.isSidePowered(blockpos.offset(enumfacing1), enumfacing1))
/*     */       {
/* 133 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 146 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 148 */     if (!worldIn.isRemote) {
/*     */       
/* 150 */       boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */       
/* 152 */       if (flag && eventID == 1) {
/*     */         
/* 154 */         worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.TRUE), 2);
/* 155 */         return false;
/*     */       } 
/*     */       
/* 158 */       if (!flag && eventID == 0)
/*     */       {
/* 160 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     if (eventID == 0) {
/*     */       
/* 166 */       if (!doMove(worldIn, pos, enumfacing, true))
/*     */       {
/* 168 */         return false;
/*     */       }
/*     */       
/* 171 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.TRUE), 2);
/* 172 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.out", 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
/*     */     }
/* 174 */     else if (eventID == 1) {
/*     */       
/* 176 */       TileEntity tileentity1 = worldIn.getTileEntity(pos.offset(enumfacing));
/*     */       
/* 178 */       if (tileentity1 instanceof TileEntityPiston)
/*     */       {
/* 180 */         ((TileEntityPiston)tileentity1).clearPistonTileEntity();
/*     */       }
/*     */       
/* 183 */       worldIn.setBlockState(pos, Blocks.piston_extension.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
/* 184 */       worldIn.setTileEntity(pos, BlockPistonMoving.newTileEntity(getStateFromMeta(eventParam), enumfacing, false, true));
/*     */       
/* 186 */       if (this.isSticky) {
/*     */         
/* 188 */         BlockPos blockpos = pos.add(enumfacing.getFrontOffsetX() * 2, enumfacing.getFrontOffsetY() * 2, enumfacing.getFrontOffsetZ() * 2);
/* 189 */         Block block = worldIn.getBlockState(blockpos).getBlock();
/* 190 */         boolean flag1 = false;
/*     */         
/* 192 */         if (block == Blocks.piston_extension) {
/*     */           
/* 194 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */           
/* 196 */           if (tileentity instanceof TileEntityPiston) {
/*     */             
/* 198 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity;
/*     */             
/* 200 */             if (tileentitypiston.getFacing() == enumfacing && tileentitypiston.isExtending()) {
/*     */               
/* 202 */               tileentitypiston.clearPistonTileEntity();
/* 203 */               flag1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 208 */         if (!flag1 && block.getMaterial() != Material.air && canPush(block, worldIn, blockpos, enumfacing.getOpposite(), false) && (block.getMobilityFlag() == 0 || block == Blocks.piston || block == Blocks.sticky_piston))
/*     */         {
/* 210 */           doMove(worldIn, pos, enumfacing, false);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 215 */         worldIn.setBlockToAir(pos.offset(enumfacing));
/*     */       } 
/*     */       
/* 218 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.in", 0.5F, worldIn.rand.nextFloat() * 0.15F + 0.6F);
/*     */     } 
/*     */     
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 226 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 228 */     if (iblockstate.getBlock() == this && ((Boolean)iblockstate.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 230 */       float f = 0.25F;
/* 231 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       
/* 233 */       if (enumfacing != null)
/*     */       {
/* 235 */         switch (enumfacing) {
/*     */           
/*     */           case DOWN:
/* 238 */             setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case UP:
/* 242 */             setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*     */             break;
/*     */           
/*     */           case NORTH:
/* 246 */             setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 250 */             setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/*     */             break;
/*     */           
/*     */           case WEST:
/* 254 */             setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case EAST:
/* 258 */             setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/*     */     } else {
/* 264 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 273 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 283 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 284 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 289 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 290 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 295 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 300 */     int i = meta & 0x7;
/* 301 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
/* 306 */     if (MathHelper.abs((float)entityIn.posX - clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - clickedBlock.getZ()) < 2.0F) {
/*     */       
/* 308 */       double d0 = entityIn.posY + entityIn.getEyeHeight();
/*     */       
/* 310 */       if (d0 - clickedBlock.getY() > 2.0D)
/*     */       {
/* 312 */         return EnumFacing.UP;
/*     */       }
/*     */       
/* 315 */       if (clickedBlock.getY() - d0 > 0.0D)
/*     */       {
/* 317 */         return EnumFacing.DOWN;
/*     */       }
/*     */     } 
/*     */     
/* 321 */     return entityIn.getHorizontalFacing().getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canPush(Block blockIn, World worldIn, BlockPos pos, EnumFacing direction, boolean allowDestroy) {
/* 326 */     if (blockIn == Blocks.obsidian)
/*     */     {
/* 328 */       return false;
/*     */     }
/* 330 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 332 */       return false;
/*     */     }
/* 334 */     if (pos.getY() >= 0 && (direction != EnumFacing.DOWN || pos.getY() != 0)) {
/*     */       
/* 336 */       if (pos.getY() <= worldIn.getHeight() - 1 && (direction != EnumFacing.UP || pos.getY() != worldIn.getHeight() - 1)) {
/*     */         
/* 338 */         if (blockIn != Blocks.piston && blockIn != Blocks.sticky_piston) {
/*     */           
/* 340 */           if (blockIn.getBlockHardness(worldIn, pos) == -1.0F)
/*     */           {
/* 342 */             return false;
/*     */           }
/*     */           
/* 345 */           if (blockIn.getMobilityFlag() == 2)
/*     */           {
/* 347 */             return false;
/*     */           }
/*     */           
/* 350 */           if (blockIn.getMobilityFlag() == 1)
/*     */           {
/* 352 */             if (!allowDestroy)
/*     */             {
/* 354 */               return false;
/*     */             }
/*     */             
/* 357 */             return true;
/*     */           }
/*     */         
/* 360 */         } else if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */           
/* 362 */           return false;
/*     */         } 
/*     */         
/* 365 */         return !(blockIn instanceof ITileEntityProvider);
/*     */       } 
/*     */ 
/*     */       
/* 369 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 374 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doMove(World worldIn, BlockPos pos, EnumFacing direction, boolean extending) {
/* 380 */     if (!extending)
/*     */     {
/* 382 */       worldIn.setBlockToAir(pos.offset(direction));
/*     */     }
/*     */     
/* 385 */     BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(worldIn, pos, direction, extending);
/* 386 */     List<BlockPos> list = blockpistonstructurehelper.getBlocksToMove();
/* 387 */     List<BlockPos> list1 = blockpistonstructurehelper.getBlocksToDestroy();
/*     */     
/* 389 */     if (!blockpistonstructurehelper.canMove())
/*     */     {
/* 391 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 395 */     int i = list.size() + list1.size();
/* 396 */     Block[] ablock = new Block[i];
/* 397 */     EnumFacing enumfacing = extending ? direction : direction.getOpposite();
/*     */     
/* 399 */     for (int j = list1.size() - 1; j >= 0; j--) {
/*     */       
/* 401 */       BlockPos blockpos = list1.get(j);
/* 402 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/* 403 */       block.dropBlockAsItem(worldIn, blockpos, worldIn.getBlockState(blockpos), 0);
/* 404 */       worldIn.setBlockToAir(blockpos);
/* 405 */       i--;
/* 406 */       ablock[i] = block;
/*     */     } 
/*     */     
/* 409 */     for (int k = list.size() - 1; k >= 0; k--) {
/*     */       
/* 411 */       BlockPos blockpos2 = list.get(k);
/* 412 */       IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 413 */       Block block1 = iblockstate.getBlock();
/* 414 */       block1.getMetaFromState(iblockstate);
/* 415 */       worldIn.setBlockToAir(blockpos2);
/* 416 */       blockpos2 = blockpos2.offset(enumfacing);
/* 417 */       worldIn.setBlockState(blockpos2, Blocks.piston_extension.getDefaultState().withProperty((IProperty)FACING, (Comparable)direction), 4);
/* 418 */       worldIn.setTileEntity(blockpos2, BlockPistonMoving.newTileEntity(iblockstate, direction, extending, false));
/* 419 */       i--;
/* 420 */       ablock[i] = block1;
/*     */     } 
/*     */     
/* 423 */     BlockPos blockpos1 = pos.offset(direction);
/*     */     
/* 425 */     if (extending) {
/*     */       
/* 427 */       BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 428 */       IBlockState iblockstate1 = Blocks.piston_head.getDefaultState().withProperty((IProperty)BlockPistonExtension.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonExtension.TYPE, blockpistonextension$enumpistontype);
/* 429 */       IBlockState iblockstate2 = Blocks.piston_extension.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/* 430 */       worldIn.setBlockState(blockpos1, iblockstate2, 4);
/* 431 */       worldIn.setTileEntity(blockpos1, BlockPistonMoving.newTileEntity(iblockstate1, direction, true, false));
/*     */     } 
/*     */     
/* 434 */     for (int l = list1.size() - 1; l >= 0; l--)
/*     */     {
/* 436 */       worldIn.notifyNeighborsOfStateChange(list1.get(l), ablock[i++]);
/*     */     }
/*     */     
/* 439 */     for (int i1 = list.size() - 1; i1 >= 0; i1--)
/*     */     {
/* 441 */       worldIn.notifyNeighborsOfStateChange(list.get(i1), ablock[i++]);
/*     */     }
/*     */     
/* 444 */     if (extending) {
/*     */       
/* 446 */       worldIn.notifyNeighborsOfStateChange(blockpos1, Blocks.piston_head);
/* 447 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */     } 
/*     */     
/* 450 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 459 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 467 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)EXTENDED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 475 */     int i = 0;
/* 476 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 478 */     if (((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue())
/*     */     {
/* 480 */       i |= 0x8;
/*     */     }
/*     */     
/* 483 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 488 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)EXTENDED });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockPistonBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */