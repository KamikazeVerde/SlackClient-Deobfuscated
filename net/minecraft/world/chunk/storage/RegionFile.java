/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.List;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class RegionFile
/*     */ {
/*  20 */   private static final byte[] emptySector = new byte[4096];
/*     */   private final File fileName;
/*     */   private RandomAccessFile dataFile;
/*  23 */   private final int[] offsets = new int[1024];
/*  24 */   private final int[] chunkTimestamps = new int[1024];
/*     */   
/*     */   private List<Boolean> sectorFree;
/*     */   
/*     */   private int sizeDelta;
/*     */   
/*     */   private long lastModified;
/*     */   
/*     */   public RegionFile(File fileNameIn) {
/*  33 */     this.fileName = fileNameIn;
/*  34 */     this.sizeDelta = 0;
/*     */ 
/*     */     
/*     */     try {
/*  38 */       if (fileNameIn.exists())
/*     */       {
/*  40 */         this.lastModified = fileNameIn.lastModified();
/*     */       }
/*     */       
/*  43 */       this.dataFile = new RandomAccessFile(fileNameIn, "rw");
/*     */       
/*  45 */       if (this.dataFile.length() < 4096L) {
/*     */         
/*  47 */         for (int i = 0; i < 1024; i++)
/*     */         {
/*  49 */           this.dataFile.writeInt(0);
/*     */         }
/*     */         
/*  52 */         for (int i1 = 0; i1 < 1024; i1++)
/*     */         {
/*  54 */           this.dataFile.writeInt(0);
/*     */         }
/*     */         
/*  57 */         this.sizeDelta += 8192;
/*     */       } 
/*     */       
/*  60 */       if ((this.dataFile.length() & 0xFFFL) != 0L)
/*     */       {
/*  62 */         for (int j1 = 0; j1 < (this.dataFile.length() & 0xFFFL); j1++)
/*     */         {
/*  64 */           this.dataFile.write(0);
/*     */         }
/*     */       }
/*     */       
/*  68 */       int k1 = (int)this.dataFile.length() / 4096;
/*  69 */       this.sectorFree = Lists.newArrayListWithCapacity(k1);
/*     */       
/*  71 */       for (int j = 0; j < k1; j++)
/*     */       {
/*  73 */         this.sectorFree.add(Boolean.TRUE);
/*     */       }
/*     */       
/*  76 */       this.sectorFree.set(0, Boolean.FALSE);
/*  77 */       this.sectorFree.set(1, Boolean.FALSE);
/*  78 */       this.dataFile.seek(0L);
/*     */       
/*  80 */       for (int l1 = 0; l1 < 1024; l1++) {
/*     */         
/*  82 */         int k = this.dataFile.readInt();
/*  83 */         this.offsets[l1] = k;
/*     */         
/*  85 */         if (k != 0 && (k >> 8) + (k & 0xFF) <= this.sectorFree.size())
/*     */         {
/*  87 */           for (int l = 0; l < (k & 0xFF); l++)
/*     */           {
/*  89 */             this.sectorFree.set((k >> 8) + l, Boolean.FALSE);
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/*  94 */       for (int i2 = 0; i2 < 1024; i2++)
/*     */       {
/*  96 */         int j2 = this.dataFile.readInt();
/*  97 */         this.chunkTimestamps[i2] = j2;
/*     */       }
/*     */     
/* 100 */     } catch (IOException ioexception) {
/*     */       
/* 102 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DataInputStream getChunkDataInputStream(int x, int z) {
/* 114 */     if (outOfBounds(x, z))
/*     */     {
/* 116 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 122 */       int i = getOffset(x, z);
/*     */       
/* 124 */       if (i == 0)
/*     */       {
/* 126 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 130 */       int j = i >> 8;
/* 131 */       int k = i & 0xFF;
/*     */       
/* 133 */       if (j + k > this.sectorFree.size())
/*     */       {
/* 135 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 139 */       this.dataFile.seek((j * 4096));
/* 140 */       int l = this.dataFile.readInt();
/*     */       
/* 142 */       if (l > 4096 * k)
/*     */       {
/* 144 */         return null;
/*     */       }
/* 146 */       if (l <= 0)
/*     */       {
/* 148 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 152 */       byte b0 = this.dataFile.readByte();
/*     */       
/* 154 */       if (b0 == 1) {
/*     */         
/* 156 */         byte[] abyte1 = new byte[l - 1];
/* 157 */         this.dataFile.read(abyte1);
/* 158 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
/*     */       } 
/* 160 */       if (b0 == 2) {
/*     */         
/* 162 */         byte[] abyte = new byte[l - 1];
/* 163 */         this.dataFile.read(abyte);
/* 164 */         return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
/*     */       } 
/*     */ 
/*     */       
/* 168 */       return null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 174 */     catch (IOException var9) {
/*     */       
/* 176 */       return null;
/*     */     } 
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
/*     */   public DataOutputStream getChunkDataOutputStream(int x, int z) {
/* 189 */     return outOfBounds(x, z) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z)));
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
/*     */   
/*     */   protected synchronized void write(int x, int z, byte[] data, int length) {
/*     */     try {
/* 204 */       int i = getOffset(x, z);
/* 205 */       int j = i >> 8;
/* 206 */       int k = i & 0xFF;
/* 207 */       int l = (length + 5) / 4096 + 1;
/*     */       
/* 209 */       if (l >= 256) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 214 */       if (j != 0 && k == l) {
/*     */         
/* 216 */         write(j, data, length);
/*     */       }
/*     */       else {
/*     */         
/* 220 */         for (int i1 = 0; i1 < k; i1++)
/*     */         {
/* 222 */           this.sectorFree.set(j + i1, Boolean.TRUE);
/*     */         }
/*     */         
/* 225 */         int l1 = this.sectorFree.indexOf(Boolean.TRUE);
/* 226 */         int j1 = 0;
/*     */         
/* 228 */         if (l1 != -1)
/*     */         {
/* 230 */           for (int k1 = l1; k1 < this.sectorFree.size(); k1++) {
/*     */             
/* 232 */             if (j1 != 0) {
/*     */               
/* 234 */               if (((Boolean)this.sectorFree.get(k1)).booleanValue())
/*     */               {
/* 236 */                 j1++;
/*     */               }
/*     */               else
/*     */               {
/* 240 */                 j1 = 0;
/*     */               }
/*     */             
/* 243 */             } else if (((Boolean)this.sectorFree.get(k1)).booleanValue()) {
/*     */               
/* 245 */               l1 = k1;
/* 246 */               j1 = 1;
/*     */             } 
/*     */             
/* 249 */             if (j1 >= l) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 256 */         if (j1 >= l) {
/*     */           
/* 258 */           j = l1;
/* 259 */           setOffset(x, z, l1 << 8 | l);
/*     */           
/* 261 */           for (int j2 = 0; j2 < l; j2++)
/*     */           {
/* 263 */             this.sectorFree.set(j + j2, Boolean.FALSE);
/*     */           }
/*     */           
/* 266 */           write(j, data, length);
/*     */         }
/*     */         else {
/*     */           
/* 270 */           this.dataFile.seek(this.dataFile.length());
/* 271 */           j = this.sectorFree.size();
/*     */           
/* 273 */           for (int i2 = 0; i2 < l; i2++) {
/*     */             
/* 275 */             this.dataFile.write(emptySector);
/* 276 */             this.sectorFree.add(Boolean.FALSE);
/*     */           } 
/*     */           
/* 279 */           this.sizeDelta += 4096 * l;
/* 280 */           write(j, data, length);
/* 281 */           setOffset(x, z, j << 8 | l);
/*     */         } 
/*     */       } 
/*     */       
/* 285 */       setChunkTimestamp(x, z, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
/*     */     }
/* 287 */     catch (IOException ioexception) {
/*     */       
/* 289 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(int sectorNumber, byte[] data, int length) throws IOException {
/* 298 */     this.dataFile.seek((sectorNumber * 4096));
/* 299 */     this.dataFile.writeInt(length + 1);
/* 300 */     this.dataFile.writeByte(2);
/* 301 */     this.dataFile.write(data, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean outOfBounds(int x, int z) {
/* 312 */     return (x < 0 || x >= 32 || z < 0 || z >= 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getOffset(int x, int z) {
/* 323 */     return this.offsets[x + z * 32];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChunkSaved(int x, int z) {
/* 334 */     return (getOffset(x, z) != 0);
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
/*     */   private void setOffset(int x, int z, int offset) throws IOException {
/* 346 */     this.offsets[x + z * 32] = offset;
/* 347 */     this.dataFile.seek(((x + z * 32) * 4));
/* 348 */     this.dataFile.writeInt(offset);
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
/*     */   private void setChunkTimestamp(int x, int z, int timestamp) throws IOException {
/* 360 */     this.chunkTimestamps[x + z * 32] = timestamp;
/* 361 */     this.dataFile.seek((4096 + (x + z * 32) * 4));
/* 362 */     this.dataFile.writeInt(timestamp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 370 */     if (this.dataFile != null)
/*     */     {
/* 372 */       this.dataFile.close();
/*     */     }
/*     */   }
/*     */   
/*     */   class ChunkBuffer
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     private int chunkX;
/*     */     private int chunkZ;
/*     */     
/*     */     public ChunkBuffer(int x, int z) {
/* 383 */       super(8096);
/* 384 */       this.chunkX = x;
/* 385 */       this.chunkZ = z;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 390 */       RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\chunk\storage\RegionFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */