/*     */ package cc.slack.features.modules.impl.combat;
/*     */ 
/*     */ import cc.slack.events.impl.player.JumpEvent;
/*     */ import cc.slack.events.impl.player.MotionEvent;
/*     */ import cc.slack.events.impl.player.StrafeEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.events.impl.render.RenderEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.network.PacketUtil;
/*     */ import cc.slack.utils.other.MathUtil;
/*     */ import cc.slack.utils.other.RaycastUtil;
/*     */ import cc.slack.utils.other.TimeUtil;
/*     */ import cc.slack.utils.player.AttackUtil;
/*     */ import cc.slack.utils.player.BlinkUtil;
/*     */ import cc.slack.utils.player.RotationUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.security.SecureRandom;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "KillAura", category = Category.COMBAT, key = 19)
/*     */ public class KillAura
/*     */   extends Module
/*     */ {
/*  47 */   private final NumberValue<Double> aimRange = new NumberValue("Aim Range", Double.valueOf(7.0D), Double.valueOf(3.0D), Double.valueOf(12.0D), Double.valueOf(0.01D));
/*  48 */   private final NumberValue<Double> attackRange = new NumberValue("Attack Range", Double.valueOf(3.0D), Double.valueOf(3.0D), Double.valueOf(7.0D), Double.valueOf(0.01D));
/*     */ 
/*     */   
/*  51 */   private final ModeValue<AttackUtil.AttackPattern> attackPattern = new ModeValue("Pattern", (Object[])AttackUtil.AttackPattern.values());
/*  52 */   private final NumberValue<Integer> cps = new NumberValue("CPS", Integer.valueOf(14), Integer.valueOf(1), Integer.valueOf(30), Integer.valueOf(1));
/*  53 */   private final NumberValue<Double> randomization = new NumberValue("Randomization", Double.valueOf(1.5D), Double.valueOf(0.0D), Double.valueOf(4.0D), Double.valueOf(0.01D));
/*     */ 
/*     */   
/*  56 */   private final ModeValue<String> autoBlock = new ModeValue("Autoblock", (Object[])new String[] { "None", "Fake", "Blatant", "Vanilla", "Basic", "Blink" });
/*  57 */   private final ModeValue<String> blinkMode = new ModeValue("Blink Autoblock Mode", (Object[])new String[] { "Legit", "Legit HVH", "Blatant" });
/*  58 */   private final NumberValue<Double> blockRange = new NumberValue("Block Range", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(7.0D), Double.valueOf(0.01D));
/*  59 */   private final BooleanValue interactAutoblock = new BooleanValue("Interact", false);
/*  60 */   private final BooleanValue renderBlocking = new BooleanValue("Render Blocking", true);
/*     */ 
/*     */   
/*  63 */   private final BooleanValue rotationRand = new BooleanValue("Rotation Randomization", false);
/*  64 */   private final NumberValue<Double> minRotationSpeed = new NumberValue("Min Rotation Speed", Double.valueOf(65.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(5.0D));
/*  65 */   private final NumberValue<Double> maxRotationSpeed = new NumberValue("Max Rotation Speed", Double.valueOf(85.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(5.0D));
/*     */ 
/*     */ 
/*     */   
/*  69 */   private final BooleanValue moveFix = new BooleanValue("Move Fix", false);
/*  70 */   private final BooleanValue keepSprint = new BooleanValue("Keep Sprint", true);
/*  71 */   private final BooleanValue rayCast = new BooleanValue("Ray Cast", true);
/*     */   
/*  73 */   private final ModeValue<String> sortMode = new ModeValue("Sort", (Object[])new String[] { "FOV", "Distance", "Health", "Hurt Ticks" });
/*     */ 
/*     */   
/*  76 */   private final TimeUtil timer = new TimeUtil();
/*  77 */   private final TimeUtil rotationCenter = new TimeUtil();
/*     */   
/*     */   private double rotationOffset;
/*     */   
/*     */   private EntityLivingBase target;
/*     */   private EntityLivingBase rayCastedEntity;
/*     */   private float[] rotations;
/*     */   private long attackDelay;
/*     */   private int queuedAttacks;
/*     */   public boolean isBlocking;
/*     */   private boolean wasBlink;
/*     */   public boolean renderBlock;
/*     */   
/*     */   public KillAura() {
/*  91 */     addSettings(new Value[] { (Value)this.aimRange, (Value)this.attackRange, (Value)this.attackPattern, (Value)this.cps, (Value)this.randomization, (Value)this.autoBlock, (Value)this.blinkMode, (Value)this.blockRange, (Value)this.interactAutoblock, (Value)this.renderBlocking, (Value)this.rotationRand, (Value)this.minRotationSpeed, (Value)this.maxRotationSpeed, (Value)this.moveFix, (Value)this.keepSprint, (Value)this.rayCast, (Value)this.sortMode });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  96 */     this.wasBlink = false;
/*  97 */     this.rotations = new float[] { (mc.getPlayer()).rotationYaw, (mc.getPlayer()).rotationPitch };
/*  98 */     this.attackDelay = AttackUtil.getAttackDelay(((Integer)this.cps.getValue()).intValue(), ((Double)this.randomization.getValue()).doubleValue(), (AttackUtil.AttackPattern)this.attackPattern.getValue());
/*  99 */     this.queuedAttacks = 0;
/* 100 */     this.timer.reset();
/* 101 */     this.rotationCenter.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 106 */     if (this.isBlocking) unblock(); 
/* 107 */     if (this.wasBlink) BlinkUtil.disable(); 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onStrafe(StrafeEvent e) {
/* 112 */     if (this.target != null && ((Boolean)this.moveFix.getValue()).booleanValue()) e.setYaw(this.rotations[0]); 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onJump(JumpEvent e) {
/* 117 */     if (this.target != null && ((Boolean)this.moveFix.getValue()).booleanValue()) e.setYaw(this.rotations[0]); 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onMotion(MotionEvent e) {
/* 122 */     if (this.target == null) {
/* 123 */       this.rotations[0] = (mc.getPlayer()).rotationYaw;
/* 124 */       this.rotations[1] = (mc.getPlayer()).rotationPitch;
/* 125 */       e.setYaw(this.rotations[0]);
/* 126 */       e.setPitch(this.rotations[1]);
/*     */     } else {
/* 128 */       e.setYaw(this.rotations[0]);
/* 129 */       e.setPitch(this.rotations[1]);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onRender(RenderEvent e) {
/* 135 */     if (e.getState() != RenderEvent.State.RENDER_2D)
/* 136 */       return;  if (this.timer.hasReached(this.attackDelay) && this.target != null) {
/* 137 */       this.queuedAttacks++;
/* 138 */       this.timer.reset();
/* 139 */       this.attackDelay = AttackUtil.getAttackDelay(((Integer)this.cps.getValue()).intValue(), ((Double)this.randomization.getValue()).doubleValue(), (AttackUtil.AttackPattern)this.attackPattern.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent e) {
/* 145 */     this.target = AttackUtil.getTarget(((Double)this.aimRange.getValue()).doubleValue(), (String)this.sortMode.getValue());
/*     */     
/* 147 */     if (this.target == null) {
/* 148 */       this.attackDelay = 0L;
/* 149 */       if (this.isBlocking)
/* 150 */         unblock(); 
/* 151 */       if (this.wasBlink) {
/* 152 */         this.wasBlink = false;
/* 153 */         BlinkUtil.disable();
/*     */       } 
/* 155 */       this.renderBlock = false;
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     if (mc.getPlayer().getDistanceToEntity((Entity)this.target) > ((Double)this.aimRange.getValue()).doubleValue())
/* 160 */       return;  this.renderBlock = (canAutoBlock() && (((Boolean)this.renderBlocking.getValue()).booleanValue() || this.isBlocking || ((String)this.autoBlock.getValue()).equals("Fake")) && !((String)this.autoBlock.getValue()).equals("None"));
/*     */     
/* 162 */     this.rotations = calculateRotations((Entity)this.target);
/*     */     
/* 164 */     if ((mc.getPlayer().getDistanceToEntity((Entity)this.target) < ((Double)this.blockRange.getValue()).doubleValue() || this.isBlocking) && 
/* 165 */       preAttack())
/* 166 */       return;  while (this.queuedAttacks > 0) {
/* 167 */       attack(this.target);
/* 168 */       this.queuedAttacks--;
/*     */     } 
/* 170 */     if (canAutoBlock()) postAttack(); 
/*     */   }
/*     */   
/*     */   private void attack(EntityLivingBase target) {
/* 174 */     this.rayCastedEntity = null;
/* 175 */     if (((Boolean)this.rayCast.getValue()).booleanValue()) this.rayCastedEntity = RaycastUtil.rayCast(((Double)this.attackRange.getValue()).doubleValue(), this.rotations);
/*     */     
/* 177 */     mc.getPlayer().swingItem();
/*     */     
/* 179 */     if (mc.getPlayer().getDistanceToEntity((this.rayCastedEntity == null) ? (Entity)target : (Entity)this.rayCastedEntity) > ((Double)this.attackRange.getValue()).doubleValue() + 0.3D) {
/*     */       return;
/*     */     }
/* 182 */     if (((Boolean)this.keepSprint.getValue()).booleanValue()) {
/* 183 */       mc.getPlayerController().syncCurrentPlayItem();
/* 184 */       PacketUtil.send((Packet)new C02PacketUseEntity((this.rayCastedEntity == null) ? (Entity)target : (Entity)this.rayCastedEntity, C02PacketUseEntity.Action.ATTACK));
/* 185 */       if ((mc.getPlayer()).fallDistance > 0.0F && !(mc.getPlayer()).onGround) {
/* 186 */         mc.getPlayer().onCriticalHit((this.rayCastedEntity == null) ? (Entity)target : (Entity)this.rayCastedEntity);
/*     */       }
/*     */     } else {
/* 189 */       mc.getPlayerController().attackEntity((EntityPlayer)mc.getPlayer(), (this.rayCastedEntity == null) ? (Entity)target : (Entity)this.rayCastedEntity);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean preAttack() {
/* 194 */     switch (((String)this.autoBlock.getValue()).toLowerCase()) {
/*     */       case "blatant":
/* 196 */         unblock();
/*     */         break;
/*     */       case "basic":
/* 199 */         switch ((mc.getPlayer()).ticksExisted % 3) {
/*     */           case 0:
/* 201 */             unblock();
/* 202 */             return true;
/*     */           case 1:
/* 204 */             return false;
/*     */           case 2:
/* 206 */             block();
/* 207 */             return true;
/*     */         } 
/*     */         break;
/*     */       case "blink":
/* 211 */         switch (((String)this.blinkMode.getValue()).toLowerCase()) {
/*     */           case "legit":
/* 213 */             switch ((mc.getPlayer()).ticksExisted % 3) {
/*     */               case 0:
/* 215 */                 unblock();
/* 216 */                 return true;
/*     */               case 1:
/* 218 */                 return false;
/*     */               case 2:
/* 220 */                 block();
/* 221 */                 if (!BlinkUtil.isEnabled)
/* 222 */                   BlinkUtil.enable(false, true); 
/* 223 */                 BlinkUtil.setConfig(false, true);
/* 224 */                 BlinkUtil.releasePackets();
/* 225 */                 this.wasBlink = true;
/* 226 */                 return true;
/*     */             } 
/*     */             break;
/*     */           case "legit hvh":
/* 230 */             switch ((mc.getPlayer()).ticksExisted % 5) {
/*     */               case 0:
/* 232 */                 unblock();
/* 233 */                 return true;
/*     */               case 4:
/* 235 */                 block();
/* 236 */                 if (!BlinkUtil.isEnabled)
/* 237 */                   BlinkUtil.enable(false, true); 
/* 238 */                 BlinkUtil.setConfig(false, true);
/* 239 */                 BlinkUtil.releasePackets();
/* 240 */                 this.wasBlink = true;
/* 241 */                 return true;
/*     */             } 
/*     */             break;
/*     */           case "blatant":
/* 245 */             switch ((mc.getPlayer()).ticksExisted % 2) {
/*     */               case 0:
/* 247 */                 unblock();
/* 248 */                 return true;
/*     */               case 1:
/* 250 */                 return false;
/*     */             } 
/*     */             
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */     } 
/* 258 */     return false;
/*     */   }
/*     */   
/*     */   private void postAttack() {
/* 262 */     switch (((String)this.autoBlock.getValue()).toLowerCase()) {
/*     */       case "blatant":
/* 264 */         block();
/*     */         break;
/*     */       case "vanilla":
/* 267 */         block();
/*     */         break;
/*     */       case "blink":
/* 270 */         if (((String)this.blinkMode.getValue()).equals("Blatant")) {
/* 271 */           block();
/* 272 */           if (!BlinkUtil.isEnabled)
/* 273 */             BlinkUtil.enable(false, true); 
/* 274 */           BlinkUtil.setConfig(false, true);
/* 275 */           BlinkUtil.releasePackets();
/* 276 */           this.wasBlink = true;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] calculateRotations(Entity entity) {
/* 285 */     AxisAlignedBB bb = entity.getEntityBoundingBox();
/*     */     
/* 287 */     if (this.rotationCenter.hasReached(1200) && ((Boolean)this.rotationRand.getValue()).booleanValue()) {
/* 288 */       this.rotationOffset = (new SecureRandom()).nextDouble();
/* 289 */       this.rotationCenter.reset();
/*     */     } 
/*     */     
/* 292 */     double distancedYaw = (entity.getDistanceToEntity((Entity)mc.getPlayer()) > ((Double)this.attackRange.getValue()).doubleValue()) ? entity.getEyeHeight() : (2.0D * entity.getDistanceToEntity((Entity)mc.getPlayer()) / 3.5D);
/* 293 */     float[] newRots = RotationUtil.getRotations(bb.minX + (bb.maxX - bb.minX) / 2.0D + (
/* 294 */         ((Boolean)this.rotationRand.getValue()).booleanValue() ? (this.rotationOffset / 2.0D) : 0.0D), bb.minY + distancedYaw, bb.minZ + (bb.maxZ - bb.minZ) / 2.0D + (
/*     */         
/* 296 */         ((Boolean)this.rotationRand.getValue()).booleanValue() ? (this.rotationOffset / 2.0D) : 0.0D));
/*     */     
/* 298 */     float pitchSpeed = (float)((mc.getGameSettings()).mouseSensitivity * MathUtil.getRandomInRange(((Double)this.minRotationSpeed.getValue()).doubleValue(), ((Double)this.maxRotationSpeed.getValue()).doubleValue()));
/* 299 */     float yawSpeed = (float)((mc.getGameSettings()).mouseSensitivity * MathUtil.getRandomInRange(((Double)this.minRotationSpeed.getValue()).doubleValue(), ((Double)this.maxRotationSpeed.getValue()).doubleValue()));
/*     */     
/* 301 */     newRots[0] = RotationUtil.updateRots(this.rotations[0], (float)MathUtil.getRandomInRange(newRots[0] - 2.19782323D, newRots[0] + 2.8972343D), pitchSpeed);
/* 302 */     newRots[1] = RotationUtil.updateRots(this.rotations[1], (float)MathUtil.getRandomInRange(newRots[1] - 3.13672842D, newRots[1] + 3.8716793D), yawSpeed);
/*     */     
/* 304 */     newRots[1] = MathHelper.clamp_float(newRots[1], -90.0F, 90.0F);
/*     */     
/* 306 */     return RotationUtil.applyGCD(newRots, this.rotations);
/*     */   }
/*     */   
/*     */   private void block() {
/* 310 */     block(((Boolean)this.interactAutoblock.getValue()).booleanValue());
/*     */   }
/*     */   
/*     */   private void block(boolean interact) {
/* 314 */     if (this.isBlocking)
/* 315 */       return;  EntityLivingBase targetEntity = (this.rayCastedEntity == null) ? this.target : this.rayCastedEntity;
/* 316 */     if (interact) {
/* 317 */       PacketUtil.send((Packet)new C02PacketUseEntity((Entity)targetEntity, C02PacketUseEntity.Action.INTERACT));
/*     */     }
/*     */     
/* 320 */     PacketUtil.send((Packet)new C08PacketPlayerBlockPlacement(mc.getPlayer().getCurrentEquippedItem()));
/* 321 */     this.isBlocking = true;
/*     */   }
/*     */   
/*     */   private void unblock() {
/* 325 */     if (!(mc.getGameSettings()).keyBindUseItem.isKeyDown())
/* 326 */     { PacketUtil.send((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)); }
/*     */     
/* 328 */     else if (canAutoBlock()) { (mc.getGameSettings()).keyBindUseItem.setPressed(false); }
/* 329 */      this.isBlocking = false;
/*     */   }
/*     */   
/*     */   private boolean canAutoBlock() {
/* 333 */     return (this.target != null && mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().getItem() instanceof net.minecraft.item.ItemSword && mc.getPlayer().getDistanceToEntity((Entity)this.target) < ((Double)this.blockRange.getValue()).doubleValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\combat\KillAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */