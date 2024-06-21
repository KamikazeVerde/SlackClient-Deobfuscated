/*     */ package cc.slack.utils.player;
/*     */ 
/*     */ import cc.slack.events.impl.player.MoveEvent;
/*     */ import cc.slack.utils.client.mc;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.potion.Potion;
/*     */ 
/*     */ public class MovementUtil
/*     */   extends mc {
/*     */   public static void setSpeed(MoveEvent event, double speed) {
/*  11 */     setBaseSpeed(event, speed, (getPlayer()).rotationYaw, (getPlayer()).moveForward, (getPlayer()).moveStrafing);
/*     */   }
/*     */   
/*     */   public static void setSpeed(MoveEvent event, double speed, float yawDegrees) {
/*  15 */     setBaseSpeed(event, speed, yawDegrees, (getPlayer()).moveForward, (getPlayer()).moveStrafing);
/*     */   }
/*     */   
/*     */   public static void minLimitStrafe(float speed) {
/*  19 */     strafe();
/*  20 */     if (getSpeed() < speed) {
/*  21 */       strafe(speed);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void strafe() {
/*  26 */     strafe((float)getSpeed());
/*     */   }
/*     */   
/*     */   public static void strafe(float speed) {
/*  30 */     float yaw = getDirection();
/*     */     
/*  32 */     (getPlayer()).motionX = Math.cos(Math.toRadians((yaw + 90.0F))) * speed;
/*  33 */     (getPlayer()).motionZ = Math.cos(Math.toRadians(yaw)) * speed;
/*     */   }
/*     */   
/*     */   private static void setBaseSpeed(MoveEvent event, double speed, float yaw, double forward, double strafing) {
/*  37 */     if (getPlayer() != null && getWorld() != null) {
/*  38 */       boolean reversed = (forward < 0.0D);
/*  39 */       float strafingYaw = 90.0F * ((forward > 0.0D) ? 0.5F : (reversed ? -0.5F : 1.0F));
/*     */       
/*  41 */       if (reversed)
/*  42 */         yaw += 180.0F; 
/*  43 */       if (strafing > 0.0D) {
/*  44 */         yaw -= strafingYaw;
/*  45 */       } else if (strafing < 0.0D) {
/*  46 */         yaw += strafingYaw;
/*     */       } 
/*  48 */       double finalX = Math.cos(Math.toRadians((yaw + 90.0F))) * speed;
/*  49 */       double finalZ = Math.cos(Math.toRadians(yaw)) * speed;
/*     */       
/*  51 */       if (event != null) {
/*  52 */         if (isMoving()) {
/*  53 */           event.setX(finalX);
/*  54 */           event.setZ(finalZ);
/*     */         } else {
/*  56 */           event.setZeroXZ();
/*     */         } 
/*  58 */       } else if (isMoving()) {
/*  59 */         (getPlayer()).motionX = finalX;
/*  60 */         (getPlayer()).motionZ = finalZ;
/*     */       } else {
/*  62 */         resetMotion(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isMoving() {
/*  68 */     return (getPlayer() != null && ((getPlayer()).moveForward != 0.0F || (getPlayer()).moveStrafing != 0.0F));
/*     */   }
/*     */   
/*     */   public static boolean isBindsMoving() {
/*  72 */     return (getPlayer() != null && (
/*  73 */       GameSettings.isKeyDown((mc.getGameSettings()).keyBindForward) || 
/*  74 */       GameSettings.isKeyDown((mc.getGameSettings()).keyBindRight) || 
/*  75 */       GameSettings.isKeyDown((mc.getGameSettings()).keyBindBack) || 
/*  76 */       GameSettings.isKeyDown((mc.getGameSettings()).keyBindLeft)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getDirection() {
/*  81 */     if (RotationUtil.isEnabled && RotationUtil.strafeFix) return getBindsDirection((getPlayer()).rotationYaw); 
/*  82 */     return getDirection((getPlayer()).rotationYaw, (getPlayer()).moveForward, (getPlayer()).moveStrafing);
/*     */   }
/*     */   
/*     */   public static float getDirection(float rotationYaw, float moveForward, float moveStrafing) {
/*  86 */     if (moveForward == 0.0F && moveStrafing == 0.0F) return rotationYaw;
/*     */     
/*  88 */     boolean reversed = (moveForward < 0.0F);
/*  89 */     double strafingYaw = 90.0D * ((moveForward > 0.0F) ? 0.5D : (reversed ? -0.5D : 1.0D));
/*     */     
/*  91 */     if (reversed)
/*  92 */       rotationYaw += 180.0F; 
/*  93 */     if (moveStrafing > 0.0F) {
/*  94 */       rotationYaw = (float)(rotationYaw - strafingYaw);
/*  95 */     } else if (moveStrafing < 0.0F) {
/*  96 */       rotationYaw = (float)(rotationYaw + strafingYaw);
/*     */     } 
/*  98 */     return rotationYaw;
/*     */   }
/*     */   
/*     */   public static float getBindsDirection(float rotationYaw) {
/* 102 */     int moveForward = 0;
/* 103 */     if (GameSettings.isKeyDown((mc.getGameSettings()).keyBindForward)) moveForward++; 
/* 104 */     if (GameSettings.isKeyDown((mc.getGameSettings()).keyBindBack)) moveForward--;
/*     */     
/* 106 */     int moveStrafing = 0;
/* 107 */     if (GameSettings.isKeyDown((mc.getGameSettings()).keyBindRight)) moveStrafing++; 
/* 108 */     if (GameSettings.isKeyDown((mc.getGameSettings()).keyBindLeft)) moveStrafing--;
/*     */     
/* 110 */     boolean reversed = (moveForward < 0);
/* 111 */     double strafingYaw = 90.0D * ((moveForward > 0) ? 0.5D : (reversed ? -0.5D : 1.0D));
/*     */     
/* 113 */     if (reversed)
/* 114 */       rotationYaw += 180.0F; 
/* 115 */     if (moveStrafing > 0) {
/* 116 */       rotationYaw = (float)(rotationYaw + strafingYaw);
/* 117 */     } else if (moveStrafing < 0) {
/* 118 */       rotationYaw = (float)(rotationYaw - strafingYaw);
/*     */     } 
/* 120 */     return rotationYaw;
/*     */   }
/*     */   
/*     */   public static void resetMotion() {
/* 124 */     (getPlayer()).motionX = (getPlayer()).motionZ = 0.0D;
/*     */   }
/*     */   
/*     */   public static void resetMotion(boolean stopY) {
/* 128 */     (getPlayer()).motionX = (getPlayer()).motionZ = 0.0D;
/* 129 */     if (stopY) (getPlayer()).motionY = 0.0D; 
/*     */   }
/*     */   
/*     */   public static double getSpeed() {
/* 133 */     return Math.hypot((getPlayer()).motionX, (getPlayer()).motionZ);
/*     */   }
/*     */   
/*     */   public static double getSpeed(MoveEvent event) {
/* 137 */     return Math.hypot(event.getX(), event.getZ());
/*     */   }
/*     */   
/*     */   public static double getBaseMoveSpeed() {
/* 141 */     double baseSpeed = 0.2873D;
/*     */     
/* 143 */     if (getPlayer().isPotionActive(Potion.moveSpeed)) {
/* 144 */       double amplifier = getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
/* 145 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1.0D);
/*     */     } 
/*     */     
/* 148 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   public static void updateBinds() {
/* 152 */     (mc.getGameSettings()).keyBindJump.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindJump);
/* 153 */     (mc.getGameSettings()).keyBindSprint.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindSprint);
/* 154 */     (mc.getGameSettings()).keyBindForward.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindForward);
/* 155 */     (mc.getGameSettings()).keyBindRight.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindRight);
/* 156 */     (mc.getGameSettings()).keyBindBack.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindBack);
/* 157 */     (mc.getGameSettings()).keyBindLeft.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindLeft);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\MovementUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */