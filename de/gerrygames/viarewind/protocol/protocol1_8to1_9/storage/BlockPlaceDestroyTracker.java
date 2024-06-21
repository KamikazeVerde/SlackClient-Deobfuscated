/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ 
/*    */ public class BlockPlaceDestroyTracker
/*    */   extends StoredObject {
/*    */   private long blockPlaced;
/*    */   
/*    */   public BlockPlaceDestroyTracker(UserConnection user) {
/* 11 */     super(user);
/*    */   }
/*    */   private long lastMining; private boolean mining;
/*    */   public long getBlockPlaced() {
/* 15 */     return this.blockPlaced;
/*    */   }
/*    */   
/*    */   public void place() {
/* 19 */     this.blockPlaced = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean isMining() {
/* 23 */     long time = System.currentTimeMillis() - this.lastMining;
/* 24 */     return (time < 75L);
/*    */   }
/*    */   
/*    */   public void setMining(boolean mining) {
/* 28 */     this.mining = (mining && ((EntityTracker)getUser().get(EntityTracker.class)).getPlayerGamemode() != 1);
/* 29 */     this.lastMining = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public long getLastMining() {
/* 33 */     return this.lastMining;
/*    */   }
/*    */   
/*    */   public void updateMining() {
/* 37 */     if (isMining()) {
/* 38 */       this.lastMining = System.currentTimeMillis();
/*    */     }
/*    */   }
/*    */   
/*    */   public void setLastMining(long lastMining) {
/* 43 */     this.lastMining = lastMining;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\BlockPlaceDestroyTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */