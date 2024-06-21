/*      */ package net.optifine;
/*      */ 
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelBakery;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.config.NbtTagValue;
/*      */ import net.optifine.render.Blender;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.StrUtils;
/*      */ 
/*      */ public class CustomItems {
/*   46 */   private static CustomItemProperties[][] itemProperties = (CustomItemProperties[][])null;
/*   47 */   private static CustomItemProperties[][] enchantmentProperties = (CustomItemProperties[][])null;
/*   48 */   private static Map mapPotionIds = null;
/*   49 */   private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*      */   private static boolean useGlint = true;
/*      */   private static boolean renderOffHand = false;
/*      */   public static final int MASK_POTION_SPLASH = 16384;
/*      */   public static final int MASK_POTION_NAME = 63;
/*      */   public static final int MASK_POTION_EXTENDED = 64;
/*      */   public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
/*      */   public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
/*      */   public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
/*      */   public static final String DEFAULT_TEXTURE_OVERLAY = "items/potion_overlay";
/*      */   public static final String DEFAULT_TEXTURE_SPLASH = "items/potion_bottle_splash";
/*      */   public static final String DEFAULT_TEXTURE_DRINKABLE = "items/potion_bottle_drinkable";
/*   61 */   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
/*      */   
/*      */   private static final String TYPE_POTION_NORMAL = "normal";
/*      */   private static final String TYPE_POTION_SPLASH = "splash";
/*      */   private static final String TYPE_POTION_LINGER = "linger";
/*      */   
/*      */   public static void update() {
/*   68 */     itemProperties = (CustomItemProperties[][])null;
/*   69 */     enchantmentProperties = (CustomItemProperties[][])null;
/*   70 */     useGlint = true;
/*      */     
/*   72 */     if (Config.isCustomItems()) {
/*      */       
/*   74 */       readCitProperties("mcpatcher/cit.properties");
/*   75 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/*   77 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*      */         
/*   79 */         IResourcePack iresourcepack = airesourcepack[i];
/*   80 */         update(iresourcepack);
/*      */       } 
/*      */       
/*   83 */       update((IResourcePack)Config.getDefaultResourcePack());
/*      */       
/*   85 */       if (itemProperties.length <= 0)
/*      */       {
/*   87 */         itemProperties = (CustomItemProperties[][])null;
/*      */       }
/*      */       
/*   90 */       if (enchantmentProperties.length <= 0)
/*      */       {
/*   92 */         enchantmentProperties = (CustomItemProperties[][])null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readCitProperties(String fileName) {
/*      */     try {
/*  101 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  102 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  104 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  109 */       Config.dbg("CustomItems: Loading " + fileName);
/*  110 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  111 */       propertiesOrdered.load(inputstream);
/*  112 */       inputstream.close();
/*  113 */       useGlint = Config.parseBoolean(propertiesOrdered.getProperty("useGlint"), true);
/*      */     }
/*  115 */     catch (FileNotFoundException var4) {
/*      */       
/*      */       return;
/*      */     }
/*  119 */     catch (IOException ioexception) {
/*      */       
/*  121 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void update(IResourcePack rp) {
/*  127 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/cit/", ".properties", null);
/*  128 */     Map map = makeAutoImageProperties(rp);
/*      */     
/*  130 */     if (map.size() > 0) {
/*      */       
/*  132 */       Set set = map.keySet();
/*  133 */       String[] astring1 = (String[])set.toArray((Object[])new String[set.size()]);
/*  134 */       astring = (String[])Config.addObjectsToArray((Object[])astring, (Object[])astring1);
/*      */     } 
/*      */     
/*  137 */     Arrays.sort((Object[])astring);
/*  138 */     List list = makePropertyList(itemProperties);
/*  139 */     List list1 = makePropertyList(enchantmentProperties);
/*      */     
/*  141 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  143 */       String s = astring[i];
/*  144 */       Config.dbg("CustomItems: " + s);
/*      */ 
/*      */       
/*      */       try {
/*  148 */         CustomItemProperties customitemproperties = null;
/*      */         
/*  150 */         if (map.containsKey(s))
/*      */         {
/*  152 */           customitemproperties = (CustomItemProperties)map.get(s);
/*      */         }
/*      */         
/*  155 */         if (customitemproperties == null)
/*      */         
/*  157 */         { ResourceLocation resourcelocation = new ResourceLocation(s);
/*  158 */           InputStream inputstream = rp.getInputStream(resourcelocation);
/*      */           
/*  160 */           if (inputstream == null)
/*      */           
/*  162 */           { Config.warn("CustomItems file not found: " + s); }
/*      */           
/*      */           else
/*      */           
/*  166 */           { PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  167 */             propertiesOrdered.load(inputstream);
/*  168 */             customitemproperties = new CustomItemProperties((Properties)propertiesOrdered, s);
/*      */ 
/*      */             
/*  171 */             if (customitemproperties.isValid(s))
/*      */             
/*  173 */             { addToItemList(customitemproperties, list);
/*  174 */               addToEnchantmentList(customitemproperties, list1); }  }  continue; }  if (customitemproperties.isValid(s)) { addToItemList(customitemproperties, list); addToEnchantmentList(customitemproperties, list1); }
/*      */ 
/*      */       
/*  177 */       } catch (FileNotFoundException var11) {
/*      */         
/*  179 */         Config.warn("CustomItems file not found: " + s);
/*      */         continue;
/*  181 */       } catch (Exception exception) {
/*      */         
/*  183 */         exception.printStackTrace();
/*      */         continue;
/*      */       } 
/*      */     } 
/*  187 */     itemProperties = propertyListToArray(list);
/*  188 */     enchantmentProperties = propertyListToArray(list1);
/*  189 */     Comparator<? super CustomItemProperties> comparator = getPropertiesComparator();
/*      */     
/*  191 */     for (int j = 0; j < itemProperties.length; j++) {
/*      */       
/*  193 */       CustomItemProperties[] acustomitemproperties = itemProperties[j];
/*      */       
/*  195 */       if (acustomitemproperties != null)
/*      */       {
/*  197 */         Arrays.sort(acustomitemproperties, comparator);
/*      */       }
/*      */     } 
/*      */     
/*  201 */     for (int k = 0; k < enchantmentProperties.length; k++) {
/*      */       
/*  203 */       CustomItemProperties[] acustomitemproperties1 = enchantmentProperties[k];
/*      */       
/*  205 */       if (acustomitemproperties1 != null)
/*      */       {
/*  207 */         Arrays.sort(acustomitemproperties1, comparator);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Comparator getPropertiesComparator() {
/*  214 */     Comparator comparator = new Comparator()
/*      */       {
/*      */         public int compare(Object o1, Object o2)
/*      */         {
/*  218 */           CustomItemProperties customitemproperties = (CustomItemProperties)o1;
/*  219 */           CustomItemProperties customitemproperties1 = (CustomItemProperties)o2;
/*  220 */           return (customitemproperties.layer != customitemproperties1.layer) ? (customitemproperties.layer - customitemproperties1.layer) : ((customitemproperties.weight != customitemproperties1.weight) ? (customitemproperties1.weight - customitemproperties.weight) : (!customitemproperties.basePath.equals(customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name)));
/*      */         }
/*      */       };
/*  223 */     return comparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap) {
/*  228 */     for (CustomItemProperties customitemproperties : getAllProperties())
/*      */     {
/*  230 */       customitemproperties.updateIcons(textureMap);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadModels(ModelBakery modelBakery) {
/*  236 */     for (CustomItemProperties customitemproperties : getAllProperties())
/*      */     {
/*  238 */       customitemproperties.loadModels(modelBakery);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateModels() {
/*  244 */     for (CustomItemProperties customitemproperties : getAllProperties()) {
/*      */       
/*  246 */       if (customitemproperties.type == 1) {
/*      */         
/*  248 */         TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  249 */         customitemproperties.updateModelTexture(texturemap, itemModelGenerator);
/*  250 */         customitemproperties.updateModelsFull();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static List<CustomItemProperties> getAllProperties() {
/*  257 */     List<CustomItemProperties> list = new ArrayList<>();
/*  258 */     addAll(itemProperties, list);
/*  259 */     addAll(enchantmentProperties, list);
/*  260 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addAll(CustomItemProperties[][] cipsArr, List<CustomItemProperties> list) {
/*  265 */     if (cipsArr != null)
/*      */     {
/*  267 */       for (int i = 0; i < cipsArr.length; i++) {
/*      */         
/*  269 */         CustomItemProperties[] acustomitemproperties = cipsArr[i];
/*      */         
/*  271 */         if (acustomitemproperties != null)
/*      */         {
/*  273 */           for (int j = 0; j < acustomitemproperties.length; j++) {
/*      */             
/*  275 */             CustomItemProperties customitemproperties = acustomitemproperties[j];
/*      */             
/*  277 */             if (customitemproperties != null)
/*      */             {
/*  279 */               list.add(customitemproperties);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map makeAutoImageProperties(IResourcePack rp) {
/*  289 */     Map<Object, Object> map = new HashMap<>();
/*  290 */     map.putAll(makePotionImageProperties(rp, "normal", Item.getIdFromItem((Item)Items.potionitem)));
/*  291 */     map.putAll(makePotionImageProperties(rp, "splash", Item.getIdFromItem((Item)Items.potionitem)));
/*  292 */     map.putAll(makePotionImageProperties(rp, "linger", Item.getIdFromItem((Item)Items.potionitem)));
/*  293 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map makePotionImageProperties(IResourcePack rp, String type, int itemId) {
/*  298 */     Map<Object, Object> map = new HashMap<>();
/*  299 */     String s = type + "/";
/*  300 */     String[] astring = { "mcpatcher/cit/potion/" + s, "mcpatcher/cit/Potion/" + s };
/*  301 */     String[] astring1 = { ".png" };
/*  302 */     String[] astring2 = ResUtils.collectFiles(rp, astring, astring1);
/*      */     
/*  304 */     for (int i = 0; i < astring2.length; i++) {
/*      */       
/*  306 */       String s1 = astring2[i];
/*  307 */       String name = StrUtils.removePrefixSuffix(s1, astring, astring1);
/*  308 */       Properties properties = makePotionProperties(name, type, itemId, s1);
/*      */       
/*  310 */       if (properties != null) {
/*      */         
/*  312 */         String s3 = StrUtils.removeSuffix(s1, astring1) + ".properties";
/*  313 */         CustomItemProperties customitemproperties = new CustomItemProperties(properties, s3);
/*  314 */         map.put(s3, customitemproperties);
/*      */       } 
/*      */     } 
/*      */     
/*  318 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Properties makePotionProperties(String name, String type, int itemId, String path) {
/*  323 */     if (StrUtils.endsWith(name, new String[] { "_n", "_s" }))
/*      */     {
/*  325 */       return null;
/*      */     }
/*  327 */     if (name.equals("empty") && type.equals("normal")) {
/*      */       
/*  329 */       itemId = Item.getIdFromItem(Items.glass_bottle);
/*  330 */       PropertiesOrdered propertiesOrdered1 = new PropertiesOrdered();
/*  331 */       propertiesOrdered1.put("type", "item");
/*  332 */       propertiesOrdered1.put("items", "" + itemId);
/*  333 */       return (Properties)propertiesOrdered1;
/*      */     } 
/*      */ 
/*      */     
/*  337 */     int[] aint = (int[])getMapPotionIds().get(name);
/*      */     
/*  339 */     if (aint == null) {
/*      */       
/*  341 */       Config.warn("Potion not found for image: " + path);
/*  342 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  346 */     StringBuffer stringbuffer = new StringBuffer();
/*      */     
/*  348 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  350 */       int j = aint[i];
/*      */       
/*  352 */       if (type.equals("splash"))
/*      */       {
/*  354 */         j |= 0x4000;
/*      */       }
/*      */       
/*  357 */       if (i > 0)
/*      */       {
/*  359 */         stringbuffer.append(" ");
/*      */       }
/*      */       
/*  362 */       stringbuffer.append(j);
/*      */     } 
/*      */     
/*  365 */     int k = 16447;
/*      */     
/*  367 */     if (name.equals("water") || name.equals("mundane"))
/*      */     {
/*  369 */       k |= 0x40;
/*      */     }
/*      */     
/*  372 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  373 */     propertiesOrdered.put("type", "item");
/*  374 */     propertiesOrdered.put("items", "" + itemId);
/*  375 */     propertiesOrdered.put("damage", "" + stringbuffer.toString());
/*  376 */     propertiesOrdered.put("damageMask", "" + k);
/*      */     
/*  378 */     if (type.equals("splash")) {
/*      */       
/*  380 */       propertiesOrdered.put("texture.potion_bottle_splash", name);
/*      */     }
/*      */     else {
/*      */       
/*  384 */       propertiesOrdered.put("texture.potion_bottle_drinkable", name);
/*      */     } 
/*      */     
/*  387 */     return (Properties)propertiesOrdered;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map getMapPotionIds() {
/*  394 */     if (mapPotionIds == null) {
/*      */       
/*  396 */       mapPotionIds = new LinkedHashMap<>();
/*  397 */       mapPotionIds.put("water", getPotionId(0, 0));
/*  398 */       mapPotionIds.put("awkward", getPotionId(0, 1));
/*  399 */       mapPotionIds.put("thick", getPotionId(0, 2));
/*  400 */       mapPotionIds.put("potent", getPotionId(0, 3));
/*  401 */       mapPotionIds.put("regeneration", getPotionIds(1));
/*  402 */       mapPotionIds.put("movespeed", getPotionIds(2));
/*  403 */       mapPotionIds.put("fireresistance", getPotionIds(3));
/*  404 */       mapPotionIds.put("poison", getPotionIds(4));
/*  405 */       mapPotionIds.put("heal", getPotionIds(5));
/*  406 */       mapPotionIds.put("nightvision", getPotionIds(6));
/*  407 */       mapPotionIds.put("clear", getPotionId(7, 0));
/*  408 */       mapPotionIds.put("bungling", getPotionId(7, 1));
/*  409 */       mapPotionIds.put("charming", getPotionId(7, 2));
/*  410 */       mapPotionIds.put("rank", getPotionId(7, 3));
/*  411 */       mapPotionIds.put("weakness", getPotionIds(8));
/*  412 */       mapPotionIds.put("damageboost", getPotionIds(9));
/*  413 */       mapPotionIds.put("moveslowdown", getPotionIds(10));
/*  414 */       mapPotionIds.put("leaping", getPotionIds(11));
/*  415 */       mapPotionIds.put("harm", getPotionIds(12));
/*  416 */       mapPotionIds.put("waterbreathing", getPotionIds(13));
/*  417 */       mapPotionIds.put("invisibility", getPotionIds(14));
/*  418 */       mapPotionIds.put("thin", getPotionId(15, 0));
/*  419 */       mapPotionIds.put("debonair", getPotionId(15, 1));
/*  420 */       mapPotionIds.put("sparkling", getPotionId(15, 2));
/*  421 */       mapPotionIds.put("stinky", getPotionId(15, 3));
/*  422 */       mapPotionIds.put("mundane", getPotionId(0, 4));
/*  423 */       mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
/*  424 */       mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
/*  425 */       mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
/*  426 */       mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
/*  427 */       mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
/*  428 */       mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
/*  429 */       mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
/*  430 */       mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
/*      */     } 
/*      */     
/*  433 */     return mapPotionIds;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getPotionIds(int baseId) {
/*  438 */     return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getPotionId(int baseId, int subId) {
/*  443 */     return new int[] { baseId + subId * 16 };
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPotionNameDamage(String name) {
/*  448 */     String s = "potion." + name;
/*  449 */     Potion[] apotion = Potion.potionTypes;
/*      */     
/*  451 */     for (int i = 0; i < apotion.length; i++) {
/*      */       
/*  453 */       Potion potion = apotion[i];
/*      */       
/*  455 */       if (potion != null) {
/*      */         
/*  457 */         String s1 = potion.getName();
/*      */         
/*  459 */         if (s.equals(s1))
/*      */         {
/*  461 */           return potion.getId();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  466 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(CustomItemProperties[][] propsArr) {
/*  471 */     List<List> list = new ArrayList();
/*      */     
/*  473 */     if (propsArr != null)
/*      */     {
/*  475 */       for (int i = 0; i < propsArr.length; i++) {
/*      */         
/*  477 */         CustomItemProperties[] acustomitemproperties = propsArr[i];
/*  478 */         List list1 = null;
/*      */         
/*  480 */         if (acustomitemproperties != null)
/*      */         {
/*  482 */           list1 = new ArrayList(Arrays.asList((Object[])acustomitemproperties));
/*      */         }
/*      */         
/*  485 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/*  489 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomItemProperties[][] propertyListToArray(List<List> lists) {
/*  494 */     CustomItemProperties[][] acustomitemproperties = new CustomItemProperties[lists.size()][];
/*      */     
/*  496 */     for (int i = 0; i < lists.size(); i++) {
/*      */       
/*  498 */       List list = lists.get(i);
/*      */       
/*  500 */       if (list != null) {
/*      */         
/*  502 */         CustomItemProperties[] acustomitemproperties1 = (CustomItemProperties[])list.toArray((Object[])new CustomItemProperties[list.size()]);
/*  503 */         Arrays.sort(acustomitemproperties1, new CustomItemsComparator());
/*  504 */         acustomitemproperties[i] = acustomitemproperties1;
/*      */       } 
/*      */     } 
/*      */     
/*  508 */     return acustomitemproperties;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToItemList(CustomItemProperties cp, List itemList) {
/*  513 */     if (cp.items != null)
/*      */     {
/*  515 */       for (int i = 0; i < cp.items.length; i++) {
/*      */         
/*  517 */         int j = cp.items[i];
/*      */         
/*  519 */         if (j <= 0) {
/*      */           
/*  521 */           Config.warn("Invalid item ID: " + j);
/*      */         }
/*      */         else {
/*      */           
/*  525 */           addToList(cp, itemList, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToEnchantmentList(CustomItemProperties cp, List enchantmentList) {
/*  533 */     if (cp.type == 2)
/*      */     {
/*  535 */       if (cp.enchantmentIds != null)
/*      */       {
/*  537 */         for (int i = 0; i < 256; i++) {
/*      */           
/*  539 */           if (cp.enchantmentIds.isInRange(i))
/*      */           {
/*  541 */             addToList(cp, enchantmentList, i);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(CustomItemProperties cp, List<List> lists, int id) {
/*  550 */     while (id >= lists.size())
/*      */     {
/*  552 */       lists.add(null);
/*      */     }
/*      */     
/*  555 */     List<List> list = lists.get(id);
/*      */     
/*  557 */     if (list == null) {
/*      */       
/*  559 */       list = new ArrayList();
/*  560 */       list.set(id, list);
/*      */     } 
/*      */     
/*  563 */     list.add(cp);
/*      */   }
/*      */ 
/*      */   
/*      */   public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel model, ResourceLocation modelLocation, boolean fullModel) {
/*  568 */     if (!fullModel && model.isGui3d())
/*      */     {
/*  570 */       return model;
/*      */     }
/*  572 */     if (itemProperties == null)
/*      */     {
/*  574 */       return model;
/*      */     }
/*      */ 
/*      */     
/*  578 */     CustomItemProperties customitemproperties = getCustomItemProperties(itemStack, 1);
/*      */     
/*  580 */     if (customitemproperties == null)
/*      */     {
/*  582 */       return model;
/*      */     }
/*      */ 
/*      */     
/*  586 */     IBakedModel ibakedmodel = customitemproperties.getBakedModel(modelLocation, fullModel);
/*  587 */     return (ibakedmodel != null) ? ibakedmodel : model;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean bindCustomArmorTexture(ItemStack itemStack, int layer, String overlay) {
/*  594 */     if (itemProperties == null)
/*      */     {
/*  596 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  600 */     ResourceLocation resourcelocation = getCustomArmorLocation(itemStack, layer, overlay);
/*      */     
/*  602 */     if (resourcelocation == null)
/*      */     {
/*  604 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  608 */     Config.getTextureManager().bindTexture(resourcelocation);
/*  609 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, int layer, String overlay) {
/*  616 */     CustomItemProperties customitemproperties = getCustomItemProperties(itemStack, 3);
/*      */     
/*  618 */     if (customitemproperties == null)
/*      */     {
/*  620 */       return null;
/*      */     }
/*  622 */     if (customitemproperties.mapTextureLocations == null)
/*      */     {
/*  624 */       return customitemproperties.textureLocation;
/*      */     }
/*      */ 
/*      */     
/*  628 */     Item item = itemStack.getItem();
/*      */     
/*  630 */     if (!(item instanceof ItemArmor))
/*      */     {
/*  632 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  636 */     ItemArmor itemarmor = (ItemArmor)item;
/*  637 */     String s = itemarmor.getArmorMaterial().getName();
/*  638 */     StringBuffer stringbuffer = new StringBuffer();
/*  639 */     stringbuffer.append("texture.");
/*  640 */     stringbuffer.append(s);
/*  641 */     stringbuffer.append("_layer_");
/*  642 */     stringbuffer.append(layer);
/*      */     
/*  644 */     if (overlay != null) {
/*      */       
/*  646 */       stringbuffer.append("_");
/*  647 */       stringbuffer.append(overlay);
/*      */     } 
/*      */     
/*  650 */     String s1 = stringbuffer.toString();
/*  651 */     ResourceLocation resourcelocation = (ResourceLocation)customitemproperties.mapTextureLocations.get(s1);
/*  652 */     return (resourcelocation == null) ? customitemproperties.textureLocation : resourcelocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int type) {
/*  659 */     if (itemProperties == null)
/*      */     {
/*  661 */       return null;
/*      */     }
/*  663 */     if (itemStack == null)
/*      */     {
/*  665 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  669 */     Item item = itemStack.getItem();
/*  670 */     int i = Item.getIdFromItem(item);
/*      */     
/*  672 */     if (i >= 0 && i < itemProperties.length) {
/*      */       
/*  674 */       CustomItemProperties[] acustomitemproperties = itemProperties[i];
/*      */       
/*  676 */       if (acustomitemproperties != null)
/*      */       {
/*  678 */         for (int j = 0; j < acustomitemproperties.length; j++) {
/*      */           
/*  680 */           CustomItemProperties customitemproperties = acustomitemproperties[j];
/*      */           
/*  682 */           if (customitemproperties.type == type && matchesProperties(customitemproperties, itemStack, (int[][])null))
/*      */           {
/*  684 */             return customitemproperties;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  690 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
/*  696 */     Item item = itemStack.getItem();
/*      */     
/*  698 */     if (cip.damage != null) {
/*      */       
/*  700 */       int i = itemStack.getItemDamage();
/*      */       
/*  702 */       if (cip.damageMask != 0)
/*      */       {
/*  704 */         i &= cip.damageMask;
/*      */       }
/*      */       
/*  707 */       if (cip.damagePercent) {
/*      */         
/*  709 */         int j = item.getMaxDamage();
/*  710 */         i = (int)((i * 100) / j);
/*      */       } 
/*      */       
/*  713 */       if (!cip.damage.isInRange(i))
/*      */       {
/*  715 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  719 */     if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.stackSize))
/*      */     {
/*  721 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  725 */     int[][] aint = enchantmentIdLevels;
/*      */     
/*  727 */     if (cip.enchantmentIds != null) {
/*      */       
/*  729 */       if (enchantmentIdLevels == null)
/*      */       {
/*  731 */         aint = getEnchantmentIdLevels(itemStack);
/*      */       }
/*      */       
/*  734 */       boolean flag = false;
/*      */       
/*  736 */       for (int k = 0; k < aint.length; k++) {
/*      */         
/*  738 */         int l = aint[k][0];
/*      */         
/*  740 */         if (cip.enchantmentIds.isInRange(l)) {
/*      */           
/*  742 */           flag = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  747 */       if (!flag)
/*      */       {
/*  749 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  753 */     if (cip.enchantmentLevels != null) {
/*      */       
/*  755 */       if (aint == null)
/*      */       {
/*  757 */         aint = getEnchantmentIdLevels(itemStack);
/*      */       }
/*      */       
/*  760 */       boolean flag1 = false;
/*      */       
/*  762 */       for (int i1 = 0; i1 < aint.length; i1++) {
/*      */         
/*  764 */         int k1 = aint[i1][1];
/*      */         
/*  766 */         if (cip.enchantmentLevels.isInRange(k1)) {
/*      */           
/*  768 */           flag1 = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  773 */       if (!flag1)
/*      */       {
/*  775 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  779 */     if (cip.nbtTagValues != null) {
/*      */       
/*  781 */       NBTTagCompound nbttagcompound = itemStack.getTagCompound();
/*      */       
/*  783 */       for (int j1 = 0; j1 < cip.nbtTagValues.length; j1++) {
/*      */         
/*  785 */         NbtTagValue nbttagvalue = cip.nbtTagValues[j1];
/*      */         
/*  787 */         if (!nbttagvalue.matches(nbttagcompound))
/*      */         {
/*  789 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  794 */     if (cip.hand != 0) {
/*      */       
/*  796 */       if (cip.hand == 1 && renderOffHand)
/*      */       {
/*  798 */         return false;
/*      */       }
/*      */       
/*  801 */       if (cip.hand == 2 && !renderOffHand)
/*      */       {
/*  803 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  807 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
/*  813 */     Item item = itemStack.getItem();
/*  814 */     NBTTagList nbttaglist = (item == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
/*      */     
/*  816 */     if (nbttaglist != null && nbttaglist.tagCount() > 0) {
/*      */       
/*  818 */       int[][] aint = new int[nbttaglist.tagCount()][2];
/*      */       
/*  820 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/*  822 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  823 */         int j = nbttagcompound.getShort("id");
/*  824 */         int k = nbttagcompound.getShort("lvl");
/*  825 */         aint[i][0] = j;
/*  826 */         aint[i][1] = k;
/*      */       } 
/*      */       
/*  829 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/*  833 */     return EMPTY_INT2_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean renderCustomEffect(RenderItem renderItem, ItemStack itemStack, IBakedModel model) {
/*  839 */     if (enchantmentProperties == null)
/*      */     {
/*  841 */       return false;
/*      */     }
/*  843 */     if (itemStack == null)
/*      */     {
/*  845 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  849 */     int[][] aint = getEnchantmentIdLevels(itemStack);
/*      */     
/*  851 */     if (aint.length <= 0)
/*      */     {
/*  853 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  857 */     Set<Integer> set = null;
/*  858 */     boolean flag = false;
/*  859 */     TextureManager texturemanager = Config.getTextureManager();
/*      */     
/*  861 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  863 */       int j = aint[i][0];
/*      */       
/*  865 */       if (j >= 0 && j < enchantmentProperties.length) {
/*      */         
/*  867 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*      */         
/*  869 */         if (acustomitemproperties != null)
/*      */         {
/*  871 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/*      */             
/*  873 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*      */             
/*  875 */             if (set == null)
/*      */             {
/*  877 */               set = new HashSet();
/*      */             }
/*      */             
/*  880 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, itemStack, aint) && customitemproperties.textureLocation != null) {
/*      */               
/*  882 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/*  883 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*      */               
/*  885 */               if (!flag) {
/*      */                 
/*  887 */                 flag = true;
/*  888 */                 GlStateManager.depthMask(false);
/*  889 */                 GlStateManager.depthFunc(514);
/*  890 */                 GlStateManager.disableLighting();
/*  891 */                 GlStateManager.matrixMode(5890);
/*      */               } 
/*      */               
/*  894 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/*  895 */               GlStateManager.pushMatrix();
/*  896 */               GlStateManager.scale(f / 2.0F, f / 2.0F, f / 2.0F);
/*  897 */               float f1 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  898 */               GlStateManager.translate(f1, 0.0F, 0.0F);
/*  899 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/*  900 */               renderItem.renderModel(model, -1);
/*  901 */               GlStateManager.popMatrix();
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  908 */     if (flag) {
/*      */       
/*  910 */       GlStateManager.enableAlpha();
/*  911 */       GlStateManager.enableBlend();
/*  912 */       GlStateManager.blendFunc(770, 771);
/*  913 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  914 */       GlStateManager.matrixMode(5888);
/*  915 */       GlStateManager.enableLighting();
/*  916 */       GlStateManager.depthFunc(515);
/*  917 */       GlStateManager.depthMask(true);
/*  918 */       texturemanager.bindTexture(TextureMap.locationBlocksTexture);
/*      */     } 
/*      */     
/*  921 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean renderCustomArmorEffect(EntityLivingBase entity, ItemStack itemStack, ModelBase model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
/*  928 */     if (enchantmentProperties == null)
/*      */     {
/*  930 */       return false;
/*      */     }
/*  932 */     if (Config.isShaders() && Shaders.isShadowPass)
/*      */     {
/*  934 */       return false;
/*      */     }
/*  936 */     if (itemStack == null)
/*      */     {
/*  938 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  942 */     int[][] aint = getEnchantmentIdLevels(itemStack);
/*      */     
/*  944 */     if (aint.length <= 0)
/*      */     {
/*  946 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  950 */     Set<Integer> set = null;
/*  951 */     boolean flag = false;
/*  952 */     TextureManager texturemanager = Config.getTextureManager();
/*      */     
/*  954 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  956 */       int j = aint[i][0];
/*      */       
/*  958 */       if (j >= 0 && j < enchantmentProperties.length) {
/*      */         
/*  960 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*      */         
/*  962 */         if (acustomitemproperties != null)
/*      */         {
/*  964 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/*      */             
/*  966 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*      */             
/*  968 */             if (set == null)
/*      */             {
/*  970 */               set = new HashSet();
/*      */             }
/*      */             
/*  973 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, itemStack, aint) && customitemproperties.textureLocation != null) {
/*      */               
/*  975 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/*  976 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*      */               
/*  978 */               if (!flag) {
/*      */                 
/*  980 */                 flag = true;
/*      */                 
/*  982 */                 if (Config.isShaders())
/*      */                 {
/*  984 */                   ShadersRender.renderEnchantedGlintBegin();
/*      */                 }
/*      */                 
/*  987 */                 GlStateManager.enableBlend();
/*  988 */                 GlStateManager.depthFunc(514);
/*  989 */                 GlStateManager.depthMask(false);
/*      */               } 
/*      */               
/*  992 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/*  993 */               GlStateManager.disableLighting();
/*  994 */               GlStateManager.matrixMode(5890);
/*  995 */               GlStateManager.loadIdentity();
/*  996 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/*  997 */               float f1 = f / 8.0F;
/*  998 */               GlStateManager.scale(f1, f1 / 2.0F, f1);
/*  999 */               float f2 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/* 1000 */               GlStateManager.translate(0.0F, f2, 0.0F);
/* 1001 */               GlStateManager.matrixMode(5888);
/* 1002 */               model.render((Entity)entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1009 */     if (flag) {
/*      */       
/* 1011 */       GlStateManager.enableAlpha();
/* 1012 */       GlStateManager.enableBlend();
/* 1013 */       GlStateManager.blendFunc(770, 771);
/* 1014 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1015 */       GlStateManager.matrixMode(5890);
/* 1016 */       GlStateManager.loadIdentity();
/* 1017 */       GlStateManager.matrixMode(5888);
/* 1018 */       GlStateManager.enableLighting();
/* 1019 */       GlStateManager.depthMask(true);
/* 1020 */       GlStateManager.depthFunc(515);
/* 1021 */       GlStateManager.disableBlend();
/*      */       
/* 1023 */       if (Config.isShaders())
/*      */       {
/* 1025 */         ShadersRender.renderEnchantedGlintEnd();
/*      */       }
/*      */     } 
/*      */     
/* 1029 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUseGlint() {
/* 1036 */     return useGlint;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */