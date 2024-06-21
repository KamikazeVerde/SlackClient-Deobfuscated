/*      */ package net.minecraft.item;
/*      */ 
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentDurability;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureAttribute;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.event.HoverEvent;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public final class ItemStack
/*      */ {
/*   38 */   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
/*      */ 
/*      */   
/*      */   public int stackSize;
/*      */ 
/*      */   
/*      */   public int animationsToGo;
/*      */   
/*      */   public Item item;
/*      */   
/*      */   private NBTTagCompound stackTagCompound;
/*      */   
/*      */   private int itemDamage;
/*      */   
/*      */   private EntityItemFrame itemFrame;
/*      */   
/*      */   private Block canDestroyCacheBlock;
/*      */   
/*      */   private boolean canDestroyCacheResult;
/*      */   
/*      */   private Block canPlaceOnCacheBlock;
/*      */   
/*      */   private boolean canPlaceOnCacheResult;
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn) {
/*   64 */     this(blockIn, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn, int amount) {
/*   69 */     this(blockIn, amount, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn, int amount, int meta) {
/*   74 */     this(Item.getItemFromBlock(blockIn), amount, meta);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn) {
/*   79 */     this(itemIn, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn, int amount) {
/*   84 */     this(itemIn, amount, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn, int amount, int meta) {
/*   89 */     this.canDestroyCacheBlock = null;
/*   90 */     this.canDestroyCacheResult = false;
/*   91 */     this.canPlaceOnCacheBlock = null;
/*   92 */     this.canPlaceOnCacheResult = false;
/*   93 */     this.item = itemIn;
/*   94 */     this.stackSize = amount;
/*   95 */     this.itemDamage = meta;
/*      */     
/*   97 */     if (this.itemDamage < 0)
/*      */     {
/*   99 */       this.itemDamage = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
/*  105 */     ItemStack itemstack = new ItemStack();
/*  106 */     itemstack.readFromNBT(nbt);
/*  107 */     return (itemstack.getItem() != null) ? itemstack : null;
/*      */   }
/*      */ 
/*      */   
/*      */   private ItemStack() {
/*  112 */     this.canDestroyCacheBlock = null;
/*  113 */     this.canDestroyCacheResult = false;
/*  114 */     this.canPlaceOnCacheBlock = null;
/*  115 */     this.canPlaceOnCacheResult = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack splitStack(int amount) {
/*  123 */     ItemStack itemstack = new ItemStack(this.item, amount, this.itemDamage);
/*      */     
/*  125 */     if (this.stackTagCompound != null)
/*      */     {
/*  127 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*      */     }
/*      */     
/*  130 */     this.stackSize -= amount;
/*  131 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItem() {
/*  139 */     return this.item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  148 */     boolean flag = getItem().onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*      */     
/*  150 */     if (flag)
/*      */     {
/*  152 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*      */     }
/*      */     
/*  155 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(Block blockIn) {
/*  160 */     return getItem().getStrVsBlock(this, blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack useItemRightClick(World worldIn, EntityPlayer playerIn) {
/*  169 */     return getItem().onItemRightClick(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(World worldIn, EntityPlayer playerIn) {
/*  177 */     return getItem().onItemUseFinish(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/*  185 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
/*  186 */     nbt.setString("id", (resourcelocation == null) ? "minecraft:air" : resourcelocation.toString());
/*  187 */     nbt.setByte("Count", (byte)this.stackSize);
/*  188 */     nbt.setShort("Damage", (short)this.itemDamage);
/*      */     
/*  190 */     if (this.stackTagCompound != null)
/*      */     {
/*  192 */       nbt.setTag("tag", (NBTBase)this.stackTagCompound);
/*      */     }
/*      */     
/*  195 */     return nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound nbt) {
/*  203 */     if (nbt.hasKey("id", 8)) {
/*      */       
/*  205 */       this.item = Item.getByNameOrId(nbt.getString("id"));
/*      */     }
/*      */     else {
/*      */       
/*  209 */       this.item = Item.getItemById(nbt.getShort("id"));
/*      */     } 
/*      */     
/*  212 */     this.stackSize = nbt.getByte("Count");
/*  213 */     this.itemDamage = nbt.getShort("Damage");
/*      */     
/*  215 */     if (this.itemDamage < 0)
/*      */     {
/*  217 */       this.itemDamage = 0;
/*      */     }
/*      */     
/*  220 */     if (nbt.hasKey("tag", 10)) {
/*      */       
/*  222 */       this.stackTagCompound = nbt.getCompoundTag("tag");
/*      */       
/*  224 */       if (this.item != null)
/*      */       {
/*  226 */         this.item.updateItemStackNBT(this.stackTagCompound);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStackSize() {
/*  236 */     return getItem().getItemStackLimit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStackable() {
/*  244 */     return (getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemStackDamageable() {
/*  252 */     return (this.item == null) ? false : ((this.item.getMaxDamage() <= 0) ? false : ((!hasTagCompound() || !getTagCompound().getBoolean("Unbreakable"))));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  257 */     return this.item.getHasSubtypes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemDamaged() {
/*  265 */     return (isItemStackDamageable() && this.itemDamage > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemDamage() {
/*  270 */     return this.itemDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMetadata() {
/*  275 */     return this.itemDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemDamage(int meta) {
/*  280 */     this.itemDamage = meta;
/*      */     
/*  282 */     if (this.itemDamage < 0)
/*      */     {
/*  284 */       this.itemDamage = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  293 */     return this.item.getMaxDamage();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attemptDamageItem(int amount, Random rand) {
/*  304 */     if (!isItemStackDamageable())
/*      */     {
/*  306 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  310 */     if (amount > 0) {
/*      */       
/*  312 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
/*  313 */       int j = 0;
/*      */       
/*  315 */       for (int k = 0; i > 0 && k < amount; k++) {
/*      */         
/*  317 */         if (EnchantmentDurability.negateDamage(this, i, rand))
/*      */         {
/*  319 */           j++;
/*      */         }
/*      */       } 
/*      */       
/*  323 */       amount -= j;
/*      */       
/*  325 */       if (amount <= 0)
/*      */       {
/*  327 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  331 */     this.itemDamage += amount;
/*  332 */     return (this.itemDamage > getMaxDamage());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void damageItem(int amount, EntityLivingBase entityIn) {
/*  341 */     if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).capabilities.isCreativeMode)
/*      */     {
/*  343 */       if (isItemStackDamageable())
/*      */       {
/*  345 */         if (attemptDamageItem(amount, entityIn.getRNG())) {
/*      */           
/*  347 */           entityIn.renderBrokenItemStack(this);
/*  348 */           this.stackSize--;
/*      */           
/*  350 */           if (entityIn instanceof EntityPlayer) {
/*      */             
/*  352 */             EntityPlayer entityplayer = (EntityPlayer)entityIn;
/*  353 */             entityplayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
/*      */             
/*  355 */             if (this.stackSize == 0 && getItem() instanceof ItemBow)
/*      */             {
/*  357 */               entityplayer.destroyCurrentEquippedItem();
/*      */             }
/*      */           } 
/*      */           
/*  361 */           if (this.stackSize < 0)
/*      */           {
/*  363 */             this.stackSize = 0;
/*      */           }
/*      */           
/*  366 */           this.itemDamage = 0;
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
/*  377 */     boolean flag = this.item.hitEntity(this, entityIn, (EntityLivingBase)playerIn);
/*      */     
/*  379 */     if (flag)
/*      */     {
/*  381 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyed(World worldIn, Block blockIn, BlockPos pos, EntityPlayer playerIn) {
/*  392 */     boolean flag = this.item.onBlockDestroyed(this, worldIn, blockIn, pos, (EntityLivingBase)playerIn);
/*      */     
/*  394 */     if (flag)
/*      */     {
/*  396 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockIn) {
/*  405 */     return this.item.canHarvestBlock(blockIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn) {
/*  410 */     return this.item.itemInteractionForEntity(this, playerIn, entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack copy() {
/*  418 */     ItemStack itemstack = new ItemStack(this.item, this.stackSize, this.itemDamage);
/*      */     
/*  420 */     if (this.stackTagCompound != null)
/*      */     {
/*  422 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*      */     }
/*      */     
/*  425 */     return itemstack;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
/*  430 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? ((stackA.stackTagCompound == null && stackB.stackTagCompound != null) ? false : ((stackA.stackTagCompound == null || stackA.stackTagCompound.equals(stackB.stackTagCompound)))) : false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
/*  438 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? stackA.isItemStackEqual(stackB) : false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isItemStackEqual(ItemStack other) {
/*  446 */     return (this.stackSize != other.stackSize) ? false : ((this.item != other.item) ? false : ((this.itemDamage != other.itemDamage) ? false : ((this.stackTagCompound == null && other.stackTagCompound != null) ? false : ((this.stackTagCompound == null || this.stackTagCompound.equals(other.stackTagCompound))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
/*  454 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? stackA.isItemEqual(stackB) : false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEqual(ItemStack other) {
/*  463 */     return (other != null && this.item == other.item && this.itemDamage == other.itemDamage);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  468 */     return this.item.getUnlocalizedName(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ItemStack copyItemStack(ItemStack stack) {
/*  476 */     return (stack == null) ? null : stack.copy();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  481 */     return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
/*  490 */     if (this.animationsToGo > 0)
/*      */     {
/*  492 */       this.animationsToGo--;
/*      */     }
/*      */     
/*  495 */     this.item.onUpdate(this, worldIn, entityIn, inventorySlot, isCurrentItem);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
/*  500 */     playerIn.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], amount);
/*  501 */     this.item.onCreated(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getIsItemStackEqual(ItemStack p_179549_1_) {
/*  506 */     return isItemStackEqual(p_179549_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration() {
/*  511 */     return getItem().getMaxItemUseDuration(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction() {
/*  516 */     return getItem().getItemUseAction(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(World worldIn, EntityPlayer playerIn, int timeLeft) {
/*  526 */     getItem().onPlayerStoppedUsing(this, worldIn, playerIn, timeLeft);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasTagCompound() {
/*  534 */     return (this.stackTagCompound != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getTagCompound() {
/*  542 */     return this.stackTagCompound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getSubCompound(String key, boolean create) {
/*  553 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
/*      */     {
/*  555 */       return this.stackTagCompound.getCompoundTag(key);
/*      */     }
/*  557 */     if (create) {
/*      */       
/*  559 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  560 */       setTagInfo(key, (NBTBase)nbttagcompound);
/*  561 */       return nbttagcompound;
/*      */     } 
/*      */ 
/*      */     
/*  565 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagList getEnchantmentTagList() {
/*  571 */     return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTagCompound(NBTTagCompound nbt) {
/*  579 */     this.stackTagCompound = nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/*  587 */     String s = getItem().getItemStackDisplayName(this);
/*      */     
/*  589 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
/*      */       
/*  591 */       NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*      */       
/*  593 */       if (nbttagcompound.hasKey("Name", 8))
/*      */       {
/*  595 */         s = nbttagcompound.getString("Name");
/*      */       }
/*      */     } 
/*      */     
/*  599 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack setStackDisplayName(String displayName) {
/*  604 */     if (this.stackTagCompound == null)
/*      */     {
/*  606 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/*  609 */     if (!this.stackTagCompound.hasKey("display", 10))
/*      */     {
/*  611 */       this.stackTagCompound.setTag("display", (NBTBase)new NBTTagCompound());
/*      */     }
/*      */     
/*  614 */     this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
/*  615 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearCustomName() {
/*  623 */     if (this.stackTagCompound != null)
/*      */     {
/*  625 */       if (this.stackTagCompound.hasKey("display", 10)) {
/*      */         
/*  627 */         NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*  628 */         nbttagcompound.removeTag("Name");
/*      */         
/*  630 */         if (nbttagcompound.hasNoTags()) {
/*      */           
/*  632 */           this.stackTagCompound.removeTag("display");
/*      */           
/*  634 */           if (this.stackTagCompound.hasNoTags())
/*      */           {
/*  636 */             setTagCompound(null);
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
/*      */   public boolean hasDisplayName() {
/*  648 */     return (this.stackTagCompound == null) ? false : (!this.stackTagCompound.hasKey("display", 10) ? false : this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8));
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTooltip(EntityPlayer playerIn, boolean advanced) {
/*  653 */     List<String> list = Lists.newArrayList();
/*  654 */     String s = getDisplayName();
/*      */     
/*  656 */     if (hasDisplayName())
/*      */     {
/*  658 */       s = ChatFormatting.ITALIC + s;
/*      */     }
/*      */     
/*  661 */     s = s + ChatFormatting.RESET;
/*      */     
/*  663 */     if (advanced) {
/*      */       
/*  665 */       String s1 = "";
/*      */       
/*  667 */       if (s.length() > 0) {
/*      */         
/*  669 */         s = s + " (";
/*  670 */         s1 = ")";
/*      */       } 
/*      */       
/*  673 */       int i = Item.getIdFromItem(this.item);
/*      */       
/*  675 */       if (getHasSubtypes())
/*      */       {
/*  677 */         s = s + String.format("#%04d/%d%s", new Object[] { Integer.valueOf(i), Integer.valueOf(this.itemDamage), s1 });
/*      */       }
/*      */       else
/*      */       {
/*  681 */         s = s + String.format("#%04d%s", new Object[] { Integer.valueOf(i), s1 });
/*      */       }
/*      */     
/*  684 */     } else if (!hasDisplayName() && this.item == Items.filled_map) {
/*      */       
/*  686 */       s = s + " #" + this.itemDamage;
/*      */     } 
/*      */     
/*  689 */     list.add(s);
/*  690 */     int i1 = 0;
/*      */     
/*  692 */     if (hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99))
/*      */     {
/*  694 */       i1 = this.stackTagCompound.getInteger("HideFlags");
/*      */     }
/*      */     
/*  697 */     if ((i1 & 0x20) == 0)
/*      */     {
/*  699 */       this.item.addInformation(this, playerIn, list, advanced);
/*      */     }
/*      */     
/*  702 */     if (hasTagCompound()) {
/*      */       
/*  704 */       if ((i1 & 0x1) == 0) {
/*      */         
/*  706 */         NBTTagList nbttaglist = getEnchantmentTagList();
/*      */         
/*  708 */         if (nbttaglist != null)
/*      */         {
/*  710 */           for (int j = 0; j < nbttaglist.tagCount(); j++) {
/*      */             
/*  712 */             int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/*  713 */             int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*      */             
/*  715 */             if (Enchantment.getEnchantmentById(k) != null)
/*      */             {
/*  717 */               list.add(Enchantment.getEnchantmentById(k).getTranslatedName(l));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  723 */       if (this.stackTagCompound.hasKey("display", 10)) {
/*      */         
/*  725 */         NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*      */         
/*  727 */         if (nbttagcompound.hasKey("color", 3))
/*      */         {
/*  729 */           if (advanced) {
/*      */             
/*  731 */             list.add("Color: #" + Integer.toHexString(nbttagcompound.getInteger("color")).toUpperCase());
/*      */           }
/*      */           else {
/*      */             
/*  735 */             list.add(ChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
/*      */           } 
/*      */         }
/*      */         
/*  739 */         if (nbttagcompound.getTagId("Lore") == 9) {
/*      */           
/*  741 */           NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
/*      */           
/*  743 */           if (nbttaglist1.tagCount() > 0)
/*      */           {
/*  745 */             for (int j1 = 0; j1 < nbttaglist1.tagCount(); j1++)
/*      */             {
/*  747 */               list.add(ChatFormatting.DARK_PURPLE + "" + ChatFormatting.ITALIC + nbttaglist1.getStringTagAt(j1));
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  754 */     Multimap<String, AttributeModifier> multimap = getAttributeModifiers();
/*      */     
/*  756 */     if (!multimap.isEmpty() && (i1 & 0x2) == 0) {
/*      */       
/*  758 */       list.add("");
/*      */       
/*  760 */       for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)multimap.entries()) {
/*      */         double d1;
/*  762 */         AttributeModifier attributemodifier = entry.getValue();
/*  763 */         double d0 = attributemodifier.getAmount();
/*      */         
/*  765 */         if (attributemodifier.getID() == Item.itemModifierUUID)
/*      */         {
/*  767 */           d0 += EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  772 */         if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
/*      */           
/*  774 */           d1 = d0;
/*      */         }
/*      */         else {
/*      */           
/*  778 */           d1 = d0 * 100.0D;
/*      */         } 
/*      */         
/*  781 */         if (d0 > 0.0D) {
/*      */           
/*  783 */           list.add(ChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) })); continue;
/*      */         } 
/*  785 */         if (d0 < 0.0D) {
/*      */           
/*  787 */           d1 *= -1.0D;
/*  788 */           list.add(ChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) }));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  793 */     if (hasTagCompound() && getTagCompound().getBoolean("Unbreakable") && (i1 & 0x4) == 0)
/*      */     {
/*  795 */       list.add(ChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
/*      */     }
/*      */     
/*  798 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (i1 & 0x8) == 0) {
/*      */       
/*  800 */       NBTTagList nbttaglist2 = this.stackTagCompound.getTagList("CanDestroy", 8);
/*      */       
/*  802 */       if (nbttaglist2.tagCount() > 0) {
/*      */         
/*  804 */         list.add("");
/*  805 */         list.add(ChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));
/*      */         
/*  807 */         for (int k1 = 0; k1 < nbttaglist2.tagCount(); k1++) {
/*      */           
/*  809 */           Block block = Block.getBlockFromName(nbttaglist2.getStringTagAt(k1));
/*      */           
/*  811 */           if (block != null) {
/*      */             
/*  813 */             list.add(ChatFormatting.DARK_GRAY + block.getLocalizedName());
/*      */           }
/*      */           else {
/*      */             
/*  817 */             list.add(ChatFormatting.DARK_GRAY + "missingno");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  823 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (i1 & 0x10) == 0) {
/*      */       
/*  825 */       NBTTagList nbttaglist3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*      */       
/*  827 */       if (nbttaglist3.tagCount() > 0) {
/*      */         
/*  829 */         list.add("");
/*  830 */         list.add(ChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));
/*      */         
/*  832 */         for (int l1 = 0; l1 < nbttaglist3.tagCount(); l1++) {
/*      */           
/*  834 */           Block block1 = Block.getBlockFromName(nbttaglist3.getStringTagAt(l1));
/*      */           
/*  836 */           if (block1 != null) {
/*      */             
/*  838 */             list.add(ChatFormatting.DARK_GRAY + block1.getLocalizedName());
/*      */           }
/*      */           else {
/*      */             
/*  842 */             list.add(ChatFormatting.DARK_GRAY + "missingno");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  848 */     if (advanced) {
/*      */       
/*  850 */       if (isItemDamaged())
/*      */       {
/*  852 */         list.add("Durability: " + (getMaxDamage() - getItemDamage()) + " / " + getMaxDamage());
/*      */       }
/*      */       
/*  855 */       list.add(ChatFormatting.DARK_GRAY + ((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
/*      */       
/*  857 */       if (hasTagCompound())
/*      */       {
/*  859 */         list.add(ChatFormatting.DARK_GRAY + "NBT: " + getTagCompound().getKeySet().size() + " tag(s)");
/*      */       }
/*      */     } 
/*      */     
/*  863 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect() {
/*  868 */     return getItem().hasEffect(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity() {
/*  873 */     return getItem().getRarity(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEnchantable() {
/*  881 */     return !getItem().isItemTool(this) ? false : (!isItemEnchanted());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEnchantment(Enchantment ench, int level) {
/*  889 */     if (this.stackTagCompound == null)
/*      */     {
/*  891 */       setTagCompound(new NBTTagCompound());
/*      */     }
/*      */     
/*  894 */     if (!this.stackTagCompound.hasKey("ench", 9))
/*      */     {
/*  896 */       this.stackTagCompound.setTag("ench", (NBTBase)new NBTTagList());
/*      */     }
/*      */     
/*  899 */     NBTTagList nbttaglist = this.stackTagCompound.getTagList("ench", 10);
/*  900 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  901 */     nbttagcompound.setShort("id", (short)ench.effectId);
/*  902 */     nbttagcompound.setShort("lvl", (short)(byte)level);
/*  903 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEnchanted() {
/*  911 */     return (this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTagInfo(String key, NBTBase value) {
/*  916 */     if (this.stackTagCompound == null)
/*      */     {
/*  918 */       setTagCompound(new NBTTagCompound());
/*      */     }
/*      */     
/*  921 */     this.stackTagCompound.setTag(key, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEditBlocks() {
/*  926 */     return getItem().canItemEditBlocks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnItemFrame() {
/*  934 */     return (this.itemFrame != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemFrame(EntityItemFrame frame) {
/*  942 */     this.itemFrame = frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItemFrame getItemFrame() {
/*  950 */     return this.itemFrame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRepairCost() {
/*  958 */     return (hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRepairCost(int cost) {
/*  966 */     if (!hasTagCompound())
/*      */     {
/*  968 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/*  971 */     this.stackTagCompound.setInteger("RepairCost", cost);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getAttributeModifiers() {
/*      */     Multimap<String, AttributeModifier> multimap;
/*  978 */     if (hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
/*      */       
/*  980 */       HashMultimap hashMultimap = HashMultimap.create();
/*  981 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/*      */       
/*  983 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*      */       {
/*  985 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  986 */         AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);
/*      */         
/*  988 */         if (attributemodifier != null && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L)
/*      */         {
/*  990 */           hashMultimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  996 */       multimap = getItem().getItemAttributeModifiers();
/*      */     } 
/*      */     
/*  999 */     return multimap;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItem(Item newItem) {
/* 1004 */     this.item = newItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getChatComponent() {
/* 1012 */     ChatComponentText chatcomponenttext = new ChatComponentText(getDisplayName());
/*      */     
/* 1014 */     if (hasDisplayName())
/*      */     {
/* 1016 */       chatcomponenttext.getChatStyle().setItalic(Boolean.TRUE);
/*      */     }
/*      */     
/* 1019 */     IChatComponent ichatcomponent = (new ChatComponentText("[")).appendSibling((IChatComponent)chatcomponenttext).appendText("]");
/*      */     
/* 1021 */     if (this.item != null) {
/*      */       
/* 1023 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 1024 */       writeToNBT(nbttagcompound);
/* 1025 */       ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, (IChatComponent)new ChatComponentText(nbttagcompound.toString())));
/* 1026 */       ichatcomponent.getChatStyle().setColor((getRarity()).rarityColor);
/*      */     } 
/*      */     
/* 1029 */     return ichatcomponent;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canDestroy(Block blockIn) {
/* 1034 */     if (blockIn == this.canDestroyCacheBlock)
/*      */     {
/* 1036 */       return this.canDestroyCacheResult;
/*      */     }
/*      */ 
/*      */     
/* 1040 */     this.canDestroyCacheBlock = blockIn;
/*      */     
/* 1042 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
/*      */       
/* 1044 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanDestroy", 8);
/*      */       
/* 1046 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1048 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*      */         
/* 1050 */         if (block == blockIn) {
/*      */           
/* 1052 */           this.canDestroyCacheResult = true;
/* 1053 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1058 */     this.canDestroyCacheResult = false;
/* 1059 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceOn(Block blockIn) {
/* 1065 */     if (blockIn == this.canPlaceOnCacheBlock)
/*      */     {
/* 1067 */       return this.canPlaceOnCacheResult;
/*      */     }
/*      */ 
/*      */     
/* 1071 */     this.canPlaceOnCacheBlock = blockIn;
/*      */     
/* 1073 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
/*      */       
/* 1075 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*      */       
/* 1077 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1079 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*      */         
/* 1081 */         if (block == blockIn) {
/*      */           
/* 1083 */           this.canPlaceOnCacheResult = true;
/* 1084 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1089 */     this.canPlaceOnCacheResult = false;
/* 1090 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */