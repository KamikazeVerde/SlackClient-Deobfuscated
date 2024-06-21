package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

public interface VersionProvider extends Provider {
  int getClosestServerProtocol(UserConnection paramUserConnection) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\version\VersionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */