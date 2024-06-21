/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.BlockStandingSign;
/*    */ import net.minecraft.block.BlockWallSign;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSign
/*    */   extends Item {
/*    */   public ItemSign() {
/* 19 */     this.maxStackSize = 16;
/* 20 */     setCreativeTab(CreativeTabs.tabDecorations);
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
/* 31 */     if (side == EnumFacing.DOWN)
/*    */     {
/* 33 */       return false;
/*    */     }
/* 35 */     if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
/*    */     {
/* 37 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 41 */     pos = pos.offset(side);
/*    */     
/* 43 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*    */     {
/* 45 */       return false;
/*    */     }
/* 47 */     if (!Blocks.standing_sign.canPlaceBlockAt(worldIn, pos))
/*    */     {
/* 49 */       return false;
/*    */     }
/* 51 */     if (worldIn.isRemote)
/*    */     {
/* 53 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 57 */     if (side == EnumFacing.UP) {
/*    */       
/* 59 */       int i = MathHelper.floor_double(((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/* 60 */       worldIn.setBlockState(pos, Blocks.standing_sign.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
/*    */     }
/*    */     else {
/*    */       
/* 64 */       worldIn.setBlockState(pos, Blocks.wall_sign.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)side), 3);
/*    */     } 
/*    */     
/* 67 */     stack.stackSize--;
/* 68 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*    */     
/* 70 */     if (tileentity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack))
/*    */     {
/* 72 */       playerIn.openEditSign((TileEntitySign)tileentity);
/*    */     }
/*    */     
/* 75 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */