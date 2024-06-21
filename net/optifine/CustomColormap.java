/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomColormap
/*     */   implements CustomColors.IColorizer {
/*  28 */   public String name = null;
/*  29 */   public String basePath = null;
/*  30 */   private int format = -1;
/*  31 */   private MatchBlock[] matchBlocks = null;
/*  32 */   private String source = null;
/*  33 */   private int color = -1;
/*  34 */   private int yVariance = 0;
/*  35 */   private int yOffset = 0;
/*  36 */   private int width = 0;
/*  37 */   private int height = 0;
/*  38 */   private int[] colors = null;
/*  39 */   private float[][] colorsRgb = (float[][])null;
/*     */   private static final int FORMAT_UNKNOWN = -1;
/*     */   private static final int FORMAT_VANILLA = 0;
/*     */   private static final int FORMAT_GRID = 1;
/*     */   private static final int FORMAT_FIXED = 2;
/*     */   public static final String FORMAT_VANILLA_STRING = "vanilla";
/*     */   public static final String FORMAT_GRID_STRING = "grid";
/*     */   public static final String FORMAT_FIXED_STRING = "fixed";
/*  47 */   public static final String[] FORMAT_STRINGS = new String[] { "vanilla", "grid", "fixed" };
/*     */   
/*     */   public static final String KEY_FORMAT = "format";
/*     */   public static final String KEY_BLOCKS = "blocks";
/*     */   public static final String KEY_SOURCE = "source";
/*     */   public static final String KEY_COLOR = "color";
/*     */   public static final String KEY_Y_VARIANCE = "yVariance";
/*     */   public static final String KEY_Y_OFFSET = "yOffset";
/*     */   
/*     */   public CustomColormap(Properties props, String path, int width, int height, String formatDefault) {
/*  57 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/*  58 */     this.name = connectedparser.parseName(path);
/*  59 */     this.basePath = connectedparser.parseBasePath(path);
/*  60 */     this.format = parseFormat(props.getProperty("format", formatDefault));
/*  61 */     this.matchBlocks = connectedparser.parseMatchBlocks(props.getProperty("blocks"));
/*  62 */     this.source = parseTexture(props.getProperty("source"), path, this.basePath);
/*  63 */     this.color = ConnectedParser.parseColor(props.getProperty("color"), -1);
/*  64 */     this.yVariance = connectedparser.parseInt(props.getProperty("yVariance"), 0);
/*  65 */     this.yOffset = connectedparser.parseInt(props.getProperty("yOffset"), 0);
/*  66 */     this.width = width;
/*  67 */     this.height = height;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseFormat(String str) {
/*  72 */     if (str == null)
/*     */     {
/*  74 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  78 */     str = str.trim();
/*     */     
/*  80 */     if (str.equals("vanilla"))
/*     */     {
/*  82 */       return 0;
/*     */     }
/*  84 */     if (str.equals("grid"))
/*     */     {
/*  86 */       return 1;
/*     */     }
/*  88 */     if (str.equals("fixed"))
/*     */     {
/*  90 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*  94 */     warn("Unknown format: " + str);
/*  95 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 102 */     if (this.format != 0 && this.format != 1) {
/*     */       
/* 104 */       if (this.format != 2)
/*     */       {
/* 106 */         return false;
/*     */       }
/*     */       
/* 109 */       if (this.color < 0)
/*     */       {
/* 111 */         this.color = 16777215;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 116 */       if (this.source == null) {
/*     */         
/* 118 */         warn("Source not defined: " + path);
/* 119 */         return false;
/*     */       } 
/*     */       
/* 122 */       readColors();
/*     */       
/* 124 */       if (this.colors == null)
/*     */       {
/* 126 */         return false;
/*     */       }
/*     */       
/* 129 */       if (this.color < 0) {
/*     */         
/* 131 */         if (this.format == 0)
/*     */         {
/* 133 */           this.color = getColor(127, 127);
/*     */         }
/*     */         
/* 136 */         if (this.format == 1)
/*     */         {
/* 138 */           this.color = getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidMatchBlocks(String path) {
/* 148 */     if (this.matchBlocks == null) {
/*     */       
/* 150 */       this.matchBlocks = detectMatchBlocks();
/*     */       
/* 152 */       if (this.matchBlocks == null) {
/*     */         
/* 154 */         warn("Match blocks not defined: " + path);
/* 155 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private MatchBlock[] detectMatchBlocks() {
/* 164 */     Block block = Block.getBlockFromName(this.name);
/*     */     
/* 166 */     if (block != null)
/*     */     {
/* 168 */       return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(block)) };
/*     */     }
/*     */ 
/*     */     
/* 172 */     Pattern pattern = Pattern.compile("^block([0-9]+).*$");
/* 173 */     Matcher matcher = pattern.matcher(this.name);
/*     */     
/* 175 */     if (matcher.matches()) {
/*     */       
/* 177 */       String s = matcher.group(1);
/* 178 */       int i = Config.parseInt(s, -1);
/*     */       
/* 180 */       if (i >= 0)
/*     */       {
/* 182 */         return new MatchBlock[] { new MatchBlock(i) };
/*     */       }
/*     */     } 
/*     */     
/* 186 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/* 187 */     MatchBlock[] amatchblock = connectedparser.parseMatchBlock(this.name);
/* 188 */     return (amatchblock != null) ? amatchblock : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readColors() {
/*     */     try {
/* 196 */       this.colors = null;
/*     */       
/* 198 */       if (this.source == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 203 */       String s = this.source + ".png";
/* 204 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 205 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 207 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 212 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/*     */       
/* 214 */       if (bufferedimage == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 219 */       int i = bufferedimage.getWidth();
/* 220 */       int j = bufferedimage.getHeight();
/* 221 */       boolean flag = (this.width < 0 || this.width == i);
/* 222 */       boolean flag1 = (this.height < 0 || this.height == j);
/*     */       
/* 224 */       if (!flag || !flag1)
/*     */       {
/* 226 */         dbg("Non-standard palette size: " + i + "x" + j + ", should be: " + this.width + "x" + this.height + ", path: " + s);
/*     */       }
/*     */       
/* 229 */       this.width = i;
/* 230 */       this.height = j;
/*     */       
/* 232 */       if (this.width <= 0 || this.height <= 0) {
/*     */         
/* 234 */         warn("Invalid palette size: " + i + "x" + j + ", path: " + s);
/*     */         
/*     */         return;
/*     */       } 
/* 238 */       this.colors = new int[i * j];
/* 239 */       bufferedimage.getRGB(0, 0, i, j, this.colors, 0, i);
/*     */     }
/* 241 */     catch (IOException ioexception) {
/*     */       
/* 243 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 249 */     Config.dbg("CustomColors: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 254 */     Config.warn("CustomColors: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String parseTexture(String texStr, String path, String basePath) {
/* 259 */     if (texStr != null) {
/*     */       
/* 261 */       texStr = texStr.trim();
/* 262 */       String s1 = ".png";
/*     */       
/* 264 */       if (texStr.endsWith(s1))
/*     */       {
/* 266 */         texStr = texStr.substring(0, texStr.length() - s1.length());
/*     */       }
/*     */       
/* 269 */       texStr = fixTextureName(texStr, basePath);
/* 270 */       return texStr;
/*     */     } 
/*     */ 
/*     */     
/* 274 */     String s = path;
/* 275 */     int i = path.lastIndexOf('/');
/*     */     
/* 277 */     if (i >= 0)
/*     */     {
/* 279 */       s = path.substring(i + 1);
/*     */     }
/*     */     
/* 282 */     int j = s.lastIndexOf('.');
/*     */     
/* 284 */     if (j >= 0)
/*     */     {
/* 286 */       s = s.substring(0, j);
/*     */     }
/*     */     
/* 289 */     s = fixTextureName(s, basePath);
/* 290 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixTextureName(String iconName, String basePath) {
/* 296 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*     */     
/* 298 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/"))
/*     */     {
/* 300 */       iconName = basePath + "/" + iconName;
/*     */     }
/*     */     
/* 303 */     if (iconName.endsWith(".png"))
/*     */     {
/* 305 */       iconName = iconName.substring(0, iconName.length() - 4);
/*     */     }
/*     */     
/* 308 */     String s = "textures/blocks/";
/*     */     
/* 310 */     if (iconName.startsWith(s))
/*     */     {
/* 312 */       iconName = iconName.substring(s.length());
/*     */     }
/*     */     
/* 315 */     if (iconName.startsWith("/"))
/*     */     {
/* 317 */       iconName = iconName.substring(1);
/*     */     }
/*     */     
/* 320 */     return iconName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesBlock(BlockStateBase blockState) {
/* 325 */     return Matches.block(blockState, this.matchBlocks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorRandom() {
/* 330 */     if (this.format == 2)
/*     */     {
/* 332 */       return this.color;
/*     */     }
/*     */ 
/*     */     
/* 336 */     int i = CustomColors.random.nextInt(this.colors.length);
/* 337 */     return this.colors[i];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(int index) {
/* 343 */     index = Config.limit(index, 0, this.colors.length - 1);
/* 344 */     return this.colors[index] & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(int cx, int cy) {
/* 349 */     cx = Config.limit(cx, 0, this.width - 1);
/* 350 */     cy = Config.limit(cy, 0, this.height - 1);
/* 351 */     return this.colors[cy * this.width + cx] & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][] getColorsRgb() {
/* 356 */     if (this.colorsRgb == null)
/*     */     {
/* 358 */       this.colorsRgb = toRgb(this.colors);
/*     */     }
/*     */     
/* 361 */     return this.colorsRgb;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/* 366 */     return getColor(blockAccess, blockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockAccess blockAccess, BlockPos blockPos) {
/* 371 */     BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/* 372 */     return getColor(biomegenbase, blockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isColorConstant() {
/* 377 */     return (this.format == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(BiomeGenBase biome, BlockPos blockPos) {
/* 382 */     return (this.format == 0) ? getColorVanilla(biome, blockPos) : ((this.format == 1) ? getColorGrid(biome, blockPos) : this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorSmooth(IBlockAccess blockAccess, double x, double y, double z, int radius) {
/* 387 */     if (this.format == 2)
/*     */     {
/* 389 */       return this.color;
/*     */     }
/*     */ 
/*     */     
/* 393 */     int i = MathHelper.floor_double(x);
/* 394 */     int j = MathHelper.floor_double(y);
/* 395 */     int k = MathHelper.floor_double(z);
/* 396 */     int l = 0;
/* 397 */     int i1 = 0;
/* 398 */     int j1 = 0;
/* 399 */     int k1 = 0;
/* 400 */     BlockPosM blockposm = new BlockPosM(0, 0, 0);
/*     */     
/* 402 */     for (int l1 = i - radius; l1 <= i + radius; l1++) {
/*     */       
/* 404 */       for (int i2 = k - radius; i2 <= k + radius; i2++) {
/*     */         
/* 406 */         blockposm.setXyz(l1, j, i2);
/* 407 */         int j2 = getColor(blockAccess, blockposm);
/* 408 */         l += j2 >> 16 & 0xFF;
/* 409 */         i1 += j2 >> 8 & 0xFF;
/* 410 */         j1 += j2 & 0xFF;
/* 411 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 415 */     int k2 = l / k1;
/* 416 */     int l2 = i1 / k1;
/* 417 */     int i3 = j1 / k1;
/* 418 */     return k2 << 16 | l2 << 8 | i3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getColorVanilla(BiomeGenBase biome, BlockPos blockPos) {
/* 424 */     double d0 = MathHelper.clamp_float(biome.getFloatTemperature(blockPos), 0.0F, 1.0F);
/* 425 */     double d1 = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
/* 426 */     d1 *= d0;
/* 427 */     int i = (int)((1.0D - d0) * (this.width - 1));
/* 428 */     int j = (int)((1.0D - d1) * (this.height - 1));
/* 429 */     return getColor(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColorGrid(BiomeGenBase biome, BlockPos blockPos) {
/* 434 */     int i = biome.biomeID;
/* 435 */     int j = blockPos.getY() - this.yOffset;
/*     */     
/* 437 */     if (this.yVariance > 0) {
/*     */       
/* 439 */       int k = blockPos.getX() << 16 + blockPos.getZ();
/* 440 */       int l = Config.intHash(k);
/* 441 */       int i1 = this.yVariance * 2 + 1;
/* 442 */       int j1 = (l & 0xFF) % i1 - this.yVariance;
/* 443 */       j += j1;
/*     */     } 
/*     */     
/* 446 */     return getColor(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 451 */     return (this.format == 2) ? 1 : this.colors.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 456 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 461 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float[][] toRgb(int[] cols) {
/* 466 */     float[][] afloat = new float[cols.length][3];
/*     */     
/* 468 */     for (int i = 0; i < cols.length; i++) {
/*     */       
/* 470 */       int j = cols[i];
/* 471 */       float f = (j >> 16 & 0xFF) / 255.0F;
/* 472 */       float f1 = (j >> 8 & 0xFF) / 255.0F;
/* 473 */       float f2 = (j & 0xFF) / 255.0F;
/* 474 */       float[] afloat1 = afloat[i];
/* 475 */       afloat1[0] = f;
/* 476 */       afloat1[1] = f1;
/* 477 */       afloat1[2] = f2;
/*     */     } 
/*     */     
/* 480 */     return afloat;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMatchBlock(MatchBlock mb) {
/* 485 */     if (this.matchBlocks == null)
/*     */     {
/* 487 */       this.matchBlocks = new MatchBlock[0];
/*     */     }
/*     */     
/* 490 */     this.matchBlocks = (MatchBlock[])Config.addObjectToArray((Object[])this.matchBlocks, mb);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMatchBlock(int blockId, int metadata) {
/* 495 */     MatchBlock matchblock = getMatchBlock(blockId);
/*     */     
/* 497 */     if (matchblock != null) {
/*     */       
/* 499 */       if (metadata >= 0)
/*     */       {
/* 501 */         matchblock.addMetadata(metadata);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 506 */       addMatchBlock(new MatchBlock(blockId, metadata));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private MatchBlock getMatchBlock(int blockId) {
/* 512 */     if (this.matchBlocks == null)
/*     */     {
/* 514 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 518 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/* 520 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 522 */       if (matchblock.getBlockId() == blockId)
/*     */       {
/* 524 */         return matchblock;
/*     */       }
/*     */     } 
/*     */     
/* 528 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getMatchBlockIds() {
/* 534 */     if (this.matchBlocks == null)
/*     */     {
/* 536 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 540 */     Set<Integer> set = new HashSet();
/*     */     
/* 542 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/* 544 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 546 */       if (matchblock.getBlockId() >= 0)
/*     */       {
/* 548 */         set.add(Integer.valueOf(matchblock.getBlockId()));
/*     */       }
/*     */     } 
/*     */     
/* 552 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 553 */     int[] aint = new int[ainteger.length];
/*     */     
/* 555 */     for (int j = 0; j < ainteger.length; j++)
/*     */     {
/* 557 */       aint[j] = ainteger[j].intValue();
/*     */     }
/*     */     
/* 560 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 566 */     return "" + this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", source: " + this.source;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomColormap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */