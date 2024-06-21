/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandEntityData
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "entitydata";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 34 */     return "commands.entitydata.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*    */     NBTTagCompound nbttagcompound2;
/* 45 */     if (args.length < 2)
/*    */     {
/* 47 */       throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 51 */     Entity entity = func_175768_b(sender, args[0]);
/*    */     
/* 53 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer)
/*    */     {
/* 55 */       throw new CommandException("commands.entitydata.noPlayers", new Object[] { entity.getDisplayName() });
/*    */     }
/*    */ 
/*    */     
/* 59 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 60 */     entity.writeToNBT(nbttagcompound);
/* 61 */     NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 66 */       nbttagcompound2 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
/*    */     }
/* 68 */     catch (NBTException nbtexception) {
/*    */       
/* 70 */       throw new CommandException("commands.entitydata.tagError", new Object[] { nbtexception.getMessage() });
/*    */     } 
/*    */     
/* 73 */     nbttagcompound2.removeTag("UUIDMost");
/* 74 */     nbttagcompound2.removeTag("UUIDLeast");
/* 75 */     nbttagcompound.merge(nbttagcompound2);
/*    */     
/* 77 */     if (nbttagcompound.equals(nbttagcompound1))
/*    */     {
/* 79 */       throw new CommandException("commands.entitydata.failed", new Object[] { nbttagcompound.toString() });
/*    */     }
/*    */ 
/*    */     
/* 83 */     entity.readFromNBT(nbttagcompound);
/* 84 */     notifyOperators(sender, this, "commands.entitydata.success", new Object[] { nbttagcompound.toString() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 98 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandEntityData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */