/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClearInventory
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "clear";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  30 */     return "commands.clear.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  38 */     return 2;
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
/*  49 */     EntityPlayerMP entityplayermp = (args.length == 0) ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
/*  50 */     Item item = (args.length >= 2) ? getItemByText(sender, args[1]) : null;
/*  51 */     int i = (args.length >= 3) ? parseInt(args[2], -1) : -1;
/*  52 */     int j = (args.length >= 4) ? parseInt(args[3], -1) : -1;
/*  53 */     NBTTagCompound nbttagcompound = null;
/*     */     
/*  55 */     if (args.length >= 5) {
/*     */       
/*     */       try {
/*     */         
/*  59 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 4));
/*     */       }
/*  61 */       catch (NBTException nbtexception) {
/*     */         
/*  63 */         throw new CommandException("commands.clear.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     }
/*     */     
/*  67 */     if (args.length >= 2 && item == null)
/*     */     {
/*  69 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getCommandSenderName() });
/*     */     }
/*     */ 
/*     */     
/*  73 */     int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
/*  74 */     entityplayermp.inventoryContainer.detectAndSendChanges();
/*     */     
/*  76 */     if (!entityplayermp.capabilities.isCreativeMode)
/*     */     {
/*  78 */       entityplayermp.updateHeldItem();
/*     */     }
/*     */     
/*  81 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/*     */     
/*  83 */     if (k == 0)
/*     */     {
/*  85 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getCommandSenderName() });
/*     */     }
/*     */ 
/*     */     
/*  89 */     if (j == 0) {
/*     */       
/*  91 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.clear.testing", new Object[] { entityplayermp.getCommandSenderName(), Integer.valueOf(k) }));
/*     */     }
/*     */     else {
/*     */       
/*  95 */       notifyOperators(sender, this, "commands.clear.success", new Object[] { entityplayermp.getCommandSenderName(), Integer.valueOf(k) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 103 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, func_147209_d()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] func_147209_d() {
/* 108 */     return MinecraftServer.getServer().getAllUsernames();
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
/* 119 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandClearInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */