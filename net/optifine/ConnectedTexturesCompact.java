/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.optifine.render.RenderEnv;
/*     */ 
/*     */ 
/*     */ public class ConnectedTexturesCompact
/*     */ {
/*     */   private static final int COMPACT_NONE = 0;
/*     */   private static final int COMPACT_ALL = 1;
/*     */   private static final int COMPACT_V = 2;
/*     */   private static final int COMPACT_H = 3;
/*     */   private static final int COMPACT_HV = 4;
/*     */   
/*     */   public static BakedQuad[] getConnectedTextureCtmCompact(int ctmIndex, ConnectedProperties cp, int side, BakedQuad quad, RenderEnv renderEnv) {
/*  19 */     if (cp.ctmTileIndexes != null && ctmIndex >= 0 && ctmIndex < cp.ctmTileIndexes.length) {
/*     */       
/*  21 */       int i = cp.ctmTileIndexes[ctmIndex];
/*     */       
/*  23 */       if (i >= 0 && i <= cp.tileIcons.length)
/*     */       {
/*  25 */         return getQuadsCompact(i, cp.tileIcons, quad, renderEnv);
/*     */       }
/*     */     } 
/*     */     
/*  29 */     switch (ctmIndex) {
/*     */       
/*     */       case 1:
/*  32 */         return getQuadsCompactH(0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 2:
/*  35 */         return getQuadsCompact(3, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 3:
/*  38 */         return getQuadsCompactH(3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 4:
/*  41 */         return getQuadsCompact4(0, 3, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 5:
/*  44 */         return getQuadsCompact4(3, 0, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 6:
/*  47 */         return getQuadsCompact4(2, 4, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 7:
/*  50 */         return getQuadsCompact4(3, 3, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 8:
/*  53 */         return getQuadsCompact4(4, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 9:
/*  56 */         return getQuadsCompact4(4, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 10:
/*  59 */         return getQuadsCompact4(1, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 11:
/*  62 */         return getQuadsCompact4(1, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 12:
/*  65 */         return getQuadsCompactV(0, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 13:
/*  68 */         return getQuadsCompact4(0, 3, 2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 14:
/*  71 */         return getQuadsCompactV(3, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 15:
/*  74 */         return getQuadsCompact4(3, 0, 1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 16:
/*  77 */         return getQuadsCompact4(2, 4, 0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 17:
/*  80 */         return getQuadsCompact4(4, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 18:
/*  83 */         return getQuadsCompact4(4, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 19:
/*  86 */         return getQuadsCompact4(4, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 20:
/*  89 */         return getQuadsCompact4(1, 4, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 21:
/*  92 */         return getQuadsCompact4(4, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 22:
/*  95 */         return getQuadsCompact4(4, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 23:
/*  98 */         return getQuadsCompact4(4, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 24:
/* 101 */         return getQuadsCompact(2, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 25:
/* 104 */         return getQuadsCompactH(2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 26:
/* 107 */         return getQuadsCompact(1, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 27:
/* 110 */         return getQuadsCompactH(1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 28:
/* 113 */         return getQuadsCompact4(2, 4, 2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 29:
/* 116 */         return getQuadsCompact4(3, 3, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 30:
/* 119 */         return getQuadsCompact4(2, 1, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 31:
/* 122 */         return getQuadsCompact4(3, 3, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 32:
/* 125 */         return getQuadsCompact4(1, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 33:
/* 128 */         return getQuadsCompact4(1, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 34:
/* 131 */         return getQuadsCompact4(4, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 35:
/* 134 */         return getQuadsCompact4(1, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 36:
/* 137 */         return getQuadsCompactV(2, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 37:
/* 140 */         return getQuadsCompact4(2, 1, 0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 38:
/* 143 */         return getQuadsCompactV(1, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 39:
/* 146 */         return getQuadsCompact4(1, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 40:
/* 149 */         return getQuadsCompact4(4, 1, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 41:
/* 152 */         return getQuadsCompact4(1, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 42:
/* 155 */         return getQuadsCompact4(1, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 43:
/* 158 */         return getQuadsCompact4(4, 2, 1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 44:
/* 161 */         return getQuadsCompact4(1, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 45:
/* 164 */         return getQuadsCompact4(4, 1, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 46:
/* 167 */         return getQuadsCompact(4, cp.tileIcons, quad, renderEnv);
/*     */     } 
/*     */     
/* 170 */     return getQuadsCompact(0, cp.tileIcons, quad, renderEnv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactH(int indexLeft, int indexRight, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 176 */     return getQuadsCompact(Dir.LEFT, indexLeft, Dir.RIGHT, indexRight, sprites, side, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactV(int indexUp, int indexDown, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 181 */     return getQuadsCompact(Dir.UP, indexUp, Dir.DOWN, indexDown, sprites, side, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact4(int upLeft, int upRight, int downLeft, int downRight, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 186 */     return (upLeft == upRight) ? ((downLeft == downRight) ? getQuadsCompact(Dir.UP, upLeft, Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.UP, upLeft, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv)) : ((downLeft == downRight) ? getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : ((upLeft == downLeft) ? ((upRight == downRight) ? getQuadsCompact(Dir.LEFT, upLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv)) : ((upRight == downRight) ? getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.DOWN_LEFT, downLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(int index, TextureAtlasSprite[] sprites, BakedQuad quad, RenderEnv renderEnv) {
/* 191 */     TextureAtlasSprite textureatlassprite = sprites[index];
/* 192 */     return ConnectedTextures.getQuads(textureatlassprite, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 197 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 198 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 199 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, Dir dir3, int index3, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 204 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 205 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 206 */     BakedQuad bakedquad2 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
/* 207 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, Dir dir3, int index3, Dir dir4, int index4, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 212 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 213 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 214 */     BakedQuad bakedquad2 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
/* 215 */     BakedQuad bakedquad3 = getQuadCompact(sprites[index4], dir4, side, quad, renderEnv);
/* 216 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2, bakedquad3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite sprite, Dir dir, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 221 */     switch (dir) {
/*     */       
/*     */       case UP:
/* 224 */         return getQuadCompact(sprite, dir, 0, 0, 16, 8, side, quad, renderEnv);
/*     */       
/*     */       case UP_RIGHT:
/* 227 */         return getQuadCompact(sprite, dir, 8, 0, 16, 8, side, quad, renderEnv);
/*     */       
/*     */       case RIGHT:
/* 230 */         return getQuadCompact(sprite, dir, 8, 0, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN_RIGHT:
/* 233 */         return getQuadCompact(sprite, dir, 8, 8, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN:
/* 236 */         return getQuadCompact(sprite, dir, 0, 8, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN_LEFT:
/* 239 */         return getQuadCompact(sprite, dir, 0, 8, 8, 16, side, quad, renderEnv);
/*     */       
/*     */       case LEFT:
/* 242 */         return getQuadCompact(sprite, dir, 0, 0, 8, 16, side, quad, renderEnv);
/*     */       
/*     */       case UP_LEFT:
/* 245 */         return getQuadCompact(sprite, dir, 0, 0, 8, 8, side, quad, renderEnv);
/*     */     } 
/*     */     
/* 248 */     return quad;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite sprite, Dir dir, int x1, int y1, int x2, int y2, int side, BakedQuad quadIn, RenderEnv renderEnv) {
/* 254 */     Map[][] amap = ConnectedTextures.getSpriteQuadCompactMaps();
/*     */     
/* 256 */     if (amap == null)
/*     */     {
/* 258 */       return quadIn;
/*     */     }
/*     */ 
/*     */     
/* 262 */     int i = sprite.getIndexInMap();
/*     */     
/* 264 */     if (i >= 0 && i < amap.length) {
/*     */       
/* 266 */       Map[] amap1 = amap[i];
/*     */       
/* 268 */       if (amap1 == null) {
/*     */         
/* 270 */         amap1 = new Map[Dir.VALUES.length];
/* 271 */         amap[i] = amap1;
/*     */       } 
/*     */       
/* 274 */       Map<BakedQuad, BakedQuad> map = amap1[dir.ordinal()];
/*     */       
/* 276 */       if (map == null) {
/*     */         
/* 278 */         map = new IdentityHashMap<>(1);
/* 279 */         amap1[dir.ordinal()] = map;
/*     */       } 
/*     */       
/* 282 */       BakedQuad bakedquad = map.get(quadIn);
/*     */       
/* 284 */       if (bakedquad == null) {
/*     */         
/* 286 */         bakedquad = makeSpriteQuadCompact(quadIn, sprite, side, x1, y1, x2, y2);
/* 287 */         map.put(quadIn, bakedquad);
/*     */       } 
/*     */       
/* 290 */       return bakedquad;
/*     */     } 
/*     */ 
/*     */     
/* 294 */     return quadIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad makeSpriteQuadCompact(BakedQuad quad, TextureAtlasSprite sprite, int side, int x1, int y1, int x2, int y2) {
/* 301 */     int[] aint = (int[])quad.getVertexData().clone();
/* 302 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/* 304 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 306 */       fixVertexCompact(aint, i, textureatlassprite, sprite, side, x1, y1, x2, y2);
/*     */     }
/*     */     
/* 309 */     BakedQuad bakedquad = new BakedQuad(aint, quad.getTintIndex(), quad.getFace(), sprite);
/* 310 */     return bakedquad;
/*     */   }
/*     */   
/*     */   private static void fixVertexCompact(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo, int side, int x1, int y1, int x2, int y2) {
/*     */     float f5, f6;
/* 315 */     int i = data.length / 4;
/* 316 */     int j = i * vertex;
/* 317 */     float f = Float.intBitsToFloat(data[j + 4]);
/* 318 */     float f1 = Float.intBitsToFloat(data[j + 4 + 1]);
/* 319 */     double d0 = spriteFrom.getSpriteU16(f);
/* 320 */     double d1 = spriteFrom.getSpriteV16(f1);
/* 321 */     float f2 = Float.intBitsToFloat(data[j + 0]);
/* 322 */     float f3 = Float.intBitsToFloat(data[j + 1]);
/* 323 */     float f4 = Float.intBitsToFloat(data[j + 2]);
/*     */ 
/*     */ 
/*     */     
/* 327 */     switch (side) {
/*     */       
/*     */       case 0:
/* 330 */         f5 = f2;
/* 331 */         f6 = 1.0F - f4;
/*     */         break;
/*     */       
/*     */       case 1:
/* 335 */         f5 = f2;
/* 336 */         f6 = f4;
/*     */         break;
/*     */       
/*     */       case 2:
/* 340 */         f5 = 1.0F - f2;
/* 341 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 3:
/* 345 */         f5 = f2;
/* 346 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 4:
/* 350 */         f5 = f4;
/* 351 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 5:
/* 355 */         f5 = 1.0F - f4;
/* 356 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 363 */     float f7 = 15.968F;
/* 364 */     float f8 = 15.968F;
/*     */     
/* 366 */     if (d0 < x1) {
/*     */       
/* 368 */       f5 = (float)(f5 + (x1 - d0) / f7);
/* 369 */       d0 = x1;
/*     */     } 
/*     */     
/* 372 */     if (d0 > x2) {
/*     */       
/* 374 */       f5 = (float)(f5 - (d0 - x2) / f7);
/* 375 */       d0 = x2;
/*     */     } 
/*     */     
/* 378 */     if (d1 < y1) {
/*     */       
/* 380 */       f6 = (float)(f6 + (y1 - d1) / f8);
/* 381 */       d1 = y1;
/*     */     } 
/*     */     
/* 384 */     if (d1 > y2) {
/*     */       
/* 386 */       f6 = (float)(f6 - (d1 - y2) / f8);
/* 387 */       d1 = y2;
/*     */     } 
/*     */     
/* 390 */     switch (side) {
/*     */       
/*     */       case 0:
/* 393 */         f2 = f5;
/* 394 */         f4 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 1:
/* 398 */         f2 = f5;
/* 399 */         f4 = f6;
/*     */         break;
/*     */       
/*     */       case 2:
/* 403 */         f2 = 1.0F - f5;
/* 404 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 3:
/* 408 */         f2 = f5;
/* 409 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 4:
/* 413 */         f4 = f5;
/* 414 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 5:
/* 418 */         f4 = 1.0F - f5;
/* 419 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 426 */     data[j + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(d0));
/* 427 */     data[j + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(d1));
/* 428 */     data[j + 0] = Float.floatToRawIntBits(f2);
/* 429 */     data[j + 1] = Float.floatToRawIntBits(f3);
/* 430 */     data[j + 2] = Float.floatToRawIntBits(f4);
/*     */   }
/*     */   
/*     */   private enum Dir
/*     */   {
/* 435 */     UP,
/* 436 */     UP_RIGHT,
/* 437 */     RIGHT,
/* 438 */     DOWN_RIGHT,
/* 439 */     DOWN,
/* 440 */     DOWN_LEFT,
/* 441 */     LEFT,
/* 442 */     UP_LEFT;
/*     */     
/* 444 */     public static final Dir[] VALUES = values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\ConnectedTexturesCompact.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */