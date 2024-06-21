/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
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
/*    */ public class MovementTracker
/*    */   implements StorableObject
/*    */ {
/*    */   private static final long IDLE_PACKET_DELAY = 50L;
/*    */   private static final long IDLE_PACKET_LIMIT = 20L;
/* 25 */   private long nextIdlePacket = 0L;
/*    */   
/*    */   private boolean ground = true;
/*    */ 
/*    */   
/*    */   public void incrementIdlePacket() {
/* 31 */     this.nextIdlePacket = Math.max(this.nextIdlePacket + 50L, System.currentTimeMillis() - 1000L);
/*    */   }
/*    */   
/*    */   public long getNextIdlePacket() {
/* 35 */     return this.nextIdlePacket;
/*    */   }
/*    */   
/*    */   public boolean isGround() {
/* 39 */     return this.ground;
/*    */   }
/*    */   
/*    */   public void setGround(boolean ground) {
/* 43 */     this.ground = ground;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\storage\MovementTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */