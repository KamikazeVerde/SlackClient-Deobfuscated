package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;

public interface TrackedEntity {
  EntityType entityType();
  
  StoredEntityData data();
  
  boolean hasData();
  
  boolean hasSentMetadata();
  
  void sentMetadata(boolean paramBoolean);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\entity\TrackedEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */