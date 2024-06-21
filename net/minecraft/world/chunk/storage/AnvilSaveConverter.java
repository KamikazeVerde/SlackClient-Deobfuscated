/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.SaveFormatOld;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilSaveConverter extends SaveFormatOld {
/*  30 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   public AnvilSaveConverter(File p_i2144_1_) {
/*  34 */     super(p_i2144_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  42 */     return "Anvil";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
/*  47 */     if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
/*     */       
/*  49 */       List<SaveFormatComparator> list = Lists.newArrayList();
/*  50 */       File[] afile = this.savesDirectory.listFiles();
/*     */       
/*  52 */       for (File file1 : afile) {
/*     */         
/*  54 */         if (file1.isDirectory()) {
/*     */           
/*  56 */           String s = file1.getName();
/*  57 */           WorldInfo worldinfo = getWorldInfo(s);
/*     */           
/*  59 */           if (worldinfo != null && (worldinfo.getSaveVersion() == 19132 || worldinfo.getSaveVersion() == 19133)) {
/*     */             
/*  61 */             boolean flag = (worldinfo.getSaveVersion() != getSaveVersion());
/*  62 */             String s1 = worldinfo.getWorldName();
/*     */             
/*  64 */             if (StringUtils.isEmpty(s1))
/*     */             {
/*  66 */               s1 = s;
/*     */             }
/*     */             
/*  69 */             long i = 0L;
/*  70 */             list.add(new SaveFormatComparator(s, s1, worldinfo.getLastTimePlayed(), i, worldinfo.getGameType(), flag, worldinfo.isHardcoreModeEnabled(), worldinfo.areCommandsAllowed()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  75 */       return list;
/*     */     } 
/*     */ 
/*     */     
/*  79 */     throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSaveVersion() {
/*  85 */     return 19133;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushCache() {
/*  90 */     RegionFileCache.clearRegionFileReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/*  98 */     return (ISaveHandler)new AnvilSaveHandler(this.savesDirectory, saveName, storePlayerdata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_154334_a(String saveName) {
/* 103 */     WorldInfo worldinfo = getWorldInfo(saveName);
/* 104 */     return (worldinfo != null && worldinfo.getSaveVersion() == 19132);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 114 */     WorldInfo worldinfo = getWorldInfo(saveName);
/* 115 */     return (worldinfo != null && worldinfo.getSaveVersion() != getSaveVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 125 */     progressCallback.setLoadingProgress(0);
/* 126 */     List<File> list = Lists.newArrayList();
/* 127 */     List<File> list1 = Lists.newArrayList();
/* 128 */     List<File> list2 = Lists.newArrayList();
/* 129 */     File file1 = new File(this.savesDirectory, filename);
/* 130 */     File file2 = new File(file1, "DIM-1");
/* 131 */     File file3 = new File(file1, "DIM1");
/* 132 */     logger.info("Scanning folders...");
/* 133 */     addRegionFilesToCollection(file1, list);
/*     */     
/* 135 */     if (file2.exists())
/*     */     {
/* 137 */       addRegionFilesToCollection(file2, list1);
/*     */     }
/*     */     
/* 140 */     if (file3.exists())
/*     */     {
/* 142 */       addRegionFilesToCollection(file3, list2);
/*     */     }
/*     */     
/* 145 */     int i = list.size() + list1.size() + list2.size();
/* 146 */     logger.info("Total conversion count is " + i);
/* 147 */     WorldInfo worldinfo = getWorldInfo(filename);
/* 148 */     WorldChunkManager worldchunkmanager = null;
/*     */     
/* 150 */     if (worldinfo.getTerrainType() == WorldType.FLAT) {
/*     */       
/* 152 */       WorldChunkManagerHell worldChunkManagerHell = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
/*     */     }
/*     */     else {
/*     */       
/* 156 */       worldchunkmanager = new WorldChunkManager(worldinfo.getSeed(), worldinfo.getTerrainType(), worldinfo.getGeneratorOptions());
/*     */     } 
/*     */     
/* 159 */     convertFile(new File(file1, "region"), list, worldchunkmanager, 0, i, progressCallback);
/* 160 */     convertFile(new File(file2, "region"), list1, (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F), list.size(), i, progressCallback);
/* 161 */     convertFile(new File(file3, "region"), list2, (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F), list.size() + list1.size(), i, progressCallback);
/* 162 */     worldinfo.setSaveVersion(19133);
/*     */     
/* 164 */     if (worldinfo.getTerrainType() == WorldType.DEFAULT_1_1)
/*     */     {
/* 166 */       worldinfo.setTerrainType(WorldType.DEFAULT);
/*     */     }
/*     */     
/* 169 */     createFile(filename);
/* 170 */     ISaveHandler isavehandler = getSaveLoader(filename, false);
/* 171 */     isavehandler.saveWorldInfo(worldinfo);
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFile(String filename) {
/* 182 */     File file1 = new File(this.savesDirectory, filename);
/*     */     
/* 184 */     if (!file1.exists()) {
/*     */       
/* 186 */       logger.warn("Unable to create level.dat_mcr backup");
/*     */     }
/*     */     else {
/*     */       
/* 190 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 192 */       if (!file2.exists()) {
/*     */         
/* 194 */         logger.warn("Unable to create level.dat_mcr backup");
/*     */       }
/*     */       else {
/*     */         
/* 198 */         File file3 = new File(file1, "level.dat_mcr");
/*     */         
/* 200 */         if (!file2.renameTo(file3))
/*     */         {
/* 202 */           logger.warn("Unable to create level.dat_mcr backup");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void convertFile(File p_75813_1_, Iterable<File> p_75813_2_, WorldChunkManager p_75813_3_, int p_75813_4_, int p_75813_5_, IProgressUpdate p_75813_6_) {
/* 210 */     for (File file1 : p_75813_2_) {
/*     */       
/* 212 */       convertChunks(p_75813_1_, file1, p_75813_3_, p_75813_4_, p_75813_5_, p_75813_6_);
/* 213 */       p_75813_4_++;
/* 214 */       int i = (int)Math.round(100.0D * p_75813_4_ / p_75813_5_);
/* 215 */       p_75813_6_.setLoadingProgress(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertChunks(File p_75811_1_, File p_75811_2_, WorldChunkManager p_75811_3_, int p_75811_4_, int p_75811_5_, IProgressUpdate progressCallback) {
/*     */     try {
/* 226 */       String s = p_75811_2_.getName();
/* 227 */       RegionFile regionfile = new RegionFile(p_75811_2_);
/* 228 */       RegionFile regionfile1 = new RegionFile(new File(p_75811_1_, s.substring(0, s.length() - ".mcr".length()) + ".mca"));
/*     */       
/* 230 */       for (int i = 0; i < 32; i++) {
/*     */         
/* 232 */         for (int j = 0; j < 32; j++) {
/*     */           
/* 234 */           if (regionfile.isChunkSaved(i, j) && !regionfile1.isChunkSaved(i, j)) {
/*     */             
/* 236 */             DataInputStream datainputstream = regionfile.getChunkDataInputStream(i, j);
/*     */             
/* 238 */             if (datainputstream == null) {
/*     */               
/* 240 */               logger.warn("Failed to fetch input stream");
/*     */             }
/*     */             else {
/*     */               
/* 244 */               NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 245 */               datainputstream.close();
/* 246 */               NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Level");
/* 247 */               ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = ChunkLoader.load(nbttagcompound1);
/* 248 */               NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 249 */               NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 250 */               nbttagcompound2.setTag("Level", (NBTBase)nbttagcompound3);
/* 251 */               ChunkLoader.convertToAnvilFormat(chunkloader$anvilconverterdata, nbttagcompound3, p_75811_3_);
/* 252 */               DataOutputStream dataoutputstream = regionfile1.getChunkDataOutputStream(i, j);
/* 253 */               CompressedStreamTools.write(nbttagcompound2, dataoutputstream);
/* 254 */               dataoutputstream.close();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 259 */         int k = (int)Math.round(100.0D * (p_75811_4_ * 1024) / (p_75811_5_ * 1024));
/* 260 */         int l = (int)Math.round(100.0D * ((i + 1) * 32 + p_75811_4_ * 1024) / (p_75811_5_ * 1024));
/*     */         
/* 262 */         if (l > k)
/*     */         {
/* 264 */           progressCallback.setLoadingProgress(l);
/*     */         }
/*     */       } 
/*     */       
/* 268 */       regionfile.close();
/* 269 */       regionfile1.close();
/*     */     }
/* 271 */     catch (IOException ioexception) {
/*     */       
/* 273 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRegionFilesToCollection(File worldDir, Collection<File> collection) {
/* 282 */     File file1 = new File(worldDir, "region");
/* 283 */     File[] afile = file1.listFiles(new FilenameFilter()
/*     */         {
/*     */           public boolean accept(File p_accept_1_, String p_accept_2_)
/*     */           {
/* 287 */             return p_accept_2_.endsWith(".mcr");
/*     */           }
/*     */         });
/*     */     
/* 291 */     if (afile != null)
/*     */     {
/* 293 */       Collections.addAll(collection, afile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\chunk\storage\AnvilSaveConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */