package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

public interface Namespaced {
  @NotNull
  @Pattern("[a-z0-9_\\-.]+")
  String namespace();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\key\Namespaced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */