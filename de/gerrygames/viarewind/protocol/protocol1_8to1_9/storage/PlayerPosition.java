/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ 
/*     */ public class PlayerPosition extends StoredObject {
/*     */   private double posX;
/*     */   private double posY;
/*     */   private double posZ;
/*  10 */   private int confirmId = -1; private float yaw; private float pitch; private boolean onGround;
/*     */   
/*     */   public PlayerPosition(UserConnection user) {
/*  13 */     super(user);
/*     */   }
/*     */   
/*     */   public void setPos(double x, double y, double z) {
/*  17 */     this.posX = x;
/*  18 */     this.posY = y;
/*  19 */     this.posZ = z;
/*     */   }
/*     */   
/*     */   public void setYaw(float yaw) {
/*  23 */     this.yaw = yaw % 360.0F;
/*     */   }
/*     */   
/*     */   public void setPitch(float pitch) {
/*  27 */     this.pitch = pitch % 360.0F;
/*     */   }
/*     */   
/*     */   public double getPosX() {
/*  31 */     return this.posX;
/*     */   }
/*     */   
/*     */   public double getPosY() {
/*  35 */     return this.posY;
/*     */   }
/*     */   
/*     */   public double getPosZ() {
/*  39 */     return this.posZ;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  43 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  47 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/*  51 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public int getConfirmId() {
/*  55 */     return this.confirmId;
/*     */   }
/*     */   
/*     */   public void setPosX(double posX) {
/*  59 */     this.posX = posX;
/*     */   }
/*     */   
/*     */   public void setPosY(double posY) {
/*  63 */     this.posY = posY;
/*     */   }
/*     */   
/*     */   public void setPosZ(double posZ) {
/*  67 */     this.posZ = posZ;
/*     */   }
/*     */   
/*     */   public void setOnGround(boolean onGround) {
/*  71 */     this.onGround = onGround;
/*     */   }
/*     */   
/*     */   public void setConfirmId(int confirmId) {
/*  75 */     this.confirmId = confirmId;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*  79 */     if (o == this) return true; 
/*  80 */     if (!(o instanceof PlayerPosition)) return false; 
/*  81 */     PlayerPosition other = (PlayerPosition)o;
/*  82 */     if (!other.canEqual(this)) return false; 
/*  83 */     if (Double.compare(getPosX(), other.getPosX()) != 0) return false; 
/*  84 */     if (Double.compare(getPosY(), other.getPosY()) != 0) return false; 
/*  85 */     if (Double.compare(getPosZ(), other.getPosZ()) != 0) return false; 
/*  86 */     if (Float.compare(getYaw(), other.getYaw()) != 0) return false; 
/*  87 */     if (Float.compare(getPitch(), other.getPitch()) != 0) return false; 
/*  88 */     if (isOnGround() != other.isOnGround()) return false; 
/*  89 */     return (getConfirmId() == other.getConfirmId());
/*     */   }
/*     */   
/*     */   protected boolean canEqual(Object other) {
/*  93 */     return other instanceof PlayerPosition;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  97 */     int PRIME = 59;
/*  98 */     int result = 1;
/*  99 */     long $posX = Double.doubleToLongBits(getPosX());
/* 100 */     result = result * 59 + (int)($posX >>> 32L ^ $posX);
/* 101 */     long $posY = Double.doubleToLongBits(getPosY());
/* 102 */     result = result * 59 + (int)($posY >>> 32L ^ $posY);
/* 103 */     long $posZ = Double.doubleToLongBits(getPosZ());
/* 104 */     result = result * 59 + (int)($posZ >>> 32L ^ $posZ);
/* 105 */     result = result * 59 + Float.floatToIntBits(getYaw());
/* 106 */     result = result * 59 + Float.floatToIntBits(getPitch());
/* 107 */     result = result * 59 + (isOnGround() ? 79 : 97);
/* 108 */     result = result * 59 + getConfirmId();
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 113 */     return "PlayerPosition(posX=" + getPosX() + ", posY=" + getPosY() + ", posZ=" + getPosZ() + ", yaw=" + getYaw() + ", pitch=" + getPitch() + ", onGround=" + isOnGround() + ", confirmId=" + getConfirmId() + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\PlayerPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */