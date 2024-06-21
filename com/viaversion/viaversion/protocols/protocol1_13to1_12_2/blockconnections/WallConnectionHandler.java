/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class WallConnectionHandler
/*    */   extends AbstractFenceConnectionHandler
/*    */ {
/* 27 */   private static final BlockFace[] BLOCK_FACES = new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
/* 28 */   private static final int[] OPPOSITES = new int[] { 3, 2, 1, 0 };
/*    */   
/*    */   static List<ConnectionData.ConnectorInitAction> init() {
/* 31 */     List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(2);
/* 32 */     actions.add((new WallConnectionHandler("cobbleWall")).getInitAction("minecraft:cobblestone_wall"));
/* 33 */     actions.add((new WallConnectionHandler("cobbleWall")).getInitAction("minecraft:mossy_cobblestone_wall"));
/* 34 */     return actions;
/*    */   }
/*    */ 
/*    */   
/*    */   public WallConnectionHandler(String blockConnections) {
/* 39 */     super(blockConnections);
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte getStates(WrappedBlockData blockData) {
/* 44 */     byte states = super.getStates(blockData);
/* 45 */     if (blockData.getValue("up").equals("true")) states = (byte)(states | 0x10); 
/* 46 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte getStates(UserConnection user, Position position, int blockState) {
/* 51 */     byte states = super.getStates(user, position, blockState);
/* 52 */     if (up(user, position)) states = (byte)(states | 0x10); 
/* 53 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte statesSize() {
/* 58 */     return 32;
/*    */   }
/*    */   
/*    */   public boolean up(UserConnection user, Position position) {
/* 62 */     if (isWall(getBlockData(user, position.getRelative(BlockFace.BOTTOM))) || isWall(getBlockData(user, position.getRelative(BlockFace.TOP))))
/* 63 */       return true; 
/* 64 */     int blockFaces = getBlockFaces(user, position);
/* 65 */     if (blockFaces == 0 || blockFaces == 15) return true; 
/* 66 */     for (int i = 0; i < BLOCK_FACES.length; i++) {
/* 67 */       if ((blockFaces & 1 << i) != 0 && (blockFaces & 1 << OPPOSITES[i]) == 0) return true; 
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */   
/*    */   private int getBlockFaces(UserConnection user, Position position) {
/* 73 */     int blockFaces = 0;
/* 74 */     for (int i = 0; i < BLOCK_FACES.length; i++) {
/* 75 */       if (isWall(getBlockData(user, position.getRelative(BLOCK_FACES[i])))) {
/* 76 */         blockFaces |= 1 << i;
/*    */       }
/*    */     } 
/* 79 */     return blockFaces;
/*    */   }
/*    */   
/*    */   private boolean isWall(int id) {
/* 83 */     return getBlockStates().contains(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\WallConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */