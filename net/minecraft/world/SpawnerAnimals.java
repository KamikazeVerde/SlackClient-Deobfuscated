/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntitySpawnPlacementRegistry;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ 
/*     */ public final class SpawnerAnimals {
/*  32 */   private static final int MOB_COUNT_DIV = (int)Math.pow(17.0D, 2.0D);
/*  33 */   private final Set<ChunkCoordIntPair> eligibleChunksForSpawning = Sets.newHashSet();
/*  34 */   private Map<Class, EntityLiving> mapSampleEntitiesByClass = (Map)new HashMap<>();
/*  35 */   private int lastPlayerChunkX = Integer.MAX_VALUE;
/*  36 */   private int lastPlayerChunkZ = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */   
/*     */   private int countChunkPos;
/*     */ 
/*     */ 
/*     */   
/*     */   public int findChunksForSpawning(WorldServer p_77192_1_, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean p_77192_4_) {
/*  45 */     if (!spawnHostileMobs && !spawnPeacefulMobs)
/*     */     {
/*  47 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  51 */     boolean flag = true;
/*  52 */     EntityPlayer entityplayer = null;
/*     */     
/*  54 */     if (p_77192_1_.playerEntities.size() == 1) {
/*     */       
/*  56 */       entityplayer = p_77192_1_.playerEntities.get(0);
/*     */       
/*  58 */       if (this.eligibleChunksForSpawning.size() > 0 && entityplayer != null && entityplayer.chunkCoordX == this.lastPlayerChunkX && entityplayer.chunkCoordZ == this.lastPlayerChunkZ)
/*     */       {
/*  60 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/*  64 */     if (flag) {
/*     */       
/*  66 */       this.eligibleChunksForSpawning.clear();
/*  67 */       int i = 0;
/*     */       
/*  69 */       for (EntityPlayer entityplayer1 : p_77192_1_.playerEntities) {
/*     */         
/*  71 */         if (!entityplayer1.isSpectator()) {
/*     */           
/*  73 */           int j = MathHelper.floor_double(entityplayer1.posX / 16.0D);
/*  74 */           int k = MathHelper.floor_double(entityplayer1.posZ / 16.0D);
/*  75 */           int l = 8;
/*     */           
/*  77 */           for (int i1 = -l; i1 <= l; i1++) {
/*     */             
/*  79 */             for (int j1 = -l; j1 <= l; j1++) {
/*     */               
/*  81 */               boolean flag1 = (i1 == -l || i1 == l || j1 == -l || j1 == l);
/*  82 */               ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i1 + j, j1 + k);
/*     */               
/*  84 */               if (!this.eligibleChunksForSpawning.contains(chunkcoordintpair)) {
/*     */                 
/*  86 */                 i++;
/*     */                 
/*  88 */                 if (!flag1 && p_77192_1_.getWorldBorder().contains(chunkcoordintpair))
/*     */                 {
/*  90 */                   this.eligibleChunksForSpawning.add(chunkcoordintpair);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  98 */       this.countChunkPos = i;
/*     */       
/* 100 */       if (entityplayer != null) {
/*     */         
/* 102 */         this.lastPlayerChunkX = entityplayer.chunkCoordX;
/* 103 */         this.lastPlayerChunkZ = entityplayer.chunkCoordZ;
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     int j4 = 0;
/* 108 */     BlockPos blockpos2 = p_77192_1_.getSpawnPoint();
/* 109 */     BlockPosM blockposm = new BlockPosM(0, 0, 0);
/* 110 */     new BlockPos.MutableBlockPos();
/*     */     
/* 112 */     for (EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
/*     */       
/* 114 */       if ((!enumcreaturetype.getPeacefulCreature() || spawnPeacefulMobs) && (enumcreaturetype.getPeacefulCreature() || spawnHostileMobs) && (!enumcreaturetype.getAnimal() || p_77192_4_)) {
/*     */         
/* 116 */         int k4 = Reflector.ForgeWorld_countEntities.exists() ? Reflector.callInt(p_77192_1_, Reflector.ForgeWorld_countEntities, new Object[] { enumcreaturetype, Boolean.TRUE }) : p_77192_1_.countEntities(enumcreaturetype.getCreatureClass());
/* 117 */         int l4 = enumcreaturetype.getMaxNumberOfCreature() * this.countChunkPos / MOB_COUNT_DIV;
/*     */         
/* 119 */         if (k4 <= l4) {
/*     */           
/* 121 */           Collection<ChunkCoordIntPair> collection = this.eligibleChunksForSpawning;
/*     */           
/* 123 */           if (Reflector.ForgeHooksClient.exists()) {
/*     */             
/* 125 */             ArrayList<ChunkCoordIntPair> arraylist = Lists.newArrayList(collection);
/* 126 */             Collections.shuffle(arraylist);
/* 127 */             collection = arraylist;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 132 */           label118: for (ChunkCoordIntPair chunkcoordintpair1 : collection) {
/*     */             
/* 134 */             BlockPosM blockPosM = getRandomChunkPosition(p_77192_1_, chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos, blockposm);
/* 135 */             int k1 = blockPosM.getX();
/* 136 */             int l1 = blockPosM.getY();
/* 137 */             int i2 = blockPosM.getZ();
/* 138 */             Block block = p_77192_1_.getBlockState((BlockPos)blockPosM).getBlock();
/*     */             
/* 140 */             if (!block.isNormalCube()) {
/*     */               
/* 142 */               int j2 = 0;
/*     */               
/* 144 */               for (int k2 = 0; k2 < 3; k2++) {
/*     */                 
/* 146 */                 int l2 = k1;
/* 147 */                 int i3 = l1;
/* 148 */                 int j3 = i2;
/* 149 */                 int k3 = 6;
/* 150 */                 BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = null;
/* 151 */                 IEntityLivingData ientitylivingdata = null;
/*     */                 
/* 153 */                 for (int l3 = 0; l3 < 4; l3++) {
/*     */                   
/* 155 */                   l2 += p_77192_1_.rand.nextInt(k3) - p_77192_1_.rand.nextInt(k3);
/* 156 */                   i3 += p_77192_1_.rand.nextInt(1) - p_77192_1_.rand.nextInt(1);
/* 157 */                   j3 += p_77192_1_.rand.nextInt(k3) - p_77192_1_.rand.nextInt(k3);
/* 158 */                   BlockPos blockpos1 = new BlockPos(l2, i3, j3);
/* 159 */                   float f = l2 + 0.5F;
/* 160 */                   float f1 = j3 + 0.5F;
/*     */                   
/* 162 */                   if (!p_77192_1_.isAnyPlayerWithinRangeAt(f, i3, f1, 24.0D) && blockpos2.distanceSq(f, i3, f1) >= 576.0D) {
/*     */                     
/* 164 */                     if (biomegenbase$spawnlistentry == null) {
/*     */                       
/* 166 */                       biomegenbase$spawnlistentry = p_77192_1_.getSpawnListEntryForTypeAt(enumcreaturetype, blockpos1);
/*     */                       
/* 168 */                       if (biomegenbase$spawnlistentry == null) {
/*     */                         break;
/*     */                       }
/*     */                     } 
/*     */ 
/*     */                     
/* 174 */                     if (p_77192_1_.canCreatureTypeSpawnHere(enumcreaturetype, biomegenbase$spawnlistentry, blockpos1) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(biomegenbase$spawnlistentry.entityClass), p_77192_1_, blockpos1)) {
/*     */                       EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */                       
/*     */                       try {
/* 180 */                         entityliving = this.mapSampleEntitiesByClass.get(biomegenbase$spawnlistentry.entityClass);
/*     */                         
/* 182 */                         if (entityliving == null)
/*     */                         {
/* 184 */                           entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_77192_1_ });
/* 185 */                           this.mapSampleEntitiesByClass.put(biomegenbase$spawnlistentry.entityClass, entityliving);
/*     */                         }
/*     */                       
/* 188 */                       } catch (Exception exception1) {
/*     */                         
/* 190 */                         exception1.printStackTrace();
/* 191 */                         return j4;
/*     */                       } 
/*     */                       
/* 194 */                       entityliving.setLocationAndAngles(f, i3, f1, p_77192_1_.rand.nextFloat() * 360.0F, 0.0F);
/* 195 */                       boolean flag2 = Reflector.ForgeEventFactory_canEntitySpawn.exists() ? ReflectorForge.canEntitySpawn(entityliving, p_77192_1_, f, i3, f1) : ((entityliving.getCanSpawnHere() && entityliving.isNotColliding()));
/*     */                       
/* 197 */                       if (flag2) {
/*     */                         
/* 199 */                         this.mapSampleEntitiesByClass.remove(biomegenbase$spawnlistentry.entityClass);
/*     */                         
/* 201 */                         if (!ReflectorForge.doSpecialSpawn(entityliving, p_77192_1_, f, i3, f1))
/*     */                         {
/* 203 */                           ientitylivingdata = entityliving.onInitialSpawn(p_77192_1_.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/*     */                         }
/*     */                         
/* 206 */                         if (entityliving.isNotColliding()) {
/*     */                           
/* 208 */                           j2++;
/* 209 */                           p_77192_1_.spawnEntityInWorld((Entity)entityliving);
/*     */                         } 
/*     */                         
/* 212 */                         int i4 = Reflector.ForgeEventFactory_getMaxSpawnPackSize.exists() ? Reflector.callInt(Reflector.ForgeEventFactory_getMaxSpawnPackSize, new Object[] { entityliving }) : entityliving.getMaxSpawnedInChunk();
/*     */                         
/* 214 */                         if (j2 >= i4) {
/*     */                           continue label118;
/*     */                         }
/*     */                       } 
/*     */ 
/*     */                       
/* 220 */                       j4 += j2;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     return j4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
/* 237 */     Chunk chunk = worldIn.getChunkFromChunkCoords(x, z);
/* 238 */     int i = x * 16 + worldIn.rand.nextInt(16);
/* 239 */     int j = z * 16 + worldIn.rand.nextInt(16);
/* 240 */     int k = MathHelper.func_154354_b(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
/* 241 */     int l = worldIn.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 242 */     return new BlockPos(i, l, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockPosM getRandomChunkPosition(World p_getRandomChunkPosition_0_, int p_getRandomChunkPosition_1_, int p_getRandomChunkPosition_2_, BlockPosM p_getRandomChunkPosition_3_) {
/* 247 */     Chunk chunk = p_getRandomChunkPosition_0_.getChunkFromChunkCoords(p_getRandomChunkPosition_1_, p_getRandomChunkPosition_2_);
/* 248 */     int i = p_getRandomChunkPosition_1_ * 16 + p_getRandomChunkPosition_0_.rand.nextInt(16);
/* 249 */     int j = p_getRandomChunkPosition_2_ * 16 + p_getRandomChunkPosition_0_.rand.nextInt(16);
/* 250 */     int k = MathHelper.func_154354_b(chunk.getHeightValue(i & 0xF, j & 0xF) + 1, 16);
/* 251 */     int l = p_getRandomChunkPosition_0_.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 252 */     p_getRandomChunkPosition_3_.setXyz(i, l, j);
/* 253 */     return p_getRandomChunkPosition_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType p_180267_0_, World worldIn, BlockPos pos) {
/* 258 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 260 */       return false;
/*     */     }
/* 262 */     if (p_180267_0_ == null)
/*     */     {
/* 264 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 268 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 270 */     if (p_180267_0_ == EntityLiving.SpawnPlacementType.IN_WATER)
/*     */     {
/* 272 */       return (block.getMaterial().isLiquid() && worldIn.getBlockState(pos.down()).getBlock().getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */     }
/*     */ 
/*     */     
/* 276 */     BlockPos blockpos = pos.down();
/* 277 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 278 */     boolean flag = Reflector.ForgeBlock_canCreatureSpawn.exists() ? Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_canCreatureSpawn, new Object[] { worldIn, blockpos, p_180267_0_ }) : World.doesBlockHaveSolidTopSurface(worldIn, blockpos);
/*     */     
/* 280 */     if (!flag)
/*     */     {
/* 282 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 286 */     Block block1 = worldIn.getBlockState(blockpos).getBlock();
/* 287 */     boolean flag1 = (block1 != Blocks.bedrock && block1 != Blocks.barrier);
/* 288 */     return (flag1 && !block.isNormalCube() && !block.getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performWorldGenSpawning(World worldIn, BiomeGenBase p_77191_1_, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random p_77191_6_) {
/* 299 */     List<BiomeGenBase.SpawnListEntry> list = p_77191_1_.getSpawnableList(EnumCreatureType.CREATURE);
/*     */     
/* 301 */     if (!list.isEmpty())
/*     */     {
/* 303 */       while (p_77191_6_.nextFloat() < p_77191_1_.getSpawningChance()) {
/*     */         
/* 305 */         BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(worldIn.rand, list);
/* 306 */         int i = biomegenbase$spawnlistentry.minGroupCount + p_77191_6_.nextInt(1 + biomegenbase$spawnlistentry.maxGroupCount - biomegenbase$spawnlistentry.minGroupCount);
/* 307 */         IEntityLivingData ientitylivingdata = null;
/* 308 */         int j = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
/* 309 */         int k = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
/* 310 */         int l = j;
/* 311 */         int i1 = k;
/*     */         
/* 313 */         for (int j1 = 0; j1 < i; j1++) {
/*     */           
/* 315 */           boolean flag = false;
/*     */           
/* 317 */           for (int k1 = 0; !flag && k1 < 4; k1++) {
/*     */             
/* 319 */             BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
/*     */             
/* 321 */             if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos)) {
/*     */               EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 327 */                 entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */               }
/* 329 */               catch (Exception exception1) {
/*     */                 
/* 331 */                 exception1.printStackTrace();
/*     */                 
/*     */                 continue;
/*     */               } 
/* 335 */               if (Reflector.ForgeEventFactory_canEntitySpawn.exists()) {
/*     */                 
/* 337 */                 Object object = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, new Object[] { entityliving, worldIn, Float.valueOf(j + 0.5F), Integer.valueOf(blockpos.getY()), Float.valueOf(k + 0.5F) });
/*     */                 
/* 339 */                 if (object == ReflectorForge.EVENT_RESULT_DENY) {
/*     */                   continue;
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 345 */               entityliving.setLocationAndAngles((j + 0.5F), blockpos.getY(), (k + 0.5F), p_77191_6_.nextFloat() * 360.0F, 0.0F);
/* 346 */               worldIn.spawnEntityInWorld((Entity)entityliving);
/* 347 */               ientitylivingdata = entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/* 348 */               flag = true;
/*     */             } 
/*     */             
/* 351 */             j += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
/*     */             
/* 353 */             for (k += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); j < p_77191_2_ || j >= p_77191_2_ + p_77191_4_ || k < p_77191_3_ || k >= p_77191_3_ + p_77191_4_; k = i1 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5))
/*     */             {
/* 355 */               j = l + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
/*     */             }
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\SpawnerAnimals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */