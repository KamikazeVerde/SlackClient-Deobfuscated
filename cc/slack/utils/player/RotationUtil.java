/*     */ package cc.slack.utils.player;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.MathUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RotationUtil extends mc {
/*     */   public enum TargetRotation {
/*  13 */     EDGE, CENTER, OPTIMAL, MIDDLE, TOPHALF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEnabled = false;
/*     */   
/*  19 */   public static float[] clientRotation = new float[] { 0.0F, 0.0F };
/*  20 */   public static float keepRotationTicks = 0.0F;
/*  21 */   public static float randomizeAmount = 0.0F;
/*     */   
/*     */   public static boolean strafeFix = true;
/*     */   public static boolean strictStrafeFix = false;
/*     */   
/*     */   public static void setStrafeFix(boolean enabled, boolean strict) {
/*  27 */     strafeFix = enabled;
/*  28 */     strictStrafeFix = strict;
/*     */   }
/*     */   
/*     */   public static void disable() {
/*  32 */     isEnabled = false;
/*  33 */     keepRotationTicks = 0.0F;
/*  34 */     strafeFix = false;
/*  35 */     strictStrafeFix = true;
/*     */   }
/*     */   
/*     */   public static void setClientRotation(float[] targetRotation) {
/*  39 */     setClientRotation(targetRotation, 0);
/*     */   }
/*     */   
/*     */   public static void setClientRotation(float[] targetRotation, int keepRotation) {
/*  43 */     if (!isEnabled || keepRotationTicks <= 0.0F) {
/*  44 */       isEnabled = true;
/*  45 */       keepRotationTicks = keepRotation;
/*  46 */       clientRotation = targetRotation;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setPlayerRotation(float[] targetRotation) {
/*  51 */     targetRotation = applyGCD(targetRotation, new float[] { (mc.getPlayer()).prevRotationYaw, (mc.getPlayer()).prevRotationPitch });
/*  52 */     (mc.getPlayer()).rotationYaw = targetRotation[0];
/*  53 */     (mc.getPlayer()).rotationPitch = targetRotation[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getNeededRotations(Vec3 vec) {
/*  58 */     Vec3 playerVector = new Vec3((getPlayer()).posX, (getPlayer()).posY + getPlayer().getEyeHeight(), (getPlayer()).posZ);
/*  59 */     double y = vec.yCoord - playerVector.yCoord;
/*  60 */     double x = vec.xCoord - playerVector.xCoord;
/*  61 */     double z = vec.zCoord - playerVector.zCoord;
/*     */     
/*  63 */     double dff = Math.sqrt(x * x + z * z);
/*  64 */     float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
/*  65 */     float pitch = (float)-Math.toDegrees(Math.atan2(y, dff));
/*     */     
/*  67 */     return new float[] {
/*  68 */         updateRots(yaw, yaw, 180.0F), 
/*  69 */         updateRots(pitch, pitch, 90.0F)
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getTargetRotations(AxisAlignedBB aabb, TargetRotation mode, double random) {
/*  75 */     double minX = 0.0D, maxX = 1.0D;
/*  76 */     double minY = 0.0D, maxY = 1.0D;
/*  77 */     double minZ = 0.0D, maxZ = 1.0D;
/*     */     
/*  79 */     switch (mode) {
/*     */ 
/*     */       
/*     */       case CENTER:
/*  83 */         minX = 0.5D; maxX = 0.5D;
/*  84 */         minY = 0.5D; maxY = 0.5D;
/*  85 */         minZ = 0.5D; maxZ = 0.5D;
/*     */         break;
/*     */       case MIDDLE:
/*  88 */         minX = 0.4D; maxX = 0.6D;
/*  89 */         minY = 0.4D; maxY = 0.6D;
/*  90 */         minZ = 0.4D; maxZ = 0.6D;
/*     */         break;
/*     */ 
/*     */       
/*     */       case TOPHALF:
/*  95 */         minX = 0.1D; maxX = 0.9D;
/*  96 */         minY = 0.5D; maxY = 0.9D;
/*  97 */         minZ = 0.1D; maxZ = 0.9D;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     Vec3 rotPoint = new Vec3(aabb.minX, aabb.minY, aabb.minZ);
/* 103 */     if (mode == TargetRotation.OPTIMAL) {
/*     */ 
/*     */ 
/*     */       
/* 107 */       rotPoint = new Vec3(Math.max(aabb.minX, Math.min(aabb.maxX, (mc.getPlayer()).posX)), Math.max(aabb.minY, Math.min(aabb.maxY, (mc.getPlayer()).posY)), Math.max(aabb.minZ, Math.min(aabb.maxZ, (mc.getPlayer()).posZ)));
/*     */     } else {
/* 109 */       double minRotDiff = 180.0D;
/*     */       double x;
/* 111 */       for (x = minX; x <= maxX; x += 0.1D) {
/* 112 */         double y; for (y = minY; y <= maxY; y += 0.1D) {
/* 113 */           double z; for (z = minZ; z <= maxZ; z += 0.1D) {
/* 114 */             double currentRotDiff = getRotationDifference(
/* 115 */                 MathUtil.interpolate(aabb.maxX, aabb.minX, x), 
/* 116 */                 MathUtil.interpolate(aabb.maxY, aabb.minY, y), 
/* 117 */                 MathUtil.interpolate(aabb.maxZ, aabb.minZ, z));
/*     */             
/* 119 */             if (currentRotDiff < minRotDiff) {
/* 120 */               minRotDiff = currentRotDiff;
/*     */ 
/*     */ 
/*     */               
/* 124 */               rotPoint = new Vec3(MathUtil.interpolate(aabb.maxX, aabb.minX, x), MathUtil.interpolate(aabb.maxY, aabb.minY, y), MathUtil.interpolate(aabb.maxZ, aabb.minZ, z));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     double randX = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
/* 132 */     double randY = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
/* 133 */     double randZ = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
/*     */     
/* 135 */     rotPoint.addVector(randX, randY, randZ);
/*     */     
/* 137 */     return getRotations(rotPoint);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(Vec3 start, Vec3 dst) {
/* 141 */     double xDif = dst.xCoord - start.xCoord;
/* 142 */     double yDif = dst.yCoord - start.yCoord;
/* 143 */     double zDif = dst.zCoord - start.zCoord;
/*     */     
/* 145 */     double distXZ = Math.sqrt(xDif * xDif + zDif * zDif);
/*     */     
/* 147 */     return new float[] {
/* 148 */         (float)(Math.atan2(zDif, xDif) * 180.0D / Math.PI) - 90.0F, 
/* 149 */         (float)-(Math.atan2(yDif, distXZ) * 180.0D / Math.PI) };
/*     */   }
/*     */   
/*     */   public static float[] getRotations(Entity entity) {
/* 153 */     return getRotations(entity.posX, entity.posY, entity.posZ);
/*     */   }
/*     */   public static float[] getRotations(Vec3 vec) {
/* 156 */     return getRotations(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z) {
/* 160 */     Vec3 lookVec = getPlayer().getPositionEyes(1.0F);
/* 161 */     double dx = lookVec.xCoord - x;
/* 162 */     double dy = lookVec.yCoord - y;
/* 163 */     double dz = lookVec.zCoord - z;
/*     */     
/* 165 */     double dist = Math.hypot(dx, dz);
/* 166 */     double yaw = Math.toDegrees(Math.atan2(dz, dx));
/* 167 */     double pitch = Math.toDegrees(Math.atan2(dy, dist));
/*     */     
/* 169 */     return new float[] { (float)yaw + 90.0F, (float)pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 getNormalRotVector(float[] rotation) {
/* 177 */     return getNormalRotVector(rotation[0], rotation[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3 getNormalRotVector(float yaw, float pitch) {
/* 182 */     return mc.getPlayer().getVectorForRotation(pitch, yaw);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getRotationDifference(Entity e) {
/* 187 */     float[] entityRotation = getRotations(e.posX, e.posY, e.posZ);
/* 188 */     return getRotationDifference(entityRotation);
/*     */   }
/*     */   
/*     */   public static double getRotationDifference(Vec3 e) {
/* 192 */     float[] entityRotation = getRotations(e.xCoord, e.yCoord, e.zCoord);
/* 193 */     return getRotationDifference(entityRotation);
/*     */   }
/*     */   
/*     */   public static double getRotationDifference(double x, double y, double z) {
/* 197 */     float[] entityRotation = getRotations(x, y, z);
/* 198 */     return getRotationDifference(entityRotation);
/*     */   }
/*     */   
/*     */   public static double getRotationDifference(float[] e) {
/* 202 */     double yawDif = MathHelper.wrapAngleTo180_double(((mc.getPlayer()).rotationYaw - e[0]));
/* 203 */     double pitchDif = MathHelper.wrapAngleTo180_double(((mc.getPlayer()).rotationPitch - e[1]));
/* 204 */     return Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(float[] lastRotations, float smoothing, Vec3 start, Vec3 dst) {
/* 208 */     float[] rotations = getRotations(start, dst);
/* 209 */     applySmoothing(lastRotations, smoothing, rotations);
/*     */     
/* 211 */     return rotations;
/*     */   }
/*     */   
/*     */   public static void applySmoothing(float[] lastRotations, float smoothing, float[] dstRotation) {
/* 215 */     if (smoothing > 0.0F) {
/* 216 */       float yawChange = MathHelper.wrapAngleTo180_float(dstRotation[0] - lastRotations[0]);
/* 217 */       float pitchChange = MathHelper.wrapAngleTo180_float(dstRotation[1] - lastRotations[1]);
/*     */       
/* 219 */       float smoothingFactor = Math.max(1.0F, smoothing / 10.0F);
/*     */       
/* 221 */       dstRotation[0] = lastRotations[0] + yawChange / smoothingFactor;
/* 222 */       dstRotation[1] = Math.max(Math.min(90.0F, lastRotations[1] + pitchChange / smoothingFactor), -90.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float[] applyGCD(float[] rotations, float[] prevRots) {
/* 227 */     float mouseSensitivity = (float)((mc.getGameSettings()).mouseSensitivity * (1.0D + Math.random() / 1.0E7D) * 0.6000000238418579D + 0.20000000298023224D);
/* 228 */     double multiplier = (mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F) * 0.15D;
/* 229 */     float yaw = prevRots[0] + (float)(Math.round((rotations[0] - prevRots[0]) / multiplier) * multiplier);
/* 230 */     float pitch = prevRots[1] + (float)(Math.round((rotations[1] - prevRots[1]) / multiplier) * multiplier);
/*     */     
/* 232 */     return new float[] { yaw, MathHelper.clamp_float(pitch, -90.0F, 90.0F) };
/*     */   }
/*     */   
/*     */   public static float updateRots(float from, float to, float speed) {
/* 236 */     float f = MathHelper.wrapAngleTo180_float(to - from);
/* 237 */     if (f > speed) f = speed; 
/* 238 */     if (f < -speed) f = -speed; 
/* 239 */     return from + f;
/*     */   }
/*     */   
/*     */   public static float[] getLimitedRotation(float[] from, float[] to, float speed) {
/* 243 */     double yawDif = MathHelper.wrapAngleTo180_double((from[0] - to[0]));
/* 244 */     double pitchDif = MathHelper.wrapAngleTo180_double((from[1] - to[1]));
/* 245 */     double rotDif = Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
/*     */     
/* 247 */     double yawLimit = yawDif * speed / rotDif;
/* 248 */     double pitchLimit = pitchDif * speed / rotDif;
/*     */     
/* 250 */     return new float[] { updateRots(from[0], to[0], (float)yawLimit), updateRots(from[1], to[1], (float)pitchLimit) };
/*     */   }
/*     */   
/*     */   public static EnumFacing getEnumDirection(float yaw) {
/* 254 */     return EnumFacing.getHorizontal(MathHelper.floor_double((yaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */