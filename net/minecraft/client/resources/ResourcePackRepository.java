/*     */ package net.minecraft.client.resources;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenWorking;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.comparator.LastModifiedFileComparator;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackRepository {
/*  41 */   private static final Logger logger = LogManager.getLogger();
/*  42 */   private static final FileFilter resourcePackFilter = new FileFilter()
/*     */     {
/*     */       public boolean accept(File p_accept_1_)
/*     */       {
/*  46 */         boolean flag = (p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip"));
/*  47 */         boolean flag1 = (p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile());
/*  48 */         return (flag || flag1);
/*     */       }
/*     */     };
/*     */   private final File dirResourcepacks;
/*     */   public final IResourcePack rprDefaultResourcePack;
/*     */   private final File dirServerResourcepacks;
/*     */   public final IMetadataSerializer rprMetadataSerializer;
/*     */   private IResourcePack resourcePackInstance;
/*  56 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   private ListenableFuture<Object> field_177322_i;
/*  58 */   private List<Entry> repositoryEntriesAll = Lists.newArrayList();
/*  59 */   public List<Entry> repositoryEntries = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ResourcePackRepository(File dirResourcepacksIn, File dirServerResourcepacksIn, IResourcePack rprDefaultResourcePackIn, IMetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
/*  63 */     this.dirResourcepacks = dirResourcepacksIn;
/*  64 */     this.dirServerResourcepacks = dirServerResourcepacksIn;
/*  65 */     this.rprDefaultResourcePack = rprDefaultResourcePackIn;
/*  66 */     this.rprMetadataSerializer = rprMetadataSerializerIn;
/*  67 */     fixDirResourcepacks();
/*  68 */     updateRepositoryEntriesAll();
/*  69 */     Iterator<String> iterator = settings.resourcePacks.iterator();
/*     */     
/*  71 */     while (iterator.hasNext()) {
/*     */       
/*  73 */       String s = iterator.next();
/*     */       
/*  75 */       for (Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
/*     */         
/*  77 */         if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
/*     */           
/*  79 */           if (resourcepackrepository$entry.func_183027_f() == 1 || settings.field_183018_l.contains(resourcepackrepository$entry.getResourcePackName())) {
/*     */             
/*  81 */             this.repositoryEntries.add(resourcepackrepository$entry);
/*     */             
/*     */             break;
/*     */           } 
/*  85 */           iterator.remove();
/*  86 */           logger.warn("Removed selected resource pack {} because it's no longer compatible", new Object[] { resourcepackrepository$entry.getResourcePackName() });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixDirResourcepacks() {
/*  94 */     if (this.dirResourcepacks.exists()) {
/*     */       
/*  96 */       if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs()))
/*     */       {
/*  98 */         logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
/*     */       }
/*     */     }
/* 101 */     else if (!this.dirResourcepacks.mkdirs()) {
/*     */       
/* 103 */       logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<File> getResourcePackFiles() {
/* 109 */     return this.dirResourcepacks.isDirectory() ? Arrays.<File>asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.<File>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRepositoryEntriesAll() {
/* 114 */     List<Entry> list = Lists.newArrayList();
/*     */     
/* 116 */     for (File file1 : getResourcePackFiles()) {
/*     */       
/* 118 */       Entry resourcepackrepository$entry = new Entry(file1);
/*     */       
/* 120 */       if (!this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
/*     */ 
/*     */         
/*     */         try {
/* 124 */           resourcepackrepository$entry.updateResourcePack();
/* 125 */           list.add(resourcepackrepository$entry);
/*     */         }
/* 127 */         catch (Exception var61) {
/*     */           
/* 129 */           list.remove(resourcepackrepository$entry);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 134 */       int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);
/*     */       
/* 136 */       if (i > -1 && i < this.repositoryEntriesAll.size())
/*     */       {
/* 138 */         list.add(this.repositoryEntriesAll.get(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 143 */     this.repositoryEntriesAll.removeAll(list);
/*     */     
/* 145 */     for (Entry resourcepackrepository$entry1 : this.repositoryEntriesAll)
/*     */     {
/* 147 */       resourcepackrepository$entry1.closeResourcePack();
/*     */     }
/*     */     
/* 150 */     this.repositoryEntriesAll = list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntriesAll() {
/* 155 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntriesAll);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntries() {
/* 160 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntries);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRepositories(List<Entry> p_148527_1_) {
/* 165 */     this.repositoryEntries.clear();
/* 166 */     this.repositoryEntries.addAll(p_148527_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDirResourcepacks() {
/* 171 */     return this.dirResourcepacks;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> downloadResourcePack(String url, String hash) {
/*     */     String s;
/* 178 */     if (hash.matches("^[a-f0-9]{40}$")) {
/*     */       
/* 180 */       s = hash;
/*     */     }
/*     */     else {
/*     */       
/* 184 */       s = "legacy";
/*     */     } 
/*     */     
/* 187 */     final File file1 = new File(this.dirServerResourcepacks, s);
/* 188 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 192 */       func_148529_f();
/*     */       
/* 194 */       if (file1.exists() && hash.length() == 40) {
/*     */         
/*     */         try {
/*     */           
/* 198 */           String s1 = Hashing.sha1().hashBytes(Files.toByteArray(file1)).toString();
/*     */           
/* 200 */           if (s1.equals(hash)) {
/*     */             
/* 202 */             ListenableFuture<Object> listenablefuture2 = setResourcePackInstance(file1);
/* 203 */             ListenableFuture<Object> listenablefuture3 = listenablefuture2;
/* 204 */             return listenablefuture3;
/*     */           } 
/*     */           
/* 207 */           logger.warn("File " + file1 + " had wrong hash (expected " + hash + ", found " + s1 + "). Deleting it.");
/* 208 */           FileUtils.deleteQuietly(file1);
/*     */         }
/* 210 */         catch (IOException ioexception) {
/*     */           
/* 212 */           logger.warn("File " + file1 + " couldn't be hashed. Deleting it.", ioexception);
/* 213 */           FileUtils.deleteQuietly(file1);
/*     */         } 
/*     */       }
/*     */       
/* 217 */       func_183028_i();
/* 218 */       final GuiScreenWorking guiscreenworking = new GuiScreenWorking();
/* 219 */       Map<String, String> map = Minecraft.getSessionInfo();
/* 220 */       final Minecraft minecraft = Minecraft.getMinecraft();
/* 221 */       Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 225 */                 minecraft.displayGuiScreen((GuiScreen)guiscreenworking);
/*     */               }
/*     */             }));
/* 228 */       final SettableFuture<Object> settablefuture = SettableFuture.create();
/* 229 */       this.field_177322_i = HttpUtil.downloadResourcePack(file1, url, map, 52428800, (IProgressUpdate)guiscreenworking, minecraft.getProxy());
/* 230 */       Futures.addCallback(this.field_177322_i, new FutureCallback<Object>()
/*     */           {
/*     */             public void onSuccess(Object p_onSuccess_1_)
/*     */             {
/* 234 */               ResourcePackRepository.this.setResourcePackInstance(file1);
/* 235 */               settablefuture.set(null);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 239 */               settablefuture.setException(p_onFailure_1_);
/*     */             }
/*     */           });
/* 242 */       ListenableFuture<Object> listenablefuture = this.field_177322_i;
/* 243 */       ListenableFuture<Object> listenablefuture11 = listenablefuture;
/* 244 */       return listenablefuture11;
/*     */     }
/*     */     finally {
/*     */       
/* 248 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_183028_i() {
/* 254 */     List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, null));
/* 255 */     Collections.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
/* 256 */     int i = 0;
/*     */     
/* 258 */     for (File file1 : list) {
/*     */       
/* 260 */       if (i++ >= 10) {
/*     */         
/* 262 */         logger.info("Deleting old server resource pack " + file1.getName());
/* 263 */         FileUtils.deleteQuietly(file1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> setResourcePackInstance(File p_177319_1_) {
/* 270 */     this.resourcePackInstance = new FileResourcePack(p_177319_1_);
/* 271 */     return Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IResourcePack getResourcePackInstance() {
/* 279 */     return this.resourcePackInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_148529_f() {
/* 284 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 288 */       if (this.field_177322_i != null)
/*     */       {
/* 290 */         this.field_177322_i.cancel(true);
/*     */       }
/*     */       
/* 293 */       this.field_177322_i = null;
/*     */       
/* 295 */       if (this.resourcePackInstance != null)
/*     */       {
/* 297 */         this.resourcePackInstance = null;
/* 298 */         Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 303 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     private final File resourcePackFile;
/*     */     private IResourcePack reResourcePack;
/*     */     private PackMetadataSection rePackMetadataSection;
/*     */     private BufferedImage texturePackIcon;
/*     */     private ResourceLocation locationTexturePackIcon;
/*     */     
/*     */     private Entry(File resourcePackFileIn) {
/* 317 */       this.resourcePackFile = resourcePackFileIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateResourcePack() throws IOException {
/* 322 */       this.reResourcePack = this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile);
/* 323 */       this.rePackMetadataSection = this.reResourcePack.<PackMetadataSection>getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
/*     */ 
/*     */       
/*     */       try {
/* 327 */         this.texturePackIcon = this.reResourcePack.getPackImage();
/*     */       }
/* 329 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       if (this.texturePackIcon == null)
/*     */       {
/* 336 */         this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
/*     */       }
/*     */       
/* 339 */       closeResourcePack();
/*     */     }
/*     */ 
/*     */     
/*     */     public void bindTexturePackIcon(TextureManager textureManagerIn) {
/* 344 */       if (this.locationTexturePackIcon == null)
/*     */       {
/* 346 */         this.locationTexturePackIcon = textureManagerIn.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
/*     */       }
/*     */       
/* 349 */       textureManagerIn.bindTexture(this.locationTexturePackIcon);
/*     */     }
/*     */ 
/*     */     
/*     */     public void closeResourcePack() {
/* 354 */       if (this.reResourcePack instanceof Closeable)
/*     */       {
/* 356 */         IOUtils.closeQuietly((Closeable)this.reResourcePack);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IResourcePack getResourcePack() {
/* 362 */       return this.reResourcePack;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getResourcePackName() {
/* 367 */       return this.reResourcePack.getPackName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTexturePackDescription() {
/* 372 */       return (this.rePackMetadataSection == null) ? (ChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.getPackDescription().getFormattedText();
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_183027_f() {
/* 377 */       return this.rePackMetadataSection.getPackFormat();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 382 */       return (this == p_equals_1_) ? true : ((p_equals_1_ instanceof Entry) ? toString().equals(p_equals_1_.toString()) : false);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 387 */       return toString().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 392 */       return String.format("%s:%s:%d", new Object[] { this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", Long.valueOf(this.resourcePackFile.lastModified()) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\resources\ResourcePackRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */