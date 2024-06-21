/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockLilyPad
/*    */   extends BlockBush
/*    */ {
/*    */   protected BlockLilyPad() {
/* 19 */     float f = 0.5F;
/* 20 */     float f1 = 0.015625F;
/* 21 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/* 22 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 32 */     if (collidingEntity == null || !(collidingEntity instanceof net.minecraft.entity.item.EntityBoat))
/*    */     {
/* 34 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 40 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockColor() {
/* 45 */     return 7455580;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderColor(IBlockState state) {
/* 50 */     return 7455580;
/*    */   }
/*    */ 
/*    */   
/*    */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 55 */     return 2129968;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground) {
/* 63 */     return (ground == Blocks.water);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 68 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*    */       
/* 70 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 71 */       return (iblockstate.getBlock().getMaterial() == Material.water && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0);
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 84 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockLilyPad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */