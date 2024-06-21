/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.GameRules;
/*     */ 
/*     */ public class CommandGameRule
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "gamerule";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  26 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.gamerule.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     String s2;
/*  47 */     GameRules gamerules = getGameRules();
/*  48 */     String s = (args.length > 0) ? args[0] : "";
/*  49 */     String s1 = (args.length > 1) ? buildString(args, 1) : "";
/*     */     
/*  51 */     switch (args.length) {
/*     */       
/*     */       case 0:
/*  54 */         sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])gamerules.getRules())));
/*     */         return;
/*     */       
/*     */       case 1:
/*  58 */         if (!gamerules.hasRule(s))
/*     */         {
/*  60 */           throw new CommandException("commands.gamerule.norule", new Object[] { s });
/*     */         }
/*     */         
/*  63 */         s2 = gamerules.getGameRuleStringValue(s);
/*  64 */         sender.addChatMessage((new ChatComponentText(s)).appendText(" = ").appendText(s2));
/*  65 */         sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gamerules.getInt(s));
/*     */         return;
/*     */     } 
/*     */     
/*  69 */     if (gamerules.areSameType(s, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s1) && !"false".equals(s1))
/*     */     {
/*  71 */       throw new CommandException("commands.generic.boolean.invalid", new Object[] { s1 });
/*     */     }
/*     */     
/*  74 */     gamerules.setOrCreateGameRule(s, s1);
/*  75 */     func_175773_a(gamerules, s);
/*  76 */     notifyOperators(sender, this, "commands.gamerule.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void func_175773_a(GameRules p_175773_0_, String p_175773_1_) {
/*  82 */     if ("reducedDebugInfo".equals(p_175773_1_)) {
/*     */       
/*  84 */       byte b0 = (byte)(p_175773_0_.getGameRuleBooleanValue(p_175773_1_) ? 22 : 23);
/*     */       
/*  86 */       for (EntityPlayerMP entityplayermp : MinecraftServer.getServer().getConfigurationManager().func_181057_v())
/*     */       {
/*  88 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)new S19PacketEntityStatus((Entity)entityplayermp, b0));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  95 */     if (args.length == 1)
/*     */     {
/*  97 */       return getListOfStringsMatchingLastWord(args, getGameRules().getRules());
/*     */     }
/*     */ 
/*     */     
/* 101 */     if (args.length == 2) {
/*     */       
/* 103 */       GameRules gamerules = getGameRules();
/*     */       
/* 105 */       if (gamerules.areSameType(args[0], GameRules.ValueType.BOOLEAN_VALUE))
/*     */       {
/* 107 */         return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GameRules getGameRules() {
/* 120 */     return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandGameRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */