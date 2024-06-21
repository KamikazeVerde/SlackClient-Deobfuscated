package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
public interface ValueWriter<T> {
  void write(PacketWrapper paramPacketWrapper, T paramT) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\remapper\ValueWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */