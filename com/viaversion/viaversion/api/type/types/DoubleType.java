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
/*    */ public class DoubleType
/*    */   extends Type<Double>
/*    */   implements TypeConverter<Double>
/*    */ {
/*    */   public DoubleType() {
/* 32 */     super(Double.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Double read(ByteBuf buffer) {
/* 41 */     return Double.valueOf(buffer.readDouble());
/*    */   }
/*    */   
/*    */   public double readPrimitive(ByteBuf buffer) {
/* 45 */     return buffer.readDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Double object) {
/* 54 */     buffer.writeDouble(object.doubleValue());
/*    */   }
/*    */   
/*    */   public void writePrimitive(ByteBuf buffer, double object) {
/* 58 */     buffer.writeDouble(object);
/*    */   }
/*    */ 
/*    */   
/*    */   public Double from(Object o) {
/* 63 */     if (o instanceof Number)
/* 64 */       return Double.valueOf(((Number)o).doubleValue()); 
/* 65 */     if (o instanceof Boolean) {
/* 66 */       return Double.valueOf(((Boolean)o).booleanValue() ? 1.0D : 0.0D);
/*    */     }
/* 68 */     return (Double)o;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\DoubleType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */