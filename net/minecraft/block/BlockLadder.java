/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLadder
/*     */   extends Block {
/*  21 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */ 
/*     */   
/*     */   protected BlockLadder() {
/*  25 */     super(Material.circuits);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  27 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  32 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  33 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  38 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  39 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  44 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  46 */     if (iblockstate.getBlock() == this) {
/*     */       float f;
/*     */       
/*  49 */       if (ViaLoadingBase.getInstance().getTargetVersion().is113Plus()) {
/*  50 */         f = 0.1875F;
/*     */       } else {
/*  52 */         f = 0.125F;
/*     */       } 
/*     */       
/*  55 */       switch ((EnumFacing)iblockstate.getValue((IProperty)FACING)) {
/*     */         
/*     */         case NORTH:
/*  58 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */           return;
/*     */         
/*     */         case SOUTH:
/*  62 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */           return;
/*     */         
/*     */         case WEST:
/*  66 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/*  71 */       setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  91 */     return worldIn.getBlockState(pos.west()).getBlock().isNormalCube() ? true : (worldIn.getBlockState(pos.east()).getBlock().isNormalCube() ? true : (worldIn.getBlockState(pos.north()).getBlock().isNormalCube() ? true : worldIn.getBlockState(pos.south()).getBlock().isNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 100 */     if (facing.getAxis().isHorizontal() && canBlockStay(worldIn, pos, facing))
/*     */     {
/* 102 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */ 
/*     */     
/* 106 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 108 */       if (canBlockStay(worldIn, pos, enumfacing))
/*     */       {
/* 110 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 123 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 125 */     if (!canBlockStay(worldIn, pos, enumfacing)) {
/*     */       
/* 127 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 128 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 131 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
/* 136 */     return worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock().isNormalCube();
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 141 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 149 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 151 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 153 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 156 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 164 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 169 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockLadder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */