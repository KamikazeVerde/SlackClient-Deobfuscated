package com.viaversion.viaversion.platform;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public interface WrappedChannelInitializer {
  ChannelInitializer<Channel> original();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\platform\WrappedChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */