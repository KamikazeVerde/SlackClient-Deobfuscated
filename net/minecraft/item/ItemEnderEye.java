/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.block.BlockEndPortalFrame;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEnderEye extends Item {
/*     */   public ItemEnderEye() {
/*  20 */     setCreativeTab(CreativeTabs.tabMisc);
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
/*  31 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  33 */     if (playerIn.canPlayerEdit(pos.offset(side), side, stack) && iblockstate.getBlock() == Blocks.end_portal_frame && !((Boolean)iblockstate.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*     */       
/*  35 */       if (!worldIn.isRemote) {
/*  36 */         worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.TRUE), 2);
/*  37 */         worldIn.updateComparatorOutputLevel(pos, Blocks.end_portal_frame);
/*  38 */         stack.stackSize--;
/*     */         
/*  40 */         for (int i = 0; i < 16; i++) {
/*  41 */           double d0 = (pos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  42 */           double d1 = (pos.getY() + 0.8125F);
/*  43 */           double d2 = (pos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  44 */           double d3 = 0.0D;
/*  45 */           double d4 = 0.0D;
/*  46 */           double d5 = 0.0D;
/*  47 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */         } 
/*     */         
/*  50 */         EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)BlockEndPortalFrame.FACING);
/*  51 */         int l = 0;
/*  52 */         int j = 0;
/*  53 */         boolean flag1 = false;
/*  54 */         boolean flag = true;
/*  55 */         EnumFacing enumfacing1 = enumfacing.rotateY();
/*     */         
/*  57 */         for (int k = -2; k <= 2; k++) {
/*  58 */           BlockPos blockpos1 = pos.offset(enumfacing1, k);
/*  59 */           IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */           
/*  61 */           if (iblockstate1.getBlock() == Blocks.end_portal_frame) {
/*  62 */             if (!((Boolean)iblockstate1.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  63 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*  67 */             j = k;
/*     */             
/*  69 */             if (!flag1) {
/*  70 */               l = k;
/*  71 */               flag1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  76 */         if (flag && j == l + 2) {
/*  77 */           BlockPos blockpos = pos.offset(enumfacing, 4);
/*     */           
/*  79 */           for (int i1 = l; i1 <= j; i1++) {
/*  80 */             BlockPos blockpos2 = blockpos.offset(enumfacing1, i1);
/*  81 */             IBlockState iblockstate3 = worldIn.getBlockState(blockpos2);
/*     */             
/*  83 */             if (iblockstate3.getBlock() != Blocks.end_portal_frame || !((Boolean)iblockstate3.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  84 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*  89 */           for (int j1 = l - 1; j1 <= j + 1; j1 += 4) {
/*  90 */             blockpos = pos.offset(enumfacing1, j1);
/*     */             
/*  92 */             for (int l1 = 1; l1 <= 3; l1++) {
/*  93 */               BlockPos blockpos3 = blockpos.offset(enumfacing, l1);
/*  94 */               IBlockState iblockstate2 = worldIn.getBlockState(blockpos3);
/*     */               
/*  96 */               if (iblockstate2.getBlock() != Blocks.end_portal_frame || !((Boolean)iblockstate2.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  97 */                 flag = false;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 103 */           if (flag) {
/* 104 */             for (int k1 = l; k1 <= j; k1++) {
/* 105 */               blockpos = pos.offset(enumfacing1, k1);
/*     */               
/* 107 */               for (int i2 = 1; i2 <= 3; i2++) {
/* 108 */                 BlockPos blockpos4 = blockpos.offset(enumfacing, i2);
/* 109 */                 worldIn.setBlockState(blockpos4, Blocks.end_portal.getDefaultState(), 2);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 116 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 129 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, false);
/*     */     
/* 131 */     if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && worldIn.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.end_portal_frame)
/*     */     {
/* 133 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 137 */     if (!worldIn.isRemote) {
/*     */       
/* 139 */       BlockPos blockpos = worldIn.getStrongholdPos("Stronghold", new BlockPos((Entity)playerIn));
/*     */       
/* 141 */       if (blockpos != null) {
/*     */         
/* 143 */         EntityEnderEye entityendereye = new EntityEnderEye(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);
/* 144 */         entityendereye.moveTowards(blockpos);
/* 145 */         worldIn.spawnEntityInWorld((Entity)entityendereye);
/* 146 */         worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 147 */         worldIn.playAuxSFXAtEntity(null, 1002, new BlockPos((Entity)playerIn), 0);
/*     */         
/* 149 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 151 */           itemStackIn.stackSize--;
/*     */         }
/*     */         
/* 154 */         playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return itemStackIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemEnderEye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */