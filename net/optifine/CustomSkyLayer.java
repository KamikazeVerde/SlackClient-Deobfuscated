/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.NumUtils;
/*     */ import net.optifine.util.SmoothFloat;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomSkyLayer
/*     */ {
/*  27 */   public String source = null;
/*  28 */   private int startFadeIn = -1;
/*  29 */   private int endFadeIn = -1;
/*  30 */   private int startFadeOut = -1;
/*  31 */   private int endFadeOut = -1;
/*  32 */   private int blend = 1;
/*     */   private boolean rotate = false;
/*  34 */   private float speed = 1.0F;
/*     */   private float[] axis;
/*     */   private RangeListInt days;
/*     */   private int daysLoop;
/*     */   private boolean weatherClear;
/*     */   private boolean weatherRain;
/*     */   private boolean weatherThunder;
/*     */   public BiomeGenBase[] biomes;
/*     */   public RangeListInt heights;
/*     */   private float transition;
/*     */   private SmoothFloat smoothPositionBrightness;
/*     */   public int textureId;
/*     */   private World lastWorld;
/*  47 */   public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };
/*     */   
/*     */   private static final String WEATHER_CLEAR = "clear";
/*     */   private static final String WEATHER_RAIN = "rain";
/*     */   private static final String WEATHER_THUNDER = "thunder";
/*     */   
/*     */   public CustomSkyLayer(Properties props, String defSource) {
/*  54 */     this.axis = DEFAULT_AXIS;
/*  55 */     this.days = null;
/*  56 */     this.daysLoop = 8;
/*  57 */     this.weatherClear = true;
/*  58 */     this.weatherRain = false;
/*  59 */     this.weatherThunder = false;
/*  60 */     this.biomes = null;
/*  61 */     this.heights = null;
/*  62 */     this.transition = 1.0F;
/*  63 */     this.smoothPositionBrightness = null;
/*  64 */     this.textureId = -1;
/*  65 */     this.lastWorld = null;
/*  66 */     ConnectedParser connectedparser = new ConnectedParser("CustomSky");
/*  67 */     this.source = props.getProperty("source", defSource);
/*  68 */     this.startFadeIn = parseTime(props.getProperty("startFadeIn"));
/*  69 */     this.endFadeIn = parseTime(props.getProperty("endFadeIn"));
/*  70 */     this.startFadeOut = parseTime(props.getProperty("startFadeOut"));
/*  71 */     this.endFadeOut = parseTime(props.getProperty("endFadeOut"));
/*  72 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*  73 */     this.rotate = parseBoolean(props.getProperty("rotate"), true);
/*  74 */     this.speed = parseFloat(props.getProperty("speed"), 1.0F);
/*  75 */     this.axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
/*  76 */     this.days = connectedparser.parseRangeListInt(props.getProperty("days"));
/*  77 */     this.daysLoop = connectedparser.parseInt(props.getProperty("daysLoop"), 8);
/*  78 */     List<String> list = parseWeatherList(props.getProperty("weather", "clear"));
/*  79 */     this.weatherClear = list.contains("clear");
/*  80 */     this.weatherRain = list.contains("rain");
/*  81 */     this.weatherThunder = list.contains("thunder");
/*  82 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  83 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*  84 */     this.transition = parseFloat(props.getProperty("transition"), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> parseWeatherList(String str) {
/*  89 */     List<String> list = Arrays.asList(new String[] { "clear", "rain", "thunder" });
/*  90 */     List<String> list1 = new ArrayList<>();
/*  91 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/*  93 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  95 */       String s = astring[i];
/*     */       
/*  97 */       if (!list.contains(s)) {
/*     */         
/*  99 */         Config.warn("Unknown weather: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 103 */         list1.add(s);
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return list1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseTime(String str) {
/* 112 */     if (str == null)
/*     */     {
/* 114 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 118 */     String[] astring = Config.tokenize(str, ":");
/*     */     
/* 120 */     if (astring.length != 2) {
/*     */       
/* 122 */       Config.warn("Invalid time: " + str);
/* 123 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     String s = astring[0];
/* 128 */     String s1 = astring[1];
/* 129 */     int i = Config.parseInt(s, -1);
/* 130 */     int j = Config.parseInt(s1, -1);
/*     */     
/* 132 */     if (i >= 0 && i <= 23 && j >= 0 && j <= 59) {
/*     */       
/* 134 */       i -= 6;
/*     */       
/* 136 */       if (i < 0)
/*     */       {
/* 138 */         i += 24;
/*     */       }
/*     */       
/* 141 */       int k = i * 1000 + (int)(j / 60.0D * 1000.0D);
/* 142 */       return k;
/*     */     } 
/*     */ 
/*     */     
/* 146 */     Config.warn("Invalid time: " + str);
/* 147 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parseBoolean(String str, boolean defVal) {
/* 155 */     if (str == null)
/*     */     {
/* 157 */       return defVal;
/*     */     }
/* 159 */     if (str.toLowerCase().equals("true"))
/*     */     {
/* 161 */       return true;
/*     */     }
/* 163 */     if (str.toLowerCase().equals("false"))
/*     */     {
/* 165 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 169 */     Config.warn("Unknown boolean: " + str);
/* 170 */     return defVal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float parseFloat(String str, float defVal) {
/* 176 */     if (str == null)
/*     */     {
/* 178 */       return defVal;
/*     */     }
/*     */ 
/*     */     
/* 182 */     float f = Config.parseFloat(str, Float.MIN_VALUE);
/*     */     
/* 184 */     if (f == Float.MIN_VALUE) {
/*     */       
/* 186 */       Config.warn("Invalid value: " + str);
/* 187 */       return defVal;
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] parseAxis(String str, float[] defVal) {
/* 198 */     if (str == null)
/*     */     {
/* 200 */       return defVal;
/*     */     }
/*     */ 
/*     */     
/* 204 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 206 */     if (astring.length != 3) {
/*     */       
/* 208 */       Config.warn("Invalid axis: " + str);
/* 209 */       return defVal;
/*     */     } 
/*     */ 
/*     */     
/* 213 */     float[] afloat = new float[3];
/*     */     
/* 215 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 217 */       afloat[i] = Config.parseFloat(astring[i], Float.MIN_VALUE);
/*     */       
/* 219 */       if (afloat[i] == Float.MIN_VALUE) {
/*     */         
/* 221 */         Config.warn("Invalid axis: " + str);
/* 222 */         return defVal;
/*     */       } 
/*     */       
/* 225 */       if (afloat[i] < -1.0F || afloat[i] > 1.0F) {
/*     */         
/* 227 */         Config.warn("Invalid axis values: " + str);
/* 228 */         return defVal;
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     float f2 = afloat[0];
/* 233 */     float f = afloat[1];
/* 234 */     float f1 = afloat[2];
/*     */     
/* 236 */     if (f2 * f2 + f * f + f1 * f1 < 1.0E-5F) {
/*     */       
/* 238 */       Config.warn("Invalid axis values: " + str);
/* 239 */       return defVal;
/*     */     } 
/*     */ 
/*     */     
/* 243 */     float[] afloat1 = { f1, f, -f2 };
/* 244 */     return afloat1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 252 */     if (this.source == null) {
/*     */       
/* 254 */       Config.warn("No source texture: " + path);
/* 255 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 259 */     this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
/*     */     
/* 261 */     if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
/*     */       
/* 263 */       int i = normalizeTime(this.endFadeIn - this.startFadeIn);
/*     */       
/* 265 */       if (this.startFadeOut < 0) {
/*     */         
/* 267 */         this.startFadeOut = normalizeTime(this.endFadeOut - i);
/*     */         
/* 269 */         if (timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn))
/*     */         {
/* 271 */           this.startFadeOut = this.endFadeIn;
/*     */         }
/*     */       } 
/*     */       
/* 275 */       int j = normalizeTime(this.startFadeOut - this.endFadeIn);
/* 276 */       int k = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 277 */       int l = normalizeTime(this.startFadeIn - this.endFadeOut);
/* 278 */       int i1 = i + j + k + l;
/*     */       
/* 280 */       if (i1 != 24000) {
/*     */         
/* 282 */         Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
/* 283 */         return false;
/*     */       } 
/* 285 */       if (this.speed < 0.0F) {
/*     */         
/* 287 */         Config.warn("Invalid speed: " + this.speed);
/* 288 */         return false;
/*     */       } 
/* 290 */       if (this.daysLoop <= 0) {
/*     */         
/* 292 */         Config.warn("Invalid daysLoop: " + this.daysLoop);
/* 293 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 297 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 302 */     Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int normalizeTime(int timeMc) {
/* 310 */     while (timeMc >= 24000)
/*     */     {
/* 312 */       timeMc -= 24000;
/*     */     }
/*     */     
/* 315 */     while (timeMc < 0)
/*     */     {
/* 317 */       timeMc += 24000;
/*     */     }
/*     */     
/* 320 */     return timeMc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(World world, int timeOfDay, float celestialAngle, float rainStrength, float thunderStrength) {
/* 325 */     float f = getPositionBrightness(world);
/* 326 */     float f1 = getWeatherBrightness(rainStrength, thunderStrength);
/* 327 */     float f2 = getFadeBrightness(timeOfDay);
/* 328 */     float f3 = f * f1 * f2;
/* 329 */     f3 = Config.limit(f3, 0.0F, 1.0F);
/*     */     
/* 331 */     if (f3 >= 1.0E-4F) {
/*     */       
/* 333 */       GlStateManager.bindTexture(this.textureId);
/* 334 */       Blender.setupBlend(this.blend, f3);
/* 335 */       GlStateManager.pushMatrix();
/*     */       
/* 337 */       if (this.rotate) {
/*     */         
/* 339 */         float f4 = 0.0F;
/*     */         
/* 341 */         if (this.speed != Math.round(this.speed)) {
/*     */           
/* 343 */           long i = (world.getWorldTime() + 18000L) / 24000L;
/* 344 */           double d0 = (this.speed % 1.0F);
/* 345 */           double d1 = i * d0;
/* 346 */           f4 = (float)(d1 % 1.0D);
/*     */         } 
/*     */         
/* 349 */         GlStateManager.rotate(360.0F * (f4 + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
/*     */       } 
/*     */       
/* 352 */       Tessellator tessellator = Tessellator.getInstance();
/* 353 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 354 */       GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/* 355 */       renderSide(tessellator, 4);
/* 356 */       GlStateManager.pushMatrix();
/* 357 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 358 */       renderSide(tessellator, 1);
/* 359 */       GlStateManager.popMatrix();
/* 360 */       GlStateManager.pushMatrix();
/* 361 */       GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/* 362 */       renderSide(tessellator, 0);
/* 363 */       GlStateManager.popMatrix();
/* 364 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 365 */       renderSide(tessellator, 5);
/* 366 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 367 */       renderSide(tessellator, 2);
/* 368 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 369 */       renderSide(tessellator, 3);
/* 370 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getPositionBrightness(World world) {
/* 376 */     if (this.biomes == null && this.heights == null)
/*     */     {
/* 378 */       return 1.0F;
/*     */     }
/*     */ 
/*     */     
/* 382 */     float f = getPositionBrightnessRaw(world);
/*     */     
/* 384 */     if (this.smoothPositionBrightness == null)
/*     */     {
/* 386 */       this.smoothPositionBrightness = new SmoothFloat(f, this.transition);
/*     */     }
/*     */     
/* 389 */     f = this.smoothPositionBrightness.getSmoothValue(f);
/* 390 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getPositionBrightnessRaw(World world) {
/* 396 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/* 398 */     if (entity == null)
/*     */     {
/* 400 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 404 */     BlockPos blockpos = entity.getPosition();
/*     */     
/* 406 */     if (this.biomes != null) {
/*     */       
/* 408 */       BiomeGenBase biomegenbase = world.getBiomeGenForCoords(blockpos);
/*     */       
/* 410 */       if (biomegenbase == null)
/*     */       {
/* 412 */         return 0.0F;
/*     */       }
/*     */       
/* 415 */       if (!Matches.biome(biomegenbase, this.biomes))
/*     */       {
/* 417 */         return 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 421 */     return (this.heights != null && !this.heights.isInRange(blockpos.getY())) ? 0.0F : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getWeatherBrightness(float rainStrength, float thunderStrength) {
/* 427 */     float f = 1.0F - rainStrength;
/* 428 */     float f1 = rainStrength - thunderStrength;
/* 429 */     float f2 = 0.0F;
/*     */     
/* 431 */     if (this.weatherClear)
/*     */     {
/* 433 */       f2 += f;
/*     */     }
/*     */     
/* 436 */     if (this.weatherRain)
/*     */     {
/* 438 */       f2 += f1;
/*     */     }
/*     */     
/* 441 */     if (this.weatherThunder)
/*     */     {
/* 443 */       f2 += thunderStrength;
/*     */     }
/*     */     
/* 446 */     f2 = NumUtils.limit(f2, 0.0F, 1.0F);
/* 447 */     return f2;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getFadeBrightness(int timeOfDay) {
/* 452 */     if (timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
/*     */       
/* 454 */       int k = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 455 */       int l = normalizeTime(timeOfDay - this.startFadeIn);
/* 456 */       return l / k;
/*     */     } 
/* 458 */     if (timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut))
/*     */     {
/* 460 */       return 1.0F;
/*     */     }
/* 462 */     if (timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
/*     */       
/* 464 */       int i = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 465 */       int j = normalizeTime(timeOfDay - this.startFadeOut);
/* 466 */       return 1.0F - j / i;
/*     */     } 
/*     */ 
/*     */     
/* 470 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSide(Tessellator tess, int side) {
/* 476 */     WorldRenderer worldrenderer = tess.getWorldRenderer();
/* 477 */     double d0 = (side % 3) / 3.0D;
/* 478 */     double d1 = (side / 3) / 2.0D;
/* 479 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 480 */     worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(d0, d1).endVertex();
/* 481 */     worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(d0, d1 + 0.5D).endVertex();
/* 482 */     worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(d0 + 0.3333333333333333D, d1 + 0.5D).endVertex();
/* 483 */     worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(d0 + 0.3333333333333333D, d1).endVertex();
/* 484 */     tess.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive(World world, int timeOfDay) {
/* 489 */     if (world != this.lastWorld) {
/*     */       
/* 491 */       this.lastWorld = world;
/* 492 */       this.smoothPositionBrightness = null;
/*     */     } 
/*     */     
/* 495 */     if (timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn))
/*     */     {
/* 497 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 501 */     if (this.days != null) {
/*     */       
/* 503 */       long i = world.getWorldTime();
/*     */       
/*     */       long j;
/* 506 */       for (j = i - this.startFadeIn; j < 0L; j += (24000 * this.daysLoop));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 511 */       int k = (int)(j / 24000L);
/* 512 */       int l = k % this.daysLoop;
/*     */       
/* 514 */       if (!this.days.isInRange(l))
/*     */       {
/* 516 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 520 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
/* 526 */     return (timeStart <= timeEnd) ? ((timeOfDay >= timeStart && timeOfDay <= timeEnd)) : ((timeOfDay >= timeStart || timeOfDay <= timeEnd));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 531 */     return "" + this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomSkyLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */