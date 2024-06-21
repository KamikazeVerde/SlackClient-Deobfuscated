/*    */ package cc.slack.events.impl.game;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ import cc.slack.events.State;
/*    */ 
/*    */ public class TickEvent
/*    */   extends Event {
/*    */   public void setState(State state) {
/*  9 */     this.state = state;
/*    */   }
/*    */   public State getState() {
/* 12 */     return this.state;
/*    */   }
/*    */   
/* 15 */   private State state = State.PRE;
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\game\TickEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */