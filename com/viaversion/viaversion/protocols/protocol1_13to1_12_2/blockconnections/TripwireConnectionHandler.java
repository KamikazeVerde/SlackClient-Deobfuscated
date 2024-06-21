/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
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
/*     */ public class TripwireConnectionHandler
/*     */   extends ConnectionHandler
/*     */ {
/*  30 */   private static final Int2ObjectMap<TripwireData> TRIPWIRE_DATA_MAP = (Int2ObjectMap<TripwireData>)new Int2ObjectOpenHashMap();
/*  31 */   private static final Int2ObjectMap<BlockFace> TRIPWIRE_HOOKS = (Int2ObjectMap<BlockFace>)new Int2ObjectArrayMap();
/*  32 */   private static final int[] CONNECTED_BLOCKS = new int[128];
/*     */   
/*     */   static ConnectionData.ConnectorInitAction init() {
/*  35 */     Arrays.fill(CONNECTED_BLOCKS, -1);
/*  36 */     TripwireConnectionHandler connectionHandler = new TripwireConnectionHandler();
/*  37 */     return blockData -> {
/*     */         if (blockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
/*     */           TRIPWIRE_HOOKS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
/*     */         } else if (blockData.getMinecraftKey().equals("minecraft:tripwire")) {
/*     */           TripwireData tripwireData = new TripwireData(blockData.getValue("attached").equals("true"), blockData.getValue("disarmed").equals("true"), blockData.getValue("powered").equals("true"));
/*     */           TRIPWIRE_DATA_MAP.put(blockData.getSavedBlockStateId(), tripwireData);
/*     */           CONNECTED_BLOCKS[getStates(blockData)] = blockData.getSavedBlockStateId();
/*     */           ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte getStates(WrappedBlockData blockData) {
/*  56 */     byte b = 0;
/*  57 */     if (blockData.getValue("attached").equals("true")) b = (byte)(b | 0x1); 
/*  58 */     if (blockData.getValue("disarmed").equals("true")) b = (byte)(b | 0x2); 
/*  59 */     if (blockData.getValue("powered").equals("true")) b = (byte)(b | 0x4); 
/*  60 */     if (blockData.getValue("east").equals("true")) b = (byte)(b | 0x8); 
/*  61 */     if (blockData.getValue("north").equals("true")) b = (byte)(b | 0x10); 
/*  62 */     if (blockData.getValue("south").equals("true")) b = (byte)(b | 0x20); 
/*  63 */     if (blockData.getValue("west").equals("true")) b = (byte)(b | 0x40); 
/*  64 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public int connect(UserConnection user, Position position, int blockState) {
/*  69 */     TripwireData tripwireData = (TripwireData)TRIPWIRE_DATA_MAP.get(blockState);
/*  70 */     if (tripwireData == null) return blockState; 
/*  71 */     byte b = 0;
/*  72 */     if (tripwireData.isAttached()) b = (byte)(b | 0x1); 
/*  73 */     if (tripwireData.isDisarmed()) b = (byte)(b | 0x2); 
/*  74 */     if (tripwireData.isPowered()) b = (byte)(b | 0x4);
/*     */     
/*  76 */     int east = getBlockData(user, position.getRelative(BlockFace.EAST));
/*  77 */     int north = getBlockData(user, position.getRelative(BlockFace.NORTH));
/*  78 */     int south = getBlockData(user, position.getRelative(BlockFace.SOUTH));
/*  79 */     int west = getBlockData(user, position.getRelative(BlockFace.WEST));
/*     */     
/*  81 */     if (TRIPWIRE_DATA_MAP.containsKey(east) || TRIPWIRE_HOOKS.get(east) == BlockFace.WEST) {
/*  82 */       b = (byte)(b | 0x8);
/*     */     }
/*  84 */     if (TRIPWIRE_DATA_MAP.containsKey(north) || TRIPWIRE_HOOKS.get(north) == BlockFace.SOUTH) {
/*  85 */       b = (byte)(b | 0x10);
/*     */     }
/*  87 */     if (TRIPWIRE_DATA_MAP.containsKey(south) || TRIPWIRE_HOOKS.get(south) == BlockFace.NORTH) {
/*  88 */       b = (byte)(b | 0x20);
/*     */     }
/*  90 */     if (TRIPWIRE_DATA_MAP.containsKey(west) || TRIPWIRE_HOOKS.get(west) == BlockFace.EAST) {
/*  91 */       b = (byte)(b | 0x40);
/*     */     }
/*     */     
/*  94 */     int newBlockState = CONNECTED_BLOCKS[b];
/*  95 */     return (newBlockState == -1) ? blockState : newBlockState;
/*     */   }
/*     */   
/*     */   private static final class TripwireData {
/*     */     private final boolean attached;
/*     */     
/*     */     private TripwireData(boolean attached, boolean disarmed, boolean powered) {
/* 102 */       this.attached = attached;
/* 103 */       this.disarmed = disarmed;
/* 104 */       this.powered = powered;
/*     */     }
/*     */     private final boolean disarmed; private final boolean powered;
/*     */     public boolean isAttached() {
/* 108 */       return this.attached;
/*     */     }
/*     */     
/*     */     public boolean isDisarmed() {
/* 112 */       return this.disarmed;
/*     */     }
/*     */     
/*     */     public boolean isPowered() {
/* 116 */       return this.powered;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\TripwireConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */