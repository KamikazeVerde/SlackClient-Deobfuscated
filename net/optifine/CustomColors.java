/*      */ package net.optifine;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockRedstoneWire;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMonsterPlacer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.ColorizerFoliage;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.config.MatchBlock;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.util.EntityUtils;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.StrUtils;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ 
/*      */ public class CustomColors
/*      */ {
/*   56 */   private static String paletteFormatDefault = "vanilla";
/*   57 */   private static CustomColormap waterColors = null;
/*   58 */   private static CustomColormap foliagePineColors = null;
/*   59 */   private static CustomColormap foliageBirchColors = null;
/*   60 */   private static CustomColormap swampFoliageColors = null;
/*   61 */   private static CustomColormap swampGrassColors = null;
/*   62 */   private static CustomColormap[] colorsBlockColormaps = null;
/*   63 */   private static CustomColormap[][] blockColormaps = (CustomColormap[][])null;
/*   64 */   private static CustomColormap skyColors = null;
/*   65 */   private static CustomColorFader skyColorFader = new CustomColorFader();
/*   66 */   private static CustomColormap fogColors = null;
/*   67 */   private static CustomColorFader fogColorFader = new CustomColorFader();
/*   68 */   private static CustomColormap underwaterColors = null;
/*   69 */   private static CustomColorFader underwaterColorFader = new CustomColorFader();
/*   70 */   private static CustomColormap underlavaColors = null;
/*   71 */   private static CustomColorFader underlavaColorFader = new CustomColorFader();
/*   72 */   private static LightMapPack[] lightMapPacks = null;
/*   73 */   private static int lightmapMinDimensionId = 0;
/*   74 */   private static CustomColormap redstoneColors = null;
/*   75 */   private static CustomColormap xpOrbColors = null;
/*   76 */   private static int xpOrbTime = -1;
/*   77 */   private static CustomColormap durabilityColors = null;
/*   78 */   private static CustomColormap stemColors = null;
/*   79 */   private static CustomColormap stemMelonColors = null;
/*   80 */   private static CustomColormap stemPumpkinColors = null;
/*   81 */   private static CustomColormap myceliumParticleColors = null;
/*      */   private static boolean useDefaultGrassFoliageColors = true;
/*   83 */   private static int particleWaterColor = -1;
/*   84 */   private static int particlePortalColor = -1;
/*   85 */   private static int lilyPadColor = -1;
/*   86 */   private static int expBarTextColor = -1;
/*   87 */   private static int bossTextColor = -1;
/*   88 */   private static int signTextColor = -1;
/*   89 */   private static Vec3 fogColorNether = null;
/*   90 */   private static Vec3 fogColorEnd = null;
/*   91 */   private static Vec3 skyColorEnd = null;
/*   92 */   private static int[] spawnEggPrimaryColors = null;
/*   93 */   private static int[] spawnEggSecondaryColors = null;
/*   94 */   private static float[][] wolfCollarColors = (float[][])null;
/*   95 */   private static float[][] sheepColors = (float[][])null;
/*   96 */   private static int[] textColors = null;
/*   97 */   private static int[] mapColorsOriginal = null;
/*   98 */   private static int[] potionColors = null;
/*   99 */   private static final IBlockState BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
/*  100 */   private static final IBlockState BLOCK_STATE_WATER = Blocks.water.getDefaultState();
/*  101 */   public static Random random = new Random();
/*  102 */   private static final IColorizer COLORIZER_GRASS = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  106 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  107 */         return (CustomColors.swampGrassColors != null && biomegenbase == BiomeGenBase.swampland) ? CustomColors.swampGrassColors.getColor(biomegenbase, blockPos) : biomegenbase.getGrassColorAtPos(blockPos);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  111 */         return false;
/*      */       }
/*      */     };
/*  114 */   private static final IColorizer COLORIZER_FOLIAGE = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  118 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  119 */         return (CustomColors.swampFoliageColors != null && biomegenbase == BiomeGenBase.swampland) ? CustomColors.swampFoliageColors.getColor(biomegenbase, blockPos) : biomegenbase.getFoliageColorAtPos(blockPos);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  123 */         return false;
/*      */       }
/*      */     };
/*  126 */   private static final IColorizer COLORIZER_FOLIAGE_PINE = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  130 */         return (CustomColors.foliagePineColors != null) ? CustomColors.foliagePineColors.getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorPine();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  134 */         return (CustomColors.foliagePineColors == null);
/*      */       }
/*      */     };
/*  137 */   private static final IColorizer COLORIZER_FOLIAGE_BIRCH = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  141 */         return (CustomColors.foliageBirchColors != null) ? CustomColors.foliageBirchColors.getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorBirch();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  145 */         return (CustomColors.foliageBirchColors == null);
/*      */       }
/*      */     };
/*  148 */   private static final IColorizer COLORIZER_WATER = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  152 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  153 */         return (CustomColors.waterColors != null) ? CustomColors.waterColors.getColor(biomegenbase, blockPos) : (Reflector.ForgeBiome_getWaterColorMultiplier.exists() ? Reflector.callInt(biomegenbase, Reflector.ForgeBiome_getWaterColorMultiplier, new Object[0]) : biomegenbase.waterColorMultiplier);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  157 */         return false;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public static void update() {
/*  163 */     paletteFormatDefault = "vanilla";
/*  164 */     waterColors = null;
/*  165 */     foliageBirchColors = null;
/*  166 */     foliagePineColors = null;
/*  167 */     swampGrassColors = null;
/*  168 */     swampFoliageColors = null;
/*  169 */     skyColors = null;
/*  170 */     fogColors = null;
/*  171 */     underwaterColors = null;
/*  172 */     underlavaColors = null;
/*  173 */     redstoneColors = null;
/*  174 */     xpOrbColors = null;
/*  175 */     xpOrbTime = -1;
/*  176 */     durabilityColors = null;
/*  177 */     stemColors = null;
/*  178 */     myceliumParticleColors = null;
/*  179 */     lightMapPacks = null;
/*  180 */     particleWaterColor = -1;
/*  181 */     particlePortalColor = -1;
/*  182 */     lilyPadColor = -1;
/*  183 */     expBarTextColor = -1;
/*  184 */     bossTextColor = -1;
/*  185 */     signTextColor = -1;
/*  186 */     fogColorNether = null;
/*  187 */     fogColorEnd = null;
/*  188 */     skyColorEnd = null;
/*  189 */     colorsBlockColormaps = null;
/*  190 */     blockColormaps = (CustomColormap[][])null;
/*  191 */     useDefaultGrassFoliageColors = true;
/*  192 */     spawnEggPrimaryColors = null;
/*  193 */     spawnEggSecondaryColors = null;
/*  194 */     wolfCollarColors = (float[][])null;
/*  195 */     sheepColors = (float[][])null;
/*  196 */     textColors = null;
/*  197 */     setMapColors(mapColorsOriginal);
/*  198 */     potionColors = null;
/*  199 */     paletteFormatDefault = getValidProperty("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
/*  200 */     String s = "mcpatcher/colormap/";
/*  201 */     String[] astring = { "water.png", "watercolorX.png" };
/*  202 */     waterColors = getCustomColors(s, astring, 256, 256);
/*  203 */     updateUseDefaultGrassFoliageColors();
/*      */     
/*  205 */     if (Config.isCustomColors()) {
/*      */       
/*  207 */       String[] astring1 = { "pine.png", "pinecolor.png" };
/*  208 */       foliagePineColors = getCustomColors(s, astring1, 256, 256);
/*  209 */       String[] astring2 = { "birch.png", "birchcolor.png" };
/*  210 */       foliageBirchColors = getCustomColors(s, astring2, 256, 256);
/*  211 */       String[] astring3 = { "swampgrass.png", "swampgrasscolor.png" };
/*  212 */       swampGrassColors = getCustomColors(s, astring3, 256, 256);
/*  213 */       String[] astring4 = { "swampfoliage.png", "swampfoliagecolor.png" };
/*  214 */       swampFoliageColors = getCustomColors(s, astring4, 256, 256);
/*  215 */       String[] astring5 = { "sky0.png", "skycolor0.png" };
/*  216 */       skyColors = getCustomColors(s, astring5, 256, 256);
/*  217 */       String[] astring6 = { "fog0.png", "fogcolor0.png" };
/*  218 */       fogColors = getCustomColors(s, astring6, 256, 256);
/*  219 */       String[] astring7 = { "underwater.png", "underwatercolor.png" };
/*  220 */       underwaterColors = getCustomColors(s, astring7, 256, 256);
/*  221 */       String[] astring8 = { "underlava.png", "underlavacolor.png" };
/*  222 */       underlavaColors = getCustomColors(s, astring8, 256, 256);
/*  223 */       String[] astring9 = { "redstone.png", "redstonecolor.png" };
/*  224 */       redstoneColors = getCustomColors(s, astring9, 16, 1);
/*  225 */       xpOrbColors = getCustomColors(s + "xporb.png", -1, -1);
/*  226 */       durabilityColors = getCustomColors(s + "durability.png", -1, -1);
/*  227 */       String[] astring10 = { "stem.png", "stemcolor.png" };
/*  228 */       stemColors = getCustomColors(s, astring10, 8, 1);
/*  229 */       stemPumpkinColors = getCustomColors(s + "pumpkinstem.png", 8, 1);
/*  230 */       stemMelonColors = getCustomColors(s + "melonstem.png", 8, 1);
/*  231 */       String[] astring11 = { "myceliumparticle.png", "myceliumparticlecolor.png" };
/*  232 */       myceliumParticleColors = getCustomColors(s, astring11, -1, -1);
/*  233 */       Pair<LightMapPack[], Integer> pair = parseLightMapPacks();
/*  234 */       lightMapPacks = (LightMapPack[])pair.getLeft();
/*  235 */       lightmapMinDimensionId = ((Integer)pair.getRight()).intValue();
/*  236 */       readColorProperties("mcpatcher/color.properties");
/*  237 */       blockColormaps = readBlockColormaps(new String[] { s + "custom/", s + "blocks/" }, colorsBlockColormaps, 256, 256);
/*  238 */       updateUseDefaultGrassFoliageColors();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getValidProperty(String fileName, String key, String[] validValues, String valDef) {
/*      */     try {
/*  246 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  247 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  249 */       if (inputstream == null)
/*      */       {
/*  251 */         return valDef;
/*      */       }
/*      */ 
/*      */       
/*  255 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  256 */       propertiesOrdered.load(inputstream);
/*  257 */       inputstream.close();
/*  258 */       String s = propertiesOrdered.getProperty(key);
/*      */       
/*  260 */       if (s == null)
/*      */       {
/*  262 */         return valDef;
/*      */       }
/*      */ 
/*      */       
/*  266 */       List<String> list = Arrays.asList(validValues);
/*      */       
/*  268 */       if (!list.contains(s)) {
/*      */         
/*  270 */         warn("Invalid value: " + key + "=" + s);
/*  271 */         warn("Expected values: " + Config.arrayToString((Object[])validValues));
/*  272 */         return valDef;
/*      */       } 
/*      */ 
/*      */       
/*  276 */       dbg("" + key + "=" + s);
/*  277 */       return s;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  282 */     catch (FileNotFoundException var9) {
/*      */       
/*  284 */       return valDef;
/*      */     }
/*  286 */     catch (IOException ioexception) {
/*      */       
/*  288 */       ioexception.printStackTrace();
/*  289 */       return valDef;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
/*  295 */     String s = "mcpatcher/lightmap/world";
/*  296 */     String s1 = ".png";
/*  297 */     String[] astring = ResUtils.collectFiles(s, s1);
/*  298 */     Map<Integer, String> map = new HashMap<>();
/*      */     
/*  300 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  302 */       String s2 = astring[i];
/*  303 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/*  304 */       int j = Config.parseInt(s3, -2147483648);
/*      */       
/*  306 */       if (j == Integer.MIN_VALUE) {
/*      */         
/*  308 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*      */       }
/*      */       else {
/*      */         
/*  312 */         map.put(Integer.valueOf(j), s2);
/*      */       } 
/*      */     } 
/*      */     
/*  316 */     Set<Integer> set = map.keySet();
/*  317 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/*  318 */     Arrays.sort((Object[])ainteger);
/*      */     
/*  320 */     if (ainteger.length <= 0)
/*      */     {
/*  322 */       return (Pair<LightMapPack[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*      */     }
/*      */ 
/*      */     
/*  326 */     int j1 = ainteger[0].intValue();
/*  327 */     int k1 = ainteger[ainteger.length - 1].intValue();
/*  328 */     int k = k1 - j1 + 1;
/*  329 */     CustomColormap[] acustomcolormap = new CustomColormap[k];
/*      */     
/*  331 */     for (int l = 0; l < ainteger.length; l++) {
/*      */       
/*  333 */       Integer integer = ainteger[l];
/*  334 */       String s4 = map.get(integer);
/*  335 */       CustomColormap customcolormap = getCustomColors(s4, -1, -1);
/*      */       
/*  337 */       if (customcolormap != null)
/*      */       {
/*  339 */         if (customcolormap.getWidth() < 16) {
/*      */           
/*  341 */           warn("Invalid lightmap width: " + customcolormap.getWidth() + ", path: " + s4);
/*      */         }
/*      */         else {
/*      */           
/*  345 */           int i1 = integer.intValue() - j1;
/*  346 */           acustomcolormap[i1] = customcolormap;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  351 */     LightMapPack[] alightmappack = new LightMapPack[acustomcolormap.length];
/*      */     
/*  353 */     for (int l1 = 0; l1 < acustomcolormap.length; l1++) {
/*      */       
/*  355 */       CustomColormap customcolormap3 = acustomcolormap[l1];
/*      */       
/*  357 */       if (customcolormap3 != null) {
/*      */         
/*  359 */         String s5 = customcolormap3.name;
/*  360 */         String s6 = customcolormap3.basePath;
/*  361 */         CustomColormap customcolormap1 = getCustomColors(s6 + "/" + s5 + "_rain.png", -1, -1);
/*  362 */         CustomColormap customcolormap2 = getCustomColors(s6 + "/" + s5 + "_thunder.png", -1, -1);
/*  363 */         LightMap lightmap = new LightMap(customcolormap3);
/*  364 */         LightMap lightmap1 = (customcolormap1 != null) ? new LightMap(customcolormap1) : null;
/*  365 */         LightMap lightmap2 = (customcolormap2 != null) ? new LightMap(customcolormap2) : null;
/*  366 */         LightMapPack lightmappack = new LightMapPack(lightmap, lightmap1, lightmap2);
/*  367 */         alightmappack[l1] = lightmappack;
/*      */       } 
/*      */     } 
/*      */     
/*  371 */     return (Pair<LightMapPack[], Integer>)new ImmutablePair(alightmappack, Integer.valueOf(j1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTextureHeight(String path, int defHeight) {
/*      */     try {
/*  379 */       InputStream inputstream = Config.getResourceStream(new ResourceLocation(path));
/*      */       
/*  381 */       if (inputstream == null)
/*      */       {
/*  383 */         return defHeight;
/*      */       }
/*      */ 
/*      */       
/*  387 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*  388 */       inputstream.close();
/*  389 */       return (bufferedimage == null) ? defHeight : bufferedimage.getHeight();
/*      */     
/*      */     }
/*  392 */     catch (IOException var4) {
/*      */       
/*  394 */       return defHeight;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readColorProperties(String fileName) {
/*      */     try {
/*  402 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  403 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  405 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  410 */       dbg("Loading " + fileName);
/*  411 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  412 */       propertiesOrdered.load(inputstream);
/*  413 */       inputstream.close();
/*  414 */       particleWaterColor = readColor((Properties)propertiesOrdered, new String[] { "particle.water", "drop.water" });
/*  415 */       particlePortalColor = readColor((Properties)propertiesOrdered, "particle.portal");
/*  416 */       lilyPadColor = readColor((Properties)propertiesOrdered, "lilypad");
/*  417 */       expBarTextColor = readColor((Properties)propertiesOrdered, "text.xpbar");
/*  418 */       bossTextColor = readColor((Properties)propertiesOrdered, "text.boss");
/*  419 */       signTextColor = readColor((Properties)propertiesOrdered, "text.sign");
/*  420 */       fogColorNether = readColorVec3((Properties)propertiesOrdered, "fog.nether");
/*  421 */       fogColorEnd = readColorVec3((Properties)propertiesOrdered, "fog.end");
/*  422 */       skyColorEnd = readColorVec3((Properties)propertiesOrdered, "sky.end");
/*  423 */       colorsBlockColormaps = readCustomColormaps((Properties)propertiesOrdered, fileName);
/*  424 */       spawnEggPrimaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.shell.", "Spawn egg shell");
/*  425 */       spawnEggSecondaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.spots.", "Spawn egg spot");
/*  426 */       wolfCollarColors = readDyeColors((Properties)propertiesOrdered, fileName, "collar.", "Wolf collar");
/*  427 */       sheepColors = readDyeColors((Properties)propertiesOrdered, fileName, "sheep.", "Sheep");
/*  428 */       textColors = readTextColors((Properties)propertiesOrdered, fileName, "text.code.", "Text");
/*  429 */       int[] aint = readMapColors((Properties)propertiesOrdered, fileName, "map.", "Map");
/*      */       
/*  431 */       if (aint != null) {
/*      */         
/*  433 */         if (mapColorsOriginal == null)
/*      */         {
/*  435 */           mapColorsOriginal = getMapColors();
/*      */         }
/*      */         
/*  438 */         setMapColors(aint);
/*      */       } 
/*      */       
/*  441 */       potionColors = readPotionColors((Properties)propertiesOrdered, fileName, "potion.", "Potion");
/*  442 */       xpOrbTime = Config.parseInt(propertiesOrdered.getProperty("xporb.time"), -1);
/*      */     }
/*  444 */     catch (FileNotFoundException var5) {
/*      */       
/*      */       return;
/*      */     }
/*  448 */     catch (IOException ioexception) {
/*      */       
/*  450 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[] readCustomColormaps(Properties props, String fileName) {
/*  456 */     List<CustomColormap> list = new ArrayList();
/*  457 */     String s = "palette.block.";
/*  458 */     Map<Object, Object> map = new HashMap<>();
/*      */     
/*  460 */     for (Object e : props.keySet()) {
/*      */       
/*  462 */       String s1 = (String)e;
/*  463 */       String s2 = props.getProperty(s1);
/*      */       
/*  465 */       if (s1.startsWith(s))
/*      */       {
/*  467 */         map.put(s1, s2);
/*      */       }
/*      */     } 
/*      */     
/*  471 */     String[] astring = (String[])map.keySet().toArray((Object[])new String[map.size()]);
/*      */     
/*  473 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  475 */       String s6 = astring[j];
/*  476 */       String s3 = props.getProperty(s6);
/*  477 */       dbg("Block palette: " + s6 + " = " + s3);
/*  478 */       String s4 = s6.substring(s.length());
/*  479 */       String s5 = TextureUtils.getBasePath(fileName);
/*  480 */       s4 = TextureUtils.fixResourcePath(s4, s5);
/*  481 */       CustomColormap customcolormap = getCustomColors(s4, 256, 256);
/*      */       
/*  483 */       if (customcolormap == null) {
/*      */         
/*  485 */         warn("Colormap not found: " + s4);
/*      */       }
/*      */       else {
/*      */         
/*  489 */         ConnectedParser connectedparser = new ConnectedParser("CustomColors");
/*  490 */         MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s3);
/*      */         
/*  492 */         if (amatchblock != null && amatchblock.length > 0) {
/*      */           
/*  494 */           for (int i = 0; i < amatchblock.length; i++) {
/*      */             
/*  496 */             MatchBlock matchblock = amatchblock[i];
/*  497 */             customcolormap.addMatchBlock(matchblock);
/*      */           } 
/*      */           
/*  500 */           list.add(customcolormap);
/*      */         }
/*      */         else {
/*      */           
/*  504 */           warn("Invalid match blocks: " + s3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  509 */     if (list.size() <= 0)
/*      */     {
/*  511 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  515 */     CustomColormap[] acustomcolormap = list.<CustomColormap>toArray(new CustomColormap[list.size()]);
/*  516 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int width, int height) {
/*  522 */     String[] astring = ResUtils.collectFiles(basePaths, new String[] { ".properties" });
/*  523 */     Arrays.sort((Object[])astring);
/*  524 */     List list = new ArrayList();
/*      */     
/*  526 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  528 */       String s = astring[i];
/*  529 */       dbg("Block colormap: " + s);
/*      */ 
/*      */       
/*      */       try {
/*  533 */         ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);
/*  534 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */         
/*  536 */         if (inputstream == null)
/*      */         {
/*  538 */           warn("File not found: " + s);
/*      */         }
/*      */         else
/*      */         {
/*  542 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  543 */           propertiesOrdered.load(inputstream);
/*  544 */           CustomColormap customcolormap = new CustomColormap((Properties)propertiesOrdered, s, width, height, paletteFormatDefault);
/*      */           
/*  546 */           if (customcolormap.isValid(s) && customcolormap.isValidMatchBlocks(s))
/*      */           {
/*  548 */             addToBlockList(customcolormap, list);
/*      */           }
/*      */         }
/*      */       
/*  552 */       } catch (FileNotFoundException var12) {
/*      */         
/*  554 */         warn("File not found: " + s);
/*      */       }
/*  556 */       catch (Exception exception) {
/*      */         
/*  558 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  562 */     if (basePalettes != null)
/*      */     {
/*  564 */       for (int j = 0; j < basePalettes.length; j++) {
/*      */         
/*  566 */         CustomColormap customcolormap1 = basePalettes[j];
/*  567 */         addToBlockList(customcolormap1, list);
/*      */       } 
/*      */     }
/*      */     
/*  571 */     if (list.size() <= 0)
/*      */     {
/*  573 */       return (CustomColormap[][])null;
/*      */     }
/*      */ 
/*      */     
/*  577 */     CustomColormap[][] acustomcolormap = blockListToArray(list);
/*  578 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToBlockList(CustomColormap cm, List blockList) {
/*  584 */     int[] aint = cm.getMatchBlockIds();
/*      */     
/*  586 */     if (aint != null && aint.length > 0) {
/*      */       
/*  588 */       for (int i = 0; i < aint.length; i++) {
/*      */         
/*  590 */         int j = aint[i];
/*      */         
/*  592 */         if (j < 0)
/*      */         {
/*  594 */           warn("Invalid block ID: " + j);
/*      */         }
/*      */         else
/*      */         {
/*  598 */           addToList(cm, blockList, j);
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  604 */       warn("No match blocks: " + Config.arrayToString(aint));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(CustomColormap cm, List<List> lists, int id) {
/*  610 */     while (id >= lists.size())
/*      */     {
/*  612 */       lists.add(null);
/*      */     }
/*      */     
/*  615 */     List<List> list = lists.get(id);
/*      */     
/*  617 */     if (list == null) {
/*      */       
/*  619 */       list = new ArrayList();
/*  620 */       list.set(id, list);
/*      */     } 
/*      */     
/*  623 */     list.add(cm);
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] blockListToArray(List<List> lists) {
/*  628 */     CustomColormap[][] acustomcolormap = new CustomColormap[lists.size()][];
/*      */     
/*  630 */     for (int i = 0; i < lists.size(); i++) {
/*      */       
/*  632 */       List list = lists.get(i);
/*      */       
/*  634 */       if (list != null) {
/*      */         
/*  636 */         CustomColormap[] acustomcolormap1 = (CustomColormap[])list.toArray((Object[])new CustomColormap[list.size()]);
/*  637 */         acustomcolormap[i] = acustomcolormap1;
/*      */       } 
/*      */     } 
/*      */     
/*  641 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readColor(Properties props, String[] names) {
/*  646 */     for (int i = 0; i < names.length; i++) {
/*      */       
/*  648 */       String s = names[i];
/*  649 */       int j = readColor(props, s);
/*      */       
/*  651 */       if (j >= 0)
/*      */       {
/*  653 */         return j;
/*      */       }
/*      */     } 
/*      */     
/*  657 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readColor(Properties props, String name) {
/*  662 */     String s = props.getProperty(name);
/*      */     
/*  664 */     if (s == null)
/*      */     {
/*  666 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  670 */     s = s.trim();
/*  671 */     int i = parseColor(s);
/*      */     
/*  673 */     if (i < 0) {
/*      */       
/*  675 */       warn("Invalid color: " + name + " = " + s);
/*  676 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  680 */     dbg(name + " = " + s);
/*  681 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseColor(String str) {
/*  688 */     if (str == null)
/*      */     {
/*  690 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  694 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/*  698 */       int i = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  699 */       return i;
/*      */     }
/*  701 */     catch (NumberFormatException var2) {
/*      */       
/*  703 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3 readColorVec3(Properties props, String name) {
/*  710 */     int i = readColor(props, name);
/*      */     
/*  712 */     if (i < 0)
/*      */     {
/*  714 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  718 */     int j = i >> 16 & 0xFF;
/*  719 */     int k = i >> 8 & 0xFF;
/*  720 */     int l = i & 0xFF;
/*  721 */     float f = j / 255.0F;
/*  722 */     float f1 = k / 255.0F;
/*  723 */     float f2 = l / 255.0F;
/*  724 */     return new Vec3(f, f1, f2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomColormap getCustomColors(String basePath, String[] paths, int width, int height) {
/*  730 */     for (int i = 0; i < paths.length; i++) {
/*      */       
/*  732 */       String s = paths[i];
/*  733 */       s = basePath + s;
/*  734 */       CustomColormap customcolormap = getCustomColors(s, width, height);
/*      */       
/*  736 */       if (customcolormap != null)
/*      */       {
/*  738 */         return customcolormap;
/*      */       }
/*      */     } 
/*      */     
/*  742 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static CustomColormap getCustomColors(String pathImage, int width, int height) {
/*      */     try {
/*  749 */       ResourceLocation resourcelocation = new ResourceLocation(pathImage);
/*      */       
/*  751 */       if (!Config.hasResource(resourcelocation))
/*      */       {
/*  753 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  757 */       dbg("Colormap " + pathImage);
/*  758 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  759 */       String s = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
/*  760 */       ResourceLocation resourcelocation1 = new ResourceLocation(s);
/*      */       
/*  762 */       if (Config.hasResource(resourcelocation1)) {
/*      */         
/*  764 */         InputStream inputstream = Config.getResourceStream(resourcelocation1);
/*  765 */         propertiesOrdered.load(inputstream);
/*  766 */         inputstream.close();
/*  767 */         dbg("Colormap properties: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  771 */         propertiesOrdered.put("format", paletteFormatDefault);
/*  772 */         propertiesOrdered.put("source", pathImage);
/*  773 */         s = pathImage;
/*      */       } 
/*      */       
/*  776 */       CustomColormap customcolormap = new CustomColormap((Properties)propertiesOrdered, s, width, height, paletteFormatDefault);
/*  777 */       return !customcolormap.isValid(s) ? null : customcolormap;
/*      */     
/*      */     }
/*  780 */     catch (Exception exception) {
/*      */       
/*  782 */       exception.printStackTrace();
/*  783 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateUseDefaultGrassFoliageColors() {
/*  789 */     useDefaultGrassFoliageColors = (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors() && Config.isSmoothBiomes());
/*      */   }
/*      */   
/*      */   public static int getColorMultiplier(BakedQuad quad, IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
/*      */     IColorizer customcolors$icolorizer;
/*  794 */     Block block = blockState.getBlock();
/*  795 */     IBlockState iblockstate = renderEnv.getBlockState();
/*      */     
/*  797 */     if (blockColormaps != null) {
/*      */       
/*  799 */       if (!quad.hasTintIndex()) {
/*      */         
/*  801 */         if (block == Blocks.grass)
/*      */         {
/*  803 */           iblockstate = BLOCK_STATE_DIRT;
/*      */         }
/*      */         
/*  806 */         if (block == Blocks.redstone_wire)
/*      */         {
/*  808 */           return -1;
/*      */         }
/*      */       } 
/*      */       
/*  812 */       if (block == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
/*      */         
/*  814 */         blockPos = blockPos.down();
/*  815 */         iblockstate = blockAccess.getBlockState(blockPos);
/*      */       } 
/*      */       
/*  818 */       CustomColormap customcolormap = getBlockColormap(iblockstate);
/*      */       
/*  820 */       if (customcolormap != null) {
/*      */         
/*  822 */         if (Config.isSmoothBiomes() && !customcolormap.isColorConstant())
/*      */         {
/*  824 */           return getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolormap, renderEnv.getColorizerBlockPosM());
/*      */         }
/*      */         
/*  827 */         return customcolormap.getColor(blockAccess, blockPos);
/*      */       } 
/*      */     } 
/*      */     
/*  831 */     if (!quad.hasTintIndex())
/*      */     {
/*  833 */       return -1;
/*      */     }
/*  835 */     if (block == Blocks.waterlily)
/*      */     {
/*  837 */       return getLilypadColorMultiplier(blockAccess, blockPos);
/*      */     }
/*  839 */     if (block == Blocks.redstone_wire)
/*      */     {
/*  841 */       return getRedstoneColor(renderEnv.getBlockState());
/*      */     }
/*  843 */     if (block instanceof net.minecraft.block.BlockStem)
/*      */     {
/*  845 */       return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
/*      */     }
/*  847 */     if (useDefaultGrassFoliageColors)
/*      */     {
/*  849 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  853 */     int i = renderEnv.getMetadata();
/*      */ 
/*      */     
/*  856 */     if (block != Blocks.grass && block != Blocks.tallgrass && block != Blocks.double_plant) {
/*      */       
/*  858 */       if (block == Blocks.double_plant) {
/*      */         
/*  860 */         customcolors$icolorizer = COLORIZER_GRASS;
/*      */         
/*  862 */         if (i >= 8)
/*      */         {
/*  864 */           blockPos = blockPos.down();
/*      */         }
/*      */       }
/*  867 */       else if (block == Blocks.leaves) {
/*      */         
/*  869 */         switch (i & 0x3) {
/*      */           
/*      */           case 0:
/*  872 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */           
/*      */           case 1:
/*  876 */             customcolors$icolorizer = COLORIZER_FOLIAGE_PINE;
/*      */             break;
/*      */           
/*      */           case 2:
/*  880 */             customcolors$icolorizer = COLORIZER_FOLIAGE_BIRCH;
/*      */             break;
/*      */           
/*      */           default:
/*  884 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */         } 
/*  887 */       } else if (block == Blocks.leaves2) {
/*      */         
/*  889 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       }
/*      */       else {
/*      */         
/*  893 */         if (block != Blocks.vine)
/*      */         {
/*  895 */           return -1;
/*      */         }
/*      */         
/*  898 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  903 */       customcolors$icolorizer = COLORIZER_GRASS;
/*      */     } 
/*      */     
/*  906 */     return (Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolors$icolorizer, renderEnv.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(iblockstate, blockAccess, blockPos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static BiomeGenBase getColorBiome(IBlockAccess blockAccess, BlockPos blockPos) {
/*  912 */     BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(blockPos);
/*      */     
/*  914 */     if (biomegenbase == BiomeGenBase.swampland && !Config.isSwampColors())
/*      */     {
/*  916 */       biomegenbase = BiomeGenBase.plains;
/*      */     }
/*      */     
/*  919 */     return biomegenbase;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap getBlockColormap(IBlockState blockState) {
/*  924 */     if (blockColormaps == null)
/*      */     {
/*  926 */       return null;
/*      */     }
/*  928 */     if (!(blockState instanceof BlockStateBase))
/*      */     {
/*  930 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  934 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  935 */     int i = blockstatebase.getBlockId();
/*      */     
/*  937 */     if (i >= 0 && i < blockColormaps.length) {
/*      */       
/*  939 */       CustomColormap[] acustomcolormap = blockColormaps[i];
/*      */       
/*  941 */       if (acustomcolormap == null)
/*      */       {
/*  943 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  947 */       for (int j = 0; j < acustomcolormap.length; j++) {
/*      */         
/*  949 */         CustomColormap customcolormap = acustomcolormap[j];
/*      */         
/*  951 */         if (customcolormap.matchesBlock(blockstatebase))
/*      */         {
/*  953 */           return customcolormap;
/*      */         }
/*      */       } 
/*      */       
/*  957 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  962 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSmoothColorMultiplier(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, IColorizer colorizer, BlockPosM blockPosM) {
/*  969 */     int i = 0;
/*  970 */     int j = 0;
/*  971 */     int k = 0;
/*  972 */     int l = blockPos.getX();
/*  973 */     int i1 = blockPos.getY();
/*  974 */     int j1 = blockPos.getZ();
/*  975 */     BlockPosM blockposm = blockPosM;
/*      */     
/*  977 */     for (int k1 = l - 1; k1 <= l + 1; k1++) {
/*      */       
/*  979 */       for (int l1 = j1 - 1; l1 <= j1 + 1; l1++) {
/*      */         
/*  981 */         blockposm.setXyz(k1, i1, l1);
/*  982 */         int i2 = colorizer.getColor(blockState, blockAccess, blockposm);
/*  983 */         i += i2 >> 16 & 0xFF;
/*  984 */         j += i2 >> 8 & 0xFF;
/*  985 */         k += i2 & 0xFF;
/*      */       } 
/*      */     } 
/*      */     
/*  989 */     int j2 = i / 9;
/*  990 */     int k2 = j / 9;
/*  991 */     int l2 = k / 9;
/*  992 */     return j2 << 16 | k2 << 8 | l2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getFluidColor(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
/*  997 */     Block block = blockState.getBlock();
/*  998 */     IColorizer customcolors$icolorizer = getBlockColormap(blockState);
/*      */     
/* 1000 */     if (customcolors$icolorizer == null && blockState.getBlock().getMaterial() == Material.water)
/*      */     {
/* 1002 */       customcolors$icolorizer = COLORIZER_WATER;
/*      */     }
/*      */     
/* 1005 */     return (customcolors$icolorizer == null) ? block.colorMultiplier(blockAccess, blockPos, 0) : ((Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolors$icolorizer, renderEnv.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(blockState, blockAccess, blockPos));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updatePortalFX(EntityFX fx) {
/* 1010 */     if (particlePortalColor >= 0) {
/*      */       
/* 1012 */       int i = particlePortalColor;
/* 1013 */       int j = i >> 16 & 0xFF;
/* 1014 */       int k = i >> 8 & 0xFF;
/* 1015 */       int l = i & 0xFF;
/* 1016 */       float f = j / 255.0F;
/* 1017 */       float f1 = k / 255.0F;
/* 1018 */       float f2 = l / 255.0F;
/* 1019 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateMyceliumFX(EntityFX fx) {
/* 1025 */     if (myceliumParticleColors != null) {
/*      */       
/* 1027 */       int i = myceliumParticleColors.getColorRandom();
/* 1028 */       int j = i >> 16 & 0xFF;
/* 1029 */       int k = i >> 8 & 0xFF;
/* 1030 */       int l = i & 0xFF;
/* 1031 */       float f = j / 255.0F;
/* 1032 */       float f1 = k / 255.0F;
/* 1033 */       float f2 = l / 255.0F;
/* 1034 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getRedstoneColor(IBlockState blockState) {
/* 1040 */     if (redstoneColors == null)
/*      */     {
/* 1042 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1046 */     int i = getRedstoneLevel(blockState, 15);
/* 1047 */     int j = redstoneColors.getColor(i);
/* 1048 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateReddustFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z) {
/* 1054 */     if (redstoneColors != null) {
/*      */       
/* 1056 */       IBlockState iblockstate = blockAccess.getBlockState(new BlockPos(x, y, z));
/* 1057 */       int i = getRedstoneLevel(iblockstate, 15);
/* 1058 */       int j = redstoneColors.getColor(i);
/* 1059 */       int k = j >> 16 & 0xFF;
/* 1060 */       int l = j >> 8 & 0xFF;
/* 1061 */       int i1 = j & 0xFF;
/* 1062 */       float f = k / 255.0F;
/* 1063 */       float f1 = l / 255.0F;
/* 1064 */       float f2 = i1 / 255.0F;
/* 1065 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getRedstoneLevel(IBlockState state, int def) {
/* 1071 */     Block block = state.getBlock();
/*      */     
/* 1073 */     if (!(block instanceof BlockRedstoneWire))
/*      */     {
/* 1075 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1079 */     Object object = state.getValue((IProperty)BlockRedstoneWire.POWER);
/*      */     
/* 1081 */     if (!(object instanceof Integer))
/*      */     {
/* 1083 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1087 */     Integer integer = (Integer)object;
/* 1088 */     return integer.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getXpOrbTimer(float timer) {
/* 1095 */     if (xpOrbTime <= 0)
/*      */     {
/* 1097 */       return timer;
/*      */     }
/*      */ 
/*      */     
/* 1101 */     float f = 628.0F / xpOrbTime;
/* 1102 */     return timer * f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getXpOrbColor(float timer) {
/* 1108 */     if (xpOrbColors == null)
/*      */     {
/* 1110 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1114 */     int i = (int)Math.round(((MathHelper.sin(timer) + 1.0F) * (xpOrbColors.getLength() - 1)) / 2.0D);
/* 1115 */     int j = xpOrbColors.getColor(i);
/* 1116 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDurabilityColor(int dur255) {
/* 1122 */     if (durabilityColors == null)
/*      */     {
/* 1124 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1128 */     int i = dur255 * durabilityColors.getLength() / 255;
/* 1129 */     int j = durabilityColors.getColor(i);
/* 1130 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateWaterFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z, RenderEnv renderEnv) {
/* 1136 */     if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
/*      */       
/* 1138 */       BlockPos blockpos = new BlockPos(x, y, z);
/* 1139 */       renderEnv.reset(BLOCK_STATE_WATER, blockpos);
/* 1140 */       int i = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockpos, renderEnv);
/* 1141 */       int j = i >> 16 & 0xFF;
/* 1142 */       int k = i >> 8 & 0xFF;
/* 1143 */       int l = i & 0xFF;
/* 1144 */       float f = j / 255.0F;
/* 1145 */       float f1 = k / 255.0F;
/* 1146 */       float f2 = l / 255.0F;
/*      */       
/* 1148 */       if (particleWaterColor >= 0) {
/*      */         
/* 1150 */         int i1 = particleWaterColor >> 16 & 0xFF;
/* 1151 */         int j1 = particleWaterColor >> 8 & 0xFF;
/* 1152 */         int k1 = particleWaterColor & 0xFF;
/* 1153 */         f *= i1 / 255.0F;
/* 1154 */         f1 *= j1 / 255.0F;
/* 1155 */         f2 *= k1 / 255.0F;
/*      */       } 
/*      */       
/* 1158 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getLilypadColorMultiplier(IBlockAccess blockAccess, BlockPos blockPos) {
/* 1164 */     return (lilyPadColor < 0) ? Blocks.waterlily.colorMultiplier(blockAccess, blockPos) : lilyPadColor;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColorNether(Vec3 col) {
/* 1169 */     return (fogColorNether == null) ? col : fogColorNether;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColorEnd(Vec3 col) {
/* 1174 */     return (fogColorEnd == null) ? col : fogColorEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getSkyColorEnd(Vec3 col) {
/* 1179 */     return (skyColorEnd == null) ? col : skyColorEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getSkyColor(Vec3 skyColor3d, IBlockAccess blockAccess, double x, double y, double z) {
/* 1184 */     if (skyColors == null)
/*      */     {
/* 1186 */       return skyColor3d;
/*      */     }
/*      */ 
/*      */     
/* 1190 */     int i = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1191 */     int j = i >> 16 & 0xFF;
/* 1192 */     int k = i >> 8 & 0xFF;
/* 1193 */     int l = i & 0xFF;
/* 1194 */     float f = j / 255.0F;
/* 1195 */     float f1 = k / 255.0F;
/* 1196 */     float f2 = l / 255.0F;
/* 1197 */     float f3 = (float)skyColor3d.xCoord / 0.5F;
/* 1198 */     float f4 = (float)skyColor3d.yCoord / 0.66275F;
/* 1199 */     float f5 = (float)skyColor3d.zCoord;
/* 1200 */     f *= f3;
/* 1201 */     f1 *= f4;
/* 1202 */     f2 *= f5;
/* 1203 */     Vec3 vec3 = skyColorFader.getColor(f, f1, f2);
/* 1204 */     return vec3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColor(Vec3 fogColor3d, IBlockAccess blockAccess, double x, double y, double z) {
/* 1210 */     if (fogColors == null)
/*      */     {
/* 1212 */       return fogColor3d;
/*      */     }
/*      */ 
/*      */     
/* 1216 */     int i = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1217 */     int j = i >> 16 & 0xFF;
/* 1218 */     int k = i >> 8 & 0xFF;
/* 1219 */     int l = i & 0xFF;
/* 1220 */     float f = j / 255.0F;
/* 1221 */     float f1 = k / 255.0F;
/* 1222 */     float f2 = l / 255.0F;
/* 1223 */     float f3 = (float)fogColor3d.xCoord / 0.753F;
/* 1224 */     float f4 = (float)fogColor3d.yCoord / 0.8471F;
/* 1225 */     float f5 = (float)fogColor3d.zCoord;
/* 1226 */     f *= f3;
/* 1227 */     f1 *= f4;
/* 1228 */     f2 *= f5;
/* 1229 */     Vec3 vec3 = fogColorFader.getColor(f, f1, f2);
/* 1230 */     return vec3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderwaterColor(IBlockAccess blockAccess, double x, double y, double z) {
/* 1236 */     return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderlavaColor(IBlockAccess blockAccess, double x, double y, double z) {
/* 1241 */     return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderFluidColor(IBlockAccess blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader) {
/* 1246 */     if (underFluidColors == null)
/*      */     {
/* 1248 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1252 */     int i = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1253 */     int j = i >> 16 & 0xFF;
/* 1254 */     int k = i >> 8 & 0xFF;
/* 1255 */     int l = i & 0xFF;
/* 1256 */     float f = j / 255.0F;
/* 1257 */     float f1 = k / 255.0F;
/* 1258 */     float f2 = l / 255.0F;
/* 1259 */     Vec3 vec3 = underFluidColorFader.getColor(f, f1, f2);
/* 1260 */     return vec3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getStemColorMultiplier(Block blockStem, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
/* 1266 */     CustomColormap customcolormap = stemColors;
/*      */     
/* 1268 */     if (blockStem == Blocks.pumpkin_stem && stemPumpkinColors != null)
/*      */     {
/* 1270 */       customcolormap = stemPumpkinColors;
/*      */     }
/*      */     
/* 1273 */     if (blockStem == Blocks.melon_stem && stemMelonColors != null)
/*      */     {
/* 1275 */       customcolormap = stemMelonColors;
/*      */     }
/*      */     
/* 1278 */     if (customcolormap == null)
/*      */     {
/* 1280 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1284 */     int i = renderEnv.getMetadata();
/* 1285 */     return customcolormap.getColor(i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision, float partialTicks) {
/* 1291 */     if (world == null)
/*      */     {
/* 1293 */       return false;
/*      */     }
/* 1295 */     if (lightMapPacks == null)
/*      */     {
/* 1297 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1301 */     int i = world.provider.getDimensionId();
/* 1302 */     int j = i - lightmapMinDimensionId;
/*      */     
/* 1304 */     if (j >= 0 && j < lightMapPacks.length) {
/*      */       
/* 1306 */       LightMapPack lightmappack = lightMapPacks[j];
/* 1307 */       return (lightmappack == null) ? false : lightmappack.updateLightmap(world, torchFlickerX, lmColors, nightvision, partialTicks);
/*      */     } 
/*      */ 
/*      */     
/* 1311 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vec3 getWorldFogColor(Vec3 fogVec, World world, Entity renderViewEntity, float partialTicks) {
/*      */     Minecraft minecraft;
/* 1318 */     int i = world.provider.getDimensionId();
/*      */     
/* 1320 */     switch (i) {
/*      */       
/*      */       case -1:
/* 1323 */         fogVec = getFogColorNether(fogVec);
/*      */         break;
/*      */       
/*      */       case 0:
/* 1327 */         minecraft = Minecraft.getMinecraft();
/* 1328 */         fogVec = getFogColor(fogVec, (IBlockAccess)minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D, renderViewEntity.posZ);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1332 */         fogVec = getFogColorEnd(fogVec);
/*      */         break;
/*      */     } 
/* 1335 */     return fogVec;
/*      */   }
/*      */   
/*      */   public static Vec3 getWorldSkyColor(Vec3 skyVec, World world, Entity renderViewEntity, float partialTicks) {
/*      */     Minecraft minecraft;
/* 1340 */     int i = world.provider.getDimensionId();
/*      */     
/* 1342 */     switch (i) {
/*      */       
/*      */       case 0:
/* 1345 */         minecraft = Minecraft.getMinecraft();
/* 1346 */         skyVec = getSkyColor(skyVec, (IBlockAccess)minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D, renderViewEntity.posZ);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1350 */         skyVec = getSkyColorEnd(skyVec);
/*      */         break;
/*      */     } 
/* 1353 */     return skyVec;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readSpawnEggColors(Properties props, String fileName, String prefix, String logName) {
/* 1358 */     List<Integer> list = new ArrayList<>();
/* 1359 */     Set<Object> set = props.keySet();
/* 1360 */     int i = 0;
/*      */     
/* 1362 */     for (Object e : set) {
/*      */       
/* 1364 */       String s = (String)e;
/* 1365 */       String s1 = props.getProperty(s);
/*      */       
/* 1367 */       if (s.startsWith(prefix)) {
/*      */         
/* 1369 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1370 */         int j = EntityUtils.getEntityIdByName(s2);
/*      */         
/* 1372 */         if (j < 0) {
/*      */           
/* 1374 */           warn("Invalid spawn egg name: " + s);
/*      */           
/*      */           continue;
/*      */         } 
/* 1378 */         int k = parseColor(s1);
/*      */         
/* 1380 */         if (k < 0) {
/*      */           
/* 1382 */           warn("Invalid spawn egg color: " + s + " = " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/* 1386 */         while (list.size() <= j)
/*      */         {
/* 1388 */           list.add(Integer.valueOf(-1));
/*      */         }
/*      */         
/* 1391 */         list.set(j, Integer.valueOf(k));
/* 1392 */         i++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1398 */     if (i <= 0)
/*      */     {
/* 1400 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1404 */     dbg(logName + " colors: " + i);
/* 1405 */     int[] aint = new int[list.size()];
/*      */     
/* 1407 */     for (int l = 0; l < aint.length; l++)
/*      */     {
/* 1409 */       aint[l] = ((Integer)list.get(l)).intValue();
/*      */     }
/*      */     
/* 1412 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSpawnEggColor(ItemMonsterPlacer item, ItemStack itemStack, int layer, int color) {
/* 1418 */     int i = itemStack.getMetadata();
/* 1419 */     int[] aint = (layer == 0) ? spawnEggPrimaryColors : spawnEggSecondaryColors;
/*      */     
/* 1421 */     if (aint == null)
/*      */     {
/* 1423 */       return color;
/*      */     }
/* 1425 */     if (i >= 0 && i < aint.length) {
/*      */       
/* 1427 */       int j = aint[i];
/* 1428 */       return (j < 0) ? color : j;
/*      */     } 
/*      */ 
/*      */     
/* 1432 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getColorFromItemStack(ItemStack itemStack, int layer, int color) {
/* 1438 */     if (itemStack == null)
/*      */     {
/* 1440 */       return color;
/*      */     }
/*      */ 
/*      */     
/* 1444 */     Item item = itemStack.getItem();
/* 1445 */     return (item == null) ? color : ((item instanceof ItemMonsterPlacer) ? getSpawnEggColor((ItemMonsterPlacer)item, itemStack, layer, color) : color);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[][] readDyeColors(Properties props, String fileName, String prefix, String logName) {
/* 1451 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/* 1452 */     Map<String, EnumDyeColor> map = new HashMap<>();
/*      */     
/* 1454 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/*      */       
/* 1456 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/* 1457 */       map.put(enumdyecolor.getName(), enumdyecolor);
/*      */     } 
/*      */     
/* 1460 */     float[][] afloat1 = new float[aenumdyecolor.length][];
/* 1461 */     int k = 0;
/*      */     
/* 1463 */     for (Object e : props.keySet()) {
/*      */       
/* 1465 */       String s = (String)e;
/* 1466 */       String s1 = props.getProperty(s);
/*      */       
/* 1468 */       if (s.startsWith(prefix)) {
/*      */         
/* 1470 */         String s2 = StrUtils.removePrefix(s, prefix);
/*      */         
/* 1472 */         if (s2.equals("lightBlue"))
/*      */         {
/* 1474 */           s2 = "light_blue";
/*      */         }
/*      */         
/* 1477 */         EnumDyeColor enumdyecolor1 = map.get(s2);
/* 1478 */         int j = parseColor(s1);
/*      */         
/* 1480 */         if (enumdyecolor1 != null && j >= 0) {
/*      */           
/* 1482 */           float[] afloat = { (j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F };
/* 1483 */           afloat1[enumdyecolor1.ordinal()] = afloat;
/* 1484 */           k++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1488 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1493 */     if (k <= 0)
/*      */     {
/* 1495 */       return (float[][])null;
/*      */     }
/*      */ 
/*      */     
/* 1499 */     dbg(logName + " colors: " + k);
/* 1500 */     return afloat1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] getDyeColors(EnumDyeColor dye, float[][] dyeColors, float[] colors) {
/* 1506 */     if (dyeColors == null)
/*      */     {
/* 1508 */       return colors;
/*      */     }
/* 1510 */     if (dye == null)
/*      */     {
/* 1512 */       return colors;
/*      */     }
/*      */ 
/*      */     
/* 1516 */     float[] afloat = dyeColors[dye.ordinal()];
/* 1517 */     return (afloat == null) ? colors : afloat;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] getWolfCollarColors(EnumDyeColor dye, float[] colors) {
/* 1523 */     return getDyeColors(dye, wolfCollarColors, colors);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float[] getSheepColors(EnumDyeColor dye, float[] colors) {
/* 1528 */     return getDyeColors(dye, sheepColors, colors);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
/* 1533 */     int[] aint = new int[32];
/* 1534 */     Arrays.fill(aint, -1);
/* 1535 */     int i = 0;
/*      */     
/* 1537 */     for (Object e : props.keySet()) {
/*      */       
/* 1539 */       String s = (String)e;
/* 1540 */       String s1 = props.getProperty(s);
/*      */       
/* 1542 */       if (s.startsWith(prefix)) {
/*      */         
/* 1544 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1545 */         int j = Config.parseInt(s2, -1);
/* 1546 */         int k = parseColor(s1);
/*      */         
/* 1548 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1550 */           aint[j] = k;
/* 1551 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1555 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1560 */     if (i <= 0)
/*      */     {
/* 1562 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1566 */     dbg(logName + " colors: " + i);
/* 1567 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getTextColor(int index, int color) {
/* 1573 */     if (textColors == null)
/*      */     {
/* 1575 */       return color;
/*      */     }
/* 1577 */     if (index >= 0 && index < textColors.length) {
/*      */       
/* 1579 */       int i = textColors[index];
/* 1580 */       return (i < 0) ? color : i;
/*      */     } 
/*      */ 
/*      */     
/* 1584 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readMapColors(Properties props, String fileName, String prefix, String logName) {
/* 1590 */     int[] aint = new int[MapColor.mapColorArray.length];
/* 1591 */     Arrays.fill(aint, -1);
/* 1592 */     int i = 0;
/*      */     
/* 1594 */     for (Object o : props.keySet()) {
/*      */       
/* 1596 */       String s = (String)o;
/* 1597 */       String s1 = props.getProperty(s);
/*      */       
/* 1599 */       if (s.startsWith(prefix)) {
/*      */         
/* 1601 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1602 */         int j = getMapColorIndex(s2);
/* 1603 */         int k = parseColor(s1);
/*      */         
/* 1605 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1607 */           aint[j] = k;
/* 1608 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1612 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1617 */     if (i <= 0)
/*      */     {
/* 1619 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1623 */     dbg(logName + " colors: " + i);
/* 1624 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readPotionColors(Properties props, String fileName, String prefix, String logName) {
/* 1630 */     int[] aint = new int[Potion.potionTypes.length];
/* 1631 */     Arrays.fill(aint, -1);
/* 1632 */     int i = 0;
/*      */     
/* 1634 */     for (Object e : props.keySet()) {
/*      */       
/* 1636 */       String s = (String)e;
/* 1637 */       String s1 = props.getProperty(s);
/*      */       
/* 1639 */       if (s.startsWith(prefix)) {
/*      */         
/* 1641 */         int j = getPotionId(s);
/* 1642 */         int k = parseColor(s1);
/*      */         
/* 1644 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1646 */           aint[j] = k;
/* 1647 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1651 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1656 */     if (i <= 0)
/*      */     {
/* 1658 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1662 */     dbg(logName + " colors: " + i);
/* 1663 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getPotionId(String name) {
/* 1669 */     if (name.equals("potion.water"))
/*      */     {
/* 1671 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 1675 */     Potion[] apotion = Potion.potionTypes;
/*      */     
/* 1677 */     for (int i = 0; i < apotion.length; i++) {
/*      */       
/* 1679 */       Potion potion = apotion[i];
/*      */       
/* 1681 */       if (potion != null && potion.getName().equals(name))
/*      */       {
/* 1683 */         return potion.getId();
/*      */       }
/*      */     } 
/*      */     
/* 1687 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getPotionColor(int potionId, int color) {
/* 1693 */     if (potionColors == null)
/*      */     {
/* 1695 */       return color;
/*      */     }
/* 1697 */     if (potionId >= 0 && potionId < potionColors.length) {
/*      */       
/* 1699 */       int i = potionColors[potionId];
/* 1700 */       return (i < 0) ? color : i;
/*      */     } 
/*      */ 
/*      */     
/* 1704 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getMapColorIndex(String name) {
/* 1710 */     return (name == null) ? -1 : (name.equals("air") ? MapColor.airColor.colorIndex : (name.equals("grass") ? MapColor.grassColor.colorIndex : (name.equals("sand") ? MapColor.sandColor.colorIndex : (name.equals("cloth") ? MapColor.clothColor.colorIndex : (name.equals("tnt") ? MapColor.tntColor.colorIndex : (name.equals("ice") ? MapColor.iceColor.colorIndex : (name.equals("iron") ? MapColor.ironColor.colorIndex : (name.equals("foliage") ? MapColor.foliageColor.colorIndex : (name.equals("clay") ? MapColor.clayColor.colorIndex : (name.equals("dirt") ? MapColor.dirtColor.colorIndex : (name.equals("stone") ? MapColor.stoneColor.colorIndex : (name.equals("water") ? MapColor.waterColor.colorIndex : (name.equals("wood") ? MapColor.woodColor.colorIndex : (name.equals("quartz") ? MapColor.quartzColor.colorIndex : (name.equals("gold") ? MapColor.goldColor.colorIndex : (name.equals("diamond") ? MapColor.diamondColor.colorIndex : (name.equals("lapis") ? MapColor.lapisColor.colorIndex : (name.equals("emerald") ? MapColor.emeraldColor.colorIndex : (name.equals("podzol") ? MapColor.obsidianColor.colorIndex : (name.equals("netherrack") ? MapColor.netherrackColor.colorIndex : ((!name.equals("snow") && !name.equals("white")) ? ((!name.equals("adobe") && !name.equals("orange")) ? (name.equals("magenta") ? MapColor.magentaColor.colorIndex : ((!name.equals("light_blue") && !name.equals("lightBlue")) ? (name.equals("yellow") ? MapColor.yellowColor.colorIndex : (name.equals("lime") ? MapColor.limeColor.colorIndex : (name.equals("pink") ? MapColor.pinkColor.colorIndex : (name.equals("gray") ? MapColor.grayColor.colorIndex : (name.equals("silver") ? MapColor.silverColor.colorIndex : (name.equals("cyan") ? MapColor.cyanColor.colorIndex : (name.equals("purple") ? MapColor.purpleColor.colorIndex : (name.equals("blue") ? MapColor.blueColor.colorIndex : (name.equals("brown") ? MapColor.brownColor.colorIndex : (name.equals("green") ? MapColor.greenColor.colorIndex : (name.equals("red") ? MapColor.redColor.colorIndex : (name.equals("black") ? MapColor.blackColor.colorIndex : -1)))))))))))) : MapColor.lightBlueColor.colorIndex)) : MapColor.adobeColor.colorIndex) : MapColor.snowColor.colorIndex)))))))))))))))))))));
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getMapColors() {
/* 1715 */     MapColor[] amapcolor = MapColor.mapColorArray;
/* 1716 */     int[] aint = new int[amapcolor.length];
/* 1717 */     Arrays.fill(aint, -1);
/*      */     
/* 1719 */     for (int i = 0; i < amapcolor.length && i < aint.length; i++) {
/*      */       
/* 1721 */       MapColor mapcolor = amapcolor[i];
/*      */       
/* 1723 */       if (mapcolor != null)
/*      */       {
/* 1725 */         aint[i] = mapcolor.colorValue;
/*      */       }
/*      */     } 
/*      */     
/* 1729 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setMapColors(int[] colors) {
/* 1734 */     if (colors != null) {
/*      */       
/* 1736 */       MapColor[] amapcolor = MapColor.mapColorArray;
/* 1737 */       boolean flag = false;
/*      */       
/* 1739 */       for (int i = 0; i < amapcolor.length && i < colors.length; i++) {
/*      */         
/* 1741 */         MapColor mapcolor = amapcolor[i];
/*      */         
/* 1743 */         if (mapcolor != null) {
/*      */           
/* 1745 */           int j = colors[i];
/*      */           
/* 1747 */           if (j >= 0 && mapcolor.colorValue != j) {
/*      */             
/* 1749 */             mapcolor.colorValue = j;
/* 1750 */             flag = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1755 */       if (flag)
/*      */       {
/* 1757 */         Minecraft.getMinecraft().getTextureManager().reloadBannerTextures();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbg(String str) {
/* 1764 */     Config.dbg("CustomColors: " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void warn(String str) {
/* 1769 */     Config.warn("CustomColors: " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getExpBarTextColor(int color) {
/* 1774 */     return (expBarTextColor < 0) ? color : expBarTextColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBossTextColor(int color) {
/* 1779 */     return (bossTextColor < 0) ? color : bossTextColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getSignTextColor(int color) {
/* 1784 */     return (signTextColor < 0) ? color : signTextColor;
/*      */   }
/*      */   
/*      */   public static interface IColorizer {
/*      */     int getColor(IBlockState param1IBlockState, IBlockAccess param1IBlockAccess, BlockPos param1BlockPos);
/*      */     
/*      */     boolean isColorConstant();
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */