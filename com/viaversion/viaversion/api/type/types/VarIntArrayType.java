/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
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
/*    */ public class VarIntArrayType
/*    */   extends Type<int[]>
/*    */ {
/*    */   public VarIntArrayType() {
/* 31 */     super(int[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] read(ByteBuf buffer) throws Exception {
/* 36 */     int length = Type.VAR_INT.readPrimitive(buffer);
/* 37 */     Preconditions.checkArgument(buffer.isReadable(length));
/* 38 */     int[] array = new int[length];
/* 39 */     for (int i = 0; i < array.length; i++) {
/* 40 */       array[i] = Type.VAR_INT.readPrimitive(buffer);
/*    */     }
/* 42 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, int[] object) throws Exception {
/* 47 */     Type.VAR_INT.writePrimitive(buffer, object.length);
/* 48 */     for (int i : object)
/* 49 */       Type.VAR_INT.writePrimitive(buffer, i); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\VarIntArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */