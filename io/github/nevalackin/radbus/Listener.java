package io.github.nevalackin.radbus;

@FunctionalInterface
public interface Listener<Event> {
  void invoke(Event paramEvent);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\io\github\nevalackin\radbus\Listener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */