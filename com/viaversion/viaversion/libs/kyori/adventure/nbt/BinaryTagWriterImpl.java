/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.DataOutput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
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
/*    */ final class BinaryTagWriterImpl
/*    */   implements BinaryTagIO.Writer
/*    */ {
/* 39 */   static final BinaryTagIO.Writer INSTANCE = new BinaryTagWriterImpl();
/*    */ 
/*    */   
/*    */   public void write(@NotNull CompoundBinaryTag tag, @NotNull Path path, BinaryTagIO.Compression compression) throws IOException {
/* 43 */     OutputStream os = Files.newOutputStream(path, new java.nio.file.OpenOption[0]); 
/* 44 */     try { write(tag, os, compression);
/* 45 */       if (os != null) os.close();  }
/*    */     catch (Throwable throwable) { if (os != null)
/*    */         try { os.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 50 */      } public void write(@NotNull CompoundBinaryTag tag, @NotNull OutputStream output, BinaryTagIO.Compression compression) throws IOException { DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output)))); 
/* 51 */     try { write(tag, dos);
/* 52 */       dos.close(); }
/*    */     catch (Throwable throwable) { try { dos.close(); }
/*    */       catch (Throwable throwable1)
/*    */       { throwable.addSuppressed(throwable1); }
/*    */        throw throwable; }
/* 57 */      } public void write(@NotNull CompoundBinaryTag tag, @NotNull DataOutput output) throws IOException { output.writeByte(BinaryTagTypes.COMPOUND.id());
/* 58 */     output.writeUTF("");
/* 59 */     BinaryTagTypes.COMPOUND.write(tag, output); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeNamed(Map.Entry<String, CompoundBinaryTag> tag, @NotNull Path path, BinaryTagIO.Compression compression) throws IOException {
/* 64 */     OutputStream os = Files.newOutputStream(path, new java.nio.file.OpenOption[0]); 
/* 65 */     try { writeNamed(tag, os, compression);
/* 66 */       if (os != null) os.close();  }
/*    */     catch (Throwable throwable) { if (os != null)
/*    */         try { os.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 71 */      } public void writeNamed(Map.Entry<String, CompoundBinaryTag> tag, @NotNull OutputStream output, BinaryTagIO.Compression compression) throws IOException { DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output)))); 
/* 72 */     try { writeNamed(tag, dos);
/* 73 */       dos.close(); }
/*    */     catch (Throwable throwable) { try { dos.close(); }
/*    */       catch (Throwable throwable1)
/*    */       { throwable.addSuppressed(throwable1); }
/*    */        throw throwable; }
/* 78 */      } public void writeNamed(Map.Entry<String, CompoundBinaryTag> tag, @NotNull DataOutput output) throws IOException { output.writeByte(BinaryTagTypes.COMPOUND.id());
/* 79 */     output.writeUTF(tag.getKey());
/* 80 */     BinaryTagTypes.COMPOUND.write(tag.getValue(), output); }
/*    */ 
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\BinaryTagWriterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */