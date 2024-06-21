/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.primitives.Floats;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Comparator;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.shaders.SVertexBuilder;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WorldRenderer
/*      */ {
/*      */   private ByteBuffer byteBuffer;
/*      */   public IntBuffer rawIntBuffer;
/*      */   private ShortBuffer field_181676_c;
/*      */   public FloatBuffer rawFloatBuffer;
/*      */   public int vertexCount;
/*      */   private VertexFormatElement field_181677_f;
/*      */   private int field_181678_g;
/*      */   private boolean needsUpdate;
/*      */   public int drawMode;
/*      */   private double xOffset;
/*      */   private double yOffset;
/*      */   private double zOffset;
/*      */   private VertexFormat vertexFormat;
/*      */   private boolean isDrawing;
/*   45 */   private EnumWorldBlockLayer blockLayer = null;
/*   46 */   private boolean[] drawnIcons = new boolean[256];
/*   47 */   private TextureAtlasSprite[] quadSprites = null;
/*   48 */   private TextureAtlasSprite[] quadSpritesPrev = null;
/*   49 */   private TextureAtlasSprite quadSprite = null;
/*      */   public SVertexBuilder sVertexBuilder;
/*   51 */   public RenderEnv renderEnv = null;
/*   52 */   public BitSet animatedSprites = null;
/*   53 */   public BitSet animatedSpritesCached = new BitSet();
/*      */   
/*      */   private boolean modeTriangles = false;
/*      */   private ByteBuffer byteBufferTriangles;
/*      */   
/*      */   public WorldRenderer(int bufferSizeIn) {
/*   59 */     this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
/*   60 */     this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*   61 */     this.field_181676_c = this.byteBuffer.asShortBuffer();
/*   62 */     this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*   63 */     SVertexBuilder.initVertexBuilder(this);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_181670_b(int p_181670_1_) {
/*   68 */     if (p_181670_1_ > this.rawIntBuffer.remaining()) {
/*      */       
/*   70 */       int i = this.byteBuffer.capacity();
/*   71 */       int j = i % 2097152;
/*   72 */       int k = j + (((this.rawIntBuffer.position() + p_181670_1_) * 4 - j) / 2097152 + 1) * 2097152;
/*   73 */       LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + i + " bytes, new size " + k + " bytes.");
/*   74 */       int l = this.rawIntBuffer.position();
/*   75 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(k);
/*   76 */       this.byteBuffer.position(0);
/*   77 */       bytebuffer.put(this.byteBuffer);
/*   78 */       bytebuffer.rewind();
/*   79 */       this.byteBuffer = bytebuffer;
/*   80 */       this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*   81 */       this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*   82 */       this.rawIntBuffer.position(l);
/*   83 */       this.field_181676_c = this.byteBuffer.asShortBuffer();
/*   84 */       this.field_181676_c.position(l << 1);
/*      */       
/*   86 */       if (this.quadSprites != null) {
/*      */         
/*   88 */         TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
/*   89 */         int i1 = getBufferQuadSize();
/*   90 */         this.quadSprites = new TextureAtlasSprite[i1];
/*   91 */         System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
/*   92 */         this.quadSpritesPrev = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181674_a(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
/*   99 */     int i = this.vertexCount / 4;
/*  100 */     final float[] afloat = new float[i];
/*      */     
/*  102 */     for (int j = 0; j < i; j++)
/*      */     {
/*  104 */       afloat[j] = func_181665_a(this.rawFloatBuffer, (float)(p_181674_1_ + this.xOffset), (float)(p_181674_2_ + this.yOffset), (float)(p_181674_3_ + this.zOffset), this.vertexFormat.func_181719_f(), j * this.vertexFormat.getNextOffset());
/*      */     }
/*      */     
/*  107 */     Integer[] ainteger = new Integer[i];
/*      */     
/*  109 */     for (int k = 0; k < ainteger.length; k++)
/*      */     {
/*  111 */       ainteger[k] = Integer.valueOf(k);
/*      */     }
/*      */     
/*  114 */     Arrays.sort(ainteger, new Comparator<Integer>()
/*      */         {
/*      */           public int compare(Integer p_compare_1_, Integer p_compare_2_)
/*      */           {
/*  118 */             return Floats.compare(afloat[p_compare_2_.intValue()], afloat[p_compare_1_.intValue()]);
/*      */           }
/*      */         });
/*  121 */     BitSet bitset = new BitSet();
/*  122 */     int l = this.vertexFormat.getNextOffset();
/*  123 */     int[] aint = new int[l];
/*      */     
/*  125 */     for (int l1 = 0; (l1 = bitset.nextClearBit(l1)) < ainteger.length; l1++) {
/*      */       
/*  127 */       int i1 = ainteger[l1].intValue();
/*      */       
/*  129 */       if (i1 != l1) {
/*      */         
/*  131 */         this.rawIntBuffer.limit(i1 * l + l);
/*  132 */         this.rawIntBuffer.position(i1 * l);
/*  133 */         this.rawIntBuffer.get(aint);
/*  134 */         int j1 = i1;
/*      */         int k1;
/*  136 */         for (k1 = ainteger[i1].intValue(); j1 != l1; k1 = ainteger[k1].intValue()) {
/*      */           
/*  138 */           this.rawIntBuffer.limit(k1 * l + l);
/*  139 */           this.rawIntBuffer.position(k1 * l);
/*  140 */           IntBuffer intbuffer = this.rawIntBuffer.slice();
/*  141 */           this.rawIntBuffer.limit(j1 * l + l);
/*  142 */           this.rawIntBuffer.position(j1 * l);
/*  143 */           this.rawIntBuffer.put(intbuffer);
/*  144 */           bitset.set(j1);
/*  145 */           j1 = k1;
/*      */         } 
/*      */         
/*  148 */         this.rawIntBuffer.limit(l1 * l + l);
/*  149 */         this.rawIntBuffer.position(l1 * l);
/*  150 */         this.rawIntBuffer.put(aint);
/*      */       } 
/*      */       
/*  153 */       bitset.set(l1);
/*      */     } 
/*      */     
/*  156 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/*  157 */     this.rawIntBuffer.position(func_181664_j());
/*      */     
/*  159 */     if (this.quadSprites != null) {
/*      */       
/*  161 */       TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];
/*  162 */       int i2 = this.vertexFormat.getNextOffset() / 4 * 4;
/*      */       
/*  164 */       for (int j2 = 0; j2 < ainteger.length; j2++) {
/*      */         
/*  166 */         int k2 = ainteger[j2].intValue();
/*  167 */         atextureatlassprite[j2] = this.quadSprites[k2];
/*      */       } 
/*      */       
/*  170 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public State func_181672_a() {
/*  176 */     this.rawIntBuffer.rewind();
/*  177 */     int i = func_181664_j();
/*  178 */     this.rawIntBuffer.limit(i);
/*  179 */     int[] aint = new int[i];
/*  180 */     this.rawIntBuffer.get(aint);
/*  181 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/*  182 */     this.rawIntBuffer.position(i);
/*  183 */     TextureAtlasSprite[] atextureatlassprite = null;
/*      */     
/*  185 */     if (this.quadSprites != null) {
/*      */       
/*  187 */       int j = this.vertexCount / 4;
/*  188 */       atextureatlassprite = new TextureAtlasSprite[j];
/*  189 */       System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
/*      */     } 
/*      */     
/*  192 */     return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_181664_j() {
/*  197 */     return this.vertexCount * this.vertexFormat.func_181719_f();
/*      */   }
/*      */ 
/*      */   
/*      */   private static float func_181665_a(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
/*  202 */     float f = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 0);
/*  203 */     float f1 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 1);
/*  204 */     float f2 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 2);
/*  205 */     float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 0);
/*  206 */     float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 1);
/*  207 */     float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 2);
/*  208 */     float f6 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 0);
/*  209 */     float f7 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 1);
/*  210 */     float f8 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 2);
/*  211 */     float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 0);
/*  212 */     float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
/*  213 */     float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
/*  214 */     float f12 = (f + f3 + f6 + f9) * 0.25F - p_181665_1_;
/*  215 */     float f13 = (f1 + f4 + f7 + f10) * 0.25F - p_181665_2_;
/*  216 */     float f14 = (f2 + f5 + f8 + f11) * 0.25F - p_181665_3_;
/*  217 */     return f12 * f12 + f13 * f13 + f14 * f14;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVertexState(State state) {
/*  222 */     this.rawIntBuffer.clear();
/*  223 */     func_181670_b((state.getRawBuffer()).length);
/*  224 */     this.rawIntBuffer.put(state.getRawBuffer());
/*  225 */     this.vertexCount = state.getVertexCount();
/*  226 */     this.vertexFormat = new VertexFormat(state.getVertexFormat());
/*      */     
/*  228 */     if (state.stateQuadSprites != null) {
/*      */       
/*  230 */       if (this.quadSprites == null)
/*      */       {
/*  232 */         this.quadSprites = this.quadSpritesPrev;
/*      */       }
/*      */       
/*  235 */       if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize())
/*      */       {
/*  237 */         this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*      */       }
/*      */       
/*  240 */       TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
/*  241 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*      */     }
/*      */     else {
/*      */       
/*  245 */       if (this.quadSprites != null)
/*      */       {
/*  247 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  250 */       this.quadSprites = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/*  256 */     this.vertexCount = 0;
/*  257 */     this.field_181677_f = null;
/*  258 */     this.field_181678_g = 0;
/*  259 */     this.quadSprite = null;
/*      */     
/*  261 */     if (SmartAnimations.isActive()) {
/*      */       
/*  263 */       if (this.animatedSprites == null)
/*      */       {
/*  265 */         this.animatedSprites = this.animatedSpritesCached;
/*      */       }
/*      */       
/*  268 */       this.animatedSprites.clear();
/*      */     }
/*  270 */     else if (this.animatedSprites != null) {
/*      */       
/*  272 */       this.animatedSprites = null;
/*      */     } 
/*      */     
/*  275 */     this.modeTriangles = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void begin(int p_181668_1_, VertexFormat p_181668_2_) {
/*  280 */     if (this.isDrawing)
/*      */     {
/*  282 */       throw new IllegalStateException("Already building!");
/*      */     }
/*      */ 
/*      */     
/*  286 */     this.isDrawing = true;
/*  287 */     reset();
/*  288 */     this.drawMode = p_181668_1_;
/*  289 */     this.vertexFormat = p_181668_2_;
/*  290 */     this.field_181677_f = p_181668_2_.getElement(this.field_181678_g);
/*  291 */     this.needsUpdate = false;
/*  292 */     this.byteBuffer.limit(this.byteBuffer.capacity());
/*      */     
/*  294 */     if (Config.isShaders())
/*      */     {
/*  296 */       SVertexBuilder.endSetVertexFormat(this);
/*      */     }
/*      */     
/*  299 */     if (Config.isMultiTexture()) {
/*      */       
/*  301 */       if (this.blockLayer != null)
/*      */       {
/*  303 */         if (this.quadSprites == null)
/*      */         {
/*  305 */           this.quadSprites = this.quadSpritesPrev;
/*      */         }
/*      */         
/*  308 */         if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize())
/*      */         {
/*  310 */           this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  316 */       if (this.quadSprites != null)
/*      */       {
/*  318 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  321 */       this.quadSprites = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldRenderer tex(double p_181673_1_, double p_181673_3_) {
/*  328 */     if (this.quadSprite != null && this.quadSprites != null) {
/*      */       
/*  330 */       p_181673_1_ = this.quadSprite.toSingleU((float)p_181673_1_);
/*  331 */       p_181673_3_ = this.quadSprite.toSingleV((float)p_181673_3_);
/*  332 */       this.quadSprites[this.vertexCount / 4] = this.quadSprite;
/*      */     } 
/*      */     
/*  335 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*      */     
/*  337 */     switch (this.field_181677_f.getType()) {
/*      */       
/*      */       case FLOAT:
/*  340 */         this.byteBuffer.putFloat(i, (float)p_181673_1_);
/*  341 */         this.byteBuffer.putFloat(i + 4, (float)p_181673_3_);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  346 */         this.byteBuffer.putInt(i, (int)p_181673_1_);
/*  347 */         this.byteBuffer.putInt(i + 4, (int)p_181673_3_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  352 */         this.byteBuffer.putShort(i, (short)(int)p_181673_3_);
/*  353 */         this.byteBuffer.putShort(i + 2, (short)(int)p_181673_1_);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  358 */         this.byteBuffer.put(i, (byte)(int)p_181673_3_);
/*  359 */         this.byteBuffer.put(i + 1, (byte)(int)p_181673_1_);
/*      */         break;
/*      */     } 
/*  362 */     func_181667_k();
/*  363 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer func_181671_a(int p_181671_1_, int p_181671_2_) {
/*  368 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*      */     
/*  370 */     switch (this.field_181677_f.getType()) {
/*      */       
/*      */       case FLOAT:
/*  373 */         this.byteBuffer.putFloat(i, p_181671_1_);
/*  374 */         this.byteBuffer.putFloat(i + 4, p_181671_2_);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  379 */         this.byteBuffer.putInt(i, p_181671_1_);
/*  380 */         this.byteBuffer.putInt(i + 4, p_181671_2_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  385 */         this.byteBuffer.putShort(i, (short)p_181671_2_);
/*  386 */         this.byteBuffer.putShort(i + 2, (short)p_181671_1_);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  391 */         this.byteBuffer.put(i, (byte)p_181671_2_);
/*  392 */         this.byteBuffer.put(i + 1, (byte)p_181671_1_);
/*      */         break;
/*      */     } 
/*  395 */     func_181667_k();
/*  396 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
/*  401 */     int i = (this.vertexCount - 4) * this.vertexFormat.func_181719_f() + this.vertexFormat.getElementOffsetById(1) / 4;
/*  402 */     int j = this.vertexFormat.getNextOffset() >> 2;
/*  403 */     this.rawIntBuffer.put(i, p_178962_1_);
/*  404 */     this.rawIntBuffer.put(i + j, p_178962_2_);
/*  405 */     this.rawIntBuffer.put(i + j * 2, p_178962_3_);
/*  406 */     this.rawIntBuffer.put(i + j * 3, p_178962_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putPosition(double x, double y, double z) {
/*  411 */     int i = this.vertexFormat.func_181719_f();
/*  412 */     int j = (this.vertexCount - 4) * i;
/*      */     
/*  414 */     for (int k = 0; k < 4; k++) {
/*      */       
/*  416 */       int l = j + k * i;
/*  417 */       int i1 = l + 1;
/*  418 */       int j1 = i1 + 1;
/*  419 */       this.rawIntBuffer.put(l, Float.floatToRawIntBits((float)(x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
/*  420 */       this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float)(y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
/*  421 */       this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float)(z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColorIndex(int p_78909_1_) {
/*  430 */     return ((this.vertexCount - p_78909_1_) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorMultiplier(float red, float green, float blue, int p_178978_4_) {
/*  435 */     int i = getColorIndex(p_178978_4_);
/*  436 */     int j = -1;
/*      */     
/*  438 */     if (!this.needsUpdate) {
/*      */       
/*  440 */       j = this.rawIntBuffer.get(i);
/*      */       
/*  442 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */         
/*  444 */         int k = (int)((j & 0xFF) * red);
/*  445 */         int l = (int)((j >> 8 & 0xFF) * green);
/*  446 */         int i1 = (int)((j >> 16 & 0xFF) * blue);
/*  447 */         j &= 0xFF000000;
/*  448 */         j = j | i1 << 16 | l << 8 | k;
/*      */       }
/*      */       else {
/*      */         
/*  452 */         int j1 = (int)((j >> 24 & 0xFF) * red);
/*  453 */         int k1 = (int)((j >> 16 & 0xFF) * green);
/*  454 */         int l1 = (int)((j >> 8 & 0xFF) * blue);
/*  455 */         j &= 0xFF;
/*  456 */         j = j | j1 << 24 | k1 << 16 | l1 << 8;
/*      */       } 
/*      */     } 
/*      */     
/*  460 */     this.rawIntBuffer.put(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   private void putColor(int argb, int p_178988_2_) {
/*  465 */     int i = getColorIndex(p_178988_2_);
/*  466 */     int j = argb >> 16 & 0xFF;
/*  467 */     int k = argb >> 8 & 0xFF;
/*  468 */     int l = argb & 0xFF;
/*  469 */     int i1 = argb >> 24 & 0xFF;
/*  470 */     putColorRGBA(i, j, k, l, i1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGB_F(float red, float green, float blue, int p_178994_4_) {
/*  475 */     int i = getColorIndex(p_178994_4_);
/*  476 */     int j = MathHelper.clamp_int((int)(red * 255.0F), 0, 255);
/*  477 */     int k = MathHelper.clamp_int((int)(green * 255.0F), 0, 255);
/*  478 */     int l = MathHelper.clamp_int((int)(blue * 255.0F), 0, 255);
/*  479 */     putColorRGBA(i, j, k, l, 255);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGBA(int index, int red, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
/*  484 */     if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */       
/*  486 */       this.rawIntBuffer.put(index, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | red);
/*      */     }
/*      */     else {
/*      */       
/*  490 */       this.rawIntBuffer.put(index, red << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markDirty() {
/*  499 */     this.needsUpdate = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer func_181666_a(float p_181666_1_, float p_181666_2_, float p_181666_3_, float p_181666_4_) {
/*  504 */     return func_181669_b((int)(p_181666_1_ * 255.0F), (int)(p_181666_2_ * 255.0F), (int)(p_181666_3_ * 255.0F), (int)(p_181666_4_ * 255.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer func_181669_b(int p_181669_1_, int p_181669_2_, int p_181669_3_, int p_181669_4_) {
/*  509 */     if (this.needsUpdate)
/*      */     {
/*  511 */       return this;
/*      */     }
/*      */ 
/*      */     
/*  515 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*      */     
/*  517 */     switch (this.field_181677_f.getType()) {
/*      */       
/*      */       case FLOAT:
/*  520 */         this.byteBuffer.putFloat(i, p_181669_1_ / 255.0F);
/*  521 */         this.byteBuffer.putFloat(i + 4, p_181669_2_ / 255.0F);
/*  522 */         this.byteBuffer.putFloat(i + 8, p_181669_3_ / 255.0F);
/*  523 */         this.byteBuffer.putFloat(i + 12, p_181669_4_ / 255.0F);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  528 */         this.byteBuffer.putFloat(i, p_181669_1_);
/*  529 */         this.byteBuffer.putFloat(i + 4, p_181669_2_);
/*  530 */         this.byteBuffer.putFloat(i + 8, p_181669_3_);
/*  531 */         this.byteBuffer.putFloat(i + 12, p_181669_4_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  536 */         this.byteBuffer.putShort(i, (short)p_181669_1_);
/*  537 */         this.byteBuffer.putShort(i + 2, (short)p_181669_2_);
/*  538 */         this.byteBuffer.putShort(i + 4, (short)p_181669_3_);
/*  539 */         this.byteBuffer.putShort(i + 6, (short)p_181669_4_);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  544 */         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */           
/*  546 */           this.byteBuffer.put(i, (byte)p_181669_1_);
/*  547 */           this.byteBuffer.put(i + 1, (byte)p_181669_2_);
/*  548 */           this.byteBuffer.put(i + 2, (byte)p_181669_3_);
/*  549 */           this.byteBuffer.put(i + 3, (byte)p_181669_4_);
/*      */           
/*      */           break;
/*      */         } 
/*  553 */         this.byteBuffer.put(i, (byte)p_181669_4_);
/*  554 */         this.byteBuffer.put(i + 1, (byte)p_181669_3_);
/*  555 */         this.byteBuffer.put(i + 2, (byte)p_181669_2_);
/*  556 */         this.byteBuffer.put(i + 3, (byte)p_181669_1_);
/*      */         break;
/*      */     } 
/*      */     
/*  560 */     func_181667_k();
/*  561 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVertexData(int[] vertexData) {
/*  567 */     if (Config.isShaders())
/*      */     {
/*  569 */       SVertexBuilder.beginAddVertexData(this, vertexData);
/*      */     }
/*      */     
/*  572 */     func_181670_b(vertexData.length);
/*  573 */     this.rawIntBuffer.position(func_181664_j());
/*  574 */     this.rawIntBuffer.put(vertexData);
/*  575 */     this.vertexCount += vertexData.length / this.vertexFormat.func_181719_f();
/*      */     
/*  577 */     if (Config.isShaders())
/*      */     {
/*  579 */       SVertexBuilder.endAddVertexData(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void endVertex() {
/*  585 */     this.vertexCount++;
/*  586 */     func_181670_b(this.vertexFormat.func_181719_f());
/*  587 */     this.field_181678_g = 0;
/*  588 */     this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
/*      */     
/*  590 */     if (Config.isShaders())
/*      */     {
/*  592 */       SVertexBuilder.endAddVertex(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer pos(double p_181662_1_, double p_181662_3_, double p_181662_5_) {
/*  598 */     if (Config.isShaders())
/*      */     {
/*  600 */       SVertexBuilder.beginAddVertex(this);
/*      */     }
/*      */     
/*  603 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*      */     
/*  605 */     switch (this.field_181677_f.getType()) {
/*      */       
/*      */       case FLOAT:
/*  608 */         this.byteBuffer.putFloat(i, (float)(p_181662_1_ + this.xOffset));
/*  609 */         this.byteBuffer.putFloat(i + 4, (float)(p_181662_3_ + this.yOffset));
/*  610 */         this.byteBuffer.putFloat(i + 8, (float)(p_181662_5_ + this.zOffset));
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  615 */         this.byteBuffer.putInt(i, Float.floatToRawIntBits((float)(p_181662_1_ + this.xOffset)));
/*  616 */         this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float)(p_181662_3_ + this.yOffset)));
/*  617 */         this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float)(p_181662_5_ + this.zOffset)));
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  622 */         this.byteBuffer.putShort(i, (short)(int)(p_181662_1_ + this.xOffset));
/*  623 */         this.byteBuffer.putShort(i + 2, (short)(int)(p_181662_3_ + this.yOffset));
/*  624 */         this.byteBuffer.putShort(i + 4, (short)(int)(p_181662_5_ + this.zOffset));
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  629 */         this.byteBuffer.put(i, (byte)(int)(p_181662_1_ + this.xOffset));
/*  630 */         this.byteBuffer.put(i + 1, (byte)(int)(p_181662_3_ + this.yOffset));
/*  631 */         this.byteBuffer.put(i + 2, (byte)(int)(p_181662_5_ + this.zOffset));
/*      */         break;
/*      */     } 
/*  634 */     func_181667_k();
/*  635 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putNormal(float x, float y, float z) {
/*  640 */     int i = (byte)(int)(x * 127.0F) & 0xFF;
/*  641 */     int j = (byte)(int)(y * 127.0F) & 0xFF;
/*  642 */     int k = (byte)(int)(z * 127.0F) & 0xFF;
/*  643 */     int l = i | j << 8 | k << 16;
/*  644 */     int i1 = this.vertexFormat.getNextOffset() >> 2;
/*  645 */     int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
/*  646 */     this.rawIntBuffer.put(j1, l);
/*  647 */     this.rawIntBuffer.put(j1 + i1, l);
/*  648 */     this.rawIntBuffer.put(j1 + i1 * 2, l);
/*  649 */     this.rawIntBuffer.put(j1 + i1 * 3, l);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_181667_k() {
/*  654 */     this.field_181678_g++;
/*  655 */     this.field_181678_g %= this.vertexFormat.getElementCount();
/*  656 */     this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
/*      */     
/*  658 */     if (this.field_181677_f.getUsage() == VertexFormatElement.EnumUsage.PADDING)
/*      */     {
/*  660 */       func_181667_k();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer func_181663_c(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
/*  666 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*      */     
/*  668 */     switch (this.field_181677_f.getType()) {
/*      */       
/*      */       case FLOAT:
/*  671 */         this.byteBuffer.putFloat(i, p_181663_1_);
/*  672 */         this.byteBuffer.putFloat(i + 4, p_181663_2_);
/*  673 */         this.byteBuffer.putFloat(i + 8, p_181663_3_);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  678 */         this.byteBuffer.putInt(i, (int)p_181663_1_);
/*  679 */         this.byteBuffer.putInt(i + 4, (int)p_181663_2_);
/*  680 */         this.byteBuffer.putInt(i + 8, (int)p_181663_3_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  685 */         this.byteBuffer.putShort(i, (short)((int)(p_181663_1_ * 32767.0F) & 0xFFFF));
/*  686 */         this.byteBuffer.putShort(i + 2, (short)((int)(p_181663_2_ * 32767.0F) & 0xFFFF));
/*  687 */         this.byteBuffer.putShort(i + 4, (short)((int)(p_181663_3_ * 32767.0F) & 0xFFFF));
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  692 */         this.byteBuffer.put(i, (byte)((int)(p_181663_1_ * 127.0F) & 0xFF));
/*  693 */         this.byteBuffer.put(i + 1, (byte)((int)(p_181663_2_ * 127.0F) & 0xFF));
/*  694 */         this.byteBuffer.put(i + 2, (byte)((int)(p_181663_3_ * 127.0F) & 0xFF));
/*      */         break;
/*      */     } 
/*  697 */     func_181667_k();
/*  698 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTranslation(double x, double y, double z) {
/*  703 */     this.xOffset = x;
/*  704 */     this.yOffset = y;
/*  705 */     this.zOffset = z;
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishDrawing() {
/*  710 */     if (!this.isDrawing)
/*      */     {
/*  712 */       throw new IllegalStateException("Not building!");
/*      */     }
/*      */ 
/*      */     
/*  716 */     this.isDrawing = false;
/*  717 */     this.byteBuffer.position(0);
/*  718 */     this.byteBuffer.limit(func_181664_j() * 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer getByteBuffer() {
/*  724 */     return this.modeTriangles ? this.byteBufferTriangles : this.byteBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public VertexFormat getVertexFormat() {
/*  729 */     return this.vertexFormat;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVertexCount() {
/*  734 */     return this.modeTriangles ? (this.vertexCount / 4 * 6) : this.vertexCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDrawMode() {
/*  739 */     return this.modeTriangles ? 4 : this.drawMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColor4(int argb) {
/*  744 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  746 */       putColor(argb, i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGB_F4(float red, float green, float blue) {
/*  752 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  754 */       putColorRGB_F(red, green, blue, i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void putSprite(TextureAtlasSprite p_putSprite_1_) {
/*  760 */     if (this.animatedSprites != null && p_putSprite_1_ != null && p_putSprite_1_.getAnimationIndex() >= 0)
/*      */     {
/*  762 */       this.animatedSprites.set(p_putSprite_1_.getAnimationIndex());
/*      */     }
/*      */     
/*  765 */     if (this.quadSprites != null) {
/*      */       
/*  767 */       int i = this.vertexCount / 4;
/*  768 */       this.quadSprites[i - 1] = p_putSprite_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSprite(TextureAtlasSprite p_setSprite_1_) {
/*  774 */     if (this.animatedSprites != null && p_setSprite_1_ != null && p_setSprite_1_.getAnimationIndex() >= 0)
/*      */     {
/*  776 */       this.animatedSprites.set(p_setSprite_1_.getAnimationIndex());
/*      */     }
/*      */     
/*  779 */     if (this.quadSprites != null)
/*      */     {
/*  781 */       this.quadSprite = p_setSprite_1_;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMultiTexture() {
/*  787 */     return (this.quadSprites != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawMultiTexture() {
/*  792 */     if (this.quadSprites != null) {
/*      */       
/*  794 */       int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
/*      */       
/*  796 */       if (this.drawnIcons.length <= i)
/*      */       {
/*  798 */         this.drawnIcons = new boolean[i + 1];
/*      */       }
/*      */       
/*  801 */       Arrays.fill(this.drawnIcons, false);
/*  802 */       int j = 0;
/*  803 */       int k = -1;
/*  804 */       int l = this.vertexCount / 4;
/*      */       
/*  806 */       for (int i1 = 0; i1 < l; i1++) {
/*      */         
/*  808 */         TextureAtlasSprite textureatlassprite = this.quadSprites[i1];
/*      */         
/*  810 */         if (textureatlassprite != null) {
/*      */           
/*  812 */           int j1 = textureatlassprite.getIndexInMap();
/*      */           
/*  814 */           if (!this.drawnIcons[j1])
/*      */           {
/*  816 */             if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
/*      */               
/*  818 */               if (k < 0)
/*      */               {
/*  820 */                 k = i1;
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/*  825 */               i1 = drawForIcon(textureatlassprite, i1) - 1;
/*  826 */               j++;
/*      */               
/*  828 */               if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT)
/*      */               {
/*  830 */                 this.drawnIcons[j1] = true;
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  837 */       if (k >= 0) {
/*      */         
/*  839 */         drawForIcon(TextureUtils.iconGrassSideOverlay, k);
/*  840 */         j++;
/*      */       } 
/*      */       
/*  843 */       if (j > 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
/*  852 */     GL11.glBindTexture(3553, p_drawForIcon_1_.glSpriteTextureId);
/*  853 */     int i = -1;
/*  854 */     int j = -1;
/*  855 */     int k = this.vertexCount / 4;
/*      */     
/*  857 */     for (int l = p_drawForIcon_2_; l < k; l++) {
/*      */       
/*  859 */       TextureAtlasSprite textureatlassprite = this.quadSprites[l];
/*      */       
/*  861 */       if (textureatlassprite == p_drawForIcon_1_) {
/*      */         
/*  863 */         if (j < 0)
/*      */         {
/*  865 */           j = l;
/*      */         }
/*      */       }
/*  868 */       else if (j >= 0) {
/*      */         
/*  870 */         draw(j, l);
/*      */         
/*  872 */         if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT)
/*      */         {
/*  874 */           return l;
/*      */         }
/*      */         
/*  877 */         j = -1;
/*      */         
/*  879 */         if (i < 0)
/*      */         {
/*  881 */           i = l;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  886 */     if (j >= 0)
/*      */     {
/*  888 */       draw(j, k);
/*      */     }
/*      */     
/*  891 */     if (i < 0)
/*      */     {
/*  893 */       i = k;
/*      */     }
/*      */     
/*  896 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private void draw(int p_draw_1_, int p_draw_2_) {
/*  901 */     int i = p_draw_2_ - p_draw_1_;
/*      */     
/*  903 */     if (i > 0) {
/*      */       
/*  905 */       int j = p_draw_1_ * 4;
/*  906 */       int k = i * 4;
/*  907 */       GL11.glDrawArrays(this.drawMode, j, k);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlockLayer(EnumWorldBlockLayer p_setBlockLayer_1_) {
/*  913 */     this.blockLayer = p_setBlockLayer_1_;
/*      */     
/*  915 */     if (p_setBlockLayer_1_ == null) {
/*      */       
/*  917 */       if (this.quadSprites != null)
/*      */       {
/*  919 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  922 */       this.quadSprites = null;
/*  923 */       this.quadSprite = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBufferQuadSize() {
/*  929 */     int i = this.rawIntBuffer.capacity() * 4 / this.vertexFormat.func_181719_f() * 4;
/*  930 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderEnv getRenderEnv(IBlockState p_getRenderEnv_1_, BlockPos p_getRenderEnv_2_) {
/*  935 */     if (this.renderEnv == null) {
/*      */       
/*  937 */       this.renderEnv = new RenderEnv(p_getRenderEnv_1_, p_getRenderEnv_2_);
/*  938 */       return this.renderEnv;
/*      */     } 
/*      */ 
/*      */     
/*  942 */     this.renderEnv.reset(p_getRenderEnv_1_, p_getRenderEnv_2_);
/*  943 */     return this.renderEnv;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDrawing() {
/*  949 */     return this.isDrawing;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getXOffset() {
/*  954 */     return this.xOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/*  959 */     return this.yOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getZOffset() {
/*  964 */     return this.zOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer getBlockLayer() {
/*  969 */     return this.blockLayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorMultiplierRgba(float p_putColorMultiplierRgba_1_, float p_putColorMultiplierRgba_2_, float p_putColorMultiplierRgba_3_, float p_putColorMultiplierRgba_4_, int p_putColorMultiplierRgba_5_) {
/*  974 */     int i = getColorIndex(p_putColorMultiplierRgba_5_);
/*  975 */     int j = -1;
/*      */     
/*  977 */     if (!this.needsUpdate) {
/*      */       
/*  979 */       j = this.rawIntBuffer.get(i);
/*      */       
/*  981 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */         
/*  983 */         int k = (int)((j & 0xFF) * p_putColorMultiplierRgba_1_);
/*  984 */         int l = (int)((j >> 8 & 0xFF) * p_putColorMultiplierRgba_2_);
/*  985 */         int i1 = (int)((j >> 16 & 0xFF) * p_putColorMultiplierRgba_3_);
/*  986 */         int j1 = (int)((j >> 24 & 0xFF) * p_putColorMultiplierRgba_4_);
/*  987 */         j = j1 << 24 | i1 << 16 | l << 8 | k;
/*      */       }
/*      */       else {
/*      */         
/*  991 */         int k1 = (int)((j >> 24 & 0xFF) * p_putColorMultiplierRgba_1_);
/*  992 */         int l1 = (int)((j >> 16 & 0xFF) * p_putColorMultiplierRgba_2_);
/*  993 */         int i2 = (int)((j >> 8 & 0xFF) * p_putColorMultiplierRgba_3_);
/*  994 */         int j2 = (int)((j & 0xFF) * p_putColorMultiplierRgba_4_);
/*  995 */         j = k1 << 24 | l1 << 16 | i2 << 8 | j2;
/*      */       } 
/*      */     } 
/*      */     
/*  999 */     this.rawIntBuffer.put(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   public void quadsToTriangles() {
/* 1004 */     if (this.drawMode == 7) {
/*      */       
/* 1006 */       if (this.byteBufferTriangles == null)
/*      */       {
/* 1008 */         this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
/*      */       }
/*      */       
/* 1011 */       if (this.byteBufferTriangles.capacity() < this.byteBuffer.capacity() * 2)
/*      */       {
/* 1013 */         this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
/*      */       }
/*      */       
/* 1016 */       int i = this.vertexFormat.getNextOffset();
/* 1017 */       int j = this.byteBuffer.limit();
/* 1018 */       this.byteBuffer.rewind();
/* 1019 */       this.byteBufferTriangles.clear();
/*      */       
/* 1021 */       for (int k = 0; k < this.vertexCount; k += 4) {
/*      */         
/* 1023 */         this.byteBuffer.limit((k + 3) * i);
/* 1024 */         this.byteBuffer.position(k * i);
/* 1025 */         this.byteBufferTriangles.put(this.byteBuffer);
/* 1026 */         this.byteBuffer.limit((k + 1) * i);
/* 1027 */         this.byteBuffer.position(k * i);
/* 1028 */         this.byteBufferTriangles.put(this.byteBuffer);
/* 1029 */         this.byteBuffer.limit((k + 2 + 2) * i);
/* 1030 */         this.byteBuffer.position((k + 2) * i);
/* 1031 */         this.byteBufferTriangles.put(this.byteBuffer);
/*      */       } 
/*      */       
/* 1034 */       this.byteBuffer.limit(j);
/* 1035 */       this.byteBuffer.rewind();
/* 1036 */       this.byteBufferTriangles.flip();
/* 1037 */       this.modeTriangles = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isColorDisabled() {
/* 1043 */     return this.needsUpdate;
/*      */   }
/*      */ 
/*      */   
/*      */   public class State
/*      */   {
/*      */     private final int[] stateRawBuffer;
/*      */     private final VertexFormat stateVertexFormat;
/*      */     private TextureAtlasSprite[] stateQuadSprites;
/*      */     
/*      */     public State(int[] p_i1_2_, VertexFormat p_i1_3_, TextureAtlasSprite[] p_i1_4_) {
/* 1054 */       this.stateRawBuffer = p_i1_2_;
/* 1055 */       this.stateVertexFormat = p_i1_3_;
/* 1056 */       this.stateQuadSprites = p_i1_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public State(int[] p_i46453_2_, VertexFormat p_i46453_3_) {
/* 1061 */       this.stateRawBuffer = p_i46453_2_;
/* 1062 */       this.stateVertexFormat = p_i46453_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] getRawBuffer() {
/* 1067 */       return this.stateRawBuffer;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getVertexCount() {
/* 1072 */       return this.stateRawBuffer.length / this.stateVertexFormat.func_181719_f();
/*      */     }
/*      */ 
/*      */     
/*      */     public VertexFormat getVertexFormat() {
/* 1077 */       return this.stateVertexFormat;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\WorldRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */