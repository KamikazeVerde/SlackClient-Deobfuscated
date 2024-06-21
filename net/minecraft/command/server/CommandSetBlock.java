/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSetBlock
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  27 */     return "setblock";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  35 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  45 */     return "commands.setblock.usage";
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
/*  56 */     if (args.length < 4)
/*     */     {
/*  58 */       throw new WrongUsageException("commands.setblock.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  62 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  63 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  64 */     Block block = CommandBase.getBlockByText(sender, args[3]);
/*  65 */     int i = 0;
/*     */     
/*  67 */     if (args.length >= 5)
/*     */     {
/*  69 */       i = parseInt(args[4], 0, 15);
/*     */     }
/*     */     
/*  72 */     World world = sender.getEntityWorld();
/*     */     
/*  74 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  76 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  80 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  81 */     boolean flag = false;
/*     */     
/*  83 */     if (args.length >= 7 && block.hasTileEntity()) {
/*     */       
/*  85 */       String s = getChatComponentFromNthArg(sender, args, 6).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/*  89 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  90 */         flag = true;
/*     */       }
/*  92 */       catch (NBTException nbtexception) {
/*     */         
/*  94 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     if (args.length >= 6)
/*     */     {
/* 100 */       if (args[5].equals("destroy")) {
/*     */         
/* 102 */         world.destroyBlock(blockpos, true);
/*     */         
/* 104 */         if (block == Blocks.air) {
/*     */           
/* 106 */           notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */           
/*     */           return;
/*     */         } 
/* 110 */       } else if (args[5].equals("keep") && !world.isAirBlock(blockpos)) {
/*     */         
/* 112 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/* 116 */     TileEntity tileentity1 = world.getTileEntity(blockpos);
/*     */     
/* 118 */     if (tileentity1 != null) {
/*     */       
/* 120 */       if (tileentity1 instanceof IInventory)
/*     */       {
/* 122 */         ((IInventory)tileentity1).clear();
/*     */       }
/*     */       
/* 125 */       world.setBlockState(blockpos, Blocks.air.getDefaultState(), (block == Blocks.air) ? 2 : 4);
/*     */     } 
/*     */     
/* 128 */     IBlockState iblockstate = block.getStateFromMeta(i);
/*     */     
/* 130 */     if (!world.setBlockState(blockpos, iblockstate, 2))
/*     */     {
/* 132 */       throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 136 */     if (flag) {
/*     */       
/* 138 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 140 */       if (tileentity != null) {
/*     */         
/* 142 */         nbttagcompound.setInteger("x", blockpos.getX());
/* 143 */         nbttagcompound.setInteger("y", blockpos.getY());
/* 144 */         nbttagcompound.setInteger("z", blockpos.getZ());
/* 145 */         tileentity.readFromNBT(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     world.notifyNeighborsRespectDebug(blockpos, iblockstate.getBlock());
/* 150 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 151 */     notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 159 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : ((args.length == 6) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep" }) : null));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandSetBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */