/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDoor;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemDoor
/*    */   extends Item {
/*    */   private Block block;
/*    */   
/*    */   public ItemDoor(Block block) {
/* 18 */     this.block = block;
/* 19 */     setCreativeTab(CreativeTabs.tabRedstone);
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
/* 30 */     if (side != EnumFacing.UP)
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 36 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 37 */     Block block = iblockstate.getBlock();
/*    */     
/* 39 */     if (!block.isReplaceable(worldIn, pos))
/*    */     {
/* 41 */       pos = pos.offset(side);
/*    */     }
/*    */     
/* 44 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*    */     {
/* 46 */       return false;
/*    */     }
/* 48 */     if (!this.block.canPlaceBlockAt(worldIn, pos))
/*    */     {
/* 50 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 54 */     placeDoor(worldIn, pos, EnumFacing.fromAngle(playerIn.rotationYaw), this.block);
/* 55 */     stack.stackSize--;
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door) {
/* 63 */     BlockPos blockpos = pos.offset(facing.rotateY());
/* 64 */     BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
/* 65 */     int i = (worldIn.getBlockState(blockpos1).getBlock().isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).getBlock().isNormalCube() ? 1 : 0);
/* 66 */     int j = (worldIn.getBlockState(blockpos).getBlock().isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).getBlock().isNormalCube() ? 1 : 0);
/* 67 */     boolean flag = (worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door);
/* 68 */     boolean flag1 = (worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door);
/* 69 */     boolean flag2 = false;
/*    */     
/* 71 */     if ((flag && !flag1) || j > i)
/*    */     {
/* 73 */       flag2 = true;
/*    */     }
/*    */     
/* 76 */     BlockPos blockpos2 = pos.up();
/* 77 */     IBlockState iblockstate = door.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)facing).withProperty((IProperty)BlockDoor.HINGE, flag2 ? (Comparable)BlockDoor.EnumHingePosition.RIGHT : (Comparable)BlockDoor.EnumHingePosition.LEFT);
/* 78 */     worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.LOWER), 2);
/* 79 */     worldIn.setBlockState(blockpos2, iblockstate.withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER), 2);
/* 80 */     worldIn.notifyNeighborsOfStateChange(pos, door);
/* 81 */     worldIn.notifyNeighborsOfStateChange(blockpos2, door);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */