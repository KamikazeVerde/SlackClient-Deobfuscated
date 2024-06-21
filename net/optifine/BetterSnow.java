/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLever;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ 
/*     */ 
/*     */ public class BetterSnow
/*     */ {
/*  30 */   private static IBakedModel modelSnowLayer = null;
/*     */ 
/*     */   
/*     */   public static void update() {
/*  34 */     modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel getModelSnowLayer() {
/*  39 */     return modelSnowLayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBlockState getStateSnowLayer() {
/*  44 */     return Blocks.snow_layer.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean shouldRender(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos) {
/*  49 */     Block block = blockState.getBlock();
/*  50 */     return !checkBlock(block, blockState) ? false : hasSnowNeighbours(blockAccess, blockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasSnowNeighbours(IBlockAccess blockAccess, BlockPos pos) {
/*  55 */     Block block = Blocks.snow_layer;
/*  56 */     return (blockAccess.getBlockState(pos.north()).getBlock() != block && blockAccess.getBlockState(pos.south()).getBlock() != block && blockAccess.getBlockState(pos.west()).getBlock() != block && blockAccess.getBlockState(pos.east()).getBlock() != block) ? false : blockAccess.getBlockState(pos.down()).getBlock().isOpaqueCube();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkBlock(Block block, IBlockState blockState) {
/*  61 */     if (block.isFullCube())
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if (block.isOpaqueCube())
/*     */     {
/*  67 */       return false;
/*     */     }
/*  69 */     if (block instanceof net.minecraft.block.BlockSnow)
/*     */     {
/*  71 */       return false;
/*     */     }
/*  73 */     if (!(block instanceof net.minecraft.block.BlockBush) || (!(block instanceof net.minecraft.block.BlockDoublePlant) && !(block instanceof net.minecraft.block.BlockFlower) && !(block instanceof net.minecraft.block.BlockMushroom) && !(block instanceof net.minecraft.block.BlockSapling) && !(block instanceof net.minecraft.block.BlockTallGrass))) {
/*     */       
/*  75 */       if (!(block instanceof net.minecraft.block.BlockFence) && !(block instanceof net.minecraft.block.BlockFenceGate) && !(block instanceof net.minecraft.block.BlockFlowerPot) && !(block instanceof net.minecraft.block.BlockPane) && !(block instanceof net.minecraft.block.BlockReed) && !(block instanceof net.minecraft.block.BlockWall)) {
/*     */         
/*  77 */         if (block instanceof net.minecraft.block.BlockRedstoneTorch && blockState.getValue((IProperty)BlockTorch.FACING) == EnumFacing.UP)
/*     */         {
/*  79 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  83 */         if (block instanceof BlockLever) {
/*     */           
/*  85 */           Object object = blockState.getValue((IProperty)BlockLever.FACING);
/*     */           
/*  87 */           if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z)
/*     */           {
/*  89 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*  93 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  98 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 103 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\BetterSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */