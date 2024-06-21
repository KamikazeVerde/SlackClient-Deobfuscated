/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.Vector3f;
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
/*    */ public class Vector3fType
/*    */   extends Type<Vector3f>
/*    */ {
/*    */   public Vector3fType() {
/* 32 */     super(Vector3f.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector3f read(ByteBuf buffer) throws Exception {
/* 37 */     float x = buffer.readFloat();
/* 38 */     float y = buffer.readFloat();
/* 39 */     float z = buffer.readFloat();
/* 40 */     return new Vector3f(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Vector3f object) throws Exception {
/* 45 */     buffer.writeFloat(object.x());
/* 46 */     buffer.writeFloat(object.y());
/* 47 */     buffer.writeFloat(object.z());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Vector3fType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */