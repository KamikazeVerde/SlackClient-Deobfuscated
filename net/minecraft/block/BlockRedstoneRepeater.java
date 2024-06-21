/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneRepeater
/*     */   extends BlockRedstoneDiode {
/*  22 */   public static final PropertyBool LOCKED = PropertyBool.create("locked");
/*  23 */   public static final PropertyInteger DELAY = PropertyInteger.create("delay", 1, 4);
/*     */ 
/*     */   
/*     */   protected BlockRedstoneRepeater(boolean powered) {
/*  27 */     super(powered);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)DELAY, Integer.valueOf(1)).withProperty((IProperty)LOCKED, Boolean.FALSE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  36 */     return StatCollector.translateToLocal("item.diode.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  45 */     return state.withProperty((IProperty)LOCKED, Boolean.valueOf(isLocked(worldIn, pos, state)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  50 */     if (!playerIn.capabilities.allowEdit)
/*     */     {
/*  52 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  56 */     worldIn.setBlockState(pos, state.cycleProperty((IProperty)DELAY), 3);
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getDelay(IBlockState state) {
/*  63 */     return ((Integer)state.getValue((IProperty)DELAY)).intValue() * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState) {
/*  68 */     Integer integer = (Integer)unpoweredState.getValue((IProperty)DELAY);
/*  69 */     Boolean obool = (Boolean)unpoweredState.getValue((IProperty)LOCKED);
/*  70 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue((IProperty)FACING);
/*  71 */     return Blocks.powered_repeater.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)DELAY, integer).withProperty((IProperty)LOCKED, obool);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState) {
/*  76 */     Integer integer = (Integer)poweredState.getValue((IProperty)DELAY);
/*  77 */     Boolean obool = (Boolean)poweredState.getValue((IProperty)LOCKED);
/*  78 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue((IProperty)FACING);
/*  79 */     return Blocks.unpowered_repeater.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)DELAY, integer).withProperty((IProperty)LOCKED, obool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  89 */     return Items.repeater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  97 */     return Items.repeater;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 102 */     return (getPowerOnSides(worldIn, pos, state) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canPowerSide(Block blockIn) {
/* 107 */     return isRedstoneRepeaterBlockID(blockIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 112 */     if (this.isRepeaterPowered) {
/*     */       
/* 114 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 115 */       double d0 = (pos.getX() + 0.5F) + (rand.nextFloat() - 0.5F) * 0.2D;
/* 116 */       double d1 = (pos.getY() + 0.4F) + (rand.nextFloat() - 0.5F) * 0.2D;
/* 117 */       double d2 = (pos.getZ() + 0.5F) + (rand.nextFloat() - 0.5F) * 0.2D;
/* 118 */       float f = -5.0F;
/*     */       
/* 120 */       if (rand.nextBoolean())
/*     */       {
/* 122 */         f = (((Integer)state.getValue((IProperty)DELAY)).intValue() * 2 - 1);
/*     */       }
/*     */       
/* 125 */       f /= 16.0F;
/* 126 */       double d3 = (f * enumfacing.getFrontOffsetX());
/* 127 */       double d4 = (f * enumfacing.getFrontOffsetZ());
/* 128 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 134 */     super.breakBlock(worldIn, pos, state);
/* 135 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 143 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)LOCKED, Boolean.FALSE).withProperty((IProperty)DELAY, Integer.valueOf(1 + (meta >> 2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 151 */     int i = 0;
/* 152 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 153 */     i |= ((Integer)state.getValue((IProperty)DELAY)).intValue() - 1 << 2;
/* 154 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 159 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)DELAY, (IProperty)LOCKED });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockRedstoneRepeater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */