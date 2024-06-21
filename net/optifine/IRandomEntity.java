package net.optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

public interface IRandomEntity {
  int getId();
  
  BlockPos getSpawnPosition();
  
  BiomeGenBase getSpawnBiome();
  
  String getName();
  
  int getHealth();
  
  int getMaxHealth();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\IRandomEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */