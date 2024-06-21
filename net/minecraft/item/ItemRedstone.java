/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item
/*    */ {
/*    */   public ItemRedstone() {
/* 16 */     setCreativeTab(CreativeTabs.tabRedstone);
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
/* 27 */     boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
/* 28 */     BlockPos blockpos = flag ? pos : pos.offset(side);
/*    */     
/* 30 */     if (!playerIn.canPlayerEdit(blockpos, side, stack))
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 36 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */     
/* 38 */     if (!worldIn.canBlockBePlaced(block, blockpos, false, side, null, stack))
/*    */     {
/* 40 */       return false;
/*    */     }
/* 42 */     if (Blocks.redstone_wire.canPlaceBlockAt(worldIn, blockpos)) {
/*    */       
/* 44 */       stack.stackSize--;
/* 45 */       worldIn.setBlockState(blockpos, Blocks.redstone_wire.getDefaultState());
/* 46 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemRedstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */