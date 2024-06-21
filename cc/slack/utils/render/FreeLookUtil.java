/*    */ package cc.slack.utils.render;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ 
/*    */ public class FreeLookUtil
/*    */   extends mc
/*    */ {
/*    */   public static float cameraYaw;
/*    */   public static float cameraPitch;
/*    */   public static boolean freelooking;
/*    */   
/*    */   public static void overrideMouse(float f3, float f4) {
/* 13 */     cameraYaw += f3 * 0.15F;
/* 14 */     cameraPitch -= f4 * 0.15F;
/* 15 */     cameraPitch = Math.max(-90.0F, Math.min(90.0F, cameraPitch));
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 19 */     this; return freelooking ? cameraYaw : (mc.getPlayer()).rotationYaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 23 */     this; return freelooking ? cameraPitch : (mc.getPlayer()).rotationPitch;
/*    */   }
/*    */   
/*    */   public float getPrevYaw() {
/* 27 */     this; return freelooking ? cameraYaw : (mc.getPlayer()).prevRotationYaw;
/*    */   }
/*    */   
/*    */   public float getPrevPitch() {
/* 31 */     this; return freelooking ? cameraPitch : (mc.getPlayer()).prevRotationPitch;
/*    */   }
/*    */   
/*    */   public static void setFreelooking(boolean setFreelook) {
/* 35 */     freelooking = setFreelook;
/*    */   }
/*    */   
/*    */   public static void enable() {
/* 39 */     setFreelooking(true);
/* 40 */     cameraYaw = (mc.getPlayer()).rotationYaw;
/* 41 */     cameraPitch = (mc.getPlayer()).rotationPitch;
/*    */   }
/*    */   
/*    */   public static void disable() {
/* 45 */     setFreelooking(false);
/* 46 */     cameraYaw = (mc.getPlayer()).rotationYaw;
/* 47 */     cameraPitch = (mc.getPlayer()).rotationPitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\render\FreeLookUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */