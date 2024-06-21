/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.model.BlockModelUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ public class BetterGrass
/*     */ {
/*     */   private static boolean betterGrass = true;
/*     */   private static boolean betterMycelium = true;
/*     */   private static boolean betterPodzol = true;
/*     */   private static boolean betterGrassSnow = true;
/*     */   private static boolean betterMyceliumSnow = true;
/*     */   private static boolean betterPodzolSnow = true;
/*     */   private static boolean grassMultilayer = false;
/*  33 */   private static TextureAtlasSprite spriteGrass = null;
/*  34 */   private static TextureAtlasSprite spriteGrassSide = null;
/*  35 */   private static TextureAtlasSprite spriteMycelium = null;
/*  36 */   private static TextureAtlasSprite spritePodzol = null;
/*  37 */   private static TextureAtlasSprite spriteSnow = null;
/*     */   private static boolean spritesLoaded = false;
/*  39 */   private static IBakedModel modelCubeGrass = null;
/*  40 */   private static IBakedModel modelCubeMycelium = null;
/*  41 */   private static IBakedModel modelCubePodzol = null;
/*  42 */   private static IBakedModel modelCubeSnow = null;
/*     */   
/*     */   private static boolean modelsLoaded = false;
/*     */   private static final String TEXTURE_GRASS_DEFAULT = "blocks/grass_top";
/*     */   private static final String TEXTURE_GRASS_SIDE_DEFAULT = "blocks/grass_side";
/*     */   private static final String TEXTURE_MYCELIUM_DEFAULT = "blocks/mycelium_top";
/*     */   private static final String TEXTURE_PODZOL_DEFAULT = "blocks/dirt_podzol_top";
/*     */   private static final String TEXTURE_SNOW_DEFAULT = "blocks/snow";
/*     */   
/*     */   public static void updateIcons(TextureMap textureMap) {
/*  52 */     spritesLoaded = false;
/*  53 */     modelsLoaded = false;
/*  54 */     loadProperties(textureMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  59 */     if (spritesLoaded) {
/*     */       
/*  61 */       modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
/*     */       
/*  63 */       if (grassMultilayer) {
/*     */         
/*  65 */         IBakedModel ibakedmodel = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
/*  66 */         modelCubeGrass = BlockModelUtils.joinModelsCube(ibakedmodel, modelCubeGrass);
/*     */       } 
/*     */       
/*  69 */       modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
/*  70 */       modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
/*  71 */       modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
/*  72 */       modelsLoaded = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadProperties(TextureMap textureMap) {
/*  78 */     betterGrass = true;
/*  79 */     betterMycelium = true;
/*  80 */     betterPodzol = true;
/*  81 */     betterGrassSnow = true;
/*  82 */     betterMyceliumSnow = true;
/*  83 */     betterPodzolSnow = true;
/*  84 */     spriteGrass = textureMap.registerSprite(new ResourceLocation("blocks/grass_top"));
/*  85 */     spriteGrassSide = textureMap.registerSprite(new ResourceLocation("blocks/grass_side"));
/*  86 */     spriteMycelium = textureMap.registerSprite(new ResourceLocation("blocks/mycelium_top"));
/*  87 */     spritePodzol = textureMap.registerSprite(new ResourceLocation("blocks/dirt_podzol_top"));
/*  88 */     spriteSnow = textureMap.registerSprite(new ResourceLocation("blocks/snow"));
/*  89 */     spritesLoaded = true;
/*  90 */     String s = "optifine/bettergrass.properties";
/*     */ 
/*     */     
/*     */     try {
/*  94 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */       
/*  96 */       if (!Config.hasResource(resourcelocation)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 101 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 103 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 108 */       boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*     */       
/* 110 */       if (flag) {
/*     */         
/* 112 */         Config.dbg("BetterGrass: Parsing default configuration " + s);
/*     */       }
/*     */       else {
/*     */         
/* 116 */         Config.dbg("BetterGrass: Parsing configuration " + s);
/*     */       } 
/*     */       
/* 119 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 120 */       propertiesOrdered.load(inputstream);
/* 121 */       betterGrass = getBoolean((Properties)propertiesOrdered, "grass", true);
/* 122 */       betterMycelium = getBoolean((Properties)propertiesOrdered, "mycelium", true);
/* 123 */       betterPodzol = getBoolean((Properties)propertiesOrdered, "podzol", true);
/* 124 */       betterGrassSnow = getBoolean((Properties)propertiesOrdered, "grass.snow", true);
/* 125 */       betterMyceliumSnow = getBoolean((Properties)propertiesOrdered, "mycelium.snow", true);
/* 126 */       betterPodzolSnow = getBoolean((Properties)propertiesOrdered, "podzol.snow", true);
/* 127 */       grassMultilayer = getBoolean((Properties)propertiesOrdered, "grass.multilayer", false);
/* 128 */       spriteGrass = registerSprite((Properties)propertiesOrdered, "texture.grass", "blocks/grass_top", textureMap);
/* 129 */       spriteGrassSide = registerSprite((Properties)propertiesOrdered, "texture.grass_side", "blocks/grass_side", textureMap);
/* 130 */       spriteMycelium = registerSprite((Properties)propertiesOrdered, "texture.mycelium", "blocks/mycelium_top", textureMap);
/* 131 */       spritePodzol = registerSprite((Properties)propertiesOrdered, "texture.podzol", "blocks/dirt_podzol_top", textureMap);
/* 132 */       spriteSnow = registerSprite((Properties)propertiesOrdered, "texture.snow", "blocks/snow", textureMap);
/*     */     }
/* 134 */     catch (IOException ioexception) {
/*     */       
/* 136 */       Config.warn("Error reading: " + s + ", " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite registerSprite(Properties props, String key, String textureDefault, TextureMap textureMap) {
/* 142 */     String s = props.getProperty(key);
/*     */     
/* 144 */     if (s == null)
/*     */     {
/* 146 */       s = textureDefault;
/*     */     }
/*     */     
/* 149 */     ResourceLocation resourcelocation = new ResourceLocation("textures/" + s + ".png");
/*     */     
/* 151 */     if (!Config.hasResource(resourcelocation)) {
/*     */       
/* 153 */       Config.warn("BetterGrass texture not found: " + resourcelocation);
/* 154 */       s = textureDefault;
/*     */     } 
/*     */     
/* 157 */     ResourceLocation resourcelocation1 = new ResourceLocation(s);
/* 158 */     TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation1);
/* 159 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List getFaceQuads(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 164 */     if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
/*     */       
/* 166 */       if (!modelsLoaded)
/*     */       {
/* 168 */         return quads;
/*     */       }
/*     */ 
/*     */       
/* 172 */       Block block = blockState.getBlock();
/* 173 */       return (block instanceof net.minecraft.block.BlockMycelium) ? getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads) : ((block instanceof BlockDirt) ? getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads) : ((block instanceof net.minecraft.block.BlockGrass) ? getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads) : quads));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     return quads;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsMycelium(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 184 */     Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
/* 185 */     boolean flag = (block == Blocks.snow || block == Blocks.snow_layer);
/*     */     
/* 187 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 189 */       if (flag)
/*     */       {
/* 191 */         if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer)
/*     */         {
/* 193 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/*     */       }
/* 196 */       else if (betterMycelium && getBlockAt(blockPos.down(), facing, blockAccess) == Blocks.mycelium)
/*     */       {
/* 198 */         return modelCubeMycelium.getFaceQuads(facing);
/*     */       }
/*     */     
/* 201 */     } else if (flag) {
/*     */       
/* 203 */       if (betterMyceliumSnow)
/*     */       {
/* 205 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/*     */     }
/* 208 */     else if (betterMycelium) {
/*     */       
/* 210 */       return modelCubeMycelium.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 213 */     return quads;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsDirt(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 218 */     Block block = getBlockAt(blockPos, EnumFacing.UP, blockAccess);
/*     */     
/* 220 */     if (blockState.getValue((IProperty)BlockDirt.VARIANT) != BlockDirt.DirtType.PODZOL)
/*     */     {
/* 222 */       return quads;
/*     */     }
/*     */ 
/*     */     
/* 226 */     boolean flag = (block == Blocks.snow || block == Blocks.snow_layer);
/*     */     
/* 228 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 230 */       if (flag)
/*     */       {
/* 232 */         if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer)
/*     */         {
/* 234 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/*     */       }
/* 237 */       else if (betterPodzol)
/*     */       {
/* 239 */         BlockPos blockpos = blockPos.down().offset(facing);
/* 240 */         IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/*     */         
/* 242 */         if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
/*     */         {
/* 244 */           return modelCubePodzol.getFaceQuads(facing);
/*     */         }
/*     */       }
/*     */     
/* 248 */     } else if (flag) {
/*     */       
/* 250 */       if (betterPodzolSnow)
/*     */       {
/* 252 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/*     */     }
/* 255 */     else if (betterPodzol) {
/*     */       
/* 257 */       return modelCubePodzol.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 260 */     return quads;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsGrass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 266 */     Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
/* 267 */     boolean flag = (block == Blocks.snow || block == Blocks.snow_layer);
/*     */     
/* 269 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 271 */       if (flag)
/*     */       {
/* 273 */         if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer)
/*     */         {
/* 275 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/*     */       }
/* 278 */       else if (betterGrass && getBlockAt(blockPos.down(), facing, blockAccess) == Blocks.grass)
/*     */       {
/* 280 */         return modelCubeGrass.getFaceQuads(facing);
/*     */       }
/*     */     
/* 283 */     } else if (flag) {
/*     */       
/* 285 */       if (betterGrassSnow)
/*     */       {
/* 287 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/*     */     }
/* 290 */     else if (betterGrass) {
/*     */       
/* 292 */       return modelCubeGrass.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 295 */     return quads;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Block getBlockAt(BlockPos blockPos, EnumFacing facing, IBlockAccess blockAccess) {
/* 300 */     BlockPos blockpos = blockPos.offset(facing);
/* 301 */     Block block = blockAccess.getBlockState(blockpos).getBlock();
/* 302 */     return block;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(Properties props, String key, boolean def) {
/* 307 */     String s = props.getProperty(key);
/* 308 */     return (s == null) ? def : Boolean.parseBoolean(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\BetterGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */