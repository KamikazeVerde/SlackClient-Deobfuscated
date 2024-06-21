/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.PartialType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class CustomStringType
/*    */   extends PartialType<String[], Integer> {
/*    */   public CustomStringType(Integer param) {
/* 10 */     super(param, String[].class);
/*    */   }
/*    */   
/*    */   public String[] read(ByteBuf buffer, Integer size) throws Exception {
/* 14 */     if (buffer.readableBytes() < size.intValue() / 4) {
/* 15 */       throw new RuntimeException("Readable bytes does not match expected!");
/*    */     }
/* 17 */     String[] array = new String[size.intValue()];
/* 18 */     for (int i = 0; i < size.intValue(); i++) {
/* 19 */       array[i] = (String)Type.STRING.read(buffer);
/*    */     }
/* 21 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Integer size, String[] strings) throws Exception {
/* 26 */     for (String s : strings) Type.STRING.write(buffer, s); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\CustomStringType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */