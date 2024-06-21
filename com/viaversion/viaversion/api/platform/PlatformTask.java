package com.viaversion.viaversion.api.platform;

public interface PlatformTask<T> {
  @Deprecated
  T getObject();
  
  void cancel();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\platform\PlatformTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */