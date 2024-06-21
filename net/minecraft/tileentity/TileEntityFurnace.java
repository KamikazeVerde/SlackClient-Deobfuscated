/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFurnace;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerFurnace;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.SlotFurnaceFuel;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class TileEntityFurnace
/*     */   extends TileEntityLockable implements ITickable, ISidedInventory {
/*  30 */   private static final int[] slotsTop = new int[] { 0 };
/*  31 */   private static final int[] slotsBottom = new int[] { 2, 1 };
/*  32 */   private static final int[] slotsSides = new int[] { 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private ItemStack[] furnaceItemStacks = new ItemStack[3];
/*     */ 
/*     */   
/*     */   private int furnaceBurnTime;
/*     */ 
/*     */   
/*     */   private int currentItemBurnTime;
/*     */ 
/*     */   
/*     */   private int cookTime;
/*     */ 
/*     */   
/*     */   private int totalCookTime;
/*     */   
/*     */   private String furnaceCustomName;
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  55 */     return this.furnaceItemStacks.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  65 */     return this.furnaceItemStacks[index];
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
/*  76 */     if (this.furnaceItemStacks[index] != null) {
/*     */       
/*  78 */       if ((this.furnaceItemStacks[index]).stackSize <= count) {
/*     */         
/*  80 */         ItemStack itemstack1 = this.furnaceItemStacks[index];
/*  81 */         this.furnaceItemStacks[index] = null;
/*  82 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  86 */       ItemStack itemstack = this.furnaceItemStacks[index].splitStack(count);
/*     */       
/*  88 */       if ((this.furnaceItemStacks[index]).stackSize == 0)
/*     */       {
/*  90 */         this.furnaceItemStacks[index] = null;
/*     */       }
/*     */       
/*  93 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return null;
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
/* 109 */     if (this.furnaceItemStacks[index] != null) {
/*     */       
/* 111 */       ItemStack itemstack = this.furnaceItemStacks[index];
/* 112 */       this.furnaceItemStacks[index] = null;
/* 113 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 126 */     boolean flag = (stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]));
/* 127 */     this.furnaceItemStacks[index] = stack;
/*     */     
/* 129 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 131 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 134 */     if (index == 0 && !flag) {
/*     */       
/* 136 */       this.totalCookTime = getCookTime(stack);
/* 137 */       this.cookTime = 0;
/* 138 */       markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 147 */     return hasCustomName() ? this.furnaceCustomName : "container.furnace";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 155 */     return (this.furnaceCustomName != null && this.furnaceCustomName.length() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomInventoryName(String p_145951_1_) {
/* 160 */     this.furnaceCustomName = p_145951_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 165 */     super.readFromNBT(compound);
/* 166 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 167 */     this.furnaceItemStacks = new ItemStack[getSizeInventory()];
/*     */     
/* 169 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 171 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 172 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/* 174 */       if (j >= 0 && j < this.furnaceItemStacks.length)
/*     */       {
/* 176 */         this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 180 */     this.furnaceBurnTime = compound.getShort("BurnTime");
/* 181 */     this.cookTime = compound.getShort("CookTime");
/* 182 */     this.totalCookTime = compound.getShort("CookTimeTotal");
/* 183 */     this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
/*     */     
/* 185 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 187 */       this.furnaceCustomName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 193 */     super.writeToNBT(compound);
/* 194 */     compound.setShort("BurnTime", (short)this.furnaceBurnTime);
/* 195 */     compound.setShort("CookTime", (short)this.cookTime);
/* 196 */     compound.setShort("CookTimeTotal", (short)this.totalCookTime);
/* 197 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 199 */     for (int i = 0; i < this.furnaceItemStacks.length; i++) {
/*     */       
/* 201 */       if (this.furnaceItemStacks[i] != null) {
/*     */         
/* 203 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 204 */         nbttagcompound.setByte("Slot", (byte)i);
/* 205 */         this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
/* 206 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 212 */     if (hasCustomName())
/*     */     {
/* 214 */       compound.setString("CustomName", this.furnaceCustomName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 223 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 231 */     return (this.furnaceBurnTime > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBurning(IInventory p_174903_0_) {
/* 236 */     return (p_174903_0_.getField(0) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 244 */     boolean flag = isBurning();
/* 245 */     boolean flag1 = false;
/*     */     
/* 247 */     if (isBurning())
/*     */     {
/* 249 */       this.furnaceBurnTime--;
/*     */     }
/*     */     
/* 252 */     if (!this.worldObj.isRemote) {
/*     */       
/* 254 */       if (isBurning() || (this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null)) {
/*     */         
/* 256 */         if (!isBurning() && canSmelt()) {
/*     */           
/* 258 */           this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
/*     */           
/* 260 */           if (isBurning()) {
/*     */             
/* 262 */             flag1 = true;
/*     */             
/* 264 */             if (this.furnaceItemStacks[1] != null) {
/*     */               
/* 266 */               (this.furnaceItemStacks[1]).stackSize--;
/*     */               
/* 268 */               if ((this.furnaceItemStacks[1]).stackSize == 0) {
/*     */                 
/* 270 */                 Item item = this.furnaceItemStacks[1].getItem().getContainerItem();
/* 271 */                 this.furnaceItemStacks[1] = (item != null) ? new ItemStack(item) : null;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 277 */         if (isBurning() && canSmelt()) {
/*     */           
/* 279 */           this.cookTime++;
/*     */           
/* 281 */           if (this.cookTime == this.totalCookTime)
/*     */           {
/* 283 */             this.cookTime = 0;
/* 284 */             this.totalCookTime = getCookTime(this.furnaceItemStacks[0]);
/* 285 */             smeltItem();
/* 286 */             flag1 = true;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 291 */           this.cookTime = 0;
/*     */         }
/*     */       
/* 294 */       } else if (!isBurning() && this.cookTime > 0) {
/*     */         
/* 296 */         this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
/*     */       } 
/*     */       
/* 299 */       if (flag != isBurning()) {
/*     */         
/* 301 */         flag1 = true;
/* 302 */         BlockFurnace.setState(isBurning(), this.worldObj, this.pos);
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     if (flag1)
/*     */     {
/* 308 */       markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCookTime(ItemStack stack) {
/* 314 */     return 200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSmelt() {
/* 322 */     if (this.furnaceItemStacks[0] == null)
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 328 */     ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
/* 329 */     return (itemstack == null) ? false : ((this.furnaceItemStacks[2] == null) ? true : (!this.furnaceItemStacks[2].isItemEqual(itemstack) ? false : (((this.furnaceItemStacks[2]).stackSize < getInventoryStackLimit() && (this.furnaceItemStacks[2]).stackSize < this.furnaceItemStacks[2].getMaxStackSize()) ? true : (((this.furnaceItemStacks[2]).stackSize < itemstack.getMaxStackSize())))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void smeltItem() {
/* 338 */     if (canSmelt()) {
/*     */       
/* 340 */       ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
/*     */       
/* 342 */       if (this.furnaceItemStacks[2] == null) {
/*     */         
/* 344 */         this.furnaceItemStacks[2] = itemstack.copy();
/*     */       }
/* 346 */       else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem()) {
/*     */         
/* 348 */         (this.furnaceItemStacks[2]).stackSize++;
/*     */       } 
/*     */       
/* 351 */       if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket)
/*     */       {
/* 353 */         this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
/*     */       }
/*     */       
/* 356 */       (this.furnaceItemStacks[0]).stackSize--;
/*     */       
/* 358 */       if ((this.furnaceItemStacks[0]).stackSize <= 0)
/*     */       {
/* 360 */         this.furnaceItemStacks[0] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getItemBurnTime(ItemStack p_145952_0_) {
/* 371 */     if (p_145952_0_ == null)
/*     */     {
/* 373 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 377 */     Item item = p_145952_0_.getItem();
/*     */     
/* 379 */     if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
/*     */       
/* 381 */       Block block = Block.getBlockFromItem(item);
/*     */       
/* 383 */       if (block == Blocks.wooden_slab)
/*     */       {
/* 385 */         return 150;
/*     */       }
/*     */       
/* 388 */       if (block.getMaterial() == Material.wood)
/*     */       {
/* 390 */         return 300;
/*     */       }
/*     */       
/* 393 */       if (block == Blocks.coal_block)
/*     */       {
/* 395 */         return 16000;
/*     */       }
/*     */     } 
/*     */     
/* 399 */     return (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) ? 200 : ((item == Items.stick) ? 100 : ((item == Items.coal) ? 1600 : ((item == Items.lava_bucket) ? 20000 : ((item == Item.getItemFromBlock(Blocks.sapling)) ? 100 : ((item == Items.blaze_rod) ? 2400 : 0)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isItemFuel(ItemStack p_145954_0_) {
/* 405 */     return (getItemBurnTime(p_145954_0_) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 413 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 429 */     return (index == 2) ? false : ((index != 1) ? true : ((isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack))));
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 434 */     return (side == EnumFacing.DOWN) ? slotsBottom : ((side == EnumFacing.UP) ? slotsTop : slotsSides);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 443 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 452 */     if (direction == EnumFacing.DOWN && index == 1) {
/*     */       
/* 454 */       Item item = stack.getItem();
/*     */       
/* 456 */       if (item != Items.water_bucket && item != Items.bucket)
/*     */       {
/* 458 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 462 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 467 */     return "minecraft:furnace";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 472 */     return (Container)new ContainerFurnace(playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 477 */     switch (id) {
/*     */       
/*     */       case 0:
/* 480 */         return this.furnaceBurnTime;
/*     */       
/*     */       case 1:
/* 483 */         return this.currentItemBurnTime;
/*     */       
/*     */       case 2:
/* 486 */         return this.cookTime;
/*     */       
/*     */       case 3:
/* 489 */         return this.totalCookTime;
/*     */     } 
/*     */     
/* 492 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 498 */     switch (id) {
/*     */       
/*     */       case 0:
/* 501 */         this.furnaceBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 505 */         this.currentItemBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 2:
/* 509 */         this.cookTime = value;
/*     */         break;
/*     */       
/*     */       case 3:
/* 513 */         this.totalCookTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 519 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 524 */     for (int i = 0; i < this.furnaceItemStacks.length; i++)
/*     */     {
/* 526 */       this.furnaceItemStacks[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */