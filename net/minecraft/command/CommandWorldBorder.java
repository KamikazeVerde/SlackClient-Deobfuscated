/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandWorldBorder
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "worldborder";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  25 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.worldborder.usage";
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
/*  46 */     if (args.length < 1)
/*     */     {
/*  48 */       throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  52 */     WorldBorder worldborder = getWorldBorder();
/*     */     
/*  54 */     if (args[0].equals("set")) {
/*     */       
/*  56 */       if (args.length != 2 && args.length != 3)
/*     */       {
/*  58 */         throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
/*     */       }
/*     */       
/*  61 */       double d0 = worldborder.getTargetSize();
/*  62 */       double d2 = parseDouble(args[1], 1.0D, 6.0E7D);
/*  63 */       long i = (args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L;
/*     */       
/*  65 */       if (i > 0L) {
/*     */         
/*  67 */         worldborder.setTransition(d0, d2, i);
/*     */         
/*  69 */         if (d0 > d2)
/*     */         {
/*  71 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         }
/*     */         else
/*     */         {
/*  75 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  80 */         worldborder.setTransition(d2);
/*  81 */         notifyOperators(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }) });
/*     */       }
/*     */     
/*  84 */     } else if (args[0].equals("add")) {
/*     */       
/*  86 */       if (args.length != 2 && args.length != 3)
/*     */       {
/*  88 */         throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  91 */       double d4 = worldborder.getDiameter();
/*  92 */       double d8 = d4 + parseDouble(args[1], -d4, 6.0E7D - d4);
/*  93 */       long i1 = worldborder.getTimeUntilTarget() + ((args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L);
/*     */       
/*  95 */       if (i1 > 0L) {
/*     */         
/*  97 */         worldborder.setTransition(d4, d8, i1);
/*     */         
/*  99 */         if (d4 > d8)
/*     */         {
/* 101 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(i1 / 1000L) });
/*     */         }
/*     */         else
/*     */         {
/* 105 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(i1 / 1000L) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 110 */         worldborder.setTransition(d8);
/* 111 */         notifyOperators(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }) });
/*     */       }
/*     */     
/* 114 */     } else if (args[0].equals("center")) {
/*     */       
/* 116 */       if (args.length != 3)
/*     */       {
/* 118 */         throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
/*     */       }
/*     */       
/* 121 */       BlockPos blockpos = sender.getPosition();
/* 122 */       double d1 = parseDouble(blockpos.getX() + 0.5D, args[1], true);
/* 123 */       double d3 = parseDouble(blockpos.getZ() + 0.5D, args[2], true);
/* 124 */       worldborder.setCenter(d1, d3);
/* 125 */       notifyOperators(sender, this, "commands.worldborder.center.success", new Object[] { Double.valueOf(d1), Double.valueOf(d3) });
/*     */     }
/* 127 */     else if (args[0].equals("damage")) {
/*     */       
/* 129 */       if (args.length < 2)
/*     */       {
/* 131 */         throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
/*     */       }
/*     */       
/* 134 */       if (args[1].equals("buffer"))
/*     */       {
/* 136 */         if (args.length != 3)
/*     */         {
/* 138 */           throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
/*     */         }
/*     */         
/* 141 */         double d5 = parseDouble(args[2], 0.0D);
/* 142 */         double d9 = worldborder.getDamageBuffer();
/* 143 */         worldborder.setDamageBuffer(d5);
/* 144 */         notifyOperators(sender, this, "commands.worldborder.damage.buffer.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d5) }), String.format("%.1f", new Object[] { Double.valueOf(d9) }) });
/*     */       }
/* 146 */       else if (args[1].equals("amount"))
/*     */       {
/* 148 */         if (args.length != 3)
/*     */         {
/* 150 */           throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
/*     */         }
/*     */         
/* 153 */         double d6 = parseDouble(args[2], 0.0D);
/* 154 */         double d10 = worldborder.getDamageAmount();
/* 155 */         worldborder.setDamageAmount(d6);
/* 156 */         notifyOperators(sender, this, "commands.worldborder.damage.amount.success", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d6) }), String.format("%.2f", new Object[] { Double.valueOf(d10) }) });
/*     */       }
/*     */     
/* 159 */     } else if (args[0].equals("warning")) {
/*     */       
/* 161 */       if (args.length < 2)
/*     */       {
/* 163 */         throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
/*     */       }
/*     */       
/* 166 */       int j = parseInt(args[2], 0);
/*     */       
/* 168 */       if (args[1].equals("time"))
/*     */       {
/* 170 */         if (args.length != 3)
/*     */         {
/* 172 */           throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
/*     */         }
/*     */         
/* 175 */         int k = worldborder.getWarningTime();
/* 176 */         worldborder.setWarningTime(j);
/* 177 */         notifyOperators(sender, this, "commands.worldborder.warning.time.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k) });
/*     */       }
/* 179 */       else if (args[1].equals("distance"))
/*     */       {
/* 181 */         if (args.length != 3)
/*     */         {
/* 183 */           throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
/*     */         }
/*     */         
/* 186 */         int l = worldborder.getWarningDistance();
/* 187 */         worldborder.setWarningDistance(j);
/* 188 */         notifyOperators(sender, this, "commands.worldborder.warning.distance.success", new Object[] { Integer.valueOf(j), Integer.valueOf(l) });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 193 */       if (!args[0].equals("get"))
/*     */       {
/* 195 */         throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */       }
/*     */       
/* 198 */       double d7 = worldborder.getDiameter();
/* 199 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor_double(d7 + 0.5D));
/* 200 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.worldborder.get.success", new Object[] { String.format("%.0f", new Object[] { Double.valueOf(d7) }) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldBorder getWorldBorder() {
/* 207 */     return (MinecraftServer.getServer()).worldServers[0].getWorldBorder();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 212 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "set", "center", "damage", "warning", "add", "get" }) : ((args.length == 2 && args[0].equals("damage")) ? getListOfStringsMatchingLastWord(args, new String[] { "buffer", "amount" }) : ((args.length >= 2 && args.length <= 3 && args[0].equals("center")) ? func_181043_b(args, 1, pos) : ((args.length == 2 && args[0].equals("warning")) ? getListOfStringsMatchingLastWord(args, new String[] { "time", "distance" }) : null)));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandWorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */