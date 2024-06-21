package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;

public interface EntityTracker {
  UserConnection user();
  
  void addEntity(int paramInt, EntityType paramEntityType);
  
  boolean hasEntity(int paramInt);
  
  TrackedEntity entity(int paramInt);
  
  EntityType entityType(int paramInt);
  
  void removeEntity(int paramInt);
  
  void clearEntities();
  
  StoredEntityData entityData(int paramInt);
  
  StoredEntityData entityDataIfPresent(int paramInt);
  
  int clientEntityId();
  
  void setClientEntityId(int paramInt);
  
  int currentWorldSectionHeight();
  
  void setCurrentWorldSectionHeight(int paramInt);
  
  int currentMinY();
  
  void setCurrentMinY(int paramInt);
  
  String currentWorld();
  
  void setCurrentWorld(String paramString);
  
  int biomesSent();
  
  void setBiomesSent(int paramInt);
  
  EntityType playerType();
  
  DimensionData dimensionData(String paramString);
  
  void setDimensions(Map<String, DimensionData> paramMap);
  
  boolean trackClientEntity();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\entity\EntityTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */