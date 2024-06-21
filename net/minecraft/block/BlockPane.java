/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPane
/*     */   extends Block {
/*  23 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  24 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  25 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  26 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */   
/*     */   private final boolean canDrop;
/*     */   
/*     */   protected BlockPane(Material materialIn, boolean canDrop) {
/*  31 */     super(materialIn);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.FALSE).withProperty((IProperty)EAST, Boolean.FALSE).withProperty((IProperty)SOUTH, Boolean.FALSE).withProperty((IProperty)WEST, Boolean.FALSE));
/*  33 */     this.canDrop = canDrop;
/*  34 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  43 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock()))).withProperty((IProperty)WEST, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock()))).withProperty((IProperty)EAST, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  53 */     return !this.canDrop ? null : super.getItemDropped(state, rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  71 */     return (worldIn.getBlockState(pos).getBlock() == this) ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  81 */     boolean flag = canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
/*  82 */     boolean flag1 = canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
/*  83 */     boolean flag2 = canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
/*  84 */     boolean flag3 = canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());
/*     */     
/*  86 */     if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/*  88 */       if (flag2)
/*     */       {
/*  90 */         setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
/*  91 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/*  93 */       else if (flag3)
/*     */       {
/*  95 */         setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  96 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 101 */       setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/* 102 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/* 105 */     if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/* 107 */       if (flag)
/*     */       {
/* 109 */         setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
/* 110 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/* 112 */       else if (flag1)
/*     */       {
/* 114 */         setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
/* 115 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 120 */       setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
/* 121 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 130 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 135 */     float f = 0.4375F;
/* 136 */     float f1 = 0.5625F;
/* 137 */     float f2 = 0.4375F;
/* 138 */     float f3 = 0.5625F;
/* 139 */     boolean flag = canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
/* 140 */     boolean flag1 = canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
/* 141 */     boolean flag2 = canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
/* 142 */     boolean flag3 = canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());
/*     */     
/* 144 */     if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/* 146 */       if (flag2)
/*     */       {
/* 148 */         f = 0.0F;
/*     */       }
/* 150 */       else if (flag3)
/*     */       {
/* 152 */         f1 = 1.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 157 */       f = 0.0F;
/* 158 */       f1 = 1.0F;
/*     */     } 
/*     */     
/* 161 */     if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/* 163 */       if (flag)
/*     */       {
/* 165 */         f2 = 0.0F;
/*     */       }
/* 167 */       else if (flag1)
/*     */       {
/* 169 */         f3 = 1.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 174 */       f2 = 0.0F;
/* 175 */       f3 = 1.0F;
/*     */     } 
/*     */     
/* 178 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean canPaneConnectToBlock(Block blockIn) {
/* 183 */     return (blockIn.isFullBlock() || blockIn == this || blockIn == Blocks.glass || blockIn == Blocks.stained_glass || blockIn == Blocks.stained_glass_pane || blockIn instanceof BlockPane);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 193 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 201 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 206 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */