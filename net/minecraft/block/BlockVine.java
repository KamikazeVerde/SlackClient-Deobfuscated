/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
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
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockVine
/*     */   extends Block {
/*  28 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  29 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  30 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  31 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  32 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  33 */   public static final PropertyBool[] ALL_FACES = new PropertyBool[] { UP, NORTH, SOUTH, WEST, EAST };
/*     */ 
/*     */   
/*     */   public BlockVine() {
/*  37 */     super(Material.vine);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.FALSE).withProperty((IProperty)NORTH, Boolean.FALSE).withProperty((IProperty)EAST, Boolean.FALSE).withProperty((IProperty)SOUTH, Boolean.FALSE).withProperty((IProperty)WEST, Boolean.FALSE));
/*  39 */     setTickRandomly(true);
/*  40 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  49 */     return state.withProperty((IProperty)UP, Boolean.valueOf(worldIn.getBlockState(pos.up()).getBlock().isBlockNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  57 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  83 */     float f = 0.0625F;
/*  84 */     float f1 = 1.0F;
/*  85 */     float f2 = 1.0F;
/*  86 */     float f3 = 1.0F;
/*  87 */     float f4 = 0.0F;
/*  88 */     float f5 = 0.0F;
/*  89 */     float f6 = 0.0F;
/*  90 */     boolean flag = false;
/*     */     
/*  92 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)WEST)).booleanValue()) {
/*     */       
/*  94 */       f4 = Math.max(f4, 0.0625F);
/*  95 */       f1 = 0.0F;
/*  96 */       f2 = 0.0F;
/*  97 */       f5 = 1.0F;
/*  98 */       f3 = 0.0F;
/*  99 */       f6 = 1.0F;
/* 100 */       flag = true;
/*     */     } 
/*     */     
/* 103 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EAST)).booleanValue()) {
/*     */       
/* 105 */       f1 = Math.min(f1, 0.9375F);
/* 106 */       f4 = 1.0F;
/* 107 */       f2 = 0.0F;
/* 108 */       f5 = 1.0F;
/* 109 */       f3 = 0.0F;
/* 110 */       f6 = 1.0F;
/* 111 */       flag = true;
/*     */     } 
/*     */     
/* 114 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)NORTH)).booleanValue()) {
/*     */       
/* 116 */       f6 = Math.max(f6, 0.0625F);
/* 117 */       f3 = 0.0F;
/* 118 */       f1 = 0.0F;
/* 119 */       f4 = 1.0F;
/* 120 */       f2 = 0.0F;
/* 121 */       f5 = 1.0F;
/* 122 */       flag = true;
/*     */     } 
/*     */     
/* 125 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)SOUTH)).booleanValue()) {
/*     */       
/* 127 */       f3 = Math.min(f3, 0.9375F);
/* 128 */       f6 = 1.0F;
/* 129 */       f1 = 0.0F;
/* 130 */       f4 = 1.0F;
/* 131 */       f2 = 0.0F;
/* 132 */       f5 = 1.0F;
/* 133 */       flag = true;
/*     */     } 
/*     */     
/* 136 */     if (!flag && canPlaceOn(worldIn.getBlockState(pos.up()).getBlock())) {
/*     */       
/* 138 */       f2 = Math.min(f2, 0.9375F);
/* 139 */       f5 = 1.0F;
/* 140 */       f1 = 0.0F;
/* 141 */       f4 = 1.0F;
/* 142 */       f3 = 0.0F;
/* 143 */       f6 = 1.0F;
/*     */     } 
/*     */     
/* 146 */     setBlockBounds(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 159 */     switch (side) {
/*     */       
/*     */       case UP:
/* 162 */         return canPlaceOn(worldIn.getBlockState(pos.up()).getBlock());
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case EAST:
/*     */       case WEST:
/* 168 */         return canPlaceOn(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock());
/*     */     } 
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(Block blockIn) {
/* 177 */     return (blockIn.isFullCube() && blockIn.blockMaterial.blocksMovement());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state) {
/* 182 */     IBlockState iblockstate = state;
/*     */     
/* 184 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 186 */       PropertyBool propertybool = getPropertyFor(enumfacing);
/*     */       
/* 188 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue() && !canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing)).getBlock())) {
/*     */         
/* 190 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
/*     */         
/* 192 */         if (iblockstate1.getBlock() != this || !((Boolean)iblockstate1.getValue((IProperty)propertybool)).booleanValue())
/*     */         {
/* 194 */           state = state.withProperty((IProperty)propertybool, Boolean.FALSE);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 199 */     if (getNumGrownFaces(state) == 0)
/*     */     {
/* 201 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 205 */     if (iblockstate != state)
/*     */     {
/* 207 */       worldIn.setBlockState(pos, state, 2);
/*     */     }
/*     */     
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockColor() {
/* 216 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 221 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 226 */     return worldIn.getBiomeGenForCoords(pos).getFoliageColorAtPos(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 234 */     if (!worldIn.isRemote && !recheckGrownSides(worldIn, pos, state)) {
/*     */       
/* 236 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 237 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 243 */     if (!worldIn.isRemote)
/*     */     {
/* 245 */       if (worldIn.rand.nextInt(4) == 0) {
/*     */         
/* 247 */         int i = 4;
/* 248 */         int j = 5;
/* 249 */         boolean flag = false;
/*     */         
/*     */         int k;
/* 252 */         label103: for (k = -i; k <= i; k++) {
/*     */           
/* 254 */           for (int l = -i; l <= i; l++) {
/*     */             
/* 256 */             for (int i1 = -1; i1 <= 1; i1++) {
/*     */               
/* 258 */               if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this) {
/*     */                 
/* 260 */                 j--;
/*     */                 
/* 262 */                 if (j <= 0) {
/*     */                   
/* 264 */                   flag = true;
/*     */                   
/*     */                   break label103;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 272 */         EnumFacing enumfacing1 = EnumFacing.random(rand);
/* 273 */         BlockPos blockpos1 = pos.up();
/*     */         
/* 275 */         if (enumfacing1 == EnumFacing.UP && pos.getY() < 255 && worldIn.isAirBlock(blockpos1)) {
/*     */           
/* 277 */           if (!flag)
/*     */           {
/* 279 */             IBlockState iblockstate2 = state;
/*     */             
/* 281 */             for (EnumFacing enumfacing3 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 283 */               if (rand.nextBoolean() || !canPlaceOn(worldIn.getBlockState(blockpos1.offset(enumfacing3)).getBlock()))
/*     */               {
/* 285 */                 iblockstate2 = iblockstate2.withProperty((IProperty)getPropertyFor(enumfacing3), Boolean.FALSE);
/*     */               }
/*     */             } 
/*     */             
/* 289 */             if (((Boolean)iblockstate2.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 291 */               worldIn.setBlockState(blockpos1, iblockstate2, 2);
/*     */             }
/*     */           }
/*     */         
/* 295 */         } else if (enumfacing1.getAxis().isHorizontal() && !((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing1))).booleanValue()) {
/*     */           
/* 297 */           if (!flag)
/*     */           {
/* 299 */             BlockPos blockpos3 = pos.offset(enumfacing1);
/* 300 */             Block block1 = worldIn.getBlockState(blockpos3).getBlock();
/*     */             
/* 302 */             if (block1.blockMaterial == Material.air) {
/*     */               
/* 304 */               EnumFacing enumfacing2 = enumfacing1.rotateY();
/* 305 */               EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
/* 306 */               boolean flag1 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing2))).booleanValue();
/* 307 */               boolean flag2 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing4))).booleanValue();
/* 308 */               BlockPos blockpos4 = blockpos3.offset(enumfacing2);
/* 309 */               BlockPos blockpos = blockpos3.offset(enumfacing4);
/*     */               
/* 311 */               if (flag1 && canPlaceOn(worldIn.getBlockState(blockpos4).getBlock()))
/*     */               {
/* 313 */                 worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing2), Boolean.TRUE), 2);
/*     */               }
/* 315 */               else if (flag2 && canPlaceOn(worldIn.getBlockState(blockpos).getBlock()))
/*     */               {
/* 317 */                 worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing4), Boolean.TRUE), 2);
/*     */               }
/* 319 */               else if (flag1 && worldIn.isAirBlock(blockpos4) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing2)).getBlock()))
/*     */               {
/* 321 */                 worldIn.setBlockState(blockpos4, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.TRUE), 2);
/*     */               }
/* 323 */               else if (flag2 && worldIn.isAirBlock(blockpos) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing4)).getBlock()))
/*     */               {
/* 325 */                 worldIn.setBlockState(blockpos, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.TRUE), 2);
/*     */               }
/* 327 */               else if (canPlaceOn(worldIn.getBlockState(blockpos3.up()).getBlock()))
/*     */               {
/* 329 */                 worldIn.setBlockState(blockpos3, getDefaultState(), 2);
/*     */               }
/*     */             
/* 332 */             } else if (block1.blockMaterial.isOpaque() && block1.isFullCube()) {
/*     */               
/* 334 */               worldIn.setBlockState(pos, state.withProperty((IProperty)getPropertyFor(enumfacing1), Boolean.TRUE), 2);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 340 */         else if (pos.getY() > 1) {
/*     */           
/* 342 */           BlockPos blockpos2 = pos.down();
/* 343 */           IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 344 */           Block block = iblockstate.getBlock();
/*     */           
/* 346 */           if (block.blockMaterial == Material.air) {
/*     */             
/* 348 */             IBlockState iblockstate1 = state;
/*     */             
/* 350 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 352 */               if (rand.nextBoolean())
/*     */               {
/* 354 */                 iblockstate1 = iblockstate1.withProperty((IProperty)getPropertyFor(enumfacing), Boolean.FALSE);
/*     */               }
/*     */             } 
/*     */             
/* 358 */             if (((Boolean)iblockstate1.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 360 */               worldIn.setBlockState(blockpos2, iblockstate1, 2);
/*     */             }
/*     */           }
/* 363 */           else if (block == this) {
/*     */             
/* 365 */             IBlockState iblockstate3 = iblockstate;
/*     */             
/* 367 */             for (EnumFacing enumfacing5 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 369 */               PropertyBool propertybool = getPropertyFor(enumfacing5);
/*     */               
/* 371 */               if (rand.nextBoolean() && ((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */               {
/* 373 */                 iblockstate3 = iblockstate3.withProperty((IProperty)propertybool, Boolean.TRUE);
/*     */               }
/*     */             } 
/*     */             
/* 377 */             if (((Boolean)iblockstate3.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 379 */               worldIn.setBlockState(blockpos2, iblockstate3, 2);
/*     */             }
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
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 394 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)UP, Boolean.FALSE).withProperty((IProperty)NORTH, Boolean.FALSE).withProperty((IProperty)EAST, Boolean.FALSE).withProperty((IProperty)SOUTH, Boolean.FALSE).withProperty((IProperty)WEST, Boolean.FALSE);
/* 395 */     return facing.getAxis().isHorizontal() ? iblockstate.withProperty((IProperty)getPropertyFor(facing.getOpposite()), Boolean.TRUE) : iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 405 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 413 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 418 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/*     */       
/* 420 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 421 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.vine, 1, 0));
/*     */     }
/*     */     else {
/*     */       
/* 425 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 431 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 439 */     return getDefaultState().withProperty((IProperty)SOUTH, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)WEST, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)NORTH, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)EAST, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 447 */     int i = 0;
/*     */     
/* 449 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 451 */       i |= 0x1;
/*     */     }
/*     */     
/* 454 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/* 456 */       i |= 0x2;
/*     */     }
/*     */     
/* 459 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/* 461 */       i |= 0x4;
/*     */     }
/*     */     
/* 464 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/* 466 */       i |= 0x8;
/*     */     }
/*     */     
/* 469 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 474 */     return new BlockState(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST });
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertyBool getPropertyFor(EnumFacing side) {
/* 479 */     switch (side) {
/*     */       
/*     */       case UP:
/* 482 */         return UP;
/*     */       
/*     */       case NORTH:
/* 485 */         return NORTH;
/*     */       
/*     */       case SOUTH:
/* 488 */         return SOUTH;
/*     */       
/*     */       case EAST:
/* 491 */         return EAST;
/*     */       
/*     */       case WEST:
/* 494 */         return WEST;
/*     */     } 
/*     */     
/* 497 */     throw new IllegalArgumentException(side + " is an invalid choice");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumGrownFaces(IBlockState state) {
/* 503 */     int i = 0;
/*     */     
/* 505 */     for (PropertyBool propertybool : ALL_FACES) {
/*     */       
/* 507 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */       {
/* 509 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 513 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockVine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */