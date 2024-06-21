package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface ListTagSetter<R, T extends BinaryTag> {
  @NotNull
  R add(T paramT);
  
  @NotNull
  R add(Iterable<? extends T> paramIterable);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ListTagSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */