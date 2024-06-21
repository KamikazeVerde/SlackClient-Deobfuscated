/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
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
/*    */ public class OptionalVarIntType
/*    */   extends Type<Integer>
/*    */ {
/*    */   public OptionalVarIntType() {
/* 31 */     super(Integer.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer read(ByteBuf buffer) throws Exception {
/* 36 */     int value = Type.VAR_INT.readPrimitive(buffer);
/* 37 */     return (value == 0) ? null : Integer.valueOf(value - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Integer object) throws Exception {
/* 42 */     if (object == null) {
/* 43 */       Type.VAR_INT.writePrimitive(buffer, 0);
/*    */     } else {
/* 45 */       Type.VAR_INT.writePrimitive(buffer, object.intValue() + 1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\OptionalVarIntType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */