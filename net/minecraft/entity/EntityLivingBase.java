/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.events.impl.player.JumpEvent;
/*      */ import cc.slack.features.modules.impl.other.Tweaks;
/*      */ import cc.slack.utils.client.mc;
/*      */ import cc.slack.utils.player.RotationUtil;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.passive.EntityWolf;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagShort;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.potion.PotionHelper;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.CombatTracker;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ 
/*      */ public abstract class EntityLivingBase
/*      */   extends Entity
/*      */ {
/*   62 */   private static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   63 */   private static final AttributeModifier sprintingSpeedBoostModifier = (new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
/*      */   private BaseAttributeMap attributeMap;
/*   65 */   private final CombatTracker _combatTracker = new CombatTracker(this);
/*   66 */   private final Map<Integer, PotionEffect> activePotionsMap = Maps.newHashMap();
/*      */ 
/*      */   
/*   69 */   private final ItemStack[] previousEquipment = new ItemStack[5];
/*      */ 
/*      */   
/*      */   public boolean isSwingInProgress;
/*      */ 
/*      */   
/*      */   public int swingProgressInt;
/*      */ 
/*      */   
/*      */   public int arrowHitTimer;
/*      */ 
/*      */   
/*      */   public int hurtTime;
/*      */ 
/*      */   
/*      */   public int ticksSinceLastDamage;
/*      */   
/*      */   public int maxHurtTime;
/*      */   
/*      */   public float attackedAtYaw;
/*      */   
/*      */   public int deathTime;
/*      */   
/*      */   public float prevSwingProgress;
/*      */   
/*      */   public float swingProgress;
/*      */   
/*      */   public float prevLimbSwingAmount;
/*      */   
/*      */   public float limbSwingAmount;
/*      */   
/*      */   public float limbSwing;
/*      */   
/*  102 */   public int maxHurtResistantTime = 20;
/*      */   
/*      */   public float prevCameraPitch;
/*      */   
/*      */   public float cameraPitch;
/*      */   
/*      */   public float field_70769_ao;
/*      */   
/*      */   public float field_70770_ap;
/*      */   
/*      */   public float renderYawOffset;
/*      */   
/*      */   public float prevRenderYawOffset;
/*      */   
/*      */   public float rotationYawHead;
/*      */   
/*      */   public float prevRotationYawHead;
/*  119 */   public float jumpMovementFactor = 0.02F;
/*      */ 
/*      */   
/*      */   protected EntityPlayer attackingPlayer;
/*      */ 
/*      */   
/*      */   protected int recentlyHit;
/*      */ 
/*      */   
/*      */   protected boolean dead;
/*      */ 
/*      */   
/*      */   protected int entityAge;
/*      */ 
/*      */   
/*      */   protected float prevOnGroundSpeedFactor;
/*      */ 
/*      */   
/*      */   protected float onGroundSpeedFactor;
/*      */ 
/*      */   
/*      */   protected float movedDistance;
/*      */ 
/*      */   
/*      */   protected float prevMovedDistance;
/*      */ 
/*      */   
/*      */   protected float field_70741_aB;
/*      */ 
/*      */   
/*      */   protected int scoreValue;
/*      */ 
/*      */   
/*      */   protected float lastDamage;
/*      */ 
/*      */   
/*      */   protected boolean isJumping;
/*      */ 
/*      */   
/*      */   public float moveStrafing;
/*      */ 
/*      */   
/*      */   public float moveForward;
/*      */ 
/*      */   
/*      */   protected float randomYawVelocity;
/*      */ 
/*      */   
/*      */   protected int newPosRotationIncrements;
/*      */ 
/*      */   
/*      */   protected double newPosX;
/*      */ 
/*      */   
/*      */   protected double newPosY;
/*      */   
/*      */   protected double newPosZ;
/*      */   
/*      */   protected double newRotationYaw;
/*      */   
/*      */   protected double newRotationPitch;
/*      */   
/*      */   private boolean potionsNeedUpdate = true;
/*      */   
/*      */   private EntityLivingBase entityLivingToAttack;
/*      */   
/*      */   private int revengeTimer;
/*      */   
/*      */   private EntityLivingBase lastAttacker;
/*      */   
/*      */   private int lastAttackerTime;
/*      */   
/*      */   private float landMovementFactor;
/*      */   
/*      */   public int jumpTicks;
/*      */   
/*      */   private float absorptionAmount;
/*      */   
/*      */   public float renderPitch;
/*      */   
/*      */   public float prevRenderPitch;
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  203 */     attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLivingBase(World worldIn) {
/*  208 */     super(worldIn);
/*  209 */     applyEntityAttributes();
/*  210 */     setHealth(getMaxHealth());
/*  211 */     this.preventEntitySpawning = true;
/*  212 */     this.field_70770_ap = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
/*  213 */     setPosition(this.posX, this.posY, this.posZ);
/*  214 */     this.field_70769_ao = (float)Math.random() * 12398.0F;
/*  215 */     this.rotationYaw = (float)(Math.random() * Math.PI * 2.0D);
/*  216 */     this.rotationYawHead = this.rotationYaw;
/*  217 */     this.renderPitch = this.rotationPitch;
/*  218 */     this.stepHeight = 0.6F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  223 */     this.dataWatcher.addObject(7, Integer.valueOf(0));
/*  224 */     this.dataWatcher.addObject(8, Byte.valueOf((byte)0));
/*  225 */     this.dataWatcher.addObject(9, Byte.valueOf((byte)0));
/*  226 */     this.dataWatcher.addObject(6, Float.valueOf(1.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  231 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
/*  232 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
/*  233 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/*  238 */     if (!isInWater())
/*      */     {
/*  240 */       handleWaterMovement();
/*      */     }
/*      */     
/*  243 */     if (!this.worldObj.isRemote && this.fallDistance > 3.0F && onGroundIn) {
/*      */       
/*  245 */       IBlockState iblockstate = this.worldObj.getBlockState(pos);
/*  246 */       Block block = iblockstate.getBlock();
/*  247 */       float f = MathHelper.ceiling_float_int(this.fallDistance - 3.0F);
/*      */       
/*  249 */       if (block.getMaterial() != Material.air) {
/*      */         
/*  251 */         double d0 = Math.min(0.2F + f / 15.0F, 10.0F);
/*      */         
/*  253 */         if (d0 > 2.5D)
/*      */         {
/*  255 */           d0 = 2.5D;
/*      */         }
/*      */         
/*  258 */         int i = (int)(150.0D * d0);
/*  259 */         ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(iblockstate) });
/*      */       } 
/*      */     } 
/*      */     
/*  263 */     super.updateFallState(y, onGroundIn, blockIn, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBreatheUnderwater() {
/*  268 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  276 */     this.prevSwingProgress = this.swingProgress;
/*  277 */     super.onEntityUpdate();
/*  278 */     this.worldObj.theProfiler.startSection("livingEntityBaseTick");
/*  279 */     boolean flag = this instanceof EntityPlayer;
/*      */     
/*  281 */     if (isEntityAlive())
/*      */     {
/*  283 */       if (isEntityInsideOpaqueBlock()) {
/*      */         
/*  285 */         attackEntityFrom(DamageSource.inWall, 1.0F);
/*      */       }
/*  287 */       else if (flag && !this.worldObj.getWorldBorder().contains(getEntityBoundingBox())) {
/*      */         
/*  289 */         double d0 = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
/*      */         
/*  291 */         if (d0 < 0.0D)
/*      */         {
/*  293 */           attackEntityFrom(DamageSource.inWall, Math.max(1, MathHelper.floor_double(-d0 * this.worldObj.getWorldBorder().getDamageAmount())));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  298 */     if (isImmuneToFire() || this.worldObj.isRemote)
/*      */     {
/*  300 */       extinguish();
/*      */     }
/*      */     
/*  303 */     boolean flag1 = (flag && ((EntityPlayer)this).capabilities.disableDamage);
/*      */     
/*  305 */     if (isEntityAlive())
/*      */     {
/*  307 */       if (isInsideOfMaterial(Material.water)) {
/*      */         
/*  309 */         if (!canBreatheUnderwater() && !isPotionActive(Potion.waterBreathing.id) && !flag1) {
/*      */           
/*  311 */           setAir(decreaseAirSupply(getAir()));
/*      */           
/*  313 */           if (getAir() == -20) {
/*      */             
/*  315 */             setAir(0);
/*      */             
/*  317 */             for (int i = 0; i < 8; i++) {
/*      */               
/*  319 */               float f = this.rand.nextFloat() - this.rand.nextFloat();
/*  320 */               float f1 = this.rand.nextFloat() - this.rand.nextFloat();
/*  321 */               float f2 = this.rand.nextFloat() - this.rand.nextFloat();
/*  322 */               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f, this.posY + f1, this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */             } 
/*      */             
/*  325 */             attackEntityFrom(DamageSource.drown, 2.0F);
/*      */           } 
/*      */         } 
/*      */         
/*  329 */         if (!this.worldObj.isRemote && isRiding() && this.ridingEntity instanceof EntityLivingBase)
/*      */         {
/*  331 */           mountEntity((Entity)null);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  336 */         setAir(300);
/*      */       } 
/*      */     }
/*      */     
/*  340 */     if (isEntityAlive() && isWet())
/*      */     {
/*  342 */       extinguish();
/*      */     }
/*      */     
/*  345 */     this.prevCameraPitch = this.cameraPitch;
/*      */     
/*  347 */     if (this.hurtTime > 0)
/*      */     {
/*  349 */       this.hurtTime--;
/*      */     }
/*  351 */     this.ticksSinceLastDamage--;
/*      */     
/*  353 */     if (this.hurtResistantTime > 0 && !(this instanceof net.minecraft.entity.player.EntityPlayerMP))
/*      */     {
/*  355 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  358 */     if (getHealth() <= 0.0F)
/*      */     {
/*  360 */       onDeathUpdate();
/*      */     }
/*      */     
/*  363 */     if (this.recentlyHit > 0) {
/*      */       
/*  365 */       this.recentlyHit--;
/*      */     }
/*      */     else {
/*      */       
/*  369 */       this.attackingPlayer = null;
/*      */     } 
/*      */     
/*  372 */     if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive())
/*      */     {
/*  374 */       this.lastAttacker = null;
/*      */     }
/*      */     
/*  377 */     if (this.entityLivingToAttack != null)
/*      */     {
/*  379 */       if (!this.entityLivingToAttack.isEntityAlive()) {
/*      */         
/*  381 */         setRevengeTarget((EntityLivingBase)null);
/*      */       }
/*  383 */       else if (this.ticksExisted - this.revengeTimer > 100) {
/*      */         
/*  385 */         setRevengeTarget((EntityLivingBase)null);
/*      */       } 
/*      */     }
/*      */     
/*  389 */     updatePotionEffects();
/*  390 */     this.prevMovedDistance = this.movedDistance;
/*  391 */     this.prevRenderYawOffset = this.renderYawOffset;
/*  392 */     this.prevRotationYawHead = this.rotationYawHead;
/*  393 */     this.prevRotationYaw = this.rotationYaw;
/*  394 */     this.prevRenderPitch = this.renderPitch;
/*  395 */     this.prevRotationPitch = this.rotationPitch;
/*  396 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChild() {
/*  404 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDeathUpdate() {
/*  412 */     this.deathTime++;
/*      */     
/*  414 */     if (this.deathTime == 20) {
/*      */       
/*  416 */       if (!this.worldObj.isRemote && (this.recentlyHit > 0 || isPlayer()) && canDropLoot() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
/*      */         
/*  418 */         int i = getExperiencePoints(this.attackingPlayer);
/*      */         
/*  420 */         while (i > 0) {
/*      */           
/*  422 */           int j = EntityXPOrb.getXPSplit(i);
/*  423 */           i -= j;
/*  424 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
/*      */         } 
/*      */       } 
/*      */       
/*  428 */       setDead();
/*      */       
/*  430 */       for (int k = 0; k < 20; k++) {
/*      */         
/*  432 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  433 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  434 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  435 */         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, d0, d1, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDropLoot() {
/*  445 */     return !isChild();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int decreaseAirSupply(int p_70682_1_) {
/*  453 */     int i = EnchantmentHelper.getRespiration(this);
/*  454 */     return (i > 0 && this.rand.nextInt(i + 1) > 0) ? p_70682_1_ : (p_70682_1_ - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  462 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/*  470 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Random getRNG() {
/*  475 */     return this.rand;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLivingBase getAITarget() {
/*  480 */     return this.entityLivingToAttack;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRevengeTimer() {
/*  485 */     return this.revengeTimer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  490 */     this.entityLivingToAttack = livingBase;
/*  491 */     this.revengeTimer = this.ticksExisted;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLivingBase getLastAttacker() {
/*  496 */     return this.lastAttacker;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLastAttackerTime() {
/*  501 */     return this.lastAttackerTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastAttacker(Entity entityIn) {
/*  506 */     if (entityIn instanceof EntityLivingBase) {
/*      */       
/*  508 */       this.lastAttacker = (EntityLivingBase)entityIn;
/*      */     }
/*      */     else {
/*      */       
/*  512 */       this.lastAttacker = null;
/*      */     } 
/*      */     
/*  515 */     this.lastAttackerTime = this.ticksExisted;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAge() {
/*  520 */     return this.entityAge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  528 */     tagCompound.setFloat("HealF", getHealth());
/*  529 */     tagCompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
/*  530 */     tagCompound.setShort("HurtTime", (short)this.hurtTime);
/*  531 */     tagCompound.setInteger("HurtByTimestamp", this.revengeTimer);
/*  532 */     tagCompound.setShort("DeathTime", (short)this.deathTime);
/*  533 */     tagCompound.setFloat("AbsorptionAmount", getAbsorptionAmount());
/*      */     
/*  535 */     for (ItemStack itemstack : getInventory()) {
/*      */       
/*  537 */       if (itemstack != null)
/*      */       {
/*  539 */         this.attributeMap.removeAttributeModifiers(itemstack.getAttributeModifiers());
/*      */       }
/*      */     } 
/*      */     
/*  543 */     tagCompound.setTag("Attributes", (NBTBase)SharedMonsterAttributes.writeBaseAttributeMapToNBT(getAttributeMap()));
/*      */     
/*  545 */     for (ItemStack itemstack1 : getInventory()) {
/*      */       
/*  547 */       if (itemstack1 != null)
/*      */       {
/*  549 */         this.attributeMap.applyAttributeModifiers(itemstack1.getAttributeModifiers());
/*      */       }
/*      */     } 
/*      */     
/*  553 */     if (!this.activePotionsMap.isEmpty()) {
/*      */       
/*  555 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/*  557 */       for (PotionEffect potioneffect : this.activePotionsMap.values())
/*      */       {
/*  559 */         nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*      */       }
/*      */       
/*  562 */       tagCompound.setTag("ActiveEffects", (NBTBase)nbttaglist);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  571 */     setAbsorptionAmount(tagCompund.getFloat("AbsorptionAmount"));
/*      */     
/*  573 */     if (tagCompund.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote)
/*      */     {
/*  575 */       SharedMonsterAttributes.func_151475_a(getAttributeMap(), tagCompund.getTagList("Attributes", 10));
/*      */     }
/*      */     
/*  578 */     if (tagCompund.hasKey("ActiveEffects", 9)) {
/*      */       
/*  580 */       NBTTagList nbttaglist = tagCompund.getTagList("ActiveEffects", 10);
/*      */       
/*  582 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/*  584 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  585 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*      */         
/*  587 */         if (potioneffect != null)
/*      */         {
/*  589 */           this.activePotionsMap.put(Integer.valueOf(potioneffect.getPotionID()), potioneffect);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  594 */     if (tagCompund.hasKey("HealF", 99)) {
/*      */       
/*  596 */       setHealth(tagCompund.getFloat("HealF"));
/*      */     }
/*      */     else {
/*      */       
/*  600 */       NBTBase nbtbase = tagCompund.getTag("Health");
/*      */       
/*  602 */       if (nbtbase == null) {
/*      */         
/*  604 */         setHealth(getMaxHealth());
/*      */       }
/*  606 */       else if (nbtbase.getId() == 5) {
/*      */         
/*  608 */         setHealth(((NBTTagFloat)nbtbase).getFloat());
/*      */       }
/*  610 */       else if (nbtbase.getId() == 2) {
/*      */         
/*  612 */         setHealth(((NBTTagShort)nbtbase).getShort());
/*      */       } 
/*      */     } 
/*      */     
/*  616 */     this.hurtTime = tagCompund.getShort("HurtTime");
/*  617 */     this.deathTime = tagCompund.getShort("DeathTime");
/*  618 */     this.revengeTimer = tagCompund.getInteger("HurtByTimestamp");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updatePotionEffects() {
/*  623 */     Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
/*      */     
/*  625 */     while (iterator.hasNext()) {
/*      */       
/*  627 */       Integer integer = iterator.next();
/*  628 */       PotionEffect potioneffect = this.activePotionsMap.get(integer);
/*      */       
/*  630 */       if (!potioneffect.onUpdate(this)) {
/*      */         
/*  632 */         if (!this.worldObj.isRemote) {
/*      */           
/*  634 */           iterator.remove();
/*  635 */           onFinishedPotionEffect(potioneffect);
/*      */         }  continue;
/*      */       } 
/*  638 */       if (potioneffect.getDuration() % 600 == 0)
/*      */       {
/*  640 */         onChangedPotionEffect(potioneffect, false);
/*      */       }
/*      */     } 
/*      */     
/*  644 */     if (this.potionsNeedUpdate) {
/*      */       
/*  646 */       if (!this.worldObj.isRemote)
/*      */       {
/*  648 */         updatePotionMetadata();
/*      */       }
/*      */       
/*  651 */       this.potionsNeedUpdate = false;
/*      */     } 
/*      */     
/*  654 */     int i = this.dataWatcher.getWatchableObjectInt(7);
/*  655 */     boolean flag1 = (this.dataWatcher.getWatchableObjectByte(8) > 0);
/*      */     
/*  657 */     if (i > 0) {
/*      */       int j;
/*  659 */       boolean flag = false;
/*      */       
/*  661 */       if (!isInvisible()) {
/*      */         
/*  663 */         flag = this.rand.nextBoolean();
/*      */       }
/*      */       else {
/*      */         
/*  667 */         flag = (this.rand.nextInt(15) == 0);
/*      */       } 
/*      */       
/*  670 */       if (flag1)
/*      */       {
/*  672 */         j = flag & ((this.rand.nextInt(5) == 0) ? 1 : 0);
/*      */       }
/*      */       
/*  675 */       if (j != 0 && i > 0) {
/*      */         
/*  677 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/*  678 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/*  679 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*  680 */         this.worldObj.spawnParticle(flag1 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/*  691 */     if (this.activePotionsMap.isEmpty()) {
/*      */       
/*  693 */       resetPotionEffectMetadata();
/*  694 */       setInvisible(false);
/*      */     }
/*      */     else {
/*      */       
/*  698 */       int i = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
/*  699 */       this.dataWatcher.updateObject(8, Byte.valueOf((byte)(PotionHelper.getAreAmbient(this.activePotionsMap.values()) ? 1 : 0)));
/*  700 */       this.dataWatcher.updateObject(7, Integer.valueOf(i));
/*  701 */       setInvisible(isPotionActive(Potion.invisibility.id));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPotionEffectMetadata() {
/*  710 */     this.dataWatcher.updateObject(8, Byte.valueOf((byte)0));
/*  711 */     this.dataWatcher.updateObject(7, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearActivePotions() {
/*  716 */     Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
/*      */     
/*  718 */     while (iterator.hasNext()) {
/*      */       
/*  720 */       Integer integer = iterator.next();
/*  721 */       PotionEffect potioneffect = this.activePotionsMap.get(integer);
/*      */       
/*  723 */       if (!this.worldObj.isRemote) {
/*      */         
/*  725 */         iterator.remove();
/*  726 */         onFinishedPotionEffect(potioneffect);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Collection<PotionEffect> getActivePotionEffects() {
/*  733 */     return this.activePotionsMap.values();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionActive(int potionId) {
/*  738 */     return this.activePotionsMap.containsKey(Integer.valueOf(potionId));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionActive(Potion potionIn) {
/*  743 */     return this.activePotionsMap.containsKey(Integer.valueOf(potionIn.id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PotionEffect getActivePotionEffect(Potion potionIn) {
/*  751 */     return this.activePotionsMap.get(Integer.valueOf(potionIn.id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPotionEffect(PotionEffect potioneffectIn) {
/*  759 */     if (isPotionApplicable(potioneffectIn))
/*      */     {
/*  761 */       if (this.activePotionsMap.containsKey(Integer.valueOf(potioneffectIn.getPotionID()))) {
/*      */         
/*  763 */         ((PotionEffect)this.activePotionsMap.get(Integer.valueOf(potioneffectIn.getPotionID()))).combine(potioneffectIn);
/*  764 */         onChangedPotionEffect(this.activePotionsMap.get(Integer.valueOf(potioneffectIn.getPotionID())), true);
/*      */       }
/*      */       else {
/*      */         
/*  768 */         this.activePotionsMap.put(Integer.valueOf(potioneffectIn.getPotionID()), potioneffectIn);
/*  769 */         onNewPotionEffect(potioneffectIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/*  776 */     if (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
/*      */       
/*  778 */       int i = potioneffectIn.getPotionID();
/*      */       
/*  780 */       if (i == Potion.regeneration.id || i == Potion.poison.id)
/*      */       {
/*  782 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  786 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityUndead() {
/*  794 */     return (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffectClient(int potionId) {
/*  802 */     this.activePotionsMap.remove(Integer.valueOf(potionId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffect(int potionId) {
/*  810 */     PotionEffect potioneffect = this.activePotionsMap.remove(Integer.valueOf(potionId));
/*      */     
/*  812 */     if (potioneffect != null)
/*      */     {
/*  814 */       onFinishedPotionEffect(potioneffect);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/*  820 */     this.potionsNeedUpdate = true;
/*      */     
/*  822 */     if (!this.worldObj.isRemote)
/*      */     {
/*  824 */       Potion.potionTypes[id.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/*  830 */     this.potionsNeedUpdate = true;
/*      */     
/*  832 */     if (p_70695_2_ && !this.worldObj.isRemote) {
/*      */       
/*  834 */       Potion.potionTypes[id.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), id.getAmplifier());
/*  835 */       Potion.potionTypes[id.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect p_70688_1_) {
/*  841 */     this.potionsNeedUpdate = true;
/*      */     
/*  843 */     if (!this.worldObj.isRemote)
/*      */     {
/*  845 */       Potion.potionTypes[p_70688_1_.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), p_70688_1_.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float healAmount) {
/*  854 */     float f = getHealth();
/*      */     
/*  856 */     if (f > 0.0F)
/*      */     {
/*  858 */       setHealth(f + healAmount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final float getHealth() {
/*  864 */     return this.dataWatcher.getWatchableObjectFloat(6);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHealth(float health) {
/*  870 */     this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(health, 0.0F, getMaxHealth())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  878 */     if (isEntityInvulnerable(source))
/*      */     {
/*  880 */       return false;
/*      */     }
/*  882 */     if (this.worldObj.isRemote)
/*      */     {
/*  884 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  888 */     this.entityAge = 0;
/*      */     
/*  890 */     if (getHealth() <= 0.0F)
/*      */     {
/*  892 */       return false;
/*      */     }
/*  894 */     if (source.isFireDamage() && isPotionActive(Potion.fireResistance))
/*      */     {
/*  896 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  900 */     if ((source == DamageSource.anvil || source == DamageSource.fallingBlock) && getEquipmentInSlot(4) != null) {
/*      */       
/*  902 */       getEquipmentInSlot(4).damageItem((int)(amount * 4.0F + this.rand.nextFloat() * amount * 2.0F), this);
/*  903 */       amount *= 0.75F;
/*      */     } 
/*      */     
/*  906 */     this.limbSwingAmount = 1.5F;
/*  907 */     boolean flag = true;
/*      */     
/*  909 */     if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F) {
/*      */       
/*  911 */       if (amount <= this.lastDamage)
/*      */       {
/*  913 */         return false;
/*      */       }
/*      */       
/*  916 */       damageEntity(source, amount - this.lastDamage);
/*  917 */       this.lastDamage = amount;
/*  918 */       flag = false;
/*      */     }
/*      */     else {
/*      */       
/*  922 */       this.lastDamage = amount;
/*  923 */       this.hurtResistantTime = this.maxHurtResistantTime;
/*  924 */       damageEntity(source, amount);
/*  925 */       this.hurtTime = this.maxHurtTime = 10;
/*  926 */       this.ticksSinceLastDamage = 0;
/*      */     } 
/*      */     
/*  929 */     this.attackedAtYaw = 0.0F;
/*  930 */     Entity entity = source.getEntity();
/*      */     
/*  932 */     if (entity != null) {
/*      */       
/*  934 */       if (entity instanceof EntityLivingBase)
/*      */       {
/*  936 */         setRevengeTarget((EntityLivingBase)entity);
/*      */       }
/*      */       
/*  939 */       if (entity instanceof EntityPlayer) {
/*      */         
/*  941 */         this.recentlyHit = 100;
/*  942 */         this.attackingPlayer = (EntityPlayer)entity;
/*      */       }
/*  944 */       else if (entity instanceof EntityWolf) {
/*      */         
/*  946 */         EntityWolf entitywolf = (EntityWolf)entity;
/*      */         
/*  948 */         if (entitywolf.isTamed()) {
/*      */           
/*  950 */           this.recentlyHit = 100;
/*  951 */           this.attackingPlayer = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  956 */     if (flag) {
/*      */       
/*  958 */       this.worldObj.setEntityState(this, (byte)2);
/*      */       
/*  960 */       if (source != DamageSource.drown)
/*      */       {
/*  962 */         setBeenAttacked();
/*      */       }
/*      */       
/*  965 */       if (entity != null) {
/*      */         
/*  967 */         double d1 = entity.posX - this.posX;
/*      */         
/*      */         double d0;
/*  970 */         for (d0 = entity.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
/*      */         {
/*  972 */           d1 = (Math.random() - Math.random()) * 0.01D;
/*      */         }
/*      */         
/*  975 */         this.attackedAtYaw = (float)(MathHelper.func_181159_b(d0, d1) * 180.0D / Math.PI - this.rotationYaw);
/*  976 */         knockBack(entity, amount, d1, d0);
/*      */       }
/*      */       else {
/*      */         
/*  980 */         this.attackedAtYaw = ((int)(Math.random() * 2.0D) * 180);
/*      */       } 
/*      */     } 
/*      */     
/*  984 */     if (getHealth() <= 0.0F) {
/*      */       
/*  986 */       String s = getDeathSound();
/*      */       
/*  988 */       if (flag && s != null)
/*      */       {
/*  990 */         playSound(s, getSoundVolume(), getSoundPitch());
/*      */       }
/*      */       
/*  993 */       onDeath(source);
/*      */     }
/*      */     else {
/*      */       
/*  997 */       String s1 = getHurtSound();
/*      */       
/*  999 */       if (flag && s1 != null)
/*      */       {
/* 1001 */         playSound(s1, getSoundVolume(), getSoundPitch());
/*      */       }
/*      */     } 
/*      */     
/* 1005 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHurtTime() {
/* 1010 */     return this.hurtTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderBrokenItemStack(ItemStack stack) {
/* 1017 */     playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);
/*      */     
/* 1019 */     for (int i = 0; i < 5; i++) {
/*      */       
/* 1021 */       Vec3 vec3 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/* 1022 */       vec3 = vec3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/* 1023 */       vec3 = vec3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/* 1024 */       double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/* 1025 */       Vec3 vec31 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/* 1026 */       vec31 = vec31.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/* 1027 */       vec31 = vec31.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/* 1028 */       vec31 = vec31.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1029 */       this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(stack.getItem()) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/* 1038 */     Entity entity = cause.getEntity();
/* 1039 */     EntityLivingBase entitylivingbase = func_94060_bK();
/*      */     
/* 1041 */     if (this.scoreValue >= 0 && entitylivingbase != null)
/*      */     {
/* 1043 */       entitylivingbase.addToPlayerScore(this, this.scoreValue);
/*      */     }
/*      */     
/* 1046 */     if (entity != null)
/*      */     {
/* 1048 */       entity.onKillEntity(this);
/*      */     }
/*      */     
/* 1051 */     this.dead = true;
/* 1052 */     getCombatTracker().reset();
/*      */     
/* 1054 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1056 */       int i = 0;
/*      */       
/* 1058 */       if (entity instanceof EntityPlayer)
/*      */       {
/* 1060 */         i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
/*      */       }
/*      */       
/* 1063 */       if (canDropLoot() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
/*      */         
/* 1065 */         dropFewItems((this.recentlyHit > 0), i);
/* 1066 */         dropEquipment((this.recentlyHit > 0), i);
/*      */         
/* 1068 */         if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + i * 0.01F)
/*      */         {
/* 1070 */           addRandomDrop();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1075 */     this.worldObj.setEntityState(this, (byte)3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean p_82160_1_, int p_82160_2_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void knockBack(Entity entityIn, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
/* 1090 */     if (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
/*      */       
/* 1092 */       this.isAirBorne = true;
/* 1093 */       float f = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
/* 1094 */       float f1 = 0.4F;
/* 1095 */       this.motionX /= 2.0D;
/* 1096 */       this.motionY /= 2.0D;
/* 1097 */       this.motionZ /= 2.0D;
/* 1098 */       this.motionX -= p_70653_3_ / f * f1;
/* 1099 */       this.motionY += f1;
/* 1100 */       this.motionZ -= p_70653_5_ / f * f1;
/*      */       
/* 1102 */       if (this.motionY > 0.4000000059604645D)
/*      */       {
/* 1104 */         this.motionY = 0.4000000059604645D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/* 1114 */     return "game.neutral.hurt";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/* 1122 */     return "game.neutral.die";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addRandomDrop() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/* 1144 */     int i = MathHelper.floor_double(this.posX);
/* 1145 */     int j = MathHelper.floor_double((getEntityBoundingBox()).minY);
/* 1146 */     int k = MathHelper.floor_double(this.posZ);
/* 1147 */     Block block = this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
/* 1148 */     return ((block == Blocks.ladder || block == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).isSpectator()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1156 */     return (!this.isDead && getHealth() > 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1161 */     super.fall(distance, damageMultiplier);
/* 1162 */     PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
/* 1163 */     float f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0.0F;
/* 1164 */     int i = MathHelper.ceiling_float_int((distance - 3.0F - f) * damageMultiplier);
/*      */     
/* 1166 */     if (i > 0) {
/*      */       
/* 1168 */       playSound(getFallSoundString(i), 1.0F, 1.0F);
/* 1169 */       attackEntityFrom(DamageSource.fall, i);
/* 1170 */       int j = MathHelper.floor_double(this.posX);
/* 1171 */       int k = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 1172 */       int l = MathHelper.floor_double(this.posZ);
/* 1173 */       Block block = this.worldObj.getBlockState(new BlockPos(j, k, l)).getBlock();
/*      */       
/* 1175 */       if (block.getMaterial() != Material.air) {
/*      */         
/* 1177 */         Block.SoundType block$soundtype = block.stepSound;
/* 1178 */         playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.5F, block$soundtype.getFrequency() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getFallSoundString(int damageValue) {
/* 1185 */     return (damageValue > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/* 1193 */     this.hurtTime = this.maxHurtTime = 10;
/* 1194 */     this.ticksSinceLastDamage = 0;
/* 1195 */     this.attackedAtYaw = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1203 */     int i = 0;
/*      */     
/* 1205 */     for (ItemStack itemstack : getInventory()) {
/*      */       
/* 1207 */       if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
/*      */         
/* 1209 */         int j = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
/* 1210 */         i += j;
/*      */       } 
/*      */     } 
/*      */     
/* 1214 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageArmor(float p_70675_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyArmorCalculations(DamageSource source, float damage) {
/* 1229 */     if (!source.isUnblockable()) {
/*      */       
/* 1231 */       int i = 25 - getTotalArmorValue();
/* 1232 */       float f = damage * i;
/* 1233 */       damageArmor(damage);
/* 1234 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1237 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 1247 */     if (source.isDamageAbsolute())
/*      */     {
/* 1249 */       return damage;
/*      */     }
/*      */ 
/*      */     
/* 1253 */     if (isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
/*      */       
/* 1255 */       int i = (getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
/* 1256 */       int j = 25 - i;
/* 1257 */       float f = damage * j;
/* 1258 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1261 */     if (damage <= 0.0F)
/*      */     {
/* 1263 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/* 1267 */     int k = EnchantmentHelper.getEnchantmentModifierDamage(getInventory(), source);
/*      */     
/* 1269 */     if (k > 20)
/*      */     {
/* 1271 */       k = 20;
/*      */     }
/*      */     
/* 1274 */     if (k > 0 && k <= 20) {
/*      */       
/* 1276 */       int l = 25 - k;
/* 1277 */       float f1 = damage * l;
/* 1278 */       damage = f1 / 25.0F;
/*      */     } 
/*      */     
/* 1281 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1292 */     if (!isEntityInvulnerable(damageSrc)) {
/*      */       
/* 1294 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1295 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1296 */       float f = damageAmount;
/* 1297 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1298 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1300 */       if (damageAmount != 0.0F) {
/*      */         
/* 1302 */         float f1 = getHealth();
/* 1303 */         setHealth(f1 - damageAmount);
/* 1304 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/* 1305 */         setAbsorptionAmount(getAbsorptionAmount() - damageAmount);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CombatTracker getCombatTracker() {
/* 1312 */     return this._combatTracker;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLivingBase func_94060_bK() {
/* 1317 */     return (this._combatTracker.func_94550_c() != null) ? this._combatTracker.func_94550_c() : ((this.attackingPlayer != null) ? (EntityLivingBase)this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
/*      */   }
/*      */ 
/*      */   
/*      */   public final float getMaxHealth() {
/* 1322 */     return (float)getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getArrowCountInEntity() {
/* 1330 */     return this.dataWatcher.getWatchableObjectByte(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setArrowCountInEntity(int count) {
/* 1338 */     this.dataWatcher.updateObject(9, Byte.valueOf((byte)count));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getArmSwingAnimationEnd() {
/* 1347 */     return isPotionActive(Potion.digSpeed) ? (6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) : (isPotionActive(Potion.digSlowdown) ? (6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swingItem() {
/* 1355 */     if (!this.isSwingInProgress || this.swingProgressInt >= getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
/*      */       
/* 1357 */       this.swingProgressInt = -1;
/* 1358 */       this.isSwingInProgress = true;
/*      */       
/* 1360 */       if (this.worldObj instanceof WorldServer)
/*      */       {
/* 1362 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S0BPacketAnimation(this, 0));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleHealthUpdate(byte id) {
/* 1369 */     if (id == 2) {
/*      */       
/* 1371 */       this.limbSwingAmount = 1.5F;
/* 1372 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 1373 */       this.hurtTime = this.maxHurtTime = 10;
/* 1374 */       this.ticksSinceLastDamage = 0;
/* 1375 */       this.attackedAtYaw = 0.0F;
/* 1376 */       String s = getHurtSound();
/*      */       
/* 1378 */       if (s != null)
/*      */       {
/* 1380 */         playSound(getHurtSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1383 */       attackEntityFrom(DamageSource.generic, 0.0F);
/*      */     }
/* 1385 */     else if (id == 3) {
/*      */       
/* 1387 */       String s1 = getDeathSound();
/*      */       
/* 1389 */       if (s1 != null)
/*      */       {
/* 1391 */         playSound(getDeathSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1394 */       setHealth(0.0F);
/* 1395 */       onDeath(DamageSource.generic);
/*      */     }
/*      */     else {
/*      */       
/* 1399 */       super.handleHealthUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/* 1408 */     attackEntityFrom(DamageSource.outOfWorld, 4.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateArmSwingProgress() {
/* 1416 */     int i = getArmSwingAnimationEnd();
/*      */     
/* 1418 */     if (this.isSwingInProgress) {
/*      */       
/* 1420 */       this.swingProgressInt++;
/*      */       
/* 1422 */       if (this.swingProgressInt >= i)
/*      */       {
/* 1424 */         this.swingProgressInt = 0;
/* 1425 */         this.isSwingInProgress = false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1430 */       this.swingProgressInt = 0;
/*      */     } 
/*      */     
/* 1433 */     this.swingProgress = this.swingProgressInt / i;
/*      */   }
/*      */ 
/*      */   
/*      */   public IAttributeInstance getEntityAttribute(IAttribute attribute) {
/* 1438 */     return getAttributeMap().getAttributeInstance(attribute);
/*      */   }
/*      */ 
/*      */   
/*      */   public BaseAttributeMap getAttributeMap() {
/* 1443 */     if (this.attributeMap == null)
/*      */     {
/* 1445 */       this.attributeMap = (BaseAttributeMap)new ServersideAttributeMap();
/*      */     }
/*      */     
/* 1448 */     return this.attributeMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumCreatureAttribute getCreatureAttribute() {
/* 1456 */     return EnumCreatureAttribute.UNDEFINED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getHeldItem();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getEquipmentInSlot(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getCurrentArmor(int paramInt);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setCurrentItemOrArmor(int paramInt, ItemStack paramItemStack);
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1481 */     super.setSprinting(sprinting);
/* 1482 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*      */     
/* 1484 */     if (iattributeinstance.getModifier(sprintingSpeedBoostModifierUUID) != null)
/*      */     {
/* 1486 */       iattributeinstance.removeModifier(sprintingSpeedBoostModifier);
/*      */     }
/*      */     
/* 1489 */     if (sprinting)
/*      */     {
/* 1491 */       iattributeinstance.applyModifier(sprintingSpeedBoostModifier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack[] getInventory();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/* 1505 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundPitch() {
/* 1513 */     return isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/* 1521 */     return (getHealth() <= 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismountEntity(Entity p_110145_1_) {
/* 1529 */     double d0 = p_110145_1_.posX;
/* 1530 */     double d1 = (p_110145_1_.getEntityBoundingBox()).minY + p_110145_1_.height;
/* 1531 */     double d2 = p_110145_1_.posZ;
/* 1532 */     int i = 1;
/*      */     
/* 1534 */     for (int j = -i; j <= i; j++) {
/*      */       
/* 1536 */       for (int k = -i; k < i; k++) {
/*      */         
/* 1538 */         if (j != 0 || k != 0) {
/*      */           
/* 1540 */           int l = (int)(this.posX + j);
/* 1541 */           int i1 = (int)(this.posZ + k);
/* 1542 */           AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(j, 1.0D, k);
/*      */           
/* 1544 */           if (this.worldObj.func_147461_a(axisalignedbb).isEmpty()) {
/*      */             
/* 1546 */             if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(l, (int)this.posY, i1))) {
/*      */               
/* 1548 */               setPositionAndUpdate(this.posX + j, this.posY + 1.0D, this.posZ + k);
/*      */               
/*      */               return;
/*      */             } 
/* 1552 */             if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(l, (int)this.posY - 1, i1)) || this.worldObj.getBlockState(new BlockPos(l, (int)this.posY - 1, i1)).getBlock().getMaterial() == Material.water) {
/*      */               
/* 1554 */               d0 = this.posX + j;
/* 1555 */               d1 = this.posY + 1.0D;
/* 1556 */               d2 = this.posZ + k;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1563 */     setPositionAndUpdate(d0, d1, d2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 1568 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getJumpUpwardsMotion() {
/* 1573 */     return 0.42F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void jump() {
/* 1581 */     float jumpYaw = this.rotationYaw;
/* 1582 */     if (RotationUtil.isEnabled && RotationUtil.strafeFix) {
/* 1583 */       jumpYaw = RotationUtil.clientRotation[0];
/*      */     }
/* 1585 */     JumpEvent event = new JumpEvent(jumpYaw);
/* 1586 */     if (this == mc.getPlayer() && event.call().isCanceled())
/*      */       return; 
/* 1588 */     this.motionY = getJumpUpwardsMotion();
/*      */     
/* 1590 */     if (isPotionActive(Potion.jump))
/*      */     {
/* 1592 */       this.motionY += ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
/*      */     }
/*      */     
/* 1595 */     if (isSprinting()) {
/*      */       
/* 1597 */       float f = event.getYaw() * 0.017453292F;
/* 1598 */       this.motionX -= (MathHelper.sin(f) * 0.2F);
/* 1599 */       this.motionZ += (MathHelper.cos(f) * 0.2F);
/*      */     } 
/*      */     
/* 1602 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITick() {
/* 1610 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleJumpLava() {
/* 1615 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1623 */     if (isServerWorld())
/*      */     {
/* 1625 */       if (!isInWater() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/*      */         
/* 1627 */         if (!isInLava() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying))
/*      */         {
/* 1629 */           float f5, f4 = 0.91F;
/*      */           
/* 1631 */           if (this.onGround)
/*      */           {
/* 1633 */             f4 = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*      */           }
/*      */           
/* 1636 */           float f = 0.16277136F / f4 * f4 * f4;
/*      */ 
/*      */           
/* 1639 */           if (this.onGround) {
/*      */             
/* 1641 */             f5 = getAIMoveSpeed() * f;
/*      */           }
/*      */           else {
/*      */             
/* 1645 */             f5 = this.jumpMovementFactor;
/*      */           } 
/*      */           
/* 1648 */           moveFlying(strafe, forward, f5);
/* 1649 */           f4 = 0.91F;
/*      */           
/* 1651 */           if (this.onGround)
/*      */           {
/* 1653 */             f4 = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*      */           }
/*      */           
/* 1656 */           if (isOnLadder()) {
/*      */             
/* 1658 */             float f6 = 0.15F;
/* 1659 */             this.motionX = MathHelper.clamp_double(this.motionX, -f6, f6);
/* 1660 */             this.motionZ = MathHelper.clamp_double(this.motionZ, -f6, f6);
/* 1661 */             this.fallDistance = 0.0F;
/*      */             
/* 1663 */             if (this.motionY < -0.15D)
/*      */             {
/* 1665 */               this.motionY = -0.15D;
/*      */             }
/*      */             
/* 1668 */             boolean flag = (isSneaking() && this instanceof EntityPlayer);
/*      */             
/* 1670 */             if (flag && this.motionY < 0.0D)
/*      */             {
/* 1672 */               this.motionY = 0.0D;
/*      */             }
/*      */           } 
/*      */           
/* 1676 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/*      */           
/* 1678 */           if (this.isCollidedHorizontally && isOnLadder())
/*      */           {
/* 1680 */             this.motionY = 0.2D;
/*      */           }
/*      */           
/* 1683 */           if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
/*      */             
/* 1685 */             if (this.posY > 0.0D)
/*      */             {
/* 1687 */               this.motionY = -0.1D;
/*      */             }
/*      */             else
/*      */             {
/* 1691 */               this.motionY = 0.0D;
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1696 */             this.motionY -= 0.08D;
/*      */           } 
/*      */           
/* 1699 */           this.motionY *= 0.9800000190734863D;
/* 1700 */           this.motionX *= f4;
/* 1701 */           this.motionZ *= f4;
/*      */         }
/*      */         else
/*      */         {
/* 1705 */           double d1 = this.posY;
/* 1706 */           moveFlying(strafe, forward, 0.02F);
/* 1707 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1708 */           this.motionX *= 0.5D;
/* 1709 */           this.motionY *= 0.5D;
/* 1710 */           this.motionZ *= 0.5D;
/* 1711 */           this.motionY -= 0.02D;
/*      */           
/* 1713 */           if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d1, this.motionZ))
/*      */           {
/* 1715 */             this.motionY = 0.30000001192092896D;
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1721 */         double d0 = this.posY;
/* 1722 */         float f1 = 0.8F;
/* 1723 */         float f2 = 0.02F;
/* 1724 */         float f3 = EnchantmentHelper.getDepthStriderModifier(this);
/*      */         
/* 1726 */         if (f3 > 3.0F)
/*      */         {
/* 1728 */           f3 = 3.0F;
/*      */         }
/*      */         
/* 1731 */         if (!this.onGround)
/*      */         {
/* 1733 */           f3 *= 0.5F;
/*      */         }
/*      */         
/* 1736 */         if (f3 > 0.0F) {
/*      */           
/* 1738 */           f1 += (0.54600006F - f1) * f3 / 3.0F;
/* 1739 */           f2 += (getAIMoveSpeed() * 1.0F - f2) * f3 / 3.0F;
/*      */         } 
/*      */         
/* 1742 */         moveFlying(strafe, forward, f2);
/* 1743 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1744 */         this.motionX *= f1;
/* 1745 */         this.motionY *= 0.800000011920929D;
/* 1746 */         this.motionZ *= f1;
/* 1747 */         this.motionY -= 0.02D;
/*      */         
/* 1749 */         if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ))
/*      */         {
/* 1751 */           this.motionY = 0.30000001192092896D;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1756 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1757 */     double d2 = this.posX - this.prevPosX;
/* 1758 */     double d3 = this.posZ - this.prevPosZ;
/* 1759 */     float f7 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4.0F;
/*      */     
/* 1761 */     if (f7 > 1.0F)
/*      */     {
/* 1763 */       f7 = 1.0F;
/*      */     }
/*      */     
/* 1766 */     this.limbSwingAmount += (f7 - this.limbSwingAmount) * 0.4F;
/* 1767 */     this.limbSwing += this.limbSwingAmount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1775 */     return this.landMovementFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/* 1783 */     this.landMovementFactor = speedIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attackEntityAsMob(Entity entityIn) {
/* 1788 */     setLastAttacker(entityIn);
/* 1789 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1797 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 1805 */     super.onUpdate();
/*      */     
/* 1807 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1809 */       int i = getArrowCountInEntity();
/*      */       
/* 1811 */       if (i > 0) {
/*      */         
/* 1813 */         if (this.arrowHitTimer <= 0)
/*      */         {
/* 1815 */           this.arrowHitTimer = 20 * (30 - i);
/*      */         }
/*      */         
/* 1818 */         this.arrowHitTimer--;
/*      */         
/* 1820 */         if (this.arrowHitTimer <= 0)
/*      */         {
/* 1822 */           setArrowCountInEntity(i - 1);
/*      */         }
/*      */       } 
/*      */       
/* 1826 */       for (int j = 0; j < 5; j++) {
/*      */         
/* 1828 */         ItemStack itemstack = this.previousEquipment[j];
/* 1829 */         ItemStack itemstack1 = getEquipmentInSlot(j);
/*      */         
/* 1831 */         if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/*      */           
/* 1833 */           ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S04PacketEntityEquipment(getEntityId(), j, itemstack1));
/*      */           
/* 1835 */           if (itemstack != null)
/*      */           {
/* 1837 */             this.attributeMap.removeAttributeModifiers(itemstack.getAttributeModifiers());
/*      */           }
/*      */           
/* 1840 */           if (itemstack1 != null)
/*      */           {
/* 1842 */             this.attributeMap.applyAttributeModifiers(itemstack1.getAttributeModifiers());
/*      */           }
/*      */           
/* 1845 */           this.previousEquipment[j] = (itemstack1 == null) ? null : itemstack1.copy();
/*      */         } 
/*      */       } 
/*      */       
/* 1849 */       if (this.ticksExisted % 20 == 0)
/*      */       {
/* 1851 */         getCombatTracker().reset();
/*      */       }
/*      */     } 
/*      */     
/* 1855 */     onLivingUpdate();
/* 1856 */     double d0 = this.posX - this.prevPosX;
/* 1857 */     double d1 = this.posZ - this.prevPosZ;
/* 1858 */     float f = (float)(d0 * d0 + d1 * d1);
/* 1859 */     float f1 = this.renderYawOffset;
/* 1860 */     float f2 = 0.0F;
/* 1861 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 1862 */     float f3 = 0.0F;
/*      */     
/* 1864 */     if (f > 0.0025000002F) {
/*      */       
/* 1866 */       f3 = 1.0F;
/* 1867 */       f2 = (float)Math.sqrt(f) * 3.0F;
/* 1868 */       f1 = (float)MathHelper.func_181159_b(d1, d0) * 180.0F / 3.1415927F - 90.0F;
/*      */     } 
/*      */     
/* 1871 */     if (this.swingProgress > 0.0F)
/*      */     {
/* 1873 */       f1 = this.rotationYaw;
/*      */     }
/*      */     
/* 1876 */     if (!this.onGround)
/*      */     {
/* 1878 */       f3 = 0.0F;
/*      */     }
/*      */     
/* 1881 */     this.onGroundSpeedFactor += (f3 - this.onGroundSpeedFactor) * 0.3F;
/* 1882 */     this.worldObj.theProfiler.startSection("headTurn");
/* 1883 */     f2 = func_110146_f(f1, f2);
/* 1884 */     this.worldObj.theProfiler.endSection();
/* 1885 */     this.worldObj.theProfiler.startSection("rangeChecks");
/*      */     
/* 1887 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*      */     {
/* 1889 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1892 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*      */     {
/* 1894 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1897 */     while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F)
/*      */     {
/* 1899 */       this.prevRenderYawOffset -= 360.0F;
/*      */     }
/*      */     
/* 1902 */     while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F)
/*      */     {
/* 1904 */       this.prevRenderYawOffset += 360.0F;
/*      */     }
/*      */     
/* 1907 */     while (this.rotationPitch - this.prevRotationPitch < -180.0F)
/*      */     {
/* 1909 */       this.prevRotationPitch -= 360.0F;
/*      */     }
/*      */     
/* 1912 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*      */     {
/* 1914 */       this.prevRotationPitch += 360.0F;
/*      */     }
/*      */     
/* 1917 */     while (this.rotationYawHead - this.prevRotationYawHead < -180.0F)
/*      */     {
/* 1919 */       this.prevRotationYawHead -= 360.0F;
/*      */     }
/*      */     
/* 1922 */     while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F)
/*      */     {
/* 1924 */       this.prevRotationYawHead += 360.0F;
/*      */     }
/*      */     
/* 1927 */     this.worldObj.theProfiler.endSection();
/* 1928 */     this.movedDistance += f2;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
/* 1933 */     float f = MathHelper.wrapAngleTo180_float(p_110146_1_ - this.renderYawOffset);
/* 1934 */     this.renderYawOffset += f * 0.3F;
/* 1935 */     float f1 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
/* 1936 */     boolean flag = (f1 < -90.0F || f1 >= 90.0F);
/*      */     
/* 1938 */     if (f1 < -75.0F)
/*      */     {
/* 1940 */       f1 = -75.0F;
/*      */     }
/*      */     
/* 1943 */     if (f1 >= 75.0F)
/*      */     {
/* 1945 */       f1 = 75.0F;
/*      */     }
/*      */     
/* 1948 */     this.renderYawOffset = this.rotationYaw - f1;
/*      */     
/* 1950 */     if (f1 * f1 > 2500.0F)
/*      */     {
/* 1952 */       this.renderYawOffset += f1 * 0.2F;
/*      */     }
/*      */     
/* 1955 */     if (flag)
/*      */     {
/* 1957 */       p_110146_2_ *= -1.0F;
/*      */     }
/*      */     
/* 1960 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/* 1969 */     if (this.jumpTicks > 0)
/*      */     {
/* 1971 */       this.jumpTicks--;
/*      */     }
/*      */     
/* 1974 */     if (this.newPosRotationIncrements > 0) {
/*      */       
/* 1976 */       double d0 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 1977 */       double d1 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 1978 */       double d2 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 1979 */       double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 1980 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.newPosRotationIncrements);
/* 1981 */       this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
/* 1982 */       this.renderPitch = this.rotationPitch;
/* 1983 */       this.newPosRotationIncrements--;
/* 1984 */       setPosition(d0, d1, d2);
/* 1985 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */     }
/* 1987 */     else if (!isServerWorld()) {
/*      */       
/* 1989 */       this.motionX *= 0.98D;
/* 1990 */       this.motionY *= 0.98D;
/* 1991 */       this.motionZ *= 0.98D;
/*      */     } 
/*      */     
/* 1994 */     if (Math.abs(this.motionX) < 0.005D)
/*      */     {
/* 1996 */       this.motionX = 0.0D;
/*      */     }
/*      */     
/* 1999 */     if (Math.abs(this.motionY) < 0.005D)
/*      */     {
/* 2001 */       this.motionY = 0.0D;
/*      */     }
/*      */     
/* 2004 */     if (Math.abs(this.motionZ) < 0.005D)
/*      */     {
/* 2006 */       this.motionZ = 0.0D;
/*      */     }
/*      */     
/* 2009 */     this.worldObj.theProfiler.startSection("ai");
/*      */     
/* 2011 */     if (isMovementBlocked()) {
/*      */       
/* 2013 */       this.isJumping = false;
/* 2014 */       this.moveStrafing = 0.0F;
/* 2015 */       this.moveForward = 0.0F;
/* 2016 */       this.randomYawVelocity = 0.0F;
/*      */     }
/* 2018 */     else if (isServerWorld()) {
/*      */       
/* 2020 */       this.worldObj.theProfiler.startSection("newAi");
/* 2021 */       updateEntityActionState();
/* 2022 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/* 2025 */     this.worldObj.theProfiler.endSection();
/* 2026 */     this.worldObj.theProfiler.startSection("jump");
/*      */     
/* 2028 */     if (this.isJumping) {
/* 2029 */       if (isInWater()) {
/* 2030 */         updateAITick();
/* 2031 */       } else if (isInLava()) {
/* 2032 */         handleJumpLava();
/* 2033 */       } else if (this.onGround && this.jumpTicks == 0) {
/* 2034 */         jump();
/* 2035 */         this.jumpTicks = 10;
/* 2036 */         if (((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).isToggle() && (
/* 2037 */           (Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).nojumpdelay.getValue()).booleanValue()) {
/* 2038 */           this.jumpTicks = ((Integer)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).noJumpDelayTicks.getValue()).intValue();
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 2045 */       this.jumpTicks = 0;
/*      */     } 
/* 2047 */     this.worldObj.theProfiler.endSection();
/* 2048 */     this.worldObj.theProfiler.startSection("travel");
/* 2049 */     this.moveStrafing *= 0.98F;
/* 2050 */     this.moveForward *= 0.98F;
/* 2051 */     this.randomYawVelocity *= 0.9F;
/* 2052 */     moveEntityWithHeading(this.moveStrafing, this.moveForward);
/* 2053 */     this.worldObj.theProfiler.endSection();
/* 2054 */     this.worldObj.theProfiler.startSection("push");
/*      */     
/* 2056 */     if (!this.worldObj.isRemote)
/*      */     {
/* 2058 */       collideWithNearbyEntities();
/*      */     }
/*      */     
/* 2061 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {}
/*      */ 
/*      */   
/*      */   protected void collideWithNearbyEntities() {
/* 2070 */     List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
/*      */           {
/*      */             public boolean apply(Entity p_apply_1_)
/*      */             {
/* 2074 */               return p_apply_1_.canBePushed();
/*      */             }
/*      */           }));
/*      */     
/* 2078 */     if (!list.isEmpty())
/*      */     {
/* 2080 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/* 2082 */         Entity entity = list.get(i);
/* 2083 */         collideWithEntity(entity);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void collideWithEntity(Entity p_82167_1_) {
/* 2090 */     p_82167_1_.applyEntityCollision(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 2098 */     if (this.ridingEntity != null && entityIn == null) {
/*      */       
/* 2100 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2102 */         dismountEntity(this.ridingEntity);
/*      */       }
/*      */       
/* 2105 */       if (this.ridingEntity != null)
/*      */       {
/* 2107 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 2110 */       this.ridingEntity = null;
/*      */     }
/*      */     else {
/*      */       
/* 2114 */       super.mountEntity(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 2123 */     super.updateRidden();
/* 2124 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 2125 */     this.onGroundSpeedFactor = 0.0F;
/* 2126 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 2131 */     this.newPosX = x;
/* 2132 */     this.newPosY = y;
/* 2133 */     this.newPosZ = z;
/* 2134 */     this.newRotationYaw = yaw;
/* 2135 */     this.newRotationPitch = pitch;
/* 2136 */     this.renderPitch = pitch;
/* 2137 */     this.newPosRotationIncrements = posRotationIncrements;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJumping(boolean p_70637_1_) {
/* 2142 */     this.isJumping = p_70637_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
/* 2150 */     if (!p_71001_1_.isDead && !this.worldObj.isRemote) {
/*      */       
/* 2152 */       EntityTracker entitytracker = ((WorldServer)this.worldObj).getEntityTracker();
/*      */       
/* 2154 */       if (p_71001_1_ instanceof net.minecraft.entity.item.EntityItem)
/*      */       {
/* 2156 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */       
/* 2159 */       if (p_71001_1_ instanceof net.minecraft.entity.projectile.EntityArrow)
/*      */       {
/* 2161 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */       
/* 2164 */       if (p_71001_1_ instanceof EntityXPOrb)
/*      */       {
/* 2166 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canEntityBeSeen(Entity entityIn) {
/* 2176 */     return (this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ), new Vec3(entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ)) == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 2184 */     return getLook(1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 2192 */     if (partialTicks == 1.0F)
/*      */     {
/* 2194 */       return getVectorForRotation(this.rotationPitch, this.rotationYawHead);
/*      */     }
/*      */ 
/*      */     
/* 2198 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 2199 */     float f1 = this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * partialTicks;
/* 2200 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSwingProgress(float partialTickTime) {
/* 2209 */     float f = this.swingProgress - this.prevSwingProgress;
/*      */     
/* 2211 */     if (f < 0.0F)
/*      */     {
/* 2213 */       f++;
/*      */     }
/*      */     
/* 2216 */     return this.prevSwingProgress + f * partialTickTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 2224 */     return !this.worldObj.isRemote;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 2232 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 2240 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 2248 */     this.velocityChanged = (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRotationYawHead() {
/* 2253 */     return this.rotationYawHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {
/* 2261 */     this.rotationYawHead = rotation;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181013_g(float p_181013_1_) {
/* 2266 */     this.renderYawOffset = p_181013_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2271 */     return this.absorptionAmount;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 2276 */     if (amount < 0.0F)
/*      */     {
/* 2278 */       amount = 0.0F;
/*      */     }
/*      */     
/* 2281 */     this.absorptionAmount = amount;
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 2286 */     return (Team)this.worldObj.getScoreboard().getPlayersTeam(getUniqueID().toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOnSameTeam(EntityLivingBase otherEntity) {
/* 2291 */     return isOnTeam(otherEntity.getTeam());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnTeam(Team p_142012_1_) {
/* 2299 */     return (getTeam() != null) ? getTeam().isSameTeam(p_142012_1_) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void markPotionsDirty() {
/* 2318 */     this.potionsNeedUpdate = true;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\EntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */