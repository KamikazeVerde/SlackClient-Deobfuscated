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
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWire
/*     */   extends Block {
/*  24 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  25 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*  26 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  27 */   public static final PropertyBool DISARMED = PropertyBool.create("disarmed");
/*  28 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  29 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  30 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  31 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */ 
/*     */   
/*     */   public BlockTripWire() {
/*  35 */     super(Material.circuits);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.FALSE).withProperty((IProperty)SUSPENDED, Boolean.FALSE).withProperty((IProperty)ATTACHED, Boolean.FALSE).withProperty((IProperty)DISARMED, Boolean.FALSE).withProperty((IProperty)NORTH, Boolean.FALSE).withProperty((IProperty)EAST, Boolean.FALSE).withProperty((IProperty)SOUTH, Boolean.FALSE).withProperty((IProperty)WEST, Boolean.FALSE));
/*  37 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  38 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  47 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.NORTH))).withProperty((IProperty)EAST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.EAST))).withProperty((IProperty)SOUTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.SOUTH))).withProperty((IProperty)WEST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.WEST)));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/*  70 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  80 */     return Items.string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  88 */     return Items.string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  96 */     boolean flag = ((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue();
/*  97 */     boolean flag1 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */     
/*  99 */     if (flag != flag1) {
/*     */       
/* 101 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 102 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 108 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 109 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)ATTACHED)).booleanValue();
/* 110 */     boolean flag1 = ((Boolean)iblockstate.getValue((IProperty)SUSPENDED)).booleanValue();
/*     */     
/* 112 */     if (!flag1) {
/*     */       
/* 114 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
/*     */     }
/* 116 */     else if (!flag) {
/*     */       
/* 118 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 122 */       setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 128 */     state = state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())));
/* 129 */     worldIn.setBlockState(pos, state, 3);
/* 130 */     notifyHook(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 135 */     notifyHook(worldIn, pos, state.withProperty((IProperty)POWERED, Boolean.TRUE));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 140 */     if (!worldIn.isRemote)
/*     */     {
/* 142 */       if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears)
/*     */       {
/* 144 */         worldIn.setBlockState(pos, state.withProperty((IProperty)DISARMED, Boolean.TRUE), 4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyHook(World worldIn, BlockPos pos, IBlockState state) {
/* 151 */     for (EnumFacing enumfacing : new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST }) {
/*     */       
/* 153 */       for (int i = 1; i < 42; i++) {
/*     */         
/* 155 */         BlockPos blockpos = pos.offset(enumfacing, i);
/* 156 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 158 */         if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/*     */           
/* 160 */           if (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing.getOpposite())
/*     */           {
/* 162 */             Blocks.tripwire_hook.func_176260_a(worldIn, blockpos, iblockstate, false, true, i, state);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 168 */         if (iblockstate.getBlock() != Blocks.tripwire) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 181 */     if (!worldIn.isRemote)
/*     */     {
/* 183 */       if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 185 */         updateState(worldIn, pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 199 */     if (!worldIn.isRemote)
/*     */     {
/* 201 */       if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 203 */         updateState(worldIn, pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos) {
/* 210 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 211 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue();
/* 212 */     boolean flag1 = false;
/* 213 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ));
/*     */     
/* 215 */     if (!list.isEmpty())
/*     */     {
/* 217 */       for (Entity entity : list) {
/*     */         
/* 219 */         if (!entity.doesEntityNotTriggerPressurePlate()) {
/*     */           
/* 221 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 227 */     if (flag1 != flag) {
/*     */       
/* 229 */       iblockstate = iblockstate.withProperty((IProperty)POWERED, Boolean.valueOf(flag1));
/* 230 */       worldIn.setBlockState(pos, iblockstate, 3);
/* 231 */       notifyHook(worldIn, pos, iblockstate);
/*     */     } 
/*     */     
/* 234 */     if (flag1)
/*     */     {
/* 236 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction) {
/* 242 */     BlockPos blockpos = pos.offset(direction);
/* 243 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 244 */     Block block = iblockstate.getBlock();
/*     */     
/* 246 */     if (block == Blocks.tripwire_hook) {
/*     */       
/* 248 */       EnumFacing enumfacing = direction.getOpposite();
/* 249 */       return (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing);
/*     */     } 
/* 251 */     if (block == Blocks.tripwire) {
/*     */       
/* 253 */       boolean flag = ((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue();
/* 254 */       boolean flag1 = ((Boolean)iblockstate.getValue((IProperty)SUSPENDED)).booleanValue();
/* 255 */       return (flag == flag1);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 268 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)SUSPENDED, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)DISARMED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 276 */     int i = 0;
/*     */     
/* 278 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 280 */       i |= 0x1;
/*     */     }
/*     */     
/* 283 */     if (((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue())
/*     */     {
/* 285 */       i |= 0x2;
/*     */     }
/*     */     
/* 288 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 290 */       i |= 0x4;
/*     */     }
/*     */     
/* 293 */     if (((Boolean)state.getValue((IProperty)DISARMED)).booleanValue())
/*     */     {
/* 295 */       i |= 0x8;
/*     */     }
/*     */     
/* 298 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 303 */     return new BlockState(this, new IProperty[] { (IProperty)POWERED, (IProperty)SUSPENDED, (IProperty)ATTACHED, (IProperty)DISARMED, (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockTripWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */