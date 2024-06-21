/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDirt;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemHoe extends Item {
/*    */   protected Item.ToolMaterial theToolMaterial;
/*    */   
/*    */   public ItemHoe(Item.ToolMaterial material) {
/* 20 */     this.theToolMaterial = material;
/* 21 */     this.maxStackSize = 1;
/* 22 */     setMaxDamage(material.getMaxUses());
/* 23 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 36 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*    */     {
/* 38 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 42 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 43 */     Block block = iblockstate.getBlock();
/*    */     
/* 45 */     if (side != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air) {
/*    */       
/* 47 */       if (block == Blocks.grass)
/*    */       {
/* 49 */         return useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
/*    */       }
/*    */       
/* 52 */       if (block == Blocks.dirt)
/*    */       {
/* 54 */         switch ((BlockDirt.DirtType)iblockstate.getValue((IProperty)BlockDirt.VARIANT)) {
/*    */           
/*    */           case DIRT:
/* 57 */             return useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
/*    */           
/*    */           case COARSE_DIRT:
/* 60 */             return useHoe(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT));
/*    */         } 
/*    */       
/*    */       }
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState) {
/* 71 */     worldIn.playSoundEffect((target.getX() + 0.5F), (target.getY() + 0.5F), (target.getZ() + 0.5F), (newState.getBlock()).stepSound.getStepSound(), ((newState.getBlock()).stepSound.getVolume() + 1.0F) / 2.0F, (newState.getBlock()).stepSound.getFrequency() * 0.8F);
/*    */     
/* 73 */     if (worldIn.isRemote)
/*    */     {
/* 75 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 79 */     worldIn.setBlockState(target, newState);
/* 80 */     stack.damageItem(1, (EntityLivingBase)player);
/* 81 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 90 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMaterialName() {
/* 99 */     return this.theToolMaterial.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemHoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */