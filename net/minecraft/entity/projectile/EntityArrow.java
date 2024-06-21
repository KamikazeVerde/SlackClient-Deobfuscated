/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityArrow extends Entity implements IProjectile {
/*  30 */   private int xTile = -1;
/*  31 */   private int yTile = -1;
/*  32 */   private int zTile = -1;
/*     */   
/*     */   private Block inTile;
/*     */   
/*     */   private int inData;
/*     */   
/*     */   private boolean inGround;
/*     */   
/*     */   public int canBePickedUp;
/*     */   
/*     */   public int arrowShake;
/*     */   
/*     */   public Entity shootingEntity;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*  47 */   private double damage = 2.0D;
/*     */ 
/*     */   
/*     */   private int knockbackStrength;
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn) {
/*  54 */     super(worldIn);
/*  55 */     this.renderDistanceWeight = 10.0D;
/*  56 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, double x, double y, double z) {
/*  61 */     super(worldIn);
/*  62 */     this.renderDistanceWeight = 10.0D;
/*  63 */     setSize(0.5F, 0.5F);
/*  64 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
/*  69 */     super(worldIn);
/*  70 */     this.renderDistanceWeight = 10.0D;
/*  71 */     this.shootingEntity = (Entity)shooter;
/*     */     
/*  73 */     if (shooter instanceof EntityPlayer)
/*     */     {
/*  75 */       this.canBePickedUp = 1;
/*     */     }
/*     */     
/*  78 */     this.posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
/*  79 */     double d0 = p_i1755_3_.posX - shooter.posX;
/*  80 */     double d1 = (p_i1755_3_.getEntityBoundingBox()).minY + (p_i1755_3_.height / 3.0F) - this.posY;
/*  81 */     double d2 = p_i1755_3_.posZ - shooter.posZ;
/*  82 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/*     */     
/*  84 */     if (d3 >= 1.0E-7D) {
/*     */       
/*  86 */       float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0D / Math.PI) - 90.0F;
/*  87 */       float f1 = (float)-(MathHelper.func_181159_b(d1, d3) * 180.0D / Math.PI);
/*  88 */       double d4 = d0 / d3;
/*  89 */       double d5 = d2 / d3;
/*  90 */       setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f, f1);
/*  91 */       float f2 = (float)(d3 * 0.20000000298023224D);
/*  92 */       setThrowableHeading(d0, d1 + f2, d2, p_i1755_4_, p_i1755_5_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter, float velocity) {
/*  98 */     super(worldIn);
/*  99 */     this.renderDistanceWeight = 10.0D;
/* 100 */     this.shootingEntity = (Entity)shooter;
/*     */     
/* 102 */     if (shooter instanceof EntityPlayer)
/*     */     {
/* 104 */       this.canBePickedUp = 1;
/*     */     }
/*     */     
/* 107 */     setSize(0.5F, 0.5F);
/* 108 */     setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
/* 109 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/* 110 */     this.posY -= 0.10000000149011612D;
/* 111 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/* 112 */     setPosition(this.posX, this.posY, this.posZ);
/* 113 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 114 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 115 */     this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F);
/* 116 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 1.5F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 121 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 131 */     float f = MathHelper.sqrt_double(x * x + y * y + z * z);
/* 132 */     x /= f;
/* 133 */     y /= f;
/* 134 */     z /= f;
/* 135 */     x += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 136 */     y += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 137 */     z += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 138 */     x *= velocity;
/* 139 */     y *= velocity;
/* 140 */     z *= velocity;
/* 141 */     this.motionX = x;
/* 142 */     this.motionY = y;
/* 143 */     this.motionZ = z;
/* 144 */     float f1 = MathHelper.sqrt_double(x * x + z * z);
/* 145 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0D / Math.PI);
/* 146 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.func_181159_b(y, f1) * 180.0D / Math.PI);
/* 147 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 152 */     setPosition(x, y, z);
/* 153 */     setRotation(yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 161 */     this.motionX = x;
/* 162 */     this.motionY = y;
/* 163 */     this.motionZ = z;
/*     */     
/* 165 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 167 */       float f = MathHelper.sqrt_double(x * x + z * z);
/* 168 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0D / Math.PI);
/* 169 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.func_181159_b(y, f) * 180.0D / Math.PI);
/* 170 */       this.prevRotationPitch = this.rotationPitch;
/* 171 */       this.prevRotationYaw = this.rotationYaw;
/* 172 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 173 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 182 */     super.onUpdate();
/*     */     
/* 184 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 186 */       float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 187 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0D / Math.PI);
/* 188 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, f) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 191 */     BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
/* 192 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 193 */     Block block = iblockstate.getBlock();
/*     */     
/* 195 */     if (block.getMaterial() != Material.air) {
/*     */       
/* 197 */       block.setBlockBoundsBasedOnState((IBlockAccess)this.worldObj, blockpos);
/* 198 */       AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);
/*     */       
/* 200 */       if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ)))
/*     */       {
/* 202 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 206 */     if (this.arrowShake > 0)
/*     */     {
/* 208 */       this.arrowShake--;
/*     */     }
/*     */     
/* 211 */     if (this.inGround) {
/*     */       
/* 213 */       int j = block.getMetaFromState(iblockstate);
/*     */       
/* 215 */       if (block == this.inTile && j == this.inData)
/*     */       {
/* 217 */         this.ticksInGround++;
/*     */         
/* 219 */         if (this.ticksInGround >= 1200)
/*     */         {
/* 221 */           setDead();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 226 */         this.inGround = false;
/* 227 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 228 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 229 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 230 */         this.ticksInGround = 0;
/* 231 */         this.ticksInAir = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 236 */       this.ticksInAir++;
/* 237 */       Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 238 */       Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 239 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
/* 240 */       vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 241 */       vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 243 */       if (movingobjectposition != null)
/*     */       {
/* 245 */         vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 248 */       Entity entity = null;
/* 249 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 250 */       double d0 = 0.0D;
/*     */       
/* 252 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 254 */         Entity entity1 = list.get(i);
/*     */         
/* 256 */         if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
/*     */           
/* 258 */           float f1 = 0.3F;
/* 259 */           AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/* 260 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
/*     */           
/* 262 */           if (movingobjectposition1 != null) {
/*     */             
/* 264 */             double d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 266 */             if (d1 < d0 || d0 == 0.0D) {
/*     */               
/* 268 */               entity = entity1;
/* 269 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 275 */       if (entity != null)
/*     */       {
/* 277 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 280 */       if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
/*     */         
/* 282 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
/*     */         
/* 284 */         if (entityplayer.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer)))
/*     */         {
/* 286 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */       
/* 290 */       if (movingobjectposition != null)
/*     */       {
/* 292 */         if (movingobjectposition.entityHit != null) {
/*     */           DamageSource damagesource;
/* 294 */           float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 295 */           int l = MathHelper.ceiling_double_int(f2 * this.damage);
/*     */           
/* 297 */           if (getIsCritical())
/*     */           {
/* 299 */             l += this.rand.nextInt(l / 2 + 2);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 304 */           if (this.shootingEntity == null) {
/*     */             
/* 306 */             damagesource = DamageSource.causeArrowDamage(this, this);
/*     */           }
/*     */           else {
/*     */             
/* 310 */             damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
/*     */           } 
/*     */           
/* 313 */           if (isBurning() && !(movingobjectposition.entityHit instanceof net.minecraft.entity.monster.EntityEnderman))
/*     */           {
/* 315 */             movingobjectposition.entityHit.setFire(5);
/*     */           }
/*     */           
/* 318 */           if (movingobjectposition.entityHit.attackEntityFrom(damagesource, l))
/*     */           {
/* 320 */             if (movingobjectposition.entityHit instanceof EntityLivingBase) {
/*     */               
/* 322 */               EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
/*     */               
/* 324 */               if (!this.worldObj.isRemote)
/*     */               {
/* 326 */                 entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
/*     */               }
/*     */               
/* 329 */               if (this.knockbackStrength > 0) {
/*     */                 
/* 331 */                 float f7 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */                 
/* 333 */                 if (f7 > 0.0F)
/*     */                 {
/* 335 */                   movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f7, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f7);
/*     */                 }
/*     */               } 
/*     */               
/* 339 */               if (this.shootingEntity instanceof EntityLivingBase) {
/*     */                 
/* 341 */                 EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
/* 342 */                 EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, (Entity)entitylivingbase);
/*     */               } 
/*     */               
/* 345 */               if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
/*     */               {
/* 347 */                 ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(6, 0.0F));
/*     */               }
/*     */             } 
/*     */             
/* 351 */             playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/*     */             
/* 353 */             if (!(movingobjectposition.entityHit instanceof net.minecraft.entity.monster.EntityEnderman))
/*     */             {
/* 355 */               setDead();
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 360 */             this.motionX *= -0.10000000149011612D;
/* 361 */             this.motionY *= -0.10000000149011612D;
/* 362 */             this.motionZ *= -0.10000000149011612D;
/* 363 */             this.rotationYaw += 180.0F;
/* 364 */             this.prevRotationYaw += 180.0F;
/* 365 */             this.ticksInAir = 0;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 370 */           BlockPos blockpos1 = movingobjectposition.getBlockPos();
/* 371 */           this.xTile = blockpos1.getX();
/* 372 */           this.yTile = blockpos1.getY();
/* 373 */           this.zTile = blockpos1.getZ();
/* 374 */           IBlockState iblockstate1 = this.worldObj.getBlockState(blockpos1);
/* 375 */           this.inTile = iblockstate1.getBlock();
/* 376 */           this.inData = this.inTile.getMetaFromState(iblockstate1);
/* 377 */           this.motionX = (float)(movingobjectposition.hitVec.xCoord - this.posX);
/* 378 */           this.motionY = (float)(movingobjectposition.hitVec.yCoord - this.posY);
/* 379 */           this.motionZ = (float)(movingobjectposition.hitVec.zCoord - this.posZ);
/* 380 */           float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 381 */           this.posX -= this.motionX / f5 * 0.05000000074505806D;
/* 382 */           this.posY -= this.motionY / f5 * 0.05000000074505806D;
/* 383 */           this.posZ -= this.motionZ / f5 * 0.05000000074505806D;
/* 384 */           playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 385 */           this.inGround = true;
/* 386 */           this.arrowShake = 7;
/* 387 */           setIsCritical(false);
/*     */           
/* 389 */           if (this.inTile.getMaterial() != Material.air)
/*     */           {
/* 391 */             this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate1, this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 396 */       if (getIsCritical())
/*     */       {
/* 398 */         for (int k = 0; k < 4; k++)
/*     */         {
/* 400 */           this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * k / 4.0D, this.posY + this.motionY * k / 4.0D, this.posZ + this.motionZ * k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 404 */       this.posX += this.motionX;
/* 405 */       this.posY += this.motionY;
/* 406 */       this.posZ += this.motionZ;
/* 407 */       float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 408 */       this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */       
/* 410 */       for (this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, f3) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */       {
/* 417 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 420 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */       {
/* 422 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 425 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */       {
/* 427 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 430 */       this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 431 */       this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 432 */       float f4 = 0.99F;
/* 433 */       float f6 = 0.05F;
/*     */       
/* 435 */       if (isInWater()) {
/*     */         
/* 437 */         for (int i1 = 0; i1 < 4; i1++) {
/*     */           
/* 439 */           float f8 = 0.25F;
/* 440 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f8, this.posY - this.motionY * f8, this.posZ - this.motionZ * f8, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 443 */         f4 = 0.6F;
/*     */       } 
/*     */       
/* 446 */       if (isWet())
/*     */       {
/* 448 */         extinguish();
/*     */       }
/*     */       
/* 451 */       this.motionX *= f4;
/* 452 */       this.motionY *= f4;
/* 453 */       this.motionZ *= f4;
/* 454 */       this.motionY -= f6;
/* 455 */       setPosition(this.posX, this.posY, this.posZ);
/* 456 */       doBlockCollisions();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 465 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 466 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 467 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 468 */     tagCompound.setShort("life", (short)this.ticksInGround);
/* 469 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 470 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 471 */     tagCompound.setByte("inData", (byte)this.inData);
/* 472 */     tagCompound.setByte("shake", (byte)this.arrowShake);
/* 473 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 474 */     tagCompound.setByte("pickup", (byte)this.canBePickedUp);
/* 475 */     tagCompound.setDouble("damage", this.damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 483 */     this.xTile = tagCompund.getShort("xTile");
/* 484 */     this.yTile = tagCompund.getShort("yTile");
/* 485 */     this.zTile = tagCompund.getShort("zTile");
/* 486 */     this.ticksInGround = tagCompund.getShort("life");
/*     */     
/* 488 */     if (tagCompund.hasKey("inTile", 8)) {
/*     */       
/* 490 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 494 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 497 */     this.inData = tagCompund.getByte("inData") & 0xFF;
/* 498 */     this.arrowShake = tagCompund.getByte("shake") & 0xFF;
/* 499 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */     
/* 501 */     if (tagCompund.hasKey("damage", 99))
/*     */     {
/* 503 */       this.damage = tagCompund.getDouble("damage");
/*     */     }
/*     */     
/* 506 */     if (tagCompund.hasKey("pickup", 99)) {
/*     */       
/* 508 */       this.canBePickedUp = tagCompund.getByte("pickup");
/*     */     }
/* 510 */     else if (tagCompund.hasKey("player", 99)) {
/*     */       
/* 512 */       this.canBePickedUp = tagCompund.getBoolean("player") ? 1 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 521 */     if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
/*     */       
/* 523 */       boolean flag = (this.canBePickedUp == 1 || (this.canBePickedUp == 2 && entityIn.capabilities.isCreativeMode));
/*     */       
/* 525 */       if (this.canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1)))
/*     */       {
/* 527 */         flag = false;
/*     */       }
/*     */       
/* 530 */       if (flag) {
/*     */         
/* 532 */         playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 533 */         entityIn.onItemPickup(this, 1);
/* 534 */         setDead();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 545 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(double damageIn) {
/* 550 */     this.damage = damageIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamage() {
/* 555 */     return this.damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKnockbackStrength(int knockbackStrengthIn) {
/* 563 */     this.knockbackStrength = knockbackStrengthIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 571 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 576 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsCritical(boolean critical) {
/* 584 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 586 */     if (critical) {
/*     */       
/* 588 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 592 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsCritical() {
/* 601 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/* 602 */     return ((b0 & 0x1) != 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\projectile\EntityArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */