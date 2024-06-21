/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexConsumer;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexProducer;
/*     */ import net.optifine.model.QuadBounds;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BakedQuad
/*     */   implements IVertexProducer
/*     */ {
/*     */   protected int[] vertexData;
/*     */   protected final int tintIndex;
/*     */   protected EnumFacing face;
/*     */   protected TextureAtlasSprite sprite;
/*  22 */   private int[] vertexDataSingle = null;
/*     */   
/*     */   private QuadBounds quadBounds;
/*     */   private boolean quadEmissiveChecked;
/*     */   private BakedQuad quadEmissive;
/*     */   
/*     */   public BakedQuad(int[] p_i3_1_, int p_i3_2_, EnumFacing p_i3_3_, TextureAtlasSprite p_i3_4_) {
/*  29 */     this.vertexData = p_i3_1_;
/*  30 */     this.tintIndex = p_i3_2_;
/*  31 */     this.face = p_i3_3_;
/*  32 */     this.sprite = p_i3_4_;
/*  33 */     fixVertexData();
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn) {
/*  38 */     this.vertexData = vertexDataIn;
/*  39 */     this.tintIndex = tintIndexIn;
/*  40 */     this.face = faceIn;
/*  41 */     fixVertexData();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getSprite() {
/*  46 */     if (this.sprite == null)
/*     */     {
/*  48 */       this.sprite = getSpriteByUv(getVertexData());
/*     */     }
/*     */     
/*  51 */     return this.sprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getVertexData() {
/*  56 */     fixVertexData();
/*  57 */     return this.vertexData;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTintIndex() {
/*  62 */     return (this.tintIndex != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTintIndex() {
/*  67 */     return this.tintIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFace() {
/*  72 */     if (this.face == null)
/*     */     {
/*  74 */       this.face = FaceBakery.getFacingFromVertexData(getVertexData());
/*     */     }
/*     */     
/*  77 */     return this.face;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getVertexDataSingle() {
/*  82 */     if (this.vertexDataSingle == null)
/*     */     {
/*  84 */       this.vertexDataSingle = makeVertexDataSingle(getVertexData(), getSprite());
/*     */     }
/*     */     
/*  87 */     return this.vertexDataSingle;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_) {
/*  92 */     int[] aint = (int[])p_makeVertexDataSingle_0_.clone();
/*  93 */     int i = p_makeVertexDataSingle_1_.sheetWidth / p_makeVertexDataSingle_1_.getIconWidth();
/*  94 */     int j = p_makeVertexDataSingle_1_.sheetHeight / p_makeVertexDataSingle_1_.getIconHeight();
/*  95 */     int k = aint.length / 4;
/*     */     
/*  97 */     for (int l = 0; l < 4; l++) {
/*     */       
/*  99 */       int i1 = l * k;
/* 100 */       float f = Float.intBitsToFloat(aint[i1 + 4]);
/* 101 */       float f1 = Float.intBitsToFloat(aint[i1 + 4 + 1]);
/* 102 */       float f2 = p_makeVertexDataSingle_1_.toSingleU(f);
/* 103 */       float f3 = p_makeVertexDataSingle_1_.toSingleV(f1);
/* 104 */       aint[i1 + 4] = Float.floatToRawIntBits(f2);
/* 105 */       aint[i1 + 4 + 1] = Float.floatToRawIntBits(f3);
/*     */     } 
/*     */     
/* 108 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pipe(IVertexConsumer p_pipe_1_) {
/* 113 */     Reflector.callVoid(Reflector.LightUtil_putBakedQuad, new Object[] { p_pipe_1_, this });
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_) {
/* 118 */     float f = 1.0F;
/* 119 */     float f1 = 1.0F;
/* 120 */     float f2 = 0.0F;
/* 121 */     float f3 = 0.0F;
/* 122 */     int i = p_getSpriteByUv_0_.length / 4;
/*     */     
/* 124 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 126 */       int k = j * i;
/* 127 */       float f4 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4]);
/* 128 */       float f5 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4 + 1]);
/* 129 */       f = Math.min(f, f4);
/* 130 */       f1 = Math.min(f1, f5);
/* 131 */       f2 = Math.max(f2, f4);
/* 132 */       f3 = Math.max(f3, f5);
/*     */     } 
/*     */     
/* 135 */     float f6 = (f + f2) / 2.0F;
/* 136 */     float f7 = (f1 + f3) / 2.0F;
/* 137 */     TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
/* 138 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fixVertexData() {
/* 143 */     if (Config.isShaders()) {
/*     */       
/* 145 */       if (this.vertexData.length == 28)
/*     */       {
/* 147 */         this.vertexData = expandVertexData(this.vertexData);
/*     */       }
/*     */     }
/* 150 */     else if (this.vertexData.length == 56) {
/*     */       
/* 152 */       this.vertexData = compactVertexData(this.vertexData);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] expandVertexData(int[] p_expandVertexData_0_) {
/* 158 */     int i = p_expandVertexData_0_.length / 4;
/* 159 */     int j = i * 2;
/* 160 */     int[] aint = new int[j * 4];
/*     */     
/* 162 */     for (int k = 0; k < 4; k++)
/*     */     {
/* 164 */       System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
/*     */     }
/*     */     
/* 167 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] compactVertexData(int[] p_compactVertexData_0_) {
/* 172 */     int i = p_compactVertexData_0_.length / 4;
/* 173 */     int j = i / 2;
/* 174 */     int[] aint = new int[j * 4];
/*     */     
/* 176 */     for (int k = 0; k < 4; k++)
/*     */     {
/* 178 */       System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
/*     */     }
/*     */     
/* 181 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuadBounds getQuadBounds() {
/* 186 */     if (this.quadBounds == null)
/*     */     {
/* 188 */       this.quadBounds = new QuadBounds(getVertexData());
/*     */     }
/*     */     
/* 191 */     return this.quadBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMidX() {
/* 196 */     QuadBounds quadbounds = getQuadBounds();
/* 197 */     return (quadbounds.getMaxX() + quadbounds.getMinX()) / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMidY() {
/* 202 */     QuadBounds quadbounds = getQuadBounds();
/* 203 */     return ((quadbounds.getMaxY() + quadbounds.getMinY()) / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMidZ() {
/* 208 */     QuadBounds quadbounds = getQuadBounds();
/* 209 */     return ((quadbounds.getMaxZ() + quadbounds.getMinZ()) / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFaceQuad() {
/* 214 */     QuadBounds quadbounds = getQuadBounds();
/* 215 */     return quadbounds.isFaceQuad(this.face);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullQuad() {
/* 220 */     QuadBounds quadbounds = getQuadBounds();
/* 221 */     return quadbounds.isFullQuad(this.face);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullFaceQuad() {
/* 226 */     return (isFullQuad() && isFaceQuad());
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad getQuadEmissive() {
/* 231 */     if (this.quadEmissiveChecked)
/*     */     {
/* 233 */       return this.quadEmissive;
/*     */     }
/*     */ 
/*     */     
/* 237 */     if (this.quadEmissive == null && this.sprite != null && this.sprite.spriteEmissive != null)
/*     */     {
/* 239 */       this.quadEmissive = new BreakingFour(this, this.sprite.spriteEmissive);
/*     */     }
/*     */     
/* 242 */     this.quadEmissiveChecked = true;
/* 243 */     return this.quadEmissive;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 249 */     return "vertex: " + (this.vertexData.length / 7) + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\block\model\BakedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */