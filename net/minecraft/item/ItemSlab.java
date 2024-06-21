/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSlab
/*     */   extends ItemBlock
/*     */ {
/*     */   private final BlockSlab singleSlab;
/*     */   private final BlockSlab doubleSlab;
/*     */   
/*     */   public ItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
/*  19 */     super(block);
/*  20 */     this.singleSlab = singleSlab;
/*  21 */     this.doubleSlab = doubleSlab;
/*  22 */     setMaxDamage(0);
/*  23 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/*  32 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  41 */     return this.singleSlab.getUnlocalizedName(stack.getMetadata());
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
/*  52 */     if (stack.stackSize == 0)
/*     */     {
/*  54 */       return false;
/*     */     }
/*  56 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  62 */     Object object = this.singleSlab.getVariant(stack);
/*  63 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  65 */     if (iblockstate.getBlock() == this.singleSlab) {
/*     */       
/*  67 */       IProperty iproperty = this.singleSlab.getVariantProperty();
/*  68 */       Comparable comparable = iblockstate.getValue(iproperty);
/*  69 */       BlockSlab.EnumBlockHalf blockslab$enumblockhalf = (BlockSlab.EnumBlockHalf)iblockstate.getValue((IProperty)BlockSlab.HALF);
/*     */       
/*  71 */       if (((side == EnumFacing.UP && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM) || (side == EnumFacing.DOWN && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.TOP)) && comparable == object) {
/*     */         
/*  73 */         IBlockState iblockstate1 = this.doubleSlab.getDefaultState().withProperty(iproperty, comparable);
/*     */         
/*  75 */         if (worldIn.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3)) {
/*     */           
/*  77 */           worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getFrequency() * 0.8F);
/*  78 */           stack.stackSize--;
/*     */         } 
/*     */         
/*  81 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return tryPlace(stack, worldIn, pos.offset(side), object) ? true : super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/*  91 */     BlockPos blockpos = pos;
/*  92 */     IProperty iproperty = this.singleSlab.getVariantProperty();
/*  93 */     Object object = this.singleSlab.getVariant(stack);
/*  94 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  96 */     if (iblockstate.getBlock() == this.singleSlab) {
/*     */       
/*  98 */       boolean flag = (iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP);
/*     */       
/* 100 */       if (((side == EnumFacing.UP && !flag) || (side == EnumFacing.DOWN && flag)) && object == iblockstate.getValue(iproperty))
/*     */       {
/* 102 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     pos = pos.offset(side);
/* 107 */     IBlockState iblockstate1 = worldIn.getBlockState(pos);
/* 108 */     return (iblockstate1.getBlock() == this.singleSlab && object == iblockstate1.getValue(iproperty)) ? true : super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean tryPlace(ItemStack stack, World worldIn, BlockPos pos, Object variantInStack) {
/* 113 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 115 */     if (iblockstate.getBlock() == this.singleSlab) {
/*     */       
/* 117 */       Comparable comparable = iblockstate.getValue(this.singleSlab.getVariantProperty());
/*     */       
/* 119 */       if (comparable == variantInStack) {
/*     */         
/* 121 */         IBlockState iblockstate1 = this.doubleSlab.getDefaultState().withProperty(this.singleSlab.getVariantProperty(), comparable);
/*     */         
/* 123 */         if (worldIn.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3)) {
/*     */           
/* 125 */           worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getFrequency() * 0.8F);
/* 126 */           stack.stackSize--;
/*     */         } 
/*     */         
/* 129 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */