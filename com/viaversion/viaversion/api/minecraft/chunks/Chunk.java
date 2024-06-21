package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.BitSet;
import java.util.List;

public interface Chunk {
  int getX();
  
  int getZ();
  
  boolean isBiomeData();
  
  boolean isFullChunk();
  
  boolean isIgnoreOldLightData();
  
  void setIgnoreOldLightData(boolean paramBoolean);
  
  int getBitmask();
  
  void setBitmask(int paramInt);
  
  BitSet getChunkMask();
  
  void setChunkMask(BitSet paramBitSet);
  
  ChunkSection[] getSections();
  
  void setSections(ChunkSection[] paramArrayOfChunkSection);
  
  int[] getBiomeData();
  
  void setBiomeData(int[] paramArrayOfint);
  
  CompoundTag getHeightMap();
  
  void setHeightMap(CompoundTag paramCompoundTag);
  
  List<CompoundTag> getBlockEntities();
  
  List<BlockEntity> blockEntities();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\Chunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */