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
/*    */ public class Position1_14Type
/*    */   extends Type<Position>
/*    */ {
/*    */   public Position1_14Type() {
/* 32 */     super(Position.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Position read(ByteBuf buffer) {
/* 37 */     long val = buffer.readLong();
/*    */     
/* 39 */     long x = val >> 38L;
/* 40 */     long y = val << 52L >> 52L;
/* 41 */     long z = val << 26L >> 38L;
/*    */     
/* 43 */     return new Position((int)x, (int)y, (int)z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Position object) {
/* 48 */     buffer.writeLong((object.x() & 0x3FFFFFFL) << 38L | (object
/* 49 */         .y() & 0xFFF) | (object
/* 50 */         .z() & 0x3FFFFFFL) << 12L);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends Type> getBaseClass() {
/* 55 */     return (Class)PositionType.class;
/*    */   }
/*    */   
/*    */   public static final class OptionalPosition1_14Type
/*    */     extends OptionalType<Position> {
/*    */     public OptionalPosition1_14Type() {
/* 61 */       super(Type.POSITION1_14);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\Position1_14Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */