/*    */ package cc.slack.utils.player;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ 
/*    */ public class TimerUtil
/*    */   extends mc {
/*    */   private static final float DEFAULT_TIMER = 1.0F;
/*    */   
/*    */   public static void set(float timer) {
/* 10 */     setTimer(timer, 0);
/*    */   }
/*    */   
/*    */   public static void setTick(float timer, int tick) {
/* 14 */     setTimer(timer, tick);
/*    */   }
/*    */   
/*    */   public static void setTickOnGround(float timer, int tick) {
/* 18 */     setTimer((getPlayer()).onGround ? timer : 1.0F, tick);
/*    */   }
/*    */   
/*    */   public static void setOnGround(float timer) {
/* 22 */     setTimer((getPlayer()).onGround ? timer : 1.0F, 0);
/*    */   }
/*    */   
/*    */   private static void setTimer(float timer, int tick) {
/* 26 */     if (!shouldTimer()) {
/* 27 */       reset();
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     if (tick == 0) {
/* 32 */       (getTimer()).timerSpeed = timer;
/*    */     } else {
/* 34 */       (getTimer()).timerSpeed = ((mc.getPlayer()).ticksExisted % tick == 0) ? timer : 1.0F;
/*    */     } 
/*    */   }
/*    */   private static boolean shouldTimer() {
/* 38 */     return (getPlayer() != null && getWorld() != null && getPlayer().isEntityAlive());
/*    */   }
/*    */   
/*    */   public static void reset() {
/* 42 */     (getTimer()).timerSpeed = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\TimerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */