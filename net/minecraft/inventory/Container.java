/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public abstract class Container
/*     */ {
/*  16 */   public List<ItemStack> inventoryItemStacks = Lists.newArrayList();
/*  17 */   public List<Slot> inventorySlots = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public int windowId;
/*     */   
/*     */   private short transactionID;
/*     */   
/*  24 */   private int dragMode = -1;
/*     */   
/*     */   private int dragEvent;
/*     */   
/*  28 */   private final Set<Slot> dragSlots = Sets.newHashSet();
/*  29 */   protected List<ICrafting> crafters = Lists.newArrayList();
/*  30 */   private Set<EntityPlayer> playerList = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Slot addSlotToContainer(Slot slotIn) {
/*  37 */     slotIn.slotNumber = this.inventorySlots.size();
/*  38 */     this.inventorySlots.add(slotIn);
/*  39 */     this.inventoryItemStacks.add(null);
/*  40 */     return slotIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  45 */     if (this.crafters.contains(listener))
/*     */     {
/*  47 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*     */ 
/*     */     
/*  51 */     this.crafters.add(listener);
/*  52 */     listener.updateCraftingInventory(this, getInventory());
/*  53 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCraftingFromCrafters(ICrafting listeners) {
/*  62 */     this.crafters.remove(listeners);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getInventory() {
/*  67 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  69 */     for (int i = 0; i < this.inventorySlots.size(); i++)
/*     */     {
/*  71 */       list.add(((Slot)this.inventorySlots.get(i)).getStack());
/*     */     }
/*     */     
/*  74 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  82 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*     */       
/*  84 */       ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
/*  85 */       ItemStack itemstack1 = this.inventoryItemStacks.get(i);
/*     */       
/*  87 */       if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/*     */         
/*  89 */         itemstack1 = (itemstack == null) ? null : itemstack.copy();
/*  90 */         this.inventoryItemStacks.set(i, itemstack1);
/*     */         
/*  92 */         for (int j = 0; j < this.crafters.size(); j++)
/*     */         {
/*  94 */           ((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enchantItem(EntityPlayer playerIn, int id) {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Slot getSlotFromInventory(IInventory inv, int slotIn) {
/* 110 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*     */       
/* 112 */       Slot slot = this.inventorySlots.get(i);
/*     */       
/* 114 */       if (slot.isHere(inv, slotIn))
/*     */       {
/* 116 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Slot getSlot(int slotId) {
/* 125 */     return this.inventorySlots.get(slotId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 133 */     Slot slot = this.inventorySlots.get(index);
/* 134 */     return (slot != null) ? slot.getStack() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
/* 144 */     ItemStack itemstack = null;
/* 145 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 147 */     if (mode == 5) {
/*     */       
/* 149 */       int i = this.dragEvent;
/* 150 */       this.dragEvent = getDragEvent(clickedButton);
/*     */       
/* 152 */       if ((i != 1 || this.dragEvent != 2) && i != this.dragEvent) {
/*     */         
/* 154 */         resetDrag();
/*     */       }
/* 156 */       else if (inventoryplayer.getItemStack() == null) {
/*     */         
/* 158 */         resetDrag();
/*     */       }
/* 160 */       else if (this.dragEvent == 0) {
/*     */         
/* 162 */         this.dragMode = extractDragMode(clickedButton);
/*     */         
/* 164 */         if (isValidDragMode(this.dragMode, playerIn))
/*     */         {
/* 166 */           this.dragEvent = 1;
/* 167 */           this.dragSlots.clear();
/*     */         }
/*     */         else
/*     */         {
/* 171 */           resetDrag();
/*     */         }
/*     */       
/* 174 */       } else if (this.dragEvent == 1) {
/*     */         
/* 176 */         Slot slot = this.inventorySlots.get(slotId);
/*     */         
/* 178 */         if (slot != null && canAddItemToSlot(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && (inventoryplayer.getItemStack()).stackSize > this.dragSlots.size() && canDragIntoSlot(slot))
/*     */         {
/* 180 */           this.dragSlots.add(slot);
/*     */         }
/*     */       }
/* 183 */       else if (this.dragEvent == 2) {
/*     */         
/* 185 */         if (!this.dragSlots.isEmpty()) {
/*     */           
/* 187 */           ItemStack itemstack3 = inventoryplayer.getItemStack().copy();
/* 188 */           int j = (inventoryplayer.getItemStack()).stackSize;
/*     */           
/* 190 */           for (Slot slot1 : this.dragSlots) {
/*     */             
/* 192 */             if (slot1 != null && canAddItemToSlot(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && (inventoryplayer.getItemStack()).stackSize >= this.dragSlots.size() && canDragIntoSlot(slot1)) {
/*     */               
/* 194 */               ItemStack itemstack1 = itemstack3.copy();
/* 195 */               int k = slot1.getHasStack() ? (slot1.getStack()).stackSize : 0;
/* 196 */               computeStackSize(this.dragSlots, this.dragMode, itemstack1, k);
/*     */               
/* 198 */               if (itemstack1.stackSize > itemstack1.getMaxStackSize())
/*     */               {
/* 200 */                 itemstack1.stackSize = itemstack1.getMaxStackSize();
/*     */               }
/*     */               
/* 203 */               if (itemstack1.stackSize > slot1.getItemStackLimit(itemstack1))
/*     */               {
/* 205 */                 itemstack1.stackSize = slot1.getItemStackLimit(itemstack1);
/*     */               }
/*     */               
/* 208 */               j -= itemstack1.stackSize - k;
/* 209 */               slot1.putStack(itemstack1);
/*     */             } 
/*     */           } 
/*     */           
/* 213 */           itemstack3.stackSize = j;
/*     */           
/* 215 */           if (itemstack3.stackSize <= 0)
/*     */           {
/* 217 */             itemstack3 = null;
/*     */           }
/*     */           
/* 220 */           inventoryplayer.setItemStack(itemstack3);
/*     */         } 
/*     */         
/* 223 */         resetDrag();
/*     */       }
/*     */       else {
/*     */         
/* 227 */         resetDrag();
/*     */       }
/*     */     
/* 230 */     } else if (this.dragEvent != 0) {
/*     */       
/* 232 */       resetDrag();
/*     */     }
/* 234 */     else if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1)) {
/*     */       
/* 236 */       if (slotId == -999) {
/*     */         
/* 238 */         if (inventoryplayer.getItemStack() != null) {
/*     */           
/* 240 */           if (clickedButton == 0) {
/*     */             
/* 242 */             playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
/* 243 */             inventoryplayer.setItemStack(null);
/*     */           } 
/*     */           
/* 246 */           if (clickedButton == 1)
/*     */           {
/* 248 */             playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);
/*     */             
/* 250 */             if ((inventoryplayer.getItemStack()).stackSize == 0)
/*     */             {
/* 252 */               inventoryplayer.setItemStack(null);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 257 */       } else if (mode == 1) {
/*     */         
/* 259 */         if (slotId < 0)
/*     */         {
/* 261 */           return null;
/*     */         }
/*     */         
/* 264 */         Slot slot6 = this.inventorySlots.get(slotId);
/*     */         
/* 266 */         if (slot6 != null && slot6.canTakeStack(playerIn)) {
/*     */           
/* 268 */           ItemStack itemstack8 = transferStackInSlot(playerIn, slotId);
/*     */           
/* 270 */           if (itemstack8 != null)
/*     */           {
/* 272 */             Item item = itemstack8.getItem();
/* 273 */             itemstack = itemstack8.copy();
/*     */             
/* 275 */             if (slot6.getStack() != null && slot6.getStack().getItem() == item)
/*     */             {
/* 277 */               retrySlotClick(slotId, clickedButton, true, playerIn);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 284 */         if (slotId < 0)
/*     */         {
/* 286 */           return null;
/*     */         }
/*     */         
/* 289 */         Slot slot7 = this.inventorySlots.get(slotId);
/*     */         
/* 291 */         if (slot7 != null)
/*     */         {
/* 293 */           ItemStack itemstack9 = slot7.getStack();
/* 294 */           ItemStack itemstack10 = inventoryplayer.getItemStack();
/*     */           
/* 296 */           if (itemstack9 != null)
/*     */           {
/* 298 */             itemstack = itemstack9.copy();
/*     */           }
/*     */           
/* 301 */           if (itemstack9 == null) {
/*     */             
/* 303 */             if (itemstack10 != null && slot7.isItemValid(itemstack10))
/*     */             {
/* 305 */               int k2 = (clickedButton == 0) ? itemstack10.stackSize : 1;
/*     */               
/* 307 */               if (k2 > slot7.getItemStackLimit(itemstack10))
/*     */               {
/* 309 */                 k2 = slot7.getItemStackLimit(itemstack10);
/*     */               }
/*     */               
/* 312 */               if (itemstack10.stackSize >= k2)
/*     */               {
/* 314 */                 slot7.putStack(itemstack10.splitStack(k2));
/*     */               }
/*     */               
/* 317 */               if (itemstack10.stackSize == 0)
/*     */               {
/* 319 */                 inventoryplayer.setItemStack(null);
/*     */               }
/*     */             }
/*     */           
/* 323 */           } else if (slot7.canTakeStack(playerIn)) {
/*     */             
/* 325 */             if (itemstack10 == null) {
/*     */               
/* 327 */               int j2 = (clickedButton == 0) ? itemstack9.stackSize : ((itemstack9.stackSize + 1) / 2);
/* 328 */               ItemStack itemstack12 = slot7.decrStackSize(j2);
/* 329 */               inventoryplayer.setItemStack(itemstack12);
/*     */               
/* 331 */               if (itemstack9.stackSize == 0)
/*     */               {
/* 333 */                 slot7.putStack(null);
/*     */               }
/*     */               
/* 336 */               slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
/*     */             }
/* 338 */             else if (slot7.isItemValid(itemstack10)) {
/*     */               
/* 340 */               if (itemstack9.getItem() == itemstack10.getItem() && itemstack9.getMetadata() == itemstack10.getMetadata() && ItemStack.areItemStackTagsEqual(itemstack9, itemstack10))
/*     */               {
/* 342 */                 int i2 = (clickedButton == 0) ? itemstack10.stackSize : 1;
/*     */                 
/* 344 */                 if (i2 > slot7.getItemStackLimit(itemstack10) - itemstack9.stackSize)
/*     */                 {
/* 346 */                   i2 = slot7.getItemStackLimit(itemstack10) - itemstack9.stackSize;
/*     */                 }
/*     */                 
/* 349 */                 if (i2 > itemstack10.getMaxStackSize() - itemstack9.stackSize)
/*     */                 {
/* 351 */                   i2 = itemstack10.getMaxStackSize() - itemstack9.stackSize;
/*     */                 }
/*     */                 
/* 354 */                 itemstack10.splitStack(i2);
/*     */                 
/* 356 */                 if (itemstack10.stackSize == 0)
/*     */                 {
/* 358 */                   inventoryplayer.setItemStack(null);
/*     */                 }
/*     */                 
/* 361 */                 itemstack9.stackSize += i2;
/*     */               }
/* 363 */               else if (itemstack10.stackSize <= slot7.getItemStackLimit(itemstack10))
/*     */               {
/* 365 */                 slot7.putStack(itemstack10);
/* 366 */                 inventoryplayer.setItemStack(itemstack9);
/*     */               }
/*     */             
/* 369 */             } else if (itemstack9.getItem() == itemstack10.getItem() && itemstack10.getMaxStackSize() > 1 && (!itemstack9.getHasSubtypes() || itemstack9.getMetadata() == itemstack10.getMetadata()) && ItemStack.areItemStackTagsEqual(itemstack9, itemstack10)) {
/*     */               
/* 371 */               int l1 = itemstack9.stackSize;
/*     */               
/* 373 */               if (l1 > 0 && l1 + itemstack10.stackSize <= itemstack10.getMaxStackSize()) {
/*     */                 
/* 375 */                 itemstack10.stackSize += l1;
/* 376 */                 itemstack9 = slot7.decrStackSize(l1);
/*     */                 
/* 378 */                 if (itemstack9.stackSize == 0)
/*     */                 {
/* 380 */                   slot7.putStack(null);
/*     */                 }
/*     */                 
/* 383 */                 slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 388 */           slot7.onSlotChanged();
/*     */         }
/*     */       
/*     */       } 
/* 392 */     } else if (mode == 2 && clickedButton >= 0 && clickedButton < 9) {
/*     */       
/* 394 */       Slot slot5 = this.inventorySlots.get(slotId);
/*     */       
/* 396 */       if (slot5.canTakeStack(playerIn)) {
/*     */         int i;
/* 398 */         ItemStack itemstack7 = inventoryplayer.getStackInSlot(clickedButton);
/* 399 */         boolean flag = (itemstack7 == null || (slot5.inventory == inventoryplayer && slot5.isItemValid(itemstack7)));
/* 400 */         int k1 = -1;
/*     */         
/* 402 */         if (!flag) {
/*     */           
/* 404 */           k1 = inventoryplayer.getFirstEmptyStack();
/* 405 */           i = flag | ((k1 > -1) ? 1 : 0);
/*     */         } 
/*     */         
/* 408 */         if (slot5.getHasStack() && i != 0) {
/*     */           
/* 410 */           ItemStack itemstack11 = slot5.getStack();
/* 411 */           inventoryplayer.setInventorySlotContents(clickedButton, itemstack11.copy());
/*     */           
/* 413 */           if ((slot5.inventory != inventoryplayer || !slot5.isItemValid(itemstack7)) && itemstack7 != null) {
/*     */             
/* 415 */             if (k1 > -1)
/*     */             {
/* 417 */               inventoryplayer.addItemStackToInventory(itemstack7);
/* 418 */               slot5.decrStackSize(itemstack11.stackSize);
/* 419 */               slot5.putStack(null);
/* 420 */               slot5.onPickupFromSlot(playerIn, itemstack11);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 425 */             slot5.decrStackSize(itemstack11.stackSize);
/* 426 */             slot5.putStack(itemstack7);
/* 427 */             slot5.onPickupFromSlot(playerIn, itemstack11);
/*     */           }
/*     */         
/* 430 */         } else if (!slot5.getHasStack() && itemstack7 != null && slot5.isItemValid(itemstack7)) {
/*     */           
/* 432 */           inventoryplayer.setInventorySlotContents(clickedButton, null);
/* 433 */           slot5.putStack(itemstack7);
/*     */         }
/*     */       
/*     */       } 
/* 437 */     } else if (mode == 3 && playerIn.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0) {
/*     */       
/* 439 */       Slot slot4 = this.inventorySlots.get(slotId);
/*     */       
/* 441 */       if (slot4 != null && slot4.getHasStack())
/*     */       {
/* 443 */         ItemStack itemstack6 = slot4.getStack().copy();
/* 444 */         itemstack6.stackSize = itemstack6.getMaxStackSize();
/* 445 */         inventoryplayer.setItemStack(itemstack6);
/*     */       }
/*     */     
/* 448 */     } else if (mode == 4 && inventoryplayer.getItemStack() == null && slotId >= 0) {
/*     */       
/* 450 */       Slot slot3 = this.inventorySlots.get(slotId);
/*     */       
/* 452 */       if (slot3 != null && slot3.getHasStack() && slot3.canTakeStack(playerIn))
/*     */       {
/* 454 */         ItemStack itemstack5 = slot3.decrStackSize((clickedButton == 0) ? 1 : (slot3.getStack()).stackSize);
/* 455 */         slot3.onPickupFromSlot(playerIn, itemstack5);
/* 456 */         playerIn.dropPlayerItemWithRandomChoice(itemstack5, true);
/*     */       }
/*     */     
/* 459 */     } else if (mode == 6 && slotId >= 0) {
/*     */       
/* 461 */       Slot slot2 = this.inventorySlots.get(slotId);
/* 462 */       ItemStack itemstack4 = inventoryplayer.getItemStack();
/*     */       
/* 464 */       if (itemstack4 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(playerIn))) {
/*     */         
/* 466 */         int i1 = (clickedButton == 0) ? 0 : (this.inventorySlots.size() - 1);
/* 467 */         int j1 = (clickedButton == 0) ? 1 : -1;
/*     */         
/* 469 */         for (int l2 = 0; l2 < 2; l2++) {
/*     */           int i3;
/* 471 */           for (i3 = i1; i3 >= 0 && i3 < this.inventorySlots.size() && itemstack4.stackSize < itemstack4.getMaxStackSize(); i3 += j1) {
/*     */             
/* 473 */             Slot slot8 = this.inventorySlots.get(i3);
/*     */             
/* 475 */             if (slot8.getHasStack() && canAddItemToSlot(slot8, itemstack4, true) && slot8.canTakeStack(playerIn) && canMergeSlot(itemstack4, slot8) && (l2 != 0 || (slot8.getStack()).stackSize != slot8.getStack().getMaxStackSize())) {
/*     */               
/* 477 */               int l = Math.min(itemstack4.getMaxStackSize() - itemstack4.stackSize, (slot8.getStack()).stackSize);
/* 478 */               ItemStack itemstack2 = slot8.decrStackSize(l);
/* 479 */               itemstack4.stackSize += l;
/*     */               
/* 481 */               if (itemstack2.stackSize <= 0)
/*     */               {
/* 483 */                 slot8.putStack(null);
/*     */               }
/*     */               
/* 486 */               slot8.onPickupFromSlot(playerIn, itemstack2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 492 */       detectAndSendChanges();
/*     */     } 
/*     */     
/* 495 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot p_94530_2_) {
/* 504 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {
/* 516 */     slotClick(slotId, clickedButton, 1, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 524 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 526 */     if (inventoryplayer.getItemStack() != null) {
/*     */       
/* 528 */       playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
/* 529 */       inventoryplayer.setItemStack(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 538 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStackInSlot(int slotID, ItemStack stack) {
/* 549 */     getSlot(slotID).putStack(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStacksInSlots(ItemStack[] p_75131_1_) {
/* 557 */     for (int i = 0; i < p_75131_1_.length; i++)
/*     */     {
/* 559 */       getSlot(i).putStack(p_75131_1_[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getNextTransactionID(InventoryPlayer p_75136_1_) {
/* 572 */     this.transactionID = (short)(this.transactionID + 1);
/* 573 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanCraft(EntityPlayer p_75129_1_) {
/* 581 */     return !this.playerList.contains(p_75129_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanCraft(EntityPlayer p_75128_1_, boolean p_75128_2_) {
/* 589 */     if (p_75128_2_) {
/*     */       
/* 591 */       this.playerList.remove(p_75128_1_);
/*     */     }
/*     */     else {
/*     */       
/* 595 */       this.playerList.add(p_75128_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canInteractWith(EntityPlayer paramEntityPlayer);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
/* 608 */     boolean flag = false;
/* 609 */     int i = startIndex;
/*     */     
/* 611 */     if (reverseDirection)
/*     */     {
/* 613 */       i = endIndex - 1;
/*     */     }
/*     */     
/* 616 */     if (stack.isStackable())
/*     */     {
/* 618 */       while (stack.stackSize > 0 && ((!reverseDirection && i < endIndex) || (reverseDirection && i >= startIndex))) {
/*     */         
/* 620 */         Slot slot = this.inventorySlots.get(i);
/* 621 */         ItemStack itemstack = slot.getStack();
/*     */         
/* 623 */         if (itemstack != null && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
/*     */           
/* 625 */           int j = itemstack.stackSize + stack.stackSize;
/*     */           
/* 627 */           if (j <= stack.getMaxStackSize()) {
/*     */             
/* 629 */             stack.stackSize = 0;
/* 630 */             itemstack.stackSize = j;
/* 631 */             slot.onSlotChanged();
/* 632 */             flag = true;
/*     */           }
/* 634 */           else if (itemstack.stackSize < stack.getMaxStackSize()) {
/*     */             
/* 636 */             stack.stackSize -= stack.getMaxStackSize() - itemstack.stackSize;
/* 637 */             itemstack.stackSize = stack.getMaxStackSize();
/* 638 */             slot.onSlotChanged();
/* 639 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 643 */         if (reverseDirection) {
/*     */           
/* 645 */           i--;
/*     */           
/*     */           continue;
/*     */         } 
/* 649 */         i++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 654 */     if (stack.stackSize > 0) {
/*     */       
/* 656 */       if (reverseDirection) {
/*     */         
/* 658 */         i = endIndex - 1;
/*     */       }
/*     */       else {
/*     */         
/* 662 */         i = startIndex;
/*     */       } 
/*     */       
/* 665 */       while ((!reverseDirection && i < endIndex) || (reverseDirection && i >= startIndex)) {
/*     */         
/* 667 */         Slot slot1 = this.inventorySlots.get(i);
/* 668 */         ItemStack itemstack1 = slot1.getStack();
/*     */         
/* 670 */         if (itemstack1 == null) {
/*     */           
/* 672 */           slot1.putStack(stack.copy());
/* 673 */           slot1.onSlotChanged();
/* 674 */           stack.stackSize = 0;
/* 675 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/* 679 */         if (reverseDirection) {
/*     */           
/* 681 */           i--;
/*     */           
/*     */           continue;
/*     */         } 
/* 685 */         i++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 690 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int extractDragMode(int p_94529_0_) {
/* 698 */     return p_94529_0_ >> 2 & 0x3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDragEvent(int p_94532_0_) {
/* 706 */     return p_94532_0_ & 0x3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_94534_d(int p_94534_0_, int p_94534_1_) {
/* 711 */     return p_94534_0_ & 0x3 | (p_94534_1_ & 0x3) << 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidDragMode(int dragModeIn, EntityPlayer player) {
/* 716 */     return (dragModeIn == 0) ? true : ((dragModeIn == 1) ? true : ((dragModeIn == 2 && player.capabilities.isCreativeMode)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetDrag() {
/* 724 */     this.dragEvent = 0;
/* 725 */     this.dragSlots.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canAddItemToSlot(Slot slotIn, ItemStack stack, boolean stackSizeMatters) {
/*     */     int i;
/* 733 */     boolean flag = (slotIn == null || !slotIn.getHasStack());
/*     */     
/* 735 */     if (slotIn != null && slotIn.getHasStack() && stack != null && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack))
/*     */     {
/* 737 */       i = flag | (((slotIn.getStack()).stackSize + (stackSizeMatters ? 0 : stack.stackSize) <= stack.getMaxStackSize()) ? 1 : 0);
/*     */     }
/*     */     
/* 740 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeStackSize(Set<Slot> p_94525_0_, int p_94525_1_, ItemStack p_94525_2_, int p_94525_3_) {
/* 749 */     switch (p_94525_1_) {
/*     */       
/*     */       case 0:
/* 752 */         p_94525_2_.stackSize = MathHelper.floor_float(p_94525_2_.stackSize / p_94525_0_.size());
/*     */         break;
/*     */       
/*     */       case 1:
/* 756 */         p_94525_2_.stackSize = 1;
/*     */         break;
/*     */       
/*     */       case 2:
/* 760 */         p_94525_2_.stackSize = p_94525_2_.getItem().getItemStackLimit();
/*     */         break;
/*     */     } 
/* 763 */     p_94525_2_.stackSize += p_94525_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDragIntoSlot(Slot p_94531_1_) {
/* 772 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcRedstone(TileEntity te) {
/* 780 */     return (te instanceof IInventory) ? calcRedstoneFromInventory((IInventory)te) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcRedstoneFromInventory(IInventory inv) {
/* 785 */     if (inv == null)
/*     */     {
/* 787 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 791 */     int i = 0;
/* 792 */     float f = 0.0F;
/*     */     
/* 794 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/* 796 */       ItemStack itemstack = inv.getStackInSlot(j);
/*     */       
/* 798 */       if (itemstack != null) {
/*     */         
/* 800 */         f += itemstack.stackSize / Math.min(inv.getInventoryStackLimit(), itemstack.getMaxStackSize());
/* 801 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 805 */     f /= inv.getSizeInventory();
/* 806 */     return MathHelper.floor_float(f * 14.0F) + ((i > 0) ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\inventory\Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */