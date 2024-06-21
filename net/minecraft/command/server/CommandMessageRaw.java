/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.SyntaxErrorException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentProcessor;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandMessageRaw
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 24 */     return "tellraw";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 32 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 42 */     return "commands.tellraw.usage";
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
/* 53 */     if (args.length < 2)
/*    */     {
/* 55 */       throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 59 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/* 60 */     String s = buildString(args, 1);
/*    */ 
/*    */     
/*    */     try {
/* 64 */       IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 65 */       entityPlayerMP.addChatMessage(ChatComponentProcessor.processComponent(sender, ichatcomponent, (Entity)entityPlayerMP));
/*    */     }
/* 67 */     catch (JsonParseException jsonparseexception) {
/*    */       
/* 69 */       Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/* 70 */       throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 77 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 88 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandMessageRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */