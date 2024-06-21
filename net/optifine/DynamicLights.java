/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.EntityClassLocator;
/*     */ import net.optifine.config.IObjectLocator;
/*     */ import net.optifine.config.ItemLocator;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicLights
/*     */ {
/*  38 */   private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
/*  39 */   private static Map<Class, Integer> mapEntityLightLevels = (Map)new HashMap<>();
/*  40 */   private static Map<Item, Integer> mapItemLightLevels = new HashMap<>();
/*  41 */   private static long timeUpdateMs = 0L;
/*     */   
/*     */   private static final double MAX_DIST = 7.5D;
/*     */   
/*     */   private static final double MAX_DIST_SQ = 56.25D;
/*     */   
/*     */   private static final int LIGHT_LEVEL_MAX = 15;
/*     */   private static final int LIGHT_LEVEL_FIRE = 15;
/*     */   private static final int LIGHT_LEVEL_BLAZE = 10;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
/*     */   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
/*     */   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
/*     */   private static boolean initialized;
/*     */   
/*     */   public static void entityAdded(Entity entityIn, RenderGlobal renderGlobal) {}
/*     */   
/*     */   public static void entityRemoved(Entity entityIn, RenderGlobal renderGlobal) {
/*  59 */     synchronized (mapDynamicLights) {
/*     */       
/*  61 */       DynamicLight dynamiclight = mapDynamicLights.remove(entityIn.getEntityId());
/*     */       
/*  63 */       if (dynamiclight != null)
/*     */       {
/*  65 */         dynamiclight.updateLitChunks(renderGlobal);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(RenderGlobal renderGlobal) {
/*  72 */     long i = System.currentTimeMillis();
/*     */     
/*  74 */     if (i >= timeUpdateMs + 50L) {
/*     */       
/*  76 */       timeUpdateMs = i;
/*     */       
/*  78 */       if (!initialized)
/*     */       {
/*  80 */         initialize();
/*     */       }
/*     */       
/*  83 */       synchronized (mapDynamicLights) {
/*     */         
/*  85 */         updateMapDynamicLights(renderGlobal);
/*     */         
/*  87 */         if (mapDynamicLights.size() > 0) {
/*     */           
/*  89 */           List<DynamicLight> list = mapDynamicLights.valueList();
/*     */           
/*  91 */           for (int j = 0; j < list.size(); j++) {
/*     */             
/*  93 */             DynamicLight dynamiclight = list.get(j);
/*  94 */             dynamiclight.update(renderGlobal);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initialize() {
/* 103 */     initialized = true;
/* 104 */     mapEntityLightLevels.clear();
/* 105 */     mapItemLightLevels.clear();
/* 106 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/* 108 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 110 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/* 114 */         ResourceLocation resourcelocation = new ResourceLocation(s, "optifine/dynamic_lights.properties");
/* 115 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/* 116 */         loadModConfiguration(inputstream, resourcelocation.toString(), s);
/*     */       }
/* 118 */       catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (mapEntityLightLevels.size() > 0)
/*     */     {
/* 126 */       Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
/*     */     }
/*     */     
/* 129 */     if (mapItemLightLevels.size() > 0)
/*     */     {
/* 131 */       Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModConfiguration(InputStream in, String path, String modId) {
/* 137 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 141 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 142 */         propertiesOrdered.load(in);
/* 143 */         in.close();
/* 144 */         Config.dbg("DynamicLights: Parsing " + path);
/* 145 */         ConnectedParser connectedparser = new ConnectedParser("DynamicLights");
/* 146 */         loadModLightLevels(propertiesOrdered.getProperty("entities"), mapEntityLightLevels, (IObjectLocator)new EntityClassLocator(), connectedparser, path, modId);
/* 147 */         loadModLightLevels(propertiesOrdered.getProperty("items"), mapItemLightLevels, (IObjectLocator)new ItemLocator(), connectedparser, path, modId);
/*     */       }
/* 149 */       catch (IOException var5) {
/*     */         
/* 151 */         Config.warn("DynamicLights: Error reading " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModLightLevels(String prop, Map<Object, Integer> mapLightLevels, IObjectLocator ol, ConnectedParser cp, String path, String modId) {
/* 158 */     if (prop != null) {
/*     */       
/* 160 */       String[] astring = Config.tokenize(prop, " ");
/*     */       
/* 162 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/* 164 */         String s = astring[i];
/* 165 */         String[] astring1 = Config.tokenize(s, ":");
/*     */         
/* 167 */         if (astring1.length != 2) {
/*     */           
/* 169 */           cp.warn("Invalid entry: " + s + ", in:" + path);
/*     */         }
/*     */         else {
/*     */           
/* 173 */           String s1 = astring1[0];
/* 174 */           String s2 = astring1[1];
/* 175 */           String s3 = modId + ":" + s1;
/* 176 */           ResourceLocation resourcelocation = new ResourceLocation(s3);
/* 177 */           Object object = ol.getObject(resourcelocation);
/*     */           
/* 179 */           if (object == null) {
/*     */             
/* 181 */             cp.warn("Object not found: " + s3);
/*     */           }
/*     */           else {
/*     */             
/* 185 */             int j = cp.parseInt(s2, -1);
/*     */             
/* 187 */             if (j >= 0 && j <= 15) {
/*     */               
/* 189 */               mapLightLevels.put(object, new Integer(j));
/*     */             }
/*     */             else {
/*     */               
/* 193 */               cp.warn("Invalid light level: " + s);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateMapDynamicLights(RenderGlobal renderGlobal) {
/* 203 */     WorldClient worldClient = renderGlobal.getWorld();
/*     */     
/* 205 */     if (worldClient != null)
/*     */     {
/* 207 */       for (Entity entity : worldClient.getLoadedEntityList()) {
/*     */         
/* 209 */         int i = getLightLevel(entity);
/*     */         
/* 211 */         if (i > 0) {
/*     */           
/* 213 */           int j = entity.getEntityId();
/* 214 */           DynamicLight dynamiclight = mapDynamicLights.get(j);
/*     */           
/* 216 */           if (dynamiclight == null) {
/*     */             
/* 218 */             dynamiclight = new DynamicLight(entity);
/* 219 */             mapDynamicLights.put(j, dynamiclight);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 224 */         int k = entity.getEntityId();
/* 225 */         DynamicLight dynamiclight1 = mapDynamicLights.remove(k);
/*     */         
/* 227 */         if (dynamiclight1 != null)
/*     */         {
/* 229 */           dynamiclight1.updateLitChunks(renderGlobal);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(BlockPos pos, int combinedLight) {
/* 238 */     double d0 = getLightLevel(pos);
/* 239 */     combinedLight = getCombinedLight(d0, combinedLight);
/* 240 */     return combinedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(Entity entity, int combinedLight) {
/* 245 */     double d0 = getLightLevel(entity);
/* 246 */     combinedLight = getCombinedLight(d0, combinedLight);
/* 247 */     return combinedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(double lightPlayer, int combinedLight) {
/* 252 */     if (lightPlayer > 0.0D) {
/*     */       
/* 254 */       int i = (int)(lightPlayer * 16.0D);
/* 255 */       int j = combinedLight & 0xFF;
/*     */       
/* 257 */       if (i > j) {
/*     */         
/* 259 */         combinedLight &= 0xFFFFFF00;
/* 260 */         combinedLight |= i;
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     return combinedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getLightLevel(BlockPos pos) {
/* 269 */     double d0 = 0.0D;
/*     */     
/* 271 */     synchronized (mapDynamicLights) {
/*     */       
/* 273 */       List<DynamicLight> list = mapDynamicLights.valueList();
/*     */       
/* 275 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 277 */         DynamicLight dynamiclight = list.get(i);
/* 278 */         int j = dynamiclight.getLastLightLevel();
/*     */         
/* 280 */         if (j > 0) {
/*     */           
/* 282 */           double d1 = dynamiclight.getLastPosX();
/* 283 */           double d2 = dynamiclight.getLastPosY();
/* 284 */           double d3 = dynamiclight.getLastPosZ();
/* 285 */           double d4 = pos.getX() - d1;
/* 286 */           double d5 = pos.getY() - d2;
/* 287 */           double d6 = pos.getZ() - d3;
/* 288 */           double d7 = d4 * d4 + d5 * d5 + d6 * d6;
/*     */           
/* 290 */           if (dynamiclight.isUnderwater() && !Config.isClearWater()) {
/*     */             
/* 292 */             j = Config.limit(j - 2, 0, 15);
/* 293 */             d7 *= 2.0D;
/*     */           } 
/*     */           
/* 296 */           if (d7 <= 56.25D) {
/*     */             
/* 298 */             double d8 = Math.sqrt(d7);
/* 299 */             double d9 = 1.0D - d8 / 7.5D;
/* 300 */             double d10 = d9 * j;
/*     */             
/* 302 */             if (d10 > d0)
/*     */             {
/* 304 */               d0 = d10;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     double d11 = Config.limit(d0, 0.0D, 15.0D);
/* 312 */     return d11;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getLightLevel(ItemStack itemStack) {
/* 317 */     if (itemStack == null)
/*     */     {
/* 319 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 323 */     Item item = itemStack.getItem();
/*     */     
/* 325 */     if (item instanceof ItemBlock) {
/*     */       
/* 327 */       ItemBlock itemblock = (ItemBlock)item;
/* 328 */       Block block = itemblock.getBlock();
/*     */       
/* 330 */       if (block != null)
/*     */       {
/* 332 */         return block.getLightValue();
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if (item == Items.lava_bucket)
/*     */     {
/* 338 */       return Blocks.lava.getLightValue();
/*     */     }
/* 340 */     if (item != Items.blaze_rod && item != Items.blaze_powder) {
/*     */       
/* 342 */       if (item == Items.glowstone_dust)
/*     */       {
/* 344 */         return 8;
/*     */       }
/* 346 */       if (item == Items.prismarine_crystals)
/*     */       {
/* 348 */         return 8;
/*     */       }
/* 350 */       if (item == Items.magma_cream)
/*     */       {
/* 352 */         return 8;
/*     */       }
/* 354 */       if (item == Items.nether_star)
/*     */       {
/* 356 */         return Blocks.beacon.getLightValue() / 2;
/*     */       }
/*     */ 
/*     */       
/* 360 */       if (!mapItemLightLevels.isEmpty()) {
/*     */         
/* 362 */         Integer integer = mapItemLightLevels.get(item);
/*     */         
/* 364 */         if (integer != null)
/*     */         {
/* 366 */           return integer.intValue();
/*     */         }
/*     */       } 
/*     */       
/* 370 */       return 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 375 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightLevel(Entity entity) {
/* 382 */     if (entity == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight())
/*     */     {
/* 384 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 388 */     if (entity instanceof EntityPlayer) {
/*     */       
/* 390 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*     */       
/* 392 */       if (entityplayer.isSpectator())
/*     */       {
/* 394 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 398 */     if (entity.isBurning())
/*     */     {
/* 400 */       return 15;
/*     */     }
/*     */ 
/*     */     
/* 404 */     if (!mapEntityLightLevels.isEmpty()) {
/*     */       
/* 406 */       Integer integer = mapEntityLightLevels.get(entity.getClass());
/*     */       
/* 408 */       if (integer != null)
/*     */       {
/* 410 */         return integer.intValue();
/*     */       }
/*     */     } 
/*     */     
/* 414 */     if (entity instanceof net.minecraft.entity.projectile.EntityFireball)
/*     */     {
/* 416 */       return 15;
/*     */     }
/* 418 */     if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/*     */     {
/* 420 */       return 15;
/*     */     }
/* 422 */     if (entity instanceof EntityBlaze) {
/*     */       
/* 424 */       EntityBlaze entityblaze = (EntityBlaze)entity;
/* 425 */       return entityblaze.func_70845_n() ? 15 : 10;
/*     */     } 
/* 427 */     if (entity instanceof EntityMagmaCube) {
/*     */       
/* 429 */       EntityMagmaCube entitymagmacube = (EntityMagmaCube)entity;
/* 430 */       return (entitymagmacube.squishFactor > 0.6D) ? 13 : 8;
/*     */     } 
/*     */ 
/*     */     
/* 434 */     if (entity instanceof EntityCreeper) {
/*     */       
/* 436 */       EntityCreeper entitycreeper = (EntityCreeper)entity;
/*     */       
/* 438 */       if (entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001D)
/*     */       {
/* 440 */         return 15;
/*     */       }
/*     */     } 
/*     */     
/* 444 */     if (entity instanceof EntityLivingBase) {
/*     */       
/* 446 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 447 */       ItemStack itemstack2 = entitylivingbase.getHeldItem();
/* 448 */       int i = getLightLevel(itemstack2);
/* 449 */       ItemStack itemstack1 = entitylivingbase.getEquipmentInSlot(4);
/* 450 */       int j = getLightLevel(itemstack1);
/* 451 */       return Math.max(i, j);
/*     */     } 
/* 453 */     if (entity instanceof EntityItem) {
/*     */       
/* 455 */       EntityItem entityitem = (EntityItem)entity;
/* 456 */       ItemStack itemstack = getItemStack(entityitem);
/* 457 */       return getLightLevel(itemstack);
/*     */     } 
/*     */ 
/*     */     
/* 461 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeLights(RenderGlobal renderGlobal) {
/* 470 */     synchronized (mapDynamicLights) {
/*     */       
/* 472 */       List<DynamicLight> list = mapDynamicLights.valueList();
/*     */       
/* 474 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 476 */         DynamicLight dynamiclight = list.get(i);
/* 477 */         dynamiclight.updateLitChunks(renderGlobal);
/*     */       } 
/*     */       
/* 480 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clear() {
/* 486 */     synchronized (mapDynamicLights) {
/*     */       
/* 488 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCount() {
/* 494 */     synchronized (mapDynamicLights) {
/*     */       
/* 496 */       return mapDynamicLights.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getItemStack(EntityItem entityItem) {
/* 502 */     ItemStack itemstack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);
/* 503 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\DynamicLights.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */