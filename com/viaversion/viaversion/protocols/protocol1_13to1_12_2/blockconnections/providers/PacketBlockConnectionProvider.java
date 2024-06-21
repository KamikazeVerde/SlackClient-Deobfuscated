/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
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
/*    */ 
/*    */ public class PacketBlockConnectionProvider
/*    */   extends BlockConnectionProvider
/*    */ {
/*    */   public void storeBlock(UserConnection connection, int x, int y, int z, int blockState) {
/* 27 */     ((BlockConnectionStorage)connection.get(BlockConnectionStorage.class)).store(x, y, z, blockState);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeBlock(UserConnection connection, int x, int y, int z) {
/* 32 */     ((BlockConnectionStorage)connection.get(BlockConnectionStorage.class)).remove(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockData(UserConnection connection, int x, int y, int z) {
/* 37 */     return ((BlockConnectionStorage)connection.get(BlockConnectionStorage.class)).get(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearStorage(UserConnection connection) {
/* 42 */     ((BlockConnectionStorage)connection.get(BlockConnectionStorage.class)).clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void unloadChunk(UserConnection connection, int x, int z) {
/* 47 */     ((BlockConnectionStorage)connection.get(BlockConnectionStorage.class)).unloadChunk(x, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unloadChunkSection(UserConnection connection, int chunkX, int chunkY, int chunkZ) {
/* 52 */     ((BlockConnectionStorage)connection.get(BlockConnectionStorage.class)).unloadSection(chunkX, chunkY, chunkZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean storesBlocks() {
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public UserBlockData forUser(UserConnection connection) {
/* 62 */     BlockConnectionStorage storage = (BlockConnectionStorage)connection.get(BlockConnectionStorage.class);
/* 63 */     return (x, y, z) -> storage.get(x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\providers\PacketBlockConnectionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */