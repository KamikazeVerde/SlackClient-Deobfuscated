package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public interface BlockedProtocolVersions {
  boolean contains(int paramInt);
  
  int blocksBelow();
  
  int blocksAbove();
  
  IntSet singleBlockedVersions();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\version\BlockedProtocolVersions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */