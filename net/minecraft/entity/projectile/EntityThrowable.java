/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public abstract class EntityThrowable
/*     */   extends Entity
/*     */   implements IProjectile {
/*  24 */   private int xTile = -1;
/*  25 */   private int yTile = -1;
/*  26 */   private int zTile = -1;
/*     */   
/*     */   private Block inTile;
/*     */   
/*     */   protected boolean inGround;
/*     */   
/*     */   public int throwableShake;
/*     */   private EntityLivingBase thrower;
/*     */   private String throwerName;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   
/*     */   public EntityThrowable(World worldIn) {
/*  39 */     super(worldIn);
/*  40 */     setSize(0.25F, 0.25F);
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
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  53 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  55 */     if (Double.isNaN(d0))
/*     */     {
/*  57 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  60 */     d0 *= 64.0D;
/*  61 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityThrowable(World worldIn, EntityLivingBase throwerIn) {
/*  66 */     super(worldIn);
/*  67 */     this.thrower = throwerIn;
/*  68 */     setSize(0.25F, 0.25F);
/*  69 */     setLocationAndAngles(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
/*  70 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  71 */     this.posY -= 0.10000000149011612D;
/*  72 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  73 */     setPosition(this.posX, this.posY, this.posZ);
/*  74 */     float f = 0.4F;
/*  75 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  76 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  77 */     this.motionY = (-MathHelper.sin((this.rotationPitch + getInaccuracy()) / 180.0F * 3.1415927F) * f);
/*  78 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, getVelocity(), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityThrowable(World worldIn, double x, double y, double z) {
/*  83 */     super(worldIn);
/*  84 */     this.ticksInGround = 0;
/*  85 */     setSize(0.25F, 0.25F);
/*  86 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getVelocity() {
/*  91 */     return 1.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getInaccuracy() {
/*  96 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 106 */     float f = MathHelper.sqrt_double(x * x + y * y + z * z);
/* 107 */     x /= f;
/* 108 */     y /= f;
/* 109 */     z /= f;
/* 110 */     x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 111 */     y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 112 */     z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 113 */     x *= velocity;
/* 114 */     y *= velocity;
/* 115 */     z *= velocity;
/* 116 */     this.motionX = x;
/* 117 */     this.motionY = y;
/* 118 */     this.motionZ = z;
/* 119 */     float f1 = MathHelper.sqrt_double(x * x + z * z);
/* 120 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0D / Math.PI);
/* 121 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.func_181159_b(y, f1) * 180.0D / Math.PI);
/* 122 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 130 */     this.motionX = x;
/* 131 */     this.motionY = y;
/* 132 */     this.motionZ = z;
/*     */     
/* 134 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 136 */       float f = MathHelper.sqrt_double(x * x + z * z);
/* 137 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0D / Math.PI);
/* 138 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.func_181159_b(y, f) * 180.0D / Math.PI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 147 */     this.lastTickPosX = this.posX;
/* 148 */     this.lastTickPosY = this.posY;
/* 149 */     this.lastTickPosZ = this.posZ;
/* 150 */     super.onUpdate();
/*     */     
/* 152 */     if (this.throwableShake > 0)
/*     */     {
/* 154 */       this.throwableShake--;
/*     */     }
/*     */     
/* 157 */     if (this.inGround) {
/*     */       
/* 159 */       if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/*     */         
/* 161 */         this.ticksInGround++;
/*     */         
/* 163 */         if (this.ticksInGround == 1200)
/*     */         {
/* 165 */           setDead();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 171 */       this.inGround = false;
/* 172 */       this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 173 */       this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 174 */       this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 175 */       this.ticksInGround = 0;
/* 176 */       this.ticksInAir = 0;
/*     */     }
/*     */     else {
/*     */       
/* 180 */       this.ticksInAir++;
/*     */     } 
/*     */     
/* 183 */     Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 184 */     Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 185 */     MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
/* 186 */     vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 187 */     vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */     
/* 189 */     if (movingobjectposition != null)
/*     */     {
/* 191 */       vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */     }
/*     */     
/* 194 */     if (!this.worldObj.isRemote) {
/*     */       
/* 196 */       Entity entity = null;
/* 197 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 198 */       double d0 = 0.0D;
/* 199 */       EntityLivingBase entitylivingbase = getThrower();
/*     */       
/* 201 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/* 203 */         Entity entity1 = list.get(j);
/*     */         
/* 205 */         if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || this.ticksInAir >= 5)) {
/*     */           
/* 207 */           float f = 0.3F;
/* 208 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 209 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
/*     */           
/* 211 */           if (movingobjectposition1 != null) {
/*     */             
/* 213 */             double d1 = vec3.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 215 */             if (d1 < d0 || d0 == 0.0D) {
/*     */               
/* 217 */               entity = entity1;
/* 218 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 224 */       if (entity != null)
/*     */       {
/* 226 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     } 
/*     */     
/* 230 */     if (movingobjectposition != null)
/*     */     {
/* 232 */       if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.portal) {
/*     */         
/* 234 */         func_181015_d(movingobjectposition.getBlockPos());
/*     */       }
/*     */       else {
/*     */         
/* 238 */         onImpact(movingobjectposition);
/*     */       } 
/*     */     }
/*     */     
/* 242 */     this.posX += this.motionX;
/* 243 */     this.posY += this.motionY;
/* 244 */     this.posZ += this.motionZ;
/* 245 */     float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 246 */     this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */     
/* 248 */     for (this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 255 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 258 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 260 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 263 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 265 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 268 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 269 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 270 */     float f2 = 0.99F;
/* 271 */     float f3 = getGravityVelocity();
/*     */     
/* 273 */     if (isInWater()) {
/*     */       
/* 275 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 277 */         float f4 = 0.25F;
/* 278 */         this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */       } 
/*     */       
/* 281 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 284 */     this.motionX *= f2;
/* 285 */     this.motionY *= f2;
/* 286 */     this.motionZ *= f2;
/* 287 */     this.motionY -= f3;
/* 288 */     setPosition(this.posX, this.posY, this.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/* 296 */     return 0.03F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 309 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 310 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 311 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 312 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 313 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 314 */     tagCompound.setByte("shake", (byte)this.throwableShake);
/* 315 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/*     */     
/* 317 */     if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 319 */       this.throwerName = this.thrower.getCommandSenderName();
/*     */     }
/*     */     
/* 322 */     tagCompound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 330 */     this.xTile = tagCompund.getShort("xTile");
/* 331 */     this.yTile = tagCompund.getShort("yTile");
/* 332 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 334 */     if (tagCompund.hasKey("inTile", 8)) {
/*     */       
/* 336 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 340 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 343 */     this.throwableShake = tagCompund.getByte("shake") & 0xFF;
/* 344 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/* 345 */     this.thrower = null;
/* 346 */     this.throwerName = tagCompund.getString("ownerName");
/*     */     
/* 348 */     if (this.throwerName != null && this.throwerName.length() == 0)
/*     */     {
/* 350 */       this.throwerName = null;
/*     */     }
/*     */     
/* 353 */     this.thrower = getThrower();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLivingBase getThrower() {
/* 358 */     if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
/*     */       
/* 360 */       this.thrower = (EntityLivingBase)this.worldObj.getPlayerEntityByName(this.throwerName);
/*     */       
/* 362 */       if (this.thrower == null && this.worldObj instanceof WorldServer) {
/*     */         
/*     */         try {
/*     */           
/* 366 */           Entity entity = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
/*     */           
/* 368 */           if (entity instanceof EntityLivingBase)
/*     */           {
/* 370 */             this.thrower = (EntityLivingBase)entity;
/*     */           }
/*     */         }
/* 373 */         catch (Throwable var2) {
/*     */           
/* 375 */           this.thrower = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 380 */     return this.thrower;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\projectile\EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */