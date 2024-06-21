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
/*     */ public class DoorConnectionHandler
/*     */   extends ConnectionHandler
/*     */ {
/*  32 */   private static final Int2ObjectMap<DoorData> DOOR_DATA_MAP = (Int2ObjectMap<DoorData>)new Int2ObjectOpenHashMap();
/*  33 */   private static final Map<Short, Integer> CONNECTED_STATES = new HashMap<>();
/*     */   
/*     */   static ConnectionData.ConnectorInitAction init() {
/*  36 */     List<String> baseDoors = new LinkedList<>();
/*  37 */     baseDoors.add("minecraft:oak_door");
/*  38 */     baseDoors.add("minecraft:birch_door");
/*  39 */     baseDoors.add("minecraft:jungle_door");
/*  40 */     baseDoors.add("minecraft:dark_oak_door");
/*  41 */     baseDoors.add("minecraft:acacia_door");
/*  42 */     baseDoors.add("minecraft:spruce_door");
/*  43 */     baseDoors.add("minecraft:iron_door");
/*     */     
/*  45 */     DoorConnectionHandler connectionHandler = new DoorConnectionHandler();
/*  46 */     return blockData -> {
/*     */         int type = baseDoors.indexOf(blockData.getMinecraftKey());
/*     */         if (type == -1) {
/*     */           return;
/*     */         }
/*     */         int id = blockData.getSavedBlockStateId();
/*     */         DoorData doorData = new DoorData(blockData.getValue("half").equals("lower"), blockData.getValue("hinge").equals("right"), blockData.getValue("powered").equals("true"), blockData.getValue("open").equals("true"), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)), type);
/*     */         DOOR_DATA_MAP.put(id, doorData);
/*     */         CONNECTED_STATES.put(Short.valueOf(getStates(doorData)), Integer.valueOf(id));
/*     */         ConnectionData.connectionHandlerMap.put(id, connectionHandler);
/*     */       };
/*     */   }
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
/*     */   private static short getStates(DoorData doorData) {
/*  70 */     short s = 0;
/*  71 */     if (doorData.isLower()) s = (short)(s | 0x1); 
/*  72 */     if (doorData.isOpen()) s = (short)(s | 0x2); 
/*  73 */     if (doorData.isPowered()) s = (short)(s | 0x4); 
/*  74 */     if (doorData.isRightHinge()) s = (short)(s | 0x8); 
/*  75 */     s = (short)(s | doorData.getFacing().ordinal() << 4);
/*  76 */     s = (short)(s | (doorData.getType() & 0x7) << 6);
/*  77 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int connect(UserConnection user, Position position, int blockState) {
/*  82 */     DoorData doorData = (DoorData)DOOR_DATA_MAP.get(blockState);
/*  83 */     if (doorData == null) return blockState; 
/*  84 */     short s = 0;
/*  85 */     s = (short)(s | (doorData.getType() & 0x7) << 6);
/*  86 */     if (doorData.isLower()) {
/*  87 */       DoorData upperHalf = (DoorData)DOOR_DATA_MAP.get(getBlockData(user, position.getRelative(BlockFace.TOP)));
/*  88 */       if (upperHalf == null) return blockState; 
/*  89 */       s = (short)(s | 0x1);
/*  90 */       if (doorData.isOpen()) s = (short)(s | 0x2); 
/*  91 */       if (upperHalf.isPowered()) s = (short)(s | 0x4); 
/*  92 */       if (upperHalf.isRightHinge()) s = (short)(s | 0x8); 
/*  93 */       s = (short)(s | doorData.getFacing().ordinal() << 4);
/*     */     } else {
/*  95 */       DoorData lowerHalf = (DoorData)DOOR_DATA_MAP.get(getBlockData(user, position.getRelative(BlockFace.BOTTOM)));
/*  96 */       if (lowerHalf == null) return blockState; 
/*  97 */       if (lowerHalf.isOpen()) s = (short)(s | 0x2); 
/*  98 */       if (doorData.isPowered()) s = (short)(s | 0x4); 
/*  99 */       if (doorData.isRightHinge()) s = (short)(s | 0x8); 
/* 100 */       s = (short)(s | lowerHalf.getFacing().ordinal() << 4);
/*     */     } 
/*     */     
/* 103 */     Integer newBlockState = CONNECTED_STATES.get(Short.valueOf(s));
/* 104 */     return (newBlockState == null) ? blockState : newBlockState.intValue();
/*     */   }
/*     */   
/*     */   private static final class DoorData {
/*     */     private final boolean lower;
/*     */     private final boolean rightHinge;
/*     */     private final boolean powered;
/*     */     
/*     */     private DoorData(boolean lower, boolean rightHinge, boolean powered, boolean open, BlockFace facing, int type) {
/* 113 */       this.lower = lower;
/* 114 */       this.rightHinge = rightHinge;
/* 115 */       this.powered = powered;
/* 116 */       this.open = open;
/* 117 */       this.facing = facing;
/* 118 */       this.type = type;
/*     */     }
/*     */     private final boolean open; private final BlockFace facing; private final int type;
/*     */     public boolean isLower() {
/* 122 */       return this.lower;
/*     */     }
/*     */     
/*     */     public boolean isRightHinge() {
/* 126 */       return this.rightHinge;
/*     */     }
/*     */     
/*     */     public boolean isPowered() {
/* 130 */       return this.powered;
/*     */     }
/*     */     
/*     */     public boolean isOpen() {
/* 134 */       return this.open;
/*     */     }
/*     */     
/*     */     public BlockFace getFacing() {
/* 138 */       return this.facing;
/*     */     }
/*     */     
/*     */     public int getType() {
/* 142 */       return this.type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\DoorConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */