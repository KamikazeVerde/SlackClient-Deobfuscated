/*    */ package de.gerrygames.viarewind.utils;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.exception.CancelException;
/*    */ 
/*    */ public class PacketUtil
/*    */ {
/*    */   public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol) {
/* 10 */     sendToServer(packet, packetProtocol, true);
/*    */   }
/*    */   
/*    */   public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) {
/* 14 */     sendToServer(packet, packetProtocol, skipCurrentPipeline, false);
/*    */   }
/*    */   
/*    */   public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) {
/*    */     
/* 19 */     try { if (currentThread) {
/* 20 */         packet.sendToServer(packetProtocol, skipCurrentPipeline);
/*    */       } else {
/* 22 */         packet.scheduleSendToServer(packetProtocol, skipCurrentPipeline);
/*    */       }  }
/* 24 */     catch (CancelException cancelException) {  }
/* 25 */     catch (Exception ex)
/* 26 */     { ex.printStackTrace(); }
/*    */   
/*    */   }
/*    */   
/*    */   public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol) {
/* 31 */     return sendPacket(packet, packetProtocol, true);
/*    */   }
/*    */   
/*    */   public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) {
/* 35 */     return sendPacket(packet, packetProtocol, true, false);
/*    */   }
/*    */   
/*    */   public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) {
/*    */     
/* 40 */     try { if (currentThread) {
/* 41 */         packet.send(packetProtocol, skipCurrentPipeline);
/*    */       } else {
/* 43 */         packet.scheduleSend(packetProtocol, skipCurrentPipeline);
/*    */       } 
/* 45 */       return true; }
/* 46 */     catch (CancelException cancelException) {  }
/* 47 */     catch (Exception ex)
/* 48 */     { ex.printStackTrace(); }
/*    */     
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\PacketUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */