/*     */ package net.minecraft.item;
/*     */ 
/*     */ import de.florianmichael.viamcp.fixes.FixedSoundEngine;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemBlock
/*     */   extends Item
/*     */ {
/*     */   protected final Block block;
/*     */   
/*     */   public ItemBlock(Block block) {
/*  25 */     this.block = block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBlock setUnlocalizedName(String unlocalizedName) {
/*  33 */     super.setUnlocalizedName(unlocalizedName);
/*  34 */     return this;
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
/*  45 */     return FixedSoundEngine.onItemUse(this, stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean setTileEntityNBT(World worldIn, EntityPlayer pos, BlockPos stack, ItemStack p_179224_3_) {
/*  50 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  52 */     if (minecraftserver == null)
/*     */     {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  58 */     if (p_179224_3_.hasTagCompound() && p_179224_3_.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*     */       
/*  60 */       TileEntity tileentity = worldIn.getTileEntity(stack);
/*     */       
/*  62 */       if (tileentity != null) {
/*     */         
/*  64 */         if (!worldIn.isRemote && tileentity.func_183000_F() && !minecraftserver.getConfigurationManager().canSendCommands(pos.getGameProfile()))
/*     */         {
/*  66 */           return false;
/*     */         }
/*     */         
/*  69 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  70 */         NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*  71 */         tileentity.writeToNBT(nbttagcompound);
/*  72 */         NBTTagCompound nbttagcompound2 = (NBTTagCompound)p_179224_3_.getTagCompound().getTag("BlockEntityTag");
/*  73 */         nbttagcompound.merge(nbttagcompound2);
/*  74 */         nbttagcompound.setInteger("x", stack.getX());
/*  75 */         nbttagcompound.setInteger("y", stack.getY());
/*  76 */         nbttagcompound.setInteger("z", stack.getZ());
/*     */         
/*  78 */         if (!nbttagcompound.equals(nbttagcompound1)) {
/*     */           
/*  80 */           tileentity.readFromNBT(nbttagcompound);
/*  81 */           tileentity.markDirty();
/*  82 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/*  93 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/*  95 */     if (block == Blocks.snow_layer) {
/*     */       
/*  97 */       side = EnumFacing.UP;
/*     */     }
/*  99 */     else if (!block.isReplaceable(worldIn, pos)) {
/*     */       
/* 101 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/* 104 */     return worldIn.canBlockBePlaced(this.block, pos, false, side, null, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 113 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName() {
/* 121 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 129 */     return this.block.getCreativeTabToDisplayOn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 139 */     this.block.getSubBlocks(itemIn, tab, subItems);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/* 144 */     return this.block;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */