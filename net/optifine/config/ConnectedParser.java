/*      */ package net.optifine.config;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IStringSerializable;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.ConnectedProperties;
/*      */ import net.optifine.util.EntityUtils;
/*      */ 
/*      */ public class ConnectedParser
/*      */ {
/*   32 */   private String context = null;
/*   33 */   public static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];
/*   34 */   public static final EnumDyeColor[] DYE_COLORS_INVALID = new EnumDyeColor[0];
/*   35 */   private static final INameGetter<Enum> NAME_GETTER_ENUM = new INameGetter<Enum>()
/*      */     {
/*      */       public String getName(Enum en)
/*      */       {
/*   39 */         return en.name();
/*      */       }
/*      */     };
/*   42 */   private static final INameGetter<EnumDyeColor> NAME_GETTER_DYE_COLOR = new INameGetter<EnumDyeColor>()
/*      */     {
/*      */       public String getName(EnumDyeColor col)
/*      */       {
/*   46 */         return col.getName();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public ConnectedParser(String context) {
/*   52 */     this.context = context;
/*      */   }
/*      */ 
/*      */   
/*      */   public String parseName(String path) {
/*   57 */     String s = path;
/*   58 */     int i = path.lastIndexOf('/');
/*      */     
/*   60 */     if (i >= 0)
/*      */     {
/*   62 */       s = path.substring(i + 1);
/*      */     }
/*      */     
/*   65 */     int j = s.lastIndexOf('.');
/*      */     
/*   67 */     if (j >= 0)
/*      */     {
/*   69 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*   72 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public String parseBasePath(String path) {
/*   77 */     int i = path.lastIndexOf('/');
/*   78 */     return (i < 0) ? "" : path.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
/*   83 */     if (propMatchBlocks == null)
/*      */     {
/*   85 */       return null;
/*      */     }
/*      */ 
/*      */     
/*   89 */     List list = new ArrayList();
/*   90 */     String[] astring = Config.tokenize(propMatchBlocks, " ");
/*      */     
/*   92 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*   94 */       String s = astring[i];
/*   95 */       MatchBlock[] amatchblock = parseMatchBlock(s);
/*      */       
/*   97 */       if (amatchblock != null)
/*      */       {
/*   99 */         list.addAll(Arrays.asList(amatchblock));
/*      */       }
/*      */     } 
/*      */     
/*  103 */     MatchBlock[] amatchblock1 = (MatchBlock[])list.toArray((Object[])new MatchBlock[list.size()]);
/*  104 */     return amatchblock1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState parseBlockState(String str, IBlockState def) {
/*  110 */     MatchBlock[] amatchblock = parseMatchBlock(str);
/*      */     
/*  112 */     if (amatchblock == null)
/*      */     {
/*  114 */       return def;
/*      */     }
/*  116 */     if (amatchblock.length != 1)
/*      */     {
/*  118 */       return def;
/*      */     }
/*      */ 
/*      */     
/*  122 */     MatchBlock matchblock = amatchblock[0];
/*  123 */     int i = matchblock.getBlockId();
/*  124 */     Block block = Block.getBlockById(i);
/*  125 */     return block.getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MatchBlock[] parseMatchBlock(String blockStr) {
/*  131 */     if (blockStr == null)
/*      */     {
/*  133 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  137 */     blockStr = blockStr.trim();
/*      */     
/*  139 */     if (blockStr.length() <= 0)
/*      */     {
/*  141 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  145 */     String[] astring = Config.tokenize(blockStr, ":");
/*  146 */     String s = "minecraft";
/*  147 */     int i = 0;
/*      */     
/*  149 */     if (astring.length > 1 && isFullBlockName(astring)) {
/*      */       
/*  151 */       s = astring[0];
/*  152 */       i = 1;
/*      */     }
/*      */     else {
/*      */       
/*  156 */       s = "minecraft";
/*  157 */       i = 0;
/*      */     } 
/*      */     
/*  160 */     String s1 = astring[i];
/*  161 */     String[] astring1 = Arrays.<String>copyOfRange(astring, i + 1, astring.length);
/*  162 */     Block[] ablock = parseBlockPart(s, s1);
/*      */     
/*  164 */     if (ablock == null)
/*      */     {
/*  166 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  170 */     MatchBlock[] amatchblock = new MatchBlock[ablock.length];
/*      */     
/*  172 */     for (int j = 0; j < ablock.length; j++) {
/*      */       
/*  174 */       Block block = ablock[j];
/*  175 */       int k = Block.getIdFromBlock(block);
/*  176 */       int[] aint = null;
/*      */       
/*  178 */       if (astring1.length > 0) {
/*      */         
/*  180 */         aint = parseBlockMetadatas(block, astring1);
/*      */         
/*  182 */         if (aint == null)
/*      */         {
/*  184 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  188 */       MatchBlock matchblock = new MatchBlock(k, aint);
/*  189 */       amatchblock[j] = matchblock;
/*      */     } 
/*      */     
/*  192 */     return amatchblock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullBlockName(String[] parts) {
/*  200 */     if (parts.length < 2)
/*      */     {
/*  202 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  206 */     String s = parts[1];
/*  207 */     return (s.length() < 1) ? false : (startsWithDigit(s) ? false : (!s.contains("=")));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWithDigit(String str) {
/*  213 */     if (str == null)
/*      */     {
/*  215 */       return false;
/*      */     }
/*  217 */     if (str.length() < 1)
/*      */     {
/*  219 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  223 */     char c0 = str.charAt(0);
/*  224 */     return Character.isDigit(c0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block[] parseBlockPart(String domain, String blockPart) {
/*  230 */     if (startsWithDigit(blockPart)) {
/*      */       
/*  232 */       int[] aint = parseIntList(blockPart);
/*      */       
/*  234 */       if (aint == null)
/*      */       {
/*  236 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  240 */       Block[] ablock1 = new Block[aint.length];
/*      */       
/*  242 */       for (int j = 0; j < aint.length; j++) {
/*      */         
/*  244 */         int i = aint[j];
/*  245 */         Block block1 = Block.getBlockById(i);
/*      */         
/*  247 */         if (block1 == null) {
/*      */           
/*  249 */           warn("Block not found for id: " + i);
/*  250 */           return null;
/*      */         } 
/*      */         
/*  253 */         ablock1[j] = block1;
/*      */       } 
/*      */       
/*  256 */       return ablock1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  261 */     String s = domain + ":" + blockPart;
/*  262 */     Block block = Block.getBlockFromName(s);
/*      */     
/*  264 */     if (block == null) {
/*      */       
/*  266 */       warn("Block not found for name: " + s);
/*  267 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  271 */     Block[] ablock = { block };
/*  272 */     return ablock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseBlockMetadatas(Block block, String[] params) {
/*  279 */     if (params.length <= 0)
/*      */     {
/*  281 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  285 */     String s = params[0];
/*      */     
/*  287 */     if (startsWithDigit(s)) {
/*      */       
/*  289 */       int[] aint = parseIntList(s);
/*  290 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/*  294 */     IBlockState iblockstate = block.getDefaultState();
/*  295 */     Collection collection = iblockstate.getPropertyNames();
/*  296 */     Map<IProperty, List<Comparable>> map = new HashMap<>();
/*      */     
/*  298 */     for (int i = 0; i < params.length; i++) {
/*      */       
/*  300 */       String s1 = params[i];
/*      */       
/*  302 */       if (s1.length() > 0) {
/*      */         
/*  304 */         String[] astring = Config.tokenize(s1, "=");
/*      */         
/*  306 */         if (astring.length != 2) {
/*      */           
/*  308 */           warn("Invalid block property: " + s1);
/*  309 */           return null;
/*      */         } 
/*      */         
/*  312 */         String s2 = astring[0];
/*  313 */         String s3 = astring[1];
/*  314 */         IProperty iproperty = ConnectedProperties.getProperty(s2, collection);
/*      */         
/*  316 */         if (iproperty == null) {
/*      */           
/*  318 */           warn("Property not found: " + s2 + ", block: " + block);
/*  319 */           return null;
/*      */         } 
/*      */         
/*  322 */         List<Comparable> list = map.get(s2);
/*      */         
/*  324 */         if (list == null) {
/*      */           
/*  326 */           list = new ArrayList<>();
/*  327 */           map.put(iproperty, list);
/*      */         } 
/*      */         
/*  330 */         String[] astring1 = Config.tokenize(s3, ",");
/*      */         
/*  332 */         for (int j = 0; j < astring1.length; j++) {
/*      */           
/*  334 */           String s4 = astring1[j];
/*  335 */           Comparable comparable = parsePropertyValue(iproperty, s4);
/*      */           
/*  337 */           if (comparable == null) {
/*      */             
/*  339 */             warn("Property value not found: " + s4 + ", property: " + s2 + ", block: " + block);
/*  340 */             return null;
/*      */           } 
/*      */           
/*  343 */           list.add(comparable);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  348 */     if (map.isEmpty())
/*      */     {
/*  350 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  354 */     List<Integer> list1 = new ArrayList<>();
/*      */     
/*  356 */     for (int k = 0; k < 16; k++) {
/*      */       
/*  358 */       int l = k;
/*      */ 
/*      */       
/*      */       try {
/*  362 */         IBlockState iblockstate1 = getStateFromMeta(block, l);
/*      */         
/*  364 */         if (matchState(iblockstate1, map))
/*      */         {
/*  366 */           list1.add(Integer.valueOf(l));
/*      */         }
/*      */       }
/*  369 */       catch (IllegalArgumentException illegalArgumentException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  375 */     if (list1.size() == 16)
/*      */     {
/*  377 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  381 */     int[] aint1 = new int[list1.size()];
/*      */     
/*  383 */     for (int i1 = 0; i1 < aint1.length; i1++)
/*      */     {
/*  385 */       aint1[i1] = ((Integer)list1.get(i1)).intValue();
/*      */     }
/*      */     
/*  388 */     return aint1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IBlockState getStateFromMeta(Block block, int md) {
/*      */     try {
/*  399 */       IBlockState iblockstate = block.getStateFromMeta(md);
/*      */       
/*  401 */       if (block == Blocks.double_plant && md > 7) {
/*      */         
/*  403 */         IBlockState iblockstate1 = block.getStateFromMeta(md & 0x7);
/*  404 */         iblockstate = iblockstate.withProperty((IProperty)BlockDoublePlant.VARIANT, iblockstate1.getValue((IProperty)BlockDoublePlant.VARIANT));
/*      */       } 
/*      */       
/*  407 */       return iblockstate;
/*      */     }
/*  409 */     catch (IllegalArgumentException var5) {
/*      */       
/*  411 */       return block.getDefaultState();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparable parsePropertyValue(IProperty prop, String valStr) {
/*  417 */     Class oclass = prop.getValueClass();
/*  418 */     Comparable comparable = parseValue(valStr, oclass);
/*      */     
/*  420 */     if (comparable == null) {
/*      */       
/*  422 */       Collection collection = prop.getAllowedValues();
/*  423 */       comparable = getPropertyValue(valStr, collection);
/*      */     } 
/*      */     
/*  426 */     return comparable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparable getPropertyValue(String value, Collection propertyValues) {
/*  431 */     for (Object e : propertyValues) {
/*      */       
/*  433 */       Comparable comparable = (Comparable)e;
/*  434 */       if (getValueName(comparable).equals(value))
/*      */       {
/*  436 */         return comparable;
/*      */       }
/*      */     } 
/*      */     
/*  440 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object getValueName(Comparable obj) {
/*  445 */     if (obj instanceof IStringSerializable) {
/*      */       
/*  447 */       IStringSerializable istringserializable = (IStringSerializable)obj;
/*  448 */       return istringserializable.getName();
/*      */     } 
/*      */ 
/*      */     
/*  452 */     return obj.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Comparable parseValue(String str, Class<String> cls) {
/*  458 */     return (cls == String.class) ? str : ((cls == Boolean.class) ? Boolean.valueOf(str) : Double.valueOf((cls == Float.class) ? Float.parseFloat(str) : ((cls == Double.class) ? Double.parseDouble(str) : ((cls == Integer.class) ? Integer.parseInt(str) : ((cls == Long.class) ? Long.valueOf(str) : null).longValue()))));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchState(IBlockState bs, Map<IProperty, List<Comparable>> mapPropValues) {
/*  463 */     for (IProperty iproperty : mapPropValues.keySet()) {
/*      */       
/*  465 */       List<Comparable> list = mapPropValues.get(iproperty);
/*  466 */       Comparable comparable = bs.getValue(iproperty);
/*      */       
/*  468 */       if (comparable == null)
/*      */       {
/*  470 */         return false;
/*      */       }
/*      */       
/*  473 */       if (!list.contains(comparable))
/*      */       {
/*  475 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  479 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeGenBase[] parseBiomes(String str) {
/*  484 */     if (str == null)
/*      */     {
/*  486 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  490 */     str = str.trim();
/*  491 */     boolean flag = false;
/*      */     
/*  493 */     if (str.startsWith("!")) {
/*      */       
/*  495 */       flag = true;
/*  496 */       str = str.substring(1);
/*      */     } 
/*      */     
/*  499 */     String[] astring = Config.tokenize(str, " ");
/*  500 */     List<BiomeGenBase> list = new ArrayList();
/*      */     
/*  502 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  504 */       String s = astring[i];
/*  505 */       BiomeGenBase biomegenbase = findBiome(s);
/*      */       
/*  507 */       if (biomegenbase == null) {
/*      */         
/*  509 */         warn("Biome not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  513 */         list.add(biomegenbase);
/*      */       } 
/*      */     } 
/*      */     
/*  517 */     if (flag) {
/*      */       
/*  519 */       List<BiomeGenBase> list1 = new ArrayList<>(Arrays.asList(BiomeGenBase.getBiomeGenArray()));
/*  520 */       list1.removeAll(list);
/*  521 */       list = list1;
/*      */     } 
/*      */     
/*  524 */     BiomeGenBase[] abiomegenbase = list.<BiomeGenBase>toArray(new BiomeGenBase[list.size()]);
/*  525 */     return abiomegenbase;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BiomeGenBase findBiome(String biomeName) {
/*  531 */     biomeName = biomeName.toLowerCase();
/*      */     
/*  533 */     if (biomeName.equals("nether"))
/*      */     {
/*  535 */       return BiomeGenBase.hell;
/*      */     }
/*      */ 
/*      */     
/*  539 */     BiomeGenBase[] abiomegenbase = BiomeGenBase.getBiomeGenArray();
/*      */     
/*  541 */     for (int i = 0; i < abiomegenbase.length; i++) {
/*      */       
/*  543 */       BiomeGenBase biomegenbase = abiomegenbase[i];
/*      */       
/*  545 */       if (biomegenbase != null) {
/*      */         
/*  547 */         String s = biomegenbase.biomeName.replace(" ", "").toLowerCase();
/*      */         
/*  549 */         if (s.equals(biomeName))
/*      */         {
/*  551 */           return biomegenbase;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  556 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int parseInt(String str, int defVal) {
/*  562 */     if (str == null)
/*      */     {
/*  564 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  568 */     str = str.trim();
/*  569 */     int i = Config.parseInt(str, -1);
/*      */     
/*  571 */     if (i < 0) {
/*      */       
/*  573 */       warn("Invalid number: " + str);
/*  574 */       return defVal;
/*      */     } 
/*      */ 
/*      */     
/*  578 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseIntList(String str) {
/*  585 */     if (str == null)
/*      */     {
/*  587 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  591 */     List<Integer> list = new ArrayList<>();
/*  592 */     String[] astring = Config.tokenize(str, " ,");
/*      */     
/*  594 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  596 */       String s = astring[i];
/*      */       
/*  598 */       if (s.contains("-")) {
/*      */         
/*  600 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  602 */         if (astring1.length != 2) {
/*      */           
/*  604 */           warn("Invalid interval: " + s + ", when parsing: " + str);
/*      */         }
/*      */         else {
/*      */           
/*  608 */           int k = Config.parseInt(astring1[0], -1);
/*  609 */           int l = Config.parseInt(astring1[1], -1);
/*      */           
/*  611 */           if (k >= 0 && l >= 0 && k <= l)
/*      */           {
/*  613 */             for (int i1 = k; i1 <= l; i1++)
/*      */             {
/*  615 */               list.add(Integer.valueOf(i1));
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  620 */             warn("Invalid interval: " + s + ", when parsing: " + str);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  626 */         int j = Config.parseInt(s, -1);
/*      */         
/*  628 */         if (j < 0) {
/*      */           
/*  630 */           warn("Invalid number: " + s + ", when parsing: " + str);
/*      */         }
/*      */         else {
/*      */           
/*  634 */           list.add(Integer.valueOf(j));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  639 */     int[] aint = new int[list.size()];
/*      */     
/*  641 */     for (int j1 = 0; j1 < aint.length; j1++)
/*      */     {
/*  643 */       aint[j1] = ((Integer)list.get(j1)).intValue();
/*      */     }
/*      */     
/*  646 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] parseFaces(String str, boolean[] defVal) {
/*  652 */     if (str == null)
/*      */     {
/*  654 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  658 */     EnumSet<EnumFacing> enumset = EnumSet.allOf(EnumFacing.class);
/*  659 */     String[] astring = Config.tokenize(str, " ,");
/*      */     
/*  661 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  663 */       String s = astring[i];
/*      */       
/*  665 */       if (s.equals("sides")) {
/*      */         
/*  667 */         enumset.add(EnumFacing.NORTH);
/*  668 */         enumset.add(EnumFacing.SOUTH);
/*  669 */         enumset.add(EnumFacing.WEST);
/*  670 */         enumset.add(EnumFacing.EAST);
/*      */       }
/*  672 */       else if (s.equals("all")) {
/*      */         
/*  674 */         enumset.addAll(Arrays.asList(EnumFacing.VALUES));
/*      */       }
/*      */       else {
/*      */         
/*  678 */         EnumFacing enumfacing = parseFace(s);
/*      */         
/*  680 */         if (enumfacing != null)
/*      */         {
/*  682 */           enumset.add(enumfacing);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  687 */     boolean[] aboolean = new boolean[EnumFacing.VALUES.length];
/*      */     
/*  689 */     for (int j = 0; j < aboolean.length; j++)
/*      */     {
/*  691 */       aboolean[j] = enumset.contains(EnumFacing.VALUES[j]);
/*      */     }
/*      */     
/*  694 */     return aboolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing parseFace(String str) {
/*  700 */     str = str.toLowerCase();
/*      */     
/*  702 */     if (!str.equals("bottom") && !str.equals("down")) {
/*      */       
/*  704 */       if (!str.equals("top") && !str.equals("up")) {
/*      */         
/*  706 */         if (str.equals("north"))
/*      */         {
/*  708 */           return EnumFacing.NORTH;
/*      */         }
/*  710 */         if (str.equals("south"))
/*      */         {
/*  712 */           return EnumFacing.SOUTH;
/*      */         }
/*  714 */         if (str.equals("east"))
/*      */         {
/*  716 */           return EnumFacing.EAST;
/*      */         }
/*  718 */         if (str.equals("west"))
/*      */         {
/*  720 */           return EnumFacing.WEST;
/*      */         }
/*      */ 
/*      */         
/*  724 */         Config.warn("Unknown face: " + str);
/*  725 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  730 */       return EnumFacing.UP;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  735 */     return EnumFacing.DOWN;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dbg(String str) {
/*  741 */     Config.dbg("" + this.context + ": " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   public void warn(String str) {
/*  746 */     Config.warn("" + this.context + ": " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   public RangeListInt parseRangeListInt(String str) {
/*  751 */     if (str == null)
/*      */     {
/*  753 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  757 */     RangeListInt rangelistint = new RangeListInt();
/*  758 */     String[] astring = Config.tokenize(str, " ,");
/*      */     
/*  760 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  762 */       String s = astring[i];
/*  763 */       RangeInt rangeint = parseRangeInt(s);
/*      */       
/*  765 */       if (rangeint == null)
/*      */       {
/*  767 */         return null;
/*      */       }
/*      */       
/*  770 */       rangelistint.addRange(rangeint);
/*      */     } 
/*      */     
/*  773 */     return rangelistint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeInt parseRangeInt(String str) {
/*  779 */     if (str == null)
/*      */     {
/*  781 */       return null;
/*      */     }
/*  783 */     if (str.indexOf('-') >= 0) {
/*      */       
/*  785 */       String[] astring = Config.tokenize(str, "-");
/*      */       
/*  787 */       if (astring.length != 2) {
/*      */         
/*  789 */         warn("Invalid range: " + str);
/*  790 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  794 */       int j = Config.parseInt(astring[0], -1);
/*  795 */       int k = Config.parseInt(astring[1], -1);
/*      */       
/*  797 */       if (j >= 0 && k >= 0)
/*      */       {
/*  799 */         return new RangeInt(j, k);
/*      */       }
/*      */ 
/*      */       
/*  803 */       warn("Invalid range: " + str);
/*  804 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  810 */     int i = Config.parseInt(str, -1);
/*      */     
/*  812 */     if (i < 0) {
/*      */       
/*  814 */       warn("Invalid integer: " + str);
/*  815 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  819 */     return new RangeInt(i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean parseBoolean(String str, boolean defVal) {
/*  826 */     if (str == null)
/*      */     {
/*  828 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  832 */     String s = str.toLowerCase().trim();
/*      */     
/*  834 */     if (s.equals("true"))
/*      */     {
/*  836 */       return true;
/*      */     }
/*  838 */     if (s.equals("false"))
/*      */     {
/*  840 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  844 */     warn("Invalid boolean: " + str);
/*  845 */     return defVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean parseBooleanObject(String str) {
/*  852 */     if (str == null)
/*      */     {
/*  854 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  858 */     String s = str.toLowerCase().trim();
/*      */     
/*  860 */     if (s.equals("true"))
/*      */     {
/*  862 */       return Boolean.TRUE;
/*      */     }
/*  864 */     if (s.equals("false"))
/*      */     {
/*  866 */       return Boolean.FALSE;
/*      */     }
/*      */ 
/*      */     
/*  870 */     warn("Invalid boolean: " + str);
/*  871 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseColor(String str, int defVal) {
/*  878 */     if (str == null)
/*      */     {
/*  880 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  884 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/*  888 */       int i = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  889 */       return i;
/*      */     }
/*  891 */     catch (NumberFormatException var3) {
/*      */       
/*  893 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseColor4(String str, int defVal) {
/*  900 */     if (str == null)
/*      */     {
/*  902 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  906 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/*  910 */       int i = (int)(Long.parseLong(str, 16) & 0xFFFFFFFFFFFFFFFFL);
/*  911 */       return i;
/*      */     }
/*  913 */     catch (NumberFormatException var3) {
/*      */       
/*  915 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer parseBlockRenderLayer(String str, EnumWorldBlockLayer def) {
/*  922 */     if (str == null)
/*      */     {
/*  924 */       return def;
/*      */     }
/*      */ 
/*      */     
/*  928 */     str = str.toLowerCase().trim();
/*  929 */     EnumWorldBlockLayer[] aenumworldblocklayer = EnumWorldBlockLayer.values();
/*      */     
/*  931 */     for (int i = 0; i < aenumworldblocklayer.length; i++) {
/*      */       
/*  933 */       EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[i];
/*      */       
/*  935 */       if (str.equals(enumworldblocklayer.name().toLowerCase()))
/*      */       {
/*  937 */         return enumworldblocklayer;
/*      */       }
/*      */     } 
/*      */     
/*  941 */     return def;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T parseObject(String str, T[] objs, INameGetter<T> nameGetter, String property) {
/*  947 */     if (str == null)
/*      */     {
/*  949 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  953 */     String s = str.toLowerCase().trim();
/*      */     
/*  955 */     for (int i = 0; i < objs.length; i++) {
/*      */       
/*  957 */       T t = objs[i];
/*  958 */       String s1 = nameGetter.getName(t);
/*      */       
/*  960 */       if (s1 != null && s1.toLowerCase().equals(s))
/*      */       {
/*  962 */         return t;
/*      */       }
/*      */     } 
/*      */     
/*  966 */     warn("Invalid " + property + ": " + str);
/*  967 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T[] parseObjects(String str, T[] objs, INameGetter nameGetter, String property, T[] errValue) {
/*  973 */     if (str == null)
/*      */     {
/*  975 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  979 */     str = str.toLowerCase().trim();
/*  980 */     String[] astring = Config.tokenize(str, " ");
/*  981 */     T[] at = (T[])Array.newInstance(objs.getClass().getComponentType(), astring.length);
/*      */     
/*  983 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  985 */       String s = astring[i];
/*  986 */       T t = parseObject(s, objs, nameGetter, property);
/*      */       
/*  988 */       if (t == null)
/*      */       {
/*  990 */         return errValue;
/*      */       }
/*      */       
/*  993 */       at[i] = t;
/*      */     } 
/*      */     
/*  996 */     return at;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Enum parseEnum(String str, Enum[] enums, String property) {
/* 1002 */     return parseObject(str, enums, NAME_GETTER_ENUM, property);
/*      */   }
/*      */ 
/*      */   
/*      */   public Enum[] parseEnums(String str, Enum[] enums, String property, Enum[] errValue) {
/* 1007 */     return parseObjects(str, enums, NAME_GETTER_ENUM, property, errValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumDyeColor[] parseDyeColors(String str, String property, EnumDyeColor[] errValue) {
/* 1012 */     return parseObjects(str, EnumDyeColor.values(), NAME_GETTER_DYE_COLOR, property, errValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public Weather[] parseWeather(String str, String property, Weather[] errValue) {
/* 1017 */     return parseObjects(str, Weather.values(), NAME_GETTER_ENUM, property, errValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public NbtTagValue parseNbtTagValue(String path, String value) {
/* 1022 */     return (path != null && value != null) ? new NbtTagValue(path, value) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public VillagerProfession[] parseProfessions(String profStr) {
/* 1027 */     if (profStr == null)
/*      */     {
/* 1029 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1033 */     List<VillagerProfession> list = new ArrayList<>();
/* 1034 */     String[] astring = Config.tokenize(profStr, " ");
/*      */     
/* 1036 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1038 */       String s = astring[i];
/* 1039 */       VillagerProfession villagerprofession = parseProfession(s);
/*      */       
/* 1041 */       if (villagerprofession == null) {
/*      */         
/* 1043 */         warn("Invalid profession: " + s);
/* 1044 */         return PROFESSIONS_INVALID;
/*      */       } 
/*      */       
/* 1047 */       list.add(villagerprofession);
/*      */     } 
/*      */     
/* 1050 */     if (list.isEmpty())
/*      */     {
/* 1052 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1056 */     VillagerProfession[] avillagerprofession = list.<VillagerProfession>toArray(new VillagerProfession[list.size()]);
/* 1057 */     return avillagerprofession;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private VillagerProfession parseProfession(String str) {
/* 1064 */     str = str.toLowerCase();
/* 1065 */     String[] astring = Config.tokenize(str, ":");
/*      */     
/* 1067 */     if (astring.length > 2)
/*      */     {
/* 1069 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1073 */     String s = astring[0];
/* 1074 */     String s1 = null;
/*      */     
/* 1076 */     if (astring.length > 1)
/*      */     {
/* 1078 */       s1 = astring[1];
/*      */     }
/*      */     
/* 1081 */     int i = parseProfessionId(s);
/*      */     
/* 1083 */     if (i < 0)
/*      */     {
/* 1085 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1089 */     int[] aint = null;
/*      */     
/* 1091 */     if (s1 != null) {
/*      */       
/* 1093 */       aint = parseCareerIds(i, s1);
/*      */       
/* 1095 */       if (aint == null)
/*      */       {
/* 1097 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1101 */     return new VillagerProfession(i, aint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseProfessionId(String str) {
/* 1108 */     int i = Config.parseInt(str, -1);
/* 1109 */     return (i >= 0) ? i : (str.equals("farmer") ? 0 : (str.equals("librarian") ? 1 : (str.equals("priest") ? 2 : (str.equals("blacksmith") ? 3 : (str.equals("butcher") ? 4 : (str.equals("nitwit") ? 5 : -1))))));
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] parseCareerIds(int prof, String str) {
/* 1114 */     Set<Integer> set = new HashSet<>();
/* 1115 */     String[] astring = Config.tokenize(str, ",");
/*      */     
/* 1117 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1119 */       String s = astring[i];
/* 1120 */       int j = parseCareerId(prof, s);
/*      */       
/* 1122 */       if (j < 0)
/*      */       {
/* 1124 */         return null;
/*      */       }
/*      */       
/* 1127 */       set.add(Integer.valueOf(j));
/*      */     } 
/*      */     
/* 1130 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 1131 */     int[] aint = new int[ainteger.length];
/*      */     
/* 1133 */     for (int k = 0; k < aint.length; k++)
/*      */     {
/* 1135 */       aint[k] = ainteger[k].intValue();
/*      */     }
/*      */     
/* 1138 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int parseCareerId(int prof, String str) {
/* 1143 */     int i = Config.parseInt(str, -1);
/*      */     
/* 1145 */     if (i >= 0)
/*      */     {
/* 1147 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 1151 */     if (prof == 0) {
/*      */       
/* 1153 */       if (str.equals("farmer"))
/*      */       {
/* 1155 */         return 1;
/*      */       }
/*      */       
/* 1158 */       if (str.equals("fisherman"))
/*      */       {
/* 1160 */         return 2;
/*      */       }
/*      */       
/* 1163 */       if (str.equals("shepherd"))
/*      */       {
/* 1165 */         return 3;
/*      */       }
/*      */       
/* 1168 */       if (str.equals("fletcher"))
/*      */       {
/* 1170 */         return 4;
/*      */       }
/*      */     } 
/*      */     
/* 1174 */     if (prof == 1) {
/*      */       
/* 1176 */       if (str.equals("librarian"))
/*      */       {
/* 1178 */         return 1;
/*      */       }
/*      */       
/* 1181 */       if (str.equals("cartographer"))
/*      */       {
/* 1183 */         return 2;
/*      */       }
/*      */     } 
/*      */     
/* 1187 */     if (prof == 2 && str.equals("cleric"))
/*      */     {
/* 1189 */       return 1;
/*      */     }
/*      */ 
/*      */     
/* 1193 */     if (prof == 3) {
/*      */       
/* 1195 */       if (str.equals("armor"))
/*      */       {
/* 1197 */         return 1;
/*      */       }
/*      */       
/* 1200 */       if (str.equals("weapon"))
/*      */       {
/* 1202 */         return 2;
/*      */       }
/*      */       
/* 1205 */       if (str.equals("tool"))
/*      */       {
/* 1207 */         return 3;
/*      */       }
/*      */     } 
/*      */     
/* 1211 */     if (prof == 4) {
/*      */       
/* 1213 */       if (str.equals("butcher"))
/*      */       {
/* 1215 */         return 1;
/*      */       }
/*      */       
/* 1218 */       if (str.equals("leather"))
/*      */       {
/* 1220 */         return 2;
/*      */       }
/*      */     } 
/*      */     
/* 1224 */     return (prof == 5 && str.equals("nitwit")) ? 1 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseItems(String str) {
/* 1231 */     str = str.trim();
/* 1232 */     Set<Integer> set = new TreeSet<>();
/* 1233 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/* 1235 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1237 */       String s = astring[i];
/* 1238 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1239 */       Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
/*      */       
/* 1241 */       if (item == null) {
/*      */         
/* 1243 */         warn("Item not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/* 1247 */         int j = Item.getIdFromItem(item);
/*      */         
/* 1249 */         if (j < 0) {
/*      */           
/* 1251 */           warn("Item has no ID: " + item + ", name: " + s);
/*      */         }
/*      */         else {
/*      */           
/* 1255 */           set.add(Integer.valueOf(j));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1260 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 1261 */     int[] aint = Config.toPrimitive(ainteger);
/* 1262 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] parseEntities(String str) {
/* 1267 */     str = str.trim();
/* 1268 */     Set<Integer> set = new TreeSet<>();
/* 1269 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/* 1271 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1273 */       String s = astring[i];
/* 1274 */       int j = EntityUtils.getEntityIdByName(s);
/*      */       
/* 1276 */       if (j < 0) {
/*      */         
/* 1278 */         warn("Entity not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/* 1282 */         set.add(Integer.valueOf(j));
/*      */       } 
/*      */     } 
/*      */     
/* 1286 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 1287 */     int[] aint = Config.toPrimitive(ainteger);
/* 1288 */     return aint;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\config\ConnectedParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */