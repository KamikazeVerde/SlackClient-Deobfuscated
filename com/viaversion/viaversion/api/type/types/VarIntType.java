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
/*    */ public class VarIntType
/*    */   extends Type<Integer>
/*    */   implements TypeConverter<Integer>
/*    */ {
/*    */   private static final int CONTINUE_BIT = 128;
/*    */   private static final int VALUE_BITS = 127;
/*    */   private static final int MULTI_BYTE_BITS = -128;
/*    */   private static final int MAX_BYTES = 5;
/*    */   
/*    */   public VarIntType() {
/* 37 */     super("VarInt", Integer.class);
/*    */   }
/*    */   
/*    */   public int readPrimitive(ByteBuf buffer) {
/* 41 */     int value = 0;
/* 42 */     int bytes = 0;
/*    */     
/*    */     while (true) {
/* 45 */       byte in = buffer.readByte();
/* 46 */       value |= (in & Byte.MAX_VALUE) << bytes++ * 7;
/* 47 */       if (bytes > 5) {
/* 48 */         throw new RuntimeException("VarInt too big");
/*    */       }
/*    */       
/* 51 */       if ((in & 0x80) != 128)
/* 52 */         return value; 
/*    */     } 
/*    */   }
/*    */   public void writePrimitive(ByteBuf buffer, int value) {
/* 56 */     while ((value & 0xFFFFFF80) != 0) {
/* 57 */       buffer.writeByte(value & 0x7F | 0x80);
/* 58 */       value >>>= 7;
/*    */     } 
/*    */     
/* 61 */     buffer.writeByte(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Integer read(ByteBuf buffer) {
/* 70 */     return Integer.valueOf(readPrimitive(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Integer object) {
/* 79 */     writePrimitive(buffer, object.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer from(Object o) {
/* 84 */     if (o instanceof Number)
/* 85 */       return Integer.valueOf(((Number)o).intValue()); 
/* 86 */     if (o instanceof Boolean) {
/* 87 */       return Integer.valueOf(((Boolean)o).booleanValue() ? 1 : 0);
/*    */     }
/* 89 */     return (Integer)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\VarIntType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */