package com.viaversion.viaversion.api.command;

import java.util.List;

public interface ViaVersionCommand {
  void registerSubCommand(ViaSubCommand paramViaSubCommand) throws Exception;
  
  boolean hasSubCommand(String paramString);
  
  ViaSubCommand getSubCommand(String paramString);
  
  boolean onCommand(ViaCommandSender paramViaCommandSender, String[] paramArrayOfString);
  
  List<String> onTabComplete(ViaCommandSender paramViaCommandSender, String[] paramArrayOfString);
  
  void showHelp(ViaCommandSender paramViaCommandSender);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\command\ViaVersionCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */