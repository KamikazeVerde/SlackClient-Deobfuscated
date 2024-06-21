/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandPlaySound
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "playsound";
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
/*  35 */     return "commands.playsound.usage";
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
/*  46 */     if (args.length < 2)
/*     */     {
/*  48 */       throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  52 */     int i = 0;
/*  53 */     String s = args[i++];
/*  54 */     EntityPlayerMP entityplayermp = getPlayer(sender, args[i++]);
/*  55 */     Vec3 vec3 = sender.getPositionVector();
/*  56 */     double d0 = vec3.xCoord;
/*     */     
/*  58 */     if (args.length > i)
/*     */     {
/*  60 */       d0 = parseDouble(d0, args[i++], true);
/*     */     }
/*     */     
/*  63 */     double d1 = vec3.yCoord;
/*     */     
/*  65 */     if (args.length > i)
/*     */     {
/*  67 */       d1 = parseDouble(d1, args[i++], 0, 0, false);
/*     */     }
/*     */     
/*  70 */     double d2 = vec3.zCoord;
/*     */     
/*  72 */     if (args.length > i)
/*     */     {
/*  74 */       d2 = parseDouble(d2, args[i++], true);
/*     */     }
/*     */     
/*  77 */     double d3 = 1.0D;
/*     */     
/*  79 */     if (args.length > i)
/*     */     {
/*  81 */       d3 = parseDouble(args[i++], 0.0D, 3.4028234663852886E38D);
/*     */     }
/*     */     
/*  84 */     double d4 = 1.0D;
/*     */     
/*  86 */     if (args.length > i)
/*     */     {
/*  88 */       d4 = parseDouble(args[i++], 0.0D, 2.0D);
/*     */     }
/*     */     
/*  91 */     double d5 = 0.0D;
/*     */     
/*  93 */     if (args.length > i)
/*     */     {
/*  95 */       d5 = parseDouble(args[i], 0.0D, 1.0D);
/*     */     }
/*     */     
/*  98 */     double d6 = (d3 > 1.0D) ? (d3 * 16.0D) : 16.0D;
/*  99 */     double d7 = entityplayermp.getDistance(d0, d1, d2);
/*     */     
/* 101 */     if (d7 > d6) {
/*     */       
/* 103 */       if (d5 <= 0.0D)
/*     */       {
/* 105 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { entityplayermp.getCommandSenderName() });
/*     */       }
/*     */       
/* 108 */       double d8 = d0 - entityplayermp.posX;
/* 109 */       double d9 = d1 - entityplayermp.posY;
/* 110 */       double d10 = d2 - entityplayermp.posZ;
/* 111 */       double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
/*     */       
/* 113 */       if (d11 > 0.0D) {
/*     */         
/* 115 */         d0 = entityplayermp.posX + d8 / d11 * 2.0D;
/* 116 */         d1 = entityplayermp.posY + d9 / d11 * 2.0D;
/* 117 */         d2 = entityplayermp.posZ + d10 / d11 * 2.0D;
/*     */       } 
/*     */       
/* 120 */       d3 = d5;
/*     */     } 
/*     */     
/* 123 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S29PacketSoundEffect(s, d0, d1, d2, (float)d3, (float)d4));
/* 124 */     notifyOperators(sender, this, "commands.playsound.success", new Object[] { s, entityplayermp.getCommandSenderName() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 130 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length > 2 && args.length <= 5) ? func_175771_a(args, 2, pos) : null);
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
/* 141 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandPlaySound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */