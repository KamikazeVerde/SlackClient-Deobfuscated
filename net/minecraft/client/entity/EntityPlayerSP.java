/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.events.State;
/*     */ import cc.slack.events.impl.game.ChatEvent;
/*     */ import cc.slack.events.impl.player.MotionEvent;
/*     */ import cc.slack.events.impl.player.MoveEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.features.modules.impl.combat.KillAura;
/*     */ import cc.slack.features.modules.impl.movement.NoSlow;
/*     */ import cc.slack.utils.player.AttackUtil;
/*     */ import cc.slack.utils.player.MovementUtil;
/*     */ import cc.slack.utils.player.RotationUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiCommandBlock;
/*     */ import net.minecraft.client.gui.GuiEnchantment;
/*     */ import net.minecraft.client.gui.GuiHopper;
/*     */ import net.minecraft.client.gui.GuiMerchant;
/*     */ import net.minecraft.client.gui.GuiRepair;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenBook;
/*     */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*     */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*     */ import net.minecraft.client.gui.inventory.GuiChest;
/*     */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*     */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*     */ import net.minecraft.client.gui.inventory.GuiEditSign;
/*     */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*     */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketDirection;
/*     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
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
/*     */ public class EntityPlayerSP
/*     */   extends AbstractClientPlayer
/*     */ {
/*     */   public final NetHandlerPlayClient sendQueue;
/*     */   private final StatFileWriter statWriter;
/*     */   private double lastReportedPosX;
/*     */   private double lastReportedPosY;
/*     */   private double lastReportedPosZ;
/*     */   private float lastReportedYaw;
/*     */   private float lastReportedPitch;
/*     */   private boolean serverSneakState;
/*     */   private boolean serverSprintState;
/*     */   private int positionUpdateTicks;
/*     */   private boolean hasValidHealth;
/*     */   private String clientBrand;
/*     */   public MovementInput movementInput;
/*     */   protected Minecraft mc;
/*     */   protected int sprintToggleTimer;
/*     */   public int sprintingTicksLeft;
/*     */   public float renderArmYaw;
/*     */   public float renderArmPitch;
/*     */   public float prevRenderArmYaw;
/*     */   public float prevRenderArmPitch;
/*     */   private int horseJumpPowerCounter;
/*     */   private float horseJumpPower;
/*     */   public float timeInPortal;
/*     */   public float prevTimeInPortal;
/*     */   
/*     */   public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
/* 146 */     super(worldIn, netHandler.getGameProfile());
/* 147 */     this.sendQueue = netHandler;
/* 148 */     this.statWriter = statFile;
/* 149 */     this.mc = mcIn;
/* 150 */     this.dimension = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void heal(float healAmount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mountEntity(Entity entityIn) {
/* 173 */     super.mountEntity(entityIn);
/*     */     
/* 175 */     if (entityIn instanceof EntityMinecart)
/*     */     {
/* 177 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 186 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
/*     */       
/* 188 */       (new UpdateEvent()).call();
/* 189 */       super.onUpdate();
/*     */       
/* 191 */       if (AttackUtil.combatTimer.hasReached(600L)) {
/* 192 */         AttackUtil.inCombat = false;
/* 193 */         AttackUtil.combatTarget = null;
/*     */       } 
/*     */       
/* 196 */       if (isRiding()) {
/*     */         
/* 198 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/* 199 */         this.sendQueue.addToSendQueue((Packet)new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
/*     */       }
/*     */       else {
/*     */         
/* 203 */         onUpdateWalkingPlayer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateWalkingPlayer() {
/* 213 */     float realYaw = this.rotationYaw;
/* 214 */     float realPitch = this.rotationPitch;
/* 215 */     if (RotationUtil.isEnabled) {
/*     */       
/* 217 */       this.rotationYaw = RotationUtil.clientRotation[0];
/* 218 */       this.rotationPitch = RotationUtil.clientRotation[1];
/* 219 */       this.rotationYaw += (new Random()).nextFloat() * RotationUtil.randomizeAmount;
/* 220 */       this.rotationPitch += (new Random()).nextFloat() * RotationUtil.randomizeAmount;
/*     */       
/* 222 */       float[] gcdFix = RotationUtil.applyGCD(new float[] { this.rotationYaw, this.rotationPitch }, new float[] { this.lastReportedYaw, this.lastReportedPitch });
/* 223 */       this.rotationYaw = gcdFix[0];
/* 224 */       this.rotationPitch = gcdFix[1];
/*     */       
/* 226 */       if (RotationUtil.keepRotationTicks <= 0.0F) {
/* 227 */         RotationUtil.isEnabled = false;
/* 228 */         if (RotationUtil.strafeFix) {
/* 229 */           MovementUtil.updateBinds();
/* 230 */           RotationUtil.strafeFix = false;
/*     */         } 
/*     */       } else {
/* 233 */         RotationUtil.keepRotationTicks--;
/*     */       } 
/*     */     } else {
/* 236 */       RotationUtil.clientRotation[0] = this.rotationYaw;
/* 237 */       RotationUtil.clientRotation[1] = this.rotationPitch;
/* 238 */       RotationUtil.keepRotationTicks = 0.0F;
/* 239 */       RotationUtil.randomizeAmount = 0.0F;
/*     */     } 
/* 241 */     MotionEvent event = new MotionEvent(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.lastReportedYaw, this.lastReportedPitch, this.onGround, this.mc.thePlayer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     event.call();
/* 247 */     this.rotationYaw = realYaw;
/* 248 */     this.rotationPitch = realPitch;
/*     */     
/* 250 */     boolean flag = isSprinting();
/*     */     
/* 252 */     if (flag != this.serverSprintState) {
/*     */       
/* 254 */       if (flag) {
/*     */         
/* 256 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SPRINTING));
/*     */       }
/*     */       else {
/*     */         
/* 260 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
/*     */       } 
/*     */       
/* 263 */       this.serverSprintState = flag;
/*     */     } 
/*     */     
/* 266 */     boolean flag1 = isSneaking();
/*     */     
/* 268 */     if (flag1 != this.serverSneakState) {
/*     */       
/* 270 */       if (flag1) {
/*     */         
/* 272 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SNEAKING));
/*     */       }
/*     */       else {
/*     */         
/* 276 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*     */       } 
/*     */       
/* 279 */       this.serverSneakState = flag1;
/*     */     } 
/*     */     
/* 282 */     if (event.isCanceled())
/*     */       return; 
/* 284 */     if (isCurrentViewEntity()) {
/*     */       
/* 286 */       double d0 = event.getX() - this.lastReportedPosX;
/* 287 */       double d1 = event.getY() - this.lastReportedPosY;
/* 288 */       double d2 = event.getZ() - this.lastReportedPosZ;
/* 289 */       double d3 = (event.getYaw() - this.lastReportedYaw);
/* 290 */       double d4 = (event.getPitch() - this.lastReportedPitch);
/* 291 */       boolean flag2 = (d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20);
/* 292 */       boolean flag3 = (d3 != 0.0D || d4 != 0.0D);
/*     */       
/* 294 */       if (this.ridingEntity == null) {
/*     */         
/* 296 */         if (flag2 && flag3)
/*     */         {
/* 298 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch(), event.isGround()));
/*     */         }
/* 300 */         else if (flag2)
/*     */         {
/* 302 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), event.isGround()));
/*     */         }
/* 304 */         else if (flag3)
/*     */         {
/* 306 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(event.getYaw(), event.getPitch(), event.isGround()));
/*     */         }
/*     */         else
/*     */         {
/* 310 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(event.isGround()));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 315 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 316 */         flag2 = false;
/*     */       } 
/*     */       
/* 319 */       this.positionUpdateTicks++;
/*     */       
/* 321 */       if (flag2) {
/*     */         
/* 323 */         this.lastReportedPosX = event.getX();
/* 324 */         this.lastReportedPosY = event.getY();
/* 325 */         this.lastReportedPosZ = event.getZ();
/* 326 */         this.positionUpdateTicks = 0;
/*     */       } 
/*     */       
/* 329 */       if (flag3) {
/*     */         
/* 331 */         this.lastReportedYaw = event.getYaw();
/* 332 */         this.lastReportedPitch = event.getPitch();
/*     */       } 
/*     */       
/* 335 */       if (event.getPitch() != this.mc.thePlayer.rotationPitch) {
/* 336 */         this.renderPitch = event.getPitch();
/*     */       }
/* 338 */       if (event.getYaw() != this.mc.thePlayer.rotationYaw) {
/* 339 */         this.renderYawOffset = event.getYaw();
/* 340 */         this.rotationYawHead = event.getYaw();
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     event.setState(State.POST);
/* 345 */     event.call();
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 350 */     MoveEvent moveEvent = new MoveEvent(x, y, z, false);
/* 351 */     moveEvent.call();
/* 352 */     moveEntity(moveEvent.getX(), moveEvent.getY(), moveEvent.getZ(), moveEvent.safewalk);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItem dropOneItem(boolean dropAll) {
/* 360 */     C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
/* 361 */     this.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 362 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void joinEntityItemWithWorld(EntityItem itemIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String message) {
/* 379 */     ChatEvent chatEvent = new ChatEvent(message, PacketDirection.OUTGOING);
/* 380 */     if (chatEvent.call().isCanceled())
/* 381 */       return;  this.sendQueue.addToSendQueue((Packet)new C01PacketChatMessage(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swingItem() {
/* 389 */     super.swingItem();
/* 390 */     this.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
/*     */   }
/*     */ 
/*     */   
/*     */   public void respawnPlayer() {
/* 395 */     this.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 404 */     if (!isEntityInvulnerable(damageSrc))
/*     */     {
/* 406 */       setHealth(getHealth() - damageAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeScreen() {
/* 415 */     this.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(this.openContainer.windowId));
/* 416 */     closeScreenAndDropStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeScreenAndDropStack() {
/* 421 */     this.inventory.setItemStack(null);
/* 422 */     super.closeScreen();
/* 423 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerSPHealth(float health) {
/* 431 */     if (this.hasValidHealth) {
/*     */       
/* 433 */       float f = getHealth() - health;
/*     */       
/* 435 */       if (f <= 0.0F)
/*     */       {
/* 437 */         setHealth(health);
/*     */         
/* 439 */         if (f < 0.0F)
/*     */         {
/* 441 */           this.hurtResistantTime = this.maxHurtResistantTime / 2;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 446 */         this.lastDamage = f;
/* 447 */         setHealth(getHealth());
/* 448 */         this.hurtResistantTime = this.maxHurtResistantTime;
/* 449 */         damageEntity(DamageSource.generic, f);
/* 450 */         this.hurtTime = this.maxHurtTime = 10;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 455 */       setHealth(health);
/* 456 */       this.hasValidHealth = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStat(StatBase stat, int amount) {
/* 465 */     if (stat != null)
/*     */     {
/* 467 */       if (stat.isIndependent)
/*     */       {
/* 469 */         super.addStat(stat, amount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerAbilities() {
/* 479 */     this.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(this.capabilities));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUser() {
/* 487 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendHorseJump() {
/* 492 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendHorseInventory() {
/* 497 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientBrand(String brand) {
/* 502 */     this.clientBrand = brand;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClientBrand() {
/* 507 */     return this.clientBrand;
/*     */   }
/*     */ 
/*     */   
/*     */   public StatFileWriter getStatFileWriter() {
/* 512 */     return this.statWriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChatComponentMessage(IChatComponent chatComponent) {
/* 517 */     this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 522 */     if (!this.noClip) {
/* 523 */       BlockPos blockpos = new BlockPos(x, y, z);
/* 524 */       double d0 = x - blockpos.getX();
/* 525 */       double d1 = z - blockpos.getZ();
/*     */       
/* 527 */       if (!isOpenBlockSpace(blockpos)) {
/* 528 */         int i = -1;
/* 529 */         double d2 = 9999.0D;
/*     */         
/* 531 */         if (isOpenBlockSpace(blockpos.west()) && d0 < d2) {
/* 532 */           d2 = d0;
/* 533 */           i = 0;
/*     */         } 
/*     */         
/* 536 */         if (isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2) {
/* 537 */           d2 = 1.0D - d0;
/* 538 */           i = 1;
/*     */         } 
/*     */         
/* 541 */         if (isOpenBlockSpace(blockpos.north()) && d1 < d2) {
/* 542 */           d2 = d1;
/* 543 */           i = 4;
/*     */         } 
/*     */         
/* 546 */         if (isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2) {
/* 547 */           d2 = 1.0D - d1;
/* 548 */           i = 5;
/*     */         } 
/*     */         
/* 551 */         float f = 0.1F;
/*     */         
/* 553 */         if (i == 0) {
/* 554 */           this.motionX = -f;
/*     */         }
/*     */         
/* 557 */         if (i == 1) {
/* 558 */           this.motionX = f;
/*     */         }
/*     */         
/* 561 */         if (i == 4) {
/* 562 */           this.motionZ = -f;
/*     */         }
/*     */         
/* 565 */         if (i == 5) {
/* 566 */           this.motionZ = f;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 571 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOpenBlockSpace(BlockPos pos) {
/* 579 */     return (!this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSprinting(boolean sprinting) {
/* 587 */     super.setSprinting(sprinting);
/* 588 */     this.sprintingTicksLeft = sprinting ? 600 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPStats(float currentXP, int maxXP, int level) {
/* 596 */     this.experience = currentXP;
/* 597 */     this.experienceTotal = maxXP;
/* 598 */     this.experienceLevel = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 608 */     this.mc.ingameGUI.getChatGUI().printChatMessage(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 619 */     return (permLevel <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 628 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(String name, float volume, float pitch) {
/* 633 */     this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerWorld() {
/* 641 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 646 */     return (this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHorseJumpPower() {
/* 651 */     return this.horseJumpPower;
/*     */   }
/*     */ 
/*     */   
/*     */   public void openEditSign(TileEntitySign signTile) {
/* 656 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditSign(signTile));
/*     */   }
/*     */ 
/*     */   
/*     */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {
/* 661 */     this.mc.displayGuiScreen((GuiScreen)new GuiCommandBlock(cmdBlockLogic));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayGUIBook(ItemStack bookStack) {
/* 669 */     Item item = bookStack.getItem();
/*     */     
/* 671 */     if (item == Items.writable_book)
/*     */     {
/* 673 */       this.mc.displayGuiScreen((GuiScreen)new GuiScreenBook(this, bookStack, true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayGUIChest(IInventory chestInventory) {
/* 682 */     String s = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
/*     */     
/* 684 */     if ("minecraft:chest".equals(s)) {
/*     */       
/* 686 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*     */     }
/* 688 */     else if ("minecraft:hopper".equals(s)) {
/*     */       
/* 690 */       this.mc.displayGuiScreen((GuiScreen)new GuiHopper(this.inventory, chestInventory));
/*     */     }
/* 692 */     else if ("minecraft:furnace".equals(s)) {
/*     */       
/* 694 */       this.mc.displayGuiScreen((GuiScreen)new GuiFurnace(this.inventory, chestInventory));
/*     */     }
/* 696 */     else if ("minecraft:brewing_stand".equals(s)) {
/*     */       
/* 698 */       this.mc.displayGuiScreen((GuiScreen)new GuiBrewingStand(this.inventory, chestInventory));
/*     */     }
/* 700 */     else if ("minecraft:beacon".equals(s)) {
/*     */       
/* 702 */       this.mc.displayGuiScreen((GuiScreen)new GuiBeacon(this.inventory, chestInventory));
/*     */     }
/* 704 */     else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
/*     */       
/* 706 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*     */     }
/*     */     else {
/*     */       
/* 710 */       this.mc.displayGuiScreen((GuiScreen)new GuiDispenser(this.inventory, chestInventory));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/* 716 */     this.mc.displayGuiScreen((GuiScreen)new GuiScreenHorseInventory((IInventory)this.inventory, horseInventory, horse));
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayGui(IInteractionObject guiOwner) {
/* 721 */     String s = guiOwner.getGuiID();
/*     */     
/* 723 */     if ("minecraft:crafting_table".equals(s)) {
/*     */       
/* 725 */       this.mc.displayGuiScreen((GuiScreen)new GuiCrafting(this.inventory, this.worldObj));
/*     */     }
/* 727 */     else if ("minecraft:enchanting_table".equals(s)) {
/*     */       
/* 729 */       this.mc.displayGuiScreen((GuiScreen)new GuiEnchantment(this.inventory, this.worldObj, (IWorldNameable)guiOwner));
/*     */     }
/* 731 */     else if ("minecraft:anvil".equals(s)) {
/*     */       
/* 733 */       this.mc.displayGuiScreen((GuiScreen)new GuiRepair(this.inventory, this.worldObj));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayVillagerTradeGui(IMerchant villager) {
/* 739 */     this.mc.displayGuiScreen((GuiScreen)new GuiMerchant(this.inventory, villager, this.worldObj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCriticalHit(Entity entityHit) {
/* 747 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnchantmentCritical(Entity entityHit) {
/* 752 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSneaking() {
/* 760 */     boolean flag = (this.movementInput != null) ? this.movementInput.sneak : false;
/* 761 */     return (flag && !this.sleeping);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntityActionState() {
/* 766 */     super.updateEntityActionState();
/*     */     
/* 768 */     if (isCurrentViewEntity()) {
/*     */       
/* 770 */       this.moveStrafing = this.movementInput.moveStrafe;
/* 771 */       this.moveForward = this.movementInput.moveForward;
/* 772 */       this.isJumping = this.movementInput.jump;
/* 773 */       this.prevRenderArmYaw = this.renderArmYaw;
/* 774 */       this.prevRenderArmPitch = this.renderArmPitch;
/* 775 */       this.renderArmPitch = (float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D);
/* 776 */       this.renderArmYaw = (float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCurrentViewEntity() {
/* 782 */     return (this.mc.getRenderViewEntity() == this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 791 */     this.renderPitch = this.rotationPitch;
/*     */     
/* 793 */     if (this.sprintingTicksLeft > 0) {
/*     */       
/* 795 */       this.sprintingTicksLeft--;
/*     */       
/* 797 */       if (this.sprintingTicksLeft == 0)
/*     */       {
/* 799 */         setSprinting(false);
/*     */       }
/*     */     } 
/*     */     
/* 803 */     if (this.sprintToggleTimer > 0)
/*     */     {
/* 805 */       this.sprintToggleTimer--;
/*     */     }
/*     */     
/* 808 */     this.prevTimeInPortal = this.timeInPortal;
/*     */     
/* 810 */     if (this.inPortal) {
/*     */       
/* 812 */       if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame())
/*     */       {
/* 814 */         this.mc.displayGuiScreen(null);
/*     */       }
/*     */       
/* 817 */       if (this.timeInPortal == 0.0F)
/*     */       {
/* 819 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       
/* 822 */       this.timeInPortal += 0.0125F;
/*     */       
/* 824 */       if (this.timeInPortal >= 1.0F)
/*     */       {
/* 826 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */       
/* 829 */       this.inPortal = false;
/*     */     }
/* 831 */     else if (isPotionActive(Potion.confusion) && getActivePotionEffect(Potion.confusion).getDuration() > 60) {
/*     */       
/* 833 */       this.timeInPortal += 0.006666667F;
/*     */       
/* 835 */       if (this.timeInPortal > 1.0F)
/*     */       {
/* 837 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 842 */       if (this.timeInPortal > 0.0F)
/*     */       {
/* 844 */         this.timeInPortal -= 0.05F;
/*     */       }
/*     */       
/* 847 */       if (this.timeInPortal < 0.0F)
/*     */       {
/* 849 */         this.timeInPortal = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 853 */     if (this.timeUntilPortal > 0)
/*     */     {
/* 855 */       this.timeUntilPortal--;
/*     */     }
/*     */     
/* 858 */     boolean flag = this.movementInput.jump;
/* 859 */     boolean flag1 = this.movementInput.sneak;
/* 860 */     float f = 0.8F;
/* 861 */     boolean flag2 = (this.movementInput.moveForward >= f);
/* 862 */     this.movementInput.updatePlayerMoveState();
/* 863 */     boolean usingItem = (isUsingItem() || (((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isToggle() && ((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isBlocking));
/*     */     
/* 865 */     if (usingItem && !isRiding()) {
/*     */ 
/*     */       
/* 868 */       if (((NoSlow)Slack.getInstance().getModuleManager().getInstance(NoSlow.class)).isToggle()) {
/* 869 */         this.movementInput.moveStrafe *= ((Float)((NoSlow)Slack.getInstance().getModuleManager().getInstance(NoSlow.class)).strafeMultiplier.getValue()).floatValue();
/* 870 */         this.movementInput.moveForward *= ((Float)((NoSlow)Slack.getInstance().getModuleManager().getInstance(NoSlow.class)).forwardMultiplier.getValue()).floatValue();
/* 871 */         setSprinting(true);
/*     */       } else {
/* 873 */         this.movementInput.moveStrafe *= 0.2F;
/* 874 */         this.movementInput.moveForward *= 0.2F;
/* 875 */         setSprinting(false);
/*     */       } 
/* 877 */       this.sprintToggleTimer = 0;
/*     */     } 
/*     */     
/* 880 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 881 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 882 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 883 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 884 */     boolean flag3 = (getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying);
/*     */     
/* 886 */     if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !isSprinting() && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness))
/*     */     {
/* 888 */       if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
/*     */         
/* 890 */         this.sprintToggleTimer = 7;
/*     */       }
/*     */       else {
/*     */         
/* 894 */         setSprinting(true);
/*     */       } 
/*     */     }
/*     */     
/* 898 */     if (!isSprinting() && this.movementInput.moveForward >= f && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown())
/*     */     {
/* 900 */       setSprinting(true);
/*     */     }
/*     */     
/* 903 */     if (isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3))
/*     */     {
/* 905 */       setSprinting(false);
/*     */     }
/*     */     
/* 908 */     if (this.capabilities.allowFlying)
/*     */     {
/* 910 */       if (this.mc.playerController.isSpectatorMode()) {
/*     */         
/* 912 */         if (!this.capabilities.isFlying)
/*     */         {
/* 914 */           this.capabilities.isFlying = true;
/* 915 */           sendPlayerAbilities();
/*     */         }
/*     */       
/* 918 */       } else if (!flag && this.movementInput.jump) {
/*     */         
/* 920 */         if (this.flyToggleTimer == 0) {
/*     */           
/* 922 */           this.flyToggleTimer = 7;
/*     */         }
/*     */         else {
/*     */           
/* 926 */           this.capabilities.isFlying = !this.capabilities.isFlying;
/* 927 */           sendPlayerAbilities();
/* 928 */           this.flyToggleTimer = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 933 */     if (this.capabilities.isFlying && isCurrentViewEntity()) {
/*     */       
/* 935 */       if (this.movementInput.sneak)
/*     */       {
/* 937 */         this.motionY -= (this.capabilities.getFlySpeed() * 3.0F);
/*     */       }
/*     */       
/* 940 */       if (this.movementInput.jump)
/*     */       {
/* 942 */         this.motionY += (this.capabilities.getFlySpeed() * 3.0F);
/*     */       }
/*     */     } 
/*     */     
/* 946 */     if (isRidingHorse()) {
/*     */       
/* 948 */       if (this.horseJumpPowerCounter < 0) {
/*     */         
/* 950 */         this.horseJumpPowerCounter++;
/*     */         
/* 952 */         if (this.horseJumpPowerCounter == 0)
/*     */         {
/* 954 */           this.horseJumpPower = 0.0F;
/*     */         }
/*     */       } 
/*     */       
/* 958 */       if (flag && !this.movementInput.jump) {
/*     */         
/* 960 */         this.horseJumpPowerCounter = -10;
/* 961 */         sendHorseJump();
/*     */       }
/* 963 */       else if (!flag && this.movementInput.jump) {
/*     */         
/* 965 */         this.horseJumpPowerCounter = 0;
/* 966 */         this.horseJumpPower = 0.0F;
/*     */       }
/* 968 */       else if (flag) {
/*     */         
/* 970 */         this.horseJumpPowerCounter++;
/*     */         
/* 972 */         if (this.horseJumpPowerCounter < 10)
/*     */         {
/* 974 */           this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
/*     */         }
/*     */         else
/*     */         {
/* 978 */           this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 984 */       this.horseJumpPower = 0.0F;
/*     */     } 
/*     */     
/* 987 */     super.onLivingUpdate();
/*     */     
/* 989 */     if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
/*     */       
/* 991 */       this.capabilities.isFlying = false;
/* 992 */       sendPlayerAbilities();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\entity\EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */