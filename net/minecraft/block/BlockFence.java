/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemLead;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFence
/*     */   extends Block
/*     */ {
/*  24 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*     */ 
/*     */   
/*  27 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*     */ 
/*     */   
/*  30 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*     */ 
/*     */   
/*  33 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */ 
/*     */   
/*     */   public BlockFence(Material materialIn) {
/*  37 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFence(Material p_i46395_1_, MapColor p_i46395_2_) {
/*  42 */     super(p_i46395_1_, p_i46395_2_);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.FALSE).withProperty((IProperty)EAST, Boolean.FALSE).withProperty((IProperty)SOUTH, Boolean.FALSE).withProperty((IProperty)WEST, Boolean.FALSE));
/*  44 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  54 */     boolean flag = canConnectTo((IBlockAccess)worldIn, pos.north());
/*  55 */     boolean flag1 = canConnectTo((IBlockAccess)worldIn, pos.south());
/*  56 */     boolean flag2 = canConnectTo((IBlockAccess)worldIn, pos.west());
/*  57 */     boolean flag3 = canConnectTo((IBlockAccess)worldIn, pos.east());
/*  58 */     float f = 0.375F;
/*  59 */     float f1 = 0.625F;
/*  60 */     float f2 = 0.375F;
/*  61 */     float f3 = 0.625F;
/*     */     
/*  63 */     if (flag)
/*     */     {
/*  65 */       f2 = 0.0F;
/*     */     }
/*     */     
/*  68 */     if (flag1)
/*     */     {
/*  70 */       f3 = 1.0F;
/*     */     }
/*     */     
/*  73 */     if (flag || flag1) {
/*     */       
/*  75 */       setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
/*  76 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  79 */     f2 = 0.375F;
/*  80 */     f3 = 0.625F;
/*     */     
/*  82 */     if (flag2)
/*     */     {
/*  84 */       f = 0.0F;
/*     */     }
/*     */     
/*  87 */     if (flag3)
/*     */     {
/*  89 */       f1 = 1.0F;
/*     */     }
/*     */     
/*  92 */     if (flag2 || flag3 || (!flag && !flag1)) {
/*     */       
/*  94 */       setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
/*  95 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  98 */     if (flag)
/*     */     {
/* 100 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 103 */     if (flag1)
/*     */     {
/* 105 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 108 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 113 */     boolean flag = canConnectTo(worldIn, pos.north());
/* 114 */     boolean flag1 = canConnectTo(worldIn, pos.south());
/* 115 */     boolean flag2 = canConnectTo(worldIn, pos.west());
/* 116 */     boolean flag3 = canConnectTo(worldIn, pos.east());
/* 117 */     float f = 0.375F;
/* 118 */     float f1 = 0.625F;
/* 119 */     float f2 = 0.375F;
/* 120 */     float f3 = 0.625F;
/*     */     
/* 122 */     if (flag)
/*     */     {
/* 124 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 127 */     if (flag1)
/*     */     {
/* 129 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 132 */     if (flag2)
/*     */     {
/* 134 */       f = 0.0F;
/*     */     }
/*     */     
/* 137 */     if (flag3)
/*     */     {
/* 139 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 142 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
/* 165 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 166 */     return (block == Blocks.barrier) ? false : (((!(block instanceof BlockFence) || block.blockMaterial != this.blockMaterial) && !(block instanceof BlockFenceGate)) ? ((block.blockMaterial.isOpaque() && block.isFullCube()) ? ((block.blockMaterial != Material.gourd)) : false) : true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 176 */     return worldIn.isRemote ? true : ItemLead.attachToFence(playerIn, worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 193 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canConnectTo(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canConnectTo(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canConnectTo(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canConnectTo(worldIn, pos.west())));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 198 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockFence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */