/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemReed
/*    */   extends Item {
/*    */   private Block block;
/*    */   
/*    */   public ItemReed(Block block) {
/* 19 */     this.block = block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 30 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 31 */     Block block = iblockstate.getBlock();
/*    */     
/* 33 */     if (block == Blocks.snow_layer && ((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue() < 1) {
/*    */       
/* 35 */       side = EnumFacing.UP;
/*    */     }
/* 37 */     else if (!block.isReplaceable(worldIn, pos)) {
/*    */       
/* 39 */       pos = pos.offset(side);
/*    */     } 
/*    */     
/* 42 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*    */     {
/* 44 */       return false;
/*    */     }
/* 46 */     if (stack.stackSize == 0)
/*    */     {
/* 48 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 52 */     if (worldIn.canBlockBePlaced(this.block, pos, false, side, null, stack)) {
/*    */       
/* 54 */       IBlockState iblockstate1 = this.block.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, (EntityLivingBase)playerIn);
/*    */       
/* 56 */       if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/*    */         
/* 58 */         iblockstate1 = worldIn.getBlockState(pos);
/*    */         
/* 60 */         if (iblockstate1.getBlock() == this.block) {
/*    */           
/* 62 */           ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack);
/* 63 */           iblockstate1.getBlock().onBlockPlacedBy(worldIn, pos, iblockstate1, (EntityLivingBase)playerIn, stack);
/*    */         } 
/*    */         
/* 66 */         worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
/* 67 */         stack.stackSize--;
/* 68 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */