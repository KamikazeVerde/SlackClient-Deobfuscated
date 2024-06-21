/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockStandingSign;
/*     */ import net.minecraft.block.BlockWallSign;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBanner extends ItemBlock {
/*     */   public ItemBanner() {
/*  23 */     super(Blocks.standing_banner);
/*  24 */     this.maxStackSize = 16;
/*  25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  26 */     setHasSubtypes(true);
/*  27 */     setMaxDamage(0);
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
/*  38 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  40 */       return false;
/*     */     }
/*  42 */     if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
/*     */     {
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  48 */     pos = pos.offset(side);
/*     */     
/*  50 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*     */     {
/*  52 */       return false;
/*     */     }
/*  54 */     if (!Blocks.standing_banner.canPlaceBlockAt(worldIn, pos))
/*     */     {
/*  56 */       return false;
/*     */     }
/*  58 */     if (worldIn.isRemote)
/*     */     {
/*  60 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  64 */     if (side == EnumFacing.UP) {
/*     */       
/*  66 */       int i = MathHelper.floor_double(((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/*  67 */       worldIn.setBlockState(pos, Blocks.standing_banner.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
/*     */     }
/*     */     else {
/*     */       
/*  71 */       worldIn.setBlockState(pos, Blocks.wall_banner.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)side), 3);
/*     */     } 
/*     */     
/*  74 */     stack.stackSize--;
/*  75 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  77 */     if (tileentity instanceof TileEntityBanner)
/*     */     {
/*  79 */       ((TileEntityBanner)tileentity).setItemValues(stack);
/*     */     }
/*     */     
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  89 */     String s = "item.banner.";
/*  90 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/*  91 */     s = s + enumdyecolor.getUnlocalizedName() + ".name";
/*  92 */     return StatCollector.translateToLocal(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 103 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*     */     
/* 105 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) {
/*     */       
/* 107 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 109 */       for (int i = 0; i < nbttaglist.tagCount() && i < 6; i++) {
/*     */         
/* 111 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 112 */         EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound1.getInteger("Color"));
/* 113 */         TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = TileEntityBanner.EnumBannerPattern.getPatternByID(nbttagcompound1.getString("Pattern"));
/*     */         
/* 115 */         if (tileentitybanner$enumbannerpattern != null)
/*     */         {
/* 117 */           tooltip.add(StatCollector.translateToLocal("item.banner." + tileentitybanner$enumbannerpattern.getPatternName() + "." + enumdyecolor.getUnlocalizedName()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 125 */     if (renderPass == 0)
/*     */     {
/* 127 */       return 16777215;
/*     */     }
/*     */ 
/*     */     
/* 131 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/* 132 */     return (enumdyecolor.getMapColor()).colorValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 143 */     for (EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
/*     */       
/* 145 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 146 */       TileEntityBanner.func_181020_a(nbttagcompound, enumdyecolor.getDyeDamage(), null);
/* 147 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 148 */       nbttagcompound1.setTag("BlockEntityTag", (NBTBase)nbttagcompound);
/* 149 */       ItemStack itemstack = new ItemStack(itemIn, 1, enumdyecolor.getDyeDamage());
/* 150 */       itemstack.setTagCompound(nbttagcompound1);
/* 151 */       subItems.add(itemstack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 160 */     return CreativeTabs.tabDecorations;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumDyeColor getBaseColor(ItemStack stack) {
/* 165 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 166 */     EnumDyeColor enumdyecolor = null;
/*     */     
/* 168 */     if (nbttagcompound != null && nbttagcompound.hasKey("Base")) {
/*     */       
/* 170 */       enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound.getInteger("Base"));
/*     */     }
/*     */     else {
/*     */       
/* 174 */       enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */     } 
/*     */     
/* 177 */     return enumdyecolor;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */