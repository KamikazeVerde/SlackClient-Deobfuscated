package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.UUID;

public interface ProtocolInfo {
  State getState();
  
  void setState(State paramState);
  
  int getProtocolVersion();
  
  void setProtocolVersion(int paramInt);
  
  int getServerProtocolVersion();
  
  void setServerProtocolVersion(int paramInt);
  
  String getUsername();
  
  void setUsername(String paramString);
  
  UUID getUuid();
  
  void setUuid(UUID paramUUID);
  
  ProtocolPipeline getPipeline();
  
  void setPipeline(ProtocolPipeline paramProtocolPipeline);
  
  @Deprecated
  UserConnection getUser();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\connection\ProtocolInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */