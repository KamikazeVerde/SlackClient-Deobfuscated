/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
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
/*    */ public class FloatType
/*    */   extends Type<Float>
/*    */   implements TypeConverter<Float>
/*    */ {
/*    */   public FloatType() {
/* 33 */     super(Float.class);
/*    */   }
/*    */   
/*    */   public float readPrimitive(ByteBuf buffer) {
/* 37 */     return buffer.readFloat();
/*    */   }
/*    */   
/*    */   public void writePrimitive(ByteBuf buffer, float object) {
/* 41 */     buffer.writeFloat(object);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Float read(ByteBuf buffer) {
/* 50 */     return Float.valueOf(buffer.readFloat());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void write(ByteBuf buffer, Float object) {
/* 59 */     buffer.writeFloat(object.floatValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Float from(Object o) {
/* 64 */     if (o instanceof Number)
/* 65 */       return Float.valueOf(((Number)o).floatValue()); 
/* 66 */     if (o instanceof Boolean) {
/* 67 */       return Float.valueOf(((Boolean)o).booleanValue() ? 1.0F : 0.0F);
/*    */     }
/* 69 */     return (Float)o;
/*    */   }
/*    */   
/*    */   public static final class OptionalFloatType
/*    */     extends OptionalType<Float> {
/*    */     public OptionalFloatType() {
/* 75 */       super(Type.FLOAT);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\FloatType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */