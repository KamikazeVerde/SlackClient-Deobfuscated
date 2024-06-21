/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.NBTIO;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufInputStream;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
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
/*    */ public class NBTType
/*    */   extends Type<CompoundTag>
/*    */ {
/*    */   private static final int MAX_NBT_BYTES = 2097152;
/*    */   private static final int MAX_NESTING_LEVEL = 512;
/*    */   
/*    */   public NBTType() {
/* 41 */     super(CompoundTag.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag read(ByteBuf buffer) throws Exception {
/* 46 */     int readerIndex = buffer.readerIndex();
/* 47 */     byte b = buffer.readByte();
/* 48 */     if (b == 0) {
/* 49 */       return null;
/*    */     }
/* 51 */     buffer.readerIndex(readerIndex);
/* 52 */     return NBTIO.readTag((DataInput)new ByteBufInputStream(buffer), TagLimiter.create(2097152, 512));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, CompoundTag object) throws Exception {
/* 58 */     if (object == null) {
/* 59 */       buffer.writeByte(0);
/*    */     } else {
/* 61 */       ByteBufOutputStream bytebufStream = new ByteBufOutputStream(buffer);
/* 62 */       NBTIO.writeTag((DataOutput)bytebufStream, object);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\NBTType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */