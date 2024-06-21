/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ public class ItemFireworkCharge
/*     */   extends Item
/*     */ {
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  14 */     if (renderPass != 1)
/*     */     {
/*  16 */       return super.getColorFromItemStack(stack, renderPass);
/*     */     }
/*     */ 
/*     */     
/*  20 */     NBTBase nbtbase = getExplosionTag(stack, "Colors");
/*     */     
/*  22 */     if (!(nbtbase instanceof NBTTagIntArray))
/*     */     {
/*  24 */       return 9079434;
/*     */     }
/*     */ 
/*     */     
/*  28 */     NBTTagIntArray nbttagintarray = (NBTTagIntArray)nbtbase;
/*  29 */     int[] aint = nbttagintarray.getIntArray();
/*     */     
/*  31 */     if (aint.length == 1)
/*     */     {
/*  33 */       return aint[0];
/*     */     }
/*     */ 
/*     */     
/*  37 */     int i = 0;
/*  38 */     int j = 0;
/*  39 */     int k = 0;
/*     */     
/*  41 */     for (int l : aint) {
/*     */       
/*  43 */       i += (l & 0xFF0000) >> 16;
/*  44 */       j += (l & 0xFF00) >> 8;
/*  45 */       k += (l & 0xFF) >> 0;
/*     */     } 
/*     */     
/*  48 */     i /= aint.length;
/*  49 */     j /= aint.length;
/*  50 */     k /= aint.length;
/*  51 */     return i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTBase getExplosionTag(ItemStack stack, String key) {
/*  59 */     if (stack.hasTagCompound()) {
/*     */       
/*  61 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  63 */       if (nbttagcompound != null)
/*     */       {
/*  65 */         return nbttagcompound.getTag(key);
/*     */       }
/*     */     } 
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
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  80 */     if (stack.hasTagCompound()) {
/*     */       
/*  82 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  84 */       if (nbttagcompound != null)
/*     */       {
/*  86 */         addExplosionInfo(nbttagcompound, tooltip);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addExplosionInfo(NBTTagCompound nbt, List<String> tooltip) {
/*  93 */     byte b0 = nbt.getByte("Type");
/*     */     
/*  95 */     if (b0 >= 0 && b0 <= 4) {
/*     */       
/*  97 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
/*     */     }
/*     */     else {
/*     */       
/* 101 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
/*     */     } 
/*     */     
/* 104 */     int[] aint = nbt.getIntArray("Colors");
/*     */     
/* 106 */     if (aint.length > 0) {
/*     */       
/* 108 */       boolean flag = true;
/* 109 */       String s = "";
/*     */       
/* 111 */       for (int i : aint) {
/*     */         
/* 113 */         if (!flag)
/*     */         {
/* 115 */           s = s + ", ";
/*     */         }
/*     */         
/* 118 */         flag = false;
/* 119 */         boolean flag1 = false;
/*     */         
/* 121 */         for (int j = 0; j < ItemDye.dyeColors.length; j++) {
/*     */           
/* 123 */           if (i == ItemDye.dyeColors[j]) {
/*     */             
/* 125 */             flag1 = true;
/* 126 */             s = s + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(j).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 131 */         if (!flag1)
/*     */         {
/* 133 */           s = s + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */       } 
/*     */       
/* 137 */       tooltip.add(s);
/*     */     } 
/*     */     
/* 140 */     int[] aint1 = nbt.getIntArray("FadeColors");
/*     */     
/* 142 */     if (aint1.length > 0) {
/*     */       
/* 144 */       boolean flag2 = true;
/* 145 */       String s1 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";
/*     */       
/* 147 */       for (int l : aint1) {
/*     */         
/* 149 */         if (!flag2)
/*     */         {
/* 151 */           s1 = s1 + ", ";
/*     */         }
/*     */         
/* 154 */         flag2 = false;
/* 155 */         boolean flag5 = false;
/*     */         
/* 157 */         for (int k = 0; k < 16; k++) {
/*     */           
/* 159 */           if (l == ItemDye.dyeColors[k]) {
/*     */             
/* 161 */             flag5 = true;
/* 162 */             s1 = s1 + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(k).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 167 */         if (!flag5)
/*     */         {
/* 169 */           s1 = s1 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */       } 
/*     */       
/* 173 */       tooltip.add(s1);
/*     */     } 
/*     */     
/* 176 */     boolean flag3 = nbt.getBoolean("Trail");
/*     */     
/* 178 */     if (flag3)
/*     */     {
/* 180 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
/*     */     }
/*     */     
/* 183 */     boolean flag4 = nbt.getBoolean("Flicker");
/*     */     
/* 185 */     if (flag4)
/*     */     {
/* 187 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemFireworkCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */