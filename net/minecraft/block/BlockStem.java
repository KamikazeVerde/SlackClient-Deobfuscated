/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStem
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  24 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(EnumFacing p_apply_1_)
/*     */         {
/*  29 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*     */   
/*     */   private final Block crop;
/*     */   
/*     */   protected BlockStem(Block crop) {
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  37 */     this.crop = crop;
/*  38 */     setTickRandomly(true);
/*  39 */     float f = 0.125F;
/*  40 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  41 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  50 */     state = state.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */     
/*  52 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  54 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*     */         
/*  56 */         state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  61 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  69 */     return (ground == Blocks.farmland);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  74 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  76 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */       
/*  78 */       float f = BlockCrops.getGrowthChance(this, worldIn, pos);
/*     */       
/*  80 */       if (rand.nextInt((int)(25.0F / f) + 1) == 0) {
/*     */         
/*  82 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  84 */         if (i < 7) {
/*     */           
/*  86 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  87 */           worldIn.setBlockState(pos, state, 2);
/*     */         }
/*     */         else {
/*     */           
/*  91 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */             
/*  93 */             if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*  99 */           pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
/* 100 */           Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */           
/* 102 */           if ((worldIn.getBlockState(pos).getBlock()).blockMaterial == Material.air && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass))
/*     */           {
/* 104 */             worldIn.setBlockState(pos, this.crop.getDefaultState());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void growStem(World worldIn, BlockPos pos, IBlockState state) {
/* 113 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/* 114 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(Math.min(7, i))), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 119 */     if (state.getBlock() != this)
/*     */     {
/* 121 */       return super.getRenderColor(state);
/*     */     }
/*     */ 
/*     */     
/* 125 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/* 126 */     int j = i * 32;
/* 127 */     int k = 255 - i * 8;
/* 128 */     int l = i * 4;
/* 129 */     return j << 16 | k << 8 | l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 135 */     return getRenderColor(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 143 */     float f = 0.125F;
/* 144 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 149 */     this.maxY = ((((Integer)worldIn.getBlockState(pos).getValue((IProperty)AGE)).intValue() * 2 + 2) / 16.0F);
/* 150 */     float f = 0.125F;
/* 151 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
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
/* 162 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/* 164 */     if (!worldIn.isRemote) {
/*     */       
/* 166 */       Item item = getSeedItem();
/*     */       
/* 168 */       if (item != null) {
/*     */         
/* 170 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/* 172 */         for (int j = 0; j < 3; j++) {
/*     */           
/* 174 */           if (worldIn.rand.nextInt(15) <= i)
/*     */           {
/* 176 */             spawnAsEntity(worldIn, pos, new ItemStack(item));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getSeedItem() {
/* 185 */     return (this.crop == Blocks.pumpkin) ? Items.pumpkin_seeds : ((this.crop == Blocks.melon_block) ? Items.melon_seeds : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 195 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 203 */     Item item = getSeedItem();
/* 204 */     return (item != null) ? item : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 212 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() != 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 222 */     growStem(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 230 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 238 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 243 */     return new BlockState(this, new IProperty[] { (IProperty)AGE, (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockStem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */