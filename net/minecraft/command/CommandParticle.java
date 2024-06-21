/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandParticle
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "particle";
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
/*  35 */     return "commands.particle.usage";
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
/*  46 */     if (args.length < 8)
/*     */     {
/*  48 */       throw new WrongUsageException("commands.particle.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  52 */     boolean flag = false;
/*  53 */     EnumParticleTypes enumparticletypes = null;
/*     */     
/*  55 */     for (EnumParticleTypes enumparticletypes1 : EnumParticleTypes.values()) {
/*     */       
/*  57 */       if (enumparticletypes1.hasArguments()) {
/*     */         
/*  59 */         if (args[0].startsWith(enumparticletypes1.getParticleName())) {
/*     */           
/*  61 */           flag = true;
/*  62 */           enumparticletypes = enumparticletypes1;
/*     */           
/*     */           break;
/*     */         } 
/*  66 */       } else if (args[0].equals(enumparticletypes1.getParticleName())) {
/*     */         
/*  68 */         flag = true;
/*  69 */         enumparticletypes = enumparticletypes1;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  74 */     if (!flag)
/*     */     {
/*  76 */       throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */     }
/*     */ 
/*     */     
/*  80 */     String s = args[0];
/*  81 */     Vec3 vec3 = sender.getPositionVector();
/*  82 */     double d6 = (float)parseDouble(vec3.xCoord, args[1], true);
/*  83 */     double d0 = (float)parseDouble(vec3.yCoord, args[2], true);
/*  84 */     double d1 = (float)parseDouble(vec3.zCoord, args[3], true);
/*  85 */     double d2 = (float)parseDouble(args[4]);
/*  86 */     double d3 = (float)parseDouble(args[5]);
/*  87 */     double d4 = (float)parseDouble(args[6]);
/*  88 */     double d5 = (float)parseDouble(args[7]);
/*  89 */     int i = 0;
/*     */     
/*  91 */     if (args.length > 8)
/*     */     {
/*  93 */       i = parseInt(args[8], 0);
/*     */     }
/*     */     
/*  96 */     boolean flag1 = false;
/*     */     
/*  98 */     if (args.length > 9 && "force".equals(args[9]))
/*     */     {
/* 100 */       flag1 = true;
/*     */     }
/*     */     
/* 103 */     World world = sender.getEntityWorld();
/*     */     
/* 105 */     if (world instanceof WorldServer) {
/*     */       
/* 107 */       WorldServer worldserver = (WorldServer)world;
/* 108 */       int[] aint = new int[enumparticletypes.getArgumentCount()];
/*     */       
/* 110 */       if (enumparticletypes.hasArguments()) {
/*     */         
/* 112 */         String[] astring = args[0].split("_", 3);
/*     */         
/* 114 */         for (int j = 1; j < astring.length; j++) {
/*     */ 
/*     */           
/*     */           try {
/* 118 */             aint[j - 1] = Integer.parseInt(astring[j]);
/*     */           }
/* 120 */           catch (NumberFormatException var29) {
/*     */             
/* 122 */             throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 127 */       worldserver.spawnParticle(enumparticletypes, flag1, d6, d0, d1, i, d2, d3, d4, d5, aint);
/* 128 */       notifyOperators(sender, this, "commands.particle.success", new Object[] { s, Integer.valueOf(Math.max(i, 1)) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 136 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, EnumParticleTypes.getParticleNames()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force" }) : null));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */