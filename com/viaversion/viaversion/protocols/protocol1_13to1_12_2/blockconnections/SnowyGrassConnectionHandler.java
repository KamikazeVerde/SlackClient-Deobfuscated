/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
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
/*    */ public class SnowyGrassConnectionHandler
/*    */   extends ConnectionHandler
/*    */ {
/* 31 */   private static final Object2IntMap<GrassBlock> GRASS_BLOCKS = (Object2IntMap<GrassBlock>)new Object2IntOpenHashMap();
/* 32 */   private static final IntSet SNOWY_GRASS_BLOCKS = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 35 */     Set<String> snowyGrassBlocks = new HashSet<>();
/* 36 */     snowyGrassBlocks.add("minecraft:grass_block");
/* 37 */     snowyGrassBlocks.add("minecraft:podzol");
/* 38 */     snowyGrassBlocks.add("minecraft:mycelium");
/*    */     
/* 40 */     GRASS_BLOCKS.defaultReturnValue(-1);
/* 41 */     SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
/* 42 */     return blockData -> {
/*    */         if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
/*    */           ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
/*    */           blockData.set("snowy", "true");
/*    */           GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), true), blockData.getBlockStateId());
/*    */           blockData.set("snowy", "false");
/*    */           GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), false), blockData.getBlockStateId());
/*    */         } 
/*    */         if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
/*    */           ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
/*    */           SNOWY_GRASS_BLOCKS.add(blockData.getSavedBlockStateId());
/*    */         } 
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public int connect(UserConnection user, Position position, int blockState) {
/* 59 */     int blockUpId = getBlockData(user, position.getRelative(BlockFace.TOP));
/* 60 */     int newId = GRASS_BLOCKS.getInt(new GrassBlock(blockState, SNOWY_GRASS_BLOCKS.contains(blockUpId)));
/* 61 */     return (newId != -1) ? newId : blockState;
/*    */   }
/*    */   
/*    */   private static final class GrassBlock {
/*    */     private final int blockStateId;
/*    */     private final boolean snowy;
/*    */     
/*    */     private GrassBlock(int blockStateId, boolean snowy) {
/* 69 */       this.blockStateId = blockStateId;
/* 70 */       this.snowy = snowy;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 75 */       if (this == o) return true; 
/* 76 */       if (o == null || getClass() != o.getClass()) return false; 
/* 77 */       GrassBlock that = (GrassBlock)o;
/* 78 */       if (this.blockStateId != that.blockStateId) return false; 
/* 79 */       return (this.snowy == that.snowy);
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 84 */       int result = this.blockStateId;
/* 85 */       result = 31 * result + (this.snowy ? 1 : 0);
/* 86 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\SnowyGrassConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */