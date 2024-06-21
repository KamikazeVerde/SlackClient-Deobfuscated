/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateSwimmer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedRandomFishable;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityGuardian
/*     */   extends EntityMob {
/*     */   private float field_175482_b;
/*     */   private float field_175484_c;
/*     */   private float field_175483_bk;
/*     */   private float field_175485_bl;
/*     */   private float field_175486_bm;
/*     */   private EntityLivingBase targetedEntity;
/*     */   private int field_175479_bo;
/*     */   private boolean field_175480_bp;
/*     */   private EntityAIWander wander;
/*     */   
/*     */   public EntityGuardian(World worldIn) {
/*  54 */     super(worldIn);
/*  55 */     this.experienceValue = 10;
/*  56 */     setSize(0.85F, 0.85F);
/*  57 */     this.tasks.addTask(4, new AIGuardianAttack(this));
/*     */     EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;
/*  59 */     this.tasks.addTask(5, (EntityAIBase)(entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D)));
/*  60 */     this.tasks.addTask(7, (EntityAIBase)(this.wander = new EntityAIWander(this, 1.0D, 80)));
/*  61 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  62 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityGuardian.class, 12.0F, 0.01F));
/*  63 */     this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  64 */     this.wander.setMutexBits(3);
/*  65 */     entityaimovetowardsrestriction.setMutexBits(3);
/*  66 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector(this)));
/*  67 */     this.moveHelper = new GuardianMoveHelper(this);
/*  68 */     this.field_175484_c = this.field_175482_b = this.rand.nextFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  73 */     super.applyEntityAttributes();
/*  74 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/*  75 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*  76 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*  77 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  85 */     super.readEntityFromNBT(tagCompund);
/*  86 */     setElder(tagCompund.getBoolean("Elder"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  94 */     super.writeEntityToNBT(tagCompound);
/*  95 */     tagCompound.setBoolean("Elder", isElder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/* 103 */     return (PathNavigate)new PathNavigateSwimmer((EntityLiving)this, worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 108 */     super.entityInit();
/* 109 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/* 110 */     this.dataWatcher.addObject(17, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSyncedFlagSet(int flagId) {
/* 118 */     return ((this.dataWatcher.getWatchableObjectInt(16) & flagId) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSyncedFlag(int flagId, boolean state) {
/* 128 */     int i = this.dataWatcher.getWatchableObjectInt(16);
/*     */     
/* 130 */     if (state) {
/*     */       
/* 132 */       this.dataWatcher.updateObject(16, Integer.valueOf(i | flagId));
/*     */     }
/*     */     else {
/*     */       
/* 136 */       this.dataWatcher.updateObject(16, Integer.valueOf(i & (flagId ^ 0xFFFFFFFF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175472_n() {
/* 142 */     return isSyncedFlagSet(2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175476_l(boolean p_175476_1_) {
/* 147 */     setSyncedFlag(2, p_175476_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_175464_ck() {
/* 152 */     return isElder() ? 60 : 80;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isElder() {
/* 157 */     return isSyncedFlagSet(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElder(boolean elder) {
/* 167 */     setSyncedFlag(4, elder);
/*     */     
/* 169 */     if (elder) {
/*     */       
/* 171 */       setSize(1.9975F, 1.9975F);
/* 172 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/* 173 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
/* 174 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
/* 175 */       enablePersistence();
/* 176 */       this.wander.setExecutionChance(400);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElder() {
/* 182 */     setElder(true);
/* 183 */     this.field_175486_bm = this.field_175485_bl = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTargetedEntity(int entityId) {
/* 188 */     this.dataWatcher.updateObject(17, Integer.valueOf(entityId));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTargetedEntity() {
/* 193 */     return (this.dataWatcher.getWatchableObjectInt(17) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLivingBase getTargetedEntity() {
/* 198 */     if (!hasTargetedEntity())
/*     */     {
/* 200 */       return null;
/*     */     }
/* 202 */     if (this.worldObj.isRemote) {
/*     */       
/* 204 */       if (this.targetedEntity != null)
/*     */       {
/* 206 */         return this.targetedEntity;
/*     */       }
/*     */ 
/*     */       
/* 210 */       Entity entity = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
/*     */       
/* 212 */       if (entity instanceof EntityLivingBase) {
/*     */         
/* 214 */         this.targetedEntity = (EntityLivingBase)entity;
/* 215 */         return this.targetedEntity;
/*     */       } 
/*     */ 
/*     */       
/* 219 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     return getAttackTarget();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDataWatcherUpdate(int dataID) {
/* 231 */     super.onDataWatcherUpdate(dataID);
/*     */     
/* 233 */     if (dataID == 16) {
/*     */       
/* 235 */       if (isElder() && this.width < 1.0F)
/*     */       {
/* 237 */         setSize(1.9975F, 1.9975F);
/*     */       }
/*     */     }
/* 240 */     else if (dataID == 17) {
/*     */       
/* 242 */       this.field_175479_bo = 0;
/* 243 */       this.targetedEntity = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTalkInterval() {
/* 252 */     return 160;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 260 */     return !isInWater() ? "mob.guardian.land.idle" : (isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 268 */     return !isInWater() ? "mob.guardian.land.hit" : (isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 276 */     return !isInWater() ? "mob.guardian.land.death" : (isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 290 */     return this.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 295 */     return (this.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.water) ? (10.0F + this.worldObj.getLightBrightness(pos) - 0.5F) : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 304 */     if (this.worldObj.isRemote) {
/*     */       
/* 306 */       this.field_175484_c = this.field_175482_b;
/*     */       
/* 308 */       if (!isInWater()) {
/*     */         
/* 310 */         this.field_175483_bk = 2.0F;
/*     */         
/* 312 */         if (this.motionY > 0.0D && this.field_175480_bp && !isSilent())
/*     */         {
/* 314 */           this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
/*     */         }
/*     */         
/* 317 */         this.field_175480_bp = (this.motionY < 0.0D && this.worldObj.isBlockNormalCube((new BlockPos((Entity)this)).down(), false));
/*     */       }
/* 319 */       else if (func_175472_n()) {
/*     */         
/* 321 */         if (this.field_175483_bk < 0.5F)
/*     */         {
/* 323 */           this.field_175483_bk = 4.0F;
/*     */         }
/*     */         else
/*     */         {
/* 327 */           this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 332 */         this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
/*     */       } 
/*     */       
/* 335 */       this.field_175482_b += this.field_175483_bk;
/* 336 */       this.field_175486_bm = this.field_175485_bl;
/*     */       
/* 338 */       if (!isInWater()) {
/*     */         
/* 340 */         this.field_175485_bl = this.rand.nextFloat();
/*     */       }
/* 342 */       else if (func_175472_n()) {
/*     */         
/* 344 */         this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
/*     */       }
/*     */       else {
/*     */         
/* 348 */         this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
/*     */       } 
/*     */       
/* 351 */       if (func_175472_n() && isInWater()) {
/*     */         
/* 353 */         Vec3 vec3 = getLook(0.0F);
/*     */         
/* 355 */         for (int i = 0; i < 2; i++)
/*     */         {
/* 357 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width - vec3.xCoord * 1.5D, this.posY + this.rand.nextDouble() * this.height - vec3.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width - vec3.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       } 
/*     */       
/* 361 */       if (hasTargetedEntity()) {
/*     */         
/* 363 */         if (this.field_175479_bo < func_175464_ck())
/*     */         {
/* 365 */           this.field_175479_bo++;
/*     */         }
/*     */         
/* 368 */         EntityLivingBase entitylivingbase = getTargetedEntity();
/*     */         
/* 370 */         if (entitylivingbase != null) {
/*     */           
/* 372 */           getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/* 373 */           getLookHelper().onUpdateLook();
/* 374 */           double d5 = func_175477_p(0.0F);
/* 375 */           double d0 = entitylivingbase.posX - this.posX;
/* 376 */           double d1 = entitylivingbase.posY + (entitylivingbase.height * 0.5F) - this.posY + getEyeHeight();
/* 377 */           double d2 = entitylivingbase.posZ - this.posZ;
/* 378 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 379 */           d0 /= d3;
/* 380 */           d1 /= d3;
/* 381 */           d2 /= d3;
/* 382 */           double d4 = this.rand.nextDouble();
/*     */           
/* 384 */           while (d4 < d3) {
/*     */             
/* 386 */             d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
/* 387 */             this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 393 */     if (this.inWater) {
/*     */       
/* 395 */       setAir(300);
/*     */     }
/* 397 */     else if (this.onGround) {
/*     */       
/* 399 */       this.motionY += 0.5D;
/* 400 */       this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 401 */       this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 402 */       this.rotationYaw = this.rand.nextFloat() * 360.0F;
/* 403 */       this.onGround = false;
/* 404 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 407 */     if (hasTargetedEntity())
/*     */     {
/* 409 */       this.rotationYaw = this.rotationYawHead;
/*     */     }
/*     */     
/* 412 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175471_a(float p_175471_1_) {
/* 417 */     return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * p_175471_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175469_o(float p_175469_1_) {
/* 422 */     return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * p_175469_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175477_p(float p_175477_1_) {
/* 427 */     return (this.field_175479_bo + p_175477_1_) / func_175464_ck();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 432 */     super.updateAITasks();
/*     */     
/* 434 */     if (isElder()) {
/*     */       
/* 436 */       int i = 1200;
/* 437 */       int j = 1200;
/* 438 */       int k = 6000;
/* 439 */       int l = 2;
/*     */       
/* 441 */       if ((this.ticksExisted + getEntityId()) % 1200 == 0) {
/*     */         
/* 443 */         Potion potion = Potion.digSlowdown;
/*     */         
/* 445 */         for (EntityPlayerMP entityplayermp : this.worldObj.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>()
/*     */             {
/*     */               public boolean apply(EntityPlayerMP p_apply_1_)
/*     */               {
/* 449 */                 return (EntityGuardian.this.getDistanceSqToEntity((Entity)p_apply_1_) < 2500.0D && p_apply_1_.theItemInWorldManager.survivalOrAdventure());
/*     */               }
/*     */             })) {
/*     */           
/* 453 */           if (!entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion).getAmplifier() < 2 || entityplayermp.getActivePotionEffect(potion).getDuration() < 1200) {
/*     */             
/* 455 */             entityplayermp.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(10, 0.0F));
/* 456 */             entityplayermp.addPotionEffect(new PotionEffect(potion.id, 6000, 2));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 461 */       if (!hasHome())
/*     */       {
/* 463 */         setHomePosAndDistance(new BlockPos((Entity)this), 16);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
/* 473 */     int i = this.rand.nextInt(3) + this.rand.nextInt(p_70628_2_ + 1);
/*     */     
/* 475 */     if (i > 0)
/*     */     {
/* 477 */       entityDropItem(new ItemStack(Items.prismarine_shard, i, 0), 1.0F);
/*     */     }
/*     */     
/* 480 */     if (this.rand.nextInt(3 + p_70628_2_) > 1) {
/*     */       
/* 482 */       entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 1.0F);
/*     */     }
/* 484 */     else if (this.rand.nextInt(3 + p_70628_2_) > 1) {
/*     */       
/* 486 */       entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
/*     */     } 
/*     */     
/* 489 */     if (p_70628_1_ && isElder())
/*     */     {
/* 491 */       entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 500 */     ItemStack itemstack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand);
/* 501 */     entityDropItem(itemstack, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 509 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 517 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 525 */     return ((this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos((Entity)this))) && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 533 */     if (!func_175472_n() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
/*     */       
/* 535 */       EntityLivingBase entitylivingbase = (EntityLivingBase)source.getSourceOfDamage();
/*     */       
/* 537 */       if (!source.isExplosion()) {
/*     */         
/* 539 */         entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage((Entity)this), 2.0F);
/* 540 */         entitylivingbase.playSound("damage.thorns", 0.5F, 1.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 544 */     this.wander.makeUpdate();
/* 545 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 554 */     return 180;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 562 */     if (isServerWorld()) {
/*     */       
/* 564 */       if (isInWater())
/*     */       {
/* 566 */         moveFlying(strafe, forward, 0.1F);
/* 567 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 568 */         this.motionX *= 0.8999999761581421D;
/* 569 */         this.motionY *= 0.8999999761581421D;
/* 570 */         this.motionZ *= 0.8999999761581421D;
/*     */         
/* 572 */         if (!func_175472_n() && getAttackTarget() == null)
/*     */         {
/* 574 */           this.motionY -= 0.005D;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 579 */         super.moveEntityWithHeading(strafe, forward);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 584 */       super.moveEntityWithHeading(strafe, forward);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIGuardianAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private EntityGuardian theEntity;
/*     */     private int tickCounter;
/*     */     
/*     */     public AIGuardianAttack(EntityGuardian p_i45833_1_) {
/* 595 */       this.theEntity = p_i45833_1_;
/* 596 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 601 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 602 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 607 */       return (super.continueExecuting() && (this.theEntity.isElder() || this.theEntity.getDistanceSqToEntity((Entity)this.theEntity.getAttackTarget()) > 9.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 612 */       this.tickCounter = -10;
/* 613 */       this.theEntity.getNavigator().clearPathEntity();
/* 614 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.theEntity.getAttackTarget(), 90.0F, 90.0F);
/* 615 */       this.theEntity.isAirBorne = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 620 */       this.theEntity.setTargetedEntity(0);
/* 621 */       this.theEntity.setAttackTarget(null);
/* 622 */       this.theEntity.wander.makeUpdate();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 627 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 628 */       this.theEntity.getNavigator().clearPathEntity();
/* 629 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/*     */       
/* 631 */       if (!this.theEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/*     */         
/* 633 */         this.theEntity.setAttackTarget(null);
/*     */       }
/*     */       else {
/*     */         
/* 637 */         this.tickCounter++;
/*     */         
/* 639 */         if (this.tickCounter == 0) {
/*     */           
/* 641 */           this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
/* 642 */           this.theEntity.worldObj.setEntityState((Entity)this.theEntity, (byte)21);
/*     */         }
/* 644 */         else if (this.tickCounter >= this.theEntity.func_175464_ck()) {
/*     */           
/* 646 */           float f = 1.0F;
/*     */           
/* 648 */           if (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */           {
/* 650 */             f += 2.0F;
/*     */           }
/*     */           
/* 653 */           if (this.theEntity.isElder())
/*     */           {
/* 655 */             f += 2.0F;
/*     */           }
/*     */           
/* 658 */           entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage((Entity)this.theEntity, (Entity)this.theEntity), f);
/* 659 */           entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.theEntity), (float)this.theEntity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
/* 660 */           this.theEntity.setAttackTarget(null);
/*     */         }
/* 662 */         else if (this.tickCounter < 60 || this.tickCounter % 20 == 0) {
/*     */         
/*     */         } 
/*     */ 
/*     */         
/* 667 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private EntityGuardian entityGuardian;
/*     */     
/*     */     public GuardianMoveHelper(EntityGuardian p_i45831_1_) {
/* 678 */       super((EntityLiving)p_i45831_1_);
/* 679 */       this.entityGuardian = p_i45831_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 684 */       if (this.update && !this.entityGuardian.getNavigator().noPath()) {
/*     */         
/* 686 */         double d0 = this.posX - this.entityGuardian.posX;
/* 687 */         double d1 = this.posY - this.entityGuardian.posY;
/* 688 */         double d2 = this.posZ - this.entityGuardian.posZ;
/* 689 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 690 */         d3 = MathHelper.sqrt_double(d3);
/* 691 */         d1 /= d3;
/* 692 */         float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0D / Math.PI) - 90.0F;
/* 693 */         this.entityGuardian.rotationYaw = limitAngle(this.entityGuardian.rotationYaw, f, 30.0F);
/* 694 */         this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
/* 695 */         float f1 = (float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 696 */         this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
/* 697 */         double d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5D) * 0.05D;
/* 698 */         double d5 = Math.cos((this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
/* 699 */         double d6 = Math.sin((this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
/* 700 */         this.entityGuardian.motionX += d4 * d5;
/* 701 */         this.entityGuardian.motionZ += d4 * d6;
/* 702 */         d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75D) * 0.05D;
/* 703 */         this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25D;
/* 704 */         this.entityGuardian.motionY += this.entityGuardian.getAIMoveSpeed() * d1 * 0.1D;
/* 705 */         EntityLookHelper entitylookhelper = this.entityGuardian.getLookHelper();
/* 706 */         double d7 = this.entityGuardian.posX + d0 / d3 * 2.0D;
/* 707 */         double d8 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + d1 / d3 * 1.0D;
/* 708 */         double d9 = this.entityGuardian.posZ + d2 / d3 * 2.0D;
/* 709 */         double d10 = entitylookhelper.getLookPosX();
/* 710 */         double d11 = entitylookhelper.getLookPosY();
/* 711 */         double d12 = entitylookhelper.getLookPosZ();
/*     */         
/* 713 */         if (!entitylookhelper.getIsLooking()) {
/*     */           
/* 715 */           d10 = d7;
/* 716 */           d11 = d8;
/* 717 */           d12 = d9;
/*     */         } 
/*     */         
/* 720 */         this.entityGuardian.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
/* 721 */         this.entityGuardian.func_175476_l(true);
/*     */       }
/*     */       else {
/*     */         
/* 725 */         this.entityGuardian.setAIMoveSpeed(0.0F);
/* 726 */         this.entityGuardian.func_175476_l(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianTargetSelector
/*     */     implements Predicate<EntityLivingBase>
/*     */   {
/*     */     private EntityGuardian parentEntity;
/*     */     
/*     */     public GuardianTargetSelector(EntityGuardian p_i45832_1_) {
/* 737 */       this.parentEntity = p_i45832_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(EntityLivingBase p_apply_1_) {
/* 742 */       return ((p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof net.minecraft.entity.passive.EntitySquid) && p_apply_1_.getDistanceSqToEntity((Entity)this.parentEntity) > 9.0D);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\monster\EntityGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */