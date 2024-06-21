/*      */ package net.optifine;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.BlockPart;
/*      */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*      */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*      */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*      */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*      */ import net.minecraft.client.renderer.texture.ITextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelBakery;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*      */ import net.minecraft.client.resources.model.ModelRotation;
/*      */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.config.IParserInt;
/*      */ import net.optifine.config.NbtTagValue;
/*      */ import net.optifine.config.ParserEnchantmentId;
/*      */ import net.optifine.config.RangeInt;
/*      */ import net.optifine.config.RangeListInt;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.Blender;
/*      */ import net.optifine.util.StrUtils;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class CustomItemProperties
/*      */ {
/*   48 */   public String name = null;
/*   49 */   public String basePath = null;
/*   50 */   public int type = 1;
/*   51 */   public int[] items = null;
/*   52 */   public String texture = null;
/*   53 */   public Map<String, String> mapTextures = null;
/*   54 */   public String model = null;
/*   55 */   public Map<String, String> mapModels = null;
/*   56 */   public RangeListInt damage = null;
/*      */   public boolean damagePercent = false;
/*   58 */   public int damageMask = 0;
/*   59 */   public RangeListInt stackSize = null;
/*   60 */   public RangeListInt enchantmentIds = null;
/*   61 */   public RangeListInt enchantmentLevels = null;
/*   62 */   public NbtTagValue[] nbtTagValues = null;
/*   63 */   public int hand = 0;
/*   64 */   public int blend = 1;
/*   65 */   public float speed = 0.0F;
/*   66 */   public float rotation = 0.0F;
/*   67 */   public int layer = 0;
/*   68 */   public float duration = 1.0F;
/*   69 */   public int weight = 0;
/*   70 */   public ResourceLocation textureLocation = null;
/*   71 */   public Map mapTextureLocations = null;
/*   72 */   public TextureAtlasSprite sprite = null;
/*   73 */   public Map mapSprites = null;
/*   74 */   public IBakedModel bakedModelTexture = null;
/*   75 */   public Map<String, IBakedModel> mapBakedModelsTexture = null;
/*   76 */   public IBakedModel bakedModelFull = null;
/*   77 */   public Map<String, IBakedModel> mapBakedModelsFull = null;
/*   78 */   private int textureWidth = 0;
/*   79 */   private int textureHeight = 0;
/*      */   
/*      */   public static final int TYPE_UNKNOWN = 0;
/*      */   public static final int TYPE_ITEM = 1;
/*      */   public static final int TYPE_ENCHANTMENT = 2;
/*      */   public static final int TYPE_ARMOR = 3;
/*      */   public static final int HAND_ANY = 0;
/*      */   public static final int HAND_MAIN = 1;
/*      */   public static final int HAND_OFF = 2;
/*      */   public static final String INVENTORY = "inventory";
/*      */   
/*      */   public CustomItemProperties(Properties props, String path) {
/*   91 */     this.name = parseName(path);
/*   92 */     this.basePath = parseBasePath(path);
/*   93 */     this.type = parseType(props.getProperty("type"));
/*   94 */     this.items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
/*   95 */     this.mapModels = parseModels(props, this.basePath);
/*   96 */     this.model = parseModel(props.getProperty("model"), path, this.basePath, this.type, this.mapModels);
/*   97 */     this.mapTextures = parseTextures(props, this.basePath);
/*   98 */     boolean flag = (this.mapModels == null && this.model == null);
/*   99 */     this.texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, this.basePath, this.type, this.mapTextures, flag);
/*  100 */     String s = props.getProperty("damage");
/*      */     
/*  102 */     if (s != null) {
/*      */       
/*  104 */       this.damagePercent = s.contains("%");
/*  105 */       s = s.replace("%", "");
/*  106 */       this.damage = parseRangeListInt(s);
/*  107 */       this.damageMask = parseInt(props.getProperty("damageMask"), 0);
/*      */     } 
/*      */     
/*  110 */     this.stackSize = parseRangeListInt(props.getProperty("stackSize"));
/*  111 */     this.enchantmentIds = parseRangeListInt(props.getProperty("enchantmentIDs"), (IParserInt)new ParserEnchantmentId());
/*  112 */     this.enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
/*  113 */     this.nbtTagValues = parseNbtTagValues(props);
/*  114 */     this.hand = parseHand(props.getProperty("hand"));
/*  115 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*  116 */     this.speed = parseFloat(props.getProperty("speed"), 0.0F);
/*  117 */     this.rotation = parseFloat(props.getProperty("rotation"), 0.0F);
/*  118 */     this.layer = parseInt(props.getProperty("layer"), 0);
/*  119 */     this.weight = parseInt(props.getProperty("weight"), 0);
/*  120 */     this.duration = parseFloat(props.getProperty("duration"), 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseName(String path) {
/*  125 */     String s = path;
/*  126 */     int i = path.lastIndexOf('/');
/*      */     
/*  128 */     if (i >= 0)
/*      */     {
/*  130 */       s = path.substring(i + 1);
/*      */     }
/*      */     
/*  133 */     int j = s.lastIndexOf('.');
/*      */     
/*  135 */     if (j >= 0)
/*      */     {
/*  137 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*  140 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String path) {
/*  145 */     int i = path.lastIndexOf('/');
/*  146 */     return (i < 0) ? "" : path.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseType(String str) {
/*  151 */     if (str == null)
/*      */     {
/*  153 */       return 1;
/*      */     }
/*  155 */     if (str.equals("item"))
/*      */     {
/*  157 */       return 1;
/*      */     }
/*  159 */     if (str.equals("enchantment"))
/*      */     {
/*  161 */       return 2;
/*      */     }
/*  163 */     if (str.equals("armor"))
/*      */     {
/*  165 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  169 */     Config.warn("Unknown method: " + str);
/*  170 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] parseItems(String str, String str2) {
/*  176 */     if (str == null)
/*      */     {
/*  178 */       str = str2;
/*      */     }
/*      */     
/*  181 */     if (str == null)
/*      */     {
/*  183 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  187 */     str = str.trim();
/*  188 */     Set<Integer> set = new TreeSet();
/*  189 */     String[] astring = Config.tokenize(str, " ");
/*      */ 
/*      */     
/*  192 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  194 */       String s = astring[i];
/*  195 */       int j = Config.parseInt(s, -1);
/*      */       
/*  197 */       if (j >= 0) {
/*      */         
/*  199 */         set.add(new Integer(j));
/*      */         
/*      */         continue;
/*      */       } 
/*  203 */       if (s.contains("-")) {
/*      */         
/*  205 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  207 */         if (astring1.length == 2) {
/*      */           
/*  209 */           int k = Config.parseInt(astring1[0], -1);
/*  210 */           int l = Config.parseInt(astring1[1], -1);
/*      */           
/*  212 */           if (k >= 0 && l >= 0) {
/*      */             
/*  214 */             int i1 = Math.min(k, l);
/*  215 */             int j1 = Math.max(k, l);
/*  216 */             int k1 = i1;
/*      */ 
/*      */ 
/*      */             
/*  220 */             while (k1 <= j1) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  225 */               set.add(new Integer(k1));
/*  226 */               k1++;
/*      */             } 
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  232 */       Item item = Item.getByNameOrId(s);
/*      */       
/*  234 */       if (item == null) {
/*      */         
/*  236 */         Config.warn("Item not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  240 */         int i2 = Item.getIdFromItem(item);
/*      */         
/*  242 */         if (i2 <= 0) {
/*      */           
/*  244 */           Config.warn("Item not found: " + s);
/*      */         }
/*      */         else {
/*      */           
/*  248 */           set.add(new Integer(i2));
/*      */         } 
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*  254 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/*  255 */     int[] aint = new int[ainteger.length];
/*      */     
/*  257 */     for (int l1 = 0; l1 < aint.length; l1++)
/*      */     {
/*  259 */       aint[l1] = ainteger[l1].intValue();
/*      */     }
/*      */     
/*  262 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseTexture(String texStr, String texStr2, String texStr3, String path, String basePath, int type, Map<String, String> mapTexs, boolean textureFromPath) {
/*  268 */     if (texStr == null)
/*      */     {
/*  270 */       texStr = texStr2;
/*      */     }
/*      */     
/*  273 */     if (texStr == null)
/*      */     {
/*  275 */       texStr = texStr3;
/*      */     }
/*      */     
/*  278 */     if (texStr != null) {
/*      */       
/*  280 */       String s2 = ".png";
/*      */       
/*  282 */       if (texStr.endsWith(s2))
/*      */       {
/*  284 */         texStr = texStr.substring(0, texStr.length() - s2.length());
/*      */       }
/*      */       
/*  287 */       texStr = fixTextureName(texStr, basePath);
/*  288 */       return texStr;
/*      */     } 
/*  290 */     if (type == 3)
/*      */     {
/*  292 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  296 */     if (mapTexs != null) {
/*      */       
/*  298 */       String s = mapTexs.get("texture.bow_standby");
/*      */       
/*  300 */       if (s != null)
/*      */       {
/*  302 */         return s;
/*      */       }
/*      */     } 
/*      */     
/*  306 */     if (!textureFromPath)
/*      */     {
/*  308 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  312 */     String s1 = path;
/*  313 */     int i = path.lastIndexOf('/');
/*      */     
/*  315 */     if (i >= 0)
/*      */     {
/*  317 */       s1 = path.substring(i + 1);
/*      */     }
/*      */     
/*  320 */     int j = s1.lastIndexOf('.');
/*      */     
/*  322 */     if (j >= 0)
/*      */     {
/*  324 */       s1 = s1.substring(0, j);
/*      */     }
/*      */     
/*  327 */     s1 = fixTextureName(s1, basePath);
/*  328 */     return s1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map parseTextures(Properties props, String basePath) {
/*  335 */     String s = "texture.";
/*  336 */     Map map = getMatchingProperties(props, s);
/*      */     
/*  338 */     if (map.size() <= 0)
/*      */     {
/*  340 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  344 */     Set set = map.keySet();
/*  345 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*      */     
/*  347 */     for (Object e : set) {
/*      */       
/*  349 */       String s1 = (String)e;
/*  350 */       String s2 = (String)map.get(s1);
/*  351 */       s2 = fixTextureName(s2, basePath);
/*  352 */       map1.put(s1, s2);
/*      */     } 
/*      */     
/*  355 */     return map1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixTextureName(String iconName, String basePath) {
/*  361 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*      */     
/*  363 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/"))
/*      */     {
/*  365 */       iconName = basePath + "/" + iconName;
/*      */     }
/*      */     
/*  368 */     if (iconName.endsWith(".png"))
/*      */     {
/*  370 */       iconName = iconName.substring(0, iconName.length() - 4);
/*      */     }
/*      */     
/*  373 */     if (iconName.startsWith("/"))
/*      */     {
/*  375 */       iconName = iconName.substring(1);
/*      */     }
/*      */     
/*  378 */     return iconName;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseModel(String modelStr, String path, String basePath, int type, Map<String, String> mapModelNames) {
/*  383 */     if (modelStr != null) {
/*      */       
/*  385 */       String s1 = ".json";
/*      */       
/*  387 */       if (modelStr.endsWith(s1))
/*      */       {
/*  389 */         modelStr = modelStr.substring(0, modelStr.length() - s1.length());
/*      */       }
/*      */       
/*  392 */       modelStr = fixModelName(modelStr, basePath);
/*  393 */       return modelStr;
/*      */     } 
/*  395 */     if (type == 3)
/*      */     {
/*  397 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  401 */     if (mapModelNames != null) {
/*      */       
/*  403 */       String s = mapModelNames.get("model.bow_standby");
/*      */       
/*  405 */       if (s != null)
/*      */       {
/*  407 */         return s;
/*      */       }
/*      */     } 
/*      */     
/*  411 */     return modelStr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map parseModels(Properties props, String basePath) {
/*  417 */     String s = "model.";
/*  418 */     Map map = getMatchingProperties(props, s);
/*      */     
/*  420 */     if (map.size() <= 0)
/*      */     {
/*  422 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  426 */     Set set = map.keySet();
/*  427 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*      */     
/*  429 */     for (Object e : set) {
/*      */       
/*  431 */       String s1 = (String)e;
/*  432 */       String s2 = (String)map.get(s1);
/*  433 */       s2 = fixModelName(s2, basePath);
/*  434 */       map1.put(s1, s2);
/*      */     } 
/*      */     
/*  437 */     return map1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixModelName(String modelName, String basePath) {
/*  443 */     modelName = TextureUtils.fixResourcePath(modelName, basePath);
/*  444 */     boolean flag = (modelName.startsWith("block/") || modelName.startsWith("item/"));
/*      */     
/*  446 */     if (!modelName.startsWith(basePath) && !flag && !modelName.startsWith("mcpatcher/"))
/*      */     {
/*  448 */       modelName = basePath + "/" + modelName;
/*      */     }
/*      */     
/*  451 */     String s = ".json";
/*      */     
/*  453 */     if (modelName.endsWith(s))
/*      */     {
/*  455 */       modelName = modelName.substring(0, modelName.length() - s.length());
/*      */     }
/*      */     
/*  458 */     if (modelName.startsWith("/"))
/*      */     {
/*  460 */       modelName = modelName.substring(1);
/*      */     }
/*      */     
/*  463 */     return modelName;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseInt(String str, int defVal) {
/*  468 */     if (str == null)
/*      */     {
/*  470 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  474 */     str = str.trim();
/*  475 */     int i = Config.parseInt(str, -2147483648);
/*      */     
/*  477 */     if (i == Integer.MIN_VALUE) {
/*      */       
/*  479 */       Config.warn("Invalid integer: " + str);
/*  480 */       return defVal;
/*      */     } 
/*      */ 
/*      */     
/*  484 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float parseFloat(String str, float defVal) {
/*  491 */     if (str == null)
/*      */     {
/*  493 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  497 */     str = str.trim();
/*  498 */     float f = Config.parseFloat(str, Float.MIN_VALUE);
/*      */     
/*  500 */     if (f == Float.MIN_VALUE) {
/*      */       
/*  502 */       Config.warn("Invalid float: " + str);
/*  503 */       return defVal;
/*      */     } 
/*      */ 
/*      */     
/*  507 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeListInt parseRangeListInt(String str) {
/*  514 */     return parseRangeListInt(str, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private RangeListInt parseRangeListInt(String str, IParserInt parser) {
/*  519 */     if (str == null)
/*      */     {
/*  521 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  525 */     String[] astring = Config.tokenize(str, " ");
/*  526 */     RangeListInt rangelistint = new RangeListInt();
/*      */     
/*  528 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  530 */       String s = astring[i];
/*      */       
/*  532 */       if (parser != null) {
/*      */         
/*  534 */         int j = parser.parse(s, -2147483648);
/*      */         
/*  536 */         if (j != Integer.MIN_VALUE) {
/*      */           
/*  538 */           rangelistint.addRange(new RangeInt(j, j));
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*  543 */       RangeInt rangeint = parseRangeInt(s);
/*      */       
/*  545 */       if (rangeint == null) {
/*      */         
/*  547 */         Config.warn("Invalid range list: " + str);
/*  548 */         return null;
/*      */       } 
/*      */       
/*  551 */       rangelistint.addRange(rangeint);
/*      */       continue;
/*      */     } 
/*  554 */     return rangelistint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeInt parseRangeInt(String str) {
/*  560 */     if (str == null)
/*      */     {
/*  562 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  566 */     str = str.trim();
/*  567 */     int i = str.length() - str.replace("-", "").length();
/*      */     
/*  569 */     if (i > 1) {
/*      */       
/*  571 */       Config.warn("Invalid range: " + str);
/*  572 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  576 */     String[] astring = Config.tokenize(str, "- ");
/*  577 */     int[] aint = new int[astring.length];
/*      */     
/*  579 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  581 */       String s = astring[j];
/*  582 */       int k = Config.parseInt(s, -1);
/*      */       
/*  584 */       if (k < 0) {
/*      */         
/*  586 */         Config.warn("Invalid range: " + str);
/*  587 */         return null;
/*      */       } 
/*      */       
/*  590 */       aint[j] = k;
/*      */     } 
/*      */     
/*  593 */     if (aint.length == 1) {
/*      */       
/*  595 */       int i1 = aint[0];
/*      */       
/*  597 */       if (str.startsWith("-"))
/*      */       {
/*  599 */         return new RangeInt(0, i1);
/*      */       }
/*  601 */       if (str.endsWith("-"))
/*      */       {
/*  603 */         return new RangeInt(i1, 65535);
/*      */       }
/*      */ 
/*      */       
/*  607 */       return new RangeInt(i1, i1);
/*      */     } 
/*      */     
/*  610 */     if (aint.length == 2) {
/*      */       
/*  612 */       int l = Math.min(aint[0], aint[1]);
/*  613 */       int j1 = Math.max(aint[0], aint[1]);
/*  614 */       return new RangeInt(l, j1);
/*      */     } 
/*      */ 
/*      */     
/*  618 */     Config.warn("Invalid range: " + str);
/*  619 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NbtTagValue[] parseNbtTagValues(Properties props) {
/*  627 */     String s = "nbt.";
/*  628 */     Map map = getMatchingProperties(props, s);
/*      */     
/*  630 */     if (map.size() <= 0)
/*      */     {
/*  632 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  636 */     List<NbtTagValue> list = new ArrayList();
/*      */     
/*  638 */     for (Object e : map.keySet()) {
/*      */       
/*  640 */       String s1 = (String)e;
/*  641 */       String s2 = (String)map.get(s1);
/*  642 */       String s3 = s1.substring(s.length());
/*  643 */       NbtTagValue nbttagvalue = new NbtTagValue(s3, s2);
/*  644 */       list.add(nbttagvalue);
/*      */     } 
/*      */     
/*  647 */     NbtTagValue[] anbttagvalue = list.<NbtTagValue>toArray(new NbtTagValue[list.size()]);
/*  648 */     return anbttagvalue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map getMatchingProperties(Properties props, String keyPrefix) {
/*  654 */     Map<Object, Object> map = new LinkedHashMap<>();
/*      */     
/*  656 */     for (Object e : props.keySet()) {
/*      */       
/*  658 */       String s = (String)e;
/*  659 */       String s1 = props.getProperty(s);
/*      */       
/*  661 */       if (s.startsWith(keyPrefix))
/*      */       {
/*  663 */         map.put(s, s1);
/*      */       }
/*      */     } 
/*      */     
/*  667 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseHand(String str) {
/*  672 */     if (str == null)
/*      */     {
/*  674 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  678 */     str = str.toLowerCase();
/*      */     
/*  680 */     if (str.equals("any"))
/*      */     {
/*  682 */       return 0;
/*      */     }
/*  684 */     if (str.equals("main"))
/*      */     {
/*  686 */       return 1;
/*      */     }
/*  688 */     if (str.equals("off"))
/*      */     {
/*  690 */       return 2;
/*      */     }
/*      */ 
/*      */     
/*  694 */     Config.warn("Invalid hand: " + str);
/*  695 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String path) {
/*  702 */     if (this.name != null && this.name.length() > 0) {
/*      */       
/*  704 */       if (this.basePath == null) {
/*      */         
/*  706 */         Config.warn("No base path found: " + path);
/*  707 */         return false;
/*      */       } 
/*  709 */       if (this.type == 0) {
/*      */         
/*  711 */         Config.warn("No type defined: " + path);
/*  712 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  716 */       if (this.type == 1 || this.type == 3) {
/*      */         
/*  718 */         if (this.items == null)
/*      */         {
/*  720 */           this.items = detectItems();
/*      */         }
/*      */         
/*  723 */         if (this.items == null) {
/*      */           
/*  725 */           Config.warn("No items defined: " + path);
/*  726 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/*  730 */       if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
/*      */         
/*  732 */         Config.warn("No texture or model specified: " + path);
/*  733 */         return false;
/*      */       } 
/*  735 */       if (this.type == 2 && this.enchantmentIds == null) {
/*      */         
/*  737 */         Config.warn("No enchantmentIDs specified: " + path);
/*  738 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  742 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  748 */     Config.warn("No name found: " + path);
/*  749 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectItems() {
/*  755 */     Item item = Item.getByNameOrId(this.name);
/*      */     
/*  757 */     if (item == null)
/*      */     {
/*  759 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  763 */     int i = Item.getIdFromItem(item);
/*  764 */     (new int[1])[0] = i; return (i <= 0) ? null : new int[1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(TextureMap textureMap) {
/*  770 */     if (this.texture != null) {
/*      */       
/*  772 */       this.textureLocation = getTextureLocation(this.texture);
/*      */       
/*  774 */       if (this.type == 1) {
/*      */         
/*  776 */         ResourceLocation resourcelocation = getSpriteLocation(this.textureLocation);
/*  777 */         this.sprite = textureMap.registerSprite(resourcelocation);
/*      */       } 
/*      */     } 
/*      */     
/*  781 */     if (this.mapTextures != null) {
/*      */       
/*  783 */       this.mapTextureLocations = new HashMap<>();
/*  784 */       this.mapSprites = new HashMap<>();
/*      */       
/*  786 */       for (String s : this.mapTextures.keySet()) {
/*      */         
/*  788 */         String s1 = this.mapTextures.get(s);
/*  789 */         ResourceLocation resourcelocation1 = getTextureLocation(s1);
/*  790 */         this.mapTextureLocations.put(s, resourcelocation1);
/*      */         
/*  792 */         if (this.type == 1) {
/*      */           
/*  794 */           ResourceLocation resourcelocation2 = getSpriteLocation(resourcelocation1);
/*  795 */           TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation2);
/*  796 */           this.mapSprites.put(s, textureatlassprite);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getTextureLocation(String texName) {
/*  804 */     if (texName == null)
/*      */     {
/*  806 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  810 */     ResourceLocation resourcelocation = new ResourceLocation(texName);
/*  811 */     String s = resourcelocation.getResourceDomain();
/*  812 */     String s1 = resourcelocation.getResourcePath();
/*      */     
/*  814 */     if (!s1.contains("/"))
/*      */     {
/*  816 */       s1 = "textures/items/" + s1;
/*      */     }
/*      */     
/*  819 */     String s2 = s1 + ".png";
/*  820 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s2);
/*  821 */     boolean flag = Config.hasResource(resourcelocation1);
/*      */     
/*  823 */     if (!flag)
/*      */     {
/*  825 */       Config.warn("File not found: " + s2);
/*      */     }
/*      */     
/*  828 */     return resourcelocation1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceLocation getSpriteLocation(ResourceLocation resLoc) {
/*  834 */     String s = resLoc.getResourcePath();
/*  835 */     s = StrUtils.removePrefix(s, "textures/");
/*  836 */     s = StrUtils.removeSuffix(s, ".png");
/*  837 */     ResourceLocation resourcelocation = new ResourceLocation(resLoc.getResourceDomain(), s);
/*  838 */     return resourcelocation;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelTexture(TextureMap textureMap, ItemModelGenerator itemModelGenerator) {
/*  843 */     if (this.texture != null || this.mapTextures != null) {
/*      */       
/*  845 */       String[] astring = getModelTextures();
/*  846 */       boolean flag = isUseTint();
/*  847 */       this.bakedModelTexture = makeBakedModel(textureMap, itemModelGenerator, astring, flag);
/*      */       
/*  849 */       if (this.type == 1 && this.mapTextures != null)
/*      */       {
/*  851 */         for (String s : this.mapTextures.keySet()) {
/*      */           
/*  853 */           String s1 = this.mapTextures.get(s);
/*  854 */           String s2 = StrUtils.removePrefix(s, "texture.");
/*      */           
/*  856 */           if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/*      */             
/*  858 */             String[] astring1 = { s1 };
/*  859 */             IBakedModel ibakedmodel = makeBakedModel(textureMap, itemModelGenerator, astring1, flag);
/*      */             
/*  861 */             if (this.mapBakedModelsTexture == null)
/*      */             {
/*  863 */               this.mapBakedModelsTexture = new HashMap<>();
/*      */             }
/*      */             
/*  866 */             String s3 = "item/" + s2;
/*  867 */             this.mapBakedModelsTexture.put(s3, ibakedmodel);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isUseTint() {
/*  876 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IBakedModel makeBakedModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator, String[] textures, boolean useTint) {
/*  881 */     String[] astring = new String[textures.length];
/*      */     
/*  883 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  885 */       String s = textures[i];
/*  886 */       astring[i] = StrUtils.removePrefix(s, "textures/");
/*      */     } 
/*      */     
/*  889 */     ModelBlock modelblock = makeModelBlock(astring);
/*  890 */     ModelBlock modelblock1 = itemModelGenerator.makeItemModel(textureMap, modelblock);
/*  891 */     IBakedModel ibakedmodel = bakeModel(textureMap, modelblock1, useTint);
/*  892 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] getModelTextures() {
/*  897 */     if (this.type == 1 && this.items.length == 1) {
/*      */       
/*  899 */       Item item = Item.getItemById(this.items[0]);
/*      */       
/*  901 */       if (item == Items.potionitem && this.damage != null && this.damage.getCountRanges() > 0) {
/*      */         
/*  903 */         RangeInt rangeint = this.damage.getRange(0);
/*  904 */         int i = rangeint.getMin();
/*  905 */         boolean flag = ((i & 0x4000) != 0);
/*  906 */         String s5 = getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
/*  907 */         String s6 = null;
/*      */         
/*  909 */         if (flag) {
/*      */           
/*  911 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
/*      */         }
/*      */         else {
/*      */           
/*  915 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
/*      */         } 
/*      */         
/*  918 */         return new String[] { s5, s6 };
/*      */       } 
/*      */       
/*  921 */       if (item instanceof ItemArmor) {
/*      */         
/*  923 */         ItemArmor itemarmor = (ItemArmor)item;
/*      */         
/*  925 */         if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
/*      */           
/*  927 */           String s = "leather";
/*  928 */           String s1 = "helmet";
/*      */           
/*  930 */           if (itemarmor.armorType == 0)
/*      */           {
/*  932 */             s1 = "helmet";
/*      */           }
/*      */           
/*  935 */           if (itemarmor.armorType == 1)
/*      */           {
/*  937 */             s1 = "chestplate";
/*      */           }
/*      */           
/*  940 */           if (itemarmor.armorType == 2)
/*      */           {
/*  942 */             s1 = "leggings";
/*      */           }
/*      */           
/*  945 */           if (itemarmor.armorType == 3)
/*      */           {
/*  947 */             s1 = "boots";
/*      */           }
/*      */           
/*  950 */           String s2 = s + "_" + s1;
/*  951 */           String s3 = getMapTexture(this.mapTextures, "texture." + s2, "items/" + s2);
/*  952 */           String s4 = getMapTexture(this.mapTextures, "texture." + s2 + "_overlay", "items/" + s2 + "_overlay");
/*  953 */           return new String[] { s3, s4 };
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  958 */     return new String[] { this.texture };
/*      */   }
/*      */ 
/*      */   
/*      */   private String getMapTexture(Map<String, String> map, String key, String def) {
/*  963 */     if (map == null)
/*      */     {
/*  965 */       return def;
/*      */     }
/*      */ 
/*      */     
/*  969 */     String s = map.get(key);
/*  970 */     return (s == null) ? def : s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ModelBlock makeModelBlock(String[] modelTextures) {
/*  976 */     StringBuffer stringbuffer = new StringBuffer();
/*  977 */     stringbuffer.append("{\"parent\": \"builtin/generated\",\"textures\": {");
/*      */     
/*  979 */     for (int i = 0; i < modelTextures.length; i++) {
/*      */       
/*  981 */       String s = modelTextures[i];
/*      */       
/*  983 */       if (i > 0)
/*      */       {
/*  985 */         stringbuffer.append(", ");
/*      */       }
/*      */       
/*  988 */       stringbuffer.append("\"layer" + i + "\": \"" + s + "\"");
/*      */     } 
/*      */     
/*  991 */     stringbuffer.append("}}");
/*  992 */     String s1 = stringbuffer.toString();
/*  993 */     ModelBlock modelblock = ModelBlock.deserialize(s1);
/*  994 */     return modelblock;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IBakedModel bakeModel(TextureMap textureMap, ModelBlock modelBlockIn, boolean useTint) {
/*  999 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/* 1000 */     boolean flag = false;
/* 1001 */     String s = modelBlockIn.resolveTextureName("particle");
/* 1002 */     TextureAtlasSprite textureatlassprite = textureMap.getAtlasSprite((new ResourceLocation(s)).toString());
/* 1003 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(modelBlockIn)).setTexture(textureatlassprite);
/*      */     
/* 1005 */     for (BlockPart blockpart : modelBlockIn.getElements()) {
/*      */       
/* 1007 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/*      */         
/* 1009 */         BlockPartFace blockpartface = (BlockPartFace)blockpart.mapFaces.get(enumfacing);
/*      */         
/* 1011 */         if (!useTint)
/*      */         {
/* 1013 */           blockpartface = new BlockPartFace(blockpartface.cullFace, -1, blockpartface.texture, blockpartface.blockFaceUV);
/*      */         }
/*      */         
/* 1016 */         String s1 = modelBlockIn.resolveTextureName(blockpartface.texture);
/* 1017 */         TextureAtlasSprite textureatlassprite1 = textureMap.getAtlasSprite((new ResourceLocation(s1)).toString());
/* 1018 */         BakedQuad bakedquad = makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelrotation, flag);
/*      */         
/* 1020 */         if (blockpartface.cullFace == null) {
/*      */           
/* 1022 */           simplebakedmodel$builder.addGeneralQuad(bakedquad);
/*      */           
/*      */           continue;
/*      */         } 
/* 1026 */         simplebakedmodel$builder.addFaceQuad(modelrotation.rotateFace(blockpartface.cullFace), bakedquad);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1031 */     return simplebakedmodel$builder.makeBakedModel();
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad makeBakedQuad(BlockPart blockPart, BlockPartFace blockPartFace, TextureAtlasSprite textureAtlasSprite, EnumFacing enumFacing, ModelRotation modelRotation, boolean uvLocked) {
/* 1036 */     FaceBakery facebakery = new FaceBakery();
/* 1037 */     return facebakery.makeBakedQuad(blockPart.positionFrom, blockPart.positionTo, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.partRotation, uvLocked, blockPart.shade);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1042 */     return "" + this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.arrayToString(this.items) + "], textture: " + this.texture;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getTextureWidth(TextureManager textureManager) {
/* 1047 */     if (this.textureWidth <= 0) {
/*      */       
/* 1049 */       if (this.textureLocation != null) {
/*      */         
/* 1051 */         ITextureObject itextureobject = textureManager.getTexture(this.textureLocation);
/* 1052 */         int i = itextureobject.getGlTextureId();
/* 1053 */         int j = GlStateManager.getBoundTexture();
/* 1054 */         GlStateManager.bindTexture(i);
/* 1055 */         this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
/* 1056 */         GlStateManager.bindTexture(j);
/*      */       } 
/*      */       
/* 1059 */       if (this.textureWidth <= 0)
/*      */       {
/* 1061 */         this.textureWidth = 16;
/*      */       }
/*      */     } 
/*      */     
/* 1065 */     return this.textureWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getTextureHeight(TextureManager textureManager) {
/* 1070 */     if (this.textureHeight <= 0) {
/*      */       
/* 1072 */       if (this.textureLocation != null) {
/*      */         
/* 1074 */         ITextureObject itextureobject = textureManager.getTexture(this.textureLocation);
/* 1075 */         int i = itextureobject.getGlTextureId();
/* 1076 */         int j = GlStateManager.getBoundTexture();
/* 1077 */         GlStateManager.bindTexture(i);
/* 1078 */         this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
/* 1079 */         GlStateManager.bindTexture(j);
/*      */       } 
/*      */       
/* 1082 */       if (this.textureHeight <= 0)
/*      */       {
/* 1084 */         this.textureHeight = 16;
/*      */       }
/*      */     } 
/*      */     
/* 1088 */     return this.textureHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBakedModel getBakedModel(ResourceLocation modelLocation, boolean fullModel) {
/*      */     IBakedModel ibakedmodel;
/*      */     Map<String, IBakedModel> map;
/* 1096 */     if (fullModel) {
/*      */       
/* 1098 */       ibakedmodel = this.bakedModelFull;
/* 1099 */       map = this.mapBakedModelsFull;
/*      */     }
/*      */     else {
/*      */       
/* 1103 */       ibakedmodel = this.bakedModelTexture;
/* 1104 */       map = this.mapBakedModelsTexture;
/*      */     } 
/*      */     
/* 1107 */     if (modelLocation != null && map != null) {
/*      */       
/* 1109 */       String s = modelLocation.getResourcePath();
/* 1110 */       IBakedModel ibakedmodel1 = map.get(s);
/*      */       
/* 1112 */       if (ibakedmodel1 != null)
/*      */       {
/* 1114 */         return ibakedmodel1;
/*      */       }
/*      */     } 
/*      */     
/* 1118 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadModels(ModelBakery modelBakery) {
/* 1123 */     if (this.model != null)
/*      */     {
/* 1125 */       loadItemModel(modelBakery, this.model);
/*      */     }
/*      */     
/* 1128 */     if (this.type == 1 && this.mapModels != null)
/*      */     {
/* 1130 */       for (String s : this.mapModels.keySet()) {
/*      */         
/* 1132 */         String s1 = this.mapModels.get(s);
/* 1133 */         String s2 = StrUtils.removePrefix(s, "model.");
/*      */         
/* 1135 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield"))
/*      */         {
/* 1137 */           loadItemModel(modelBakery, s1);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelsFull() {
/* 1145 */     ModelManager modelmanager = Config.getModelManager();
/* 1146 */     IBakedModel ibakedmodel = modelmanager.getMissingModel();
/*      */     
/* 1148 */     if (this.model != null) {
/*      */       
/* 1150 */       ResourceLocation resourcelocation = getModelLocation(this.model);
/* 1151 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/* 1152 */       this.bakedModelFull = modelmanager.getModel(modelresourcelocation);
/*      */       
/* 1154 */       if (this.bakedModelFull == ibakedmodel) {
/*      */         
/* 1156 */         Config.warn("Custom Items: Model not found " + modelresourcelocation.getResourcePath());
/* 1157 */         this.bakedModelFull = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1161 */     if (this.type == 1 && this.mapModels != null)
/*      */     {
/* 1163 */       for (String s : this.mapModels.keySet()) {
/*      */         
/* 1165 */         String s1 = this.mapModels.get(s);
/* 1166 */         String s2 = StrUtils.removePrefix(s, "model.");
/*      */         
/* 1168 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/*      */           
/* 1170 */           ResourceLocation resourcelocation1 = getModelLocation(s1);
/* 1171 */           ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(resourcelocation1, "inventory");
/* 1172 */           IBakedModel ibakedmodel1 = modelmanager.getModel(modelresourcelocation1);
/*      */           
/* 1174 */           if (ibakedmodel1 == ibakedmodel) {
/*      */             
/* 1176 */             Config.warn("Custom Items: Model not found " + modelresourcelocation1.getResourcePath());
/*      */             
/*      */             continue;
/*      */           } 
/* 1180 */           if (this.mapBakedModelsFull == null)
/*      */           {
/* 1182 */             this.mapBakedModelsFull = new HashMap<>();
/*      */           }
/*      */           
/* 1185 */           String s3 = "item/" + s2;
/* 1186 */           this.mapBakedModelsFull.put(s3, ibakedmodel1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadItemModel(ModelBakery modelBakery, String model) {
/* 1195 */     ResourceLocation resourcelocation = getModelLocation(model);
/* 1196 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/*      */     
/* 1198 */     if (Reflector.ModelLoader.exists()) {
/*      */       
/*      */       try
/*      */       {
/* 1202 */         Object object = Reflector.ModelLoader_VanillaLoader_INSTANCE.getValue();
/* 1203 */         checkNull(object, "vanillaLoader is null");
/* 1204 */         Object object1 = Reflector.call(object, Reflector.ModelLoader_VanillaLoader_loadModel, new Object[] { modelresourcelocation });
/* 1205 */         checkNull(object1, "iModel is null");
/* 1206 */         Map<ModelResourceLocation, Object> map = (Map)Reflector.getFieldValue(modelBakery, Reflector.ModelLoader_stateModels);
/* 1207 */         checkNull(map, "stateModels is null");
/* 1208 */         map.put(modelresourcelocation, object1);
/* 1209 */         Set set = (Set)Reflector.getFieldValue(modelBakery, Reflector.ModelLoader_textures);
/* 1210 */         checkNull(set, "registryTextures is null");
/* 1211 */         Collection collection = (Collection)Reflector.call(object1, Reflector.IModel_getTextures, new Object[0]);
/* 1212 */         checkNull(collection, "modelTextures is null");
/* 1213 */         set.addAll(collection);
/*      */       }
/* 1215 */       catch (Exception exception)
/*      */       {
/* 1217 */         Config.warn("Error registering model with ModelLoader: " + modelresourcelocation + ", " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1222 */       modelBakery.loadItemModel(resourcelocation.toString(), (ResourceLocation)modelresourcelocation, resourcelocation);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkNull(Object obj, String msg) throws NullPointerException {
/* 1228 */     if (obj == null)
/*      */     {
/* 1230 */       throw new NullPointerException(msg);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static ResourceLocation getModelLocation(String modelName) {
/* 1236 */     return (Reflector.ModelLoader.exists() && !modelName.startsWith("mcpatcher/") && !modelName.startsWith("optifine/")) ? new ResourceLocation("models/" + modelName) : new ResourceLocation(modelName);
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomItemProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */