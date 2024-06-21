/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class IntArrayType
/*    */   extends Type<int[]> {
/*    */   public IntArrayType() {
/*  9 */     super(int[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] read(ByteBuf byteBuf) throws Exception {
/* 14 */     byte size = byteBuf.readByte();
/* 15 */     int[] array = new int[size]; byte i;
/* 16 */     for (i = 0; i < size; i = (byte)(i + 1)) {
/* 17 */       array[i] = byteBuf.readInt();
/*    */     }
/* 19 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf byteBuf, int[] array) throws Exception {
/* 24 */     byteBuf.writeByte(array.length);
/* 25 */     for (int i : array) byteBuf.writeInt(i); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\IntArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */