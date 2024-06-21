/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class BlockBed
/*     */   extends BlockDirectional {
/*  25 */   public static final PropertyEnum<EnumPartType> PART = PropertyEnum.create("part", EnumPartType.class);
/*  26 */   public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
/*     */ 
/*     */   
/*     */   public BlockBed() {
/*  30 */     super(Material.cloth);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)OCCUPIED, Boolean.FALSE));
/*  32 */     setBedBounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  37 */     if (worldIn.isRemote)
/*     */     {
/*  39 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  43 */     if (state.getValue((IProperty)PART) != EnumPartType.HEAD) {
/*     */       
/*  45 */       pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  46 */       state = worldIn.getBlockState(pos);
/*     */       
/*  48 */       if (state.getBlock() != this)
/*     */       {
/*  50 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  54 */     if (worldIn.provider.canRespawnHere() && worldIn.getBiomeGenForCoords(pos) != BiomeGenBase.hell) {
/*     */       
/*  56 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue()) {
/*     */         
/*  58 */         EntityPlayer entityplayer = getPlayerInBed(worldIn, pos);
/*     */         
/*  60 */         if (entityplayer != null) {
/*     */           
/*  62 */           playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
/*  63 */           return true;
/*     */         } 
/*     */         
/*  66 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.FALSE);
/*  67 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */       
/*  70 */       EntityPlayer.EnumStatus entityplayer$enumstatus = playerIn.trySleep(pos);
/*     */       
/*  72 */       if (entityplayer$enumstatus == EntityPlayer.EnumStatus.OK) {
/*     */         
/*  74 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.TRUE);
/*  75 */         worldIn.setBlockState(pos, state, 4);
/*  76 */         return true;
/*     */       } 
/*     */ 
/*     */       
/*  80 */       if (entityplayer$enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
/*     */         
/*  82 */         playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
/*     */       }
/*  84 */       else if (entityplayer$enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
/*     */         
/*  86 */         playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
/*     */       } 
/*     */       
/*  89 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  94 */     worldIn.setBlockToAir(pos);
/*  95 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */     
/*  97 */     if (worldIn.getBlockState(blockpos).getBlock() == this)
/*     */     {
/*  99 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */     
/* 102 */     worldIn.newExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, true);
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
/* 110 */     for (EntityPlayer entityplayer : worldIn.playerEntities) {
/*     */       
/* 112 */       if (entityplayer.isPlayerSleeping() && entityplayer.playerLocation.equals(pos))
/*     */       {
/* 114 */         return entityplayer;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 136 */     setBedBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 144 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 146 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 148 */       if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this)
/*     */       {
/* 150 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */     }
/* 153 */     else if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
/*     */       
/* 155 */       worldIn.setBlockToAir(pos);
/*     */       
/* 157 */       if (!worldIn.isRemote)
/*     */       {
/* 159 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       }
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
/* 171 */     return (state.getValue((IProperty)PART) == EnumPartType.HEAD) ? null : Items.bed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBedBounds() {
/* 176 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos getSafeExitLocation(World worldIn, BlockPos pos, int tries) {
/* 184 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 185 */     int i = pos.getX();
/* 186 */     int j = pos.getY();
/* 187 */     int k = pos.getZ();
/*     */     
/* 189 */     for (int l = 0; l <= 1; l++) {
/*     */       
/* 191 */       int i1 = i - enumfacing.getFrontOffsetX() * l - 1;
/* 192 */       int j1 = k - enumfacing.getFrontOffsetZ() * l - 1;
/* 193 */       int k1 = i1 + 2;
/* 194 */       int l1 = j1 + 2;
/*     */       
/* 196 */       for (int i2 = i1; i2 <= k1; i2++) {
/*     */         
/* 198 */         for (int j2 = j1; j2 <= l1; j2++) {
/*     */           
/* 200 */           BlockPos blockpos = new BlockPos(i2, j, j2);
/*     */           
/* 202 */           if (hasRoomForPlayer(worldIn, blockpos)) {
/*     */             
/* 204 */             if (tries <= 0)
/*     */             {
/* 206 */               return blockpos;
/*     */             }
/*     */             
/* 209 */             tries--;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean hasRoomForPlayer(World worldIn, BlockPos pos) {
/* 220 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !worldIn.getBlockState(pos).getBlock().getMaterial().isSolid() && !worldIn.getBlockState(pos.up()).getBlock().getMaterial().isSolid());
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
/* 231 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT)
/*     */     {
/* 233 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 239 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 244 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 252 */     return Items.bed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 257 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 259 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */       
/* 261 */       if (worldIn.getBlockState(blockpos).getBlock() == this)
/*     */       {
/* 263 */         worldIn.setBlockToAir(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 273 */     EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/* 274 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)PART, EnumPartType.HEAD).withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)OCCUPIED, Boolean.valueOf(((meta & 0x4) > 0))) : getDefaultState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 283 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/*     */       
/* 285 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue((IProperty)FACING)));
/*     */       
/* 287 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 289 */         state = state.withProperty((IProperty)OCCUPIED, iblockstate.getValue((IProperty)OCCUPIED));
/*     */       }
/*     */     } 
/*     */     
/* 293 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 301 */     int i = 0;
/* 302 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 304 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 306 */       i |= 0x8;
/*     */       
/* 308 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue())
/*     */       {
/* 310 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 314 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 319 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)PART, (IProperty)OCCUPIED });
/*     */   }
/*     */   
/*     */   public enum EnumPartType
/*     */     implements IStringSerializable {
/* 324 */     HEAD("head"),
/* 325 */     FOOT("foot");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumPartType(String name) {
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */