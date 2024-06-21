/*    */ package cc.slack.utils.player;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ 
/*    */ public class ItemSpoofUtil
/*    */   extends mc
/*    */ {
/*    */   public static boolean isEnabled = false;
/*  9 */   public static int renderSlot = 0;
/* 10 */   public static int realSlot = 0;
/*    */   
/*    */   public static void startSpoofing(int slot) {
/* 13 */     if (isEnabled) {
/* 14 */       realSlot = slot;
/* 15 */       (mc.getPlayer()).inventory.currentItem = realSlot;
/*    */       return;
/*    */     } 
/* 18 */     renderSlot = (mc.getPlayer()).inventory.currentItem;
/* 19 */     realSlot = slot;
/* 20 */     (mc.getPlayer()).inventory.currentItem = realSlot;
/* 21 */     isEnabled = true;
/*    */   }
/*    */   
/*    */   public static void stopSpoofing() {
/* 25 */     realSlot = renderSlot;
/* 26 */     isEnabled = false;
/* 27 */     (mc.getPlayer()).inventory.currentItem = renderSlot;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\ItemSpoofUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */