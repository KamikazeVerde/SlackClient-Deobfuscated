/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityComparator;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneComparator
/*     */   extends BlockRedstoneDiode implements ITileEntityProvider {
/*  31 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  32 */   public static final PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);
/*     */ 
/*     */   
/*     */   public BlockRedstoneComparator(boolean powered) {
/*  36 */     super(powered);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.FALSE).withProperty((IProperty)MODE, Mode.COMPARE));
/*  38 */     this.isBlockContainer = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  46 */     return StatCollector.translateToLocal("item.comparator.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  56 */     return Items.comparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  64 */     return Items.comparator;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDelay(IBlockState state) {
/*  69 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState) {
/*  74 */     Boolean obool = (Boolean)unpoweredState.getValue((IProperty)POWERED);
/*  75 */     Mode blockredstonecomparator$mode = (Mode)unpoweredState.getValue((IProperty)MODE);
/*  76 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue((IProperty)FACING);
/*  77 */     return Blocks.powered_comparator.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState) {
/*  82 */     Boolean obool = (Boolean)poweredState.getValue((IProperty)POWERED);
/*  83 */     Mode blockredstonecomparator$mode = (Mode)poweredState.getValue((IProperty)MODE);
/*  84 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue((IProperty)FACING);
/*  85 */     return Blocks.unpowered_comparator.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  90 */     return (this.isRepeaterPowered || ((Boolean)state.getValue((IProperty)POWERED)).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/*  95 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  96 */     return (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int calculateOutput(World worldIn, BlockPos pos, IBlockState state) {
/* 101 */     return (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? Math.max(calculateInputStrength(worldIn, pos, state) - getPowerOnSides((IBlockAccess)worldIn, pos, state), 0) : calculateInputStrength(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/* 106 */     int i = calculateInputStrength(worldIn, pos, state);
/*     */     
/* 108 */     if (i >= 15)
/*     */     {
/* 110 */       return true;
/*     */     }
/* 112 */     if (i == 0)
/*     */     {
/* 114 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 118 */     int j = getPowerOnSides((IBlockAccess)worldIn, pos, state);
/* 119 */     return (j == 0) ? true : ((i >= j));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 125 */     int i = super.calculateInputStrength(worldIn, pos, state);
/* 126 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 127 */     BlockPos blockpos = pos.offset(enumfacing);
/* 128 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 130 */     if (block.hasComparatorInputOverride()) {
/*     */       
/* 132 */       i = block.getComparatorInputOverride(worldIn, blockpos);
/*     */     }
/* 134 */     else if (i < 15 && block.isNormalCube()) {
/*     */       
/* 136 */       blockpos = blockpos.offset(enumfacing);
/* 137 */       block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 139 */       if (block.hasComparatorInputOverride()) {
/*     */         
/* 141 */         i = block.getComparatorInputOverride(worldIn, blockpos);
/*     */       }
/* 143 */       else if (block.getMaterial() == Material.air) {
/*     */         
/* 145 */         EntityItemFrame entityitemframe = findItemFrame(worldIn, enumfacing, blockpos);
/*     */         
/* 147 */         if (entityitemframe != null)
/*     */         {
/* 149 */           i = entityitemframe.func_174866_q();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityItemFrame findItemFrame(World worldIn, final EnumFacing facing, BlockPos pos) {
/* 159 */     List<EntityItemFrame> list = worldIn.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)), new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(Entity p_apply_1_)
/*     */           {
/* 163 */             return (p_apply_1_ != null && p_apply_1_.getHorizontalFacing() == facing);
/*     */           }
/*     */         });
/* 166 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 171 */     if (!playerIn.capabilities.allowEdit)
/*     */     {
/* 173 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 177 */     state = state.cycleProperty((IProperty)MODE);
/* 178 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? 0.55F : 0.5F);
/* 179 */     worldIn.setBlockState(pos, state, 2);
/* 180 */     onStateChange(worldIn, pos, state);
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 187 */     if (!worldIn.isBlockTickPending(pos, this)) {
/*     */       
/* 189 */       int i = calculateOutput(worldIn, pos, state);
/* 190 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/* 191 */       int j = (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */       
/* 193 */       if (i != j || isPowered(state) != shouldBePowered(worldIn, pos, state))
/*     */       {
/* 195 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/*     */           
/* 197 */           worldIn.updateBlockTick(pos, this, 2, -1);
/*     */         }
/*     */         else {
/*     */           
/* 201 */           worldIn.updateBlockTick(pos, this, 2, 0);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onStateChange(World worldIn, BlockPos pos, IBlockState state) {
/* 209 */     int i = calculateOutput(worldIn, pos, state);
/* 210 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 211 */     int j = 0;
/*     */     
/* 213 */     if (tileentity instanceof TileEntityComparator) {
/*     */       
/* 215 */       TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
/* 216 */       j = tileentitycomparator.getOutputSignal();
/* 217 */       tileentitycomparator.setOutputSignal(i);
/*     */     } 
/*     */     
/* 220 */     if (j != i || state.getValue((IProperty)MODE) == Mode.COMPARE) {
/*     */       
/* 222 */       boolean flag1 = shouldBePowered(worldIn, pos, state);
/* 223 */       boolean flag = isPowered(state);
/*     */       
/* 225 */       if (flag && !flag1) {
/*     */         
/* 227 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.FALSE), 2);
/*     */       }
/* 229 */       else if (!flag && flag1) {
/*     */         
/* 231 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.TRUE), 2);
/*     */       } 
/*     */       
/* 234 */       notifyNeighbors(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 240 */     if (this.isRepeaterPowered)
/*     */     {
/* 242 */       worldIn.setBlockState(pos, getUnpoweredState(state).withProperty((IProperty)POWERED, Boolean.TRUE), 4);
/*     */     }
/*     */     
/* 245 */     onStateChange(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 250 */     super.onBlockAdded(worldIn, pos, state);
/* 251 */     worldIn.setTileEntity(pos, createNewTileEntity(worldIn, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 256 */     super.breakBlock(worldIn, pos, state);
/* 257 */     worldIn.removeTileEntity(pos);
/* 258 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 266 */     super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
/* 267 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 268 */     return (tileentity == null) ? false : tileentity.receiveClientEvent(eventID, eventParam);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 276 */     return (TileEntity)new TileEntityComparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 284 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)MODE, ((meta & 0x4) > 0) ? Mode.SUBTRACT : Mode.COMPARE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 292 */     int i = 0;
/* 293 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 295 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 297 */       i |= 0x8;
/*     */     }
/*     */     
/* 300 */     if (state.getValue((IProperty)MODE) == Mode.SUBTRACT)
/*     */     {
/* 302 */       i |= 0x4;
/*     */     }
/*     */     
/* 305 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 310 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)MODE, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 319 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)POWERED, Boolean.FALSE).withProperty((IProperty)MODE, Mode.COMPARE);
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */     implements IStringSerializable {
/* 324 */     COMPARE("compare"),
/* 325 */     SUBTRACT("subtract");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     Mode(String name) {
/* 331 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 336 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 341 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockRedstoneComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */