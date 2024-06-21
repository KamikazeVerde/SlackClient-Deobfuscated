/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemDye extends Item {
/*  21 */   public static final int[] dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*     */ 
/*     */   
/*     */   public ItemDye() {
/*  25 */     setHasSubtypes(true);
/*  26 */     setMaxDamage(0);
/*  27 */     setCreativeTab(CreativeTabs.tabMaterials);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  36 */     int i = stack.getMetadata();
/*  37 */     return getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  48 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*     */     {
/*  50 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  54 */     EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */     
/*  56 */     if (enumdyecolor == EnumDyeColor.WHITE) {
/*     */       
/*  58 */       if (applyBonemeal(stack, worldIn, pos))
/*     */       {
/*  60 */         if (!worldIn.isRemote)
/*     */         {
/*  62 */           worldIn.playAuxSFX(2005, pos, 0);
/*     */         }
/*     */         
/*  65 */         return true;
/*     */       }
/*     */     
/*  68 */     } else if (enumdyecolor == EnumDyeColor.BROWN) {
/*     */       
/*  70 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*  71 */       Block block = iblockstate.getBlock();
/*     */       
/*  73 */       if (block == Blocks.log && iblockstate.getValue((IProperty)BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
/*     */         
/*  75 */         if (side == EnumFacing.DOWN)
/*     */         {
/*  77 */           return false;
/*     */         }
/*     */         
/*  80 */         if (side == EnumFacing.UP)
/*     */         {
/*  82 */           return false;
/*     */         }
/*     */         
/*  85 */         pos = pos.offset(side);
/*     */         
/*  87 */         if (worldIn.isAirBlock(pos)) {
/*     */           
/*  89 */           IBlockState iblockstate1 = Blocks.cocoa.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, (EntityLivingBase)playerIn);
/*  90 */           worldIn.setBlockState(pos, iblockstate1, 2);
/*     */           
/*  92 */           if (!playerIn.capabilities.isCreativeMode)
/*     */           {
/*  94 */             stack.stackSize--;
/*     */           }
/*     */         } 
/*     */         
/*  98 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
/* 108 */     IBlockState iblockstate = worldIn.getBlockState(target);
/*     */     
/* 110 */     if (iblockstate.getBlock() instanceof IGrowable) {
/*     */       
/* 112 */       IGrowable igrowable = (IGrowable)iblockstate.getBlock();
/*     */       
/* 114 */       if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
/*     */         
/* 116 */         if (!worldIn.isRemote) {
/*     */           
/* 118 */           if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
/*     */           {
/* 120 */             igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
/*     */           }
/*     */           
/* 123 */           stack.stackSize--;
/*     */         } 
/*     */         
/* 126 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
/* 135 */     if (amount == 0)
/*     */     {
/* 137 */       amount = 15;
/*     */     }
/*     */     
/* 140 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 142 */     if (block.getMaterial() != Material.air) {
/*     */       
/* 144 */       block.setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*     */       
/* 146 */       for (int i = 0; i < amount; i++) {
/*     */         
/* 148 */         double d0 = itemRand.nextGaussian() * 0.02D;
/* 149 */         double d1 = itemRand.nextGaussian() * 0.02D;
/* 150 */         double d2 = itemRand.nextGaussian() * 0.02D;
/* 151 */         worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (pos.getX() + itemRand.nextFloat()), pos.getY() + itemRand.nextFloat() * block.getBlockBoundsMaxY(), (pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/* 161 */     if (target instanceof EntitySheep) {
/*     */       
/* 163 */       EntitySheep entitysheep = (EntitySheep)target;
/* 164 */       EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */       
/* 166 */       if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
/*     */         
/* 168 */         entitysheep.setFleeceColor(enumdyecolor);
/* 169 */         stack.stackSize--;
/*     */       } 
/*     */       
/* 172 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 187 */     for (int i = 0; i < 16; i++)
/*     */     {
/* 189 */       subItems.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemDye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */