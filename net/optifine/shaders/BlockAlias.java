/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.config.MatchBlock;
/*    */ 
/*    */ 
/*    */ public class BlockAlias
/*    */ {
/*    */   private int blockAliasId;
/*    */   private MatchBlock[] matchBlocks;
/*    */   
/*    */   public BlockAlias(int blockAliasId, MatchBlock[] matchBlocks) {
/* 17 */     this.blockAliasId = blockAliasId;
/* 18 */     this.matchBlocks = matchBlocks;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockAliasId() {
/* 23 */     return this.blockAliasId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(int id, int metadata) {
/* 28 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 30 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 32 */       if (matchblock.matches(id, metadata))
/*    */       {
/* 34 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getMatchBlockIds() {
/* 43 */     Set<Integer> set = new HashSet<>();
/*    */     
/* 45 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 47 */       MatchBlock matchblock = this.matchBlocks[i];
/* 48 */       int j = matchblock.getBlockId();
/* 49 */       set.add(Integer.valueOf(j));
/*    */     } 
/*    */     
/* 52 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 53 */     int[] aint = Config.toPrimitive(ainteger);
/* 54 */     return aint;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchBlock[] getMatchBlocks(int matchBlockId) {
/* 59 */     List<MatchBlock> list = new ArrayList<>();
/*    */     
/* 61 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 63 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 65 */       if (matchblock.getBlockId() == matchBlockId)
/*    */       {
/* 67 */         list.add(matchblock);
/*    */       }
/*    */     } 
/*    */     
/* 71 */     MatchBlock[] amatchblock = list.<MatchBlock>toArray(new MatchBlock[list.size()]);
/* 72 */     return amatchblock;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     return "block." + this.blockAliasId + "=" + Config.arrayToString((Object[])this.matchBlocks);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\BlockAlias.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */