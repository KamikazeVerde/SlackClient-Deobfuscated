/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockOre
/*     */   extends Block
/*     */ {
/*     */   public BlockOre() {
/*  20 */     this(Material.rock.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockOre(MapColor p_i46390_1_) {
/*  25 */     super(Material.rock, p_i46390_1_);
/*  26 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  36 */     return (this == Blocks.coal_ore) ? Items.coal : ((this == Blocks.diamond_ore) ? Items.diamond : ((this == Blocks.lapis_ore) ? Items.dye : ((this == Blocks.emerald_ore) ? Items.emerald : ((this == Blocks.quartz_ore) ? Items.quartz : Item.getItemFromBlock(this)))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  44 */     return (this == Blocks.lapis_ore) ? (4 + random.nextInt(5)) : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  52 */     if (fortune > 0 && Item.getItemFromBlock(this) != getItemDropped((IBlockState)getBlockState().getValidStates().iterator().next(), random, fortune)) {
/*     */       
/*  54 */       int i = random.nextInt(fortune + 2) - 1;
/*     */       
/*  56 */       if (i < 0)
/*     */       {
/*  58 */         i = 0;
/*     */       }
/*     */       
/*  61 */       return quantityDropped(random) * (i + 1);
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return quantityDropped(random);
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
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  77 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/*  79 */     if (getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
/*     */       
/*  81 */       int i = 0;
/*     */       
/*  83 */       if (this == Blocks.coal_ore) {
/*     */         
/*  85 */         i = MathHelper.getRandomIntegerInRange(worldIn.rand, 0, 2);
/*     */       }
/*  87 */       else if (this == Blocks.diamond_ore) {
/*     */         
/*  89 */         i = MathHelper.getRandomIntegerInRange(worldIn.rand, 3, 7);
/*     */       }
/*  91 */       else if (this == Blocks.emerald_ore) {
/*     */         
/*  93 */         i = MathHelper.getRandomIntegerInRange(worldIn.rand, 3, 7);
/*     */       }
/*  95 */       else if (this == Blocks.lapis_ore) {
/*     */         
/*  97 */         i = MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/*     */       }
/*  99 */       else if (this == Blocks.quartz_ore) {
/*     */         
/* 101 */         i = MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/*     */       } 
/*     */       
/* 104 */       dropXpOnBlockBreak(worldIn, pos, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 110 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 119 */     return (this == Blocks.lapis_ore) ? EnumDyeColor.BLUE.getDyeDamage() : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */