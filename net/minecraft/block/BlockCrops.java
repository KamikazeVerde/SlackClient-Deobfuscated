/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCrops
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  19 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*     */ 
/*     */   
/*     */   protected BlockCrops() {
/*  23 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  24 */     setTickRandomly(true);
/*  25 */     float f = 0.5F;
/*  26 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  27 */     setCreativeTab(null);
/*  28 */     setHardness(0.0F);
/*  29 */     setStepSound(soundTypeGrass);
/*  30 */     disableStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  38 */     return (ground == Blocks.farmland);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  43 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  45 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */       
/*  47 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/*  49 */       if (i < 7) {
/*     */         
/*  51 */         float f = getGrowthChance(this, worldIn, pos);
/*     */         
/*  53 */         if (rand.nextInt((int)(25.0F / f) + 1) == 0)
/*     */         {
/*  55 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state) {
/*  63 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/*     */     
/*  65 */     if (i > 7)
/*     */     {
/*  67 */       i = 7;
/*     */     }
/*     */     
/*  70 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
/*  75 */     float f = 1.0F;
/*  76 */     BlockPos blockpos = pos.down();
/*     */     
/*  78 */     for (int i = -1; i <= 1; i++) {
/*     */       
/*  80 */       for (int j = -1; j <= 1; j++) {
/*     */         
/*  82 */         float f1 = 0.0F;
/*  83 */         IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
/*     */         
/*  85 */         if (iblockstate.getBlock() == Blocks.farmland) {
/*     */           
/*  87 */           f1 = 1.0F;
/*     */           
/*  89 */           if (((Integer)iblockstate.getValue((IProperty)BlockFarmland.MOISTURE)).intValue() > 0)
/*     */           {
/*  91 */             f1 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/*  95 */         if (i != 0 || j != 0)
/*     */         {
/*  97 */           f1 /= 4.0F;
/*     */         }
/*     */         
/* 100 */         f += f1;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     BlockPos blockpos1 = pos.north();
/* 105 */     BlockPos blockpos2 = pos.south();
/* 106 */     BlockPos blockpos3 = pos.west();
/* 107 */     BlockPos blockpos4 = pos.east();
/* 108 */     boolean flag = (blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock());
/* 109 */     boolean flag1 = (blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock());
/*     */     
/* 111 */     if (flag && flag1) {
/*     */       
/* 113 */       f /= 2.0F;
/*     */     }
/*     */     else {
/*     */       
/* 117 */       boolean flag2 = (blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock());
/*     */       
/* 119 */       if (flag2)
/*     */       {
/* 121 */         f /= 2.0F;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 130 */     return ((worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getSeed() {
/* 135 */     return Items.wheat_seeds;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getCrop() {
/* 140 */     return Items.wheat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 151 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     
/* 153 */     if (!worldIn.isRemote) {
/*     */       
/* 155 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/* 157 */       if (i >= 7) {
/*     */         
/* 159 */         int j = 3 + fortune;
/*     */         
/* 161 */         for (int k = 0; k < j; k++) {
/*     */           
/* 163 */           if (worldIn.rand.nextInt(15) <= i)
/*     */           {
/* 165 */             spawnAsEntity(worldIn, pos, new ItemStack(getSeed(), 1, 0));
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
/* 179 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() == 7) ? getCrop() : getSeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 187 */     return getSeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 195 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() < 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 205 */     grow(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 213 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 221 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 226 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockCrops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */