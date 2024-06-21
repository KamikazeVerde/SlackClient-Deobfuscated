/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBanner extends BlockContainer {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  27 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockBanner() {
/*  31 */     super(Material.wood);
/*  32 */     float f = 0.25F;
/*  33 */     float f1 = 1.0F;
/*  34 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  42 */     return StatCollector.translateToLocal("item.banner.white.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  47 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  52 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  53 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181623_g() {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  84 */     return (TileEntity)new TileEntityBanner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  94 */     return Items.banner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 102 */     return Items.banner;
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
/* 113 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 115 */     if (tileentity instanceof TileEntityBanner) {
/*     */       
/* 117 */       ItemStack itemstack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileentity).getBaseColor());
/* 118 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 119 */       tileentity.writeToNBT(nbttagcompound);
/* 120 */       nbttagcompound.removeTag("x");
/* 121 */       nbttagcompound.removeTag("y");
/* 122 */       nbttagcompound.removeTag("z");
/* 123 */       nbttagcompound.removeTag("id");
/* 124 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 125 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/* 129 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 135 */     return (!func_181087_e(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 140 */     if (te instanceof TileEntityBanner) {
/*     */       
/* 142 */       TileEntityBanner tileentitybanner = (TileEntityBanner)te;
/* 143 */       ItemStack itemstack = new ItemStack(Items.banner, 1, ((TileEntityBanner)te).getBaseColor());
/* 144 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 145 */       TileEntityBanner.func_181020_a(nbttagcompound, tileentitybanner.getBaseColor(), tileentitybanner.func_181021_d());
/* 146 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 147 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/* 151 */       super.harvestBlock(worldIn, player, pos, state, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class BlockBannerHanging
/*     */     extends BlockBanner
/*     */   {
/*     */     public BlockBannerHanging() {
/* 159 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*     */     }
/*     */ 
/*     */     
/*     */     public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 164 */       EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 165 */       float f = 0.0F;
/* 166 */       float f1 = 0.78125F;
/* 167 */       float f2 = 0.0F;
/* 168 */       float f3 = 1.0F;
/* 169 */       float f4 = 0.125F;
/* 170 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 172 */       switch (enumfacing) {
/*     */ 
/*     */         
/*     */         default:
/* 176 */           setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
/*     */           return;
/*     */         
/*     */         case SOUTH:
/* 180 */           setBlockBounds(f2, f, 0.0F, f3, f1, f4);
/*     */           return;
/*     */         
/*     */         case WEST:
/* 184 */           setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3); return;
/*     */         case EAST:
/*     */           break;
/*     */       } 
/* 188 */       setBlockBounds(0.0F, f, f2, f4, f1, f3);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 194 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 196 */       if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid()) {
/*     */         
/* 198 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 199 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 202 */       super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 207 */       EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */       
/* 209 */       if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */       {
/* 211 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*     */       
/* 214 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 219 */       return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     protected BlockState createBlockState() {
/* 224 */       return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockBannerStanding
/*     */     extends BlockBanner
/*     */   {
/*     */     public BlockBannerStanding() {
/* 232 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 237 */       if (!worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
/*     */         
/* 239 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 240 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 243 */       super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 248 */       return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 253 */       return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     protected BlockState createBlockState() {
/* 258 */       return new BlockState(this, new IProperty[] { (IProperty)ROTATION });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */