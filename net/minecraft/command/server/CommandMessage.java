/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerNotFoundException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.ChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class CommandMessage
/*    */   extends CommandBase
/*    */ {
/*    */   public List<String> getCommandAliases() {
/* 21 */     return Arrays.asList(new String[] { "w", "msg" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandName() {
/* 29 */     return "tell";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 37 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 47 */     return "commands.message.usage";
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
/* 58 */     if (args.length < 2)
/*    */     {
/* 60 */       throw new WrongUsageException("commands.message.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 64 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*    */     
/* 66 */     if (entityPlayerMP == sender)
/*    */     {
/* 68 */       throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 72 */     IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 1, !(sender instanceof net.minecraft.entity.player.EntityPlayer));
/* 73 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { sender.getDisplayName(), ichatcomponent.createCopy() });
/* 74 */     ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { entityPlayerMP.getDisplayName(), ichatcomponent.createCopy() });
/* 75 */     chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.GRAY).setItalic(Boolean.TRUE);
/* 76 */     chatcomponenttranslation1.getChatStyle().setColor(ChatFormatting.GRAY).setItalic(Boolean.TRUE);
/* 77 */     entityPlayerMP.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 78 */     sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 85 */     return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
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
/* 96 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */