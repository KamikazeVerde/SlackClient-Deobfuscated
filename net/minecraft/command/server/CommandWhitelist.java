/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class CommandWhitelist
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "whitelist";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  39 */     return "commands.whitelist.usage";
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
/*  50 */     if (args.length < 1)
/*     */     {
/*  52 */       throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  56 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  58 */     if (args[0].equals("on")) {
/*     */       
/*  60 */       minecraftserver.getConfigurationManager().setWhiteListEnabled(true);
/*  61 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.enabled", new Object[0]);
/*     */     }
/*  63 */     else if (args[0].equals("off")) {
/*     */       
/*  65 */       minecraftserver.getConfigurationManager().setWhiteListEnabled(false);
/*  66 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.disabled", new Object[0]);
/*     */     }
/*  68 */     else if (args[0].equals("list")) {
/*     */       
/*  70 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.whitelist.list", new Object[] { Integer.valueOf((minecraftserver.getConfigurationManager().getWhitelistedPlayerNames()).length), Integer.valueOf((minecraftserver.getConfigurationManager().getAvailablePlayerDat()).length) }));
/*  71 */       String[] astring = minecraftserver.getConfigurationManager().getWhitelistedPlayerNames();
/*  72 */       sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])astring)));
/*     */     }
/*  74 */     else if (args[0].equals("add")) {
/*     */       
/*  76 */       if (args.length < 2)
/*     */       {
/*  78 */         throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  81 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[1]);
/*     */       
/*  83 */       if (gameprofile == null)
/*     */       {
/*  85 */         throw new CommandException("commands.whitelist.add.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/*  88 */       minecraftserver.getConfigurationManager().addWhitelistedPlayer(gameprofile);
/*  89 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.add.success", new Object[] { args[1] });
/*     */     }
/*  91 */     else if (args[0].equals("remove")) {
/*     */       
/*  93 */       if (args.length < 2)
/*     */       {
/*  95 */         throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
/*     */       }
/*     */       
/*  98 */       GameProfile gameprofile1 = minecraftserver.getConfigurationManager().getWhitelistedPlayers().func_152706_a(args[1]);
/*     */       
/* 100 */       if (gameprofile1 == null)
/*     */       {
/* 102 */         throw new CommandException("commands.whitelist.remove.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/* 105 */       minecraftserver.getConfigurationManager().removePlayerFromWhitelist(gameprofile1);
/* 106 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.remove.success", new Object[] { args[1] });
/*     */     }
/* 108 */     else if (args[0].equals("reload")) {
/*     */       
/* 110 */       minecraftserver.getConfigurationManager().loadWhiteList();
/* 111 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.reloaded", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 118 */     if (args.length == 1)
/*     */     {
/* 120 */       return getListOfStringsMatchingLastWord(args, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*     */     }
/*     */ 
/*     */     
/* 124 */     if (args.length == 2) {
/*     */       
/* 126 */       if (args[0].equals("remove"))
/*     */       {
/* 128 */         return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getWhitelistedPlayerNames());
/*     */       }
/*     */       
/* 131 */       if (args[0].equals("add"))
/*     */       {
/* 133 */         return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getPlayerProfileCache().getUsernames());
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */