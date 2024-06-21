/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandXP
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  15 */     return "xp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  23 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  33 */     return "commands.xp.usage";
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
/*  44 */     if (args.length <= 0)
/*     */     {
/*  46 */       throw new WrongUsageException("commands.xp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  50 */     String s = args[0];
/*  51 */     boolean flag = (s.endsWith("l") || s.endsWith("L"));
/*     */     
/*  53 */     if (flag && s.length() > 1)
/*     */     {
/*  55 */       s = s.substring(0, s.length() - 1);
/*     */     }
/*     */     
/*  58 */     int i = parseInt(s);
/*  59 */     boolean flag1 = (i < 0);
/*     */     
/*  61 */     if (flag1)
/*     */     {
/*  63 */       i *= -1;
/*     */     }
/*     */     
/*  66 */     EntityPlayerMP entityPlayerMP = (args.length > 1) ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
/*     */     
/*  68 */     if (flag) {
/*     */       
/*  70 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceLevel);
/*     */       
/*  72 */       if (flag1)
/*     */       {
/*  74 */         entityPlayerMP.addExperienceLevel(-i);
/*  75 */         notifyOperators(sender, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getCommandSenderName() });
/*     */       }
/*     */       else
/*     */       {
/*  79 */         entityPlayerMP.addExperienceLevel(i);
/*  80 */         notifyOperators(sender, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getCommandSenderName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  85 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceTotal);
/*     */       
/*  87 */       if (flag1)
/*     */       {
/*  89 */         throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
/*     */       }
/*     */       
/*  92 */       entityPlayerMP.addExperience(i);
/*  93 */       notifyOperators(sender, this, "commands.xp.success", new Object[] { Integer.valueOf(i), entityPlayerMP.getCommandSenderName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 100 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getAllUsernames() {
/* 105 */     return MinecraftServer.getServer().getAllUsernames();
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
/* 116 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandXP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */