/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.TypeConverter;
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
/*    */ public class UnsignedByteType
/*    */   extends Type<Short>
/*    */   implements TypeConverter<Short>
/*    */ {
/*    */   public UnsignedByteType() {
/* 31 */     super("Unsigned Byte", Short.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Short read(ByteBuf buffer) {
/* 36 */     return Short.valueOf(buffer.readUnsignedByte());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Short object) {
/* 41 */     buffer.writeByte(object.shortValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Short from(Object o) {
/* 46 */     if (o instanceof Number)
/* 47 */       return Short.valueOf(((Number)o).shortValue()); 
/* 48 */     if (o instanceof Boolean) {
/* 49 */       return Short.valueOf(((Boolean)o).booleanValue() ? 1 : 0);
/*    */     }
/* 51 */     return (Short)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\UnsignedByteType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */