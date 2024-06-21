/*     */ package com.viaversion.viaversion.libs.opennbt;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
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
/*     */ public final class NBTIO
/*     */ {
/*     */   public static CompoundTag readFile(String path) throws IOException {
/*  35 */     return readFile(new File(path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readFile(File file) throws IOException {
/*  46 */     return readFile(file, true, false);
/*     */   }
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
/*     */   public static CompoundTag readFile(String path, boolean compressed, boolean littleEndian) throws IOException {
/*  59 */     return readFile(new File(path), compressed, littleEndian);
/*     */   }
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
/*     */   public static CompoundTag readFile(File file, boolean compressed, boolean littleEndian) throws IOException {
/*  72 */     InputStream in = Files.newInputStream(file.toPath(), new java.nio.file.OpenOption[0]);
/*     */     try {
/*  74 */       if (compressed) {
/*  75 */         in = new GZIPInputStream(in);
/*     */       }
/*     */       
/*  78 */       CompoundTag tag = readTag(in, littleEndian);
/*  79 */       if (!(tag instanceof CompoundTag)) {
/*  80 */         throw new IOException("Root tag is not a CompoundTag!");
/*     */       }
/*     */       
/*  83 */       return tag;
/*     */     } finally {
/*  85 */       in.close();
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
/*     */   public static void writeFile(CompoundTag tag, String path) throws IOException {
/*  97 */     writeFile(tag, new File(path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeFile(CompoundTag tag, File file) throws IOException {
/* 108 */     writeFile(tag, file, true, false);
/*     */   }
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
/*     */   public static void writeFile(CompoundTag tag, String path, boolean compressed, boolean littleEndian) throws IOException {
/* 121 */     writeFile(tag, new File(path), compressed, littleEndian);
/*     */   }
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
/*     */   public static void writeFile(CompoundTag tag, File file, boolean compressed, boolean littleEndian) throws IOException {
/* 134 */     if (!file.exists()) {
/* 135 */       if (file.getParentFile() != null && !file.getParentFile().exists()) {
/* 136 */         file.getParentFile().mkdirs();
/*     */       }
/*     */       
/* 139 */       file.createNewFile();
/*     */     } 
/*     */     
/* 142 */     OutputStream out = Files.newOutputStream(file.toPath(), new java.nio.file.OpenOption[0]);
/*     */     try {
/* 144 */       if (compressed) {
/* 145 */         out = new GZIPOutputStream(out);
/*     */       }
/*     */       
/* 148 */       writeTag(out, tag, littleEndian);
/*     */     } finally {
/* 150 */       out.close();
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
/*     */   public static CompoundTag readTag(InputStream in) throws IOException {
/* 162 */     return readTag(in, TagLimiter.noop());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readTag(InputStream in, TagLimiter tagLimiter) throws IOException {
/* 173 */     return readTag(new DataInputStream(in), tagLimiter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readTag(InputStream in, boolean littleEndian) throws IOException {
/* 185 */     return readTag(littleEndian ? new LittleEndianDataInputStream(in) : new DataInputStream(in));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readTag(DataInput in) throws IOException {
/* 196 */     return readTag(in, TagLimiter.noop());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag readTag(DataInput in, TagLimiter tagLimiter) throws IOException {
/* 208 */     int id = in.readByte();
/* 209 */     if (id != 10) {
/* 210 */       throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", new Object[] { Integer.valueOf(id) }));
/*     */     }
/*     */ 
/*     */     
/* 214 */     in.skipBytes(in.readUnsignedShort());
/*     */     
/* 216 */     CompoundTag tag = new CompoundTag();
/* 217 */     tag.read(in, tagLimiter);
/* 218 */     return tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeTag(OutputStream out, CompoundTag tag) throws IOException {
/* 229 */     writeTag(out, tag, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeTag(OutputStream out, CompoundTag tag, boolean littleEndian) throws IOException {
/* 241 */     writeTag(littleEndian ? new LittleEndianDataOutputStream(out) : new DataOutputStream(out), tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeTag(DataOutput out, CompoundTag tag) throws IOException {
/* 252 */     out.writeByte(10);
/* 253 */     out.writeUTF("");
/* 254 */     tag.write(out);
/*     */   }
/*     */   
/*     */   private static final class LittleEndianDataInputStream
/*     */     extends FilterInputStream implements DataInput {
/*     */     private LittleEndianDataInputStream(InputStream in) {
/* 260 */       super(in);
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b) throws IOException {
/* 265 */       return this.in.read(b, 0, b.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int len) throws IOException {
/* 271 */       return this.in.read(b, off, len);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFully(byte[] b) throws IOException {
/* 276 */       readFully(b, 0, b.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFully(byte[] b, int off, int len) throws IOException {
/* 281 */       if (len < 0) {
/* 282 */         throw new IndexOutOfBoundsException();
/*     */       }
/*     */       int pos;
/* 285 */       for (pos = 0; pos < len; pos += read) {
/* 286 */         int read = this.in.read(b, off + pos, len - pos);
/* 287 */         if (read < 0) {
/* 288 */           throw new EOFException();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int skipBytes(int n) throws IOException {
/* 296 */       int total = 0;
/* 297 */       int skipped = 0;
/* 298 */       while (total < n && (skipped = (int)this.in.skip((n - total))) > 0) {
/* 299 */         total += skipped;
/*     */       }
/*     */       
/* 302 */       return total;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean readBoolean() throws IOException {
/* 307 */       int val = this.in.read();
/* 308 */       if (val < 0) {
/* 309 */         throw new EOFException();
/*     */       }
/*     */       
/* 312 */       return (val != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte readByte() throws IOException {
/* 317 */       int val = this.in.read();
/* 318 */       if (val < 0) {
/* 319 */         throw new EOFException();
/*     */       }
/*     */       
/* 322 */       return (byte)val;
/*     */     }
/*     */ 
/*     */     
/*     */     public int readUnsignedByte() throws IOException {
/* 327 */       int val = this.in.read();
/* 328 */       if (val < 0) {
/* 329 */         throw new EOFException();
/*     */       }
/*     */       
/* 332 */       return val;
/*     */     }
/*     */ 
/*     */     
/*     */     public short readShort() throws IOException {
/* 337 */       int b1 = this.in.read();
/* 338 */       int b2 = this.in.read();
/* 339 */       if ((b1 | b2) < 0) {
/* 340 */         throw new EOFException();
/*     */       }
/*     */       
/* 343 */       return (short)(b1 | b2 << 8);
/*     */     }
/*     */ 
/*     */     
/*     */     public int readUnsignedShort() throws IOException {
/* 348 */       int b1 = this.in.read();
/* 349 */       int b2 = this.in.read();
/* 350 */       if ((b1 | b2) < 0) {
/* 351 */         throw new EOFException();
/*     */       }
/*     */       
/* 354 */       return b1 | b2 << 8;
/*     */     }
/*     */ 
/*     */     
/*     */     public char readChar() throws IOException {
/* 359 */       int b1 = this.in.read();
/* 360 */       int b2 = this.in.read();
/* 361 */       if ((b1 | b2) < 0) {
/* 362 */         throw new EOFException();
/*     */       }
/*     */       
/* 365 */       return (char)(b1 | b2 << 8);
/*     */     }
/*     */ 
/*     */     
/*     */     public int readInt() throws IOException {
/* 370 */       int b1 = this.in.read();
/* 371 */       int b2 = this.in.read();
/* 372 */       int b3 = this.in.read();
/* 373 */       int b4 = this.in.read();
/* 374 */       if ((b1 | b2 | b3 | b4) < 0) {
/* 375 */         throw new EOFException();
/*     */       }
/*     */       
/* 378 */       return b1 | b2 << 8 | b3 << 16 | b4 << 24;
/*     */     }
/*     */ 
/*     */     
/*     */     public long readLong() throws IOException {
/* 383 */       long b1 = this.in.read();
/* 384 */       long b2 = this.in.read();
/* 385 */       long b3 = this.in.read();
/* 386 */       long b4 = this.in.read();
/* 387 */       long b5 = this.in.read();
/* 388 */       long b6 = this.in.read();
/* 389 */       long b7 = this.in.read();
/* 390 */       long b8 = this.in.read();
/* 391 */       if ((b1 | b2 | b3 | b4 | b5 | b6 | b7 | b8) < 0L) {
/* 392 */         throw new EOFException();
/*     */       }
/*     */       
/* 395 */       return b1 | b2 << 8L | b3 << 16L | b4 << 24L | b5 << 32L | b6 << 40L | b7 << 48L | b8 << 56L;
/*     */     }
/*     */ 
/*     */     
/*     */     public float readFloat() throws IOException {
/* 400 */       return Float.intBitsToFloat(readInt());
/*     */     }
/*     */ 
/*     */     
/*     */     public double readDouble() throws IOException {
/* 405 */       return Double.longBitsToDouble(readLong());
/*     */     }
/*     */ 
/*     */     
/*     */     public String readLine() throws IOException {
/* 410 */       throw new UnsupportedOperationException("Use readUTF.");
/*     */     }
/*     */ 
/*     */     
/*     */     public String readUTF() throws IOException {
/* 415 */       byte[] bytes = new byte[readUnsignedShort()];
/* 416 */       readFully(bytes);
/*     */       
/* 418 */       return new String(bytes, StandardCharsets.UTF_8);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class LittleEndianDataOutputStream
/*     */     extends FilterOutputStream implements DataOutput {
/*     */     private LittleEndianDataOutputStream(OutputStream out) {
/* 425 */       super(out);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void write(int b) throws IOException {
/* 430 */       this.out.write(b);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void write(byte[] b, int off, int len) throws IOException {
/* 435 */       this.out.write(b, off, len);
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/* 440 */       this.out.flush();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeBoolean(boolean b) throws IOException {
/* 445 */       this.out.write(b ? 1 : 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeByte(int b) throws IOException {
/* 450 */       this.out.write(b);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeShort(int s) throws IOException {
/* 455 */       this.out.write(s & 0xFF);
/* 456 */       this.out.write(s >>> 8 & 0xFF);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeChar(int c) throws IOException {
/* 461 */       this.out.write(c & 0xFF);
/* 462 */       this.out.write(c >>> 8 & 0xFF);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeInt(int i) throws IOException {
/* 467 */       this.out.write(i & 0xFF);
/* 468 */       this.out.write(i >>> 8 & 0xFF);
/* 469 */       this.out.write(i >>> 16 & 0xFF);
/* 470 */       this.out.write(i >>> 24 & 0xFF);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeLong(long l) throws IOException {
/* 475 */       this.out.write((int)(l & 0xFFL));
/* 476 */       this.out.write((int)(l >>> 8L & 0xFFL));
/* 477 */       this.out.write((int)(l >>> 16L & 0xFFL));
/* 478 */       this.out.write((int)(l >>> 24L & 0xFFL));
/* 479 */       this.out.write((int)(l >>> 32L & 0xFFL));
/* 480 */       this.out.write((int)(l >>> 40L & 0xFFL));
/* 481 */       this.out.write((int)(l >>> 48L & 0xFFL));
/* 482 */       this.out.write((int)(l >>> 56L & 0xFFL));
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeFloat(float f) throws IOException {
/* 487 */       writeInt(Float.floatToIntBits(f));
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeDouble(double d) throws IOException {
/* 492 */       writeLong(Double.doubleToLongBits(d));
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeBytes(String s) throws IOException {
/* 497 */       int len = s.length();
/* 498 */       for (int index = 0; index < len; index++) {
/* 499 */         this.out.write((byte)s.charAt(index));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeChars(String s) throws IOException {
/* 505 */       int len = s.length();
/* 506 */       for (int index = 0; index < len; index++) {
/* 507 */         char c = s.charAt(index);
/* 508 */         this.out.write(c & 0xFF);
/* 509 */         this.out.write(c >>> 8 & 0xFF);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeUTF(String s) throws IOException {
/* 515 */       byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
/*     */       
/* 517 */       writeShort(bytes.length);
/* 518 */       write(bytes);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\NBTIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */