/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.nio.charset.StandardCharsets;
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
/*    */ public class StringType
/*    */   extends Type<String>
/*    */ {
/* 33 */   private static final int maxJavaCharUtf8Length = (Character.toString('￿')
/* 34 */     .getBytes(StandardCharsets.UTF_8)).length;
/*    */   private final int maxLength;
/*    */   
/*    */   public StringType() {
/* 38 */     this(32767);
/*    */   }
/*    */   
/*    */   public StringType(int maxLength) {
/* 42 */     super(String.class);
/* 43 */     this.maxLength = maxLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public String read(ByteBuf buffer) throws Exception {
/* 48 */     int len = Type.VAR_INT.readPrimitive(buffer);
/*    */     
/* 50 */     Preconditions.checkArgument((len <= this.maxLength * maxJavaCharUtf8Length), "Cannot receive string longer than Short.MAX_VALUE * " + maxJavaCharUtf8Length + " bytes (got %s bytes)", new Object[] {
/* 51 */           Integer.valueOf(len)
/*    */         });
/* 53 */     String string = buffer.toString(buffer.readerIndex(), len, StandardCharsets.UTF_8);
/* 54 */     buffer.skipBytes(len);
/*    */     
/* 56 */     Preconditions.checkArgument((string.length() <= this.maxLength), "Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", new Object[] {
/* 57 */           Integer.valueOf(string.length())
/*    */         });
/* 59 */     return string;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, String object) throws Exception {
/* 64 */     Preconditions.checkArgument((object.length() <= this.maxLength), "Cannot send string longer than Short.MAX_VALUE (got %s characters)", new Object[] { Integer.valueOf(object.length()) });
/*    */     
/* 66 */     byte[] b = object.getBytes(StandardCharsets.UTF_8);
/* 67 */     Type.VAR_INT.writePrimitive(buffer, b.length);
/* 68 */     buffer.writeBytes(b);
/*    */   }
/*    */   
/*    */   public static final class OptionalStringType
/*    */     extends OptionalType<String> {
/*    */     public OptionalStringType() {
/* 74 */       super(Type.STRING);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\StringType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */