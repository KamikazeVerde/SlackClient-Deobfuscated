/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
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
/*    */ public class RemainingBytesType
/*    */   extends Type<byte[]>
/*    */ {
/*    */   public RemainingBytesType() {
/* 30 */     super(byte[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read(ByteBuf buffer) {
/* 35 */     byte[] array = new byte[buffer.readableBytes()];
/* 36 */     buffer.readBytes(array);
/* 37 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, byte[] object) {
/* 42 */     buffer.writeBytes(object);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\RemainingBytesType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */