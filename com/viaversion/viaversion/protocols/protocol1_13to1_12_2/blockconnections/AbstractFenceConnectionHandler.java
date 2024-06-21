/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import java.util.Arrays;
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
/*    */ public abstract class AbstractFenceConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 29 */   private static final StairConnectionHandler STAIR_CONNECTION_HANDLER = new StairConnectionHandler();
/* 30 */   private final IntSet blockStates = (IntSet)new IntOpenHashSet();
/* 31 */   private final int[] connectedBlockStates = new int[statesSize()];
/*    */   private final int blockConnectionsTypeId;
/*    */   
/*    */   protected AbstractFenceConnectionHandler(String blockConnections) {
/* 35 */     this.blockConnectionsTypeId = (blockConnections != null) ? BlockData.connectionTypeId(blockConnections) : -1;
/* 36 */     Arrays.fill(this.connectedBlockStates, -1);
/*    */   }
/*    */   
/*    */   public ConnectionData.ConnectorInitAction getInitAction(String key) {
/* 40 */     AbstractFenceConnectionHandler handler = this;
/* 41 */     return blockData -> {
/*    */         if (key.equals(blockData.getMinecraftKey())) {
/*    */           if (blockData.hasData("waterlogged") && blockData.getValue("waterlogged").equals("true")) {
/*    */             return;
/*    */           }
/*    */           this.blockStates.add(blockData.getSavedBlockStateId());
/*    */           ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
/*    */           byte internalStateId = getStates(blockData);
/*    */           this.connectedBlockStates[internalStateId] = blockData.getSavedBlockStateId();
/*    */         } 
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte getStates(WrappedBlockData blockData) {
/* 56 */     byte states = 0;
/* 57 */     if (blockData.getValue("east").equals("true")) states = (byte)(states | 0x1); 
/* 58 */     if (blockData.getValue("north").equals("true")) states = (byte)(states | 0x2); 
/* 59 */     if (blockData.getValue("south").equals("true")) states = (byte)(states | 0x4); 
/* 60 */     if (blockData.getValue("west").equals("true")) states = (byte)(states | 0x8); 
/* 61 */     return states;
/*    */   }
/*    */   
/*    */   protected byte getStates(UserConnection user, Position position, int blockState) {
/* 65 */     byte states = 0;
/* 66 */     boolean pre1_12 = (user.getProtocolInfo().getServerProtocolVersion() < ProtocolVersion.v1_12.getVersion());
/* 67 */     if (connects(BlockFace.EAST, getBlockData(user, position.getRelative(BlockFace.EAST)), pre1_12)) states = (byte)(states | 0x1); 
/* 68 */     if (connects(BlockFace.NORTH, getBlockData(user, position.getRelative(BlockFace.NORTH)), pre1_12)) states = (byte)(states | 0x2); 
/* 69 */     if (connects(BlockFace.SOUTH, getBlockData(user, position.getRelative(BlockFace.SOUTH)), pre1_12)) states = (byte)(states | 0x4); 
/* 70 */     if (connects(BlockFace.WEST, getBlockData(user, position.getRelative(BlockFace.WEST)), pre1_12)) states = (byte)(states | 0x8); 
/* 71 */     return states;
/*    */   }
/*    */   
/*    */   protected byte statesSize() {
/* 75 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockData(UserConnection user, Position position) {
/* 80 */     return STAIR_CONNECTION_HANDLER.connect(user, position, super.getBlockData(user, position));
/*    */   }
/*    */ 
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 85 */     int newBlockState = this.connectedBlockStates[getStates(user, position, blockState)];
/* 86 */     return (newBlockState == -1) ? blockState : newBlockState;
/*    */   }
/*    */   
/*    */   protected boolean connects(BlockFace side, int blockState, boolean pre1_12) {
/* 90 */     if (this.blockStates.contains(blockState)) return true; 
/* 91 */     if (this.blockConnectionsTypeId == -1) return false;
/*    */     
/* 93 */     BlockData blockData = (BlockData)ConnectionData.blockConnectionData.get(blockState);
/* 94 */     return (blockData != null && blockData.connectsTo(this.blockConnectionsTypeId, side.opposite(), pre1_12));
/*    */   }
/*    */   
/*    */   public IntSet getBlockStates() {
/* 98 */     return this.blockStates;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\AbstractFenceConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */