package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;

public interface StoredEntityData {
  EntityType type();
  
  boolean has(Class<?> paramClass);
  
  <T> T get(Class<T> paramClass);
  
  <T> T remove(Class<T> paramClass);
  
  void put(Object paramObject);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\entity\StoredEntityData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */