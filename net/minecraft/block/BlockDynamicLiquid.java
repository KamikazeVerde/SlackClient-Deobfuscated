/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDynamicLiquid extends BlockLiquid {
/*     */   int adjacentSourceBlocks;
/*     */   
/*     */   protected BlockDynamicLiquid(Material materialIn) {
/*  19 */     super(materialIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeStaticBlock(World worldIn, BlockPos pos, IBlockState currentState) {
/*  24 */     worldIn.setBlockState(pos, getStaticBlock(this.blockMaterial).getDefaultState().withProperty((IProperty)LEVEL, currentState.getValue((IProperty)LEVEL)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  29 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  30 */     int j = 1;
/*     */     
/*  32 */     if (this.blockMaterial == Material.lava && !worldIn.provider.doesWaterVaporize())
/*     */     {
/*  34 */       j = 2;
/*     */     }
/*     */     
/*  37 */     int k = tickRate(worldIn);
/*     */     
/*  39 */     if (i > 0) {
/*     */       
/*  41 */       int l = -100;
/*  42 */       this.adjacentSourceBlocks = 0;
/*     */       
/*  44 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/*  46 */         l = checkAdjacentBlock(worldIn, pos.offset(enumfacing), l);
/*     */       }
/*     */       
/*  49 */       int i1 = l + j;
/*     */       
/*  51 */       if (i1 >= 8 || l < 0)
/*     */       {
/*  53 */         i1 = -1;
/*     */       }
/*     */       
/*  56 */       if (getLevel((IBlockAccess)worldIn, pos.up()) >= 0) {
/*     */         
/*  58 */         int j1 = getLevel((IBlockAccess)worldIn, pos.up());
/*     */         
/*  60 */         if (j1 >= 8) {
/*     */           
/*  62 */           i1 = j1;
/*     */         }
/*     */         else {
/*     */           
/*  66 */           i1 = j1 + 8;
/*     */         } 
/*     */       } 
/*     */       
/*  70 */       if (this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.water) {
/*     */         
/*  72 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */         
/*  74 */         if (iblockstate1.getBlock().getMaterial().isSolid()) {
/*     */           
/*  76 */           i1 = 0;
/*     */         }
/*  78 */         else if (iblockstate1.getBlock().getMaterial() == this.blockMaterial && ((Integer)iblockstate1.getValue((IProperty)LEVEL)).intValue() == 0) {
/*     */           
/*  80 */           i1 = 0;
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       if (this.blockMaterial == Material.lava && i < 8 && i1 < 8 && i1 > i && rand.nextInt(4) != 0)
/*     */       {
/*  86 */         k *= 4;
/*     */       }
/*     */       
/*  89 */       if (i1 == i) {
/*     */         
/*  91 */         placeStaticBlock(worldIn, pos, state);
/*     */       }
/*     */       else {
/*     */         
/*  95 */         i = i1;
/*     */         
/*  97 */         if (i1 < 0)
/*     */         {
/*  99 */           worldIn.setBlockToAir(pos);
/*     */         }
/*     */         else
/*     */         {
/* 103 */           state = state.withProperty((IProperty)LEVEL, Integer.valueOf(i1));
/* 104 */           worldIn.setBlockState(pos, state, 2);
/* 105 */           worldIn.scheduleUpdate(pos, this, k);
/* 106 */           worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 112 */       placeStaticBlock(worldIn, pos, state);
/*     */     } 
/*     */     
/* 115 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */     
/* 117 */     if (canFlowInto(worldIn, pos.down(), iblockstate)) {
/*     */       
/* 119 */       if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.down()).getBlock().getMaterial() == Material.water) {
/*     */         
/* 121 */         worldIn.setBlockState(pos.down(), Blocks.stone.getDefaultState());
/* 122 */         triggerMixEffects(worldIn, pos.down());
/*     */         
/*     */         return;
/*     */       } 
/* 126 */       if (i >= 8)
/*     */       {
/* 128 */         tryFlowInto(worldIn, pos.down(), iblockstate, i);
/*     */       }
/*     */       else
/*     */       {
/* 132 */         tryFlowInto(worldIn, pos.down(), iblockstate, i + 8);
/*     */       }
/*     */     
/* 135 */     } else if (i >= 0 && (i == 0 || isBlocked(worldIn, pos.down(), iblockstate))) {
/*     */       
/* 137 */       Set<EnumFacing> set = getPossibleFlowDirections(worldIn, pos);
/* 138 */       int k1 = i + j;
/*     */       
/* 140 */       if (i >= 8)
/*     */       {
/* 142 */         k1 = 1;
/*     */       }
/*     */       
/* 145 */       if (k1 >= 8) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 150 */       for (EnumFacing enumfacing1 : set)
/*     */       {
/* 152 */         tryFlowInto(worldIn, pos.offset(enumfacing1), worldIn.getBlockState(pos.offset(enumfacing1)), k1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryFlowInto(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 159 */     if (canFlowInto(worldIn, pos, state)) {
/*     */       
/* 161 */       if (state.getBlock() != Blocks.air)
/*     */       {
/* 163 */         if (this.blockMaterial == Material.lava) {
/*     */           
/* 165 */           triggerMixEffects(worldIn, pos);
/*     */         }
/*     */         else {
/*     */           
/* 169 */           state.getBlock().dropBlockAsItem(worldIn, pos, state, 0);
/*     */         } 
/*     */       }
/*     */       
/* 173 */       worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(level)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_176374_a(World worldIn, BlockPos pos, int distance, EnumFacing calculateFlowCost) {
/* 179 */     int i = 1000;
/*     */     
/* 181 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 183 */       if (enumfacing != calculateFlowCost) {
/*     */         
/* 185 */         BlockPos blockpos = pos.offset(enumfacing);
/* 186 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 188 */         if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getBlock().getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */           
/* 190 */           if (!isBlocked(worldIn, blockpos.down(), iblockstate))
/*     */           {
/* 192 */             return distance;
/*     */           }
/*     */           
/* 195 */           if (distance < 4) {
/*     */             
/* 197 */             int j = func_176374_a(worldIn, blockpos, distance + 1, enumfacing.getOpposite());
/*     */             
/* 199 */             if (j < i)
/*     */             {
/* 201 */               i = j;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<EnumFacing> getPossibleFlowDirections(World worldIn, BlockPos pos) {
/* 213 */     int i = 1000;
/* 214 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 216 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 218 */       BlockPos blockpos = pos.offset(enumfacing);
/* 219 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 221 */       if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getBlock().getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */         int j;
/*     */ 
/*     */         
/* 225 */         if (isBlocked(worldIn, blockpos.down(), worldIn.getBlockState(blockpos.down()))) {
/*     */           
/* 227 */           j = func_176374_a(worldIn, blockpos, 1, enumfacing.getOpposite());
/*     */         }
/*     */         else {
/*     */           
/* 231 */           j = 0;
/*     */         } 
/*     */         
/* 234 */         if (j < i)
/*     */         {
/* 236 */           set.clear();
/*     */         }
/*     */         
/* 239 */         if (j <= i) {
/*     */           
/* 241 */           set.add(enumfacing);
/* 242 */           i = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos, IBlockState state) {
/* 252 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 253 */     return (!(block instanceof BlockDoor) && block != Blocks.standing_sign && block != Blocks.ladder && block != Blocks.reeds) ? ((block.blockMaterial == Material.portal) ? true : block.blockMaterial.blocksMovement()) : true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int checkAdjacentBlock(World worldIn, BlockPos pos, int currentMinLevel) {
/* 258 */     int i = getLevel((IBlockAccess)worldIn, pos);
/*     */     
/* 260 */     if (i < 0)
/*     */     {
/* 262 */       return currentMinLevel;
/*     */     }
/*     */ 
/*     */     
/* 266 */     if (i == 0)
/*     */     {
/* 268 */       this.adjacentSourceBlocks++;
/*     */     }
/*     */     
/* 271 */     if (i >= 8)
/*     */     {
/* 273 */       i = 0;
/*     */     }
/*     */     
/* 276 */     return (currentMinLevel >= 0 && i >= currentMinLevel) ? currentMinLevel : i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canFlowInto(World worldIn, BlockPos pos, IBlockState state) {
/* 282 */     Material material = state.getBlock().getMaterial();
/* 283 */     return (material != this.blockMaterial && material != Material.lava && !isBlocked(worldIn, pos, state));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 288 */     if (!checkForMixing(worldIn, pos, state))
/*     */     {
/* 290 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockDynamicLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */