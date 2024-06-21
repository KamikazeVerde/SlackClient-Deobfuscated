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
/*    */ public class BooleanType
/*    */   extends Type<Boolean>
/*    */   implements TypeConverter<Boolean>
/*    */ {
/*    */   public BooleanType() {
/* 31 */     super(Boolean.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean read(ByteBuf buffer) {
/* 36 */     return Boolean.valueOf(buffer.readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Boolean object) {
/* 41 */     buffer.writeBoolean(object.booleanValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean from(Object o) {
/* 47 */     if (o instanceof Number) {
/* 48 */       return Boolean.valueOf((((Number)o).intValue() == 1));
/*    */     }
/* 50 */     return (Boolean)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\BooleanType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */