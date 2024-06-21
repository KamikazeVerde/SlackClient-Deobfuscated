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
/*    */ public class VarLongType
/*    */   extends Type<Long>
/*    */   implements TypeConverter<Long>
/*    */ {
/*    */   public VarLongType() {
/* 32 */     super("VarLong", Long.class);
/*    */   }
/*    */   
/*    */   public long readPrimitive(ByteBuf buffer) {
/* 36 */     long out = 0L;
/* 37 */     int bytes = 0;
/*    */     
/*    */     while (true) {
/* 40 */       byte in = buffer.readByte();
/*    */       
/* 42 */       out |= (in & Byte.MAX_VALUE) << bytes++ * 7;
/*    */       
/* 44 */       if (bytes > 10) {
/* 45 */         throw new RuntimeException("VarLong too big");
/*    */       }
/* 47 */       if ((in & 0x80) != 128)
/* 48 */         return out; 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void writePrimitive(ByteBuf buffer, long object) {
/*    */     do {
/* 54 */       int part = (int)(object & 0x7FL);
/*    */       
/* 56 */       object >>>= 7L;
/* 57 */       if (object != 0L) {
/* 58 */         part |= 0x80;
/*    */       }
/*    */       
/* 61 */       buffer.writeByte(part);
/* 62 */     } while (object != 0L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Long read(ByteBuf buffer) {
/* 71 */     return Long.valueOf(readPrimitive(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Long object) {
/* 80 */     writePrimitive(buffer, object.longValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Long from(Object o) {
/* 85 */     if (o instanceof Number)
/* 86 */       return Long.valueOf(((Number)o).longValue()); 
/* 87 */     if (o instanceof Boolean) {
/* 88 */       return Long.valueOf(((Boolean)o).booleanValue() ? 1L : 0L);
/*    */     }
/* 90 */     return (Long)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\VarLongType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */