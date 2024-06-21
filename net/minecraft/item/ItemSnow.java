/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSnow
/*    */   extends ItemBlock {
/*    */   public ItemSnow(Block block) {
/* 16 */     super(block);
/* 17 */     setMaxDamage(0);
/* 18 */     setHasSubtypes(true);
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
/* 29 */     if (stack.stackSize == 0)
/*    */     {
/* 31 */       return false;
/*    */     }
/* 33 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*    */     {
/* 35 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 39 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 40 */     Block block = iblockstate.getBlock();
/* 41 */     BlockPos blockpos = pos;
/*    */     
/* 43 */     if ((side != EnumFacing.UP || block != this.block) && !block.isReplaceable(worldIn, pos)) {
/*    */       
/* 45 */       blockpos = pos.offset(side);
/* 46 */       iblockstate = worldIn.getBlockState(blockpos);
/* 47 */       block = iblockstate.getBlock();
/*    */     } 
/*    */     
/* 50 */     if (block == this.block) {
/*    */       
/* 52 */       int i = ((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue();
/*    */       
/* 54 */       if (i <= 7) {
/*    */         
/* 56 */         IBlockState iblockstate1 = iblockstate.withProperty((IProperty)BlockSnow.LAYERS, Integer.valueOf(i + 1));
/* 57 */         AxisAlignedBB axisalignedbb = this.block.getCollisionBoundingBox(worldIn, blockpos, iblockstate1);
/*    */         
/* 59 */         if (axisalignedbb != null && worldIn.checkNoEntityCollision(axisalignedbb) && worldIn.setBlockState(blockpos, iblockstate1, 2)) {
/*    */           
/* 61 */           worldIn.playSoundEffect((blockpos.getX() + 0.5F), (blockpos.getY() + 0.5F), (blockpos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
/* 62 */           stack.stackSize--;
/* 63 */           return true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return super.onItemUse(stack, playerIn, worldIn, blockpos, side, hitX, hitY, hitZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 78 */     return damage;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */