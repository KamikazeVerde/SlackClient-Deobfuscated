package com.viaversion.viaversion.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface ViaCodecHandler {
  void transform(ByteBuf paramByteBuf) throws Exception;
  
  void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception;
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\handlers\ViaCodecHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */