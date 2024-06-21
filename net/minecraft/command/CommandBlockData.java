/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandBlockData
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "blockdata";
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
/*  36 */     return "commands.blockdata.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     NBTTagCompound nbttagcompound2;
/*  47 */     if (args.length < 4)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  54 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  55 */     World world = sender.getEntityWorld();
/*     */     
/*  57 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  59 */       throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  63 */     TileEntity tileentity = world.getTileEntity(blockpos);
/*     */     
/*  65 */     if (tileentity == null)
/*     */     {
/*  67 */       throw new CommandException("commands.blockdata.notValid", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  71 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  72 */     tileentity.writeToNBT(nbttagcompound);
/*  73 */     NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  78 */       nbttagcompound2 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(sender, args, 3).getUnformattedText());
/*     */     }
/*  80 */     catch (NBTException nbtexception) {
/*     */       
/*  82 */       throw new CommandException("commands.blockdata.tagError", new Object[] { nbtexception.getMessage() });
/*     */     } 
/*     */     
/*  85 */     nbttagcompound.merge(nbttagcompound2);
/*  86 */     nbttagcompound.setInteger("x", blockpos.getX());
/*  87 */     nbttagcompound.setInteger("y", blockpos.getY());
/*  88 */     nbttagcompound.setInteger("z", blockpos.getZ());
/*     */     
/*  90 */     if (nbttagcompound.equals(nbttagcompound1))
/*     */     {
/*  92 */       throw new CommandException("commands.blockdata.failed", new Object[] { nbttagcompound.toString() });
/*     */     }
/*     */ 
/*     */     
/*  96 */     tileentity.readFromNBT(nbttagcompound);
/*  97 */     tileentity.markDirty();
/*  98 */     world.markBlockForUpdate(blockpos);
/*  99 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 100 */     notifyOperators(sender, this, "commands.blockdata.success", new Object[] { nbttagcompound.toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 109 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandBlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */