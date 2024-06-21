package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import java.io.IOException;

public interface ToNumberStrategy {
  Number readNumber(JsonReader paramJsonReader) throws IOException;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\ToNumberStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */