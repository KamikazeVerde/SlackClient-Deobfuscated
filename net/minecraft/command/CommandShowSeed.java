/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandShowSeed
/*    */   extends CommandBase
/*    */ {
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 17 */     return (MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandName() {
/* 25 */     return "seed";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 33 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 43 */     return "commands.seed.usage";
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
/* 54 */     World world = (sender instanceof EntityPlayer) ? ((EntityPlayer)sender).worldObj : (World)MinecraftServer.getServer().worldServerForDimension(0);
/* 55 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.seed.success", new Object[] { Long.valueOf(world.getSeed()) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandShowSeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */