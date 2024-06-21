/*    */ package cc.slack.events.impl.network;
/*    */ import cc.slack.events.Event;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketDirection;
/*    */ 
/*    */ public class PacketEvent extends Event {
/*    */   private Packet packet;
/*    */   
/*    */   public PacketEvent(Packet packet, PacketDirection direction) {
/* 10 */     this.packet = packet; this.direction = direction;
/*    */   } private final PacketDirection direction;
/*    */   public PacketDirection getDirection() {
/* 13 */     return this.direction;
/*    */   }
/*    */   
/*    */   public void setPacket(Packet<?> packet) {
/* 17 */     this.packet = packet;
/*    */   }
/*    */   public <T extends Packet> T getPacket() {
/* 20 */     return (T)this.packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\network\PacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */