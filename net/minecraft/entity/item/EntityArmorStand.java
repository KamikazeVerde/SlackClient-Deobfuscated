/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityArmorStand
/*     */   extends EntityLivingBase
/*     */ {
/*  28 */   private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*  29 */   private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*  30 */   private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
/*  31 */   private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
/*  32 */   private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
/*  33 */   private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
/*     */   
/*     */   private final ItemStack[] contents;
/*     */   
/*     */   private boolean canInteract;
/*     */   
/*     */   private long punchCooldown;
/*     */   
/*     */   private int disabledSlots;
/*     */   
/*     */   private boolean field_181028_bj;
/*     */   private Rotations headRotation;
/*     */   private Rotations bodyRotation;
/*     */   private Rotations leftArmRotation;
/*     */   private Rotations rightArmRotation;
/*     */   private Rotations leftLegRotation;
/*     */   private Rotations rightLegRotation;
/*     */   
/*     */   public EntityArmorStand(World worldIn) {
/*  52 */     super(worldIn);
/*  53 */     this.contents = new ItemStack[5];
/*  54 */     this.headRotation = DEFAULT_HEAD_ROTATION;
/*  55 */     this.bodyRotation = DEFAULT_BODY_ROTATION;
/*  56 */     this.leftArmRotation = DEFAULT_LEFTARM_ROTATION;
/*  57 */     this.rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
/*  58 */     this.leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
/*  59 */     this.rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;
/*  60 */     setSilent(true);
/*  61 */     this.noClip = hasNoGravity();
/*  62 */     setSize(0.5F, 1.975F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArmorStand(World worldIn, double posX, double posY, double posZ) {
/*  67 */     this(worldIn);
/*  68 */     setPosition(posX, posY, posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerWorld() {
/*  76 */     return (super.isServerWorld() && !hasNoGravity());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  81 */     super.entityInit();
/*  82 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*  83 */     this.dataWatcher.addObject(11, DEFAULT_HEAD_ROTATION);
/*  84 */     this.dataWatcher.addObject(12, DEFAULT_BODY_ROTATION);
/*  85 */     this.dataWatcher.addObject(13, DEFAULT_LEFTARM_ROTATION);
/*  86 */     this.dataWatcher.addObject(14, DEFAULT_RIGHTARM_ROTATION);
/*  87 */     this.dataWatcher.addObject(15, DEFAULT_LEFTLEG_ROTATION);
/*  88 */     this.dataWatcher.addObject(16, DEFAULT_RIGHTLEG_ROTATION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getHeldItem() {
/*  96 */     return this.contents[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEquipmentInSlot(int slotIn) {
/* 104 */     return this.contents[slotIn];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentArmor(int slotIn) {
/* 109 */     return this.contents[slotIn + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 117 */     this.contents[slotIn] = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getInventory() {
/* 125 */     return this.contents;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*     */     int i;
/* 132 */     if (inventorySlot == 99) {
/*     */       
/* 134 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 138 */       i = inventorySlot - 100 + 1;
/*     */       
/* 140 */       if (i < 0 || i >= this.contents.length)
/*     */       {
/* 142 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if (itemStackIn != null && EntityLiving.getArmorPosition(itemStackIn) != i && (i != 4 || !(itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock)))
/*     */     {
/* 148 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 152 */     setCurrentItemOrArmor(i, itemStackIn);
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 162 */     super.writeEntityToNBT(tagCompound);
/* 163 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 165 */     for (int i = 0; i < this.contents.length; i++) {
/*     */       
/* 167 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 169 */       if (this.contents[i] != null)
/*     */       {
/* 171 */         this.contents[i].writeToNBT(nbttagcompound);
/*     */       }
/*     */       
/* 174 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 177 */     tagCompound.setTag("Equipment", (NBTBase)nbttaglist);
/*     */     
/* 179 */     if (getAlwaysRenderNameTag() && (getCustomNameTag() == null || getCustomNameTag().length() == 0))
/*     */     {
/* 181 */       tagCompound.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*     */     }
/*     */     
/* 184 */     tagCompound.setBoolean("Invisible", isInvisible());
/* 185 */     tagCompound.setBoolean("Small", isSmall());
/* 186 */     tagCompound.setBoolean("ShowArms", getShowArms());
/* 187 */     tagCompound.setInteger("DisabledSlots", this.disabledSlots);
/* 188 */     tagCompound.setBoolean("NoGravity", hasNoGravity());
/* 189 */     tagCompound.setBoolean("NoBasePlate", hasNoBasePlate());
/*     */     
/* 191 */     if (func_181026_s())
/*     */     {
/* 193 */       tagCompound.setBoolean("Marker", func_181026_s());
/*     */     }
/*     */     
/* 196 */     tagCompound.setTag("Pose", (NBTBase)readPoseFromNBT());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 204 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 206 */     if (tagCompund.hasKey("Equipment", 9)) {
/*     */       
/* 208 */       NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);
/*     */       
/* 210 */       for (int i = 0; i < this.contents.length; i++)
/*     */       {
/* 212 */         this.contents[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */       }
/*     */     } 
/*     */     
/* 216 */     setInvisible(tagCompund.getBoolean("Invisible"));
/* 217 */     setSmall(tagCompund.getBoolean("Small"));
/* 218 */     setShowArms(tagCompund.getBoolean("ShowArms"));
/* 219 */     this.disabledSlots = tagCompund.getInteger("DisabledSlots");
/* 220 */     setNoGravity(tagCompund.getBoolean("NoGravity"));
/* 221 */     setNoBasePlate(tagCompund.getBoolean("NoBasePlate"));
/* 222 */     func_181027_m(tagCompund.getBoolean("Marker"));
/* 223 */     this.field_181028_bj = !func_181026_s();
/* 224 */     this.noClip = hasNoGravity();
/* 225 */     NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Pose");
/* 226 */     writePoseToNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePoseToNBT(NBTTagCompound tagCompound) {
/* 236 */     NBTTagList nbttaglist = tagCompound.getTagList("Head", 5);
/*     */     
/* 238 */     if (nbttaglist.tagCount() > 0) {
/*     */       
/* 240 */       setHeadRotation(new Rotations(nbttaglist));
/*     */     }
/*     */     else {
/*     */       
/* 244 */       setHeadRotation(DEFAULT_HEAD_ROTATION);
/*     */     } 
/*     */     
/* 247 */     NBTTagList nbttaglist1 = tagCompound.getTagList("Body", 5);
/*     */     
/* 249 */     if (nbttaglist1.tagCount() > 0) {
/*     */       
/* 251 */       setBodyRotation(new Rotations(nbttaglist1));
/*     */     }
/*     */     else {
/*     */       
/* 255 */       setBodyRotation(DEFAULT_BODY_ROTATION);
/*     */     } 
/*     */     
/* 258 */     NBTTagList nbttaglist2 = tagCompound.getTagList("LeftArm", 5);
/*     */     
/* 260 */     if (nbttaglist2.tagCount() > 0) {
/*     */       
/* 262 */       setLeftArmRotation(new Rotations(nbttaglist2));
/*     */     }
/*     */     else {
/*     */       
/* 266 */       setLeftArmRotation(DEFAULT_LEFTARM_ROTATION);
/*     */     } 
/*     */     
/* 269 */     NBTTagList nbttaglist3 = tagCompound.getTagList("RightArm", 5);
/*     */     
/* 271 */     if (nbttaglist3.tagCount() > 0) {
/*     */       
/* 273 */       setRightArmRotation(new Rotations(nbttaglist3));
/*     */     }
/*     */     else {
/*     */       
/* 277 */       setRightArmRotation(DEFAULT_RIGHTARM_ROTATION);
/*     */     } 
/*     */     
/* 280 */     NBTTagList nbttaglist4 = tagCompound.getTagList("LeftLeg", 5);
/*     */     
/* 282 */     if (nbttaglist4.tagCount() > 0) {
/*     */       
/* 284 */       setLeftLegRotation(new Rotations(nbttaglist4));
/*     */     }
/*     */     else {
/*     */       
/* 288 */       setLeftLegRotation(DEFAULT_LEFTLEG_ROTATION);
/*     */     } 
/*     */     
/* 291 */     NBTTagList nbttaglist5 = tagCompound.getTagList("RightLeg", 5);
/*     */     
/* 293 */     if (nbttaglist5.tagCount() > 0) {
/*     */       
/* 295 */       setRightLegRotation(new Rotations(nbttaglist5));
/*     */     }
/*     */     else {
/*     */       
/* 299 */       setRightLegRotation(DEFAULT_RIGHTLEG_ROTATION);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagCompound readPoseFromNBT() {
/* 305 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 307 */     if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation))
/*     */     {
/* 309 */       nbttagcompound.setTag("Head", (NBTBase)this.headRotation.writeToNBT());
/*     */     }
/*     */     
/* 312 */     if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation))
/*     */     {
/* 314 */       nbttagcompound.setTag("Body", (NBTBase)this.bodyRotation.writeToNBT());
/*     */     }
/*     */     
/* 317 */     if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation))
/*     */     {
/* 319 */       nbttagcompound.setTag("LeftArm", (NBTBase)this.leftArmRotation.writeToNBT());
/*     */     }
/*     */     
/* 322 */     if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation))
/*     */     {
/* 324 */       nbttagcompound.setTag("RightArm", (NBTBase)this.rightArmRotation.writeToNBT());
/*     */     }
/*     */     
/* 327 */     if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation))
/*     */     {
/* 329 */       nbttagcompound.setTag("LeftLeg", (NBTBase)this.leftLegRotation.writeToNBT());
/*     */     }
/*     */     
/* 332 */     if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation))
/*     */     {
/* 334 */       nbttagcompound.setTag("RightLeg", (NBTBase)this.rightLegRotation.writeToNBT());
/*     */     }
/*     */     
/* 337 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/* 345 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity p_82167_1_) {}
/*     */ 
/*     */   
/*     */   protected void collideWithNearbyEntities() {
/* 354 */     List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox());
/*     */     
/* 356 */     if (list != null && !list.isEmpty())
/*     */     {
/* 358 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 360 */         Entity entity = list.get(i);
/*     */         
/* 362 */         if (entity instanceof EntityMinecart && ((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE && getDistanceSqToEntity(entity) <= 0.2D)
/*     */         {
/* 364 */           entity.applyEntityCollision((Entity)this);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 375 */     if (func_181026_s())
/*     */     {
/* 377 */       return false;
/*     */     }
/* 379 */     if (!this.worldObj.isRemote && !player.isSpectator()) {
/*     */       
/* 381 */       int i = 0;
/* 382 */       ItemStack itemstack = player.getCurrentEquippedItem();
/* 383 */       boolean flag = (itemstack != null);
/*     */       
/* 385 */       if (flag && itemstack.getItem() instanceof ItemArmor) {
/*     */         
/* 387 */         ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*     */         
/* 389 */         if (itemarmor.armorType == 3) {
/*     */           
/* 391 */           i = 1;
/*     */         }
/* 393 */         else if (itemarmor.armorType == 2) {
/*     */           
/* 395 */           i = 2;
/*     */         }
/* 397 */         else if (itemarmor.armorType == 1) {
/*     */           
/* 399 */           i = 3;
/*     */         }
/* 401 */         else if (itemarmor.armorType == 0) {
/*     */           
/* 403 */           i = 4;
/*     */         } 
/*     */       } 
/*     */       
/* 407 */       if (flag && (itemstack.getItem() == Items.skull || itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)))
/*     */       {
/* 409 */         i = 4;
/*     */       }
/*     */       
/* 412 */       double d4 = 0.1D;
/* 413 */       double d0 = 0.9D;
/* 414 */       double d1 = 0.4D;
/* 415 */       double d2 = 1.6D;
/* 416 */       int j = 0;
/* 417 */       boolean flag1 = isSmall();
/* 418 */       double d3 = flag1 ? (targetVec3.yCoord * 2.0D) : targetVec3.yCoord;
/*     */       
/* 420 */       if (d3 >= 0.1D && d3 < 0.1D + (flag1 ? 0.8D : 0.45D) && this.contents[1] != null) {
/*     */         
/* 422 */         j = 1;
/*     */       }
/* 424 */       else if (d3 >= 0.9D + (flag1 ? 0.3D : 0.0D) && d3 < 0.9D + (flag1 ? 1.0D : 0.7D) && this.contents[3] != null) {
/*     */         
/* 426 */         j = 3;
/*     */       }
/* 428 */       else if (d3 >= 0.4D && d3 < 0.4D + (flag1 ? 1.0D : 0.8D) && this.contents[2] != null) {
/*     */         
/* 430 */         j = 2;
/*     */       }
/* 432 */       else if (d3 >= 1.6D && this.contents[4] != null) {
/*     */         
/* 434 */         j = 4;
/*     */       } 
/*     */       
/* 437 */       boolean flag2 = (this.contents[j] != null);
/*     */       
/* 439 */       if ((this.disabledSlots & 1 << j) != 0 || (this.disabledSlots & 1 << i) != 0) {
/*     */         
/* 441 */         j = i;
/*     */         
/* 443 */         if ((this.disabledSlots & 1 << i) != 0) {
/*     */           
/* 445 */           if ((this.disabledSlots & 0x1) != 0)
/*     */           {
/* 447 */             return true;
/*     */           }
/*     */           
/* 450 */           j = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 454 */       if (flag && i == 0 && !getShowArms())
/*     */       {
/* 456 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 460 */       if (flag) {
/*     */         
/* 462 */         func_175422_a(player, i);
/*     */       }
/* 464 */       else if (flag2) {
/*     */         
/* 466 */         func_175422_a(player, j);
/*     */       } 
/*     */       
/* 469 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 474 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_175422_a(EntityPlayer p_175422_1_, int p_175422_2_) {
/* 480 */     ItemStack itemstack = this.contents[p_175422_2_];
/*     */     
/* 482 */     if (itemstack == null || (this.disabledSlots & 1 << p_175422_2_ + 8) == 0)
/*     */     {
/* 484 */       if (itemstack != null || (this.disabledSlots & 1 << p_175422_2_ + 16) == 0) {
/*     */         
/* 486 */         int i = p_175422_1_.inventory.currentItem;
/* 487 */         ItemStack itemstack1 = p_175422_1_.inventory.getStackInSlot(i);
/*     */         
/* 489 */         if (p_175422_1_.capabilities.isCreativeMode && (itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.air)) && itemstack1 != null) {
/*     */           
/* 491 */           ItemStack itemstack3 = itemstack1.copy();
/* 492 */           itemstack3.stackSize = 1;
/* 493 */           setCurrentItemOrArmor(p_175422_2_, itemstack3);
/*     */         }
/* 495 */         else if (itemstack1 != null && itemstack1.stackSize > 1) {
/*     */           
/* 497 */           if (itemstack == null)
/*     */           {
/* 499 */             ItemStack itemstack2 = itemstack1.copy();
/* 500 */             itemstack2.stackSize = 1;
/* 501 */             setCurrentItemOrArmor(p_175422_2_, itemstack2);
/* 502 */             itemstack1.stackSize--;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 507 */           setCurrentItemOrArmor(p_175422_2_, itemstack1);
/* 508 */           p_175422_1_.inventory.setInventorySlotContents(i, itemstack);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 519 */     if (this.worldObj.isRemote)
/*     */     {
/* 521 */       return false;
/*     */     }
/* 523 */     if (DamageSource.outOfWorld.equals(source)) {
/*     */       
/* 525 */       setDead();
/* 526 */       return false;
/*     */     } 
/* 528 */     if (!isEntityInvulnerable(source) && !this.canInteract && !func_181026_s()) {
/*     */       
/* 530 */       if (source.isExplosion()) {
/*     */         
/* 532 */         dropContents();
/* 533 */         setDead();
/* 534 */         return false;
/*     */       } 
/* 536 */       if (DamageSource.inFire.equals(source)) {
/*     */         
/* 538 */         if (!isBurning()) {
/*     */           
/* 540 */           setFire(5);
/*     */         }
/*     */         else {
/*     */           
/* 544 */           damageArmorStand(0.15F);
/*     */         } 
/*     */         
/* 547 */         return false;
/*     */       } 
/* 549 */       if (DamageSource.onFire.equals(source) && getHealth() > 0.5F) {
/*     */         
/* 551 */         damageArmorStand(4.0F);
/* 552 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 556 */       boolean flag = "arrow".equals(source.getDamageType());
/* 557 */       boolean flag1 = "player".equals(source.getDamageType());
/*     */       
/* 559 */       if (!flag1 && !flag)
/*     */       {
/* 561 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 565 */       if (source.getSourceOfDamage() instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */       {
/* 567 */         source.getSourceOfDamage().setDead();
/*     */       }
/*     */       
/* 570 */       if (source.getEntity() instanceof EntityPlayer && !((EntityPlayer)source.getEntity()).capabilities.allowEdit)
/*     */       {
/* 572 */         return false;
/*     */       }
/* 574 */       if (source.isCreativePlayer()) {
/*     */         
/* 576 */         playParticles();
/* 577 */         setDead();
/* 578 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 582 */       long i = this.worldObj.getTotalWorldTime();
/*     */       
/* 584 */       if (i - this.punchCooldown > 5L && !flag) {
/*     */         
/* 586 */         this.punchCooldown = i;
/*     */       }
/*     */       else {
/*     */         
/* 590 */         dropBlock();
/* 591 */         playParticles();
/* 592 */         setDead();
/*     */       } 
/*     */       
/* 595 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 602 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 612 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/* 614 */     if (Double.isNaN(d0) || d0 == 0.0D)
/*     */     {
/* 616 */       d0 = 4.0D;
/*     */     }
/*     */     
/* 619 */     d0 *= 64.0D;
/* 620 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void playParticles() {
/* 625 */     if (this.worldObj instanceof WorldServer)
/*     */     {
/* 627 */       ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 10, (this.width / 4.0F), (this.height / 4.0F), (this.width / 4.0F), 0.05D, new int[] { Block.getStateId(Blocks.planks.getDefaultState()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void damageArmorStand(float p_175406_1_) {
/* 633 */     float f = getHealth();
/* 634 */     f -= p_175406_1_;
/*     */     
/* 636 */     if (f <= 0.5F) {
/*     */       
/* 638 */       dropContents();
/* 639 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 643 */       setHealth(f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropBlock() {
/* 649 */     Block.spawnAsEntity(this.worldObj, new BlockPos((Entity)this), new ItemStack((Item)Items.armor_stand));
/* 650 */     dropContents();
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropContents() {
/* 655 */     for (int i = 0; i < this.contents.length; i++) {
/*     */       
/* 657 */       if (this.contents[i] != null && (this.contents[i]).stackSize > 0) {
/*     */         
/* 659 */         if (this.contents[i] != null)
/*     */         {
/* 661 */           Block.spawnAsEntity(this.worldObj, (new BlockPos((Entity)this)).up(), this.contents[i]);
/*     */         }
/*     */         
/* 664 */         this.contents[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
/* 671 */     this.prevRenderYawOffset = this.prevRotationYaw;
/* 672 */     this.renderYawOffset = this.rotationYaw;
/* 673 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 678 */     return isChild() ? (this.height * 0.5F) : (this.height * 0.9F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 686 */     if (!hasNoGravity())
/*     */     {
/* 688 */       super.moveEntityWithHeading(strafe, forward);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 697 */     super.onUpdate();
/* 698 */     Rotations rotations = this.dataWatcher.getWatchableObjectRotations(11);
/*     */     
/* 700 */     if (!this.headRotation.equals(rotations))
/*     */     {
/* 702 */       setHeadRotation(rotations);
/*     */     }
/*     */     
/* 705 */     Rotations rotations1 = this.dataWatcher.getWatchableObjectRotations(12);
/*     */     
/* 707 */     if (!this.bodyRotation.equals(rotations1))
/*     */     {
/* 709 */       setBodyRotation(rotations1);
/*     */     }
/*     */     
/* 712 */     Rotations rotations2 = this.dataWatcher.getWatchableObjectRotations(13);
/*     */     
/* 714 */     if (!this.leftArmRotation.equals(rotations2))
/*     */     {
/* 716 */       setLeftArmRotation(rotations2);
/*     */     }
/*     */     
/* 719 */     Rotations rotations3 = this.dataWatcher.getWatchableObjectRotations(14);
/*     */     
/* 721 */     if (!this.rightArmRotation.equals(rotations3))
/*     */     {
/* 723 */       setRightArmRotation(rotations3);
/*     */     }
/*     */     
/* 726 */     Rotations rotations4 = this.dataWatcher.getWatchableObjectRotations(15);
/*     */     
/* 728 */     if (!this.leftLegRotation.equals(rotations4))
/*     */     {
/* 730 */       setLeftLegRotation(rotations4);
/*     */     }
/*     */     
/* 733 */     Rotations rotations5 = this.dataWatcher.getWatchableObjectRotations(16);
/*     */     
/* 735 */     if (!this.rightLegRotation.equals(rotations5))
/*     */     {
/* 737 */       setRightLegRotation(rotations5);
/*     */     }
/*     */     
/* 740 */     boolean flag = func_181026_s();
/*     */     
/* 742 */     if (!this.field_181028_bj && flag) {
/*     */       
/* 744 */       func_181550_a(false);
/*     */     }
/*     */     else {
/*     */       
/* 748 */       if (!this.field_181028_bj || flag) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 753 */       func_181550_a(true);
/*     */     } 
/*     */     
/* 756 */     this.field_181028_bj = flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181550_a(boolean p_181550_1_) {
/* 761 */     double d0 = this.posX;
/* 762 */     double d1 = this.posY;
/* 763 */     double d2 = this.posZ;
/*     */     
/* 765 */     if (p_181550_1_) {
/*     */       
/* 767 */       setSize(0.5F, 1.975F);
/*     */     }
/*     */     else {
/*     */       
/* 771 */       setSize(0.0F, 0.0F);
/*     */     } 
/*     */     
/* 774 */     setPosition(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updatePotionMetadata() {
/* 783 */     setInvisible(this.canInteract);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 788 */     this.canInteract = invisible;
/* 789 */     super.setInvisible(invisible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 797 */     return isSmall();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillCommand() {
/* 805 */     setDead();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmuneToExplosions() {
/* 810 */     return isInvisible();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSmall(boolean p_175420_1_) {
/* 815 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 817 */     if (p_175420_1_) {
/*     */       
/* 819 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     else {
/*     */       
/* 823 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 826 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSmall() {
/* 831 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNoGravity(boolean p_175425_1_) {
/* 836 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 838 */     if (p_175425_1_) {
/*     */       
/* 840 */       b0 = (byte)(b0 | 0x2);
/*     */     }
/*     */     else {
/*     */       
/* 844 */       b0 = (byte)(b0 & 0xFFFFFFFD);
/*     */     } 
/*     */     
/* 847 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoGravity() {
/* 852 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x2) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setShowArms(boolean p_175413_1_) {
/* 857 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 859 */     if (p_175413_1_) {
/*     */       
/* 861 */       b0 = (byte)(b0 | 0x4);
/*     */     }
/*     */     else {
/*     */       
/* 865 */       b0 = (byte)(b0 & 0xFFFFFFFB);
/*     */     } 
/*     */     
/* 868 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getShowArms() {
/* 873 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNoBasePlate(boolean p_175426_1_) {
/* 878 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 880 */     if (p_175426_1_) {
/*     */       
/* 882 */       b0 = (byte)(b0 | 0x8);
/*     */     }
/*     */     else {
/*     */       
/* 886 */       b0 = (byte)(b0 & 0xFFFFFFF7);
/*     */     } 
/*     */     
/* 889 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoBasePlate() {
/* 894 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x8) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181027_m(boolean p_181027_1_) {
/* 899 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 901 */     if (p_181027_1_) {
/*     */       
/* 903 */       b0 = (byte)(b0 | 0x10);
/*     */     }
/*     */     else {
/*     */       
/* 907 */       b0 = (byte)(b0 & 0xFFFFFFEF);
/*     */     } 
/*     */     
/* 910 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181026_s() {
/* 915 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeadRotation(Rotations p_175415_1_) {
/* 920 */     this.headRotation = p_175415_1_;
/* 921 */     this.dataWatcher.updateObject(11, p_175415_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBodyRotation(Rotations p_175424_1_) {
/* 926 */     this.bodyRotation = p_175424_1_;
/* 927 */     this.dataWatcher.updateObject(12, p_175424_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLeftArmRotation(Rotations p_175405_1_) {
/* 932 */     this.leftArmRotation = p_175405_1_;
/* 933 */     this.dataWatcher.updateObject(13, p_175405_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRightArmRotation(Rotations p_175428_1_) {
/* 938 */     this.rightArmRotation = p_175428_1_;
/* 939 */     this.dataWatcher.updateObject(14, p_175428_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLeftLegRotation(Rotations p_175417_1_) {
/* 944 */     this.leftLegRotation = p_175417_1_;
/* 945 */     this.dataWatcher.updateObject(15, p_175417_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRightLegRotation(Rotations p_175427_1_) {
/* 950 */     this.rightLegRotation = p_175427_1_;
/* 951 */     this.dataWatcher.updateObject(16, p_175427_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getHeadRotation() {
/* 956 */     return this.headRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getBodyRotation() {
/* 961 */     return this.bodyRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getLeftArmRotation() {
/* 966 */     return this.leftArmRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getRightArmRotation() {
/* 971 */     return this.rightArmRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getLeftLegRotation() {
/* 976 */     return this.leftLegRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getRightLegRotation() {
/* 981 */     return this.rightLegRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 989 */     return (super.canBeCollidedWith() && !func_181026_s());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\item\EntityArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */