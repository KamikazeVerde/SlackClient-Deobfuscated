/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ public class CommandGive
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "give";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  38 */     return "commands.give.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  49 */     if (args.length < 2)
/*     */     {
/*  51 */       throw new WrongUsageException("commands.give.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*  56 */     Item item = getItemByText(sender, args[1]);
/*  57 */     int i = (args.length >= 3) ? parseInt(args[2], 1, 64) : 1;
/*  58 */     int j = (args.length >= 4) ? parseInt(args[3]) : 0;
/*  59 */     ItemStack itemstack = new ItemStack(item, i, j);
/*     */     
/*  61 */     if (args.length >= 5) {
/*     */       
/*  63 */       String s = getChatComponentFromNthArg(sender, args, 4).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/*  67 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/*     */       }
/*  69 */       catch (NBTException nbtexception) {
/*     */         
/*  71 */         throw new CommandException("commands.give.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     boolean flag = ((EntityPlayer)entityPlayerMP).inventory.addItemStackToInventory(itemstack);
/*     */     
/*  77 */     if (flag) {
/*     */       
/*  79 */       ((EntityPlayer)entityPlayerMP).worldObj.playSoundAtEntity((Entity)entityPlayerMP, "random.pop", 0.2F, ((entityPlayerMP.getRNG().nextFloat() - entityPlayerMP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  80 */       ((EntityPlayer)entityPlayerMP).inventoryContainer.detectAndSendChanges();
/*     */     } 
/*     */     
/*  83 */     if (flag && itemstack.stackSize <= 0) {
/*     */       
/*  85 */       itemstack.stackSize = 1;
/*  86 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i);
/*  87 */       EntityItem entityitem1 = entityPlayerMP.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       
/*  89 */       if (entityitem1 != null)
/*     */       {
/*  91 */         entityitem1.func_174870_v();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  96 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i - itemstack.stackSize);
/*  97 */       EntityItem entityitem = entityPlayerMP.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       
/*  99 */       if (entityitem != null) {
/*     */         
/* 101 */         entityitem.setNoPickupDelay();
/* 102 */         entityitem.setOwner(entityPlayerMP.getCommandSenderName());
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     notifyOperators(sender, this, "commands.give.success", new Object[] { itemstack.getChatComponent(), Integer.valueOf(i), entityPlayerMP.getCommandSenderName() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 112 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getPlayers()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getPlayers() {
/* 117 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 128 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandGive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */