package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
public interface ValueReader<T> {
  T read(PacketWrapper paramPacketWrapper) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\remapper\ValueReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */