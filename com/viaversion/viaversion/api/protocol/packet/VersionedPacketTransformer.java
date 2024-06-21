package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.function.Consumer;

public interface VersionedPacketTransformer<C extends ClientboundPacketType, S extends ServerboundPacketType> {
  boolean send(PacketWrapper paramPacketWrapper) throws Exception;
  
  boolean send(UserConnection paramUserConnection, C paramC, Consumer<PacketWrapper> paramConsumer) throws Exception;
  
  boolean send(UserConnection paramUserConnection, S paramS, Consumer<PacketWrapper> paramConsumer) throws Exception;
  
  boolean scheduleSend(PacketWrapper paramPacketWrapper) throws Exception;
  
  boolean scheduleSend(UserConnection paramUserConnection, C paramC, Consumer<PacketWrapper> paramConsumer) throws Exception;
  
  boolean scheduleSend(UserConnection paramUserConnection, S paramS, Consumer<PacketWrapper> paramConsumer) throws Exception;
  
  PacketWrapper transform(PacketWrapper paramPacketWrapper) throws Exception;
  
  PacketWrapper transform(UserConnection paramUserConnection, C paramC, Consumer<PacketWrapper> paramConsumer) throws Exception;
  
  PacketWrapper transform(UserConnection paramUserConnection, S paramS, Consumer<PacketWrapper> paramConsumer) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\VersionedPacketTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */