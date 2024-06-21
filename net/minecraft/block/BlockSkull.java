/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.BlockStateHelper;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSkull extends BlockContainer {
/*  38 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  39 */   public static final PropertyBool NODROP = PropertyBool.create("nodrop");
/*  40 */   private static final Predicate<BlockWorldState> IS_WITHER_SKELETON = new Predicate<BlockWorldState>()
/*     */     {
/*     */       public boolean apply(BlockWorldState p_apply_1_)
/*     */       {
/*  44 */         return (p_apply_1_.getBlockState() != null && p_apply_1_.getBlockState().getBlock() == Blocks.skull && p_apply_1_.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)p_apply_1_.getTileEntity()).getSkullType() == 1);
/*     */       }
/*     */     };
/*     */   
/*     */   private BlockPattern witherBasePattern;
/*     */   private BlockPattern witherPattern;
/*     */   
/*     */   protected BlockSkull() {
/*  52 */     super(Material.circuits);
/*  53 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)NODROP, Boolean.FALSE));
/*  54 */     setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  62 */     return StatCollector.translateToLocal("tile.skull.skeleton.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  80 */     switch ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/*  84 */         setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */         return;
/*     */       
/*     */       case NORTH:
/*  88 */         setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
/*     */         return;
/*     */       
/*     */       case SOUTH:
/*  92 */         setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
/*     */         return;
/*     */       
/*     */       case WEST:
/*  96 */         setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F); return;
/*     */       case EAST:
/*     */         break;
/*     */     } 
/* 100 */     setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 106 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 107 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 116 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)NODROP, Boolean.FALSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 124 */     return (TileEntity)new TileEntitySkull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 132 */     return Items.skull;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 137 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 138 */     return (tileentity instanceof TileEntitySkull) ? ((TileEntitySkull)tileentity).getSkullType() : super.getDamageValue(worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 153 */     if (player.capabilities.isCreativeMode) {
/*     */       
/* 155 */       state = state.withProperty((IProperty)NODROP, Boolean.TRUE);
/* 156 */       worldIn.setBlockState(pos, state, 4);
/*     */     } 
/*     */     
/* 159 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 164 */     if (!worldIn.isRemote) {
/*     */       
/* 166 */       if (!((Boolean)state.getValue((IProperty)NODROP)).booleanValue()) {
/*     */         
/* 168 */         TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */         
/* 170 */         if (tileentity instanceof TileEntitySkull) {
/*     */           
/* 172 */           TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/* 173 */           ItemStack itemstack = new ItemStack(Items.skull, 1, getDamageValue(worldIn, pos));
/*     */           
/* 175 */           if (tileentityskull.getSkullType() == 3 && tileentityskull.getPlayerProfile() != null) {
/*     */             
/* 177 */             itemstack.setTagCompound(new NBTTagCompound());
/* 178 */             NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 179 */             NBTUtil.writeGameProfile(nbttagcompound, tileentityskull.getPlayerProfile());
/* 180 */             itemstack.getTagCompound().setTag("SkullOwner", (NBTBase)nbttagcompound);
/*     */           } 
/*     */           
/* 183 */           spawnAsEntity(worldIn, pos, itemstack);
/*     */         } 
/*     */       } 
/*     */       
/* 187 */       super.breakBlock(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 198 */     return Items.skull;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDispenserPlace(World worldIn, BlockPos pos, ItemStack stack) {
/* 203 */     return (stack.getMetadata() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote) ? ((getWitherBasePattern().match(worldIn, pos) != null)) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkWitherSpawn(World worldIn, BlockPos pos, TileEntitySkull te) {
/* 208 */     if (te.getSkullType() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote) {
/*     */       
/* 210 */       BlockPattern blockpattern = getWitherPattern();
/* 211 */       BlockPattern.PatternHelper blockpattern$patternhelper = blockpattern.match(worldIn, pos);
/*     */       
/* 213 */       if (blockpattern$patternhelper != null) {
/*     */         
/* 215 */         for (int i = 0; i < 3; i++) {
/*     */           
/* 217 */           BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, 0, 0);
/* 218 */           worldIn.setBlockState(blockworldstate.getPos(), blockworldstate.getBlockState().withProperty((IProperty)NODROP, Boolean.TRUE), 2);
/*     */         } 
/*     */         
/* 221 */         for (int j = 0; j < blockpattern.getPalmLength(); j++) {
/*     */           
/* 223 */           for (int k = 0; k < blockpattern.getThumbLength(); k++) {
/*     */             
/* 225 */             BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(j, k, 0);
/* 226 */             worldIn.setBlockState(blockworldstate1.getPos(), Blocks.air.getDefaultState(), 2);
/*     */           } 
/*     */         } 
/*     */         
/* 230 */         BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 0, 0).getPos();
/* 231 */         EntityWither entitywither = new EntityWither(worldIn);
/* 232 */         BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
/* 233 */         entitywither.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.55D, blockpos1.getZ() + 0.5D, (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? 0.0F : 90.0F, 0.0F);
/* 234 */         entitywither.renderYawOffset = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? 0.0F : 90.0F;
/* 235 */         entitywither.func_82206_m();
/*     */         
/* 237 */         for (EntityPlayer entityplayer : worldIn.getEntitiesWithinAABB(EntityPlayer.class, entitywither.getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D)))
/*     */         {
/* 239 */           entityplayer.triggerAchievement((StatBase)AchievementList.spawnWither);
/*     */         }
/*     */         
/* 242 */         worldIn.spawnEntityInWorld((Entity)entitywither);
/*     */         
/* 244 */         for (int l = 0; l < 120; l++)
/*     */         {
/* 246 */           worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, blockpos.getX() + worldIn.rand.nextDouble(), (blockpos.getY() - 2) + worldIn.rand.nextDouble() * 3.9D, blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 249 */         for (int i1 = 0; i1 < blockpattern.getPalmLength(); i1++) {
/*     */           
/* 251 */           for (int j1 = 0; j1 < blockpattern.getThumbLength(); j1++) {
/*     */             
/* 253 */             BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(i1, j1, 0);
/* 254 */             worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.air);
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
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 266 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta & 0x7)).withProperty((IProperty)NODROP, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 274 */     int i = 0;
/* 275 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 277 */     if (((Boolean)state.getValue((IProperty)NODROP)).booleanValue())
/*     */     {
/* 279 */       i |= 0x8;
/*     */     }
/*     */     
/* 282 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 287 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)NODROP });
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getWitherBasePattern() {
/* 292 */     if (this.witherBasePattern == null)
/*     */     {
/* 294 */       this.witherBasePattern = FactoryBlockPattern.start().aisle(new String[] { "   ", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.soul_sand))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
/*     */     }
/*     */     
/* 297 */     return this.witherBasePattern;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getWitherPattern() {
/* 302 */     if (this.witherPattern == null)
/*     */     {
/* 304 */       this.witherPattern = FactoryBlockPattern.start().aisle(new String[] { "^^^", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.soul_sand))).where('^', IS_WITHER_SKELETON).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
/*     */     }
/*     */     
/* 307 */     return this.witherPattern;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */