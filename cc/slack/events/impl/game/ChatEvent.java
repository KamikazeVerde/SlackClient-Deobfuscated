/*    */ package cc.slack.events.impl.game;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ import net.minecraft.network.PacketDirection;
/*    */ 
/*    */ public class ChatEvent extends Event {
/*    */   private String message;
/*    */   private final PacketDirection direction;
/*    */   
/* 10 */   public void setMessage(String message) { this.message = message; } public ChatEvent(String message, PacketDirection direction) {
/* 11 */     this.message = message; this.direction = direction;
/*    */   }
/* 13 */   public String getMessage() { return this.message; } public PacketDirection getDirection() {
/* 14 */     return this.direction;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\game\ChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */