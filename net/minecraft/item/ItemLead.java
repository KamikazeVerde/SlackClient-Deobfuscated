/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLead
/*    */   extends Item
/*    */ {
/*    */   public ItemLead() {
/* 18 */     setCreativeTab(CreativeTabs.tabTools);
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
/* 29 */     Block block = worldIn.getBlockState(pos).getBlock();
/*    */     
/* 31 */     if (block instanceof net.minecraft.block.BlockFence) {
/*    */       
/* 33 */       if (worldIn.isRemote)
/*    */       {
/* 35 */         return true;
/*    */       }
/*    */ 
/*    */       
/* 39 */       attachToFence(playerIn, worldIn, pos);
/* 40 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean attachToFence(EntityPlayer player, World worldIn, BlockPos fence) {
/* 51 */     EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(worldIn, fence);
/* 52 */     boolean flag = false;
/* 53 */     double d0 = 7.0D;
/* 54 */     int i = fence.getX();
/* 55 */     int j = fence.getY();
/* 56 */     int k = fence.getZ();
/*    */     
/* 58 */     for (EntityLiving entityliving : worldIn.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(i - d0, j - d0, k - d0, i + d0, j + d0, k + d0))) {
/*    */       
/* 60 */       if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == player) {
/*    */         
/* 62 */         if (entityleashknot == null)
/*    */         {
/* 64 */           entityleashknot = EntityLeashKnot.createKnot(worldIn, fence);
/*    */         }
/*    */         
/* 67 */         entityliving.setLeashedToEntity((Entity)entityleashknot, true);
/* 68 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemLead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */