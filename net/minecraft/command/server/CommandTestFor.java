/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ public class CommandTestFor
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "testfor";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  41 */     return "commands.testfor.usage";
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
/*  52 */     if (args.length < 1)
/*     */     {
/*  54 */       throw new WrongUsageException("commands.testfor.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  58 */     Entity entity = func_175768_b(sender, args[0]);
/*  59 */     NBTTagCompound nbttagcompound = null;
/*     */     
/*  61 */     if (args.length >= 2) {
/*     */       
/*     */       try {
/*     */         
/*  65 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 1));
/*     */       }
/*  67 */       catch (NBTException nbtexception) {
/*     */         
/*  69 */         throw new CommandException("commands.testfor.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     }
/*     */     
/*  73 */     if (nbttagcompound != null) {
/*     */       
/*  75 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  76 */       entity.writeToNBT(nbttagcompound1);
/*     */       
/*  78 */       if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*     */       {
/*  80 */         throw new CommandException("commands.testfor.failure", new Object[] { entity.getCommandSenderName() });
/*     */       }
/*     */     } 
/*     */     
/*  84 */     notifyOperators(sender, (ICommand)this, "commands.testfor.success", new Object[] { entity.getCommandSenderName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/*  96 */     return (index == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 101 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandTestFor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */