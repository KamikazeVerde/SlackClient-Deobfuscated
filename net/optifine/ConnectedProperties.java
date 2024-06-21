/*      */ package net.optifine;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.config.MatchBlock;
/*      */ import net.optifine.config.Matches;
/*      */ import net.optifine.config.NbtTagValue;
/*      */ import net.optifine.config.RangeInt;
/*      */ import net.optifine.config.RangeListInt;
/*      */ import net.optifine.util.MathUtils;
/*      */ import net.optifine.util.TextureUtils;
/*      */ 
/*      */ public class ConnectedProperties
/*      */ {
/*   30 */   public String name = null;
/*   31 */   public String basePath = null;
/*   32 */   public MatchBlock[] matchBlocks = null;
/*   33 */   public int[] metadatas = null;
/*   34 */   public String[] matchTiles = null;
/*   35 */   public int method = 0;
/*   36 */   public String[] tiles = null;
/*   37 */   public int connect = 0;
/*   38 */   public int faces = 63;
/*   39 */   public BiomeGenBase[] biomes = null;
/*   40 */   public RangeListInt heights = null;
/*   41 */   public int renderPass = 0;
/*      */   public boolean innerSeams = false;
/*   43 */   public int[] ctmTileIndexes = null;
/*   44 */   public int width = 0;
/*   45 */   public int height = 0;
/*   46 */   public int[] weights = null;
/*   47 */   public int randomLoops = 0;
/*   48 */   public int symmetry = 1;
/*      */   public boolean linked = false;
/*   50 */   public NbtTagValue nbtName = null;
/*   51 */   public int[] sumWeights = null;
/*   52 */   public int sumAllWeights = 1;
/*   53 */   public TextureAtlasSprite[] matchTileIcons = null;
/*   54 */   public TextureAtlasSprite[] tileIcons = null;
/*   55 */   public MatchBlock[] connectBlocks = null;
/*   56 */   public String[] connectTiles = null;
/*   57 */   public TextureAtlasSprite[] connectTileIcons = null;
/*   58 */   public int tintIndex = -1;
/*   59 */   public IBlockState tintBlockState = Blocks.air.getDefaultState();
/*   60 */   public EnumWorldBlockLayer layer = null;
/*      */   
/*      */   public static final int METHOD_NONE = 0;
/*      */   public static final int METHOD_CTM = 1;
/*      */   public static final int METHOD_HORIZONTAL = 2;
/*      */   public static final int METHOD_TOP = 3;
/*      */   public static final int METHOD_RANDOM = 4;
/*      */   public static final int METHOD_REPEAT = 5;
/*      */   public static final int METHOD_VERTICAL = 6;
/*      */   public static final int METHOD_FIXED = 7;
/*      */   public static final int METHOD_HORIZONTAL_VERTICAL = 8;
/*      */   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
/*      */   public static final int METHOD_CTM_COMPACT = 10;
/*      */   public static final int METHOD_OVERLAY = 11;
/*      */   public static final int METHOD_OVERLAY_FIXED = 12;
/*      */   public static final int METHOD_OVERLAY_RANDOM = 13;
/*      */   public static final int METHOD_OVERLAY_REPEAT = 14;
/*      */   public static final int METHOD_OVERLAY_CTM = 15;
/*      */   public static final int CONNECT_NONE = 0;
/*      */   public static final int CONNECT_BLOCK = 1;
/*      */   public static final int CONNECT_TILE = 2;
/*      */   public static final int CONNECT_MATERIAL = 3;
/*      */   public static final int CONNECT_UNKNOWN = 128;
/*      */   public static final int FACE_BOTTOM = 1;
/*      */   public static final int FACE_TOP = 2;
/*      */   public static final int FACE_NORTH = 4;
/*      */   public static final int FACE_SOUTH = 8;
/*      */   public static final int FACE_WEST = 16;
/*      */   public static final int FACE_EAST = 32;
/*      */   public static final int FACE_SIDES = 60;
/*      */   public static final int FACE_ALL = 63;
/*      */   public static final int FACE_UNKNOWN = 128;
/*      */   public static final int SYMMETRY_NONE = 1;
/*      */   public static final int SYMMETRY_OPPOSITE = 2;
/*      */   public static final int SYMMETRY_ALL = 6;
/*      */   public static final int SYMMETRY_UNKNOWN = 128;
/*      */   public static final String TILE_SKIP_PNG = "<skip>.png";
/*      */   public static final String TILE_DEFAULT_PNG = "<default>.png";
/*      */   
/*      */   public ConnectedProperties(Properties props, String path) {
/*  100 */     ConnectedParser connectedparser = new ConnectedParser("ConnectedTextures");
/*  101 */     this.name = connectedparser.parseName(path);
/*  102 */     this.basePath = connectedparser.parseBasePath(path);
/*  103 */     this.matchBlocks = connectedparser.parseMatchBlocks(props.getProperty("matchBlocks"));
/*  104 */     this.metadatas = connectedparser.parseIntList(props.getProperty("metadata"));
/*  105 */     this.matchTiles = parseMatchTiles(props.getProperty("matchTiles"));
/*  106 */     this.method = parseMethod(props.getProperty("method"));
/*  107 */     this.tiles = parseTileNames(props.getProperty("tiles"));
/*  108 */     this.connect = parseConnect(props.getProperty("connect"));
/*  109 */     this.faces = parseFaces(props.getProperty("faces"));
/*  110 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  111 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*      */     
/*  113 */     if (this.heights == null) {
/*      */       
/*  115 */       int i = connectedparser.parseInt(props.getProperty("minHeight"), -1);
/*  116 */       int j = connectedparser.parseInt(props.getProperty("maxHeight"), 1024);
/*      */       
/*  118 */       if (i != -1 || j != 1024)
/*      */       {
/*  120 */         this.heights = new RangeListInt(new RangeInt(i, j));
/*      */       }
/*      */     } 
/*      */     
/*  124 */     this.renderPass = connectedparser.parseInt(props.getProperty("renderPass"), -1);
/*  125 */     this.innerSeams = connectedparser.parseBoolean(props.getProperty("innerSeams"), false);
/*  126 */     this.ctmTileIndexes = parseCtmTileIndexes(props);
/*  127 */     this.width = connectedparser.parseInt(props.getProperty("width"), -1);
/*  128 */     this.height = connectedparser.parseInt(props.getProperty("height"), -1);
/*  129 */     this.weights = connectedparser.parseIntList(props.getProperty("weights"));
/*  130 */     this.randomLoops = connectedparser.parseInt(props.getProperty("randomLoops"), 0);
/*  131 */     this.symmetry = parseSymmetry(props.getProperty("symmetry"));
/*  132 */     this.linked = connectedparser.parseBoolean(props.getProperty("linked"), false);
/*  133 */     this.nbtName = connectedparser.parseNbtTagValue("name", props.getProperty("name"));
/*  134 */     this.connectBlocks = connectedparser.parseMatchBlocks(props.getProperty("connectBlocks"));
/*  135 */     this.connectTiles = parseMatchTiles(props.getProperty("connectTiles"));
/*  136 */     this.tintIndex = connectedparser.parseInt(props.getProperty("tintIndex"), -1);
/*  137 */     this.tintBlockState = connectedparser.parseBlockState(props.getProperty("tintBlock"), Blocks.air.getDefaultState());
/*  138 */     this.layer = connectedparser.parseBlockRenderLayer(props.getProperty("layer"), EnumWorldBlockLayer.CUTOUT_MIPPED);
/*      */   }
/*      */ 
/*      */   
/*      */   private int[] parseCtmTileIndexes(Properties props) {
/*  143 */     if (this.tiles == null)
/*      */     {
/*  145 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  149 */     Map<Integer, Integer> map = new HashMap<>();
/*      */     
/*  151 */     for (Object object : props.keySet()) {
/*      */       
/*  153 */       if (object instanceof String) {
/*      */         
/*  155 */         String s = (String)object;
/*  156 */         String s1 = "ctm.";
/*      */         
/*  158 */         if (s.startsWith(s1)) {
/*      */           
/*  160 */           String s2 = s.substring(s1.length());
/*  161 */           String s3 = props.getProperty(s);
/*      */           
/*  163 */           if (s3 != null) {
/*      */             
/*  165 */             s3 = s3.trim();
/*  166 */             int i = Config.parseInt(s2, -1);
/*      */             
/*  168 */             if (i >= 0 && i <= 46) {
/*      */               
/*  170 */               int j = Config.parseInt(s3, -1);
/*      */               
/*  172 */               if (j >= 0 && j < this.tiles.length) {
/*      */                 
/*  174 */                 map.put(Integer.valueOf(i), Integer.valueOf(j));
/*      */                 
/*      */                 continue;
/*      */               } 
/*  178 */               Config.warn("Invalid CTM tile index: " + s3);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*  183 */             Config.warn("Invalid CTM index: " + s2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  190 */     if (map.isEmpty())
/*      */     {
/*  192 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  196 */     int[] aint = new int[47];
/*      */     
/*  198 */     for (int k = 0; k < aint.length; k++) {
/*      */       
/*  200 */       aint[k] = -1;
/*      */       
/*  202 */       if (map.containsKey(Integer.valueOf(k)))
/*      */       {
/*  204 */         aint[k] = ((Integer)map.get(Integer.valueOf(k))).intValue();
/*      */       }
/*      */     } 
/*      */     
/*  208 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] parseMatchTiles(String str) {
/*  215 */     if (str == null)
/*      */     {
/*  217 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  221 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/*  223 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  225 */       String s = astring[i];
/*      */       
/*  227 */       if (s.endsWith(".png"))
/*      */       {
/*  229 */         s = s.substring(0, s.length() - 4);
/*      */       }
/*      */       
/*  232 */       s = TextureUtils.fixResourcePath(s, this.basePath);
/*  233 */       astring[i] = s;
/*      */     } 
/*      */     
/*  236 */     return astring;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseName(String path) {
/*  242 */     String s = path;
/*  243 */     int i = path.lastIndexOf('/');
/*      */     
/*  245 */     if (i >= 0)
/*      */     {
/*  247 */       s = path.substring(i + 1);
/*      */     }
/*      */     
/*  250 */     int j = s.lastIndexOf('.');
/*      */     
/*  252 */     if (j >= 0)
/*      */     {
/*  254 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*  257 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String path) {
/*  262 */     int i = path.lastIndexOf('/');
/*  263 */     return (i < 0) ? "" : path.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] parseTileNames(String str) {
/*  268 */     if (str == null)
/*      */     {
/*  270 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  274 */     List<String> list = new ArrayList();
/*  275 */     String[] astring = Config.tokenize(str, " ,");
/*      */ 
/*      */     
/*  278 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  280 */       String s = astring[i];
/*      */       
/*  282 */       if (s.contains("-")) {
/*      */         
/*  284 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  286 */         if (astring1.length == 2) {
/*      */           
/*  288 */           int j = Config.parseInt(astring1[0], -1);
/*  289 */           int k = Config.parseInt(astring1[1], -1);
/*      */           
/*  291 */           if (j >= 0 && k >= 0) {
/*      */             
/*  293 */             if (j > k) {
/*      */               
/*  295 */               Config.warn("Invalid interval: " + s + ", when parsing: " + str);
/*      */             }
/*      */             else {
/*      */               
/*  299 */               int l = j;
/*      */ 
/*      */ 
/*      */               
/*  303 */               while (l <= k) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  308 */                 list.add(String.valueOf(l));
/*  309 */                 l++;
/*      */               } 
/*      */             }  continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  315 */       list.add(s);
/*      */       continue;
/*      */     } 
/*  318 */     String[] astring2 = list.<String>toArray(new String[list.size()]);
/*      */     
/*  320 */     for (int i1 = 0; i1 < astring2.length; i1++) {
/*      */       
/*  322 */       String s1 = astring2[i1];
/*  323 */       s1 = TextureUtils.fixResourcePath(s1, this.basePath);
/*      */       
/*  325 */       if (!s1.startsWith(this.basePath) && !s1.startsWith("textures/") && !s1.startsWith("mcpatcher/"))
/*      */       {
/*  327 */         s1 = this.basePath + "/" + s1;
/*      */       }
/*      */       
/*  330 */       if (s1.endsWith(".png"))
/*      */       {
/*  332 */         s1 = s1.substring(0, s1.length() - 4);
/*      */       }
/*      */       
/*  335 */       if (s1.startsWith("/"))
/*      */       {
/*  337 */         s1 = s1.substring(1);
/*      */       }
/*      */       
/*  340 */       astring2[i1] = s1;
/*      */     } 
/*      */     
/*  343 */     return astring2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseSymmetry(String str) {
/*  349 */     if (str == null)
/*      */     {
/*  351 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  355 */     str = str.trim();
/*      */     
/*  357 */     if (str.equals("opposite"))
/*      */     {
/*  359 */       return 2;
/*      */     }
/*  361 */     if (str.equals("all"))
/*      */     {
/*  363 */       return 6;
/*      */     }
/*      */ 
/*      */     
/*  367 */     Config.warn("Unknown symmetry: " + str);
/*  368 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFaces(String str) {
/*  375 */     if (str == null)
/*      */     {
/*  377 */       return 63;
/*      */     }
/*      */ 
/*      */     
/*  381 */     String[] astring = Config.tokenize(str, " ,");
/*  382 */     int i = 0;
/*      */     
/*  384 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  386 */       String s = astring[j];
/*  387 */       int k = parseFace(s);
/*  388 */       i |= k;
/*      */     } 
/*      */     
/*  391 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFace(String str) {
/*  397 */     str = str.toLowerCase();
/*      */     
/*  399 */     if (!str.equals("bottom") && !str.equals("down")) {
/*      */       
/*  401 */       if (!str.equals("top") && !str.equals("up")) {
/*      */         
/*  403 */         if (str.equals("north"))
/*      */         {
/*  405 */           return 4;
/*      */         }
/*  407 */         if (str.equals("south"))
/*      */         {
/*  409 */           return 8;
/*      */         }
/*  411 */         if (str.equals("east"))
/*      */         {
/*  413 */           return 32;
/*      */         }
/*  415 */         if (str.equals("west"))
/*      */         {
/*  417 */           return 16;
/*      */         }
/*  419 */         if (str.equals("sides"))
/*      */         {
/*  421 */           return 60;
/*      */         }
/*  423 */         if (str.equals("all"))
/*      */         {
/*  425 */           return 63;
/*      */         }
/*      */ 
/*      */         
/*  429 */         Config.warn("Unknown face: " + str);
/*  430 */         return 128;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  435 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  440 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseConnect(String str) {
/*  446 */     if (str == null)
/*      */     {
/*  448 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  452 */     str = str.trim();
/*      */     
/*  454 */     if (str.equals("block"))
/*      */     {
/*  456 */       return 1;
/*      */     }
/*  458 */     if (str.equals("tile"))
/*      */     {
/*  460 */       return 2;
/*      */     }
/*  462 */     if (str.equals("material"))
/*      */     {
/*  464 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  468 */     Config.warn("Unknown connect: " + str);
/*  469 */     return 128;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IProperty getProperty(String key, Collection properties) {
/*  476 */     for (Object e : properties) {
/*      */       
/*  478 */       IProperty iproperty = (IProperty)e;
/*  479 */       if (key.equals(iproperty.getName()))
/*      */       {
/*  481 */         return iproperty;
/*      */       }
/*      */     } 
/*      */     
/*  485 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int parseMethod(String str) {
/*  490 */     if (str == null)
/*      */     {
/*  492 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  496 */     str = str.trim();
/*      */     
/*  498 */     if (!str.equals("ctm") && !str.equals("glass")) {
/*      */       
/*  500 */       if (str.equals("ctm_compact"))
/*      */       {
/*  502 */         return 10;
/*      */       }
/*  504 */       if (!str.equals("horizontal") && !str.equals("bookshelf")) {
/*      */         
/*  506 */         if (str.equals("vertical"))
/*      */         {
/*  508 */           return 6;
/*      */         }
/*  510 */         if (str.equals("top"))
/*      */         {
/*  512 */           return 3;
/*      */         }
/*  514 */         if (str.equals("random"))
/*      */         {
/*  516 */           return 4;
/*      */         }
/*  518 */         if (str.equals("repeat"))
/*      */         {
/*  520 */           return 5;
/*      */         }
/*  522 */         if (str.equals("fixed"))
/*      */         {
/*  524 */           return 7;
/*      */         }
/*  526 */         if (!str.equals("horizontal+vertical") && !str.equals("h+v")) {
/*      */           
/*  528 */           if (!str.equals("vertical+horizontal") && !str.equals("v+h")) {
/*      */             
/*  530 */             if (str.equals("overlay"))
/*      */             {
/*  532 */               return 11;
/*      */             }
/*  534 */             if (str.equals("overlay_fixed"))
/*      */             {
/*  536 */               return 12;
/*      */             }
/*  538 */             if (str.equals("overlay_random"))
/*      */             {
/*  540 */               return 13;
/*      */             }
/*  542 */             if (str.equals("overlay_repeat"))
/*      */             {
/*  544 */               return 14;
/*      */             }
/*  546 */             if (str.equals("overlay_ctm"))
/*      */             {
/*  548 */               return 15;
/*      */             }
/*      */ 
/*      */             
/*  552 */             Config.warn("Unknown method: " + str);
/*  553 */             return 0;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  558 */           return 9;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  563 */         return 8;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  568 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  573 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String path) {
/*  580 */     if (this.name != null && this.name.length() > 0) {
/*      */       
/*  582 */       if (this.basePath == null) {
/*      */         
/*  584 */         Config.warn("No base path found: " + path);
/*  585 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  589 */       if (this.matchBlocks == null)
/*      */       {
/*  591 */         this.matchBlocks = detectMatchBlocks();
/*      */       }
/*      */       
/*  594 */       if (this.matchTiles == null && this.matchBlocks == null)
/*      */       {
/*  596 */         this.matchTiles = detectMatchTiles();
/*      */       }
/*      */       
/*  599 */       if (this.matchBlocks == null && this.matchTiles == null) {
/*      */         
/*  601 */         Config.warn("No matchBlocks or matchTiles specified: " + path);
/*  602 */         return false;
/*      */       } 
/*  604 */       if (this.method == 0) {
/*      */         
/*  606 */         Config.warn("No method: " + path);
/*  607 */         return false;
/*      */       } 
/*  609 */       if (this.tiles != null && this.tiles.length > 0) {
/*      */         
/*  611 */         if (this.connect == 0)
/*      */         {
/*  613 */           this.connect = detectConnect();
/*      */         }
/*      */         
/*  616 */         if (this.connect == 128) {
/*      */           
/*  618 */           Config.warn("Invalid connect in: " + path);
/*  619 */           return false;
/*      */         } 
/*  621 */         if (this.renderPass > 0) {
/*      */           
/*  623 */           Config.warn("Render pass not supported: " + this.renderPass);
/*  624 */           return false;
/*      */         } 
/*  626 */         if ((this.faces & 0x80) != 0) {
/*      */           
/*  628 */           Config.warn("Invalid faces in: " + path);
/*  629 */           return false;
/*      */         } 
/*  631 */         if ((this.symmetry & 0x80) != 0) {
/*      */           
/*  633 */           Config.warn("Invalid symmetry in: " + path);
/*  634 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  638 */         switch (this.method) {
/*      */           
/*      */           case 1:
/*  641 */             return isValidCtm(path);
/*      */           
/*      */           case 2:
/*  644 */             return isValidHorizontal(path);
/*      */           
/*      */           case 3:
/*  647 */             return isValidTop(path);
/*      */           
/*      */           case 4:
/*  650 */             return isValidRandom(path);
/*      */           
/*      */           case 5:
/*  653 */             return isValidRepeat(path);
/*      */           
/*      */           case 6:
/*  656 */             return isValidVertical(path);
/*      */           
/*      */           case 7:
/*  659 */             return isValidFixed(path);
/*      */           
/*      */           case 8:
/*  662 */             return isValidHorizontalVertical(path);
/*      */           
/*      */           case 9:
/*  665 */             return isValidVerticalHorizontal(path);
/*      */           
/*      */           case 10:
/*  668 */             return isValidCtmCompact(path);
/*      */           
/*      */           case 11:
/*  671 */             return isValidOverlay(path);
/*      */           
/*      */           case 12:
/*  674 */             return isValidOverlayFixed(path);
/*      */           
/*      */           case 13:
/*  677 */             return isValidOverlayRandom(path);
/*      */           
/*      */           case 14:
/*  680 */             return isValidOverlayRepeat(path);
/*      */           
/*      */           case 15:
/*  683 */             return isValidOverlayCtm(path);
/*      */         } 
/*      */         
/*  686 */         Config.warn("Unknown method: " + path);
/*  687 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  693 */       Config.warn("No tiles specified: " + path);
/*  694 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  700 */     Config.warn("No name found: " + path);
/*  701 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int detectConnect() {
/*  707 */     return (this.matchBlocks != null) ? 1 : ((this.matchTiles != null) ? 2 : 128);
/*      */   }
/*      */ 
/*      */   
/*      */   private MatchBlock[] detectMatchBlocks() {
/*  712 */     int[] aint = detectMatchBlockIds();
/*      */     
/*  714 */     if (aint == null)
/*      */     {
/*  716 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  720 */     MatchBlock[] amatchblock = new MatchBlock[aint.length];
/*      */     
/*  722 */     for (int i = 0; i < amatchblock.length; i++)
/*      */     {
/*  724 */       amatchblock[i] = new MatchBlock(aint[i]);
/*      */     }
/*      */     
/*  727 */     return amatchblock;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectMatchBlockIds() {
/*  733 */     if (!this.name.startsWith("block"))
/*      */     {
/*  735 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  739 */     int i = "block".length();
/*      */     
/*      */     int j;
/*  742 */     for (j = i; j < this.name.length(); j++) {
/*      */       
/*  744 */       char c0 = this.name.charAt(j);
/*      */       
/*  746 */       if (c0 < '0' || c0 > '9') {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  752 */     if (j == i)
/*      */     {
/*  754 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  758 */     String s = this.name.substring(i, j);
/*  759 */     int k = Config.parseInt(s, -1);
/*  760 */     (new int[1])[0] = k; return (k < 0) ? null : new int[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] detectMatchTiles() {
/*  767 */     TextureAtlasSprite textureatlassprite = getIcon(this.name);
/*  768 */     (new String[1])[0] = this.name; return (textureatlassprite == null) ? null : new String[1];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getIcon(String iconName) {
/*  773 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  774 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(iconName);
/*      */     
/*  776 */     if (textureatlassprite != null)
/*      */     {
/*  778 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/*  782 */     textureatlassprite = texturemap.getSpriteSafe("blocks/" + iconName);
/*  783 */     return textureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtm(String path) {
/*  789 */     if (this.tiles == null)
/*      */     {
/*  791 */       this.tiles = parseTileNames("0-11 16-27 32-43 48-58");
/*      */     }
/*      */     
/*  794 */     if (this.tiles.length < 47) {
/*      */       
/*  796 */       Config.warn("Invalid tiles, must be at least 47: " + path);
/*  797 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  801 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtmCompact(String path) {
/*  807 */     if (this.tiles == null)
/*      */     {
/*  809 */       this.tiles = parseTileNames("0-4");
/*      */     }
/*      */     
/*  812 */     if (this.tiles.length < 5) {
/*      */       
/*  814 */       Config.warn("Invalid tiles, must be at least 5: " + path);
/*  815 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  819 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlay(String path) {
/*  825 */     if (this.tiles == null)
/*      */     {
/*  827 */       this.tiles = parseTileNames("0-16");
/*      */     }
/*      */     
/*  830 */     if (this.tiles.length < 17) {
/*      */       
/*  832 */       Config.warn("Invalid tiles, must be at least 17: " + path);
/*  833 */       return false;
/*      */     } 
/*  835 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  837 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  841 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  842 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayFixed(String path) {
/*  848 */     if (!isValidFixed(path))
/*      */     {
/*  850 */       return false;
/*      */     }
/*  852 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  854 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  858 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  859 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayRandom(String path) {
/*  865 */     if (!isValidRandom(path))
/*      */     {
/*  867 */       return false;
/*      */     }
/*  869 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  871 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  875 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  876 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayRepeat(String path) {
/*  882 */     if (!isValidRepeat(path))
/*      */     {
/*  884 */       return false;
/*      */     }
/*  886 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  888 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  892 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  893 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayCtm(String path) {
/*  899 */     if (!isValidCtm(path))
/*      */     {
/*  901 */       return false;
/*      */     }
/*  903 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  905 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  909 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  910 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontal(String path) {
/*  916 */     if (this.tiles == null)
/*      */     {
/*  918 */       this.tiles = parseTileNames("12-15");
/*      */     }
/*      */     
/*  921 */     if (this.tiles.length != 4) {
/*      */       
/*  923 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/*  924 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  928 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVertical(String path) {
/*  934 */     if (this.tiles == null) {
/*      */       
/*  936 */       Config.warn("No tiles defined for vertical: " + path);
/*  937 */       return false;
/*      */     } 
/*  939 */     if (this.tiles.length != 4) {
/*      */       
/*  941 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/*  942 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  946 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontalVertical(String path) {
/*  952 */     if (this.tiles == null) {
/*      */       
/*  954 */       Config.warn("No tiles defined for horizontal+vertical: " + path);
/*  955 */       return false;
/*      */     } 
/*  957 */     if (this.tiles.length != 7) {
/*      */       
/*  959 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/*  960 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  964 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVerticalHorizontal(String path) {
/*  970 */     if (this.tiles == null) {
/*      */       
/*  972 */       Config.warn("No tiles defined for vertical+horizontal: " + path);
/*  973 */       return false;
/*      */     } 
/*  975 */     if (this.tiles.length != 7) {
/*      */       
/*  977 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/*  978 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  982 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRandom(String path) {
/*  988 */     if (this.tiles != null && this.tiles.length > 0) {
/*      */       
/*  990 */       if (this.weights != null) {
/*      */         
/*  992 */         if (this.weights.length > this.tiles.length) {
/*      */           
/*  994 */           Config.warn("More weights defined than tiles, trimming weights: " + path);
/*  995 */           int[] aint = new int[this.tiles.length];
/*  996 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/*  997 */           this.weights = aint;
/*      */         } 
/*      */         
/* 1000 */         if (this.weights.length < this.tiles.length) {
/*      */           
/* 1002 */           Config.warn("Less weights defined than tiles, expanding weights: " + path);
/* 1003 */           int[] aint1 = new int[this.tiles.length];
/* 1004 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/* 1005 */           int i = MathUtils.getAverage(this.weights);
/*      */           
/* 1007 */           for (int j = this.weights.length; j < aint1.length; j++)
/*      */           {
/* 1009 */             aint1[j] = i;
/*      */           }
/*      */           
/* 1012 */           this.weights = aint1;
/*      */         } 
/*      */         
/* 1015 */         this.sumWeights = new int[this.weights.length];
/* 1016 */         int k = 0;
/*      */         
/* 1018 */         for (int l = 0; l < this.weights.length; l++) {
/*      */           
/* 1020 */           k += this.weights[l];
/* 1021 */           this.sumWeights[l] = k;
/*      */         } 
/*      */         
/* 1024 */         this.sumAllWeights = k;
/*      */         
/* 1026 */         if (this.sumAllWeights <= 0) {
/*      */           
/* 1028 */           Config.warn("Invalid sum of all weights: " + k);
/* 1029 */           this.sumAllWeights = 1;
/*      */         } 
/*      */       } 
/*      */       
/* 1033 */       if (this.randomLoops >= 0 && this.randomLoops <= 9)
/*      */       {
/* 1035 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1039 */       Config.warn("Invalid randomLoops: " + this.randomLoops);
/* 1040 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1045 */     Config.warn("Tiles not defined: " + path);
/* 1046 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRepeat(String path) {
/* 1052 */     if (this.tiles == null) {
/*      */       
/* 1054 */       Config.warn("Tiles not defined: " + path);
/* 1055 */       return false;
/*      */     } 
/* 1057 */     if (this.width <= 0) {
/*      */       
/* 1059 */       Config.warn("Invalid width: " + path);
/* 1060 */       return false;
/*      */     } 
/* 1062 */     if (this.height <= 0) {
/*      */       
/* 1064 */       Config.warn("Invalid height: " + path);
/* 1065 */       return false;
/*      */     } 
/* 1067 */     if (this.tiles.length != this.width * this.height) {
/*      */       
/* 1069 */       Config.warn("Number of tiles does not equal width x height: " + path);
/* 1070 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1074 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidFixed(String path) {
/* 1080 */     if (this.tiles == null) {
/*      */       
/* 1082 */       Config.warn("Tiles not defined: " + path);
/* 1083 */       return false;
/*      */     } 
/* 1085 */     if (this.tiles.length != 1) {
/*      */       
/* 1087 */       Config.warn("Number of tiles should be 1 for method: fixed.");
/* 1088 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1092 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidTop(String path) {
/* 1098 */     if (this.tiles == null)
/*      */     {
/* 1100 */       this.tiles = parseTileNames("66");
/*      */     }
/*      */     
/* 1103 */     if (this.tiles.length != 1) {
/*      */       
/* 1105 */       Config.warn("Invalid tiles, must be exactly 1: " + path);
/* 1106 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1110 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(TextureMap textureMap) {
/* 1116 */     if (this.matchTiles != null)
/*      */     {
/* 1118 */       this.matchTileIcons = registerIcons(this.matchTiles, textureMap, false, false);
/*      */     }
/*      */     
/* 1121 */     if (this.connectTiles != null)
/*      */     {
/* 1123 */       this.connectTileIcons = registerIcons(this.connectTiles, textureMap, false, false);
/*      */     }
/*      */     
/* 1126 */     if (this.tiles != null)
/*      */     {
/* 1128 */       this.tileIcons = registerIcons(this.tiles, textureMap, true, !isMethodOverlay(this.method));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isMethodOverlay(int method) {
/* 1134 */     switch (method) {
/*      */       
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/* 1141 */         return true;
/*      */     } 
/*      */     
/* 1144 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite[] registerIcons(String[] tileNames, TextureMap textureMap, boolean skipTiles, boolean defaultTiles) {
/* 1150 */     if (tileNames == null)
/*      */     {
/* 1152 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1156 */     List<TextureAtlasSprite> list = new ArrayList();
/*      */     
/* 1158 */     for (int i = 0; i < tileNames.length; i++) {
/*      */       
/* 1160 */       String s = tileNames[i];
/* 1161 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1162 */       String s1 = resourcelocation.getResourceDomain();
/* 1163 */       String s2 = resourcelocation.getResourcePath();
/*      */       
/* 1165 */       if (!s2.contains("/"))
/*      */       {
/* 1167 */         s2 = "textures/blocks/" + s2;
/*      */       }
/*      */       
/* 1170 */       String s3 = s2 + ".png";
/*      */       
/* 1172 */       if (skipTiles && s3.endsWith("<skip>.png")) {
/*      */         
/* 1174 */         list.add(null);
/*      */       }
/* 1176 */       else if (defaultTiles && s3.endsWith("<default>.png")) {
/*      */         
/* 1178 */         list.add(ConnectedTextures.SPRITE_DEFAULT);
/*      */       }
/*      */       else {
/*      */         
/* 1182 */         ResourceLocation resourcelocation1 = new ResourceLocation(s1, s3);
/* 1183 */         boolean flag = Config.hasResource(resourcelocation1);
/*      */         
/* 1185 */         if (!flag)
/*      */         {
/* 1187 */           Config.warn("File not found: " + s3);
/*      */         }
/*      */         
/* 1190 */         String s4 = "textures/";
/* 1191 */         String s5 = s2;
/*      */         
/* 1193 */         if (s2.startsWith(s4))
/*      */         {
/* 1195 */           s5 = s2.substring(s4.length());
/*      */         }
/*      */         
/* 1198 */         ResourceLocation resourcelocation2 = new ResourceLocation(s1, s5);
/* 1199 */         TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation2);
/* 1200 */         list.add(textureatlassprite);
/*      */       } 
/*      */     } 
/*      */     
/* 1204 */     TextureAtlasSprite[] atextureatlassprite = list.<TextureAtlasSprite>toArray(new TextureAtlasSprite[list.size()]);
/* 1205 */     return atextureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesBlockId(int blockId) {
/* 1211 */     return Matches.blockId(blockId, this.matchBlocks);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesBlock(int blockId, int metadata) {
/* 1216 */     return !Matches.block(blockId, metadata, this.matchBlocks) ? false : Matches.metadata(metadata, this.metadatas);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesIcon(TextureAtlasSprite icon) {
/* 1221 */     return Matches.sprite(icon, this.matchTileIcons);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1226 */     return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", matchTiles: " + Config.arrayToString((Object[])this.matchTiles);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesBiome(BiomeGenBase biome) {
/* 1231 */     return Matches.biome(biome, this.biomes);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMetadataMax() {
/* 1236 */     int i = -1;
/* 1237 */     i = getMax(this.metadatas, i);
/*      */     
/* 1239 */     if (this.matchBlocks != null)
/*      */     {
/* 1241 */       for (int j = 0; j < this.matchBlocks.length; j++) {
/*      */         
/* 1243 */         MatchBlock matchblock = this.matchBlocks[j];
/* 1244 */         i = getMax(matchblock.getMetadatas(), i);
/*      */       } 
/*      */     }
/*      */     
/* 1248 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getMax(int[] mds, int max) {
/* 1253 */     if (mds == null)
/*      */     {
/* 1255 */       return max;
/*      */     }
/*      */ 
/*      */     
/* 1259 */     for (int i = 0; i < mds.length; i++) {
/*      */       
/* 1261 */       int j = mds[i];
/*      */       
/* 1263 */       if (j > max)
/*      */       {
/* 1265 */         max = j;
/*      */       }
/*      */     } 
/*      */     
/* 1269 */     return max;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\ConnectedProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */