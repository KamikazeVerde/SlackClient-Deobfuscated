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
/*    */ public class ByteType
/*    */   extends Type<Byte>
/*    */   implements TypeConverter<Byte>
/*    */ {
/*    */   public ByteType() {
/* 31 */     super(Byte.class);
/*    */   }
/*    */   
/*    */   public byte readPrimitive(ByteBuf buffer) {
/* 35 */     return buffer.readByte();
/*    */   }
/*    */   
/*    */   public void writePrimitive(ByteBuf buffer, byte object) {
/* 39 */     buffer.writeByte(object);
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Byte read(ByteBuf buffer) {
/* 45 */     return Byte.valueOf(buffer.readByte());
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Byte object) {
/* 51 */     buffer.writeByte(object.byteValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Byte from(Object o) {
/* 56 */     if (o instanceof Number)
/* 57 */       return Byte.valueOf(((Number)o).byteValue()); 
/* 58 */     if (o instanceof Boolean) {
/* 59 */       return Byte.valueOf(((Boolean)o).booleanValue() ? 1 : 0);
/*    */     }
/* 61 */     return (Byte)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\ByteType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */