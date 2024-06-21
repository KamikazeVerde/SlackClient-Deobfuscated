/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTrapDoor extends Block {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  27 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  28 */   public static final PropertyEnum<DoorHalf> HALF = PropertyEnum.create("half", DoorHalf.class);
/*     */ 
/*     */   
/*     */   protected BlockTrapDoor(Material materialIn) {
/*  32 */     super(materialIn);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.FALSE).withProperty((IProperty)HALF, DoorHalf.BOTTOM));
/*  34 */     float f = 0.5F;
/*  35 */     float f1 = 1.0F;
/*  36 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  37 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  55 */     return !((Boolean)worldIn.getBlockState(pos).getValue((IProperty)OPEN)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  60 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  61 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  66 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  67 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  72 */     setBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  80 */     float f = 0.1875F;
/*  81 */     setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(IBlockState state) {
/*  86 */     if (state.getBlock() == this) {
/*     */       
/*  88 */       boolean flag = (state.getValue((IProperty)HALF) == DoorHalf.TOP);
/*  89 */       Boolean obool = (Boolean)state.getValue((IProperty)OPEN);
/*  90 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  91 */       float f = 0.1875F;
/*     */       
/*  93 */       if (flag) {
/*     */         
/*  95 */         setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       else {
/*     */         
/*  99 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
/*     */       } 
/*     */       
/* 102 */       if (obool.booleanValue()) {
/*     */         
/* 104 */         if (enumfacing == EnumFacing.NORTH)
/*     */         {
/* 106 */           setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 109 */         if (enumfacing == EnumFacing.SOUTH)
/*     */         {
/* 111 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
/*     */         }
/*     */         
/* 114 */         if (enumfacing == EnumFacing.WEST)
/*     */         {
/* 116 */           setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 119 */         if (enumfacing == EnumFacing.EAST)
/*     */         {
/* 121 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 129 */     if (this.blockMaterial == Material.iron)
/*     */     {
/* 131 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 135 */     state = state.cycleProperty((IProperty)OPEN);
/* 136 */     worldIn.setBlockState(pos, state, 2);
/* 137 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 147 */     if (!worldIn.isRemote) {
/*     */       
/* 149 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */       
/* 151 */       if (!isValidSupportBlock(worldIn.getBlockState(blockpos).getBlock())) {
/*     */         
/* 153 */         worldIn.setBlockToAir(pos);
/* 154 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       }
/*     */       else {
/*     */         
/* 158 */         boolean flag = worldIn.isBlockPowered(pos);
/*     */         
/* 160 */         if (flag || neighborBlock.canProvidePower()) {
/*     */           
/* 162 */           boolean flag1 = ((Boolean)state.getValue((IProperty)OPEN)).booleanValue();
/*     */           
/* 164 */           if (flag1 != flag) {
/*     */             
/* 166 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 167 */             worldIn.playAuxSFXAtEntity(null, flag ? 1003 : 1006, pos, 0);
/*     */           } 
/*     */         } 
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
/* 182 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 183 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 192 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 194 */     if (facing.getAxis().isHorizontal()) {
/*     */       
/* 196 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)OPEN, Boolean.FALSE);
/* 197 */       iblockstate = iblockstate.withProperty((IProperty)HALF, (hitY > 0.5F) ? DoorHalf.TOP : DoorHalf.BOTTOM);
/*     */     } 
/*     */     
/* 200 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 208 */     return (!side.getAxis().isVertical() && isValidSupportBlock(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static EnumFacing getFacing(int meta) {
/* 213 */     switch (meta & 0x3) {
/*     */       
/*     */       case 0:
/* 216 */         return EnumFacing.NORTH;
/*     */       
/*     */       case 1:
/* 219 */         return EnumFacing.SOUTH;
/*     */       
/*     */       case 2:
/* 222 */         return EnumFacing.WEST;
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return EnumFacing.EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getMetaForFacing(EnumFacing facing) {
/* 232 */     switch (facing) {
/*     */       
/*     */       case NORTH:
/* 235 */         return 0;
/*     */       
/*     */       case SOUTH:
/* 238 */         return 1;
/*     */       
/*     */       case WEST:
/* 241 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/* 245 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidSupportBlock(Block blockIn) {
/* 251 */     return ((blockIn.blockMaterial.isOpaque() && blockIn.isFullCube()) || blockIn == Blocks.glowstone || blockIn instanceof BlockSlab || blockIn instanceof BlockStairs);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 256 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 264 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? DoorHalf.BOTTOM : DoorHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 272 */     int i = 0;
/* 273 */     i |= getMetaForFacing((EnumFacing)state.getValue((IProperty)FACING));
/*     */     
/* 275 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */     {
/* 277 */       i |= 0x4;
/*     */     }
/*     */     
/* 280 */     if (state.getValue((IProperty)HALF) == DoorHalf.TOP)
/*     */     {
/* 282 */       i |= 0x8;
/*     */     }
/*     */     
/* 285 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 290 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)OPEN, (IProperty)HALF });
/*     */   }
/*     */   
/*     */   public enum DoorHalf
/*     */     implements IStringSerializable {
/* 295 */     TOP("top"),
/* 296 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     DoorHalf(String name) {
/* 302 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 307 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 312 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockTrapDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */