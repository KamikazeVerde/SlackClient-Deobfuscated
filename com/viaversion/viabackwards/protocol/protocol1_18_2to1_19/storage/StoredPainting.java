/*    */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import java.util.UUID;
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
/*    */ public final class StoredPainting
/*    */   implements StorableObject
/*    */ {
/*    */   private final int entityId;
/*    */   private final UUID uuid;
/*    */   private final Position position;
/*    */   private final byte direction;
/*    */   
/*    */   public StoredPainting(int entityId, UUID uuid, Position position, int direction3d) {
/* 32 */     this.entityId = entityId;
/* 33 */     this.uuid = uuid;
/* 34 */     this.position = position;
/* 35 */     this.direction = to2dDirection(direction3d);
/*    */   }
/*    */   
/*    */   public int entityId() {
/* 39 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public UUID uuid() {
/* 43 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public Position position() {
/* 47 */     return this.position;
/*    */   }
/*    */   
/*    */   public byte direction() {
/* 51 */     return this.direction;
/*    */   }
/*    */   
/*    */   private byte to2dDirection(int direction) {
/* 55 */     switch (direction) {
/*    */       case 0:
/*    */       case 1:
/* 58 */         return -1;
/*    */       case 2:
/* 60 */         return 2;
/*    */       case 3:
/* 62 */         return 0;
/*    */       case 4:
/* 64 */         return 1;
/*    */       case 5:
/* 66 */         return 3;
/*    */     } 
/* 68 */     throw new IllegalArgumentException("Invalid direction: " + direction);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\storage\StoredPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */