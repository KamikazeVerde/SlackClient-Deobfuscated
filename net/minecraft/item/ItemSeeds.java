/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeeds
/*    */   extends Item
/*    */ {
/*    */   private Block crops;
/*    */   private Block soilBlockID;
/*    */   
/*    */   public ItemSeeds(Block crops, Block soil) {
/* 19 */     this.crops = crops;
/* 20 */     this.soilBlockID = soil;
/* 21 */     setCreativeTab(CreativeTabs.tabMaterials);
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
/* 32 */     if (side != EnumFacing.UP)
/*    */     {
/* 34 */       return false;
/*    */     }
/* 36 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*    */     {
/* 38 */       return false;
/*    */     }
/* 40 */     if (worldIn.getBlockState(pos).getBlock() == this.soilBlockID && worldIn.isAirBlock(pos.up())) {
/*    */       
/* 42 */       worldIn.setBlockState(pos.up(), this.crops.getDefaultState());
/* 43 */       stack.stackSize--;
/* 44 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemSeeds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */