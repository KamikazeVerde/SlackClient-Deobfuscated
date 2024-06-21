/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ public class SVertexBuilder
/*     */ {
/*     */   int vertexSize;
/*     */   int offsetNormal;
/*     */   int offsetUV;
/*     */   int offsetUVCenter;
/*     */   boolean hasNormal;
/*     */   boolean hasTangent;
/*     */   boolean hasUV;
/*     */   boolean hasUVCenter;
/*  27 */   long[] entityData = new long[10];
/*  28 */   int entityDataIndex = 0;
/*     */ 
/*     */   
/*     */   public SVertexBuilder() {
/*  32 */     this.entityData[this.entityDataIndex] = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initVertexBuilder(WorldRenderer wrr) {
/*  37 */     wrr.sVertexBuilder = new SVertexBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushEntity(long data) {
/*  42 */     this.entityDataIndex++;
/*  43 */     this.entityData[this.entityDataIndex] = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void popEntity() {
/*  48 */     this.entityData[this.entityDataIndex] = 0L;
/*  49 */     this.entityDataIndex--;
/*     */   }
/*     */   
/*     */   public static void pushEntity(IBlockState blockState, BlockPos blockPos, IBlockAccess blockAccess, WorldRenderer wrr) {
/*     */     int i, j;
/*  54 */     Block block = blockState.getBlock();
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (blockState instanceof BlockStateBase) {
/*     */       
/*  60 */       BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  61 */       i = blockstatebase.getBlockId();
/*  62 */       j = blockstatebase.getMetadata();
/*     */     }
/*     */     else {
/*     */       
/*  66 */       i = Block.getIdFromBlock(block);
/*  67 */       j = block.getMetaFromState(blockState);
/*     */     } 
/*     */     
/*  70 */     int j1 = BlockAliases.getBlockAliasId(i, j);
/*     */     
/*  72 */     if (j1 >= 0)
/*     */     {
/*  74 */       i = j1;
/*     */     }
/*     */     
/*  77 */     int k = block.getRenderType();
/*  78 */     int l = ((k & 0xFFFF) << 16) + (i & 0xFFFF);
/*  79 */     int i1 = j & 0xFFFF;
/*  80 */     wrr.sVertexBuilder.pushEntity((i1 << 32L) + l);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void popEntity(WorldRenderer wrr) {
/*  85 */     wrr.sVertexBuilder.popEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean popEntity(boolean value, WorldRenderer wrr) {
/*  90 */     wrr.sVertexBuilder.popEntity();
/*  91 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endSetVertexFormat(WorldRenderer wrr) {
/*  96 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*  97 */     VertexFormat vertexformat = wrr.getVertexFormat();
/*  98 */     svertexbuilder.vertexSize = vertexformat.getNextOffset() / 4;
/*  99 */     svertexbuilder.hasNormal = vertexformat.hasNormal();
/* 100 */     svertexbuilder.hasTangent = svertexbuilder.hasNormal;
/* 101 */     svertexbuilder.hasUV = vertexformat.hasElementOffset(0);
/* 102 */     svertexbuilder.offsetNormal = svertexbuilder.hasNormal ? (vertexformat.getNormalOffset() / 4) : 0;
/* 103 */     svertexbuilder.offsetUV = svertexbuilder.hasUV ? (vertexformat.getElementOffsetById(0) / 4) : 0;
/* 104 */     svertexbuilder.offsetUVCenter = 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertex(WorldRenderer wrr) {
/* 109 */     if (wrr.vertexCount == 0)
/*     */     {
/* 111 */       endSetVertexFormat(wrr);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endAddVertex(WorldRenderer wrr) {
/* 117 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 119 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 121 */       if (wrr.drawMode == 7 && wrr.vertexCount % 4 == 0)
/*     */       {
/* 123 */         svertexbuilder.calcNormal(wrr, wrr.func_181664_j() - 4 * svertexbuilder.vertexSize);
/*     */       }
/*     */       
/* 126 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 127 */       int j = wrr.func_181664_j() - 14 + 12;
/* 128 */       wrr.rawIntBuffer.put(j, (int)i);
/* 129 */       wrr.rawIntBuffer.put(j + 1, (int)(i >> 32L));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertexData(WorldRenderer wrr, int[] data) {
/* 135 */     if (wrr.vertexCount == 0)
/*     */     {
/* 137 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 140 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 142 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 144 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/*     */       
/* 146 */       for (int j = 12; j + 1 < data.length; j += 14) {
/*     */         
/* 148 */         data[j] = (int)i;
/* 149 */         data[j + 1] = (int)(i >> 32L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertexData(WorldRenderer wrr, ByteBuffer byteBuffer) {
/* 156 */     if (wrr.vertexCount == 0)
/*     */     {
/* 158 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 161 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 163 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 165 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 166 */       int j = byteBuffer.limit() / 4;
/*     */       
/* 168 */       for (int k = 12; k + 1 < j; k += 14) {
/*     */         
/* 170 */         int l = (int)i;
/* 171 */         int i1 = (int)(i >> 32L);
/* 172 */         byteBuffer.putInt(k * 4, l);
/* 173 */         byteBuffer.putInt((k + 1) * 4, i1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endAddVertexData(WorldRenderer wrr) {
/* 180 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 182 */     if (svertexbuilder.vertexSize == 14 && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0)
/*     */     {
/* 184 */       svertexbuilder.calcNormal(wrr, wrr.func_181664_j() - 4 * svertexbuilder.vertexSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcNormal(WorldRenderer wrr, int baseIndex) {
/* 190 */     FloatBuffer floatbuffer = wrr.rawFloatBuffer;
/* 191 */     IntBuffer intbuffer = wrr.rawIntBuffer;
/* 192 */     int i = wrr.func_181664_j();
/* 193 */     float f = floatbuffer.get(baseIndex + 0 * this.vertexSize);
/* 194 */     float f1 = floatbuffer.get(baseIndex + 0 * this.vertexSize + 1);
/* 195 */     float f2 = floatbuffer.get(baseIndex + 0 * this.vertexSize + 2);
/* 196 */     float f3 = floatbuffer.get(baseIndex + 0 * this.vertexSize + this.offsetUV);
/* 197 */     float f4 = floatbuffer.get(baseIndex + 0 * this.vertexSize + this.offsetUV + 1);
/* 198 */     float f5 = floatbuffer.get(baseIndex + 1 * this.vertexSize);
/* 199 */     float f6 = floatbuffer.get(baseIndex + 1 * this.vertexSize + 1);
/* 200 */     float f7 = floatbuffer.get(baseIndex + 1 * this.vertexSize + 2);
/* 201 */     float f8 = floatbuffer.get(baseIndex + 1 * this.vertexSize + this.offsetUV);
/* 202 */     float f9 = floatbuffer.get(baseIndex + 1 * this.vertexSize + this.offsetUV + 1);
/* 203 */     float f10 = floatbuffer.get(baseIndex + 2 * this.vertexSize);
/* 204 */     float f11 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 1);
/* 205 */     float f12 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 2);
/* 206 */     float f13 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV);
/* 207 */     float f14 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV + 1);
/* 208 */     float f15 = floatbuffer.get(baseIndex + 3 * this.vertexSize);
/* 209 */     float f16 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 1);
/* 210 */     float f17 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 2);
/* 211 */     float f18 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV);
/* 212 */     float f19 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV + 1);
/* 213 */     float f20 = f10 - f;
/* 214 */     float f21 = f11 - f1;
/* 215 */     float f22 = f12 - f2;
/* 216 */     float f23 = f15 - f5;
/* 217 */     float f24 = f16 - f6;
/* 218 */     float f25 = f17 - f7;
/* 219 */     float f30 = f21 * f25 - f24 * f22;
/* 220 */     float f31 = f22 * f23 - f25 * f20;
/* 221 */     float f32 = f20 * f24 - f23 * f21;
/* 222 */     float f33 = f30 * f30 + f31 * f31 + f32 * f32;
/* 223 */     float f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 224 */     f30 *= f34;
/* 225 */     f31 *= f34;
/* 226 */     f32 *= f34;
/* 227 */     f20 = f5 - f;
/* 228 */     f21 = f6 - f1;
/* 229 */     f22 = f7 - f2;
/* 230 */     float f26 = f8 - f3;
/* 231 */     float f27 = f9 - f4;
/* 232 */     f23 = f10 - f;
/* 233 */     f24 = f11 - f1;
/* 234 */     f25 = f12 - f2;
/* 235 */     float f28 = f13 - f3;
/* 236 */     float f29 = f14 - f4;
/* 237 */     float f35 = f26 * f29 - f28 * f27;
/* 238 */     float f36 = (f35 != 0.0F) ? (1.0F / f35) : 1.0F;
/* 239 */     float f37 = (f29 * f20 - f27 * f23) * f36;
/* 240 */     float f38 = (f29 * f21 - f27 * f24) * f36;
/* 241 */     float f39 = (f29 * f22 - f27 * f25) * f36;
/* 242 */     float f40 = (f26 * f23 - f28 * f20) * f36;
/* 243 */     float f41 = (f26 * f24 - f28 * f21) * f36;
/* 244 */     float f42 = (f26 * f25 - f28 * f22) * f36;
/* 245 */     f33 = f37 * f37 + f38 * f38 + f39 * f39;
/* 246 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 247 */     f37 *= f34;
/* 248 */     f38 *= f34;
/* 249 */     f39 *= f34;
/* 250 */     f33 = f40 * f40 + f41 * f41 + f42 * f42;
/* 251 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 252 */     f40 *= f34;
/* 253 */     f41 *= f34;
/* 254 */     f42 *= f34;
/* 255 */     float f43 = f32 * f38 - f31 * f39;
/* 256 */     float f44 = f30 * f39 - f32 * f37;
/* 257 */     float f45 = f31 * f37 - f30 * f38;
/* 258 */     float f46 = (f40 * f43 + f41 * f44 + f42 * f45 < 0.0F) ? -1.0F : 1.0F;
/* 259 */     int j = (int)(f30 * 127.0F) & 0xFF;
/* 260 */     int k = (int)(f31 * 127.0F) & 0xFF;
/* 261 */     int l = (int)(f32 * 127.0F) & 0xFF;
/* 262 */     int i1 = (l << 16) + (k << 8) + j;
/* 263 */     intbuffer.put(baseIndex + 0 * this.vertexSize + this.offsetNormal, i1);
/* 264 */     intbuffer.put(baseIndex + 1 * this.vertexSize + this.offsetNormal, i1);
/* 265 */     intbuffer.put(baseIndex + 2 * this.vertexSize + this.offsetNormal, i1);
/* 266 */     intbuffer.put(baseIndex + 3 * this.vertexSize + this.offsetNormal, i1);
/* 267 */     int j1 = ((int)(f37 * 32767.0F) & 0xFFFF) + (((int)(f38 * 32767.0F) & 0xFFFF) << 16);
/* 268 */     int k1 = ((int)(f39 * 32767.0F) & 0xFFFF) + (((int)(f46 * 32767.0F) & 0xFFFF) << 16);
/* 269 */     intbuffer.put(baseIndex + 0 * this.vertexSize + 10, j1);
/* 270 */     intbuffer.put(baseIndex + 0 * this.vertexSize + 10 + 1, k1);
/* 271 */     intbuffer.put(baseIndex + 1 * this.vertexSize + 10, j1);
/* 272 */     intbuffer.put(baseIndex + 1 * this.vertexSize + 10 + 1, k1);
/* 273 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10, j1);
/* 274 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10 + 1, k1);
/* 275 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10, j1);
/* 276 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10 + 1, k1);
/* 277 */     float f47 = (f3 + f8 + f13 + f18) / 4.0F;
/* 278 */     float f48 = (f4 + f9 + f14 + f19) / 4.0F;
/* 279 */     floatbuffer.put(baseIndex + 0 * this.vertexSize + 8, f47);
/* 280 */     floatbuffer.put(baseIndex + 0 * this.vertexSize + 8 + 1, f48);
/* 281 */     floatbuffer.put(baseIndex + 1 * this.vertexSize + 8, f47);
/* 282 */     floatbuffer.put(baseIndex + 1 * this.vertexSize + 8 + 1, f48);
/* 283 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8, f47);
/* 284 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8 + 1, f48);
/* 285 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8, f47);
/* 286 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8 + 1, f48);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void calcNormalChunkLayer(WorldRenderer wrr) {
/* 291 */     if (wrr.getVertexFormat().hasNormal() && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0) {
/*     */       
/* 293 */       SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/* 294 */       endSetVertexFormat(wrr);
/* 295 */       int i = wrr.vertexCount * svertexbuilder.vertexSize;
/*     */       
/* 297 */       for (int j = 0; j < i; j += svertexbuilder.vertexSize * 4)
/*     */       {
/* 299 */         svertexbuilder.calcNormal(wrr, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawArrays(int drawMode, int first, int count, WorldRenderer wrr) {
/* 306 */     if (count != 0) {
/*     */       
/* 308 */       VertexFormat vertexformat = wrr.getVertexFormat();
/* 309 */       int i = vertexformat.getNextOffset();
/*     */       
/* 311 */       if (i == 56) {
/*     */         
/* 313 */         ByteBuffer bytebuffer = wrr.getByteBuffer();
/* 314 */         bytebuffer.position(32);
/* 315 */         GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, i, bytebuffer);
/* 316 */         bytebuffer.position(40);
/* 317 */         GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, i, bytebuffer);
/* 318 */         bytebuffer.position(48);
/* 319 */         GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, i, bytebuffer);
/* 320 */         bytebuffer.position(0);
/* 321 */         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 322 */         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 323 */         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/* 324 */         GlStateManager.glDrawArrays(drawMode, first, count);
/* 325 */         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 326 */         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 327 */         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */       }
/*     */       else {
/*     */         
/* 331 */         GlStateManager.glDrawArrays(drawMode, first, count);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\SVertexBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */