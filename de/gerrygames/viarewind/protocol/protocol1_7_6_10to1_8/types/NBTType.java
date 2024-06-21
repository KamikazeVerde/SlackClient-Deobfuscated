/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ import com.viaversion.viaversion.libs.opennbt.NBTIO;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufInputStream;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class NBTType extends Type<CompoundTag> {
/*    */   public NBTType() {
/* 14 */     super(CompoundTag.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag read(ByteBuf buffer) {
/* 19 */     short length = buffer.readShort();
/* 20 */     if (length < 0) return null; 
/* 21 */     ByteBufInputStream byteBufInputStream = new ByteBufInputStream(buffer);
/* 22 */     DataInputStream dataInputStream = new DataInputStream((InputStream)byteBufInputStream);
/*    */     
/* 24 */     try { return NBTIO.readTag(dataInputStream); }
/* 25 */     catch (Throwable throwable) { throwable.printStackTrace(); }
/*    */     finally
/*    */     { 
/* 28 */       try { dataInputStream.close(); }
/* 29 */       catch (IOException e) { e.printStackTrace(); }
/*    */        }
/* 31 */      return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, CompoundTag nbt) throws Exception {
/* 36 */     if (nbt == null) {
/* 37 */       buffer.writeShort(-1);
/*    */     } else {
/* 39 */       ByteBuf buf = buffer.alloc().buffer();
/* 40 */       ByteBufOutputStream bytebufStream = new ByteBufOutputStream(buf);
/* 41 */       DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)bytebufStream);
/* 42 */       NBTIO.writeTag(dataOutputStream, nbt);
/* 43 */       dataOutputStream.close();
/* 44 */       buffer.writeShort(buf.readableBytes());
/* 45 */       buffer.writeBytes(buf);
/* 46 */       buf.release();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\NBTType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */