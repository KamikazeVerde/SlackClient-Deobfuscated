/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandStats
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "stats";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  30 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  40 */     return "commands.stats.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     CommandResultStats commandresultstats;
/*  51 */     if (args.length < 1)
/*     */     {
/*  53 */       throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (args[0].equals("entity")) {
/*     */       
/*  61 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  65 */       if (!args[0].equals("block"))
/*     */       {
/*  67 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/*  70 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (flag) {
/*     */       
/*  77 */       if (args.length < 5)
/*     */       {
/*  79 */         throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  82 */       i = 4;
/*     */     }
/*     */     else {
/*     */       
/*  86 */       if (args.length < 3)
/*     */       {
/*  88 */         throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  91 */       i = 2;
/*     */     } 
/*     */     
/*  94 */     String s = args[i++];
/*     */     
/*  96 */     if ("set".equals(s)) {
/*     */       
/*  98 */       if (args.length < i + 3)
/*     */       {
/* 100 */         if (i == 5)
/*     */         {
/* 102 */           throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
/*     */         }
/*     */         
/* 105 */         throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 110 */       if (!"clear".equals(s))
/*     */       {
/* 112 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/* 115 */       if (args.length < i + 1) {
/*     */         
/* 117 */         if (i == 5)
/*     */         {
/* 119 */           throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
/*     */         }
/*     */         
/* 122 */         throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     CommandResultStats.Type commandresultstats$type = CommandResultStats.Type.getTypeByName(args[i++]);
/*     */     
/* 128 */     if (commandresultstats$type == null)
/*     */     {
/* 130 */       throw new CommandException("commands.stats.failed", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 134 */     World world = sender.getEntityWorld();
/*     */ 
/*     */     
/* 137 */     if (flag) {
/*     */       
/* 139 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 140 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 142 */       if (tileentity == null)
/*     */       {
/* 144 */         throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 147 */       if (tileentity instanceof TileEntityCommandBlock)
/*     */       {
/* 149 */         commandresultstats = ((TileEntityCommandBlock)tileentity).getCommandResultStats();
/*     */       }
/*     */       else
/*     */       {
/* 153 */         if (!(tileentity instanceof TileEntitySign))
/*     */         {
/* 155 */           throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */         }
/*     */         
/* 158 */         commandresultstats = ((TileEntitySign)tileentity).getStats();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 163 */       Entity entity = func_175768_b(sender, args[1]);
/* 164 */       commandresultstats = entity.getCommandStats();
/*     */     } 
/*     */     
/* 167 */     if ("set".equals(s)) {
/*     */       
/* 169 */       String s1 = args[i++];
/* 170 */       String s2 = args[i];
/*     */       
/* 172 */       if (s1.length() == 0 || s2.length() == 0)
/*     */       {
/* 174 */         throw new CommandException("commands.stats.failed", new Object[0]);
/*     */       }
/*     */       
/* 177 */       CommandResultStats.func_179667_a(commandresultstats, commandresultstats$type, s1, s2);
/* 178 */       notifyOperators(sender, this, "commands.stats.success", new Object[] { commandresultstats$type.getTypeName(), s2, s1 });
/*     */     }
/* 180 */     else if ("clear".equals(s)) {
/*     */       
/* 182 */       CommandResultStats.func_179667_a(commandresultstats, commandresultstats$type, null, null);
/* 183 */       notifyOperators(sender, this, "commands.stats.cleared", new Object[] { commandresultstats$type.getTypeName() });
/*     */     } 
/*     */     
/* 186 */     if (flag) {
/*     */       
/* 188 */       BlockPos blockpos1 = parseBlockPos(sender, args, 1, false);
/* 189 */       TileEntity tileentity1 = world.getTileEntity(blockpos1);
/* 190 */       tileentity1.markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 198 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" }) : ((args.length == 2 && args[0].equals("entity")) ? getListOfStringsMatchingLastWord(args, func_175776_d()) : ((args.length >= 2 && args.length <= 4 && args[0].equals("block")) ? func_175771_a(args, 1, pos) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? (((args.length != 6 || !args[0].equals("entity")) && (args.length != 8 || !args[0].equals("block"))) ? null : getListOfStringsMatchingLastWord(args, func_175777_e())) : getListOfStringsMatchingLastWord(args, CommandResultStats.Type.getTypeNames())) : getListOfStringsMatchingLastWord(args, new String[] { "set", "clear" }))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] func_175776_d() {
/* 203 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> func_175777_e() {
/* 208 */     Collection<ScoreObjective> collection = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
/* 209 */     List<String> list = Lists.newArrayList();
/*     */     
/* 211 */     for (ScoreObjective scoreobjective : collection) {
/*     */       
/* 213 */       if (!scoreobjective.getCriteria().isReadOnly())
/*     */       {
/* 215 */         list.add(scoreobjective.getName());
/*     */       }
/*     */     } 
/*     */     
/* 219 */     return list;
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
/* 230 */     return (args.length > 0 && args[0].equals("entity") && index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */