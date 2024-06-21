/*    */ package cc.slack.events.impl.render;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ import cc.slack.events.State;
/*    */ 
/*    */ public class RenderScoreboard
/*    */   extends Event {
/*    */   State state;
/*    */   
/*    */   public RenderScoreboard(State state) {
/* 11 */     this.state = state;
/*    */   }
/*    */   
/*    */   public State getState() {
/* 15 */     return this.state;
/*    */   }
/*    */   
/*    */   public boolean isPre() {
/* 19 */     return (this.state == State.PRE);
/*    */   }
/*    */   
/*    */   public void setState(State state) {
/* 23 */     this.state = state;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\render\RenderScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */