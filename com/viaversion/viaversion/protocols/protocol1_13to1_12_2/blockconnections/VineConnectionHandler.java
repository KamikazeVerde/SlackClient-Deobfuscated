/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
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
/*    */ class VineConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 27 */   private static final IntSet VINES = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 30 */     VineConnectionHandler connectionHandler = new VineConnectionHandler();
/* 31 */     return blockData -> {
/*    */         if (!blockData.getMinecraftKey().equals("minecraft:vine")) {
/*    */           return;
/*    */         }
/*    */         VINES.add(blockData.getSavedBlockStateId());
/*    */         ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
/*    */       };
/*    */   }
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 41 */     if (isAttachedToBlock(user, position)) return blockState;
/*    */     
/* 43 */     Position upperPos = position.getRelative(BlockFace.TOP);
/* 44 */     int upperBlock = getBlockData(user, upperPos);
/* 45 */     if (VINES.contains(upperBlock) && isAttachedToBlock(user, upperPos)) return blockState;
/*    */ 
/*    */     
/* 48 */     return 0;
/*    */   }
/*    */   
/*    */   private boolean isAttachedToBlock(UserConnection user, Position position) {
/* 52 */     return (isAttachedToBlock(user, position, BlockFace.EAST) || 
/* 53 */       isAttachedToBlock(user, position, BlockFace.WEST) || 
/* 54 */       isAttachedToBlock(user, position, BlockFace.NORTH) || 
/* 55 */       isAttachedToBlock(user, position, BlockFace.SOUTH));
/*    */   }
/*    */   
/*    */   private boolean isAttachedToBlock(UserConnection user, Position position, BlockFace blockFace) {
/* 59 */     return ConnectionData.OCCLUDING_STATES.contains(getBlockData(user, position.getRelative(blockFace)));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\VineConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */