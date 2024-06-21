/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
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
/*    */ 
/*    */ public class ByteArrayType
/*    */   extends Type<byte[]>
/*    */ {
/*    */   private final int length;
/*    */   
/*    */   public ByteArrayType(int length) {
/* 35 */     super(byte[].class);
/* 36 */     this.length = length;
/*    */   }
/*    */   
/*    */   public ByteArrayType() {
/* 40 */     super(byte[].class);
/* 41 */     this.length = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, byte[] object) throws Exception {
/* 46 */     if (this.length != -1) {
/* 47 */       Preconditions.checkArgument((this.length == object.length), "Length does not match expected length");
/*    */     } else {
/* 49 */       Type.VAR_INT.writePrimitive(buffer, object.length);
/*    */     } 
/* 51 */     buffer.writeBytes(object);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read(ByteBuf buffer) throws Exception {
/* 56 */     int length = (this.length == -1) ? Type.VAR_INT.readPrimitive(buffer) : this.length;
/* 57 */     Preconditions.checkArgument(buffer.isReadable(length), "Length is fewer than readable bytes");
/* 58 */     byte[] array = new byte[length];
/* 59 */     buffer.readBytes(array);
/* 60 */     return array;
/*    */   }
/*    */   
/*    */   public static final class OptionalByteArrayType
/*    */     extends OptionalType<byte[]> {
/*    */     public OptionalByteArrayType() {
/* 66 */       super(Type.BYTE_ARRAY_PRIMITIVE);
/*    */     }
/*    */     
/*    */     public OptionalByteArrayType(int length) {
/* 70 */       super(new ByteArrayType(length));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\ByteArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */