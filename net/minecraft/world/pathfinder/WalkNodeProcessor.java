/*     */ package net.minecraft.world.pathfinder;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WalkNodeProcessor
/*     */   extends NodeProcessor
/*     */ {
/*     */   private boolean canEnterDoors;
/*     */   private boolean canBreakDoors;
/*     */   private boolean avoidsWater;
/*     */   private boolean canSwim;
/*     */   private boolean shouldAvoidWater;
/*     */   
/*     */   public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
/*  27 */     super.initProcessor(iblockaccessIn, entityIn);
/*  28 */     this.shouldAvoidWater = this.avoidsWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess() {
/*  38 */     super.postProcess();
/*  39 */     this.avoidsWater = this.shouldAvoidWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointTo(Entity entityIn) {
/*     */     int i;
/*  49 */     if (this.canSwim && entityIn.isInWater()) {
/*     */       
/*  51 */       i = (int)(entityIn.getEntityBoundingBox()).minY;
/*  52 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ));
/*     */       
/*  54 */       for (Block block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock(); block == Blocks.flowing_water || block == Blocks.water; block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock()) {
/*     */         
/*  56 */         i++;
/*  57 */         blockpos$mutableblockpos.func_181079_c(MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ));
/*     */       } 
/*     */       
/*  60 */       this.avoidsWater = false;
/*     */     }
/*     */     else {
/*     */       
/*  64 */       i = MathHelper.floor_double((entityIn.getEntityBoundingBox()).minY + 0.5D);
/*     */     } 
/*     */     
/*  67 */     return openPoint(MathHelper.floor_double((entityIn.getEntityBoundingBox()).minX), i, MathHelper.floor_double((entityIn.getEntityBoundingBox()).minZ));
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
/*     */   public PathPoint getPathPointToCoords(Entity entityIn, double x, double y, double target) {
/*  80 */     return openPoint(MathHelper.floor_double(x - (entityIn.width / 2.0F)), MathHelper.floor_double(y), MathHelper.floor_double(target - (entityIn.width / 2.0F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int findPathOptions(PathPoint[] pathOptions, Entity entityIn, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/*  85 */     int i = 0;
/*  86 */     int j = 0;
/*     */     
/*  88 */     if (getVerticalOffset(entityIn, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord) == 1)
/*     */     {
/*  90 */       j = 1;
/*     */     }
/*     */     
/*  93 */     PathPoint pathpoint = getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1, j);
/*  94 */     PathPoint pathpoint1 = getSafePoint(entityIn, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord, j);
/*  95 */     PathPoint pathpoint2 = getSafePoint(entityIn, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord, j);
/*  96 */     PathPoint pathpoint3 = getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1, j);
/*     */     
/*  98 */     if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 100 */       pathOptions[i++] = pathpoint;
/*     */     }
/*     */     
/* 103 */     if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 105 */       pathOptions[i++] = pathpoint1;
/*     */     }
/*     */     
/* 108 */     if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 110 */       pathOptions[i++] = pathpoint2;
/*     */     }
/*     */     
/* 113 */     if (pathpoint3 != null && !pathpoint3.visited && pathpoint3.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 115 */       pathOptions[i++] = pathpoint3;
/*     */     }
/*     */     
/* 118 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathPoint getSafePoint(Entity entityIn, int x, int y, int z, int p_176171_5_) {
/* 126 */     PathPoint pathpoint = null;
/* 127 */     int i = getVerticalOffset(entityIn, x, y, z);
/*     */     
/* 129 */     if (i == 2)
/*     */     {
/* 131 */       return openPoint(x, y, z);
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (i == 1)
/*     */     {
/* 137 */       pathpoint = openPoint(x, y, z);
/*     */     }
/*     */     
/* 140 */     if (pathpoint == null && p_176171_5_ > 0 && i != -3 && i != -4 && getVerticalOffset(entityIn, x, y + p_176171_5_, z) == 1) {
/*     */       
/* 142 */       pathpoint = openPoint(x, y + p_176171_5_, z);
/* 143 */       y += p_176171_5_;
/*     */     } 
/*     */     
/* 146 */     if (pathpoint != null) {
/*     */       
/* 148 */       int j = 0;
/*     */       
/*     */       int k;
/* 151 */       for (k = 0; y > 0; pathpoint = openPoint(x, y, z)) {
/*     */         
/* 153 */         k = getVerticalOffset(entityIn, x, y - 1, z);
/*     */         
/* 155 */         if (this.avoidsWater && k == -1)
/*     */         {
/* 157 */           return null;
/*     */         }
/*     */         
/* 160 */         if (k != 1) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 165 */         if (j++ >= entityIn.getMaxFallHeight())
/*     */         {
/* 167 */           return null;
/*     */         }
/*     */         
/* 170 */         y--;
/*     */         
/* 172 */         if (y <= 0)
/*     */         {
/* 174 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 178 */       if (k == -2)
/*     */       {
/* 180 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 184 */     return pathpoint;
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
/*     */   private int getVerticalOffset(Entity entityIn, int x, int y, int z) {
/* 196 */     return func_176170_a(this.blockaccess, entityIn, x, y, z, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.avoidsWater, this.canBreakDoors, this.canEnterDoors);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_176170_a(IBlockAccess blockaccessIn, Entity entityIn, int x, int y, int z, int sizeX, int sizeY, int sizeZ, boolean avoidWater, boolean breakDoors, boolean enterDoors) {
/* 201 */     boolean flag = false;
/* 202 */     BlockPos blockpos = new BlockPos(entityIn);
/* 203 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 205 */     for (int i = x; i < x + sizeX; i++) {
/*     */       
/* 207 */       for (int j = y; j < y + sizeY; j++) {
/*     */         
/* 209 */         for (int k = z; k < z + sizeZ; k++) {
/*     */           
/* 211 */           blockpos$mutableblockpos.func_181079_c(i, j, k);
/* 212 */           Block block = blockaccessIn.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock();
/*     */           
/* 214 */           if (block.getMaterial() != Material.air) {
/*     */             
/* 216 */             if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
/*     */               
/* 218 */               if (block != Blocks.flowing_water && block != Blocks.water)
/*     */               {
/* 220 */                 if (!enterDoors && block instanceof net.minecraft.block.BlockDoor && block.getMaterial() == Material.wood)
/*     */                 {
/* 222 */                   return 0;
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 227 */                 if (avoidWater)
/*     */                 {
/* 229 */                   return -1;
/*     */                 }
/*     */                 
/* 232 */                 flag = true;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 237 */               flag = true;
/*     */             } 
/*     */             
/* 240 */             if (entityIn.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock() instanceof net.minecraft.block.BlockRailBase) {
/*     */               
/* 242 */               if (!(entityIn.worldObj.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockRailBase) && !(entityIn.worldObj.getBlockState(blockpos.down()).getBlock() instanceof net.minecraft.block.BlockRailBase))
/*     */               {
/* 244 */                 return -3;
/*     */               }
/*     */             }
/* 247 */             else if (!block.isPassable(blockaccessIn, (BlockPos)blockpos$mutableblockpos) && (!breakDoors || !(block instanceof net.minecraft.block.BlockDoor) || block.getMaterial() != Material.wood)) {
/*     */               
/* 249 */               if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockFenceGate || block instanceof net.minecraft.block.BlockWall)
/*     */               {
/* 251 */                 return -3;
/*     */               }
/*     */               
/* 254 */               if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor)
/*     */               {
/* 256 */                 return -4;
/*     */               }
/*     */               
/* 259 */               Material material = block.getMaterial();
/*     */               
/* 261 */               if (material != Material.lava)
/*     */               {
/* 263 */                 return 0;
/*     */               }
/*     */               
/* 266 */               if (!entityIn.isInLava())
/*     */               {
/* 268 */                 return -2;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     return flag ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnterDoors(boolean canEnterDoorsIn) {
/* 281 */     this.canEnterDoors = canEnterDoorsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBreakDoors(boolean canBreakDoorsIn) {
/* 286 */     this.canBreakDoors = canBreakDoorsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAvoidsWater(boolean avoidsWaterIn) {
/* 291 */     this.avoidsWater = avoidsWaterIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCanSwim(boolean canSwimIn) {
/* 296 */     this.canSwim = canSwimIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnterDoors() {
/* 301 */     return this.canEnterDoors;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCanSwim() {
/* 306 */     return this.canSwim;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAvoidsWater() {
/* 311 */     return this.avoidsWater;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\pathfinder\WalkNodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */