/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveFormatOld
/*     */   implements ISaveFormat {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   protected final File savesDirectory;
/*     */ 
/*     */ 
/*     */   
/*     */   public SaveFormatOld(File p_i2147_1_) {
/*  26 */     if (!p_i2147_1_.exists())
/*     */     {
/*  28 */       p_i2147_1_.mkdirs();
/*     */     }
/*     */     
/*  31 */     this.savesDirectory = p_i2147_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  39 */     return "Old Format";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
/*  44 */     List<SaveFormatComparator> list = Lists.newArrayList();
/*     */     
/*  46 */     for (int i = 0; i < 5; i++) {
/*     */       
/*  48 */       String s = "World" + (i + 1);
/*  49 */       WorldInfo worldinfo = getWorldInfo(s);
/*     */       
/*  51 */       if (worldinfo != null)
/*     */       {
/*  53 */         list.add(new SaveFormatComparator(s, "", worldinfo.getLastTimePlayed(), worldinfo.getSizeOnDisk(), worldinfo.getGameType(), false, worldinfo.isHardcoreModeEnabled(), worldinfo.areCommandsAllowed()));
/*     */       }
/*     */     } 
/*     */     
/*  57 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushCache() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo getWorldInfo(String saveName) {
/*  71 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/*  73 */     if (!file1.exists())
/*     */     {
/*  75 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  79 */     File file2 = new File(file1, "level.dat");
/*     */     
/*  81 */     if (file2.exists()) {
/*     */       
/*     */       try {
/*     */         
/*  85 */         NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/*  86 */         NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
/*  87 */         return new WorldInfo(nbttagcompound3);
/*     */       }
/*  89 */       catch (Exception exception1) {
/*     */         
/*  91 */         logger.error("Exception reading " + file2, exception1);
/*     */       } 
/*     */     }
/*     */     
/*  95 */     file2 = new File(file1, "level.dat_old");
/*     */     
/*  97 */     if (file2.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 101 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/* 102 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 103 */         return new WorldInfo(nbttagcompound1);
/*     */       }
/* 105 */       catch (Exception exception) {
/*     */         
/* 107 */         logger.error("Exception reading " + file2, exception);
/*     */       } 
/*     */     }
/*     */     
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renameWorld(String dirName, String newName) {
/* 124 */     File file1 = new File(this.savesDirectory, dirName);
/*     */     
/* 126 */     if (file1.exists()) {
/*     */       
/* 128 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 130 */       if (file2.exists()) {
/*     */         
/*     */         try {
/*     */           
/* 134 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/* 135 */           NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 136 */           nbttagcompound1.setString("LevelName", newName);
/* 137 */           CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file2));
/*     */         }
/* 139 */         catch (Exception exception) {
/*     */           
/* 141 */           exception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_154335_d(String p_154335_1_) {
/* 149 */     File file1 = new File(this.savesDirectory, p_154335_1_);
/*     */     
/* 151 */     if (file1.exists())
/*     */     {
/* 153 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 159 */       file1.mkdir();
/* 160 */       file1.delete();
/* 161 */       return true;
/*     */     }
/* 163 */     catch (Throwable throwable) {
/*     */       
/* 165 */       logger.warn("Couldn't make new level", throwable);
/* 166 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteWorldDirectory(String p_75802_1_) {
/* 177 */     File file1 = new File(this.savesDirectory, p_75802_1_);
/*     */     
/* 179 */     if (!file1.exists())
/*     */     {
/* 181 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 185 */     logger.info("Deleting level " + p_75802_1_);
/*     */     
/* 187 */     for (int i = 1; i <= 5; i++) {
/*     */       
/* 189 */       logger.info("Attempt " + i + "...");
/*     */       
/* 191 */       if (deleteFiles(file1.listFiles())) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 196 */       logger.warn("Unsuccessful in deleting contents.");
/*     */       
/* 198 */       if (i < 5) {
/*     */         
/*     */         try {
/*     */           
/* 202 */           Thread.sleep(500L);
/*     */         }
/* 204 */         catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     return file1.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean deleteFiles(File[] files) {
/* 221 */     for (int i = 0; i < files.length; i++) {
/*     */       
/* 223 */       File file1 = files[i];
/* 224 */       logger.debug("Deleting " + file1);
/*     */       
/* 226 */       if (file1.isDirectory() && !deleteFiles(file1.listFiles())) {
/*     */         
/* 228 */         logger.warn("Couldn't delete directory " + file1);
/* 229 */         return false;
/*     */       } 
/*     */       
/* 232 */       if (!file1.delete()) {
/*     */         
/* 234 */         logger.warn("Couldn't delete file " + file1);
/* 235 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/* 247 */     return new SaveHandler(this.savesDirectory, saveName, storePlayerdata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_154334_a(String saveName) {
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canLoadWorld(String p_90033_1_) {
/* 280 */     File file1 = new File(this.savesDirectory, p_90033_1_);
/* 281 */     return file1.isDirectory();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\storage\SaveFormatOld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */