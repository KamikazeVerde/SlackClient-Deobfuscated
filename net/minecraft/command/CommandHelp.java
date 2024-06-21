/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandHelp
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "help";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  41 */     return "commands.help.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  46 */     return Arrays.asList(new String[] { "?" });
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
/*  57 */     List<ICommand> list = getSortedPossibleCommands(sender);
/*  58 */     int i = 7;
/*  59 */     int j = (list.size() - 1) / 7;
/*  60 */     int k = 0;
/*     */ 
/*     */     
/*     */     try {
/*  64 */       k = (args.length == 0) ? 0 : (parseInt(args[0], 1, j + 1) - 1);
/*     */     }
/*  66 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/*  68 */       Map<String, ICommand> map = getCommands();
/*  69 */       ICommand icommand = map.get(args[0]);
/*     */       
/*  71 */       if (icommand != null)
/*     */       {
/*  73 */         throw new WrongUsageException(icommand.getCommandUsage(sender), new Object[0]);
/*     */       }
/*     */       
/*  76 */       if (MathHelper.parseIntWithDefault(args[0], -1) != -1)
/*     */       {
/*  78 */         throw numberinvalidexception;
/*     */       }
/*     */       
/*  81 */       throw new CommandNotFoundException();
/*     */     } 
/*     */     
/*  84 */     int l = Math.min((k + 1) * 7, list.size());
/*  85 */     ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.help.header", new Object[] { Integer.valueOf(k + 1), Integer.valueOf(j + 1) });
/*  86 */     chatcomponenttranslation1.getChatStyle().setColor(ChatFormatting.DARK_GREEN);
/*  87 */     sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */     
/*  89 */     for (int i1 = k * 7; i1 < l; i1++) {
/*     */       
/*  91 */       ICommand icommand1 = list.get(i1);
/*  92 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(icommand1.getCommandUsage(sender), new Object[0]);
/*  93 */       chatcomponenttranslation.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + icommand1.getCommandName() + " "));
/*  94 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */     } 
/*     */     
/*  97 */     if (k == 0 && sender instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */       
/*  99 */       ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
/* 100 */       chatcomponenttranslation2.getChatStyle().setColor(ChatFormatting.GREEN);
/* 101 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ICommand> getSortedPossibleCommands(ICommandSender p_71534_1_) {
/* 107 */     List<ICommand> list = MinecraftServer.getServer().getCommandManager().getPossibleCommands(p_71534_1_);
/* 108 */     Collections.sort(list);
/* 109 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, ICommand> getCommands() {
/* 114 */     return MinecraftServer.getServer().getCommandManager().getCommands();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 119 */     if (args.length == 1) {
/*     */       
/* 121 */       Set<String> set = getCommands().keySet();
/* 122 */       return getListOfStringsMatchingLastWord(args, set.<String>toArray(new String[set.size()]));
/*     */     } 
/*     */ 
/*     */     
/* 126 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */