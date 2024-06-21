/*      */ package net.minecraft.network;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.primitives.Doubles;
/*      */ import com.google.common.primitives.Floats;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import java.io.IOException;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Future;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerBeacon;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.ContainerRepair;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.ItemEditableBook;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemWritableBook;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.play.INetHandlerPlayServer;
/*      */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*      */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*      */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*      */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.client.C0APacketAnimation;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.network.play.client.C0CPacketInput;
/*      */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*      */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*      */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*      */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*      */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.client.C18PacketSpectate;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*      */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.UserListBansEntry;
/*      */ import net.minecraft.server.management.UserListEntry;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatAllowedCharacters;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
/*   98 */   private static final Logger logger = LogManager.getLogger();
/*      */   
/*      */   public final NetworkManager netManager;
/*      */   
/*      */   private final MinecraftServer serverController;
/*      */   
/*      */   public EntityPlayerMP playerEntity;
/*      */   
/*      */   private int networkTickCount;
/*      */   
/*      */   private int field_175090_f;
/*      */   
/*      */   private int floatingTickCount;
/*      */   
/*      */   private boolean field_147366_g;
/*      */   
/*      */   private int field_147378_h;
/*      */   
/*      */   private long lastPingTime;
/*      */   
/*      */   private long lastSentPingPacket;
/*      */   private int chatSpamThresholdCount;
/*      */   private int itemDropThreshold;
/*  121 */   private IntHashMap<Short> field_147372_n = new IntHashMap();
/*      */   
/*      */   private double lastPosX;
/*      */   private double lastPosY;
/*      */   private double lastPosZ;
/*      */   private boolean hasMoved = true;
/*      */   
/*      */   public NetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn) {
/*  129 */     this.serverController = server;
/*  130 */     this.netManager = networkManagerIn;
/*  131 */     networkManagerIn.setNetHandler((INetHandler)this);
/*  132 */     this.playerEntity = playerIn;
/*  133 */     playerIn.playerNetServerHandler = this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*  141 */     this.field_147366_g = false;
/*  142 */     this.networkTickCount++;
/*  143 */     this.serverController.theProfiler.startSection("keepAlive");
/*      */     
/*  145 */     if (this.networkTickCount - this.lastSentPingPacket > 40L) {
/*      */       
/*  147 */       this.lastSentPingPacket = this.networkTickCount;
/*  148 */       this.lastPingTime = currentTimeMillis();
/*  149 */       this.field_147378_h = (int)this.lastPingTime;
/*  150 */       sendPacket((Packet)new S00PacketKeepAlive(this.field_147378_h));
/*      */     } 
/*      */     
/*  153 */     this.serverController.theProfiler.endSection();
/*      */     
/*  155 */     if (this.chatSpamThresholdCount > 0)
/*      */     {
/*  157 */       this.chatSpamThresholdCount--;
/*      */     }
/*      */     
/*  160 */     if (this.itemDropThreshold > 0)
/*      */     {
/*  162 */       this.itemDropThreshold--;
/*      */     }
/*      */     
/*  165 */     if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60))
/*      */     {
/*  167 */       kickPlayerFromServer("You have been idle for too long!");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/*  173 */     return this.netManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void kickPlayerFromServer(String reason) {
/*  181 */     final ChatComponentText chatcomponenttext = new ChatComponentText(reason);
/*  182 */     this.netManager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>()
/*      */         {
/*      */           public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception
/*      */           {
/*  186 */             NetHandlerPlayServer.this.netManager.closeChannel((IChatComponent)chatcomponenttext);
/*      */           }
/*      */         },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/*  189 */     this.netManager.disableAutoRead();
/*  190 */     Futures.getUnchecked((Future)this.serverController.addScheduledTask(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  194 */               NetHandlerPlayServer.this.netManager.checkDisconnected();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processInput(C0CPacketInput packetIn) {
/*  205 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  206 */     this.playerEntity.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.getForwardSpeed(), packetIn.isJumping(), packetIn.isSneaking());
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_183006_b(C03PacketPlayer p_183006_1_) {
/*  211 */     return (!Doubles.isFinite(p_183006_1_.getPositionX()) || !Doubles.isFinite(p_183006_1_.getPositionY()) || !Doubles.isFinite(p_183006_1_.getPositionZ()) || !Floats.isFinite(p_183006_1_.getPitch()) || !Floats.isFinite(p_183006_1_.getYaw()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayer(C03PacketPlayer packetIn) {
/*  219 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  221 */     if (func_183006_b(packetIn)) {
/*      */       
/*  223 */       kickPlayerFromServer("Invalid move packet received");
/*      */     }
/*      */     else {
/*      */       
/*  227 */       WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  228 */       this.field_147366_g = true;
/*      */       
/*  230 */       if (!this.playerEntity.playerConqueredTheEnd) {
/*      */         
/*  232 */         double d0 = this.playerEntity.posX;
/*  233 */         double d1 = this.playerEntity.posY;
/*  234 */         double d2 = this.playerEntity.posZ;
/*  235 */         double d3 = 0.0D;
/*  236 */         double d4 = packetIn.getPositionX() - this.lastPosX;
/*  237 */         double d5 = packetIn.getPositionY() - this.lastPosY;
/*  238 */         double d6 = packetIn.getPositionZ() - this.lastPosZ;
/*      */         
/*  240 */         if (packetIn.isMoving()) {
/*      */           
/*  242 */           d3 = d4 * d4 + d5 * d5 + d6 * d6;
/*      */           
/*  244 */           if (!this.hasMoved && d3 < 0.25D)
/*      */           {
/*  246 */             this.hasMoved = true;
/*      */           }
/*      */         } 
/*      */         
/*  250 */         if (this.hasMoved) {
/*      */           
/*  252 */           this.field_175090_f = this.networkTickCount;
/*      */           
/*  254 */           if (this.playerEntity.ridingEntity != null) {
/*      */             
/*  256 */             float f4 = this.playerEntity.rotationYaw;
/*  257 */             float f = this.playerEntity.rotationPitch;
/*  258 */             this.playerEntity.ridingEntity.updateRiderPosition();
/*  259 */             double d16 = this.playerEntity.posX;
/*  260 */             double d17 = this.playerEntity.posY;
/*  261 */             double d18 = this.playerEntity.posZ;
/*      */             
/*  263 */             if (packetIn.getRotating()) {
/*      */               
/*  265 */               f4 = packetIn.getYaw();
/*  266 */               f = packetIn.getPitch();
/*      */             } 
/*      */             
/*  269 */             this.playerEntity.onGround = packetIn.isOnGround();
/*  270 */             this.playerEntity.onUpdateEntity();
/*  271 */             this.playerEntity.setPositionAndRotation(d16, d17, d18, f4, f);
/*      */             
/*  273 */             if (this.playerEntity.ridingEntity != null)
/*      */             {
/*  275 */               this.playerEntity.ridingEntity.updateRiderPosition();
/*      */             }
/*      */             
/*  278 */             this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*      */             
/*  280 */             if (this.playerEntity.ridingEntity != null) {
/*      */               
/*  282 */               if (d3 > 4.0D) {
/*      */                 
/*  284 */                 Entity entity = this.playerEntity.ridingEntity;
/*  285 */                 this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S18PacketEntityTeleport(entity));
/*  286 */                 setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */               } 
/*      */               
/*  289 */               this.playerEntity.ridingEntity.isAirBorne = true;
/*      */             } 
/*      */             
/*  292 */             if (this.hasMoved) {
/*      */               
/*  294 */               this.lastPosX = this.playerEntity.posX;
/*  295 */               this.lastPosY = this.playerEntity.posY;
/*  296 */               this.lastPosZ = this.playerEntity.posZ;
/*      */             } 
/*      */             
/*  299 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  303 */           if (this.playerEntity.isPlayerSleeping()) {
/*      */             
/*  305 */             this.playerEntity.onUpdateEntity();
/*  306 */             this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  307 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  311 */           double d7 = this.playerEntity.posY;
/*  312 */           this.lastPosX = this.playerEntity.posX;
/*  313 */           this.lastPosY = this.playerEntity.posY;
/*  314 */           this.lastPosZ = this.playerEntity.posZ;
/*  315 */           double d8 = this.playerEntity.posX;
/*  316 */           double d9 = this.playerEntity.posY;
/*  317 */           double d10 = this.playerEntity.posZ;
/*  318 */           float f1 = this.playerEntity.rotationYaw;
/*  319 */           float f2 = this.playerEntity.rotationPitch;
/*      */           
/*  321 */           if (packetIn.isMoving() && packetIn.getPositionY() == -999.0D)
/*      */           {
/*  323 */             packetIn.setMoving(false);
/*      */           }
/*      */           
/*  326 */           if (packetIn.isMoving()) {
/*      */             
/*  328 */             d8 = packetIn.getPositionX();
/*  329 */             d9 = packetIn.getPositionY();
/*  330 */             d10 = packetIn.getPositionZ();
/*      */             
/*  332 */             if (Math.abs(packetIn.getPositionX()) > 3.0E7D || Math.abs(packetIn.getPositionZ()) > 3.0E7D) {
/*      */               
/*  334 */               kickPlayerFromServer("Illegal position");
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  339 */           if (packetIn.getRotating()) {
/*      */             
/*  341 */             f1 = packetIn.getYaw();
/*  342 */             f2 = packetIn.getPitch();
/*      */           } 
/*      */           
/*  345 */           this.playerEntity.onUpdateEntity();
/*  346 */           this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */           
/*  348 */           if (!this.hasMoved) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*  353 */           double d11 = d8 - this.playerEntity.posX;
/*  354 */           double d12 = d9 - this.playerEntity.posY;
/*  355 */           double d13 = d10 - this.playerEntity.posZ;
/*  356 */           double d14 = this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ;
/*  357 */           double d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*      */           
/*  359 */           if (d15 - d14 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getCommandSenderName()))) {
/*      */             
/*  361 */             logger.warn(this.playerEntity.getCommandSenderName() + " moved too quickly! " + d11 + "," + d12 + "," + d13 + " (" + d11 + ", " + d12 + ", " + d13 + ")");
/*  362 */             setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */             
/*      */             return;
/*      */           } 
/*  366 */           float f3 = 0.0625F;
/*  367 */           boolean flag = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */           
/*  369 */           if (this.playerEntity.onGround && !packetIn.isOnGround() && d12 > 0.0D)
/*      */           {
/*  371 */             this.playerEntity.jump();
/*      */           }
/*      */           
/*  374 */           this.playerEntity.moveEntity(d11, d12, d13);
/*  375 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  376 */           d11 = d8 - this.playerEntity.posX;
/*  377 */           d12 = d9 - this.playerEntity.posY;
/*      */           
/*  379 */           if (d12 > -0.5D || d12 < 0.5D)
/*      */           {
/*  381 */             d12 = 0.0D;
/*      */           }
/*      */           
/*  384 */           d13 = d10 - this.playerEntity.posZ;
/*  385 */           d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*  386 */           boolean flag1 = false;
/*      */           
/*  388 */           if (d15 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
/*      */             
/*  390 */             flag1 = true;
/*  391 */             logger.warn(this.playerEntity.getCommandSenderName() + " moved wrongly!");
/*      */           } 
/*      */           
/*  394 */           this.playerEntity.setPositionAndRotation(d8, d9, d10, f1, f2);
/*  395 */           this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
/*      */           
/*  397 */           if (!this.playerEntity.noClip) {
/*      */             
/*  399 */             boolean flag2 = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */             
/*  401 */             if (flag && (flag1 || !flag2) && !this.playerEntity.isPlayerSleeping()) {
/*      */               
/*  403 */               setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  408 */           AxisAlignedBB axisalignedbb = this.playerEntity.getEntityBoundingBox().expand(f3, f3, f3).addCoord(0.0D, -0.55D, 0.0D);
/*      */           
/*  410 */           if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldserver.checkBlockCollision(axisalignedbb)) {
/*      */             
/*  412 */             if (d12 >= -0.03125D) {
/*      */               
/*  414 */               this.floatingTickCount++;
/*      */               
/*  416 */               if (this.floatingTickCount > 80) {
/*      */                 
/*  418 */                 logger.warn(this.playerEntity.getCommandSenderName() + " was kicked for floating too long!");
/*  419 */                 kickPlayerFromServer("Flying is not enabled on this server");
/*      */ 
/*      */                 
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           } else {
/*  426 */             this.floatingTickCount = 0;
/*      */           } 
/*      */           
/*  429 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  430 */           this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*  431 */           this.playerEntity.handleFalling(this.playerEntity.posY - d7, packetIn.isOnGround());
/*      */         }
/*  433 */         else if (this.networkTickCount - this.field_175090_f > 20) {
/*      */           
/*  435 */           setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
/*  443 */     setPlayerLocation(x, y, z, yaw, pitch, Collections.emptySet());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<S08PacketPlayerPosLook.EnumFlags> relativeSet) {
/*  448 */     this.hasMoved = false;
/*  449 */     this.lastPosX = x;
/*  450 */     this.lastPosY = y;
/*  451 */     this.lastPosZ = z;
/*      */     
/*  453 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X))
/*      */     {
/*  455 */       this.lastPosX += this.playerEntity.posX;
/*      */     }
/*      */     
/*  458 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y))
/*      */     {
/*  460 */       this.lastPosY += this.playerEntity.posY;
/*      */     }
/*      */     
/*  463 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Z))
/*      */     {
/*  465 */       this.lastPosZ += this.playerEntity.posZ;
/*      */     }
/*      */     
/*  468 */     float f = yaw;
/*  469 */     float f1 = pitch;
/*      */     
/*  471 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT))
/*      */     {
/*  473 */       f = yaw + this.playerEntity.rotationYaw;
/*      */     }
/*      */     
/*  476 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT))
/*      */     {
/*  478 */       f1 = pitch + this.playerEntity.rotationPitch;
/*      */     }
/*      */     
/*  481 */     this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f, f1);
/*  482 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S08PacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerDigging(C07PacketPlayerDigging packetIn) {
/*      */     double d0, d1, d2, d3;
/*  492 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  493 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  494 */     BlockPos blockpos = packetIn.getPosition();
/*  495 */     this.playerEntity.markPlayerActive();
/*      */     
/*  497 */     switch (packetIn.getStatus()) {
/*      */       
/*      */       case PERFORM_RESPAWN:
/*  500 */         if (!this.playerEntity.isSpectator())
/*      */         {
/*  502 */           this.playerEntity.dropOneItem(false);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/*  508 */         if (!this.playerEntity.isSpectator())
/*      */         {
/*  510 */           this.playerEntity.dropOneItem(true);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case OPEN_INVENTORY_ACHIEVEMENT:
/*  516 */         this.playerEntity.stopUsingItem();
/*      */         return;
/*      */       
/*      */       case null:
/*      */       case null:
/*      */       case null:
/*  522 */         d0 = this.playerEntity.posX - blockpos.getX() + 0.5D;
/*  523 */         d1 = this.playerEntity.posY - blockpos.getY() + 0.5D + 1.5D;
/*  524 */         d2 = this.playerEntity.posZ - blockpos.getZ() + 0.5D;
/*  525 */         d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  527 */         if (d3 > 36.0D) {
/*      */           return;
/*      */         }
/*      */         
/*  531 */         if (blockpos.getY() >= this.serverController.getBuildLimit()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  537 */         if (packetIn.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
/*      */           
/*  539 */           if (!this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos))
/*      */           {
/*  541 */             this.playerEntity.theItemInWorldManager.onBlockClicked(blockpos, packetIn.getFacing());
/*      */           }
/*      */           else
/*      */           {
/*  545 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  550 */           if (packetIn.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
/*      */             
/*  552 */             this.playerEntity.theItemInWorldManager.blockRemoving(blockpos);
/*      */           }
/*  554 */           else if (packetIn.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
/*      */             
/*  556 */             this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
/*      */           } 
/*      */           
/*  559 */           if (worldserver.getBlockState(blockpos).getBlock().getMaterial() != Material.air)
/*      */           {
/*  561 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  569 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement packetIn) {
/*  578 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  579 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  580 */     ItemStack itemstack = this.playerEntity.inventory.getCurrentItem();
/*  581 */     boolean flag = false;
/*  582 */     BlockPos blockpos = packetIn.getPosition();
/*  583 */     EnumFacing enumfacing = EnumFacing.getFront(packetIn.getPlacedBlockDirection());
/*  584 */     this.playerEntity.markPlayerActive();
/*      */     
/*  586 */     if (packetIn.getPlacedBlockDirection() == 255) {
/*      */       
/*  588 */       if (itemstack == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  593 */       this.playerEntity.theItemInWorldManager.tryUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack);
/*      */     }
/*  595 */     else if (blockpos.getY() < this.serverController.getBuildLimit() - 1 || (enumfacing != EnumFacing.UP && blockpos.getY() < this.serverController.getBuildLimit())) {
/*      */       
/*  597 */       if (this.hasMoved && this.playerEntity.getDistanceSq(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos))
/*      */       {
/*  599 */         this.playerEntity.theItemInWorldManager.activateBlockOrUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack, blockpos, enumfacing, packetIn.getPlacedBlockOffsetX(), packetIn.getPlacedBlockOffsetY(), packetIn.getPlacedBlockOffsetZ());
/*      */       }
/*      */       
/*  602 */       flag = true;
/*      */     }
/*      */     else {
/*      */       
/*  606 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("build.tooHigh", new Object[] { Integer.valueOf(this.serverController.getBuildLimit()) });
/*  607 */       chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.RED);
/*  608 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*  609 */       flag = true;
/*      */     } 
/*      */     
/*  612 */     if (flag) {
/*      */       
/*  614 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*  615 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos.offset(enumfacing)));
/*      */     } 
/*      */     
/*  618 */     itemstack = this.playerEntity.inventory.getCurrentItem();
/*      */     
/*  620 */     if (itemstack != null && itemstack.stackSize == 0) {
/*      */       
/*  622 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
/*  623 */       itemstack = null;
/*      */     } 
/*      */     
/*  626 */     if (itemstack == null || itemstack.getMaxItemUseDuration() == 0) {
/*      */       
/*  628 */       this.playerEntity.isChangingQuantityOnly = true;
/*  629 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
/*  630 */       Slot slot = this.playerEntity.openContainer.getSlotFromInventory((IInventory)this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
/*  631 */       this.playerEntity.openContainer.detectAndSendChanges();
/*  632 */       this.playerEntity.isChangingQuantityOnly = false;
/*      */       
/*  634 */       if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), packetIn.getStack()))
/*      */       {
/*  636 */         sendPacket((Packet)new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slot.slotNumber, this.playerEntity.inventory.getCurrentItem()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSpectate(C18PacketSpectate packetIn) {
/*  643 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  645 */     if (this.playerEntity.isSpectator()) {
/*      */       
/*  647 */       Entity entity = null;
/*      */       
/*  649 */       for (WorldServer worldserver : this.serverController.worldServers) {
/*      */         
/*  651 */         if (worldserver != null) {
/*      */           
/*  653 */           entity = packetIn.getEntity(worldserver);
/*      */           
/*  655 */           if (entity != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  662 */       if (entity != null) {
/*      */         
/*  664 */         this.playerEntity.setSpectatingEntity((Entity)this.playerEntity);
/*  665 */         this.playerEntity.mountEntity(null);
/*      */         
/*  667 */         if (entity.worldObj != this.playerEntity.worldObj) {
/*      */           
/*  669 */           WorldServer worldserver1 = this.playerEntity.getServerForPlayer();
/*  670 */           WorldServer worldserver2 = (WorldServer)entity.worldObj;
/*  671 */           this.playerEntity.dimension = entity.dimension;
/*  672 */           sendPacket((Packet)new S07PacketRespawn(this.playerEntity.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
/*  673 */           worldserver1.removePlayerEntityDangerously((Entity)this.playerEntity);
/*  674 */           this.playerEntity.isDead = false;
/*  675 */           this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*      */           
/*  677 */           if (this.playerEntity.isEntityAlive()) {
/*      */             
/*  679 */             worldserver1.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*  680 */             worldserver2.spawnEntityInWorld((Entity)this.playerEntity);
/*  681 */             worldserver2.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*      */           } 
/*      */           
/*  684 */           this.playerEntity.setWorld((World)worldserver2);
/*  685 */           this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, worldserver1);
/*  686 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*  687 */           this.playerEntity.theItemInWorldManager.setWorld(worldserver2);
/*  688 */           this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldserver2);
/*  689 */           this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
/*      */         }
/*      */         else {
/*      */           
/*  693 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleResourcePackStatus(C19PacketResourcePackStatus packetIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  708 */     logger.info(this.playerEntity.getCommandSenderName() + " lost connection: " + reason);
/*  709 */     this.serverController.refreshStatusNextTick();
/*  710 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
/*  711 */     chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.YELLOW);
/*  712 */     this.serverController.getConfigurationManager().sendChatMsg((IChatComponent)chatcomponenttranslation);
/*  713 */     this.playerEntity.mountEntityAndWakeUp();
/*  714 */     this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
/*      */     
/*  716 */     if (this.serverController.isSinglePlayer() && this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())) {
/*      */       
/*  718 */       logger.info("Stopping singleplayer server as player logged out");
/*  719 */       this.serverController.initiateShutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacket(final Packet packetIn) {
/*  725 */     if (packetIn instanceof S02PacketChat) {
/*      */       
/*  727 */       S02PacketChat s02packetchat = (S02PacketChat)packetIn;
/*  728 */       EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.playerEntity.getChatVisibility();
/*      */       
/*  730 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  735 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02packetchat.isChat()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  743 */       this.netManager.sendPacket(packetIn);
/*      */     }
/*  745 */     catch (Throwable throwable) {
/*      */       
/*  747 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Sending packet");
/*  748 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Packet being sent");
/*  749 */       crashreportcategory.addCrashSectionCallable("Packet class", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  753 */               return packetIn.getClass().getCanonicalName();
/*      */             }
/*      */           });
/*  756 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processHeldItemChange(C09PacketHeldItemChange packetIn) {
/*  765 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  767 */     if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
/*      */       
/*  769 */       this.playerEntity.inventory.currentItem = packetIn.getSlotId();
/*  770 */       this.playerEntity.markPlayerActive();
/*      */     }
/*      */     else {
/*      */       
/*  774 */       logger.warn(this.playerEntity.getCommandSenderName() + " tried to set an invalid carried item");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processChatMessage(C01PacketChatMessage packetIn) {
/*  783 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  785 */     if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */       
/*  787 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
/*  788 */       chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.RED);
/*  789 */       sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*      */     }
/*      */     else {
/*      */       
/*  793 */       this.playerEntity.markPlayerActive();
/*  794 */       String s = packetIn.getMessage();
/*  795 */       s = StringUtils.normalizeSpace(s);
/*      */       
/*  797 */       for (int i = 0; i < s.length(); i++) {
/*      */         
/*  799 */         if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i))) {
/*      */           
/*  801 */           kickPlayerFromServer("Illegal characters in chat");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  806 */       if (s.startsWith("/")) {
/*      */         
/*  808 */         handleSlashCommand(s);
/*      */       }
/*      */       else {
/*      */         
/*  812 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), s });
/*  813 */         this.serverController.getConfigurationManager().sendChatMsgImpl((IChatComponent)chatComponentTranslation, false);
/*      */       } 
/*      */       
/*  816 */       this.chatSpamThresholdCount += 20;
/*      */       
/*  818 */       if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile()))
/*      */       {
/*  820 */         kickPlayerFromServer("disconnect.spam");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSlashCommand(String command) {
/*  830 */     this.serverController.getCommandManager().executeCommand((ICommandSender)this.playerEntity, command);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAnimation(C0APacketAnimation packetIn) {
/*  835 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  836 */     this.playerEntity.markPlayerActive();
/*  837 */     this.playerEntity.swingItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEntityAction(C0BPacketEntityAction packetIn) {
/*  846 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  847 */     this.playerEntity.markPlayerActive();
/*      */     
/*  849 */     switch (packetIn.getAction()) {
/*      */       
/*      */       case PERFORM_RESPAWN:
/*  852 */         this.playerEntity.setSneaking(true);
/*      */         return;
/*      */       
/*      */       case REQUEST_STATS:
/*  856 */         this.playerEntity.setSneaking(false);
/*      */         return;
/*      */       
/*      */       case OPEN_INVENTORY_ACHIEVEMENT:
/*  860 */         this.playerEntity.setSprinting(true);
/*      */         return;
/*      */       
/*      */       case null:
/*  864 */         this.playerEntity.setSprinting(false);
/*      */         return;
/*      */       
/*      */       case null:
/*  868 */         this.playerEntity.wakeUpPlayer(false, true, true);
/*  869 */         this.hasMoved = false;
/*      */         return;
/*      */       
/*      */       case null:
/*  873 */         if (this.playerEntity.ridingEntity instanceof EntityHorse)
/*      */         {
/*  875 */           ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(packetIn.getAuxData());
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case null:
/*  881 */         if (this.playerEntity.ridingEntity instanceof EntityHorse)
/*      */         {
/*  883 */           ((EntityHorse)this.playerEntity.ridingEntity).openGUI((EntityPlayer)this.playerEntity);
/*      */         }
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  889 */     throw new IllegalArgumentException("Invalid client command!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processUseEntity(C02PacketUseEntity packetIn) {
/*  899 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  900 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  901 */     Entity entity = packetIn.getEntityFromWorld((World)worldserver);
/*  902 */     this.playerEntity.markPlayerActive();
/*      */     
/*  904 */     if (entity != null) {
/*      */       
/*  906 */       boolean flag = this.playerEntity.canEntityBeSeen(entity);
/*  907 */       double d0 = 36.0D;
/*      */       
/*  909 */       if (!flag)
/*      */       {
/*  911 */         d0 = 9.0D;
/*      */       }
/*      */       
/*  914 */       if (this.playerEntity.getDistanceSqToEntity(entity) < d0)
/*      */       {
/*  916 */         if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT) {
/*      */           
/*  918 */           this.playerEntity.interactWith(entity);
/*      */         }
/*  920 */         else if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
/*      */           
/*  922 */           entity.interactAt((EntityPlayer)this.playerEntity, packetIn.getHitVec());
/*      */         }
/*  924 */         else if (packetIn.getAction() == C02PacketUseEntity.Action.ATTACK) {
/*      */           
/*  926 */           if (entity instanceof EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb || entity instanceof net.minecraft.entity.projectile.EntityArrow || entity == this.playerEntity) {
/*      */             
/*  928 */             kickPlayerFromServer("Attempting to attack an invalid entity");
/*  929 */             this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
/*      */             
/*      */             return;
/*      */           } 
/*  933 */           this.playerEntity.attackTargetEntityWithCurrentItem(entity);
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
/*      */   public void processClientStatus(C16PacketClientStatus packetIn) {
/*  945 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  946 */     this.playerEntity.markPlayerActive();
/*  947 */     C16PacketClientStatus.EnumState c16packetclientstatus$enumstate = packetIn.getStatus();
/*      */     
/*  949 */     switch (c16packetclientstatus$enumstate) {
/*      */       
/*      */       case PERFORM_RESPAWN:
/*  952 */         if (this.playerEntity.playerConqueredTheEnd) {
/*      */           
/*  954 */           this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true); break;
/*      */         } 
/*  956 */         if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
/*      */           
/*  958 */           if (this.serverController.isSinglePlayer() && this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())) {
/*      */             
/*  960 */             this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*  961 */             this.serverController.deleteWorldAndStopServer();
/*      */             
/*      */             break;
/*      */           } 
/*  965 */           UserListBansEntry userlistbansentry = new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore");
/*  966 */           this.serverController.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/*  967 */           this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/*  972 */         if (this.playerEntity.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  977 */         this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/*  983 */         this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
/*      */         break;
/*      */       
/*      */       case OPEN_INVENTORY_ACHIEVEMENT:
/*  987 */         this.playerEntity.triggerAchievement((StatBase)AchievementList.openInventory);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCloseWindow(C0DPacketCloseWindow packetIn) {
/*  996 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  997 */     this.playerEntity.closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClickWindow(C0EPacketClickWindow packetIn) {
/* 1007 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1008 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1010 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity))
/*      */     {
/* 1012 */       if (this.playerEntity.isSpectator()) {
/*      */         
/* 1014 */         List<ItemStack> list = Lists.newArrayList();
/*      */         
/* 1016 */         for (int i = 0; i < this.playerEntity.openContainer.inventorySlots.size(); i++)
/*      */         {
/* 1018 */           list.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(i)).getStack());
/*      */         }
/*      */         
/* 1021 */         this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list);
/*      */       }
/*      */       else {
/*      */         
/* 1025 */         ItemStack itemstack = this.playerEntity.openContainer.slotClick(packetIn.getSlotId(), packetIn.getUsedButton(), packetIn.getMode(), (EntityPlayer)this.playerEntity);
/*      */         
/* 1027 */         if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), itemstack)) {
/*      */           
/* 1029 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/* 1030 */           this.playerEntity.isChangingQuantityOnly = true;
/* 1031 */           this.playerEntity.openContainer.detectAndSendChanges();
/* 1032 */           this.playerEntity.updateHeldItem();
/* 1033 */           this.playerEntity.isChangingQuantityOnly = false;
/*      */         }
/*      */         else {
/*      */           
/* 1037 */           this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(packetIn.getActionNumber()));
/* 1038 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
/* 1039 */           this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, false);
/* 1040 */           List<ItemStack> list1 = Lists.newArrayList();
/*      */           
/* 1042 */           for (int j = 0; j < this.playerEntity.openContainer.inventorySlots.size(); j++)
/*      */           {
/* 1044 */             list1.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(j)).getStack());
/*      */           }
/*      */           
/* 1047 */           this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list1);
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
/*      */   public void processEnchantItem(C11PacketEnchantItem packetIn) {
/* 1059 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1060 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1062 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*      */       
/* 1064 */       this.playerEntity.openContainer.enchantItem((EntityPlayer)this.playerEntity, packetIn.getButton());
/* 1065 */       this.playerEntity.openContainer.detectAndSendChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction packetIn) {
/* 1074 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/* 1076 */     if (this.playerEntity.theItemInWorldManager.isCreative()) {
/*      */       
/* 1078 */       boolean flag = (packetIn.getSlotId() < 0);
/* 1079 */       ItemStack itemstack = packetIn.getStack();
/*      */       
/* 1081 */       if (itemstack != null && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*      */         
/* 1083 */         NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("BlockEntityTag");
/*      */         
/* 1085 */         if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
/*      */           
/* 1087 */           BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
/* 1088 */           TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(blockpos);
/*      */           
/* 1090 */           if (tileentity != null) {
/*      */             
/* 1092 */             NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 1093 */             tileentity.writeToNBT(nbttagcompound1);
/* 1094 */             nbttagcompound1.removeTag("x");
/* 1095 */             nbttagcompound1.removeTag("y");
/* 1096 */             nbttagcompound1.removeTag("z");
/* 1097 */             itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1102 */       boolean flag1 = (packetIn.getSlotId() >= 1 && packetIn.getSlotId() < 36 + InventoryPlayer.getHotbarSize());
/* 1103 */       boolean flag2 = (itemstack == null || itemstack.getItem() != null);
/* 1104 */       boolean flag3 = (itemstack == null || (itemstack.getMetadata() >= 0 && itemstack.stackSize <= 64 && itemstack.stackSize > 0));
/*      */       
/* 1106 */       if (flag1 && flag2 && flag3) {
/*      */         
/* 1108 */         if (itemstack == null) {
/*      */           
/* 1110 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), null);
/*      */         }
/*      */         else {
/*      */           
/* 1114 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), itemstack);
/*      */         } 
/*      */         
/* 1117 */         this.playerEntity.inventoryContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */       }
/* 1119 */       else if (flag && flag2 && flag3 && this.itemDropThreshold < 200) {
/*      */         
/* 1121 */         this.itemDropThreshold += 20;
/* 1122 */         EntityItem entityitem = this.playerEntity.dropPlayerItemWithRandomChoice(itemstack, true);
/*      */         
/* 1124 */         if (entityitem != null)
/*      */         {
/* 1126 */           entityitem.setAgeToCreativeDespawnTime();
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
/*      */   public void processConfirmTransaction(C0FPacketConfirmTransaction packetIn) {
/* 1139 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1140 */     Short oshort = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
/*      */     
/* 1142 */     if (oshort != null && packetIn.getUid() == oshort.shortValue() && this.playerEntity.openContainer.windowId == packetIn.getWindowId() && !this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator())
/*      */     {
/* 1144 */       this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void processUpdateSign(C12PacketUpdateSign packetIn) {
/* 1150 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1151 */     this.playerEntity.markPlayerActive();
/* 1152 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/* 1153 */     BlockPos blockpos = packetIn.getPosition();
/*      */     
/* 1155 */     if (worldserver.isBlockLoaded(blockpos)) {
/*      */       
/* 1157 */       TileEntity tileentity = worldserver.getTileEntity(blockpos);
/*      */       
/* 1159 */       if (!(tileentity instanceof TileEntitySign)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1164 */       TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */       
/* 1166 */       if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != this.playerEntity) {
/*      */         
/* 1168 */         this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " just tried to change non-editable sign");
/*      */         
/*      */         return;
/*      */       } 
/* 1172 */       IChatComponent[] aichatcomponent = packetIn.getLines();
/*      */       
/* 1174 */       for (int i = 0; i < aichatcomponent.length; i++)
/*      */       {
/* 1176 */         tileentitysign.signText[i] = (IChatComponent)new ChatComponentText(ChatFormatting.getTextWithoutFormattingCodes(aichatcomponent[i].getUnformattedText()));
/*      */       }
/*      */       
/* 1179 */       tileentitysign.markDirty();
/* 1180 */       worldserver.markBlockForUpdate(blockpos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeepAlive(C00PacketKeepAlive packetIn) {
/* 1189 */     if (packetIn.getKey() == this.field_147378_h) {
/*      */       
/* 1191 */       int i = (int)(currentTimeMillis() - this.lastPingTime);
/* 1192 */       this.playerEntity.ping = (this.playerEntity.ping * 3 + i) / 4;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private long currentTimeMillis() {
/* 1198 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerAbilities(C13PacketPlayerAbilities packetIn) {
/* 1206 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1207 */     this.playerEntity.capabilities.isFlying = (packetIn.isFlying() && this.playerEntity.capabilities.allowFlying);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processTabComplete(C14PacketTabComplete packetIn) {
/* 1215 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1216 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1218 */     for (String s : this.serverController.getTabCompletions((ICommandSender)this.playerEntity, packetIn.getMessage(), packetIn.getTargetBlock()))
/*      */     {
/* 1220 */       list.add(s);
/*      */     }
/*      */     
/* 1223 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S3APacketTabComplete(list.<String>toArray(new String[list.size()])));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientSettings(C15PacketClientSettings packetIn) {
/* 1232 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1233 */     this.playerEntity.handleClientSettings(packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processVanilla250Packet(C17PacketCustomPayload packetIn) {
/* 1241 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/* 1243 */     if ("MC|BEdit".equals(packetIn.getChannelName())) {
/*      */       
/* 1245 */       PacketBuffer packetbuffer3 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */ 
/*      */       
/*      */       try {
/* 1249 */         ItemStack itemstack1 = packetbuffer3.readItemStackFromBuffer();
/*      */         
/* 1251 */         if (itemstack1 != null) {
/*      */           
/* 1253 */           if (!ItemWritableBook.isNBTValid(itemstack1.getTagCompound()))
/*      */           {
/* 1255 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1258 */           ItemStack itemstack3 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1260 */           if (itemstack3 == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1265 */           if (itemstack1.getItem() == Items.writable_book && itemstack1.getItem() == itemstack3.getItem())
/*      */           {
/* 1267 */             itemstack3.setTagInfo("pages", (NBTBase)itemstack1.getTagCompound().getTagList("pages", 8));
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1273 */       } catch (Exception exception3) {
/*      */         
/* 1275 */         logger.error("Couldn't handle book info", exception3);
/*      */ 
/*      */         
/*      */         return;
/*      */       } finally {
/* 1280 */         packetbuffer3.release();
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1285 */     if ("MC|BSign".equals(packetIn.getChannelName())) {
/*      */       
/* 1287 */       PacketBuffer packetbuffer2 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */ 
/*      */       
/*      */       try {
/* 1291 */         ItemStack itemstack = packetbuffer2.readItemStackFromBuffer();
/*      */         
/* 1293 */         if (itemstack != null) {
/*      */           
/* 1295 */           if (!ItemEditableBook.validBookTagContents(itemstack.getTagCompound()))
/*      */           {
/* 1297 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1300 */           ItemStack itemstack2 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1302 */           if (itemstack2 == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1307 */           if (itemstack.getItem() == Items.written_book && itemstack2.getItem() == Items.writable_book) {
/*      */             
/* 1309 */             itemstack2.setTagInfo("author", (NBTBase)new NBTTagString(this.playerEntity.getCommandSenderName()));
/* 1310 */             itemstack2.setTagInfo("title", (NBTBase)new NBTTagString(itemstack.getTagCompound().getString("title")));
/* 1311 */             itemstack2.setTagInfo("pages", (NBTBase)itemstack.getTagCompound().getTagList("pages", 8));
/* 1312 */             itemstack2.setItem(Items.written_book);
/*      */           } 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1318 */       } catch (Exception exception4) {
/*      */         
/* 1320 */         logger.error("Couldn't sign book", exception4);
/*      */ 
/*      */         
/*      */         return;
/*      */       } finally {
/* 1325 */         packetbuffer2.release();
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1330 */     if ("MC|TrSel".equals(packetIn.getChannelName())) {
/*      */       
/*      */       try
/*      */       {
/* 1334 */         int i = packetIn.getBufferData().readInt();
/* 1335 */         Container container = this.playerEntity.openContainer;
/*      */         
/* 1337 */         if (container instanceof ContainerMerchant)
/*      */         {
/* 1339 */           ((ContainerMerchant)container).setCurrentRecipeIndex(i);
/*      */         }
/*      */       }
/* 1342 */       catch (Exception exception2)
/*      */       {
/* 1344 */         logger.error("Couldn't select trade", exception2);
/*      */       }
/*      */     
/* 1347 */     } else if ("MC|AdvCdm".equals(packetIn.getChannelName())) {
/*      */       
/* 1349 */       if (!this.serverController.isCommandBlockEnabled()) {
/*      */         
/* 1351 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
/*      */       }
/* 1353 */       else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
/*      */         
/* 1355 */         PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */ 
/*      */         
/*      */         try {
/* 1359 */           int j = packetbuffer.readByte();
/* 1360 */           CommandBlockLogic commandblocklogic = null;
/*      */           
/* 1362 */           if (j == 0) {
/*      */             
/* 1364 */             TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(new BlockPos(packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt()));
/*      */             
/* 1366 */             if (tileentity instanceof TileEntityCommandBlock)
/*      */             {
/* 1368 */               commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*      */             }
/*      */           }
/* 1371 */           else if (j == 1) {
/*      */             
/* 1373 */             Entity entity = this.playerEntity.worldObj.getEntityByID(packetbuffer.readInt());
/*      */             
/* 1375 */             if (entity instanceof EntityMinecartCommandBlock)
/*      */             {
/* 1377 */               commandblocklogic = ((EntityMinecartCommandBlock)entity).getCommandBlockLogic();
/*      */             }
/*      */           } 
/*      */           
/* 1381 */           String s1 = packetbuffer.readStringFromBuffer(packetbuffer.readableBytes());
/* 1382 */           boolean flag = packetbuffer.readBoolean();
/*      */           
/* 1384 */           if (commandblocklogic != null)
/*      */           {
/* 1386 */             commandblocklogic.setCommand(s1);
/* 1387 */             commandblocklogic.setTrackOutput(flag);
/*      */             
/* 1389 */             if (!flag)
/*      */             {
/* 1391 */               commandblocklogic.setLastOutput(null);
/*      */             }
/*      */             
/* 1394 */             commandblocklogic.updateCommand();
/* 1395 */             this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.setCommand.success", new Object[] { s1 }));
/*      */           }
/*      */         
/* 1398 */         } catch (Exception exception1) {
/*      */           
/* 1400 */           logger.error("Couldn't set command block", exception1);
/*      */         }
/*      */         finally {
/*      */           
/* 1404 */           packetbuffer.release();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1409 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
/*      */       }
/*      */     
/* 1412 */     } else if ("MC|Beacon".equals(packetIn.getChannelName())) {
/*      */       
/* 1414 */       if (this.playerEntity.openContainer instanceof ContainerBeacon) {
/*      */         
/*      */         try {
/*      */           
/* 1418 */           PacketBuffer packetbuffer1 = packetIn.getBufferData();
/* 1419 */           int k = packetbuffer1.readInt();
/* 1420 */           int l = packetbuffer1.readInt();
/* 1421 */           ContainerBeacon containerbeacon = (ContainerBeacon)this.playerEntity.openContainer;
/* 1422 */           Slot slot = containerbeacon.getSlot(0);
/*      */           
/* 1424 */           if (slot.getHasStack())
/*      */           {
/* 1426 */             slot.decrStackSize(1);
/* 1427 */             IInventory iinventory = containerbeacon.func_180611_e();
/* 1428 */             iinventory.setField(1, k);
/* 1429 */             iinventory.setField(2, l);
/* 1430 */             iinventory.markDirty();
/*      */           }
/*      */         
/* 1433 */         } catch (Exception exception) {
/*      */           
/* 1435 */           logger.error("Couldn't set beacon", exception);
/*      */         }
/*      */       
/*      */       }
/* 1439 */     } else if ("MC|ItemName".equals(packetIn.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
/*      */       
/* 1441 */       ContainerRepair containerrepair = (ContainerRepair)this.playerEntity.openContainer;
/*      */       
/* 1443 */       if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1) {
/*      */         
/* 1445 */         String s = ChatAllowedCharacters.filterAllowedCharacters(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */         
/* 1447 */         if (s.length() <= 30)
/*      */         {
/* 1449 */           containerrepair.updateItemName(s);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1454 */         containerrepair.updateItemName("");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\network\NetHandlerPlayServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */