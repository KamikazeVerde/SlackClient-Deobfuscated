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
/*    */ public class IntType
/*    */   extends Type<Integer>
/*    */   implements TypeConverter<Integer>
/*    */ {
/*    */   public IntType() {
/* 31 */     super(Integer.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer read(ByteBuf buffer) {
/* 36 */     return Integer.valueOf(buffer.readInt());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Integer object) {
/* 41 */     buffer.writeInt(object.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer from(Object o) {
/* 46 */     if (o instanceof Number)
/* 47 */       return Integer.valueOf(((Number)o).intValue()); 
/* 48 */     if (o instanceof Boolean) {
/* 49 */       return Integer.valueOf(((Boolean)o).booleanValue() ? 1 : 0);
/*    */     }
/* 51 */     return (Integer)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\IntType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */