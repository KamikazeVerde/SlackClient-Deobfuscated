/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.RangeInt;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.config.VillagerProfession;
/*     */ import net.optifine.config.Weather;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.ArrayUtils;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class RandomEntityRule {
/*  27 */   private String pathProps = null;
/*  28 */   private ResourceLocation baseResLoc = null;
/*     */   private int index;
/*  30 */   private int[] textures = null;
/*  31 */   private ResourceLocation[] resourceLocations = null;
/*  32 */   private int[] weights = null;
/*  33 */   private BiomeGenBase[] biomes = null;
/*  34 */   private RangeListInt heights = null;
/*  35 */   private RangeListInt healthRange = null;
/*     */   private boolean healthPercent = false;
/*  37 */   private NbtTagValue nbtName = null;
/*  38 */   public int[] sumWeights = null;
/*  39 */   public int sumAllWeights = 1;
/*  40 */   private VillagerProfession[] professions = null;
/*  41 */   private EnumDyeColor[] collarColors = null;
/*  42 */   private Boolean baby = null;
/*  43 */   private RangeListInt moonPhases = null;
/*  44 */   private RangeListInt dayTimes = null;
/*  45 */   private Weather[] weatherList = null;
/*     */ 
/*     */   
/*     */   public RandomEntityRule(Properties props, String pathProps, ResourceLocation baseResLoc, int index, String valTextures, ConnectedParser cp) {
/*  49 */     this.pathProps = pathProps;
/*  50 */     this.baseResLoc = baseResLoc;
/*  51 */     this.index = index;
/*  52 */     this.textures = cp.parseIntList(valTextures);
/*  53 */     this.weights = cp.parseIntList(props.getProperty("weights." + index));
/*  54 */     this.biomes = cp.parseBiomes(props.getProperty("biomes." + index));
/*  55 */     this.heights = cp.parseRangeListInt(props.getProperty("heights." + index));
/*     */     
/*  57 */     if (this.heights == null)
/*     */     {
/*  59 */       this.heights = parseMinMaxHeight(props, index);
/*     */     }
/*     */     
/*  62 */     String s = props.getProperty("health." + index);
/*     */     
/*  64 */     if (s != null) {
/*     */       
/*  66 */       this.healthPercent = s.contains("%");
/*  67 */       s = s.replace("%", "");
/*  68 */       this.healthRange = cp.parseRangeListInt(s);
/*     */     } 
/*     */     
/*  71 */     this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
/*  72 */     this.professions = cp.parseProfessions(props.getProperty("professions." + index));
/*  73 */     this.collarColors = cp.parseDyeColors(props.getProperty("collarColors." + index), "collar color", ConnectedParser.DYE_COLORS_INVALID);
/*  74 */     this.baby = cp.parseBooleanObject(props.getProperty("baby." + index));
/*  75 */     this.moonPhases = cp.parseRangeListInt(props.getProperty("moonPhase." + index));
/*  76 */     this.dayTimes = cp.parseRangeListInt(props.getProperty("dayTime." + index));
/*  77 */     this.weatherList = cp.parseWeather(props.getProperty("weather." + index), "weather." + index, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private RangeListInt parseMinMaxHeight(Properties props, int index) {
/*  82 */     String s = props.getProperty("minHeight." + index);
/*  83 */     String s1 = props.getProperty("maxHeight." + index);
/*     */     
/*  85 */     if (s == null && s1 == null)
/*     */     {
/*  87 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  91 */     int i = 0;
/*     */     
/*  93 */     if (s != null) {
/*     */       
/*  95 */       i = Config.parseInt(s, -1);
/*     */       
/*  97 */       if (i < 0) {
/*     */         
/*  99 */         Config.warn("Invalid minHeight: " + s);
/* 100 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     int j = 256;
/*     */     
/* 106 */     if (s1 != null) {
/*     */       
/* 108 */       j = Config.parseInt(s1, -1);
/*     */       
/* 110 */       if (j < 0) {
/*     */         
/* 112 */         Config.warn("Invalid maxHeight: " + s1);
/* 113 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     if (j < 0) {
/*     */       
/* 119 */       Config.warn("Invalid minHeight, maxHeight: " + s + ", " + s1);
/* 120 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     RangeListInt rangelistint = new RangeListInt();
/* 125 */     rangelistint.addRange(new RangeInt(i, j));
/* 126 */     return rangelistint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 133 */     if (this.textures != null && this.textures.length != 0) {
/*     */       
/* 135 */       if (this.resourceLocations != null)
/*     */       {
/* 137 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 141 */       this.resourceLocations = new ResourceLocation[this.textures.length];
/* 142 */       boolean flag = this.pathProps.startsWith("mcpatcher/mob/");
/* 143 */       ResourceLocation resourcelocation = RandomEntities.getLocationRandom(this.baseResLoc, flag);
/*     */       
/* 145 */       if (resourcelocation == null) {
/*     */         
/* 147 */         Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
/* 148 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 152 */       for (int i = 0; i < this.resourceLocations.length; i++) {
/*     */         
/* 154 */         int j = this.textures[i];
/*     */         
/* 156 */         if (j <= 1) {
/*     */           
/* 158 */           this.resourceLocations[i] = this.baseResLoc;
/*     */         }
/*     */         else {
/*     */           
/* 162 */           ResourceLocation resourcelocation1 = RandomEntities.getLocationIndexed(resourcelocation, j);
/*     */           
/* 164 */           if (resourcelocation1 == null) {
/*     */             
/* 166 */             Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
/* 167 */             return false;
/*     */           } 
/*     */           
/* 170 */           if (!Config.hasResource(resourcelocation1)) {
/*     */             
/* 172 */             Config.warn("Texture not found: " + resourcelocation1.getResourcePath());
/* 173 */             return false;
/*     */           } 
/*     */           
/* 176 */           this.resourceLocations[i] = resourcelocation1;
/*     */         } 
/*     */       } 
/*     */       
/* 180 */       if (this.weights != null) {
/*     */         
/* 182 */         if (this.weights.length > this.resourceLocations.length) {
/*     */           
/* 184 */           Config.warn("More weights defined than skins, trimming weights: " + path);
/* 185 */           int[] aint = new int[this.resourceLocations.length];
/* 186 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/* 187 */           this.weights = aint;
/*     */         } 
/*     */         
/* 190 */         if (this.weights.length < this.resourceLocations.length) {
/*     */           
/* 192 */           Config.warn("Less weights defined than skins, expanding weights: " + path);
/* 193 */           int[] aint1 = new int[this.resourceLocations.length];
/* 194 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/* 195 */           int l = MathUtils.getAverage(this.weights);
/*     */           
/* 197 */           for (int j1 = this.weights.length; j1 < aint1.length; j1++)
/*     */           {
/* 199 */             aint1[j1] = l;
/*     */           }
/*     */           
/* 202 */           this.weights = aint1;
/*     */         } 
/*     */         
/* 205 */         this.sumWeights = new int[this.weights.length];
/* 206 */         int k = 0;
/*     */         
/* 208 */         for (int i1 = 0; i1 < this.weights.length; i1++) {
/*     */           
/* 210 */           if (this.weights[i1] < 0) {
/*     */             
/* 212 */             Config.warn("Invalid weight: " + this.weights[i1]);
/* 213 */             return false;
/*     */           } 
/*     */           
/* 216 */           k += this.weights[i1];
/* 217 */           this.sumWeights[i1] = k;
/*     */         } 
/*     */         
/* 220 */         this.sumAllWeights = k;
/*     */         
/* 222 */         if (this.sumAllWeights <= 0) {
/*     */           
/* 224 */           Config.warn("Invalid sum of all weights: " + k);
/* 225 */           this.sumAllWeights = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 229 */       if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/*     */         
/* 231 */         Config.warn("Invalid professions or careers: " + path);
/* 232 */         return false;
/*     */       } 
/* 234 */       if (this.collarColors == ConnectedParser.DYE_COLORS_INVALID) {
/*     */         
/* 236 */         Config.warn("Invalid collar colors: " + path);
/* 237 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 241 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     Config.warn("Invalid skins for rule: " + this.index);
/* 249 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(IRandomEntity randomEntity) {
/* 255 */     if (this.biomes != null && !Matches.biome(randomEntity.getSpawnBiome(), this.biomes))
/*     */     {
/* 257 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 261 */     if (this.heights != null) {
/*     */       
/* 263 */       BlockPos blockpos = randomEntity.getSpawnPosition();
/*     */       
/* 265 */       if (blockpos != null && !this.heights.isInRange(blockpos.getY()))
/*     */       {
/* 267 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     if (this.healthRange != null) {
/*     */       
/* 273 */       int i1 = randomEntity.getHealth();
/*     */       
/* 275 */       if (this.healthPercent) {
/*     */         
/* 277 */         int i = randomEntity.getMaxHealth();
/*     */         
/* 279 */         if (i > 0)
/*     */         {
/* 281 */           i1 = (int)((i1 * 100) / i);
/*     */         }
/*     */       } 
/*     */       
/* 285 */       if (!this.healthRange.isInRange(i1))
/*     */       {
/* 287 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     if (this.nbtName != null) {
/*     */       
/* 293 */       String s = randomEntity.getName();
/*     */       
/* 295 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 297 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 301 */     if (this.professions != null && randomEntity instanceof RandomEntity) {
/*     */       
/* 303 */       RandomEntity randomentity = (RandomEntity)randomEntity;
/* 304 */       Entity entity = randomentity.getEntity();
/*     */       
/* 306 */       if (entity instanceof EntityVillager) {
/*     */         
/* 308 */         EntityVillager entityvillager = (EntityVillager)entity;
/* 309 */         int j = entityvillager.getProfession();
/* 310 */         int k = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, -1);
/*     */         
/* 312 */         if (j < 0 || k < 0)
/*     */         {
/* 314 */           return false;
/*     */         }
/*     */         
/* 317 */         boolean flag = false;
/*     */         
/* 319 */         for (int l = 0; l < this.professions.length; l++) {
/*     */           
/* 321 */           VillagerProfession villagerprofession = this.professions[l];
/*     */           
/* 323 */           if (villagerprofession.matches(j, k)) {
/*     */             
/* 325 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 330 */         if (!flag)
/*     */         {
/* 332 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     if (this.collarColors != null && randomEntity instanceof RandomEntity) {
/*     */       
/* 339 */       RandomEntity randomentity1 = (RandomEntity)randomEntity;
/* 340 */       Entity entity1 = randomentity1.getEntity();
/*     */       
/* 342 */       if (entity1 instanceof EntityWolf) {
/*     */         
/* 344 */         EntityWolf entitywolf = (EntityWolf)entity1;
/*     */         
/* 346 */         if (!entitywolf.isTamed())
/*     */         {
/* 348 */           return false;
/*     */         }
/*     */         
/* 351 */         EnumDyeColor enumdyecolor = entitywolf.getCollarColor();
/*     */         
/* 353 */         if (!Config.equalsOne(enumdyecolor, (Object[])this.collarColors))
/*     */         {
/* 355 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 360 */     if (this.baby != null && randomEntity instanceof RandomEntity) {
/*     */       
/* 362 */       RandomEntity randomentity2 = (RandomEntity)randomEntity;
/* 363 */       Entity entity2 = randomentity2.getEntity();
/*     */       
/* 365 */       if (entity2 instanceof EntityLiving) {
/*     */         
/* 367 */         EntityLiving entityliving = (EntityLiving)entity2;
/*     */         
/* 369 */         if (entityliving.isChild() != this.baby.booleanValue())
/*     */         {
/* 371 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 376 */     if (this.moonPhases != null) {
/*     */       
/* 378 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 380 */       if (worldClient != null) {
/*     */         
/* 382 */         int j1 = worldClient.getMoonPhase();
/*     */         
/* 384 */         if (!this.moonPhases.isInRange(j1))
/*     */         {
/* 386 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 391 */     if (this.dayTimes != null) {
/*     */       
/* 393 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 395 */       if (worldClient != null) {
/*     */         
/* 397 */         int k1 = (int)worldClient.getWorldInfo().getWorldTime();
/*     */         
/* 399 */         if (!this.dayTimes.isInRange(k1))
/*     */         {
/* 401 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 406 */     if (this.weatherList != null) {
/*     */       
/* 408 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 410 */       if (worldClient != null) {
/*     */         
/* 412 */         Weather weather = Weather.getWeather((World)worldClient, 0.0F);
/*     */         
/* 414 */         if (!ArrayUtils.contains((Object[])this.weatherList, weather))
/*     */         {
/* 416 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 421 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc, int randomId) {
/* 427 */     if (this.resourceLocations != null && this.resourceLocations.length != 0) {
/*     */       
/* 429 */       int i = 0;
/*     */       
/* 431 */       if (this.weights == null) {
/*     */         
/* 433 */         i = randomId % this.resourceLocations.length;
/*     */       }
/*     */       else {
/*     */         
/* 437 */         int j = randomId % this.sumAllWeights;
/*     */         
/* 439 */         for (int k = 0; k < this.sumWeights.length; k++) {
/*     */           
/* 441 */           if (this.sumWeights[k] > j) {
/*     */             
/* 443 */             i = k;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 449 */       return this.resourceLocations[i];
/*     */     } 
/*     */ 
/*     */     
/* 453 */     return loc;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\RandomEntityRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */