/*      */ package net.minecraft.entity.player;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.features.modules.impl.ghost.KeepSprint;
/*      */ import com.google.common.base.Charsets;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.BlockDirectional;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureAttribute;
/*      */ import net.minecraft.entity.IEntityMultiPart;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.boss.EntityDragonPart;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityMob;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.passive.EntityPig;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.event.ClickEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerPlayer;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryEnderChest;
/*      */ import net.minecraft.item.EnumAction;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.LockCode;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class EntityPlayer
/*      */   extends EntityLivingBase
/*      */ {
/*   84 */   public InventoryPlayer inventory = new InventoryPlayer(this);
/*   85 */   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
/*      */ 
/*      */ 
/*      */   
/*      */   public Container inventoryContainer;
/*      */ 
/*      */ 
/*      */   
/*      */   public Container openContainer;
/*      */ 
/*      */   
/*   96 */   protected FoodStats foodStats = new FoodStats();
/*      */ 
/*      */   
/*      */   protected int flyToggleTimer;
/*      */ 
/*      */   
/*      */   public float prevCameraYaw;
/*      */ 
/*      */   
/*      */   public float cameraYaw;
/*      */   
/*      */   public int xpCooldown;
/*      */   
/*      */   public double prevChasingPosX;
/*      */   
/*      */   public double prevChasingPosY;
/*      */   
/*      */   public double prevChasingPosZ;
/*      */   
/*      */   public double chasingPosX;
/*      */   
/*      */   public double chasingPosY;
/*      */   
/*      */   public double chasingPosZ;
/*      */   
/*      */   protected boolean sleeping;
/*      */   
/*      */   public BlockPos playerLocation;
/*      */   
/*      */   private int sleepTimer;
/*      */   
/*      */   public float renderOffsetX;
/*      */   
/*      */   public float renderOffsetY;
/*      */   
/*      */   public float renderOffsetZ;
/*      */   
/*      */   private BlockPos spawnChunk;
/*      */   
/*      */   private boolean spawnForced;
/*      */   
/*      */   private BlockPos startMinecartRidingCoordinate;
/*      */   
/*  139 */   public PlayerCapabilities capabilities = new PlayerCapabilities();
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceLevel;
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceTotal;
/*      */ 
/*      */ 
/*      */   
/*      */   public float experience;
/*      */ 
/*      */ 
/*      */   
/*      */   private int xpSeed;
/*      */ 
/*      */ 
/*      */   
/*      */   private ItemStack itemInUse;
/*      */ 
/*      */   
/*      */   private int itemInUseCount;
/*      */ 
/*      */   
/*  165 */   protected float speedOnGround = 0.1F;
/*  166 */   protected float speedInAir = 0.02F;
/*      */ 
/*      */   
/*      */   private int lastXPSound;
/*      */ 
/*      */   
/*      */   private final GameProfile gameProfile;
/*      */   
/*      */   private boolean hasReducedDebug = false;
/*      */   
/*      */   public EntityFishHook fishEntity;
/*      */ 
/*      */   
/*      */   public EntityPlayer(World worldIn, GameProfile gameProfileIn) {
/*  180 */     super(worldIn);
/*  181 */     this.entityUniqueID = getUUID(gameProfileIn);
/*  182 */     this.gameProfile = gameProfileIn;
/*  183 */     this.inventoryContainer = (Container)new ContainerPlayer(this.inventory, !worldIn.isRemote, this);
/*  184 */     this.openContainer = this.inventoryContainer;
/*  185 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*  186 */     setLocationAndAngles(blockpos.getX() + 0.5D, (blockpos.getY() + 1), blockpos.getZ() + 0.5D, 0.0F, 0.0F);
/*  187 */     this.field_70741_aB = 180.0F;
/*  188 */     this.fireResistance = 20;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  193 */     super.applyEntityAttributes();
/*  194 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*  195 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  200 */     super.entityInit();
/*  201 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  202 */     this.dataWatcher.addObject(17, Float.valueOf(0.0F));
/*  203 */     this.dataWatcher.addObject(18, Integer.valueOf(0));
/*  204 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getItemInUse() {
/*  212 */     return this.itemInUse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemInUseCount() {
/*  220 */     return this.itemInUseCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingItem() {
/*  228 */     return (this.itemInUse != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemInUseDuration() {
/*  236 */     return isUsingItem() ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopUsingItem() {
/*  241 */     if (this.itemInUse != null)
/*      */     {
/*  243 */       this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
/*      */     }
/*      */     
/*  246 */     clearItemInUse();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearItemInUse() {
/*  251 */     this.itemInUse = null;
/*  252 */     this.itemInUseCount = 0;
/*      */     
/*  254 */     if (!this.worldObj.isRemote)
/*      */     {
/*  256 */       setEating(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlocking() {
/*  262 */     return (isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  270 */     this.noClip = isSpectator();
/*      */     
/*  272 */     if (isSpectator())
/*      */     {
/*  274 */       this.onGround = false;
/*      */     }
/*      */     
/*  277 */     if (this.itemInUse != null) {
/*      */       
/*  279 */       ItemStack itemstack = this.inventory.getCurrentItem();
/*      */       
/*  281 */       if (itemstack == this.itemInUse) {
/*      */         
/*  283 */         if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0)
/*      */         {
/*  285 */           updateItemUse(itemstack, 5);
/*      */         }
/*      */         
/*  288 */         if (--this.itemInUseCount == 0 && !this.worldObj.isRemote)
/*      */         {
/*  290 */           onItemUseFinish();
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  295 */         clearItemInUse();
/*      */       } 
/*      */     } 
/*      */     
/*  299 */     if (this.xpCooldown > 0)
/*      */     {
/*  301 */       this.xpCooldown--;
/*      */     }
/*      */     
/*  304 */     if (isPlayerSleeping()) {
/*      */       
/*  306 */       this.sleepTimer++;
/*      */       
/*  308 */       if (this.sleepTimer > 100)
/*      */       {
/*  310 */         this.sleepTimer = 100;
/*      */       }
/*      */       
/*  313 */       if (!this.worldObj.isRemote)
/*      */       {
/*  315 */         if (!isInBed())
/*      */         {
/*  317 */           wakeUpPlayer(true, true, false);
/*      */         }
/*  319 */         else if (this.worldObj.isDaytime())
/*      */         {
/*  321 */           wakeUpPlayer(false, true, true);
/*      */         }
/*      */       
/*      */       }
/*  325 */     } else if (this.sleepTimer > 0) {
/*      */       
/*  327 */       this.sleepTimer++;
/*      */       
/*  329 */       if (this.sleepTimer >= 110)
/*      */       {
/*  331 */         this.sleepTimer = 0;
/*      */       }
/*      */     } 
/*      */     
/*  335 */     super.onUpdate();
/*      */     
/*  337 */     if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  339 */       closeScreen();
/*  340 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  343 */     if (isBurning() && this.capabilities.disableDamage)
/*      */     {
/*  345 */       extinguish();
/*      */     }
/*      */     
/*  348 */     this.prevChasingPosX = this.chasingPosX;
/*  349 */     this.prevChasingPosY = this.chasingPosY;
/*  350 */     this.prevChasingPosZ = this.chasingPosZ;
/*  351 */     double d5 = this.posX - this.chasingPosX;
/*  352 */     double d0 = this.posY - this.chasingPosY;
/*  353 */     double d1 = this.posZ - this.chasingPosZ;
/*  354 */     double d2 = 10.0D;
/*      */     
/*  356 */     if (d5 > d2)
/*      */     {
/*  358 */       this.prevChasingPosX = this.chasingPosX = this.posX;
/*      */     }
/*      */     
/*  361 */     if (d1 > d2)
/*      */     {
/*  363 */       this.prevChasingPosZ = this.chasingPosZ = this.posZ;
/*      */     }
/*      */     
/*  366 */     if (d0 > d2)
/*      */     {
/*  368 */       this.prevChasingPosY = this.chasingPosY = this.posY;
/*      */     }
/*      */     
/*  371 */     if (d5 < -d2)
/*      */     {
/*  373 */       this.prevChasingPosX = this.chasingPosX = this.posX;
/*      */     }
/*      */     
/*  376 */     if (d1 < -d2)
/*      */     {
/*  378 */       this.prevChasingPosZ = this.chasingPosZ = this.posZ;
/*      */     }
/*      */     
/*  381 */     if (d0 < -d2)
/*      */     {
/*  383 */       this.prevChasingPosY = this.chasingPosY = this.posY;
/*      */     }
/*      */     
/*  386 */     this.chasingPosX += d5 * 0.25D;
/*  387 */     this.chasingPosZ += d1 * 0.25D;
/*  388 */     this.chasingPosY += d0 * 0.25D;
/*      */     
/*  390 */     if (this.ridingEntity == null)
/*      */     {
/*  392 */       this.startMinecartRidingCoordinate = null;
/*      */     }
/*      */     
/*  395 */     if (!this.worldObj.isRemote) {
/*      */       
/*  397 */       this.foodStats.onUpdate(this);
/*  398 */       triggerAchievement(StatList.minutesPlayedStat);
/*      */       
/*  400 */       if (isEntityAlive())
/*      */       {
/*  402 */         triggerAchievement(StatList.timeSinceDeathStat);
/*      */       }
/*      */     } 
/*      */     
/*  406 */     int i = 29999999;
/*  407 */     double d3 = MathHelper.clamp_double(this.posX, -2.9999999E7D, 2.9999999E7D);
/*  408 */     double d4 = MathHelper.clamp_double(this.posZ, -2.9999999E7D, 2.9999999E7D);
/*      */     
/*  410 */     if (d3 != this.posX || d4 != this.posZ)
/*      */     {
/*  412 */       setPosition(d3, this.posY, d4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  421 */     return this.capabilities.disableDamage ? 0 : 80;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSwimSound() {
/*  426 */     return "game.player.swim";
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSplashSound() {
/*  431 */     return "game.player.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/*  439 */     return 10;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/*  444 */     this.worldObj.playSoundToNearExcept(this, name, volume, pitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateItemUse(ItemStack itemStackIn, int p_71010_2_) {
/*  452 */     if (itemStackIn.getItemUseAction() == EnumAction.DRINK)
/*      */     {
/*  454 */       playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */     
/*  457 */     if (itemStackIn.getItemUseAction() == EnumAction.EAT) {
/*      */       
/*  459 */       for (int i = 0; i < p_71010_2_; i++) {
/*      */         
/*  461 */         Vec3 vec3 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  462 */         vec3 = vec3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  463 */         vec3 = vec3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  464 */         double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/*  465 */         Vec3 vec31 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*  466 */         vec31 = vec31.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  467 */         vec31 = vec31.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  468 */         vec31 = vec31.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */         
/*  470 */         if (itemStackIn.getHasSubtypes()) {
/*      */           
/*  472 */           this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(itemStackIn.getItem()), itemStackIn.getMetadata() });
/*      */         }
/*      */         else {
/*      */           
/*  476 */           this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(itemStackIn.getItem()) });
/*      */         } 
/*      */       } 
/*      */       
/*  480 */       playSound("random.eat", 0.5F + 0.5F * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/*  489 */     if (this.itemInUse != null) {
/*      */       
/*  491 */       updateItemUse(this.itemInUse, 16);
/*  492 */       int i = this.itemInUse.stackSize;
/*  493 */       ItemStack itemstack = this.itemInUse.onItemUseFinish(this.worldObj, this);
/*      */       
/*  495 */       if (itemstack != this.itemInUse || (itemstack != null && itemstack.stackSize != i)) {
/*      */         
/*  497 */         this.inventory.mainInventory[this.inventory.currentItem] = itemstack;
/*      */         
/*  499 */         if (itemstack.stackSize == 0)
/*      */         {
/*  501 */           this.inventory.mainInventory[this.inventory.currentItem] = null;
/*      */         }
/*      */       } 
/*      */       
/*  505 */       clearItemInUse();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleHealthUpdate(byte id) {
/*  511 */     if (id == 9) {
/*      */       
/*  513 */       onItemUseFinish();
/*      */     }
/*  515 */     else if (id == 23) {
/*      */       
/*  517 */       this.hasReducedDebug = false;
/*      */     }
/*  519 */     else if (id == 22) {
/*      */       
/*  521 */       this.hasReducedDebug = true;
/*      */     }
/*      */     else {
/*      */       
/*  525 */       super.handleHealthUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/*  534 */     return (getHealth() <= 0.0F || isPlayerSleeping());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeScreen() {
/*  542 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/*  550 */     if (!this.worldObj.isRemote && isSneaking()) {
/*      */       
/*  552 */       mountEntity(null);
/*  553 */       setSneaking(false);
/*      */     }
/*      */     else {
/*      */       
/*  557 */       double d0 = this.posX;
/*  558 */       double d1 = this.posY;
/*  559 */       double d2 = this.posZ;
/*  560 */       float f = this.rotationYaw;
/*  561 */       float f1 = this.rotationPitch;
/*  562 */       super.updateRidden();
/*  563 */       this.prevCameraYaw = this.cameraYaw;
/*  564 */       this.cameraYaw = 0.0F;
/*  565 */       addMountedMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */       
/*  567 */       if (this.ridingEntity instanceof EntityPig) {
/*      */         
/*  569 */         this.rotationPitch = f1;
/*  570 */         this.rotationYaw = f;
/*  571 */         this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preparePlayerToSpawn() {
/*  582 */     setSize(0.6F, 1.8F);
/*  583 */     super.preparePlayerToSpawn();
/*  584 */     setHealth(getMaxHealth());
/*  585 */     this.deathTime = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {
/*  590 */     super.updateEntityActionState();
/*  591 */     updateArmSwingProgress();
/*  592 */     this.rotationYawHead = this.rotationYaw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  601 */     if (this.flyToggleTimer > 0)
/*      */     {
/*  603 */       this.flyToggleTimer--;
/*      */     }
/*      */     
/*  606 */     if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
/*      */       
/*  608 */       if (getHealth() < getMaxHealth() && this.ticksExisted % 20 == 0)
/*      */       {
/*  610 */         heal(1.0F);
/*      */       }
/*      */       
/*  613 */       if (this.foodStats.needFood() && this.ticksExisted % 10 == 0)
/*      */       {
/*  615 */         this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
/*      */       }
/*      */     } 
/*      */     
/*  619 */     this.inventory.decrementAnimations();
/*  620 */     this.prevCameraYaw = this.cameraYaw;
/*  621 */     super.onLivingUpdate();
/*  622 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*      */     
/*  624 */     if (!this.worldObj.isRemote)
/*      */     {
/*  626 */       iattributeinstance.setBaseValue(this.capabilities.getWalkSpeed());
/*      */     }
/*      */     
/*  629 */     this.jumpMovementFactor = this.speedInAir;
/*      */     
/*  631 */     if (isSprinting())
/*      */     {
/*  633 */       this.jumpMovementFactor = (float)(this.jumpMovementFactor + this.speedInAir * 0.3D);
/*      */     }
/*      */     
/*  636 */     setAIMoveSpeed((float)iattributeinstance.getAttributeValue());
/*  637 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  638 */     float f1 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);
/*      */     
/*  640 */     if (f > 0.1F)
/*      */     {
/*  642 */       f = 0.1F;
/*      */     }
/*      */     
/*  645 */     if (!this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  647 */       f = 0.0F;
/*      */     }
/*      */     
/*  650 */     if (this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  652 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  655 */     this.cameraYaw += (f - this.cameraYaw) * 0.4F;
/*  656 */     this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;
/*      */     
/*  658 */     if (getHealth() > 0.0F && !isSpectator()) {
/*      */       
/*  660 */       AxisAlignedBB axisalignedbb = null;
/*      */       
/*  662 */       if (this.ridingEntity != null && !this.ridingEntity.isDead) {
/*      */         
/*  664 */         axisalignedbb = getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
/*      */       }
/*      */       else {
/*      */         
/*  668 */         axisalignedbb = getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
/*      */       } 
/*      */       
/*  671 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, axisalignedbb);
/*      */       
/*  673 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  675 */         Entity entity = list.get(i);
/*      */         
/*  677 */         if (!entity.isDead)
/*      */         {
/*  679 */           collideWithPlayer(entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void collideWithPlayer(Entity p_71044_1_) {
/*  687 */     p_71044_1_.onCollideWithPlayer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getScore() {
/*  692 */     return this.dataWatcher.getWatchableObjectInt(18);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScore(int p_85040_1_) {
/*  700 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_85040_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScore(int p_85039_1_) {
/*  708 */     int i = getScore();
/*  709 */     this.dataWatcher.updateObject(18, Integer.valueOf(i + p_85039_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  717 */     super.onDeath(cause);
/*  718 */     setSize(0.2F, 0.2F);
/*  719 */     setPosition(this.posX, this.posY, this.posZ);
/*  720 */     this.motionY = 0.10000000149011612D;
/*      */     
/*  722 */     if (getCommandSenderName().equals("Notch"))
/*      */     {
/*  724 */       dropItem(new ItemStack(Items.apple, 1), true, false);
/*      */     }
/*      */     
/*  727 */     if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
/*      */     {
/*  729 */       this.inventory.dropAllItems();
/*      */     }
/*      */     
/*  732 */     if (cause != null) {
/*      */       
/*  734 */       this.motionX = (-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
/*  735 */       this.motionZ = (-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
/*      */     }
/*      */     else {
/*      */       
/*  739 */       this.motionX = this.motionZ = 0.0D;
/*      */     } 
/*      */     
/*  742 */     triggerAchievement(StatList.deathsStat);
/*  743 */     func_175145_a(StatList.timeSinceDeathStat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  751 */     return "game.player.hurt";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  759 */     return "game.player.die";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {
/*  768 */     addScore(amount);
/*  769 */     Collection<ScoreObjective> collection = getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
/*      */     
/*  771 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/*  773 */       triggerAchievement(StatList.playerKillsStat);
/*  774 */       collection.addAll(getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
/*  775 */       collection.addAll(func_175137_e(entityIn));
/*      */     }
/*      */     else {
/*      */       
/*  779 */       triggerAchievement(StatList.mobKillsStat);
/*      */     } 
/*      */     
/*  782 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/*  784 */       Score score = getWorldScoreboard().getValueFromObjective(getCommandSenderName(), scoreobjective);
/*  785 */       score.func_96648_a();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<ScoreObjective> func_175137_e(Entity p_175137_1_) {
/*  791 */     ScorePlayerTeam scoreplayerteam = getWorldScoreboard().getPlayersTeam(getCommandSenderName());
/*      */     
/*  793 */     if (scoreplayerteam != null) {
/*      */       
/*  795 */       int i = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  797 */       if (i >= 0 && i < IScoreObjectiveCriteria.field_178793_i.length)
/*      */       {
/*  799 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[i])) {
/*      */           
/*  801 */           Score score = getWorldScoreboard().getValueFromObjective(p_175137_1_.getCommandSenderName(), scoreobjective);
/*  802 */           score.func_96648_a();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  807 */     ScorePlayerTeam scoreplayerteam1 = getWorldScoreboard().getPlayersTeam(p_175137_1_.getCommandSenderName());
/*      */     
/*  809 */     if (scoreplayerteam1 != null) {
/*      */       
/*  811 */       int j = scoreplayerteam1.getChatFormat().getColorIndex();
/*      */       
/*  813 */       if (j >= 0 && j < IScoreObjectiveCriteria.field_178792_h.length)
/*      */       {
/*  815 */         return getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[j]);
/*      */       }
/*      */     } 
/*      */     
/*  819 */     return Lists.newArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem dropOneItem(boolean dropAll) {
/*  827 */     return dropItem(this.inventory.decrStackSize(this.inventory.currentItem, (dropAll && this.inventory.getCurrentItem() != null) ? (this.inventory.getCurrentItem()).stackSize : 1), false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem dropPlayerItemWithRandomChoice(ItemStack itemStackIn, boolean unused) {
/*  835 */     return dropItem(itemStackIn, false, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
/*  840 */     if (droppedItem == null)
/*      */     {
/*  842 */       return null;
/*      */     }
/*  844 */     if (droppedItem.stackSize == 0)
/*      */     {
/*  846 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  850 */     double d0 = this.posY - 0.30000001192092896D + getEyeHeight();
/*  851 */     EntityItem entityitem = new EntityItem(this.worldObj, this.posX, d0, this.posZ, droppedItem);
/*  852 */     entityitem.setPickupDelay(40);
/*      */     
/*  854 */     if (traceItem)
/*      */     {
/*  856 */       entityitem.setThrower(getCommandSenderName());
/*      */     }
/*      */     
/*  859 */     if (dropAround) {
/*      */       
/*  861 */       float f = this.rand.nextFloat() * 0.5F;
/*  862 */       float f1 = this.rand.nextFloat() * 3.1415927F * 2.0F;
/*  863 */       entityitem.motionX = (-MathHelper.sin(f1) * f);
/*  864 */       entityitem.motionZ = (MathHelper.cos(f1) * f);
/*  865 */       entityitem.motionY = 0.20000000298023224D;
/*      */     }
/*      */     else {
/*      */       
/*  869 */       float f2 = 0.3F;
/*  870 */       entityitem.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f2);
/*  871 */       entityitem.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f2);
/*  872 */       entityitem.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f2 + 0.1F);
/*  873 */       float f3 = this.rand.nextFloat() * 3.1415927F * 2.0F;
/*  874 */       f2 = 0.02F * this.rand.nextFloat();
/*  875 */       entityitem.motionX += Math.cos(f3) * f2;
/*  876 */       entityitem.motionY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*  877 */       entityitem.motionZ += Math.sin(f3) * f2;
/*      */     } 
/*      */     
/*  880 */     joinEntityItemWithWorld(entityitem);
/*      */     
/*  882 */     if (traceItem)
/*      */     {
/*  884 */       triggerAchievement(StatList.dropStat);
/*      */     }
/*      */     
/*  887 */     return entityitem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void joinEntityItemWithWorld(EntityItem itemIn) {
/*  896 */     this.worldObj.spawnEntityInWorld((Entity)itemIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getToolDigEfficiency(Block p_180471_1_) {
/*  904 */     float f = this.inventory.getStrVsBlock(p_180471_1_);
/*      */     
/*  906 */     if (f > 1.0F) {
/*      */       
/*  908 */       int i = EnchantmentHelper.getEfficiencyModifier(this);
/*  909 */       ItemStack itemstack = this.inventory.getCurrentItem();
/*      */       
/*  911 */       if (i > 0 && itemstack != null)
/*      */       {
/*  913 */         f += (i * i + 1);
/*      */       }
/*      */     } 
/*      */     
/*  917 */     if (isPotionActive(Potion.digSpeed))
/*      */     {
/*  919 */       f *= 1.0F + (getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
/*      */     }
/*      */     
/*  922 */     if (isPotionActive(Potion.digSlowdown)) {
/*      */       
/*  924 */       float f1 = 1.0F;
/*      */       
/*  926 */       switch (getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
/*      */         
/*      */         case 0:
/*  929 */           f1 = 0.3F;
/*      */           break;
/*      */         
/*      */         case 1:
/*  933 */           f1 = 0.09F;
/*      */           break;
/*      */         
/*      */         case 2:
/*  937 */           f1 = 0.0027F;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  942 */           f1 = 8.1E-4F;
/*      */           break;
/*      */       } 
/*  945 */       f *= f1;
/*      */     } 
/*      */     
/*  948 */     if (isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))
/*      */     {
/*  950 */       f /= 5.0F;
/*      */     }
/*      */     
/*  953 */     if (!this.onGround)
/*      */     {
/*  955 */       f /= 5.0F;
/*      */     }
/*      */     
/*  958 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockToHarvest) {
/*  966 */     return this.inventory.canHeldItemHarvest(blockToHarvest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  974 */     super.readEntityFromNBT(tagCompund);
/*  975 */     this.entityUniqueID = getUUID(this.gameProfile);
/*  976 */     NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);
/*  977 */     this.inventory.readFromNBT(nbttaglist);
/*  978 */     this.inventory.currentItem = tagCompund.getInteger("SelectedItemSlot");
/*  979 */     this.sleeping = tagCompund.getBoolean("Sleeping");
/*  980 */     this.sleepTimer = tagCompund.getShort("SleepTimer");
/*  981 */     this.experience = tagCompund.getFloat("XpP");
/*  982 */     this.experienceLevel = tagCompund.getInteger("XpLevel");
/*  983 */     this.experienceTotal = tagCompund.getInteger("XpTotal");
/*  984 */     this.xpSeed = tagCompund.getInteger("XpSeed");
/*      */     
/*  986 */     if (this.xpSeed == 0)
/*      */     {
/*  988 */       this.xpSeed = this.rand.nextInt();
/*      */     }
/*      */     
/*  991 */     setScore(tagCompund.getInteger("Score"));
/*      */     
/*  993 */     if (this.sleeping) {
/*      */       
/*  995 */       this.playerLocation = new BlockPos((Entity)this);
/*  996 */       wakeUpPlayer(true, true, false);
/*      */     } 
/*      */     
/*  999 */     if (tagCompund.hasKey("SpawnX", 99) && tagCompund.hasKey("SpawnY", 99) && tagCompund.hasKey("SpawnZ", 99)) {
/*      */       
/* 1001 */       this.spawnChunk = new BlockPos(tagCompund.getInteger("SpawnX"), tagCompund.getInteger("SpawnY"), tagCompund.getInteger("SpawnZ"));
/* 1002 */       this.spawnForced = tagCompund.getBoolean("SpawnForced");
/*      */     } 
/*      */     
/* 1005 */     this.foodStats.readNBT(tagCompund);
/* 1006 */     this.capabilities.readCapabilitiesFromNBT(tagCompund);
/*      */     
/* 1008 */     if (tagCompund.hasKey("EnderItems", 9)) {
/*      */       
/* 1010 */       NBTTagList nbttaglist1 = tagCompund.getTagList("EnderItems", 10);
/* 1011 */       this.theInventoryEnderChest.loadInventoryFromNBT(nbttaglist1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 1020 */     super.writeEntityToNBT(tagCompound);
/* 1021 */     tagCompound.setTag("Inventory", (NBTBase)this.inventory.writeToNBT(new NBTTagList()));
/* 1022 */     tagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
/* 1023 */     tagCompound.setBoolean("Sleeping", this.sleeping);
/* 1024 */     tagCompound.setShort("SleepTimer", (short)this.sleepTimer);
/* 1025 */     tagCompound.setFloat("XpP", this.experience);
/* 1026 */     tagCompound.setInteger("XpLevel", this.experienceLevel);
/* 1027 */     tagCompound.setInteger("XpTotal", this.experienceTotal);
/* 1028 */     tagCompound.setInteger("XpSeed", this.xpSeed);
/* 1029 */     tagCompound.setInteger("Score", getScore());
/*      */     
/* 1031 */     if (this.spawnChunk != null) {
/*      */       
/* 1033 */       tagCompound.setInteger("SpawnX", this.spawnChunk.getX());
/* 1034 */       tagCompound.setInteger("SpawnY", this.spawnChunk.getY());
/* 1035 */       tagCompound.setInteger("SpawnZ", this.spawnChunk.getZ());
/* 1036 */       tagCompound.setBoolean("SpawnForced", this.spawnForced);
/*      */     } 
/*      */     
/* 1039 */     this.foodStats.writeNBT(tagCompound);
/* 1040 */     this.capabilities.writeCapabilitiesToNBT(tagCompound);
/* 1041 */     tagCompound.setTag("EnderItems", (NBTBase)this.theInventoryEnderChest.saveInventoryToNBT());
/* 1042 */     ItemStack itemstack = this.inventory.getCurrentItem();
/*      */     
/* 1044 */     if (itemstack != null && itemstack.getItem() != null)
/*      */     {
/* 1046 */       tagCompound.setTag("SelectedItem", (NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1055 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1057 */       return false;
/*      */     }
/* 1059 */     if (this.capabilities.disableDamage && !source.canHarmInCreative())
/*      */     {
/* 1061 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1065 */     this.entityAge = 0;
/*      */     
/* 1067 */     if (getHealth() <= 0.0F)
/*      */     {
/* 1069 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1073 */     if (isPlayerSleeping() && !this.worldObj.isRemote)
/*      */     {
/* 1075 */       wakeUpPlayer(true, true, false);
/*      */     }
/*      */     
/* 1078 */     if (source.isDifficultyScaled()) {
/*      */       
/* 1080 */       if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
/*      */       {
/* 1082 */         amount = 0.0F;
/*      */       }
/*      */       
/* 1085 */       if (this.worldObj.getDifficulty() == EnumDifficulty.EASY)
/*      */       {
/* 1087 */         amount = amount / 2.0F + 1.0F;
/*      */       }
/*      */       
/* 1090 */       if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*      */       {
/* 1092 */         amount = amount * 3.0F / 2.0F;
/*      */       }
/*      */     } 
/*      */     
/* 1096 */     if (amount == 0.0F)
/*      */     {
/* 1098 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1102 */     Entity entity = source.getEntity();
/*      */     
/* 1104 */     if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null)
/*      */     {
/* 1106 */       entity = ((EntityArrow)entity).shootingEntity;
/*      */     }
/*      */     
/* 1109 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/* 1117 */     Team team = getTeam();
/* 1118 */     Team team1 = other.getTeam();
/* 1119 */     return (team == null) ? true : (!team.isSameTeam(team1) ? true : team.getAllowFriendlyFire());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void damageArmor(float p_70675_1_) {
/* 1124 */     this.inventory.damageArmor(p_70675_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1132 */     return this.inventory.getTotalArmorValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getArmorVisibility() {
/* 1141 */     int i = 0;
/*      */     
/* 1143 */     for (ItemStack itemstack : this.inventory.armorInventory) {
/*      */       
/* 1145 */       if (itemstack != null)
/*      */       {
/* 1147 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 1151 */     return i / this.inventory.armorInventory.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1160 */     if (!isEntityInvulnerable(damageSrc)) {
/*      */       
/* 1162 */       if (!damageSrc.isUnblockable() && isBlocking() && damageAmount > 0.0F)
/*      */       {
/* 1164 */         damageAmount = (1.0F + damageAmount) * 0.5F;
/*      */       }
/*      */       
/* 1167 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1168 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1169 */       float f = damageAmount;
/* 1170 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1171 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1173 */       if (damageAmount != 0.0F) {
/*      */         
/* 1175 */         addExhaustion(damageSrc.getHungerDamage());
/* 1176 */         float f1 = getHealth();
/* 1177 */         setHealth(getHealth() - damageAmount);
/* 1178 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/*      */         
/* 1180 */         if (damageAmount < 3.4028235E37F)
/*      */         {
/* 1182 */           addStat(StatList.damageTakenStat, Math.round(damageAmount * 10.0F));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactWith(Entity p_70998_1_) {
/* 1224 */     if (isSpectator()) {
/*      */       
/* 1226 */       if (p_70998_1_ instanceof IInventory)
/*      */       {
/* 1228 */         displayGUIChest((IInventory)p_70998_1_);
/*      */       }
/*      */       
/* 1231 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1235 */     ItemStack itemstack = getCurrentEquippedItem();
/* 1236 */     ItemStack itemstack1 = (itemstack != null) ? itemstack.copy() : null;
/*      */     
/* 1238 */     if (!p_70998_1_.interactFirst(this)) {
/*      */       
/* 1240 */       if (itemstack != null && p_70998_1_ instanceof EntityLivingBase) {
/*      */         
/* 1242 */         if (this.capabilities.isCreativeMode)
/*      */         {
/* 1244 */           itemstack = itemstack1;
/*      */         }
/*      */         
/* 1247 */         if (itemstack.interactWithEntity(this, (EntityLivingBase)p_70998_1_)) {
/*      */           
/* 1249 */           if (itemstack.stackSize <= 0 && !this.capabilities.isCreativeMode)
/*      */           {
/* 1251 */             destroyCurrentEquippedItem();
/*      */           }
/*      */           
/* 1254 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/* 1258 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1262 */     if (itemstack != null && itemstack == getCurrentEquippedItem())
/*      */     {
/* 1264 */       if (itemstack.stackSize <= 0 && !this.capabilities.isCreativeMode) {
/*      */         
/* 1266 */         destroyCurrentEquippedItem();
/*      */       }
/* 1268 */       else if (itemstack.stackSize < itemstack1.stackSize && this.capabilities.isCreativeMode) {
/*      */         
/* 1270 */         itemstack.stackSize = itemstack1.stackSize;
/*      */       } 
/*      */     }
/*      */     
/* 1274 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentEquippedItem() {
/* 1284 */     return this.inventory.getCurrentItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyCurrentEquippedItem() {
/* 1292 */     this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1300 */     return -0.35D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1309 */     if (targetEntity.canAttackWithItem())
/*      */     {
/* 1311 */       if (!targetEntity.hitByEntity((Entity)this)) {
/*      */         
/* 1313 */         float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/* 1314 */         int i = 0;
/* 1315 */         float f1 = 0.0F;
/*      */         
/* 1317 */         if (targetEntity instanceof EntityLivingBase) {
/*      */           
/* 1319 */           f1 = EnchantmentHelper.func_152377_a(getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
/*      */         }
/*      */         else {
/*      */           
/* 1323 */           f1 = EnchantmentHelper.func_152377_a(getHeldItem(), EnumCreatureAttribute.UNDEFINED);
/*      */         } 
/*      */         
/* 1326 */         i += EnchantmentHelper.getKnockbackModifier(this);
/*      */         
/* 1328 */         if (isSprinting())
/*      */         {
/* 1330 */           i++;
/*      */         }
/*      */         
/* 1333 */         if (f > 0.0F || f1 > 0.0F) {
/*      */           
/* 1335 */           boolean flag = (this.fallDistance > 0.0F && !this.onGround && !isOnLadder() && !isInWater() && !isPotionActive(Potion.blindness) && this.ridingEntity == null && targetEntity instanceof EntityLivingBase);
/*      */           
/* 1337 */           if (flag && f > 0.0F)
/*      */           {
/* 1339 */             f *= 1.5F;
/*      */           }
/*      */           
/* 1342 */           f += f1;
/* 1343 */           boolean flag1 = false;
/* 1344 */           int j = EnchantmentHelper.getFireAspectModifier(this);
/*      */           
/* 1346 */           if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning()) {
/*      */             
/* 1348 */             flag1 = true;
/* 1349 */             targetEntity.setFire(1);
/*      */           } 
/*      */           
/* 1352 */           double d0 = targetEntity.motionX;
/* 1353 */           double d1 = targetEntity.motionY;
/* 1354 */           double d2 = targetEntity.motionZ;
/* 1355 */           boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);
/*      */           
/* 1357 */           if (flag2) {
/*      */             EntityLivingBase entityLivingBase;
/* 1359 */             if (i > 0 && !((KeepSprint)Slack.getInstance().getModuleManager().getInstance(KeepSprint.class)).isToggle()) {
/*      */               
/* 1361 */               targetEntity.addVelocity((-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F));
/* 1362 */               this.motionX *= 0.6D;
/* 1363 */               this.motionZ *= 0.6D;
/* 1364 */               setSprinting(false);
/*      */             } 
/*      */             
/* 1367 */             if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
/*      */               
/* 1369 */               ((EntityPlayerMP)targetEntity).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(targetEntity));
/* 1370 */               targetEntity.velocityChanged = false;
/* 1371 */               targetEntity.motionX = d0;
/* 1372 */               targetEntity.motionY = d1;
/* 1373 */               targetEntity.motionZ = d2;
/*      */             } 
/*      */             
/* 1376 */             if (flag)
/*      */             {
/* 1378 */               onCriticalHit(targetEntity);
/*      */             }
/*      */             
/* 1381 */             if (f1 > 0.0F)
/*      */             {
/* 1383 */               onEnchantmentCritical(targetEntity);
/*      */             }
/*      */             
/* 1386 */             if (f >= 18.0F)
/*      */             {
/* 1388 */               triggerAchievement((StatBase)AchievementList.overkill);
/*      */             }
/*      */             
/* 1391 */             setLastAttacker(targetEntity);
/*      */             
/* 1393 */             if (targetEntity instanceof EntityLivingBase)
/*      */             {
/* 1395 */               EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)this);
/*      */             }
/*      */             
/* 1398 */             EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
/* 1399 */             ItemStack itemstack = getCurrentEquippedItem();
/* 1400 */             Entity entity = targetEntity;
/*      */             
/* 1402 */             if (targetEntity instanceof EntityDragonPart) {
/*      */               
/* 1404 */               IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).entityDragonObj;
/*      */               
/* 1406 */               if (ientitymultipart instanceof EntityLivingBase)
/*      */               {
/* 1408 */                 entityLivingBase = (EntityLivingBase)ientitymultipart;
/*      */               }
/*      */             } 
/*      */             
/* 1412 */             if (itemstack != null && entityLivingBase instanceof EntityLivingBase) {
/*      */               
/* 1414 */               itemstack.hitEntity(entityLivingBase, this);
/*      */               
/* 1416 */               if (itemstack.stackSize <= 0)
/*      */               {
/* 1418 */                 destroyCurrentEquippedItem();
/*      */               }
/*      */             } 
/*      */             
/* 1422 */             if (targetEntity instanceof EntityLivingBase) {
/*      */               
/* 1424 */               addStat(StatList.damageDealtStat, Math.round(f * 10.0F));
/*      */               
/* 1426 */               if (j > 0)
/*      */               {
/* 1428 */                 targetEntity.setFire(j * 4);
/*      */               }
/*      */             } 
/*      */             
/* 1432 */             addExhaustion(0.3F);
/*      */           }
/* 1434 */           else if (flag1) {
/*      */             
/* 1436 */             targetEntity.extinguish();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void respawnPlayer() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/* 1463 */     super.setDead();
/* 1464 */     this.inventoryContainer.onContainerClosed(this);
/*      */     
/* 1466 */     if (this.openContainer != null)
/*      */     {
/* 1468 */       this.openContainer.onContainerClosed(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1477 */     return (!this.sleeping && super.isEntityInsideOpaqueBlock());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUser() {
/* 1485 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1493 */     return this.gameProfile;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumStatus trySleep(BlockPos bedLocation) {
/* 1498 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1500 */       if (isPlayerSleeping() || !isEntityAlive())
/*      */       {
/* 1502 */         return EnumStatus.OTHER_PROBLEM;
/*      */       }
/*      */       
/* 1505 */       if (!this.worldObj.provider.isSurfaceWorld())
/*      */       {
/* 1507 */         return EnumStatus.NOT_POSSIBLE_HERE;
/*      */       }
/*      */       
/* 1510 */       if (this.worldObj.isDaytime())
/*      */       {
/* 1512 */         return EnumStatus.NOT_POSSIBLE_NOW;
/*      */       }
/*      */       
/* 1515 */       if (Math.abs(this.posX - bedLocation.getX()) > 3.0D || Math.abs(this.posY - bedLocation.getY()) > 2.0D || Math.abs(this.posZ - bedLocation.getZ()) > 3.0D)
/*      */       {
/* 1517 */         return EnumStatus.TOO_FAR_AWAY;
/*      */       }
/*      */       
/* 1520 */       double d0 = 8.0D;
/* 1521 */       double d1 = 5.0D;
/* 1522 */       List<EntityMob> list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(bedLocation.getX() - d0, bedLocation.getY() - d1, bedLocation.getZ() - d0, bedLocation.getX() + d0, bedLocation.getY() + d1, bedLocation.getZ() + d0));
/*      */       
/* 1524 */       if (!list.isEmpty())
/*      */       {
/* 1526 */         return EnumStatus.NOT_SAFE;
/*      */       }
/*      */     } 
/*      */     
/* 1530 */     if (isRiding())
/*      */     {
/* 1532 */       mountEntity(null);
/*      */     }
/*      */     
/* 1535 */     setSize(0.2F, 0.2F);
/*      */     
/* 1537 */     if (this.worldObj.isBlockLoaded(bedLocation)) {
/*      */       
/* 1539 */       EnumFacing enumfacing = (EnumFacing)this.worldObj.getBlockState(bedLocation).getValue((IProperty)BlockDirectional.FACING);
/* 1540 */       float f = 0.5F;
/* 1541 */       float f1 = 0.5F;
/*      */       
/* 1543 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1546 */           f1 = 0.9F;
/*      */           break;
/*      */         
/*      */         case NORTH:
/* 1550 */           f1 = 0.1F;
/*      */           break;
/*      */         
/*      */         case WEST:
/* 1554 */           f = 0.1F;
/*      */           break;
/*      */         
/*      */         case EAST:
/* 1558 */           f = 0.9F;
/*      */           break;
/*      */       } 
/* 1561 */       func_175139_a(enumfacing);
/* 1562 */       setPosition((bedLocation.getX() + f), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + f1));
/*      */     }
/*      */     else {
/*      */       
/* 1566 */       setPosition((bedLocation.getX() + 0.5F), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1569 */     this.sleeping = true;
/* 1570 */     this.sleepTimer = 0;
/* 1571 */     this.playerLocation = bedLocation;
/* 1572 */     this.motionX = this.motionZ = this.motionY = 0.0D;
/*      */     
/* 1574 */     if (!this.worldObj.isRemote)
/*      */     {
/* 1576 */       this.worldObj.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1579 */     return EnumStatus.OK;
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175139_a(EnumFacing p_175139_1_) {
/* 1584 */     this.renderOffsetX = 0.0F;
/* 1585 */     this.renderOffsetZ = 0.0F;
/*      */     
/* 1587 */     switch (p_175139_1_) {
/*      */       
/*      */       case SOUTH:
/* 1590 */         this.renderOffsetZ = -1.8F;
/*      */         break;
/*      */       
/*      */       case NORTH:
/* 1594 */         this.renderOffsetZ = 1.8F;
/*      */         break;
/*      */       
/*      */       case WEST:
/* 1598 */         this.renderOffsetX = 1.8F;
/*      */         break;
/*      */       
/*      */       case EAST:
/* 1602 */         this.renderOffsetX = -1.8F;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean p_70999_1_, boolean updateWorldFlag, boolean setSpawn) {
/* 1611 */     setSize(0.6F, 1.8F);
/* 1612 */     IBlockState iblockstate = this.worldObj.getBlockState(this.playerLocation);
/*      */     
/* 1614 */     if (this.playerLocation != null && iblockstate.getBlock() == Blocks.bed) {
/*      */       
/* 1616 */       this.worldObj.setBlockState(this.playerLocation, iblockstate.withProperty((IProperty)BlockBed.OCCUPIED, Boolean.FALSE), 4);
/* 1617 */       BlockPos blockpos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
/*      */       
/* 1619 */       if (blockpos == null)
/*      */       {
/* 1621 */         blockpos = this.playerLocation.up();
/*      */       }
/*      */       
/* 1624 */       setPosition((blockpos.getX() + 0.5F), (blockpos.getY() + 0.1F), (blockpos.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1627 */     this.sleeping = false;
/*      */     
/* 1629 */     if (!this.worldObj.isRemote && updateWorldFlag)
/*      */     {
/* 1631 */       this.worldObj.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1634 */     this.sleepTimer = p_70999_1_ ? 0 : 100;
/*      */     
/* 1636 */     if (setSpawn)
/*      */     {
/* 1638 */       setSpawnPoint(this.playerLocation, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isInBed() {
/* 1644 */     return (this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn) {
/* 1654 */     Block block = worldIn.getBlockState(bedLocation).getBlock();
/*      */     
/* 1656 */     if (block != Blocks.bed) {
/*      */       
/* 1658 */       if (!forceSpawn)
/*      */       {
/* 1660 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1664 */       boolean flag = block.func_181623_g();
/* 1665 */       boolean flag1 = worldIn.getBlockState(bedLocation.up()).getBlock().func_181623_g();
/* 1666 */       return (flag && flag1) ? bedLocation : null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1671 */     return BlockBed.getSafeExitLocation(worldIn, bedLocation, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBedOrientationInDegrees() {
/* 1680 */     if (this.playerLocation != null) {
/*      */       
/* 1682 */       EnumFacing enumfacing = (EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue((IProperty)BlockDirectional.FACING);
/*      */       
/* 1684 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1687 */           return 90.0F;
/*      */         
/*      */         case NORTH:
/* 1690 */           return 270.0F;
/*      */         
/*      */         case WEST:
/* 1693 */           return 0.0F;
/*      */         
/*      */         case EAST:
/* 1696 */           return 180.0F;
/*      */       } 
/*      */     
/*      */     } 
/* 1700 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1708 */     return this.sleeping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerFullyAsleep() {
/* 1716 */     return (this.sleeping && this.sleepTimer >= 100);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSleepTimer() {
/* 1721 */     return this.sleepTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {}
/*      */ 
/*      */   
/*      */   public BlockPos getBedLocation() {
/* 1730 */     return this.spawnChunk;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpawnForced() {
/* 1735 */     return this.spawnForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos, boolean forced) {
/* 1740 */     if (pos != null) {
/*      */       
/* 1742 */       this.spawnChunk = pos;
/* 1743 */       this.spawnForced = forced;
/*      */     }
/*      */     else {
/*      */       
/* 1747 */       this.spawnChunk = null;
/* 1748 */       this.spawnForced = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void triggerAchievement(StatBase achievementIn) {
/* 1757 */     addStat(achievementIn, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_175145_a(StatBase p_175145_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void jump() {
/* 1776 */     super.jump();
/* 1777 */     triggerAchievement(StatList.jumpStat);
/*      */     
/* 1779 */     if (isSprinting()) {
/*      */       
/* 1781 */       addExhaustion(0.8F);
/*      */     }
/*      */     else {
/*      */       
/* 1785 */       addExhaustion(0.2F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1794 */     double d0 = this.posX;
/* 1795 */     double d1 = this.posY;
/* 1796 */     double d2 = this.posZ;
/*      */     
/* 1798 */     if (this.capabilities.isFlying && this.ridingEntity == null) {
/*      */       
/* 1800 */       double d3 = this.motionY;
/* 1801 */       float f = this.jumpMovementFactor;
/* 1802 */       this.jumpMovementFactor = this.capabilities.getFlySpeed() * (isSprinting() ? 2 : true);
/* 1803 */       super.moveEntityWithHeading(strafe, forward);
/* 1804 */       this.motionY = d3 * 0.6D;
/* 1805 */       this.jumpMovementFactor = f;
/*      */     }
/*      */     else {
/*      */       
/* 1809 */       super.moveEntityWithHeading(strafe, forward);
/*      */     } 
/*      */     
/* 1812 */     addMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1820 */     return (float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
/* 1828 */     if (this.ridingEntity == null)
/*      */     {
/* 1830 */       if (isInsideOfMaterial(Material.water)) {
/*      */         
/* 1832 */         int i = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1834 */         if (i > 0)
/*      */         {
/* 1836 */           addStat(StatList.distanceDoveStat, i);
/* 1837 */           addExhaustion(0.015F * i * 0.01F);
/*      */         }
/*      */       
/* 1840 */       } else if (isInWater()) {
/*      */         
/* 1842 */         int j = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1844 */         if (j > 0)
/*      */         {
/* 1846 */           addStat(StatList.distanceSwumStat, j);
/* 1847 */           addExhaustion(0.015F * j * 0.01F);
/*      */         }
/*      */       
/* 1850 */       } else if (isOnLadder()) {
/*      */         
/* 1852 */         if (p_71000_3_ > 0.0D)
/*      */         {
/* 1854 */           addStat(StatList.distanceClimbedStat, (int)Math.round(p_71000_3_ * 100.0D));
/*      */         }
/*      */       }
/* 1857 */       else if (this.onGround) {
/*      */         
/* 1859 */         int k = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1861 */         if (k > 0) {
/*      */           
/* 1863 */           addStat(StatList.distanceWalkedStat, k);
/*      */           
/* 1865 */           if (isSprinting())
/*      */           {
/* 1867 */             addStat(StatList.distanceSprintedStat, k);
/* 1868 */             addExhaustion(0.099999994F * k * 0.01F);
/*      */           }
/*      */           else
/*      */           {
/* 1872 */             if (isSneaking())
/*      */             {
/* 1874 */               addStat(StatList.distanceCrouchedStat, k);
/*      */             }
/*      */             
/* 1877 */             addExhaustion(0.01F * k * 0.01F);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/* 1883 */         int l = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1885 */         if (l > 25)
/*      */         {
/* 1887 */           addStat(StatList.distanceFlownStat, l);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMountedMovementStat(double p_71015_1_, double p_71015_3_, double p_71015_5_) {
/* 1898 */     if (this.ridingEntity != null) {
/*      */       
/* 1900 */       int i = Math.round(MathHelper.sqrt_double(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);
/*      */       
/* 1902 */       if (i > 0)
/*      */       {
/* 1904 */         if (this.ridingEntity instanceof net.minecraft.entity.item.EntityMinecart) {
/*      */           
/* 1906 */           addStat(StatList.distanceByMinecartStat, i);
/*      */           
/* 1908 */           if (this.startMinecartRidingCoordinate == null)
/*      */           {
/* 1910 */             this.startMinecartRidingCoordinate = new BlockPos((Entity)this);
/*      */           }
/* 1912 */           else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0D)
/*      */           {
/* 1914 */             triggerAchievement((StatBase)AchievementList.onARail);
/*      */           }
/*      */         
/* 1917 */         } else if (this.ridingEntity instanceof net.minecraft.entity.item.EntityBoat) {
/*      */           
/* 1919 */           addStat(StatList.distanceByBoatStat, i);
/*      */         }
/* 1921 */         else if (this.ridingEntity instanceof EntityPig) {
/*      */           
/* 1923 */           addStat(StatList.distanceByPigStat, i);
/*      */         }
/* 1925 */         else if (this.ridingEntity instanceof EntityHorse) {
/*      */           
/* 1927 */           addStat(StatList.distanceByHorseStat, i);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1935 */     if (!this.capabilities.allowFlying) {
/*      */       
/* 1937 */       if (distance >= 2.0F)
/*      */       {
/* 1939 */         addStat(StatList.distanceFallenStat, (int)Math.round(distance * 100.0D));
/*      */       }
/*      */       
/* 1942 */       super.fall(distance, damageMultiplier);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1951 */     if (!isSpectator())
/*      */     {
/* 1953 */       super.resetHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getFallSoundString(int damageValue) {
/* 1959 */     return (damageValue > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 1967 */     if (entityLivingIn instanceof net.minecraft.entity.monster.IMob)
/*      */     {
/* 1969 */       triggerAchievement((StatBase)AchievementList.killEnemy);
/*      */     }
/*      */     
/* 1972 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity)entityLivingIn)));
/*      */     
/* 1974 */     if (entitylist$entityegginfo != null)
/*      */     {
/* 1976 */       triggerAchievement(entitylist$entityegginfo.field_151512_d);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 1985 */     if (!this.capabilities.isFlying)
/*      */     {
/* 1987 */       super.setInWeb();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentArmor(int slotIn) {
/* 1993 */     return this.inventory.armorItemInSlot(slotIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperience(int amount) {
/* 2001 */     addScore(amount);
/* 2002 */     int i = Integer.MAX_VALUE - this.experienceTotal;
/*      */     
/* 2004 */     if (amount > i)
/*      */     {
/* 2006 */       amount = i;
/*      */     }
/*      */     
/* 2009 */     this.experience += amount / xpBarCap();
/*      */     
/* 2011 */     for (this.experienceTotal += amount; this.experience >= 1.0F; this.experience /= xpBarCap()) {
/*      */       
/* 2013 */       this.experience = (this.experience - 1.0F) * xpBarCap();
/* 2014 */       addExperienceLevel(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getXPSeed() {
/* 2020 */     return this.xpSeed;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeExperienceLevel(int levels) {
/* 2025 */     this.experienceLevel -= levels;
/*      */     
/* 2027 */     if (this.experienceLevel < 0) {
/*      */       
/* 2029 */       this.experienceLevel = 0;
/* 2030 */       this.experience = 0.0F;
/* 2031 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2034 */     this.xpSeed = this.rand.nextInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/* 2042 */     this.experienceLevel += levels;
/*      */     
/* 2044 */     if (this.experienceLevel < 0) {
/*      */       
/* 2046 */       this.experienceLevel = 0;
/* 2047 */       this.experience = 0.0F;
/* 2048 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2051 */     if (levels > 0 && this.experienceLevel % 5 == 0 && this.lastXPSound < this.ticksExisted - 100.0F) {
/*      */       
/* 2053 */       float f = (this.experienceLevel > 30) ? 1.0F : (this.experienceLevel / 30.0F);
/* 2054 */       this.worldObj.playSoundAtEntity((Entity)this, "random.levelup", f * 0.75F, 1.0F);
/* 2055 */       this.lastXPSound = this.ticksExisted;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int xpBarCap() {
/* 2065 */     return (this.experienceLevel >= 30) ? (112 + (this.experienceLevel - 30) * 9) : ((this.experienceLevel >= 15) ? (37 + (this.experienceLevel - 15) * 5) : (7 + this.experienceLevel * 2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExhaustion(float p_71020_1_) {
/* 2073 */     if (!this.capabilities.disableDamage)
/*      */     {
/* 2075 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2077 */         this.foodStats.addExhaustion(p_71020_1_);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FoodStats getFoodStats() {
/* 2087 */     return this.foodStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEat(boolean ignoreHunger) {
/* 2092 */     return ((ignoreHunger || this.foodStats.needFood()) && !this.capabilities.disableDamage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldHeal() {
/* 2100 */     return (getHealth() > 0.0F && getHealth() < getMaxHealth());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack, int duration) {
/* 2108 */     if (stack != this.itemInUse) {
/*      */       
/* 2110 */       this.itemInUse = stack;
/* 2111 */       this.itemInUseCount = duration;
/*      */       
/* 2113 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2115 */         setEating(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAllowEdit() {
/* 2122 */     return this.capabilities.allowEdit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlayerEdit(BlockPos p_175151_1_, EnumFacing p_175151_2_, ItemStack p_175151_3_) {
/* 2127 */     if (this.capabilities.allowEdit)
/*      */     {
/* 2129 */       return true;
/*      */     }
/* 2131 */     if (p_175151_3_ == null)
/*      */     {
/* 2133 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2137 */     BlockPos blockpos = p_175151_1_.offset(p_175151_2_.getOpposite());
/* 2138 */     Block block = this.worldObj.getBlockState(blockpos).getBlock();
/* 2139 */     return (p_175151_3_.canPlaceOn(block) || p_175151_3_.canEditBlocks());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/* 2148 */     if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
/*      */     {
/* 2150 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2154 */     int i = this.experienceLevel * 7;
/* 2155 */     return (i > 100) ? 100 : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/* 2164 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2169 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
/* 2178 */     if (respawnFromEnd) {
/*      */       
/* 2180 */       this.inventory.copyInventory(oldPlayer.inventory);
/* 2181 */       setHealth(oldPlayer.getHealth());
/* 2182 */       this.foodStats = oldPlayer.foodStats;
/* 2183 */       this.experienceLevel = oldPlayer.experienceLevel;
/* 2184 */       this.experienceTotal = oldPlayer.experienceTotal;
/* 2185 */       this.experience = oldPlayer.experience;
/* 2186 */       setScore(oldPlayer.getScore());
/* 2187 */       this.field_181016_an = oldPlayer.field_181016_an;
/* 2188 */       this.field_181017_ao = oldPlayer.field_181017_ao;
/* 2189 */       this.field_181018_ap = oldPlayer.field_181018_ap;
/*      */     }
/* 2191 */     else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
/*      */       
/* 2193 */       this.inventory.copyInventory(oldPlayer.inventory);
/* 2194 */       this.experienceLevel = oldPlayer.experienceLevel;
/* 2195 */       this.experienceTotal = oldPlayer.experienceTotal;
/* 2196 */       this.experience = oldPlayer.experience;
/* 2197 */       setScore(oldPlayer.getScore());
/*      */     } 
/*      */     
/* 2200 */     this.xpSeed = oldPlayer.xpSeed;
/* 2201 */     this.theInventoryEnderChest = oldPlayer.theInventoryEnderChest;
/* 2202 */     getDataWatcher().updateObject(10, Byte.valueOf(oldPlayer.getDataWatcher().getWatchableObjectByte(10)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 2211 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameType) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommandSenderName() {
/* 2233 */     return this.gameProfile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InventoryEnderChest getInventoryEnderChest() {
/* 2241 */     return this.theInventoryEnderChest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getEquipmentInSlot(int slotIn) {
/* 2249 */     return (slotIn == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[slotIn - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem() {
/* 2257 */     return this.inventory.getCurrentItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 2265 */     this.inventory.armorInventory[slotIn] = stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2275 */     if (!isInvisible())
/*      */     {
/* 2277 */       return false;
/*      */     }
/* 2279 */     if (player.isSpectator())
/*      */     {
/* 2281 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2285 */     Team team = getTeam();
/* 2286 */     return (team == null || player == null || player.getTeam() != team || !team.getSeeFriendlyInvisiblesEnabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isSpectator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 2300 */     return this.inventory.armorInventory;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2305 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */   
/*      */   public Scoreboard getWorldScoreboard() {
/* 2310 */     return this.worldObj.getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 2315 */     return (Team)getWorldScoreboard().getPlayersTeam(getCommandSenderName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2323 */     ChatComponentText chatComponentText = new ChatComponentText(ScorePlayerTeam.formatPlayerName(getTeam(), getCommandSenderName()));
/* 2324 */     chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + getCommandSenderName() + " "));
/* 2325 */     chatComponentText.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2326 */     chatComponentText.getChatStyle().setInsertion(getCommandSenderName());
/* 2327 */     return (IChatComponent)chatComponentText;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 2332 */     float f = 1.62F;
/*      */     
/* 2334 */     if (isPlayerSleeping())
/*      */     {
/* 2336 */       f = 0.2F;
/*      */     }
/*      */     
/* 2339 */     if (isSneaking())
/*      */     {
/* 2341 */       f -= 0.08F;
/*      */     }
/*      */     
/* 2344 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 2349 */     if (amount < 0.0F)
/*      */     {
/* 2351 */       amount = 0.0F;
/*      */     }
/*      */     
/* 2354 */     getDataWatcher().updateObject(17, Float.valueOf(amount));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2359 */     return getDataWatcher().getWatchableObjectFloat(17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UUID getUUID(GameProfile profile) {
/* 2367 */     UUID uuid = profile.getId();
/*      */     
/* 2369 */     if (uuid == null)
/*      */     {
/* 2371 */       uuid = getOfflineUUID(profile.getName());
/*      */     }
/*      */     
/* 2374 */     return uuid;
/*      */   }
/*      */ 
/*      */   
/*      */   public static UUID getOfflineUUID(String username) {
/* 2379 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canOpen(LockCode code) {
/* 2387 */     if (code.isEmpty())
/*      */     {
/* 2389 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2393 */     ItemStack itemstack = getCurrentEquippedItem();
/* 2394 */     return (itemstack != null && itemstack.hasDisplayName()) ? itemstack.getDisplayName().equals(code.getLock()) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWearing(EnumPlayerModelParts p_175148_1_) {
/* 2400 */     return ((getDataWatcher().getWatchableObjectByte(10) & p_175148_1_.getPartMask()) == p_175148_1_.getPartMask());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2408 */     return (MinecraftServer.getServer()).worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2413 */     if (inventorySlot >= 0 && inventorySlot < this.inventory.mainInventory.length) {
/*      */       
/* 2415 */       this.inventory.setInventorySlotContents(inventorySlot, itemStackIn);
/* 2416 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2420 */     int i = inventorySlot - 100;
/*      */     
/* 2422 */     if (i >= 0 && i < this.inventory.armorInventory.length) {
/*      */       
/* 2424 */       int k = i + 1;
/*      */       
/* 2426 */       if (itemStackIn != null && itemStackIn.getItem() != null)
/*      */       {
/* 2428 */         if (itemStackIn.getItem() instanceof net.minecraft.item.ItemArmor) {
/*      */           
/* 2430 */           if (EntityLiving.getArmorPosition(itemStackIn) != k)
/*      */           {
/* 2432 */             return false;
/*      */           }
/*      */         }
/* 2435 */         else if (k != 4 || (itemStackIn.getItem() != Items.skull && !(itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock))) {
/*      */           
/* 2437 */           return false;
/*      */         } 
/*      */       }
/*      */       
/* 2441 */       this.inventory.setInventorySlotContents(i + this.inventory.mainInventory.length, itemStackIn);
/* 2442 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2446 */     int j = inventorySlot - 200;
/*      */     
/* 2448 */     if (j >= 0 && j < this.theInventoryEnderChest.getSizeInventory()) {
/*      */       
/* 2450 */       this.theInventoryEnderChest.setInventorySlotContents(j, itemStackIn);
/* 2451 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2455 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasReducedDebug() {
/* 2466 */     return this.hasReducedDebug;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReducedDebug(boolean reducedDebug) {
/* 2471 */     this.hasReducedDebug = reducedDebug;
/*      */   }
/*      */   
/*      */   public enum EnumChatVisibility
/*      */   {
/* 2476 */     FULL(0, "options.chat.visibility.full"),
/* 2477 */     SYSTEM(1, "options.chat.visibility.system"),
/* 2478 */     HIDDEN(2, "options.chat.visibility.hidden");
/*      */     
/* 2480 */     private static final EnumChatVisibility[] ID_LOOKUP = new EnumChatVisibility[(values()).length];
/*      */     
/*      */     private final int chatVisibility;
/*      */     
/*      */     private final String resourceKey;
/*      */ 
/*      */     
/*      */     EnumChatVisibility(int id, String resourceKey) {
/*      */       this.chatVisibility = id;
/*      */       this.resourceKey = resourceKey;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getChatVisibility() {
/*      */       return this.chatVisibility;
/*      */     }
/*      */     
/*      */     public static EnumChatVisibility getEnumChatVisibility(int id) {
/*      */       return ID_LOOKUP[id % ID_LOOKUP.length];
/*      */     }
/*      */     
/*      */     public String getResourceKey() {
/*      */       return this.resourceKey;
/*      */     }
/*      */     
/*      */     static {
/* 2506 */       for (EnumChatVisibility entityplayer$enumchatvisibility : values())
/*      */       {
/* 2508 */         ID_LOOKUP[entityplayer$enumchatvisibility.chatVisibility] = entityplayer$enumchatvisibility;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public enum EnumStatus
/*      */   {
/* 2515 */     OK,
/* 2516 */     NOT_POSSIBLE_HERE,
/* 2517 */     NOT_POSSIBLE_NOW,
/* 2518 */     TOO_FAR_AWAY,
/* 2519 */     OTHER_PROBLEM,
/* 2520 */     NOT_SAFE;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\player\EntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */