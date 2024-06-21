/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RedstoneConnectionHandler
/*     */   extends ConnectionHandler
/*     */ {
/*  29 */   private static final IntSet REDSTONE = (IntSet)new IntOpenHashSet();
/*  30 */   private static final Int2IntMap CONNECTED_BLOCK_STATES = (Int2IntMap)new Int2IntOpenHashMap(1296);
/*  31 */   private static final Int2IntMap POWER_MAPPINGS = (Int2IntMap)new Int2IntOpenHashMap(1296);
/*  32 */   private static final int BLOCK_CONNECTION_TYPE_ID = BlockData.connectionTypeId("redstone");
/*     */   
/*     */   static ConnectionData.ConnectorInitAction init() {
/*  35 */     RedstoneConnectionHandler connectionHandler = new RedstoneConnectionHandler();
/*  36 */     String redstoneKey = "minecraft:redstone_wire";
/*  37 */     return blockData -> {
/*     */         if (!"minecraft:redstone_wire".equals(blockData.getMinecraftKey())) {
/*     */           return;
/*     */         }
/*     */         REDSTONE.add(blockData.getSavedBlockStateId());
/*     */         ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
/*     */         CONNECTED_BLOCK_STATES.put(getStates(blockData), blockData.getSavedBlockStateId());
/*     */         POWER_MAPPINGS.put(blockData.getSavedBlockStateId(), Integer.parseInt(blockData.getValue("power")));
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static short getStates(WrappedBlockData data) {
/*  50 */     short b = 0;
/*  51 */     b = (short)(b | getState(data.getValue("east")));
/*  52 */     b = (short)(b | getState(data.getValue("north")) << 2);
/*  53 */     b = (short)(b | getState(data.getValue("south")) << 4);
/*  54 */     b = (short)(b | getState(data.getValue("west")) << 6);
/*  55 */     b = (short)(b | Integer.parseInt(data.getValue("power")) << 8);
/*  56 */     return b;
/*     */   }
/*     */   
/*     */   private static int getState(String value) {
/*  60 */     switch (value) {
/*     */       case "none":
/*  62 */         return 0;
/*     */       case "side":
/*  64 */         return 1;
/*     */       case "up":
/*  66 */         return 2;
/*     */     } 
/*  68 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int connect(UserConnection user, Position position, int blockState) {
/*  74 */     short b = 0;
/*  75 */     b = (short)(b | connects(user, position, BlockFace.EAST));
/*  76 */     b = (short)(b | connects(user, position, BlockFace.NORTH) << 2);
/*  77 */     b = (short)(b | connects(user, position, BlockFace.SOUTH) << 4);
/*  78 */     b = (short)(b | connects(user, position, BlockFace.WEST) << 6);
/*  79 */     b = (short)(b | POWER_MAPPINGS.get(blockState) << 8);
/*  80 */     return CONNECTED_BLOCK_STATES.getOrDefault(b, blockState);
/*     */   }
/*     */   
/*     */   private int connects(UserConnection user, Position position, BlockFace side) {
/*  84 */     Position relative = position.getRelative(side);
/*  85 */     int blockState = getBlockData(user, relative);
/*  86 */     if (connects(side, blockState)) {
/*  87 */       return 1;
/*     */     }
/*  89 */     int up = getBlockData(user, relative.getRelative(BlockFace.TOP));
/*  90 */     if (REDSTONE.contains(up) && !ConnectionData.OCCLUDING_STATES.contains(getBlockData(user, position.getRelative(BlockFace.TOP)))) {
/*  91 */       return 2;
/*     */     }
/*  93 */     int down = getBlockData(user, relative.getRelative(BlockFace.BOTTOM));
/*  94 */     if (REDSTONE.contains(down) && !ConnectionData.OCCLUDING_STATES.contains(getBlockData(user, relative))) {
/*  95 */       return 1;
/*     */     }
/*  97 */     return 0;
/*     */   }
/*     */   
/*     */   private boolean connects(BlockFace side, int blockState) {
/* 101 */     BlockData blockData = (BlockData)ConnectionData.blockConnectionData.get(blockState);
/* 102 */     return (blockData != null && blockData.connectsTo(BLOCK_CONNECTION_TYPE_ID, side.opposite(), false));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\RedstoneConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */