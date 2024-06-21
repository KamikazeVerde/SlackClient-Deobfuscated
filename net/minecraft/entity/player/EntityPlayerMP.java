/*      */ package net.minecraft.entity.player;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerChest;
/*      */ import net.minecraft.inventory.ContainerHorseInventory;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.ICrafting;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryMerchant;
/*      */ import net.minecraft.item.EnumAction;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMapBase;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetHandlerPlayServer;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.S0APacketUseBed;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*      */ import net.minecraft.network.play.server.S21PacketChunkData;
/*      */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*      */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*      */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*      */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*      */ import net.minecraft.network.play.server.S43PacketCamera;
/*      */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.ItemInWorldManager;
/*      */ import net.minecraft.server.management.UserListOpsEntry;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.stats.StatisticsFile;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IJsonSerializable;
/*      */ import net.minecraft.util.JsonSerializableSet;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.ILockableContainer;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class EntityPlayerMP
/*      */   extends EntityPlayer
/*      */   implements ICrafting
/*      */ {
/*  101 */   private static final Logger logger = LogManager.getLogger();
/*  102 */   private String translator = "en_US";
/*      */ 
/*      */   
/*      */   public NetHandlerPlayServer playerNetServerHandler;
/*      */ 
/*      */   
/*      */   public final MinecraftServer mcServer;
/*      */ 
/*      */   
/*      */   public final ItemInWorldManager theItemInWorldManager;
/*      */ 
/*      */   
/*      */   public double managedPosX;
/*      */ 
/*      */   
/*      */   public double managedPosZ;
/*      */ 
/*      */   
/*  120 */   public final List<ChunkCoordIntPair> loadedChunks = Lists.newLinkedList();
/*  121 */   private final List<Integer> destroyedItemsNetCache = Lists.newLinkedList();
/*      */ 
/*      */   
/*      */   private final StatisticsFile statsFile;
/*      */ 
/*      */   
/*  127 */   private float combinedHealth = Float.MIN_VALUE;
/*      */ 
/*      */   
/*  130 */   private float lastHealth = -1.0E8F;
/*      */ 
/*      */   
/*  133 */   private int lastFoodLevel = -99999999;
/*      */ 
/*      */   
/*      */   private boolean wasHungry = true;
/*      */ 
/*      */   
/*  139 */   private int lastExperience = -99999999;
/*  140 */   private int respawnInvulnerabilityTicks = 60;
/*      */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*      */   private boolean chatColours = true;
/*  143 */   private long playerLastActiveTime = System.currentTimeMillis();
/*      */ 
/*      */   
/*  146 */   private Entity spectatingEntity = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentWindowId;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChangingQuantityOnly;
/*      */ 
/*      */ 
/*      */   
/*      */   public int ping;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean playerConqueredTheEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP(MinecraftServer server, WorldServer worldIn, GameProfile profile, ItemInWorldManager interactionManager) {
/*  168 */     super((World)worldIn, profile);
/*  169 */     interactionManager.thisPlayerMP = this;
/*  170 */     this.theItemInWorldManager = interactionManager;
/*  171 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*      */     
/*  173 */     if (!worldIn.provider.getHasNoSky() && worldIn.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
/*      */       
/*  175 */       int i = Math.max(5, server.getSpawnProtectionSize() - 6);
/*  176 */       int j = MathHelper.floor_double(worldIn.getWorldBorder().getClosestDistance(blockpos.getX(), blockpos.getZ()));
/*      */       
/*  178 */       if (j < i)
/*      */       {
/*  180 */         i = j;
/*      */       }
/*      */       
/*  183 */       if (j <= 1)
/*      */       {
/*  185 */         i = 1;
/*      */       }
/*      */       
/*  188 */       blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos.add(this.rand.nextInt(i * 2) - i, 0, this.rand.nextInt(i * 2) - i));
/*      */     } 
/*      */     
/*  191 */     this.mcServer = server;
/*  192 */     this.statsFile = server.getConfigurationManager().getPlayerStatsFile(this);
/*  193 */     this.stepHeight = 0.0F;
/*  194 */     moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
/*      */     
/*  196 */     while (!worldIn.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && this.posY < 255.0D)
/*      */     {
/*  198 */       setPosition(this.posX, this.posY + 1.0D, this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  207 */     super.readEntityFromNBT(tagCompund);
/*      */     
/*  209 */     if (tagCompund.hasKey("playerGameType", 99))
/*      */     {
/*  211 */       if (MinecraftServer.getServer().getForceGamemode()) {
/*      */         
/*  213 */         this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
/*      */       }
/*      */       else {
/*      */         
/*  217 */         this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(tagCompund.getInteger("playerGameType")));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  227 */     super.writeEntityToNBT(tagCompound);
/*  228 */     tagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/*  236 */     super.addExperienceLevel(levels);
/*  237 */     this.lastExperience = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeExperienceLevel(int levels) {
/*  242 */     super.removeExperienceLevel(levels);
/*  243 */     this.lastExperience = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addSelfToInternalCraftingInventory() {
/*  248 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {
/*  256 */     super.sendEnterCombat();
/*  257 */     this.playerNetServerHandler.sendPacket((Packet)new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {
/*  265 */     super.sendEndCombat();
/*  266 */     this.playerNetServerHandler.sendPacket((Packet)new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  274 */     this.theItemInWorldManager.updateBlockRemoving();
/*  275 */     this.respawnInvulnerabilityTicks--;
/*      */     
/*  277 */     if (this.hurtResistantTime > 0)
/*      */     {
/*  279 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  282 */     this.openContainer.detectAndSendChanges();
/*      */     
/*  284 */     if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  286 */       closeScreen();
/*  287 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  290 */     while (!this.destroyedItemsNetCache.isEmpty()) {
/*      */       
/*  292 */       int i = Math.min(this.destroyedItemsNetCache.size(), 2147483647);
/*  293 */       int[] aint = new int[i];
/*  294 */       Iterator<Integer> iterator = this.destroyedItemsNetCache.iterator();
/*  295 */       int j = 0;
/*      */       
/*  297 */       while (iterator.hasNext() && j < i) {
/*      */         
/*  299 */         aint[j++] = ((Integer)iterator.next()).intValue();
/*  300 */         iterator.remove();
/*      */       } 
/*      */       
/*  303 */       this.playerNetServerHandler.sendPacket((Packet)new S13PacketDestroyEntities(aint));
/*      */     } 
/*      */     
/*  306 */     if (!this.loadedChunks.isEmpty()) {
/*      */       
/*  308 */       List<Chunk> list = Lists.newArrayList();
/*  309 */       Iterator<ChunkCoordIntPair> iterator1 = this.loadedChunks.iterator();
/*  310 */       List<TileEntity> list1 = Lists.newArrayList();
/*      */       
/*  312 */       while (iterator1.hasNext() && list.size() < 10) {
/*      */         
/*  314 */         ChunkCoordIntPair chunkcoordintpair = iterator1.next();
/*      */         
/*  316 */         if (chunkcoordintpair != null) {
/*      */           
/*  318 */           if (this.worldObj.isBlockLoaded(new BlockPos(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPos << 4))) {
/*      */             
/*  320 */             Chunk chunk = this.worldObj.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/*      */             
/*  322 */             if (chunk.isPopulated()) {
/*      */               
/*  324 */               list.add(chunk);
/*  325 */               list1.addAll(((WorldServer)this.worldObj).getTileEntitiesIn(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPos * 16, chunkcoordintpair.chunkXPos * 16 + 16, 256, chunkcoordintpair.chunkZPos * 16 + 16));
/*  326 */               iterator1.remove();
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*  332 */         iterator1.remove();
/*      */       } 
/*      */ 
/*      */       
/*  336 */       if (!list.isEmpty()) {
/*      */         
/*  338 */         if (list.size() == 1) {
/*      */           
/*  340 */           this.playerNetServerHandler.sendPacket((Packet)new S21PacketChunkData(list.get(0), true, 65535));
/*      */         }
/*      */         else {
/*      */           
/*  344 */           this.playerNetServerHandler.sendPacket((Packet)new S26PacketMapChunkBulk(list));
/*      */         } 
/*      */         
/*  347 */         for (TileEntity tileentity : list1)
/*      */         {
/*  349 */           sendTileEntityUpdate(tileentity);
/*      */         }
/*      */         
/*  352 */         for (Chunk chunk1 : list)
/*      */         {
/*  354 */           getServerForPlayer().getEntityTracker().func_85172_a(this, chunk1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  359 */     Entity entity = getSpectatingEntity();
/*      */     
/*  361 */     if (entity != this)
/*      */     {
/*  363 */       if (!entity.isEntityAlive()) {
/*      */         
/*  365 */         setSpectatingEntity((Entity)this);
/*      */       }
/*      */       else {
/*      */         
/*  369 */         setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*  370 */         this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
/*      */         
/*  372 */         if (isSneaking())
/*      */         {
/*  374 */           setSpectatingEntity((Entity)this);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateEntity() {
/*      */     try {
/*  384 */       super.onUpdate();
/*      */       
/*  386 */       for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
/*      */         
/*  388 */         ItemStack itemstack = this.inventory.getStackInSlot(i);
/*      */         
/*  390 */         if (itemstack != null && itemstack.getItem().isMap()) {
/*      */           
/*  392 */           Packet packet = ((ItemMapBase)itemstack.getItem()).createMapDataPacket(itemstack, this.worldObj, this);
/*      */           
/*  394 */           if (packet != null)
/*      */           {
/*  396 */             this.playerNetServerHandler.sendPacket(packet);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  401 */       if (getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || ((this.foodStats.getSaturationLevel() == 0.0F)) != this.wasHungry) {
/*      */         
/*  403 */         this.playerNetServerHandler.sendPacket((Packet)new S06PacketUpdateHealth(getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
/*  404 */         this.lastHealth = getHealth();
/*  405 */         this.lastFoodLevel = this.foodStats.getFoodLevel();
/*  406 */         this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0F);
/*      */       } 
/*      */       
/*  409 */       if (getHealth() + getAbsorptionAmount() != this.combinedHealth) {
/*      */         
/*  411 */         this.combinedHealth = getHealth() + getAbsorptionAmount();
/*      */         
/*  413 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.health)) {
/*      */           
/*  415 */           getWorldScoreboard().getValueFromObjective(getCommandSenderName(), scoreobjective).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
/*      */         } 
/*      */       } 
/*      */       
/*  419 */       if (this.experienceTotal != this.lastExperience) {
/*      */         
/*  421 */         this.lastExperience = this.experienceTotal;
/*  422 */         this.playerNetServerHandler.sendPacket((Packet)new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
/*      */       } 
/*      */       
/*  425 */       if (this.ticksExisted % 20 * 5 == 0 && !getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes))
/*      */       {
/*  427 */         updateBiomesExplored();
/*      */       }
/*      */     }
/*  430 */     catch (Throwable throwable) {
/*      */       
/*  432 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
/*  433 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
/*  434 */       addEntityCrashInfo(crashreportcategory);
/*  435 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateBiomesExplored() {
/*  444 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
/*  445 */     String s = biomegenbase.biomeName;
/*  446 */     JsonSerializableSet jsonserializableset = (JsonSerializableSet)getStatFile().func_150870_b((StatBase)AchievementList.exploreAllBiomes);
/*      */     
/*  448 */     if (jsonserializableset == null)
/*      */     {
/*  450 */       jsonserializableset = (JsonSerializableSet)getStatFile().func_150872_a((StatBase)AchievementList.exploreAllBiomes, (IJsonSerializable)new JsonSerializableSet());
/*      */     }
/*      */     
/*  453 */     jsonserializableset.add(s);
/*      */     
/*  455 */     if (getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && jsonserializableset.size() >= BiomeGenBase.explorationBiomesList.size()) {
/*      */       
/*  457 */       Set<BiomeGenBase> set = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
/*      */       
/*  459 */       for (String s1 : jsonserializableset) {
/*      */         
/*  461 */         Iterator<BiomeGenBase> iterator = set.iterator();
/*      */         
/*  463 */         while (iterator.hasNext()) {
/*      */           
/*  465 */           BiomeGenBase biomegenbase1 = iterator.next();
/*      */           
/*  467 */           if (biomegenbase1.biomeName.equals(s1))
/*      */           {
/*  469 */             iterator.remove();
/*      */           }
/*      */         } 
/*      */         
/*  473 */         if (set.isEmpty()) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  479 */       if (set.isEmpty())
/*      */       {
/*  481 */         triggerAchievement((StatBase)AchievementList.exploreAllBiomes);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  491 */     if (this.worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages")) {
/*      */       
/*  493 */       Team team = getTeam();
/*      */       
/*  495 */       if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS) {
/*      */         
/*  497 */         if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS)
/*      */         {
/*  499 */           this.mcServer.getConfigurationManager().sendMessageToAllTeamMembers(this, getCombatTracker().getDeathMessage());
/*      */         }
/*  501 */         else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM)
/*      */         {
/*  503 */           this.mcServer.getConfigurationManager().sendMessageToTeamOrEvryPlayer(this, getCombatTracker().getDeathMessage());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  508 */         this.mcServer.getConfigurationManager().sendChatMsg(getCombatTracker().getDeathMessage());
/*      */       } 
/*      */     } 
/*      */     
/*  512 */     if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
/*      */     {
/*  514 */       this.inventory.dropAllItems();
/*      */     }
/*      */     
/*  517 */     for (ScoreObjective scoreobjective : this.worldObj.getScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.deathCount)) {
/*      */       
/*  519 */       Score score = getWorldScoreboard().getValueFromObjective(getCommandSenderName(), scoreobjective);
/*  520 */       score.func_96648_a();
/*      */     } 
/*      */     
/*  523 */     EntityLivingBase entitylivingbase = func_94060_bK();
/*      */     
/*  525 */     if (entitylivingbase != null) {
/*      */       
/*  527 */       EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity)entitylivingbase)));
/*      */       
/*  529 */       if (entitylist$entityegginfo != null)
/*      */       {
/*  531 */         triggerAchievement(entitylist$entityegginfo.field_151513_e);
/*      */       }
/*      */       
/*  534 */       entitylivingbase.addToPlayerScore((Entity)this, this.scoreValue);
/*      */     } 
/*      */     
/*  537 */     triggerAchievement(StatList.deathsStat);
/*  538 */     func_175145_a(StatList.timeSinceDeathStat);
/*  539 */     getCombatTracker().reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  547 */     if (isEntityInvulnerable(source))
/*      */     {
/*  549 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  553 */     boolean flag = (this.mcServer.isDedicatedServer() && canPlayersAttack() && "fall".equals(source.damageType));
/*      */     
/*  555 */     if (!flag && this.respawnInvulnerabilityTicks > 0 && source != DamageSource.outOfWorld)
/*      */     {
/*  557 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  561 */     if (source instanceof net.minecraft.util.EntityDamageSource) {
/*      */       
/*  563 */       Entity entity = source.getEntity();
/*      */       
/*  565 */       if (entity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entity))
/*      */       {
/*  567 */         return false;
/*      */       }
/*      */       
/*  570 */       if (entity instanceof EntityArrow) {
/*      */         
/*  572 */         EntityArrow entityarrow = (EntityArrow)entity;
/*      */         
/*  574 */         if (entityarrow.shootingEntity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entityarrow.shootingEntity))
/*      */         {
/*  576 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  581 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/*  588 */     return !canPlayersAttack() ? false : super.canAttackPlayer(other);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canPlayersAttack() {
/*  596 */     return this.mcServer.isPVPEnabled();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/*  604 */     if (this.dimension == 1 && dimensionId == 1) {
/*      */       
/*  606 */       triggerAchievement((StatBase)AchievementList.theEnd2);
/*  607 */       this.worldObj.removeEntity((Entity)this);
/*  608 */       this.playerConqueredTheEnd = true;
/*  609 */       this.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(4, 0.0F));
/*      */     }
/*      */     else {
/*      */       
/*  613 */       if (this.dimension == 0 && dimensionId == 1) {
/*      */         
/*  615 */         triggerAchievement((StatBase)AchievementList.theEnd);
/*  616 */         BlockPos blockpos = this.mcServer.worldServerForDimension(dimensionId).getSpawnCoordinate();
/*      */         
/*  618 */         if (blockpos != null)
/*      */         {
/*  620 */           this.playerNetServerHandler.setPlayerLocation(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 0.0F, 0.0F);
/*      */         }
/*      */         
/*  623 */         dimensionId = 1;
/*      */       }
/*      */       else {
/*      */         
/*  627 */         triggerAchievement((StatBase)AchievementList.portal);
/*      */       } 
/*      */       
/*  630 */       this.mcServer.getConfigurationManager().transferPlayerToDimension(this, dimensionId);
/*  631 */       this.lastExperience = -1;
/*  632 */       this.lastHealth = -1.0F;
/*  633 */       this.lastFoodLevel = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/*  639 */     return player.isSpectator() ? ((getSpectatingEntity() == this)) : (isSpectator() ? false : super.isSpectatedByPlayer(player));
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendTileEntityUpdate(TileEntity p_147097_1_) {
/*  644 */     if (p_147097_1_ != null) {
/*      */       
/*  646 */       Packet packet = p_147097_1_.getDescriptionPacket();
/*      */       
/*  648 */       if (packet != null)
/*      */       {
/*  650 */         this.playerNetServerHandler.sendPacket(packet);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
/*  660 */     super.onItemPickup(p_71001_1_, p_71001_2_);
/*  661 */     this.openContainer.detectAndSendChanges();
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer.EnumStatus trySleep(BlockPos bedLocation) {
/*  666 */     EntityPlayer.EnumStatus entityplayer$enumstatus = super.trySleep(bedLocation);
/*      */     
/*  668 */     if (entityplayer$enumstatus == EntityPlayer.EnumStatus.OK) {
/*      */       
/*  670 */       S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, bedLocation);
/*  671 */       getServerForPlayer().getEntityTracker().sendToAllTrackingEntity((Entity)this, (Packet)s0APacketUseBed);
/*  672 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  673 */       this.playerNetServerHandler.sendPacket((Packet)s0APacketUseBed);
/*      */     } 
/*      */     
/*  676 */     return entityplayer$enumstatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean p_70999_1_, boolean updateWorldFlag, boolean setSpawn) {
/*  684 */     if (isPlayerSleeping())
/*      */     {
/*  686 */       getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 2));
/*      */     }
/*      */     
/*  689 */     super.wakeUpPlayer(p_70999_1_, updateWorldFlag, setSpawn);
/*      */     
/*  691 */     if (this.playerNetServerHandler != null)
/*      */     {
/*  693 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/*  702 */     Entity entity = this.ridingEntity;
/*  703 */     super.mountEntity(entityIn);
/*      */     
/*  705 */     if (entityIn != entity) {
/*      */       
/*  707 */       this.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, (Entity)this, this.ridingEntity));
/*  708 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleFalling(double p_71122_1_, boolean p_71122_3_) {
/*  721 */     int i = MathHelper.floor_double(this.posX);
/*  722 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  723 */     int k = MathHelper.floor_double(this.posZ);
/*  724 */     BlockPos blockpos = new BlockPos(i, j, k);
/*  725 */     Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*      */     
/*  727 */     if (block.getMaterial() == Material.air) {
/*      */       
/*  729 */       Block block1 = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */       
/*  731 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*      */         
/*  733 */         blockpos = blockpos.down();
/*  734 */         block = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       } 
/*      */     } 
/*      */     
/*  738 */     super.updateFallState(p_71122_1_, p_71122_3_, block, blockpos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {
/*  743 */     signTile.setPlayer(this);
/*  744 */     this.playerNetServerHandler.sendPacket((Packet)new S36PacketSignEditorOpen(signTile.getPos()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getNextWindowId() {
/*  752 */     this.currentWindowId = this.currentWindowId % 100 + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {
/*  757 */     getNextWindowId();
/*  758 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
/*  759 */     this.openContainer = guiOwner.createContainer(this.inventory, this);
/*  760 */     this.openContainer.windowId = this.currentWindowId;
/*  761 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {
/*  769 */     if (this.openContainer != this.inventoryContainer)
/*      */     {
/*  771 */       closeScreen();
/*      */     }
/*      */     
/*  774 */     if (chestInventory instanceof ILockableContainer) {
/*      */       
/*  776 */       ILockableContainer ilockablecontainer = (ILockableContainer)chestInventory;
/*      */       
/*  778 */       if (ilockablecontainer.isLocked() && !canOpen(ilockablecontainer.getLockCode()) && !isSpectator()) {
/*      */         
/*  780 */         this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)new ChatComponentTranslation("container.isLocked", new Object[] { chestInventory.getDisplayName() }), (byte)2));
/*  781 */         this.playerNetServerHandler.sendPacket((Packet)new S29PacketSoundEffect("random.door_close", this.posX, this.posY, this.posZ, 1.0F, 1.0F));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  786 */     getNextWindowId();
/*      */     
/*  788 */     if (chestInventory instanceof IInteractionObject) {
/*      */       
/*  790 */       this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  791 */       this.openContainer = ((IInteractionObject)chestInventory).createContainer(this.inventory, this);
/*      */     }
/*      */     else {
/*      */       
/*  795 */       this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "minecraft:container", chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  796 */       this.openContainer = (Container)new ContainerChest(this.inventory, chestInventory, this);
/*      */     } 
/*      */     
/*  799 */     this.openContainer.windowId = this.currentWindowId;
/*  800 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {
/*  805 */     getNextWindowId();
/*  806 */     this.openContainer = (Container)new ContainerMerchant(this.inventory, villager, this.worldObj);
/*  807 */     this.openContainer.windowId = this.currentWindowId;
/*  808 */     this.openContainer.onCraftGuiOpened(this);
/*  809 */     InventoryMerchant inventoryMerchant = ((ContainerMerchant)this.openContainer).getMerchantInventory();
/*  810 */     IChatComponent ichatcomponent = villager.getDisplayName();
/*  811 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", ichatcomponent, inventoryMerchant.getSizeInventory()));
/*  812 */     MerchantRecipeList merchantrecipelist = villager.getRecipes(this);
/*      */     
/*  814 */     if (merchantrecipelist != null) {
/*      */       
/*  816 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  817 */       packetbuffer.writeInt(this.currentWindowId);
/*  818 */       merchantrecipelist.writeToBuf(packetbuffer);
/*  819 */       this.playerNetServerHandler.sendPacket((Packet)new S3FPacketCustomPayload("MC|TrList", packetbuffer));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/*  825 */     if (this.openContainer != this.inventoryContainer)
/*      */     {
/*  827 */       closeScreen();
/*      */     }
/*      */     
/*  830 */     getNextWindowId();
/*  831 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", horseInventory.getDisplayName(), horseInventory.getSizeInventory(), horse.getEntityId()));
/*  832 */     this.openContainer = (Container)new ContainerHorseInventory(this.inventory, horseInventory, horse, this);
/*  833 */     this.openContainer.windowId = this.currentWindowId;
/*  834 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {
/*  842 */     Item item = bookStack.getItem();
/*      */     
/*  844 */     if (item == Items.written_book)
/*      */     {
/*  846 */       this.playerNetServerHandler.sendPacket((Packet)new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/*  860 */     if (!(containerToSend.getSlot(slotInd) instanceof net.minecraft.inventory.SlotCrafting))
/*      */     {
/*  862 */       if (!this.isChangingQuantityOnly)
/*      */       {
/*  864 */         this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(containerToSend.windowId, slotInd, stack));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendContainerToPlayer(Container p_71120_1_) {
/*  871 */     updateCraftingInventory(p_71120_1_, p_71120_1_.getInventory());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
/*  882 */     this.playerNetServerHandler.sendPacket((Packet)new S30PacketWindowItems(containerToSend.windowId, itemsList));
/*  883 */     this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
/*  897 */     this.playerNetServerHandler.sendPacket((Packet)new S31PacketWindowProperty(containerIn.windowId, varToUpdate, newValue));
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175173_a(Container p_175173_1_, IInventory p_175173_2_) {
/*  902 */     for (int i = 0; i < p_175173_2_.getFieldCount(); i++)
/*      */     {
/*  904 */       this.playerNetServerHandler.sendPacket((Packet)new S31PacketWindowProperty(p_175173_1_.windowId, i, p_175173_2_.getField(i)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeScreen() {
/*  913 */     this.playerNetServerHandler.sendPacket((Packet)new S2EPacketCloseWindow(this.openContainer.windowId));
/*  914 */     closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateHeldItem() {
/*  922 */     if (!this.isChangingQuantityOnly)
/*      */     {
/*  924 */       this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeContainer() {
/*  933 */     this.openContainer.onContainerClosed(this);
/*  934 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityActionState(float p_110430_1_, float p_110430_2_, boolean p_110430_3_, boolean sneaking) {
/*  939 */     if (this.ridingEntity != null) {
/*      */       
/*  941 */       if (p_110430_1_ >= -1.0F && p_110430_1_ <= 1.0F)
/*      */       {
/*  943 */         this.moveStrafing = p_110430_1_;
/*      */       }
/*      */       
/*  946 */       if (p_110430_2_ >= -1.0F && p_110430_2_ <= 1.0F)
/*      */       {
/*  948 */         this.moveForward = p_110430_2_;
/*      */       }
/*      */       
/*  951 */       this.isJumping = p_110430_3_;
/*  952 */       setSneaking(sneaking);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {
/*  961 */     if (stat != null) {
/*      */       
/*  963 */       this.statsFile.increaseStat(this, stat, amount);
/*      */       
/*  965 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(stat.func_150952_k()))
/*      */       {
/*  967 */         getWorldScoreboard().getValueFromObjective(getCommandSenderName(), scoreobjective).increseScore(amount);
/*      */       }
/*      */       
/*  970 */       if (this.statsFile.func_150879_e())
/*      */       {
/*  972 */         this.statsFile.func_150876_a(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175145_a(StatBase p_175145_1_) {
/*  979 */     if (p_175145_1_ != null) {
/*      */       
/*  981 */       this.statsFile.unlockAchievement(this, p_175145_1_, 0);
/*      */       
/*  983 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(p_175145_1_.func_150952_k()))
/*      */       {
/*  985 */         getWorldScoreboard().getValueFromObjective(getCommandSenderName(), scoreobjective).setScorePoints(0);
/*      */       }
/*      */       
/*  988 */       if (this.statsFile.func_150879_e())
/*      */       {
/*  990 */         this.statsFile.func_150876_a(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void mountEntityAndWakeUp() {
/*  997 */     if (this.riddenByEntity != null)
/*      */     {
/*  999 */       this.riddenByEntity.mountEntity((Entity)this);
/*      */     }
/*      */     
/* 1002 */     if (this.sleeping)
/*      */     {
/* 1004 */       wakeUpPlayer(true, false, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerHealthUpdated() {
/* 1014 */     this.lastHealth = -1.0E8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {
/* 1019 */     this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat(chatComponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/* 1027 */     this.playerNetServerHandler.sendPacket((Packet)new S19PacketEntityStatus((Entity)this, (byte)9));
/* 1028 */     super.onItemUseFinish();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack, int duration) {
/* 1036 */     super.setItemInUse(stack, duration);
/*      */     
/* 1038 */     if (stack != null && stack.getItem() != null && stack.getItem().getItemUseAction(stack) == EnumAction.EAT)
/*      */     {
/* 1040 */       getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 3));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
/* 1050 */     super.clonePlayer(oldPlayer, respawnFromEnd);
/* 1051 */     this.lastExperience = -1;
/* 1052 */     this.lastHealth = -1.0F;
/* 1053 */     this.lastFoodLevel = -1;
/* 1054 */     this.destroyedItemsNetCache.addAll(((EntityPlayerMP)oldPlayer).destroyedItemsNetCache);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/* 1059 */     super.onNewPotionEffect(id);
/* 1060 */     this.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(getEntityId(), id));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/* 1065 */     super.onChangedPotionEffect(id, p_70695_2_);
/* 1066 */     this.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(getEntityId(), id));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect p_70688_1_) {
/* 1071 */     super.onFinishedPotionEffect(p_70688_1_);
/* 1072 */     this.playerNetServerHandler.sendPacket((Packet)new S1EPacketRemoveEntityEffect(getEntityId(), p_70688_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 1080 */     this.playerNetServerHandler.setPlayerLocation(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {
/* 1088 */     getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation(entityHit, 4));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {
/* 1093 */     getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation(entityHit, 5));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {
/* 1101 */     if (this.playerNetServerHandler != null) {
/*      */       
/* 1103 */       this.playerNetServerHandler.sendPacket((Packet)new S39PacketPlayerAbilities(this.capabilities));
/* 1104 */       updatePotionMetadata();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldServer getServerForPlayer() {
/* 1110 */     return (WorldServer)this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameType) {
/* 1118 */     this.theItemInWorldManager.setGameType(gameType);
/* 1119 */     this.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(3, gameType.getID()));
/*      */     
/* 1121 */     if (gameType == WorldSettings.GameType.SPECTATOR) {
/*      */       
/* 1123 */       mountEntity((Entity)null);
/*      */     }
/*      */     else {
/*      */       
/* 1127 */       setSpectatingEntity((Entity)this);
/*      */     } 
/*      */     
/* 1130 */     sendPlayerAbilities();
/* 1131 */     markPotionsDirty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpectator() {
/* 1139 */     return (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/* 1149 */     this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat(component));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 1160 */     if ("seed".equals(commandName) && !this.mcServer.isDedicatedServer())
/*      */     {
/* 1162 */       return true;
/*      */     }
/* 1164 */     if (!"tell".equals(commandName) && !"help".equals(commandName) && !"me".equals(commandName) && !"trigger".equals(commandName)) {
/*      */       
/* 1166 */       if (this.mcServer.getConfigurationManager().canSendCommands(getGameProfile())) {
/*      */         
/* 1168 */         UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(getGameProfile());
/* 1169 */         return (userlistopsentry != null) ? ((userlistopsentry.getPermissionLevel() >= permLevel)) : ((this.mcServer.getOpPermissionLevel() >= permLevel));
/*      */       } 
/*      */ 
/*      */       
/* 1173 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1178 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlayerIP() {
/* 1187 */     String s = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
/* 1188 */     s = s.substring(s.indexOf("/") + 1);
/* 1189 */     s = s.substring(0, s.indexOf(":"));
/* 1190 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleClientSettings(C15PacketClientSettings packetIn) {
/* 1195 */     this.translator = packetIn.getLang();
/* 1196 */     this.chatVisibility = packetIn.getChatVisibility();
/* 1197 */     this.chatColours = packetIn.isColorsEnabled();
/* 1198 */     getDataWatcher().updateObject(10, Byte.valueOf((byte)packetIn.getModelPartFlags()));
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 1203 */     return this.chatVisibility;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadResourcePack(String url, String hash) {
/* 1208 */     this.playerNetServerHandler.sendPacket((Packet)new S48PacketResourcePackSend(url, hash));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1217 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markPlayerActive() {
/* 1222 */     this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StatisticsFile getStatFile() {
/* 1230 */     return this.statsFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity p_152339_1_) {
/* 1238 */     if (p_152339_1_ instanceof EntityPlayer) {
/*      */       
/* 1240 */       this.playerNetServerHandler.sendPacket((Packet)new S13PacketDestroyEntities(new int[] { p_152339_1_.getEntityId() }));
/*      */     }
/*      */     else {
/*      */       
/* 1244 */       this.destroyedItemsNetCache.add(Integer.valueOf(p_152339_1_.getEntityId()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/* 1254 */     if (isSpectator()) {
/*      */       
/* 1256 */       resetPotionEffectMetadata();
/* 1257 */       setInvisible(true);
/*      */     }
/*      */     else {
/*      */       
/* 1261 */       super.updatePotionMetadata();
/*      */     } 
/*      */     
/* 1264 */     getServerForPlayer().getEntityTracker().func_180245_a(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getSpectatingEntity() {
/* 1269 */     return (this.spectatingEntity == null) ? (Entity)this : this.spectatingEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpectatingEntity(Entity entityToSpectate) {
/* 1274 */     Entity entity = getSpectatingEntity();
/* 1275 */     this.spectatingEntity = (entityToSpectate == null) ? (Entity)this : entityToSpectate;
/*      */     
/* 1277 */     if (entity != this.spectatingEntity) {
/*      */       
/* 1279 */       this.playerNetServerHandler.sendPacket((Packet)new S43PacketCamera(this.spectatingEntity));
/* 1280 */       setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1290 */     if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
/*      */       
/* 1292 */       setSpectatingEntity(targetEntity);
/*      */     }
/*      */     else {
/*      */       
/* 1296 */       super.attackTargetEntityWithCurrentItem(targetEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLastActiveTime() {
/* 1302 */     return this.playerLastActiveTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getTabListDisplayName() {
/* 1311 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\player\EntityPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */