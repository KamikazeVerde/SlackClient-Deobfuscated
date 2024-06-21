/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import java.util.UUID;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.EntityAITasks;
/*      */ import net.minecraft.entity.ai.EntityJumpHelper;
/*      */ import net.minecraft.entity.ai.EntityLookHelper;
/*      */ import net.minecraft.entity.ai.EntityMoveHelper;
/*      */ import net.minecraft.entity.ai.EntitySenses;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityGhast;
/*      */ import net.minecraft.entity.passive.EntityTameable;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemSword;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.pathfinding.PathNavigate;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.optifine.reflect.Reflector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class EntityLiving
/*      */   extends EntityLivingBase
/*      */ {
/*      */   public int livingSoundTime;
/*      */   protected int experienceValue;
/*      */   private EntityLookHelper lookHelper;
/*      */   protected EntityMoveHelper moveHelper;
/*      */   protected EntityJumpHelper jumpHelper;
/*      */   private EntityBodyHelper bodyHelper;
/*      */   protected PathNavigate navigator;
/*      */   protected final EntityAITasks tasks;
/*      */   protected final EntityAITasks targetTasks;
/*      */   private EntityLivingBase attackTarget;
/*      */   private EntitySenses senses;
/*   69 */   private ItemStack[] equipment = new ItemStack[5];
/*      */ 
/*      */   
/*   72 */   protected float[] equipmentDropChances = new float[5];
/*      */   
/*      */   private boolean canPickUpLoot;
/*      */   
/*      */   private boolean persistenceRequired;
/*      */   
/*      */   private boolean isLeashed;
/*      */   
/*      */   private Entity leashedToEntity;
/*      */   private NBTTagCompound leashNBTTag;
/*   82 */   private UUID teamUuid = null;
/*   83 */   private String teamUuidString = null;
/*      */ 
/*      */   
/*      */   public EntityLiving(World worldIn) {
/*   87 */     super(worldIn);
/*   88 */     this.tasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*   89 */     this.targetTasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*   90 */     this.lookHelper = new EntityLookHelper(this);
/*   91 */     this.moveHelper = new EntityMoveHelper(this);
/*   92 */     this.jumpHelper = new EntityJumpHelper(this);
/*   93 */     this.bodyHelper = new EntityBodyHelper(this);
/*   94 */     this.navigator = getNewNavigator(worldIn);
/*   95 */     this.senses = new EntitySenses(this);
/*      */     
/*   97 */     for (int i = 0; i < this.equipmentDropChances.length; i++)
/*      */     {
/*   99 */       this.equipmentDropChances[i] = 0.085F;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  105 */     super.applyEntityAttributes();
/*  106 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PathNavigate getNewNavigator(World worldIn) {
/*  114 */     return (PathNavigate)new PathNavigateGround(this, worldIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLookHelper getLookHelper() {
/*  119 */     return this.lookHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityMoveHelper getMoveHelper() {
/*  124 */     return this.moveHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityJumpHelper getJumpHelper() {
/*  129 */     return this.jumpHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public PathNavigate getNavigator() {
/*  134 */     return this.navigator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntitySenses getEntitySenses() {
/*  142 */     return this.senses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityLivingBase getAttackTarget() {
/*  150 */     return this.attackTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
/*  158 */     this.attackTarget = entitylivingbaseIn;
/*  159 */     Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, new Object[] { this, entitylivingbaseIn });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
/*  167 */     return (cls != EntityGhast.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eatGrassBonus() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  180 */     super.entityInit();
/*  181 */     this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  189 */     return 80;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playLivingSound() {
/*  197 */     String s = getLivingSound();
/*      */     
/*  199 */     if (s != null)
/*      */     {
/*  201 */       playSound(s, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  210 */     super.onEntityUpdate();
/*  211 */     this.worldObj.theProfiler.startSection("mobBaseTick");
/*      */     
/*  213 */     if (isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
/*      */       
/*  215 */       this.livingSoundTime = -getTalkInterval();
/*  216 */       playLivingSound();
/*      */     } 
/*      */     
/*  219 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  227 */     if (this.experienceValue > 0) {
/*      */       
/*  229 */       int i = this.experienceValue;
/*  230 */       ItemStack[] aitemstack = getInventory();
/*      */       
/*  232 */       for (int j = 0; j < aitemstack.length; j++) {
/*      */         
/*  234 */         if (aitemstack[j] != null && this.equipmentDropChances[j] <= 1.0F)
/*      */         {
/*  236 */           i += 1 + this.rand.nextInt(3);
/*      */         }
/*      */       } 
/*      */       
/*  240 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  244 */     return this.experienceValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnExplosionParticle() {
/*  253 */     if (this.worldObj.isRemote) {
/*      */       
/*  255 */       for (int i = 0; i < 20; i++)
/*      */       {
/*  257 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  258 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  259 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  260 */         double d3 = 10.0D;
/*  261 */         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d0 * d3, this.posY + (this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d2 * d3, d0, d1, d2, new int[0]);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  266 */       this.worldObj.setEntityState(this, (byte)20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleHealthUpdate(byte id) {
/*  272 */     if (id == 20) {
/*      */       
/*  274 */       spawnExplosionParticle();
/*      */     }
/*      */     else {
/*      */       
/*  278 */       super.handleHealthUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  287 */     if (Config.isSmoothWorld() && canSkipUpdate()) {
/*      */       
/*  289 */       onUpdateMinimal();
/*      */     }
/*      */     else {
/*      */       
/*  293 */       super.onUpdate();
/*      */       
/*  295 */       if (!this.worldObj.isRemote)
/*      */       {
/*  297 */         updateLeashedState();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
/*  304 */     this.bodyHelper.updateRenderAngles();
/*  305 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  313 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item getDropItem() {
/*  318 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
/*  326 */     Item item = getDropItem();
/*      */     
/*  328 */     if (item != null) {
/*      */       
/*  330 */       int i = this.rand.nextInt(3);
/*      */       
/*  332 */       if (p_70628_2_ > 0)
/*      */       {
/*  334 */         i += this.rand.nextInt(p_70628_2_ + 1);
/*      */       }
/*      */       
/*  337 */       for (int j = 0; j < i; j++)
/*      */       {
/*  339 */         dropItem(item, 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  349 */     super.writeEntityToNBT(tagCompound);
/*  350 */     tagCompound.setBoolean("CanPickUpLoot", canPickUpLoot());
/*  351 */     tagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
/*  352 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  354 */     for (int i = 0; i < this.equipment.length; i++) {
/*      */       
/*  356 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */       
/*  358 */       if (this.equipment[i] != null)
/*      */       {
/*  360 */         this.equipment[i].writeToNBT(nbttagcompound);
/*      */       }
/*      */       
/*  363 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */     } 
/*      */     
/*  366 */     tagCompound.setTag("Equipment", (NBTBase)nbttaglist);
/*  367 */     NBTTagList nbttaglist1 = new NBTTagList();
/*      */     
/*  369 */     for (int j = 0; j < this.equipmentDropChances.length; j++)
/*      */     {
/*  371 */       nbttaglist1.appendTag((NBTBase)new NBTTagFloat(this.equipmentDropChances[j]));
/*      */     }
/*      */     
/*  374 */     tagCompound.setTag("DropChances", (NBTBase)nbttaglist1);
/*  375 */     tagCompound.setBoolean("Leashed", this.isLeashed);
/*      */     
/*  377 */     if (this.leashedToEntity != null) {
/*      */       
/*  379 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */       
/*  381 */       if (this.leashedToEntity instanceof EntityLivingBase) {
/*      */         
/*  383 */         nbttagcompound1.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
/*  384 */         nbttagcompound1.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
/*      */       }
/*  386 */       else if (this.leashedToEntity instanceof EntityHanging) {
/*      */         
/*  388 */         BlockPos blockpos = ((EntityHanging)this.leashedToEntity).getHangingPosition();
/*  389 */         nbttagcompound1.setInteger("X", blockpos.getX());
/*  390 */         nbttagcompound1.setInteger("Y", blockpos.getY());
/*  391 */         nbttagcompound1.setInteger("Z", blockpos.getZ());
/*      */       } 
/*      */       
/*  394 */       tagCompound.setTag("Leash", (NBTBase)nbttagcompound1);
/*      */     } 
/*      */     
/*  397 */     if (isAIDisabled())
/*      */     {
/*  399 */       tagCompound.setBoolean("NoAI", isAIDisabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  408 */     super.readEntityFromNBT(tagCompund);
/*      */     
/*  410 */     if (tagCompund.hasKey("CanPickUpLoot", 1))
/*      */     {
/*  412 */       setCanPickUpLoot(tagCompund.getBoolean("CanPickUpLoot"));
/*      */     }
/*      */     
/*  415 */     this.persistenceRequired = tagCompund.getBoolean("PersistenceRequired");
/*      */     
/*  417 */     if (tagCompund.hasKey("Equipment", 9)) {
/*      */       
/*  419 */       NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);
/*      */       
/*  421 */       for (int i = 0; i < this.equipment.length; i++)
/*      */       {
/*  423 */         this.equipment[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*      */       }
/*      */     } 
/*      */     
/*  427 */     if (tagCompund.hasKey("DropChances", 9)) {
/*      */       
/*  429 */       NBTTagList nbttaglist1 = tagCompund.getTagList("DropChances", 5);
/*      */       
/*  431 */       for (int j = 0; j < nbttaglist1.tagCount(); j++)
/*      */       {
/*  433 */         this.equipmentDropChances[j] = nbttaglist1.getFloatAt(j);
/*      */       }
/*      */     } 
/*      */     
/*  437 */     this.isLeashed = tagCompund.getBoolean("Leashed");
/*      */     
/*  439 */     if (this.isLeashed && tagCompund.hasKey("Leash", 10))
/*      */     {
/*  441 */       this.leashNBTTag = tagCompund.getCompoundTag("Leash");
/*      */     }
/*      */     
/*  444 */     setNoAI(tagCompund.getBoolean("NoAI"));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMoveForward(float p_70657_1_) {
/*  449 */     this.moveForward = p_70657_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/*  457 */     super.setAIMoveSpeed(speedIn);
/*  458 */     setMoveForward(speedIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  467 */     super.onLivingUpdate();
/*  468 */     this.worldObj.theProfiler.startSection("looting");
/*      */     
/*  470 */     if (!this.worldObj.isRemote && canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
/*      */     {
/*  472 */       for (EntityItem entityitem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D))) {
/*      */         
/*  474 */         if (!entityitem.isDead && entityitem.getEntityItem() != null && !entityitem.cannotPickup())
/*      */         {
/*  476 */           updateEquipmentIfNeeded(entityitem);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  481 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  490 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  491 */     int i = getArmorPosition(itemstack);
/*      */     
/*  493 */     if (i > -1) {
/*      */       
/*  495 */       boolean flag = true;
/*  496 */       ItemStack itemstack1 = getEquipmentInSlot(i);
/*      */       
/*  498 */       if (itemstack1 != null)
/*      */       {
/*  500 */         if (i == 0) {
/*      */           
/*  502 */           if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
/*      */             
/*  504 */             flag = true;
/*      */           }
/*  506 */           else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
/*      */             
/*  508 */             ItemSword itemsword = (ItemSword)itemstack.getItem();
/*  509 */             ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
/*      */             
/*  511 */             if (itemsword.getDamageVsEntity() != itemsword1.getDamageVsEntity())
/*      */             {
/*  513 */               flag = (itemsword.getDamageVsEntity() > itemsword1.getDamageVsEntity());
/*      */             }
/*      */             else
/*      */             {
/*  517 */               flag = (itemstack.getMetadata() > itemstack1.getMetadata() || (itemstack.hasTagCompound() && !itemstack1.hasTagCompound()));
/*      */             }
/*      */           
/*  520 */           } else if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && itemstack1.getItem() instanceof net.minecraft.item.ItemBow) {
/*      */             
/*  522 */             flag = (itemstack.hasTagCompound() && !itemstack1.hasTagCompound());
/*      */           }
/*      */           else {
/*      */             
/*  526 */             flag = false;
/*      */           }
/*      */         
/*  529 */         } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
/*      */           
/*  531 */           flag = true;
/*      */         }
/*  533 */         else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor) {
/*      */           
/*  535 */           ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*  536 */           ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
/*      */           
/*  538 */           if (itemarmor.damageReduceAmount != itemarmor1.damageReduceAmount)
/*      */           {
/*  540 */             flag = (itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount);
/*      */           }
/*      */           else
/*      */           {
/*  544 */             flag = (itemstack.getMetadata() > itemstack1.getMetadata() || (itemstack.hasTagCompound() && !itemstack1.hasTagCompound()));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  549 */           flag = false;
/*      */         } 
/*      */       }
/*      */       
/*  553 */       if (flag && func_175448_a(itemstack)) {
/*      */         
/*  555 */         if (itemstack1 != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[i])
/*      */         {
/*  557 */           entityDropItem(itemstack1, 0.0F);
/*      */         }
/*      */         
/*  560 */         if (itemstack.getItem() == Items.diamond && itemEntity.getThrower() != null) {
/*      */           
/*  562 */           EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(itemEntity.getThrower());
/*      */           
/*  564 */           if (entityplayer != null)
/*      */           {
/*  566 */             entityplayer.triggerAchievement((StatBase)AchievementList.diamondsToYou);
/*      */           }
/*      */         } 
/*      */         
/*  570 */         setCurrentItemOrArmor(i, itemstack);
/*  571 */         this.equipmentDropChances[i] = 2.0F;
/*  572 */         this.persistenceRequired = true;
/*  573 */         onItemPickup((Entity)itemEntity, 1);
/*  574 */         itemEntity.setDead();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_175448_a(ItemStack stack) {
/*  581 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  589 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void despawnEntity() {
/*  597 */     Object object = null;
/*  598 */     Object object1 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*  599 */     Object object2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*      */     
/*  601 */     if (this.persistenceRequired) {
/*      */       
/*  603 */       this.entityAge = 0;
/*      */     }
/*  605 */     else if ((this.entityAge & 0x1F) == 31 && (object = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, new Object[] { this })) != object1) {
/*      */       
/*  607 */       if (object == object2)
/*      */       {
/*  609 */         this.entityAge = 0;
/*      */       }
/*      */       else
/*      */       {
/*  613 */         setDead();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  618 */       EntityPlayer entityPlayer = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
/*      */       
/*  620 */       if (entityPlayer != null) {
/*      */         
/*  622 */         double d0 = ((Entity)entityPlayer).posX - this.posX;
/*  623 */         double d1 = ((Entity)entityPlayer).posY - this.posY;
/*  624 */         double d2 = ((Entity)entityPlayer).posZ - this.posZ;
/*  625 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  627 */         if (canDespawn() && d3 > 16384.0D)
/*      */         {
/*  629 */           setDead();
/*      */         }
/*      */         
/*  632 */         if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && canDespawn()) {
/*      */           
/*  634 */           setDead();
/*      */         }
/*  636 */         else if (d3 < 1024.0D) {
/*      */           
/*  638 */           this.entityAge = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void updateEntityActionState() {
/*  646 */     this.entityAge++;
/*  647 */     this.worldObj.theProfiler.startSection("checkDespawn");
/*  648 */     despawnEntity();
/*  649 */     this.worldObj.theProfiler.endSection();
/*  650 */     this.worldObj.theProfiler.startSection("sensing");
/*  651 */     this.senses.clearSensingCache();
/*  652 */     this.worldObj.theProfiler.endSection();
/*  653 */     this.worldObj.theProfiler.startSection("targetSelector");
/*  654 */     this.targetTasks.onUpdateTasks();
/*  655 */     this.worldObj.theProfiler.endSection();
/*  656 */     this.worldObj.theProfiler.startSection("goalSelector");
/*  657 */     this.tasks.onUpdateTasks();
/*  658 */     this.worldObj.theProfiler.endSection();
/*  659 */     this.worldObj.theProfiler.startSection("navigation");
/*  660 */     this.navigator.onUpdateNavigation();
/*  661 */     this.worldObj.theProfiler.endSection();
/*  662 */     this.worldObj.theProfiler.startSection("mob tick");
/*  663 */     updateAITasks();
/*  664 */     this.worldObj.theProfiler.endSection();
/*  665 */     this.worldObj.theProfiler.startSection("controls");
/*  666 */     this.worldObj.theProfiler.startSection("move");
/*  667 */     this.moveHelper.onUpdateMoveHelper();
/*  668 */     this.worldObj.theProfiler.endStartSection("look");
/*  669 */     this.lookHelper.onUpdateLook();
/*  670 */     this.worldObj.theProfiler.endStartSection("jump");
/*  671 */     this.jumpHelper.doJump();
/*  672 */     this.worldObj.theProfiler.endSection();
/*  673 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVerticalFaceSpeed() {
/*  686 */     return 40;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void faceEntity(Entity entityIn, float p_70625_2_, float p_70625_3_) {
/*  694 */     double d2, d0 = entityIn.posX - this.posX;
/*  695 */     double d1 = entityIn.posZ - this.posZ;
/*      */ 
/*      */     
/*  698 */     if (entityIn instanceof EntityLivingBase) {
/*      */       
/*  700 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
/*  701 */       d2 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
/*      */     }
/*      */     else {
/*      */       
/*  705 */       d2 = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D - this.posY + getEyeHeight();
/*      */     } 
/*      */     
/*  708 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
/*  709 */     float f = (float)(MathHelper.func_181159_b(d1, d0) * 180.0D / Math.PI) - 90.0F;
/*  710 */     float f1 = (float)-(MathHelper.func_181159_b(d2, d3) * 180.0D / Math.PI);
/*  711 */     this.rotationPitch = updateRotation(this.rotationPitch, f1, p_70625_3_);
/*  712 */     this.rotationYaw = updateRotation(this.rotationYaw, f, p_70625_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
/*  720 */     float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
/*      */     
/*  722 */     if (f > p_70663_3_)
/*      */     {
/*  724 */       f = p_70663_3_;
/*      */     }
/*      */     
/*  727 */     if (f < -p_70663_3_)
/*      */     {
/*  729 */       f = -p_70663_3_;
/*      */     }
/*      */     
/*  732 */     return p_70663_1_ + f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnHere() {
/*  740 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNotColliding() {
/*  748 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRenderSizeModifier() {
/*  756 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  764 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/*  772 */     if (getAttackTarget() == null)
/*      */     {
/*  774 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  778 */     int i = (int)(getHealth() - getMaxHealth() * 0.33F);
/*  779 */     i -= (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
/*      */     
/*  781 */     if (i < 0)
/*      */     {
/*  783 */       i = 0;
/*      */     }
/*      */     
/*  786 */     return i + 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem() {
/*  795 */     return this.equipment[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getEquipmentInSlot(int slotIn) {
/*  803 */     return this.equipment[slotIn];
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentArmor(int slotIn) {
/*  808 */     return this.equipment[slotIn + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/*  816 */     this.equipment[slotIn] = stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/*  824 */     return this.equipment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean p_82160_1_, int p_82160_2_) {
/*  832 */     for (int i = 0; i < (getInventory()).length; i++) {
/*      */       
/*  834 */       ItemStack itemstack = getEquipmentInSlot(i);
/*  835 */       boolean flag = (this.equipmentDropChances[i] > 1.0F);
/*      */       
/*  837 */       if (itemstack != null && (p_82160_1_ || flag) && this.rand.nextFloat() - p_82160_2_ * 0.01F < this.equipmentDropChances[i]) {
/*      */         
/*  839 */         if (!flag && itemstack.isItemStackDamageable()) {
/*      */           
/*  841 */           int j = Math.max(itemstack.getMaxDamage() - 25, 1);
/*  842 */           int k = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(j) + 1);
/*      */           
/*  844 */           if (k > j)
/*      */           {
/*  846 */             k = j;
/*      */           }
/*      */           
/*  849 */           if (k < 1)
/*      */           {
/*  851 */             k = 1;
/*      */           }
/*      */           
/*  854 */           itemstack.setItemDamage(k);
/*      */         } 
/*      */         
/*  857 */         entityDropItem(itemstack, 0.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/*  867 */     if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty()) {
/*      */       
/*  869 */       int i = this.rand.nextInt(2);
/*  870 */       float f = (this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.1F : 0.25F;
/*      */       
/*  872 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/*  874 */         i++;
/*      */       }
/*      */       
/*  877 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/*  879 */         i++;
/*      */       }
/*      */       
/*  882 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/*  884 */         i++;
/*      */       }
/*      */       
/*  887 */       for (int j = 3; j >= 0; j--) {
/*      */         
/*  889 */         ItemStack itemstack = getCurrentArmor(j);
/*      */         
/*  891 */         if (j < 3 && this.rand.nextFloat() < f) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  896 */         if (itemstack == null) {
/*      */           
/*  898 */           Item item = getArmorItemForSlot(j + 1, i);
/*      */           
/*  900 */           if (item != null)
/*      */           {
/*  902 */             setCurrentItemOrArmor(j + 1, new ItemStack(item));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getArmorPosition(ItemStack stack) {
/*  911 */     if (stack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && stack.getItem() != Items.skull) {
/*      */       
/*  913 */       if (stack.getItem() instanceof ItemArmor)
/*      */       {
/*  915 */         switch (((ItemArmor)stack.getItem()).armorType) {
/*      */           
/*      */           case 0:
/*  918 */             return 4;
/*      */           
/*      */           case 1:
/*  921 */             return 3;
/*      */           
/*      */           case 2:
/*  924 */             return 2;
/*      */           
/*      */           case 3:
/*  927 */             return 1;
/*      */         } 
/*      */       
/*      */       }
/*  931 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  935 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Item getArmorItemForSlot(int armorSlot, int itemTier) {
/*  944 */     switch (armorSlot) {
/*      */       
/*      */       case 4:
/*  947 */         if (itemTier == 0)
/*      */         {
/*  949 */           return (Item)Items.leather_helmet;
/*      */         }
/*  951 */         if (itemTier == 1)
/*      */         {
/*  953 */           return (Item)Items.golden_helmet;
/*      */         }
/*  955 */         if (itemTier == 2)
/*      */         {
/*  957 */           return (Item)Items.chainmail_helmet;
/*      */         }
/*  959 */         if (itemTier == 3)
/*      */         {
/*  961 */           return (Item)Items.iron_helmet;
/*      */         }
/*  963 */         if (itemTier == 4)
/*      */         {
/*  965 */           return (Item)Items.diamond_helmet;
/*      */         }
/*      */       
/*      */       case 3:
/*  969 */         if (itemTier == 0)
/*      */         {
/*  971 */           return (Item)Items.leather_chestplate;
/*      */         }
/*  973 */         if (itemTier == 1)
/*      */         {
/*  975 */           return (Item)Items.golden_chestplate;
/*      */         }
/*  977 */         if (itemTier == 2)
/*      */         {
/*  979 */           return (Item)Items.chainmail_chestplate;
/*      */         }
/*  981 */         if (itemTier == 3)
/*      */         {
/*  983 */           return (Item)Items.iron_chestplate;
/*      */         }
/*  985 */         if (itemTier == 4)
/*      */         {
/*  987 */           return (Item)Items.diamond_chestplate;
/*      */         }
/*      */       
/*      */       case 2:
/*  991 */         if (itemTier == 0)
/*      */         {
/*  993 */           return (Item)Items.leather_leggings;
/*      */         }
/*  995 */         if (itemTier == 1)
/*      */         {
/*  997 */           return (Item)Items.golden_leggings;
/*      */         }
/*  999 */         if (itemTier == 2)
/*      */         {
/* 1001 */           return (Item)Items.chainmail_leggings;
/*      */         }
/* 1003 */         if (itemTier == 3)
/*      */         {
/* 1005 */           return (Item)Items.iron_leggings;
/*      */         }
/* 1007 */         if (itemTier == 4)
/*      */         {
/* 1009 */           return (Item)Items.diamond_leggings;
/*      */         }
/*      */       
/*      */       case 1:
/* 1013 */         if (itemTier == 0)
/*      */         {
/* 1015 */           return (Item)Items.leather_boots;
/*      */         }
/* 1017 */         if (itemTier == 1)
/*      */         {
/* 1019 */           return (Item)Items.golden_boots;
/*      */         }
/* 1021 */         if (itemTier == 2)
/*      */         {
/* 1023 */           return (Item)Items.chainmail_boots;
/*      */         }
/* 1025 */         if (itemTier == 3)
/*      */         {
/* 1027 */           return (Item)Items.iron_boots;
/*      */         }
/* 1029 */         if (itemTier == 4)
/*      */         {
/* 1031 */           return (Item)Items.diamond_boots;
/*      */         }
/*      */         break;
/*      */     } 
/* 1035 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 1044 */     float f = difficulty.getClampedAdditionalDifficulty();
/*      */     
/* 1046 */     if (getHeldItem() != null && this.rand.nextFloat() < 0.25F * f)
/*      */     {
/* 1048 */       EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItem(), (int)(5.0F + f * this.rand.nextInt(18)));
/*      */     }
/*      */     
/* 1051 */     for (int i = 0; i < 4; i++) {
/*      */       
/* 1053 */       ItemStack itemstack = getCurrentArmor(i);
/*      */       
/* 1055 */       if (itemstack != null && this.rand.nextFloat() < 0.5F * f)
/*      */       {
/* 1057 */         EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * this.rand.nextInt(18)));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 1068 */     getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
/* 1069 */     return livingdata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeSteered() {
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enablePersistence() {
/* 1086 */     this.persistenceRequired = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEquipmentDropChance(int slotIn, float chance) {
/* 1091 */     this.equipmentDropChances[slotIn] = chance;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPickUpLoot() {
/* 1096 */     return this.canPickUpLoot;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanPickUpLoot(boolean canPickup) {
/* 1101 */     this.canPickUpLoot = canPickup;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNoDespawnRequired() {
/* 1106 */     return this.persistenceRequired;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean interactFirst(EntityPlayer playerIn) {
/* 1114 */     if (getLeashed() && getLeashedToEntity() == playerIn) {
/*      */       
/* 1116 */       clearLeashed(true, !playerIn.capabilities.isCreativeMode);
/* 1117 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1121 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*      */     
/* 1123 */     if (itemstack != null && itemstack.getItem() == Items.lead && allowLeashing()) {
/*      */       
/* 1125 */       if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
/*      */         
/* 1127 */         setLeashedToEntity((Entity)playerIn, true);
/* 1128 */         itemstack.stackSize--;
/* 1129 */         return true;
/*      */       } 
/*      */       
/* 1132 */       if (((EntityTameable)this).isOwner((EntityLivingBase)playerIn)) {
/*      */         
/* 1134 */         setLeashedToEntity((Entity)playerIn, true);
/* 1135 */         itemstack.stackSize--;
/* 1136 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1140 */     if (interact(playerIn))
/*      */     {
/* 1142 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1146 */     return super.interactFirst(playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean interact(EntityPlayer player) {
/* 1156 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLeashedState() {
/* 1164 */     if (this.leashNBTTag != null)
/*      */     {
/* 1166 */       recreateLeash();
/*      */     }
/*      */     
/* 1169 */     if (this.isLeashed) {
/*      */       
/* 1171 */       if (!isEntityAlive())
/*      */       {
/* 1173 */         clearLeashed(true, true);
/*      */       }
/*      */       
/* 1176 */       if (this.leashedToEntity == null || this.leashedToEntity.isDead)
/*      */       {
/* 1178 */         clearLeashed(true, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearLeashed(boolean sendPacket, boolean dropLead) {
/* 1191 */     if (this.isLeashed) {
/*      */       
/* 1193 */       this.isLeashed = false;
/* 1194 */       this.leashedToEntity = null;
/*      */       
/* 1196 */       if (!this.worldObj.isRemote && dropLead)
/*      */       {
/* 1198 */         dropItem(Items.lead, 1);
/*      */       }
/*      */       
/* 1201 */       if (!this.worldObj.isRemote && sendPacket && this.worldObj instanceof WorldServer)
/*      */       {
/* 1203 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S1BPacketEntityAttach(1, this, null));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean allowLeashing() {
/* 1210 */     return (!getLeashed() && !(this instanceof net.minecraft.entity.monster.IMob));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getLeashed() {
/* 1215 */     return this.isLeashed;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getLeashedToEntity() {
/* 1220 */     return this.leashedToEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification) {
/* 1228 */     this.isLeashed = true;
/* 1229 */     this.leashedToEntity = entityIn;
/*      */     
/* 1231 */     if (!this.worldObj.isRemote && sendAttachNotification && this.worldObj instanceof WorldServer)
/*      */     {
/* 1233 */       ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S1BPacketEntityAttach(1, this, this.leashedToEntity));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void recreateLeash() {
/* 1239 */     if (this.isLeashed && this.leashNBTTag != null)
/*      */     {
/* 1241 */       if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
/*      */         
/* 1243 */         UUID uuid = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));
/*      */         
/* 1245 */         for (EntityLivingBase entitylivingbase : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D))) {
/*      */           
/* 1247 */           if (entitylivingbase.getUniqueID().equals(uuid)) {
/*      */             
/* 1249 */             this.leashedToEntity = entitylivingbase;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1254 */       } else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
/*      */         
/* 1256 */         BlockPos blockpos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
/* 1257 */         EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(this.worldObj, blockpos);
/*      */         
/* 1259 */         if (entityleashknot == null)
/*      */         {
/* 1261 */           entityleashknot = EntityLeashKnot.createKnot(this.worldObj, blockpos);
/*      */         }
/*      */         
/* 1264 */         this.leashedToEntity = entityleashknot;
/*      */       }
/*      */       else {
/*      */         
/* 1268 */         clearLeashed(false, true);
/*      */       } 
/*      */     }
/*      */     
/* 1272 */     this.leashNBTTag = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*      */     int i;
/* 1279 */     if (inventorySlot == 99) {
/*      */       
/* 1281 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 1285 */       i = inventorySlot - 100 + 1;
/*      */       
/* 1287 */       if (i < 0 || i >= this.equipment.length)
/*      */       {
/* 1289 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1293 */     if (itemStackIn == null || getArmorPosition(itemStackIn) == i || (i == 4 && itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock)) {
/*      */       
/* 1295 */       setCurrentItemOrArmor(i, itemStackIn);
/* 1296 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1300 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1309 */     return (super.isServerWorld() && !isAIDisabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoAI(boolean disable) {
/* 1317 */     this.dataWatcher.updateObject(15, Byte.valueOf((byte)(disable ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAIDisabled() {
/* 1325 */     return (this.dataWatcher.getWatchableObjectByte(15) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSkipUpdate() {
/* 1330 */     if (isChild())
/*      */     {
/* 1332 */       return false;
/*      */     }
/* 1334 */     if (this.hurtTime > 0)
/*      */     {
/* 1336 */       return false;
/*      */     }
/* 1338 */     if (this.ticksExisted < 20)
/*      */     {
/* 1340 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1344 */     World world = getEntityWorld();
/*      */     
/* 1346 */     if (world == null)
/*      */     {
/* 1348 */       return false;
/*      */     }
/* 1350 */     if (world.playerEntities.size() != 1)
/*      */     {
/* 1352 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1356 */     Entity entity = world.playerEntities.get(0);
/* 1357 */     double d0 = Math.max(Math.abs(this.posX - entity.posX) - 16.0D, 0.0D);
/* 1358 */     double d1 = Math.max(Math.abs(this.posZ - entity.posZ) - 16.0D, 0.0D);
/* 1359 */     double d2 = d0 * d0 + d1 * d1;
/* 1360 */     return !isInRangeToRenderDist(d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdateMinimal() {
/* 1367 */     this.entityAge++;
/*      */     
/* 1369 */     if (this instanceof net.minecraft.entity.monster.EntityMob) {
/*      */       
/* 1371 */       float f = getBrightness(1.0F);
/*      */       
/* 1373 */       if (f > 0.5F)
/*      */       {
/* 1375 */         this.entityAge += 2;
/*      */       }
/*      */     } 
/*      */     
/* 1379 */     despawnEntity();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 1384 */     UUID uuid = getUniqueID();
/*      */     
/* 1386 */     if (this.teamUuid != uuid) {
/*      */       
/* 1388 */       this.teamUuid = uuid;
/* 1389 */       this.teamUuidString = uuid.toString();
/*      */     } 
/*      */     
/* 1392 */     return (Team)this.worldObj.getScoreboard().getPlayersTeam(this.teamUuidString);
/*      */   }
/*      */   
/*      */   public enum SpawnPlacementType
/*      */   {
/* 1397 */     ON_GROUND,
/* 1398 */     IN_AIR,
/* 1399 */     IN_WATER;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\EntityLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */