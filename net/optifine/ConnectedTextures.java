/*      */ package net.optifine;
/*      */ 
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.EnumMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockPane;
/*      */ import net.minecraft.block.BlockStainedGlassPane;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.Matches;
/*      */ import net.optifine.model.BlockModelUtils;
/*      */ import net.optifine.model.ListQuadsOverlay;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.TileEntityUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectedTextures
/*      */ {
/*   47 */   private static Map[] spriteQuadMaps = null;
/*   48 */   private static Map[] spriteQuadFullMaps = null;
/*   49 */   private static Map[][] spriteQuadCompactMaps = (Map[][])null;
/*   50 */   private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
/*   51 */   private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])null;
/*      */   private static boolean multipass = false;
/*      */   protected static final int UNKNOWN = -1;
/*      */   protected static final int Y_NEG_DOWN = 0;
/*      */   protected static final int Y_POS_UP = 1;
/*      */   protected static final int Z_NEG_NORTH = 2;
/*      */   protected static final int Z_POS_SOUTH = 3;
/*      */   protected static final int X_NEG_WEST = 4;
/*      */   protected static final int X_POS_EAST = 5;
/*      */   private static final int Y_AXIS = 0;
/*      */   private static final int Z_AXIS = 1;
/*      */   private static final int X_AXIS = 2;
/*   63 */   public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
/*   64 */   private static TextureAtlasSprite emptySprite = null;
/*   65 */   private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH };
/*   66 */   private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH };
/*   67 */   private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[] { BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP };
/*   68 */   private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP };
/*   69 */   private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP };
/*   70 */   private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[] { BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP };
/*   71 */   private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN };
/*   72 */   private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN };
/*   73 */   private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[] { BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST };
/*   74 */   private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[] { BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST };
/*   75 */   private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[] { BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
/*   76 */   private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[] { BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST };
/*   77 */   private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[] { BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH };
/*   78 */   private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[] { BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH };
/*   79 */   private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST };
/*   80 */   private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH };
/*   81 */   public static final TextureAtlasSprite SPRITE_DEFAULT = new TextureAtlasSprite("<default>");
/*      */ 
/*      */   
/*      */   public static BakedQuad[] getConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
/*   85 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*   87 */     if (textureatlassprite == null)
/*      */     {
/*   89 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     }
/*      */ 
/*      */     
/*   93 */     Block block = blockState.getBlock();
/*      */     
/*   95 */     if (skipConnectedTexture(blockAccess, blockState, blockPos, quad, renderEnv)) {
/*      */       
/*   97 */       quad = getQuad(emptySprite, quad);
/*   98 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     } 
/*      */ 
/*      */     
/*  102 */     EnumFacing enumfacing = quad.getFace();
/*  103 */     BakedQuad[] abakedquad = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, enumfacing, quad, renderEnv);
/*  104 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean skipConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
/*  111 */     Block block = blockState.getBlock();
/*      */     
/*  113 */     if (block instanceof BlockPane) {
/*      */       
/*  115 */       TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */       
/*  117 */       if (textureatlassprite.getIconName().startsWith("minecraft:blocks/glass_pane_top")) {
/*      */         
/*  119 */         IBlockState iblockstate1 = blockAccess.getBlockState(blockPos.offset(quad.getFace()));
/*  120 */         return (iblockstate1 == blockState);
/*      */       } 
/*      */     } 
/*      */     
/*  124 */     if (block instanceof BlockPane) {
/*      */       
/*  126 */       EnumFacing enumfacing = quad.getFace();
/*      */       
/*  128 */       if (enumfacing != EnumFacing.UP && enumfacing != EnumFacing.DOWN)
/*      */       {
/*  130 */         return false;
/*      */       }
/*      */       
/*  133 */       if (!quad.isFaceQuad())
/*      */       {
/*  135 */         return false;
/*      */       }
/*      */       
/*  138 */       BlockPos blockpos = blockPos.offset(quad.getFace());
/*  139 */       IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/*      */       
/*  141 */       if (iblockstate.getBlock() != block)
/*      */       {
/*  143 */         return false;
/*      */       }
/*      */       
/*  146 */       if (block == Blocks.stained_glass_pane && iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR) != blockState.getValue((IProperty)BlockStainedGlassPane.COLOR))
/*      */       {
/*  148 */         return false;
/*      */       }
/*      */       
/*  151 */       iblockstate = iblockstate.getBlock().getActualState(iblockstate, blockAccess, blockpos);
/*  152 */       double d0 = quad.getMidX();
/*      */       
/*  154 */       if (d0 < 0.4D) {
/*      */         
/*  156 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.WEST)).booleanValue())
/*      */         {
/*  158 */           return true;
/*      */         }
/*      */       }
/*  161 */       else if (d0 > 0.6D) {
/*      */         
/*  163 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.EAST)).booleanValue())
/*      */         {
/*  165 */           return true;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  170 */         double d1 = quad.getMidZ();
/*      */         
/*  172 */         if (d1 < 0.4D) {
/*      */           
/*  174 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.NORTH)).booleanValue())
/*      */           {
/*  176 */             return true;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  181 */           if (d1 <= 0.6D)
/*      */           {
/*  183 */             return true;
/*      */           }
/*      */           
/*  186 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.SOUTH)).booleanValue())
/*      */           {
/*  188 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  194 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static BakedQuad[] getQuads(TextureAtlasSprite sprite, BakedQuad quadIn, RenderEnv renderEnv) {
/*  199 */     if (sprite == null)
/*      */     {
/*  201 */       return null;
/*      */     }
/*  203 */     if (sprite == SPRITE_DEFAULT)
/*      */     {
/*  205 */       return renderEnv.getArrayQuadsCtm(quadIn);
/*      */     }
/*      */ 
/*      */     
/*  209 */     BakedQuad bakedquad = getQuad(sprite, quadIn);
/*  210 */     BakedQuad[] abakedquad = renderEnv.getArrayQuadsCtm(bakedquad);
/*  211 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad getQuad(TextureAtlasSprite sprite, BakedQuad quadIn) {
/*  217 */     if (spriteQuadMaps == null)
/*      */     {
/*  219 */       return quadIn;
/*      */     }
/*      */ 
/*      */     
/*  223 */     int i = sprite.getIndexInMap();
/*      */     
/*  225 */     if (i >= 0 && i < spriteQuadMaps.length) {
/*      */       
/*  227 */       Map<Object, Object> map = spriteQuadMaps[i];
/*      */       
/*  229 */       if (map == null) {
/*      */         
/*  231 */         map = new IdentityHashMap<>(1);
/*  232 */         spriteQuadMaps[i] = map;
/*      */       } 
/*      */       
/*  235 */       BakedQuad bakedquad = (BakedQuad)map.get(quadIn);
/*      */       
/*  237 */       if (bakedquad == null) {
/*      */         
/*  239 */         bakedquad = makeSpriteQuad(quadIn, sprite);
/*  240 */         map.put(quadIn, bakedquad);
/*      */       } 
/*      */       
/*  243 */       return bakedquad;
/*      */     } 
/*      */ 
/*      */     
/*  247 */     return quadIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad getQuadFull(TextureAtlasSprite sprite, BakedQuad quadIn, int tintIndex) {
/*  254 */     if (spriteQuadFullMaps == null)
/*      */     {
/*  256 */       return null;
/*      */     }
/*  258 */     if (sprite == null)
/*      */     {
/*  260 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  264 */     int i = sprite.getIndexInMap();
/*      */     
/*  266 */     if (i >= 0 && i < spriteQuadFullMaps.length) {
/*      */       
/*  268 */       Map<EnumFacing, Object> map = spriteQuadFullMaps[i];
/*      */       
/*  270 */       if (map == null) {
/*      */         
/*  272 */         map = new EnumMap<>(EnumFacing.class);
/*  273 */         spriteQuadFullMaps[i] = map;
/*      */       } 
/*      */       
/*  276 */       EnumFacing enumfacing = quadIn.getFace();
/*  277 */       BakedQuad bakedquad = (BakedQuad)map.get(enumfacing);
/*      */       
/*  279 */       if (bakedquad == null) {
/*      */         
/*  281 */         bakedquad = BlockModelUtils.makeBakedQuad(enumfacing, sprite, tintIndex);
/*  282 */         map.put(enumfacing, bakedquad);
/*      */       } 
/*      */       
/*  285 */       return bakedquad;
/*      */     } 
/*      */ 
/*      */     
/*  289 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad makeSpriteQuad(BakedQuad quad, TextureAtlasSprite sprite) {
/*  296 */     int[] aint = (int[])quad.getVertexData().clone();
/*  297 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  299 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  301 */       fixVertex(aint, i, textureatlassprite, sprite);
/*      */     }
/*      */     
/*  304 */     BakedQuad bakedquad = new BakedQuad(aint, quad.getTintIndex(), quad.getFace(), sprite);
/*  305 */     return bakedquad;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void fixVertex(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo) {
/*  310 */     int i = data.length / 4;
/*  311 */     int j = i * vertex;
/*  312 */     float f = Float.intBitsToFloat(data[j + 4]);
/*  313 */     float f1 = Float.intBitsToFloat(data[j + 4 + 1]);
/*  314 */     double d0 = spriteFrom.getSpriteU16(f);
/*  315 */     double d1 = spriteFrom.getSpriteV16(f1);
/*  316 */     data[j + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(d0));
/*  317 */     data[j + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(d1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureMultiPass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing side, BakedQuad quad, RenderEnv renderEnv) {
/*  322 */     BakedQuad[] abakedquad = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
/*      */     
/*  324 */     if (!multipass)
/*      */     {
/*  326 */       return abakedquad;
/*      */     }
/*  328 */     if (abakedquad.length == 1 && abakedquad[0] == quad)
/*      */     {
/*  330 */       return abakedquad;
/*      */     }
/*      */ 
/*      */     
/*  334 */     List<BakedQuad> list = renderEnv.getListQuadsCtmMultipass(abakedquad);
/*      */     
/*  336 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  338 */       BakedQuad bakedquad = list.get(i);
/*  339 */       BakedQuad bakedquad1 = bakedquad;
/*      */       
/*  341 */       for (int j = 0; j < 3; j++) {
/*      */         
/*  343 */         BakedQuad[] abakedquad1 = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, bakedquad1, false, j + 1, renderEnv);
/*      */         
/*  345 */         if (abakedquad1.length != 1 || abakedquad1[0] == bakedquad1) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  350 */         bakedquad1 = abakedquad1[0];
/*      */       } 
/*      */       
/*  353 */       list.set(i, bakedquad1);
/*      */     } 
/*      */     
/*  356 */     for (int k = 0; k < abakedquad.length; k++)
/*      */     {
/*  358 */       abakedquad[k] = list.get(k);
/*      */     }
/*      */     
/*  361 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BakedQuad[] getConnectedTextureSingle(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, BakedQuad quad, boolean checkBlocks, int pass, RenderEnv renderEnv) {
/*  367 */     Block block = blockState.getBlock();
/*      */     
/*  369 */     if (!(blockState instanceof BlockStateBase))
/*      */     {
/*  371 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     }
/*      */ 
/*      */     
/*  375 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  376 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  378 */     if (tileProperties != null) {
/*      */       
/*  380 */       int i = textureatlassprite.getIndexInMap();
/*      */       
/*  382 */       if (i >= 0 && i < tileProperties.length) {
/*      */         
/*  384 */         ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */         
/*  386 */         if (aconnectedproperties != null) {
/*      */           
/*  388 */           int j = getSide(facing);
/*      */           
/*  390 */           for (int k = 0; k < aconnectedproperties.length; k++) {
/*      */             
/*  392 */             ConnectedProperties connectedproperties = aconnectedproperties[k];
/*      */             
/*  394 */             if (connectedproperties != null && connectedproperties.matchesBlockId(blockstatebase.getBlockId())) {
/*      */               
/*  396 */               BakedQuad[] abakedquad = getConnectedTexture(connectedproperties, blockAccess, blockstatebase, blockPos, j, quad, pass, renderEnv);
/*      */               
/*  398 */               if (abakedquad != null)
/*      */               {
/*  400 */                 return abakedquad;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  408 */     if (blockProperties != null && checkBlocks) {
/*      */       
/*  410 */       int l = renderEnv.getBlockId();
/*      */       
/*  412 */       if (l >= 0 && l < blockProperties.length) {
/*      */         
/*  414 */         ConnectedProperties[] aconnectedproperties1 = blockProperties[l];
/*      */         
/*  416 */         if (aconnectedproperties1 != null) {
/*      */           
/*  418 */           int i1 = getSide(facing);
/*      */           
/*  420 */           for (int j1 = 0; j1 < aconnectedproperties1.length; j1++) {
/*      */             
/*  422 */             ConnectedProperties connectedproperties1 = aconnectedproperties1[j1];
/*      */             
/*  424 */             if (connectedproperties1 != null && connectedproperties1.matchesIcon(textureatlassprite)) {
/*      */               
/*  426 */               BakedQuad[] abakedquad1 = getConnectedTexture(connectedproperties1, blockAccess, blockstatebase, blockPos, i1, quad, pass, renderEnv);
/*      */               
/*  428 */               if (abakedquad1 != null)
/*      */               {
/*  430 */                 return abakedquad1;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  438 */     return renderEnv.getArrayQuadsCtm(quad);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSide(EnumFacing facing) {
/*  444 */     if (facing == null)
/*      */     {
/*  446 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  450 */     switch (facing) {
/*      */       
/*      */       case DOWN:
/*  453 */         return 0;
/*      */       
/*      */       case UP:
/*  456 */         return 1;
/*      */       
/*      */       case EAST:
/*  459 */         return 5;
/*      */       
/*      */       case WEST:
/*  462 */         return 4;
/*      */       
/*      */       case NORTH:
/*  465 */         return 2;
/*      */       
/*      */       case SOUTH:
/*  468 */         return 3;
/*      */     } 
/*      */     
/*  471 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EnumFacing getFacing(int side) {
/*  478 */     switch (side) {
/*      */       
/*      */       case 0:
/*  481 */         return EnumFacing.DOWN;
/*      */       
/*      */       case 1:
/*  484 */         return EnumFacing.UP;
/*      */       
/*      */       case 2:
/*  487 */         return EnumFacing.NORTH;
/*      */       
/*      */       case 3:
/*  490 */         return EnumFacing.SOUTH;
/*      */       
/*      */       case 4:
/*  493 */         return EnumFacing.WEST;
/*      */       
/*      */       case 5:
/*  496 */         return EnumFacing.EAST;
/*      */     } 
/*      */     
/*  499 */     return EnumFacing.UP;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTexture(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, BakedQuad quad, int pass, RenderEnv renderEnv) {
/*  505 */     int i = 0;
/*  506 */     int j = blockState.getMetadata();
/*  507 */     int k = j;
/*  508 */     Block block = blockState.getBlock();
/*      */     
/*  510 */     if (block instanceof net.minecraft.block.BlockRotatedPillar) {
/*      */       
/*  512 */       i = getWoodAxis(side, j);
/*      */       
/*  514 */       if (cp.getMetadataMax() <= 3)
/*      */       {
/*  516 */         k = j & 0x3;
/*      */       }
/*      */     } 
/*      */     
/*  520 */     if (block instanceof net.minecraft.block.BlockQuartz) {
/*      */       
/*  522 */       i = getQuartzAxis(side, j);
/*      */       
/*  524 */       if (cp.getMetadataMax() <= 2 && k > 2)
/*      */       {
/*  526 */         k = 2;
/*      */       }
/*      */     } 
/*      */     
/*  530 */     if (!cp.matchesBlock(blockState.getBlockId(), k))
/*      */     {
/*  532 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  536 */     if (side >= 0 && cp.faces != 63) {
/*      */       
/*  538 */       int l = side;
/*      */       
/*  540 */       if (i != 0)
/*      */       {
/*  542 */         l = fixSideByAxis(side, i);
/*      */       }
/*      */       
/*  545 */       if ((1 << l & cp.faces) == 0)
/*      */       {
/*  547 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  551 */     int i1 = blockPos.getY();
/*      */     
/*  553 */     if (cp.heights != null && !cp.heights.isInRange(i1))
/*      */     {
/*  555 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  559 */     if (cp.biomes != null) {
/*      */       
/*  561 */       BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(blockPos);
/*      */       
/*  563 */       if (!cp.matchesBiome(biomegenbase))
/*      */       {
/*  565 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  569 */     if (cp.nbtName != null) {
/*      */       
/*  571 */       String s = TileEntityUtils.getTileEntityName(blockAccess, blockPos);
/*      */       
/*  573 */       if (!cp.nbtName.matchesValue(s))
/*      */       {
/*  575 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  579 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  581 */     switch (cp.method) {
/*      */       
/*      */       case 1:
/*  584 */         return getQuads(getConnectedTextureCtm(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j, renderEnv), quad, renderEnv);
/*      */       
/*      */       case 2:
/*  587 */         return getQuads(getConnectedTextureHorizontal(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 3:
/*  590 */         return getQuads(getConnectedTextureTop(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 4:
/*  593 */         return getQuads(getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side), quad, renderEnv);
/*      */       
/*      */       case 5:
/*  596 */         return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
/*      */       
/*      */       case 6:
/*  599 */         return getQuads(getConnectedTextureVertical(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 7:
/*  602 */         return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
/*      */       
/*      */       case 8:
/*  605 */         return getQuads(getConnectedTextureHorizontalVertical(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 9:
/*  608 */         return getQuads(getConnectedTextureVerticalHorizontal(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 10:
/*  611 */         if (pass == 0)
/*      */         {
/*  613 */           return getConnectedTextureCtmCompact(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */         }
/*      */       
/*      */       default:
/*  617 */         return null;
/*      */       
/*      */       case 11:
/*  620 */         return getConnectedTextureOverlay(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */       
/*      */       case 12:
/*  623 */         return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
/*      */       
/*      */       case 13:
/*  626 */         return getConnectedTextureOverlayRandom(cp, blockAccess, blockState, blockPos, side, quad, renderEnv);
/*      */       
/*      */       case 14:
/*  629 */         return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
/*      */       case 15:
/*      */         break;
/*  632 */     }  return getConnectedTextureOverlayCtm(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int fixSideByAxis(int side, int vertAxis) {
/*  640 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/*  643 */         return side;
/*      */       
/*      */       case 1:
/*  646 */         switch (side) {
/*      */           
/*      */           case 0:
/*  649 */             return 2;
/*      */           
/*      */           case 1:
/*  652 */             return 3;
/*      */           
/*      */           case 2:
/*  655 */             return 1;
/*      */           
/*      */           case 3:
/*  658 */             return 0;
/*      */         } 
/*      */         
/*  661 */         return side;
/*      */ 
/*      */       
/*      */       case 2:
/*  665 */         switch (side) {
/*      */           
/*      */           case 0:
/*  668 */             return 4;
/*      */           
/*      */           case 1:
/*  671 */             return 5;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  676 */             return side;
/*      */           
/*      */           case 4:
/*  679 */             return 1;
/*      */           case 5:
/*      */             break;
/*  682 */         }  return 0;
/*      */     } 
/*      */ 
/*      */     
/*  686 */     return side;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getWoodAxis(int side, int metadata) {
/*  692 */     int i = (metadata & 0xC) >> 2;
/*      */     
/*  694 */     switch (i) {
/*      */       
/*      */       case 1:
/*  697 */         return 2;
/*      */       
/*      */       case 2:
/*  700 */         return 1;
/*      */     } 
/*      */     
/*  703 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getQuartzAxis(int side, int metadata) {
/*  709 */     switch (metadata) {
/*      */       
/*      */       case 3:
/*  712 */         return 2;
/*      */       
/*      */       case 4:
/*  715 */         return 1;
/*      */     } 
/*      */     
/*  718 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side) {
/*  724 */     if (cp.tileIcons.length == 1)
/*      */     {
/*  726 */       return cp.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/*  730 */     int i = side / cp.symmetry * cp.symmetry;
/*      */     
/*  732 */     if (cp.linked) {
/*      */       
/*  734 */       BlockPos blockpos = blockPos.down();
/*      */       
/*  736 */       for (IBlockState iblockstate = blockAccess.getBlockState(blockpos); iblockstate.getBlock() == blockState.getBlock(); iblockstate = blockAccess.getBlockState(blockpos)) {
/*      */         
/*  738 */         blockPos = blockpos;
/*  739 */         blockpos = blockpos.down();
/*      */         
/*  741 */         if (blockpos.getY() < 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  748 */     int l = Config.getRandom(blockPos, i) & Integer.MAX_VALUE;
/*      */     
/*  750 */     for (int i1 = 0; i1 < cp.randomLoops; i1++)
/*      */     {
/*  752 */       l = Config.intHash(l);
/*      */     }
/*      */     
/*  755 */     int j1 = 0;
/*      */     
/*  757 */     if (cp.weights == null) {
/*      */       
/*  759 */       j1 = l % cp.tileIcons.length;
/*      */     }
/*      */     else {
/*      */       
/*  763 */       int j = l % cp.sumAllWeights;
/*  764 */       int[] aint = cp.sumWeights;
/*      */       
/*  766 */       for (int k = 0; k < aint.length; k++) {
/*      */         
/*  768 */         if (j < aint[k]) {
/*      */           
/*  770 */           j1 = k;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  776 */     return cp.tileIcons[j1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties cp) {
/*  782 */     return cp.tileIcons[0];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties cp, BlockPos blockPos, int side) {
/*  787 */     if (cp.tileIcons.length == 1)
/*      */     {
/*  789 */       return cp.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/*  793 */     int i = blockPos.getX();
/*  794 */     int j = blockPos.getY();
/*  795 */     int k = blockPos.getZ();
/*  796 */     int l = 0;
/*  797 */     int i1 = 0;
/*      */     
/*  799 */     switch (side) {
/*      */       
/*      */       case 0:
/*  802 */         l = i;
/*  803 */         i1 = -k - 1;
/*      */         break;
/*      */       
/*      */       case 1:
/*  807 */         l = i;
/*  808 */         i1 = k;
/*      */         break;
/*      */       
/*      */       case 2:
/*  812 */         l = -i - 1;
/*  813 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 3:
/*  817 */         l = i;
/*  818 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 4:
/*  822 */         l = k;
/*  823 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 5:
/*  827 */         l = -k - 1;
/*  828 */         i1 = -j;
/*      */         break;
/*      */     } 
/*  831 */     l %= cp.width;
/*  832 */     i1 %= cp.height;
/*      */     
/*  834 */     if (l < 0)
/*      */     {
/*  836 */       l += cp.width;
/*      */     }
/*      */     
/*  839 */     if (i1 < 0)
/*      */     {
/*  841 */       i1 += cp.height;
/*      */     }
/*      */     
/*  844 */     int j1 = i1 * cp.width + l;
/*  845 */     return cp.tileIcons[j1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
/*  851 */     int i = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
/*  852 */     return cp.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad[] getConnectedTextureCtmCompact(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*  857 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*  858 */     int i = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, textureatlassprite, metadata, renderEnv);
/*  859 */     return ConnectedTexturesCompact.getConnectedTextureCtmCompact(i, cp, side, quad, renderEnv);
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlay(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*      */     Object dirEdges;
/*  864 */     if (!quad.isFullQuad())
/*      */     {
/*  866 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  870 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*  871 */     BlockDir[] ablockdir = getSideDirections(side, vertAxis);
/*  872 */     boolean[] aboolean = renderEnv.getBorderFlags();
/*      */     
/*  874 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  876 */       aboolean[i] = isNeighbourOverlay(cp, blockAccess, blockState, ablockdir[i].offset(blockPos), side, textureatlassprite, metadata);
/*      */     }
/*      */     
/*  879 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  884 */       if (!aboolean[0] || !aboolean[1] || !aboolean[2] || !aboolean[3]) {
/*      */         
/*  886 */         if (aboolean[0] && aboolean[1] && aboolean[2]) {
/*      */           
/*  888 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
/*  889 */           Object object = null;
/*  890 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  893 */         if (aboolean[0] && aboolean[2] && aboolean[3]) {
/*      */           
/*  895 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
/*  896 */           Object object = null;
/*  897 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  900 */         if (aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */           
/*  902 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
/*  903 */           Object object = null;
/*  904 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  907 */         if (aboolean[0] && aboolean[1] && aboolean[3]) {
/*      */           
/*  909 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
/*  910 */           Object object = null;
/*  911 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  914 */         BlockDir[] ablockdir1 = getEdgeDirections(side, vertAxis);
/*  915 */         boolean[] aboolean1 = renderEnv.getBorderFlags2();
/*      */         
/*  917 */         for (int j = 0; j < 4; j++)
/*      */         {
/*  919 */           aboolean1[j] = isNeighbourOverlay(cp, blockAccess, blockState, ablockdir1[j].offset(blockPos), side, textureatlassprite, metadata);
/*      */         }
/*      */         
/*  922 */         if (aboolean[1] && aboolean[2]) {
/*      */           
/*  924 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  926 */           if (aboolean1[3])
/*      */           {
/*  928 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  931 */           Object object4 = null;
/*  932 */           return (BakedQuad[])object4;
/*      */         } 
/*      */         
/*  935 */         if (aboolean[0] && aboolean[2]) {
/*      */           
/*  937 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  939 */           if (aboolean1[2])
/*      */           {
/*  941 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  944 */           Object object3 = null;
/*  945 */           return (BakedQuad[])object3;
/*      */         } 
/*      */         
/*  948 */         if (aboolean[1] && aboolean[3]) {
/*      */           
/*  950 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  952 */           if (aboolean1[1])
/*      */           {
/*  954 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  957 */           Object object2 = null;
/*  958 */           return (BakedQuad[])object2;
/*      */         } 
/*      */         
/*  961 */         if (aboolean[0] && aboolean[3]) {
/*      */           
/*  963 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  965 */           if (aboolean1[0])
/*      */           {
/*  967 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  970 */           Object object1 = null;
/*  971 */           return (BakedQuad[])object1;
/*      */         } 
/*      */         
/*  974 */         boolean[] aboolean2 = renderEnv.getBorderFlags3();
/*      */         
/*  976 */         for (int k = 0; k < 4; k++)
/*      */         {
/*  978 */           aboolean2[k] = isNeighbourMatching(cp, blockAccess, blockState, ablockdir[k].offset(blockPos), side, textureatlassprite, metadata);
/*      */         }
/*      */         
/*  981 */         if (aboolean[0])
/*      */         {
/*  983 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  986 */         if (aboolean[1])
/*      */         {
/*  988 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  991 */         if (aboolean[2])
/*      */         {
/*  993 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  996 */         if (aboolean[3])
/*      */         {
/*  998 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1001 */         if (aboolean1[0] && (aboolean2[1] || aboolean2[2]) && !aboolean[1] && !aboolean[2])
/*      */         {
/* 1003 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1006 */         if (aboolean1[1] && (aboolean2[0] || aboolean2[2]) && !aboolean[0] && !aboolean[2])
/*      */         {
/* 1008 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1011 */         if (aboolean1[2] && (aboolean2[1] || aboolean2[3]) && !aboolean[1] && !aboolean[3])
/*      */         {
/* 1013 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1016 */         if (aboolean1[3] && (aboolean2[0] || aboolean2[3]) && !aboolean[0] && !aboolean[3])
/*      */         {
/* 1018 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1021 */         Object object5 = null;
/* 1022 */         return (BakedQuad[])object5;
/*      */       } 
/*      */       
/* 1025 */       listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState);
/* 1026 */       dirEdges = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1030 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1032 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1036 */     return (BakedQuad[])dirEdges;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayFixed(ConnectedProperties cp, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/* 1042 */     if (!quad.isFullQuad())
/*      */     {
/* 1044 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1048 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1053 */       TextureAtlasSprite textureatlassprite = getConnectedTextureFixed(cp);
/*      */       
/* 1055 */       if (textureatlassprite != null)
/*      */       {
/* 1057 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1060 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1064 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1066 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1070 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayRandom(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/* 1076 */     if (!quad.isFullQuad())
/*      */     {
/* 1078 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1082 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1087 */       TextureAtlasSprite textureatlassprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
/*      */       
/* 1089 */       if (textureatlassprite != null)
/*      */       {
/* 1091 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1094 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1098 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1100 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1104 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayRepeat(ConnectedProperties cp, BlockPos blockPos, int side, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/* 1110 */     if (!quad.isFullQuad())
/*      */     {
/* 1112 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1116 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1121 */       TextureAtlasSprite textureatlassprite = getConnectedTextureRepeat(cp, blockPos, side);
/*      */       
/* 1123 */       if (textureatlassprite != null)
/*      */       {
/* 1125 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1128 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1132 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1134 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1138 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayCtm(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*      */     Object object;
/* 1144 */     if (!quad.isFullQuad())
/*      */     {
/* 1146 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1150 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1155 */       TextureAtlasSprite textureatlassprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.getSprite(), metadata, renderEnv);
/*      */       
/* 1157 */       if (textureatlassprite != null)
/*      */       {
/* 1159 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1162 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1166 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1168 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1172 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockDir[] getSideDirections(int side, int vertAxis) {
/* 1178 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1181 */         return SIDES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/* 1184 */         return SIDES_Y_POS_UP;
/*      */       
/*      */       case 2:
/* 1187 */         if (vertAxis == 1)
/*      */         {
/* 1189 */           return SIDES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/* 1192 */         return SIDES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/* 1195 */         return SIDES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/* 1198 */         return SIDES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/* 1201 */         if (vertAxis == 2)
/*      */         {
/* 1203 */           return SIDES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/* 1206 */         return SIDES_X_POS_EAST;
/*      */     } 
/*      */     
/* 1209 */     throw new IllegalArgumentException("Unknown side: " + side);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockDir[] getEdgeDirections(int side, int vertAxis) {
/* 1215 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1218 */         return EDGES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/* 1221 */         return EDGES_Y_POS_UP;
/*      */       
/*      */       case 2:
/* 1224 */         if (vertAxis == 1)
/*      */         {
/* 1226 */           return EDGES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/* 1229 */         return EDGES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/* 1232 */         return EDGES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/* 1235 */         return EDGES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/* 1238 */         if (vertAxis == 2)
/*      */         {
/* 1240 */           return EDGES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/* 1243 */         return EDGES_X_POS_EAST;
/*      */     } 
/*      */     
/* 1246 */     throw new IllegalArgumentException("Unknown side: " + side);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Map[][] getSpriteQuadCompactMaps() {
/* 1252 */     return spriteQuadCompactMaps;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getConnectedTextureCtmIndex(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
/* 1257 */     boolean[] aboolean = renderEnv.getBorderFlags();
/*      */     
/* 1259 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1262 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1263 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1264 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1265 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */         
/* 1267 */         if (cp.innerSeams) {
/*      */           
/* 1269 */           BlockPos blockpos6 = blockPos.down();
/* 1270 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos6.west(), side, icon, metadata));
/* 1271 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos6.east(), side, icon, metadata));
/* 1272 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos6.north(), side, icon, metadata));
/* 1273 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos6.south(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1279 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1280 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1281 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1282 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */         
/* 1284 */         if (cp.innerSeams) {
/*      */           
/* 1286 */           BlockPos blockpos5 = blockPos.up();
/* 1287 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos5.west(), side, icon, metadata));
/* 1288 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos5.east(), side, icon, metadata));
/* 1289 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos5.south(), side, icon, metadata));
/* 1290 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos5.north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1296 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1297 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1298 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1299 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1301 */         if (cp.innerSeams) {
/*      */           
/* 1303 */           BlockPos blockpos4 = blockPos.north();
/* 1304 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos4.east(), side, icon, metadata));
/* 1305 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos4.west(), side, icon, metadata));
/* 1306 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos4.down(), side, icon, metadata));
/* 1307 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos4.up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1310 */         if (vertAxis == 1) {
/*      */           
/* 1312 */           switchValues(0, 1, aboolean);
/* 1313 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1319 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1320 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1321 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1322 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1324 */         if (cp.innerSeams) {
/*      */           
/* 1326 */           BlockPos blockpos3 = blockPos.south();
/* 1327 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos3.west(), side, icon, metadata));
/* 1328 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos3.east(), side, icon, metadata));
/* 1329 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos3.down(), side, icon, metadata));
/* 1330 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos3.up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1336 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1337 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1338 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1339 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1341 */         if (cp.innerSeams) {
/*      */           
/* 1343 */           BlockPos blockpos2 = blockPos.west();
/* 1344 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos2.north(), side, icon, metadata));
/* 1345 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos2.south(), side, icon, metadata));
/* 1346 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos2.down(), side, icon, metadata));
/* 1347 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos2.up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1353 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1354 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1355 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1356 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1358 */         if (cp.innerSeams) {
/*      */           
/* 1360 */           BlockPos blockpos = blockPos.east();
/* 1361 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos.south(), side, icon, metadata));
/* 1362 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos.north(), side, icon, metadata));
/* 1363 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos.down(), side, icon, metadata));
/* 1364 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos.up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1367 */         if (vertAxis == 2) {
/*      */           
/* 1369 */           switchValues(0, 1, aboolean);
/* 1370 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1374 */     int i = 0;
/*      */     
/* 1376 */     if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & (!aboolean[2] ? 1 : 0) & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1378 */       i = 3;
/*      */     }
/* 1380 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & (!aboolean[2] ? 1 : 0) & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1382 */       i = 1;
/*      */     }
/* 1384 */     else if (((!aboolean[0] ? 1 : 0) & (!aboolean[1] ? 1 : 0) & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1386 */       i = 12;
/*      */     }
/* 1388 */     else if (((!aboolean[0] ? 1 : 0) & (!aboolean[1] ? 1 : 0) & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1390 */       i = 36;
/*      */     }
/* 1392 */     else if ((aboolean[0] & aboolean[1] & (!aboolean[2] ? 1 : 0) & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1394 */       i = 2;
/*      */     }
/* 1396 */     else if (((!aboolean[0] ? 1 : 0) & (!aboolean[1] ? 1 : 0) & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1398 */       i = 24;
/*      */     }
/* 1400 */     else if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1402 */       i = 15;
/*      */     }
/* 1404 */     else if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1406 */       i = 39;
/*      */     }
/* 1408 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1410 */       i = 13;
/*      */     }
/* 1412 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1414 */       i = 37;
/*      */     }
/* 1416 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1418 */       i = 25;
/*      */     }
/* 1420 */     else if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1422 */       i = 27;
/*      */     }
/* 1424 */     else if ((aboolean[0] & aboolean[1] & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1426 */       i = 38;
/*      */     }
/* 1428 */     else if ((aboolean[0] & aboolean[1] & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1430 */       i = 14;
/*      */     }
/* 1432 */     else if ((aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1434 */       i = 26;
/*      */     } 
/*      */     
/* 1437 */     if (i == 0)
/*      */     {
/* 1439 */       return i;
/*      */     }
/* 1441 */     if (!Config.isConnectedTexturesFancy())
/*      */     {
/* 1443 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 1447 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1450 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().north(), side, icon, metadata);
/* 1451 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().north(), side, icon, metadata);
/* 1452 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().south(), side, icon, metadata);
/* 1453 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().south(), side, icon, metadata);
/*      */         
/* 1455 */         if (cp.innerSeams) {
/*      */           
/* 1457 */           BlockPos blockpos11 = blockPos.down();
/* 1458 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos11.east().north(), side, icon, metadata));
/* 1459 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos11.west().north(), side, icon, metadata));
/* 1460 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos11.east().south(), side, icon, metadata));
/* 1461 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos11.west().south(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1467 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().south(), side, icon, metadata);
/* 1468 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().south(), side, icon, metadata);
/* 1469 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().north(), side, icon, metadata);
/* 1470 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().north(), side, icon, metadata);
/*      */         
/* 1472 */         if (cp.innerSeams) {
/*      */           
/* 1474 */           BlockPos blockpos10 = blockPos.up();
/* 1475 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos10.east().south(), side, icon, metadata));
/* 1476 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos10.west().south(), side, icon, metadata));
/* 1477 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos10.east().north(), side, icon, metadata));
/* 1478 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos10.west().north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1484 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().down(), side, icon, metadata);
/* 1485 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().down(), side, icon, metadata);
/* 1486 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().up(), side, icon, metadata);
/* 1487 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().up(), side, icon, metadata);
/*      */         
/* 1489 */         if (cp.innerSeams) {
/*      */           
/* 1491 */           BlockPos blockpos9 = blockPos.north();
/* 1492 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos9.west().down(), side, icon, metadata));
/* 1493 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos9.east().down(), side, icon, metadata));
/* 1494 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos9.west().up(), side, icon, metadata));
/* 1495 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos9.east().up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1498 */         if (vertAxis == 1) {
/*      */           
/* 1500 */           switchValues(0, 3, aboolean);
/* 1501 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1507 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().down(), side, icon, metadata);
/* 1508 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().down(), side, icon, metadata);
/* 1509 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().up(), side, icon, metadata);
/* 1510 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().up(), side, icon, metadata);
/*      */         
/* 1512 */         if (cp.innerSeams) {
/*      */           
/* 1514 */           BlockPos blockpos8 = blockPos.south();
/* 1515 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos8.east().down(), side, icon, metadata));
/* 1516 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos8.west().down(), side, icon, metadata));
/* 1517 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos8.east().up(), side, icon, metadata));
/* 1518 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos8.west().up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1524 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().south(), side, icon, metadata);
/* 1525 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().north(), side, icon, metadata);
/* 1526 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().south(), side, icon, metadata);
/* 1527 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().north(), side, icon, metadata);
/*      */         
/* 1529 */         if (cp.innerSeams) {
/*      */           
/* 1531 */           BlockPos blockpos7 = blockPos.west();
/* 1532 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos7.down().south(), side, icon, metadata));
/* 1533 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos7.down().north(), side, icon, metadata));
/* 1534 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos7.up().south(), side, icon, metadata));
/* 1535 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos7.up().north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1541 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().north(), side, icon, metadata);
/* 1542 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().south(), side, icon, metadata);
/* 1543 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().north(), side, icon, metadata);
/* 1544 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().south(), side, icon, metadata);
/*      */         
/* 1546 */         if (cp.innerSeams) {
/*      */           
/* 1548 */           BlockPos blockpos1 = blockPos.east();
/* 1549 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos1.down().north(), side, icon, metadata));
/* 1550 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos1.down().south(), side, icon, metadata));
/* 1551 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos1.up().north(), side, icon, metadata));
/* 1552 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos1.up().south(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1555 */         if (vertAxis == 2) {
/*      */           
/* 1557 */           switchValues(0, 3, aboolean);
/* 1558 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1562 */     if (i == 13 && aboolean[0]) {
/*      */       
/* 1564 */       i = 4;
/*      */     }
/* 1566 */     else if (i == 15 && aboolean[1]) {
/*      */       
/* 1568 */       i = 5;
/*      */     }
/* 1570 */     else if (i == 37 && aboolean[2]) {
/*      */       
/* 1572 */       i = 16;
/*      */     }
/* 1574 */     else if (i == 39 && aboolean[3]) {
/*      */       
/* 1576 */       i = 17;
/*      */     }
/* 1578 */     else if (i == 14 && aboolean[0] && aboolean[1]) {
/*      */       
/* 1580 */       i = 7;
/*      */     }
/* 1582 */     else if (i == 25 && aboolean[0] && aboolean[2]) {
/*      */       
/* 1584 */       i = 6;
/*      */     }
/* 1586 */     else if (i == 27 && aboolean[3] && aboolean[1]) {
/*      */       
/* 1588 */       i = 19;
/*      */     }
/* 1590 */     else if (i == 38 && aboolean[3] && aboolean[2]) {
/*      */       
/* 1592 */       i = 18;
/*      */     }
/* 1594 */     else if (i == 14 && !aboolean[0] && aboolean[1]) {
/*      */       
/* 1596 */       i = 31;
/*      */     }
/* 1598 */     else if (i == 25 && aboolean[0] && !aboolean[2]) {
/*      */       
/* 1600 */       i = 30;
/*      */     }
/* 1602 */     else if (i == 27 && !aboolean[3] && aboolean[1]) {
/*      */       
/* 1604 */       i = 41;
/*      */     }
/* 1606 */     else if (i == 38 && aboolean[3] && !aboolean[2]) {
/*      */       
/* 1608 */       i = 40;
/*      */     }
/* 1610 */     else if (i == 14 && aboolean[0] && !aboolean[1]) {
/*      */       
/* 1612 */       i = 29;
/*      */     }
/* 1614 */     else if (i == 25 && !aboolean[0] && aboolean[2]) {
/*      */       
/* 1616 */       i = 28;
/*      */     }
/* 1618 */     else if (i == 27 && aboolean[3] && !aboolean[1]) {
/*      */       
/* 1620 */       i = 43;
/*      */     }
/* 1622 */     else if (i == 38 && !aboolean[3] && aboolean[2]) {
/*      */       
/* 1624 */       i = 42;
/*      */     }
/* 1626 */     else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1628 */       i = 46;
/*      */     }
/* 1630 */     else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1632 */       i = 9;
/*      */     }
/* 1634 */     else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1636 */       i = 21;
/*      */     }
/* 1638 */     else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1640 */       i = 8;
/*      */     }
/* 1642 */     else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1644 */       i = 20;
/*      */     }
/* 1646 */     else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1648 */       i = 11;
/*      */     }
/* 1650 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1652 */       i = 22;
/*      */     }
/* 1654 */     else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1656 */       i = 23;
/*      */     }
/* 1658 */     else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1660 */       i = 10;
/*      */     }
/* 1662 */     else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1664 */       i = 34;
/*      */     }
/* 1666 */     else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1668 */       i = 35;
/*      */     }
/* 1670 */     else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1672 */       i = 32;
/*      */     }
/* 1674 */     else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1676 */       i = 33;
/*      */     }
/* 1678 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1680 */       i = 44;
/*      */     }
/* 1682 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1684 */       i = 45;
/*      */     } 
/*      */     
/* 1687 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void switchValues(int ix1, int ix2, boolean[] arr) {
/* 1693 */     boolean flag = arr[ix1];
/* 1694 */     arr[ix1] = arr[ix2];
/* 1695 */     arr[ix2] = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourOverlay(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1700 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/*      */     
/* 1702 */     if (!isFullCubeModel(iblockstate))
/*      */     {
/* 1704 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1708 */     if (cp.connectBlocks != null) {
/*      */       
/* 1710 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1712 */       if (!Matches.block(blockstatebase.getBlockId(), blockstatebase.getMetadata(), cp.connectBlocks))
/*      */       {
/* 1714 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1718 */     if (cp.connectTileIcons != null) {
/*      */       
/* 1720 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, iblockstate, side);
/*      */       
/* 1722 */       if (!Config.isSameOne(textureatlassprite, (Object[])cp.connectTileIcons))
/*      */       {
/* 1724 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1728 */     IBlockState iblockstate1 = iblockaccess.getBlockState(blockPos.offset(getFacing(side)));
/* 1729 */     return iblockstate1.getBlock().isOpaqueCube() ? false : ((side == 1 && iblockstate1.getBlock() == Blocks.snow_layer) ? false : (!isNeighbour(cp, iblockaccess, blockState, blockPos, iblockstate, side, icon, metadata)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFullCubeModel(IBlockState state) {
/* 1735 */     if (state.getBlock().isFullCube())
/*      */     {
/* 1737 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1741 */     Block block = state.getBlock();
/* 1742 */     return (block instanceof net.minecraft.block.BlockGlass) ? true : (block instanceof net.minecraft.block.BlockStainedGlass);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourMatching(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1748 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/*      */     
/* 1750 */     if (iblockstate == AIR_DEFAULT_STATE)
/*      */     {
/* 1752 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1756 */     if (cp.matchBlocks != null && iblockstate instanceof BlockStateBase) {
/*      */       
/* 1758 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1760 */       if (!cp.matchesBlock(blockstatebase.getBlockId(), blockstatebase.getMetadata()))
/*      */       {
/* 1762 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1766 */     if (cp.matchTileIcons != null) {
/*      */       
/* 1768 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, iblockstate, side);
/*      */       
/* 1770 */       if (textureatlassprite != icon)
/*      */       {
/* 1772 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1776 */     IBlockState iblockstate1 = iblockaccess.getBlockState(blockPos.offset(getFacing(side)));
/* 1777 */     return iblockstate1.getBlock().isOpaqueCube() ? false : ((side != 1 || iblockstate1.getBlock() != Blocks.snow_layer));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1783 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/* 1784 */     return isNeighbour(cp, iblockaccess, blockState, blockPos, iblockstate, side, icon, metadata);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, IBlockState neighbourState, int side, TextureAtlasSprite icon, int metadata) {
/* 1789 */     if (blockState == neighbourState)
/*      */     {
/* 1791 */       return true;
/*      */     }
/* 1793 */     if (cp.connect == 2) {
/*      */       
/* 1795 */       if (neighbourState == null)
/*      */       {
/* 1797 */         return false;
/*      */       }
/* 1799 */       if (neighbourState == AIR_DEFAULT_STATE)
/*      */       {
/* 1801 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1805 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, neighbourState, side);
/* 1806 */       return (textureatlassprite == icon);
/*      */     } 
/*      */     
/* 1809 */     if (cp.connect == 3)
/*      */     {
/* 1811 */       return (neighbourState == null) ? false : ((neighbourState == AIR_DEFAULT_STATE) ? false : ((neighbourState.getBlock().getMaterial() == blockState.getBlock().getMaterial())));
/*      */     }
/* 1813 */     if (!(neighbourState instanceof BlockStateBase))
/*      */     {
/* 1815 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1819 */     BlockStateBase blockstatebase = (BlockStateBase)neighbourState;
/* 1820 */     Block block = blockstatebase.getBlock();
/* 1821 */     int i = blockstatebase.getMetadata();
/* 1822 */     return (block == blockState.getBlock() && i == metadata);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getNeighbourIcon(IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, IBlockState neighbourState, int side) {
/* 1828 */     neighbourState = neighbourState.getBlock().getActualState(neighbourState, iblockaccess, blockPos);
/* 1829 */     IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(neighbourState);
/*      */     
/* 1831 */     if (ibakedmodel == null)
/*      */     {
/* 1833 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1837 */     if (Reflector.ForgeBlock_getExtendedState.exists())
/*      */     {
/* 1839 */       neighbourState = (IBlockState)Reflector.call(neighbourState.getBlock(), Reflector.ForgeBlock_getExtendedState, new Object[] { neighbourState, iblockaccess, blockPos });
/*      */     }
/*      */     
/* 1842 */     EnumFacing enumfacing = getFacing(side);
/* 1843 */     List<BakedQuad> list = ibakedmodel.getFaceQuads(enumfacing);
/*      */     
/* 1845 */     if (list == null)
/*      */     {
/* 1847 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1851 */     if (Config.isBetterGrass())
/*      */     {
/* 1853 */       list = BetterGrass.getFaceQuads(iblockaccess, neighbourState, blockPos, enumfacing, list);
/*      */     }
/*      */     
/* 1856 */     if (list.size() > 0) {
/*      */       
/* 1858 */       BakedQuad bakedquad1 = list.get(0);
/* 1859 */       return bakedquad1.getSprite();
/*      */     } 
/*      */ 
/*      */     
/* 1863 */     List<BakedQuad> list1 = ibakedmodel.getGeneralQuads();
/*      */     
/* 1865 */     if (list1 == null)
/*      */     {
/* 1867 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1871 */     for (int i = 0; i < list1.size(); i++) {
/*      */       
/* 1873 */       BakedQuad bakedquad = list1.get(i);
/*      */       
/* 1875 */       if (bakedquad.getFace() == enumfacing)
/*      */       {
/* 1877 */         return bakedquad.getSprite();
/*      */       }
/*      */     } 
/*      */     
/* 1881 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1892 */     boolean flag = false;
/* 1893 */     boolean flag1 = false;
/*      */ 
/*      */     
/* 1896 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/* 1899 */         switch (side) {
/*      */           
/*      */           case 0:
/* 1902 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1903 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1907 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1908 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1912 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1913 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1917 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1918 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1922 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1923 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1927 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1928 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/* 1935 */         switch (side) {
/*      */           
/*      */           case 0:
/* 1938 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1939 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1943 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1944 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1948 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1949 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1953 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1954 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1958 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1959 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1963 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1964 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/* 1971 */         switch (side) {
/*      */           
/*      */           case 0:
/* 1974 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1975 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1979 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1980 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1984 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1985 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1989 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1990 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1994 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1995 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1999 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 2000 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata); break;
/*      */         } 
/*      */         break;
/*      */     } 
/* 2004 */     int i = 3;
/*      */     
/* 2006 */     if (flag) {
/*      */       
/* 2008 */       if (flag1)
/*      */       {
/* 2010 */         i = 1;
/*      */       }
/*      */       else
/*      */       {
/* 2014 */         i = 2;
/*      */       }
/*      */     
/* 2017 */     } else if (flag1) {
/*      */       
/* 2019 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 2023 */       i = 3;
/*      */     } 
/*      */     
/* 2026 */     return cp.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2031 */     boolean flag = false;
/* 2032 */     boolean flag1 = false;
/*      */     
/* 2034 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/* 2037 */         if (side == 1) {
/*      */           
/* 2039 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 2040 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata); break;
/*      */         } 
/* 2042 */         if (side == 0) {
/*      */           
/* 2044 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 2045 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */           
/*      */           break;
/*      */         } 
/* 2049 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 2050 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 2056 */         if (side == 3) {
/*      */           
/* 2058 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 2059 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata); break;
/*      */         } 
/* 2061 */         if (side == 2) {
/*      */           
/* 2063 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 2064 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */           
/*      */           break;
/*      */         } 
/* 2068 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 2069 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 2075 */         if (side == 5) {
/*      */           
/* 2077 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 2078 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata); break;
/*      */         } 
/* 2080 */         if (side == 4) {
/*      */           
/* 2082 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 2083 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */           
/*      */           break;
/*      */         } 
/* 2087 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 2088 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/* 2092 */     int i = 3;
/*      */     
/* 2094 */     if (flag) {
/*      */       
/* 2096 */       if (flag1)
/*      */       {
/* 2098 */         i = 1;
/*      */       }
/*      */       else
/*      */       {
/* 2102 */         i = 2;
/*      */       }
/*      */     
/* 2105 */     } else if (flag1) {
/*      */       
/* 2107 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 2111 */       i = 3;
/*      */     } 
/*      */     
/* 2114 */     return cp.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2119 */     TextureAtlasSprite[] atextureatlassprite = cp.tileIcons;
/* 2120 */     TextureAtlasSprite textureatlassprite = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/*      */     
/* 2122 */     if (textureatlassprite != null && textureatlassprite != icon && textureatlassprite != atextureatlassprite[3])
/*      */     {
/* 2124 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/* 2128 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/* 2129 */     return (textureatlassprite1 == atextureatlassprite[0]) ? atextureatlassprite[4] : ((textureatlassprite1 == atextureatlassprite[1]) ? atextureatlassprite[5] : ((textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2135 */     TextureAtlasSprite[] atextureatlassprite = cp.tileIcons;
/* 2136 */     TextureAtlasSprite textureatlassprite = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/*      */     
/* 2138 */     if (textureatlassprite != null && textureatlassprite != icon && textureatlassprite != atextureatlassprite[3])
/*      */     {
/* 2140 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/* 2144 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/* 2145 */     return (textureatlassprite1 == atextureatlassprite[0]) ? atextureatlassprite[4] : ((textureatlassprite1 == atextureatlassprite[1]) ? atextureatlassprite[5] : ((textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2151 */     boolean flag = false;
/*      */     
/* 2153 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/* 2156 */         if (side == 1 || side == 0)
/*      */         {
/* 2158 */           return null;
/*      */         }
/*      */         
/* 2161 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 1:
/* 2165 */         if (side == 3 || side == 2)
/*      */         {
/* 2167 */           return null;
/*      */         }
/*      */         
/* 2170 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 2:
/* 2174 */         if (side == 5 || side == 4)
/*      */         {
/* 2176 */           return null;
/*      */         }
/*      */         
/* 2179 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */         break;
/*      */     } 
/* 2182 */     if (flag)
/*      */     {
/* 2184 */       return cp.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/* 2188 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap) {
/* 2194 */     blockProperties = (ConnectedProperties[][])null;
/* 2195 */     tileProperties = (ConnectedProperties[][])null;
/* 2196 */     spriteQuadMaps = null;
/* 2197 */     spriteQuadCompactMaps = (Map[][])null;
/*      */     
/* 2199 */     if (Config.isConnectedTextures()) {
/*      */       
/* 2201 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/* 2203 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*      */         
/* 2205 */         IResourcePack iresourcepack = airesourcepack[i];
/* 2206 */         updateIcons(textureMap, iresourcepack);
/*      */       } 
/*      */       
/* 2209 */       updateIcons(textureMap, (IResourcePack)Config.getDefaultResourcePack());
/* 2210 */       ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/ctm/default/empty");
/* 2211 */       emptySprite = textureMap.registerSprite(resourcelocation);
/* 2212 */       spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
/* 2213 */       spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
/* 2214 */       spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
/*      */       
/* 2216 */       if (blockProperties.length <= 0)
/*      */       {
/* 2218 */         blockProperties = (ConnectedProperties[][])null;
/*      */       }
/*      */       
/* 2221 */       if (tileProperties.length <= 0)
/*      */       {
/* 2223 */         tileProperties = (ConnectedProperties[][])null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateIconEmpty(TextureMap textureMap) {}
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap, IResourcePack rp) {
/* 2234 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
/* 2235 */     Arrays.sort((Object[])astring);
/* 2236 */     List list = makePropertyList(tileProperties);
/* 2237 */     List list1 = makePropertyList(blockProperties);
/*      */     
/* 2239 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 2241 */       String s = astring[i];
/* 2242 */       Config.dbg("ConnectedTextures: " + s);
/*      */ 
/*      */       
/*      */       try {
/* 2246 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 2247 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/*      */         
/* 2249 */         if (inputstream == null) {
/*      */           
/* 2251 */           Config.warn("ConnectedTextures file not found: " + s);
/*      */         }
/*      */         else {
/*      */           
/* 2255 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 2256 */           propertiesOrdered.load(inputstream);
/* 2257 */           ConnectedProperties connectedproperties = new ConnectedProperties((Properties)propertiesOrdered, s);
/*      */           
/* 2259 */           if (connectedproperties.isValid(s))
/*      */           {
/* 2261 */             connectedproperties.updateIcons(textureMap);
/* 2262 */             addToTileList(connectedproperties, list);
/* 2263 */             addToBlockList(connectedproperties, list1);
/*      */           }
/*      */         
/*      */         } 
/* 2267 */       } catch (FileNotFoundException var11) {
/*      */         
/* 2269 */         Config.warn("ConnectedTextures file not found: " + s);
/*      */       }
/* 2271 */       catch (Exception exception) {
/*      */         
/* 2273 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/* 2277 */     blockProperties = propertyListToArray(list1);
/* 2278 */     tileProperties = propertyListToArray(list);
/* 2279 */     multipass = detectMultipass();
/* 2280 */     Config.dbg("Multipass connected textures: " + multipass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(ConnectedProperties[][] propsArr) {
/* 2285 */     List<List> list = new ArrayList();
/*      */     
/* 2287 */     if (propsArr != null)
/*      */     {
/* 2289 */       for (int i = 0; i < propsArr.length; i++) {
/*      */         
/* 2291 */         ConnectedProperties[] aconnectedproperties = propsArr[i];
/* 2292 */         List list1 = null;
/*      */         
/* 2294 */         if (aconnectedproperties != null)
/*      */         {
/* 2296 */           list1 = new ArrayList(Arrays.asList((Object[])aconnectedproperties));
/*      */         }
/*      */         
/* 2299 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/* 2303 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean detectMultipass() {
/* 2308 */     List list = new ArrayList();
/*      */     
/* 2310 */     for (int i = 0; i < tileProperties.length; i++) {
/*      */       
/* 2312 */       ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */       
/* 2314 */       if (aconnectedproperties != null)
/*      */       {
/* 2316 */         list.addAll(Arrays.asList(aconnectedproperties));
/*      */       }
/*      */     } 
/*      */     
/* 2320 */     for (int k = 0; k < blockProperties.length; k++) {
/*      */       
/* 2322 */       ConnectedProperties[] aconnectedproperties2 = blockProperties[k];
/*      */       
/* 2324 */       if (aconnectedproperties2 != null)
/*      */       {
/* 2326 */         list.addAll(Arrays.asList(aconnectedproperties2));
/*      */       }
/*      */     } 
/*      */     
/* 2330 */     ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray((Object[])new ConnectedProperties[list.size()]);
/* 2331 */     Set set1 = new HashSet();
/* 2332 */     Set<?> set = new HashSet();
/*      */     
/* 2334 */     for (int j = 0; j < aconnectedproperties1.length; j++) {
/*      */       
/* 2336 */       ConnectedProperties connectedproperties = aconnectedproperties1[j];
/*      */       
/* 2338 */       if (connectedproperties.matchTileIcons != null)
/*      */       {
/* 2340 */         set1.addAll(Arrays.asList(connectedproperties.matchTileIcons));
/*      */       }
/*      */       
/* 2343 */       if (connectedproperties.tileIcons != null)
/*      */       {
/* 2345 */         set.addAll(Arrays.asList(connectedproperties.tileIcons));
/*      */       }
/*      */     } 
/*      */     
/* 2349 */     set1.retainAll(set);
/* 2350 */     return !set1.isEmpty();
/*      */   }
/*      */   
/*      */   private static ConnectedProperties[][] propertyListToArray(List<List> list) {
/* 2354 */     ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
/* 2355 */     for (int i = 0; i < list.size(); i++) {
/* 2356 */       List subList = list.get(i);
/* 2357 */       if (subList != null) {
/* 2358 */         ConnectedProperties[] subArr = (ConnectedProperties[])subList.toArray((Object[])new ConnectedProperties[subList.size()]);
/* 2359 */         propArr[i] = subArr;
/*      */       } 
/*      */     } 
/* 2362 */     return propArr;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToTileList(ConnectedProperties cp, List tileList) {
/* 2367 */     if (cp.matchTileIcons != null)
/*      */     {
/* 2369 */       for (int i = 0; i < cp.matchTileIcons.length; i++) {
/*      */         
/* 2371 */         TextureAtlasSprite textureatlassprite = cp.matchTileIcons[i];
/*      */         
/* 2373 */         if (!(textureatlassprite instanceof TextureAtlasSprite)) {
/*      */           
/* 2375 */           Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureatlassprite + ", name: " + textureatlassprite.getIconName());
/*      */         }
/*      */         else {
/*      */           
/* 2379 */           int j = textureatlassprite.getIndexInMap();
/*      */           
/* 2381 */           if (j < 0) {
/*      */             
/* 2383 */             Config.warn("Invalid tile ID: " + j + ", icon: " + textureatlassprite.getIconName());
/*      */           }
/*      */           else {
/*      */             
/* 2387 */             addToList(cp, tileList, j);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToBlockList(ConnectedProperties cp, List blockList) {
/* 2396 */     if (cp.matchBlocks != null)
/*      */     {
/* 2398 */       for (int i = 0; i < cp.matchBlocks.length; i++) {
/*      */         
/* 2400 */         int j = cp.matchBlocks[i].getBlockId();
/*      */         
/* 2402 */         if (j < 0) {
/*      */           
/* 2404 */           Config.warn("Invalid block ID: " + j);
/*      */         }
/*      */         else {
/*      */           
/* 2408 */           addToList(cp, blockList, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(ConnectedProperties cp, List<List> list, int id) {
/* 2416 */     while (id >= list.size())
/*      */     {
/* 2418 */       list.add(null);
/*      */     }
/*      */     
/* 2421 */     List<ConnectedProperties> l = list.get(id);
/*      */     
/* 2423 */     if (l == null) {
/*      */       
/* 2425 */       l = new ArrayList();
/* 2426 */       list.set(id, l);
/*      */     } 
/*      */     
/* 2429 */     l.add(cp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String[] getDefaultCtmPaths() {
/* 2434 */     List<String> list = new ArrayList();
/* 2435 */     String s = "mcpatcher/ctm/default/";
/*      */     
/* 2437 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
/*      */       
/* 2439 */       list.add(s + "glass.properties");
/* 2440 */       list.add(s + "glasspane.properties");
/*      */     } 
/*      */     
/* 2443 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png")))
/*      */     {
/* 2445 */       list.add(s + "bookshelf.properties");
/*      */     }
/*      */     
/* 2448 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png")))
/*      */     {
/* 2450 */       list.add(s + "sandstone.properties");
/*      */     }
/*      */     
/* 2453 */     String[] astring = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*      */     
/* 2455 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 2457 */       String s1 = astring[i];
/*      */       
/* 2459 */       if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s1 + ".png"))) {
/*      */         
/* 2461 */         list.add(s + i + "_glass_" + s1 + "/glass_" + s1 + ".properties");
/* 2462 */         list.add(s + i + "_glass_" + s1 + "/glass_pane_" + s1 + ".properties");
/*      */       } 
/*      */     } 
/*      */     
/* 2466 */     String[] astring1 = list.<String>toArray(new String[list.size()]);
/* 2467 */     return astring1;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\ConnectedTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */