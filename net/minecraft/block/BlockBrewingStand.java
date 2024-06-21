/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBrewingStand extends BlockContainer {
/*  31 */   public static final PropertyBool[] HAS_BOTTLE = new PropertyBool[] { PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2") };
/*     */ 
/*     */   
/*     */   public BlockBrewingStand() {
/*  35 */     super(Material.iron);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)HAS_BOTTLE[0], Boolean.FALSE).withProperty((IProperty)HAS_BOTTLE[1], Boolean.FALSE).withProperty((IProperty)HAS_BOTTLE[2], Boolean.FALSE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  44 */     return StatCollector.translateToLocal("item.brewingStand.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  60 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  68 */     return (TileEntity)new TileEntityBrewingStand();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  83 */     setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
/*  84 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  85 */     setBlockBoundsForItemRender();
/*  86 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  94 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  99 */     if (worldIn.isRemote)
/*     */     {
/* 101 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 105 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 107 */     if (tileentity instanceof TileEntityBrewingStand) {
/*     */       
/* 109 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 110 */       playerIn.triggerAchievement(StatList.field_181729_M);
/*     */     } 
/*     */     
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 122 */     if (stack.hasDisplayName()) {
/*     */       
/* 124 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 126 */       if (tileentity instanceof TileEntityBrewingStand)
/*     */       {
/* 128 */         ((TileEntityBrewingStand)tileentity).setName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 135 */     double d0 = (pos.getX() + 0.4F + rand.nextFloat() * 0.2F);
/* 136 */     double d1 = (pos.getY() + 0.7F + rand.nextFloat() * 0.3F);
/* 137 */     double d2 = (pos.getZ() + 0.4F + rand.nextFloat() * 0.2F);
/* 138 */     worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 143 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 145 */     if (tileentity instanceof TileEntityBrewingStand)
/*     */     {
/* 147 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/*     */     }
/*     */     
/* 150 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 160 */     return Items.brewing_stand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 168 */     return Items.brewing_stand;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 178 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 183 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 191 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 193 */     for (int i = 0; i < 3; i++)
/*     */     {
/* 195 */       iblockstate = iblockstate.withProperty((IProperty)HAS_BOTTLE[i], Boolean.valueOf(((meta & 1 << i) > 0)));
/*     */     }
/*     */     
/* 198 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 206 */     int i = 0;
/*     */     
/* 208 */     for (int j = 0; j < 3; j++) {
/*     */       
/* 210 */       if (((Boolean)state.getValue((IProperty)HAS_BOTTLE[j])).booleanValue())
/*     */       {
/* 212 */         i |= 1 << j;
/*     */       }
/*     */     } 
/*     */     
/* 216 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 221 */     return new BlockState(this, new IProperty[] { (IProperty)HAS_BOTTLE[0], (IProperty)HAS_BOTTLE[1], (IProperty)HAS_BOTTLE[2] });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */