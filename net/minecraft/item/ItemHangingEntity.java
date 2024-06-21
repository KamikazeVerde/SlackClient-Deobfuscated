/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityHanging;
/*    */ import net.minecraft.entity.item.EntityItemFrame;
/*    */ import net.minecraft.entity.item.EntityPainting;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemHangingEntity
/*    */   extends Item {
/*    */   private final Class<? extends EntityHanging> hangingEntityClass;
/*    */   
/*    */   public ItemHangingEntity(Class<? extends EntityHanging> entityClass) {
/* 18 */     this.hangingEntityClass = entityClass;
/* 19 */     setCreativeTab(CreativeTabs.tabDecorations);
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
/* 30 */     if (side == EnumFacing.DOWN)
/*    */     {
/* 32 */       return false;
/*    */     }
/* 34 */     if (side == EnumFacing.UP)
/*    */     {
/* 36 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 40 */     BlockPos blockpos = pos.offset(side);
/*    */     
/* 42 */     if (!playerIn.canPlayerEdit(blockpos, side, stack))
/*    */     {
/* 44 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 48 */     EntityHanging entityhanging = createEntity(worldIn, blockpos, side);
/*    */     
/* 50 */     if (entityhanging != null && entityhanging.onValidSurface()) {
/*    */       
/* 52 */       if (!worldIn.isRemote)
/*    */       {
/* 54 */         worldIn.spawnEntityInWorld((Entity)entityhanging);
/*    */       }
/*    */       
/* 57 */       stack.stackSize--;
/*    */     } 
/*    */     
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
/* 67 */     return (this.hangingEntityClass == EntityPainting.class) ? (EntityHanging)new EntityPainting(worldIn, pos, clickedSide) : ((this.hangingEntityClass == EntityItemFrame.class) ? (EntityHanging)new EntityItemFrame(worldIn, pos, clickedSide) : null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemHangingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */