/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ final class BinaryTagReaderImpl
/*    */   implements BinaryTagIO.Reader
/*    */ {
/*    */   private final long maxBytes;
/* 42 */   static final BinaryTagIO.Reader UNLIMITED = new BinaryTagReaderImpl(-1L);
/* 43 */   static final BinaryTagIO.Reader DEFAULT_LIMIT = new BinaryTagReaderImpl(131082L);
/*    */   
/*    */   BinaryTagReaderImpl(long maxBytes) {
/* 46 */     this.maxBytes = maxBytes;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public CompoundBinaryTag read(@NotNull Path path, BinaryTagIO.Compression compression) throws IOException {
/* 51 */     InputStream is = Files.newInputStream(path, new java.nio.file.OpenOption[0]); 
/* 52 */     try { CompoundBinaryTag compoundBinaryTag = read(is, compression);
/* 53 */       if (is != null) is.close();  return compoundBinaryTag; }
/*    */     catch (Throwable throwable) { if (is != null)
/*    */         try { is.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 58 */      } @NotNull public CompoundBinaryTag read(@NotNull InputStream input, BinaryTagIO.Compression compression) throws IOException { DataInputStream dis = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input)))); 
/* 59 */     try { CompoundBinaryTag compoundBinaryTag = read(dis);
/* 60 */       dis.close(); return compoundBinaryTag; }
/*    */     catch (Throwable throwable) { try { dis.close(); }
/*    */       catch (Throwable throwable1)
/*    */       { throwable.addSuppressed(throwable1); }
/*    */        throw throwable; }
/* 65 */      } @NotNull public CompoundBinaryTag read(@NotNull DataInput input) throws IOException { if (!(input instanceof TrackingDataInput)) {
/* 66 */       input = new TrackingDataInput(input, this.maxBytes);
/*    */     }
/*    */     
/* 69 */     BinaryTagType<? extends BinaryTag> type = BinaryTagType.of(input.readByte());
/* 70 */     requireCompound(type);
/* 71 */     input.skipBytes(input.readUnsignedShort());
/* 72 */     return BinaryTagTypes.COMPOUND.read(input); }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull Path path, BinaryTagIO.Compression compression) throws IOException {
/* 77 */     InputStream is = Files.newInputStream(path, new java.nio.file.OpenOption[0]); 
/* 78 */     try { Map.Entry<String, CompoundBinaryTag> entry = readNamed(is, compression);
/* 79 */       if (is != null) is.close();  return entry; }
/*    */     catch (Throwable throwable) { if (is != null)
/*    */         try { is.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 84 */      } public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull InputStream input, BinaryTagIO.Compression compression) throws IOException { DataInputStream dis = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input)))); 
/* 85 */     try { Map.Entry<String, CompoundBinaryTag> entry = readNamed(dis);
/* 86 */       dis.close(); return entry; }
/*    */     catch (Throwable throwable) { try { dis.close(); }
/*    */       catch (Throwable throwable1)
/*    */       { throwable.addSuppressed(throwable1); }
/*    */        throw throwable; }
/* 91 */      } public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull DataInput input) throws IOException { BinaryTagType<? extends BinaryTag> type = BinaryTagType.of(input.readByte());
/* 92 */     requireCompound(type);
/* 93 */     String name = input.readUTF();
/* 94 */     return new AbstractMap.SimpleImmutableEntry<>(name, BinaryTagTypes.COMPOUND.read(input)); }
/*    */ 
/*    */   
/*    */   private static void requireCompound(BinaryTagType<? extends BinaryTag> type) throws IOException {
/* 98 */     if (type != BinaryTagTypes.COMPOUND)
/* 99 */       throw new IOException(String.format("Expected root tag to be a %s, was %s", new Object[] { BinaryTagTypes.COMPOUND, type })); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\BinaryTagReaderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */