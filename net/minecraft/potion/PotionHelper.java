/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ import net.optifine.CustomColors;
/*     */ 
/*     */ public class PotionHelper
/*     */ {
/*  14 */   public static final String field_77924_a = null;
/*     */   public static final String sugarEffect = "-0+1-2-3&4-4+13";
/*     */   public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
/*     */   public static final String spiderEyeEffect = "-0-1+2-3&4-4+13";
/*     */   public static final String fermentedSpiderEyeEffect = "-0+3-4+13";
/*     */   public static final String speckledMelonEffect = "+0-1+2-3&4-4+13";
/*     */   public static final String blazePowderEffect = "+0-1-2+3&4-4+13";
/*     */   public static final String magmaCreamEffect = "+0+1-2-3&4-4+13";
/*     */   public static final String redstoneEffect = "-5+6-7";
/*     */   public static final String glowstoneEffect = "+5-6-7";
/*     */   public static final String gunpowderEffect = "+14&13-13";
/*     */   public static final String goldenCarrotEffect = "-0+1+2-3+13&4-4";
/*     */   public static final String pufferfishEffect = "+0-1+2+3+13&4-4";
/*     */   public static final String rabbitFootEffect = "+0+1-2+3&4-4+13";
/*  28 */   private static final Map<Integer, String> potionRequirements = Maps.newHashMap();
/*  29 */   private static final Map<Integer, String> potionAmplifiers = Maps.newHashMap();
/*  30 */   private static final Map<Integer, Integer> DATAVALUE_COLORS = Maps.newHashMap();
/*     */ 
/*     */   
/*  33 */   private static final String[] potionPrefixes = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkFlag(int p_77914_0_, int p_77914_1_) {
/*  40 */     return ((p_77914_0_ & 1 << p_77914_1_) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int isFlagSet(int p_77910_0_, int p_77910_1_) {
/*  48 */     return checkFlag(p_77910_0_, p_77910_1_) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int isFlagUnset(int p_77916_0_, int p_77916_1_) {
/*  56 */     return checkFlag(p_77916_0_, p_77916_1_) ? 0 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPotionPrefixIndex(int dataValue) {
/*  64 */     return func_77908_a(dataValue, 5, 4, 3, 2, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcPotionLiquidColor(Collection<PotionEffect> p_77911_0_) {
/*  72 */     int i = 3694022;
/*     */     
/*  74 */     if (p_77911_0_ != null && !p_77911_0_.isEmpty()) {
/*     */       
/*  76 */       float f = 0.0F;
/*  77 */       float f1 = 0.0F;
/*  78 */       float f2 = 0.0F;
/*  79 */       float f3 = 0.0F;
/*     */       
/*  81 */       for (PotionEffect potioneffect : p_77911_0_) {
/*     */         
/*  83 */         if (potioneffect.getIsShowParticles()) {
/*     */           
/*  85 */           int j = Potion.potionTypes[potioneffect.getPotionID()].getLiquidColor();
/*     */           
/*  87 */           if (Config.isCustomColors())
/*     */           {
/*  89 */             j = CustomColors.getPotionColor(potioneffect.getPotionID(), j);
/*     */           }
/*     */           
/*  92 */           for (int k = 0; k <= potioneffect.getAmplifier(); k++) {
/*     */             
/*  94 */             f += (j >> 16 & 0xFF) / 255.0F;
/*  95 */             f1 += (j >> 8 & 0xFF) / 255.0F;
/*  96 */             f2 += (j >> 0 & 0xFF) / 255.0F;
/*  97 */             f3++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 102 */       if (f3 == 0.0F)
/*     */       {
/* 104 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 108 */       f = f / f3 * 255.0F;
/* 109 */       f1 = f1 / f3 * 255.0F;
/* 110 */       f2 = f2 / f3 * 255.0F;
/* 111 */       return (int)f << 16 | (int)f1 << 8 | (int)f2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     return Config.isCustomColors() ? CustomColors.getPotionColor(0, i) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getAreAmbient(Collection<PotionEffect> potionEffects) {
/* 125 */     for (PotionEffect potioneffect : potionEffects) {
/*     */       
/* 127 */       if (!potioneffect.getIsAmbient())
/*     */       {
/* 129 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLiquidColor(int dataValue, boolean bypassCache) {
/* 141 */     Integer integer = IntegerCache.func_181756_a(dataValue);
/*     */     
/* 143 */     if (!bypassCache) {
/*     */       
/* 145 */       if (DATAVALUE_COLORS.containsKey(integer))
/*     */       {
/* 147 */         return ((Integer)DATAVALUE_COLORS.get(integer)).intValue();
/*     */       }
/*     */ 
/*     */       
/* 151 */       int i = calcPotionLiquidColor(getPotionEffects(integer.intValue(), false));
/* 152 */       DATAVALUE_COLORS.put(integer, Integer.valueOf(i));
/* 153 */       return i;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return calcPotionLiquidColor(getPotionEffects(integer.intValue(), true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPotionPrefix(int dataValue) {
/* 167 */     int i = getPotionPrefixIndex(dataValue);
/* 168 */     return potionPrefixes[i];
/*     */   }
/*     */ 
/*     */   
/*     */   private static int func_77904_a(boolean p_77904_0_, boolean p_77904_1_, boolean p_77904_2_, int p_77904_3_, int p_77904_4_, int p_77904_5_, int p_77904_6_) {
/* 173 */     int i = 0;
/*     */     
/* 175 */     if (p_77904_0_) {
/*     */       
/* 177 */       i = isFlagUnset(p_77904_6_, p_77904_4_);
/*     */     }
/* 179 */     else if (p_77904_3_ != -1) {
/*     */       
/* 181 */       if (p_77904_3_ == 0 && countSetFlags(p_77904_6_) == p_77904_4_)
/*     */       {
/* 183 */         i = 1;
/*     */       }
/* 185 */       else if (p_77904_3_ == 1 && countSetFlags(p_77904_6_) > p_77904_4_)
/*     */       {
/* 187 */         i = 1;
/*     */       }
/* 189 */       else if (p_77904_3_ == 2 && countSetFlags(p_77904_6_) < p_77904_4_)
/*     */       {
/* 191 */         i = 1;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 196 */       i = isFlagSet(p_77904_6_, p_77904_4_);
/*     */     } 
/*     */     
/* 199 */     if (p_77904_1_)
/*     */     {
/* 201 */       i *= p_77904_5_;
/*     */     }
/*     */     
/* 204 */     if (p_77904_2_)
/*     */     {
/* 206 */       i *= -1;
/*     */     }
/*     */     
/* 209 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int countSetFlags(int p_77907_0_) {
/*     */     int i;
/* 219 */     for (i = 0; p_77907_0_ > 0; i++)
/*     */     {
/* 221 */       p_77907_0_ &= p_77907_0_ - 1;
/*     */     }
/*     */     
/* 224 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parsePotionEffects(String p_77912_0_, int p_77912_1_, int p_77912_2_, int p_77912_3_) {
/* 229 */     if (p_77912_1_ < p_77912_0_.length() && p_77912_2_ >= 0 && p_77912_1_ < p_77912_2_) {
/*     */       
/* 231 */       int i = p_77912_0_.indexOf('|', p_77912_1_);
/*     */       
/* 233 */       if (i >= 0 && i < p_77912_2_) {
/*     */         
/* 235 */         int l1 = parsePotionEffects(p_77912_0_, p_77912_1_, i - 1, p_77912_3_);
/*     */         
/* 237 */         if (l1 > 0)
/*     */         {
/* 239 */           return l1;
/*     */         }
/*     */ 
/*     */         
/* 243 */         int j2 = parsePotionEffects(p_77912_0_, i + 1, p_77912_2_, p_77912_3_);
/* 244 */         return (j2 > 0) ? j2 : 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 249 */       int j = p_77912_0_.indexOf('&', p_77912_1_);
/*     */       
/* 251 */       if (j >= 0 && j < p_77912_2_) {
/*     */         
/* 253 */         int i2 = parsePotionEffects(p_77912_0_, p_77912_1_, j - 1, p_77912_3_);
/*     */         
/* 255 */         if (i2 <= 0)
/*     */         {
/* 257 */           return 0;
/*     */         }
/*     */ 
/*     */         
/* 261 */         int k2 = parsePotionEffects(p_77912_0_, j + 1, p_77912_2_, p_77912_3_);
/* 262 */         return (k2 <= 0) ? 0 : ((i2 > k2) ? i2 : k2);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 267 */       boolean flag = false;
/* 268 */       boolean flag1 = false;
/* 269 */       boolean flag2 = false;
/* 270 */       boolean flag3 = false;
/* 271 */       boolean flag4 = false;
/* 272 */       int k = -1;
/* 273 */       int l = 0;
/* 274 */       int i1 = 0;
/* 275 */       int j1 = 0;
/*     */       
/* 277 */       for (int k1 = p_77912_1_; k1 < p_77912_2_; k1++) {
/*     */         
/* 279 */         char c0 = p_77912_0_.charAt(k1);
/*     */         
/* 281 */         if (c0 >= '0' && c0 <= '9') {
/*     */           
/* 283 */           if (flag)
/*     */           {
/* 285 */             i1 = c0 - 48;
/* 286 */             flag1 = true;
/*     */           }
/*     */           else
/*     */           {
/* 290 */             l *= 10;
/* 291 */             l += c0 - 48;
/* 292 */             flag2 = true;
/*     */           }
/*     */         
/* 295 */         } else if (c0 == '*') {
/*     */           
/* 297 */           flag = true;
/*     */         }
/* 299 */         else if (c0 == '!') {
/*     */           
/* 301 */           if (flag2) {
/*     */             
/* 303 */             j1 += func_77904_a(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 304 */             flag3 = false;
/* 305 */             flag4 = false;
/* 306 */             flag = false;
/* 307 */             flag1 = false;
/* 308 */             flag2 = false;
/* 309 */             i1 = 0;
/* 310 */             l = 0;
/* 311 */             k = -1;
/*     */           } 
/*     */           
/* 314 */           flag3 = true;
/*     */         }
/* 316 */         else if (c0 == '-') {
/*     */           
/* 318 */           if (flag2) {
/*     */             
/* 320 */             j1 += func_77904_a(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 321 */             flag3 = false;
/* 322 */             flag4 = false;
/* 323 */             flag = false;
/* 324 */             flag1 = false;
/* 325 */             flag2 = false;
/* 326 */             i1 = 0;
/* 327 */             l = 0;
/* 328 */             k = -1;
/*     */           } 
/*     */           
/* 331 */           flag4 = true;
/*     */         }
/* 333 */         else if (c0 != '=' && c0 != '<' && c0 != '>') {
/*     */           
/* 335 */           if (c0 == '+' && flag2)
/*     */           {
/* 337 */             j1 += func_77904_a(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 338 */             flag3 = false;
/* 339 */             flag4 = false;
/* 340 */             flag = false;
/* 341 */             flag1 = false;
/* 342 */             flag2 = false;
/* 343 */             i1 = 0;
/* 344 */             l = 0;
/* 345 */             k = -1;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 350 */           if (flag2) {
/*     */             
/* 352 */             j1 += func_77904_a(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 353 */             flag3 = false;
/* 354 */             flag4 = false;
/* 355 */             flag = false;
/* 356 */             flag1 = false;
/* 357 */             flag2 = false;
/* 358 */             i1 = 0;
/* 359 */             l = 0;
/* 360 */             k = -1;
/*     */           } 
/*     */           
/* 363 */           if (c0 == '=') {
/*     */             
/* 365 */             k = 0;
/*     */           }
/* 367 */           else if (c0 == '<') {
/*     */             
/* 369 */             k = 2;
/*     */           }
/* 371 */           else if (c0 == '>') {
/*     */             
/* 373 */             k = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 378 */       if (flag2)
/*     */       {
/* 380 */         j1 += func_77904_a(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/*     */       }
/*     */       
/* 383 */       return j1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 389 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> getPotionEffects(int p_77917_0_, boolean p_77917_1_) {
/* 395 */     List<PotionEffect> list = null;
/*     */     
/* 397 */     for (Potion potion : Potion.potionTypes) {
/*     */       
/* 399 */       if (potion != null && (!potion.isUsable() || p_77917_1_)) {
/*     */         
/* 401 */         String s = potionRequirements.get(Integer.valueOf(potion.getId()));
/*     */         
/* 403 */         if (s != null) {
/*     */           
/* 405 */           int i = parsePotionEffects(s, 0, s.length(), p_77917_0_);
/*     */           
/* 407 */           if (i > 0) {
/*     */             
/* 409 */             int j = 0;
/* 410 */             String s1 = potionAmplifiers.get(Integer.valueOf(potion.getId()));
/*     */             
/* 412 */             if (s1 != null) {
/*     */               
/* 414 */               j = parsePotionEffects(s1, 0, s1.length(), p_77917_0_);
/*     */               
/* 416 */               if (j < 0)
/*     */               {
/* 418 */                 j = 0;
/*     */               }
/*     */             } 
/*     */             
/* 422 */             if (potion.isInstant()) {
/*     */               
/* 424 */               i = 1;
/*     */             }
/*     */             else {
/*     */               
/* 428 */               i = 1200 * (i * 3 + (i - 1) * 2);
/* 429 */               i >>= j;
/* 430 */               i = (int)Math.round(i * potion.getEffectiveness());
/*     */               
/* 432 */               if ((p_77917_0_ & 0x4000) != 0)
/*     */               {
/* 434 */                 i = (int)Math.round(i * 0.75D + 0.5D);
/*     */               }
/*     */             } 
/*     */             
/* 438 */             if (list == null)
/*     */             {
/* 440 */               list = Lists.newArrayList();
/*     */             }
/*     */             
/* 443 */             PotionEffect potioneffect = new PotionEffect(potion.getId(), i, j);
/*     */             
/* 445 */             if ((p_77917_0_ & 0x4000) != 0)
/*     */             {
/* 447 */               potioneffect.setSplashPotion(true);
/*     */             }
/*     */             
/* 450 */             list.add(potioneffect);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int brewBitOperations(int p_77906_0_, int p_77906_1_, boolean p_77906_2_, boolean p_77906_3_, boolean p_77906_4_) {
/* 464 */     if (p_77906_4_) {
/*     */       
/* 466 */       if (!checkFlag(p_77906_0_, p_77906_1_))
/*     */       {
/* 468 */         return 0;
/*     */       }
/*     */     }
/* 471 */     else if (p_77906_2_) {
/*     */       
/* 473 */       p_77906_0_ &= 1 << p_77906_1_ ^ 0xFFFFFFFF;
/*     */     }
/* 475 */     else if (p_77906_3_) {
/*     */       
/* 477 */       if ((p_77906_0_ & 1 << p_77906_1_) == 0)
/*     */       {
/* 479 */         p_77906_0_ |= 1 << p_77906_1_;
/*     */       }
/*     */       else
/*     */       {
/* 483 */         p_77906_0_ &= 1 << p_77906_1_ ^ 0xFFFFFFFF;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 488 */       p_77906_0_ |= 1 << p_77906_1_;
/*     */     } 
/*     */     
/* 491 */     return p_77906_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int applyIngredient(int p_77913_0_, String p_77913_1_) {
/* 499 */     int i = 0;
/* 500 */     int j = p_77913_1_.length();
/* 501 */     boolean flag = false;
/* 502 */     boolean flag1 = false;
/* 503 */     boolean flag2 = false;
/* 504 */     boolean flag3 = false;
/* 505 */     int k = 0;
/*     */     
/* 507 */     for (int l = i; l < j; l++) {
/*     */       
/* 509 */       char c0 = p_77913_1_.charAt(l);
/*     */       
/* 511 */       if (c0 >= '0' && c0 <= '9') {
/*     */         
/* 513 */         k *= 10;
/* 514 */         k += c0 - 48;
/* 515 */         flag = true;
/*     */       }
/* 517 */       else if (c0 == '!') {
/*     */         
/* 519 */         if (flag) {
/*     */           
/* 521 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 522 */           flag3 = false;
/* 523 */           flag1 = false;
/* 524 */           flag2 = false;
/* 525 */           flag = false;
/* 526 */           k = 0;
/*     */         } 
/*     */         
/* 529 */         flag1 = true;
/*     */       }
/* 531 */       else if (c0 == '-') {
/*     */         
/* 533 */         if (flag) {
/*     */           
/* 535 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 536 */           flag3 = false;
/* 537 */           flag1 = false;
/* 538 */           flag2 = false;
/* 539 */           flag = false;
/* 540 */           k = 0;
/*     */         } 
/*     */         
/* 543 */         flag2 = true;
/*     */       }
/* 545 */       else if (c0 == '+') {
/*     */         
/* 547 */         if (flag)
/*     */         {
/* 549 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 550 */           flag3 = false;
/* 551 */           flag1 = false;
/* 552 */           flag2 = false;
/* 553 */           flag = false;
/* 554 */           k = 0;
/*     */         }
/*     */       
/* 557 */       } else if (c0 == '&') {
/*     */         
/* 559 */         if (flag) {
/*     */           
/* 561 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 562 */           flag3 = false;
/* 563 */           flag1 = false;
/* 564 */           flag2 = false;
/* 565 */           flag = false;
/* 566 */           k = 0;
/*     */         } 
/*     */         
/* 569 */         flag3 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 573 */     if (flag)
/*     */     {
/* 575 */       p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/*     */     }
/*     */     
/* 578 */     return p_77913_0_ & 0x7FFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_77908_a(int p_77908_0_, int p_77908_1_, int p_77908_2_, int p_77908_3_, int p_77908_4_, int p_77908_5_) {
/* 583 */     return (checkFlag(p_77908_0_, p_77908_1_) ? 16 : 0) | (checkFlag(p_77908_0_, p_77908_2_) ? 8 : 0) | (checkFlag(p_77908_0_, p_77908_3_) ? 4 : 0) | (checkFlag(p_77908_0_, p_77908_4_) ? 2 : 0) | (checkFlag(p_77908_0_, p_77908_5_) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 588 */     potionRequirements.put(Integer.valueOf(Potion.regeneration.getId()), "0 & !1 & !2 & !3 & 0+6");
/* 589 */     potionRequirements.put(Integer.valueOf(Potion.moveSpeed.getId()), "!0 & 1 & !2 & !3 & 1+6");
/* 590 */     potionRequirements.put(Integer.valueOf(Potion.fireResistance.getId()), "0 & 1 & !2 & !3 & 0+6");
/* 591 */     potionRequirements.put(Integer.valueOf(Potion.heal.getId()), "0 & !1 & 2 & !3");
/* 592 */     potionRequirements.put(Integer.valueOf(Potion.poison.getId()), "!0 & !1 & 2 & !3 & 2+6");
/* 593 */     potionRequirements.put(Integer.valueOf(Potion.weakness.getId()), "!0 & !1 & !2 & 3 & 3+6");
/* 594 */     potionRequirements.put(Integer.valueOf(Potion.harm.getId()), "!0 & !1 & 2 & 3");
/* 595 */     potionRequirements.put(Integer.valueOf(Potion.moveSlowdown.getId()), "!0 & 1 & !2 & 3 & 3+6");
/* 596 */     potionRequirements.put(Integer.valueOf(Potion.damageBoost.getId()), "0 & !1 & !2 & 3 & 3+6");
/* 597 */     potionRequirements.put(Integer.valueOf(Potion.nightVision.getId()), "!0 & 1 & 2 & !3 & 2+6");
/* 598 */     potionRequirements.put(Integer.valueOf(Potion.invisibility.getId()), "!0 & 1 & 2 & 3 & 2+6");
/* 599 */     potionRequirements.put(Integer.valueOf(Potion.waterBreathing.getId()), "0 & !1 & 2 & 3 & 2+6");
/* 600 */     potionRequirements.put(Integer.valueOf(Potion.jump.getId()), "0 & 1 & !2 & 3 & 3+6");
/* 601 */     potionAmplifiers.put(Integer.valueOf(Potion.moveSpeed.getId()), "5");
/* 602 */     potionAmplifiers.put(Integer.valueOf(Potion.digSpeed.getId()), "5");
/* 603 */     potionAmplifiers.put(Integer.valueOf(Potion.damageBoost.getId()), "5");
/* 604 */     potionAmplifiers.put(Integer.valueOf(Potion.regeneration.getId()), "5");
/* 605 */     potionAmplifiers.put(Integer.valueOf(Potion.harm.getId()), "5");
/* 606 */     potionAmplifiers.put(Integer.valueOf(Potion.heal.getId()), "5");
/* 607 */     potionAmplifiers.put(Integer.valueOf(Potion.resistance.getId()), "5");
/* 608 */     potionAmplifiers.put(Integer.valueOf(Potion.poison.getId()), "5");
/* 609 */     potionAmplifiers.put(Integer.valueOf(Potion.jump.getId()), "5");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\potion\PotionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */