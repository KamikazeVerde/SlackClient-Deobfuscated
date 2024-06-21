/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSkull extends Item {
/*  24 */   private static final String[] skullTypes = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
/*     */ 
/*     */   
/*     */   public ItemSkull() {
/*  28 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  29 */     setMaxDamage(0);
/*  30 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  41 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  43 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  47 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  48 */     Block block = iblockstate.getBlock();
/*  49 */     boolean flag = block.isReplaceable(worldIn, pos);
/*     */     
/*  51 */     if (!flag) {
/*     */       
/*  53 */       if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
/*     */       {
/*  55 */         return false;
/*     */       }
/*     */       
/*  58 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/*  61 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if (!Blocks.skull.canPlaceBlockAt(worldIn, pos))
/*     */     {
/*  67 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (!worldIn.isRemote) {
/*     */       
/*  73 */       worldIn.setBlockState(pos, Blocks.skull.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)side), 3);
/*  74 */       int i = 0;
/*     */       
/*  76 */       if (side == EnumFacing.UP)
/*     */       {
/*  78 */         i = MathHelper.floor_double((playerIn.rotationYaw * 16.0F / 360.0F) + 0.5D) & 0xF;
/*     */       }
/*     */       
/*  81 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  83 */       if (tileentity instanceof TileEntitySkull) {
/*     */         
/*  85 */         TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/*     */         
/*  87 */         if (stack.getMetadata() == 3) {
/*     */           
/*  89 */           GameProfile gameprofile = null;
/*     */           
/*  91 */           if (stack.hasTagCompound()) {
/*     */             
/*  93 */             NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */             
/*  95 */             if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */               
/*  97 */               gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */             }
/*  99 */             else if (nbttagcompound.hasKey("SkullOwner", 8) && nbttagcompound.getString("SkullOwner").length() > 0) {
/*     */               
/* 101 */               gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/*     */             } 
/*     */           } 
/*     */           
/* 105 */           tileentityskull.setPlayerProfile(gameprofile);
/*     */         }
/*     */         else {
/*     */           
/* 109 */           tileentityskull.setType(stack.getMetadata());
/*     */         } 
/*     */         
/* 112 */         tileentityskull.setSkullRotation(i);
/* 113 */         Blocks.skull.checkWitherSpawn(worldIn, pos, tileentityskull);
/*     */       } 
/*     */       
/* 116 */       stack.stackSize--;
/*     */     } 
/*     */     
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 131 */     for (int i = 0; i < skullTypes.length; i++)
/*     */     {
/* 133 */       subItems.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/* 143 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 152 */     int i = stack.getMetadata();
/*     */     
/* 154 */     if (i < 0 || i >= skullTypes.length)
/*     */     {
/* 156 */       i = 0;
/*     */     }
/*     */     
/* 159 */     return getUnlocalizedName() + "." + skullTypes[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 164 */     if (stack.getMetadata() == 3 && stack.hasTagCompound()) {
/*     */       
/* 166 */       if (stack.getTagCompound().hasKey("SkullOwner", 8))
/*     */       {
/* 168 */         return StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { stack.getTagCompound().getString("SkullOwner") });
/*     */       }
/*     */       
/* 171 */       if (stack.getTagCompound().hasKey("SkullOwner", 10)) {
/*     */         
/* 173 */         NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("SkullOwner");
/*     */         
/* 175 */         if (nbttagcompound.hasKey("Name", 8))
/*     */         {
/* 177 */           return StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { nbttagcompound.getString("Name") });
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/* 190 */     super.updateItemStackNBT(nbt);
/*     */     
/* 192 */     if (nbt.hasKey("SkullOwner", 8) && nbt.getString("SkullOwner").length() > 0) {
/*     */       
/* 194 */       GameProfile gameprofile = new GameProfile(null, nbt.getString("SkullOwner"));
/* 195 */       gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
/* 196 */       nbt.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/* 197 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 201 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */