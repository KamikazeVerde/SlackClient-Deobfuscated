/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.Quaternion;
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
/*    */ 
/*    */ public class QuaternionType
/*    */   extends Type<Quaternion>
/*    */ {
/*    */   public QuaternionType() {
/* 32 */     super(Quaternion.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Quaternion read(ByteBuf buffer) throws Exception {
/* 37 */     float x = buffer.readFloat();
/* 38 */     float y = buffer.readFloat();
/* 39 */     float z = buffer.readFloat();
/* 40 */     float w = buffer.readFloat();
/* 41 */     return new Quaternion(x, y, z, w);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Quaternion object) throws Exception {
/* 46 */     buffer.writeFloat(object.x());
/* 47 */     buffer.writeFloat(object.y());
/* 48 */     buffer.writeFloat(object.z());
/* 49 */     buffer.writeFloat(object.w());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\QuaternionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */