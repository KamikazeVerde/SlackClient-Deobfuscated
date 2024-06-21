/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class NaturalProperties
/*     */ {
/*  13 */   public int rotation = 1;
/*     */   public boolean flip = false;
/*  15 */   private Map[] quadMaps = new Map[8];
/*     */ 
/*     */   
/*     */   public NaturalProperties(String type) {
/*  19 */     if (type.equals("4")) {
/*     */       
/*  21 */       this.rotation = 4;
/*     */     }
/*  23 */     else if (type.equals("2")) {
/*     */       
/*  25 */       this.rotation = 2;
/*     */     }
/*  27 */     else if (type.equals("F")) {
/*     */       
/*  29 */       this.flip = true;
/*     */     }
/*  31 */     else if (type.equals("4F")) {
/*     */       
/*  33 */       this.rotation = 4;
/*  34 */       this.flip = true;
/*     */     }
/*  36 */     else if (type.equals("2F")) {
/*     */       
/*  38 */       this.rotation = 2;
/*  39 */       this.flip = true;
/*     */     }
/*     */     else {
/*     */       
/*  43 */       Config.warn("NaturalTextures: Unknown type: " + type);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  49 */     return (this.rotation != 2 && this.rotation != 4) ? this.flip : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized BakedQuad getQuad(BakedQuad quadIn, int rotate, boolean flipU) {
/*  54 */     int i = rotate;
/*     */     
/*  56 */     if (flipU)
/*     */     {
/*  58 */       i = rotate | 0x4;
/*     */     }
/*     */     
/*  61 */     if (i > 0 && i < this.quadMaps.length) {
/*     */       
/*  63 */       Map<Object, Object> map = this.quadMaps[i];
/*     */       
/*  65 */       if (map == null) {
/*     */         
/*  67 */         map = new IdentityHashMap<>(1);
/*  68 */         this.quadMaps[i] = map;
/*     */       } 
/*     */       
/*  71 */       BakedQuad bakedquad = (BakedQuad)map.get(quadIn);
/*     */       
/*  73 */       if (bakedquad == null) {
/*     */         
/*  75 */         bakedquad = makeQuad(quadIn, rotate, flipU);
/*  76 */         map.put(quadIn, bakedquad);
/*     */       } 
/*     */       
/*  79 */       return bakedquad;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     return quadIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BakedQuad makeQuad(BakedQuad quad, int rotate, boolean flipU) {
/*  89 */     int[] aint = quad.getVertexData();
/*  90 */     int i = quad.getTintIndex();
/*  91 */     EnumFacing enumfacing = quad.getFace();
/*  92 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/*  94 */     if (!isFullSprite(quad))
/*     */     {
/*  96 */       rotate = 0;
/*     */     }
/*     */     
/*  99 */     aint = transformVertexData(aint, rotate, flipU);
/* 100 */     BakedQuad bakedquad = new BakedQuad(aint, i, enumfacing, textureatlassprite);
/* 101 */     return bakedquad;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] transformVertexData(int[] vertexData, int rotate, boolean flipU) {
/* 106 */     int[] aint = (int[])vertexData.clone();
/* 107 */     int i = 4 - rotate;
/*     */     
/* 109 */     if (flipU)
/*     */     {
/* 111 */       i += 3;
/*     */     }
/*     */     
/* 114 */     i %= 4;
/* 115 */     int j = aint.length / 4;
/*     */     
/* 117 */     for (int k = 0; k < 4; k++) {
/*     */       
/* 119 */       int l = k * j;
/* 120 */       int i1 = i * j;
/* 121 */       aint[i1 + 4] = vertexData[l + 4];
/* 122 */       aint[i1 + 4 + 1] = vertexData[l + 4 + 1];
/*     */       
/* 124 */       if (flipU) {
/*     */         
/* 126 */         i--;
/*     */         
/* 128 */         if (i < 0)
/*     */         {
/* 130 */           i = 3;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 135 */         i++;
/*     */         
/* 137 */         if (i > 3)
/*     */         {
/* 139 */           i = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFullSprite(BakedQuad quad) {
/* 149 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/* 150 */     float f = textureatlassprite.getMinU();
/* 151 */     float f1 = textureatlassprite.getMaxU();
/* 152 */     float f2 = f1 - f;
/* 153 */     float f3 = f2 / 256.0F;
/* 154 */     float f4 = textureatlassprite.getMinV();
/* 155 */     float f5 = textureatlassprite.getMaxV();
/* 156 */     float f6 = f5 - f4;
/* 157 */     float f7 = f6 / 256.0F;
/* 158 */     int[] aint = quad.getVertexData();
/* 159 */     int i = aint.length / 4;
/*     */     
/* 161 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 163 */       int k = j * i;
/* 164 */       float f8 = Float.intBitsToFloat(aint[k + 4]);
/* 165 */       float f9 = Float.intBitsToFloat(aint[k + 4 + 1]);
/*     */       
/* 167 */       if (!equalsDelta(f8, f, f3) && !equalsDelta(f8, f1, f3))
/*     */       {
/* 169 */         return false;
/*     */       }
/*     */       
/* 172 */       if (!equalsDelta(f9, f4, f7) && !equalsDelta(f9, f5, f7))
/*     */       {
/* 174 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean equalsDelta(float x1, float x2, float deltaMax) {
/* 183 */     float f = MathHelper.abs(x1 - x2);
/* 184 */     return (f < deltaMax);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\NaturalProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */