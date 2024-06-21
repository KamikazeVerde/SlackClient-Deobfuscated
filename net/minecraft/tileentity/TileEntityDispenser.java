/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerDispenser;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ public class TileEntityDispenser extends TileEntityLockable implements IInventory {
/*  15 */   private static final Random RNG = new Random();
/*  16 */   private ItemStack[] stacks = new ItemStack[9];
/*     */ 
/*     */   
/*     */   protected String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  24 */     return 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  34 */     return this.stacks[index];
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
/*  45 */     if (this.stacks[index] != null) {
/*     */       
/*  47 */       if ((this.stacks[index]).stackSize <= count) {
/*     */         
/*  49 */         ItemStack itemstack1 = this.stacks[index];
/*  50 */         this.stacks[index] = null;
/*  51 */         markDirty();
/*  52 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  56 */       ItemStack itemstack = this.stacks[index].splitStack(count);
/*     */       
/*  58 */       if ((this.stacks[index]).stackSize == 0)
/*     */       {
/*  60 */         this.stacks[index] = null;
/*     */       }
/*     */       
/*  63 */       markDirty();
/*  64 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  69 */     return null;
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
/*  80 */     if (this.stacks[index] != null) {
/*     */       
/*  82 */       ItemStack itemstack = this.stacks[index];
/*  83 */       this.stacks[index] = null;
/*  84 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDispenseSlot() {
/*  94 */     int i = -1;
/*  95 */     int j = 1;
/*     */     
/*  97 */     for (int k = 0; k < this.stacks.length; k++) {
/*     */       
/*  99 */       if (this.stacks[k] != null && RNG.nextInt(j++) == 0)
/*     */       {
/* 101 */         i = k;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 113 */     this.stacks[index] = stack;
/*     */     
/* 115 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 117 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 120 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addItemStack(ItemStack stack) {
/* 129 */     for (int i = 0; i < this.stacks.length; i++) {
/*     */       
/* 131 */       if (this.stacks[i] == null || this.stacks[i].getItem() == null) {
/*     */         
/* 133 */         setInventorySlotContents(i, stack);
/* 134 */         return i;
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 146 */     return hasCustomName() ? this.customName : "container.dispenser";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String customName) {
/* 151 */     this.customName = customName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 159 */     return (this.customName != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 164 */     super.readFromNBT(compound);
/* 165 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 166 */     this.stacks = new ItemStack[getSizeInventory()];
/*     */     
/* 168 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 170 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 171 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 173 */       if (j >= 0 && j < this.stacks.length)
/*     */       {
/* 175 */         this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 181 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 187 */     super.writeToNBT(compound);
/* 188 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 190 */     for (int i = 0; i < this.stacks.length; i++) {
/*     */       
/* 192 */       if (this.stacks[i] != null) {
/*     */         
/* 194 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 195 */         nbttagcompound.setByte("Slot", (byte)i);
/* 196 */         this.stacks[i].writeToNBT(nbttagcompound);
/* 197 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 203 */     if (hasCustomName())
/*     */     {
/* 205 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 214 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 222 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 243 */     return "minecraft:dispenser";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 248 */     return (Container)new ContainerDispenser((IInventory)playerInventory, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 253 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 262 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 267 */     for (int i = 0; i < this.stacks.length; i++)
/*     */     {
/* 269 */       this.stacks[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */