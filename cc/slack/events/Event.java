/*    */ package cc.slack.events;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ 
/*    */ public class Event {
/*    */   private boolean cancel;
/*    */   
/*    */   public Event call() {
/*  9 */     Slack.getInstance().getEventBus().publish(this);
/* 10 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isCanceled() {
/* 14 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 18 */     this.cancel = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\Event.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */