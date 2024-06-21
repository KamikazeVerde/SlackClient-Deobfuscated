/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.VillagerData;
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
/*    */ public class VillagerDataType
/*    */   extends Type<VillagerData>
/*    */ {
/*    */   public VillagerDataType() {
/* 31 */     super(VillagerData.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public VillagerData read(ByteBuf buffer) throws Exception {
/* 36 */     return new VillagerData(Type.VAR_INT.readPrimitive(buffer), Type.VAR_INT.readPrimitive(buffer), Type.VAR_INT.readPrimitive(buffer));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, VillagerData object) throws Exception {
/* 41 */     Type.VAR_INT.writePrimitive(buffer, object.type());
/* 42 */     Type.VAR_INT.writePrimitive(buffer, object.profession());
/* 43 */     Type.VAR_INT.writePrimitive(buffer, object.level());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\VillagerDataType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */