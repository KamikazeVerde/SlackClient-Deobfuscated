package io.netty.bootstrap;

public interface ChannelFactory<T extends io.netty.channel.Channel> {
  T newChannel();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\io\netty\bootstrap\ChannelFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */