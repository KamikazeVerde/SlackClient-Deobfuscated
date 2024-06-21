/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public abstract class BlockLiquid
/*     */   extends Block {
/*  25 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockLiquid(Material materialIn) {
/*  29 */     super(materialIn);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*  31 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  32 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  37 */     return (this.blockMaterial != Material.lava);
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  42 */     return (this.blockMaterial == Material.water) ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 16777215;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getLiquidHeightPercent(int meta) {
/*  50 */     if (meta >= 8)
/*     */     {
/*  52 */       meta = 0;
/*     */     }
/*     */     
/*  55 */     return (meta + 1) / 9.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLevel(IBlockAccess worldIn, BlockPos pos) {
/*  60 */     return (worldIn.getBlockState(pos).getBlock().getMaterial() == this.blockMaterial) ? ((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getEffectiveFlowDecay(IBlockAccess worldIn, BlockPos pos) {
/*  65 */     int i = getLevel(worldIn, pos);
/*  66 */     return (i >= 8) ? 0 : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  84 */     return (hitIfLiquid && ((Integer)state.getValue((IProperty)LEVEL)).intValue() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  92 */     Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
/*  93 */     return (material == this.blockMaterial) ? false : ((side == EnumFacing.UP) ? true : ((material == Material.ice) ? false : super.isBlockSolid(worldIn, pos, side)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  98 */     return (worldIn.getBlockState(pos).getBlock().getMaterial() == this.blockMaterial) ? false : ((side == EnumFacing.UP) ? true : super.shouldSideBeRendered(worldIn, pos, side));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_176364_g(IBlockAccess blockAccess, BlockPos pos) {
/* 103 */     for (int i = -1; i <= 1; i++) {
/*     */       
/* 105 */       for (int j = -1; j <= 1; j++) {
/*     */         
/* 107 */         IBlockState iblockstate = blockAccess.getBlockState(pos.add(i, 0, j));
/* 108 */         Block block = iblockstate.getBlock();
/* 109 */         Material material = block.getMaterial();
/*     */         
/* 111 */         if (material != this.blockMaterial && !block.isFullBlock())
/*     */         {
/* 113 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 131 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 149 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getFlowVector(IBlockAccess worldIn, BlockPos pos) {
/* 154 */     Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);
/* 155 */     int i = getEffectiveFlowDecay(worldIn, pos);
/*     */     
/* 157 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 159 */       BlockPos blockpos = pos.offset(enumfacing);
/* 160 */       int j = getEffectiveFlowDecay(worldIn, blockpos);
/*     */       
/* 162 */       if (j < 0) {
/*     */         
/* 164 */         if (!worldIn.getBlockState(blockpos).getBlock().getMaterial().blocksMovement()) {
/*     */           
/* 166 */           j = getEffectiveFlowDecay(worldIn, blockpos.down());
/*     */           
/* 168 */           if (j >= 0) {
/*     */             
/* 170 */             int k = j - i - 8;
/* 171 */             vec3 = vec3.addVector(((blockpos.getX() - pos.getX()) * k), ((blockpos.getY() - pos.getY()) * k), ((blockpos.getZ() - pos.getZ()) * k));
/*     */           } 
/*     */         }  continue;
/*     */       } 
/* 175 */       if (j >= 0) {
/*     */         
/* 177 */         int l = j - i;
/* 178 */         vec3 = vec3.addVector(((blockpos.getX() - pos.getX()) * l), ((blockpos.getY() - pos.getY()) * l), ((blockpos.getZ() - pos.getZ()) * l));
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     if (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue() >= 8)
/*     */     {
/* 184 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 186 */         BlockPos blockpos1 = pos.offset(enumfacing1);
/*     */         
/* 188 */         if (isBlockSolid(worldIn, blockpos1, enumfacing1) || isBlockSolid(worldIn, blockpos1.up(), enumfacing1)) {
/*     */           
/* 190 */           vec3 = vec3.normalize().addVector(0.0D, -6.0D, 0.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 196 */     return vec3.normalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/* 201 */     return motion.add(getFlowVector((IBlockAccess)worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 209 */     return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (worldIn.provider.getHasNoSky() ? 10 : 30) : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/* 214 */     int i = worldIn.getCombinedLight(pos, 0);
/* 215 */     int j = worldIn.getCombinedLight(pos.up(), 0);
/* 216 */     int k = i & 0xFF;
/* 217 */     int l = j & 0xFF;
/* 218 */     int i1 = i >> 16 & 0xFF;
/* 219 */     int j1 = j >> 16 & 0xFF;
/* 220 */     return ((k > l) ? k : l) | ((i1 > j1) ? i1 : j1) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 225 */     return (this.blockMaterial == Material.water) ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 230 */     double d0 = pos.getX();
/* 231 */     double d1 = pos.getY();
/* 232 */     double d2 = pos.getZ();
/*     */     
/* 234 */     if (this.blockMaterial == Material.water) {
/*     */       
/* 236 */       int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */       
/* 238 */       if (i > 0 && i < 8) {
/*     */         
/* 240 */         if (rand.nextInt(64) == 0)
/*     */         {
/* 242 */           worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "liquid.water", rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() * 1.0F + 0.5F, false);
/*     */         }
/*     */       }
/* 245 */       else if (rand.nextInt(10) == 0) {
/*     */         
/* 247 */         worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube()) {
/*     */       
/* 253 */       if (rand.nextInt(100) == 0) {
/*     */         
/* 255 */         double d8 = d0 + rand.nextFloat();
/* 256 */         double d4 = d1 + this.maxY;
/* 257 */         double d6 = d2 + rand.nextFloat();
/* 258 */         worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
/* 259 */         worldIn.playSound(d8, d4, d6, "liquid.lavapop", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       } 
/*     */       
/* 262 */       if (rand.nextInt(200) == 0)
/*     */       {
/* 264 */         worldIn.playSound(d0, d1, d2, "liquid.lava", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } 
/*     */     
/* 268 */     if (rand.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*     */       
/* 270 */       Material material = worldIn.getBlockState(pos.down(2)).getBlock().getMaterial();
/*     */       
/* 272 */       if (!material.blocksMovement() && !material.isLiquid()) {
/*     */         
/* 274 */         double d3 = d0 + rand.nextFloat();
/* 275 */         double d5 = d1 - 1.05D;
/* 276 */         double d7 = d2 + rand.nextFloat();
/*     */         
/* 278 */         if (this.blockMaterial == Material.water) {
/*     */           
/* 280 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         else {
/*     */           
/* 284 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getFlowDirection(IBlockAccess worldIn, BlockPos pos, Material materialIn) {
/* 292 */     Vec3 vec3 = getFlowingBlock(materialIn).getFlowVector(worldIn, pos);
/* 293 */     return (vec3.xCoord == 0.0D && vec3.zCoord == 0.0D) ? -1000.0D : (MathHelper.func_181159_b(vec3.zCoord, vec3.xCoord) - 1.5707963267948966D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 298 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 306 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state) {
/* 311 */     if (this.blockMaterial == Material.lava) {
/*     */       
/* 313 */       boolean flag = false;
/*     */       
/* 315 */       for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */         
/* 317 */         if (enumfacing != EnumFacing.DOWN && worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial() == Material.water) {
/*     */           
/* 319 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 324 */       if (flag) {
/*     */         
/* 326 */         Integer integer = (Integer)state.getValue((IProperty)LEVEL);
/*     */         
/* 328 */         if (integer.intValue() == 0) {
/*     */           
/* 330 */           worldIn.setBlockState(pos, Blocks.obsidian.getDefaultState());
/* 331 */           triggerMixEffects(worldIn, pos);
/* 332 */           return true;
/*     */         } 
/*     */         
/* 335 */         if (integer.intValue() <= 4) {
/*     */           
/* 337 */           worldIn.setBlockState(pos, Blocks.cobblestone.getDefaultState());
/* 338 */           triggerMixEffects(worldIn, pos);
/* 339 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void triggerMixEffects(World worldIn, BlockPos pos) {
/* 349 */     double d0 = pos.getX();
/* 350 */     double d1 = pos.getY();
/* 351 */     double d2 = pos.getZ();
/* 352 */     worldIn.playSoundEffect(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */     
/* 354 */     for (int i = 0; i < 8; i++)
/*     */     {
/* 356 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 365 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 373 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 378 */     return new BlockState(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockDynamicLiquid getFlowingBlock(Material materialIn) {
/* 383 */     if (materialIn == Material.water)
/*     */     {
/* 385 */       return Blocks.flowing_water;
/*     */     }
/* 387 */     if (materialIn == Material.lava)
/*     */     {
/* 389 */       return Blocks.flowing_lava;
/*     */     }
/*     */ 
/*     */     
/* 393 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockStaticLiquid getStaticBlock(Material materialIn) {
/* 399 */     if (materialIn == Material.water)
/*     */     {
/* 401 */       return Blocks.water;
/*     */     }
/* 403 */     if (materialIn == Material.lava)
/*     */     {
/* 405 */       return Blocks.lava;
/*     */     }
/*     */ 
/*     */     
/* 409 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */