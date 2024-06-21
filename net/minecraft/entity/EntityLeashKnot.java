/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityLeashKnot
/*     */   extends EntityHanging
/*     */ {
/*     */   public EntityLeashKnot(World worldIn) {
/*  17 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLeashKnot(World worldIn, BlockPos hangingPositionIn) {
/*  22 */     super(worldIn, hangingPositionIn);
/*  23 */     setPosition(hangingPositionIn.getX() + 0.5D, hangingPositionIn.getY() + 0.5D, hangingPositionIn.getZ() + 0.5D);
/*  24 */     float f = 0.125F;
/*  25 */     float f1 = 0.1875F;
/*  26 */     float f2 = 0.25F;
/*  27 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D, this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  32 */     super.entityInit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  46 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeightPixels() {
/*  51 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  56 */     return -0.0625F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  65 */     return (distance < 1024.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(Entity brokenEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 104 */     ItemStack itemstack = playerIn.getHeldItem();
/* 105 */     boolean flag = false;
/*     */     
/* 107 */     if (itemstack != null && itemstack.getItem() == Items.lead && !this.worldObj.isRemote) {
/*     */       
/* 109 */       double d0 = 7.0D;
/*     */       
/* 111 */       for (EntityLiving entityliving : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + d0, this.posZ + d0))) {
/*     */         
/* 113 */         if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == playerIn) {
/*     */           
/* 115 */           entityliving.setLeashedToEntity(this, true);
/* 116 */           flag = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (!this.worldObj.isRemote && !flag) {
/*     */       
/* 123 */       setDead();
/*     */       
/* 125 */       if (playerIn.capabilities.isCreativeMode) {
/*     */         
/* 127 */         double d1 = 7.0D;
/*     */         
/* 129 */         for (EntityLiving entityliving1 : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - d1, this.posY - d1, this.posZ - d1, this.posX + d1, this.posY + d1, this.posZ + d1))) {
/*     */           
/* 131 */           if (entityliving1.getLeashed() && entityliving1.getLeashedToEntity() == this)
/*     */           {
/* 133 */             entityliving1.clearLeashed(true, false);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onValidSurface() {
/* 147 */     return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof net.minecraft.block.BlockFence;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityLeashKnot createKnot(World worldIn, BlockPos fence) {
/* 152 */     EntityLeashKnot entityleashknot = new EntityLeashKnot(worldIn, fence);
/* 153 */     entityleashknot.forceSpawn = true;
/* 154 */     worldIn.spawnEntityInWorld(entityleashknot);
/* 155 */     return entityleashknot;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityLeashKnot getKnotForPosition(World worldIn, BlockPos pos) {
/* 160 */     int i = pos.getX();
/* 161 */     int j = pos.getY();
/* 162 */     int k = pos.getZ();
/*     */     
/* 164 */     for (EntityLeashKnot entityleashknot : worldIn.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D))) {
/*     */       
/* 166 */       if (entityleashknot.getHangingPosition().equals(pos))
/*     */       {
/* 168 */         return entityleashknot;
/*     */       }
/*     */     } 
/*     */     
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\EntityLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */