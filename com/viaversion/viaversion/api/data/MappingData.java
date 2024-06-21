package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import java.util.List;

public interface MappingData {
  void load();
  
  int getNewBlockStateId(int paramInt);
  
  int getNewBlockId(int paramInt);
  
  int getNewItemId(int paramInt);
  
  int getOldItemId(int paramInt);
  
  int getNewParticleId(int paramInt);
  
  List<TagData> getTags(RegistryType paramRegistryType);
  
  BiMappings getItemMappings();
  
  ParticleMappings getParticleMappings();
  
  Mappings getBlockMappings();
  
  Mappings getBlockEntityMappings();
  
  Mappings getBlockStateMappings();
  
  Mappings getSoundMappings();
  
  Mappings getStatisticsMappings();
  
  Mappings getMenuMappings();
  
  Mappings getEnchantmentMappings();
  
  FullMappings getEntityMappings();
  
  FullMappings getArgumentTypeMappings();
  
  Mappings getPaintingMappings();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */