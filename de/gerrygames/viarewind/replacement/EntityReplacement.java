package de.gerrygames.viarewind.replacement;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;

public interface EntityReplacement {
  int getEntityId();
  
  void setLocation(double paramDouble1, double paramDouble2, double paramDouble3);
  
  void relMove(double paramDouble1, double paramDouble2, double paramDouble3);
  
  void setYawPitch(float paramFloat1, float paramFloat2);
  
  void setHeadYaw(float paramFloat);
  
  void spawn();
  
  void despawn();
  
  void updateMetadata(List<Metadata> paramList);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\replacement\EntityReplacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */