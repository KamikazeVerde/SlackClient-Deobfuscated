/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.PacketDirection;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ public class MessageDeserializer
/*    */   extends ByteToMessageDecoder {
/* 20 */   private static final Logger logger = LogManager.getLogger();
/* 21 */   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
/*    */   
/*    */   private final PacketDirection direction;
/*    */   
/*    */   public MessageDeserializer(PacketDirection direction) {
/* 26 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws IOException, InstantiationException, IllegalAccessException, Exception {
/* 31 */     if (p_decode_2_.readableBytes() != 0) {
/*    */       
/* 33 */       PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
/* 34 */       int i = packetbuffer.readVarIntFromBuffer();
/* 35 */       Packet packet = ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacket(this.direction, i);
/*    */       
/* 37 */       if (packet == null)
/*    */       {
/* 39 */         throw new IOException("Bad packet id " + i);
/*    */       }
/*    */ 
/*    */       
/* 43 */       packet.readPacketData(packetbuffer);
/*    */       
/* 45 */       if (packetbuffer.readableBytes() > 0)
/*    */       {
/* 47 */         throw new IOException("Packet " + ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getId() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + i);
/*    */       }
/*    */ 
/*    */       
/* 51 */       p_decode_3_.add(packet);
/*    */       
/* 53 */       if (logger.isDebugEnabled())
/*    */       {
/* 55 */         logger.debug(RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", new Object[] { p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), Integer.valueOf(i), packet.getClass().getName() });
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\MessageDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */