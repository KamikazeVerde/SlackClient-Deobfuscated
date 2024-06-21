/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTrigger
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "trigger";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  39 */     return "commands.trigger.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     EntityPlayerMP entityplayermp;
/*  50 */     if (args.length < 3)
/*     */     {
/*  52 */       throw new WrongUsageException("commands.trigger.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (sender instanceof EntityPlayerMP) {
/*     */       
/*  60 */       entityplayermp = (EntityPlayerMP)sender;
/*     */     }
/*     */     else {
/*     */       
/*  64 */       Entity entity = sender.getCommandSenderEntity();
/*     */       
/*  66 */       if (!(entity instanceof EntityPlayerMP))
/*     */       {
/*  68 */         throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
/*     */       }
/*     */       
/*  71 */       entityplayermp = (EntityPlayerMP)entity;
/*     */     } 
/*     */     
/*  74 */     Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*  75 */     ScoreObjective scoreobjective = scoreboard.getObjective(args[0]);
/*     */     
/*  77 */     if (scoreobjective != null && scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
/*     */       
/*  79 */       int i = parseInt(args[2]);
/*     */       
/*  81 */       if (!scoreboard.entityHasObjective(entityplayermp.getCommandSenderName(), scoreobjective))
/*     */       {
/*  83 */         throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */       }
/*     */ 
/*     */       
/*  87 */       Score score = scoreboard.getValueFromObjective(entityplayermp.getCommandSenderName(), scoreobjective);
/*     */       
/*  89 */       if (score.isLocked())
/*     */       {
/*  91 */         throw new CommandException("commands.trigger.disabled", new Object[] { args[0] });
/*     */       }
/*     */ 
/*     */       
/*  95 */       if ("set".equals(args[1])) {
/*     */         
/*  97 */         score.setScorePoints(i);
/*     */       }
/*     */       else {
/*     */         
/* 101 */         if (!"add".equals(args[1]))
/*     */         {
/* 103 */           throw new CommandException("commands.trigger.invalidMode", new Object[] { args[1] });
/*     */         }
/*     */         
/* 106 */         score.increseScore(i);
/*     */       } 
/*     */       
/* 109 */       score.setLocked(true);
/*     */       
/* 111 */       if (entityplayermp.theItemInWorldManager.isCreative())
/*     */       {
/* 113 */         notifyOperators(sender, this, "commands.trigger.success", new Object[] { args[0], args[1], args[2] });
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 120 */       throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 127 */     if (args.length == 1) {
/*     */       
/* 129 */       Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/* 130 */       List<String> list = Lists.newArrayList();
/*     */       
/* 132 */       for (ScoreObjective scoreobjective : scoreboard.getScoreObjectives()) {
/*     */         
/* 134 */         if (scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER)
/*     */         {
/* 136 */           list.add(scoreobjective.getName());
/*     */         }
/*     */       } 
/*     */       
/* 140 */       return getListOfStringsMatchingLastWord(args, list.<String>toArray(new String[list.size()]));
/*     */     } 
/*     */ 
/*     */     
/* 144 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, new String[] { "add", "set" }) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */