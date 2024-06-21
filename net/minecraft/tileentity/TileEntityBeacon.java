/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityBeacon
/*     */   extends TileEntityLockable implements ITickable, IInventory {
/*  33 */   public static final Potion[][] effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
/*  34 */   private final List<BeamSegment> beamSegments = Lists.newArrayList();
/*     */   
/*     */   private long beamRenderCounter;
/*     */   
/*     */   private float field_146014_j;
/*     */   private boolean isComplete;
/*  40 */   private int levels = -1;
/*     */ 
/*     */   
/*     */   private int primaryEffect;
/*     */ 
/*     */   
/*     */   private int secondaryEffect;
/*     */ 
/*     */   
/*     */   private ItemStack payment;
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  57 */     if (this.worldObj.getTotalWorldTime() % 80L == 0L)
/*     */     {
/*  59 */       updateBeacon();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBeacon() {
/*  65 */     updateSegmentColors();
/*  66 */     addEffectsToPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addEffectsToPlayers() {
/*  71 */     if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
/*     */       
/*  73 */       double d0 = (this.levels * 10 + 10);
/*  74 */       int i = 0;
/*     */       
/*  76 */       if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect)
/*     */       {
/*  78 */         i = 1;
/*     */       }
/*     */       
/*  81 */       int j = this.pos.getX();
/*  82 */       int k = this.pos.getY();
/*  83 */       int l = this.pos.getZ();
/*  84 */       AxisAlignedBB axisalignedbb = (new AxisAlignedBB(j, k, l, (j + 1), (k + 1), (l + 1))).expand(d0, d0, d0).addCoord(0.0D, this.worldObj.getHeight(), 0.0D);
/*  85 */       List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
/*     */       
/*  87 */       for (EntityPlayer entityplayer : list)
/*     */       {
/*  89 */         entityplayer.addPotionEffect(new PotionEffect(this.primaryEffect, 180, i, true, true));
/*     */       }
/*     */       
/*  92 */       if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0)
/*     */       {
/*  94 */         for (EntityPlayer entityplayer1 : list)
/*     */         {
/*  96 */           entityplayer1.addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true, true));
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSegmentColors() {
/* 104 */     int i = this.levels;
/* 105 */     int j = this.pos.getX();
/* 106 */     int k = this.pos.getY();
/* 107 */     int l = this.pos.getZ();
/* 108 */     this.levels = 0;
/* 109 */     this.beamSegments.clear();
/* 110 */     this.isComplete = true;
/* 111 */     BeamSegment tileentitybeacon$beamsegment = new BeamSegment(EntitySheep.func_175513_a(EnumDyeColor.WHITE));
/* 112 */     this.beamSegments.add(tileentitybeacon$beamsegment);
/* 113 */     boolean flag = true;
/* 114 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 116 */     int i1 = k + 1; while (true) { float[] afloat; if (i1 < 256)
/*     */       
/* 118 */       { IBlockState iblockstate = this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(j, i1, l));
/*     */ 
/*     */         
/* 121 */         if (iblockstate.getBlock() == Blocks.stained_glass)
/*     */         
/* 123 */         { afloat = EntitySheep.func_175513_a((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlass.COLOR)); }
/*     */         
/*     */         else
/*     */         
/* 127 */         { if (iblockstate.getBlock() != Blocks.stained_glass_pane)
/*     */           
/* 129 */           { if (iblockstate.getBlock().getLightOpacity() >= 15 && iblockstate.getBlock() != Blocks.bedrock) {
/*     */               
/* 131 */               this.isComplete = false;
/* 132 */               this.beamSegments.clear();
/*     */               
/*     */               break;
/*     */             } 
/* 136 */             tileentitybeacon$beamsegment.incrementHeight(); }
/*     */           
/*     */           else
/*     */           
/* 140 */           { afloat = EntitySheep.func_175513_a((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR));
/*     */ 
/*     */             
/* 143 */             if (!flag)
/*     */             {
/* 145 */               afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F }; }  }  i1++; }  } else { break; }  if (!flag) afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F };
/*     */        }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (this.isComplete) {
/*     */       
/* 163 */       for (int l1 = 1; l1 <= 4; this.levels = l1++) {
/*     */         
/* 165 */         int i2 = k - l1;
/*     */         
/* 167 */         if (i2 < 0) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 172 */         boolean flag1 = true;
/*     */         
/* 174 */         for (int j1 = j - l1; j1 <= j + l1 && flag1; j1++) {
/*     */           
/* 176 */           for (int k1 = l - l1; k1 <= l + l1; k1++) {
/*     */             
/* 178 */             Block block = this.worldObj.getBlockState(new BlockPos(j1, i2, k1)).getBlock();
/*     */             
/* 180 */             if (block != Blocks.emerald_block && block != Blocks.gold_block && block != Blocks.diamond_block && block != Blocks.iron_block) {
/*     */               
/* 182 */               flag1 = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 188 */         if (!flag1) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 194 */       if (this.levels == 0)
/*     */       {
/* 196 */         this.isComplete = false;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     if (!this.worldObj.isRemote && this.levels == 4 && i < this.levels)
/*     */     {
/* 202 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, (new AxisAlignedBB(j, k, l, j, (k - 4), l)).expand(10.0D, 5.0D, 10.0D)))
/*     */       {
/* 204 */         entityplayer.triggerAchievement((StatBase)AchievementList.fullBeacon);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BeamSegment> getBeamSegments() {
/* 211 */     return this.beamSegments;
/*     */   }
/*     */ 
/*     */   
/*     */   public float shouldBeamRender() {
/* 216 */     if (!this.isComplete)
/*     */     {
/* 218 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 222 */     int i = (int)(this.worldObj.getTotalWorldTime() - this.beamRenderCounter);
/* 223 */     this.beamRenderCounter = this.worldObj.getTotalWorldTime();
/*     */     
/* 225 */     if (i > 1) {
/*     */       
/* 227 */       this.field_146014_j -= i / 40.0F;
/*     */       
/* 229 */       if (this.field_146014_j < 0.0F)
/*     */       {
/* 231 */         this.field_146014_j = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 235 */     this.field_146014_j += 0.025F;
/*     */     
/* 237 */     if (this.field_146014_j > 1.0F)
/*     */     {
/* 239 */       this.field_146014_j = 1.0F;
/*     */     }
/*     */     
/* 242 */     return this.field_146014_j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 252 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 253 */     writeToNBT(nbttagcompound);
/* 254 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 3, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 259 */     return 65536.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_183001_h(int p_183001_1_) {
/* 264 */     if (p_183001_1_ >= 0 && p_183001_1_ < Potion.potionTypes.length && Potion.potionTypes[p_183001_1_] != null) {
/*     */       
/* 266 */       Potion potion = Potion.potionTypes[p_183001_1_];
/* 267 */       return (potion != Potion.moveSpeed && potion != Potion.digSpeed && potion != Potion.resistance && potion != Potion.jump && potion != Potion.damageBoost && potion != Potion.regeneration) ? 0 : p_183001_1_;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 277 */     super.readFromNBT(compound);
/* 278 */     this.primaryEffect = func_183001_h(compound.getInteger("Primary"));
/* 279 */     this.secondaryEffect = func_183001_h(compound.getInteger("Secondary"));
/* 280 */     this.levels = compound.getInteger("Levels");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 285 */     super.writeToNBT(compound);
/* 286 */     compound.setInteger("Primary", this.primaryEffect);
/* 287 */     compound.setInteger("Secondary", this.secondaryEffect);
/* 288 */     compound.setInteger("Levels", this.levels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 296 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 306 */     return (index == 0) ? this.payment : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 317 */     if (index == 0 && this.payment != null) {
/*     */       
/* 319 */       if (count >= this.payment.stackSize) {
/*     */         
/* 321 */         ItemStack itemstack = this.payment;
/* 322 */         this.payment = null;
/* 323 */         return itemstack;
/*     */       } 
/*     */ 
/*     */       
/* 327 */       this.payment.stackSize -= count;
/* 328 */       return new ItemStack(this.payment.getItem(), count, this.payment.getMetadata());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 333 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/* 344 */     if (index == 0 && this.payment != null) {
/*     */       
/* 346 */       ItemStack itemstack = this.payment;
/* 347 */       this.payment = null;
/* 348 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 361 */     if (index == 0)
/*     */     {
/* 363 */       this.payment = stack;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 372 */     return hasCustomName() ? this.customName : "container.beacon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 380 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 385 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 393 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 401 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 417 */     return (stack.getItem() == Items.emerald || stack.getItem() == Items.diamond || stack.getItem() == Items.gold_ingot || stack.getItem() == Items.iron_ingot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 422 */     return "minecraft:beacon";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 427 */     return (Container)new ContainerBeacon((IInventory)playerInventory, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 432 */     switch (id) {
/*     */       
/*     */       case 0:
/* 435 */         return this.levels;
/*     */       
/*     */       case 1:
/* 438 */         return this.primaryEffect;
/*     */       
/*     */       case 2:
/* 441 */         return this.secondaryEffect;
/*     */     } 
/*     */     
/* 444 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 450 */     switch (id) {
/*     */       
/*     */       case 0:
/* 453 */         this.levels = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 457 */         this.primaryEffect = func_183001_h(value);
/*     */         break;
/*     */       
/*     */       case 2:
/* 461 */         this.secondaryEffect = func_183001_h(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 467 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 472 */     this.payment = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 477 */     if (id == 1) {
/*     */       
/* 479 */       updateBeacon();
/* 480 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 484 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BeamSegment
/*     */   {
/*     */     private final float[] colors;
/*     */     
/*     */     private int height;
/*     */     
/*     */     public BeamSegment(float[] p_i45669_1_) {
/* 495 */       this.colors = p_i45669_1_;
/* 496 */       this.height = 1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void incrementHeight() {
/* 501 */       this.height++;
/*     */     }
/*     */ 
/*     */     
/*     */     public float[] getColors() {
/* 506 */       return this.colors;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 511 */       return this.height;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */