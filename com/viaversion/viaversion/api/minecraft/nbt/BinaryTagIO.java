/*     */ package com.viaversion.viaversion.api.minecraft.nbt;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BinaryTagIO
/*     */ {
/*     */   public static CompoundTag readPath(Path path) throws IOException {
/*  61 */     return readInputStream(Files.newInputStream(path, new java.nio.file.OpenOption[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readInputStream(InputStream input) throws IOException {
/*  72 */     try (DataInputStream dis = new DataInputStream(input)) {
/*  73 */       return readDataInput(dis);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readCompressedPath(Path path) throws IOException {
/*  85 */     return readCompressedInputStream(Files.newInputStream(path, new java.nio.file.OpenOption[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readCompressedInputStream(InputStream input) throws IOException {
/*  96 */     try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(input)))) {
/*  97 */       return readDataInput(dis);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readDataInput(DataInput input) throws IOException {
/* 109 */     byte type = input.readByte();
/* 110 */     if (type != 10) {
/* 111 */       throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", new Object[] { Byte.valueOf(type) }));
/*     */     }
/* 113 */     input.skipBytes(input.readUnsignedShort());
/*     */     
/* 115 */     CompoundTag compoundTag = new CompoundTag();
/* 116 */     compoundTag.read(input);
/* 117 */     return compoundTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writePath(CompoundTag tag, Path path) throws IOException {
/* 128 */     writeOutputStream(tag, Files.newOutputStream(path, new java.nio.file.OpenOption[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeOutputStream(CompoundTag tag, OutputStream output) throws IOException {
/* 139 */     try (DataOutputStream dos = new DataOutputStream(output)) {
/* 140 */       writeDataOutput(tag, dos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCompressedPath(CompoundTag tag, Path path) throws IOException {
/* 152 */     writeCompressedOutputStream(tag, Files.newOutputStream(path, new java.nio.file.OpenOption[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCompressedOutputStream(CompoundTag tag, OutputStream output) throws IOException {
/* 163 */     try (DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(output))) {
/* 164 */       writeDataOutput(tag, dos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDataOutput(CompoundTag tag, DataOutput output) throws IOException {
/* 176 */     output.writeByte(10);
/* 177 */     output.writeUTF("");
/* 178 */     tag.write(output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readString(String input) throws IOException {
/*     */     try {
/* 190 */       CharBuffer buffer = new CharBuffer(input);
/* 191 */       TagStringReader parser = new TagStringReader(buffer);
/* 192 */       CompoundTag tag = parser.compound();
/* 193 */       if (buffer.skipWhitespace().hasMore()) {
/* 194 */         throw new IOException("Document had trailing content after first CompoundTag");
/*     */       }
/* 196 */       return tag;
/* 197 */     } catch (StringTagParseException ex) {
/* 198 */       throw new IOException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String writeString(CompoundTag tag) throws IOException {
/* 210 */     StringBuilder sb = new StringBuilder();
/* 211 */     try (TagStringWriter emit = new TagStringWriter(sb)) {
/* 212 */       emit.writeTag((Tag)tag);
/*     */     } 
/* 214 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\nbt\BinaryTagIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */