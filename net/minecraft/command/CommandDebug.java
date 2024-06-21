/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandDebug
/*     */   extends CommandBase {
/*  16 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private long field_147206_b;
/*     */   
/*     */   private int field_147207_c;
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  25 */     return "debug";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  33 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  43 */     return "commands.debug.usage";
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
/*  54 */     if (args.length < 1)
/*     */     {
/*  56 */       throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (args[0].equals("start")) {
/*     */       
/*  62 */       if (args.length != 1)
/*     */       {
/*  64 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  67 */       notifyOperators(sender, this, "commands.debug.start", new Object[0]);
/*  68 */       MinecraftServer.getServer().enableProfiling();
/*  69 */       this.field_147206_b = MinecraftServer.getCurrentTimeMillis();
/*  70 */       this.field_147207_c = MinecraftServer.getServer().getTickCounter();
/*     */     }
/*     */     else {
/*     */       
/*  74 */       if (!args[0].equals("stop"))
/*     */       {
/*  76 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  79 */       if (args.length != 1)
/*     */       {
/*  81 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  84 */       if (!(MinecraftServer.getServer()).theProfiler.profilingEnabled)
/*     */       {
/*  86 */         throw new CommandException("commands.debug.notStarted", new Object[0]);
/*     */       }
/*     */       
/*  89 */       long i = MinecraftServer.getCurrentTimeMillis();
/*  90 */       int j = MinecraftServer.getServer().getTickCounter();
/*  91 */       long k = i - this.field_147206_b;
/*  92 */       int l = j - this.field_147207_c;
/*  93 */       func_147205_a(k, l);
/*  94 */       (MinecraftServer.getServer()).theProfiler.profilingEnabled = false;
/*  95 */       notifyOperators(sender, this, "commands.debug.stop", new Object[] { Float.valueOf((float)k / 1000.0F), Integer.valueOf(l) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_147205_a(long p_147205_1_, int p_147205_3_) {
/* 102 */     File file1 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
/* 103 */     file1.getParentFile().mkdirs();
/*     */ 
/*     */     
/*     */     try {
/* 107 */       FileWriter filewriter = new FileWriter(file1);
/* 108 */       filewriter.write(func_147204_b(p_147205_1_, p_147205_3_));
/* 109 */       filewriter.close();
/*     */     }
/* 111 */     catch (Throwable throwable) {
/*     */       
/* 113 */       logger.error("Could not save profiler results to " + file1, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String func_147204_b(long p_147204_1_, int p_147204_3_) {
/* 119 */     StringBuilder stringbuilder = new StringBuilder();
/* 120 */     stringbuilder.append("---- Minecraft Profiler Results ----\n");
/* 121 */     stringbuilder.append("// ");
/* 122 */     stringbuilder.append(func_147203_d());
/* 123 */     stringbuilder.append("\n\n");
/* 124 */     stringbuilder.append("Time span: ").append(p_147204_1_).append(" ms\n");
/* 125 */     stringbuilder.append("Tick span: ").append(p_147204_3_).append(" ticks\n");
/* 126 */     stringbuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(p_147204_3_ / (float)p_147204_1_ / 1000.0F) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/* 127 */     stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
/* 128 */     func_147202_a(0, "root", stringbuilder);
/* 129 */     stringbuilder.append("--- END PROFILE DUMP ---\n\n");
/* 130 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_147202_a(int p_147202_1_, String p_147202_2_, StringBuilder p_147202_3_) {
/* 135 */     List<Profiler.Result> list = (MinecraftServer.getServer()).theProfiler.getProfilingData(p_147202_2_);
/*     */     
/* 137 */     if (list != null && list.size() >= 3)
/*     */     {
/* 139 */       for (int i = 1; i < list.size(); i++) {
/*     */         
/* 141 */         Profiler.Result profiler$result = list.get(i);
/* 142 */         p_147202_3_.append(String.format("[%02d] ", new Object[] { Integer.valueOf(p_147202_1_) }));
/*     */         
/* 144 */         for (int j = 0; j < p_147202_1_; j++)
/*     */         {
/* 146 */           p_147202_3_.append(" ");
/*     */         }
/*     */         
/* 149 */         p_147202_3_.append(profiler$result.field_76331_c).append(" - ").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.field_76332_a) })).append("%/").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.field_76330_b) })).append("%\n");
/*     */         
/* 151 */         if (!profiler$result.field_76331_c.equals("unspecified")) {
/*     */           
/*     */           try {
/*     */             
/* 155 */             func_147202_a(p_147202_1_ + 1, p_147202_2_ + "." + profiler$result.field_76331_c, p_147202_3_);
/*     */           }
/* 157 */           catch (Exception exception) {
/*     */             
/* 159 */             p_147202_3_.append("[[ EXCEPTION ").append(exception).append(" ]]");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_147203_d() {
/* 168 */     String[] astring = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
/*     */ 
/*     */     
/*     */     try {
/* 172 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 174 */     catch (Throwable var2) {
/*     */       
/* 176 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 182 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "start", "stop" }) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */