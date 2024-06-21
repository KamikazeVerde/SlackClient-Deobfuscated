package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

public interface ServerSender {
  void sendToServer(PacketWrapper paramPacketWrapper, Class<? extends Protocol> paramClass, boolean paramBoolean1, boolean paramBoolean2) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\ServerSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */