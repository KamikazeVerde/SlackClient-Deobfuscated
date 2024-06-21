package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import java.io.IOException;

public abstract class JsonReaderInternalAccess {
  public static JsonReaderInternalAccess INSTANCE;
  
  public abstract void promoteNameToValue(JsonReader paramJsonReader) throws IOException;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\JsonReaderInternalAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */