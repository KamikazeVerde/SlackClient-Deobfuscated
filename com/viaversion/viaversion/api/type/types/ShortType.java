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
/*    */ 
/*    */ public class ShortType
/*    */   extends Type<Short>
/*    */   implements TypeConverter<Short>
/*    */ {
/*    */   public ShortType() {
/* 32 */     super(Short.class);
/*    */   }
/*    */   
/*    */   public short readPrimitive(ByteBuf buffer) {
/* 36 */     return buffer.readShort();
/*    */   }
/*    */   
/*    */   public void writePrimitive(ByteBuf buffer, short object) {
/* 40 */     buffer.writeShort(object);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Short read(ByteBuf buffer) {
/* 49 */     return Short.valueOf(buffer.readShort());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Short object) {
/* 58 */     buffer.writeShort(object.shortValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Short from(Object o) {
/* 63 */     if (o instanceof Number)
/* 64 */       return Short.valueOf(((Number)o).shortValue()); 
/* 65 */     if (o instanceof Boolean) {
/* 66 */       return Short.valueOf(((Boolean)o).booleanValue() ? 1 : 0);
/*    */     }
/* 68 */     return (Short)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\ShortType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */