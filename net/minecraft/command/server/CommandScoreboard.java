/*      */ package net.minecraft.command.server;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.command.CommandBase;
/*      */ import net.minecraft.command.CommandException;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommand;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.SyntaxErrorException;
/*      */ import net.minecraft.command.WrongUsageException;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.nbt.JsonToNBT;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTException;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTUtil;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ 
/*      */ 
/*      */ public class CommandScoreboard
/*      */   extends CommandBase
/*      */ {
/*      */   public String getCommandName() {
/*   41 */     return "scoreboard";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredPermissionLevel() {
/*   49 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommandUsage(ICommandSender sender) {
/*   59 */     return "commands.scoreboard.usage";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*   70 */     if (!func_175780_b(sender, args)) {
/*      */       
/*   72 */       if (args.length < 1)
/*      */       {
/*   74 */         throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*   78 */       if (args[0].equalsIgnoreCase("objectives")) {
/*      */         
/*   80 */         if (args.length == 1)
/*      */         {
/*   82 */           throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*      */         }
/*      */         
/*   85 */         if (args[1].equalsIgnoreCase("list"))
/*      */         {
/*   87 */           listObjectives(sender);
/*      */         }
/*   89 */         else if (args[1].equalsIgnoreCase("add"))
/*      */         {
/*   91 */           if (args.length < 4)
/*      */           {
/*   93 */             throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*      */           }
/*      */           
/*   96 */           addObjective(sender, args, 2);
/*      */         }
/*   98 */         else if (args[1].equalsIgnoreCase("remove"))
/*      */         {
/*  100 */           if (args.length != 3)
/*      */           {
/*  102 */             throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  105 */           removeObjective(sender, args[2]);
/*      */         }
/*      */         else
/*      */         {
/*  109 */           if (!args[1].equalsIgnoreCase("setdisplay"))
/*      */           {
/*  111 */             throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*      */           }
/*      */           
/*  114 */           if (args.length != 3 && args.length != 4)
/*      */           {
/*  116 */             throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*      */           }
/*      */           
/*  119 */           setObjectiveDisplay(sender, args, 2);
/*      */         }
/*      */       
/*  122 */       } else if (args[0].equalsIgnoreCase("players")) {
/*      */         
/*  124 */         if (args.length == 1)
/*      */         {
/*  126 */           throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*      */         }
/*      */         
/*  129 */         if (args[1].equalsIgnoreCase("list"))
/*      */         {
/*  131 */           if (args.length > 3)
/*      */           {
/*  133 */             throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
/*      */           }
/*      */           
/*  136 */           listPlayers(sender, args, 2);
/*      */         }
/*  138 */         else if (args[1].equalsIgnoreCase("add"))
/*      */         {
/*  140 */           if (args.length < 5)
/*      */           {
/*  142 */             throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
/*      */           }
/*      */           
/*  145 */           setPlayer(sender, args, 2);
/*      */         }
/*  147 */         else if (args[1].equalsIgnoreCase("remove"))
/*      */         {
/*  149 */           if (args.length < 5)
/*      */           {
/*  151 */             throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  154 */           setPlayer(sender, args, 2);
/*      */         }
/*  156 */         else if (args[1].equalsIgnoreCase("set"))
/*      */         {
/*  158 */           if (args.length < 5)
/*      */           {
/*  160 */             throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
/*      */           }
/*      */           
/*  163 */           setPlayer(sender, args, 2);
/*      */         }
/*  165 */         else if (args[1].equalsIgnoreCase("reset"))
/*      */         {
/*  167 */           if (args.length != 3 && args.length != 4)
/*      */           {
/*  169 */             throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
/*      */           }
/*      */           
/*  172 */           resetPlayers(sender, args, 2);
/*      */         }
/*  174 */         else if (args[1].equalsIgnoreCase("enable"))
/*      */         {
/*  176 */           if (args.length != 4)
/*      */           {
/*  178 */             throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
/*      */           }
/*      */           
/*  181 */           func_175779_n(sender, args, 2);
/*      */         }
/*  183 */         else if (args[1].equalsIgnoreCase("test"))
/*      */         {
/*  185 */           if (args.length != 5 && args.length != 6)
/*      */           {
/*  187 */             throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
/*      */           }
/*      */           
/*  190 */           func_175781_o(sender, args, 2);
/*      */         }
/*      */         else
/*      */         {
/*  194 */           if (!args[1].equalsIgnoreCase("operation"))
/*      */           {
/*  196 */             throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*      */           }
/*      */           
/*  199 */           if (args.length != 7)
/*      */           {
/*  201 */             throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
/*      */           }
/*      */           
/*  204 */           func_175778_p(sender, args, 2);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  209 */         if (!args[0].equalsIgnoreCase("teams"))
/*      */         {
/*  211 */           throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*      */         }
/*      */         
/*  214 */         if (args.length == 1)
/*      */         {
/*  216 */           throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*      */         }
/*      */         
/*  219 */         if (args[1].equalsIgnoreCase("list")) {
/*      */           
/*  221 */           if (args.length > 3)
/*      */           {
/*  223 */             throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
/*      */           }
/*      */           
/*  226 */           listTeams(sender, args, 2);
/*      */         }
/*  228 */         else if (args[1].equalsIgnoreCase("add")) {
/*      */           
/*  230 */           if (args.length < 3)
/*      */           {
/*  232 */             throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*      */           }
/*      */           
/*  235 */           addTeam(sender, args, 2);
/*      */         }
/*  237 */         else if (args[1].equalsIgnoreCase("remove")) {
/*      */           
/*  239 */           if (args.length != 3)
/*      */           {
/*  241 */             throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  244 */           removeTeam(sender, args, 2);
/*      */         }
/*  246 */         else if (args[1].equalsIgnoreCase("empty")) {
/*      */           
/*  248 */           if (args.length != 3)
/*      */           {
/*  250 */             throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
/*      */           }
/*      */           
/*  253 */           emptyTeam(sender, args, 2);
/*      */         }
/*  255 */         else if (args[1].equalsIgnoreCase("join")) {
/*      */           
/*  257 */           if (args.length < 4 && (args.length != 3 || !(sender instanceof net.minecraft.entity.player.EntityPlayer)))
/*      */           {
/*  259 */             throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
/*      */           }
/*      */           
/*  262 */           joinTeam(sender, args, 2);
/*      */         }
/*  264 */         else if (args[1].equalsIgnoreCase("leave")) {
/*      */           
/*  266 */           if (args.length < 3 && !(sender instanceof net.minecraft.entity.player.EntityPlayer))
/*      */           {
/*  268 */             throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
/*      */           }
/*      */           
/*  271 */           leaveTeam(sender, args, 2);
/*      */         }
/*      */         else {
/*      */           
/*  275 */           if (!args[1].equalsIgnoreCase("option"))
/*      */           {
/*  277 */             throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*      */           }
/*      */           
/*  280 */           if (args.length != 4 && args.length != 5)
/*      */           {
/*  282 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */           }
/*      */           
/*  285 */           setTeamOption(sender, args, 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean func_175780_b(ICommandSender p_175780_1_, String[] p_175780_2_) throws CommandException {
/*  294 */     int i = -1;
/*      */     
/*  296 */     for (int j = 0; j < p_175780_2_.length; j++) {
/*      */       
/*  298 */       if (isUsernameIndex(p_175780_2_, j) && "*".equals(p_175780_2_[j])) {
/*      */         
/*  300 */         if (i >= 0)
/*      */         {
/*  302 */           throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
/*      */         }
/*      */         
/*  305 */         i = j;
/*      */       } 
/*      */     } 
/*      */     
/*  309 */     if (i < 0)
/*      */     {
/*  311 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  315 */     List<String> list1 = Lists.newArrayList(getScoreboard().getObjectiveNames());
/*  316 */     String s = p_175780_2_[i];
/*  317 */     List<String> list = Lists.newArrayList();
/*      */     
/*  319 */     for (String s1 : list1) {
/*      */       
/*  321 */       p_175780_2_[i] = s1;
/*      */ 
/*      */       
/*      */       try {
/*  325 */         processCommand(p_175780_1_, p_175780_2_);
/*  326 */         list.add(s1);
/*      */       }
/*  328 */       catch (CommandException commandexception) {
/*      */         
/*  330 */         ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/*  331 */         chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.RED);
/*  332 */         p_175780_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*      */       } 
/*      */     } 
/*      */     
/*  336 */     p_175780_2_[i] = s;
/*  337 */     p_175780_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*      */     
/*  339 */     if (list.size() == 0)
/*      */     {
/*  341 */       throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  345 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Scoreboard getScoreboard() {
/*  352 */     return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ScoreObjective getObjective(String name, boolean edit) throws CommandException {
/*  357 */     Scoreboard scoreboard = getScoreboard();
/*  358 */     ScoreObjective scoreobjective = scoreboard.getObjective(name);
/*      */     
/*  360 */     if (scoreobjective == null)
/*      */     {
/*  362 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { name });
/*      */     }
/*  364 */     if (edit && scoreobjective.getCriteria().isReadOnly())
/*      */     {
/*  366 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { name });
/*      */     }
/*      */ 
/*      */     
/*  370 */     return scoreobjective;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ScorePlayerTeam getTeam(String name) throws CommandException {
/*  376 */     Scoreboard scoreboard = getScoreboard();
/*  377 */     ScorePlayerTeam scoreplayerteam = scoreboard.getTeam(name);
/*      */     
/*  379 */     if (scoreplayerteam == null)
/*      */     {
/*  381 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { name });
/*      */     }
/*      */ 
/*      */     
/*  385 */     return scoreplayerteam;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addObjective(ICommandSender sender, String[] args, int index) throws CommandException {
/*  391 */     String s = args[index++];
/*  392 */     String s1 = args[index++];
/*  393 */     Scoreboard scoreboard = getScoreboard();
/*  394 */     IScoreObjectiveCriteria iscoreobjectivecriteria = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(s1);
/*      */     
/*  396 */     if (iscoreobjectivecriteria == null)
/*      */     {
/*  398 */       throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s1 });
/*      */     }
/*  400 */     if (scoreboard.getObjective(s) != null)
/*      */     {
/*  402 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s });
/*      */     }
/*  404 */     if (s.length() > 16)
/*      */     {
/*  406 */       throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s, Integer.valueOf(16) });
/*      */     }
/*  408 */     if (s.length() == 0)
/*      */     {
/*  410 */       throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  414 */     if (args.length > index) {
/*      */       
/*  416 */       String s2 = getChatComponentFromNthArg(sender, args, index).getUnformattedText();
/*      */       
/*  418 */       if (s2.length() > 32)
/*      */       {
/*  420 */         throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { s2, Integer.valueOf(32) });
/*      */       }
/*      */       
/*  423 */       if (s2.length() > 0)
/*      */       {
/*  425 */         scoreboard.addScoreObjective(s, iscoreobjectivecriteria).setDisplayName(s2);
/*      */       }
/*      */       else
/*      */       {
/*  429 */         scoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  434 */       scoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/*      */     } 
/*      */     
/*  437 */     notifyOperators(sender, (ICommand)this, "commands.scoreboard.objectives.add.success", new Object[] { s });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTeam(ICommandSender sender, String[] args, int index) throws CommandException {
/*  443 */     String s = args[index++];
/*  444 */     Scoreboard scoreboard = getScoreboard();
/*      */     
/*  446 */     if (scoreboard.getTeam(s) != null)
/*      */     {
/*  448 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s });
/*      */     }
/*  450 */     if (s.length() > 16)
/*      */     {
/*  452 */       throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s, Integer.valueOf(16) });
/*      */     }
/*  454 */     if (s.length() == 0)
/*      */     {
/*  456 */       throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  460 */     if (args.length > index) {
/*      */       
/*  462 */       String s1 = getChatComponentFromNthArg(sender, args, index).getUnformattedText();
/*      */       
/*  464 */       if (s1.length() > 32)
/*      */       {
/*  466 */         throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { s1, Integer.valueOf(32) });
/*      */       }
/*      */       
/*  469 */       if (s1.length() > 0)
/*      */       {
/*  471 */         scoreboard.createTeam(s).setTeamName(s1);
/*      */       }
/*      */       else
/*      */       {
/*  475 */         scoreboard.createTeam(s);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  480 */       scoreboard.createTeam(s);
/*      */     } 
/*      */     
/*  483 */     notifyOperators(sender, (ICommand)this, "commands.scoreboard.teams.add.success", new Object[] { s });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setTeamOption(ICommandSender sender, String[] args, int index) throws CommandException {
/*  489 */     ScorePlayerTeam scoreplayerteam = getTeam(args[index++]);
/*      */     
/*  491 */     if (scoreplayerteam != null) {
/*      */       
/*  493 */       String s = args[index++].toLowerCase();
/*      */       
/*  495 */       if (!s.equalsIgnoreCase("color") && !s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles") && !s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
/*      */       {
/*  497 */         throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */       }
/*  499 */       if (args.length == 4) {
/*      */         
/*  501 */         if (s.equalsIgnoreCase("color"))
/*      */         {
/*  503 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(ChatFormatting.getValidValues(true, false)) });
/*      */         }
/*  505 */         if (!s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles")) {
/*      */           
/*  507 */           if (!s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
/*      */           {
/*  509 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */           }
/*      */ 
/*      */           
/*  513 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  518 */         throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  523 */       String s1 = args[index];
/*      */       
/*  525 */       if (s.equalsIgnoreCase("color")) {
/*      */         
/*  527 */         ChatFormatting enumchatformatting = ChatFormatting.getValueByName(s1);
/*      */         
/*  529 */         if (enumchatformatting == null || enumchatformatting.isFancyStyling())
/*      */         {
/*  531 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(ChatFormatting.getValidValues(true, false)) });
/*      */         }
/*      */         
/*  534 */         scoreplayerteam.setChatFormat(enumchatformatting);
/*  535 */         scoreplayerteam.setNamePrefix(enumchatformatting.toString());
/*  536 */         scoreplayerteam.setNameSuffix(ChatFormatting.RESET.toString());
/*      */       }
/*  538 */       else if (s.equalsIgnoreCase("friendlyfire")) {
/*      */         
/*  540 */         if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false"))
/*      */         {
/*  542 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */         }
/*      */         
/*  545 */         scoreplayerteam.setAllowFriendlyFire(s1.equalsIgnoreCase("true"));
/*      */       }
/*  547 */       else if (s.equalsIgnoreCase("seeFriendlyInvisibles")) {
/*      */         
/*  549 */         if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false"))
/*      */         {
/*  551 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */         }
/*      */         
/*  554 */         scoreplayerteam.setSeeFriendlyInvisiblesEnabled(s1.equalsIgnoreCase("true"));
/*      */       }
/*  556 */       else if (s.equalsIgnoreCase("nametagVisibility")) {
/*      */         
/*  558 */         Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(s1);
/*      */         
/*  560 */         if (team$enumvisible == null)
/*      */         {
/*  562 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*      */         }
/*      */         
/*  565 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*  567 */       else if (s.equalsIgnoreCase("deathMessageVisibility")) {
/*      */         
/*  569 */         Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(s1);
/*      */         
/*  571 */         if (team$enumvisible1 == null)
/*      */         {
/*  573 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*      */         }
/*      */         
/*  576 */         scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
/*      */       } 
/*      */       
/*  579 */       notifyOperators(sender, (ICommand)this, "commands.scoreboard.teams.option.success", new Object[] { s, scoreplayerteam.getRegisteredName(), s1 });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeTeam(ICommandSender p_147194_1_, String[] p_147194_2_, int p_147194_3_) throws CommandException {
/*  586 */     Scoreboard scoreboard = getScoreboard();
/*  587 */     ScorePlayerTeam scoreplayerteam = getTeam(p_147194_2_[p_147194_3_]);
/*      */     
/*  589 */     if (scoreplayerteam != null) {
/*      */       
/*  591 */       scoreboard.removeTeam(scoreplayerteam);
/*  592 */       notifyOperators(p_147194_1_, (ICommand)this, "commands.scoreboard.teams.remove.success", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void listTeams(ICommandSender p_147186_1_, String[] p_147186_2_, int p_147186_3_) throws CommandException {
/*  598 */     Scoreboard scoreboard = getScoreboard();
/*      */     
/*  600 */     if (p_147186_2_.length > p_147186_3_) {
/*      */       
/*  602 */       ScorePlayerTeam scoreplayerteam = getTeam(p_147186_2_[p_147186_3_]);
/*      */       
/*  604 */       if (scoreplayerteam == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  609 */       Collection<String> collection = scoreplayerteam.getMembershipCollection();
/*  610 */       p_147186_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*      */       
/*  612 */       if (collection.size() <= 0)
/*      */       {
/*  614 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */       }
/*      */       
/*  617 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*  618 */       chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.DARK_GREEN);
/*  619 */       p_147186_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*  620 */       p_147186_1_.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString(collection.toArray())));
/*      */     }
/*      */     else {
/*      */       
/*  624 */       Collection<ScorePlayerTeam> collection1 = scoreboard.getTeams();
/*  625 */       p_147186_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection1.size());
/*      */       
/*  627 */       if (collection1.size() <= 0)
/*      */       {
/*  629 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/*      */       }
/*      */       
/*  632 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(collection1.size()) });
/*  633 */       chatcomponenttranslation1.getChatStyle().setColor(ChatFormatting.DARK_GREEN);
/*  634 */       p_147186_1_.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*      */       
/*  636 */       for (ScorePlayerTeam scoreplayerteam1 : collection1) {
/*      */         
/*  638 */         p_147186_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scoreplayerteam1.getRegisteredName(), scoreplayerteam1.getTeamName(), Integer.valueOf(scoreplayerteam1.getMembershipCollection().size()) }));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void joinTeam(ICommandSender p_147190_1_, String[] p_147190_2_, int p_147190_3_) throws CommandException {
/*  645 */     Scoreboard scoreboard = getScoreboard();
/*  646 */     String s = p_147190_2_[p_147190_3_++];
/*  647 */     Set<String> set = Sets.newHashSet();
/*  648 */     Set<String> set1 = Sets.newHashSet();
/*      */     
/*  650 */     if (p_147190_1_ instanceof net.minecraft.entity.player.EntityPlayer && p_147190_3_ == p_147190_2_.length) {
/*      */       
/*  652 */       String s4 = getCommandSenderAsPlayer(p_147190_1_).getCommandSenderName();
/*      */       
/*  654 */       if (scoreboard.addPlayerToTeam(s4, s))
/*      */       {
/*  656 */         set.add(s4);
/*      */       }
/*      */       else
/*      */       {
/*  660 */         set1.add(s4);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  665 */       while (p_147190_3_ < p_147190_2_.length) {
/*      */         
/*  667 */         String s1 = p_147190_2_[p_147190_3_++];
/*      */         
/*  669 */         if (s1.startsWith("@")) {
/*      */           
/*  671 */           for (Entity entity : func_175763_c(p_147190_1_, s1)) {
/*      */             
/*  673 */             String s3 = getEntityName(p_147190_1_, entity.getUniqueID().toString());
/*      */             
/*  675 */             if (scoreboard.addPlayerToTeam(s3, s)) {
/*      */               
/*  677 */               set.add(s3);
/*      */               
/*      */               continue;
/*      */             } 
/*  681 */             set1.add(s3);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  687 */         String s2 = getEntityName(p_147190_1_, s1);
/*      */         
/*  689 */         if (scoreboard.addPlayerToTeam(s2, s)) {
/*      */           
/*  691 */           set.add(s2);
/*      */           
/*      */           continue;
/*      */         } 
/*  695 */         set1.add(s2);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  701 */     if (!set.isEmpty()) {
/*      */       
/*  703 */       p_147190_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/*  704 */       notifyOperators(p_147190_1_, (ICommand)this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(set.size()), s, joinNiceString(set.toArray((Object[])new String[set.size()])) });
/*      */     } 
/*      */     
/*  707 */     if (!set1.isEmpty())
/*      */     {
/*  709 */       throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(set1.size()), s, joinNiceString(set1.toArray(new String[set1.size()])) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void leaveTeam(ICommandSender p_147199_1_, String[] p_147199_2_, int p_147199_3_) throws CommandException {
/*  715 */     Scoreboard scoreboard = getScoreboard();
/*  716 */     Set<String> set = Sets.newHashSet();
/*  717 */     Set<String> set1 = Sets.newHashSet();
/*      */     
/*  719 */     if (p_147199_1_ instanceof net.minecraft.entity.player.EntityPlayer && p_147199_3_ == p_147199_2_.length) {
/*      */       
/*  721 */       String s3 = getCommandSenderAsPlayer(p_147199_1_).getCommandSenderName();
/*      */       
/*  723 */       if (scoreboard.removePlayerFromTeams(s3))
/*      */       {
/*  725 */         set.add(s3);
/*      */       }
/*      */       else
/*      */       {
/*  729 */         set1.add(s3);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  734 */       while (p_147199_3_ < p_147199_2_.length) {
/*      */         
/*  736 */         String s = p_147199_2_[p_147199_3_++];
/*      */         
/*  738 */         if (s.startsWith("@")) {
/*      */           
/*  740 */           for (Entity entity : func_175763_c(p_147199_1_, s)) {
/*      */             
/*  742 */             String s2 = getEntityName(p_147199_1_, entity.getUniqueID().toString());
/*      */             
/*  744 */             if (scoreboard.removePlayerFromTeams(s2)) {
/*      */               
/*  746 */               set.add(s2);
/*      */               
/*      */               continue;
/*      */             } 
/*  750 */             set1.add(s2);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  756 */         String s1 = getEntityName(p_147199_1_, s);
/*      */         
/*  758 */         if (scoreboard.removePlayerFromTeams(s1)) {
/*      */           
/*  760 */           set.add(s1);
/*      */           
/*      */           continue;
/*      */         } 
/*  764 */         set1.add(s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  770 */     if (!set.isEmpty()) {
/*      */       
/*  772 */       p_147199_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/*  773 */       notifyOperators(p_147199_1_, (ICommand)this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(set.size()), joinNiceString(set.toArray((Object[])new String[set.size()])) });
/*      */     } 
/*      */     
/*  776 */     if (!set1.isEmpty())
/*      */     {
/*  778 */       throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(set1.size()), joinNiceString(set1.toArray(new String[set1.size()])) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void emptyTeam(ICommandSender p_147188_1_, String[] p_147188_2_, int p_147188_3_) throws CommandException {
/*  784 */     Scoreboard scoreboard = getScoreboard();
/*  785 */     ScorePlayerTeam scoreplayerteam = getTeam(p_147188_2_[p_147188_3_]);
/*      */     
/*  787 */     if (scoreplayerteam != null) {
/*      */       
/*  789 */       Collection<String> collection = Lists.newArrayList(scoreplayerteam.getMembershipCollection());
/*  790 */       p_147188_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, collection.size());
/*      */       
/*  792 */       if (collection.isEmpty())
/*      */       {
/*  794 */         throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */       }
/*      */ 
/*      */       
/*  798 */       for (String s : collection)
/*      */       {
/*  800 */         scoreboard.removePlayerFromTeam(s, scoreplayerteam);
/*      */       }
/*      */       
/*  803 */       notifyOperators(p_147188_1_, (ICommand)this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeObjective(ICommandSender p_147191_1_, String p_147191_2_) throws CommandException {
/*  810 */     Scoreboard scoreboard = getScoreboard();
/*  811 */     ScoreObjective scoreobjective = getObjective(p_147191_2_, false);
/*  812 */     scoreboard.removeObjective(scoreobjective);
/*  813 */     notifyOperators(p_147191_1_, (ICommand)this, "commands.scoreboard.objectives.remove.success", new Object[] { p_147191_2_ });
/*      */   }
/*      */ 
/*      */   
/*      */   protected void listObjectives(ICommandSender p_147196_1_) throws CommandException {
/*  818 */     Scoreboard scoreboard = getScoreboard();
/*  819 */     Collection<ScoreObjective> collection = scoreboard.getScoreObjectives();
/*      */     
/*  821 */     if (collection.size() <= 0)
/*      */     {
/*  823 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  827 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(collection.size()) });
/*  828 */     chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.DARK_GREEN);
/*  829 */     p_147196_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*      */     
/*  831 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/*  833 */       p_147196_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreobjective.getName(), scoreobjective.getDisplayName(), scoreobjective.getCriteria().getName() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setObjectiveDisplay(ICommandSender p_147198_1_, String[] p_147198_2_, int p_147198_3_) throws CommandException {
/*  840 */     Scoreboard scoreboard = getScoreboard();
/*  841 */     String s = p_147198_2_[p_147198_3_++];
/*  842 */     int i = Scoreboard.getObjectiveDisplaySlotNumber(s);
/*  843 */     ScoreObjective scoreobjective = null;
/*      */     
/*  845 */     if (p_147198_2_.length == 4)
/*      */     {
/*  847 */       scoreobjective = getObjective(p_147198_2_[p_147198_3_], false);
/*      */     }
/*      */     
/*  850 */     if (i < 0)
/*      */     {
/*  852 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s });
/*      */     }
/*      */ 
/*      */     
/*  856 */     scoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
/*      */     
/*  858 */     if (scoreobjective != null) {
/*      */       
/*  860 */       notifyOperators(p_147198_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getObjectiveDisplaySlot(i), scoreobjective.getName() });
/*      */     }
/*      */     else {
/*      */       
/*  864 */       notifyOperators(p_147198_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getObjectiveDisplaySlot(i) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void listPlayers(ICommandSender p_147195_1_, String[] p_147195_2_, int p_147195_3_) throws CommandException {
/*  871 */     Scoreboard scoreboard = getScoreboard();
/*      */     
/*  873 */     if (p_147195_2_.length > p_147195_3_) {
/*      */       
/*  875 */       String s = getEntityName(p_147195_1_, p_147195_2_[p_147195_3_]);
/*  876 */       Map<ScoreObjective, Score> map = scoreboard.getObjectivesForEntity(s);
/*  877 */       p_147195_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, map.size());
/*      */       
/*  879 */       if (map.size() <= 0)
/*      */       {
/*  881 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { s });
/*      */       }
/*      */       
/*  884 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(map.size()), s });
/*  885 */       chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.DARK_GREEN);
/*  886 */       p_147195_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*      */       
/*  888 */       for (Score score : map.values())
/*      */       {
/*  890 */         p_147195_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(score.getScorePoints()), score.getObjective().getDisplayName(), score.getObjective().getName() }));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  895 */       Collection<String> collection = scoreboard.getObjectiveNames();
/*  896 */       p_147195_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*      */       
/*  898 */       if (collection.size() <= 0)
/*      */       {
/*  900 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/*      */       }
/*      */       
/*  903 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(collection.size()) });
/*  904 */       chatcomponenttranslation1.getChatStyle().setColor(ChatFormatting.DARK_GREEN);
/*  905 */       p_147195_1_.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*  906 */       p_147195_1_.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString(collection.toArray())));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setPlayer(ICommandSender p_147197_1_, String[] p_147197_2_, int p_147197_3_) throws CommandException {
/*  912 */     String s = p_147197_2_[p_147197_3_ - 1];
/*  913 */     int i = p_147197_3_;
/*  914 */     String s1 = getEntityName(p_147197_1_, p_147197_2_[p_147197_3_++]);
/*      */     
/*  916 */     if (s1.length() > 40)
/*      */     {
/*  918 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s1, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/*  922 */     ScoreObjective scoreobjective = getObjective(p_147197_2_[p_147197_3_++], true);
/*  923 */     int j = s.equalsIgnoreCase("set") ? parseInt(p_147197_2_[p_147197_3_++]) : parseInt(p_147197_2_[p_147197_3_++], 0);
/*      */     
/*  925 */     if (p_147197_2_.length > p_147197_3_) {
/*      */       
/*  927 */       Entity entity = func_175768_b(p_147197_1_, p_147197_2_[i]);
/*      */ 
/*      */       
/*      */       try {
/*  931 */         NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(buildString(p_147197_2_, p_147197_3_));
/*  932 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  933 */         entity.writeToNBT(nbttagcompound1);
/*      */         
/*  935 */         if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*      */         {
/*  937 */           throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { s1 });
/*      */         }
/*      */       }
/*  940 */       catch (NBTException nbtexception) {
/*      */         
/*  942 */         throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { nbtexception.getMessage() });
/*      */       } 
/*      */     } 
/*      */     
/*  946 */     Scoreboard scoreboard = getScoreboard();
/*  947 */     Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/*      */     
/*  949 */     if (s.equalsIgnoreCase("set")) {
/*      */       
/*  951 */       score.setScorePoints(j);
/*      */     }
/*  953 */     else if (s.equalsIgnoreCase("add")) {
/*      */       
/*  955 */       score.increseScore(j);
/*      */     }
/*      */     else {
/*      */       
/*  959 */       score.decreaseScore(j);
/*      */     } 
/*      */     
/*  962 */     notifyOperators(p_147197_1_, (ICommand)this, "commands.scoreboard.players.set.success", new Object[] { scoreobjective.getName(), s1, Integer.valueOf(score.getScorePoints()) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPlayers(ICommandSender p_147187_1_, String[] p_147187_2_, int p_147187_3_) throws CommandException {
/*  968 */     Scoreboard scoreboard = getScoreboard();
/*  969 */     String s = getEntityName(p_147187_1_, p_147187_2_[p_147187_3_++]);
/*      */     
/*  971 */     if (p_147187_2_.length > p_147187_3_) {
/*      */       
/*  973 */       ScoreObjective scoreobjective = getObjective(p_147187_2_[p_147187_3_++], false);
/*  974 */       scoreboard.removeObjectiveFromEntity(s, scoreobjective);
/*  975 */       notifyOperators(p_147187_1_, (ICommand)this, "commands.scoreboard.players.resetscore.success", new Object[] { scoreobjective.getName(), s });
/*      */     }
/*      */     else {
/*      */       
/*  979 */       scoreboard.removeObjectiveFromEntity(s, null);
/*  980 */       notifyOperators(p_147187_1_, (ICommand)this, "commands.scoreboard.players.reset.success", new Object[] { s });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_175779_n(ICommandSender p_175779_1_, String[] p_175779_2_, int p_175779_3_) throws CommandException {
/*  986 */     Scoreboard scoreboard = getScoreboard();
/*  987 */     String s = getPlayerName(p_175779_1_, p_175779_2_[p_175779_3_++]);
/*      */     
/*  989 */     if (s.length() > 40)
/*      */     {
/*  991 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/*  995 */     ScoreObjective scoreobjective = getObjective(p_175779_2_[p_175779_3_], false);
/*      */     
/*  997 */     if (scoreobjective.getCriteria() != IScoreObjectiveCriteria.TRIGGER)
/*      */     {
/*  999 */       throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { scoreobjective.getName() });
/*      */     }
/*      */ 
/*      */     
/* 1003 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/* 1004 */     score.setLocked(false);
/* 1005 */     notifyOperators(p_175779_1_, (ICommand)this, "commands.scoreboard.players.enable.success", new Object[] { scoreobjective.getName(), s });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_175781_o(ICommandSender p_175781_1_, String[] p_175781_2_, int p_175781_3_) throws CommandException {
/* 1012 */     Scoreboard scoreboard = getScoreboard();
/* 1013 */     String s = getEntityName(p_175781_1_, p_175781_2_[p_175781_3_++]);
/*      */     
/* 1015 */     if (s.length() > 40)
/*      */     {
/* 1017 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1021 */     ScoreObjective scoreobjective = getObjective(p_175781_2_[p_175781_3_++], false);
/*      */     
/* 1023 */     if (!scoreboard.entityHasObjective(s, scoreobjective))
/*      */     {
/* 1025 */       throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { scoreobjective.getName(), s });
/*      */     }
/*      */ 
/*      */     
/* 1029 */     int i = p_175781_2_[p_175781_3_].equals("*") ? Integer.MIN_VALUE : parseInt(p_175781_2_[p_175781_3_]);
/* 1030 */     p_175781_3_++;
/* 1031 */     int j = (p_175781_3_ < p_175781_2_.length && !p_175781_2_[p_175781_3_].equals("*")) ? parseInt(p_175781_2_[p_175781_3_], i) : Integer.MAX_VALUE;
/* 1032 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*      */     
/* 1034 */     if (score.getScorePoints() >= i && score.getScorePoints() <= j) {
/*      */       
/* 1036 */       notifyOperators(p_175781_1_, (ICommand)this, "commands.scoreboard.players.test.success", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*      */     }
/*      */     else {
/*      */       
/* 1040 */       throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_175778_p(ICommandSender p_175778_1_, String[] p_175778_2_, int p_175778_3_) throws CommandException {
/* 1048 */     Scoreboard scoreboard = getScoreboard();
/* 1049 */     String s = getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
/* 1050 */     ScoreObjective scoreobjective = getObjective(p_175778_2_[p_175778_3_++], true);
/* 1051 */     String s1 = p_175778_2_[p_175778_3_++];
/* 1052 */     String s2 = getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
/* 1053 */     ScoreObjective scoreobjective1 = getObjective(p_175778_2_[p_175778_3_], false);
/*      */     
/* 1055 */     if (s.length() > 40)
/*      */     {
/* 1057 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/* 1059 */     if (s2.length() > 40)
/*      */     {
/* 1061 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s2, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1065 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*      */     
/* 1067 */     if (!scoreboard.entityHasObjective(s2, scoreobjective1))
/*      */     {
/* 1069 */       throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { scoreobjective1.getName(), s2 });
/*      */     }
/*      */ 
/*      */     
/* 1073 */     Score score1 = scoreboard.getValueFromObjective(s2, scoreobjective1);
/*      */     
/* 1075 */     if (s1.equals("+=")) {
/*      */       
/* 1077 */       score.setScorePoints(score.getScorePoints() + score1.getScorePoints());
/*      */     }
/* 1079 */     else if (s1.equals("-=")) {
/*      */       
/* 1081 */       score.setScorePoints(score.getScorePoints() - score1.getScorePoints());
/*      */     }
/* 1083 */     else if (s1.equals("*=")) {
/*      */       
/* 1085 */       score.setScorePoints(score.getScorePoints() * score1.getScorePoints());
/*      */     }
/* 1087 */     else if (s1.equals("/=")) {
/*      */       
/* 1089 */       if (score1.getScorePoints() != 0)
/*      */       {
/* 1091 */         score.setScorePoints(score.getScorePoints() / score1.getScorePoints());
/*      */       }
/*      */     }
/* 1094 */     else if (s1.equals("%=")) {
/*      */       
/* 1096 */       if (score1.getScorePoints() != 0)
/*      */       {
/* 1098 */         score.setScorePoints(score.getScorePoints() % score1.getScorePoints());
/*      */       }
/*      */     }
/* 1101 */     else if (s1.equals("=")) {
/*      */       
/* 1103 */       score.setScorePoints(score1.getScorePoints());
/*      */     }
/* 1105 */     else if (s1.equals("<")) {
/*      */       
/* 1107 */       score.setScorePoints(Math.min(score.getScorePoints(), score1.getScorePoints()));
/*      */     }
/* 1109 */     else if (s1.equals(">")) {
/*      */       
/* 1111 */       score.setScorePoints(Math.max(score.getScorePoints(), score1.getScorePoints()));
/*      */     }
/*      */     else {
/*      */       
/* 1115 */       if (!s1.equals("><"))
/*      */       {
/* 1117 */         throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s1 });
/*      */       }
/*      */       
/* 1120 */       int i = score.getScorePoints();
/* 1121 */       score.setScorePoints(score1.getScorePoints());
/* 1122 */       score1.setScorePoints(i);
/*      */     } 
/*      */     
/* 1125 */     notifyOperators(p_175778_1_, (ICommand)this, "commands.scoreboard.players.operation.success", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 1132 */     if (args.length == 1)
/*      */     {
/* 1134 */       return getListOfStringsMatchingLastWord(args, new String[] { "objectives", "players", "teams" });
/*      */     }
/*      */ 
/*      */     
/* 1138 */     if (args[0].equalsIgnoreCase("objectives")) {
/*      */       
/* 1140 */       if (args.length == 2)
/*      */       {
/* 1142 */         return getListOfStringsMatchingLastWord(args, new String[] { "list", "add", "remove", "setdisplay" });
/*      */       }
/*      */       
/* 1145 */       if (args[1].equalsIgnoreCase("add")) {
/*      */         
/* 1147 */         if (args.length == 4)
/*      */         {
/* 1149 */           Set<String> set = IScoreObjectiveCriteria.INSTANCES.keySet();
/* 1150 */           return getListOfStringsMatchingLastWord(args, set);
/*      */         }
/*      */       
/* 1153 */       } else if (args[1].equalsIgnoreCase("remove")) {
/*      */         
/* 1155 */         if (args.length == 3)
/*      */         {
/* 1157 */           return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */         }
/*      */       }
/* 1160 */       else if (args[1].equalsIgnoreCase("setdisplay")) {
/*      */         
/* 1162 */         if (args.length == 3)
/*      */         {
/* 1164 */           return getListOfStringsMatchingLastWord(args, Scoreboard.getDisplaySlotStrings());
/*      */         }
/*      */         
/* 1167 */         if (args.length == 4)
/*      */         {
/* 1169 */           return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */         }
/*      */       }
/*      */     
/* 1173 */     } else if (args[0].equalsIgnoreCase("players")) {
/*      */       
/* 1175 */       if (args.length == 2)
/*      */       {
/* 1177 */         return getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "remove", "reset", "list", "enable", "test", "operation" });
/*      */       }
/*      */       
/* 1180 */       if (!args[1].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("reset")) {
/*      */         
/* 1182 */         if (args[1].equalsIgnoreCase("enable")) {
/*      */           
/* 1184 */           if (args.length == 3)
/*      */           {
/* 1186 */             return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */           }
/*      */           
/* 1189 */           if (args.length == 4)
/*      */           {
/* 1191 */             return getListOfStringsMatchingLastWord(args, func_175782_e());
/*      */           }
/*      */         }
/* 1194 */         else if (!args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("test")) {
/*      */           
/* 1196 */           if (args[1].equalsIgnoreCase("operation"))
/*      */           {
/* 1198 */             if (args.length == 3)
/*      */             {
/* 1200 */               return getListOfStringsMatchingLastWord(args, getScoreboard().getObjectiveNames());
/*      */             }
/*      */             
/* 1203 */             if (args.length == 4)
/*      */             {
/* 1205 */               return getListOfStringsMatchingLastWord(args, func_147184_a(true));
/*      */             }
/*      */             
/* 1208 */             if (args.length == 5)
/*      */             {
/* 1210 */               return getListOfStringsMatchingLastWord(args, new String[] { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" });
/*      */             }
/*      */             
/* 1213 */             if (args.length == 6)
/*      */             {
/* 1215 */               return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */             }
/*      */             
/* 1218 */             if (args.length == 7)
/*      */             {
/* 1220 */               return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */             }
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1226 */           if (args.length == 3)
/*      */           {
/* 1228 */             return getListOfStringsMatchingLastWord(args, getScoreboard().getObjectiveNames());
/*      */           }
/*      */           
/* 1231 */           if (args.length == 4 && args[1].equalsIgnoreCase("test"))
/*      */           {
/* 1233 */             return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1239 */         if (args.length == 3)
/*      */         {
/* 1241 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */         }
/*      */         
/* 1244 */         if (args.length == 4)
/*      */         {
/* 1246 */           return getListOfStringsMatchingLastWord(args, func_147184_a(true));
/*      */         }
/*      */       }
/*      */     
/* 1250 */     } else if (args[0].equalsIgnoreCase("teams")) {
/*      */       
/* 1252 */       if (args.length == 2)
/*      */       {
/* 1254 */         return getListOfStringsMatchingLastWord(args, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/*      */       }
/*      */       
/* 1257 */       if (args[1].equalsIgnoreCase("join")) {
/*      */         
/* 1259 */         if (args.length == 3)
/*      */         {
/* 1261 */           return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*      */         }
/*      */         
/* 1264 */         if (args.length >= 4)
/*      */         {
/* 1266 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1271 */         if (args[1].equalsIgnoreCase("leave"))
/*      */         {
/* 1273 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */         }
/*      */         
/* 1276 */         if (!args[1].equalsIgnoreCase("empty") && !args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("remove")) {
/*      */           
/* 1278 */           if (args[1].equalsIgnoreCase("option")) {
/*      */             
/* 1280 */             if (args.length == 3)
/*      */             {
/* 1282 */               return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*      */             }
/*      */             
/* 1285 */             if (args.length == 4)
/*      */             {
/* 1287 */               return getListOfStringsMatchingLastWord(args, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" });
/*      */             }
/*      */             
/* 1290 */             if (args.length == 5)
/*      */             {
/* 1292 */               if (args[3].equalsIgnoreCase("color"))
/*      */               {
/* 1294 */                 return getListOfStringsMatchingLastWord(args, ChatFormatting.getValidValues(true, false));
/*      */               }
/*      */               
/* 1297 */               if (args[3].equalsIgnoreCase("nametagVisibility") || args[3].equalsIgnoreCase("deathMessageVisibility"))
/*      */               {
/* 1299 */                 return getListOfStringsMatchingLastWord(args, Team.EnumVisible.func_178825_a());
/*      */               }
/*      */               
/* 1302 */               if (args[3].equalsIgnoreCase("friendlyfire") || args[3].equalsIgnoreCase("seeFriendlyInvisibles"))
/*      */               {
/* 1304 */                 return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/* 1309 */         } else if (args.length == 3) {
/*      */           
/* 1311 */           return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1316 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<String> func_147184_a(boolean p_147184_1_) {
/* 1322 */     Collection<ScoreObjective> collection = getScoreboard().getScoreObjectives();
/* 1323 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1325 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/* 1327 */       if (!p_147184_1_ || !scoreobjective.getCriteria().isReadOnly())
/*      */       {
/* 1329 */         list.add(scoreobjective.getName());
/*      */       }
/*      */     } 
/*      */     
/* 1333 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<String> func_175782_e() {
/* 1338 */     Collection<ScoreObjective> collection = getScoreboard().getScoreObjectives();
/* 1339 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1341 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/* 1343 */       if (scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER)
/*      */       {
/* 1345 */         list.add(scoreobjective.getName());
/*      */       }
/*      */     } 
/*      */     
/* 1349 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsernameIndex(String[] args, int index) {
/* 1360 */     return !args[0].equalsIgnoreCase("players") ? (args[0].equalsIgnoreCase("teams") ? ((index == 2)) : false) : ((args.length > 1 && args[1].equalsIgnoreCase("operation")) ? ((index == 2 || index == 5)) : ((index == 2)));
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */