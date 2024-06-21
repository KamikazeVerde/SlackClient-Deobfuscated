/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class StairConnectionHandler
/*     */   extends ConnectionHandler
/*     */ {
/*  32 */   private static final Int2ObjectMap<StairData> STAIR_DATA_MAP = (Int2ObjectMap<StairData>)new Int2ObjectOpenHashMap();
/*  33 */   private static final Map<Short, Integer> CONNECTED_BLOCKS = new HashMap<>();
/*     */   
/*     */   static ConnectionData.ConnectorInitAction init() {
/*  36 */     List<String> baseStairs = new LinkedList<>();
/*  37 */     baseStairs.add("minecraft:oak_stairs");
/*  38 */     baseStairs.add("minecraft:cobblestone_stairs");
/*  39 */     baseStairs.add("minecraft:brick_stairs");
/*  40 */     baseStairs.add("minecraft:stone_brick_stairs");
/*  41 */     baseStairs.add("minecraft:nether_brick_stairs");
/*  42 */     baseStairs.add("minecraft:sandstone_stairs");
/*  43 */     baseStairs.add("minecraft:spruce_stairs");
/*  44 */     baseStairs.add("minecraft:birch_stairs");
/*  45 */     baseStairs.add("minecraft:jungle_stairs");
/*  46 */     baseStairs.add("minecraft:quartz_stairs");
/*  47 */     baseStairs.add("minecraft:acacia_stairs");
/*  48 */     baseStairs.add("minecraft:dark_oak_stairs");
/*  49 */     baseStairs.add("minecraft:red_sandstone_stairs");
/*  50 */     baseStairs.add("minecraft:purpur_stairs");
/*  51 */     baseStairs.add("minecraft:prismarine_stairs");
/*  52 */     baseStairs.add("minecraft:prismarine_brick_stairs");
/*  53 */     baseStairs.add("minecraft:dark_prismarine_stairs");
/*     */     
/*  55 */     StairConnectionHandler connectionHandler = new StairConnectionHandler();
/*  56 */     return blockData -> {
/*     */         byte shape;
/*     */         int type = baseStairs.indexOf(blockData.getMinecraftKey());
/*     */         if (type == -1) {
/*     */           return;
/*     */         }
/*     */         if (blockData.getValue("waterlogged").equals("true")) {
/*     */           return;
/*     */         }
/*     */         switch (blockData.getValue("shape")) {
/*     */           case "straight":
/*     */             shape = 0;
/*     */             break;
/*     */           case "inner_left":
/*     */             shape = 1;
/*     */             break;
/*     */           case "inner_right":
/*     */             shape = 2;
/*     */             break;
/*     */           case "outer_left":
/*     */             shape = 3;
/*     */             break;
/*     */           case "outer_right":
/*     */             shape = 4;
/*     */             break;
/*     */           default:
/*     */             return;
/*     */         } 
/*     */         StairData stairData = new StairData(blockData.getValue("half").equals("bottom"), shape, (byte)type, BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
/*     */         STAIR_DATA_MAP.put(blockData.getSavedBlockStateId(), stairData);
/*     */         CONNECTED_BLOCKS.put(Short.valueOf(getStates(stairData)), Integer.valueOf(blockData.getSavedBlockStateId()));
/*     */         ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short getStates(StairData stairData) {
/*  97 */     short s = 0;
/*  98 */     if (stairData.isBottom()) s = (short)(s | 0x1); 
/*  99 */     s = (short)(s | stairData.getShape() << 1);
/* 100 */     s = (short)(s | stairData.getType() << 4);
/* 101 */     s = (short)(s | stairData.getFacing().ordinal() << 9);
/* 102 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int connect(UserConnection user, Position position, int blockState) {
/* 107 */     StairData stairData = (StairData)STAIR_DATA_MAP.get(blockState);
/* 108 */     if (stairData == null) return blockState;
/*     */     
/* 110 */     short s = 0;
/* 111 */     if (stairData.isBottom()) s = (short)(s | 0x1); 
/* 112 */     s = (short)(s | getShape(user, position, stairData) << 1);
/* 113 */     s = (short)(s | stairData.getType() << 4);
/* 114 */     s = (short)(s | stairData.getFacing().ordinal() << 9);
/*     */     
/* 116 */     Integer newBlockState = CONNECTED_BLOCKS.get(Short.valueOf(s));
/* 117 */     return (newBlockState == null) ? blockState : newBlockState.intValue();
/*     */   }
/*     */   
/*     */   private int getShape(UserConnection user, Position position, StairData stair) {
/* 121 */     BlockFace facing = stair.getFacing();
/*     */     
/* 123 */     StairData relativeStair = (StairData)STAIR_DATA_MAP.get(getBlockData(user, position.getRelative(facing)));
/* 124 */     if (relativeStair != null && relativeStair.isBottom() == stair.isBottom()) {
/* 125 */       BlockFace facing2 = relativeStair.getFacing();
/* 126 */       if (facing.axis() != facing2.axis() && checkOpposite(user, stair, position, facing2.opposite())) {
/* 127 */         return (facing2 == rotateAntiClockwise(facing)) ? 3 : 4;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     relativeStair = (StairData)STAIR_DATA_MAP.get(getBlockData(user, position.getRelative(facing.opposite())));
/* 132 */     if (relativeStair != null && relativeStair.isBottom() == stair.isBottom()) {
/* 133 */       BlockFace facing2 = relativeStair.getFacing();
/* 134 */       if (facing.axis() != facing2.axis() && checkOpposite(user, stair, position, facing2)) {
/* 135 */         return (facing2 == rotateAntiClockwise(facing)) ? 1 : 2;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return 0;
/*     */   }
/*     */   
/*     */   private boolean checkOpposite(UserConnection user, StairData stair, Position position, BlockFace face) {
/* 143 */     StairData relativeStair = (StairData)STAIR_DATA_MAP.get(getBlockData(user, position.getRelative(face)));
/* 144 */     return (relativeStair == null || relativeStair.getFacing() != stair.getFacing() || relativeStair.isBottom() != stair.isBottom());
/*     */   }
/*     */   
/*     */   private BlockFace rotateAntiClockwise(BlockFace face) {
/* 148 */     switch (face) {
/*     */       case NORTH:
/* 150 */         return BlockFace.WEST;
/*     */       case SOUTH:
/* 152 */         return BlockFace.EAST;
/*     */       case EAST:
/* 154 */         return BlockFace.NORTH;
/*     */       case WEST:
/* 156 */         return BlockFace.SOUTH;
/*     */     } 
/* 158 */     return face;
/*     */   }
/*     */   
/*     */   private static final class StairData {
/*     */     private final boolean bottom;
/*     */     private final byte shape;
/*     */     private final byte type;
/*     */     private final BlockFace facing;
/*     */     
/*     */     private StairData(boolean bottom, byte shape, byte type, BlockFace facing) {
/* 168 */       this.bottom = bottom;
/* 169 */       this.shape = shape;
/* 170 */       this.type = type;
/* 171 */       this.facing = facing;
/*     */     }
/*     */     
/*     */     public boolean isBottom() {
/* 175 */       return this.bottom;
/*     */     }
/*     */     
/*     */     public byte getShape() {
/* 179 */       return this.shape;
/*     */     }
/*     */     
/*     */     public byte getType() {
/* 183 */       return this.type;
/*     */     }
/*     */     
/*     */     public BlockFace getFacing() {
/* 187 */       return this.facing;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\StairConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */