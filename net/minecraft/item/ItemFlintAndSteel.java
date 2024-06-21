/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemFlintAndSteel
/*    */   extends Item {
/*    */   public ItemFlintAndSteel() {
/* 15 */     this.maxStackSize = 1;
/* 16 */     setMaxDamage(64);
/* 17 */     setCreativeTab(CreativeTabs.tabTools);
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
/* 28 */     pos = pos.offset(side);
/*    */     
/* 30 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 36 */     if (worldIn.getBlockState(pos).getBlock().getMaterial() == Material.air) {
/*    */       
/* 38 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
/* 39 */       worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
/*    */     } 
/*    */     
/* 42 */     stack.damageItem(1, (EntityLivingBase)playerIn);
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemFlintAndSteel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */