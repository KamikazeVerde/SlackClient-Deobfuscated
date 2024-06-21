package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

public interface JsonDeserializationContext {
  <T> T deserialize(JsonElement paramJsonElement, Type paramType) throws JsonParseException;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\JsonDeserializationContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */