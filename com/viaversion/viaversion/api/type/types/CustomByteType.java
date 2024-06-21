/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.PartialType;
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
/*    */ public class CustomByteType
/*    */   extends PartialType<byte[], Integer>
/*    */ {
/*    */   public CustomByteType(Integer param) {
/* 31 */     super(param, byte[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read(ByteBuf byteBuf, Integer integer) throws Exception {
/* 36 */     if (byteBuf.readableBytes() < integer.intValue()) throw new RuntimeException("Readable bytes does not match expected!");
/*    */     
/* 38 */     byte[] byteArray = new byte[integer.intValue()];
/* 39 */     byteBuf.readBytes(byteArray);
/*    */     
/* 41 */     return byteArray;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf byteBuf, Integer integer, byte[] bytes) throws Exception {
/* 46 */     byteBuf.writeBytes(bytes);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\CustomByteType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */