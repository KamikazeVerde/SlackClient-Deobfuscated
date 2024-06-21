/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.gen.layer.GenLayer;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldChunkManager
/*     */ {
/*     */   private GenLayer genBiomes;
/*     */   private GenLayer biomeIndexLayer;
/*     */   private BiomeCache biomeCache;
/*     */   private List<BiomeGenBase> biomesToSpawnIn;
/*     */   private String field_180301_f;
/*     */   
/*     */   protected WorldChunkManager() {
/*  29 */     this.biomeCache = new BiomeCache(this);
/*  30 */     this.field_180301_f = "";
/*  31 */     this.biomesToSpawnIn = Lists.newArrayList();
/*  32 */     this.biomesToSpawnIn.add(BiomeGenBase.forest);
/*  33 */     this.biomesToSpawnIn.add(BiomeGenBase.plains);
/*  34 */     this.biomesToSpawnIn.add(BiomeGenBase.taiga);
/*  35 */     this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
/*  36 */     this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
/*  37 */     this.biomesToSpawnIn.add(BiomeGenBase.jungle);
/*  38 */     this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldChunkManager(long seed, WorldType p_i45744_3_, String p_i45744_4_) {
/*  43 */     this();
/*  44 */     this.field_180301_f = p_i45744_4_;
/*  45 */     GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, p_i45744_3_, p_i45744_4_);
/*  46 */     this.genBiomes = agenlayer[0];
/*  47 */     this.biomeIndexLayer = agenlayer[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldChunkManager(World worldIn) {
/*  52 */     this(worldIn.getSeed(), worldIn.getWorldInfo().getTerrainType(), worldIn.getWorldInfo().getGeneratorOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase> getBiomesToSpawnIn() {
/*  57 */     return this.biomesToSpawnIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenerator(BlockPos pos) {
/*  65 */     return getBiomeGenerator(pos, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenerator(BlockPos pos, BiomeGenBase biomeGenBaseIn) {
/*  70 */     return this.biomeCache.func_180284_a(pos.getX(), pos.getZ(), biomeGenBaseIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
/*  78 */     IntCache.resetIntCache();
/*     */     
/*  80 */     if (listToReuse == null || listToReuse.length < width * length)
/*     */     {
/*  82 */       listToReuse = new float[width * length];
/*     */     }
/*     */     
/*  85 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/*  87 */     for (int i = 0; i < width * length; i++) {
/*     */ 
/*     */       
/*     */       try {
/*  91 */         float f = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0F;
/*     */         
/*  93 */         if (f > 1.0F)
/*     */         {
/*  95 */           f = 1.0F;
/*     */         }
/*     */         
/*  98 */         listToReuse[i] = f;
/*     */       }
/* 100 */       catch (Throwable throwable) {
/*     */         
/* 102 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 103 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
/* 104 */         crashreportcategory.addCrashSection("biome id", Integer.valueOf(i));
/* 105 */         crashreportcategory.addCrashSection("downfalls[] size", Integer.valueOf(listToReuse.length));
/* 106 */         crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 107 */         crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 108 */         crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 109 */         crashreportcategory.addCrashSection("h", Integer.valueOf(length));
/* 110 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_) {
/* 122 */     return p_76939_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int height) {
/* 130 */     IntCache.resetIntCache();
/*     */     
/* 132 */     if (biomes == null || biomes.length < width * height)
/*     */     {
/* 134 */       biomes = new BiomeGenBase[width * height];
/*     */     }
/*     */     
/* 137 */     int[] aint = this.genBiomes.getInts(x, z, width, height);
/*     */ 
/*     */     
/*     */     try {
/* 141 */       for (int i = 0; i < width * height; i++)
/*     */       {
/* 143 */         biomes[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
/*     */       }
/*     */       
/* 146 */       return biomes;
/*     */     }
/* 148 */     catch (Throwable throwable) {
/*     */       
/* 150 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 151 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
/* 152 */       crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
/* 153 */       crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 154 */       crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 155 */       crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 156 */       crashreportcategory.addCrashSection("h", Integer.valueOf(height));
/* 157 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
/* 167 */     return getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 178 */     IntCache.resetIntCache();
/*     */     
/* 180 */     if (listToReuse == null || listToReuse.length < width * length)
/*     */     {
/* 182 */       listToReuse = new BiomeGenBase[width * length];
/*     */     }
/*     */     
/* 185 */     if (cacheFlag && width == 16 && length == 16 && (x & 0xF) == 0 && (z & 0xF) == 0) {
/*     */       
/* 187 */       BiomeGenBase[] abiomegenbase = this.biomeCache.getCachedBiomes(x, z);
/* 188 */       System.arraycopy(abiomegenbase, 0, listToReuse, 0, width * length);
/* 189 */       return listToReuse;
/*     */     } 
/*     */ 
/*     */     
/* 193 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/* 195 */     for (int i = 0; i < width * length; i++)
/*     */     {
/* 197 */       listToReuse[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
/*     */     }
/*     */     
/* 200 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List<BiomeGenBase> p_76940_4_) {
/* 209 */     IntCache.resetIntCache();
/* 210 */     int i = p_76940_1_ - p_76940_3_ >> 2;
/* 211 */     int j = p_76940_2_ - p_76940_3_ >> 2;
/* 212 */     int k = p_76940_1_ + p_76940_3_ >> 2;
/* 213 */     int l = p_76940_2_ + p_76940_3_ >> 2;
/* 214 */     int i1 = k - i + 1;
/* 215 */     int j1 = l - j + 1;
/* 216 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/*     */ 
/*     */     
/*     */     try {
/* 220 */       for (int k1 = 0; k1 < i1 * j1; k1++) {
/*     */         
/* 222 */         BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k1]);
/*     */         
/* 224 */         if (!p_76940_4_.contains(biomegenbase))
/*     */         {
/* 226 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 230 */       return true;
/*     */     }
/* 232 */     catch (Throwable throwable) {
/*     */       
/* 234 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 235 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
/* 236 */       crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
/* 237 */       crashreportcategory.addCrashSection("x", Integer.valueOf(p_76940_1_));
/* 238 */       crashreportcategory.addCrashSection("z", Integer.valueOf(p_76940_2_));
/* 239 */       crashreportcategory.addCrashSection("radius", Integer.valueOf(p_76940_3_));
/* 240 */       crashreportcategory.addCrashSection("allowed", p_76940_4_);
/* 241 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random random) {
/* 247 */     IntCache.resetIntCache();
/* 248 */     int i = x - range >> 2;
/* 249 */     int j = z - range >> 2;
/* 250 */     int k = x + range >> 2;
/* 251 */     int l = z + range >> 2;
/* 252 */     int i1 = k - i + 1;
/* 253 */     int j1 = l - j + 1;
/* 254 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/* 255 */     BlockPos blockpos = null;
/* 256 */     int k1 = 0;
/*     */     
/* 258 */     for (int l1 = 0; l1 < i1 * j1; l1++) {
/*     */       
/* 260 */       int i2 = i + l1 % i1 << 2;
/* 261 */       int j2 = j + l1 / i1 << 2;
/* 262 */       BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[l1]);
/*     */       
/* 264 */       if (biomes.contains(biomegenbase) && (blockpos == null || random.nextInt(k1 + 1) == 0)) {
/*     */         
/* 266 */         blockpos = new BlockPos(i2, 0, j2);
/* 267 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     return blockpos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupCache() {
/* 279 */     this.biomeCache.cleanupCache();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\biome\WorldChunkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */