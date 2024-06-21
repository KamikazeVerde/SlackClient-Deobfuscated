package com.viaversion.viaversion.api.minecraft.chunks;

import io.netty.buffer.ByteBuf;

public interface ChunkSectionLight {
  public static final int LIGHT_LENGTH = 2048;
  
  boolean hasSkyLight();
  
  boolean hasBlockLight();
  
  byte[] getSkyLight();
  
  byte[] getBlockLight();
  
  void setSkyLight(byte[] paramArrayOfbyte);
  
  void setBlockLight(byte[] paramArrayOfbyte);
  
  NibbleArray getSkyLightNibbleArray();
  
  NibbleArray getBlockLightNibbleArray();
  
  void readSkyLight(ByteBuf paramByteBuf);
  
  void readBlockLight(ByteBuf paramByteBuf);
  
  void writeSkyLight(ByteBuf paramByteBuf);
  
  void writeBlockLight(ByteBuf paramByteBuf);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\ChunkSectionLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */