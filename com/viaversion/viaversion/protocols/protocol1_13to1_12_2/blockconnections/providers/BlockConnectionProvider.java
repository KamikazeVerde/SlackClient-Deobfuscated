/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockConnectionProvider
/*    */   implements Provider
/*    */ {
/*    */   public int getBlockData(UserConnection connection, int x, int y, int z) {
/* 27 */     int oldId = getWorldBlockData(connection, x, y, z);
/* 28 */     return Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId);
/*    */   }
/*    */   
/*    */   public int getWorldBlockData(UserConnection connection, int x, int y, int z) {
/* 32 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeBlock(UserConnection connection, int x, int y, int z, int blockState) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeBlock(UserConnection connection, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearStorage(UserConnection connection) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void unloadChunk(UserConnection connection, int x, int z) {}
/*    */ 
/*    */   
/*    */   public void unloadChunkSection(UserConnection connection, int chunkX, int chunkY, int chunkZ) {}
/*    */ 
/*    */   
/*    */   public boolean storesBlocks() {
/* 56 */     return false;
/*    */   }
/*    */   
/*    */   public UserBlockData forUser(UserConnection connection) {
/* 60 */     return (x, y, z) -> getBlockData(connection, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\providers\BlockConnectionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */