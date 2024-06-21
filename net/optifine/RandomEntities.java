/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorRaw;
/*     */ import net.optifine.util.IntegratedServerUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class RandomEntities
/*     */ {
/*  33 */   private static Map<String, RandomEntityProperties> mapProperties = new HashMap<>();
/*     */   private static boolean active = false;
/*     */   private static RenderGlobal renderGlobal;
/*  36 */   private static RandomEntity randomEntity = new RandomEntity();
/*     */   private static TileEntityRendererDispatcher tileEntityRendererDispatcher;
/*  38 */   private static RandomTileEntity randomTileEntity = new RandomTileEntity();
/*     */   private static boolean working = false;
/*     */   public static final String SUFFIX_PNG = ".png";
/*     */   public static final String SUFFIX_PROPERTIES = ".properties";
/*     */   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
/*     */   public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
/*     */   public static final String PREFIX_TEXTURES = "textures/";
/*     */   public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
/*     */   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
/*  47 */   private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
/*     */   private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
/*  49 */   private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, EntityHorse.class, String[].class, 0);
/*  50 */   private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, EntityHorse.class, String[].class, 1);
/*     */ 
/*     */   
/*     */   public static void entityLoaded(Entity entity, World world) {
/*  54 */     if (world != null) {
/*     */       
/*  56 */       DataWatcher datawatcher = entity.getDataWatcher();
/*  57 */       datawatcher.spawnPosition = entity.getPosition();
/*  58 */       datawatcher.spawnBiome = world.getBiomeGenForCoords(datawatcher.spawnPosition);
/*  59 */       UUID uuid = entity.getUniqueID();
/*     */       
/*  61 */       if (entity instanceof EntityVillager)
/*     */       {
/*  63 */         updateEntityVillager(uuid, (EntityVillager)entity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void entityUnloaded(Entity entity, World world) {}
/*     */ 
/*     */   
/*     */   private static void updateEntityVillager(UUID uuid, EntityVillager ev) {
/*  74 */     Entity entity = IntegratedServerUtils.getEntity(uuid);
/*     */     
/*  76 */     if (entity instanceof EntityVillager) {
/*     */       
/*  78 */       EntityVillager entityvillager = (EntityVillager)entity;
/*  79 */       int i = entityvillager.getProfession();
/*  80 */       ev.setProfession(i);
/*  81 */       int j = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, 0);
/*  82 */       Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerId, j);
/*  83 */       int k = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerLevel, 0);
/*  84 */       Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerLevel, k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void worldChanged(World oldWorld, World newWorld) {
/*  90 */     if (newWorld != null) {
/*     */       
/*  92 */       List<Entity> list = newWorld.getLoadedEntityList();
/*     */       
/*  94 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/*  96 */         Entity entity = list.get(i);
/*  97 */         entityLoaded(entity, newWorld);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     randomEntity.setEntity(null);
/* 102 */     randomTileEntity.setTileEntity(null);
/*     */   }
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
/*     */     ResourceLocation name;
/* 107 */     if (!active)
/*     */     {
/* 109 */       return loc;
/*     */     }
/* 111 */     if (working)
/*     */     {
/* 113 */       return loc;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 121 */       working = true;
/* 122 */       IRandomEntity irandomentity = getRandomEntityRendered();
/*     */       
/* 124 */       if (irandomentity != null) {
/*     */         
/* 126 */         String s = loc.getResourcePath();
/*     */         
/* 128 */         if (s.startsWith("horse/"))
/*     */         {
/* 130 */           s = getHorseTexturePath(s, "horse/".length());
/*     */         }
/*     */         
/* 133 */         if (!s.startsWith("textures/entity/") && !s.startsWith("textures/painting/")) {
/*     */           
/* 135 */           ResourceLocation resourcelocation2 = loc;
/* 136 */           return resourcelocation2;
/*     */         } 
/*     */         
/* 139 */         RandomEntityProperties randomentityproperties = mapProperties.get(s);
/*     */         
/* 141 */         if (randomentityproperties == null) {
/*     */           
/* 143 */           ResourceLocation resourcelocation3 = loc;
/* 144 */           return resourcelocation3;
/*     */         } 
/*     */         
/* 147 */         ResourceLocation resourcelocation1 = randomentityproperties.getTextureLocation(loc, irandomentity);
/* 148 */         return resourcelocation1;
/*     */       } 
/*     */       
/* 151 */       name = loc;
/*     */     }
/*     */     finally {
/*     */       
/* 155 */       working = false;
/*     */     } 
/*     */     
/* 158 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getHorseTexturePath(String path, int pos) {
/* 164 */     if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
/*     */       
/* 166 */       for (int i = 0; i < HORSE_TEXTURES_ABBR.length; i++) {
/*     */         
/* 168 */         String s = HORSE_TEXTURES_ABBR[i];
/*     */         
/* 170 */         if (path.startsWith(s, pos))
/*     */         {
/* 172 */           return HORSE_TEXTURES[i];
/*     */         }
/*     */       } 
/*     */       
/* 176 */       return path;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IRandomEntity getRandomEntityRendered() {
/* 186 */     if (renderGlobal.renderedEntity != null) {
/*     */       
/* 188 */       randomEntity.setEntity(renderGlobal.renderedEntity);
/* 189 */       return randomEntity;
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (tileEntityRendererDispatcher.tileEntityRendered != null) {
/*     */       
/* 195 */       TileEntity tileentity = tileEntityRendererDispatcher.tileEntityRendered;
/*     */       
/* 197 */       if (tileentity.getWorld() != null) {
/*     */         
/* 199 */         randomTileEntity.setTileEntity(tileentity);
/* 200 */         return randomTileEntity;
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomEntityProperties makeProperties(ResourceLocation loc, boolean mcpatcher) {
/* 210 */     String s = loc.getResourcePath();
/* 211 */     ResourceLocation resourcelocation = getLocationProperties(loc, mcpatcher);
/*     */     
/* 213 */     if (resourcelocation != null) {
/*     */       
/* 215 */       RandomEntityProperties randomentityproperties = parseProperties(resourcelocation, loc);
/*     */       
/* 217 */       if (randomentityproperties != null)
/*     */       {
/* 219 */         return randomentityproperties;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     ResourceLocation[] aresourcelocation = getLocationsVariants(loc, mcpatcher);
/* 224 */     return (aresourcelocation == null) ? null : new RandomEntityProperties(s, aresourcelocation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomEntityProperties parseProperties(ResourceLocation propLoc, ResourceLocation resLoc) {
/*     */     try {
/* 231 */       String s = propLoc.getResourcePath();
/* 232 */       dbg(resLoc.getResourcePath() + ", properties: " + s);
/* 233 */       InputStream inputstream = Config.getResourceStream(propLoc);
/*     */       
/* 235 */       if (inputstream == null) {
/*     */         
/* 237 */         warn("Properties not found: " + s);
/* 238 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 242 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 243 */       propertiesOrdered.load(inputstream);
/* 244 */       inputstream.close();
/* 245 */       RandomEntityProperties randomentityproperties = new RandomEntityProperties((Properties)propertiesOrdered, s, resLoc);
/* 246 */       return !randomentityproperties.isValid(s) ? null : randomentityproperties;
/*     */     
/*     */     }
/* 249 */     catch (FileNotFoundException var6) {
/*     */       
/* 251 */       warn("File not found: " + resLoc.getResourcePath());
/* 252 */       return null;
/*     */     }
/* 254 */     catch (IOException ioexception) {
/*     */       
/* 256 */       ioexception.printStackTrace();
/* 257 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getLocationProperties(ResourceLocation loc, boolean mcpatcher) {
/* 263 */     ResourceLocation resourcelocation = getLocationRandom(loc, mcpatcher);
/*     */     
/* 265 */     if (resourcelocation == null)
/*     */     {
/* 267 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 271 */     String s = resourcelocation.getResourceDomain();
/* 272 */     String s1 = resourcelocation.getResourcePath();
/* 273 */     String s2 = StrUtils.removeSuffix(s1, ".png");
/* 274 */     String s3 = s2 + ".properties";
/* 275 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s3);
/*     */     
/* 277 */     if (Config.hasResource(resourcelocation1))
/*     */     {
/* 279 */       return resourcelocation1;
/*     */     }
/*     */ 
/*     */     
/* 283 */     String s4 = getParentTexturePath(s2);
/*     */     
/* 285 */     if (s4 == null)
/*     */     {
/* 287 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 291 */     ResourceLocation resourcelocation2 = new ResourceLocation(s, s4 + ".properties");
/* 292 */     return Config.hasResource(resourcelocation2) ? resourcelocation2 : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ResourceLocation getLocationRandom(ResourceLocation loc, boolean mcpatcher) {
/* 300 */     String s = loc.getResourceDomain();
/* 301 */     String s1 = loc.getResourcePath();
/* 302 */     String s2 = "textures/";
/* 303 */     String s3 = "optifine/random/";
/*     */     
/* 305 */     if (mcpatcher) {
/*     */       
/* 307 */       s2 = "textures/entity/";
/* 308 */       s3 = "mcpatcher/mob/";
/*     */     } 
/*     */     
/* 311 */     if (!s1.startsWith(s2))
/*     */     {
/* 313 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 317 */     String s4 = StrUtils.replacePrefix(s1, s2, s3);
/* 318 */     return new ResourceLocation(s, s4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPathBase(String pathRandom) {
/* 324 */     return pathRandom.startsWith("optifine/random/") ? StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/") : (pathRandom.startsWith("mcpatcher/mob/") ? StrUtils.replacePrefix(pathRandom, "mcpatcher/mob/", "textures/entity/") : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static ResourceLocation getLocationIndexed(ResourceLocation loc, int index) {
/* 329 */     if (loc == null)
/*     */     {
/* 331 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 335 */     String s = loc.getResourcePath();
/* 336 */     int i = s.lastIndexOf('.');
/*     */     
/* 338 */     if (i < 0)
/*     */     {
/* 340 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 344 */     String s1 = s.substring(0, i);
/* 345 */     String s2 = s.substring(i);
/* 346 */     String s3 = s1 + index + s2;
/* 347 */     ResourceLocation resourcelocation = new ResourceLocation(loc.getResourceDomain(), s3);
/* 348 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getParentTexturePath(String path) {
/* 355 */     for (int i = 0; i < DEPENDANT_SUFFIXES.length; i++) {
/*     */       
/* 357 */       String s = DEPENDANT_SUFFIXES[i];
/*     */       
/* 359 */       if (path.endsWith(s)) {
/*     */         
/* 361 */         String s1 = StrUtils.removeSuffix(path, s);
/* 362 */         return s1;
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation[] getLocationsVariants(ResourceLocation loc, boolean mcpatcher) {
/* 371 */     List<ResourceLocation> list = new ArrayList();
/* 372 */     list.add(loc);
/* 373 */     ResourceLocation resourcelocation = getLocationRandom(loc, mcpatcher);
/*     */     
/* 375 */     if (resourcelocation == null)
/*     */     {
/* 377 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 381 */     for (int i = 1; i < list.size() + 10; i++) {
/*     */       
/* 383 */       int j = i + 1;
/* 384 */       ResourceLocation resourcelocation1 = getLocationIndexed(resourcelocation, j);
/*     */       
/* 386 */       if (Config.hasResource(resourcelocation1))
/*     */       {
/* 388 */         list.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 392 */     if (list.size() <= 1)
/*     */     {
/* 394 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 398 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[list.size()]);
/* 399 */     dbg(loc.getResourcePath() + ", variants: " + aresourcelocation.length);
/* 400 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 407 */     mapProperties.clear();
/* 408 */     active = false;
/*     */     
/* 410 */     if (Config.isRandomEntities())
/*     */     {
/* 412 */       initialize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initialize() {
/* 418 */     renderGlobal = Config.getRenderGlobal();
/* 419 */     tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
/* 420 */     String[] astring = { "optifine/random/", "mcpatcher/mob/" };
/* 421 */     String[] astring1 = { ".png", ".properties" };
/* 422 */     String[] astring2 = ResUtils.collectFiles(astring, astring1);
/* 423 */     Set<String> set = new HashSet();
/*     */     
/* 425 */     for (int i = 0; i < astring2.length; i++) {
/*     */       
/* 427 */       String s = astring2[i];
/* 428 */       s = StrUtils.removeSuffix(s, astring1);
/* 429 */       s = StrUtils.trimTrailing(s, "0123456789");
/* 430 */       s = s + ".png";
/* 431 */       String s1 = getPathBase(s);
/*     */       
/* 433 */       if (!set.contains(s1)) {
/*     */         
/* 435 */         set.add(s1);
/* 436 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */         
/* 438 */         if (Config.hasResource(resourcelocation)) {
/*     */           
/* 440 */           RandomEntityProperties randomentityproperties = mapProperties.get(s1);
/*     */           
/* 442 */           if (randomentityproperties == null) {
/*     */             
/* 444 */             randomentityproperties = makeProperties(resourcelocation, false);
/*     */             
/* 446 */             if (randomentityproperties == null)
/*     */             {
/* 448 */               randomentityproperties = makeProperties(resourcelocation, true);
/*     */             }
/*     */             
/* 451 */             if (randomentityproperties != null)
/*     */             {
/* 453 */               mapProperties.put(s1, randomentityproperties);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 460 */     active = !mapProperties.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbg(String str) {
/* 465 */     Config.dbg("RandomEntities: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void warn(String str) {
/* 470 */     Config.warn("RandomEntities: " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\RandomEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */