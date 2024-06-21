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
/*    */ public class LongType
/*    */   extends Type<Long>
/*    */   implements TypeConverter<Long>
/*    */ {
/*    */   public LongType() {
/* 32 */     super(Long.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Long read(ByteBuf buffer) {
/* 41 */     return Long.valueOf(buffer.readLong());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Long object) {
/* 50 */     buffer.writeLong(object.longValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Long from(Object o) {
/* 55 */     if (o instanceof Number)
/* 56 */       return Long.valueOf(((Number)o).longValue()); 
/* 57 */     if (o instanceof Boolean) {
/* 58 */       return Long.valueOf(((Boolean)o).booleanValue() ? 1L : 0L);
/*    */     }
/* 60 */     return (Long)o;
/*    */   }
/*    */   
/*    */   public long readPrimitive(ByteBuf buffer) {
/* 64 */     return buffer.readLong();
/*    */   }
/*    */   
/*    */   public void writePrimitive(ByteBuf buffer, long object) {
/* 68 */     buffer.writeLong(object);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\LongType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */