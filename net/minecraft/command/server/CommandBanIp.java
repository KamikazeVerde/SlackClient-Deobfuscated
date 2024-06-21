/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.PlayerNotFoundException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.IPBanEntry;
/*     */ import net.minecraft.server.management.UserListEntry;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class CommandBanIp extends CommandBase {
/*  20 */   public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  27 */     return "ban-ip";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  35 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/*  45 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  55 */     return "commands.banip.usage";
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
/*  66 */     if (args.length >= 1 && args[0].length() > 1) {
/*     */       
/*  68 */       IChatComponent ichatcomponent = (args.length >= 2) ? getChatComponentFromNthArg(sender, args, 1) : null;
/*  69 */       Matcher matcher = field_147211_a.matcher(args[0]);
/*     */       
/*  71 */       if (matcher.matches())
/*     */       {
/*  73 */         func_147210_a(sender, args[0], (ichatcomponent == null) ? null : ichatcomponent.getUnformattedText());
/*     */       }
/*     */       else
/*     */       {
/*  77 */         EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/*     */         
/*  79 */         if (entityplayermp == null)
/*     */         {
/*  81 */           throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
/*     */         }
/*     */         
/*  84 */         func_147210_a(sender, entityplayermp.getPlayerIP(), (ichatcomponent == null) ? null : ichatcomponent.getUnformattedText());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  89 */       throw new WrongUsageException("commands.banip.usage", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  95 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_147210_a(ICommandSender p_147210_1_, String p_147210_2_, String p_147210_3_) {
/* 100 */     IPBanEntry ipbanentry = new IPBanEntry(p_147210_2_, null, p_147210_1_.getCommandSenderName(), null, p_147210_3_);
/* 101 */     MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry((UserListEntry)ipbanentry);
/* 102 */     List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(p_147210_2_);
/* 103 */     String[] astring = new String[list.size()];
/* 104 */     int i = 0;
/*     */     
/* 106 */     for (EntityPlayerMP entityplayermp : list) {
/*     */       
/* 108 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
/* 109 */       astring[i++] = entityplayermp.getCommandSenderName();
/*     */     } 
/*     */     
/* 112 */     if (list.isEmpty()) {
/*     */       
/* 114 */       notifyOperators(p_147210_1_, (ICommand)this, "commands.banip.success", new Object[] { p_147210_2_ });
/*     */     }
/*     */     else {
/*     */       
/* 118 */       notifyOperators(p_147210_1_, (ICommand)this, "commands.banip.success.players", new Object[] { p_147210_2_, joinNiceString((Object[])astring) });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandBanIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */