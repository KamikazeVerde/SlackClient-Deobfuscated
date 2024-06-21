/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public class FireConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 29 */   private static final String[] WOOD_TYPES = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };
/* 30 */   private static final int[] CONNECTED_BLOCKS = new int[32];
/* 31 */   private static final IntSet FLAMMABLE_BLOCKS = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   private static void addWoodTypes(Set<String> set, String suffix) {
/* 34 */     for (String woodType : WOOD_TYPES) {
/* 35 */       set.add("minecraft:" + woodType + suffix);
/*    */     }
/*    */   }
/*    */   
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 40 */     Set<String> flammabeIds = new HashSet<>();
/* 41 */     flammabeIds.add("minecraft:tnt");
/* 42 */     flammabeIds.add("minecraft:vine");
/* 43 */     flammabeIds.add("minecraft:bookshelf");
/* 44 */     flammabeIds.add("minecraft:hay_block");
/* 45 */     flammabeIds.add("minecraft:deadbush");
/* 46 */     addWoodTypes(flammabeIds, "_slab");
/* 47 */     addWoodTypes(flammabeIds, "_log");
/* 48 */     addWoodTypes(flammabeIds, "_planks");
/* 49 */     addWoodTypes(flammabeIds, "_leaves");
/* 50 */     addWoodTypes(flammabeIds, "_fence");
/* 51 */     addWoodTypes(flammabeIds, "_fence_gate");
/* 52 */     addWoodTypes(flammabeIds, "_stairs");
/*    */     
/* 54 */     FireConnectionHandler connectionHandler = new FireConnectionHandler();
/* 55 */     return blockData -> {
/*    */         String key = blockData.getMinecraftKey();
/*    */         if (key.contains("_wool") || key.contains("_carpet") || flammabeIds.contains(key)) {
/*    */           FLAMMABLE_BLOCKS.add(blockData.getSavedBlockStateId());
/*    */         } else if (key.equals("minecraft:fire")) {
/*    */           int id = blockData.getSavedBlockStateId();
/*    */           CONNECTED_BLOCKS[getStates(blockData)] = id;
/*    */           ConnectionData.connectionHandlerMap.put(id, connectionHandler);
/*    */         } 
/*    */       };
/*    */   }
/*    */   
/*    */   private static byte getStates(WrappedBlockData blockData) {
/* 68 */     byte states = 0;
/* 69 */     if (blockData.getValue("east").equals("true")) states = (byte)(states | 0x1); 
/* 70 */     if (blockData.getValue("north").equals("true")) states = (byte)(states | 0x2); 
/* 71 */     if (blockData.getValue("south").equals("true")) states = (byte)(states | 0x4); 
/* 72 */     if (blockData.getValue("up").equals("true")) states = (byte)(states | 0x8); 
/* 73 */     if (blockData.getValue("west").equals("true")) states = (byte)(states | 0x10); 
/* 74 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 79 */     byte states = 0;
/* 80 */     if (FLAMMABLE_BLOCKS.contains(getBlockData(user, position.getRelative(BlockFace.EAST)))) states = (byte)(states | 0x1); 
/* 81 */     if (FLAMMABLE_BLOCKS.contains(getBlockData(user, position.getRelative(BlockFace.NORTH)))) states = (byte)(states | 0x2); 
/* 82 */     if (FLAMMABLE_BLOCKS.contains(getBlockData(user, position.getRelative(BlockFace.SOUTH)))) states = (byte)(states | 0x4); 
/* 83 */     if (FLAMMABLE_BLOCKS.contains(getBlockData(user, position.getRelative(BlockFace.TOP)))) states = (byte)(states | 0x8); 
/* 84 */     if (FLAMMABLE_BLOCKS.contains(getBlockData(user, position.getRelative(BlockFace.WEST)))) states = (byte)(states | 0x10); 
/* 85 */     return CONNECTED_BLOCKS[states];
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\FireConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */