/*    */ package cc.slack.utils.network;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ @Deprecated
/*    */ public class TimedPacket
/*    */ {
/*    */   private Packet<?> packet;
/*    */   private long ms;
/*    */   
/*    */   public TimedPacket(Packet<?> packet, long ms) {
/* 12 */     this.packet = packet;
/* 13 */     this.ms = ms;
/*    */   }
/*    */   
/*    */   public Packet<?> getPacket() {
/* 17 */     return this.packet;
/*    */   }
/*    */   
/*    */   public void setPacket(Packet<?> packet) {
/* 21 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public long getMs() {
/* 25 */     return this.ms;
/*    */   }
/*    */   
/*    */   public void setMs(long ms) {
/* 29 */     this.ms = ms;
/*    */   }
/*    */   
/*    */   public boolean elapsed(long ms) {
/* 33 */     return (System.currentTimeMillis() - getMs() > ms);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\network\TimedPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */