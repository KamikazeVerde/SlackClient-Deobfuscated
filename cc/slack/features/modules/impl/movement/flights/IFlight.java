package cc.slack.features.modules.impl.movement.flights;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;

public interface IFlight {
  default void onEnable() {}
  
  default void onDisable() {}
  
  default void onMove(MoveEvent event) {}
  
  default void onPacket(PacketEvent event) {}
  
  default void onCollide(CollideEvent event) {}
  
  default void onUpdate(UpdateEvent event) {}
  
  default void onMotion(MotionEvent event) {}
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\flights\IFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */