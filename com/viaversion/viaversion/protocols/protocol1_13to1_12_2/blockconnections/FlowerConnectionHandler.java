/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*    */ 
/*    */ public class FlowerConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 31 */   private static final Int2IntMap FLOWERS = (Int2IntMap)new Int2IntOpenHashMap();
/*    */   
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 34 */     Set<String> baseFlower = new HashSet<>();
/* 35 */     baseFlower.add("minecraft:rose_bush");
/* 36 */     baseFlower.add("minecraft:sunflower");
/* 37 */     baseFlower.add("minecraft:peony");
/* 38 */     baseFlower.add("minecraft:tall_grass");
/* 39 */     baseFlower.add("minecraft:large_fern");
/* 40 */     baseFlower.add("minecraft:lilac");
/*    */     
/* 42 */     FlowerConnectionHandler handler = new FlowerConnectionHandler();
/* 43 */     return blockData -> {
/*    */         if (baseFlower.contains(blockData.getMinecraftKey())) {
/*    */           ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
/*    */           if (blockData.getValue("half").equals("lower")) {
/*    */             blockData.set("half", "upper");
/*    */             FLOWERS.put(blockData.getSavedBlockStateId(), blockData.getBlockStateId());
/*    */           } 
/*    */         } 
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 56 */     int blockBelowId = getBlockData(user, position.getRelative(BlockFace.BOTTOM));
/* 57 */     int connectBelow = FLOWERS.get(blockBelowId);
/* 58 */     if (connectBelow != 0) {
/* 59 */       int blockAboveId = getBlockData(user, position.getRelative(BlockFace.TOP));
/* 60 */       if (Via.getConfig().isStemWhenBlockAbove()) {
/* 61 */         if (blockAboveId == 0) {
/* 62 */           return connectBelow;
/*    */         }
/* 64 */       } else if (!FLOWERS.containsKey(blockAboveId)) {
/* 65 */         return connectBelow;
/*    */       } 
/*    */     } 
/* 68 */     return blockState;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\FlowerConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */