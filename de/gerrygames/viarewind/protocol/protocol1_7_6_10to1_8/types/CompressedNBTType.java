/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.NBTIO;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufInputStream;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ 
/*    */ public class CompressedNBTType extends Type<CompoundTag> {
/*    */   public CompressedNBTType() {
/* 16 */     super(CompoundTag.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag read(ByteBuf buffer) throws IOException {
/* 21 */     short length = buffer.readShort();
/* 22 */     if (length <= 0) {
/* 23 */       return null;
/*    */     }
/*    */     
/* 26 */     ByteBuf compressed = buffer.readSlice(length);
/*    */     
/* 28 */     GZIPInputStream gzipStream = new GZIPInputStream((InputStream)new ByteBufInputStream(compressed)); 
/* 29 */     try { CompoundTag compoundTag = NBTIO.readTag(gzipStream);
/* 30 */       gzipStream.close(); return compoundTag; }
/*    */     catch (Throwable throwable) { try { gzipStream.close(); }
/*    */       catch (Throwable throwable1)
/*    */       { throwable.addSuppressed(throwable1); }
/*    */        throw throwable; }
/* 35 */      } public void write(ByteBuf buffer, CompoundTag nbt) throws Exception { if (nbt == null) {
/* 36 */       buffer.writeShort(-1);
/*    */       
/*    */       return;
/*    */     } 
/* 40 */     ByteBuf compressedBuf = buffer.alloc().buffer();
/*    */     try {
/* 42 */       GZIPOutputStream gzipStream = new GZIPOutputStream((OutputStream)new ByteBufOutputStream(compressedBuf)); 
/* 43 */       try { NBTIO.writeTag(gzipStream, nbt);
/* 44 */         gzipStream.close(); } catch (Throwable throwable) { try { gzipStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */          throw throwable; }
/* 46 */        buffer.writeShort(compressedBuf.readableBytes());
/* 47 */       buffer.writeBytes(compressedBuf);
/*    */     } finally {
/* 49 */       compressedBuf.release();
/*    */     }  }
/*    */ 
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\CompressedNBTType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */