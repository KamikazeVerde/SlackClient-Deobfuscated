/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.util;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Vector;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
/*    */ 
/*    */ public class RelativeMoveUtil {
/*    */   public static Vector[] calculateRelativeMoves(UserConnection user, int entityId, int relX, int relY, int relZ) {
/*    */     int sentRelX, sentRelY, sentRelZ;
/*    */     Vector[] moves;
/* 10 */     EntityTracker tracker = (EntityTracker)user.get(EntityTracker.class);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 15 */     Vector offset = tracker.getEntityOffset(entityId);
/* 16 */     if (offset != null) {
/* 17 */       relX += offset.blockX();
/* 18 */       relY += offset.blockY();
/* 19 */       relZ += offset.blockZ();
/*    */     } 
/*    */     
/* 22 */     if (relX > 32767) {
/* 23 */       x = relX - 32767;
/* 24 */       relX = 32767;
/* 25 */     } else if (relX < -32768) {
/* 26 */       x = relX - -32768;
/* 27 */       relX = -32768;
/*    */     } else {
/* 29 */       x = 0;
/*    */     } 
/*    */     
/* 32 */     if (relY > 32767) {
/* 33 */       y = relY - 32767;
/* 34 */       relY = 32767;
/* 35 */     } else if (relY < -32768) {
/* 36 */       y = relY - -32768;
/* 37 */       relY = -32768;
/*    */     } else {
/* 39 */       y = 0;
/*    */     } 
/*    */     
/* 42 */     if (relZ > 32767) {
/* 43 */       z = relZ - 32767;
/* 44 */       relZ = 32767;
/* 45 */     } else if (relZ < -32768) {
/* 46 */       z = relZ - -32768;
/* 47 */       relZ = -32768;
/*    */     } else {
/* 49 */       z = 0;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     if (relX > 16256 || relX < -16384 || relY > 16256 || relY < -16384 || relZ > 16256 || relZ < -16384) {
/* 56 */       byte relX1 = (byte)(relX / 256);
/* 57 */       byte relX2 = (byte)Math.round((relX - relX1 * 128) / 128.0F);
/* 58 */       byte relY1 = (byte)(relY / 256);
/* 59 */       byte relY2 = (byte)Math.round((relY - relY1 * 128) / 128.0F);
/* 60 */       byte relZ1 = (byte)(relZ / 256);
/* 61 */       byte relZ2 = (byte)Math.round((relZ - relZ1 * 128) / 128.0F);
/*    */       
/* 63 */       sentRelX = relX1 + relX2;
/* 64 */       sentRelY = relY1 + relY2;
/* 65 */       sentRelZ = relZ1 + relZ2;
/*    */       
/* 67 */       moves = new Vector[] { new Vector(relX1, relY1, relZ1), new Vector(relX2, relY2, relZ2) };
/*    */     } else {
/* 69 */       sentRelX = Math.round(relX / 128.0F);
/* 70 */       sentRelY = Math.round(relY / 128.0F);
/* 71 */       sentRelZ = Math.round(relZ / 128.0F);
/*    */       
/* 73 */       moves = new Vector[] { new Vector(sentRelX, sentRelY, sentRelZ) };
/*    */     } 
/*    */     
/* 76 */     int x = x + relX - sentRelX * 128;
/* 77 */     int y = y + relY - sentRelY * 128;
/* 78 */     int z = z + relZ - sentRelZ * 128;
/*    */     
/* 80 */     tracker.setEntityOffset(entityId, new Vector(x, y, z));
/*    */     
/* 82 */     return moves;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_\\util\RelativeMoveUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */