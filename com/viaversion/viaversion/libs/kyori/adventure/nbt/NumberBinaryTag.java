package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface NumberBinaryTag extends BinaryTag {
  @NotNull
  BinaryTagType<? extends NumberBinaryTag> type();
  
  byte byteValue();
  
  double doubleValue();
  
  float floatValue();
  
  int intValue();
  
  long longValue();
  
  short shortValue();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\NumberBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */