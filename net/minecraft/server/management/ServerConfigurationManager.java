/*      */ package net.minecraft.server.management;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.io.File;
/*      */ import java.net.SocketAddress;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetHandlerPlayServer;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3EPacketTeams;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*      */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.ServerScoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.stats.StatisticsFile;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.border.IBorderListener;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.storage.IPlayerFileData;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class ServerConfigurationManager
/*      */ {
/*   64 */   public static final File FILE_PLAYERBANS = new File("banned-players.json");
/*   65 */   public static final File FILE_IPBANS = new File("banned-ips.json");
/*   66 */   public static final File FILE_OPS = new File("ops.json");
/*   67 */   public static final File FILE_WHITELIST = new File("whitelist.json");
/*   68 */   private static final Logger logger = LogManager.getLogger();
/*   69 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*      */   
/*      */   private final MinecraftServer mcServer;
/*      */   
/*   73 */   private final List<EntityPlayerMP> playerEntityList = Lists.newArrayList();
/*   74 */   private final Map<UUID, EntityPlayerMP> uuidToPlayerMap = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   private final UserListBans bannedPlayers;
/*      */ 
/*      */   
/*      */   private final BanList bannedIPs;
/*      */ 
/*      */   
/*      */   private final UserListOps ops;
/*      */ 
/*      */   
/*      */   private final UserListWhitelist whiteListedPlayers;
/*      */ 
/*      */   
/*      */   private final Map<UUID, StatisticsFile> playerStatFiles;
/*      */ 
/*      */   
/*      */   private IPlayerFileData playerNBTManagerObj;
/*      */   
/*      */   private boolean whiteListEnforced;
/*      */   
/*      */   protected int maxPlayers;
/*      */   
/*      */   private int viewDistance;
/*      */   
/*      */   private WorldSettings.GameType gameType;
/*      */   
/*      */   private boolean commandsAllowedForAll;
/*      */   
/*      */   private int playerPingIndex;
/*      */ 
/*      */   
/*      */   public ServerConfigurationManager(MinecraftServer server) {
/*  108 */     this.bannedPlayers = new UserListBans(FILE_PLAYERBANS);
/*  109 */     this.bannedIPs = new BanList(FILE_IPBANS);
/*  110 */     this.ops = new UserListOps(FILE_OPS);
/*  111 */     this.whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
/*  112 */     this.playerStatFiles = Maps.newHashMap();
/*  113 */     this.mcServer = server;
/*  114 */     this.bannedPlayers.setLanServer(false);
/*  115 */     this.bannedIPs.setLanServer(false);
/*  116 */     this.maxPlayers = 8;
/*      */   }
/*      */   
/*      */   public void initializeConnectionToPlayer(NetworkManager netManager, EntityPlayerMP playerIn) {
/*      */     ChatComponentTranslation chatcomponenttranslation;
/*  121 */     GameProfile gameprofile = playerIn.getGameProfile();
/*  122 */     PlayerProfileCache playerprofilecache = this.mcServer.getPlayerProfileCache();
/*  123 */     GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
/*  124 */     String s = (gameprofile1 == null) ? gameprofile.getName() : gameprofile1.getName();
/*  125 */     playerprofilecache.addEntry(gameprofile);
/*  126 */     NBTTagCompound nbttagcompound = readPlayerDataFromFile(playerIn);
/*  127 */     playerIn.setWorld((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*  128 */     playerIn.theItemInWorldManager.setWorld((WorldServer)playerIn.worldObj);
/*  129 */     String s1 = "local";
/*      */     
/*  131 */     if (netManager.getRemoteAddress() != null)
/*      */     {
/*  133 */       s1 = netManager.getRemoteAddress().toString();
/*      */     }
/*      */     
/*  136 */     logger.info(playerIn.getCommandSenderName() + "[" + s1 + "] logged in with entity id " + playerIn.getEntityId() + " at (" + playerIn.posX + ", " + playerIn.posY + ", " + playerIn.posZ + ")");
/*  137 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  138 */     WorldInfo worldinfo = worldserver.getWorldInfo();
/*  139 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  140 */     setPlayerGameTypeBasedOnOther(playerIn, null, (World)worldserver);
/*  141 */     NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.mcServer, netManager, playerIn);
/*  142 */     nethandlerplayserver.sendPacket((Packet)new S01PacketJoinGame(playerIn.getEntityId(), playerIn.theItemInWorldManager.getGameType(), worldinfo.isHardcoreModeEnabled(), worldserver.provider.getDimensionId(), worldserver.getDifficulty(), getMaxPlayers(), worldinfo.getTerrainType(), worldserver.getGameRules().getGameRuleBooleanValue("reducedDebugInfo")));
/*  143 */     nethandlerplayserver.sendPacket((Packet)new S3FPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(getServerInstance().getServerModName())));
/*  144 */     nethandlerplayserver.sendPacket((Packet)new S41PacketServerDifficulty(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
/*  145 */     nethandlerplayserver.sendPacket((Packet)new S05PacketSpawnPosition(blockpos));
/*  146 */     nethandlerplayserver.sendPacket((Packet)new S39PacketPlayerAbilities(playerIn.capabilities));
/*  147 */     nethandlerplayserver.sendPacket((Packet)new S09PacketHeldItemChange(playerIn.inventory.currentItem));
/*  148 */     playerIn.getStatFile().func_150877_d();
/*  149 */     playerIn.getStatFile().sendAchievements(playerIn);
/*  150 */     sendScoreboard((ServerScoreboard)worldserver.getScoreboard(), playerIn);
/*  151 */     this.mcServer.refreshStatusNextTick();
/*      */ 
/*      */     
/*  154 */     if (!playerIn.getCommandSenderName().equalsIgnoreCase(s)) {
/*      */       
/*  156 */       chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { playerIn.getDisplayName(), s });
/*      */     }
/*      */     else {
/*      */       
/*  160 */       chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { playerIn.getDisplayName() });
/*      */     } 
/*      */     
/*  163 */     chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.YELLOW);
/*  164 */     sendChatMsg((IChatComponent)chatcomponenttranslation);
/*  165 */     playerLoggedIn(playerIn);
/*  166 */     nethandlerplayserver.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/*  167 */     updateTimeAndWeatherForPlayer(playerIn, worldserver);
/*      */     
/*  169 */     if (this.mcServer.getResourcePackUrl().length() > 0)
/*      */     {
/*  171 */       playerIn.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
/*      */     }
/*      */     
/*  174 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects())
/*      */     {
/*  176 */       nethandlerplayserver.sendPacket((Packet)new S1DPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*      */     }
/*      */     
/*  179 */     playerIn.addSelfToInternalCraftingInventory();
/*      */     
/*  181 */     if (nbttagcompound != null && nbttagcompound.hasKey("Riding", 10)) {
/*      */       
/*  183 */       Entity entity = EntityList.createEntityFromNBT(nbttagcompound.getCompoundTag("Riding"), (World)worldserver);
/*      */       
/*  185 */       if (entity != null) {
/*      */         
/*  187 */         entity.forceSpawn = true;
/*  188 */         worldserver.spawnEntityInWorld(entity);
/*  189 */         playerIn.mountEntity(entity);
/*  190 */         entity.forceSpawn = false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendScoreboard(ServerScoreboard scoreboardIn, EntityPlayerMP playerIn) {
/*  197 */     Set<ScoreObjective> set = Sets.newHashSet();
/*      */     
/*  199 */     for (ScorePlayerTeam scoreplayerteam : scoreboardIn.getTeams())
/*      */     {
/*  201 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S3EPacketTeams(scoreplayerteam, 0));
/*      */     }
/*      */     
/*  204 */     for (int i = 0; i < 19; i++) {
/*      */       
/*  206 */       ScoreObjective scoreobjective = scoreboardIn.getObjectiveInDisplaySlot(i);
/*      */       
/*  208 */       if (scoreobjective != null && !set.contains(scoreobjective)) {
/*      */         
/*  210 */         for (Packet packet : scoreboardIn.func_96550_d(scoreobjective))
/*      */         {
/*  212 */           playerIn.playerNetServerHandler.sendPacket(packet);
/*      */         }
/*      */         
/*  215 */         set.add(scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerManager(WorldServer[] worldServers) {
/*  225 */     this.playerNBTManagerObj = worldServers[0].getSaveHandler().getPlayerNBTManager();
/*  226 */     worldServers[0].getWorldBorder().addListener(new IBorderListener()
/*      */         {
/*      */           public void onSizeChanged(WorldBorder border, double newSize)
/*      */           {
/*  230 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
/*      */           }
/*      */           
/*      */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/*  234 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
/*      */           }
/*      */           
/*      */           public void onCenterChanged(WorldBorder border, double x, double z) {
/*  238 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
/*      */           }
/*      */           
/*      */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/*  242 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
/*      */           }
/*      */           
/*      */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/*  246 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
/*      */           }
/*      */ 
/*      */           
/*      */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {}
/*      */ 
/*      */           
/*      */           public void onDamageBufferChanged(WorldBorder border, double newSize) {}
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void preparePlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/*  259 */     WorldServer worldserver = playerIn.getServerForPlayer();
/*      */     
/*  261 */     if (worldIn != null)
/*      */     {
/*  263 */       worldIn.getPlayerManager().removePlayer(playerIn);
/*      */     }
/*      */     
/*  266 */     worldserver.getPlayerManager().addPlayer(playerIn);
/*  267 */     worldserver.theChunkProviderServer.loadChunk((int)playerIn.posX >> 4, (int)playerIn.posZ >> 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getEntityViewDistance() {
/*  272 */     return PlayerManager.getFurthestViewableBlock(getViewDistance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP playerIn) {
/*  280 */     NBTTagCompound nbttagcompound1, nbttagcompound = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
/*      */ 
/*      */     
/*  283 */     if (playerIn.getCommandSenderName().equals(this.mcServer.getServerOwner()) && nbttagcompound != null) {
/*      */       
/*  285 */       playerIn.readFromNBT(nbttagcompound);
/*  286 */       nbttagcompound1 = nbttagcompound;
/*  287 */       logger.debug("loading single player");
/*      */     }
/*      */     else {
/*      */       
/*  291 */       nbttagcompound1 = this.playerNBTManagerObj.readPlayerData((EntityPlayer)playerIn);
/*      */     } 
/*      */     
/*  294 */     return nbttagcompound1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePlayerData(EntityPlayerMP playerIn) {
/*  302 */     this.playerNBTManagerObj.writePlayerData((EntityPlayer)playerIn);
/*  303 */     StatisticsFile statisticsfile = this.playerStatFiles.get(playerIn.getUniqueID());
/*      */     
/*  305 */     if (statisticsfile != null)
/*      */     {
/*  307 */       statisticsfile.saveStatFile();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playerLoggedIn(EntityPlayerMP playerIn) {
/*  316 */     this.playerEntityList.add(playerIn);
/*  317 */     this.uuidToPlayerMap.put(playerIn.getUniqueID(), playerIn);
/*  318 */     sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { playerIn }));
/*  319 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  320 */     worldserver.spawnEntityInWorld((Entity)playerIn);
/*  321 */     preparePlayer(playerIn, null);
/*      */     
/*  323 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  325 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*  326 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { entityplayermp }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serverUpdateMountedMovingPlayer(EntityPlayerMP playerIn) {
/*  335 */     playerIn.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playerLoggedOut(EntityPlayerMP playerIn) {
/*  343 */     playerIn.triggerAchievement(StatList.leaveGameStat);
/*  344 */     writePlayerData(playerIn);
/*  345 */     WorldServer worldserver = playerIn.getServerForPlayer();
/*      */     
/*  347 */     if (playerIn.ridingEntity != null) {
/*      */       
/*  349 */       worldserver.removePlayerEntityDangerously(playerIn.ridingEntity);
/*  350 */       logger.debug("removing player mount");
/*      */     } 
/*      */     
/*  353 */     worldserver.removeEntity((Entity)playerIn);
/*  354 */     worldserver.getPlayerManager().removePlayer(playerIn);
/*  355 */     this.playerEntityList.remove(playerIn);
/*  356 */     UUID uuid = playerIn.getUniqueID();
/*  357 */     EntityPlayerMP entityplayermp = this.uuidToPlayerMap.get(uuid);
/*      */     
/*  359 */     if (entityplayermp == playerIn) {
/*      */       
/*  361 */       this.uuidToPlayerMap.remove(uuid);
/*  362 */       this.playerStatFiles.remove(uuid);
/*      */     } 
/*      */     
/*  365 */     sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { playerIn }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/*  373 */     if (this.bannedPlayers.isBanned(profile)) {
/*      */       
/*  375 */       UserListBansEntry userlistbansentry = this.bannedPlayers.getEntry(profile);
/*  376 */       String s1 = "You are banned from this server!\nReason: " + userlistbansentry.getBanReason();
/*      */       
/*  378 */       if (userlistbansentry.getBanEndDate() != null)
/*      */       {
/*  380 */         s1 = s1 + "\nYour ban will be removed on " + dateFormat.format(userlistbansentry.getBanEndDate());
/*      */       }
/*      */       
/*  383 */       return s1;
/*      */     } 
/*  385 */     if (!canJoin(profile))
/*      */     {
/*  387 */       return "You are not white-listed on this server!";
/*      */     }
/*  389 */     if (this.bannedIPs.isBanned(address)) {
/*      */       
/*  391 */       IPBanEntry ipbanentry = this.bannedIPs.getBanEntry(address);
/*  392 */       String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getBanReason();
/*      */       
/*  394 */       if (ipbanentry.getBanEndDate() != null)
/*      */       {
/*  396 */         s = s + "\nYour ban will be removed on " + dateFormat.format(ipbanentry.getBanEndDate());
/*      */       }
/*      */       
/*  399 */       return s;
/*      */     } 
/*      */ 
/*      */     
/*  403 */     return (this.playerEntityList.size() >= this.maxPlayers && !func_183023_f(profile)) ? "The server is full!" : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP createPlayerForUser(GameProfile profile) {
/*  412 */     UUID uuid = EntityPlayer.getUUID(profile);
/*  413 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*      */     
/*  415 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  417 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  419 */       if (entityplayermp.getUniqueID().equals(uuid))
/*      */       {
/*  421 */         list.add(entityplayermp);
/*      */       }
/*      */     } 
/*      */     
/*  425 */     EntityPlayerMP entityplayermp2 = this.uuidToPlayerMap.get(profile.getId());
/*      */     
/*  427 */     if (entityplayermp2 != null && !list.contains(entityplayermp2))
/*      */     {
/*  429 */       list.add(entityplayermp2);
/*      */     }
/*      */     
/*  432 */     for (EntityPlayerMP entityplayermp1 : list)
/*      */     {
/*  434 */       entityplayermp1.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
/*      */     }
/*      */ 
/*      */     
/*  438 */     ItemInWorldManager iteminworldmanager = new ItemInWorldManager((World)this.mcServer.worldServerForDimension(0));
/*      */     
/*  440 */     return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), profile, iteminworldmanager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP playerIn, int dimension, boolean conqueredEnd) {
/*  448 */     playerIn.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(playerIn);
/*  449 */     playerIn.getServerForPlayer().getEntityTracker().untrackEntity((Entity)playerIn);
/*  450 */     playerIn.getServerForPlayer().getPlayerManager().removePlayer(playerIn);
/*  451 */     this.playerEntityList.remove(playerIn);
/*  452 */     this.mcServer.worldServerForDimension(playerIn.dimension).removePlayerEntityDangerously((Entity)playerIn);
/*  453 */     BlockPos blockpos = playerIn.getBedLocation();
/*  454 */     boolean flag = playerIn.isSpawnForced();
/*  455 */     playerIn.dimension = dimension;
/*      */     
/*  457 */     ItemInWorldManager iteminworldmanager = new ItemInWorldManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*      */     
/*  459 */     EntityPlayerMP entityplayermp = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(playerIn.dimension), playerIn.getGameProfile(), iteminworldmanager);
/*  460 */     entityplayermp.playerNetServerHandler = playerIn.playerNetServerHandler;
/*  461 */     entityplayermp.clonePlayer((EntityPlayer)playerIn, conqueredEnd);
/*  462 */     entityplayermp.setEntityId(playerIn.getEntityId());
/*  463 */     entityplayermp.func_174817_o((Entity)playerIn);
/*  464 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  465 */     setPlayerGameTypeBasedOnOther(entityplayermp, playerIn, (World)worldserver);
/*      */     
/*  467 */     if (blockpos != null) {
/*      */       
/*  469 */       BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation((World)this.mcServer.worldServerForDimension(playerIn.dimension), blockpos, flag);
/*      */       
/*  471 */       if (blockpos1 != null) {
/*      */         
/*  473 */         entityplayermp.setLocationAndAngles((blockpos1.getX() + 0.5F), (blockpos1.getY() + 0.1F), (blockpos1.getZ() + 0.5F), 0.0F, 0.0F);
/*  474 */         entityplayermp.setSpawnPoint(blockpos, flag);
/*      */       }
/*      */       else {
/*      */         
/*  478 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(0, 0.0F));
/*      */       } 
/*      */     } 
/*      */     
/*  482 */     worldserver.theChunkProviderServer.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);
/*      */     
/*  484 */     while (!worldserver.getCollidingBoundingBoxes((Entity)entityplayermp, entityplayermp.getEntityBoundingBox()).isEmpty() && entityplayermp.posY < 256.0D)
/*      */     {
/*  486 */       entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ);
/*      */     }
/*      */     
/*  489 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S07PacketRespawn(entityplayermp.dimension, entityplayermp.worldObj.getDifficulty(), entityplayermp.worldObj.getWorldInfo().getTerrainType(), entityplayermp.theItemInWorldManager.getGameType()));
/*  490 */     BlockPos blockpos2 = worldserver.getSpawnPoint();
/*  491 */     entityplayermp.playerNetServerHandler.setPlayerLocation(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
/*  492 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S05PacketSpawnPosition(blockpos2));
/*  493 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S1FPacketSetExperience(entityplayermp.experience, entityplayermp.experienceTotal, entityplayermp.experienceLevel));
/*  494 */     updateTimeAndWeatherForPlayer(entityplayermp, worldserver);
/*  495 */     worldserver.getPlayerManager().addPlayer(entityplayermp);
/*  496 */     worldserver.spawnEntityInWorld((Entity)entityplayermp);
/*  497 */     this.playerEntityList.add(entityplayermp);
/*  498 */     this.uuidToPlayerMap.put(entityplayermp.getUniqueID(), entityplayermp);
/*  499 */     entityplayermp.addSelfToInternalCraftingInventory();
/*  500 */     entityplayermp.setHealth(entityplayermp.getHealth());
/*  501 */     return entityplayermp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferPlayerToDimension(EntityPlayerMP playerIn, int dimension) {
/*  509 */     int i = playerIn.dimension;
/*  510 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  511 */     playerIn.dimension = dimension;
/*  512 */     WorldServer worldserver1 = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  513 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S07PacketRespawn(playerIn.dimension, playerIn.worldObj.getDifficulty(), playerIn.worldObj.getWorldInfo().getTerrainType(), playerIn.theItemInWorldManager.getGameType()));
/*  514 */     worldserver.removePlayerEntityDangerously((Entity)playerIn);
/*  515 */     playerIn.isDead = false;
/*  516 */     transferEntityToWorld((Entity)playerIn, i, worldserver, worldserver1);
/*  517 */     preparePlayer(playerIn, worldserver);
/*  518 */     playerIn.playerNetServerHandler.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/*  519 */     playerIn.theItemInWorldManager.setWorld(worldserver1);
/*  520 */     updateTimeAndWeatherForPlayer(playerIn, worldserver1);
/*  521 */     syncPlayerInventory(playerIn);
/*      */     
/*  523 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects())
/*      */     {
/*  525 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferEntityToWorld(Entity entityIn, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_) {
/*  534 */     double d0 = entityIn.posX;
/*  535 */     double d1 = entityIn.posZ;
/*  536 */     double d2 = 8.0D;
/*  537 */     float f = entityIn.rotationYaw;
/*  538 */     p_82448_3_.theProfiler.startSection("moving");
/*      */     
/*  540 */     if (entityIn.dimension == -1) {
/*      */       
/*  542 */       d0 = MathHelper.clamp_double(d0 / d2, p_82448_4_.getWorldBorder().minX() + 16.0D, p_82448_4_.getWorldBorder().maxX() - 16.0D);
/*  543 */       d1 = MathHelper.clamp_double(d1 / d2, p_82448_4_.getWorldBorder().minZ() + 16.0D, p_82448_4_.getWorldBorder().maxZ() - 16.0D);
/*  544 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */       
/*  546 */       if (entityIn.isEntityAlive())
/*      */       {
/*  548 */         p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     }
/*  551 */     else if (entityIn.dimension == 0) {
/*      */       
/*  553 */       d0 = MathHelper.clamp_double(d0 * d2, p_82448_4_.getWorldBorder().minX() + 16.0D, p_82448_4_.getWorldBorder().maxX() - 16.0D);
/*  554 */       d1 = MathHelper.clamp_double(d1 * d2, p_82448_4_.getWorldBorder().minZ() + 16.0D, p_82448_4_.getWorldBorder().maxZ() - 16.0D);
/*  555 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */       
/*  557 */       if (entityIn.isEntityAlive())
/*      */       {
/*  559 */         p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     } else {
/*      */       BlockPos blockpos;
/*      */ 
/*      */ 
/*      */       
/*  566 */       if (p_82448_2_ == 1) {
/*      */         
/*  568 */         blockpos = p_82448_4_.getSpawnPoint();
/*      */       }
/*      */       else {
/*      */         
/*  572 */         blockpos = p_82448_4_.getSpawnCoordinate();
/*      */       } 
/*      */       
/*  575 */       d0 = blockpos.getX();
/*  576 */       entityIn.posY = blockpos.getY();
/*  577 */       d1 = blockpos.getZ();
/*  578 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, 90.0F, 0.0F);
/*      */       
/*  580 */       if (entityIn.isEntityAlive())
/*      */       {
/*  582 */         p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     } 
/*      */     
/*  586 */     p_82448_3_.theProfiler.endSection();
/*      */     
/*  588 */     if (p_82448_2_ != 1) {
/*      */       
/*  590 */       p_82448_3_.theProfiler.startSection("placing");
/*  591 */       d0 = MathHelper.clamp_int((int)d0, -29999872, 29999872);
/*  592 */       d1 = MathHelper.clamp_int((int)d1, -29999872, 29999872);
/*      */       
/*  594 */       if (entityIn.isEntityAlive()) {
/*      */         
/*  596 */         entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*  597 */         p_82448_4_.getDefaultTeleporter().placeInPortal(entityIn, f);
/*  598 */         p_82448_4_.spawnEntityInWorld(entityIn);
/*  599 */         p_82448_4_.updateEntityWithOptionalForce(entityIn, false);
/*      */       } 
/*      */       
/*  602 */       p_82448_3_.theProfiler.endSection();
/*      */     } 
/*      */     
/*  605 */     entityIn.setWorld((World)p_82448_4_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  613 */     if (++this.playerPingIndex > 600) {
/*      */       
/*  615 */       sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
/*  616 */       this.playerPingIndex = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToAllPlayers(Packet packetIn) {
/*  622 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  624 */       ((EntityPlayerMP)this.playerEntityList.get(i)).playerNetServerHandler.sendPacket(packetIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToAllPlayersInDimension(Packet packetIn, int dimension) {
/*  630 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  632 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  634 */       if (entityplayermp.dimension == dimension)
/*      */       {
/*  636 */         entityplayermp.playerNetServerHandler.sendPacket(packetIn);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessageToAllTeamMembers(EntityPlayer player, IChatComponent message) {
/*  643 */     Team team = player.getTeam();
/*      */     
/*  645 */     if (team != null)
/*      */     {
/*  647 */       for (String s : team.getMembershipCollection()) {
/*      */         
/*  649 */         EntityPlayerMP entityplayermp = getPlayerByUsername(s);
/*      */         
/*  651 */         if (entityplayermp != null && entityplayermp != player)
/*      */         {
/*  653 */           entityplayermp.addChatMessage(message);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessageToTeamOrEvryPlayer(EntityPlayer player, IChatComponent message) {
/*  661 */     Team team = player.getTeam();
/*      */     
/*  663 */     if (team == null) {
/*      */       
/*  665 */       sendChatMsg(message);
/*      */     }
/*      */     else {
/*      */       
/*  669 */       for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */         
/*  671 */         EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */         
/*  673 */         if (entityplayermp.getTeam() != team)
/*      */         {
/*  675 */           entityplayermp.addChatMessage(message);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String func_181058_b(boolean p_181058_1_) {
/*  683 */     String s = "";
/*  684 */     List<EntityPlayerMP> list = Lists.newArrayList(this.playerEntityList);
/*      */     
/*  686 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  688 */       if (i > 0)
/*      */       {
/*  690 */         s = s + ", ";
/*      */       }
/*      */       
/*  693 */       s = s + ((EntityPlayerMP)list.get(i)).getCommandSenderName();
/*      */       
/*  695 */       if (p_181058_1_)
/*      */       {
/*  697 */         s = s + " (" + ((EntityPlayerMP)list.get(i)).getUniqueID().toString() + ")";
/*      */       }
/*      */     } 
/*      */     
/*  701 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  709 */     String[] astring = new String[this.playerEntityList.size()];
/*      */     
/*  711 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  713 */       astring[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getCommandSenderName();
/*      */     }
/*      */     
/*  716 */     return astring;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfile[] getAllProfiles() {
/*  721 */     GameProfile[] agameprofile = new GameProfile[this.playerEntityList.size()];
/*      */     
/*  723 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  725 */       agameprofile[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getGameProfile();
/*      */     }
/*      */     
/*  728 */     return agameprofile;
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListBans getBannedPlayers() {
/*  733 */     return this.bannedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public BanList getBannedIPs() {
/*  738 */     return this.bannedIPs;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addOp(GameProfile profile) {
/*  743 */     this.ops.addEntry(new UserListOpsEntry(profile, this.mcServer.getOpPermissionLevel(), this.ops.func_183026_b(profile)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeOp(GameProfile profile) {
/*  748 */     this.ops.removeEntry(profile);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canJoin(GameProfile profile) {
/*  753 */     return (!this.whiteListEnforced || this.ops.hasEntry(profile) || this.whiteListedPlayers.hasEntry(profile));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSendCommands(GameProfile profile) {
/*  758 */     return (this.ops.hasEntry(profile) || (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(profile.getName())) || this.commandsAllowedForAll);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayerMP getPlayerByUsername(String username) {
/*  763 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/*      */       
/*  765 */       if (entityplayermp.getCommandSenderName().equalsIgnoreCase(username))
/*      */       {
/*  767 */         return entityplayermp;
/*      */       }
/*      */     } 
/*      */     
/*  771 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendToAllNear(double x, double y, double z, double radius, int dimension, Packet packetIn) {
/*  779 */     sendToAllNearExcept(null, x, y, z, radius, dimension, packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendToAllNearExcept(EntityPlayer p_148543_1_, double x, double y, double z, double radius, int dimension, Packet p_148543_11_) {
/*  788 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  790 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  792 */       if (entityplayermp != p_148543_1_ && entityplayermp.dimension == dimension) {
/*      */         
/*  794 */         double d0 = x - entityplayermp.posX;
/*  795 */         double d1 = y - entityplayermp.posY;
/*  796 */         double d2 = z - entityplayermp.posZ;
/*      */         
/*  798 */         if (d0 * d0 + d1 * d1 + d2 * d2 < radius * radius)
/*      */         {
/*  800 */           entityplayermp.playerNetServerHandler.sendPacket(p_148543_11_);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAllPlayerData() {
/*  811 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  813 */       writePlayerData(this.playerEntityList.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addWhitelistedPlayer(GameProfile profile) {
/*  819 */     this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(profile));
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePlayerFromWhitelist(GameProfile profile) {
/*  824 */     this.whiteListedPlayers.removeEntry(profile);
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListWhitelist getWhitelistedPlayers() {
/*  829 */     return this.whiteListedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getWhitelistedPlayerNames() {
/*  834 */     return this.whiteListedPlayers.getKeys();
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListOps getOppedPlayers() {
/*  839 */     return this.ops;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getOppedPlayerNames() {
/*  844 */     return this.ops.getKeys();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWhiteList() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTimeAndWeatherForPlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/*  859 */     WorldBorder worldborder = this.mcServer.worldServers[0].getWorldBorder();
/*  860 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S44PacketWorldBorder(worldborder, S44PacketWorldBorder.Action.INITIALIZE));
/*  861 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S03PacketTimeUpdate(worldIn.getTotalWorldTime(), worldIn.getWorldTime(), worldIn.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
/*      */     
/*  863 */     if (worldIn.isRaining()) {
/*      */       
/*  865 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(1, 0.0F));
/*  866 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(7, worldIn.getRainStrength(1.0F)));
/*  867 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(8, worldIn.getThunderStrength(1.0F)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void syncPlayerInventory(EntityPlayerMP playerIn) {
/*  876 */     playerIn.sendContainerToPlayer(playerIn.inventoryContainer);
/*  877 */     playerIn.setPlayerHealthUpdated();
/*  878 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S09PacketHeldItemChange(playerIn.inventory.currentItem));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  886 */     return this.playerEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  894 */     return this.maxPlayers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAvailablePlayerDat() {
/*  902 */     return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWhiteListEnabled(boolean whitelistEnabled) {
/*  907 */     this.whiteListEnforced = whitelistEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<EntityPlayerMP> getPlayersMatchingAddress(String address) {
/*  912 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*      */     
/*  914 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/*      */       
/*  916 */       if (entityplayermp.getPlayerIP().equals(address))
/*      */       {
/*  918 */         list.add(entityplayermp);
/*      */       }
/*      */     } 
/*      */     
/*  922 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewDistance() {
/*  930 */     return this.viewDistance;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer getServerInstance() {
/*  935 */     return this.mcServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getHostPlayerData() {
/*  943 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType p_152604_1_) {
/*  948 */     this.gameType = p_152604_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setPlayerGameTypeBasedOnOther(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World worldIn) {
/*  953 */     if (p_72381_2_ != null) {
/*      */       
/*  955 */       p_72381_1_.theItemInWorldManager.setGameType(p_72381_2_.theItemInWorldManager.getGameType());
/*      */     }
/*  957 */     else if (this.gameType != null) {
/*      */       
/*  959 */       p_72381_1_.theItemInWorldManager.setGameType(this.gameType);
/*      */     } 
/*      */     
/*  962 */     p_72381_1_.theItemInWorldManager.initializeGameType(worldIn.getWorldInfo().getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandsAllowedForAll(boolean p_72387_1_) {
/*  970 */     this.commandsAllowedForAll = p_72387_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllPlayers() {
/*  978 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  980 */       ((EntityPlayerMP)this.playerEntityList.get(i)).playerNetServerHandler.kickPlayerFromServer("Server closed");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendChatMsgImpl(IChatComponent component, boolean isChat) {
/*  986 */     this.mcServer.addChatMessage(component);
/*  987 */     byte b0 = (byte)(isChat ? 1 : 0);
/*  988 */     sendPacketToAllPlayers((Packet)new S02PacketChat(component, b0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendChatMsg(IChatComponent component) {
/*  996 */     sendChatMsgImpl(component, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public StatisticsFile getPlayerStatsFile(EntityPlayer playerIn) {
/* 1001 */     UUID uuid = playerIn.getUniqueID();
/* 1002 */     StatisticsFile statisticsfile = (uuid == null) ? null : this.playerStatFiles.get(uuid);
/*      */     
/* 1004 */     if (statisticsfile == null) {
/*      */       
/* 1006 */       File file1 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
/* 1007 */       File file2 = new File(file1, uuid.toString() + ".json");
/*      */       
/* 1009 */       if (!file2.exists()) {
/*      */         
/* 1011 */         File file3 = new File(file1, playerIn.getCommandSenderName() + ".json");
/*      */         
/* 1013 */         if (file3.exists() && file3.isFile())
/*      */         {
/* 1015 */           file3.renameTo(file2);
/*      */         }
/*      */       } 
/*      */       
/* 1019 */       statisticsfile = new StatisticsFile(this.mcServer, file2);
/* 1020 */       statisticsfile.readStatFile();
/* 1021 */       this.playerStatFiles.put(uuid, statisticsfile);
/*      */     } 
/*      */     
/* 1024 */     return statisticsfile;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setViewDistance(int distance) {
/* 1029 */     this.viewDistance = distance;
/*      */     
/* 1031 */     if (this.mcServer.worldServers != null)
/*      */     {
/* 1033 */       for (WorldServer worldserver : this.mcServer.worldServers) {
/*      */         
/* 1035 */         if (worldserver != null)
/*      */         {
/* 1037 */           worldserver.getPlayerManager().setPlayerViewRadius(distance);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public List<EntityPlayerMP> func_181057_v() {
/* 1045 */     return this.playerEntityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP getPlayerByUUID(UUID playerUUID) {
/* 1055 */     return this.uuidToPlayerMap.get(playerUUID);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_183023_f(GameProfile p_183023_1_) {
/* 1060 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\server\management\ServerConfigurationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */