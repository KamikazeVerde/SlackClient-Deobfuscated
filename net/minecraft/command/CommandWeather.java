/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandWeather
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 17 */     return "weather";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 25 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 35 */     return "commands.weather.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 46 */     if (args.length >= 1 && args.length <= 2) {
/*    */       
/* 48 */       int i = (300 + (new Random()).nextInt(600)) * 20;
/*    */       
/* 50 */       if (args.length >= 2)
/*    */       {
/* 52 */         i = parseInt(args[1], 1, 1000000) * 20;
/*    */       }
/*    */       
/* 55 */       WorldServer worldServer = (MinecraftServer.getServer()).worldServers[0];
/* 56 */       WorldInfo worldinfo = worldServer.getWorldInfo();
/*    */       
/* 58 */       if ("clear".equalsIgnoreCase(args[0]))
/*    */       {
/* 60 */         worldinfo.setCleanWeatherTime(i);
/* 61 */         worldinfo.setRainTime(0);
/* 62 */         worldinfo.setThunderTime(0);
/* 63 */         worldinfo.setRaining(false);
/* 64 */         worldinfo.setThundering(false);
/* 65 */         notifyOperators(sender, this, "commands.weather.clear", new Object[0]);
/*    */       }
/* 67 */       else if ("rain".equalsIgnoreCase(args[0]))
/*    */       {
/* 69 */         worldinfo.setCleanWeatherTime(0);
/* 70 */         worldinfo.setRainTime(i);
/* 71 */         worldinfo.setThunderTime(i);
/* 72 */         worldinfo.setRaining(true);
/* 73 */         worldinfo.setThundering(false);
/* 74 */         notifyOperators(sender, this, "commands.weather.rain", new Object[0]);
/*    */       }
/*    */       else
/*    */       {
/* 78 */         if (!"thunder".equalsIgnoreCase(args[0]))
/*    */         {
/* 80 */           throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */         }
/*    */         
/* 83 */         worldinfo.setCleanWeatherTime(0);
/* 84 */         worldinfo.setRainTime(i);
/* 85 */         worldinfo.setThunderTime(i);
/* 86 */         worldinfo.setRaining(true);
/* 87 */         worldinfo.setThundering(true);
/* 88 */         notifyOperators(sender, this, "commands.weather.thunder", new Object[0]);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 93 */       throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 99 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "clear", "rain", "thunder" }) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandWeather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */