/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.EulerAngle;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class EulerAngleType
/*    */   extends Type<EulerAngle>
/*    */ {
/*    */   public EulerAngleType() {
/* 31 */     super(EulerAngle.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public EulerAngle read(ByteBuf buffer) throws Exception {
/* 36 */     float x = Type.FLOAT.readPrimitive(buffer);
/* 37 */     float y = Type.FLOAT.readPrimitive(buffer);
/* 38 */     float z = Type.FLOAT.readPrimitive(buffer);
/*    */     
/* 40 */     return new EulerAngle(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, EulerAngle object) throws Exception {
/* 45 */     Type.FLOAT.writePrimitive(buffer, object.x());
/* 46 */     Type.FLOAT.writePrimitive(buffer, object.y());
/* 47 */     Type.FLOAT.writePrimitive(buffer, object.z());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\EulerAngleType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */