/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCauldron
/*     */   extends Block
/*     */ {
/*  30 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
/*     */ 
/*     */   
/*     */   public BlockCauldron() {
/*  34 */     super(Material.iron, MapColor.stoneColor);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  45 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  46 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  47 */     float f = 0.125F;
/*  48 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  49 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  50 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  51 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  52 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  53 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  54 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  55 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  56 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  64 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  85 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  86 */     float f = pos.getY() + (6.0F + (3 * i)) / 16.0F;
/*     */     
/*  88 */     if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && (entityIn.getEntityBoundingBox()).minY <= f) {
/*     */       
/*  90 */       entityIn.extinguish();
/*  91 */       setWaterLevel(worldIn, pos, state, i - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  97 */     if (worldIn.isRemote)
/*     */     {
/*  99 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 103 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/* 105 */     if (itemstack == null)
/*     */     {
/* 107 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 111 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/* 112 */     Item item = itemstack.getItem();
/*     */     
/* 114 */     if (item == Items.water_bucket) {
/*     */       
/* 116 */       if (i < 3) {
/*     */         
/* 118 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 120 */           playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
/*     */         }
/*     */         
/* 123 */         playerIn.triggerAchievement(StatList.field_181725_I);
/* 124 */         setWaterLevel(worldIn, pos, state, 3);
/*     */       } 
/*     */       
/* 127 */       return true;
/*     */     } 
/* 129 */     if (item == Items.glass_bottle) {
/*     */       
/* 131 */       if (i > 0) {
/*     */         
/* 133 */         if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 135 */           ItemStack itemstack2 = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */           
/* 137 */           if (!playerIn.inventory.addItemStackToInventory(itemstack2)) {
/*     */             
/* 139 */             worldIn.spawnEntityInWorld((Entity)new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, itemstack2));
/*     */           }
/* 141 */           else if (playerIn instanceof EntityPlayerMP) {
/*     */             
/* 143 */             ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */           } 
/*     */           
/* 146 */           playerIn.triggerAchievement(StatList.field_181726_J);
/* 147 */           itemstack.stackSize--;
/*     */           
/* 149 */           if (itemstack.stackSize <= 0)
/*     */           {
/* 151 */             playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
/*     */           }
/*     */         } 
/*     */         
/* 155 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       } 
/*     */       
/* 158 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (i > 0 && item instanceof ItemArmor) {
/*     */       
/* 164 */       ItemArmor itemarmor = (ItemArmor)item;
/*     */       
/* 166 */       if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(itemstack)) {
/*     */         
/* 168 */         itemarmor.removeColor(itemstack);
/* 169 */         setWaterLevel(worldIn, pos, state, i - 1);
/* 170 */         playerIn.triggerAchievement(StatList.field_181727_K);
/* 171 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     if (i > 0 && item instanceof net.minecraft.item.ItemBanner && TileEntityBanner.getPatterns(itemstack) > 0) {
/*     */       
/* 177 */       ItemStack itemstack1 = itemstack.copy();
/* 178 */       itemstack1.stackSize = 1;
/* 179 */       TileEntityBanner.removeBannerData(itemstack1);
/*     */       
/* 181 */       if (itemstack.stackSize <= 1 && !playerIn.capabilities.isCreativeMode) {
/*     */         
/* 183 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, itemstack1);
/*     */       }
/*     */       else {
/*     */         
/* 187 */         if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
/*     */           
/* 189 */           worldIn.spawnEntityInWorld((Entity)new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, itemstack1));
/*     */         }
/* 191 */         else if (playerIn instanceof EntityPlayerMP) {
/*     */           
/* 193 */           ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */         } 
/*     */         
/* 196 */         playerIn.triggerAchievement(StatList.field_181728_L);
/*     */         
/* 198 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 200 */           itemstack.stackSize--;
/*     */         }
/*     */       } 
/*     */       
/* 204 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 206 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       }
/*     */       
/* 209 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 222 */     worldIn.setBlockState(pos, state.withProperty((IProperty)LEVEL, Integer.valueOf(MathHelper.clamp_int(level, 0, 3))), 2);
/* 223 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillWithRain(World worldIn, BlockPos pos) {
/* 231 */     if (worldIn.rand.nextInt(20) == 1) {
/*     */       
/* 233 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 235 */       if (((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() < 3)
/*     */       {
/* 237 */         worldIn.setBlockState(pos, iblockstate.cycleProperty((IProperty)LEVEL), 2);
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
/* 249 */     return Items.cauldron;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 257 */     return Items.cauldron;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 267 */     return ((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 275 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 283 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 288 */     return new BlockState(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockCauldron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */