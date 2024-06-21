package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface ByteBufWriter<T> {
  void write(ByteBuf paramByteBuf, T paramT) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\ByteBufWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */