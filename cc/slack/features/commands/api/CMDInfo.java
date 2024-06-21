package cc.slack.features.commands.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CMDInfo {
  String name();
  
  String description();
  
  String alias();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\commands\api\CMDInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */