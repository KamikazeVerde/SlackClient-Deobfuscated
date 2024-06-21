/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemPotion
/*     */   extends Item {
/*  29 */   private Map<Integer, List<PotionEffect>> effectCache = Maps.newHashMap();
/*  30 */   private static final Map<List<PotionEffect>, Integer> SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
/*     */ 
/*     */   
/*     */   public ItemPotion() {
/*  34 */     setMaxStackSize(1);
/*  35 */     setHasSubtypes(true);
/*  36 */     setMaxDamage(0);
/*  37 */     setCreativeTab(CreativeTabs.tabBrewing);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PotionEffect> getEffects(ItemStack stack) {
/*  42 */     if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
/*     */       
/*  44 */       List<PotionEffect> list1 = Lists.newArrayList();
/*  45 */       NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);
/*     */       
/*  47 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  49 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  50 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*     */         
/*  52 */         if (potioneffect != null)
/*     */         {
/*  54 */           list1.add(potioneffect);
/*     */         }
/*     */       } 
/*     */       
/*  58 */       return list1;
/*     */     } 
/*     */ 
/*     */     
/*  62 */     List<PotionEffect> list = this.effectCache.get(Integer.valueOf(stack.getMetadata()));
/*     */     
/*  64 */     if (list == null) {
/*     */       
/*  66 */       list = PotionHelper.getPotionEffects(stack.getMetadata(), false);
/*  67 */       this.effectCache.put(Integer.valueOf(stack.getMetadata()), list);
/*     */     } 
/*     */     
/*  70 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PotionEffect> getEffects(int meta) {
/*  76 */     List<PotionEffect> list = this.effectCache.get(Integer.valueOf(meta));
/*     */     
/*  78 */     if (list == null) {
/*     */       
/*  80 */       list = PotionHelper.getPotionEffects(meta, false);
/*  81 */       this.effectCache.put(Integer.valueOf(meta), list);
/*     */     } 
/*     */     
/*  84 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  93 */     if (!playerIn.capabilities.isCreativeMode)
/*     */     {
/*  95 */       stack.stackSize--;
/*     */     }
/*     */     
/*  98 */     if (!worldIn.isRemote) {
/*     */       
/* 100 */       List<PotionEffect> list = getEffects(stack);
/*     */       
/* 102 */       if (list != null)
/*     */       {
/* 104 */         for (PotionEffect potioneffect : list)
/*     */         {
/* 106 */           playerIn.addPotionEffect(new PotionEffect(potioneffect));
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 111 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */     
/* 113 */     if (!playerIn.capabilities.isCreativeMode) {
/*     */       
/* 115 */       if (stack.stackSize <= 0)
/*     */       {
/* 117 */         return new ItemStack(Items.glass_bottle);
/*     */       }
/*     */       
/* 120 */       playerIn.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
/*     */     } 
/*     */     
/* 123 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/* 131 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 139 */     return EnumAction.DRINK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 147 */     if (isSplash(itemStackIn.getMetadata())) {
/*     */       
/* 149 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 151 */         itemStackIn.stackSize--;
/*     */       }
/*     */       
/* 154 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*     */       
/* 156 */       if (!worldIn.isRemote)
/*     */       {
/* 158 */         worldIn.spawnEntityInWorld((Entity)new EntityPotion(worldIn, (EntityLivingBase)playerIn, itemStackIn));
/*     */       }
/*     */       
/* 161 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 162 */       return itemStackIn;
/*     */     } 
/*     */ 
/*     */     
/* 166 */     playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/* 167 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSplash(int meta) {
/* 176 */     return ((meta & 0x4000) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromDamage(int meta) {
/* 181 */     return PotionHelper.getLiquidColor(meta, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 186 */     return (renderPass > 0) ? 16777215 : getColorFromDamage(stack.getMetadata());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEffectInstant(int meta) {
/* 191 */     List<PotionEffect> list = getEffects(meta);
/*     */     
/* 193 */     if (list != null && !list.isEmpty()) {
/*     */       
/* 195 */       for (PotionEffect potioneffect : list) {
/*     */         
/* 197 */         if (Potion.potionTypes[potioneffect.getPotionID()].isInstant())
/*     */         {
/* 199 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 203 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 213 */     if (stack.getMetadata() == 0)
/*     */     {
/* 215 */       return StatCollector.translateToLocal("item.emptyPotion.name").trim();
/*     */     }
/*     */ 
/*     */     
/* 219 */     String s = "";
/*     */     
/* 221 */     if (isSplash(stack.getMetadata()))
/*     */     {
/* 223 */       s = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
/*     */     }
/*     */     
/* 226 */     List<PotionEffect> list = Items.potionitem.getEffects(stack);
/*     */     
/* 228 */     if (list != null && !list.isEmpty()) {
/*     */       
/* 230 */       String s2 = ((PotionEffect)list.get(0)).getEffectName();
/* 231 */       s2 = s2 + ".postfix";
/* 232 */       return s + StatCollector.translateToLocal(s2).trim();
/*     */     } 
/*     */ 
/*     */     
/* 236 */     String s1 = PotionHelper.getPotionPrefix(stack.getMetadata());
/* 237 */     return StatCollector.translateToLocal(s1).trim() + " " + super.getItemStackDisplayName(stack);
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
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 250 */     if (stack.getMetadata() != 0) {
/*     */       
/* 252 */       List<PotionEffect> list = Items.potionitem.getEffects(stack);
/* 253 */       HashMultimap hashMultimap = HashMultimap.create();
/*     */       
/* 255 */       if (list != null && !list.isEmpty()) {
/*     */         
/* 257 */         for (PotionEffect potioneffect : list)
/*     */         {
/* 259 */           String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
/* 260 */           Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
/* 261 */           Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();
/*     */           
/* 263 */           if (map != null && map.size() > 0)
/*     */           {
/* 265 */             for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
/*     */               
/* 267 */               AttributeModifier attributemodifier = entry.getValue();
/* 268 */               AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
/* 269 */               hashMultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
/*     */             } 
/*     */           }
/*     */           
/* 273 */           if (potioneffect.getAmplifier() > 0)
/*     */           {
/* 275 */             s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
/*     */           }
/*     */           
/* 278 */           if (potioneffect.getDuration() > 20)
/*     */           {
/* 280 */             s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
/*     */           }
/*     */           
/* 283 */           if (potion.isBadEffect()) {
/*     */             
/* 285 */             tooltip.add(ChatFormatting.RED + s1);
/*     */             
/*     */             continue;
/*     */           } 
/* 289 */           tooltip.add(ChatFormatting.GRAY + s1);
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 295 */         String s = StatCollector.translateToLocal("potion.empty").trim();
/* 296 */         tooltip.add(ChatFormatting.GRAY + s);
/*     */       } 
/*     */       
/* 299 */       if (!hashMultimap.isEmpty()) {
/*     */         
/* 301 */         tooltip.add("");
/* 302 */         tooltip.add(ChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
/*     */         
/* 304 */         for (Map.Entry<String, AttributeModifier> entry1 : (Iterable<Map.Entry<String, AttributeModifier>>)hashMultimap.entries()) {
/*     */           double d1;
/* 306 */           AttributeModifier attributemodifier2 = entry1.getValue();
/* 307 */           double d0 = attributemodifier2.getAmount();
/*     */ 
/*     */           
/* 310 */           if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2) {
/*     */             
/* 312 */             d1 = attributemodifier2.getAmount();
/*     */           }
/*     */           else {
/*     */             
/* 316 */             d1 = attributemodifier2.getAmount() * 100.0D;
/*     */           } 
/*     */           
/* 319 */           if (d0 > 0.0D) {
/*     */             
/* 321 */             tooltip.add(ChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey()) })); continue;
/*     */           } 
/* 323 */           if (d0 < 0.0D) {
/*     */             
/* 325 */             d1 *= -1.0D;
/* 326 */             tooltip.add(ChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey()) }));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 335 */     List<PotionEffect> list = getEffects(stack);
/* 336 */     return (list != null && !list.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 346 */     super.getSubItems(itemIn, tab, subItems);
/*     */     
/* 348 */     if (SUB_ITEMS_CACHE.isEmpty())
/*     */     {
/* 350 */       for (int i = 0; i <= 15; i++) {
/*     */         
/* 352 */         for (int j = 0; j <= 1; j++) {
/*     */           int lvt_6_1_;
/*     */ 
/*     */           
/* 356 */           if (j == 0) {
/*     */             
/* 358 */             lvt_6_1_ = i | 0x2000;
/*     */           }
/*     */           else {
/*     */             
/* 362 */             lvt_6_1_ = i | 0x4000;
/*     */           } 
/*     */           
/* 365 */           for (int l = 0; l <= 2; l++) {
/*     */             
/* 367 */             int i1 = lvt_6_1_;
/*     */             
/* 369 */             if (l != 0)
/*     */             {
/* 371 */               if (l == 1) {
/*     */                 
/* 373 */                 i1 = lvt_6_1_ | 0x20;
/*     */               }
/* 375 */               else if (l == 2) {
/*     */                 
/* 377 */                 i1 = lvt_6_1_ | 0x40;
/*     */               } 
/*     */             }
/*     */             
/* 381 */             List<PotionEffect> list = PotionHelper.getPotionEffects(i1, false);
/*     */             
/* 383 */             if (list != null && !list.isEmpty())
/*     */             {
/* 385 */               SUB_ITEMS_CACHE.put(list, Integer.valueOf(i1));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 392 */     Iterator<Integer> iterator = SUB_ITEMS_CACHE.values().iterator();
/*     */     
/* 394 */     while (iterator.hasNext()) {
/*     */       
/* 396 */       int j1 = ((Integer)iterator.next()).intValue();
/* 397 */       subItems.add(new ItemStack(itemIn, 1, j1));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */