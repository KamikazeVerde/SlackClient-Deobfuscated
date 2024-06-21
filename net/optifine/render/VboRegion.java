/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.VboRenderList;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.util.LinkedList;
/*     */ 
/*     */ public class VboRegion
/*     */ {
/*  15 */   private EnumWorldBlockLayer layer = null;
/*  16 */   private int glBufferId = OpenGlHelper.glGenBuffers();
/*  17 */   private int capacity = 4096;
/*  18 */   private int positionTop = 0;
/*     */   private int sizeUsed;
/*  20 */   private LinkedList<VboRange> rangeList = new LinkedList();
/*  21 */   private VboRange compactRangeLast = null;
/*     */   
/*     */   private IntBuffer bufferIndexVertex;
/*     */   private IntBuffer bufferCountVertex;
/*     */   private int drawMode;
/*     */   private final int vertexBytes;
/*     */   
/*     */   public VboRegion(EnumWorldBlockLayer layer) {
/*  29 */     this.bufferIndexVertex = Config.createDirectIntBuffer(this.capacity);
/*  30 */     this.bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
/*  31 */     this.drawMode = 7;
/*  32 */     this.vertexBytes = DefaultVertexFormats.BLOCK.getNextOffset();
/*  33 */     this.layer = layer;
/*  34 */     bindBuffer();
/*  35 */     long i = toBytes(this.capacity);
/*  36 */     OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, i, OpenGlHelper.GL_STATIC_DRAW);
/*  37 */     unbindBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bufferData(ByteBuffer data, VboRange range) {
/*  42 */     int i = range.getPosition();
/*  43 */     int j = range.getSize();
/*  44 */     int k = toVertex(data.limit());
/*     */     
/*  46 */     if (k <= 0) {
/*     */       
/*  48 */       if (i >= 0)
/*     */       {
/*  50 */         range.setPosition(-1);
/*  51 */         range.setSize(0);
/*  52 */         this.rangeList.remove(range.getNode());
/*  53 */         this.sizeUsed -= j;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  58 */       if (k > j) {
/*     */         
/*  60 */         range.setPosition(this.positionTop);
/*  61 */         range.setSize(k);
/*  62 */         this.positionTop += k;
/*     */         
/*  64 */         if (i >= 0)
/*     */         {
/*  66 */           this.rangeList.remove(range.getNode());
/*     */         }
/*     */         
/*  69 */         this.rangeList.addLast(range.getNode());
/*     */       } 
/*     */       
/*  72 */       range.setSize(k);
/*  73 */       this.sizeUsed += k - j;
/*  74 */       checkVboSize(range.getPositionNext());
/*  75 */       long l = toBytes(range.getPosition());
/*  76 */       bindBuffer();
/*  77 */       OpenGlHelper.glBufferSubData(OpenGlHelper.GL_ARRAY_BUFFER, l, data);
/*  78 */       unbindBuffer();
/*     */       
/*  80 */       if (this.positionTop > this.sizeUsed * 11 / 10)
/*     */       {
/*  82 */         compactRanges(1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void compactRanges(int countMax) {
/*  89 */     if (!this.rangeList.isEmpty()) {
/*     */       
/*  91 */       VboRange vborange = this.compactRangeLast;
/*     */       
/*  93 */       if (vborange == null || !this.rangeList.contains(vborange.getNode()))
/*     */       {
/*  95 */         vborange = (VboRange)this.rangeList.getFirst().getItem();
/*     */       }
/*     */       
/*  98 */       int i = vborange.getPosition();
/*  99 */       VboRange vborange1 = vborange.getPrev();
/*     */       
/* 101 */       if (vborange1 == null) {
/*     */         
/* 103 */         i = 0;
/*     */       }
/*     */       else {
/*     */         
/* 107 */         i = vborange1.getPositionNext();
/*     */       } 
/*     */       
/* 110 */       int j = 0;
/*     */       
/* 112 */       while (vborange != null && j < countMax) {
/*     */         
/* 114 */         j++;
/*     */         
/* 116 */         if (vborange.getPosition() == i) {
/*     */           
/* 118 */           i += vborange.getSize();
/* 119 */           vborange = vborange.getNext();
/*     */           
/*     */           continue;
/*     */         } 
/* 123 */         int k = vborange.getPosition() - i;
/*     */         
/* 125 */         if (vborange.getSize() <= k) {
/*     */           
/* 127 */           copyVboData(vborange.getPosition(), i, vborange.getSize());
/* 128 */           vborange.setPosition(i);
/* 129 */           i += vborange.getSize();
/* 130 */           vborange = vborange.getNext();
/*     */           
/*     */           continue;
/*     */         } 
/* 134 */         checkVboSize(this.positionTop + vborange.getSize());
/* 135 */         copyVboData(vborange.getPosition(), this.positionTop, vborange.getSize());
/* 136 */         vborange.setPosition(this.positionTop);
/* 137 */         this.positionTop += vborange.getSize();
/* 138 */         VboRange vborange2 = vborange.getNext();
/* 139 */         this.rangeList.remove(vborange.getNode());
/* 140 */         this.rangeList.addLast(vborange.getNode());
/* 141 */         vborange = vborange2;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 146 */       if (vborange == null)
/*     */       {
/* 148 */         this.positionTop = ((VboRange)this.rangeList.getLast().getItem()).getPositionNext();
/*     */       }
/*     */       
/* 151 */       this.compactRangeLast = vborange;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkRanges() {
/* 157 */     int i = 0;
/* 158 */     int j = 0;
/*     */     
/* 160 */     for (VboRange vborange = (VboRange)this.rangeList.getFirst().getItem(); vborange != null; vborange = vborange.getNext()) {
/*     */       
/* 162 */       i++;
/* 163 */       j += vborange.getSize();
/*     */       
/* 165 */       if (vborange.getPosition() < 0 || vborange.getSize() <= 0 || vborange.getPositionNext() > this.positionTop)
/*     */       {
/* 167 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */       
/* 170 */       VboRange vborange1 = vborange.getPrev();
/*     */       
/* 172 */       if (vborange1 != null && vborange.getPosition() < vborange1.getPositionNext())
/*     */       {
/* 174 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */       
/* 177 */       VboRange vborange2 = vborange.getNext();
/*     */       
/* 179 */       if (vborange2 != null && vborange.getPositionNext() > vborange2.getPosition())
/*     */       {
/* 181 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     if (i != this.rangeList.getSize())
/*     */     {
/* 187 */       throw new RuntimeException("Invalid count: " + i + " <> " + this.rangeList.getSize());
/*     */     }
/* 189 */     if (j != this.sizeUsed)
/*     */     {
/* 191 */       throw new RuntimeException("Invalid size: " + j + " <> " + this.sizeUsed);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkVboSize(int sizeMin) {
/* 197 */     if (this.capacity < sizeMin)
/*     */     {
/* 199 */       expandVbo(sizeMin);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyVboData(int posFrom, int posTo, int size) {
/* 205 */     long i = toBytes(posFrom);
/* 206 */     long j = toBytes(posTo);
/* 207 */     long k = toBytes(size);
/* 208 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
/* 209 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, this.glBufferId);
/* 210 */     OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, i, j, k);
/* 211 */     Config.checkGlError("Copy VBO range");
/* 212 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
/* 213 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void expandVbo(int sizeMin) {
/*     */     int i;
/* 220 */     for (i = this.capacity * 6 / 4; i < sizeMin; i = i * 6 / 4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     long j = toBytes(this.capacity);
/* 226 */     long k = toBytes(i);
/* 227 */     int l = OpenGlHelper.glGenBuffers();
/* 228 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, l);
/* 229 */     OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, k, OpenGlHelper.GL_STATIC_DRAW);
/* 230 */     Config.checkGlError("Expand VBO");
/* 231 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/* 232 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
/* 233 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, l);
/* 234 */     OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, 0L, 0L, j);
/* 235 */     Config.checkGlError("Copy VBO: " + k);
/* 236 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
/* 237 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
/* 238 */     OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 239 */     this.bufferIndexVertex = Config.createDirectIntBuffer(i);
/* 240 */     this.bufferCountVertex = Config.createDirectIntBuffer(i);
/* 241 */     this.glBufferId = l;
/* 242 */     this.capacity = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindBuffer() {
/* 247 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawArrays(int drawMode, VboRange range) {
/* 252 */     if (this.drawMode != drawMode) {
/*     */       
/* 254 */       if (this.bufferIndexVertex.position() > 0)
/*     */       {
/* 256 */         throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + drawMode);
/*     */       }
/*     */       
/* 259 */       this.drawMode = drawMode;
/*     */     } 
/*     */     
/* 262 */     this.bufferIndexVertex.put(range.getPosition());
/* 263 */     this.bufferCountVertex.put(range.getSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishDraw(VboRenderList vboRenderList) {
/* 268 */     bindBuffer();
/* 269 */     vboRenderList.setupArrayPointers();
/* 270 */     this.bufferIndexVertex.flip();
/* 271 */     this.bufferCountVertex.flip();
/* 272 */     GlStateManager.glMultiDrawArrays(this.drawMode, this.bufferIndexVertex, this.bufferCountVertex);
/* 273 */     this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
/* 274 */     this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
/*     */     
/* 276 */     if (this.positionTop > this.sizeUsed * 11 / 10)
/*     */     {
/* 278 */       compactRanges(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindBuffer() {
/* 284 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlBuffers() {
/* 289 */     if (this.glBufferId >= 0) {
/*     */       
/* 291 */       OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 292 */       this.glBufferId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private long toBytes(int vertex) {
/* 298 */     return vertex * this.vertexBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   private int toVertex(long bytes) {
/* 303 */     return (int)(bytes / this.vertexBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPositionTop() {
/* 308 */     return this.positionTop;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\render\VboRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */