/*     */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShoulderTracker
/*     */   extends StoredObject
/*     */ {
/*     */   private int entityId;
/*     */   private String leftShoulder;
/*     */   private String rightShoulder;
/*     */   
/*     */   public ShoulderTracker(UserConnection user) {
/*  36 */     super(user);
/*     */   }
/*     */   
/*     */   public void update() {
/*  40 */     PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_12.CHAT_MESSAGE, null, getUser());
/*     */     
/*  42 */     wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(generateString()));
/*  43 */     wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)2));
/*     */     
/*     */     try {
/*  46 */       wrapper.scheduleSend(Protocol1_11_1To1_12.class);
/*  47 */     } catch (Exception e) {
/*  48 */       ViaBackwards.getPlatform().getLogger().severe("Failed to send the shoulder indication");
/*  49 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String generateString() {
/*  55 */     StringBuilder builder = new StringBuilder();
/*     */ 
/*     */     
/*  58 */     builder.append("  ");
/*  59 */     if (this.leftShoulder == null) {
/*  60 */       builder.append("§4§lNothing");
/*     */     } else {
/*  62 */       builder.append("§2§l").append(getName(this.leftShoulder));
/*     */     } 
/*     */     
/*  65 */     builder.append("§8§l <- §7§lShoulders§8§l -> ");
/*     */     
/*  67 */     if (this.rightShoulder == null) {
/*  68 */       builder.append("§4§lNothing");
/*     */     } else {
/*  70 */       builder.append("§2§l").append(getName(this.rightShoulder));
/*     */     } 
/*     */     
/*  73 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private String getName(String current) {
/*  77 */     if (current.startsWith("minecraft:")) {
/*  78 */       current = current.substring(10);
/*     */     }
/*     */     
/*  81 */     String[] array = current.split("_");
/*  82 */     StringBuilder builder = new StringBuilder();
/*     */     
/*  84 */     for (String s : array) {
/*  85 */       builder.append(s.substring(0, 1).toUpperCase())
/*  86 */         .append(s.substring(1))
/*  87 */         .append(" ");
/*     */     }
/*     */     
/*  90 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/*  94 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public void setEntityId(int entityId) {
/*  98 */     this.entityId = entityId;
/*     */   }
/*     */   
/*     */   public String getLeftShoulder() {
/* 102 */     return this.leftShoulder;
/*     */   }
/*     */   
/*     */   public void setLeftShoulder(String leftShoulder) {
/* 106 */     this.leftShoulder = leftShoulder;
/*     */   }
/*     */   
/*     */   public String getRightShoulder() {
/* 110 */     return this.rightShoulder;
/*     */   }
/*     */   
/*     */   public void setRightShoulder(String rightShoulder) {
/* 114 */     this.rightShoulder = rightShoulder;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     return "ShoulderTracker{entityId=" + this.entityId + ", leftShoulder='" + this.leftShoulder + '\'' + ", rightShoulder='" + this.rightShoulder + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\data\ShoulderTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */