/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ 
/*    */ public class PlayerPosition
/*    */   extends StoredObject {
/*    */   private double posX;
/*    */   private double posY;
/*    */   private double posZ;
/*    */   private float yaw;
/*    */   
/*    */   public PlayerPosition(UserConnection user) {
/* 14 */     super(user);
/*    */   }
/*    */   private float pitch; private boolean onGround; private boolean positionPacketReceived; private double receivedPosY;
/*    */   public void setPos(double x, double y, double z) {
/* 18 */     this.posX = x;
/* 19 */     this.posY = y;
/* 20 */     this.posZ = z;
/*    */   }
/*    */   
/*    */   public boolean isPositionPacketReceived() {
/* 24 */     return this.positionPacketReceived;
/*    */   }
/*    */   
/*    */   public void setPositionPacketReceived(boolean positionPacketReceived) {
/* 28 */     this.positionPacketReceived = positionPacketReceived;
/*    */   }
/*    */   
/*    */   public double getReceivedPosY() {
/* 32 */     return this.receivedPosY;
/*    */   }
/*    */   
/*    */   public void setReceivedPosY(double receivedPosY) {
/* 36 */     this.receivedPosY = receivedPosY;
/*    */   }
/*    */   
/*    */   public double getPosX() {
/* 40 */     return this.posX;
/*    */   }
/*    */   
/*    */   public void setPosX(double posX) {
/* 44 */     this.posX = posX;
/*    */   }
/*    */   
/*    */   public double getPosY() {
/* 48 */     return this.posY;
/*    */   }
/*    */   
/*    */   public void setPosY(double posY) {
/* 52 */     this.posY = posY;
/*    */   }
/*    */   
/*    */   public double getPosZ() {
/* 56 */     return this.posZ;
/*    */   }
/*    */   
/*    */   public void setPosZ(double posZ) {
/* 60 */     this.posZ = posZ;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 64 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw) {
/* 68 */     this.yaw = yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 72 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public void setPitch(float pitch) {
/* 76 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public boolean isOnGround() {
/* 80 */     return this.onGround;
/*    */   }
/*    */   
/*    */   public void setOnGround(boolean onGround) {
/* 84 */     this.onGround = onGround;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\PlayerPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */