/*     */ package net.minecraft.entity.item;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.LockCode;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
/*  16 */   private ItemStack[] minecartContainerItems = new ItemStack[36];
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dropContentsWhenDead = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn) {
/*  26 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn, double p_i1717_2_, double p_i1717_4_, double p_i1717_6_) {
/*  31 */     super(worldIn, p_i1717_2_, p_i1717_4_, p_i1717_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource p_94095_1_) {
/*  36 */     super.killMinecart(p_94095_1_);
/*     */     
/*  38 */     if (this.worldObj.getGameRules().getGameRuleBooleanValue("doEntityDrops"))
/*     */     {
/*  40 */       InventoryHelper.func_180176_a(this.worldObj, this, (IInventory)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  51 */     return this.minecartContainerItems[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  62 */     if (this.minecartContainerItems[index] != null) {
/*     */       
/*  64 */       if ((this.minecartContainerItems[index]).stackSize <= count) {
/*     */         
/*  66 */         ItemStack itemstack1 = this.minecartContainerItems[index];
/*  67 */         this.minecartContainerItems[index] = null;
/*  68 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  72 */       ItemStack itemstack = this.minecartContainerItems[index].splitStack(count);
/*     */       
/*  74 */       if ((this.minecartContainerItems[index]).stackSize == 0)
/*     */       {
/*  76 */         this.minecartContainerItems[index] = null;
/*     */       }
/*     */       
/*  79 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/*  95 */     if (this.minecartContainerItems[index] != null) {
/*     */       
/*  97 */       ItemStack itemstack = this.minecartContainerItems[index];
/*  98 */       this.minecartContainerItems[index] = null;
/*  99 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 112 */     this.minecartContainerItems[index] = stack;
/*     */     
/* 114 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 116 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 133 */     return this.isDead ? false : ((player.getDistanceSqToEntity(this) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 157 */     return hasCustomName() ? getCustomNameTag() : "container.minecart";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 165 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void travelToDimension(int dimensionId) {
/* 173 */     this.dropContentsWhenDead = false;
/* 174 */     super.travelToDimension(dimensionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 182 */     if (this.dropContentsWhenDead)
/*     */     {
/* 184 */       InventoryHelper.func_180176_a(this.worldObj, this, (IInventory)this);
/*     */     }
/*     */     
/* 187 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 195 */     super.writeEntityToNBT(tagCompound);
/* 196 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 198 */     for (int i = 0; i < this.minecartContainerItems.length; i++) {
/*     */       
/* 200 */       if (this.minecartContainerItems[i] != null) {
/*     */         
/* 202 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 203 */         nbttagcompound.setByte("Slot", (byte)i);
/* 204 */         this.minecartContainerItems[i].writeToNBT(nbttagcompound);
/* 205 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     tagCompound.setTag("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 217 */     super.readEntityFromNBT(tagCompund);
/* 218 */     NBTTagList nbttaglist = tagCompund.getTagList("Items", 10);
/* 219 */     this.minecartContainerItems = new ItemStack[getSizeInventory()];
/*     */     
/* 221 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 223 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 224 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 226 */       if (j >= 0 && j < this.minecartContainerItems.length)
/*     */       {
/* 228 */         this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 238 */     if (!this.worldObj.isRemote)
/*     */     {
/* 240 */       playerIn.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyDrag() {
/* 248 */     int i = 15 - Container.calcRedstoneFromInventory((IInventory)this);
/* 249 */     float f = 0.98F + i * 0.001F;
/* 250 */     this.motionX *= f;
/* 251 */     this.motionY *= 0.0D;
/* 252 */     this.motionZ *= f;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 257 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 266 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLockCode(LockCode code) {}
/*     */ 
/*     */   
/*     */   public LockCode getLockCode() {
/* 280 */     return LockCode.EMPTY_CODE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 285 */     for (int i = 0; i < this.minecartContainerItems.length; i++)
/*     */     {
/* 287 */       this.minecartContainerItems[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\item\EntityMinecartContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */