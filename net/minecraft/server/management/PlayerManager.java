/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S21PacketChunkData;
/*     */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.ChunkPosComparator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PlayerManager
/*     */ {
/*  34 */   private static final Logger pmLogger = LogManager.getLogger();
/*     */   private final WorldServer theWorldServer;
/*  36 */   private final List<EntityPlayerMP> players = Lists.newArrayList();
/*  37 */   private final LongHashMap<PlayerInstance> playerInstances = new LongHashMap();
/*  38 */   private final List<PlayerInstance> playerInstancesToUpdate = Lists.newArrayList();
/*  39 */   private final List<PlayerInstance> playerInstanceList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   private int playerViewRadius;
/*     */ 
/*     */ 
/*     */   
/*     */   private long previousTotalWorldTime;
/*     */ 
/*     */   
/*  50 */   private final int[][] xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
/*  51 */   private final Map<EntityPlayerMP, Set<ChunkCoordIntPair>> mapPlayerPendingEntries = new HashMap<>();
/*     */ 
/*     */   
/*     */   public PlayerManager(WorldServer serverWorld) {
/*  55 */     this.theWorldServer = serverWorld;
/*  56 */     setPlayerViewRadius(serverWorld.getMinecraftServer().getConfigurationManager().getViewDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldServer getWorldServer() {
/*  64 */     return this.theWorldServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerInstances() {
/*  72 */     Set<Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> set = this.mapPlayerPendingEntries.entrySet();
/*  73 */     Iterator<Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> iterator = set.iterator();
/*     */     
/*  75 */     while (iterator.hasNext()) {
/*     */       
/*  77 */       Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>> entry = iterator.next();
/*  78 */       Set<ChunkCoordIntPair> set1 = entry.getValue();
/*     */       
/*  80 */       if (!set1.isEmpty()) {
/*     */         
/*  82 */         EntityPlayerMP entityplayermp = entry.getKey();
/*     */         
/*  84 */         if (entityplayermp.worldObj != this.theWorldServer) {
/*     */           
/*  86 */           iterator.remove();
/*     */           
/*     */           continue;
/*     */         } 
/*  90 */         int i = this.playerViewRadius / 3 + 1;
/*     */         
/*  92 */         if (!Config.isLazyChunkLoading())
/*     */         {
/*  94 */           i = this.playerViewRadius * 2 + 1;
/*     */         }
/*     */         
/*  97 */         for (ChunkCoordIntPair chunkcoordintpair : getNearest(set1, entityplayermp, i)) {
/*     */           
/*  99 */           PlayerInstance playermanager$playerinstance = getPlayerInstance(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos, true);
/* 100 */           playermanager$playerinstance.addPlayer(entityplayermp);
/* 101 */           set1.remove(chunkcoordintpair);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 107 */     long j = this.theWorldServer.getTotalWorldTime();
/*     */     
/* 109 */     if (j - this.previousTotalWorldTime > 8000L) {
/*     */       
/* 111 */       this.previousTotalWorldTime = j;
/*     */       
/* 113 */       for (int k = 0; k < this.playerInstanceList.size(); k++)
/*     */       {
/* 115 */         PlayerInstance playermanager$playerinstance1 = this.playerInstanceList.get(k);
/* 116 */         playermanager$playerinstance1.onUpdate();
/* 117 */         playermanager$playerinstance1.processChunk();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 122 */       for (int l = 0; l < this.playerInstancesToUpdate.size(); l++) {
/*     */         
/* 124 */         PlayerInstance playermanager$playerinstance2 = this.playerInstancesToUpdate.get(l);
/* 125 */         playermanager$playerinstance2.onUpdate();
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     this.playerInstancesToUpdate.clear();
/*     */     
/* 131 */     if (this.players.isEmpty()) {
/*     */       
/* 133 */       WorldProvider worldprovider = this.theWorldServer.provider;
/*     */       
/* 135 */       if (!worldprovider.canRespawnHere())
/*     */       {
/* 137 */         this.theWorldServer.theChunkProviderServer.unloadAllChunks();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInstance(int chunkX, int chunkZ) {
/* 144 */     long i = chunkX + 2147483647L | chunkZ + 2147483647L << 32L;
/* 145 */     return (this.playerInstances.getValueByKey(i) != null);
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
/*     */   private PlayerInstance getPlayerInstance(int chunkX, int chunkZ, boolean createIfAbsent) {
/* 157 */     long i = chunkX + 2147483647L | chunkZ + 2147483647L << 32L;
/* 158 */     PlayerInstance playermanager$playerinstance = (PlayerInstance)this.playerInstances.getValueByKey(i);
/*     */     
/* 160 */     if (playermanager$playerinstance == null && createIfAbsent) {
/*     */       
/* 162 */       playermanager$playerinstance = new PlayerInstance(chunkX, chunkZ);
/* 163 */       this.playerInstances.add(i, playermanager$playerinstance);
/* 164 */       this.playerInstanceList.add(playermanager$playerinstance);
/*     */     } 
/*     */     
/* 167 */     return playermanager$playerinstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markBlockForUpdate(BlockPos pos) {
/* 172 */     int i = pos.getX() >> 4;
/* 173 */     int j = pos.getZ() >> 4;
/* 174 */     PlayerInstance playermanager$playerinstance = getPlayerInstance(i, j, false);
/*     */     
/* 176 */     if (playermanager$playerinstance != null)
/*     */     {
/* 178 */       playermanager$playerinstance.flagChunkForUpdate(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(EntityPlayerMP player) {
/* 189 */     int i = (int)player.posX >> 4;
/* 190 */     int j = (int)player.posZ >> 4;
/* 191 */     player.managedPosX = player.posX;
/* 192 */     player.managedPosZ = player.posZ;
/* 193 */     int k = Math.min(this.playerViewRadius, 8);
/* 194 */     int l = i - k;
/* 195 */     int i1 = i + k;
/* 196 */     int j1 = j - k;
/* 197 */     int k1 = j + k;
/* 198 */     Set<ChunkCoordIntPair> set = getPendingEntriesSafe(player);
/*     */     
/* 200 */     for (int l1 = i - this.playerViewRadius; l1 <= i + this.playerViewRadius; l1++) {
/*     */       
/* 202 */       for (int i2 = j - this.playerViewRadius; i2 <= j + this.playerViewRadius; i2++) {
/*     */         
/* 204 */         if (l1 >= l && l1 <= i1 && i2 >= j1 && i2 <= k1) {
/*     */           
/* 206 */           getPlayerInstance(l1, i2, true).addPlayer(player);
/*     */         }
/*     */         else {
/*     */           
/* 210 */           set.add(new ChunkCoordIntPair(l1, i2));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     this.players.add(player);
/* 216 */     filterChunkLoadQueue(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filterChunkLoadQueue(EntityPlayerMP player) {
/* 224 */     List<ChunkCoordIntPair> list = Lists.newArrayList(player.loadedChunks);
/* 225 */     int i = 0;
/* 226 */     int j = this.playerViewRadius;
/* 227 */     int k = (int)player.posX >> 4;
/* 228 */     int l = (int)player.posZ >> 4;
/* 229 */     int i1 = 0;
/* 230 */     int j1 = 0;
/* 231 */     ChunkCoordIntPair chunkcoordintpair = (getPlayerInstance(k, l, true)).chunkCoords;
/* 232 */     player.loadedChunks.clear();
/*     */     
/* 234 */     if (list.contains(chunkcoordintpair))
/*     */     {
/* 236 */       player.loadedChunks.add(chunkcoordintpair);
/*     */     }
/*     */     
/* 239 */     for (int k1 = 1; k1 <= j * 2; k1++) {
/*     */       
/* 241 */       for (int l1 = 0; l1 < 2; l1++) {
/*     */         
/* 243 */         int[] aint = this.xzDirectionsConst[i++ % 4];
/*     */         
/* 245 */         for (int i2 = 0; i2 < k1; i2++) {
/*     */           
/* 247 */           i1 += aint[0];
/* 248 */           j1 += aint[1];
/* 249 */           chunkcoordintpair = (getPlayerInstance(k + i1, l + j1, true)).chunkCoords;
/*     */           
/* 251 */           if (list.contains(chunkcoordintpair))
/*     */           {
/* 253 */             player.loadedChunks.add(chunkcoordintpair);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     i %= 4;
/*     */     
/* 261 */     for (int j2 = 0; j2 < j * 2; j2++) {
/*     */       
/* 263 */       i1 += this.xzDirectionsConst[i][0];
/* 264 */       j1 += this.xzDirectionsConst[i][1];
/* 265 */       chunkcoordintpair = (getPlayerInstance(k + i1, l + j1, true)).chunkCoords;
/*     */       
/* 267 */       if (list.contains(chunkcoordintpair))
/*     */       {
/* 269 */         player.loadedChunks.add(chunkcoordintpair);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(EntityPlayerMP player) {
/* 281 */     this.mapPlayerPendingEntries.remove(player);
/* 282 */     int i = (int)player.managedPosX >> 4;
/* 283 */     int j = (int)player.managedPosZ >> 4;
/*     */     
/* 285 */     for (int k = i - this.playerViewRadius; k <= i + this.playerViewRadius; k++) {
/*     */       
/* 287 */       for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/*     */         
/* 289 */         PlayerInstance playermanager$playerinstance = getPlayerInstance(k, l, false);
/*     */         
/* 291 */         if (playermanager$playerinstance != null)
/*     */         {
/* 293 */           playermanager$playerinstance.removePlayer(player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     this.players.remove(player);
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
/*     */   
/*     */   private boolean overlaps(int x1, int z1, int x2, int z2, int radius) {
/* 313 */     int i = x1 - x2;
/* 314 */     int j = z1 - z2;
/* 315 */     return (i >= -radius && i <= radius) ? ((j >= -radius && j <= radius)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMountedMovingPlayer(EntityPlayerMP player) {
/* 325 */     int i = (int)player.posX >> 4;
/* 326 */     int j = (int)player.posZ >> 4;
/* 327 */     double d0 = player.managedPosX - player.posX;
/* 328 */     double d1 = player.managedPosZ - player.posZ;
/* 329 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 331 */     if (d2 >= 64.0D) {
/*     */       
/* 333 */       int k = (int)player.managedPosX >> 4;
/* 334 */       int l = (int)player.managedPosZ >> 4;
/* 335 */       int i1 = this.playerViewRadius;
/* 336 */       int j1 = i - k;
/* 337 */       int k1 = j - l;
/*     */       
/* 339 */       if (j1 != 0 || k1 != 0) {
/*     */         
/* 341 */         Set<ChunkCoordIntPair> set = getPendingEntriesSafe(player);
/*     */         
/* 343 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/*     */           
/* 345 */           for (int i2 = j - i1; i2 <= j + i1; i2++) {
/*     */             
/* 347 */             if (!overlaps(l1, i2, k, l, i1))
/*     */             {
/* 349 */               if (Config.isLazyChunkLoading()) {
/*     */                 
/* 351 */                 set.add(new ChunkCoordIntPair(l1, i2));
/*     */               }
/*     */               else {
/*     */                 
/* 355 */                 getPlayerInstance(l1, i2, true).addPlayer(player);
/*     */               } 
/*     */             }
/*     */             
/* 359 */             if (!overlaps(l1 - j1, i2 - k1, i, j, i1)) {
/*     */               
/* 361 */               set.remove(new ChunkCoordIntPair(l1 - j1, i2 - k1));
/* 362 */               PlayerInstance playermanager$playerinstance = getPlayerInstance(l1 - j1, i2 - k1, false);
/*     */               
/* 364 */               if (playermanager$playerinstance != null)
/*     */               {
/* 366 */                 playermanager$playerinstance.removePlayer(player);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 372 */         filterChunkLoadQueue(player);
/* 373 */         player.managedPosX = player.posX;
/* 374 */         player.managedPosZ = player.posZ;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerWatchingChunk(EntityPlayerMP player, int chunkX, int chunkZ) {
/* 381 */     PlayerInstance playermanager$playerinstance = getPlayerInstance(chunkX, chunkZ, false);
/* 382 */     return (playermanager$playerinstance != null && playermanager$playerinstance.playersWatchingChunk.contains(player) && !player.loadedChunks.contains(playermanager$playerinstance.chunkCoords));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerViewRadius(int radius) {
/* 387 */     radius = MathHelper.clamp_int(radius, 3, 64);
/*     */     
/* 389 */     if (radius != this.playerViewRadius) {
/*     */       
/* 391 */       int i = radius - this.playerViewRadius;
/*     */       
/* 393 */       for (EntityPlayerMP entityplayermp : Lists.newArrayList(this.players)) {
/*     */         
/* 395 */         int j = (int)entityplayermp.posX >> 4;
/* 396 */         int k = (int)entityplayermp.posZ >> 4;
/* 397 */         Set<ChunkCoordIntPair> set = getPendingEntriesSafe(entityplayermp);
/*     */         
/* 399 */         if (i > 0) {
/*     */           
/* 401 */           for (int j1 = j - radius; j1 <= j + radius; j1++) {
/*     */             
/* 403 */             for (int k1 = k - radius; k1 <= k + radius; k1++) {
/*     */               
/* 405 */               if (Config.isLazyChunkLoading()) {
/*     */                 
/* 407 */                 set.add(new ChunkCoordIntPair(j1, k1));
/*     */               }
/*     */               else {
/*     */                 
/* 411 */                 PlayerInstance playermanager$playerinstance1 = getPlayerInstance(j1, k1, true);
/*     */                 
/* 413 */                 if (!playermanager$playerinstance1.playersWatchingChunk.contains(entityplayermp))
/*     */                 {
/* 415 */                   playermanager$playerinstance1.addPlayer(entityplayermp);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 423 */         for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/*     */           
/* 425 */           for (int i1 = k - this.playerViewRadius; i1 <= k + this.playerViewRadius; i1++) {
/*     */             
/* 427 */             if (!overlaps(l, i1, j, k, radius)) {
/*     */               
/* 429 */               set.remove(new ChunkCoordIntPair(l, i1));
/* 430 */               PlayerInstance playermanager$playerinstance = getPlayerInstance(l, i1, true);
/*     */               
/* 432 */               if (playermanager$playerinstance != null)
/*     */               {
/* 434 */                 playermanager$playerinstance.removePlayer(entityplayermp);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 442 */       this.playerViewRadius = radius;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFurthestViewableBlock(int distance) {
/* 451 */     return distance * 16 - 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PriorityQueue<ChunkCoordIntPair> getNearest(Set<ChunkCoordIntPair> p_getNearest_1_, EntityPlayerMP p_getNearest_2_, int p_getNearest_3_) {
/*     */     float f;
/* 458 */     for (f = p_getNearest_2_.rotationYaw + 90.0F; f <= -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     while (f > 180.0F)
/*     */     {
/* 465 */       f -= 360.0F;
/*     */     }
/*     */     
/* 468 */     double d0 = f * 0.017453292519943295D;
/* 469 */     double d1 = p_getNearest_2_.rotationPitch;
/* 470 */     double d2 = d1 * 0.017453292519943295D;
/* 471 */     ChunkPosComparator chunkposcomparator = new ChunkPosComparator(p_getNearest_2_.chunkCoordX, p_getNearest_2_.chunkCoordZ, d0, d2);
/* 472 */     Comparator<ChunkCoordIntPair> comparator = Collections.reverseOrder((Comparator<ChunkCoordIntPair>)chunkposcomparator);
/* 473 */     PriorityQueue<ChunkCoordIntPair> priorityqueue = new PriorityQueue<>(p_getNearest_3_, comparator);
/*     */     
/* 475 */     for (ChunkCoordIntPair chunkcoordintpair : p_getNearest_1_) {
/*     */       
/* 477 */       if (priorityqueue.size() < p_getNearest_3_) {
/*     */         
/* 479 */         priorityqueue.add(chunkcoordintpair);
/*     */         
/*     */         continue;
/*     */       } 
/* 483 */       ChunkCoordIntPair chunkcoordintpair1 = priorityqueue.peek();
/*     */       
/* 485 */       if (chunkposcomparator.compare(chunkcoordintpair, chunkcoordintpair1) < 0) {
/*     */         
/* 487 */         priorityqueue.remove();
/* 488 */         priorityqueue.add(chunkcoordintpair);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 493 */     return priorityqueue;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ChunkCoordIntPair> getPendingEntriesSafe(EntityPlayerMP p_getPendingEntriesSafe_1_) {
/* 498 */     Set<ChunkCoordIntPair> set = this.mapPlayerPendingEntries.get(p_getPendingEntriesSafe_1_);
/*     */     
/* 500 */     if (set != null)
/*     */     {
/* 502 */       return set;
/*     */     }
/*     */ 
/*     */     
/* 506 */     int i = Math.min(this.playerViewRadius, 8);
/* 507 */     int j = this.playerViewRadius * 2 + 1;
/* 508 */     int k = i * 2 + 1;
/* 509 */     int l = j * j - k * k;
/* 510 */     l = Math.max(l, 16);
/* 511 */     HashSet<ChunkCoordIntPair> hashset = new HashSet(l);
/* 512 */     this.mapPlayerPendingEntries.put(p_getPendingEntriesSafe_1_, hashset);
/* 513 */     return hashset;
/*     */   }
/*     */ 
/*     */   
/*     */   class PlayerInstance
/*     */   {
/* 519 */     private final List<EntityPlayerMP> playersWatchingChunk = Lists.newArrayList();
/*     */     private final ChunkCoordIntPair chunkCoords;
/* 521 */     private short[] locationOfBlockChange = new short[64];
/*     */     
/*     */     private int numBlocksToUpdate;
/*     */     private int flagsYAreasToUpdate;
/*     */     private long previousWorldTime;
/*     */     
/*     */     public PlayerInstance(int chunkX, int chunkZ) {
/* 528 */       this.chunkCoords = new ChunkCoordIntPair(chunkX, chunkZ);
/* 529 */       (PlayerManager.this.getWorldServer()).theChunkProviderServer.loadChunk(chunkX, chunkZ);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addPlayer(EntityPlayerMP player) {
/* 534 */       if (this.playersWatchingChunk.contains(player)) {
/*     */         
/* 536 */         PlayerManager.pmLogger.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { player, Integer.valueOf(this.chunkCoords.chunkXPos), Integer.valueOf(this.chunkCoords.chunkZPos) });
/*     */       }
/*     */       else {
/*     */         
/* 540 */         if (this.playersWatchingChunk.isEmpty())
/*     */         {
/* 542 */           this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/*     */         }
/*     */         
/* 545 */         this.playersWatchingChunk.add(player);
/* 546 */         player.loadedChunks.add(this.chunkCoords);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void removePlayer(EntityPlayerMP player) {
/* 552 */       if (this.playersWatchingChunk.contains(player)) {
/*     */         
/* 554 */         Chunk chunk = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
/*     */         
/* 556 */         if (chunk.isPopulated())
/*     */         {
/* 558 */           player.playerNetServerHandler.sendPacket((Packet)new S21PacketChunkData(chunk, true, 0));
/*     */         }
/*     */         
/* 561 */         this.playersWatchingChunk.remove(player);
/* 562 */         player.loadedChunks.remove(this.chunkCoords);
/*     */         
/* 564 */         if (this.playersWatchingChunk.isEmpty()) {
/*     */           
/* 566 */           long i = this.chunkCoords.chunkXPos + 2147483647L | this.chunkCoords.chunkZPos + 2147483647L << 32L;
/* 567 */           increaseInhabitedTime(chunk);
/* 568 */           PlayerManager.this.playerInstances.remove(i);
/* 569 */           PlayerManager.this.playerInstanceList.remove(this);
/*     */           
/* 571 */           if (this.numBlocksToUpdate > 0)
/*     */           {
/* 573 */             PlayerManager.this.playerInstancesToUpdate.remove(this);
/*     */           }
/*     */           
/* 576 */           (PlayerManager.this.getWorldServer()).theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void processChunk() {
/* 583 */       increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
/*     */     }
/*     */ 
/*     */     
/*     */     private void increaseInhabitedTime(Chunk theChunk) {
/* 588 */       theChunk.setInhabitedTime(theChunk.getInhabitedTime() + PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime);
/* 589 */       this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/*     */     }
/*     */ 
/*     */     
/*     */     public void flagChunkForUpdate(int x, int y, int z) {
/* 594 */       if (this.numBlocksToUpdate == 0)
/*     */       {
/* 596 */         PlayerManager.this.playerInstancesToUpdate.add(this);
/*     */       }
/*     */       
/* 599 */       this.flagsYAreasToUpdate |= 1 << y >> 4;
/*     */       
/* 601 */       if (this.numBlocksToUpdate < 64) {
/*     */         
/* 603 */         short short1 = (short)(x << 12 | z << 8 | y);
/*     */         
/* 605 */         for (int i = 0; i < this.numBlocksToUpdate; i++) {
/*     */           
/* 607 */           if (this.locationOfBlockChange[i] == short1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 613 */         this.locationOfBlockChange[this.numBlocksToUpdate++] = short1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void sendToAllPlayersWatchingChunk(Packet thePacket) {
/* 619 */       for (int i = 0; i < this.playersWatchingChunk.size(); i++) {
/*     */         
/* 621 */         EntityPlayerMP entityplayermp = this.playersWatchingChunk.get(i);
/*     */         
/* 623 */         if (!entityplayermp.loadedChunks.contains(this.chunkCoords))
/*     */         {
/* 625 */           entityplayermp.playerNetServerHandler.sendPacket(thePacket);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdate() {
/* 632 */       if (this.numBlocksToUpdate != 0) {
/*     */         
/* 634 */         if (this.numBlocksToUpdate == 1) {
/*     */           
/* 636 */           int k1 = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
/* 637 */           int i2 = this.locationOfBlockChange[0] & 0xFF;
/* 638 */           int k2 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
/* 639 */           BlockPos blockpos = new BlockPos(k1, i2, k2);
/* 640 */           sendToAllPlayersWatchingChunk((Packet)new S23PacketBlockChange((World)PlayerManager.this.theWorldServer, blockpos));
/*     */           
/* 642 */           if (PlayerManager.this.theWorldServer.getBlockState(blockpos).getBlock().hasTileEntity())
/*     */           {
/* 644 */             sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos));
/*     */           }
/*     */         }
/* 647 */         else if (this.numBlocksToUpdate != 64) {
/*     */           
/* 649 */           sendToAllPlayersWatchingChunk((Packet)new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)));
/*     */           
/* 651 */           for (int j1 = 0; j1 < this.numBlocksToUpdate; j1++)
/*     */           {
/* 653 */             int l1 = (this.locationOfBlockChange[j1] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
/* 654 */             int j2 = this.locationOfBlockChange[j1] & 0xFF;
/* 655 */             int l2 = (this.locationOfBlockChange[j1] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
/* 656 */             BlockPos blockpos1 = new BlockPos(l1, j2, l2);
/*     */             
/* 658 */             if (PlayerManager.this.theWorldServer.getBlockState(blockpos1).getBlock().hasTileEntity())
/*     */             {
/* 660 */               sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos1));
/*     */             }
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 666 */           int i = this.chunkCoords.chunkXPos * 16;
/* 667 */           int j = this.chunkCoords.chunkZPos * 16;
/* 668 */           sendToAllPlayersWatchingChunk((Packet)new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos), false, this.flagsYAreasToUpdate));
/*     */           
/* 670 */           for (int k = 0; k < 16; k++) {
/*     */             
/* 672 */             if ((this.flagsYAreasToUpdate & 1 << k) != 0) {
/*     */               
/* 674 */               int l = k << 4;
/* 675 */               List<TileEntity> list = PlayerManager.this.theWorldServer.getTileEntitiesIn(i, l, j, i + 16, l + 16, j + 16);
/*     */               
/* 677 */               for (int i1 = 0; i1 < list.size(); i1++)
/*     */               {
/* 679 */                 sendTileToAllPlayersWatchingChunk(list.get(i1));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 685 */         this.numBlocksToUpdate = 0;
/* 686 */         this.flagsYAreasToUpdate = 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void sendTileToAllPlayersWatchingChunk(TileEntity theTileEntity) {
/* 692 */       if (theTileEntity != null) {
/*     */         
/* 694 */         Packet packet = theTileEntity.getDescriptionPacket();
/*     */         
/* 696 */         if (packet != null)
/*     */         {
/* 698 */           sendToAllPlayersWatchingChunk(packet);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\server\management\PlayerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */