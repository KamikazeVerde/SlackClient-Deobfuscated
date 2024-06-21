package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

public interface JsonSerializationContext {
  JsonElement serialize(Object paramObject);
  
  JsonElement serialize(Object paramObject, Type paramType);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\JsonSerializationContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */