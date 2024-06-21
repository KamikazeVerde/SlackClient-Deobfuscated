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
/*     */ import net.minecraft.command.NumberInvalidException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CommandTestForBlock
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  27 */     return "testforblock";
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
/*  45 */     return "commands.testforblock.usage";
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
/*  58 */       throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  62 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  63 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  64 */     Block block = Block.getBlockFromName(args[3]);
/*     */     
/*  66 */     if (block == null)
/*     */     {
/*  68 */       throw new NumberInvalidException("commands.setblock.notFound", new Object[] { args[3] });
/*     */     }
/*     */ 
/*     */     
/*  72 */     int i = -1;
/*     */     
/*  74 */     if (args.length >= 5)
/*     */     {
/*  76 */       i = parseInt(args[4], -1, 15);
/*     */     }
/*     */     
/*  79 */     World world = sender.getEntityWorld();
/*     */     
/*  81 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  83 */       throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  87 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  88 */     boolean flag = false;
/*     */     
/*  90 */     if (args.length >= 6 && block.hasTileEntity()) {
/*     */       
/*  92 */       String s = getChatComponentFromNthArg(sender, args, 5).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/*  96 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  97 */         flag = true;
/*     */       }
/*  99 */       catch (NBTException nbtexception) {
/*     */         
/* 101 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     IBlockState iblockstate = world.getBlockState(blockpos);
/* 106 */     Block block1 = iblockstate.getBlock();
/*     */     
/* 108 */     if (block1 != block)
/*     */     {
/* 110 */       throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), block1.getLocalizedName(), block.getLocalizedName() });
/*     */     }
/*     */ 
/*     */     
/* 114 */     if (i > -1) {
/*     */       
/* 116 */       int j = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */       
/* 118 */       if (j != i)
/*     */       {
/* 120 */         throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), Integer.valueOf(j), Integer.valueOf(i) });
/*     */       }
/*     */     } 
/*     */     
/* 124 */     if (flag) {
/*     */       
/* 126 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 128 */       if (tileentity == null)
/*     */       {
/* 130 */         throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 133 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 134 */       tileentity.writeToNBT(nbttagcompound1);
/*     */       
/* 136 */       if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*     */       {
/* 138 */         throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */     } 
/*     */     
/* 142 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 143 */     notifyOperators(sender, (ICommand)this, "commands.testforblock.success", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 152 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandTestForBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */