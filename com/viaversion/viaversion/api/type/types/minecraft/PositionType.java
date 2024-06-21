/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
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
/*    */ public class PositionType
/*    */   extends Type<Position>
/*    */ {
/*    */   public PositionType() {
/* 32 */     super(Position.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Position read(ByteBuf buffer) {
/* 37 */     long val = buffer.readLong();
/* 38 */     long x = val >> 38L;
/* 39 */     long y = val >> 26L & 0xFFFL;
/*    */     
/* 41 */     long z = val << 38L >> 38L;
/*    */     
/* 43 */     return new Position((int)x, (short)(int)y, (int)z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Position object) {
/* 48 */     buffer.writeLong((object.x() & 0x3FFFFFFL) << 38L | (object
/* 49 */         .y() & 0xFFFL) << 26L | (object
/* 50 */         .z() & 0x3FFFFFF));
/*    */   }
/*    */   
/*    */   public static final class OptionalPositionType
/*    */     extends OptionalType<Position> {
/*    */     public OptionalPositionType() {
/* 56 */       super(Type.POSITION);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\PositionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */