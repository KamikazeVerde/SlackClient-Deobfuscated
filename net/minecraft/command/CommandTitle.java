/*     */ package net.minecraft.command;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S45PacketTitle;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandTitle extends CommandBase {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  24 */     return "title";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  32 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  42 */     return "commands.title.usage";
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
/*  53 */     if (args.length < 2)
/*     */     {
/*  55 */       throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  59 */     if (args.length < 3) {
/*     */       
/*  61 */       if ("title".equals(args[1]) || "subtitle".equals(args[1]))
/*     */       {
/*  63 */         throw new WrongUsageException("commands.title.usage.title", new Object[0]);
/*     */       }
/*     */       
/*  66 */       if ("times".equals(args[1]))
/*     */       {
/*  68 */         throw new WrongUsageException("commands.title.usage.times", new Object[0]);
/*     */       }
/*     */     } 
/*     */     
/*  72 */     EntityPlayerMP entityplayermp = getPlayer(sender, args[0]);
/*  73 */     S45PacketTitle.Type s45packettitle$type = S45PacketTitle.Type.byName(args[1]);
/*     */     
/*  75 */     if (s45packettitle$type != S45PacketTitle.Type.CLEAR && s45packettitle$type != S45PacketTitle.Type.RESET) {
/*     */       
/*  77 */       if (s45packettitle$type == S45PacketTitle.Type.TIMES) {
/*     */         
/*  79 */         if (args.length != 5)
/*     */         {
/*  81 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/*  85 */         int i = parseInt(args[2]);
/*  86 */         int j = parseInt(args[3]);
/*  87 */         int k = parseInt(args[4]);
/*  88 */         S45PacketTitle s45packettitle2 = new S45PacketTitle(i, j, k);
/*  89 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle2);
/*  90 */         notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */       } else {
/*     */         IChatComponent ichatcomponent;
/*  93 */         if (args.length < 3)
/*     */         {
/*  95 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/*  99 */         String s = buildString(args, 2);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 104 */           ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/*     */         }
/* 106 */         catch (JsonParseException jsonparseexception) {
/*     */           
/* 108 */           Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/* 109 */           throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*     */         } 
/*     */         
/* 112 */         S45PacketTitle s45packettitle1 = new S45PacketTitle(s45packettitle$type, ChatComponentProcessor.processComponent(sender, ichatcomponent, (Entity)entityplayermp));
/* 113 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle1);
/* 114 */         notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */       } 
/*     */     } else {
/* 117 */       if (args.length != 2)
/*     */       {
/* 119 */         throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 123 */       S45PacketTitle s45packettitle = new S45PacketTitle(s45packettitle$type, null);
/* 124 */       entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle);
/* 125 */       notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 132 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, S45PacketTitle.Type.getNames()) : null);
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
/* 143 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */