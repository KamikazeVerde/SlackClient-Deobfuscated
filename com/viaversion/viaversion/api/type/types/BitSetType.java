/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Arrays;
/*    */ import java.util.BitSet;
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
/*    */ public class BitSetType
/*    */   extends Type<BitSet>
/*    */ {
/*    */   private final int length;
/*    */   private final int bytesLength;
/*    */   
/*    */   public BitSetType(int length) {
/* 37 */     super(BitSet.class);
/* 38 */     this.length = length;
/* 39 */     this.bytesLength = -Math.floorDiv(-length, 8);
/*    */   }
/*    */ 
/*    */   
/*    */   public BitSet read(ByteBuf buffer) {
/* 44 */     byte[] bytes = new byte[this.bytesLength];
/* 45 */     buffer.readBytes(bytes);
/* 46 */     return BitSet.valueOf(bytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, BitSet object) {
/* 51 */     Preconditions.checkArgument((object.length() <= this.length), "BitSet of length " + object.length() + " larger than max length " + this.length);
/* 52 */     byte[] bytes = object.toByteArray();
/* 53 */     buffer.writeBytes(Arrays.copyOf(bytes, this.bytesLength));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\BitSetType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */