/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ 
/*     */ public class PlayerAbilities extends StoredObject {
/*     */   private boolean sprinting;
/*     */   private boolean allowFly;
/*     */   private boolean flying;
/*     */   
/*     */   public PlayerAbilities(UserConnection user) {
/*  11 */     super(user);
/*     */   }
/*     */   private boolean invincible; private boolean creative; private float flySpeed; private float walkSpeed;
/*     */   public byte getFlags() {
/*  15 */     byte flags = 0;
/*  16 */     if (this.invincible) flags = (byte)(flags | 0x8); 
/*  17 */     if (this.allowFly) flags = (byte)(flags | 0x4); 
/*  18 */     if (this.flying) flags = (byte)(flags | 0x2); 
/*  19 */     if (this.creative) flags = (byte)(flags | 0x1); 
/*  20 */     return flags;
/*     */   }
/*     */   
/*     */   public boolean isSprinting() {
/*  24 */     return this.sprinting;
/*     */   }
/*     */   
/*     */   public boolean isAllowFly() {
/*  28 */     return this.allowFly;
/*     */   }
/*     */   
/*     */   public boolean isFlying() {
/*  32 */     return this.flying;
/*     */   }
/*     */   
/*     */   public boolean isInvincible() {
/*  36 */     return this.invincible;
/*     */   }
/*     */   
/*     */   public boolean isCreative() {
/*  40 */     return this.creative;
/*     */   }
/*     */   
/*     */   public float getFlySpeed() {
/*  44 */     return this.flySpeed;
/*     */   }
/*     */   
/*     */   public float getWalkSpeed() {
/*  48 */     return this.walkSpeed;
/*     */   }
/*     */   
/*     */   public void setSprinting(boolean sprinting) {
/*  52 */     this.sprinting = sprinting;
/*     */   }
/*     */   
/*     */   public void setAllowFly(boolean allowFly) {
/*  56 */     this.allowFly = allowFly;
/*     */   }
/*     */   
/*     */   public void setFlying(boolean flying) {
/*  60 */     this.flying = flying;
/*     */   }
/*     */   
/*     */   public void setInvincible(boolean invincible) {
/*  64 */     this.invincible = invincible;
/*     */   }
/*     */   
/*     */   public void setCreative(boolean creative) {
/*  68 */     this.creative = creative;
/*     */   }
/*     */   
/*     */   public void setFlySpeed(float flySpeed) {
/*  72 */     this.flySpeed = flySpeed;
/*     */   }
/*     */   
/*     */   public void setWalkSpeed(float walkSpeed) {
/*  76 */     this.walkSpeed = walkSpeed;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*  80 */     if (o == this) return true; 
/*  81 */     if (!(o instanceof PlayerAbilities))
/*  82 */       return false; 
/*  83 */     PlayerAbilities other = (PlayerAbilities)o;
/*  84 */     if (!other.canEqual(this)) return false; 
/*  85 */     if (isSprinting() != other.isSprinting()) return false; 
/*  86 */     if (isAllowFly() != other.isAllowFly()) return false; 
/*  87 */     if (isFlying() != other.isFlying()) return false; 
/*  88 */     if (isInvincible() != other.isInvincible()) return false; 
/*  89 */     if (isCreative() != other.isCreative()) return false; 
/*  90 */     if (Float.compare(getFlySpeed(), other.getFlySpeed()) != 0) return false; 
/*  91 */     return (Float.compare(getWalkSpeed(), other.getWalkSpeed()) == 0);
/*     */   }
/*     */   
/*     */   protected boolean canEqual(Object other) {
/*  95 */     return other instanceof PlayerAbilities;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  99 */     int PRIME = 59;
/* 100 */     int result = 1;
/* 101 */     result = result * 59 + (isSprinting() ? 79 : 97);
/* 102 */     result = result * 59 + (isAllowFly() ? 79 : 97);
/* 103 */     result = result * 59 + (isFlying() ? 79 : 97);
/* 104 */     result = result * 59 + (isInvincible() ? 79 : 97);
/* 105 */     result = result * 59 + (isCreative() ? 79 : 97);
/* 106 */     result = result * 59 + Float.floatToIntBits(getFlySpeed());
/* 107 */     result = result * 59 + Float.floatToIntBits(getWalkSpeed());
/* 108 */     return result;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 112 */     return "PlayerAbilities(sprinting=" + isSprinting() + ", allowFly=" + isAllowFly() + ", flying=" + isFlying() + ", invincible=" + isInvincible() + ", creative=" + isCreative() + ", flySpeed=" + getFlySpeed() + ", walkSpeed=" + getWalkSpeed() + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\PlayerAbilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */