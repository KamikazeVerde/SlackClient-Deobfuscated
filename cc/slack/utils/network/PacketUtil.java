/*    */ package cc.slack.utils.network;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public final class PacketUtil
/*    */   extends mc {
/*    */   public static void send(Packet<?> packet) {
/* 13 */     getNetHandler().getNetworkManager().sendPacket(packet);
/*    */   }
/*    */   
/*    */   public static void send(Packet<?> packet, int iterations) {
/* 17 */     for (int i = 0; i < iterations; ) { send(packet); i++; }
/*    */   
/*    */   }
/*    */   public static void sendNoEvent(Packet<?> packet) {
/* 21 */     getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
/*    */   }
/*    */   public static void sendNoEvent(Packet<?> packet, int iterations) {
/* 24 */     for (int i = 0; i < iterations; ) { sendNoEvent(packet); i++; }
/*    */   
/*    */   }
/*    */   
/*    */   public static void sendBlocking(boolean callEvent, boolean place) {
/* 29 */     C08PacketPlayerBlockPlacement packet = place ? new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.getPlayer().getHeldItem(), 0.0F, 0.0F, 0.0F) : new C08PacketPlayerBlockPlacement(mc.getPlayer().getHeldItem()), c08PacketPlayerBlockPlacement = packet;
/* 30 */     if (callEvent) {
/* 31 */       send((Packet<?>)packet);
/*    */     } else {
/* 33 */       sendNoEvent((Packet<?>)packet);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void releaseUseItem(boolean callEvent) {
/* 38 */     C07PacketPlayerDigging packet = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
/* 39 */     if (callEvent) {
/* 40 */       send((Packet<?>)packet);
/*    */     } else {
/* 42 */       sendNoEvent((Packet<?>)packet);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\network\PacketUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */