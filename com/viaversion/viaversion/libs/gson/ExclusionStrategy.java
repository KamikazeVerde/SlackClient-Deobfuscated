package com.viaversion.viaversion.libs.gson;

public interface ExclusionStrategy {
  boolean shouldSkipField(FieldAttributes paramFieldAttributes);
  
  boolean shouldSkipClass(Class<?> paramClass);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\ExclusionStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */