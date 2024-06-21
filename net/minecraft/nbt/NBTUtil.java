/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NBTUtil
/*     */ {
/*     */   public static GameProfile readGameProfileFromNBT(NBTTagCompound compound) {
/*     */     UUID uuid;
/*  15 */     String s = null;
/*  16 */     String s1 = null;
/*     */     
/*  18 */     if (compound.hasKey("Name", 8))
/*     */     {
/*  20 */       s = compound.getString("Name");
/*     */     }
/*     */     
/*  23 */     if (compound.hasKey("Id", 8))
/*     */     {
/*  25 */       s1 = compound.getString("Id");
/*     */     }
/*     */     
/*  28 */     if (StringUtils.isNullOrEmpty(s) && StringUtils.isNullOrEmpty(s1))
/*     */     {
/*  30 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  38 */       uuid = UUID.fromString(s1);
/*     */     }
/*  40 */     catch (Throwable var12) {
/*     */       
/*  42 */       uuid = null;
/*     */     } 
/*     */     
/*  45 */     GameProfile gameprofile = new GameProfile(uuid, s);
/*     */     
/*  47 */     if (compound.hasKey("Properties", 10)) {
/*     */       
/*  49 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");
/*     */       
/*  51 */       for (String s2 : nbttagcompound.getKeySet()) {
/*     */         
/*  53 */         NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);
/*     */         
/*  55 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */           
/*  57 */           NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  58 */           String s3 = nbttagcompound1.getString("Value");
/*     */           
/*  60 */           if (nbttagcompound1.hasKey("Signature", 8)) {
/*     */             
/*  62 */             gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
/*     */           }
/*     */           else {
/*     */             
/*  66 */             gameprofile.getProperties().put(s2, new Property(s2, s3));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     return gameprofile;
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
/*     */   public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
/*  84 */     if (!StringUtils.isNullOrEmpty(profile.getName()))
/*     */     {
/*  86 */       tagCompound.setString("Name", profile.getName());
/*     */     }
/*     */     
/*  89 */     if (profile.getId() != null)
/*     */     {
/*  91 */       tagCompound.setString("Id", profile.getId().toString());
/*     */     }
/*     */     
/*  94 */     if (!profile.getProperties().isEmpty()) {
/*     */       
/*  96 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/*  98 */       for (String s : profile.getProperties().keySet()) {
/*     */         
/* 100 */         NBTTagList nbttaglist = new NBTTagList();
/*     */         
/* 102 */         for (Property property : profile.getProperties().get(s)) {
/*     */           
/* 104 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 105 */           nbttagcompound1.setString("Value", property.getValue());
/*     */           
/* 107 */           if (property.hasSignature())
/*     */           {
/* 109 */             nbttagcompound1.setString("Signature", property.getSignature());
/*     */           }
/*     */           
/* 112 */           nbttaglist.appendTag(nbttagcompound1);
/*     */         } 
/*     */         
/* 115 */         nbttagcompound.setTag(s, nbttaglist);
/*     */       } 
/*     */       
/* 118 */       tagCompound.setTag("Properties", nbttagcompound);
/*     */     } 
/*     */     
/* 121 */     return tagCompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_181123_a(NBTBase p_181123_0_, NBTBase p_181123_1_, boolean p_181123_2_) {
/* 126 */     if (p_181123_0_ == p_181123_1_)
/*     */     {
/* 128 */       return true;
/*     */     }
/* 130 */     if (p_181123_0_ == null)
/*     */     {
/* 132 */       return true;
/*     */     }
/* 134 */     if (p_181123_1_ == null)
/*     */     {
/* 136 */       return false;
/*     */     }
/* 138 */     if (!p_181123_0_.getClass().equals(p_181123_1_.getClass()))
/*     */     {
/* 140 */       return false;
/*     */     }
/* 142 */     if (p_181123_0_ instanceof NBTTagCompound) {
/*     */       
/* 144 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_181123_0_;
/* 145 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)p_181123_1_;
/*     */       
/* 147 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 149 */         NBTBase nbtbase1 = nbttagcompound.getTag(s);
/*     */         
/* 151 */         if (!func_181123_a(nbtbase1, nbttagcompound1.getTag(s), p_181123_2_))
/*     */         {
/* 153 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 157 */       return true;
/*     */     } 
/* 159 */     if (p_181123_0_ instanceof NBTTagList && p_181123_2_) {
/*     */       
/* 161 */       NBTTagList nbttaglist = (NBTTagList)p_181123_0_;
/* 162 */       NBTTagList nbttaglist1 = (NBTTagList)p_181123_1_;
/*     */       
/* 164 */       if (nbttaglist.tagCount() == 0)
/*     */       {
/* 166 */         return (nbttaglist1.tagCount() == 0);
/*     */       }
/*     */ 
/*     */       
/* 170 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 172 */         NBTBase nbtbase = nbttaglist.get(i);
/* 173 */         boolean flag = false;
/*     */         
/* 175 */         for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */           
/* 177 */           if (func_181123_a(nbtbase, nbttaglist1.get(j), p_181123_2_)) {
/*     */             
/* 179 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 184 */         if (!flag)
/*     */         {
/* 186 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 190 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 195 */     return p_181123_0_.equals(p_181123_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\nbt\NBTUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */