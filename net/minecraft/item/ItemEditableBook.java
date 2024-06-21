/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEditableBook extends Item {
/*     */   public ItemEditableBook() {
/*  24 */     setMaxStackSize(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean validBookTagContents(NBTTagCompound nbt) {
/*  29 */     if (!ItemWritableBook.isNBTValid(nbt))
/*     */     {
/*  31 */       return false;
/*     */     }
/*  33 */     if (!nbt.hasKey("title", 8))
/*     */     {
/*  35 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  39 */     String s = nbt.getString("title");
/*  40 */     return (s != null && s.length() <= 32) ? nbt.hasKey("author", 8) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGeneration(ItemStack book) {
/*  51 */     return book.getTagCompound().getInteger("generation");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  56 */     if (stack.hasTagCompound()) {
/*     */       
/*  58 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  59 */       String s = nbttagcompound.getString("title");
/*     */       
/*  61 */       if (!StringUtils.isNullOrEmpty(s))
/*     */       {
/*  63 */         return s;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return super.getItemStackDisplayName(stack);
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
/*  78 */     if (stack.hasTagCompound()) {
/*     */       
/*  80 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  81 */       String s = nbttagcompound.getString("author");
/*     */       
/*  83 */       if (!StringUtils.isNullOrEmpty(s))
/*     */       {
/*  85 */         tooltip.add(ChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] { s }));
/*     */       }
/*     */       
/*  88 */       tooltip.add(ChatFormatting.GRAY + StatCollector.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  97 */     if (!worldIn.isRemote)
/*     */     {
/*  99 */       resolveContents(itemStackIn, playerIn);
/*     */     }
/*     */     
/* 102 */     playerIn.displayGUIBook(itemStackIn);
/* 103 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 104 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resolveContents(ItemStack stack, EntityPlayer player) {
/* 109 */     if (stack != null && stack.getTagCompound() != null) {
/*     */       
/* 111 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 113 */       if (!nbttagcompound.getBoolean("resolved")) {
/*     */         
/* 115 */         nbttagcompound.setBoolean("resolved", true);
/*     */         
/* 117 */         if (validBookTagContents(nbttagcompound)) {
/*     */           
/* 119 */           NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);
/*     */           
/* 121 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */             ChatComponentText chatComponentText;
/* 123 */             String s = nbttaglist.getStringTagAt(i);
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 128 */               IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 129 */               ichatcomponent = ChatComponentProcessor.processComponent((ICommandSender)player, ichatcomponent, (Entity)player);
/*     */             }
/* 131 */             catch (Exception var9) {
/*     */               
/* 133 */               chatComponentText = new ChatComponentText(s);
/*     */             } 
/*     */             
/* 136 */             nbttaglist.set(i, (NBTBase)new NBTTagString(IChatComponent.Serializer.componentToJson((IChatComponent)chatComponentText)));
/*     */           } 
/*     */           
/* 139 */           nbttagcompound.setTag("pages", (NBTBase)nbttaglist);
/*     */           
/* 141 */           if (player instanceof EntityPlayerMP && player.getCurrentEquippedItem() == stack) {
/*     */             
/* 143 */             Slot slot = player.openContainer.getSlotFromInventory((IInventory)player.inventory, player.inventory.currentItem);
/* 144 */             ((EntityPlayerMP)player).playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(0, slot.slotNumber, stack));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 153 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemEditableBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */