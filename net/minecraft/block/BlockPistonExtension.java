/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonExtension
/*     */   extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  26 */   public static final PropertyEnum<EnumPistonType> TYPE = PropertyEnum.create("type", EnumPistonType.class);
/*  27 */   public static final PropertyBool SHORT = PropertyBool.create("short");
/*     */ 
/*     */   
/*     */   public BlockPistonExtension() {
/*  31 */     super(Material.piston);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, EnumPistonType.DEFAULT).withProperty((IProperty)SHORT, Boolean.FALSE));
/*  33 */     setStepSound(soundTypePiston);
/*  34 */     setHardness(0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/*  39 */     if (player.capabilities.isCreativeMode) {
/*     */       
/*  41 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  43 */       if (enumfacing != null) {
/*     */         
/*  45 */         BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/*  46 */         Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */         
/*  48 */         if (block == Blocks.piston || block == Blocks.sticky_piston)
/*     */         {
/*  50 */           worldIn.setBlockToAir(blockpos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  60 */     super.breakBlock(worldIn, pos, state);
/*  61 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/*  62 */     pos = pos.offset(enumfacing);
/*  63 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  65 */     if ((iblockstate.getBlock() == Blocks.piston || iblockstate.getBlock() == Blocks.sticky_piston) && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue()) {
/*     */       
/*  67 */       iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/*  68 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 113 */     applyHeadBounds(state);
/* 114 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 115 */     applyCoreBounds(state);
/* 116 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 117 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyCoreBounds(IBlockState state) {
/* 122 */     float f = 0.25F;
/* 123 */     float f1 = 0.375F;
/* 124 */     float f2 = 0.625F;
/* 125 */     float f3 = 0.25F;
/* 126 */     float f4 = 0.75F;
/*     */     
/* 128 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case DOWN:
/* 131 */         setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/*     */         break;
/*     */       
/*     */       case UP:
/* 135 */         setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 139 */         setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 143 */         setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 147 */         setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/*     */         break;
/*     */       
/*     */       case EAST:
/* 151 */         setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 157 */     applyHeadBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyHeadBounds(IBlockState state) {
/* 162 */     float f = 0.25F;
/* 163 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 165 */     if (enumfacing != null)
/*     */     {
/* 167 */       switch (enumfacing) {
/*     */         
/*     */         case DOWN:
/* 170 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*     */           break;
/*     */         
/*     */         case UP:
/* 174 */           setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 178 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 182 */           setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 186 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 190 */           setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 200 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 201 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 202 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/* 204 */     if (iblockstate.getBlock() != Blocks.piston && iblockstate.getBlock() != Blocks.sticky_piston) {
/*     */       
/* 206 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */     else {
/*     */       
/* 210 */       iblockstate.getBlock().onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 221 */     int i = meta & 0x7;
/* 222 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 230 */     return (worldIn.getBlockState(pos).getValue((IProperty)TYPE) == EnumPistonType.STICKY) ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 238 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 246 */     int i = 0;
/* 247 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 249 */     if (state.getValue((IProperty)TYPE) == EnumPistonType.STICKY)
/*     */     {
/* 251 */       i |= 0x8;
/*     */     }
/*     */     
/* 254 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 259 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE, (IProperty)SHORT });
/*     */   }
/*     */   
/*     */   public enum EnumPistonType
/*     */     implements IStringSerializable {
/* 264 */     DEFAULT("normal"),
/* 265 */     STICKY("sticky");
/*     */     
/*     */     private final String VARIANT;
/*     */ 
/*     */     
/*     */     EnumPistonType(String name) {
/* 271 */       this.VARIANT = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 276 */       return this.VARIANT;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 281 */       return this.VARIANT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockPistonExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */