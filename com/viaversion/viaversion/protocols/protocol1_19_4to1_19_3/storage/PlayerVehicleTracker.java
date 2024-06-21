/*    */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
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
/*    */ public class PlayerVehicleTracker
/*    */   extends StoredObject
/*    */ {
/* 25 */   private int vehicleId = -1;
/*    */   
/*    */   public PlayerVehicleTracker(UserConnection user) {
/* 28 */     super(user);
/*    */   }
/*    */   
/*    */   public int getVehicleId() {
/* 32 */     return this.vehicleId;
/*    */   }
/*    */   
/*    */   public void setVehicleId(int vehicleId) {
/* 36 */     this.vehicleId = vehicleId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\storage\PlayerVehicleTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */