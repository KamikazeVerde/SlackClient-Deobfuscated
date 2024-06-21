package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.Map;

@Beta
public interface PacketTypesProvider<CU extends com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType, CM extends com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType, SM extends com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType, SU extends com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType> {
  Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes();
  
  Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes();
  
  Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes();
  
  Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\provider\PacketTypesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */