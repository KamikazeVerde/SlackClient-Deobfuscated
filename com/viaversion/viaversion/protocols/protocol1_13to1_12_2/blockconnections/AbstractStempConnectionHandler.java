/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
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
/*    */ public abstract class AbstractStempConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 30 */   private static final BlockFace[] BLOCK_FACES = new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
/*    */   
/* 32 */   private final IntSet blockId = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   private final int baseStateId;
/* 35 */   private final Map<BlockFace, Integer> stemps = new EnumMap<>(BlockFace.class);
/*    */   
/*    */   protected AbstractStempConnectionHandler(String baseStateId) {
/* 38 */     this.baseStateId = ConnectionData.getId(baseStateId);
/*    */   }
/*    */   
/*    */   public ConnectionData.ConnectorInitAction getInitAction(String blockId, String toKey) {
/* 42 */     AbstractStempConnectionHandler handler = this;
/* 43 */     return blockData -> {
/*    */         if (blockData.getSavedBlockStateId() == this.baseStateId || blockId.equals(blockData.getMinecraftKey())) {
/*    */           if (blockData.getSavedBlockStateId() != this.baseStateId) {
/*    */             handler.blockId.add(blockData.getSavedBlockStateId());
/*    */           }
/*    */           ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
/*    */         } 
/*    */         if (blockData.getMinecraftKey().equals(toKey)) {
/*    */           String facing = blockData.getValue("facing").toUpperCase(Locale.ROOT);
/*    */           this.stemps.put(BlockFace.valueOf(facing), Integer.valueOf(blockData.getSavedBlockStateId()));
/*    */         } 
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 59 */     if (blockState != this.baseStateId) {
/* 60 */       return blockState;
/*    */     }
/* 62 */     for (BlockFace blockFace : BLOCK_FACES) {
/* 63 */       if (this.blockId.contains(getBlockData(user, position.getRelative(blockFace)))) {
/* 64 */         return ((Integer)this.stemps.get(blockFace)).intValue();
/*    */       }
/*    */     } 
/* 67 */     return this.baseStateId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\AbstractStempConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */