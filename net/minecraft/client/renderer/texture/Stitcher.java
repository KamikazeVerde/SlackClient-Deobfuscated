/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.StitcherException;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class Stitcher
/*     */ {
/*     */   private final int mipmapLevelStitcher;
/*  14 */   private final Set<Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
/*  15 */   private final List<Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
/*     */   
/*     */   private int currentWidth;
/*     */   
/*     */   private int currentHeight;
/*     */   
/*     */   private final int maxWidth;
/*     */   private final int maxHeight;
/*     */   private final boolean forcePowerOf2;
/*     */   private final int maxTileDimension;
/*     */   
/*     */   public Stitcher(int maxTextureWidth, int maxTextureHeight, boolean p_i45095_3_, int p_i45095_4_, int mipmapLevel) {
/*  27 */     this.mipmapLevelStitcher = mipmapLevel;
/*  28 */     this.maxWidth = maxTextureWidth;
/*  29 */     this.maxHeight = maxTextureHeight;
/*  30 */     this.forcePowerOf2 = p_i45095_3_;
/*  31 */     this.maxTileDimension = p_i45095_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentWidth() {
/*  36 */     return this.currentWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentHeight() {
/*  41 */     return this.currentHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSprite(TextureAtlasSprite p_110934_1_) {
/*  46 */     Holder stitcher$holder = new Holder(p_110934_1_, this.mipmapLevelStitcher);
/*     */     
/*  48 */     if (this.maxTileDimension > 0)
/*     */     {
/*  50 */       stitcher$holder.setNewDimension(this.maxTileDimension);
/*     */     }
/*     */     
/*  53 */     this.setStitchHolders.add(stitcher$holder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doStitch() {
/*  58 */     Holder[] astitcher$holder = this.setStitchHolders.<Holder>toArray(new Holder[this.setStitchHolders.size()]);
/*  59 */     Arrays.sort((Object[])astitcher$holder);
/*     */     
/*  61 */     for (Holder stitcher$holder : astitcher$holder) {
/*     */       
/*  63 */       if (!allocateSlot(stitcher$holder)) {
/*     */         
/*  65 */         String s = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", new Object[] { stitcher$holder.getAtlasSprite().getIconName(), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconWidth()), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconHeight()), Integer.valueOf(this.currentWidth), Integer.valueOf(this.currentHeight), Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight) });
/*  66 */         throw new StitcherException(stitcher$holder, s);
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     if (this.forcePowerOf2) {
/*     */       
/*  72 */       this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/*  73 */       this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TextureAtlasSprite> getStichSlots() {
/*  79 */     List<Slot> list = Lists.newArrayList();
/*     */     
/*  81 */     for (Slot stitcher$slot : this.stitchSlots)
/*     */     {
/*  83 */       stitcher$slot.getAllStitchSlots(list);
/*     */     }
/*     */     
/*  86 */     List<TextureAtlasSprite> list1 = Lists.newArrayList();
/*     */     
/*  88 */     for (Slot stitcher$slot1 : list) {
/*     */       
/*  90 */       Holder stitcher$holder = stitcher$slot1.getStitchHolder();
/*  91 */       TextureAtlasSprite textureatlassprite = stitcher$holder.getAtlasSprite();
/*  92 */       textureatlassprite.initSprite(this.currentWidth, this.currentHeight, stitcher$slot1.getOriginX(), stitcher$slot1.getOriginY(), stitcher$holder.isRotated());
/*  93 */       list1.add(textureatlassprite);
/*     */     } 
/*     */     
/*  96 */     return list1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getMipmapDimension(int p_147969_0_, int p_147969_1_) {
/* 101 */     return (p_147969_0_ >> p_147969_1_) + (((p_147969_0_ & (1 << p_147969_1_) - 1) == 0) ? 0 : 1) << p_147969_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean allocateSlot(Holder p_94310_1_) {
/* 109 */     for (int i = 0; i < this.stitchSlots.size(); i++) {
/*     */       
/* 111 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_))
/*     */       {
/* 113 */         return true;
/*     */       }
/*     */       
/* 116 */       p_94310_1_.rotate();
/*     */       
/* 118 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_))
/*     */       {
/* 120 */         return true;
/*     */       }
/*     */       
/* 123 */       p_94310_1_.rotate();
/*     */     } 
/*     */     
/* 126 */     return expandAndAllocateSlot(p_94310_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expandAndAllocateSlot(Holder p_94311_1_) {
/*     */     boolean flag1;
/*     */     Slot stitcher$slot;
/* 134 */     int i = Math.min(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/* 135 */     boolean flag = (this.currentWidth == 0 && this.currentHeight == 0);
/*     */ 
/*     */     
/* 138 */     if (this.forcePowerOf2) {
/*     */       
/* 140 */       int j = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/* 141 */       int k = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/* 142 */       int l = MathHelper.roundUpToPowerOfTwo(this.currentWidth + i);
/* 143 */       int i1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + i);
/* 144 */       boolean flag2 = (l <= this.maxWidth);
/* 145 */       boolean flag3 = (i1 <= this.maxHeight);
/*     */       
/* 147 */       if (!flag2 && !flag3)
/*     */       {
/* 149 */         return false;
/*     */       }
/*     */       
/* 152 */       boolean flag4 = (j != l);
/* 153 */       boolean flag5 = (k != i1);
/*     */       
/* 155 */       if (flag4 ^ flag5)
/*     */       {
/* 157 */         flag1 = !flag4;
/*     */       }
/*     */       else
/*     */       {
/* 161 */         flag1 = (flag2 && j <= k);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 166 */       boolean flag6 = (this.currentWidth + i <= this.maxWidth);
/* 167 */       boolean flag7 = (this.currentHeight + i <= this.maxHeight);
/*     */       
/* 169 */       if (!flag6 && !flag7)
/*     */       {
/* 171 */         return false;
/*     */       }
/*     */       
/* 174 */       flag1 = (flag6 && (flag || this.currentWidth <= this.currentHeight));
/*     */     } 
/*     */     
/* 177 */     int j1 = Math.max(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/*     */     
/* 179 */     if (MathHelper.roundUpToPowerOfTwo((!flag1 ? this.currentHeight : this.currentWidth) + j1) > (!flag1 ? this.maxHeight : this.maxWidth))
/*     */     {
/* 181 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (flag1) {
/*     */       
/* 189 */       if (p_94311_1_.getWidth() > p_94311_1_.getHeight())
/*     */       {
/* 191 */         p_94311_1_.rotate();
/*     */       }
/*     */       
/* 194 */       if (this.currentHeight == 0)
/*     */       {
/* 196 */         this.currentHeight = p_94311_1_.getHeight();
/*     */       }
/*     */       
/* 199 */       stitcher$slot = new Slot(this.currentWidth, 0, p_94311_1_.getWidth(), this.currentHeight);
/* 200 */       this.currentWidth += p_94311_1_.getWidth();
/*     */     }
/*     */     else {
/*     */       
/* 204 */       stitcher$slot = new Slot(0, this.currentHeight, this.currentWidth, p_94311_1_.getHeight());
/* 205 */       this.currentHeight += p_94311_1_.getHeight();
/*     */     } 
/*     */     
/* 208 */     stitcher$slot.addSlot(p_94311_1_);
/* 209 */     this.stitchSlots.add(stitcher$slot);
/* 210 */     return true;
/*     */   }
/*     */   
/*     */   public static class Holder
/*     */     implements Comparable<Holder>
/*     */   {
/*     */     private final TextureAtlasSprite theTexture;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private final int mipmapLevelHolder;
/*     */     private boolean rotated;
/* 221 */     private float scaleFactor = 1.0F;
/*     */ 
/*     */     
/*     */     public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_) {
/* 225 */       this.theTexture = p_i45094_1_;
/* 226 */       this.width = p_i45094_1_.getIconWidth();
/* 227 */       this.height = p_i45094_1_.getIconHeight();
/* 228 */       this.mipmapLevelHolder = p_i45094_2_;
/* 229 */       this.rotated = (Stitcher.getMipmapDimension(this.height, p_i45094_2_) > Stitcher.getMipmapDimension(this.width, p_i45094_2_));
/*     */     }
/*     */ 
/*     */     
/*     */     public TextureAtlasSprite getAtlasSprite() {
/* 234 */       return this.theTexture;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 239 */       return this.rotated ? Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 244 */       return this.rotated ? Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */ 
/*     */     
/*     */     public void rotate() {
/* 249 */       this.rotated = !this.rotated;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRotated() {
/* 254 */       return this.rotated;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setNewDimension(int p_94196_1_) {
/* 259 */       if (this.width > p_94196_1_ && this.height > p_94196_1_)
/*     */       {
/* 261 */         this.scaleFactor = p_94196_1_ / Math.min(this.width, this.height);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 267 */       return "Holder{width=" + this.width + ", height=" + this.height + '}';
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Holder p_compareTo_1_) {
/*     */       int i;
/* 274 */       if (getHeight() == p_compareTo_1_.getHeight()) {
/*     */         
/* 276 */         if (getWidth() == p_compareTo_1_.getWidth()) {
/*     */           
/* 278 */           if (this.theTexture.getIconName() == null)
/*     */           {
/* 280 */             return (p_compareTo_1_.theTexture.getIconName() == null) ? 0 : -1;
/*     */           }
/*     */           
/* 283 */           return this.theTexture.getIconName().compareTo(p_compareTo_1_.theTexture.getIconName());
/*     */         } 
/*     */         
/* 286 */         i = (getWidth() < p_compareTo_1_.getWidth()) ? 1 : -1;
/*     */       }
/*     */       else {
/*     */         
/* 290 */         i = (getHeight() < p_compareTo_1_.getHeight()) ? 1 : -1;
/*     */       } 
/*     */       
/* 293 */       return i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Slot
/*     */   {
/*     */     private final int originX;
/*     */     private final int originY;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private List<Slot> subSlots;
/*     */     private Stitcher.Holder holder;
/*     */     
/*     */     public Slot(int p_i1277_1_, int p_i1277_2_, int widthIn, int heightIn) {
/* 308 */       this.originX = p_i1277_1_;
/* 309 */       this.originY = p_i1277_2_;
/* 310 */       this.width = widthIn;
/* 311 */       this.height = heightIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Stitcher.Holder getStitchHolder() {
/* 316 */       return this.holder;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOriginX() {
/* 321 */       return this.originX;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOriginY() {
/* 326 */       return this.originY;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addSlot(Stitcher.Holder holderIn) {
/* 331 */       if (this.holder != null)
/*     */       {
/* 333 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 337 */       int i = holderIn.getWidth();
/* 338 */       int j = holderIn.getHeight();
/*     */       
/* 340 */       if (i <= this.width && j <= this.height) {
/*     */         
/* 342 */         if (i == this.width && j == this.height) {
/*     */           
/* 344 */           this.holder = holderIn;
/* 345 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 349 */         if (this.subSlots == null) {
/*     */           
/* 351 */           this.subSlots = Lists.newArrayListWithCapacity(1);
/* 352 */           this.subSlots.add(new Slot(this.originX, this.originY, i, j));
/* 353 */           int k = this.width - i;
/* 354 */           int l = this.height - j;
/*     */           
/* 356 */           if (l > 0 && k > 0) {
/*     */             
/* 358 */             int i1 = Math.max(this.height, k);
/* 359 */             int j1 = Math.max(this.width, l);
/*     */             
/* 361 */             if (i1 >= j1)
/*     */             {
/* 363 */               this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/* 364 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, this.height));
/*     */             }
/*     */             else
/*     */             {
/* 368 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/* 369 */               this.subSlots.add(new Slot(this.originX, this.originY + j, this.width, l));
/*     */             }
/*     */           
/* 372 */           } else if (k == 0) {
/*     */             
/* 374 */             this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/*     */           }
/* 376 */           else if (l == 0) {
/*     */             
/* 378 */             this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/*     */           } 
/*     */         } 
/*     */         
/* 382 */         for (Slot stitcher$slot : this.subSlots) {
/*     */           
/* 384 */           if (stitcher$slot.addSlot(holderIn))
/*     */           {
/* 386 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 390 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 395 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void getAllStitchSlots(List<Slot> p_94184_1_) {
/* 402 */       if (this.holder != null) {
/*     */         
/* 404 */         p_94184_1_.add(this);
/*     */       }
/* 406 */       else if (this.subSlots != null) {
/*     */         
/* 408 */         for (Slot stitcher$slot : this.subSlots)
/*     */         {
/* 410 */           stitcher$slot.getAllStitchSlots(p_94184_1_);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 417 */       return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\texture\Stitcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */