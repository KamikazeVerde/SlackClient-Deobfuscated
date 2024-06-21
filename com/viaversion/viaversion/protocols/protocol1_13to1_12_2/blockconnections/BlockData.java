/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ public final class BlockData
/*    */ {
/* 28 */   private static final List<String> CONNECTION_TYPES = Arrays.asList(new String[] { "fence", "netherFence", "pane", "cobbleWall", "redstone", "allFalseIfStairPre1_12" });
/* 29 */   private static final int MAGIC_STAIRS_ID = connectionTypeId("allFalseIfStairPre1_12");
/* 30 */   private final Int2ObjectMap<boolean[]> connectData = (Int2ObjectMap<boolean[]>)new Int2ObjectArrayMap();
/*    */   
/*    */   public void put(int blockConnectionTypeId, boolean[] booleans) {
/* 33 */     this.connectData.put(blockConnectionTypeId, booleans);
/*    */   }
/*    */   
/*    */   public boolean connectsTo(int blockConnectionTypeId, BlockFace face, boolean pre1_12AbstractFence) {
/* 37 */     if (pre1_12AbstractFence && this.connectData.containsKey(MAGIC_STAIRS_ID)) {
/* 38 */       return false;
/*    */     }
/*    */     
/* 41 */     boolean[] booleans = (boolean[])this.connectData.get(blockConnectionTypeId);
/* 42 */     return (booleans != null && booleans[face.ordinal()]);
/*    */   }
/*    */   
/*    */   public static int connectionTypeId(String blockConnection) {
/* 46 */     int connectionTypeId = CONNECTION_TYPES.indexOf(blockConnection);
/* 47 */     Preconditions.checkArgument((connectionTypeId != -1), "Unknown connection type: " + blockConnection);
/* 48 */     return connectionTypeId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\BlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */