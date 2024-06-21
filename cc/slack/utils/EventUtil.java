/*    */ package cc.slack.utils;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.events.Event;
/*    */ 
/*    */ public final class EventUtil {
/*    */   public static void register(Object o) {
/*  8 */     Slack.getInstance().getEventBus().subscribe(o);
/*    */   }
/*    */   
/*    */   public static void unRegister(Object o) {
/* 12 */     Slack.getInstance().getEventBus().unsubscribe(o);
/*    */   }
/*    */   
/*    */   public static void call(Event e) {
/* 16 */     Slack.getInstance().getEventBus().publish(e);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\EventUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */