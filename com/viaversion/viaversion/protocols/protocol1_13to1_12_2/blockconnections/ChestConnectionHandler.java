/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import java.util.Arrays;
/*    */ import java.util.Locale;
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
/*    */ class ChestConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 31 */   private static final Int2ObjectMap<BlockFace> CHEST_FACINGS = (Int2ObjectMap<BlockFace>)new Int2ObjectOpenHashMap();
/* 32 */   private static final int[] CONNECTED_STATES = new int[32];
/* 33 */   private static final IntSet TRAPPED_CHESTS = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 36 */     Arrays.fill(CONNECTED_STATES, -1);
/* 37 */     ChestConnectionHandler connectionHandler = new ChestConnectionHandler();
/* 38 */     return blockData -> {
/*    */         if (!blockData.getMinecraftKey().equals("minecraft:chest") && !blockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
/*    */           return;
/*    */         }
/*    */         if (blockData.getValue("waterlogged").equals("true")) {
/*    */           return;
/*    */         }
/*    */         CHEST_FACINGS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
/*    */         if (blockData.getMinecraftKey().equalsIgnoreCase("minecraft:trapped_chest")) {
/*    */           TRAPPED_CHESTS.add(blockData.getSavedBlockStateId());
/*    */         }
/*    */         CONNECTED_STATES[getStates(blockData).byteValue()] = blockData.getSavedBlockStateId();
/*    */         ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
/*    */       };
/*    */   }
/*    */   
/*    */   private static Byte getStates(WrappedBlockData blockData) {
/* 55 */     byte states = 0;
/* 56 */     String type = blockData.getValue("type");
/* 57 */     if (type.equals("left")) states = (byte)(states | 0x1); 
/* 58 */     if (type.equals("right")) states = (byte)(states | 0x2); 
/* 59 */     states = (byte)(states | BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)).ordinal() << 2);
/* 60 */     if (blockData.getMinecraftKey().equals("minecraft:trapped_chest")) states = (byte)(states | 0x10); 
/* 61 */     return Byte.valueOf(states);
/*    */   }
/*    */ 
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 66 */     BlockFace facing = (BlockFace)CHEST_FACINGS.get(blockState);
/* 67 */     byte states = 0;
/* 68 */     states = (byte)(states | facing.ordinal() << 2);
/*    */     
/* 70 */     boolean trapped = TRAPPED_CHESTS.contains(blockState);
/* 71 */     if (trapped) {
/* 72 */       states = (byte)(states | 0x10);
/*    */     }
/*    */     
/*    */     int relative;
/* 76 */     if (CHEST_FACINGS.containsKey(relative = getBlockData(user, position.getRelative(BlockFace.NORTH))) && trapped == TRAPPED_CHESTS.contains(relative)) {
/* 77 */       states = (byte)(states | ((facing == BlockFace.WEST) ? 1 : 2));
/* 78 */     } else if (CHEST_FACINGS.containsKey(relative = getBlockData(user, position.getRelative(BlockFace.SOUTH))) && trapped == TRAPPED_CHESTS.contains(relative)) {
/* 79 */       states = (byte)(states | ((facing == BlockFace.EAST) ? 1 : 2));
/* 80 */     } else if (CHEST_FACINGS.containsKey(relative = getBlockData(user, position.getRelative(BlockFace.WEST))) && trapped == TRAPPED_CHESTS.contains(relative)) {
/* 81 */       states = (byte)(states | ((facing == BlockFace.NORTH) ? 2 : 1));
/* 82 */     } else if (CHEST_FACINGS.containsKey(relative = getBlockData(user, position.getRelative(BlockFace.EAST))) && trapped == TRAPPED_CHESTS.contains(relative)) {
/* 83 */       states = (byte)(states | ((facing == BlockFace.SOUTH) ? 2 : 1));
/*    */     } 
/*    */     
/* 86 */     int newBlockState = CONNECTED_STATES[states];
/* 87 */     return (newBlockState == -1) ? blockState : newBlockState;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\ChestConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */