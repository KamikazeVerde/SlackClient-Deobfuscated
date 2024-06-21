/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ 
/*    */ public class BackwardsBlockStorage
/*    */   implements StorableObject
/*    */ {
/* 30 */   private static final IntSet WHITELIST = (IntSet)new IntOpenHashSet(779);
/*    */   
/*    */   static {
/*    */     int i;
/* 34 */     for (i = 5265; i <= 5286; i++) {
/* 35 */       WHITELIST.add(i);
/*    */     }
/*    */ 
/*    */     
/* 39 */     for (i = 0; i < 256; i++) {
/* 40 */       WHITELIST.add(748 + i);
/*    */     }
/*    */ 
/*    */     
/* 44 */     for (i = 6854; i <= 7173; i++) {
/* 45 */       WHITELIST.add(i);
/*    */     }
/*    */ 
/*    */     
/* 49 */     WHITELIST.add(1647);
/*    */ 
/*    */     
/* 52 */     for (i = 5447; i <= 5566; i++) {
/* 53 */       WHITELIST.add(i);
/*    */     }
/*    */ 
/*    */     
/* 57 */     for (i = 1028; i <= 1039; i++) {
/* 58 */       WHITELIST.add(i);
/*    */     }
/* 60 */     for (i = 1047; i <= 1082; i++) {
/* 61 */       WHITELIST.add(i);
/*    */     }
/* 63 */     for (i = 1099; i <= 1110; i++) {
/* 64 */       WHITELIST.add(i);
/*    */     }
/*    */   }
/*    */   
/* 68 */   private final Map<Position, Integer> blocks = new ConcurrentHashMap<>();
/*    */   
/*    */   public void checkAndStore(Position position, int block) {
/* 71 */     if (!WHITELIST.contains(block)) {
/*    */       
/* 73 */       this.blocks.remove(position);
/*    */       
/*    */       return;
/*    */     } 
/* 77 */     this.blocks.put(position, Integer.valueOf(block));
/*    */   }
/*    */   
/*    */   public boolean isWelcome(int block) {
/* 81 */     return WHITELIST.contains(block);
/*    */   }
/*    */   
/*    */   public Integer get(Position position) {
/* 85 */     return this.blocks.get(position);
/*    */   }
/*    */   
/*    */   public int remove(Position position) {
/* 89 */     return ((Integer)this.blocks.remove(position)).intValue();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 93 */     this.blocks.clear();
/*    */   }
/*    */   
/*    */   public Map<Position, Integer> getBlocks() {
/* 97 */     return this.blocks;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\storage\BackwardsBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */