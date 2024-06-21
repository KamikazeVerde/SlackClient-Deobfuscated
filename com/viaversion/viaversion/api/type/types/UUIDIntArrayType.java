/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UUIDIntArrayType
/*    */   extends Type<UUID>
/*    */ {
/*    */   public UUIDIntArrayType() {
/* 32 */     super(UUID.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UUID read(ByteBuf buffer) {
/* 41 */     int[] ints = { buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt() };
/*    */     
/* 43 */     return uuidFromIntArray(ints);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, UUID object) {
/* 48 */     int[] ints = uuidToIntArray(object);
/* 49 */     buffer.writeInt(ints[0]);
/* 50 */     buffer.writeInt(ints[1]);
/* 51 */     buffer.writeInt(ints[2]);
/* 52 */     buffer.writeInt(ints[3]);
/*    */   }
/*    */   
/*    */   public static UUID uuidFromIntArray(int[] ints) {
/* 56 */     return new UUID(ints[0] << 32L | ints[1] & 0xFFFFFFFFL, ints[2] << 32L | ints[3] & 0xFFFFFFFFL);
/*    */   }
/*    */   
/*    */   public static int[] uuidToIntArray(UUID uuid) {
/* 60 */     return bitsToIntArray(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
/*    */   }
/*    */   
/*    */   public static int[] bitsToIntArray(long long1, long long2) {
/* 64 */     return new int[] { (int)(long1 >> 32L), (int)long1, (int)(long2 >> 32L), (int)long2 };
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\UUIDIntArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */