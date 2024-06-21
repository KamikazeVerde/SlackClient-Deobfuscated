package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface FlattenerListener {
  default void pushStyle(@NotNull Style style) {}
  
  void component(@NotNull String paramString);
  
  default void popStyle(@NotNull Style style) {}
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\flattener\FlattenerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */