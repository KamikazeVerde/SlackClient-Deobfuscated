/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneOre
/*     */   extends Block
/*     */ {
/*     */   private final boolean isOn;
/*     */   
/*     */   public BlockRedstoneOre(boolean isOn) {
/*  23 */     super(Material.rock);
/*     */     
/*  25 */     if (isOn)
/*     */     {
/*  27 */       setTickRandomly(true);
/*     */     }
/*     */     
/*  30 */     this.isOn = isOn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  38 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  43 */     activate(worldIn, pos);
/*  44 */     super.onBlockClicked(worldIn, pos, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
/*  52 */     activate(worldIn, pos);
/*  53 */     super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  58 */     activate(worldIn, pos);
/*  59 */     return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   private void activate(World worldIn, BlockPos pos) {
/*  64 */     spawnParticles(worldIn, pos);
/*     */     
/*  66 */     if (this == Blocks.redstone_ore)
/*     */     {
/*  68 */       worldIn.setBlockState(pos, Blocks.lit_redstone_ore.getDefaultState());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  74 */     if (this == Blocks.lit_redstone_ore)
/*     */     {
/*  76 */       worldIn.setBlockState(pos, Blocks.redstone_ore.getDefaultState());
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
/*  87 */     return Items.redstone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  95 */     return quantityDropped(random) + random.nextInt(fortune + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 103 */     return 4 + random.nextInt(2);
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
/* 114 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/* 116 */     if (getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
/*     */       
/* 118 */       int i = 1 + worldIn.rand.nextInt(5);
/* 119 */       dropXpOnBlockBreak(worldIn, pos, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 125 */     if (this.isOn)
/*     */     {
/* 127 */       spawnParticles(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnParticles(World worldIn, BlockPos pos) {
/* 133 */     Random random = worldIn.rand;
/* 134 */     double d0 = 0.0625D;
/*     */     
/* 136 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 138 */       double d1 = (pos.getX() + random.nextFloat());
/* 139 */       double d2 = (pos.getY() + random.nextFloat());
/* 140 */       double d3 = (pos.getZ() + random.nextFloat());
/*     */       
/* 142 */       if (i == 0 && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube())
/*     */       {
/* 144 */         d2 = pos.getY() + d0 + 1.0D;
/*     */       }
/*     */       
/* 147 */       if (i == 1 && !worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube())
/*     */       {
/* 149 */         d2 = pos.getY() - d0;
/*     */       }
/*     */       
/* 152 */       if (i == 2 && !worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube())
/*     */       {
/* 154 */         d3 = pos.getZ() + d0 + 1.0D;
/*     */       }
/*     */       
/* 157 */       if (i == 3 && !worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube())
/*     */       {
/* 159 */         d3 = pos.getZ() - d0;
/*     */       }
/*     */       
/* 162 */       if (i == 4 && !worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube())
/*     */       {
/* 164 */         d1 = pos.getX() + d0 + 1.0D;
/*     */       }
/*     */       
/* 167 */       if (i == 5 && !worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube())
/*     */       {
/* 169 */         d1 = pos.getX() - d0;
/*     */       }
/*     */       
/* 172 */       if (d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1))
/*     */       {
/* 174 */         worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack createStackedBlock(IBlockState state) {
/* 181 */     return new ItemStack(Blocks.redstone_ore);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockRedstoneOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */