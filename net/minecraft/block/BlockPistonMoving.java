/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonMoving
/*     */   extends BlockContainer {
/*  25 */   public static final PropertyDirection FACING = BlockPistonExtension.FACING;
/*  26 */   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockPistonExtension.TYPE;
/*     */ 
/*     */   
/*     */   public BlockPistonMoving() {
/*  30 */     super(Material.piston);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
/*  32 */     setHardness(-1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static TileEntity newTileEntity(IBlockState state, EnumFacing facing, boolean extending, boolean renderHead) {
/*  45 */     return (TileEntity)new TileEntityPiston(state, facing, extending, renderHead);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  50 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  52 */     if (tileentity instanceof TileEntityPiston) {
/*     */       
/*  54 */       ((TileEntityPiston)tileentity).clearPistonTileEntity();
/*     */     }
/*     */     else {
/*     */       
/*  58 */       super.breakBlock(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*  80 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*  81 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/*  83 */     if (iblockstate.getBlock() instanceof BlockPistonBase && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue())
/*     */     {
/*  85 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 104 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
/*     */       
/* 106 */       worldIn.setBlockToAir(pos);
/* 107 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 133 */     if (!worldIn.isRemote) {
/*     */       
/* 135 */       TileEntityPiston tileentitypiston = getTileEntity((IBlockAccess)worldIn, pos);
/*     */       
/* 137 */       if (tileentitypiston != null) {
/*     */         
/* 139 */         IBlockState iblockstate = tileentitypiston.getPistonState();
/* 140 */         iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 161 */     if (!worldIn.isRemote)
/*     */     {
/* 163 */       worldIn.getTileEntity(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 169 */     TileEntityPiston tileentitypiston = getTileEntity((IBlockAccess)worldIn, pos);
/*     */     
/* 171 */     if (tileentitypiston == null)
/*     */     {
/* 173 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 177 */     float f = tileentitypiston.getProgress(0.0F);
/*     */     
/* 179 */     if (tileentitypiston.isExtending())
/*     */     {
/* 181 */       f = 1.0F - f;
/*     */     }
/*     */     
/* 184 */     return getBoundingBox(worldIn, pos, tileentitypiston.getPistonState(), f, tileentitypiston.getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 190 */     TileEntityPiston tileentitypiston = getTileEntity(worldIn, pos);
/*     */     
/* 192 */     if (tileentitypiston != null) {
/*     */       
/* 194 */       IBlockState iblockstate = tileentitypiston.getPistonState();
/* 195 */       Block block = iblockstate.getBlock();
/*     */       
/* 197 */       if (block == this || block.getMaterial() == Material.air) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 202 */       float f = tileentitypiston.getProgress(0.0F);
/*     */       
/* 204 */       if (tileentitypiston.isExtending())
/*     */       {
/* 206 */         f = 1.0F - f;
/*     */       }
/*     */       
/* 209 */       block.setBlockBoundsBasedOnState(worldIn, pos);
/*     */       
/* 211 */       if (block == Blocks.piston || block == Blocks.sticky_piston)
/*     */       {
/* 213 */         f = 0.0F;
/*     */       }
/*     */       
/* 216 */       EnumFacing enumfacing = tileentitypiston.getFacing();
/* 217 */       this.minX = block.getBlockBoundsMinX() - (enumfacing.getFrontOffsetX() * f);
/* 218 */       this.minY = block.getBlockBoundsMinY() - (enumfacing.getFrontOffsetY() * f);
/* 219 */       this.minZ = block.getBlockBoundsMinZ() - (enumfacing.getFrontOffsetZ() * f);
/* 220 */       this.maxX = block.getBlockBoundsMaxX() - (enumfacing.getFrontOffsetX() * f);
/* 221 */       this.maxY = block.getBlockBoundsMaxY() - (enumfacing.getFrontOffsetY() * f);
/* 222 */       this.maxZ = block.getBlockBoundsMaxZ() - (enumfacing.getFrontOffsetZ() * f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(World worldIn, BlockPos pos, IBlockState extendingBlock, float progress, EnumFacing direction) {
/* 228 */     if (extendingBlock.getBlock() != this && extendingBlock.getBlock().getMaterial() != Material.air) {
/*     */       
/* 230 */       AxisAlignedBB axisalignedbb = extendingBlock.getBlock().getCollisionBoundingBox(worldIn, pos, extendingBlock);
/*     */       
/* 232 */       if (axisalignedbb == null)
/*     */       {
/* 234 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 238 */       double d0 = axisalignedbb.minX;
/* 239 */       double d1 = axisalignedbb.minY;
/* 240 */       double d2 = axisalignedbb.minZ;
/* 241 */       double d3 = axisalignedbb.maxX;
/* 242 */       double d4 = axisalignedbb.maxY;
/* 243 */       double d5 = axisalignedbb.maxZ;
/*     */       
/* 245 */       if (direction.getFrontOffsetX() < 0) {
/*     */         
/* 247 */         d0 -= (direction.getFrontOffsetX() * progress);
/*     */       }
/*     */       else {
/*     */         
/* 251 */         d3 -= (direction.getFrontOffsetX() * progress);
/*     */       } 
/*     */       
/* 254 */       if (direction.getFrontOffsetY() < 0) {
/*     */         
/* 256 */         d1 -= (direction.getFrontOffsetY() * progress);
/*     */       }
/*     */       else {
/*     */         
/* 260 */         d4 -= (direction.getFrontOffsetY() * progress);
/*     */       } 
/*     */       
/* 263 */       if (direction.getFrontOffsetZ() < 0) {
/*     */         
/* 265 */         d2 -= (direction.getFrontOffsetZ() * progress);
/*     */       }
/*     */       else {
/*     */         
/* 269 */         d5 -= (direction.getFrontOffsetZ() * progress);
/*     */       } 
/*     */       
/* 272 */       return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 277 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TileEntityPiston getTileEntity(IBlockAccess worldIn, BlockPos pos) {
/* 283 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 284 */     return (tileentity instanceof TileEntityPiston) ? (TileEntityPiston)tileentity : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 300 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)BlockPistonExtension.getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 308 */     int i = 0;
/* 309 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 311 */     if (state.getValue((IProperty)TYPE) == BlockPistonExtension.EnumPistonType.STICKY)
/*     */     {
/* 313 */       i |= 0x8;
/*     */     }
/*     */     
/* 316 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 321 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockPistonMoving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */