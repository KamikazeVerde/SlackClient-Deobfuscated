/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ public class CommandAchievement
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  26 */     return "achievement";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  34 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  44 */     return "commands.achievement.usage";
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
/*  55 */     if (args.length < 2)
/*     */     {
/*  57 */       throw new WrongUsageException("commands.achievement.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  61 */     final StatBase statbase = StatList.getOneShotStat(args[1]);
/*     */     
/*  63 */     if (statbase == null && !args[1].equals("*"))
/*     */     {
/*  65 */       throw new CommandException("commands.achievement.unknownAchievement", new Object[] { args[1] });
/*     */     }
/*     */ 
/*     */     
/*  69 */     final EntityPlayerMP entityplayermp = (args.length >= 3) ? getPlayer(sender, args[2]) : getCommandSenderAsPlayer(sender);
/*  70 */     boolean flag = args[0].equalsIgnoreCase("give");
/*  71 */     boolean flag1 = args[0].equalsIgnoreCase("take");
/*     */     
/*  73 */     if (flag || flag1)
/*     */     {
/*  75 */       if (statbase == null) {
/*     */         
/*  77 */         if (flag)
/*     */         {
/*  79 */           for (Achievement achievement4 : AchievementList.achievementList)
/*     */           {
/*  81 */             entityplayermp.triggerAchievement((StatBase)achievement4);
/*     */           }
/*     */           
/*  84 */           notifyOperators(sender, (ICommand)this, "commands.achievement.give.success.all", new Object[] { entityplayermp.getCommandSenderName() });
/*     */         }
/*  86 */         else if (flag1)
/*     */         {
/*  88 */           for (Achievement achievement5 : Lists.reverse(AchievementList.achievementList))
/*     */           {
/*  90 */             entityplayermp.func_175145_a((StatBase)achievement5);
/*     */           }
/*     */           
/*  93 */           notifyOperators(sender, (ICommand)this, "commands.achievement.take.success.all", new Object[] { entityplayermp.getCommandSenderName() });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  98 */         if (statbase instanceof Achievement) {
/*     */           
/* 100 */           Achievement achievement = (Achievement)statbase;
/*     */           
/* 102 */           if (flag) {
/*     */             
/* 104 */             if (entityplayermp.getStatFile().hasAchievementUnlocked(achievement))
/*     */             {
/* 106 */               throw new CommandException("commands.achievement.alreadyHave", new Object[] { entityplayermp.getCommandSenderName(), statbase.func_150955_j() });
/*     */             }
/*     */             
/*     */             List<Achievement> list;
/*     */             
/* 111 */             for (list = Lists.newArrayList(); achievement.parentAchievement != null && !entityplayermp.getStatFile().hasAchievementUnlocked(achievement.parentAchievement); achievement = achievement.parentAchievement)
/*     */             {
/* 113 */               list.add(achievement.parentAchievement);
/*     */             }
/*     */             
/* 116 */             for (Achievement achievement1 : Lists.reverse(list))
/*     */             {
/* 118 */               entityplayermp.triggerAchievement((StatBase)achievement1);
/*     */             }
/*     */           }
/* 121 */           else if (flag1) {
/*     */             
/* 123 */             if (!entityplayermp.getStatFile().hasAchievementUnlocked(achievement))
/*     */             {
/* 125 */               throw new CommandException("commands.achievement.dontHave", new Object[] { entityplayermp.getCommandSenderName(), statbase.func_150955_j() });
/*     */             }
/*     */             
/* 128 */             List<Achievement> list1 = Lists.newArrayList((Iterator)Iterators.filter(AchievementList.achievementList.iterator(), new Predicate<Achievement>()
/*     */                   {
/*     */                     public boolean apply(Achievement p_apply_1_)
/*     */                     {
/* 132 */                       return (entityplayermp.getStatFile().hasAchievementUnlocked(p_apply_1_) && p_apply_1_ != statbase);
/*     */                     }
/*     */                   }));
/* 135 */             List<Achievement> list2 = Lists.newArrayList(list1);
/*     */             
/* 137 */             for (Achievement achievement2 : list1) {
/*     */               
/* 139 */               Achievement achievement3 = achievement2;
/*     */               
/*     */               boolean flag2;
/* 142 */               for (flag2 = false; achievement3 != null; achievement3 = achievement3.parentAchievement) {
/*     */                 
/* 144 */                 if (achievement3 == statbase)
/*     */                 {
/* 146 */                   flag2 = true;
/*     */                 }
/*     */               } 
/*     */               
/* 150 */               if (!flag2)
/*     */               {
/* 152 */                 for (achievement3 = achievement2; achievement3 != null; achievement3 = achievement3.parentAchievement)
/*     */                 {
/* 154 */                   list2.remove(achievement2);
/*     */                 }
/*     */               }
/*     */             } 
/*     */             
/* 159 */             for (Achievement achievement6 : list2)
/*     */             {
/* 161 */               entityplayermp.func_175145_a((StatBase)achievement6);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 166 */         if (flag) {
/*     */           
/* 168 */           entityplayermp.triggerAchievement(statbase);
/* 169 */           notifyOperators(sender, (ICommand)this, "commands.achievement.give.success.one", new Object[] { entityplayermp.getCommandSenderName(), statbase.func_150955_j() });
/*     */         }
/* 171 */         else if (flag1) {
/*     */           
/* 173 */           entityplayermp.func_175145_a(statbase);
/* 174 */           notifyOperators(sender, (ICommand)this, "commands.achievement.take.success.one", new Object[] { statbase.func_150955_j(), entityplayermp.getCommandSenderName() });
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 184 */     if (args.length == 1)
/*     */     {
/* 186 */       return getListOfStringsMatchingLastWord(args, new String[] { "give", "take" });
/*     */     }
/* 188 */     if (args.length != 2)
/*     */     {
/* 190 */       return (args.length == 3) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*     */     }
/*     */ 
/*     */     
/* 194 */     List<String> list = Lists.newArrayList();
/*     */     
/* 196 */     for (StatBase statbase : StatList.allStats)
/*     */     {
/* 198 */       list.add(statbase.statId);
/*     */     }
/*     */     
/* 201 */     return getListOfStringsMatchingLastWord(args, list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 213 */     return (index == 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandAchievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */