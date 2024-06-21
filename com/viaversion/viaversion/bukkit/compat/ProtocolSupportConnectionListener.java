/*    */ package com.viaversion.viaversion.bukkit.compat;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.bukkit.util.NMSUtil;
/*    */ import java.lang.reflect.Method;
/*    */ import protocolsupport.api.Connection;
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
/*    */ final class ProtocolSupportConnectionListener
/*    */   extends Connection.PacketListener
/*    */ {
/*    */   static final Method ADD_PACKET_LISTENER_METHOD;
/*    */   private static final Class<?> HANDSHAKE_PACKET_CLASS;
/*    */   private static final Method GET_VERSION_METHOD;
/*    */   private static final Method SET_VERSION_METHOD;
/*    */   private static final Method REMOVE_PACKET_LISTENER_METHOD;
/*    */   private static final Method GET_LATEST_METHOD;
/*    */   private static final Object PROTOCOL_VERSION_MINECRAFT_FUTURE;
/*    */   private static final Object PROTOCOL_TYPE_PC;
/*    */   private final Object connection;
/*    */   
/*    */   static {
/*    */     try {
/* 38 */       HANDSHAKE_PACKET_CLASS = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       Class<?> connectionImplClass = Class.forName("protocolsupport.protocol.ConnectionImpl");
/* 44 */       Class<?> connectionClass = Class.forName("protocolsupport.api.Connection");
/* 45 */       Class<?> packetListenerClass = Class.forName("protocolsupport.api.Connection$PacketListener");
/* 46 */       Class<?> protocolVersionClass = Class.forName("protocolsupport.api.ProtocolVersion");
/* 47 */       Class<?> protocolTypeClass = Class.forName("protocolsupport.api.ProtocolType");
/* 48 */       GET_VERSION_METHOD = connectionClass.getDeclaredMethod("getVersion", new Class[0]);
/* 49 */       SET_VERSION_METHOD = connectionImplClass.getDeclaredMethod("setVersion", new Class[] { protocolVersionClass });
/* 50 */       PROTOCOL_VERSION_MINECRAFT_FUTURE = protocolVersionClass.getDeclaredField("MINECRAFT_FUTURE").get(null);
/* 51 */       GET_LATEST_METHOD = protocolVersionClass.getDeclaredMethod("getLatest", new Class[] { protocolTypeClass });
/* 52 */       PROTOCOL_TYPE_PC = protocolTypeClass.getDeclaredField("PC").get(null);
/* 53 */       ADD_PACKET_LISTENER_METHOD = connectionClass.getDeclaredMethod("addPacketListener", new Class[] { packetListenerClass });
/* 54 */       REMOVE_PACKET_LISTENER_METHOD = connectionClass.getDeclaredMethod("removePacketListener", new Class[] { packetListenerClass });
/* 55 */     } catch (ReflectiveOperationException e) {
/* 56 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   ProtocolSupportConnectionListener(Object connection) {
/* 63 */     this.connection = connection;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPacketReceiving(Connection.PacketListener.PacketEvent event) {
/*    */     try {
/* 70 */       if (HANDSHAKE_PACKET_CLASS.isInstance(event.getPacket()) && GET_VERSION_METHOD.invoke(this.connection, new Object[0]) == PROTOCOL_VERSION_MINECRAFT_FUTURE) {
/* 71 */         Object packet = event.getPacket();
/* 72 */         int protocolVersion = ((Integer)HANDSHAKE_PACKET_CLASS.getDeclaredMethod(ProtocolSupportCompat.handshakeVersionMethod().methodName(), new Class[0]).invoke(packet, new Object[0])).intValue();
/*    */ 
/*    */ 
/*    */         
/* 76 */         if (protocolVersion == Via.getAPI().getServerVersion().lowestSupportedVersion()) {
/* 77 */           SET_VERSION_METHOD.invoke(this.connection, new Object[] { GET_LATEST_METHOD.invoke(null, new Object[] { PROTOCOL_TYPE_PC }) });
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 82 */       REMOVE_PACKET_LISTENER_METHOD.invoke(this.connection, new Object[] { this });
/* 83 */     } catch (ReflectiveOperationException e) {
/* 84 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\compat\ProtocolSupportConnectionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */