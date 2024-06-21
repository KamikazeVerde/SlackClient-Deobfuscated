/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.MapGenBase;
/*     */ 
/*     */ public abstract class MapGenStructure extends MapGenBase {
/*     */   private MapGenStructureData structureData;
/*  23 */   protected Map<Long, StructureStart> structureMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getStructureName();
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/*  32 */     func_143027_a(worldIn);
/*     */     
/*  34 */     if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)))) {
/*     */       
/*  36 */       this.rand.nextInt();
/*     */ 
/*     */       
/*     */       try {
/*  40 */         if (canSpawnStructureAtCoords(chunkX, chunkZ))
/*     */         {
/*  42 */           StructureStart structurestart = getStructureStart(chunkX, chunkZ);
/*  43 */           this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)), structurestart);
/*  44 */           func_143026_a(chunkX, chunkZ, structurestart);
/*     */         }
/*     */       
/*  47 */       } catch (Throwable throwable) {
/*     */         
/*  49 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
/*  50 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
/*  51 */         crashreportcategory.addCrashSectionCallable("Is feature chunk", new Callable<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/*  55 */                 return MapGenStructure.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
/*     */               }
/*     */             });
/*  58 */         crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(chunkX), Integer.valueOf(chunkZ) }));
/*  59 */         crashreportcategory.addCrashSectionCallable("Chunk pos hash", new Callable<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/*  63 */                 return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
/*     */               }
/*     */             });
/*  66 */         crashreportcategory.addCrashSectionCallable("Structure type", new Callable<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/*  70 */                 return MapGenStructure.this.getClass().getCanonicalName();
/*     */               }
/*     */             });
/*  73 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateStructure(World worldIn, Random randomIn, ChunkCoordIntPair chunkCoord) {
/*  80 */     func_143027_a(worldIn);
/*  81 */     int i = (chunkCoord.chunkXPos << 4) + 8;
/*  82 */     int j = (chunkCoord.chunkZPos << 4) + 8;
/*  83 */     boolean flag = false;
/*     */     
/*  85 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/*  87 */       if (structurestart.isSizeableStructure() && structurestart.func_175788_a(chunkCoord) && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15)) {
/*     */         
/*  89 */         structurestart.generateStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
/*  90 */         structurestart.func_175787_b(chunkCoord);
/*  91 */         flag = true;
/*  92 */         func_143026_a(structurestart.getChunkPosX(), structurestart.getChunkPosZ(), structurestart);
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175795_b(BlockPos pos) {
/* 101 */     func_143027_a(this.worldObj);
/* 102 */     return (func_175797_c(pos) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart func_175797_c(BlockPos pos) {
/* 109 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/* 111 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos)) {
/*     */         
/* 113 */         Iterator<StructureComponent> iterator = structurestart.getComponents().iterator();
/*     */ 
/*     */ 
/*     */         
/* 117 */         while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 122 */           StructureComponent structurecomponent = iterator.next();
/*     */           
/* 124 */           if (structurecomponent.getBoundingBox().isVecInside((Vec3i)pos))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 130 */             return structurestart; } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175796_a(World worldIn, BlockPos pos) {
/* 139 */     func_143027_a(worldIn);
/*     */     
/* 141 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/* 143 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos))
/*     */       {
/* 145 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos) {
/* 154 */     this.worldObj = worldIn;
/* 155 */     func_143027_a(worldIn);
/* 156 */     this.rand.setSeed(worldIn.getSeed());
/* 157 */     long i = this.rand.nextLong();
/* 158 */     long j = this.rand.nextLong();
/* 159 */     long k = (pos.getX() >> 4) * i;
/* 160 */     long l = (pos.getZ() >> 4) * j;
/* 161 */     this.rand.setSeed(k ^ l ^ worldIn.getSeed());
/* 162 */     recursiveGenerate(worldIn, pos.getX() >> 4, pos.getZ() >> 4, 0, 0, (ChunkPrimer)null);
/* 163 */     double d0 = Double.MAX_VALUE;
/* 164 */     BlockPos blockpos = null;
/*     */     
/* 166 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/* 168 */       if (structurestart.isSizeableStructure()) {
/*     */         
/* 170 */         StructureComponent structurecomponent = structurestart.getComponents().get(0);
/* 171 */         BlockPos blockpos1 = structurecomponent.getBoundingBoxCenter();
/* 172 */         double d1 = blockpos1.distanceSq((Vec3i)pos);
/*     */         
/* 174 */         if (d1 < d0) {
/*     */           
/* 176 */           d0 = d1;
/* 177 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     if (blockpos != null)
/*     */     {
/* 184 */       return blockpos;
/*     */     }
/*     */ 
/*     */     
/* 188 */     List<BlockPos> list = getCoordList();
/*     */     
/* 190 */     if (list != null) {
/*     */       
/* 192 */       BlockPos blockpos2 = null;
/*     */       
/* 194 */       for (BlockPos blockpos3 : list) {
/*     */         
/* 196 */         double d2 = blockpos3.distanceSq((Vec3i)pos);
/*     */         
/* 198 */         if (d2 < d0) {
/*     */           
/* 200 */           d0 = d2;
/* 201 */           blockpos2 = blockpos3;
/*     */         } 
/*     */       } 
/*     */       
/* 205 */       return blockpos2;
/*     */     } 
/*     */ 
/*     */     
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<BlockPos> getCoordList() {
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_143027_a(World worldIn) {
/* 221 */     if (this.structureData == null) {
/*     */       
/* 223 */       this.structureData = (MapGenStructureData)worldIn.loadItemData(MapGenStructureData.class, getStructureName());
/*     */       
/* 225 */       if (this.structureData == null) {
/*     */         
/* 227 */         this.structureData = new MapGenStructureData(getStructureName());
/* 228 */         worldIn.setItemData(getStructureName(), this.structureData);
/*     */       }
/*     */       else {
/*     */         
/* 232 */         NBTTagCompound nbttagcompound = this.structureData.getTagCompound();
/*     */         
/* 234 */         for (String s : nbttagcompound.getKeySet()) {
/*     */           
/* 236 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 238 */           if (nbtbase.getId() == 10) {
/*     */             
/* 240 */             NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
/*     */             
/* 242 */             if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
/*     */               
/* 244 */               int i = nbttagcompound1.getInteger("ChunkX");
/* 245 */               int j = nbttagcompound1.getInteger("ChunkZ");
/* 246 */               StructureStart structurestart = MapGenStructureIO.getStructureStart(nbttagcompound1, worldIn);
/*     */               
/* 248 */               if (structurestart != null)
/*     */               {
/* 250 */                 this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)), structurestart);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_143026_a(int p_143026_1_, int p_143026_2_, StructureStart start) {
/* 261 */     this.structureData.writeInstance(start.writeStructureComponentsToNBT(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
/* 262 */     this.structureData.markDirty();
/*     */   }
/*     */   
/*     */   protected abstract boolean canSpawnStructureAtCoords(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract StructureStart getStructureStart(int paramInt1, int paramInt2);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\gen\structure\MapGenStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */