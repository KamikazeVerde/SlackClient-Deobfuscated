/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemMonsterPlacer
/*     */   extends Item
/*     */ {
/*     */   public ItemMonsterPlacer() {
/*  30 */     setHasSubtypes(true);
/*  31 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  36 */     String s = ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name")).trim();
/*  37 */     String s1 = EntityList.getStringFromID(stack.getMetadata());
/*     */     
/*  39 */     if (s1 != null)
/*     */     {
/*  41 */       s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
/*     */     }
/*     */     
/*  44 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  49 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(stack.getMetadata()));
/*  50 */     return (entitylist$entityegginfo != null) ? ((renderPass == 0) ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor) : 16777215;
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
/*  61 */     if (worldIn.isRemote)
/*     */     {
/*  63 */       return true;
/*     */     }
/*  65 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*     */     {
/*  67 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  71 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  73 */     if (iblockstate.getBlock() == Blocks.mob_spawner) {
/*     */       
/*  75 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  77 */       if (tileentity instanceof TileEntityMobSpawner) {
/*     */         
/*  79 */         MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
/*  80 */         mobspawnerbaselogic.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
/*  81 */         tileentity.markDirty();
/*  82 */         worldIn.markBlockForUpdate(pos);
/*     */         
/*  84 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/*  86 */           stack.stackSize--;
/*     */         }
/*     */         
/*  89 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     pos = pos.offset(side);
/*  94 */     double d0 = 0.0D;
/*     */     
/*  96 */     if (side == EnumFacing.UP && iblockstate instanceof net.minecraft.block.BlockFence)
/*     */     {
/*  98 */       d0 = 0.5D;
/*     */     }
/*     */     
/* 101 */     Entity entity = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D);
/*     */     
/* 103 */     if (entity != null) {
/*     */       
/* 105 */       if (entity instanceof net.minecraft.entity.EntityLivingBase && stack.hasDisplayName())
/*     */       {
/* 107 */         entity.setCustomNameTag(stack.getDisplayName());
/*     */       }
/*     */       
/* 110 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 112 */         stack.stackSize--;
/*     */       }
/*     */     } 
/*     */     
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 125 */     if (worldIn.isRemote)
/*     */     {
/* 127 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 131 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*     */     
/* 133 */     if (movingobjectposition == null)
/*     */     {
/* 135 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 139 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*     */       
/* 141 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*     */       
/* 143 */       if (!worldIn.isBlockModifiable(playerIn, blockpos))
/*     */       {
/* 145 */         return itemStackIn;
/*     */       }
/*     */       
/* 148 */       if (!playerIn.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemStackIn))
/*     */       {
/* 150 */         return itemStackIn;
/*     */       }
/*     */       
/* 153 */       if (worldIn.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockLiquid) {
/*     */         
/* 155 */         Entity entity = spawnCreature(worldIn, itemStackIn.getMetadata(), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
/*     */         
/* 157 */         if (entity != null) {
/*     */           
/* 159 */           if (entity instanceof net.minecraft.entity.EntityLivingBase && itemStackIn.hasDisplayName())
/*     */           {
/* 161 */             entity.setCustomNameTag(itemStackIn.getDisplayName());
/*     */           }
/*     */           
/* 164 */           if (!playerIn.capabilities.isCreativeMode)
/*     */           {
/* 166 */             itemStackIn.stackSize--;
/*     */           }
/*     */           
/* 169 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z) {
/* 185 */     if (!EntityList.entityEggs.containsKey(Integer.valueOf(entityID)))
/*     */     {
/* 187 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 191 */     Entity entity = null;
/*     */     
/* 193 */     for (int i = 0; i < 1; i++) {
/*     */       
/* 195 */       entity = EntityList.createEntityByID(entityID, worldIn);
/*     */       
/* 197 */       if (entity instanceof net.minecraft.entity.EntityLivingBase) {
/*     */         
/* 199 */         EntityLiving entityliving = (EntityLiving)entity;
/* 200 */         entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
/* 201 */         entityliving.rotationYawHead = entityliving.rotationYaw;
/* 202 */         entityliving.renderYawOffset = entityliving.rotationYaw;
/* 203 */         entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), null);
/* 204 */         worldIn.spawnEntityInWorld(entity);
/* 205 */         entityliving.playLivingSound();
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return entity;
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
/* 220 */     for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values())
/*     */     {
/* 222 */       subItems.add(new ItemStack(itemIn, 1, entitylist$entityegginfo.spawnedID));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemMonsterPlacer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */