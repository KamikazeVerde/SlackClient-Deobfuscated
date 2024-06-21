/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.Vector;
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
/*    */ public class VectorType
/*    */   extends Type<Vector>
/*    */ {
/*    */   public VectorType() {
/* 31 */     super(Vector.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector read(ByteBuf buffer) throws Exception {
/* 36 */     int x = Type.INT.read(buffer).intValue();
/* 37 */     int y = Type.INT.read(buffer).intValue();
/* 38 */     int z = Type.INT.read(buffer).intValue();
/*    */     
/* 40 */     return new Vector(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Vector object) throws Exception {
/* 45 */     Type.INT.write(buffer, Integer.valueOf(object.blockX()));
/* 46 */     Type.INT.write(buffer, Integer.valueOf(object.blockY()));
/* 47 */     Type.INT.write(buffer, Integer.valueOf(object.blockZ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\VectorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */