/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBed;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBed extends Item {
/*    */   public ItemBed() {
/* 18 */     setCreativeTab(CreativeTabs.tabDecorations);
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
/* 29 */     if (worldIn.isRemote)
/*    */     {
/* 31 */       return true;
/*    */     }
/* 33 */     if (side != EnumFacing.UP)
/*    */     {
/* 35 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 39 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 40 */     Block block = iblockstate.getBlock();
/* 41 */     boolean flag = block.isReplaceable(worldIn, pos);
/*    */     
/* 43 */     if (!flag)
/*    */     {
/* 45 */       pos = pos.up();
/*    */     }
/*    */     
/* 48 */     int i = MathHelper.floor_double((playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 49 */     EnumFacing enumfacing = EnumFacing.getHorizontal(i);
/* 50 */     BlockPos blockpos = pos.offset(enumfacing);
/*    */     
/* 52 */     if (playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(blockpos, side, stack)) {
/*    */       
/* 54 */       boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
/* 55 */       boolean flag2 = (flag || worldIn.isAirBlock(pos));
/* 56 */       boolean flag3 = (flag1 || worldIn.isAirBlock(blockpos));
/*    */       
/* 58 */       if (flag2 && flag3 && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos.down())) {
/*    */         
/* 60 */         IBlockState iblockstate1 = Blocks.bed.getDefaultState().withProperty((IProperty)BlockBed.OCCUPIED, Boolean.FALSE).withProperty((IProperty)BlockBed.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.FOOT);
/*    */         
/* 62 */         if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/*    */           
/* 64 */           IBlockState iblockstate2 = iblockstate1.withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.HEAD);
/* 65 */           worldIn.setBlockState(blockpos, iblockstate2, 3);
/*    */         } 
/*    */         
/* 68 */         stack.stackSize--;
/* 69 */         return true;
/*    */       } 
/*    */ 
/*    */       
/* 73 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */