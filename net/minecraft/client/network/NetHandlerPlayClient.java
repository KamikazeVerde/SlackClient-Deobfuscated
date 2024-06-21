/*      */ package net.minecraft.client.network;
/*      */ import cc.slack.events.impl.network.DisconnectEvent;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.util.concurrent.FutureCallback;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URLDecoder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.client.ClientBrandRetriever;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.GuardianSound;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiDisconnected;
/*      */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMerchant;
/*      */ import net.minecraft.client.gui.GuiMultiplayer;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenBook;
/*      */ import net.minecraft.client.gui.GuiWinGame;
/*      */ import net.minecraft.client.gui.GuiYesNo;
/*      */ import net.minecraft.client.gui.IProgressMeter;
/*      */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.ServerList;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EntityPickupFX;
/*      */ import net.minecraft.client.player.inventory.ContainerLocalMenu;
/*      */ import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.DataWatcher;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLeashKnot;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityArmorStand;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.item.EntityEnderEye;
/*      */ import net.minecraft.entity.item.EntityEnderPearl;
/*      */ import net.minecraft.entity.item.EntityExpBottle;
/*      */ import net.minecraft.entity.item.EntityFallingBlock;
/*      */ import net.minecraft.entity.item.EntityFireworkRocket;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.item.EntityTNTPrimed;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityGuardian;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.entity.projectile.EntityEgg;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*      */ import net.minecraft.entity.projectile.EntityPotion;
/*      */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*      */ import net.minecraft.entity.projectile.EntitySnowball;
/*      */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.AnimalChest;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMap;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.PacketThreadUtil;
/*      */ import net.minecraft.network.play.INetHandlerPlayClient;
/*      */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*      */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*      */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.S0APacketUseBed;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*      */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*      */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*      */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*      */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*      */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*      */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*      */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.S14PacketEntity;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*      */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*      */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*      */ import net.minecraft.network.play.server.S21PacketChunkData;
/*      */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*      */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*      */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*      */ import net.minecraft.network.play.server.S27PacketExplosion;
/*      */ import net.minecraft.network.play.server.S28PacketEffect;
/*      */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*      */ import net.minecraft.network.play.server.S2APacketParticles;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*      */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*      */ import net.minecraft.network.play.server.S34PacketMaps;
/*      */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*      */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.S37PacketStatistics;
/*      */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*      */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*      */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*      */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*      */ import net.minecraft.network.play.server.S3EPacketTeams;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*      */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*      */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*      */ import net.minecraft.network.play.server.S43PacketCamera;
/*      */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*      */ import net.minecraft.network.play.server.S45PacketTitle;
/*      */ import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
/*      */ import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
/*      */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*      */ import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.Achievement;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.storage.MapData;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayClient implements INetHandlerPlayClient {
/*  205 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final NetworkManager netManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GameProfile profile;
/*      */ 
/*      */ 
/*      */   
/*      */   private final GuiScreen guiScreenServer;
/*      */ 
/*      */ 
/*      */   
/*      */   private Minecraft gameController;
/*      */ 
/*      */ 
/*      */   
/*      */   private WorldClient clientWorldController;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doneLoadingTerrain;
/*      */ 
/*      */ 
/*      */   
/*  235 */   private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
/*  236 */   public int currentServerMaxPlayers = 20;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean field_147308_k = false;
/*      */ 
/*      */   
/*  243 */   private final Random avRandomizer = new Random();
/*      */ 
/*      */   
/*      */   public NetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_) {
/*  247 */     this.gameController = mcIn;
/*  248 */     this.guiScreenServer = p_i46300_2_;
/*  249 */     this.netManager = p_i46300_3_;
/*  250 */     this.profile = p_i46300_4_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  258 */     this.clientWorldController = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleJoinGame(S01PacketJoinGame packetIn) {
/*  267 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  268 */     this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
/*  269 */     this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), packetIn.getWorldType()), packetIn.getDimension(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  270 */     this.gameController.gameSettings.difficulty = packetIn.getDifficulty();
/*  271 */     this.gameController.loadWorld(this.clientWorldController);
/*  272 */     this.gameController.thePlayer.dimension = packetIn.getDimension();
/*  273 */     this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*  274 */     this.gameController.thePlayer.setEntityId(packetIn.getEntityId());
/*  275 */     this.currentServerMaxPlayers = packetIn.getMaxPlayers();
/*  276 */     this.gameController.thePlayer.setReducedDebug(packetIn.isReducedDebugInfo());
/*  277 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*  278 */     this.gameController.gameSettings.sendSettingsToServer();
/*  279 */     this.netManager.sendPacket((Packet)new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnObject(S0EPacketSpawnObject packetIn) {
/*      */     EntityFallingBlock entityFallingBlock;
/*  287 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  288 */     double d0 = packetIn.getX() / 32.0D;
/*  289 */     double d1 = packetIn.getY() / 32.0D;
/*  290 */     double d2 = packetIn.getZ() / 32.0D;
/*  291 */     Entity entity = null;
/*      */     
/*  293 */     if (packetIn.getType() == 10) {
/*      */       
/*  295 */       EntityMinecart entityMinecart = EntityMinecart.func_180458_a((World)this.clientWorldController, d0, d1, d2, EntityMinecart.EnumMinecartType.byNetworkID(packetIn.func_149009_m()));
/*      */     }
/*  297 */     else if (packetIn.getType() == 90) {
/*      */       
/*  299 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */       
/*  301 */       if (entity1 instanceof EntityPlayer)
/*      */       {
/*  303 */         EntityFishHook entityFishHook = new EntityFishHook((World)this.clientWorldController, d0, d1, d2, (EntityPlayer)entity1);
/*      */       }
/*      */       
/*  306 */       packetIn.func_149002_g(0);
/*      */     }
/*  308 */     else if (packetIn.getType() == 60) {
/*      */       
/*  310 */       EntityArrow entityArrow = new EntityArrow((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  312 */     else if (packetIn.getType() == 61) {
/*      */       
/*  314 */       EntitySnowball entitySnowball = new EntitySnowball((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  316 */     else if (packetIn.getType() == 71) {
/*      */       
/*  318 */       EntityItemFrame entityItemFrame = new EntityItemFrame((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)), EnumFacing.getHorizontal(packetIn.func_149009_m()));
/*  319 */       packetIn.func_149002_g(0);
/*      */     }
/*  321 */     else if (packetIn.getType() == 77) {
/*      */       
/*  323 */       EntityLeashKnot entityLeashKnot = new EntityLeashKnot((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)));
/*  324 */       packetIn.func_149002_g(0);
/*      */     }
/*  326 */     else if (packetIn.getType() == 65) {
/*      */       
/*  328 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  330 */     else if (packetIn.getType() == 72) {
/*      */       
/*  332 */       EntityEnderEye entityEnderEye = new EntityEnderEye((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  334 */     else if (packetIn.getType() == 76) {
/*      */       
/*  336 */       EntityFireworkRocket entityFireworkRocket = new EntityFireworkRocket((World)this.clientWorldController, d0, d1, d2, null);
/*      */     }
/*  338 */     else if (packetIn.getType() == 63) {
/*      */       
/*  340 */       EntityLargeFireball entityLargeFireball = new EntityLargeFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  341 */       packetIn.func_149002_g(0);
/*      */     }
/*  343 */     else if (packetIn.getType() == 64) {
/*      */       
/*  345 */       EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  346 */       packetIn.func_149002_g(0);
/*      */     }
/*  348 */     else if (packetIn.getType() == 66) {
/*      */       
/*  350 */       EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  351 */       packetIn.func_149002_g(0);
/*      */     }
/*  353 */     else if (packetIn.getType() == 62) {
/*      */       
/*  355 */       EntityEgg entityEgg = new EntityEgg((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  357 */     else if (packetIn.getType() == 73) {
/*      */       
/*  359 */       EntityPotion entityPotion = new EntityPotion((World)this.clientWorldController, d0, d1, d2, packetIn.func_149009_m());
/*  360 */       packetIn.func_149002_g(0);
/*      */     }
/*  362 */     else if (packetIn.getType() == 75) {
/*      */       
/*  364 */       EntityExpBottle entityExpBottle = new EntityExpBottle((World)this.clientWorldController, d0, d1, d2);
/*  365 */       packetIn.func_149002_g(0);
/*      */     }
/*  367 */     else if (packetIn.getType() == 1) {
/*      */       
/*  369 */       EntityBoat entityBoat = new EntityBoat((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  371 */     else if (packetIn.getType() == 50) {
/*      */       
/*  373 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed((World)this.clientWorldController, d0, d1, d2, null);
/*      */     }
/*  375 */     else if (packetIn.getType() == 78) {
/*      */       
/*  377 */       EntityArmorStand entityArmorStand = new EntityArmorStand((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  379 */     else if (packetIn.getType() == 51) {
/*      */       
/*  381 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  383 */     else if (packetIn.getType() == 2) {
/*      */       
/*  385 */       EntityItem entityItem = new EntityItem((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  387 */     else if (packetIn.getType() == 70) {
/*      */       
/*  389 */       entityFallingBlock = new EntityFallingBlock((World)this.clientWorldController, d0, d1, d2, Block.getStateById(packetIn.func_149009_m() & 0xFFFF));
/*  390 */       packetIn.func_149002_g(0);
/*      */     } 
/*      */     
/*  393 */     if (entityFallingBlock != null) {
/*      */       
/*  395 */       ((Entity)entityFallingBlock).serverPosX = packetIn.getX();
/*  396 */       ((Entity)entityFallingBlock).serverPosY = packetIn.getY();
/*  397 */       ((Entity)entityFallingBlock).serverPosZ = packetIn.getZ();
/*  398 */       ((Entity)entityFallingBlock).rotationPitch = (packetIn.getPitch() * 360) / 256.0F;
/*  399 */       ((Entity)entityFallingBlock).rotationYaw = (packetIn.getYaw() * 360) / 256.0F;
/*  400 */       Entity[] aentity = entityFallingBlock.getParts();
/*      */       
/*  402 */       if (aentity != null) {
/*      */         
/*  404 */         int i = packetIn.getEntityID() - entityFallingBlock.getEntityId();
/*      */         
/*  406 */         for (int j = 0; j < aentity.length; j++)
/*      */         {
/*  408 */           aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */         }
/*      */       } 
/*      */       
/*  412 */       entityFallingBlock.setEntityId(packetIn.getEntityID());
/*  413 */       this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityFallingBlock);
/*      */       
/*  415 */       if (packetIn.func_149009_m() > 0) {
/*      */         
/*  417 */         if (packetIn.getType() == 60) {
/*      */           
/*  419 */           Entity entity2 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */           
/*  421 */           if (entity2 instanceof EntityLivingBase && entityFallingBlock instanceof EntityArrow)
/*      */           {
/*  423 */             ((EntityArrow)entityFallingBlock).shootingEntity = entity2;
/*      */           }
/*      */         } 
/*      */         
/*  427 */         entityFallingBlock.setVelocity(packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn) {
/*  437 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  438 */     EntityXPOrb entityXPOrb = new EntityXPOrb((World)this.clientWorldController, packetIn.getX() / 32.0D, packetIn.getY() / 32.0D, packetIn.getZ() / 32.0D, packetIn.getXPValue());
/*  439 */     ((Entity)entityXPOrb).serverPosX = packetIn.getX();
/*  440 */     ((Entity)entityXPOrb).serverPosY = packetIn.getY();
/*  441 */     ((Entity)entityXPOrb).serverPosZ = packetIn.getZ();
/*  442 */     ((Entity)entityXPOrb).rotationYaw = 0.0F;
/*  443 */     ((Entity)entityXPOrb).rotationPitch = 0.0F;
/*  444 */     entityXPOrb.setEntityId(packetIn.getEntityID());
/*  445 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityXPOrb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn) {
/*      */     EntityLightningBolt entityLightningBolt;
/*  453 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  454 */     double d0 = packetIn.func_149051_d() / 32.0D;
/*  455 */     double d1 = packetIn.func_149050_e() / 32.0D;
/*  456 */     double d2 = packetIn.func_149049_f() / 32.0D;
/*  457 */     Entity entity = null;
/*      */     
/*  459 */     if (packetIn.func_149053_g() == 1)
/*      */     {
/*  461 */       entityLightningBolt = new EntityLightningBolt((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*      */     
/*  464 */     if (entityLightningBolt != null) {
/*      */       
/*  466 */       ((Entity)entityLightningBolt).serverPosX = packetIn.func_149051_d();
/*  467 */       ((Entity)entityLightningBolt).serverPosY = packetIn.func_149050_e();
/*  468 */       ((Entity)entityLightningBolt).serverPosZ = packetIn.func_149049_f();
/*  469 */       ((Entity)entityLightningBolt).rotationYaw = 0.0F;
/*  470 */       ((Entity)entityLightningBolt).rotationPitch = 0.0F;
/*  471 */       entityLightningBolt.setEntityId(packetIn.func_149052_c());
/*  472 */       this.clientWorldController.addWeatherEffect((Entity)entityLightningBolt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPainting(S10PacketSpawnPainting packetIn) {
/*  481 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  482 */     EntityPainting entitypainting = new EntityPainting((World)this.clientWorldController, packetIn.getPosition(), packetIn.getFacing(), packetIn.getTitle());
/*  483 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitypainting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityVelocity(S12PacketEntityVelocity packetIn) {
/*  491 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  492 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  494 */     if (entity != null)
/*      */     {
/*  496 */       entity.setVelocity(packetIn.getMotionX() / 8000.0D, packetIn.getMotionY() / 8000.0D, packetIn.getMotionZ() / 8000.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMetadata(S1CPacketEntityMetadata packetIn) {
/*  506 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  507 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  509 */     if (entity != null && packetIn.func_149376_c() != null)
/*      */     {
/*  511 */       entity.getDataWatcher().updateWatchedObjectsFromList(packetIn.func_149376_c());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn) {
/*  520 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  521 */     double d0 = packetIn.getX() / 32.0D;
/*  522 */     double d1 = packetIn.getY() / 32.0D;
/*  523 */     double d2 = packetIn.getZ() / 32.0D;
/*  524 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  525 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  526 */     EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP((World)this.gameController.theWorld, getPlayerInfo(packetIn.getPlayer()).getGameProfile());
/*  527 */     entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = (entityotherplayermp.serverPosX = packetIn.getX());
/*  528 */     entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = (entityotherplayermp.serverPosY = packetIn.getY());
/*  529 */     entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = (entityotherplayermp.serverPosZ = packetIn.getZ());
/*  530 */     int i = packetIn.getCurrentItemID();
/*      */     
/*  532 */     if (i == 0) {
/*      */       
/*  534 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
/*      */     }
/*      */     else {
/*      */       
/*  538 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(Item.getItemById(i), 1, 0);
/*      */     } 
/*      */     
/*  541 */     entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
/*  542 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityotherplayermp);
/*  543 */     List<DataWatcher.WatchableObject> list = packetIn.func_148944_c();
/*      */     
/*  545 */     if (list != null)
/*      */     {
/*  547 */       entityotherplayermp.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityTeleport(S18PacketEntityTeleport packetIn) {
/*  556 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  557 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  559 */     if (entity != null) {
/*      */       
/*  561 */       entity.serverPosX = packetIn.getX();
/*  562 */       entity.serverPosY = packetIn.getY();
/*  563 */       entity.serverPosZ = packetIn.getZ();
/*  564 */       double d0 = entity.serverPosX / 32.0D;
/*  565 */       double d1 = entity.serverPosY / 32.0D;
/*  566 */       double d2 = entity.serverPosZ / 32.0D;
/*  567 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  568 */       float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*      */       
/*  570 */       if (Math.abs(entity.posX - d0) < 0.03125D && Math.abs(entity.posY - d1) < 0.015625D && Math.abs(entity.posZ - d2) < 0.03125D) {
/*      */         
/*  572 */         entity.setPositionAndRotation2(entity.posX, entity.posY, entity.posZ, f, f1, 3, true);
/*      */       }
/*      */       else {
/*      */         
/*  576 */         entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, true);
/*      */       } 
/*      */       
/*  579 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHeldItemChange(S09PacketHeldItemChange packetIn) {
/*  588 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  590 */     if (packetIn.getHeldItemHotbarIndex() >= 0 && packetIn.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize())
/*      */     {
/*  592 */       this.gameController.thePlayer.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMovement(S14PacketEntity packetIn) {
/*  603 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  604 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  606 */     if (entity != null) {
/*      */       
/*  608 */       entity.serverPosX += packetIn.func_149062_c();
/*  609 */       entity.serverPosY += packetIn.func_149061_d();
/*  610 */       entity.serverPosZ += packetIn.func_149064_e();
/*  611 */       double d0 = entity.serverPosX / 32.0D;
/*  612 */       double d1 = entity.serverPosY / 32.0D;
/*  613 */       double d2 = entity.serverPosZ / 32.0D;
/*  614 */       float f = packetIn.func_149060_h() ? ((packetIn.func_149066_f() * 360) / 256.0F) : entity.rotationYaw;
/*  615 */       float f1 = packetIn.func_149060_h() ? ((packetIn.func_149063_g() * 360) / 256.0F) : entity.rotationPitch;
/*  616 */       entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, false);
/*  617 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn) {
/*  627 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  628 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  630 */     if (entity != null) {
/*      */       
/*  632 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  633 */       entity.setRotationYawHead(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDestroyEntities(S13PacketDestroyEntities packetIn) {
/*  644 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  646 */     for (int i = 0; i < (packetIn.getEntityIDs()).length; i++)
/*      */     {
/*  648 */       this.clientWorldController.removeEntityFromWorld(packetIn.getEntityIDs()[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn) {
/*  659 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  660 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*  661 */     double d0 = packetIn.getX();
/*  662 */     double d1 = packetIn.getY();
/*  663 */     double d2 = packetIn.getZ();
/*  664 */     float f = packetIn.getYaw();
/*  665 */     float f1 = packetIn.getPitch();
/*      */     
/*  667 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
/*      */       
/*  669 */       d0 += ((EntityPlayer)entityPlayerSP).posX;
/*      */     }
/*      */     else {
/*      */       
/*  673 */       ((EntityPlayer)entityPlayerSP).motionX = 0.0D;
/*      */     } 
/*      */     
/*  676 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
/*      */       
/*  678 */       d1 += ((EntityPlayer)entityPlayerSP).posY;
/*      */     }
/*      */     else {
/*      */       
/*  682 */       ((EntityPlayer)entityPlayerSP).motionY = 0.0D;
/*      */     } 
/*      */     
/*  685 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
/*      */       
/*  687 */       d2 += ((EntityPlayer)entityPlayerSP).posZ;
/*      */     }
/*      */     else {
/*      */       
/*  691 */       ((EntityPlayer)entityPlayerSP).motionZ = 0.0D;
/*      */     } 
/*      */     
/*  694 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT))
/*      */     {
/*  696 */       f1 += ((EntityPlayer)entityPlayerSP).rotationPitch;
/*      */     }
/*      */     
/*  699 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT))
/*      */     {
/*  701 */       f += ((EntityPlayer)entityPlayerSP).rotationYaw;
/*      */     }
/*      */     
/*  704 */     entityPlayerSP.setPositionAndRotation(d0, d1, d2, f, f1);
/*  705 */     this.netManager.sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((EntityPlayer)entityPlayerSP).posX, (entityPlayerSP.getEntityBoundingBox()).minY, ((EntityPlayer)entityPlayerSP).posZ, ((EntityPlayer)entityPlayerSP).rotationYaw, ((EntityPlayer)entityPlayerSP).rotationPitch, false));
/*      */     
/*  707 */     if (!this.doneLoadingTerrain) {
/*      */       
/*  709 */       this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
/*  710 */       this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
/*  711 */       this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
/*  712 */       this.doneLoadingTerrain = true;
/*  713 */       this.gameController.displayGuiScreen(null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
/*  724 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  726 */     for (S22PacketMultiBlockChange.BlockUpdateData s22packetmultiblockchange$blockupdatedata : packetIn.getChangedBlocks())
/*      */     {
/*  728 */       this.clientWorldController.invalidateRegionAndSetBlock(s22packetmultiblockchange$blockupdatedata.getPos(), s22packetmultiblockchange$blockupdatedata.getBlockState());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChunkData(S21PacketChunkData packetIn) {
/*  737 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  739 */     if (packetIn.func_149274_i()) {
/*      */       
/*  741 */       if (packetIn.getExtractedSize() == 0) {
/*      */         
/*  743 */         this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), false);
/*      */         
/*      */         return;
/*      */       } 
/*  747 */       this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
/*      */     } 
/*      */     
/*  750 */     this.clientWorldController.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*  751 */     Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
/*  752 */     chunk.fillChunk(packetIn.func_149272_d(), packetIn.getExtractedSize(), packetIn.func_149274_i());
/*  753 */     this.clientWorldController.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*      */     
/*  755 */     if (!packetIn.func_149274_i() || !(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface))
/*      */     {
/*  757 */       chunk.resetRelightChecks();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockChange(S23PacketBlockChange packetIn) {
/*  766 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  767 */     this.clientWorldController.invalidateRegionAndSetBlock(packetIn.getBlockPosition(), packetIn.getBlockState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisconnect(S40PacketDisconnect packetIn) {
/*  775 */     this.netManager.closeChannel(packetIn.getReason());
/*  776 */     (new DisconnectEvent()).call();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  784 */     this.gameController.loadWorld(null);
/*      */     
/*  786 */     if (this.guiScreenServer != null) {
/*      */       
/*  788 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected(this.guiScreenServer, "disconnect.lost", reason));
/*      */     }
/*      */     else {
/*      */       
/*  792 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), "disconnect.lost", reason));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addToSendQueue(Packet p_147297_1_) {
/*  798 */     this.netManager.sendPacket(p_147297_1_);
/*      */   }
/*      */   
/*      */   public void handleCollectItem(S0DPacketCollectItem packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  803 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  804 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getCollectedItemEntityID());
/*  805 */     EntityLivingBase entitylivingbase = (EntityLivingBase)this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  807 */     if (entitylivingbase == null)
/*      */     {
/*  809 */       entityPlayerSP = this.gameController.thePlayer;
/*      */     }
/*      */     
/*  812 */     if (entity != null) {
/*      */       
/*  814 */       if (entity instanceof EntityXPOrb) {
/*      */         
/*  816 */         this.clientWorldController.playSoundAtEntity(entity, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       }
/*      */       else {
/*      */         
/*  820 */         this.clientWorldController.playSoundAtEntity(entity, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } 
/*      */       
/*  823 */       this.gameController.effectRenderer.addEffect((EntityFX)new EntityPickupFX((World)this.clientWorldController, entity, (Entity)entityPlayerSP, 0.5F));
/*  824 */       this.clientWorldController.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChat(S02PacketChat packetIn) {
/*  833 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  835 */     if (packetIn.getType() == 2) {
/*      */       
/*  837 */       this.gameController.ingameGUI.setRecordPlaying(packetIn.getChatComponent(), false);
/*      */     }
/*      */     else {
/*      */       
/*  841 */       this.gameController.ingameGUI.getChatGUI().printChatMessage(packetIn.getChatComponent());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleAnimation(S0BPacketAnimation packetIn) {
/*  851 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  852 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  854 */     if (entity != null)
/*      */     {
/*  856 */       if (packetIn.getAnimationType() == 0) {
/*      */         
/*  858 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*  859 */         entitylivingbase.swingItem();
/*      */       }
/*  861 */       else if (packetIn.getAnimationType() == 1) {
/*      */         
/*  863 */         entity.performHurtAnimation();
/*      */       }
/*  865 */       else if (packetIn.getAnimationType() == 2) {
/*      */         
/*  867 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/*  868 */         entityplayer.wakeUpPlayer(false, false, false);
/*      */       }
/*  870 */       else if (packetIn.getAnimationType() == 4) {
/*      */         
/*  872 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
/*      */       }
/*  874 */       else if (packetIn.getAnimationType() == 5) {
/*      */         
/*  876 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUseBed(S0APacketUseBed packetIn) {
/*  887 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  888 */     packetIn.getPlayer((World)this.clientWorldController).trySleep(packetIn.getBedPosition());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnMob(S0FPacketSpawnMob packetIn) {
/*  897 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  898 */     double d0 = packetIn.getX() / 32.0D;
/*  899 */     double d1 = packetIn.getY() / 32.0D;
/*  900 */     double d2 = packetIn.getZ() / 32.0D;
/*  901 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  902 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  903 */     EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.createEntityByID(packetIn.getEntityType(), (World)this.gameController.theWorld);
/*  904 */     entitylivingbase.serverPosX = packetIn.getX();
/*  905 */     entitylivingbase.serverPosY = packetIn.getY();
/*  906 */     entitylivingbase.serverPosZ = packetIn.getZ();
/*  907 */     entitylivingbase.renderYawOffset = entitylivingbase.rotationYawHead = (packetIn.getHeadPitch() * 360) / 256.0F;
/*  908 */     Entity[] aentity = entitylivingbase.getParts();
/*      */     
/*  910 */     if (aentity != null) {
/*      */       
/*  912 */       int i = packetIn.getEntityID() - entitylivingbase.getEntityId();
/*      */       
/*  914 */       for (int j = 0; j < aentity.length; j++)
/*      */       {
/*  916 */         aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */       }
/*      */     } 
/*      */     
/*  920 */     entitylivingbase.setEntityId(packetIn.getEntityID());
/*  921 */     entitylivingbase.setPositionAndRotation(d0, d1, d2, f, f1);
/*  922 */     entitylivingbase.motionX = (packetIn.getVelocityX() / 8000.0F);
/*  923 */     entitylivingbase.motionY = (packetIn.getVelocityY() / 8000.0F);
/*  924 */     entitylivingbase.motionZ = (packetIn.getVelocityZ() / 8000.0F);
/*  925 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitylivingbase);
/*  926 */     List<DataWatcher.WatchableObject> list = packetIn.func_149027_c();
/*      */     
/*  928 */     if (list != null)
/*      */     {
/*  930 */       entitylivingbase.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
/*  936 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  937 */     this.gameController.theWorld.setTotalWorldTime(packetIn.getTotalWorldTime());
/*  938 */     this.gameController.theWorld.setWorldTime(packetIn.getWorldTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSpawnPosition(S05PacketSpawnPosition packetIn) {
/*  943 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  944 */     this.gameController.thePlayer.setSpawnPoint(packetIn.getSpawnPos(), true);
/*  945 */     this.gameController.theWorld.getWorldInfo().setSpawn(packetIn.getSpawnPos());
/*      */   }
/*      */   
/*      */   public void handleEntityAttach(S1BPacketEntityAttach packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  950 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  951 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*  952 */     Entity entity1 = this.clientWorldController.getEntityByID(packetIn.getVehicleEntityId());
/*      */     
/*  954 */     if (packetIn.getLeash() == 0) {
/*      */       
/*  956 */       boolean flag = false;
/*      */       
/*  958 */       if (packetIn.getEntityId() == this.gameController.thePlayer.getEntityId()) {
/*      */         
/*  960 */         entityPlayerSP = this.gameController.thePlayer;
/*      */         
/*  962 */         if (entity1 instanceof EntityBoat)
/*      */         {
/*  964 */           ((EntityBoat)entity1).setIsBoatEmpty(false);
/*      */         }
/*      */         
/*  967 */         flag = (((Entity)entityPlayerSP).ridingEntity == null && entity1 != null);
/*      */       }
/*  969 */       else if (entity1 instanceof EntityBoat) {
/*      */         
/*  971 */         ((EntityBoat)entity1).setIsBoatEmpty(true);
/*      */       } 
/*      */       
/*  974 */       if (entityPlayerSP == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  979 */       entityPlayerSP.mountEntity(entity1);
/*      */       
/*  981 */       if (flag)
/*      */       {
/*  983 */         GameSettings gamesettings = this.gameController.gameSettings;
/*  984 */         this.gameController.ingameGUI.setRecordPlaying(I18n.format("mount.onboard", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindSneak.getKeyCode()) }), false);
/*      */       }
/*      */     
/*  987 */     } else if (packetIn.getLeash() == 1 && entityPlayerSP instanceof EntityLiving) {
/*      */       
/*  989 */       if (entity1 != null) {
/*      */         
/*  991 */         ((EntityLiving)entityPlayerSP).setLeashedToEntity(entity1, false);
/*      */       }
/*      */       else {
/*      */         
/*  995 */         ((EntityLiving)entityPlayerSP).clearLeashed(false, false);
/*      */       } 
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
/*      */   public void handleEntityStatus(S19PacketEntityStatus packetIn) {
/* 1008 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1009 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1011 */     if (entity != null)
/*      */     {
/* 1013 */       if (packetIn.getOpCode() == 21) {
/*      */         
/* 1015 */         this.gameController.getSoundHandler().playSound((ISound)new GuardianSound((EntityGuardian)entity));
/*      */       }
/*      */       else {
/*      */         
/* 1019 */         entity.handleHealthUpdate(packetIn.getOpCode());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateHealth(S06PacketUpdateHealth packetIn) {
/* 1026 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1027 */     this.gameController.thePlayer.setPlayerSPHealth(packetIn.getHealth());
/* 1028 */     this.gameController.thePlayer.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
/* 1029 */     this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetExperience(S1FPacketSetExperience packetIn) {
/* 1034 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1035 */     this.gameController.thePlayer.setXPStats(packetIn.func_149397_c(), packetIn.getTotalExperience(), packetIn.getLevel());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRespawn(S07PacketRespawn packetIn) {
/* 1040 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1042 */     if (packetIn.getDimensionID() != this.gameController.thePlayer.dimension) {
/*      */       
/* 1044 */       this.doneLoadingTerrain = false;
/* 1045 */       Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1046 */       this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), packetIn.getWorldType()), packetIn.getDimensionID(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/* 1047 */       this.clientWorldController.setWorldScoreboard(scoreboard);
/* 1048 */       this.gameController.loadWorld(this.clientWorldController);
/* 1049 */       this.gameController.thePlayer.dimension = packetIn.getDimensionID();
/* 1050 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*      */     } 
/*      */     
/* 1053 */     this.gameController.setDimensionAndSpawnPlayer(packetIn.getDimensionID());
/* 1054 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleExplosion(S27PacketExplosion packetIn) {
/* 1062 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1063 */     Explosion explosion = new Explosion((World)this.gameController.theWorld, null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
/* 1064 */     explosion.doExplosionB(true);
/* 1065 */     this.gameController.thePlayer.motionX += packetIn.func_149149_c();
/* 1066 */     this.gameController.thePlayer.motionY += packetIn.func_149144_d();
/* 1067 */     this.gameController.thePlayer.motionZ += packetIn.func_149147_e();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleOpenWindow(S2DPacketOpenWindow packetIn) {
/* 1076 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1077 */     EntityPlayerSP entityplayersp = this.gameController.thePlayer;
/*      */     
/* 1079 */     if ("minecraft:container".equals(packetIn.getGuiId())) {
/*      */       
/* 1081 */       entityplayersp.displayGUIChest((IInventory)new InventoryBasic(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/* 1082 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     }
/* 1084 */     else if ("minecraft:villager".equals(packetIn.getGuiId())) {
/*      */       
/* 1086 */       entityplayersp.displayVillagerTradeGui((IMerchant)new NpcMerchant((EntityPlayer)entityplayersp, packetIn.getWindowTitle()));
/* 1087 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     }
/* 1089 */     else if ("EntityHorse".equals(packetIn.getGuiId())) {
/*      */       
/* 1091 */       Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */       
/* 1093 */       if (entity instanceof EntityHorse)
/*      */       {
/* 1095 */         entityplayersp.displayGUIHorse((EntityHorse)entity, (IInventory)new AnimalChest(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/* 1096 */         entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */       }
/*      */     
/* 1099 */     } else if (!packetIn.hasSlots()) {
/*      */       
/* 1101 */       entityplayersp.displayGui((IInteractionObject)new LocalBlockIntercommunication(packetIn.getGuiId(), packetIn.getWindowTitle()));
/* 1102 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     }
/*      */     else {
/*      */       
/* 1106 */       ContainerLocalMenu containerlocalmenu = new ContainerLocalMenu(packetIn.getGuiId(), packetIn.getWindowTitle(), packetIn.getSlotCount());
/* 1107 */       entityplayersp.displayGUIChest((IInventory)containerlocalmenu);
/* 1108 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSetSlot(S2FPacketSetSlot packetIn) {
/* 1117 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1118 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1120 */     if (packetIn.func_149175_c() == -1) {
/*      */       
/* 1122 */       ((EntityPlayer)entityPlayerSP).inventory.setItemStack(packetIn.func_149174_e());
/*      */     }
/*      */     else {
/*      */       
/* 1126 */       boolean flag = false;
/*      */       
/* 1128 */       if (this.gameController.currentScreen instanceof GuiContainerCreative) {
/*      */         
/* 1130 */         GuiContainerCreative guicontainercreative = (GuiContainerCreative)this.gameController.currentScreen;
/* 1131 */         flag = (guicontainercreative.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex());
/*      */       } 
/*      */       
/* 1134 */       if (packetIn.func_149175_c() == 0 && packetIn.func_149173_d() >= 36 && packetIn.func_149173_d() < 45) {
/*      */         
/* 1136 */         ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventoryContainer.getSlot(packetIn.func_149173_d()).getStack();
/*      */         
/* 1138 */         if (packetIn.func_149174_e() != null && (itemstack == null || itemstack.stackSize < (packetIn.func_149174_e()).stackSize))
/*      */         {
/* 1140 */           (packetIn.func_149174_e()).animationsToGo = 5;
/*      */         }
/*      */         
/* 1143 */         ((EntityPlayer)entityPlayerSP).inventoryContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*      */       }
/* 1145 */       else if (packetIn.func_149175_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId && (packetIn.func_149175_c() != 0 || !flag)) {
/*      */         
/* 1147 */         ((EntityPlayer)entityPlayerSP).openContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn) {
/* 1158 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1159 */     Container container = null;
/* 1160 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1162 */     if (packetIn.getWindowId() == 0) {
/*      */       
/* 1164 */       container = ((EntityPlayer)entityPlayerSP).inventoryContainer;
/*      */     }
/* 1166 */     else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/*      */       
/* 1168 */       container = ((EntityPlayer)entityPlayerSP).openContainer;
/*      */     } 
/*      */     
/* 1171 */     if (container != null && !packetIn.func_148888_e())
/*      */     {
/* 1173 */       addToSendQueue((Packet)new C0FPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowItems(S30PacketWindowItems packetIn) {
/* 1182 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1183 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1185 */     if (packetIn.func_148911_c() == 0) {
/*      */       
/* 1187 */       ((EntityPlayer)entityPlayerSP).inventoryContainer.putStacksInSlots(packetIn.getItemStacks());
/*      */     }
/* 1189 */     else if (packetIn.func_148911_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/*      */       
/* 1191 */       ((EntityPlayer)entityPlayerSP).openContainer.putStacksInSlots(packetIn.getItemStacks());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn) {
/*      */     TileEntitySign tileEntitySign;
/* 1200 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1201 */     TileEntity tileentity = this.clientWorldController.getTileEntity(packetIn.getSignPosition());
/*      */     
/* 1203 */     if (!(tileentity instanceof TileEntitySign)) {
/*      */       
/* 1205 */       tileEntitySign = new TileEntitySign();
/* 1206 */       tileEntitySign.setWorldObj((World)this.clientWorldController);
/* 1207 */       tileEntitySign.setPos(packetIn.getSignPosition());
/*      */     } 
/*      */     
/* 1210 */     this.gameController.thePlayer.openEditSign(tileEntitySign);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateSign(S33PacketUpdateSign packetIn) {
/* 1218 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1219 */     boolean flag = false;
/*      */     
/* 1221 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/*      */       
/* 1223 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/*      */       
/* 1225 */       if (tileentity instanceof TileEntitySign) {
/*      */         
/* 1227 */         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */         
/* 1229 */         if (tileentitysign.getIsEditable()) {
/*      */           
/* 1231 */           System.arraycopy(packetIn.getLines(), 0, tileentitysign.signText, 0, 4);
/* 1232 */           tileentitysign.markDirty();
/*      */         } 
/*      */         
/* 1235 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1239 */     if (!flag && this.gameController.thePlayer != null)
/*      */     {
/* 1241 */       this.gameController.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Unable to locate sign at " + packetIn.getPos().getX() + ", " + packetIn.getPos().getY() + ", " + packetIn.getPos().getZ()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn) {
/* 1251 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1253 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/*      */       
/* 1255 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/* 1256 */       int i = packetIn.getTileEntityType();
/*      */       
/* 1258 */       if ((i == 1 && tileentity instanceof net.minecraft.tileentity.TileEntityMobSpawner) || (i == 2 && tileentity instanceof net.minecraft.tileentity.TileEntityCommandBlock) || (i == 3 && tileentity instanceof net.minecraft.tileentity.TileEntityBeacon) || (i == 4 && tileentity instanceof net.minecraft.tileentity.TileEntitySkull) || (i == 5 && tileentity instanceof net.minecraft.tileentity.TileEntityFlowerPot) || (i == 6 && tileentity instanceof net.minecraft.tileentity.TileEntityBanner))
/*      */       {
/* 1260 */         tileentity.readFromNBT(packetIn.getNbtCompound());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowProperty(S31PacketWindowProperty packetIn) {
/* 1270 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1271 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1273 */     if (((EntityPlayer)entityPlayerSP).openContainer != null && ((EntityPlayer)entityPlayerSP).openContainer.windowId == packetIn.getWindowId())
/*      */     {
/* 1275 */       ((EntityPlayer)entityPlayerSP).openContainer.updateProgressBar(packetIn.getVarIndex(), packetIn.getVarValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEquipment(S04PacketEntityEquipment packetIn) {
/* 1281 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1282 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/* 1284 */     if (entity != null)
/*      */     {
/* 1286 */       entity.setCurrentItemOrArmor(packetIn.getEquipmentSlot(), packetIn.getItemStack());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCloseWindow(S2EPacketCloseWindow packetIn) {
/* 1295 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1296 */     this.gameController.thePlayer.closeScreenAndDropStack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockAction(S24PacketBlockAction packetIn) {
/* 1306 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1307 */     this.gameController.theWorld.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), packetIn.getData1(), packetIn.getData2());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn) {
/* 1315 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1316 */     this.gameController.theWorld.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), packetIn.getProgress());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn) {
/* 1321 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1323 */     for (int i = 0; i < packetIn.getChunkCount(); i++) {
/*      */       
/* 1325 */       int j = packetIn.getChunkX(i);
/* 1326 */       int k = packetIn.getChunkZ(i);
/* 1327 */       this.clientWorldController.doPreChunk(j, k, true);
/* 1328 */       this.clientWorldController.invalidateBlockReceiveRegion(j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
/* 1329 */       Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(j, k);
/* 1330 */       chunk.fillChunk(packetIn.getChunkBytes(i), packetIn.getChunkSize(i), true);
/* 1331 */       this.clientWorldController.markBlockRangeForRenderUpdate(j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
/*      */       
/* 1333 */       if (!(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface))
/*      */       {
/* 1335 */         chunk.resetRelightChecks();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChangeGameState(S2BPacketChangeGameState packetIn) {
/* 1342 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1343 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1344 */     int i = packetIn.getGameState();
/* 1345 */     float f = packetIn.func_149137_d();
/* 1346 */     int j = MathHelper.floor_float(f + 0.5F);
/*      */     
/* 1348 */     if (i >= 0 && i < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[i] != null)
/*      */     {
/* 1350 */       entityPlayerSP.addChatComponentMessage((IChatComponent)new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[i], new Object[0]));
/*      */     }
/*      */     
/* 1353 */     if (i == 1) {
/*      */       
/* 1355 */       this.clientWorldController.getWorldInfo().setRaining(true);
/* 1356 */       this.clientWorldController.setRainStrength(0.0F);
/*      */     }
/* 1358 */     else if (i == 2) {
/*      */       
/* 1360 */       this.clientWorldController.getWorldInfo().setRaining(false);
/* 1361 */       this.clientWorldController.setRainStrength(1.0F);
/*      */     }
/* 1363 */     else if (i == 3) {
/*      */       
/* 1365 */       this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(j));
/*      */     }
/* 1367 */     else if (i == 4) {
/*      */       
/* 1369 */       this.gameController.displayGuiScreen((GuiScreen)new GuiWinGame());
/*      */     }
/* 1371 */     else if (i == 6) {
/*      */       
/* 1373 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((EntityPlayer)entityPlayerSP).posZ, "random.successful_hit", 0.18F, 0.45F, false);
/*      */     }
/* 1375 */     else if (i == 7) {
/*      */       
/* 1377 */       this.clientWorldController.setRainStrength(Math.max(0.0F, Math.min(2.0F, f)));
/*      */     }
/* 1379 */     else if (i == 8) {
/*      */       
/* 1381 */       this.clientWorldController.setThunderStrength(Math.max(0.0F, Math.min(2.0F, f)));
/*      */     }
/* 1383 */     else if (i == 10) {
/*      */       
/* 1385 */       this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, ((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1386 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, "mob.guardian.curse", 1.0F, 1.0F, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMaps(S34PacketMaps packetIn) {
/* 1396 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1397 */     MapData mapdata = ItemMap.loadMapData(packetIn.getMapId(), (World)this.gameController.theWorld);
/* 1398 */     packetIn.setMapdataTo(mapdata);
/* 1399 */     this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(mapdata);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEffect(S28PacketEffect packetIn) {
/* 1404 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1406 */     if (packetIn.isSoundServerwide()) {
/*      */       
/* 1408 */       this.gameController.theWorld.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     }
/*      */     else {
/*      */       
/* 1412 */       this.gameController.theWorld.playAuxSFX(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatistics(S37PacketStatistics packetIn) {
/* 1421 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1422 */     boolean flag = false;
/*      */     
/* 1424 */     for (Map.Entry<StatBase, Integer> entry : (Iterable<Map.Entry<StatBase, Integer>>)packetIn.func_148974_c().entrySet()) {
/*      */       
/* 1426 */       StatBase statbase = entry.getKey();
/* 1427 */       int i = ((Integer)entry.getValue()).intValue();
/*      */       
/* 1429 */       if (statbase.isAchievement() && i > 0) {
/*      */         
/* 1431 */         if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(statbase) == 0) {
/*      */           
/* 1433 */           Achievement achievement = (Achievement)statbase;
/* 1434 */           this.gameController.guiAchievement.displayAchievement(achievement);
/*      */           
/* 1436 */           if (statbase == AchievementList.openInventory) {
/*      */             
/* 1438 */             this.gameController.gameSettings.showInventoryAchievementHint = false;
/* 1439 */             this.gameController.gameSettings.saveOptions();
/*      */           } 
/*      */         } 
/*      */         
/* 1443 */         flag = true;
/*      */       } 
/*      */       
/* 1446 */       this.gameController.thePlayer.getStatFileWriter().unlockAchievement((EntityPlayer)this.gameController.thePlayer, statbase, i);
/*      */     } 
/*      */     
/* 1449 */     if (!this.field_147308_k && !flag && this.gameController.gameSettings.showInventoryAchievementHint)
/*      */     {
/* 1451 */       this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
/*      */     }
/*      */     
/* 1454 */     this.field_147308_k = true;
/*      */     
/* 1456 */     if (this.gameController.currentScreen instanceof IProgressMeter)
/*      */     {
/* 1458 */       ((IProgressMeter)this.gameController.currentScreen).doneLoading();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEffect(S1DPacketEntityEffect packetIn) {
/* 1464 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1465 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1467 */     if (entity instanceof EntityLivingBase) {
/*      */       
/* 1469 */       PotionEffect potioneffect = new PotionEffect(packetIn.getEffectId(), packetIn.getDuration(), packetIn.getAmplifier(), false, packetIn.func_179707_f());
/* 1470 */       potioneffect.setPotionDurationMax(packetIn.func_149429_c());
/* 1471 */       ((EntityLivingBase)entity).addPotionEffect(potioneffect);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCombatEvent(S42PacketCombatEvent packetIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleServerDifficulty(S41PacketServerDifficulty packetIn) {
/* 1482 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1483 */     this.gameController.theWorld.getWorldInfo().setDifficulty(packetIn.getDifficulty());
/* 1484 */     this.gameController.theWorld.getWorldInfo().setDifficultyLocked(packetIn.isDifficultyLocked());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCamera(S43PacketCamera packetIn) {
/* 1489 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1490 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1492 */     if (entity != null)
/*      */     {
/* 1494 */       this.gameController.setRenderViewEntity(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleWorldBorder(S44PacketWorldBorder packetIn) {
/* 1500 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1501 */     packetIn.func_179788_a(this.clientWorldController.getWorldBorder());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTitle(S45PacketTitle packetIn) {
/* 1507 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1508 */     S45PacketTitle.Type s45packettitle$type = packetIn.getType();
/* 1509 */     String title = null;
/* 1510 */     String subTitle = null;
/* 1511 */     String s2 = (packetIn.getMessage() != null) ? packetIn.getMessage().getFormattedText() : "";
/*      */     
/* 1513 */     switch (s45packettitle$type) {
/*      */       
/*      */       case ADD_PLAYER:
/* 1516 */         title = s2;
/*      */         break;
/*      */       
/*      */       case UPDATE_GAME_MODE:
/* 1520 */         subTitle = s2;
/*      */         break;
/*      */       
/*      */       case UPDATE_LATENCY:
/* 1524 */         this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
/* 1525 */         this.gameController.ingameGUI.func_175177_a();
/*      */         return;
/*      */     } 
/*      */     
/* 1529 */     this.gameController.ingameGUI.displayTitle(title, subTitle, packetIn.getFadeInTime(), packetIn.getDisplayTime(), packetIn.getFadeOutTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn) {
/* 1534 */     if (!this.netManager.isLocalChannel())
/*      */     {
/* 1536 */       this.netManager.setCompressionTreshold(packetIn.func_179760_a());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn) {
/* 1542 */     this.gameController.ingameGUI.getTabList().setHeader((packetIn.getHeader().getFormattedText().length() == 0) ? null : packetIn.getHeader());
/* 1543 */     this.gameController.ingameGUI.getTabList().setFooter((packetIn.getFooter().getFormattedText().length() == 0) ? null : packetIn.getFooter());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn) {
/* 1548 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1549 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1551 */     if (entity instanceof EntityLivingBase)
/*      */     {
/* 1553 */       ((EntityLivingBase)entity).removePotionEffectClient(packetIn.getEffectId());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerListItem(S38PacketPlayerListItem packetIn) {
/* 1560 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1562 */     for (S38PacketPlayerListItem.AddPlayerData s38packetplayerlistitem$addplayerdata : packetIn.func_179767_a()) {
/*      */       
/* 1564 */       if (packetIn.func_179768_b() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
/*      */         
/* 1566 */         this.playerInfoMap.remove(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*      */         
/*      */         continue;
/*      */       } 
/* 1570 */       NetworkPlayerInfo networkplayerinfo = this.playerInfoMap.get(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*      */       
/* 1572 */       if (packetIn.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
/*      */         
/* 1574 */         networkplayerinfo = new NetworkPlayerInfo(s38packetplayerlistitem$addplayerdata);
/* 1575 */         this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
/*      */       } 
/*      */       
/* 1578 */       if (networkplayerinfo != null)
/*      */       {
/* 1580 */         switch (packetIn.func_179768_b()) {
/*      */           
/*      */           case ADD_PLAYER:
/* 1583 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/* 1584 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_GAME_MODE:
/* 1588 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/*      */ 
/*      */           
/*      */           case UPDATE_LATENCY:
/* 1592 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_DISPLAY_NAME:
/* 1596 */             networkplayerinfo.setDisplayName(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleKeepAlive(S00PacketKeepAlive packetIn) {
/* 1605 */     addToSendQueue((Packet)new C00PacketKeepAlive(packetIn.func_149134_c()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn) {
/* 1610 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1611 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1612 */     ((EntityPlayer)entityPlayerSP).capabilities.isFlying = packetIn.isFlying();
/* 1613 */     ((EntityPlayer)entityPlayerSP).capabilities.isCreativeMode = packetIn.isCreativeMode();
/* 1614 */     ((EntityPlayer)entityPlayerSP).capabilities.disableDamage = packetIn.isInvulnerable();
/* 1615 */     ((EntityPlayer)entityPlayerSP).capabilities.allowFlying = packetIn.isAllowFlying();
/* 1616 */     ((EntityPlayer)entityPlayerSP).capabilities.setFlySpeed(packetIn.getFlySpeed());
/* 1617 */     ((EntityPlayer)entityPlayerSP).capabilities.setPlayerWalkSpeed(packetIn.getWalkSpeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTabComplete(S3APacketTabComplete packetIn) {
/* 1625 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1626 */     String[] astring = packetIn.func_149630_c();
/*      */     
/* 1628 */     if (this.gameController.currentScreen instanceof GuiChat) {
/*      */       
/* 1630 */       GuiChat guichat = (GuiChat)this.gameController.currentScreen;
/* 1631 */       guichat.onAutocompleteResponse(astring);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSoundEffect(S29PacketSoundEffect packetIn) {
/* 1637 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1638 */     this.gameController.theWorld.playSound(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getSoundName(), packetIn.getVolume(), packetIn.getPitch(), false);
/*      */   }
/*      */   
/*      */   public static boolean validateResourcePackUrl(NetHandlerPlayClient client, S48PacketResourcePackSend packet) {
/*      */     try {
/* 1643 */       String url = packet.getURL();
/* 1644 */       URI uri = new URI(url);
/* 1645 */       String scheme = uri.getScheme();
/* 1646 */       boolean isLevelProtocol = "level".equals(scheme);
/*      */       
/* 1648 */       if (!"http".equals(scheme) && !"https".equals(scheme) && !isLevelProtocol) {
/* 1649 */         client.getNetworkManager().sendPacket((Packet)new C19PacketResourcePackStatus(packet.getHash(), C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/* 1650 */         throw new URISyntaxException(url, "Wrong protocol");
/*      */       } 
/*      */       
/* 1653 */       url = URLDecoder.decode(url.substring("level://".length()), StandardCharsets.UTF_8.toString());
/*      */       
/* 1655 */       if (isLevelProtocol && (url.contains("..") || !url.endsWith("/resources.zip"))) {
/* 1656 */         System.out.println("Malicious server tried to access " + url);
/* 1657 */         EntityPlayerSP player = (Minecraft.getMinecraft()).thePlayer;
/*      */         
/* 1659 */         if (player != null) {
/* 1660 */           player.addChatMessage((IChatComponent)new ChatComponentText(ChatFormatting.RED + ChatFormatting.BOLD.toString() + "[WARNING] The current server has attempted to be malicious but we have stopped them."));
/*      */         }
/*      */ 
/*      */         
/* 1664 */         throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
/*      */       } 
/*      */       
/* 1667 */       return true;
/* 1668 */     } catch (URISyntaxException e) {
/* 1669 */       e.printStackTrace();
/* 1670 */       return false;
/* 1671 */     } catch (UnsupportedEncodingException e) {
/* 1672 */       e.printStackTrace();
/*      */ 
/*      */       
/* 1675 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleResourcePack(S48PacketResourcePackSend packetIn) {
/* 1680 */     validateResourcePackUrl(this, packetIn);
/*      */ 
/*      */     
/* 1683 */     String url = packetIn.getURL();
/* 1684 */     final String hash = packetIn.getHash();
/*      */     
/* 1686 */     if (url.startsWith("level://")) {
/*      */       
/* 1688 */       String s2 = url.substring("level://".length());
/* 1689 */       File file1 = new File(this.gameController.mcDataDir, "saves");
/* 1690 */       File file2 = new File(file1, s2);
/*      */       
/* 1692 */       if (file2.isFile())
/*      */       {
/* 1694 */         this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1695 */         Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(file2), new FutureCallback<Object>()
/*      */             {
/*      */               public void onSuccess(Object p_onSuccess_1_)
/*      */               {
/* 1699 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */               }
/*      */               
/*      */               public void onFailure(Throwable p_onFailure_1_) {
/* 1703 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */               }
/*      */             });
/*      */       }
/*      */       else
/*      */       {
/* 1709 */         this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1714 */     else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
/*      */       
/* 1716 */       this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1717 */       Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(url, hash), new FutureCallback<Object>()
/*      */           {
/*      */             public void onSuccess(Object p_onSuccess_1_)
/*      */             {
/* 1721 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */             }
/*      */             
/*      */             public void onFailure(Throwable p_onFailure_1_) {
/* 1725 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */             }
/*      */           });
/*      */     }
/* 1729 */     else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
/*      */       
/* 1731 */       this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.DECLINED));
/*      */     }
/*      */     else {
/*      */       
/* 1735 */       this.gameController.addScheduledTask(() -> this.gameController.displayGuiScreen((GuiScreen)new GuiYesNo((), I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0)));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn) {
/* 1777 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1778 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1780 */     if (entity != null)
/*      */     {
/* 1782 */       entity.clientUpdateEntityNBT(packetIn.getTagCompound());
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
/*      */   public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
/* 1794 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1796 */     if ("MC|TrList".equals(packetIn.getChannelName())) {
/*      */       
/* 1798 */       PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1802 */         int i = packetbuffer.readInt();
/* 1803 */         GuiScreen guiscreen = this.gameController.currentScreen;
/*      */         
/* 1805 */         if (guiscreen != null && guiscreen instanceof GuiMerchant && i == this.gameController.thePlayer.openContainer.windowId)
/*      */         {
/* 1807 */           IMerchant imerchant = ((GuiMerchant)guiscreen).getMerchant();
/* 1808 */           MerchantRecipeList merchantrecipelist = MerchantRecipeList.readFromBuf(packetbuffer);
/* 1809 */           imerchant.setRecipes(merchantrecipelist);
/*      */         }
/*      */       
/* 1812 */       } catch (IOException ioexception) {
/*      */         
/* 1814 */         logger.error("Couldn't load trade info", ioexception);
/*      */       }
/*      */       finally {
/*      */         
/* 1818 */         packetbuffer.release();
/*      */       }
/*      */     
/* 1821 */     } else if ("MC|Brand".equals(packetIn.getChannelName())) {
/*      */       
/* 1823 */       this.gameController.thePlayer.setClientBrand(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */     }
/* 1825 */     else if ("MC|BOpen".equals(packetIn.getChannelName())) {
/*      */       
/* 1827 */       ItemStack itemstack = this.gameController.thePlayer.getCurrentEquippedItem();
/*      */       
/* 1829 */       if (itemstack != null && itemstack.getItem() == Items.written_book)
/*      */       {
/* 1831 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenBook((EntityPlayer)this.gameController.thePlayer, itemstack, false));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn) {
/* 1841 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1842 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1844 */     if (packetIn.func_149338_e() == 0) {
/*      */       
/* 1846 */       ScoreObjective scoreobjective = scoreboard.addScoreObjective(packetIn.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
/* 1847 */       scoreobjective.setDisplayName(packetIn.func_149337_d());
/* 1848 */       scoreobjective.setRenderType(packetIn.func_179817_d());
/*      */     }
/*      */     else {
/*      */       
/* 1852 */       ScoreObjective scoreobjective1 = scoreboard.getObjective(packetIn.func_149339_c());
/*      */       
/* 1854 */       if (packetIn.func_149338_e() == 1) {
/*      */         
/* 1856 */         scoreboard.removeObjective(scoreobjective1);
/*      */       }
/* 1858 */       else if (packetIn.func_149338_e() == 2) {
/*      */         
/* 1860 */         scoreobjective1.setDisplayName(packetIn.func_149337_d());
/* 1861 */         scoreobjective1.setRenderType(packetIn.func_179817_d());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateScore(S3CPacketUpdateScore packetIn) {
/* 1871 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1872 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1873 */     ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.getObjectiveName());
/*      */     
/* 1875 */     if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
/*      */       
/* 1877 */       Score score = scoreboard.getValueFromObjective(packetIn.getPlayerName(), scoreobjective);
/* 1878 */       score.setScorePoints(packetIn.getScoreValue());
/*      */     }
/* 1880 */     else if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
/*      */       
/* 1882 */       if (StringUtils.isNullOrEmpty(packetIn.getObjectiveName())) {
/*      */         
/* 1884 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), null);
/*      */       }
/* 1886 */       else if (scoreobjective != null) {
/*      */         
/* 1888 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn) {
/* 1899 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1900 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1902 */     if (packetIn.func_149370_d().length() == 0) {
/*      */       
/* 1904 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), null);
/*      */     }
/*      */     else {
/*      */       
/* 1908 */       ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.func_149370_d());
/* 1909 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), scoreobjective);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTeams(S3EPacketTeams packetIn) {
/*      */     ScorePlayerTeam scoreplayerteam;
/* 1919 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1920 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */ 
/*      */     
/* 1923 */     if (packetIn.func_149307_h() == 0) {
/*      */       
/* 1925 */       scoreplayerteam = scoreboard.createTeam(packetIn.func_149312_c());
/*      */     }
/*      */     else {
/*      */       
/* 1929 */       scoreplayerteam = scoreboard.getTeam(packetIn.func_149312_c());
/*      */     } 
/*      */     
/* 1932 */     if (packetIn.func_149307_h() == 0 || packetIn.func_149307_h() == 2) {
/*      */       
/* 1934 */       scoreplayerteam.setTeamName(packetIn.func_149306_d());
/* 1935 */       scoreplayerteam.setNamePrefix(packetIn.func_149311_e());
/* 1936 */       scoreplayerteam.setNameSuffix(packetIn.func_149309_f());
/* 1937 */       scoreplayerteam.setChatFormat(ChatFormatting.func_175744_a(packetIn.func_179813_h()));
/* 1938 */       scoreplayerteam.func_98298_a(packetIn.func_149308_i());
/* 1939 */       Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(packetIn.func_179814_i());
/*      */       
/* 1941 */       if (team$enumvisible != null)
/*      */       {
/* 1943 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*      */     } 
/*      */     
/* 1947 */     if (packetIn.func_149307_h() == 0 || packetIn.func_149307_h() == 3)
/*      */     {
/* 1949 */       for (String s : packetIn.func_149310_g())
/*      */       {
/* 1951 */         scoreboard.addPlayerToTeam(s, packetIn.func_149312_c());
/*      */       }
/*      */     }
/*      */     
/* 1955 */     if (packetIn.func_149307_h() == 4)
/*      */     {
/* 1957 */       for (String s1 : packetIn.func_149310_g())
/*      */       {
/* 1959 */         scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
/*      */       }
/*      */     }
/*      */     
/* 1963 */     if (packetIn.func_149307_h() == 1)
/*      */     {
/* 1965 */       scoreboard.removeTeam(scoreplayerteam);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleParticles(S2APacketParticles packetIn) {
/* 1975 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1977 */     if (packetIn.getParticleCount() == 0) {
/*      */       
/* 1979 */       double d0 = (packetIn.getParticleSpeed() * packetIn.getXOffset());
/* 1980 */       double d2 = (packetIn.getParticleSpeed() * packetIn.getYOffset());
/* 1981 */       double d4 = (packetIn.getParticleSpeed() * packetIn.getZOffset());
/*      */ 
/*      */       
/*      */       try {
/* 1985 */         this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate(), packetIn.getYCoordinate(), packetIn.getZCoordinate(), d0, d2, d4, packetIn.getParticleArgs());
/*      */       }
/* 1987 */       catch (Throwable var17) {
/*      */         
/* 1989 */         logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1994 */       for (int i = 0; i < packetIn.getParticleCount(); i++) {
/*      */         
/* 1996 */         double d1 = this.avRandomizer.nextGaussian() * packetIn.getXOffset();
/* 1997 */         double d3 = this.avRandomizer.nextGaussian() * packetIn.getYOffset();
/* 1998 */         double d5 = this.avRandomizer.nextGaussian() * packetIn.getZOffset();
/* 1999 */         double d6 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 2000 */         double d7 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 2001 */         double d8 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/*      */ 
/*      */         
/*      */         try {
/* 2005 */           this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate() + d1, packetIn.getYCoordinate() + d3, packetIn.getZCoordinate() + d5, d6, d7, d8, packetIn.getParticleArgs());
/*      */         }
/* 2007 */         catch (Throwable var16) {
/*      */           
/* 2009 */           logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityProperties(S20PacketEntityProperties packetIn) {
/* 2023 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2024 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 2026 */     if (entity != null) {
/*      */       
/* 2028 */       if (!(entity instanceof EntityLivingBase))
/*      */       {
/* 2030 */         throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
/*      */       }
/*      */ 
/*      */       
/* 2034 */       BaseAttributeMap baseattributemap = ((EntityLivingBase)entity).getAttributeMap();
/*      */       
/* 2036 */       for (S20PacketEntityProperties.Snapshot s20packetentityproperties$snapshot : packetIn.func_149441_d()) {
/*      */         
/* 2038 */         IAttributeInstance iattributeinstance = baseattributemap.getAttributeInstanceByName(s20packetentityproperties$snapshot.func_151409_a());
/*      */         
/* 2040 */         if (iattributeinstance == null)
/*      */         {
/* 2042 */           iattributeinstance = baseattributemap.registerAttribute((IAttribute)new RangedAttribute(null, s20packetentityproperties$snapshot.func_151409_a(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
/*      */         }
/*      */         
/* 2045 */         iattributeinstance.setBaseValue(s20packetentityproperties$snapshot.func_151410_b());
/* 2046 */         iattributeinstance.removeAllModifiers();
/*      */         
/* 2048 */         for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.func_151408_c())
/*      */         {
/* 2050 */           iattributeinstance.applyModifier(attributemodifier);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/* 2062 */     return this.netManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
/* 2067 */     return this.playerInfoMap.values();
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_) {
/* 2072 */     return this.playerInfoMap.get(p_175102_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(String p_175104_1_) {
/* 2080 */     for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
/*      */       
/* 2082 */       if (networkplayerinfo.getGameProfile().getName().equals(p_175104_1_))
/*      */       {
/* 2084 */         return networkplayerinfo;
/*      */       }
/*      */     } 
/*      */     
/* 2088 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 2093 */     return this.profile;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\network\NetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */