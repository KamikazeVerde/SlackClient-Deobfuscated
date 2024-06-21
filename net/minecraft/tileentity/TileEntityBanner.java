/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityBanner
/*     */   extends TileEntity
/*     */ {
/*     */   private int baseColor;
/*     */   private NBTTagList patterns;
/*     */   private boolean field_175119_g;
/*     */   private List<EnumBannerPattern> patternList;
/*     */   private List<EnumDyeColor> colorList;
/*     */   private String patternResourceLocation;
/*     */   
/*     */   public void setItemValues(ItemStack stack) {
/*  32 */     this.patterns = null;
/*     */     
/*  34 */     if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*     */       
/*  36 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("BlockEntityTag");
/*     */       
/*  38 */       if (nbttagcompound.hasKey("Patterns"))
/*     */       {
/*  40 */         this.patterns = (NBTTagList)nbttagcompound.getTagList("Patterns", 10).copy();
/*     */       }
/*     */       
/*  43 */       if (nbttagcompound.hasKey("Base", 99))
/*     */       {
/*  45 */         this.baseColor = nbttagcompound.getInteger("Base");
/*     */       }
/*     */       else
/*     */       {
/*  49 */         this.baseColor = stack.getMetadata() & 0xF;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  54 */       this.baseColor = stack.getMetadata() & 0xF;
/*     */     } 
/*     */     
/*  57 */     this.patternList = null;
/*  58 */     this.colorList = null;
/*  59 */     this.patternResourceLocation = "";
/*  60 */     this.field_175119_g = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  65 */     super.writeToNBT(compound);
/*  66 */     func_181020_a(compound, this.baseColor, this.patterns);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_181020_a(NBTTagCompound p_181020_0_, int p_181020_1_, NBTTagList p_181020_2_) {
/*  71 */     p_181020_0_.setInteger("Base", p_181020_1_);
/*     */     
/*  73 */     if (p_181020_2_ != null)
/*     */     {
/*  75 */       p_181020_0_.setTag("Patterns", (NBTBase)p_181020_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  81 */     super.readFromNBT(compound);
/*  82 */     this.baseColor = compound.getInteger("Base");
/*  83 */     this.patterns = compound.getTagList("Patterns", 10);
/*  84 */     this.patternList = null;
/*  85 */     this.colorList = null;
/*  86 */     this.patternResourceLocation = null;
/*  87 */     this.field_175119_g = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/*  96 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  97 */     writeToNBT(nbttagcompound);
/*  98 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 6, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseColor() {
/* 103 */     return this.baseColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBaseColor(ItemStack stack) {
/* 108 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 109 */     return (nbttagcompound != null && nbttagcompound.hasKey("Base")) ? nbttagcompound.getInteger("Base") : stack.getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPatterns(ItemStack stack) {
/* 119 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 120 */     return (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) ? nbttagcompound.getTagList("Patterns", 10).tagCount() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnumBannerPattern> getPatternList() {
/* 125 */     initializeBannerData();
/* 126 */     return this.patternList;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList func_181021_d() {
/* 131 */     return this.patterns;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnumDyeColor> getColorList() {
/* 136 */     initializeBannerData();
/* 137 */     return this.colorList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_175116_e() {
/* 142 */     initializeBannerData();
/* 143 */     return this.patternResourceLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeBannerData() {
/* 152 */     if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null)
/*     */     {
/* 154 */       if (!this.field_175119_g) {
/*     */         
/* 156 */         this.patternResourceLocation = "";
/*     */       }
/*     */       else {
/*     */         
/* 160 */         this.patternList = Lists.newArrayList();
/* 161 */         this.colorList = Lists.newArrayList();
/* 162 */         this.patternList.add(EnumBannerPattern.BASE);
/* 163 */         this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
/* 164 */         this.patternResourceLocation = "b" + this.baseColor;
/*     */         
/* 166 */         if (this.patterns != null)
/*     */         {
/* 168 */           for (int i = 0; i < this.patterns.tagCount(); i++) {
/*     */             
/* 170 */             NBTTagCompound nbttagcompound = this.patterns.getCompoundTagAt(i);
/* 171 */             EnumBannerPattern tileentitybanner$enumbannerpattern = EnumBannerPattern.getPatternByID(nbttagcompound.getString("Pattern"));
/*     */             
/* 173 */             if (tileentitybanner$enumbannerpattern != null) {
/*     */               
/* 175 */               this.patternList.add(tileentitybanner$enumbannerpattern);
/* 176 */               int j = nbttagcompound.getInteger("Color");
/* 177 */               this.colorList.add(EnumDyeColor.byDyeDamage(j));
/* 178 */               this.patternResourceLocation += tileentitybanner$enumbannerpattern.getPatternID() + j;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeBannerData(ItemStack stack) {
/* 193 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*     */     
/* 195 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9)) {
/*     */       
/* 197 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 199 */       if (nbttaglist.tagCount() > 0) {
/*     */         
/* 201 */         nbttaglist.removeTag(nbttaglist.tagCount() - 1);
/*     */         
/* 203 */         if (nbttaglist.hasNoTags()) {
/*     */           
/* 205 */           stack.getTagCompound().removeTag("BlockEntityTag");
/*     */           
/* 207 */           if (stack.getTagCompound().hasNoTags())
/*     */           {
/* 209 */             stack.setTagCompound(null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum EnumBannerPattern
/*     */   {
/* 218 */     BASE("base", "b"),
/* 219 */     SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
/* 220 */     SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
/* 221 */     SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
/* 222 */     SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
/* 223 */     STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
/* 224 */     STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
/* 225 */     STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
/* 226 */     STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
/* 227 */     STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
/* 228 */     STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
/* 229 */     STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
/* 230 */     STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
/* 231 */     STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
/* 232 */     CROSS("cross", "cr", "# #", " # ", "# #"),
/* 233 */     STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
/* 234 */     TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
/* 235 */     TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
/* 236 */     TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
/* 237 */     TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
/* 238 */     DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
/* 239 */     DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
/* 240 */     DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
/* 241 */     DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
/* 242 */     CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
/* 243 */     RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
/* 244 */     HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
/* 245 */     HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
/* 246 */     HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
/* 247 */     HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
/* 248 */     BORDER("border", "bo", "###", "# #", "###"),
/* 249 */     CURLY_BORDER("curly_border", "cbo", (String)new ItemStack(Blocks.vine)),
/* 250 */     CREEPER("creeper", "cre", (String)new ItemStack(Items.skull, 1, 4)),
/* 251 */     GRADIENT("gradient", "gra", "# #", " # ", " # "),
/* 252 */     GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
/* 253 */     BRICKS("bricks", "bri", (String)new ItemStack(Blocks.brick_block)),
/* 254 */     SKULL("skull", "sku", (String)new ItemStack(Items.skull, 1, 1)),
/* 255 */     FLOWER("flower", "flo", (String)new ItemStack((Block)Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())),
/* 256 */     MOJANG("mojang", "moj", (String)new ItemStack(Items.golden_apple, 1, 1));
/*     */     
/*     */     private String patternName;
/*     */     
/*     */     private String patternID;
/*     */     private String[] craftingLayers;
/*     */     private ItemStack patternCraftingStack;
/*     */     
/*     */     EnumBannerPattern(String name, String id) {
/* 265 */       this.craftingLayers = new String[3];
/* 266 */       this.patternName = name;
/* 267 */       this.patternID = id;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     EnumBannerPattern(String name, String id, ItemStack craftingItem) {
/* 273 */       this.patternCraftingStack = craftingItem;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     EnumBannerPattern(String name, String id, String craftingTop, String craftingMid, String craftingBot) {
/* 279 */       this.craftingLayers[0] = craftingTop;
/* 280 */       this.craftingLayers[1] = craftingMid;
/* 281 */       this.craftingLayers[2] = craftingBot;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPatternName() {
/* 286 */       return this.patternName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPatternID() {
/* 291 */       return this.patternID;
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] getCraftingLayers() {
/* 296 */       return this.craftingLayers;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasValidCrafting() {
/* 301 */       return (this.patternCraftingStack != null || this.craftingLayers[0] != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCraftingStack() {
/* 306 */       return (this.patternCraftingStack != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingStack() {
/* 311 */       return this.patternCraftingStack;
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumBannerPattern getPatternByID(String id) {
/* 316 */       for (EnumBannerPattern tileentitybanner$enumbannerpattern : values()) {
/*     */         
/* 318 */         if (tileentitybanner$enumbannerpattern.patternID.equals(id))
/*     */         {
/* 320 */           return tileentitybanner$enumbannerpattern;
/*     */         }
/*     */       } 
/*     */       
/* 324 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */