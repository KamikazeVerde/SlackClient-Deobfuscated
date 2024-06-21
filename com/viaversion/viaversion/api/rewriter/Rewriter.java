package com.viaversion.viaversion.api.rewriter;

public interface Rewriter<T extends com.viaversion.viaversion.api.protocol.Protocol> {
  void register();
  
  T protocol();
  
  default void onMappingDataLoaded() {}
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\rewriter\Rewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */