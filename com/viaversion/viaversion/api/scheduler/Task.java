package com.viaversion.viaversion.api.scheduler;

public interface Task {
  TaskStatus status();
  
  void cancel();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\scheduler\Task.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */