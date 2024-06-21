/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class LongArrayType
/*    */   extends Type<long[]>
/*    */ {
/*    */   public LongArrayType() {
/* 31 */     super(long[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public long[] read(ByteBuf buffer) throws Exception {
/* 36 */     int length = Type.VAR_INT.readPrimitive(buffer);
/* 37 */     long[] array = new long[length];
/* 38 */     for (int i = 0; i < array.length; i++) {
/* 39 */       array[i] = buffer.readLong();
/*    */     }
/* 41 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, long[] object) throws Exception {
/* 46 */     Type.VAR_INT.writePrimitive(buffer, object.length);
/* 47 */     for (long l : object)
/* 48 */       buffer.writeLong(l); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\LongArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */