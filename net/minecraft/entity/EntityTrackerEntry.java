/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.S0APacketUseBed;
/*     */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*     */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*     */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.S14PacketEntity;
/*     */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*     */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*     */ import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTrackerEntry
/*     */ {
/*  65 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   public Entity trackedEntity;
/*     */   
/*     */   public int trackingDistanceThreshold;
/*     */   
/*     */   public int updateFrequency;
/*     */   
/*     */   public int encodedPosX;
/*     */   
/*     */   public int encodedPosY;
/*     */   
/*     */   public int encodedPosZ;
/*     */   
/*     */   public int encodedRotationYaw;
/*     */   
/*     */   public int encodedRotationPitch;
/*     */   
/*     */   public int lastHeadMotion;
/*     */   
/*     */   public double lastTrackedEntityMotionX;
/*     */   
/*     */   public double lastTrackedEntityMotionY;
/*     */   
/*     */   public double motionZ;
/*     */   
/*     */   public int updateCounter;
/*     */   
/*     */   private double lastTrackedEntityPosX;
/*     */   
/*     */   private double lastTrackedEntityPosY;
/*     */   
/*     */   private double lastTrackedEntityPosZ;
/*     */   
/*     */   private boolean firstUpdateDone;
/*     */   
/*     */   private boolean sendVelocityUpdates;
/*     */   
/*     */   private int ticksSinceLastForcedTeleport;
/*     */   private Entity field_85178_v;
/*     */   private boolean ridingEntity;
/*     */   private boolean onGround;
/*     */   public boolean playerEntitiesUpdated;
/* 108 */   public Set<EntityPlayerMP> trackingPlayers = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   public EntityTrackerEntry(Entity trackedEntityIn, int trackingDistanceThresholdIn, int updateFrequencyIn, boolean sendVelocityUpdatesIn) {
/* 112 */     this.trackedEntity = trackedEntityIn;
/* 113 */     this.trackingDistanceThreshold = trackingDistanceThresholdIn;
/* 114 */     this.updateFrequency = updateFrequencyIn;
/* 115 */     this.sendVelocityUpdates = sendVelocityUpdatesIn;
/* 116 */     this.encodedPosX = MathHelper.floor_double(trackedEntityIn.posX * 32.0D);
/* 117 */     this.encodedPosY = MathHelper.floor_double(trackedEntityIn.posY * 32.0D);
/* 118 */     this.encodedPosZ = MathHelper.floor_double(trackedEntityIn.posZ * 32.0D);
/* 119 */     this.encodedRotationYaw = MathHelper.floor_float(trackedEntityIn.rotationYaw * 256.0F / 360.0F);
/* 120 */     this.encodedRotationPitch = MathHelper.floor_float(trackedEntityIn.rotationPitch * 256.0F / 360.0F);
/* 121 */     this.lastHeadMotion = MathHelper.floor_float(trackedEntityIn.getRotationYawHead() * 256.0F / 360.0F);
/* 122 */     this.onGround = trackedEntityIn.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 127 */     return (p_equals_1_ instanceof EntityTrackerEntry) ? ((((EntityTrackerEntry)p_equals_1_).trackedEntity.getEntityId() == this.trackedEntity.getEntityId())) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     return this.trackedEntity.getEntityId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerList(List<EntityPlayer> p_73122_1_) {
/* 137 */     this.playerEntitiesUpdated = false;
/*     */     
/* 139 */     if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0D) {
/*     */       
/* 141 */       this.lastTrackedEntityPosX = this.trackedEntity.posX;
/* 142 */       this.lastTrackedEntityPosY = this.trackedEntity.posY;
/* 143 */       this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
/* 144 */       this.firstUpdateDone = true;
/* 145 */       this.playerEntitiesUpdated = true;
/* 146 */       updatePlayerEntities(p_73122_1_);
/*     */     } 
/*     */     
/* 149 */     if (this.field_85178_v != this.trackedEntity.ridingEntity || (this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0)) {
/*     */       
/* 151 */       this.field_85178_v = this.trackedEntity.ridingEntity;
/* 152 */       sendPacketToTrackedPlayers((Packet)new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
/*     */     } 
/*     */     
/* 155 */     if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
/*     */       
/* 157 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 158 */       ItemStack itemstack = entityitemframe.getDisplayedItem();
/*     */       
/* 160 */       if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemMap) {
/*     */         
/* 162 */         MapData mapdata = Items.filled_map.getMapData(itemstack, this.trackedEntity.worldObj);
/*     */         
/* 164 */         for (EntityPlayer entityplayer : p_73122_1_) {
/*     */           
/* 166 */           EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
/* 167 */           mapdata.updateVisiblePlayers((EntityPlayer)entityplayermp, itemstack);
/* 168 */           Packet packet = Items.filled_map.createMapDataPacket(itemstack, this.trackedEntity.worldObj, (EntityPlayer)entityplayermp);
/*     */           
/* 170 */           if (packet != null)
/*     */           {
/* 172 */             entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       sendMetadataToAllAssociatedPlayers();
/*     */     } 
/*     */     
/* 180 */     if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
/*     */       
/* 182 */       if (this.trackedEntity.ridingEntity == null) {
/*     */         S18PacketEntityTeleport s18PacketEntityTeleport;
/* 184 */         this.ticksSinceLastForcedTeleport++;
/* 185 */         int k = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
/* 186 */         int j1 = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
/* 187 */         int k1 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
/* 188 */         int l1 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 189 */         int i2 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 190 */         int j2 = k - this.encodedPosX;
/* 191 */         int k2 = j1 - this.encodedPosY;
/* 192 */         int i = k1 - this.encodedPosZ;
/* 193 */         Packet packet1 = null;
/* 194 */         boolean flag = (Math.abs(j2) >= 4 || Math.abs(k2) >= 4 || Math.abs(i) >= 4 || this.updateCounter % 60 == 0);
/* 195 */         boolean flag1 = (Math.abs(l1 - this.encodedRotationYaw) >= 4 || Math.abs(i2 - this.encodedRotationPitch) >= 4);
/*     */         
/* 197 */         if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow)
/*     */         {
/* 199 */           if (j2 >= -128 && j2 < 128 && k2 >= -128 && k2 < 128 && i >= -128 && i < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
/*     */             
/* 201 */             if ((!flag || !flag1) && !(this.trackedEntity instanceof EntityArrow)) {
/*     */               
/* 203 */               if (flag)
/*     */               {
/* 205 */                 S14PacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove = new S14PacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), (byte)j2, (byte)k2, (byte)i, this.trackedEntity.onGround);
/*     */               }
/* 207 */               else if (flag1)
/*     */               {
/* 209 */                 S14PacketEntity.S16PacketEntityLook s16PacketEntityLook = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 214 */               S14PacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove = new S14PacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), (byte)j2, (byte)k2, (byte)i, (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 219 */             this.onGround = this.trackedEntity.onGround;
/* 220 */             this.ticksSinceLastForcedTeleport = 0;
/* 221 */             s18PacketEntityTeleport = new S18PacketEntityTeleport(this.trackedEntity.getEntityId(), k, j1, k1, (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */           } 
/*     */         }
/*     */         
/* 225 */         if (this.sendVelocityUpdates) {
/*     */           
/* 227 */           double d0 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
/* 228 */           double d1 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
/* 229 */           double d2 = this.trackedEntity.motionZ - this.motionZ;
/* 230 */           double d3 = 0.02D;
/* 231 */           double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */           
/* 233 */           if (d4 > d3 * d3 || (d4 > 0.0D && this.trackedEntity.motionX == 0.0D && this.trackedEntity.motionY == 0.0D && this.trackedEntity.motionZ == 0.0D)) {
/*     */             
/* 235 */             this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 236 */             this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 237 */             this.motionZ = this.trackedEntity.motionZ;
/* 238 */             sendPacketToTrackedPlayers((Packet)new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
/*     */           } 
/*     */         } 
/*     */         
/* 242 */         if (s18PacketEntityTeleport != null)
/*     */         {
/* 244 */           sendPacketToTrackedPlayers((Packet)s18PacketEntityTeleport);
/*     */         }
/*     */         
/* 247 */         sendMetadataToAllAssociatedPlayers();
/*     */         
/* 249 */         if (flag) {
/*     */           
/* 251 */           this.encodedPosX = k;
/* 252 */           this.encodedPosY = j1;
/* 253 */           this.encodedPosZ = k1;
/*     */         } 
/*     */         
/* 256 */         if (flag1) {
/*     */           
/* 258 */           this.encodedRotationYaw = l1;
/* 259 */           this.encodedRotationPitch = i2;
/*     */         } 
/*     */         
/* 262 */         this.ridingEntity = false;
/*     */       }
/*     */       else {
/*     */         
/* 266 */         int j = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 267 */         int i1 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 268 */         boolean flag2 = (Math.abs(j - this.encodedRotationYaw) >= 4 || Math.abs(i1 - this.encodedRotationPitch) >= 4);
/*     */         
/* 270 */         if (flag2) {
/*     */           
/* 272 */           sendPacketToTrackedPlayers((Packet)new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)j, (byte)i1, this.trackedEntity.onGround));
/* 273 */           this.encodedRotationYaw = j;
/* 274 */           this.encodedRotationPitch = i1;
/*     */         } 
/*     */         
/* 277 */         this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
/* 278 */         this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
/* 279 */         this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
/* 280 */         sendMetadataToAllAssociatedPlayers();
/* 281 */         this.ridingEntity = true;
/*     */       } 
/*     */       
/* 284 */       int l = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/*     */       
/* 286 */       if (Math.abs(l - this.lastHeadMotion) >= 4) {
/*     */         
/* 288 */         sendPacketToTrackedPlayers((Packet)new S19PacketEntityHeadLook(this.trackedEntity, (byte)l));
/* 289 */         this.lastHeadMotion = l;
/*     */       } 
/*     */       
/* 292 */       this.trackedEntity.isAirBorne = false;
/*     */     } 
/*     */     
/* 295 */     this.updateCounter++;
/*     */     
/* 297 */     if (this.trackedEntity.velocityChanged) {
/*     */       
/* 299 */       func_151261_b((Packet)new S12PacketEntityVelocity(this.trackedEntity));
/* 300 */       this.trackedEntity.velocityChanged = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendMetadataToAllAssociatedPlayers() {
/* 310 */     DataWatcher datawatcher = this.trackedEntity.getDataWatcher();
/*     */     
/* 312 */     if (datawatcher.hasObjectChanged())
/*     */     {
/* 314 */       func_151261_b((Packet)new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), datawatcher, false));
/*     */     }
/*     */     
/* 317 */     if (this.trackedEntity instanceof EntityLivingBase) {
/*     */       
/* 319 */       ServersideAttributeMap serversideattributemap = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 320 */       Set<IAttributeInstance> set = serversideattributemap.getAttributeInstanceSet();
/*     */       
/* 322 */       if (!set.isEmpty())
/*     */       {
/* 324 */         func_151261_b((Packet)new S20PacketEntityProperties(this.trackedEntity.getEntityId(), set));
/*     */       }
/*     */       
/* 327 */       set.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketToTrackedPlayers(Packet packetIn) {
/* 338 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers)
/*     */     {
/* 340 */       entityplayermp.playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_151261_b(Packet packetIn) {
/* 346 */     sendPacketToTrackedPlayers(packetIn);
/*     */     
/* 348 */     if (this.trackedEntity instanceof EntityPlayerMP)
/*     */     {
/* 350 */       ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDestroyEntityPacketToTrackedPlayers() {
/* 356 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers)
/*     */     {
/* 358 */       entityplayermp.removeEntity(this.trackedEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromTrackedPlayers(EntityPlayerMP playerMP) {
/* 364 */     if (this.trackingPlayers.contains(playerMP)) {
/*     */       
/* 366 */       playerMP.removeEntity(this.trackedEntity);
/* 367 */       this.trackingPlayers.remove(playerMP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEntity(EntityPlayerMP playerMP) {
/* 373 */     if (playerMP != this.trackedEntity)
/*     */     {
/* 375 */       if (func_180233_c(playerMP)) {
/*     */         
/* 377 */         if (!this.trackingPlayers.contains(playerMP) && (isPlayerWatchingThisChunk(playerMP) || this.trackedEntity.forceSpawn)) {
/*     */           
/* 379 */           this.trackingPlayers.add(playerMP);
/* 380 */           Packet packet = func_151260_c();
/* 381 */           playerMP.playerNetServerHandler.sendPacket(packet);
/*     */           
/* 383 */           if (!this.trackedEntity.getDataWatcher().getIsBlank())
/*     */           {
/* 385 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), this.trackedEntity.getDataWatcher(), true));
/*     */           }
/*     */           
/* 388 */           NBTTagCompound nbttagcompound = this.trackedEntity.getNBTTagCompound();
/*     */           
/* 390 */           if (nbttagcompound != null)
/*     */           {
/* 392 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S49PacketUpdateEntityNBT(this.trackedEntity.getEntityId(), nbttagcompound));
/*     */           }
/*     */           
/* 395 */           if (this.trackedEntity instanceof EntityLivingBase) {
/*     */             
/* 397 */             ServersideAttributeMap serversideattributemap = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 398 */             Collection<IAttributeInstance> collection = serversideattributemap.getWatchedAttributes();
/*     */             
/* 400 */             if (!collection.isEmpty())
/*     */             {
/* 402 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S20PacketEntityProperties(this.trackedEntity.getEntityId(), collection));
/*     */             }
/*     */           } 
/*     */           
/* 406 */           this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 407 */           this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 408 */           this.motionZ = this.trackedEntity.motionZ;
/*     */           
/* 410 */           if (this.sendVelocityUpdates && !(packet instanceof S0FPacketSpawnMob))
/*     */           {
/* 412 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ));
/*     */           }
/*     */           
/* 415 */           if (this.trackedEntity.ridingEntity != null)
/*     */           {
/* 417 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
/*     */           }
/*     */           
/* 420 */           if (this.trackedEntity instanceof EntityLiving && ((EntityLiving)this.trackedEntity).getLeashedToEntity() != null)
/*     */           {
/* 422 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(1, this.trackedEntity, ((EntityLiving)this.trackedEntity).getLeashedToEntity()));
/*     */           }
/*     */           
/* 425 */           if (this.trackedEntity instanceof EntityLivingBase)
/*     */           {
/* 427 */             for (int i = 0; i < 5; i++) {
/*     */               
/* 429 */               ItemStack itemstack = ((EntityLivingBase)this.trackedEntity).getEquipmentInSlot(i);
/*     */               
/* 431 */               if (itemstack != null)
/*     */               {
/* 433 */                 playerMP.playerNetServerHandler.sendPacket((Packet)new S04PacketEntityEquipment(this.trackedEntity.getEntityId(), i, itemstack));
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 438 */           if (this.trackedEntity instanceof EntityPlayer) {
/*     */             
/* 440 */             EntityPlayer entityplayer = (EntityPlayer)this.trackedEntity;
/*     */             
/* 442 */             if (entityplayer.isPlayerSleeping())
/*     */             {
/* 444 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S0APacketUseBed(entityplayer, new BlockPos(this.trackedEntity)));
/*     */             }
/*     */           } 
/*     */           
/* 448 */           if (this.trackedEntity instanceof EntityLivingBase)
/*     */           {
/* 450 */             EntityLivingBase entitylivingbase = (EntityLivingBase)this.trackedEntity;
/*     */             
/* 452 */             for (PotionEffect potioneffect : entitylivingbase.getActivePotionEffects())
/*     */             {
/* 454 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(this.trackedEntity.getEntityId(), potioneffect));
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 459 */       } else if (this.trackingPlayers.contains(playerMP)) {
/*     */         
/* 461 */         this.trackingPlayers.remove(playerMP);
/* 462 */         playerMP.removeEntity(this.trackedEntity);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_180233_c(EntityPlayerMP playerMP) {
/* 469 */     double d0 = playerMP.posX - (this.encodedPosX / 32);
/* 470 */     double d1 = playerMP.posZ - (this.encodedPosZ / 32);
/* 471 */     return (d0 >= -this.trackingDistanceThreshold && d0 <= this.trackingDistanceThreshold && d1 >= -this.trackingDistanceThreshold && d1 <= this.trackingDistanceThreshold && this.trackedEntity.isSpectatedByPlayer(playerMP));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPlayerWatchingThisChunk(EntityPlayerMP playerMP) {
/* 476 */     return playerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(playerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEntities(List<EntityPlayer> p_73125_1_) {
/* 481 */     for (int i = 0; i < p_73125_1_.size(); i++)
/*     */     {
/* 483 */       updatePlayerEntity((EntityPlayerMP)p_73125_1_.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet func_151260_c() {
/* 489 */     if (this.trackedEntity.isDead)
/*     */     {
/* 491 */       logger.warn("Fetching addPacket for removed entity");
/*     */     }
/*     */     
/* 494 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityItem)
/*     */     {
/* 496 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
/*     */     }
/* 498 */     if (this.trackedEntity instanceof EntityPlayerMP)
/*     */     {
/* 500 */       return (Packet)new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
/*     */     }
/* 502 */     if (this.trackedEntity instanceof EntityMinecart) {
/*     */       
/* 504 */       EntityMinecart entityminecart = (EntityMinecart)this.trackedEntity;
/* 505 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 10, entityminecart.getMinecartType().getNetworkID());
/*     */     } 
/* 507 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityBoat)
/*     */     {
/* 509 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 1);
/*     */     }
/* 511 */     if (this.trackedEntity instanceof net.minecraft.entity.passive.IAnimals) {
/*     */       
/* 513 */       this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/* 514 */       return (Packet)new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
/*     */     } 
/* 516 */     if (this.trackedEntity instanceof EntityFishHook) {
/*     */       
/* 518 */       EntityPlayer entityPlayer = ((EntityFishHook)this.trackedEntity).angler;
/* 519 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 90, (entityPlayer != null) ? entityPlayer.getEntityId() : this.trackedEntity.getEntityId());
/*     */     } 
/* 521 */     if (this.trackedEntity instanceof EntityArrow) {
/*     */       
/* 523 */       Entity entity = ((EntityArrow)this.trackedEntity).shootingEntity;
/* 524 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 60, (entity != null) ? entity.getEntityId() : this.trackedEntity.getEntityId());
/*     */     } 
/* 526 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySnowball)
/*     */     {
/* 528 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 61);
/*     */     }
/* 530 */     if (this.trackedEntity instanceof EntityPotion)
/*     */     {
/* 532 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage());
/*     */     }
/* 534 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityExpBottle)
/*     */     {
/* 536 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 75);
/*     */     }
/* 538 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderPearl)
/*     */     {
/* 540 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 65);
/*     */     }
/* 542 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderEye)
/*     */     {
/* 544 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 72);
/*     */     }
/* 546 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityFireworkRocket)
/*     */     {
/* 548 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 76);
/*     */     }
/* 550 */     if (this.trackedEntity instanceof EntityFireball) {
/*     */       
/* 552 */       EntityFireball entityfireball = (EntityFireball)this.trackedEntity;
/* 553 */       S0EPacketSpawnObject s0epacketspawnobject2 = null;
/* 554 */       int i = 63;
/*     */       
/* 556 */       if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/*     */         
/* 558 */         i = 64;
/*     */       }
/* 560 */       else if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityWitherSkull) {
/*     */         
/* 562 */         i = 66;
/*     */       } 
/*     */       
/* 565 */       if (entityfireball.shootingEntity != null) {
/*     */         
/* 567 */         s0epacketspawnobject2 = new S0EPacketSpawnObject(this.trackedEntity, i, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
/*     */       }
/*     */       else {
/*     */         
/* 571 */         s0epacketspawnobject2 = new S0EPacketSpawnObject(this.trackedEntity, i, 0);
/*     */       } 
/*     */       
/* 574 */       s0epacketspawnobject2.setSpeedX((int)(entityfireball.accelerationX * 8000.0D));
/* 575 */       s0epacketspawnobject2.setSpeedY((int)(entityfireball.accelerationY * 8000.0D));
/* 576 */       s0epacketspawnobject2.setSpeedZ((int)(entityfireball.accelerationZ * 8000.0D));
/* 577 */       return (Packet)s0epacketspawnobject2;
/*     */     } 
/* 579 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityEgg)
/*     */     {
/* 581 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 62);
/*     */     }
/* 583 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/*     */     {
/* 585 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 50);
/*     */     }
/* 587 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderCrystal)
/*     */     {
/* 589 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 51);
/*     */     }
/* 591 */     if (this.trackedEntity instanceof EntityFallingBlock) {
/*     */       
/* 593 */       EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.trackedEntity;
/* 594 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(entityfallingblock.getBlock()));
/*     */     } 
/* 596 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityArmorStand)
/*     */     {
/* 598 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 78);
/*     */     }
/* 600 */     if (this.trackedEntity instanceof EntityPainting)
/*     */     {
/* 602 */       return (Packet)new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
/*     */     }
/* 604 */     if (this.trackedEntity instanceof EntityItemFrame) {
/*     */       
/* 606 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 607 */       S0EPacketSpawnObject s0epacketspawnobject1 = new S0EPacketSpawnObject(this.trackedEntity, 71, entityitemframe.facingDirection.getHorizontalIndex());
/* 608 */       BlockPos blockpos1 = entityitemframe.getHangingPosition();
/* 609 */       s0epacketspawnobject1.setX(MathHelper.floor_float((blockpos1.getX() * 32)));
/* 610 */       s0epacketspawnobject1.setY(MathHelper.floor_float((blockpos1.getY() * 32)));
/* 611 */       s0epacketspawnobject1.setZ(MathHelper.floor_float((blockpos1.getZ() * 32)));
/* 612 */       return (Packet)s0epacketspawnobject1;
/*     */     } 
/* 614 */     if (this.trackedEntity instanceof EntityLeashKnot) {
/*     */       
/* 616 */       EntityLeashKnot entityleashknot = (EntityLeashKnot)this.trackedEntity;
/* 617 */       S0EPacketSpawnObject s0epacketspawnobject = new S0EPacketSpawnObject(this.trackedEntity, 77);
/* 618 */       BlockPos blockpos = entityleashknot.getHangingPosition();
/* 619 */       s0epacketspawnobject.setX(MathHelper.floor_float((blockpos.getX() * 32)));
/* 620 */       s0epacketspawnobject.setY(MathHelper.floor_float((blockpos.getY() * 32)));
/* 621 */       s0epacketspawnobject.setZ(MathHelper.floor_float((blockpos.getZ() * 32)));
/* 622 */       return (Packet)s0epacketspawnobject;
/*     */     } 
/* 624 */     if (this.trackedEntity instanceof EntityXPOrb)
/*     */     {
/* 626 */       return (Packet)new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
/*     */     }
/*     */ 
/*     */     
/* 630 */     throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTrackedPlayerSymmetric(EntityPlayerMP playerMP) {
/* 639 */     if (this.trackingPlayers.contains(playerMP)) {
/*     */       
/* 641 */       this.trackingPlayers.remove(playerMP);
/* 642 */       playerMP.removeEntity(this.trackedEntity);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\EntityTrackerEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */