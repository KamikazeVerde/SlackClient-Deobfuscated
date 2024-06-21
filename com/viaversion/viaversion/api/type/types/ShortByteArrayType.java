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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShortByteArrayType
/*    */   extends Type<byte[]>
/*    */ {
/*    */   public ShortByteArrayType() {
/* 33 */     super(byte[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, byte[] object) throws Exception {
/* 38 */     buffer.writeShort(object.length);
/* 39 */     buffer.writeBytes(object);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read(ByteBuf buffer) throws Exception {
/* 44 */     byte[] array = new byte[buffer.readShort()];
/* 45 */     buffer.readBytes(array);
/* 46 */     return array;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\ShortByteArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */