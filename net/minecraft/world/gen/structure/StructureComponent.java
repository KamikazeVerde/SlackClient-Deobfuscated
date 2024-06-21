/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemDoor;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StructureComponent
/*     */ {
/*     */   protected StructureBoundingBox boundingBox;
/*     */   protected EnumFacing coordBaseMode;
/*     */   protected int componentType;
/*     */   
/*     */   public StructureComponent() {}
/*     */   
/*     */   protected StructureComponent(int type) {
/*  37 */     this.componentType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound createStructureBaseNBT() {
/*  48 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  49 */     nbttagcompound.setString("id", MapGenStructureIO.getStructureComponentName(this));
/*  50 */     nbttagcompound.setTag("BB", (NBTBase)this.boundingBox.toNBTTagIntArray());
/*  51 */     nbttagcompound.setInteger("O", (this.coordBaseMode == null) ? -1 : this.coordBaseMode.getHorizontalIndex());
/*  52 */     nbttagcompound.setInteger("GD", this.componentType);
/*  53 */     writeStructureToNBT(nbttagcompound);
/*  54 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void writeStructureToNBT(NBTTagCompound paramNBTTagCompound);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readStructureBaseNBT(World worldIn, NBTTagCompound tagCompound) {
/*  69 */     if (tagCompound.hasKey("BB"))
/*     */     {
/*  71 */       this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  74 */     int i = tagCompound.getInteger("O");
/*  75 */     this.coordBaseMode = (i == -1) ? null : EnumFacing.getHorizontal(i);
/*  76 */     this.componentType = tagCompound.getInteger("GD");
/*  77 */     readStructureFromNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void readStructureFromNBT(NBTTagCompound paramNBTTagCompound);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean addComponentParts(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureBoundingBox getBoundingBox() {
/* 100 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentType() {
/* 108 */     return this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureComponent findIntersecting(List<StructureComponent> listIn, StructureBoundingBox boundingboxIn) {
/* 116 */     for (StructureComponent structurecomponent : listIn) {
/*     */       
/* 118 */       if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(boundingboxIn))
/*     */       {
/* 120 */         return structurecomponent;
/*     */       }
/*     */     } 
/*     */     
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getBoundingBoxCenter() {
/* 129 */     return new BlockPos(this.boundingBox.getCenter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLiquidInStructureBoundingBox(World worldIn, StructureBoundingBox boundingboxIn) {
/* 137 */     int i = Math.max(this.boundingBox.minX - 1, boundingboxIn.minX);
/* 138 */     int j = Math.max(this.boundingBox.minY - 1, boundingboxIn.minY);
/* 139 */     int k = Math.max(this.boundingBox.minZ - 1, boundingboxIn.minZ);
/* 140 */     int l = Math.min(this.boundingBox.maxX + 1, boundingboxIn.maxX);
/* 141 */     int i1 = Math.min(this.boundingBox.maxY + 1, boundingboxIn.maxY);
/* 142 */     int j1 = Math.min(this.boundingBox.maxZ + 1, boundingboxIn.maxZ);
/* 143 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 145 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 147 */       for (int l1 = k; l1 <= j1; l1++) {
/*     */         
/* 149 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, j, l1)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 151 */           return true;
/*     */         }
/*     */         
/* 154 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, i1, l1)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 156 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     for (int i2 = i; i2 <= l; i2++) {
/*     */       
/* 163 */       for (int k2 = j; k2 <= i1; k2++) {
/*     */         
/* 165 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(i2, k2, k)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 167 */           return true;
/*     */         }
/*     */         
/* 170 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(i2, k2, j1)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 172 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     for (int j2 = k; j2 <= j1; j2++) {
/*     */       
/* 179 */       for (int l2 = j; l2 <= i1; l2++) {
/*     */         
/* 181 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(i, l2, j2)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 183 */           return true;
/*     */         }
/*     */         
/* 186 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(l, l2, j2)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 188 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getXWithOffset(int x, int z) {
/* 198 */     if (this.coordBaseMode == null)
/*     */     {
/* 200 */       return x;
/*     */     }
/*     */ 
/*     */     
/* 204 */     switch (this.coordBaseMode) {
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 208 */         return this.boundingBox.minX + x;
/*     */       
/*     */       case WEST:
/* 211 */         return this.boundingBox.maxX - z;
/*     */       
/*     */       case EAST:
/* 214 */         return this.boundingBox.minX + z;
/*     */     } 
/*     */     
/* 217 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getYWithOffset(int y) {
/* 224 */     return (this.coordBaseMode == null) ? y : (y + this.boundingBox.minY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getZWithOffset(int x, int z) {
/* 229 */     if (this.coordBaseMode == null)
/*     */     {
/* 231 */       return z;
/*     */     }
/*     */ 
/*     */     
/* 235 */     switch (this.coordBaseMode) {
/*     */       
/*     */       case NORTH:
/* 238 */         return this.boundingBox.maxZ - z;
/*     */       
/*     */       case SOUTH:
/* 241 */         return this.boundingBox.minZ + z;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 245 */         return this.boundingBox.minZ + x;
/*     */     } 
/*     */     
/* 248 */     return z;
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
/*     */   protected int getMetadataWithOffset(Block blockIn, int meta) {
/* 260 */     if (blockIn == Blocks.rail) {
/*     */       
/* 262 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST)
/*     */       {
/* 264 */         if (meta == 1)
/*     */         {
/* 266 */           return 0;
/*     */         }
/*     */         
/* 269 */         return 1;
/*     */       }
/*     */     
/* 272 */     } else if (blockIn instanceof net.minecraft.block.BlockDoor) {
/*     */       
/* 274 */       if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */       {
/* 276 */         if (meta == 0)
/*     */         {
/* 278 */           return 2;
/*     */         }
/*     */         
/* 281 */         if (meta == 2)
/*     */         {
/* 283 */           return 0;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 288 */         if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 290 */           return meta + 1 & 0x3;
/*     */         }
/*     */         
/* 293 */         if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 295 */           return meta + 3 & 0x3;
/*     */         }
/*     */       }
/*     */     
/* 299 */     } else if (blockIn != Blocks.stone_stairs && blockIn != Blocks.oak_stairs && blockIn != Blocks.nether_brick_stairs && blockIn != Blocks.stone_brick_stairs && blockIn != Blocks.sandstone_stairs) {
/*     */       
/* 301 */       if (blockIn == Blocks.ladder) {
/*     */         
/* 303 */         if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */         {
/* 305 */           if (meta == EnumFacing.NORTH.getIndex())
/*     */           {
/* 307 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */           
/* 310 */           if (meta == EnumFacing.SOUTH.getIndex())
/*     */           {
/* 312 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */         }
/* 315 */         else if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 317 */           if (meta == EnumFacing.NORTH.getIndex())
/*     */           {
/* 319 */             return EnumFacing.WEST.getIndex();
/*     */           }
/*     */           
/* 322 */           if (meta == EnumFacing.SOUTH.getIndex())
/*     */           {
/* 324 */             return EnumFacing.EAST.getIndex();
/*     */           }
/*     */           
/* 327 */           if (meta == EnumFacing.WEST.getIndex())
/*     */           {
/* 329 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */           
/* 332 */           if (meta == EnumFacing.EAST.getIndex())
/*     */           {
/* 334 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */         }
/* 337 */         else if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 339 */           if (meta == EnumFacing.NORTH.getIndex())
/*     */           {
/* 341 */             return EnumFacing.EAST.getIndex();
/*     */           }
/*     */           
/* 344 */           if (meta == EnumFacing.SOUTH.getIndex())
/*     */           {
/* 346 */             return EnumFacing.WEST.getIndex();
/*     */           }
/*     */           
/* 349 */           if (meta == EnumFacing.WEST.getIndex())
/*     */           {
/* 351 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */           
/* 354 */           if (meta == EnumFacing.EAST.getIndex())
/*     */           {
/* 356 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */         }
/*     */       
/* 360 */       } else if (blockIn == Blocks.stone_button) {
/*     */         
/* 362 */         if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */         {
/* 364 */           if (meta == 3)
/*     */           {
/* 366 */             return 4;
/*     */           }
/*     */           
/* 369 */           if (meta == 4)
/*     */           {
/* 371 */             return 3;
/*     */           }
/*     */         }
/* 374 */         else if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 376 */           if (meta == 3)
/*     */           {
/* 378 */             return 1;
/*     */           }
/*     */           
/* 381 */           if (meta == 4)
/*     */           {
/* 383 */             return 2;
/*     */           }
/*     */           
/* 386 */           if (meta == 2)
/*     */           {
/* 388 */             return 3;
/*     */           }
/*     */           
/* 391 */           if (meta == 1)
/*     */           {
/* 393 */             return 4;
/*     */           }
/*     */         }
/* 396 */         else if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 398 */           if (meta == 3)
/*     */           {
/* 400 */             return 2;
/*     */           }
/*     */           
/* 403 */           if (meta == 4)
/*     */           {
/* 405 */             return 1;
/*     */           }
/*     */           
/* 408 */           if (meta == 2)
/*     */           {
/* 410 */             return 3;
/*     */           }
/*     */           
/* 413 */           if (meta == 1)
/*     */           {
/* 415 */             return 4;
/*     */           }
/*     */         }
/*     */       
/* 419 */       } else if (blockIn != Blocks.tripwire_hook && !(blockIn instanceof net.minecraft.block.BlockDirectional)) {
/*     */         
/* 421 */         if (blockIn == Blocks.piston || blockIn == Blocks.sticky_piston || blockIn == Blocks.lever || blockIn == Blocks.dispenser)
/*     */         {
/* 423 */           if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */           {
/* 425 */             if (meta == EnumFacing.NORTH.getIndex() || meta == EnumFacing.SOUTH.getIndex())
/*     */             {
/* 427 */               return EnumFacing.getFront(meta).getOpposite().getIndex();
/*     */             }
/*     */           }
/* 430 */           else if (this.coordBaseMode == EnumFacing.WEST)
/*     */           {
/* 432 */             if (meta == EnumFacing.NORTH.getIndex())
/*     */             {
/* 434 */               return EnumFacing.WEST.getIndex();
/*     */             }
/*     */             
/* 437 */             if (meta == EnumFacing.SOUTH.getIndex())
/*     */             {
/* 439 */               return EnumFacing.EAST.getIndex();
/*     */             }
/*     */             
/* 442 */             if (meta == EnumFacing.WEST.getIndex())
/*     */             {
/* 444 */               return EnumFacing.NORTH.getIndex();
/*     */             }
/*     */             
/* 447 */             if (meta == EnumFacing.EAST.getIndex())
/*     */             {
/* 449 */               return EnumFacing.SOUTH.getIndex();
/*     */             }
/*     */           }
/* 452 */           else if (this.coordBaseMode == EnumFacing.EAST)
/*     */           {
/* 454 */             if (meta == EnumFacing.NORTH.getIndex())
/*     */             {
/* 456 */               return EnumFacing.EAST.getIndex();
/*     */             }
/*     */             
/* 459 */             if (meta == EnumFacing.SOUTH.getIndex())
/*     */             {
/* 461 */               return EnumFacing.WEST.getIndex();
/*     */             }
/*     */             
/* 464 */             if (meta == EnumFacing.WEST.getIndex())
/*     */             {
/* 466 */               return EnumFacing.NORTH.getIndex();
/*     */             }
/*     */             
/* 469 */             if (meta == EnumFacing.EAST.getIndex())
/*     */             {
/* 471 */               return EnumFacing.SOUTH.getIndex();
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       } else {
/*     */         
/* 478 */         EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/*     */         
/* 480 */         if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */         {
/* 482 */           if (enumfacing == EnumFacing.SOUTH || enumfacing == EnumFacing.NORTH)
/*     */           {
/* 484 */             return enumfacing.getOpposite().getHorizontalIndex();
/*     */           }
/*     */         }
/* 487 */         else if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 489 */           if (enumfacing == EnumFacing.NORTH)
/*     */           {
/* 491 */             return EnumFacing.WEST.getHorizontalIndex();
/*     */           }
/*     */           
/* 494 */           if (enumfacing == EnumFacing.SOUTH)
/*     */           {
/* 496 */             return EnumFacing.EAST.getHorizontalIndex();
/*     */           }
/*     */           
/* 499 */           if (enumfacing == EnumFacing.WEST)
/*     */           {
/* 501 */             return EnumFacing.NORTH.getHorizontalIndex();
/*     */           }
/*     */           
/* 504 */           if (enumfacing == EnumFacing.EAST)
/*     */           {
/* 506 */             return EnumFacing.SOUTH.getHorizontalIndex();
/*     */           }
/*     */         }
/* 509 */         else if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 511 */           if (enumfacing == EnumFacing.NORTH)
/*     */           {
/* 513 */             return EnumFacing.EAST.getHorizontalIndex();
/*     */           }
/*     */           
/* 516 */           if (enumfacing == EnumFacing.SOUTH)
/*     */           {
/* 518 */             return EnumFacing.WEST.getHorizontalIndex();
/*     */           }
/*     */           
/* 521 */           if (enumfacing == EnumFacing.WEST)
/*     */           {
/* 523 */             return EnumFacing.NORTH.getHorizontalIndex();
/*     */           }
/*     */           
/* 526 */           if (enumfacing == EnumFacing.EAST)
/*     */           {
/* 528 */             return EnumFacing.SOUTH.getHorizontalIndex();
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 533 */     } else if (this.coordBaseMode == EnumFacing.SOUTH) {
/*     */       
/* 535 */       if (meta == 2)
/*     */       {
/* 537 */         return 3;
/*     */       }
/*     */       
/* 540 */       if (meta == 3)
/*     */       {
/* 542 */         return 2;
/*     */       }
/*     */     }
/* 545 */     else if (this.coordBaseMode == EnumFacing.WEST) {
/*     */       
/* 547 */       if (meta == 0)
/*     */       {
/* 549 */         return 2;
/*     */       }
/*     */       
/* 552 */       if (meta == 1)
/*     */       {
/* 554 */         return 3;
/*     */       }
/*     */       
/* 557 */       if (meta == 2)
/*     */       {
/* 559 */         return 0;
/*     */       }
/*     */       
/* 562 */       if (meta == 3)
/*     */       {
/* 564 */         return 1;
/*     */       }
/*     */     }
/* 567 */     else if (this.coordBaseMode == EnumFacing.EAST) {
/*     */       
/* 569 */       if (meta == 0)
/*     */       {
/* 571 */         return 2;
/*     */       }
/*     */       
/* 574 */       if (meta == 1)
/*     */       {
/* 576 */         return 3;
/*     */       }
/*     */       
/* 579 */       if (meta == 2)
/*     */       {
/* 581 */         return 1;
/*     */       }
/*     */       
/* 584 */       if (meta == 3)
/*     */       {
/* 586 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 590 */     return meta;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 595 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 597 */     if (boundingboxIn.isVecInside((Vec3i)blockpos))
/*     */     {
/* 599 */       worldIn.setBlockState(blockpos, blockstateIn, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getBlockStateFromPos(World worldIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 605 */     int i = getXWithOffset(x, z);
/* 606 */     int j = getYWithOffset(y);
/* 607 */     int k = getZWithOffset(x, z);
/* 608 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 609 */     return !boundingboxIn.isVecInside((Vec3i)blockpos) ? Blocks.air.getDefaultState() : worldIn.getBlockState(blockpos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithAir(World worldIn, StructureBoundingBox structurebb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 618 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 620 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 622 */         for (int k = minZ; k <= maxZ; k++)
/*     */         {
/* 624 */           setBlockState(worldIn, Blocks.air.getDefaultState(), j, i, k, structurebb);
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
/*     */   protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
/* 648 */     for (int i = yMin; i <= yMax; i++) {
/*     */       
/* 650 */       for (int j = xMin; j <= xMax; j++) {
/*     */         
/* 652 */         for (int k = zMin; k <= zMax; k++) {
/*     */           
/* 654 */           if (!existingOnly || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air)
/*     */           {
/* 656 */             if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) {
/*     */               
/* 658 */               setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
/*     */             }
/*     */             else {
/*     */               
/* 662 */               setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
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
/*     */   protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector) {
/* 676 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 678 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 680 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 682 */           if (!alwaysReplace || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/*     */             
/* 684 */             blockselector.selectBlocks(rand, j, i, k, (i == minY || i == maxY || j == minX || j == maxX || k == minZ || k == maxZ));
/* 685 */             setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175805_a(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstate1, IBlockState blockstate2, boolean p_175805_13_) {
/* 694 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 696 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 698 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 700 */           if (rand.nextFloat() <= chance && (!p_175805_13_ || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air))
/*     */           {
/* 702 */             if (i != minY && i != maxY && j != minX && j != maxX && k != minZ && k != maxZ) {
/*     */               
/* 704 */               setBlockState(worldIn, blockstate2, j, i, k, boundingboxIn);
/*     */             }
/*     */             else {
/*     */               
/* 708 */               setBlockState(worldIn, blockstate1, j, i, k, boundingboxIn);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomlyPlaceBlock(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int x, int y, int z, IBlockState blockstateIn) {
/* 718 */     if (rand.nextFloat() < chance)
/*     */     {
/* 720 */       setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean p_180777_10_) {
/* 726 */     float f = (maxX - minX + 1);
/* 727 */     float f1 = (maxY - minY + 1);
/* 728 */     float f2 = (maxZ - minZ + 1);
/* 729 */     float f3 = minX + f / 2.0F;
/* 730 */     float f4 = minZ + f2 / 2.0F;
/*     */     
/* 732 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 734 */       float f5 = (i - minY) / f1;
/*     */       
/* 736 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 738 */         float f6 = (j - f3) / f * 0.5F;
/*     */         
/* 740 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 742 */           float f7 = (k - f4) / f2 * 0.5F;
/*     */           
/* 744 */           if (!p_180777_10_ || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/*     */             
/* 746 */             float f8 = f6 * f6 + f5 * f5 + f7 * f7;
/*     */             
/* 748 */             if (f8 <= 1.05F)
/*     */             {
/* 750 */               setBlockState(worldIn, blockstateIn, j, i, k, boundingboxIn);
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
/*     */   protected void clearCurrentPositionBlocksUpwards(World worldIn, int x, int y, int z, StructureBoundingBox structurebb) {
/* 763 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 765 */     if (structurebb.isVecInside((Vec3i)blockpos))
/*     */     {
/* 767 */       while (!worldIn.isAirBlock(blockpos) && blockpos.getY() < 255) {
/*     */         
/* 769 */         worldIn.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/* 770 */         blockpos = blockpos.up();
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
/*     */   protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 782 */     int i = getXWithOffset(x, z);
/* 783 */     int j = getYWithOffset(y);
/* 784 */     int k = getZWithOffset(x, z);
/*     */     
/* 786 */     if (boundingboxIn.isVecInside((Vec3i)new BlockPos(i, j, k)))
/*     */     {
/* 788 */       while ((worldIn.isAirBlock(new BlockPos(i, j, k)) || worldIn.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial().isLiquid()) && j > 1) {
/*     */         
/* 790 */         worldIn.setBlockState(new BlockPos(i, j, k), blockstateIn, 2);
/* 791 */         j--;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean generateChestContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, List<WeightedRandomChestContent> listIn, int max) {
/* 798 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 800 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.chest) {
/*     */       
/* 802 */       IBlockState iblockstate = Blocks.chest.getDefaultState();
/* 803 */       worldIn.setBlockState(blockpos, Blocks.chest.correctFacing(worldIn, blockpos, iblockstate), 2);
/* 804 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 806 */       if (tileentity instanceof net.minecraft.tileentity.TileEntityChest)
/*     */       {
/* 808 */         WeightedRandomChestContent.generateChestContents(rand, listIn, (IInventory)tileentity, max);
/*     */       }
/*     */       
/* 811 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 815 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean generateDispenserContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, int meta, List<WeightedRandomChestContent> listIn, int max) {
/* 821 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 823 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.dispenser) {
/*     */       
/* 825 */       worldIn.setBlockState(blockpos, Blocks.dispenser.getStateFromMeta(getMetadataWithOffset(Blocks.dispenser, meta)), 2);
/* 826 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 828 */       if (tileentity instanceof TileEntityDispenser)
/*     */       {
/* 830 */         WeightedRandomChestContent.generateDispenserContents(rand, listIn, (TileEntityDispenser)tileentity, max);
/*     */       }
/*     */       
/* 833 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 837 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void placeDoorCurrentPosition(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 846 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 848 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos))
/*     */     {
/* 850 */       ItemDoor.placeDoor(worldIn, blockpos, facing.rotateYCCW(), Blocks.oak_door);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
/* 856 */     this.boundingBox.offset(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */   }
/*     */   
/*     */   public static abstract class BlockSelector
/*     */   {
/* 861 */     protected IBlockState blockstate = Blocks.air.getDefaultState();
/*     */ 
/*     */     
/*     */     public abstract void selectBlocks(Random param1Random, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean);
/*     */     
/*     */     public IBlockState getBlockState() {
/* 867 */       return this.blockstate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\gen\structure\StructureComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */