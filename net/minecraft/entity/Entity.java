/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.events.impl.player.StrafeEvent;
/*      */ import cc.slack.features.modules.impl.combat.Hitbox;
/*      */ import cc.slack.utils.client.mc;
/*      */ import cc.slack.utils.player.MovementUtil;
/*      */ import cc.slack.utils.player.RotationUtil;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.block.state.pattern.BlockPattern;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.enchantment.EnchantmentProtection;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.event.HoverEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagDouble;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Entity
/*      */   implements ICommandSender
/*      */ {
/*   64 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*      */ 
/*      */   
/*      */   private static int nextEntityID;
/*      */ 
/*      */   
/*      */   private int entityId;
/*      */ 
/*      */   
/*      */   public double renderDistanceWeight;
/*      */ 
/*      */   
/*      */   public boolean preventEntitySpawning;
/*      */ 
/*      */   
/*      */   public Entity riddenByEntity;
/*      */ 
/*      */   
/*      */   public Entity ridingEntity;
/*      */ 
/*      */   
/*      */   public boolean forceSpawn;
/*      */ 
/*      */   
/*      */   public World worldObj;
/*      */ 
/*      */   
/*      */   public double prevPosX;
/*      */ 
/*      */   
/*      */   public double prevPosY;
/*      */ 
/*      */   
/*      */   public double prevPosZ;
/*      */ 
/*      */   
/*      */   public double posX;
/*      */ 
/*      */   
/*      */   public double posY;
/*      */ 
/*      */   
/*      */   public double posZ;
/*      */ 
/*      */   
/*      */   public double motionX;
/*      */ 
/*      */   
/*      */   public double motionY;
/*      */ 
/*      */   
/*      */   public double motionZ;
/*      */ 
/*      */   
/*      */   public float rotationYaw;
/*      */ 
/*      */   
/*      */   public float prevRotationYaw;
/*      */ 
/*      */   
/*      */   public float rotationPitch;
/*      */ 
/*      */   
/*      */   public float prevRotationPitch;
/*      */ 
/*      */   
/*      */   public AxisAlignedBB boundingBox;
/*      */ 
/*      */   
/*      */   public boolean onGround;
/*      */ 
/*      */   
/*      */   public int offGroundTicks;
/*      */ 
/*      */   
/*      */   public boolean isCollidedHorizontally;
/*      */ 
/*      */   
/*      */   public boolean isCollidedVertically;
/*      */ 
/*      */   
/*      */   public boolean isCollided;
/*      */ 
/*      */   
/*      */   public boolean velocityChanged;
/*      */ 
/*      */   
/*      */   public boolean isInWeb;
/*      */ 
/*      */   
/*      */   private boolean isOutsideBorder;
/*      */ 
/*      */   
/*      */   public boolean isDead;
/*      */ 
/*      */   
/*      */   public float width;
/*      */ 
/*      */   
/*      */   public float height;
/*      */ 
/*      */   
/*      */   public float prevDistanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedOnStepModified;
/*      */ 
/*      */   
/*      */   public float fallDistance;
/*      */   
/*      */   private int nextStepDistance;
/*      */   
/*      */   public double lastTickPosX;
/*      */   
/*      */   public double lastTickPosY;
/*      */   
/*      */   public double lastTickPosZ;
/*      */   
/*      */   public float stepHeight;
/*      */   
/*      */   public boolean noClip;
/*      */   
/*      */   public float entityCollisionReduction;
/*      */   
/*      */   protected Random rand;
/*      */   
/*      */   public int ticksExisted;
/*      */   
/*      */   public int fireResistance;
/*      */   
/*      */   private int fire;
/*      */   
/*      */   protected boolean inWater;
/*      */   
/*      */   public int hurtResistantTime;
/*      */   
/*      */   protected boolean firstUpdate;
/*      */   
/*      */   protected boolean isImmuneToFire;
/*      */   
/*      */   protected DataWatcher dataWatcher;
/*      */   
/*      */   private double entityRiderPitchDelta;
/*      */   
/*      */   private double entityRiderYawDelta;
/*      */   
/*      */   public boolean addedToChunk;
/*      */   
/*      */   public int chunkCoordX;
/*      */   
/*      */   public int chunkCoordY;
/*      */   
/*      */   public int chunkCoordZ;
/*      */   
/*      */   public int serverPosX;
/*      */   
/*      */   public int serverPosY;
/*      */   
/*      */   public int serverPosZ;
/*      */   
/*      */   public boolean ignoreFrustumCheck;
/*      */   
/*      */   public boolean isAirBorne;
/*      */   
/*      */   public int timeUntilPortal;
/*      */   
/*      */   protected boolean inPortal;
/*      */   
/*      */   protected int portalCounter;
/*      */   
/*      */   public int dimension;
/*      */   
/*      */   protected BlockPos field_181016_an;
/*      */   
/*      */   protected Vec3 field_181017_ao;
/*      */   
/*      */   protected EnumFacing field_181018_ap;
/*      */   
/*      */   private boolean invulnerable;
/*      */   
/*      */   protected UUID entityUniqueID;
/*      */   
/*      */   private final CommandResultStats cmdResultStats;
/*      */ 
/*      */   
/*      */   public int getEntityId() {
/*  253 */     return this.entityId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityId(int id) {
/*  258 */     this.entityId = id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  266 */     setDead();
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity(World worldIn) {
/*  271 */     this.entityId = nextEntityID++;
/*  272 */     this.renderDistanceWeight = 1.0D;
/*  273 */     this.boundingBox = ZERO_AABB;
/*  274 */     this.width = 0.6F;
/*  275 */     this.height = 1.8F;
/*  276 */     this.nextStepDistance = 1;
/*  277 */     this.rand = new Random();
/*  278 */     this.fireResistance = 1;
/*  279 */     this.firstUpdate = true;
/*  280 */     this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
/*  281 */     this.cmdResultStats = new CommandResultStats();
/*  282 */     this.worldObj = worldIn;
/*  283 */     setPosition(0.0D, 0.0D, 0.0D);
/*      */     
/*  285 */     if (worldIn != null)
/*      */     {
/*  287 */       this.dimension = worldIn.provider.getDimensionId();
/*      */     }
/*      */     
/*  290 */     this.dataWatcher = new DataWatcher(this);
/*  291 */     this.dataWatcher.addObject(0, Byte.valueOf((byte)0));
/*  292 */     this.dataWatcher.addObject(1, Short.valueOf((short)300));
/*  293 */     this.dataWatcher.addObject(3, Byte.valueOf((byte)0));
/*  294 */     this.dataWatcher.addObject(2, "");
/*  295 */     this.dataWatcher.addObject(4, Byte.valueOf((byte)0));
/*  296 */     entityInit();
/*      */   }
/*      */ 
/*      */   
/*      */   protected abstract void entityInit();
/*      */   
/*      */   public DataWatcher getDataWatcher() {
/*  303 */     return this.dataWatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/*  308 */     return (p_equals_1_ instanceof Entity) ? ((((Entity)p_equals_1_).entityId == this.entityId)) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  313 */     return this.entityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preparePlayerToSpawn() {
/*  322 */     if (this.worldObj != null) {
/*      */       
/*  324 */       while (this.posY > 0.0D && this.posY < 256.0D) {
/*      */         
/*  326 */         setPosition(this.posX, this.posY, this.posZ);
/*      */         
/*  328 */         if (this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  333 */         this.posY++;
/*      */       } 
/*      */       
/*  336 */       this.motionX = this.motionY = this.motionZ = 0.0D;
/*  337 */       this.rotationPitch = 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  346 */     this.isDead = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSize(float width, float height) {
/*  354 */     if (width != this.width || height != this.height) {
/*      */       
/*  356 */       float f = this.width;
/*  357 */       this.width = width;
/*  358 */       this.height = height;
/*  359 */       setEntityBoundingBox(new AxisAlignedBB((getEntityBoundingBox()).minX, (getEntityBoundingBox()).minY, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).minX + this.width, (getEntityBoundingBox()).minY + this.height, (getEntityBoundingBox()).minZ + this.width));
/*      */       
/*  361 */       if (this.width > f && !this.firstUpdate && !this.worldObj.isRemote)
/*      */       {
/*  363 */         moveEntity((f - this.width), 0.0D, (f - this.width));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRotation(float yaw, float pitch) {
/*  373 */     this.rotationYaw = yaw % 360.0F;
/*  374 */     this.rotationPitch = pitch % 360.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  382 */     this.posX = x;
/*  383 */     this.posY = y;
/*  384 */     this.posZ = z;
/*  385 */     float f = this.width / 2.0F;
/*  386 */     float f1 = this.height;
/*  387 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngles(float yaw, float pitch) {
/*  396 */     float f = this.rotationPitch;
/*  397 */     float f1 = this.rotationYaw;
/*  398 */     this.rotationYaw = (float)(this.rotationYaw + yaw * 0.15D);
/*  399 */     this.rotationPitch = (float)(this.rotationPitch - pitch * 0.15D);
/*  400 */     this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0F, 90.0F);
/*  401 */     this.prevRotationPitch += this.rotationPitch - f;
/*  402 */     this.prevRotationYaw += this.rotationYaw - f1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  410 */     onEntityUpdate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  418 */     this.worldObj.theProfiler.startSection("entityBaseTick");
/*      */     
/*  420 */     if (this.ridingEntity != null && this.ridingEntity.isDead)
/*      */     {
/*  422 */       this.ridingEntity = null;
/*      */     }
/*      */     
/*  425 */     this.prevDistanceWalkedModified = this.distanceWalkedModified;
/*  426 */     this.prevPosX = this.posX;
/*  427 */     this.prevPosY = this.posY;
/*  428 */     this.prevPosZ = this.posZ;
/*  429 */     this.prevRotationPitch = this.rotationPitch;
/*  430 */     this.prevRotationYaw = this.rotationYaw;
/*      */     
/*  432 */     if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
/*      */       
/*  434 */       this.worldObj.theProfiler.startSection("portal");
/*  435 */       MinecraftServer minecraftserver = ((WorldServer)this.worldObj).getMinecraftServer();
/*  436 */       int i = getMaxInPortalTime();
/*      */       
/*  438 */       if (this.inPortal) {
/*      */         
/*  440 */         if (minecraftserver.getAllowNether())
/*      */         {
/*  442 */           if (this.ridingEntity == null && this.portalCounter++ >= i) {
/*      */             int j;
/*  444 */             this.portalCounter = i;
/*  445 */             this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */             
/*  448 */             if (this.worldObj.provider.getDimensionId() == -1) {
/*      */               
/*  450 */               j = 0;
/*      */             }
/*      */             else {
/*      */               
/*  454 */               j = -1;
/*      */             } 
/*      */             
/*  457 */             travelToDimension(j);
/*      */           } 
/*      */           
/*  460 */           this.inPortal = false;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  465 */         if (this.portalCounter > 0)
/*      */         {
/*  467 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  470 */         if (this.portalCounter < 0)
/*      */         {
/*  472 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  476 */       if (this.timeUntilPortal > 0)
/*      */       {
/*  478 */         this.timeUntilPortal--;
/*      */       }
/*      */       
/*  481 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/*  484 */     spawnRunningParticles();
/*  485 */     handleWaterMovement();
/*      */     
/*  487 */     if (this.worldObj.isRemote) {
/*      */       
/*  489 */       this.fire = 0;
/*      */     }
/*  491 */     else if (this.fire > 0) {
/*      */       
/*  493 */       if (this.isImmuneToFire) {
/*      */         
/*  495 */         this.fire -= 4;
/*      */         
/*  497 */         if (this.fire < 0)
/*      */         {
/*  499 */           this.fire = 0;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  504 */         if (this.fire % 20 == 0)
/*      */         {
/*  506 */           attackEntityFrom(DamageSource.onFire, 1.0F);
/*      */         }
/*      */         
/*  509 */         this.fire--;
/*      */       } 
/*      */     } 
/*      */     
/*  513 */     if (isInLava()) {
/*      */       
/*  515 */       setOnFireFromLava();
/*  516 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  519 */     if (this.posY < -64.0D)
/*      */     {
/*  521 */       kill();
/*      */     }
/*      */     
/*  524 */     if (!this.worldObj.isRemote)
/*      */     {
/*  526 */       setFlag(0, (this.fire > 0));
/*      */     }
/*      */     
/*  529 */     this.firstUpdate = false;
/*  530 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  538 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOnFireFromLava() {
/*  546 */     if (!this.isImmuneToFire) {
/*      */       
/*  548 */       attackEntityFrom(DamageSource.lava, 4.0F);
/*  549 */       setFire(15);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFire(int seconds) {
/*  558 */     int i = seconds * 20;
/*  559 */     i = EnchantmentProtection.getFireTimeForEntity(this, i);
/*      */     
/*  561 */     if (this.fire < i)
/*      */     {
/*  563 */       this.fire = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void extinguish() {
/*  572 */     this.fire = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/*  580 */     setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOffsetPositionInLiquid(double x, double y, double z) {
/*  588 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(x, y, z);
/*  589 */     return isLiquidPresentInAABB(axisalignedbb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
/*  597 */     return (this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(bb));
/*      */   }
/*      */   
/*      */   public void moveEntity(double x, double y, double z) {
/*  601 */     moveEntity(x, y, z, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntity(double x, double y, double z, boolean safewalk) {
/*  608 */     if (this.noClip) {
/*      */       
/*  610 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/*  611 */       resetPositionToBB();
/*      */     }
/*      */     else {
/*      */       
/*  615 */       this.worldObj.theProfiler.startSection("move");
/*  616 */       double d0 = this.posX;
/*  617 */       double d1 = this.posY;
/*  618 */       double d2 = this.posZ;
/*      */       
/*  620 */       if (this.isInWeb) {
/*      */         
/*  622 */         this.isInWeb = false;
/*  623 */         x *= 0.25D;
/*  624 */         y *= 0.05000000074505806D;
/*  625 */         z *= 0.25D;
/*  626 */         this.motionX = 0.0D;
/*  627 */         this.motionY = 0.0D;
/*  628 */         this.motionZ = 0.0D;
/*      */       } 
/*      */       
/*  631 */       double d3 = x;
/*  632 */       double d4 = y;
/*  633 */       double d5 = z;
/*  634 */       boolean flag = (((this.onGround && isSneaking()) || safewalk) && this instanceof EntityPlayer);
/*      */       
/*  636 */       if (flag) {
/*      */         double d6;
/*      */ 
/*      */         
/*  640 */         for (d6 = 0.05D; x != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x) {
/*      */           
/*  642 */           if (x < d6 && x >= -d6) {
/*      */             
/*  644 */             x = 0.0D;
/*      */           }
/*  646 */           else if (x > 0.0D) {
/*      */             
/*  648 */             x -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  652 */             x += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  656 */         for (; z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty(); d5 = z) {
/*      */           
/*  658 */           if (z < d6 && z >= -d6) {
/*      */             
/*  660 */             z = 0.0D;
/*      */           }
/*  662 */           else if (z > 0.0D) {
/*      */             
/*  664 */             z -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  668 */             z += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  672 */         for (; x != 0.0D && z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty(); d5 = z) {
/*      */           
/*  674 */           if (x < d6 && x >= -d6) {
/*      */             
/*  676 */             x = 0.0D;
/*      */           }
/*  678 */           else if (x > 0.0D) {
/*      */             
/*  680 */             x -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  684 */             x += d6;
/*      */           } 
/*      */           
/*  687 */           d3 = x;
/*      */           
/*  689 */           if (z < d6 && z >= -d6) {
/*      */             
/*  691 */             z = 0.0D;
/*      */           }
/*  693 */           else if (z > 0.0D) {
/*      */             
/*  695 */             z -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  699 */             z += d6;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  704 */       List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(x, y, z));
/*  705 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*      */       
/*  707 */       for (AxisAlignedBB axisalignedbb1 : list1)
/*      */       {
/*  709 */         y = axisalignedbb1.calculateYOffset(getEntityBoundingBox(), y);
/*      */       }
/*      */       
/*  712 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*  713 */       boolean flag1 = (this.onGround || (d4 != y && d4 < 0.0D));
/*      */       
/*  715 */       for (AxisAlignedBB axisalignedbb2 : list1)
/*      */       {
/*  717 */         x = axisalignedbb2.calculateXOffset(getEntityBoundingBox(), x);
/*      */       }
/*      */       
/*  720 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*      */       
/*  722 */       for (AxisAlignedBB axisalignedbb13 : list1)
/*      */       {
/*  724 */         z = axisalignedbb13.calculateZOffset(getEntityBoundingBox(), z);
/*      */       }
/*      */       
/*  727 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*      */       
/*  729 */       if (this.stepHeight > 0.0F && flag1 && (d3 != x || d5 != z)) {
/*      */         
/*  731 */         double d11 = x;
/*  732 */         double d7 = y;
/*  733 */         double d8 = z;
/*  734 */         AxisAlignedBB axisalignedbb3 = getEntityBoundingBox();
/*  735 */         setEntityBoundingBox(axisalignedbb);
/*  736 */         y = this.stepHeight;
/*  737 */         List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(d3, y, d5));
/*  738 */         AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/*  739 */         AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
/*  740 */         double d9 = y;
/*      */         
/*  742 */         for (AxisAlignedBB axisalignedbb6 : list)
/*      */         {
/*  744 */           d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
/*      */         }
/*      */         
/*  747 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
/*  748 */         double d15 = d3;
/*      */         
/*  750 */         for (AxisAlignedBB axisalignedbb7 : list)
/*      */         {
/*  752 */           d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
/*      */         }
/*      */         
/*  755 */         axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
/*  756 */         double d16 = d5;
/*      */         
/*  758 */         for (AxisAlignedBB axisalignedbb8 : list)
/*      */         {
/*  760 */           d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
/*      */         }
/*      */         
/*  763 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
/*  764 */         AxisAlignedBB axisalignedbb14 = getEntityBoundingBox();
/*  765 */         double d17 = y;
/*      */         
/*  767 */         for (AxisAlignedBB axisalignedbb9 : list)
/*      */         {
/*  769 */           d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
/*      */         }
/*      */         
/*  772 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
/*  773 */         double d18 = d3;
/*      */         
/*  775 */         for (AxisAlignedBB axisalignedbb10 : list)
/*      */         {
/*  777 */           d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
/*      */         }
/*      */         
/*  780 */         axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
/*  781 */         double d19 = d5;
/*      */         
/*  783 */         for (AxisAlignedBB axisalignedbb11 : list)
/*      */         {
/*  785 */           d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
/*      */         }
/*      */         
/*  788 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
/*  789 */         double d20 = d15 * d15 + d16 * d16;
/*  790 */         double d10 = d18 * d18 + d19 * d19;
/*      */         
/*  792 */         if (d20 > d10) {
/*      */           
/*  794 */           x = d15;
/*  795 */           z = d16;
/*  796 */           y = -d9;
/*  797 */           setEntityBoundingBox(axisalignedbb4);
/*      */         }
/*      */         else {
/*      */           
/*  801 */           x = d18;
/*  802 */           z = d19;
/*  803 */           y = -d17;
/*  804 */           setEntityBoundingBox(axisalignedbb14);
/*      */         } 
/*      */         
/*  807 */         for (AxisAlignedBB axisalignedbb12 : list)
/*      */         {
/*  809 */           y = axisalignedbb12.calculateYOffset(getEntityBoundingBox(), y);
/*      */         }
/*      */         
/*  812 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*      */         
/*  814 */         if (d11 * d11 + d8 * d8 >= x * x + z * z) {
/*      */           
/*  816 */           x = d11;
/*  817 */           y = d7;
/*  818 */           z = d8;
/*  819 */           setEntityBoundingBox(axisalignedbb3);
/*      */         } 
/*      */       } 
/*      */       
/*  823 */       this.worldObj.theProfiler.endSection();
/*  824 */       this.worldObj.theProfiler.startSection("rest");
/*  825 */       resetPositionToBB();
/*  826 */       this.isCollidedHorizontally = (d3 != x || d5 != z);
/*  827 */       this.isCollidedVertically = (d4 != y);
/*  828 */       this.onGround = (this.isCollidedVertically && d4 < 0.0D);
/*  829 */       if (this.onGround) {
/*  830 */         this.offGroundTicks = 0;
/*      */       } else {
/*  832 */         this.offGroundTicks++;
/*      */       } 
/*  834 */       this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
/*  835 */       int i = MathHelper.floor_double(this.posX);
/*  836 */       int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  837 */       int k = MathHelper.floor_double(this.posZ);
/*  838 */       BlockPos blockpos = new BlockPos(i, j, k);
/*  839 */       Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       
/*  841 */       if (block1.getMaterial() == Material.air) {
/*      */         
/*  843 */         Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */         
/*  845 */         if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockFenceGate) {
/*      */           
/*  847 */           block1 = block;
/*  848 */           blockpos = blockpos.down();
/*      */         } 
/*      */       } 
/*      */       
/*  852 */       updateFallState(y, this.onGround, block1, blockpos);
/*      */       
/*  854 */       if (d3 != x)
/*      */       {
/*  856 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/*  859 */       if (d5 != z)
/*      */       {
/*  861 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/*  864 */       if (d4 != y)
/*      */       {
/*  866 */         block1.onLanded(this.worldObj, this);
/*      */       }
/*      */       
/*  869 */       if (canTriggerWalking() && !flag && this.ridingEntity == null) {
/*      */         
/*  871 */         double d12 = this.posX - d0;
/*  872 */         double d13 = this.posY - d1;
/*  873 */         double d14 = this.posZ - d2;
/*      */         
/*  875 */         if (block1 != Blocks.ladder)
/*      */         {
/*  877 */           d13 = 0.0D;
/*      */         }
/*      */         
/*  880 */         if (block1 != null && this.onGround)
/*      */         {
/*  882 */           block1.onEntityCollidedWithBlock(this.worldObj, blockpos, this);
/*      */         }
/*      */         
/*  885 */         this.distanceWalkedModified = (float)(this.distanceWalkedModified + MathHelper.sqrt_double(d12 * d12 + d14 * d14) * 0.6D);
/*  886 */         this.distanceWalkedOnStepModified = (float)(this.distanceWalkedOnStepModified + MathHelper.sqrt_double(d12 * d12 + d13 * d13 + d14 * d14) * 0.6D);
/*      */         
/*  888 */         if (this.distanceWalkedOnStepModified > this.nextStepDistance && block1.getMaterial() != Material.air) {
/*      */           
/*  890 */           this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
/*      */           
/*  892 */           if (isInWater()) {
/*      */             
/*  894 */             float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;
/*      */             
/*  896 */             if (f > 1.0F)
/*      */             {
/*  898 */               f = 1.0F;
/*      */             }
/*      */             
/*  901 */             playSound(getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*      */           } 
/*      */           
/*  904 */           playStepSound(blockpos, block1);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  910 */         doBlockCollisions();
/*      */       }
/*  912 */       catch (Throwable throwable) {
/*      */         
/*  914 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
/*  915 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/*  916 */         addEntityCrashInfo(crashreportcategory);
/*  917 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  920 */       boolean flag2 = isWet();
/*      */       
/*  922 */       if (this.worldObj.isFlammableWithin(getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
/*      */         
/*  924 */         dealFireDamage(1);
/*      */         
/*  926 */         if (!flag2)
/*      */         {
/*  928 */           this.fire++;
/*      */           
/*  930 */           if (this.fire == 0)
/*      */           {
/*  932 */             setFire(8);
/*      */           }
/*      */         }
/*      */       
/*  936 */       } else if (this.fire <= 0) {
/*      */         
/*  938 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  941 */       if (flag2 && this.fire > 0) {
/*      */         
/*  943 */         playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  944 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  947 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetPositionToBB() {
/*  956 */     this.posX = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) / 2.0D;
/*  957 */     this.posY = (getEntityBoundingBox()).minY;
/*  958 */     this.posZ = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) / 2.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSwimSound() {
/*  963 */     return "game.neutral.swim";
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doBlockCollisions() {
/*  968 */     BlockPos blockpos = new BlockPos((getEntityBoundingBox()).minX + 0.001D, (getEntityBoundingBox()).minY + 0.001D, (getEntityBoundingBox()).minZ + 0.001D);
/*  969 */     BlockPos blockpos1 = new BlockPos((getEntityBoundingBox()).maxX - 0.001D, (getEntityBoundingBox()).maxY - 0.001D, (getEntityBoundingBox()).maxZ - 0.001D);
/*      */     
/*  971 */     if (this.worldObj.isAreaLoaded(blockpos, blockpos1))
/*      */     {
/*  973 */       for (int i = blockpos.getX(); i <= blockpos1.getX(); i++) {
/*      */         
/*  975 */         for (int j = blockpos.getY(); j <= blockpos1.getY(); j++) {
/*      */           
/*  977 */           for (int k = blockpos.getZ(); k <= blockpos1.getZ(); k++) {
/*      */             
/*  979 */             BlockPos blockpos2 = new BlockPos(i, j, k);
/*  980 */             IBlockState iblockstate = this.worldObj.getBlockState(blockpos2);
/*      */ 
/*      */             
/*      */             try {
/*  984 */               iblockstate.getBlock().onEntityCollidedWithBlock(this.worldObj, blockpos2, iblockstate, this);
/*      */             }
/*  986 */             catch (Throwable throwable) {
/*      */               
/*  988 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
/*  989 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
/*  990 */               CrashReportCategory.addBlockInfo(crashreportcategory, blockpos2, iblockstate);
/*  991 */               throw new ReportedException(crashreport);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 1001 */     Block.SoundType block$soundtype = blockIn.stepSound;
/*      */     
/* 1003 */     if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.snow_layer) {
/*      */       
/* 1005 */       block$soundtype = Blocks.snow_layer.stepSound;
/* 1006 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */     }
/* 1008 */     else if (!blockIn.getMaterial().isLiquid()) {
/*      */       
/* 1010 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/* 1016 */     if (!isSilent())
/*      */     {
/* 1018 */       this.worldObj.playSoundAtEntity(this, name, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSilent() {
/* 1027 */     return (this.dataWatcher.getWatchableObjectByte(4) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSilent(boolean isSilent) {
/* 1037 */     this.dataWatcher.updateObject(4, Byte.valueOf((byte)(isSilent ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 1046 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/* 1051 */     if (onGroundIn) {
/*      */       
/* 1053 */       if (this.fallDistance > 0.0F)
/*      */       {
/* 1055 */         if (blockIn != null) {
/*      */           
/* 1057 */           blockIn.onFallenUpon(this.worldObj, pos, this, this.fallDistance);
/*      */         }
/*      */         else {
/*      */           
/* 1061 */           fall(this.fallDistance, 1.0F);
/*      */         } 
/*      */         
/* 1064 */         this.fallDistance = 0.0F;
/*      */       }
/*      */     
/* 1067 */     } else if (y < 0.0D) {
/*      */       
/* 1069 */       this.fallDistance = (float)(this.fallDistance - y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/* 1078 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dealFireDamage(int amount) {
/* 1087 */     if (!this.isImmuneToFire)
/*      */     {
/* 1089 */       attackEntityFrom(DamageSource.inFire, amount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isImmuneToFire() {
/* 1095 */     return this.isImmuneToFire;
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1100 */     if (this.riddenByEntity != null)
/*      */     {
/* 1102 */       this.riddenByEntity.fall(distance, damageMultiplier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWet() {
/* 1111 */     return (this.inWater || this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY, this.posZ)) || this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY + this.height, this.posZ)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInWater() {
/* 1120 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleWaterMovement() {
/* 1128 */     if (this.worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this)) {
/*      */       
/* 1130 */       if (!this.inWater && !this.firstUpdate)
/*      */       {
/* 1132 */         resetHeight();
/*      */       }
/*      */       
/* 1135 */       this.fallDistance = 0.0F;
/* 1136 */       this.inWater = true;
/* 1137 */       this.fire = 0;
/*      */     }
/*      */     else {
/*      */       
/* 1141 */       this.inWater = false;
/*      */     } 
/*      */     
/* 1144 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1152 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.2F;
/*      */     
/* 1154 */     if (f > 1.0F)
/*      */     {
/* 1156 */       f = 1.0F;
/*      */     }
/*      */     
/* 1159 */     playSound(getSplashSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 1160 */     float f1 = MathHelper.floor_double((getEntityBoundingBox()).minY);
/*      */     
/* 1162 */     for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*      */       
/* 1164 */       float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1165 */       float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1166 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f2, (f1 + 1.0F), this.posZ + f3, this.motionX, this.motionY - (this.rand.nextFloat() * 0.2F), this.motionZ, new int[0]);
/*      */     } 
/*      */     
/* 1169 */     for (int j = 0; j < 1.0F + this.width * 20.0F; j++) {
/*      */       
/* 1171 */       float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1172 */       float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1173 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f4, (f1 + 1.0F), this.posZ + f5, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnRunningParticles() {
/* 1182 */     if (isSprinting() && !isInWater())
/*      */     {
/* 1184 */       createRunningParticles();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void createRunningParticles() {
/* 1190 */     int i = MathHelper.floor_double(this.posX);
/* 1191 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 1192 */     int k = MathHelper.floor_double(this.posZ);
/* 1193 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1194 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1195 */     Block block = iblockstate.getBlock();
/*      */     
/* 1197 */     if (block.getRenderType() != -1)
/*      */     {
/* 1199 */       this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSplashSound() {
/* 1205 */     return "game.neutral.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideOfMaterial(Material materialIn) {
/* 1213 */     double d0 = this.posY + getEyeHeight();
/* 1214 */     BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
/* 1215 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1216 */     Block block = iblockstate.getBlock();
/*      */     
/* 1218 */     if (block.getMaterial() == materialIn) {
/*      */       
/* 1220 */       float f = BlockLiquid.getLiquidHeightPercent(iblockstate.getBlock().getMetaFromState(iblockstate)) - 0.11111111F;
/* 1221 */       float f1 = (blockpos.getY() + 1) - f;
/* 1222 */       boolean flag = (d0 < f1);
/* 1223 */       return (!flag && this instanceof EntityPlayer) ? false : flag;
/*      */     } 
/*      */ 
/*      */     
/* 1227 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInLava() {
/* 1233 */     return this.worldObj.isMaterialInBB(getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveFlying(float strafe, float forward, float friction) {
/* 1241 */     float movingYaw = this.rotationYaw;
/* 1242 */     if (RotationUtil.isEnabled) {
/* 1243 */       if (RotationUtil.strafeFix) {
/* 1244 */         movingYaw = RotationUtil.clientRotation[0];
/* 1245 */         if (!RotationUtil.strictStrafeFix) {
/* 1246 */           if (MovementUtil.isBindsMoving()) {
/* 1247 */             int strafeYaw = Math.round((RotationUtil.clientRotation[0] - MovementUtil.getBindsDirection((mc.getPlayer()).rotationYaw)) / 45.0F);
/* 1248 */             if (strafeYaw > 4) {
/* 1249 */               strafeYaw -= 8;
/*      */             }
/* 1251 */             if (strafeYaw < -4) {
/* 1252 */               strafeYaw += 8;
/*      */             }
/* 1254 */             (mc.getGameSettings()).keyBindForward.pressed = (Math.abs(strafeYaw) <= 1);
/* 1255 */             (mc.getGameSettings()).keyBindLeft.pressed = (strafeYaw >= 1 && strafeYaw <= 3);
/* 1256 */             (mc.getGameSettings()).keyBindBack.pressed = (Math.abs(strafeYaw) >= 3);
/* 1257 */             (mc.getGameSettings()).keyBindRight.pressed = (strafeYaw >= -3 && strafeYaw <= -1);
/*      */           } else {
/* 1259 */             (mc.getGameSettings()).keyBindForward.pressed = false;
/* 1260 */             (mc.getGameSettings()).keyBindRight.pressed = false;
/* 1261 */             (mc.getGameSettings()).keyBindBack.pressed = false;
/* 1262 */             (mc.getGameSettings()).keyBindLeft.pressed = false;
/*      */           } 
/*      */         }
/*      */       } else {
/* 1266 */         movingYaw = (mc.getPlayer()).rotationYaw;
/*      */       } 
/*      */     }
/* 1269 */     StrafeEvent event = new StrafeEvent(strafe, forward, friction, movingYaw);
/* 1270 */     if (this == mc.getPlayer()) event.call(); 
/* 1271 */     if (event.isCanceled())
/*      */       return; 
/* 1273 */     float f = event.getStrafe() * event.getStrafe() + event.getForward() * event.getForward();
/*      */     
/* 1275 */     if (f >= 1.0E-4F) {
/* 1276 */       f = MathHelper.sqrt_float(f);
/*      */       
/* 1278 */       if (f < 1.0F)
/*      */       {
/* 1280 */         f = 1.0F;
/*      */       }
/*      */       
/* 1283 */       f = event.getFriction() / f;
/* 1284 */       event.setStrafe(event.getStrafe() * f);
/* 1285 */       event.setForward(event.getForward() * f);
/* 1286 */       float f1 = MathHelper.sin(event.getYaw() * 3.1415927F / 180.0F);
/* 1287 */       float f2 = MathHelper.cos(event.getYaw() * 3.1415927F / 180.0F);
/*      */       
/* 1289 */       this.motionX += (event.getStrafe() * f2 - event.getForward() * f1);
/* 1290 */       this.motionZ += (event.getForward() * f2 + event.getStrafe() * f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBrightnessForRender(float partialTicks) {
/* 1296 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1297 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getCombinedLight(blockpos, 0) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBrightness(float partialTicks) {
/* 1305 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1306 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getLightBrightness(blockpos) : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorld(World worldIn) {
/* 1314 */     this.worldObj = worldIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
/* 1322 */     this.prevPosX = this.posX = x;
/* 1323 */     this.prevPosY = this.posY = y;
/* 1324 */     this.prevPosZ = this.posZ = z;
/* 1325 */     this.prevRotationYaw = this.rotationYaw = yaw;
/* 1326 */     this.prevRotationPitch = this.rotationPitch = pitch;
/* 1327 */     double d0 = (this.prevRotationYaw - yaw);
/*      */     
/* 1329 */     if (d0 < -180.0D)
/*      */     {
/* 1331 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1334 */     if (d0 >= 180.0D)
/*      */     {
/* 1336 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1339 */     setPosition(this.posX, this.posY, this.posZ);
/* 1340 */     setRotation(yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
/* 1345 */     setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, rotationYawIn, rotationPitchIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 1353 */     this.lastTickPosX = this.prevPosX = this.posX = x;
/* 1354 */     this.lastTickPosY = this.prevPosY = this.posY = y;
/* 1355 */     this.lastTickPosZ = this.prevPosZ = this.posZ = z;
/* 1356 */     this.rotationYaw = yaw;
/* 1357 */     this.rotationPitch = pitch;
/* 1358 */     setPosition(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDistanceToEntity(Entity entityIn) {
/* 1366 */     float f = (float)(this.posX - entityIn.posX);
/* 1367 */     float f1 = (float)(this.posY - entityIn.posY);
/* 1368 */     float f2 = (float)(this.posZ - entityIn.posZ);
/* 1369 */     return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSq(double x, double y, double z) {
/* 1377 */     double d0 = this.posX - x;
/* 1378 */     double d1 = this.posY - y;
/* 1379 */     double d2 = this.posZ - z;
/* 1380 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDistanceSq(BlockPos pos) {
/* 1385 */     return pos.distanceSq(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDistanceSqToCenter(BlockPos pos) {
/* 1390 */     return pos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistance(double x, double y, double z) {
/* 1398 */     double d0 = this.posX - x;
/* 1399 */     double d1 = this.posY - y;
/* 1400 */     double d2 = this.posZ - z;
/* 1401 */     return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSqToEntity(Entity entityIn) {
/* 1409 */     double d0 = this.posX - entityIn.posX;
/* 1410 */     double d1 = this.posY - entityIn.posY;
/* 1411 */     double d2 = this.posZ - entityIn.posZ;
/* 1412 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCollideWithPlayer(EntityPlayer entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/* 1427 */     if (entityIn.riddenByEntity != this && entityIn.ridingEntity != this)
/*      */     {
/* 1429 */       if (!entityIn.noClip && !this.noClip) {
/*      */         
/* 1431 */         double d0 = entityIn.posX - this.posX;
/* 1432 */         double d1 = entityIn.posZ - this.posZ;
/* 1433 */         double d2 = MathHelper.abs_max(d0, d1);
/*      */         
/* 1435 */         if (d2 >= 0.009999999776482582D) {
/*      */           
/* 1437 */           d2 = MathHelper.sqrt_double(d2);
/* 1438 */           d0 /= d2;
/* 1439 */           d1 /= d2;
/* 1440 */           double d3 = 1.0D / d2;
/*      */           
/* 1442 */           if (d3 > 1.0D)
/*      */           {
/* 1444 */             d3 = 1.0D;
/*      */           }
/*      */           
/* 1447 */           d0 *= d3;
/* 1448 */           d1 *= d3;
/* 1449 */           d0 *= 0.05000000074505806D;
/* 1450 */           d1 *= 0.05000000074505806D;
/* 1451 */           d0 *= (1.0F - this.entityCollisionReduction);
/* 1452 */           d1 *= (1.0F - this.entityCollisionReduction);
/*      */           
/* 1454 */           if (this.riddenByEntity == null)
/*      */           {
/* 1456 */             addVelocity(-d0, 0.0D, -d1);
/*      */           }
/*      */           
/* 1459 */           if (entityIn.riddenByEntity == null)
/*      */           {
/* 1461 */             entityIn.addVelocity(d0, 0.0D, d1);
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
/*      */   public void addVelocity(double x, double y, double z) {
/* 1473 */     this.motionX += x;
/* 1474 */     this.motionY += y;
/* 1475 */     this.motionZ += z;
/* 1476 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1484 */     this.velocityChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1492 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1494 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1498 */     setBeenAttacked();
/* 1499 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 1508 */     if (RotationUtil.isEnabled) {
/* 1509 */       float[] clientRot = RotationUtil.clientRotation;
/* 1510 */       return getVectorForRotation(clientRot[1], clientRot[0]);
/*      */     } 
/* 1512 */     if (partialTicks == 1.0F)
/*      */     {
/* 1514 */       return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */     }
/*      */ 
/*      */     
/* 1518 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1519 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
/* 1520 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Vec3 getVectorForRotation(float pitch, float yaw) {
/* 1532 */     float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/* 1533 */     float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/* 1534 */     float f2 = -MathHelper.cos(-pitch * 0.017453292F);
/* 1535 */     float f3 = MathHelper.sin(-pitch * 0.017453292F);
/* 1536 */     return new Vec3((f1 * f2), f3, (f * f2));
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getPositionEyes(float partialTicks) {
/* 1541 */     if (partialTicks == 1.0F)
/*      */     {
/* 1543 */       return new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */     }
/*      */ 
/*      */     
/* 1547 */     double d0 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
/* 1548 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks + getEyeHeight();
/* 1549 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
/* 1550 */     return new Vec3(d0, d1, d2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks) {
/* 1556 */     Vec3 vec3 = getPositionEyes(partialTicks);
/* 1557 */     Vec3 vec31 = getLook(partialTicks);
/* 1558 */     Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
/* 1559 */     return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1567 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1575 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRender3d(double x, double y, double z) {
/* 1588 */     double d0 = this.posX - x;
/* 1589 */     double d1 = this.posY - y;
/* 1590 */     double d2 = this.posZ - z;
/* 1591 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 1592 */     return isInRangeToRenderDist(d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRenderDist(double distance) {
/* 1601 */     double d0 = getEntityBoundingBox().getAverageEdgeLength();
/*      */     
/* 1603 */     if (Double.isNaN(d0))
/*      */     {
/* 1605 */       d0 = 1.0D;
/*      */     }
/*      */     
/* 1608 */     d0 = d0 * 64.0D * this.renderDistanceWeight;
/* 1609 */     return (distance < d0 * d0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeMountToNBT(NBTTagCompound tagCompund) {
/* 1618 */     String s = getEntityString();
/*      */     
/* 1620 */     if (!this.isDead && s != null) {
/*      */       
/* 1622 */       tagCompund.setString("id", s);
/* 1623 */       writeToNBT(tagCompund);
/* 1624 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1628 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
/* 1639 */     String s = getEntityString();
/*      */     
/* 1641 */     if (!this.isDead && s != null && this.riddenByEntity == null) {
/*      */       
/* 1643 */       tagCompund.setString("id", s);
/* 1644 */       writeToNBT(tagCompund);
/* 1645 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1649 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeToNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1660 */       tagCompund.setTag("Pos", (NBTBase)newDoubleNBTList(new double[] { this.posX, this.posY, this.posZ }));
/* 1661 */       tagCompund.setTag("Motion", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 1662 */       tagCompund.setTag("Rotation", (NBTBase)newFloatNBTList(new float[] { this.rotationYaw, this.rotationPitch }));
/* 1663 */       tagCompund.setFloat("FallDistance", this.fallDistance);
/* 1664 */       tagCompund.setShort("Fire", (short)this.fire);
/* 1665 */       tagCompund.setShort("Air", (short)getAir());
/* 1666 */       tagCompund.setBoolean("OnGround", this.onGround);
/* 1667 */       tagCompund.setInteger("Dimension", this.dimension);
/* 1668 */       tagCompund.setBoolean("Invulnerable", this.invulnerable);
/* 1669 */       tagCompund.setInteger("PortalCooldown", this.timeUntilPortal);
/* 1670 */       tagCompund.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
/* 1671 */       tagCompund.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
/*      */       
/* 1673 */       if (getCustomNameTag() != null && getCustomNameTag().length() > 0) {
/*      */         
/* 1675 */         tagCompund.setString("CustomName", getCustomNameTag());
/* 1676 */         tagCompund.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/* 1679 */       this.cmdResultStats.writeStatsToNBT(tagCompund);
/*      */       
/* 1681 */       if (isSilent())
/*      */       {
/* 1683 */         tagCompund.setBoolean("Silent", isSilent());
/*      */       }
/*      */       
/* 1686 */       writeEntityToNBT(tagCompund);
/*      */       
/* 1688 */       if (this.ridingEntity != null)
/*      */       {
/* 1690 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */         
/* 1692 */         if (this.ridingEntity.writeMountToNBT(nbttagcompound))
/*      */         {
/* 1694 */           tagCompund.setTag("Riding", (NBTBase)nbttagcompound);
/*      */         }
/*      */       }
/*      */     
/* 1698 */     } catch (Throwable throwable) {
/*      */       
/* 1700 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
/* 1701 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
/* 1702 */       addEntityCrashInfo(crashreportcategory);
/* 1703 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1714 */       NBTTagList nbttaglist = tagCompund.getTagList("Pos", 6);
/* 1715 */       NBTTagList nbttaglist1 = tagCompund.getTagList("Motion", 6);
/* 1716 */       NBTTagList nbttaglist2 = tagCompund.getTagList("Rotation", 5);
/* 1717 */       this.motionX = nbttaglist1.getDoubleAt(0);
/* 1718 */       this.motionY = nbttaglist1.getDoubleAt(1);
/* 1719 */       this.motionZ = nbttaglist1.getDoubleAt(2);
/*      */       
/* 1721 */       if (Math.abs(this.motionX) > 10.0D)
/*      */       {
/* 1723 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 1726 */       if (Math.abs(this.motionY) > 10.0D)
/*      */       {
/* 1728 */         this.motionY = 0.0D;
/*      */       }
/*      */       
/* 1731 */       if (Math.abs(this.motionZ) > 10.0D)
/*      */       {
/* 1733 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 1736 */       this.prevPosX = this.lastTickPosX = this.posX = nbttaglist.getDoubleAt(0);
/* 1737 */       this.prevPosY = this.lastTickPosY = this.posY = nbttaglist.getDoubleAt(1);
/* 1738 */       this.prevPosZ = this.lastTickPosZ = this.posZ = nbttaglist.getDoubleAt(2);
/* 1739 */       this.prevRotationYaw = this.rotationYaw = nbttaglist2.getFloatAt(0);
/* 1740 */       this.prevRotationPitch = this.rotationPitch = nbttaglist2.getFloatAt(1);
/* 1741 */       setRotationYawHead(this.rotationYaw);
/* 1742 */       func_181013_g(this.rotationYaw);
/* 1743 */       this.fallDistance = tagCompund.getFloat("FallDistance");
/* 1744 */       this.fire = tagCompund.getShort("Fire");
/* 1745 */       setAir(tagCompund.getShort("Air"));
/* 1746 */       this.onGround = tagCompund.getBoolean("OnGround");
/* 1747 */       this.dimension = tagCompund.getInteger("Dimension");
/* 1748 */       this.invulnerable = tagCompund.getBoolean("Invulnerable");
/* 1749 */       this.timeUntilPortal = tagCompund.getInteger("PortalCooldown");
/*      */       
/* 1751 */       if (tagCompund.hasKey("UUIDMost", 4) && tagCompund.hasKey("UUIDLeast", 4)) {
/*      */         
/* 1753 */         this.entityUniqueID = new UUID(tagCompund.getLong("UUIDMost"), tagCompund.getLong("UUIDLeast"));
/*      */       }
/* 1755 */       else if (tagCompund.hasKey("UUID", 8)) {
/*      */         
/* 1757 */         this.entityUniqueID = UUID.fromString(tagCompund.getString("UUID"));
/*      */       } 
/*      */       
/* 1760 */       setPosition(this.posX, this.posY, this.posZ);
/* 1761 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/* 1763 */       if (tagCompund.hasKey("CustomName", 8) && tagCompund.getString("CustomName").length() > 0)
/*      */       {
/* 1765 */         setCustomNameTag(tagCompund.getString("CustomName"));
/*      */       }
/*      */       
/* 1768 */       setAlwaysRenderNameTag(tagCompund.getBoolean("CustomNameVisible"));
/* 1769 */       this.cmdResultStats.readStatsFromNBT(tagCompund);
/* 1770 */       setSilent(tagCompund.getBoolean("Silent"));
/* 1771 */       readEntityFromNBT(tagCompund);
/*      */       
/* 1773 */       if (shouldSetPosAfterLoading())
/*      */       {
/* 1775 */         setPosition(this.posX, this.posY, this.posZ);
/*      */       }
/*      */     }
/* 1778 */     catch (Throwable throwable) {
/*      */       
/* 1780 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
/* 1781 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
/* 1782 */       addEntityCrashInfo(crashreportcategory);
/* 1783 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean shouldSetPosAfterLoading() {
/* 1789 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getEntityString() {
/* 1797 */     return EntityList.getEntityString(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newDoubleNBTList(double... numbers) {
/* 1819 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/* 1821 */     for (double d0 : numbers)
/*      */     {
/* 1823 */       nbttaglist.appendTag((NBTBase)new NBTTagDouble(d0));
/*      */     }
/*      */     
/* 1826 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newFloatNBTList(float... numbers) {
/* 1834 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/* 1836 */     for (float f : numbers)
/*      */     {
/* 1838 */       nbttaglist.appendTag((NBTBase)new NBTTagFloat(f));
/*      */     }
/*      */     
/* 1841 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItem(Item itemIn, int size) {
/* 1846 */     return dropItemWithOffset(itemIn, size, 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
/* 1851 */     return entityDropItem(new ItemStack(itemIn, size, 0), offsetY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY) {
/* 1859 */     if (itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
/*      */       
/* 1861 */       EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + offsetY, this.posZ, itemStackIn);
/* 1862 */       entityitem.setDefaultPickupDelay();
/* 1863 */       this.worldObj.spawnEntityInWorld((Entity)entityitem);
/* 1864 */       return entityitem;
/*      */     } 
/*      */ 
/*      */     
/* 1868 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1877 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1885 */     if (this.noClip)
/*      */     {
/* 1887 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1891 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(-2147483648, -2147483648, -2147483648);
/*      */     
/* 1893 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1895 */       int j = MathHelper.floor_double(this.posY + ((((i >> 0) % 2) - 0.5F) * 0.1F) + getEyeHeight());
/* 1896 */       int k = MathHelper.floor_double(this.posX + ((((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
/* 1897 */       int l = MathHelper.floor_double(this.posZ + ((((i >> 2) % 2) - 0.5F) * this.width * 0.8F));
/*      */       
/* 1899 */       if (blockpos$mutableblockpos.getX() != k || blockpos$mutableblockpos.getY() != j || blockpos$mutableblockpos.getZ() != l) {
/*      */         
/* 1901 */         blockpos$mutableblockpos.func_181079_c(k, j, l);
/*      */         
/* 1903 */         if (this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().isVisuallyOpaque())
/*      */         {
/* 1905 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1910 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactFirst(EntityPlayer playerIn) {
/* 1919 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/* 1928 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1936 */     if (this.ridingEntity.isDead) {
/*      */       
/* 1938 */       this.ridingEntity = null;
/*      */     }
/*      */     else {
/*      */       
/* 1942 */       this.motionX = 0.0D;
/* 1943 */       this.motionY = 0.0D;
/* 1944 */       this.motionZ = 0.0D;
/* 1945 */       onUpdate();
/*      */       
/* 1947 */       if (this.ridingEntity != null) {
/*      */         
/* 1949 */         this.ridingEntity.updateRiderPosition();
/* 1950 */         this.entityRiderYawDelta += (this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
/*      */         
/* 1952 */         for (this.entityRiderPitchDelta += (this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch); this.entityRiderYawDelta >= 180.0D; this.entityRiderYawDelta -= 360.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1957 */         while (this.entityRiderYawDelta < -180.0D)
/*      */         {
/* 1959 */           this.entityRiderYawDelta += 360.0D;
/*      */         }
/*      */         
/* 1962 */         while (this.entityRiderPitchDelta >= 180.0D)
/*      */         {
/* 1964 */           this.entityRiderPitchDelta -= 360.0D;
/*      */         }
/*      */         
/* 1967 */         while (this.entityRiderPitchDelta < -180.0D)
/*      */         {
/* 1969 */           this.entityRiderPitchDelta += 360.0D;
/*      */         }
/*      */         
/* 1972 */         double d0 = this.entityRiderYawDelta * 0.5D;
/* 1973 */         double d1 = this.entityRiderPitchDelta * 0.5D;
/* 1974 */         float f = 10.0F;
/*      */         
/* 1976 */         if (d0 > f)
/*      */         {
/* 1978 */           d0 = f;
/*      */         }
/*      */         
/* 1981 */         if (d0 < -f)
/*      */         {
/* 1983 */           d0 = -f;
/*      */         }
/*      */         
/* 1986 */         if (d1 > f)
/*      */         {
/* 1988 */           d1 = f;
/*      */         }
/*      */         
/* 1991 */         if (d1 < -f)
/*      */         {
/* 1993 */           d1 = -f;
/*      */         }
/*      */         
/* 1996 */         this.entityRiderYawDelta -= d0;
/* 1997 */         this.entityRiderPitchDelta -= d1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateRiderPosition() {
/* 2004 */     if (this.riddenByEntity != null)
/*      */     {
/* 2006 */       this.riddenByEntity.setPosition(this.posX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 2015 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/* 2023 */     return this.height * 0.75D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 2031 */     this.entityRiderPitchDelta = 0.0D;
/* 2032 */     this.entityRiderYawDelta = 0.0D;
/*      */     
/* 2034 */     if (entityIn == null) {
/*      */       
/* 2036 */       if (this.ridingEntity != null) {
/*      */         
/* 2038 */         setLocationAndAngles(this.ridingEntity.posX, (this.ridingEntity.getEntityBoundingBox()).minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
/* 2039 */         this.ridingEntity.riddenByEntity = null;
/*      */       } 
/*      */       
/* 2042 */       this.ridingEntity = null;
/*      */     }
/*      */     else {
/*      */       
/* 2046 */       if (this.ridingEntity != null)
/*      */       {
/* 2048 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 2051 */       if (entityIn != null)
/*      */       {
/* 2053 */         for (Entity entity = entityIn.ridingEntity; entity != null; entity = entity.ridingEntity) {
/*      */           
/* 2055 */           if (entity == this) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2062 */       this.ridingEntity = entityIn;
/* 2063 */       entityIn.riddenByEntity = this;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 2069 */     setPosition(x, y, z);
/* 2070 */     setRotation(yaw, pitch);
/* 2071 */     List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().contract(0.03125D, 0.0D, 0.03125D));
/*      */     
/* 2073 */     if (!list.isEmpty()) {
/*      */       
/* 2075 */       double d0 = 0.0D;
/*      */       
/* 2077 */       for (AxisAlignedBB axisalignedbb : list) {
/*      */         
/* 2079 */         if (axisalignedbb.maxY > d0)
/*      */         {
/* 2081 */           d0 = axisalignedbb.maxY;
/*      */         }
/*      */       } 
/*      */       
/* 2085 */       y += d0 - (getEntityBoundingBox()).minY;
/* 2086 */       setPosition(x, y, z);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCollisionBorderSize() {
/* 2092 */     if (((Hitbox)Slack.getInstance().getModuleManager().getInstance(Hitbox.class)).isToggle()) {
/* 2093 */       return 0.1F + ((Float)((Hitbox)Slack.getInstance().getModuleManager().getInstance(Hitbox.class)).hitboxSize.getValue()).floatValue();
/*      */     }
/* 2095 */     return 0.1F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 2104 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181015_d(BlockPos p_181015_1_) {
/* 2109 */     if (this.timeUntilPortal > 0) {
/*      */       
/* 2111 */       this.timeUntilPortal = getPortalCooldown();
/*      */     }
/*      */     else {
/*      */       
/* 2115 */       if (!this.worldObj.isRemote && !p_181015_1_.equals(this.field_181016_an)) {
/*      */         
/* 2117 */         this.field_181016_an = p_181015_1_;
/* 2118 */         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldObj, p_181015_1_);
/* 2119 */         double d0 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.func_181117_a().getZ() : blockpattern$patternhelper.func_181117_a().getX();
/* 2120 */         double d1 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? this.posZ : this.posX;
/* 2121 */         d1 = Math.abs(MathHelper.func_181160_c(d1 - ((blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) ? true : false), d0, d0 - blockpattern$patternhelper.func_181118_d()));
/* 2122 */         double d2 = MathHelper.func_181160_c(this.posY - 1.0D, blockpattern$patternhelper.func_181117_a().getY(), (blockpattern$patternhelper.func_181117_a().getY() - blockpattern$patternhelper.func_181119_e()));
/* 2123 */         this.field_181017_ao = new Vec3(d1, d2, 0.0D);
/* 2124 */         this.field_181018_ap = blockpattern$patternhelper.getFinger();
/*      */       } 
/*      */       
/* 2127 */       this.inPortal = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/* 2136 */     return 300;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/* 2144 */     this.motionX = x;
/* 2145 */     this.motionY = y;
/* 2146 */     this.motionZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHealthUpdate(byte id) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 2165 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBurning() {
/* 2180 */     boolean flag = (this.worldObj != null && this.worldObj.isRemote);
/* 2181 */     return (!this.isImmuneToFire && (this.fire > 0 || (flag && getFlag(0))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRiding() {
/* 2190 */     return (this.ridingEntity != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/* 2198 */     return getFlag(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSneaking(boolean sneaking) {
/* 2206 */     setFlag(1, sneaking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSprinting() {
/* 2214 */     return getFlag(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 2222 */     setFlag(3, sprinting);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvisible() {
/* 2227 */     return getFlag(5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2237 */     return player.isSpectator() ? false : isInvisible();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInvisible(boolean invisible) {
/* 2242 */     setFlag(5, invisible);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEating() {
/* 2247 */     return getFlag(4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEating(boolean eating) {
/* 2252 */     setFlag(4, eating);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getFlag(int flag) {
/* 2261 */     return ((this.dataWatcher.getWatchableObjectByte(0) & 1 << flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFlag(int flag, boolean set) {
/* 2269 */     byte b0 = this.dataWatcher.getWatchableObjectByte(0);
/*      */     
/* 2271 */     if (set) {
/*      */       
/* 2273 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 | 1 << flag)));
/*      */     }
/*      */     else {
/*      */       
/* 2277 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 & (1 << flag ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAir() {
/* 2283 */     return this.dataWatcher.getWatchableObjectShort(1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAir(int air) {
/* 2288 */     this.dataWatcher.updateObject(1, Short.valueOf((short)air));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 2296 */     attackEntityFrom(DamageSource.lightningBolt, 5.0F);
/* 2297 */     this.fire++;
/*      */     
/* 2299 */     if (this.fire == 0)
/*      */     {
/* 2301 */       setFire(8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 2314 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 2315 */     double d0 = x - blockpos.getX();
/* 2316 */     double d1 = y - blockpos.getY();
/* 2317 */     double d2 = z - blockpos.getZ();
/* 2318 */     List<AxisAlignedBB> list = this.worldObj.func_147461_a(getEntityBoundingBox());
/*      */     
/* 2320 */     if (list.isEmpty() && !this.worldObj.isBlockFullCube(blockpos))
/*      */     {
/* 2322 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2326 */     int i = 3;
/* 2327 */     double d3 = 9999.0D;
/*      */     
/* 2329 */     if (!this.worldObj.isBlockFullCube(blockpos.west()) && d0 < d3) {
/*      */       
/* 2331 */       d3 = d0;
/* 2332 */       i = 0;
/*      */     } 
/*      */     
/* 2335 */     if (!this.worldObj.isBlockFullCube(blockpos.east()) && 1.0D - d0 < d3) {
/*      */       
/* 2337 */       d3 = 1.0D - d0;
/* 2338 */       i = 1;
/*      */     } 
/*      */     
/* 2341 */     if (!this.worldObj.isBlockFullCube(blockpos.up()) && 1.0D - d1 < d3) {
/*      */       
/* 2343 */       d3 = 1.0D - d1;
/* 2344 */       i = 3;
/*      */     } 
/*      */     
/* 2347 */     if (!this.worldObj.isBlockFullCube(blockpos.north()) && d2 < d3) {
/*      */       
/* 2349 */       d3 = d2;
/* 2350 */       i = 4;
/*      */     } 
/*      */     
/* 2353 */     if (!this.worldObj.isBlockFullCube(blockpos.south()) && 1.0D - d2 < d3) {
/*      */       
/* 2355 */       d3 = 1.0D - d2;
/* 2356 */       i = 5;
/*      */     } 
/*      */     
/* 2359 */     float f = this.rand.nextFloat() * 0.2F + 0.1F;
/*      */     
/* 2361 */     if (i == 0)
/*      */     {
/* 2363 */       this.motionX = -f;
/*      */     }
/*      */     
/* 2366 */     if (i == 1)
/*      */     {
/* 2368 */       this.motionX = f;
/*      */     }
/*      */     
/* 2371 */     if (i == 3)
/*      */     {
/* 2373 */       this.motionY = f;
/*      */     }
/*      */     
/* 2376 */     if (i == 4)
/*      */     {
/* 2378 */       this.motionZ = -f;
/*      */     }
/*      */     
/* 2381 */     if (i == 5)
/*      */     {
/* 2383 */       this.motionZ = f;
/*      */     }
/*      */     
/* 2386 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 2395 */     this.isInWeb = true;
/* 2396 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommandSenderName() {
/* 2404 */     if (hasCustomName())
/*      */     {
/* 2406 */       return getCustomNameTag();
/*      */     }
/*      */ 
/*      */     
/* 2410 */     String s = EntityList.getEntityString(this);
/*      */     
/* 2412 */     if (s == null)
/*      */     {
/* 2414 */       s = "generic";
/*      */     }
/*      */     
/* 2417 */     return StatCollector.translateToLocal("entity." + s + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity[] getParts() {
/* 2426 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityEqual(Entity entityIn) {
/* 2434 */     return (this == entityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRotationYawHead() {
/* 2439 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_181013_g(float p_181013_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackWithItem() {
/* 2458 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitByEntity(Entity entityIn) {
/* 2466 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2471 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getCommandSenderName(), Integer.valueOf(this.entityId), (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) });
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEntityInvulnerable(DamageSource source) {
/* 2476 */     return (this.invulnerable && source != DamageSource.outOfWorld && !source.isCreativePlayer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyLocationAndAnglesFrom(Entity entityIn) {
/* 2484 */     setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyDataFromOld(Entity entityIn) {
/* 2492 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2493 */     entityIn.writeToNBT(nbttagcompound);
/* 2494 */     readFromNBT(nbttagcompound);
/* 2495 */     this.timeUntilPortal = entityIn.timeUntilPortal;
/* 2496 */     this.field_181016_an = entityIn.field_181016_an;
/* 2497 */     this.field_181017_ao = entityIn.field_181017_ao;
/* 2498 */     this.field_181018_ap = entityIn.field_181018_ap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/* 2506 */     if (!this.worldObj.isRemote && !this.isDead) {
/*      */       
/* 2508 */       this.worldObj.theProfiler.startSection("changeDimension");
/* 2509 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 2510 */       int i = this.dimension;
/* 2511 */       WorldServer worldserver = minecraftserver.worldServerForDimension(i);
/* 2512 */       WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
/* 2513 */       this.dimension = dimensionId;
/*      */       
/* 2515 */       if (i == 1 && dimensionId == 1) {
/*      */         
/* 2517 */         worldserver1 = minecraftserver.worldServerForDimension(0);
/* 2518 */         this.dimension = 0;
/*      */       } 
/*      */       
/* 2521 */       this.worldObj.removeEntity(this);
/* 2522 */       this.isDead = false;
/* 2523 */       this.worldObj.theProfiler.startSection("reposition");
/* 2524 */       minecraftserver.getConfigurationManager().transferEntityToWorld(this, i, worldserver, worldserver1);
/* 2525 */       this.worldObj.theProfiler.endStartSection("reloading");
/* 2526 */       Entity entity = EntityList.createEntityByName(EntityList.getEntityString(this), (World)worldserver1);
/*      */       
/* 2528 */       if (entity != null) {
/*      */         
/* 2530 */         entity.copyDataFromOld(this);
/*      */         
/* 2532 */         if (i == 1 && dimensionId == 1) {
/*      */           
/* 2534 */           BlockPos blockpos = this.worldObj.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
/* 2535 */           entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
/*      */         } 
/*      */         
/* 2538 */         worldserver1.spawnEntityInWorld(entity);
/*      */       } 
/*      */       
/* 2541 */       this.isDead = true;
/* 2542 */       this.worldObj.theProfiler.endSection();
/* 2543 */       worldserver.resetUpdateEntityTick();
/* 2544 */       worldserver1.resetUpdateEntityTick();
/* 2545 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 2554 */     return blockStateIn.getBlock().getExplosionResistance(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 2559 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/* 2567 */     return 3;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 func_181014_aG() {
/* 2572 */     return this.field_181017_ao;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumFacing func_181012_aH() {
/* 2577 */     return this.field_181018_ap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesEntityNotTriggerPressurePlate() {
/* 2585 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 2590 */     category.addCrashSectionCallable("Entity Type", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2594 */             return EntityList.getEntityString(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/*      */           }
/*      */         });
/* 2597 */     category.addCrashSection("Entity ID", Integer.valueOf(this.entityId));
/* 2598 */     category.addCrashSectionCallable("Entity Name", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2602 */             return Entity.this.getCommandSenderName();
/*      */           }
/*      */         });
/* 2605 */     category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) }));
/* 2606 */     category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 2607 */     category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motionX), Double.valueOf(this.motionY), Double.valueOf(this.motionZ) }));
/* 2608 */     category.addCrashSectionCallable("Entity's Rider", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2612 */             return Entity.this.riddenByEntity.toString();
/*      */           }
/*      */         });
/* 2615 */     category.addCrashSectionCallable("Entity's Vehicle", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2619 */             return Entity.this.ridingEntity.toString();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRenderOnFire() {
/* 2629 */     return isBurning();
/*      */   }
/*      */ 
/*      */   
/*      */   public UUID getUniqueID() {
/* 2634 */     return this.entityUniqueID;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2639 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2647 */     ChatComponentText chatcomponenttext = new ChatComponentText(getCommandSenderName());
/* 2648 */     chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2649 */     chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/* 2650 */     return (IChatComponent)chatcomponenttext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomNameTag(String name) {
/* 2658 */     this.dataWatcher.updateObject(2, name);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCustomNameTag() {
/* 2663 */     return this.dataWatcher.getWatchableObjectString(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 2671 */     return (this.dataWatcher.getWatchableObjectString(2).length() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
/* 2676 */     this.dataWatcher.updateObject(3, Byte.valueOf((byte)(alwaysRenderNameTag ? 1 : 0)));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTag() {
/* 2681 */     return (this.dataWatcher.getWatchableObjectByte(3) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 2689 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2694 */     return getAlwaysRenderNameTag();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDataWatcherUpdate(int dataID) {}
/*      */ 
/*      */   
/*      */   public EnumFacing getHorizontalFacing() {
/* 2703 */     return EnumFacing.getHorizontal(MathHelper.floor_double((this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*      */   }
/*      */ 
/*      */   
/*      */   protected HoverEvent getHoverEvent() {
/* 2708 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2709 */     String s = EntityList.getEntityString(this);
/* 2710 */     nbttagcompound.setString("id", getUniqueID().toString());
/*      */     
/* 2712 */     if (s != null)
/*      */     {
/* 2714 */       nbttagcompound.setString("type", s);
/*      */     }
/*      */     
/* 2717 */     nbttagcompound.setString("name", getCommandSenderName());
/* 2718 */     return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, (IChatComponent)new ChatComponentText(nbttagcompound.toString()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/* 2723 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getEntityBoundingBox() {
/* 2728 */     return this.boundingBox;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityBoundingBox(AxisAlignedBB bb) {
/* 2733 */     this.boundingBox = bb;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 2738 */     return this.height * 0.85F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOutsideBorder() {
/* 2743 */     return this.isOutsideBorder;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOutsideBorder(boolean outsideBorder) {
/* 2748 */     this.isOutsideBorder = outsideBorder;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2753 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 2773 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 2782 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 2791 */     return new Vec3(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 2800 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 2808 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2816 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 2821 */     this.cmdResultStats.func_179672_a(this, type, amount);
/*      */   }
/*      */ 
/*      */   
/*      */   public CommandResultStats getCommandStats() {
/* 2826 */     return this.cmdResultStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_174817_o(Entity entityIn) {
/* 2831 */     this.cmdResultStats.func_179671_a(entityIn.getCommandStats());
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound getNBTTagCompound() {
/* 2836 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clientUpdateEntityNBT(NBTTagCompound compound) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 2851 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isImmuneToExplosions() {
/* 2856 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
/* 2861 */     if (entityIn instanceof EntityLivingBase)
/*      */     {
/* 2863 */       EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entityIn, entityLivingBaseIn);
/*      */     }
/*      */     
/* 2866 */     EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */