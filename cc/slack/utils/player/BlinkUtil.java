/*     */ package cc.slack.utils.player;
/*     */ 
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.network.PacketUtil;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlinkUtil
/*     */   extends mc
/*     */ {
/*     */   public static boolean isEnabled = false;
/*     */   public static boolean BLINK_INBOUND = false;
/*     */   public static boolean BLINK_OUTBOUND = false;
/*  45 */   private static final CopyOnWriteArrayList<Packet> clientPackets = new CopyOnWriteArrayList<>();
/*  46 */   private static final CopyOnWriteArrayList<Packet> serverPackets = new CopyOnWriteArrayList<>();
/*     */ 
/*     */   
/*     */   public static boolean isBlinking() {
/*  50 */     return isEnabled;
/*     */   }
/*     */   
/*     */   public static void setConfig(boolean inboundState, boolean outboundState) {
/*  54 */     BLINK_INBOUND = inboundState;
/*  55 */     BLINK_OUTBOUND = outboundState;
/*     */   }
/*     */   
/*     */   public static void enable() {
/*  59 */     enable(false, true);
/*     */   }
/*     */   
/*     */   public static void enable(boolean inboundState, boolean outboundState) {
/*  63 */     setConfig(inboundState, outboundState);
/*  64 */     isEnabled = true;
/*  65 */     clientPackets.clear();
/*  66 */     serverPackets.clear();
/*     */   }
/*     */   
/*     */   public static void disable() {
/*  70 */     disable(true);
/*     */   }
/*     */   
/*     */   public static void disable(boolean releasePackets) {
/*  74 */     if (releasePackets) {
/*  75 */       releasePackets();
/*     */     }
/*  77 */     isEnabled = false;
/*     */   }
/*     */   
/*     */   public static void clearPackets() {
/*  81 */     clearPackets(true, true);
/*     */   }
/*     */   
/*     */   public static void clearPackets(boolean clearInbound, boolean clearOutbound) {
/*  85 */     if (clearInbound) serverPackets.clear(); 
/*  86 */     if (clearOutbound) clientPackets.clear(); 
/*     */   }
/*     */   
/*     */   public static void releasePackets() {
/*  90 */     releasePackets(true, true);
/*     */   }
/*     */   
/*     */   public static void releasePackets(boolean releaseInbound, boolean releaseOutgoing) {
/*  94 */     if (releaseInbound) {
/*  95 */       serverPackets.forEach(packet -> {
/*     */             packet.processPacket(mc.getMinecraft().getNetHandler().getNetworkManager().getNetHandler());
/*     */             
/*     */             serverPackets.remove(packet);
/*     */           });
/*     */     }
/* 101 */     if (releaseOutgoing) {
/* 102 */       clientPackets.forEach(packet -> {
/*     */             PacketUtil.sendNoEvent(packet);
/*     */             
/*     */             clientPackets.remove(packet);
/*     */           });
/*     */     }
/* 108 */     clearPackets();
/*     */   }
/*     */   
/*     */   public static int getSize() {
/* 112 */     return getSize(true, true);
/*     */   }
/*     */   
/*     */   public static int getSize(boolean sizeInbound, boolean sizeOutgoing) {
/* 116 */     int size = 0;
/* 117 */     if (sizeInbound) size += serverPackets.size(); 
/* 118 */     if (sizeOutgoing) size += clientPackets.size(); 
/* 119 */     return size;
/*     */   }
/*     */   
/*     */   public static boolean handlePacket(PacketEvent event) {
/* 123 */     Packet packet = event.getPacket();
/*     */     
/* 125 */     if (isBlinking()) {
/* 126 */       if (event.getDirection() == PacketDirection.INCOMING && BLINK_INBOUND) {
/* 127 */         if (!(packet instanceof net.minecraft.network.login.server.S00PacketDisconnect) && !(packet instanceof net.minecraft.network.status.server.S00PacketServerInfo) && !(packet instanceof net.minecraft.network.play.server.S3EPacketTeams) && !(packet instanceof net.minecraft.network.play.server.S19PacketEntityStatus) && !(packet instanceof net.minecraft.network.play.server.S02PacketChat) && !(packet instanceof net.minecraft.network.play.server.S3BPacketScoreboardObjective)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 132 */           serverPackets.add(packet);
/* 133 */           return true;
/*     */         } 
/* 135 */       } else if (event.getDirection() == PacketDirection.OUTGOING && BLINK_OUTBOUND && 
/* 136 */         !(packet instanceof net.minecraft.network.play.client.C00PacketKeepAlive) && !(packet instanceof net.minecraft.network.handshake.client.C00Handshake) && !(packet instanceof net.minecraft.network.login.client.C00PacketLoginStart)) {
/*     */         
/* 138 */         clientPackets.add(packet);
/* 139 */         return true;
/*     */       } 
/*     */     }
/*     */     
/* 143 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\BlinkUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */