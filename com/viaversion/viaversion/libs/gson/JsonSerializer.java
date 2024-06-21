package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

public interface JsonSerializer<T> {
  JsonElement serialize(T paramT, Type paramType, JsonSerializationContext paramJsonSerializationContext);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\JsonSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */