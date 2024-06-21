package com.viaversion.viaversion.api.command;

import java.util.UUID;

public interface ViaCommandSender {
  boolean hasPermission(String paramString);
  
  void sendMessage(String paramString);
  
  UUID getUUID();
  
  String getName();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\command\ViaCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */