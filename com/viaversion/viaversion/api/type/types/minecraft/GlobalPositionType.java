/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.GlobalPosition;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
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
/*    */ public class GlobalPositionType
/*    */   extends Type<GlobalPosition>
/*    */ {
/*    */   public GlobalPositionType() {
/* 33 */     super(GlobalPosition.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public GlobalPosition read(ByteBuf buffer) throws Exception {
/* 38 */     String dimension = (String)Type.STRING.read(buffer);
/* 39 */     return ((Position)Type.POSITION1_14.read(buffer)).withDimension(dimension);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, GlobalPosition object) throws Exception {
/* 44 */     Type.STRING.write(buffer, object.dimension());
/* 45 */     Type.POSITION1_14.write(buffer, object);
/*    */   }
/*    */   
/*    */   public static final class OptionalGlobalPositionType
/*    */     extends OptionalType<GlobalPosition> {
/*    */     public OptionalGlobalPositionType() {
/* 51 */       super(Type.GLOBAL_POSITION);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\GlobalPositionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */