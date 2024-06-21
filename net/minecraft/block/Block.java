/*      */ package net.minecraft.block;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.events.impl.player.CollideEvent;
/*      */ import cc.slack.features.modules.impl.render.ChestESP;
/*      */ import cc.slack.features.modules.impl.render.XRay;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockState;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ObjectIntIdentityMap;
/*      */ import net.minecraft.util.RegistryNamespacedDefaultedByKey;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ public class Block
/*      */ {
/*   44 */   private static final ResourceLocation AIR_ID = new ResourceLocation("air");
/*   45 */   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> blockRegistry = new RegistryNamespacedDefaultedByKey(AIR_ID);
/*   46 */   public static final ObjectIntIdentityMap<IBlockState> BLOCK_STATE_IDS = new ObjectIntIdentityMap();
/*      */   private CreativeTabs displayOnCreativeTab;
/*   48 */   public static final SoundType soundTypeStone = new SoundType("stone", 1.0F, 1.0F);
/*      */ 
/*      */   
/*   51 */   public static final SoundType soundTypeWood = new SoundType("wood", 1.0F, 1.0F);
/*      */ 
/*      */   
/*   54 */   public static final SoundType soundTypeGravel = new SoundType("gravel", 1.0F, 1.0F);
/*   55 */   public static final SoundType soundTypeGrass = new SoundType("grass", 1.0F, 1.0F);
/*   56 */   public static final SoundType soundTypePiston = new SoundType("stone", 1.0F, 1.0F);
/*   57 */   public static final SoundType soundTypeMetal = new SoundType("stone", 1.0F, 1.5F);
/*   58 */   public static final SoundType soundTypeGlass = new SoundType("stone", 1.0F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   62 */         return "dig.glass";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   66 */         return "step.stone";
/*      */       }
/*      */     };
/*   69 */   public static final SoundType soundTypeCloth = new SoundType("cloth", 1.0F, 1.0F);
/*   70 */   public static final SoundType soundTypeSand = new SoundType("sand", 1.0F, 1.0F);
/*   71 */   public static final SoundType soundTypeSnow = new SoundType("snow", 1.0F, 1.0F);
/*   72 */   public static final SoundType soundTypeLadder = new SoundType("ladder", 1.0F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   76 */         return "dig.wood";
/*      */       }
/*      */     };
/*   79 */   public static final SoundType soundTypeAnvil = new SoundType("anvil", 0.3F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   83 */         return "dig.stone";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   87 */         return "random.anvil_land";
/*      */       }
/*      */     };
/*   90 */   public static final SoundType SLIME_SOUND = new SoundType("slime", 1.0F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   94 */         return "mob.slime.big";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   98 */         return "mob.slime.big";
/*      */       }
/*      */       
/*      */       public String getStepSound() {
/*  102 */         return "mob.slime.small";
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   protected boolean fullBlock;
/*      */   
/*      */   protected int lightOpacity;
/*      */   
/*      */   protected boolean translucent;
/*      */   
/*      */   protected int lightValue;
/*      */   
/*      */   protected boolean useNeighborBrightness;
/*      */   
/*      */   protected float blockHardness;
/*      */   
/*      */   protected float blockResistance;
/*      */   
/*      */   protected boolean enableStats;
/*      */   
/*      */   protected boolean needsRandomTick;
/*      */   
/*      */   protected boolean isBlockContainer;
/*      */   
/*      */   protected double minX;
/*      */   
/*      */   protected double minY;
/*      */   
/*      */   protected double minZ;
/*      */   
/*      */   protected double maxX;
/*      */   
/*      */   protected double maxY;
/*      */   
/*      */   protected double maxZ;
/*      */   
/*      */   public SoundType stepSound;
/*      */   
/*      */   public float blockParticleGravity;
/*      */   
/*      */   protected final Material blockMaterial;
/*      */   
/*      */   protected final MapColor field_181083_K;
/*      */   
/*      */   public float slipperiness;
/*      */   
/*      */   protected final BlockState blockState;
/*      */   
/*      */   private IBlockState defaultBlockState;
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */   
/*      */   public static int getIdFromBlock(Block blockIn) {
/*  157 */     return blockRegistry.getIDForObject(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStateId(IBlockState state) {
/*  165 */     Block block = state.getBlock();
/*  166 */     return getIdFromBlock(block) + (block.getMetaFromState(state) << 12);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockById(int id) {
/*  171 */     return (Block)blockRegistry.getObjectById(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IBlockState getStateById(int id) {
/*  179 */     int i = id & 0xFFF;
/*  180 */     int j = id >> 12 & 0xF;
/*  181 */     return getBlockById(i).getStateFromMeta(j);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockFromItem(Item itemIn) {
/*  186 */     return (itemIn instanceof ItemBlock) ? ((ItemBlock)itemIn).getBlock() : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockFromName(String name) {
/*  191 */     ResourceLocation resourcelocation = new ResourceLocation(name);
/*      */     
/*  193 */     if (blockRegistry.containsKey(resourcelocation))
/*      */     {
/*  195 */       return (Block)blockRegistry.getObject(resourcelocation);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  201 */       return (Block)blockRegistry.getObjectById(Integer.parseInt(name));
/*      */     }
/*  203 */     catch (NumberFormatException var3) {
/*      */       
/*  205 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullBlock() {
/*  212 */     return this.fullBlock;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightOpacity() {
/*  217 */     return this.lightOpacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTranslucent() {
/*  225 */     return this.translucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightValue() {
/*  230 */     return this.lightValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseNeighborBrightness() {
/*  238 */     return this.useNeighborBrightness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Material getMaterial() {
/*  246 */     return this.blockMaterial;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapColor getMapColor(IBlockState state) {
/*  254 */     return this.field_181083_K;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getStateFromMeta(int meta) {
/*  262 */     return getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetaFromState(IBlockState state) {
/*  270 */     if (state != null && !state.getPropertyNames().isEmpty())
/*      */     {
/*  272 */       throw new IllegalArgumentException("Don't know how to convert " + state + " back into data...");
/*      */     }
/*      */ 
/*      */     
/*  276 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  286 */     return state;
/*      */   }
/*      */ 
/*      */   
/*      */   public Block(Material p_i46399_1_, MapColor p_i46399_2_) {
/*  291 */     this.enableStats = true;
/*  292 */     this.stepSound = soundTypeStone;
/*  293 */     this.blockParticleGravity = 1.0F;
/*  294 */     this.slipperiness = 0.6F;
/*  295 */     this.blockMaterial = p_i46399_1_;
/*  296 */     this.field_181083_K = p_i46399_2_;
/*  297 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  298 */     this.fullBlock = isOpaqueCube();
/*  299 */     this.lightOpacity = isOpaqueCube() ? 255 : 0;
/*  300 */     this.translucent = !p_i46399_1_.blocksLight();
/*  301 */     this.blockState = createBlockState();
/*  302 */     setDefaultState(this.blockState.getBaseState());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block(Material materialIn) {
/*  307 */     this(materialIn, materialIn.getMaterialMapColor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setStepSound(SoundType sound) {
/*  315 */     this.stepSound = sound;
/*  316 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightOpacity(int opacity) {
/*  324 */     this.lightOpacity = opacity;
/*  325 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightLevel(float value) {
/*  334 */     this.lightValue = (int)(15.0F * value);
/*  335 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setResistance(float resistance) {
/*  343 */     this.blockResistance = resistance * 3.0F;
/*  344 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube() {
/*  352 */     return (this.blockMaterial.blocksMovement() && isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNormalCube() {
/*  361 */     return (this.blockMaterial.isOpaque() && isFullCube() && !canProvidePower());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isVisuallyOpaque() {
/*  366 */     return (this.blockMaterial.blocksMovement() && isFullCube());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFullCube() {
/*  371 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  376 */     return !this.blockMaterial.blocksMovement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRenderType() {
/*  384 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  392 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setHardness(float hardness) {
/*  400 */     this.blockHardness = hardness;
/*      */     
/*  402 */     if (this.blockResistance < hardness * 5.0F)
/*      */     {
/*  404 */       this.blockResistance = hardness * 5.0F;
/*      */     }
/*      */     
/*  407 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block setBlockUnbreakable() {
/*  412 */     setHardness(-1.0F);
/*  413 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getBlockHardness(World worldIn, BlockPos pos) {
/*  418 */     return this.blockHardness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setTickRandomly(boolean shouldTick) {
/*  426 */     this.needsRandomTick = shouldTick;
/*  427 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTickRandomly() {
/*  436 */     return this.needsRandomTick;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasTileEntity() {
/*  441 */     return this.isBlockContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
/*  446 */     this.minX = minX;
/*  447 */     this.minY = minY;
/*  448 */     this.minZ = minZ;
/*  449 */     this.maxX = maxX;
/*  450 */     this.maxY = maxY;
/*  451 */     this.maxZ = maxZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/*  456 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  457 */     int i = worldIn.getCombinedLight(pos, block.getLightValue());
/*      */     
/*  459 */     if (i == 0 && block instanceof BlockSlab) {
/*      */       
/*  461 */       pos = pos.down();
/*  462 */       block = worldIn.getBlockState(pos).getBlock();
/*  463 */       return worldIn.getCombinedLight(pos, block.getLightValue());
/*      */     } 
/*      */ 
/*      */     
/*  467 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  473 */     if (((XRay)Slack.getInstance().getModuleManager().getInstance(XRay.class)).isToggle()) {
/*  474 */       return ((XRay)Slack.getInstance().getModuleManager().getInstance(XRay.class)).shouldRenderBlock(this);
/*      */     }
/*      */     
/*  477 */     if (((ChestESP)Slack.getInstance().getModuleManager().getInstance(ChestESP.class)).isToggle() && (
/*  478 */       (ChestESP)Slack.getInstance().getModuleManager().getInstance(ChestESP.class)).isChest(this)) {
/*  479 */       ((ChestESP)Slack.getInstance().getModuleManager().getInstance(ChestESP.class)).chestBoundingBoxes.add(pos);
/*      */     }
/*      */     
/*  482 */     return (side == EnumFacing.DOWN && this.minY > 0.0D) ? true : ((side == EnumFacing.UP && this.maxY < 1.0D) ? true : ((side == EnumFacing.NORTH && this.minZ > 0.0D) ? true : ((side == EnumFacing.SOUTH && this.maxZ < 1.0D) ? true : ((side == EnumFacing.WEST && this.minX > 0.0D) ? true : ((side == EnumFacing.EAST && this.maxX < 1.0D) ? true : (!worldIn.getBlockState(pos).getBlock().isOpaqueCube()))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  490 */     return worldIn.getBlockState(pos).getBlock().getMaterial().isSolid();
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  495 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  505 */     CollideEvent collideEvent = new CollideEvent(state.getBlock(), getCollisionBoundingBox(worldIn, pos, state), pos.getX(), pos.getY(), pos.getZ());
/*  506 */     collideEvent.call();
/*      */     
/*  508 */     if (collideEvent.isCanceled())
/*      */       return; 
/*  510 */     AxisAlignedBB axisalignedbb = collideEvent.getBoundingBox();
/*      */     
/*  512 */     if (axisalignedbb != null && mask.intersectsWith(axisalignedbb)) {
/*  513 */       list.add(axisalignedbb);
/*      */     }
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  518 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOpaqueCube() {
/*  526 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  531 */     return isCollidable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCollidable() {
/*  539 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
/*  547 */     updateTick(worldIn, pos, state, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int tickRate(World worldIn) {
/*  577 */     return 10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDropped(Random random) {
/*  593 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  603 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {
/*  611 */     float f = getBlockHardness(worldIn, pos);
/*  612 */     return (f < 0.0F) ? 0.0F : (!playerIn.canHarvestBlock(this) ? (playerIn.getToolDigEfficiency(this) / f / 100.0F) : (playerIn.getToolDigEfficiency(this) / f / 30.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int forture) {
/*  622 */     dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, forture);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  633 */     if (!worldIn.isRemote) {
/*      */       
/*  635 */       int i = quantityDroppedWithBonus(fortune, worldIn.rand);
/*      */       
/*  637 */       for (int j = 0; j < i; j++) {
/*      */         
/*  639 */         if (worldIn.rand.nextFloat() <= chance) {
/*      */           
/*  641 */           Item item = getItemDropped(state, worldIn.rand, fortune);
/*      */           
/*  643 */           if (item != null)
/*      */           {
/*  645 */             spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
/*  657 */     if (!worldIn.isRemote && worldIn.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
/*      */       
/*  659 */       float f = 0.5F;
/*  660 */       double d0 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  661 */       double d1 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  662 */       double d2 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  663 */       EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
/*  664 */       entityitem.setDefaultPickupDelay();
/*  665 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
/*  676 */     if (!worldIn.isRemote)
/*      */     {
/*  678 */       while (amount > 0) {
/*      */         
/*  680 */         int i = EntityXPOrb.getXPSplit(amount);
/*  681 */         amount -= i;
/*  682 */         worldIn.spawnEntityInWorld((Entity)new EntityXPOrb(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, i));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int damageDropped(IBlockState state) {
/*  693 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Entity exploder) {
/*  701 */     return this.blockResistance / 5.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/*  712 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  713 */     start = start.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
/*  714 */     end = end.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
/*  715 */     Vec3 vec3 = start.getIntermediateWithXValue(end, this.minX);
/*  716 */     Vec3 vec31 = start.getIntermediateWithXValue(end, this.maxX);
/*  717 */     Vec3 vec32 = start.getIntermediateWithYValue(end, this.minY);
/*  718 */     Vec3 vec33 = start.getIntermediateWithYValue(end, this.maxY);
/*  719 */     Vec3 vec34 = start.getIntermediateWithZValue(end, this.minZ);
/*  720 */     Vec3 vec35 = start.getIntermediateWithZValue(end, this.maxZ);
/*      */     
/*  722 */     if (!isVecInsideYZBounds(vec3))
/*      */     {
/*  724 */       vec3 = null;
/*      */     }
/*      */     
/*  727 */     if (!isVecInsideYZBounds(vec31))
/*      */     {
/*  729 */       vec31 = null;
/*      */     }
/*      */     
/*  732 */     if (!isVecInsideXZBounds(vec32))
/*      */     {
/*  734 */       vec32 = null;
/*      */     }
/*      */     
/*  737 */     if (!isVecInsideXZBounds(vec33))
/*      */     {
/*  739 */       vec33 = null;
/*      */     }
/*      */     
/*  742 */     if (!isVecInsideXYBounds(vec34))
/*      */     {
/*  744 */       vec34 = null;
/*      */     }
/*      */     
/*  747 */     if (!isVecInsideXYBounds(vec35))
/*      */     {
/*  749 */       vec35 = null;
/*      */     }
/*      */     
/*  752 */     Vec3 vec36 = null;
/*      */     
/*  754 */     if (vec3 != null && (vec36 == null || start.squareDistanceTo(vec3) < start.squareDistanceTo(vec36)))
/*      */     {
/*  756 */       vec36 = vec3;
/*      */     }
/*      */     
/*  759 */     if (vec31 != null && (vec36 == null || start.squareDistanceTo(vec31) < start.squareDistanceTo(vec36)))
/*      */     {
/*  761 */       vec36 = vec31;
/*      */     }
/*      */     
/*  764 */     if (vec32 != null && (vec36 == null || start.squareDistanceTo(vec32) < start.squareDistanceTo(vec36)))
/*      */     {
/*  766 */       vec36 = vec32;
/*      */     }
/*      */     
/*  769 */     if (vec33 != null && (vec36 == null || start.squareDistanceTo(vec33) < start.squareDistanceTo(vec36)))
/*      */     {
/*  771 */       vec36 = vec33;
/*      */     }
/*      */     
/*  774 */     if (vec34 != null && (vec36 == null || start.squareDistanceTo(vec34) < start.squareDistanceTo(vec36)))
/*      */     {
/*  776 */       vec36 = vec34;
/*      */     }
/*      */     
/*  779 */     if (vec35 != null && (vec36 == null || start.squareDistanceTo(vec35) < start.squareDistanceTo(vec36)))
/*      */     {
/*  781 */       vec36 = vec35;
/*      */     }
/*      */     
/*  784 */     if (vec36 == null)
/*      */     {
/*  786 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  790 */     EnumFacing enumfacing = null;
/*      */     
/*  792 */     if (vec36 == vec3)
/*      */     {
/*  794 */       enumfacing = EnumFacing.WEST;
/*      */     }
/*      */     
/*  797 */     if (vec36 == vec31)
/*      */     {
/*  799 */       enumfacing = EnumFacing.EAST;
/*      */     }
/*      */     
/*  802 */     if (vec36 == vec32)
/*      */     {
/*  804 */       enumfacing = EnumFacing.DOWN;
/*      */     }
/*      */     
/*  807 */     if (vec36 == vec33)
/*      */     {
/*  809 */       enumfacing = EnumFacing.UP;
/*      */     }
/*      */     
/*  812 */     if (vec36 == vec34)
/*      */     {
/*  814 */       enumfacing = EnumFacing.NORTH;
/*      */     }
/*      */     
/*  817 */     if (vec36 == vec35)
/*      */     {
/*  819 */       enumfacing = EnumFacing.SOUTH;
/*      */     }
/*      */     
/*  822 */     return new MovingObjectPosition(vec36.addVector(pos.getX(), pos.getY(), pos.getZ()), enumfacing, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideYZBounds(Vec3 point) {
/*  831 */     return (point == null) ? false : ((point.yCoord >= this.minY && point.yCoord <= this.maxY && point.zCoord >= this.minZ && point.zCoord <= this.maxZ));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideXZBounds(Vec3 point) {
/*  839 */     return (point == null) ? false : ((point.xCoord >= this.minX && point.xCoord <= this.maxX && point.zCoord >= this.minZ && point.zCoord <= this.maxZ));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideXYBounds(Vec3 point) {
/*  847 */     return (point == null) ? false : ((point.xCoord >= this.minX && point.xCoord <= this.maxX && point.yCoord >= this.minY && point.yCoord <= this.maxY));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer getBlockLayer() {
/*  859 */     return EnumWorldBlockLayer.SOLID;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  864 */     return canPlaceBlockOnSide(worldIn, pos, side);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  872 */     return canPlaceBlockAt(worldIn, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  877 */     return (worldIn.getBlockState(pos).getBlock()).blockMaterial.isReplaceable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  882 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  898 */     return getStateFromMeta(meta);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {}
/*      */ 
/*      */   
/*      */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/*  907 */     return motion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinX() {
/*  919 */     return this.minX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxX() {
/*  927 */     return this.maxX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinY() {
/*  935 */     return this.minY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxY() {
/*  943 */     return this.maxY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinZ() {
/*  951 */     return this.minZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxZ() {
/*  959 */     return this.maxZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockColor() {
/*  964 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRenderColor(IBlockState state) {
/*  969 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  974 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int colorMultiplier(IBlockAccess worldIn, BlockPos pos) {
/*  979 */     return colorMultiplier(worldIn, pos, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  984 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canProvidePower() {
/*  992 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 1004 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlockBoundsForItemRender() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 1016 */     player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
/* 1017 */     player.addExhaustion(0.025F);
/*      */     
/* 1019 */     if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier((EntityLivingBase)player)) {
/*      */       
/* 1021 */       ItemStack itemstack = createStackedBlock(state);
/*      */       
/* 1023 */       if (itemstack != null)
/*      */       {
/* 1025 */         spawnAsEntity(worldIn, pos, itemstack);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1030 */       int i = EnchantmentHelper.getFortuneModifier((EntityLivingBase)player);
/* 1031 */       dropBlockAsItem(worldIn, pos, state, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canSilkHarvest() {
/* 1037 */     return (isFullCube() && !this.isBlockContainer);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemStack createStackedBlock(IBlockState state) {
/* 1042 */     int i = 0;
/* 1043 */     Item item = Item.getItemFromBlock(this);
/*      */     
/* 1045 */     if (item != null && item.getHasSubtypes())
/*      */     {
/* 1047 */       i = getMetaFromState(state);
/*      */     }
/*      */     
/* 1050 */     return new ItemStack(item, 1, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDroppedWithBonus(int fortune, Random random) {
/* 1058 */     return quantityDropped(random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_181623_g() {
/* 1070 */     return (!this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid());
/*      */   }
/*      */ 
/*      */   
/*      */   public Block setUnlocalizedName(String name) {
/* 1075 */     this.unlocalizedName = name;
/* 1076 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalizedName() {
/* 1084 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/* 1092 */     return "tile." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 1100 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnableStats() {
/* 1108 */     return this.enableStats;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block disableStats() {
/* 1113 */     this.enableStats = false;
/* 1114 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMobilityFlag() {
/* 1119 */     return this.blockMaterial.getMaterialMobility();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAmbientOcclusionLightValue() {
/* 1127 */     return isBlockNormalCube() ? 0.2F : 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/* 1137 */     entityIn.fall(fallDistance, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLanded(World worldIn, Entity entityIn) {
/* 1146 */     entityIn.motionY = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItem(World worldIn, BlockPos pos) {
/* 1154 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 1159 */     return damageDropped(worldIn.getBlockState(pos));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 1167 */     list.add(new ItemStack(itemIn, 1, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTabToDisplayOn() {
/* 1175 */     return this.displayOnCreativeTab;
/*      */   }
/*      */ 
/*      */   
/*      */   public Block setCreativeTab(CreativeTabs tab) {
/* 1180 */     this.displayOnCreativeTab = tab;
/* 1181 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillWithRain(World worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlowerPot() {
/* 1200 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean requiresUpdates() {
/* 1205 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 1213 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAssociatedBlock(Block other) {
/* 1218 */     return (this == other);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isEqualTo(Block blockIn, Block other) {
/* 1223 */     return (blockIn != null && other != null) ? ((blockIn == other) ? true : blockIn.isAssociatedBlock(other)) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasComparatorInputOverride() {
/* 1228 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 1233 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 1241 */     return state;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BlockState createBlockState() {
/* 1246 */     return new BlockState(this, new IProperty[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockState getBlockState() {
/* 1251 */     return this.blockState;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void setDefaultState(IBlockState state) {
/* 1256 */     this.defaultBlockState = state;
/*      */   }
/*      */ 
/*      */   
/*      */   public final IBlockState getDefaultState() {
/* 1261 */     return this.defaultBlockState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumOffsetType getOffsetType() {
/* 1269 */     return EnumOffsetType.NONE;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1274 */     return "Block{" + blockRegistry.getNameForObject(this) + "}";
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerBlocks() {
/* 1279 */     registerBlock(0, AIR_ID, (new BlockAir()).setUnlocalizedName("air"));
/* 1280 */     registerBlock(1, "stone", (new BlockStone()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stone"));
/* 1281 */     registerBlock(2, "grass", (new BlockGrass()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("grass"));
/* 1282 */     registerBlock(3, "dirt", (new BlockDirt()).setHardness(0.5F).setStepSound(soundTypeGravel).setUnlocalizedName("dirt"));
/* 1283 */     Block block = (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
/* 1284 */     registerBlock(4, "cobblestone", block);
/* 1285 */     Block block1 = (new BlockPlanks()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("wood");
/* 1286 */     registerBlock(5, "planks", block1);
/* 1287 */     registerBlock(6, "sapling", (new BlockSapling()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("sapling"));
/* 1288 */     registerBlock(7, "bedrock", (new Block(Material.rock)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypePiston).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock));
/* 1289 */     registerBlock(8, "flowing_water", (new BlockDynamicLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1290 */     registerBlock(9, "water", (new BlockStaticLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1291 */     registerBlock(10, "flowing_lava", (new BlockDynamicLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1292 */     registerBlock(11, "lava", (new BlockStaticLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1293 */     registerBlock(12, "sand", (new BlockSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("sand"));
/* 1294 */     registerBlock(13, "gravel", (new BlockGravel()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("gravel"));
/* 1295 */     registerBlock(14, "gold_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreGold"));
/* 1296 */     registerBlock(15, "iron_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreIron"));
/* 1297 */     registerBlock(16, "coal_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreCoal"));
/* 1298 */     registerBlock(17, "log", (new BlockOldLog()).setUnlocalizedName("log"));
/* 1299 */     registerBlock(18, "leaves", (new BlockOldLeaf()).setUnlocalizedName("leaves"));
/* 1300 */     registerBlock(19, "sponge", (new BlockSponge()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("sponge"));
/* 1301 */     registerBlock(20, "glass", (new BlockGlass(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("glass"));
/* 1302 */     registerBlock(21, "lapis_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreLapis"));
/* 1303 */     registerBlock(22, "lapis_block", (new Block(Material.iron, MapColor.lapisColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock));
/* 1304 */     registerBlock(23, "dispenser", (new BlockDispenser()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dispenser"));
/* 1305 */     Block block2 = (new BlockSandStone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("sandStone");
/* 1306 */     registerBlock(24, "sandstone", block2);
/* 1307 */     registerBlock(25, "noteblock", (new BlockNote()).setHardness(0.8F).setUnlocalizedName("musicBlock"));
/* 1308 */     registerBlock(26, "bed", (new BlockBed()).setStepSound(soundTypeWood).setHardness(0.2F).setUnlocalizedName("bed").disableStats());
/* 1309 */     registerBlock(27, "golden_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("goldenRail"));
/* 1310 */     registerBlock(28, "detector_rail", (new BlockRailDetector()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("detectorRail"));
/* 1311 */     registerBlock(29, "sticky_piston", (new BlockPistonBase(true)).setUnlocalizedName("pistonStickyBase"));
/* 1312 */     registerBlock(30, "web", (new BlockWeb()).setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
/* 1313 */     registerBlock(31, "tallgrass", (new BlockTallGrass()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tallgrass"));
/* 1314 */     registerBlock(32, "deadbush", (new BlockDeadBush()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("deadbush"));
/* 1315 */     registerBlock(33, "piston", (new BlockPistonBase(false)).setUnlocalizedName("pistonBase"));
/* 1316 */     registerBlock(34, "piston_head", (new BlockPistonExtension()).setUnlocalizedName("pistonBase"));
/* 1317 */     registerBlock(35, "wool", (new BlockColored(Material.cloth)).setHardness(0.8F).setStepSound(soundTypeCloth).setUnlocalizedName("cloth"));
/* 1318 */     registerBlock(36, "piston_extension", new BlockPistonMoving());
/* 1319 */     registerBlock(37, "yellow_flower", (new BlockYellowFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower1"));
/* 1320 */     registerBlock(38, "red_flower", (new BlockRedFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower2"));
/* 1321 */     Block block3 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setLightLevel(0.125F).setUnlocalizedName("mushroom");
/* 1322 */     registerBlock(39, "brown_mushroom", block3);
/* 1323 */     Block block4 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("mushroom");
/* 1324 */     registerBlock(40, "red_mushroom", block4);
/* 1325 */     registerBlock(41, "gold_block", (new Block(Material.iron, MapColor.goldColor)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockGold").setCreativeTab(CreativeTabs.tabBlock));
/* 1326 */     registerBlock(42, "iron_block", (new Block(Material.iron, MapColor.ironColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockIron").setCreativeTab(CreativeTabs.tabBlock));
/* 1327 */     registerBlock(43, "double_stone_slab", (new BlockDoubleStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
/* 1328 */     registerBlock(44, "stone_slab", (new BlockHalfStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
/* 1329 */     Block block5 = (new Block(Material.rock, MapColor.redColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
/* 1330 */     registerBlock(45, "brick_block", block5);
/* 1331 */     registerBlock(46, "tnt", (new BlockTNT()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tnt"));
/* 1332 */     registerBlock(47, "bookshelf", (new BlockBookshelf()).setHardness(1.5F).setStepSound(soundTypeWood).setUnlocalizedName("bookshelf"));
/* 1333 */     registerBlock(48, "mossy_cobblestone", (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock));
/* 1334 */     registerBlock(49, "obsidian", (new BlockObsidian()).setHardness(50.0F).setResistance(2000.0F).setStepSound(soundTypePiston).setUnlocalizedName("obsidian"));
/* 1335 */     registerBlock(50, "torch", (new BlockTorch()).setHardness(0.0F).setLightLevel(0.9375F).setStepSound(soundTypeWood).setUnlocalizedName("torch"));
/* 1336 */     registerBlock(51, "fire", (new BlockFire()).setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeCloth).setUnlocalizedName("fire").disableStats());
/* 1337 */     registerBlock(52, "mob_spawner", (new BlockMobSpawner()).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
/* 1338 */     registerBlock(53, "oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK))).setUnlocalizedName("stairsWood"));
/* 1339 */     registerBlock(54, "chest", (new BlockChest(0)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chest"));
/* 1340 */     registerBlock(55, "redstone_wire", (new BlockRedstoneWire()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("redstoneDust").disableStats());
/* 1341 */     registerBlock(56, "diamond_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreDiamond"));
/* 1342 */     registerBlock(57, "diamond_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockDiamond").setCreativeTab(CreativeTabs.tabBlock));
/* 1343 */     registerBlock(58, "crafting_table", (new BlockWorkbench()).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("workbench"));
/* 1344 */     registerBlock(59, "wheat", (new BlockCrops()).setUnlocalizedName("crops"));
/* 1345 */     Block block6 = (new BlockFarmland()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("farmland");
/* 1346 */     registerBlock(60, "farmland", block6);
/* 1347 */     registerBlock(61, "furnace", (new BlockFurnace(false)).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
/* 1348 */     registerBlock(62, "lit_furnace", (new BlockFurnace(true)).setHardness(3.5F).setStepSound(soundTypePiston).setLightLevel(0.875F).setUnlocalizedName("furnace"));
/* 1349 */     registerBlock(63, "standing_sign", (new BlockStandingSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
/* 1350 */     registerBlock(64, "wooden_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorOak").disableStats());
/* 1351 */     registerBlock(65, "ladder", (new BlockLadder()).setHardness(0.4F).setStepSound(soundTypeLadder).setUnlocalizedName("ladder"));
/* 1352 */     registerBlock(66, "rail", (new BlockRail()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("rail"));
/* 1353 */     registerBlock(67, "stone_stairs", (new BlockStairs(block.getDefaultState())).setUnlocalizedName("stairsStone"));
/* 1354 */     registerBlock(68, "wall_sign", (new BlockWallSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
/* 1355 */     registerBlock(69, "lever", (new BlockLever()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("lever"));
/* 1356 */     registerBlock(70, "stone_pressure_plate", (new BlockPressurePlate(Material.rock, BlockPressurePlate.Sensitivity.MOBS)).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("pressurePlateStone"));
/* 1357 */     registerBlock(71, "iron_door", (new BlockDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
/* 1358 */     registerBlock(72, "wooden_pressure_plate", (new BlockPressurePlate(Material.wood, BlockPressurePlate.Sensitivity.EVERYTHING)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("pressurePlateWood"));
/* 1359 */     registerBlock(73, "redstone_ore", (new BlockRedstoneOre(false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock));
/* 1360 */     registerBlock(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone"));
/* 1361 */     registerBlock(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("notGate"));
/* 1362 */     registerBlock(76, "redstone_torch", (new BlockRedstoneTorch(true)).setHardness(0.0F).setLightLevel(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone));
/* 1363 */     registerBlock(77, "stone_button", (new BlockButtonStone()).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("button"));
/* 1364 */     registerBlock(78, "snow_layer", (new BlockSnow()).setHardness(0.1F).setStepSound(soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
/* 1365 */     registerBlock(79, "ice", (new BlockIce()).setHardness(0.5F).setLightOpacity(3).setStepSound(soundTypeGlass).setUnlocalizedName("ice"));
/* 1366 */     registerBlock(80, "snow", (new BlockSnowBlock()).setHardness(0.2F).setStepSound(soundTypeSnow).setUnlocalizedName("snow"));
/* 1367 */     registerBlock(81, "cactus", (new BlockCactus()).setHardness(0.4F).setStepSound(soundTypeCloth).setUnlocalizedName("cactus"));
/* 1368 */     registerBlock(82, "clay", (new BlockClay()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("clay"));
/* 1369 */     registerBlock(83, "reeds", (new BlockReed()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("reeds").disableStats());
/* 1370 */     registerBlock(84, "jukebox", (new BlockJukebox()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("jukebox"));
/* 1371 */     registerBlock(85, "fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.OAK.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fence"));
/* 1372 */     Block block7 = (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkin");
/* 1373 */     registerBlock(86, "pumpkin", block7);
/* 1374 */     registerBlock(87, "netherrack", (new BlockNetherrack()).setHardness(0.4F).setStepSound(soundTypePiston).setUnlocalizedName("hellrock"));
/* 1375 */     registerBlock(88, "soul_sand", (new BlockSoulSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("hellsand"));
/* 1376 */     registerBlock(89, "glowstone", (new BlockGlowstone(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("lightgem"));
/* 1377 */     registerBlock(90, "portal", (new BlockPortal()).setHardness(-1.0F).setStepSound(soundTypeGlass).setLightLevel(0.75F).setUnlocalizedName("portal"));
/* 1378 */     registerBlock(91, "lit_pumpkin", (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
/* 1379 */     registerBlock(92, "cake", (new BlockCake()).setHardness(0.5F).setStepSound(soundTypeCloth).setUnlocalizedName("cake").disableStats());
/* 1380 */     registerBlock(93, "unpowered_repeater", (new BlockRedstoneRepeater(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
/* 1381 */     registerBlock(94, "powered_repeater", (new BlockRedstoneRepeater(true)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
/* 1382 */     registerBlock(95, "stained_glass", (new BlockStainedGlass(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("stainedGlass"));
/* 1383 */     registerBlock(96, "trapdoor", (new BlockTrapDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("trapdoor").disableStats());
/* 1384 */     registerBlock(97, "monster_egg", (new BlockSilverfish()).setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
/* 1385 */     Block block8 = (new BlockStoneBrick()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebricksmooth");
/* 1386 */     registerBlock(98, "stonebrick", block8);
/* 1387 */     registerBlock(99, "brown_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.dirtColor, block3)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
/* 1388 */     registerBlock(100, "red_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.redColor, block4)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
/* 1389 */     registerBlock(101, "iron_bars", (new BlockPane(Material.iron, true)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("fenceIron"));
/* 1390 */     registerBlock(102, "glass_pane", (new BlockPane(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinGlass"));
/* 1391 */     Block block9 = (new BlockMelon()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("melon");
/* 1392 */     registerBlock(103, "melon_block", block9);
/* 1393 */     registerBlock(104, "pumpkin_stem", (new BlockStem(block7)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
/* 1394 */     registerBlock(105, "melon_stem", (new BlockStem(block9)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
/* 1395 */     registerBlock(106, "vine", (new BlockVine()).setHardness(0.2F).setStepSound(soundTypeGrass).setUnlocalizedName("vine"));
/* 1396 */     registerBlock(107, "fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fenceGate"));
/* 1397 */     registerBlock(108, "brick_stairs", (new BlockStairs(block5.getDefaultState())).setUnlocalizedName("stairsBrick"));
/* 1398 */     registerBlock(109, "stone_brick_stairs", (new BlockStairs(block8.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT))).setUnlocalizedName("stairsStoneBrickSmooth"));
/* 1399 */     registerBlock(110, "mycelium", (new BlockMycelium()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("mycel"));
/* 1400 */     registerBlock(111, "waterlily", (new BlockLilyPad()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("waterlily"));
/* 1401 */     Block block10 = (new BlockNetherBrick()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
/* 1402 */     registerBlock(112, "nether_brick", block10);
/* 1403 */     registerBlock(113, "nether_brick_fence", (new BlockFence(Material.rock, MapColor.netherrackColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherFence"));
/* 1404 */     registerBlock(114, "nether_brick_stairs", (new BlockStairs(block10.getDefaultState())).setUnlocalizedName("stairsNetherBrick"));
/* 1405 */     registerBlock(115, "nether_wart", (new BlockNetherWart()).setUnlocalizedName("netherStalk"));
/* 1406 */     registerBlock(116, "enchanting_table", (new BlockEnchantmentTable()).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable"));
/* 1407 */     registerBlock(117, "brewing_stand", (new BlockBrewingStand()).setHardness(0.5F).setLightLevel(0.125F).setUnlocalizedName("brewingStand"));
/* 1408 */     registerBlock(118, "cauldron", (new BlockCauldron()).setHardness(2.0F).setUnlocalizedName("cauldron"));
/* 1409 */     registerBlock(119, "end_portal", (new BlockEndPortal(Material.portal)).setHardness(-1.0F).setResistance(6000000.0F));
/* 1410 */     registerBlock(120, "end_portal_frame", (new BlockEndPortalFrame()).setStepSound(soundTypeGlass).setLightLevel(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations));
/* 1411 */     registerBlock(121, "end_stone", (new Block(Material.rock, MapColor.sandColor)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock));
/* 1412 */     registerBlock(122, "dragon_egg", (new BlockDragonEgg()).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setLightLevel(0.125F).setUnlocalizedName("dragonEgg"));
/* 1413 */     registerBlock(123, "redstone_lamp", (new BlockRedstoneLight(false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone));
/* 1414 */     registerBlock(124, "lit_redstone_lamp", (new BlockRedstoneLight(true)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight"));
/* 1415 */     registerBlock(125, "double_wooden_slab", (new BlockDoubleWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
/* 1416 */     registerBlock(126, "wooden_slab", (new BlockHalfWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
/* 1417 */     registerBlock(127, "cocoa", (new BlockCocoa()).setHardness(0.2F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("cocoa"));
/* 1418 */     registerBlock(128, "sandstone_stairs", (new BlockStairs(block2.getDefaultState().withProperty((IProperty)BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH))).setUnlocalizedName("stairsSandStone"));
/* 1419 */     registerBlock(129, "emerald_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreEmerald"));
/* 1420 */     registerBlock(130, "ender_chest", (new BlockEnderChest()).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(0.5F));
/* 1421 */     registerBlock(131, "tripwire_hook", (new BlockTripWireHook()).setUnlocalizedName("tripWireSource"));
/* 1422 */     registerBlock(132, "tripwire", (new BlockTripWire()).setUnlocalizedName("tripWire"));
/* 1423 */     registerBlock(133, "emerald_block", (new Block(Material.iron, MapColor.emeraldColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockEmerald").setCreativeTab(CreativeTabs.tabBlock));
/* 1424 */     registerBlock(134, "spruce_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE))).setUnlocalizedName("stairsWoodSpruce"));
/* 1425 */     registerBlock(135, "birch_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH))).setUnlocalizedName("stairsWoodBirch"));
/* 1426 */     registerBlock(136, "jungle_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE))).setUnlocalizedName("stairsWoodJungle"));
/* 1427 */     registerBlock(137, "command_block", (new BlockCommandBlock()).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("commandBlock"));
/* 1428 */     registerBlock(138, "beacon", (new BlockBeacon()).setUnlocalizedName("beacon").setLightLevel(1.0F));
/* 1429 */     registerBlock(139, "cobblestone_wall", (new BlockWall(block)).setUnlocalizedName("cobbleWall"));
/* 1430 */     registerBlock(140, "flower_pot", (new BlockFlowerPot()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("flowerPot"));
/* 1431 */     registerBlock(141, "carrots", (new BlockCarrot()).setUnlocalizedName("carrots"));
/* 1432 */     registerBlock(142, "potatoes", (new BlockPotato()).setUnlocalizedName("potatoes"));
/* 1433 */     registerBlock(143, "wooden_button", (new BlockButtonWood()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("button"));
/* 1434 */     registerBlock(144, "skull", (new BlockSkull()).setHardness(1.0F).setStepSound(soundTypePiston).setUnlocalizedName("skull"));
/* 1435 */     registerBlock(145, "anvil", (new BlockAnvil()).setHardness(5.0F).setStepSound(soundTypeAnvil).setResistance(2000.0F).setUnlocalizedName("anvil"));
/* 1436 */     registerBlock(146, "trapped_chest", (new BlockChest(1)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chestTrap"));
/* 1437 */     registerBlock(147, "light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 15, MapColor.goldColor)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_light"));
/* 1438 */     registerBlock(148, "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 150)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_heavy"));
/* 1439 */     registerBlock(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
/* 1440 */     registerBlock(150, "powered_comparator", (new BlockRedstoneComparator(true)).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
/* 1441 */     registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
/* 1442 */     registerBlock(152, "redstone_block", (new BlockCompressedPowered(Material.iron, MapColor.tntColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockRedstone").setCreativeTab(CreativeTabs.tabRedstone));
/* 1443 */     registerBlock(153, "quartz_ore", (new BlockOre(MapColor.netherrackColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherquartz"));
/* 1444 */     registerBlock(154, "hopper", (new BlockHopper()).setHardness(3.0F).setResistance(8.0F).setStepSound(soundTypeMetal).setUnlocalizedName("hopper"));
/* 1445 */     Block block11 = (new BlockQuartz()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("quartzBlock");
/* 1446 */     registerBlock(155, "quartz_block", block11);
/* 1447 */     registerBlock(156, "quartz_stairs", (new BlockStairs(block11.getDefaultState().withProperty((IProperty)BlockQuartz.VARIANT, BlockQuartz.EnumType.DEFAULT))).setUnlocalizedName("stairsQuartz"));
/* 1448 */     registerBlock(157, "activator_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("activatorRail"));
/* 1449 */     registerBlock(158, "dropper", (new BlockDropper()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dropper"));
/* 1450 */     registerBlock(159, "stained_hardened_clay", (new BlockColored(Material.rock)).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardenedStained"));
/* 1451 */     registerBlock(160, "stained_glass_pane", (new BlockStainedGlassPane()).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinStainedGlass"));
/* 1452 */     registerBlock(161, "leaves2", (new BlockNewLeaf()).setUnlocalizedName("leaves"));
/* 1453 */     registerBlock(162, "log2", (new BlockNewLog()).setUnlocalizedName("log"));
/* 1454 */     registerBlock(163, "acacia_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA))).setUnlocalizedName("stairsWoodAcacia"));
/* 1455 */     registerBlock(164, "dark_oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK))).setUnlocalizedName("stairsWoodDarkOak"));
/* 1456 */     registerBlock(165, "slime", (new BlockSlime()).setUnlocalizedName("slime").setStepSound(SLIME_SOUND));
/* 1457 */     registerBlock(166, "barrier", (new BlockBarrier()).setUnlocalizedName("barrier"));
/* 1458 */     registerBlock(167, "iron_trapdoor", (new BlockTrapDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats());
/* 1459 */     registerBlock(168, "prismarine", (new BlockPrismarine()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("prismarine"));
/* 1460 */     registerBlock(169, "sea_lantern", (new BlockSeaLantern(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("seaLantern"));
/* 1461 */     registerBlock(170, "hay_block", (new BlockHay()).setHardness(0.5F).setStepSound(soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock));
/* 1462 */     registerBlock(171, "carpet", (new BlockCarpet()).setHardness(0.1F).setStepSound(soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
/* 1463 */     registerBlock(172, "hardened_clay", (new BlockHardenedClay()).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardened"));
/* 1464 */     registerBlock(173, "coal_block", (new Block(Material.rock, MapColor.blackColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.tabBlock));
/* 1465 */     registerBlock(174, "packed_ice", (new BlockPackedIce()).setHardness(0.5F).setStepSound(soundTypeGlass).setUnlocalizedName("icePacked"));
/* 1466 */     registerBlock(175, "double_plant", new BlockDoublePlant());
/* 1467 */     registerBlock(176, "standing_banner", (new BlockBanner.BlockBannerStanding()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
/* 1468 */     registerBlock(177, "wall_banner", (new BlockBanner.BlockBannerHanging()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
/* 1469 */     registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
/* 1470 */     Block block12 = (new BlockRedSandstone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("redSandStone");
/* 1471 */     registerBlock(179, "red_sandstone", block12);
/* 1472 */     registerBlock(180, "red_sandstone_stairs", (new BlockStairs(block12.getDefaultState().withProperty((IProperty)BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH))).setUnlocalizedName("stairsRedSandStone"));
/* 1473 */     registerBlock(181, "double_stone_slab2", (new BlockDoubleStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
/* 1474 */     registerBlock(182, "stone_slab2", (new BlockHalfStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
/* 1475 */     registerBlock(183, "spruce_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.SPRUCE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFenceGate"));
/* 1476 */     registerBlock(184, "birch_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.BIRCH)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFenceGate"));
/* 1477 */     registerBlock(185, "jungle_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.JUNGLE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFenceGate"));
/* 1478 */     registerBlock(186, "dark_oak_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFenceGate"));
/* 1479 */     registerBlock(187, "acacia_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.ACACIA)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFenceGate"));
/* 1480 */     registerBlock(188, "spruce_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.SPRUCE.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFence"));
/* 1481 */     registerBlock(189, "birch_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.BIRCH.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFence"));
/* 1482 */     registerBlock(190, "jungle_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.JUNGLE.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFence"));
/* 1483 */     registerBlock(191, "dark_oak_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.DARK_OAK.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFence"));
/* 1484 */     registerBlock(192, "acacia_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.ACACIA.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFence"));
/* 1485 */     registerBlock(193, "spruce_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorSpruce").disableStats());
/* 1486 */     registerBlock(194, "birch_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorBirch").disableStats());
/* 1487 */     registerBlock(195, "jungle_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorJungle").disableStats());
/* 1488 */     registerBlock(196, "acacia_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorAcacia").disableStats());
/* 1489 */     registerBlock(197, "dark_oak_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats());
/* 1490 */     blockRegistry.validateKey();
/*      */     
/* 1492 */     for (Block block13 : blockRegistry) {
/*      */       
/* 1494 */       if (block13.blockMaterial == Material.air) {
/*      */         
/* 1496 */         block13.useNeighborBrightness = false;
/*      */         
/*      */         continue;
/*      */       } 
/* 1500 */       boolean flag = false;
/* 1501 */       boolean flag1 = block13 instanceof BlockStairs;
/* 1502 */       boolean flag2 = block13 instanceof BlockSlab;
/* 1503 */       boolean flag3 = (block13 == block6);
/* 1504 */       boolean flag4 = block13.translucent;
/* 1505 */       boolean flag5 = (block13.lightOpacity == 0);
/*      */       
/* 1507 */       if (flag1 || flag2 || flag3 || flag4 || flag5)
/*      */       {
/* 1509 */         flag = true;
/*      */       }
/*      */       
/* 1512 */       block13.useNeighborBrightness = flag;
/*      */     } 
/*      */ 
/*      */     
/* 1516 */     for (Block block14 : blockRegistry) {
/*      */       
/* 1518 */       for (UnmodifiableIterator<IBlockState> unmodifiableIterator = block14.getBlockState().getValidStates().iterator(); unmodifiableIterator.hasNext(); ) { IBlockState iblockstate = unmodifiableIterator.next();
/*      */         
/* 1520 */         int i = blockRegistry.getIDForObject(block14) << 4 | block14.getMetaFromState(iblockstate);
/* 1521 */         BLOCK_STATE_IDS.put(iblockstate, i); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerBlock(int id, ResourceLocation textualID, Block block_) {
/* 1528 */     blockRegistry.register(id, textualID, block_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerBlock(int id, String textualID, Block block_) {
/* 1533 */     registerBlock(id, new ResourceLocation(textualID), block_);
/*      */   }
/*      */   
/*      */   public enum EnumOffsetType
/*      */   {
/* 1538 */     NONE,
/* 1539 */     XZ,
/* 1540 */     XYZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SoundType
/*      */   {
/*      */     public final String soundName;
/*      */     public final float volume;
/*      */     public final float frequency;
/*      */     
/*      */     public SoundType(String name, float volume, float frequency) {
/* 1551 */       this.soundName = name;
/* 1552 */       this.volume = volume;
/* 1553 */       this.frequency = frequency;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getVolume() {
/* 1558 */       return this.volume;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFrequency() {
/* 1563 */       return this.frequency;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getBreakSound() {
/* 1568 */       return "dig." + this.soundName;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getStepSound() {
/* 1573 */       return "step." + this.soundName;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getPlaceSound() {
/* 1578 */       return getBreakSound();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */