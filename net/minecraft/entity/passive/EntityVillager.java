/*      */ package net.minecraft.entity.passive;
/*      */ 
/*      */ import java.util.Random;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentData;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAgeable;
/*      */ import net.minecraft.entity.EntityCreature;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.INpc;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.EntityAIFollowGolem;
/*      */ import net.minecraft.entity.ai.EntityAIHarvestFarmland;
/*      */ import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
/*      */ import net.minecraft.entity.ai.EntityAIMoveIndoors;
/*      */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*      */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*      */ import net.minecraft.entity.ai.EntityAIPlay;
/*      */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*      */ import net.minecraft.entity.ai.EntityAISwimming;
/*      */ import net.minecraft.entity.ai.EntityAITradePlayer;
/*      */ import net.minecraft.entity.ai.EntityAIVillagerInteract;
/*      */ import net.minecraft.entity.ai.EntityAIVillagerMate;
/*      */ import net.minecraft.entity.ai.EntityAIWander;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityWitch;
/*      */ import net.minecraft.entity.monster.EntityZombie;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Tuple;
/*      */ import net.minecraft.village.MerchantRecipe;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.village.Village;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.World;
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
/*      */ public class EntityVillager
/*      */   extends EntityAgeable
/*      */   implements IMerchant, INpc
/*      */ {
/*      */   private int randomTickDivider;
/*      */   private boolean isMating;
/*      */   private boolean isPlaying;
/*      */   Village villageObj;
/*      */   private EntityPlayer buyingPlayer;
/*      */   private MerchantRecipeList buyingList;
/*      */   private int timeUntilReset;
/*      */   private boolean needsInitilization;
/*      */   private boolean isWillingToTrade;
/*      */   private int wealth;
/*      */   private String lastBuyingPlayer;
/*      */   private int careerId;
/*      */   private int careerLevel;
/*      */   private boolean isLookingForHome;
/*      */   private boolean areAdditionalTasksSet;
/*      */   private InventoryBasic villagerInventory;
/*   96 */   private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][] { { { { new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7)) }, { new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds((Item)Items.fishing_rod, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds((Item)Items.shears, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds((Item)Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22)) } } }, { { { new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds((Item)Items.iron_helmet, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds((Item)Items.iron_chestplate, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds((Item)Items.diamond_chestplate, new PriceInfo(16, 19)) }, { new ListItemForEmeralds((Item)Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds((Item)Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.chainmail_chestplate, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.porkchop, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds((Item)Items.leather_leggings, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds((Item)Items.leather_chestplate, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10)) } } } };
/*      */ 
/*      */   
/*      */   public EntityVillager(World worldIn) {
/*  100 */     this(worldIn, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityVillager(World worldIn, int professionId) {
/*  105 */     super(worldIn);
/*  106 */     this.villagerInventory = new InventoryBasic("Items", false, 8);
/*  107 */     setProfession(professionId);
/*  108 */     setSize(0.6F, 1.8F);
/*  109 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*  110 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  111 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  112 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/*  113 */     this.tasks.addTask(1, (EntityAIBase)new EntityAITradePlayer(this));
/*  114 */     this.tasks.addTask(1, (EntityAIBase)new EntityAILookAtTradePlayer(this));
/*  115 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
/*  116 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
/*  117 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
/*  118 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.6D));
/*  119 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIVillagerMate(this));
/*  120 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIFollowGolem(this));
/*  121 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*  122 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIVillagerInteract(this));
/*  123 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
/*  124 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*  125 */     setCanPickUpLoot(true);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setAdditionalAItasks() {
/*  130 */     if (!this.areAdditionalTasksSet) {
/*      */       
/*  132 */       this.areAdditionalTasksSet = true;
/*      */       
/*  134 */       if (isChild()) {
/*      */         
/*  136 */         this.tasks.addTask(8, (EntityAIBase)new EntityAIPlay(this, 0.32D));
/*      */       }
/*  138 */       else if (getProfession() == 0) {
/*      */         
/*  140 */         this.tasks.addTask(6, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onGrowingAdult() {
/*  151 */     if (getProfession() == 0)
/*      */     {
/*  153 */       this.tasks.addTask(8, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*      */     }
/*      */     
/*  156 */     super.onGrowingAdult();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  161 */     super.applyEntityAttributes();
/*  162 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {
/*  167 */     if (--this.randomTickDivider <= 0) {
/*      */       
/*  169 */       BlockPos blockpos = new BlockPos((Entity)this);
/*  170 */       this.worldObj.getVillageCollection().addToVillagerPositionList(blockpos);
/*  171 */       this.randomTickDivider = 70 + this.rand.nextInt(50);
/*  172 */       this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(blockpos, 32);
/*      */       
/*  174 */       if (this.villageObj == null) {
/*      */         
/*  176 */         detachHome();
/*      */       }
/*      */       else {
/*      */         
/*  180 */         BlockPos blockpos1 = this.villageObj.getCenter();
/*  181 */         setHomePosAndDistance(blockpos1, (int)(this.villageObj.getVillageRadius() * 1.0F));
/*      */         
/*  183 */         if (this.isLookingForHome) {
/*      */           
/*  185 */           this.isLookingForHome = false;
/*  186 */           this.villageObj.setDefaultPlayerReputation(5);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  191 */     if (!isTrading() && this.timeUntilReset > 0) {
/*      */       
/*  193 */       this.timeUntilReset--;
/*      */       
/*  195 */       if (this.timeUntilReset <= 0) {
/*      */         
/*  197 */         if (this.needsInitilization) {
/*      */           
/*  199 */           for (MerchantRecipe merchantrecipe : this.buyingList) {
/*      */             
/*  201 */             if (merchantrecipe.isRecipeDisabled())
/*      */             {
/*  203 */               merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
/*      */             }
/*      */           } 
/*      */           
/*  207 */           populateBuyingList();
/*  208 */           this.needsInitilization = false;
/*      */           
/*  210 */           if (this.villageObj != null && this.lastBuyingPlayer != null) {
/*      */             
/*  212 */             this.worldObj.setEntityState((Entity)this, (byte)14);
/*  213 */             this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
/*      */           } 
/*      */         } 
/*      */         
/*  217 */         addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
/*      */       } 
/*      */     } 
/*      */     
/*  221 */     super.updateAITasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interact(EntityPlayer player) {
/*  229 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*  230 */     boolean flag = (itemstack != null && itemstack.getItem() == Items.spawn_egg);
/*      */     
/*  232 */     if (!flag && isEntityAlive() && !isTrading() && !isChild()) {
/*      */       
/*  234 */       if (!this.worldObj.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
/*      */         
/*  236 */         setCustomer(player);
/*  237 */         player.displayVillagerTradeGui(this);
/*      */       } 
/*      */       
/*  240 */       player.triggerAchievement(StatList.timesTalkedToVillagerStat);
/*  241 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  245 */     return super.interact(player);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  251 */     super.entityInit();
/*  252 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  260 */     super.writeEntityToNBT(tagCompound);
/*  261 */     tagCompound.setInteger("Profession", getProfession());
/*  262 */     tagCompound.setInteger("Riches", this.wealth);
/*  263 */     tagCompound.setInteger("Career", this.careerId);
/*  264 */     tagCompound.setInteger("CareerLevel", this.careerLevel);
/*  265 */     tagCompound.setBoolean("Willing", this.isWillingToTrade);
/*      */     
/*  267 */     if (this.buyingList != null)
/*      */     {
/*  269 */       tagCompound.setTag("Offers", (NBTBase)this.buyingList.getRecipiesAsTags());
/*      */     }
/*      */     
/*  272 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  274 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  276 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  278 */       if (itemstack != null)
/*      */       {
/*  280 */         nbttaglist.appendTag((NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*      */       }
/*      */     } 
/*      */     
/*  284 */     tagCompound.setTag("Inventory", (NBTBase)nbttaglist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  292 */     super.readEntityFromNBT(tagCompund);
/*  293 */     setProfession(tagCompund.getInteger("Profession"));
/*  294 */     this.wealth = tagCompund.getInteger("Riches");
/*  295 */     this.careerId = tagCompund.getInteger("Career");
/*  296 */     this.careerLevel = tagCompund.getInteger("CareerLevel");
/*  297 */     this.isWillingToTrade = tagCompund.getBoolean("Willing");
/*      */     
/*  299 */     if (tagCompund.hasKey("Offers", 10)) {
/*      */       
/*  301 */       NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Offers");
/*  302 */       this.buyingList = new MerchantRecipeList(nbttagcompound);
/*      */     } 
/*      */     
/*  305 */     NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);
/*      */     
/*  307 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */       
/*  309 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*      */       
/*  311 */       if (itemstack != null)
/*      */       {
/*  313 */         this.villagerInventory.func_174894_a(itemstack);
/*      */       }
/*      */     } 
/*      */     
/*  317 */     setCanPickUpLoot(true);
/*  318 */     setAdditionalAItasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  326 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  334 */     return isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  342 */     return "mob.villager.hit";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  350 */     return "mob.villager.death";
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfession(int professionId) {
/*  355 */     this.dataWatcher.updateObject(16, Integer.valueOf(professionId));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getProfession() {
/*  360 */     return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMating() {
/*  365 */     return this.isMating;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMating(boolean mating) {
/*  370 */     this.isMating = mating;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlaying(boolean playing) {
/*  375 */     this.isPlaying = playing;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPlaying() {
/*  380 */     return this.isPlaying;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  385 */     super.setRevengeTarget(livingBase);
/*      */     
/*  387 */     if (this.villageObj != null && livingBase != null) {
/*      */       
/*  389 */       this.villageObj.addOrRenewAgressor(livingBase);
/*      */       
/*  391 */       if (livingBase instanceof EntityPlayer) {
/*      */         
/*  393 */         int i = -1;
/*      */         
/*  395 */         if (isChild())
/*      */         {
/*  397 */           i = -3;
/*      */         }
/*      */         
/*  400 */         this.villageObj.setReputationForPlayer(livingBase.getCommandSenderName(), i);
/*      */         
/*  402 */         if (isEntityAlive())
/*      */         {
/*  404 */           this.worldObj.setEntityState((Entity)this, (byte)13);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  415 */     if (this.villageObj != null) {
/*      */       
/*  417 */       Entity entity = cause.getEntity();
/*      */       
/*  419 */       if (entity != null) {
/*      */         
/*  421 */         if (entity instanceof EntityPlayer)
/*      */         {
/*  423 */           this.villageObj.setReputationForPlayer(entity.getCommandSenderName(), -2);
/*      */         }
/*  425 */         else if (entity instanceof net.minecraft.entity.monster.IMob)
/*      */         {
/*  427 */           this.villageObj.endMatingSeason();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  432 */         EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 16.0D);
/*      */         
/*  434 */         if (entityplayer != null)
/*      */         {
/*  436 */           this.villageObj.endMatingSeason();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  441 */     super.onDeath(cause);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCustomer(EntityPlayer p_70932_1_) {
/*  446 */     this.buyingPlayer = p_70932_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer getCustomer() {
/*  451 */     return this.buyingPlayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTrading() {
/*  456 */     return (this.buyingPlayer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsWillingToTrade(boolean updateFirst) {
/*  466 */     if (!this.isWillingToTrade && updateFirst && func_175553_cp()) {
/*      */       
/*  468 */       boolean flag = false;
/*      */       
/*  470 */       for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */         
/*  472 */         ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */         
/*  474 */         if (itemstack != null)
/*      */         {
/*  476 */           if (itemstack.getItem() == Items.bread && itemstack.stackSize >= 3) {
/*      */             
/*  478 */             flag = true;
/*  479 */             this.villagerInventory.decrStackSize(i, 3);
/*      */           }
/*  481 */           else if ((itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot) && itemstack.stackSize >= 12) {
/*      */             
/*  483 */             flag = true;
/*  484 */             this.villagerInventory.decrStackSize(i, 12);
/*      */           } 
/*      */         }
/*      */         
/*  488 */         if (flag) {
/*      */           
/*  490 */           this.worldObj.setEntityState((Entity)this, (byte)18);
/*  491 */           this.isWillingToTrade = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  497 */     return this.isWillingToTrade;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setIsWillingToTrade(boolean willingToTrade) {
/*  502 */     this.isWillingToTrade = willingToTrade;
/*      */   }
/*      */ 
/*      */   
/*      */   public void useRecipe(MerchantRecipe recipe) {
/*  507 */     recipe.incrementToolUses();
/*  508 */     this.livingSoundTime = -getTalkInterval();
/*  509 */     playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/*  510 */     int i = 3 + this.rand.nextInt(4);
/*      */     
/*  512 */     if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
/*      */       
/*  514 */       this.timeUntilReset = 40;
/*  515 */       this.needsInitilization = true;
/*  516 */       this.isWillingToTrade = true;
/*      */       
/*  518 */       if (this.buyingPlayer != null) {
/*      */         
/*  520 */         this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
/*      */       }
/*      */       else {
/*      */         
/*  524 */         this.lastBuyingPlayer = null;
/*      */       } 
/*      */       
/*  527 */       i += 5;
/*      */     } 
/*      */     
/*  530 */     if (recipe.getItemToBuy().getItem() == Items.emerald)
/*      */     {
/*  532 */       this.wealth += (recipe.getItemToBuy()).stackSize;
/*      */     }
/*      */     
/*  535 */     if (recipe.getRewardsExp())
/*      */     {
/*  537 */       this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5D, this.posZ, i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void verifySellingItem(ItemStack stack) {
/*  547 */     if (!this.worldObj.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
/*      */       
/*  549 */       this.livingSoundTime = -getTalkInterval();
/*      */       
/*  551 */       if (stack != null) {
/*      */         
/*  553 */         playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/*      */       }
/*      */       else {
/*      */         
/*  557 */         playSound("mob.villager.no", getSoundVolume(), getSoundPitch());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
/*  564 */     if (this.buyingList == null)
/*      */     {
/*  566 */       populateBuyingList();
/*      */     }
/*      */     
/*  569 */     return this.buyingList;
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateBuyingList() {
/*  574 */     ITradeList[][][] aentityvillager$itradelist = DEFAULT_TRADE_LIST_MAP[getProfession()];
/*      */     
/*  576 */     if (this.careerId != 0 && this.careerLevel != 0) {
/*      */       
/*  578 */       this.careerLevel++;
/*      */     }
/*      */     else {
/*      */       
/*  582 */       this.careerId = this.rand.nextInt(aentityvillager$itradelist.length) + 1;
/*  583 */       this.careerLevel = 1;
/*      */     } 
/*      */     
/*  586 */     if (this.buyingList == null)
/*      */     {
/*  588 */       this.buyingList = new MerchantRecipeList();
/*      */     }
/*      */     
/*  591 */     int i = this.careerId - 1;
/*  592 */     int j = this.careerLevel - 1;
/*  593 */     ITradeList[][] aentityvillager$itradelist1 = aentityvillager$itradelist[i];
/*      */     
/*  595 */     if (j >= 0 && j < aentityvillager$itradelist1.length) {
/*      */       
/*  597 */       ITradeList[] aentityvillager$itradelist2 = aentityvillager$itradelist1[j];
/*      */       
/*  599 */       for (ITradeList entityvillager$itradelist : aentityvillager$itradelist2)
/*      */       {
/*  601 */         entityvillager$itradelist.modifyMerchantRecipeList(this.buyingList, this.rand);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRecipes(MerchantRecipeList recipeList) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/*  615 */     String s = getCustomNameTag();
/*      */     
/*  617 */     if (s != null && s.length() > 0) {
/*      */       
/*  619 */       ChatComponentText chatcomponenttext = new ChatComponentText(s);
/*  620 */       chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/*  621 */       chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/*  622 */       return (IChatComponent)chatcomponenttext;
/*      */     } 
/*      */ 
/*      */     
/*  626 */     if (this.buyingList == null)
/*      */     {
/*  628 */       populateBuyingList();
/*      */     }
/*      */     
/*  631 */     String s1 = null;
/*      */     
/*  633 */     switch (getProfession()) {
/*      */       
/*      */       case 0:
/*  636 */         if (this.careerId == 1) {
/*      */           
/*  638 */           s1 = "farmer"; break;
/*      */         } 
/*  640 */         if (this.careerId == 2) {
/*      */           
/*  642 */           s1 = "fisherman"; break;
/*      */         } 
/*  644 */         if (this.careerId == 3) {
/*      */           
/*  646 */           s1 = "shepherd"; break;
/*      */         } 
/*  648 */         if (this.careerId == 4)
/*      */         {
/*  650 */           s1 = "fletcher";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  656 */         s1 = "librarian";
/*      */         break;
/*      */       
/*      */       case 2:
/*  660 */         s1 = "cleric";
/*      */         break;
/*      */       
/*      */       case 3:
/*  664 */         if (this.careerId == 1) {
/*      */           
/*  666 */           s1 = "armor"; break;
/*      */         } 
/*  668 */         if (this.careerId == 2) {
/*      */           
/*  670 */           s1 = "weapon"; break;
/*      */         } 
/*  672 */         if (this.careerId == 3)
/*      */         {
/*  674 */           s1 = "tool";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  680 */         if (this.careerId == 1) {
/*      */           
/*  682 */           s1 = "butcher"; break;
/*      */         } 
/*  684 */         if (this.careerId == 2)
/*      */         {
/*  686 */           s1 = "leather";
/*      */         }
/*      */         break;
/*      */     } 
/*  690 */     if (s1 != null) {
/*      */       
/*  692 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("entity.Villager." + s1, new Object[0]);
/*  693 */       chatcomponenttranslation.getChatStyle().setChatHoverEvent(getHoverEvent());
/*  694 */       chatcomponenttranslation.getChatStyle().setInsertion(getUniqueID().toString());
/*  695 */       return (IChatComponent)chatcomponenttranslation;
/*      */     } 
/*      */ 
/*      */     
/*  699 */     return super.getDisplayName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/*  706 */     float f = 1.62F;
/*      */     
/*  708 */     if (isChild())
/*      */     {
/*  710 */       f = (float)(f - 0.81D);
/*      */     }
/*      */     
/*  713 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleHealthUpdate(byte id) {
/*  718 */     if (id == 12) {
/*      */       
/*  720 */       spawnParticles(EnumParticleTypes.HEART);
/*      */     }
/*  722 */     else if (id == 13) {
/*      */       
/*  724 */       spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
/*      */     }
/*  726 */     else if (id == 14) {
/*      */       
/*  728 */       spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
/*      */     }
/*      */     else {
/*      */       
/*  732 */       super.handleHealthUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticles(EnumParticleTypes particleType) {
/*  738 */     for (int i = 0; i < 5; i++) {
/*      */       
/*  740 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  741 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  742 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  743 */       this.worldObj.spawnParticle(particleType, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 1.0D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/*  753 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*  754 */     setProfession(this.worldObj.rand.nextInt(5));
/*  755 */     setAdditionalAItasks();
/*  756 */     return livingdata;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLookingForHome() {
/*  761 */     this.isLookingForHome = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityVillager createChild(EntityAgeable ageable) {
/*  766 */     EntityVillager entityvillager = new EntityVillager(this.worldObj);
/*  767 */     entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
/*  768 */     return entityvillager;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean allowLeashing() {
/*  773 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/*  781 */     if (!this.worldObj.isRemote && !this.isDead) {
/*      */       
/*  783 */       EntityWitch entitywitch = new EntityWitch(this.worldObj);
/*  784 */       entitywitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  785 */       entitywitch.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entitywitch)), null);
/*  786 */       entitywitch.setNoAI(isAIDisabled());
/*      */       
/*  788 */       if (hasCustomName()) {
/*      */         
/*  790 */         entitywitch.setCustomNameTag(getCustomNameTag());
/*  791 */         entitywitch.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/*  794 */       this.worldObj.spawnEntityInWorld((Entity)entitywitch);
/*  795 */       setDead();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public InventoryBasic getVillagerInventory() {
/*  801 */     return this.villagerInventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  810 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  811 */     Item item = itemstack.getItem();
/*      */     
/*  813 */     if (canVillagerPickupItem(item)) {
/*      */       
/*  815 */       ItemStack itemstack1 = this.villagerInventory.func_174894_a(itemstack);
/*      */       
/*  817 */       if (itemstack1 == null) {
/*      */         
/*  819 */         itemEntity.setDead();
/*      */       }
/*      */       else {
/*      */         
/*  823 */         itemstack.stackSize = itemstack1.stackSize;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canVillagerPickupItem(Item itemIn) {
/*  830 */     return (itemIn == Items.bread || itemIn == Items.potato || itemIn == Items.carrot || itemIn == Items.wheat || itemIn == Items.wheat_seeds);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_175553_cp() {
/*  835 */     return hasEnoughItems(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAbondonItems() {
/*  844 */     return hasEnoughItems(2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_175557_cr() {
/*  849 */     boolean flag = (getProfession() == 0);
/*  850 */     return flag ? (!hasEnoughItems(5)) : (!hasEnoughItems(1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasEnoughItems(int multiplier) {
/*  860 */     boolean flag = (getProfession() == 0);
/*      */     
/*  862 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  864 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  866 */       if (itemstack != null) {
/*      */         
/*  868 */         if ((itemstack.getItem() == Items.bread && itemstack.stackSize >= 3 * multiplier) || (itemstack.getItem() == Items.potato && itemstack.stackSize >= 12 * multiplier) || (itemstack.getItem() == Items.carrot && itemstack.stackSize >= 12 * multiplier))
/*      */         {
/*  870 */           return true;
/*      */         }
/*      */         
/*  873 */         if (flag && itemstack.getItem() == Items.wheat && itemstack.stackSize >= 9 * multiplier)
/*      */         {
/*  875 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  880 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFarmItemInInventory() {
/*  888 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  890 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  892 */       if (itemstack != null && (itemstack.getItem() == Items.wheat_seeds || itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot))
/*      */       {
/*  894 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  898 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*  903 */     if (super.replaceItemInInventory(inventorySlot, itemStackIn))
/*      */     {
/*  905 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  909 */     int i = inventorySlot - 300;
/*      */     
/*  911 */     if (i >= 0 && i < this.villagerInventory.getSizeInventory()) {
/*      */       
/*  913 */       this.villagerInventory.setInventorySlotContents(i, itemStackIn);
/*  914 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  918 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static class EmeraldForItems
/*      */     implements ITradeList
/*      */   {
/*      */     public Item sellItem;
/*      */     
/*      */     public EntityVillager.PriceInfo price;
/*      */     
/*      */     public EmeraldForItems(Item itemIn, EntityVillager.PriceInfo priceIn) {
/*  930 */       this.sellItem = itemIn;
/*  931 */       this.price = priceIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  936 */       int i = 1;
/*      */       
/*  938 */       if (this.price != null)
/*      */       {
/*  940 */         i = this.price.getPrice(random);
/*      */       }
/*      */       
/*  943 */       recipeList.add(new MerchantRecipe(new ItemStack(this.sellItem, i, 0), Items.emerald));
/*      */     }
/*      */   }
/*      */   
/*      */   static interface ITradeList
/*      */   {
/*      */     void modifyMerchantRecipeList(MerchantRecipeList param1MerchantRecipeList, Random param1Random);
/*      */   }
/*      */   
/*      */   static class ItemAndEmeraldToItem
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack field_179411_a;
/*      */     public EntityVillager.PriceInfo field_179409_b;
/*      */     public ItemStack field_179410_c;
/*      */     public EntityVillager.PriceInfo field_179408_d;
/*      */     
/*      */     public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_) {
/*  961 */       this.field_179411_a = new ItemStack(p_i45813_1_);
/*  962 */       this.field_179409_b = p_i45813_2_;
/*  963 */       this.field_179410_c = new ItemStack(p_i45813_3_);
/*  964 */       this.field_179408_d = p_i45813_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  969 */       int i = 1;
/*      */       
/*  971 */       if (this.field_179409_b != null)
/*      */       {
/*  973 */         i = this.field_179409_b.getPrice(random);
/*      */       }
/*      */       
/*  976 */       int j = 1;
/*      */       
/*  978 */       if (this.field_179408_d != null)
/*      */       {
/*  980 */         j = this.field_179408_d.getPrice(random);
/*      */       }
/*      */       
/*  983 */       recipeList.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), i, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), j, this.field_179410_c.getMetadata())));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListEnchantedBookForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  991 */       Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
/*  992 */       int i = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
/*  993 */       ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i));
/*  994 */       int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
/*      */       
/*  996 */       if (j > 64)
/*      */       {
/*  998 */         j = 64;
/*      */       }
/*      */       
/* 1001 */       recipeList.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, j), itemstack));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListEnchantedItemForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack field_179407_a;
/*      */     public EntityVillager.PriceInfo field_179406_b;
/*      */     
/*      */     public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_) {
/* 1012 */       this.field_179407_a = new ItemStack(p_i45814_1_);
/* 1013 */       this.field_179406_b = p_i45814_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/* 1018 */       int i = 1;
/*      */       
/* 1020 */       if (this.field_179406_b != null)
/*      */       {
/* 1022 */         i = this.field_179406_b.getPrice(random);
/*      */       }
/*      */       
/* 1025 */       ItemStack itemstack = new ItemStack(Items.emerald, i, 0);
/* 1026 */       ItemStack itemstack1 = new ItemStack(this.field_179407_a.getItem(), 1, this.field_179407_a.getMetadata());
/* 1027 */       itemstack1 = EnchantmentHelper.addRandomEnchantment(random, itemstack1, 5 + random.nextInt(15));
/* 1028 */       recipeList.add(new MerchantRecipe(itemstack, itemstack1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListItemForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack field_179403_a;
/*      */     public EntityVillager.PriceInfo field_179402_b;
/*      */     
/*      */     public ListItemForEmeralds(Item par1Item, EntityVillager.PriceInfo priceInfo) {
/* 1039 */       this.field_179403_a = new ItemStack(par1Item);
/* 1040 */       this.field_179402_b = priceInfo;
/*      */     }
/*      */ 
/*      */     
/*      */     public ListItemForEmeralds(ItemStack stack, EntityVillager.PriceInfo priceInfo) {
/* 1045 */       this.field_179403_a = stack;
/* 1046 */       this.field_179402_b = priceInfo;
/*      */     }
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*      */       ItemStack itemstack, itemstack1;
/* 1051 */       int i = 1;
/*      */       
/* 1053 */       if (this.field_179402_b != null)
/*      */       {
/* 1055 */         i = this.field_179402_b.getPrice(random);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1061 */       if (i < 0) {
/*      */         
/* 1063 */         itemstack = new ItemStack(Items.emerald, 1, 0);
/* 1064 */         itemstack1 = new ItemStack(this.field_179403_a.getItem(), -i, this.field_179403_a.getMetadata());
/*      */       }
/*      */       else {
/*      */         
/* 1068 */         itemstack = new ItemStack(Items.emerald, i, 0);
/* 1069 */         itemstack1 = new ItemStack(this.field_179403_a.getItem(), 1, this.field_179403_a.getMetadata());
/*      */       } 
/*      */       
/* 1072 */       recipeList.add(new MerchantRecipe(itemstack, itemstack1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class PriceInfo
/*      */     extends Tuple<Integer, Integer>
/*      */   {
/*      */     public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
/* 1080 */       super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getPrice(Random rand) {
/* 1085 */       return (((Integer)getFirst()).intValue() >= ((Integer)getSecond()).intValue()) ? ((Integer)getFirst()).intValue() : (((Integer)getFirst()).intValue() + rand.nextInt(((Integer)getSecond()).intValue() - ((Integer)getFirst()).intValue() + 1));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\passive\EntityVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */