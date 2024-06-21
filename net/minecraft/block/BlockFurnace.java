/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFurnace extends BlockContainer {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */   
/*     */   private final boolean isBurning;
/*     */   private static boolean keepInventory;
/*     */   
/*     */   protected BlockFurnace(boolean isBurning) {
/*  32 */     super(Material.rock);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  34 */     this.isBurning = isBurning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  44 */     return Item.getItemFromBlock(Blocks.furnace);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  49 */     setDefaultFacing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
/*  54 */     if (!worldIn.isRemote) {
/*     */       
/*  56 */       Block block = worldIn.getBlockState(pos.north()).getBlock();
/*  57 */       Block block1 = worldIn.getBlockState(pos.south()).getBlock();
/*  58 */       Block block2 = worldIn.getBlockState(pos.west()).getBlock();
/*  59 */       Block block3 = worldIn.getBlockState(pos.east()).getBlock();
/*  60 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  62 */       if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
/*     */         
/*  64 */         enumfacing = EnumFacing.SOUTH;
/*     */       }
/*  66 */       else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
/*     */         
/*  68 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*  70 */       else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
/*     */         
/*  72 */         enumfacing = EnumFacing.EAST;
/*     */       }
/*  74 */       else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
/*     */         
/*  76 */         enumfacing = EnumFacing.WEST;
/*     */       } 
/*     */       
/*  79 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  86 */     if (this.isBurning) {
/*     */       
/*  88 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  89 */       double d0 = pos.getX() + 0.5D;
/*  90 */       double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
/*  91 */       double d2 = pos.getZ() + 0.5D;
/*  92 */       double d3 = 0.52D;
/*  93 */       double d4 = rand.nextDouble() * 0.6D - 0.3D;
/*     */       
/*  95 */       switch (enumfacing) {
/*     */         
/*     */         case WEST:
/*  98 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*  99 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 103 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 104 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 108 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
/* 109 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 113 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
/* 114 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 121 */     if (worldIn.isRemote)
/*     */     {
/* 123 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 127 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 129 */     if (tileentity instanceof TileEntityFurnace) {
/*     */       
/* 131 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 132 */       playerIn.triggerAchievement(StatList.field_181741_Y);
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setState(boolean active, World worldIn, BlockPos pos) {
/* 141 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 142 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 143 */     keepInventory = true;
/*     */     
/* 145 */     if (active) {
/*     */       
/* 147 */       worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 148 */       worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     }
/*     */     else {
/*     */       
/* 152 */       worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 153 */       worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */     
/* 156 */     keepInventory = false;
/*     */     
/* 158 */     if (tileentity != null) {
/*     */       
/* 160 */       tileentity.validate();
/* 161 */       worldIn.setTileEntity(pos, tileentity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 170 */     return (TileEntity)new TileEntityFurnace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 179 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 187 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()), 2);
/*     */     
/* 189 */     if (stack.hasDisplayName()) {
/*     */       
/* 191 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 193 */       if (tileentity instanceof TileEntityFurnace)
/*     */       {
/* 195 */         ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 202 */     if (!keepInventory) {
/*     */       
/* 204 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 206 */       if (tileentity instanceof TileEntityFurnace) {
/*     */         
/* 208 */         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 209 */         worldIn.updateComparatorOutputLevel(pos, this);
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 223 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 231 */     return Item.getItemFromBlock(Blocks.furnace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 239 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 247 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 255 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 257 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 259 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 262 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 270 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 275 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */