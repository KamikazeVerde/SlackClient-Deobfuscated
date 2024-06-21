/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.util.Pair;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public class CommandBlockStorage
/*    */   implements StorableObject
/*    */ {
/* 30 */   private final Map<Pair<Integer, Integer>, Map<Position, CompoundTag>> storedCommandBlocks = new ConcurrentHashMap<>();
/*    */   private boolean permissions = false;
/*    */   
/*    */   public void unloadChunk(int x, int z) {
/* 34 */     Pair<Integer, Integer> chunkPos = new Pair(Integer.valueOf(x), Integer.valueOf(z));
/* 35 */     this.storedCommandBlocks.remove(chunkPos);
/*    */   }
/*    */   
/*    */   public void addOrUpdateBlock(Position position, CompoundTag tag) {
/* 39 */     Pair<Integer, Integer> chunkPos = getChunkCoords(position);
/*    */     
/* 41 */     if (!this.storedCommandBlocks.containsKey(chunkPos)) {
/* 42 */       this.storedCommandBlocks.put(chunkPos, new ConcurrentHashMap<>());
/*    */     }
/* 44 */     Map<Position, CompoundTag> blocks = this.storedCommandBlocks.get(chunkPos);
/*    */     
/* 46 */     if (blocks.containsKey(position) && (
/* 47 */       (CompoundTag)blocks.get(position)).equals(tag)) {
/*    */       return;
/*    */     }
/* 50 */     blocks.put(position, tag);
/*    */   }
/*    */   
/*    */   private Pair<Integer, Integer> getChunkCoords(Position position) {
/* 54 */     int chunkX = Math.floorDiv(position.x(), 16);
/* 55 */     int chunkZ = Math.floorDiv(position.z(), 16);
/*    */     
/* 57 */     return new Pair(Integer.valueOf(chunkX), Integer.valueOf(chunkZ));
/*    */   }
/*    */   
/*    */   public Optional<CompoundTag> getCommandBlock(Position position) {
/* 61 */     Pair<Integer, Integer> chunkCoords = getChunkCoords(position);
/*    */     
/* 63 */     Map<Position, CompoundTag> blocks = this.storedCommandBlocks.get(chunkCoords);
/* 64 */     if (blocks == null) {
/* 65 */       return Optional.empty();
/*    */     }
/* 67 */     CompoundTag tag = blocks.get(position);
/* 68 */     if (tag == null) {
/* 69 */       return Optional.empty();
/*    */     }
/* 71 */     tag = tag.clone();
/* 72 */     tag.put("powered", (Tag)new ByteTag((byte)0));
/* 73 */     tag.put("auto", (Tag)new ByteTag((byte)0));
/* 74 */     tag.put("conditionMet", (Tag)new ByteTag((byte)0));
/* 75 */     return Optional.of(tag);
/*    */   }
/*    */   
/*    */   public void unloadChunks() {
/* 79 */     this.storedCommandBlocks.clear();
/*    */   }
/*    */   
/*    */   public boolean isPermissions() {
/* 83 */     return this.permissions;
/*    */   }
/*    */   
/*    */   public void setPermissions(boolean permissions) {
/* 87 */     this.permissions = permissions;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\storage\CommandBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */