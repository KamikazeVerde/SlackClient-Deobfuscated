package com.viaversion.viaversion.api.scheduler;

import java.util.concurrent.TimeUnit;

public interface Scheduler {
  Task execute(Runnable paramRunnable);
  
  Task schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
  
  Task scheduleRepeating(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  void shutdown();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\scheduler\Scheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */