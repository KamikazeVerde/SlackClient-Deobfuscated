/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import cc.slack.events.impl.player.WorldEvent;
/*     */ import cc.slack.utils.player.BlinkUtil;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecart;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.particle.EntityFirework;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.MapStorage;
/*     */ import net.minecraft.world.storage.SaveDataMemoryStorage;
/*     */ import net.minecraft.world.storage.SaveHandlerMP;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.override.PlayerControllerOF;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class WorldClient extends World {
/*     */   private NetHandlerPlayClient sendQueue;
/*  52 */   private final Set<Entity> entityList = Sets.newHashSet(); private ChunkProviderClient clientChunkProvider;
/*  53 */   private final Set<Entity> entitySpawnQueue = Sets.newHashSet();
/*  54 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  55 */   private final Set<ChunkCoordIntPair> previousActiveChunkSet = Sets.newHashSet();
/*     */   
/*     */   private boolean playerUpdate = false;
/*     */ 
/*     */   
/*     */   public WorldClient(NetHandlerPlayClient p_i45063_1_, WorldSettings p_i45063_2_, int p_i45063_3_, EnumDifficulty p_i45063_4_, Profiler p_i45063_5_) {
/*  61 */     super((ISaveHandler)new SaveHandlerMP(), new WorldInfo(p_i45063_2_, "MpServer"), WorldProvider.getProviderForDimension(p_i45063_3_), p_i45063_5_, true);
/*  62 */     this.sendQueue = p_i45063_1_;
/*  63 */     getWorldInfo().setDifficulty(p_i45063_4_);
/*  64 */     this.provider.registerWorld(this);
/*  65 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*  66 */     this.chunkProvider = createChunkProvider();
/*  67 */     this.mapStorage = (MapStorage)new SaveDataMemoryStorage();
/*  68 */     calculateInitialSkylight();
/*  69 */     calculateInitialWeather();
/*  70 */     (new WorldEvent()).call();
/*  71 */     Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { this });
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerControllerMP.class) {
/*     */ 
/*     */       
/*  78 */       this.mc.playerController = (PlayerControllerMP)new PlayerControllerOF(this.mc, p_i45063_1_);
/*  79 */       CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.mc.playerController);
/*     */     } 
/*  81 */     BlinkUtil.disable(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  89 */     super.tick();
/*  90 */     setTotalWorldTime(getTotalWorldTime() + 1L);
/*     */     
/*  92 */     if (getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
/*     */     {
/*  94 */       setWorldTime(getWorldTime() + 1L);
/*     */     }
/*     */     
/*  97 */     this.theProfiler.startSection("reEntryProcessing");
/*     */     
/*  99 */     for (int i = 0; i < 10 && !this.entitySpawnQueue.isEmpty(); i++) {
/*     */       
/* 101 */       Entity entity = this.entitySpawnQueue.iterator().next();
/* 102 */       this.entitySpawnQueue.remove(entity);
/*     */       
/* 104 */       if (!this.loadedEntityList.contains(entity))
/*     */       {
/* 106 */         spawnEntityInWorld(entity);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     this.theProfiler.endStartSection("chunkCache");
/* 111 */     this.clientChunkProvider.unloadQueuedChunks();
/* 112 */     this.theProfiler.endStartSection("blocks");
/* 113 */     updateBlocks();
/* 114 */     this.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateBlockReceiveRegion(int p_73031_1_, int p_73031_2_, int p_73031_3_, int p_73031_4_, int p_73031_5_, int p_73031_6_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IChunkProvider createChunkProvider() {
/* 130 */     this.clientChunkProvider = new ChunkProviderClient(this);
/* 131 */     return this.clientChunkProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateBlocks() {
/* 136 */     super.updateBlocks();
/* 137 */     this.previousActiveChunkSet.retainAll(this.activeChunkSet);
/*     */     
/* 139 */     if (this.previousActiveChunkSet.size() == this.activeChunkSet.size())
/*     */     {
/* 141 */       this.previousActiveChunkSet.clear();
/*     */     }
/*     */     
/* 144 */     int i = 0;
/*     */     
/* 146 */     for (ChunkCoordIntPair chunkcoordintpair : this.activeChunkSet) {
/*     */       
/* 148 */       if (!this.previousActiveChunkSet.contains(chunkcoordintpair)) {
/*     */         
/* 150 */         int j = chunkcoordintpair.chunkXPos * 16;
/* 151 */         int k = chunkcoordintpair.chunkZPos * 16;
/* 152 */         this.theProfiler.startSection("getChunk");
/* 153 */         Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/* 154 */         playMoodSoundAndCheckLight(j, k, chunk);
/* 155 */         this.theProfiler.endSection();
/* 156 */         this.previousActiveChunkSet.add(chunkcoordintpair);
/* 157 */         i++;
/*     */         
/* 159 */         if (i >= 10) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPreChunk(int p_73025_1_, int p_73025_2_, boolean p_73025_3_) {
/* 169 */     if (p_73025_3_) {
/*     */       
/* 171 */       this.clientChunkProvider.loadChunk(p_73025_1_, p_73025_2_);
/*     */     }
/*     */     else {
/*     */       
/* 175 */       this.clientChunkProvider.unloadChunk(p_73025_1_, p_73025_2_);
/*     */     } 
/*     */     
/* 178 */     if (!p_73025_3_)
/*     */     {
/* 180 */       markBlockRangeForRenderUpdate(p_73025_1_ * 16, 0, p_73025_2_ * 16, p_73025_1_ * 16 + 15, 256, p_73025_2_ * 16 + 15);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 189 */     boolean flag = super.spawnEntityInWorld(entityIn);
/* 190 */     this.entityList.add(entityIn);
/*     */     
/* 192 */     if (!flag) {
/*     */       
/* 194 */       this.entitySpawnQueue.add(entityIn);
/*     */     }
/* 196 */     else if (entityIn instanceof EntityMinecart) {
/*     */       
/* 198 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecart((EntityMinecart)entityIn));
/*     */     } 
/*     */     
/* 201 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entityIn) {
/* 209 */     super.removeEntity(entityIn);
/* 210 */     this.entityList.remove(entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityAdded(Entity entityIn) {
/* 215 */     super.onEntityAdded(entityIn);
/*     */     
/* 217 */     if (this.entitySpawnQueue.contains(entityIn))
/*     */     {
/* 219 */       this.entitySpawnQueue.remove(entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityRemoved(Entity entityIn) {
/* 225 */     super.onEntityRemoved(entityIn);
/* 226 */     boolean flag = false;
/*     */     
/* 228 */     if (this.entityList.contains(entityIn))
/*     */     {
/* 230 */       if (entityIn.isEntityAlive()) {
/*     */         
/* 232 */         this.entitySpawnQueue.add(entityIn);
/* 233 */         flag = true;
/*     */       }
/*     */       else {
/*     */         
/* 237 */         this.entityList.remove(entityIn);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToWorld(int p_73027_1_, Entity p_73027_2_) {
/* 247 */     Entity entity = getEntityByID(p_73027_1_);
/*     */     
/* 249 */     if (entity != null)
/*     */     {
/* 251 */       removeEntity(entity);
/*     */     }
/*     */     
/* 254 */     this.entityList.add(p_73027_2_);
/* 255 */     p_73027_2_.setEntityId(p_73027_1_);
/*     */     
/* 257 */     if (!spawnEntityInWorld(p_73027_2_))
/*     */     {
/* 259 */       this.entitySpawnQueue.add(p_73027_2_);
/*     */     }
/*     */     
/* 262 */     this.entitiesById.addKey(p_73027_1_, p_73027_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getEntityByID(int id) {
/* 270 */     return (id == this.mc.thePlayer.getEntityId()) ? (Entity)this.mc.thePlayer : super.getEntityByID(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity removeEntityFromWorld(int p_73028_1_) {
/* 275 */     Entity entity = (Entity)this.entitiesById.removeObject(p_73028_1_);
/*     */     
/* 277 */     if (entity != null) {
/*     */       
/* 279 */       this.entityList.remove(entity);
/* 280 */       removeEntity(entity);
/*     */     } 
/*     */     
/* 283 */     return entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean invalidateRegionAndSetBlock(BlockPos p_180503_1_, IBlockState p_180503_2_) {
/* 288 */     int i = p_180503_1_.getX();
/* 289 */     int j = p_180503_1_.getY();
/* 290 */     int k = p_180503_1_.getZ();
/* 291 */     invalidateBlockReceiveRegion(i, j, k, i, j, k);
/* 292 */     return super.setBlockState(p_180503_1_, p_180503_2_, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendQuittingDisconnectingPacket() {
/* 300 */     this.sendQueue.getNetworkManager().closeChannel((IChatComponent)new ChatComponentText("Quitting"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateWeather() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getRenderDistanceChunks() {
/* 312 */     return this.mc.gameSettings.renderDistanceChunks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doVoidFogParticles(int p_73029_1_, int p_73029_2_, int p_73029_3_) {
/* 317 */     int i = 16;
/* 318 */     Random random = new Random();
/* 319 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 320 */     boolean flag = (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE && itemstack != null && Block.getBlockFromItem(itemstack.getItem()) == Blocks.barrier);
/* 321 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 323 */     for (int j = 0; j < 1000; j++) {
/*     */       
/* 325 */       int k = p_73029_1_ + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 326 */       int l = p_73029_2_ + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 327 */       int i1 = p_73029_3_ + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 328 */       blockpos$mutableblockpos.func_181079_c(k, l, i1);
/* 329 */       IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/* 330 */       iblockstate.getBlock().randomDisplayTick(this, (BlockPos)blockpos$mutableblockpos, iblockstate, random);
/*     */       
/* 332 */       if (flag && iblockstate.getBlock() == Blocks.barrier)
/*     */       {
/* 334 */         spawnParticle(EnumParticleTypes.BARRIER, (k + 0.5F), (l + 0.5F), (i1 + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllEntities() {
/* 344 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*     */     
/* 346 */     for (int i = 0; i < this.unloadedEntityList.size(); i++) {
/*     */       
/* 348 */       Entity entity = this.unloadedEntityList.get(i);
/* 349 */       int j = entity.chunkCoordX;
/* 350 */       int k = entity.chunkCoordZ;
/*     */       
/* 352 */       if (entity.addedToChunk && isChunkLoaded(j, k, true))
/*     */       {
/* 354 */         getChunkFromChunkCoords(j, k).removeEntity(entity);
/*     */       }
/*     */     } 
/*     */     
/* 358 */     for (int l = 0; l < this.unloadedEntityList.size(); l++)
/*     */     {
/* 360 */       onEntityRemoved(this.unloadedEntityList.get(l));
/*     */     }
/*     */     
/* 363 */     this.unloadedEntityList.clear();
/*     */     
/* 365 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/*     */       
/* 367 */       Entity entity1 = this.loadedEntityList.get(i1);
/*     */       
/* 369 */       if (entity1.ridingEntity != null) {
/*     */         
/* 371 */         if (!entity1.ridingEntity.isDead && entity1.ridingEntity.riddenByEntity == entity1) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 376 */         entity1.ridingEntity.riddenByEntity = null;
/* 377 */         entity1.ridingEntity = null;
/*     */       } 
/*     */       
/* 380 */       if (entity1.isDead) {
/*     */         
/* 382 */         int j1 = entity1.chunkCoordX;
/* 383 */         int k1 = entity1.chunkCoordZ;
/*     */         
/* 385 */         if (entity1.addedToChunk && isChunkLoaded(j1, k1, true))
/*     */         {
/* 387 */           getChunkFromChunkCoords(j1, k1).removeEntity(entity1);
/*     */         }
/*     */         
/* 390 */         this.loadedEntityList.remove(i1--);
/* 391 */         onEntityRemoved(entity1);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 401 */     CrashReportCategory crashreportcategory = super.addWorldInfoToCrashReport(report);
/* 402 */     crashreportcategory.addCrashSectionCallable("Forced entities", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 406 */             return WorldClient.this.entityList.size() + " total; " + WorldClient.this.entityList.toString();
/*     */           }
/*     */         });
/* 409 */     crashreportcategory.addCrashSectionCallable("Retry entities", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 413 */             return WorldClient.this.entitySpawnQueue.size() + " total; " + WorldClient.this.entitySpawnQueue.toString();
/*     */           }
/*     */         });
/* 416 */     crashreportcategory.addCrashSectionCallable("Server brand", new Callable<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 420 */             return WorldClient.this.mc.thePlayer.getClientBrand();
/*     */           }
/*     */         });
/* 423 */     crashreportcategory.addCrashSectionCallable("Server type", new Callable<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 427 */             return (WorldClient.this.mc.getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
/*     */           }
/*     */         });
/* 430 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSoundAtPos(BlockPos p_175731_1_, String p_175731_2_, float p_175731_3_, float p_175731_4_, boolean p_175731_5_) {
/* 438 */     playSound(p_175731_1_.getX() + 0.5D, p_175731_1_.getY() + 0.5D, p_175731_1_.getZ() + 0.5D, p_175731_2_, p_175731_3_, p_175731_4_, p_175731_5_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {
/* 446 */     double d0 = this.mc.getRenderViewEntity().getDistanceSq(x, y, z);
/* 447 */     PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(new ResourceLocation(soundName), volume, pitch, (float)x, (float)y, (float)z);
/*     */     
/* 449 */     if (distanceDelay && d0 > 100.0D) {
/*     */       
/* 451 */       double d1 = Math.sqrt(d0) / 40.0D;
/* 452 */       this.mc.getSoundHandler().playDelayedSound((ISound)positionedsoundrecord, (int)(d1 * 20.0D));
/*     */     }
/*     */     else {
/*     */       
/* 456 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {
/* 462 */     this.mc.effectRenderer.addEffect((EntityFX)new EntityFirework.StarterFX(this, x, y, z, motionX, motionY, motionZ, this.mc.effectRenderer, compund));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldScoreboard(Scoreboard p_96443_1_) {
/* 467 */     this.worldScoreboard = p_96443_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {
/* 475 */     if (time < 0L) {
/*     */       
/* 477 */       time = -time;
/* 478 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*     */     }
/*     */     else {
/*     */       
/* 482 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
/*     */     } 
/*     */     
/* 485 */     super.setWorldTime(time);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/* 490 */     int i = super.getCombinedLight(pos, lightValue);
/*     */     
/* 492 */     if (Config.isDynamicLights())
/*     */     {
/* 494 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/* 497 */     return i;
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
/*     */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/* 510 */     this.playerUpdate = isPlayerActing();
/* 511 */     boolean flag = super.setBlockState(pos, newState, flags);
/* 512 */     this.playerUpdate = false;
/* 513 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPlayerActing() {
/* 518 */     if (this.mc.playerController instanceof PlayerControllerOF) {
/*     */       
/* 520 */       PlayerControllerOF playercontrollerof = (PlayerControllerOF)this.mc.playerController;
/* 521 */       return playercontrollerof.isActing();
/*     */     } 
/*     */ 
/*     */     
/* 525 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 531 */     return this.playerUpdate;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\multiplayer\WorldClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */