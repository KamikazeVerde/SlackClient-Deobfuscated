/*    */ package cc.slack.events.impl.input;
/*    */ import cc.slack.events.Event;
/*    */ 
/*    */ public class KeyEvent extends Event {
/*    */   private final int key;
/*    */   
/*    */   public KeyEvent(int key) {
/*  8 */     this.key = key;
/*    */   } public int getKey() {
/* 10 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\input\KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */