package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

public interface InstanceCreator<T> {
  T createInstance(Type paramType);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\InstanceCreator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */