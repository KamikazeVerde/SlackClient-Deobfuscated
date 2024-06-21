/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockSlab
/*     */   extends Block {
/*  22 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*     */ 
/*     */   
/*     */   public BlockSlab(Material materialIn) {
/*  26 */     super(materialIn);
/*     */     
/*  28 */     if (isDouble()) {
/*     */       
/*  30 */       this.fullBlock = true;
/*     */     }
/*     */     else {
/*     */       
/*  34 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */     
/*  37 */     setLightOpacity(255);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  47 */     if (isDouble()) {
/*     */       
/*  49 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  53 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/*  55 */       if (iblockstate.getBlock() == this)
/*     */       {
/*  57 */         if (iblockstate.getValue((IProperty)HALF) == EnumBlockHalf.TOP) {
/*     */           
/*  59 */           setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         else {
/*     */           
/*  63 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  74 */     if (isDouble()) {
/*     */       
/*  76 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  80 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  91 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  92 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 100 */     return isDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 109 */     IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)HALF, EnumBlockHalf.BOTTOM);
/* 110 */     return isDouble() ? iblockstate : ((facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ? iblockstate : iblockstate.withProperty((IProperty)HALF, EnumBlockHalf.TOP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 118 */     return isDouble() ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 123 */     return isDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 128 */     if (isDouble())
/*     */     {
/* 130 */       return super.shouldSideBeRendered(worldIn, pos, side);
/*     */     }
/* 132 */     if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(worldIn, pos, side))
/*     */     {
/* 134 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 138 */     BlockPos blockpos = pos.offset(side.getOpposite());
/* 139 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 140 */     IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
/* 141 */     boolean flag = (isSlab(iblockstate.getBlock()) && iblockstate.getValue((IProperty)HALF) == EnumBlockHalf.TOP);
/* 142 */     boolean flag1 = (isSlab(iblockstate1.getBlock()) && iblockstate1.getValue((IProperty)HALF) == EnumBlockHalf.TOP);
/* 143 */     return flag1 ? ((side == EnumFacing.DOWN) ? true : ((side == EnumFacing.UP && super.shouldSideBeRendered(worldIn, pos, side)) ? true : ((!isSlab(iblockstate.getBlock()) || !flag)))) : ((side == EnumFacing.UP) ? true : ((side == EnumFacing.DOWN && super.shouldSideBeRendered(worldIn, pos, side)) ? true : ((!isSlab(iblockstate.getBlock()) || flag))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isSlab(Block blockIn) {
/* 149 */     return (blockIn == Blocks.stone_slab || blockIn == Blocks.wooden_slab || blockIn == Blocks.stone_slab2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getUnlocalizedName(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 159 */     return super.getDamageValue(worldIn, pos) & 0x7;
/*     */   }
/*     */   
/*     */   public abstract boolean isDouble();
/*     */   
/*     */   public abstract IProperty<?> getVariantProperty();
/*     */   
/*     */   public abstract Object getVariant(ItemStack paramItemStack);
/*     */   
/*     */   public enum EnumBlockHalf
/*     */     implements IStringSerializable {
/* 170 */     TOP("top"),
/* 171 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumBlockHalf(String name) {
/* 177 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 182 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 187 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */