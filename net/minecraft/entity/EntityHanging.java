/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockRedstoneDiode;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityHanging
/*     */   extends Entity
/*     */ {
/*     */   private int tickCounter1;
/*     */   protected BlockPos hangingPosition;
/*     */   public EnumFacing facingDirection;
/*     */   
/*     */   public EntityHanging(World worldIn) {
/*  24 */     super(worldIn);
/*  25 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityHanging(World worldIn, BlockPos hangingPositionIn) {
/*  30 */     this(worldIn);
/*  31 */     this.hangingPosition = hangingPositionIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
/*  45 */     Validate.notNull(facingDirectionIn);
/*  46 */     Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
/*  47 */     this.facingDirection = facingDirectionIn;
/*  48 */     this.prevRotationYaw = this.rotationYaw = (this.facingDirection.getHorizontalIndex() * 90);
/*  49 */     updateBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateBoundingBox() {
/*  57 */     if (this.facingDirection != null) {
/*     */       
/*  59 */       double d0 = this.hangingPosition.getX() + 0.5D;
/*  60 */       double d1 = this.hangingPosition.getY() + 0.5D;
/*  61 */       double d2 = this.hangingPosition.getZ() + 0.5D;
/*  62 */       double d3 = 0.46875D;
/*  63 */       double d4 = func_174858_a(getWidthPixels());
/*  64 */       double d5 = func_174858_a(getHeightPixels());
/*  65 */       d0 -= this.facingDirection.getFrontOffsetX() * 0.46875D;
/*  66 */       d2 -= this.facingDirection.getFrontOffsetZ() * 0.46875D;
/*  67 */       d1 += d5;
/*  68 */       EnumFacing enumfacing = this.facingDirection.rotateYCCW();
/*  69 */       d0 += d4 * enumfacing.getFrontOffsetX();
/*  70 */       d2 += d4 * enumfacing.getFrontOffsetZ();
/*  71 */       this.posX = d0;
/*  72 */       this.posY = d1;
/*  73 */       this.posZ = d2;
/*  74 */       double d6 = getWidthPixels();
/*  75 */       double d7 = getHeightPixels();
/*  76 */       double d8 = getWidthPixels();
/*     */       
/*  78 */       if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
/*     */         
/*  80 */         d8 = 1.0D;
/*     */       }
/*     */       else {
/*     */         
/*  84 */         d6 = 1.0D;
/*     */       } 
/*     */       
/*  87 */       d6 /= 32.0D;
/*  88 */       d7 /= 32.0D;
/*  89 */       d8 /= 32.0D;
/*  90 */       setEntityBoundingBox(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private double func_174858_a(int p_174858_1_) {
/*  96 */     return (p_174858_1_ % 32 == 0) ? 0.5D : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 104 */     this.prevPosX = this.posX;
/* 105 */     this.prevPosY = this.posY;
/* 106 */     this.prevPosZ = this.posZ;
/*     */     
/* 108 */     if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
/*     */       
/* 110 */       this.tickCounter1 = 0;
/*     */       
/* 112 */       if (!this.isDead && !onValidSurface()) {
/*     */         
/* 114 */         setDead();
/* 115 */         onBroken((Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onValidSurface() {
/* 125 */     if (!this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty())
/*     */     {
/* 127 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 131 */     int i = Math.max(1, getWidthPixels() / 16);
/* 132 */     int j = Math.max(1, getHeightPixels() / 16);
/* 133 */     BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
/* 134 */     EnumFacing enumfacing = this.facingDirection.rotateYCCW();
/*     */     
/* 136 */     for (int k = 0; k < i; k++) {
/*     */       
/* 138 */       for (int l = 0; l < j; l++) {
/*     */         
/* 140 */         BlockPos blockpos1 = blockpos.offset(enumfacing, k).up(l);
/* 141 */         Block block = this.worldObj.getBlockState(blockpos1).getBlock();
/*     */         
/* 143 */         if (!block.getMaterial().isSolid() && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(block))
/*     */         {
/* 145 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     for (Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox())) {
/*     */       
/* 152 */       if (entity instanceof EntityHanging)
/*     */       {
/* 154 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitByEntity(Entity entityIn) {
/* 175 */     return (entityIn instanceof EntityPlayer) ? attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityIn), 0.0F) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getHorizontalFacing() {
/* 180 */     return this.facingDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 188 */     if (isEntityInvulnerable(source))
/*     */     {
/* 190 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 194 */     if (!this.isDead && !this.worldObj.isRemote) {
/*     */       
/* 196 */       setDead();
/* 197 */       setBeenAttacked();
/* 198 */       onBroken(source.getEntity());
/*     */     } 
/*     */     
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 210 */     if (!this.worldObj.isRemote && !this.isDead && x * x + y * y + z * z > 0.0D) {
/*     */       
/* 212 */       setDead();
/* 213 */       onBroken((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVelocity(double x, double y, double z) {
/* 222 */     if (!this.worldObj.isRemote && !this.isDead && x * x + y * y + z * z > 0.0D) {
/*     */       
/* 224 */       setDead();
/* 225 */       onBroken((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 234 */     tagCompound.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
/* 235 */     tagCompound.setInteger("TileX", getHangingPosition().getX());
/* 236 */     tagCompound.setInteger("TileY", getHangingPosition().getY());
/* 237 */     tagCompound.setInteger("TileZ", getHangingPosition().getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*     */     EnumFacing enumfacing;
/* 245 */     this.hangingPosition = new BlockPos(tagCompund.getInteger("TileX"), tagCompund.getInteger("TileY"), tagCompund.getInteger("TileZ"));
/*     */ 
/*     */     
/* 248 */     if (tagCompund.hasKey("Direction", 99)) {
/*     */       
/* 250 */       enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Direction"));
/* 251 */       this.hangingPosition = this.hangingPosition.offset(enumfacing);
/*     */     }
/* 253 */     else if (tagCompund.hasKey("Facing", 99)) {
/*     */       
/* 255 */       enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Facing"));
/*     */     }
/*     */     else {
/*     */       
/* 259 */       enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Dir"));
/*     */     } 
/*     */     
/* 262 */     updateFacingWithBoundingBox(enumfacing);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int getWidthPixels();
/*     */ 
/*     */   
/*     */   public abstract int getHeightPixels();
/*     */ 
/*     */   
/*     */   public abstract void onBroken(Entity paramEntity);
/*     */ 
/*     */   
/*     */   protected boolean shouldSetPosAfterLoading() {
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 284 */     this.posX = x;
/* 285 */     this.posY = y;
/* 286 */     this.posZ = z;
/* 287 */     BlockPos blockpos = this.hangingPosition;
/* 288 */     this.hangingPosition = new BlockPos(x, y, z);
/*     */     
/* 290 */     if (!this.hangingPosition.equals(blockpos)) {
/*     */       
/* 292 */       updateBoundingBox();
/* 293 */       this.isAirBorne = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getHangingPosition() {
/* 299 */     return this.hangingPosition;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\EntityHanging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */