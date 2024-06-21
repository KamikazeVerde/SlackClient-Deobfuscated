/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTime
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  15 */     return "time";
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
/*  33 */     return "commands.time.usage";
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
/*  44 */     if (args.length > 1) {
/*     */       
/*  46 */       if (args[0].equals("set")) {
/*     */         int l;
/*     */ 
/*     */         
/*  50 */         if (args[1].equals("day")) {
/*     */           
/*  52 */           l = 1000;
/*     */         }
/*  54 */         else if (args[1].equals("night")) {
/*     */           
/*  56 */           l = 13000;
/*     */         }
/*     */         else {
/*     */           
/*  60 */           l = parseInt(args[1], 0);
/*     */         } 
/*     */         
/*  63 */         setTime(sender, l);
/*  64 */         notifyOperators(sender, this, "commands.time.set", new Object[] { Integer.valueOf(l) });
/*     */         
/*     */         return;
/*     */       } 
/*  68 */       if (args[0].equals("add")) {
/*     */         
/*  70 */         int k = parseInt(args[1], 0);
/*  71 */         addTime(sender, k);
/*  72 */         notifyOperators(sender, this, "commands.time.added", new Object[] { Integer.valueOf(k) });
/*     */         
/*     */         return;
/*     */       } 
/*  76 */       if (args[0].equals("query")) {
/*     */         
/*  78 */         if (args[1].equals("daytime")) {
/*     */           
/*  80 */           int j = (int)(sender.getEntityWorld().getWorldTime() % 2147483647L);
/*  81 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, j);
/*  82 */           notifyOperators(sender, this, "commands.time.query", new Object[] { Integer.valueOf(j) });
/*     */           
/*     */           return;
/*     */         } 
/*  86 */         if (args[1].equals("gametime")) {
/*     */           
/*  88 */           int i = (int)(sender.getEntityWorld().getTotalWorldTime() % 2147483647L);
/*  89 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
/*  90 */           notifyOperators(sender, this, "commands.time.query", new Object[] { Integer.valueOf(i) });
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*  96 */     throw new WrongUsageException("commands.time.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 101 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "query" }) : ((args.length == 2 && args[0].equals("set")) ? getListOfStringsMatchingLastWord(args, new String[] { "day", "night" }) : ((args.length == 2 && args[0].equals("query")) ? getListOfStringsMatchingLastWord(args, new String[] { "daytime", "gametime" }) : null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTime(ICommandSender p_71552_1_, int p_71552_2_) {
/* 109 */     for (int i = 0; i < (MinecraftServer.getServer()).worldServers.length; i++)
/*     */     {
/* 111 */       (MinecraftServer.getServer()).worldServers[i].setWorldTime(p_71552_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTime(ICommandSender p_71553_1_, int p_71553_2_) {
/* 120 */     for (int i = 0; i < (MinecraftServer.getServer()).worldServers.length; i++) {
/*     */       
/* 122 */       WorldServer worldserver = (MinecraftServer.getServer()).worldServers[i];
/* 123 */       worldserver.setWorldTime(worldserver.getWorldTime() + p_71553_2_);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */