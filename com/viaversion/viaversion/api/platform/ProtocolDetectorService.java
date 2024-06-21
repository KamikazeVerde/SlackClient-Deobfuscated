package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;

public interface ProtocolDetectorService {
  int serverProtocolVersion(String paramString);
  
  void probeAllServers();
  
  void setProtocolVersion(String paramString, int paramInt);
  
  int uncacheProtocolVersion(String paramString);
  
  Object2IntMap<String> detectedProtocolVersions();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\platform\ProtocolDetectorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */