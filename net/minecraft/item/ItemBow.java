/*     */ package net.minecraft.item;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBow extends Item {
/*  14 */   public static final String[] bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
/*     */ 
/*     */   
/*     */   public ItemBow() {
/*  18 */     this.maxStackSize = 1;
/*  19 */     setMaxDamage(384);
/*  20 */     setCreativeTab(CreativeTabs.tabCombat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
/*  30 */     boolean flag = (playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0);
/*     */     
/*  32 */     if (flag || playerIn.inventory.hasItem(Items.arrow)) {
/*     */       
/*  34 */       int i = getMaxItemUseDuration(stack) - timeLeft;
/*  35 */       float f = i / 20.0F;
/*  36 */       f = (f * f + f * 2.0F) / 3.0F;
/*     */       
/*  38 */       if (f < 0.1D) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  43 */       if (f > 1.0F)
/*     */       {
/*  45 */         f = 1.0F;
/*     */       }
/*     */       
/*  48 */       EntityArrow entityarrow = new EntityArrow(worldIn, (EntityLivingBase)playerIn, f * 2.0F);
/*     */       
/*  50 */       if (f == 1.0F)
/*     */       {
/*  52 */         entityarrow.setIsCritical(true);
/*     */       }
/*     */       
/*  55 */       int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
/*     */       
/*  57 */       if (j > 0)
/*     */       {
/*  59 */         entityarrow.setDamage(entityarrow.getDamage() + j * 0.5D + 0.5D);
/*     */       }
/*     */       
/*  62 */       int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
/*     */       
/*  64 */       if (k > 0)
/*     */       {
/*  66 */         entityarrow.setKnockbackStrength(k);
/*     */       }
/*     */       
/*  69 */       if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
/*     */       {
/*  71 */         entityarrow.setFire(100);
/*     */       }
/*     */       
/*  74 */       stack.damageItem(1, (EntityLivingBase)playerIn);
/*  75 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*     */       
/*  77 */       if (flag) {
/*     */         
/*  79 */         entityarrow.canBePickedUp = 2;
/*     */       }
/*     */       else {
/*     */         
/*  83 */         playerIn.inventory.consumeInventoryItem(Items.arrow);
/*     */       } 
/*     */       
/*  86 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */       
/*  88 */       if (!worldIn.isRemote)
/*     */       {
/*  90 */         worldIn.spawnEntityInWorld((Entity)entityarrow);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/* 101 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/* 109 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 117 */     return EnumAction.BOW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 125 */     if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.arrow))
/*     */     {
/* 127 */       playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/*     */     }
/*     */     
/* 130 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 138 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */