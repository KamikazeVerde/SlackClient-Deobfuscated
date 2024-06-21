/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmorStand
/*     */   extends Item
/*     */ {
/*     */   public ItemArmorStand() {
/*  21 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  32 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  34 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  38 */     boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
/*  39 */     BlockPos blockpos = flag ? pos : pos.offset(side);
/*     */     
/*  41 */     if (!playerIn.canPlayerEdit(blockpos, side, stack))
/*     */     {
/*  43 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  47 */     BlockPos blockpos1 = blockpos.up();
/*  48 */     boolean flag1 = (!worldIn.isAirBlock(blockpos) && !worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos));
/*  49 */     int i = flag1 | ((!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getBlock().isReplaceable(worldIn, blockpos1)) ? 1 : 0);
/*     */     
/*  51 */     if (i != 0)
/*     */     {
/*  53 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  57 */     double d0 = blockpos.getX();
/*  58 */     double d1 = blockpos.getY();
/*  59 */     double d2 = blockpos.getZ();
/*  60 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
/*     */     
/*  62 */     if (list.size() > 0)
/*     */     {
/*  64 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  68 */     if (!worldIn.isRemote) {
/*     */       
/*  70 */       worldIn.setBlockToAir(blockpos);
/*  71 */       worldIn.setBlockToAir(blockpos1);
/*  72 */       EntityArmorStand entityarmorstand = new EntityArmorStand(worldIn, d0 + 0.5D, d1, d2 + 0.5D);
/*  73 */       float f = MathHelper.floor_float((MathHelper.wrapAngleTo180_float(playerIn.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
/*  74 */       entityarmorstand.setLocationAndAngles(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
/*  75 */       applyRandomRotations(entityarmorstand, worldIn.rand);
/*  76 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/*  78 */       if (nbttagcompound != null && nbttagcompound.hasKey("EntityTag", 10)) {
/*     */         
/*  80 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  81 */         entityarmorstand.writeToNBTOptional(nbttagcompound1);
/*  82 */         nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
/*  83 */         entityarmorstand.readFromNBT(nbttagcompound1);
/*     */       } 
/*     */       
/*  86 */       worldIn.spawnEntityInWorld((Entity)entityarmorstand);
/*     */     } 
/*     */     
/*  89 */     stack.stackSize--;
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyRandomRotations(EntityArmorStand armorStand, Random rand) {
/*  99 */     Rotations rotations = armorStand.getHeadRotation();
/* 100 */     float f = rand.nextFloat() * 5.0F;
/* 101 */     float f1 = rand.nextFloat() * 20.0F - 10.0F;
/* 102 */     Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
/* 103 */     armorStand.setHeadRotation(rotations1);
/* 104 */     rotations = armorStand.getBodyRotation();
/* 105 */     f = rand.nextFloat() * 10.0F - 5.0F;
/* 106 */     rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
/* 107 */     armorStand.setBodyRotation(rotations1);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */