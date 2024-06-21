/*    */ package org.yaml.snakeyaml.events;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AliasEvent
/*    */   extends NodeEvent
/*    */ {
/*    */   public AliasEvent(String anchor, Mark startMark, Mark endMark) {
/* 31 */     super(anchor, startMark, endMark);
/* 32 */     if (anchor == null) {
/* 33 */       throw new NullPointerException("anchor is not specified for alias");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Event.ID getEventId() {
/* 39 */     return Event.ID.Alias;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\events\AliasEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */