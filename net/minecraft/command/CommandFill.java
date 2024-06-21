/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
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
/*     */ 
/*     */ public class CommandFill
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "fill";
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
/*  41 */     return "commands.fill.usage";
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
/*  52 */     if (args.length < 7)
/*     */     {
/*  54 */       throw new WrongUsageException("commands.fill.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  58 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  59 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  60 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  61 */     Block block = CommandBase.getBlockByText(sender, args[6]);
/*  62 */     int i = 0;
/*     */     
/*  64 */     if (args.length >= 8)
/*     */     {
/*  66 */       i = parseInt(args[7], 0, 15);
/*     */     }
/*     */     
/*  69 */     BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
/*  70 */     BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
/*  71 */     int j = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);
/*     */     
/*  73 */     if (j > 32768)
/*     */     {
/*  75 */       throw new CommandException("commands.fill.tooManyBlocks", new Object[] { Integer.valueOf(j), Integer.valueOf(32768) });
/*     */     }
/*  77 */     if (blockpos2.getY() >= 0 && blockpos3.getY() < 256) {
/*     */       
/*  79 */       World world = sender.getEntityWorld();
/*     */       
/*  81 */       for (int k = blockpos2.getZ(); k < blockpos3.getZ() + 16; k += 16) {
/*     */         
/*  83 */         for (int l = blockpos2.getX(); l < blockpos3.getX() + 16; l += 16) {
/*     */           
/*  85 */           if (!world.isBlockLoaded(new BlockPos(l, blockpos3.getY() - blockpos2.getY(), k)))
/*     */           {
/*  87 */             throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  93 */       boolean flag = false;
/*     */       
/*  95 */       if (args.length >= 10 && block.hasTileEntity()) {
/*     */         
/*  97 */         String s = getChatComponentFromNthArg(sender, args, 9).getUnformattedText();
/*     */ 
/*     */         
/*     */         try {
/* 101 */           nbttagcompound = JsonToNBT.getTagFromJson(s);
/* 102 */           flag = true;
/*     */         }
/* 104 */         catch (NBTException nbtexception) {
/*     */           
/* 106 */           throw new CommandException("commands.fill.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 110 */       List<BlockPos> list = Lists.newArrayList();
/* 111 */       j = 0;
/*     */       
/* 113 */       for (int i1 = blockpos2.getZ(); i1 <= blockpos3.getZ(); i1++) {
/*     */         
/* 115 */         for (int j1 = blockpos2.getY(); j1 <= blockpos3.getY(); j1++) {
/*     */           
/* 117 */           for (int k1 = blockpos2.getX(); k1 <= blockpos3.getX(); k1++) {
/*     */             
/* 119 */             BlockPos blockpos4 = new BlockPos(k1, j1, i1);
/*     */             
/* 121 */             if (args.length >= 9)
/*     */             {
/* 123 */               if (!args[8].equals("outline") && !args[8].equals("hollow")) {
/*     */                 
/* 125 */                 if (args[8].equals("destroy"))
/*     */                 {
/* 127 */                   world.destroyBlock(blockpos4, true);
/*     */                 }
/* 129 */                 else if (args[8].equals("keep"))
/*     */                 {
/* 131 */                   if (!world.isAirBlock(blockpos4))
/*     */                   {
/*     */                     continue;
/*     */                   }
/*     */                 }
/* 136 */                 else if (args[8].equals("replace") && !block.hasTileEntity())
/*     */                 {
/* 138 */                   if (args.length > 9) {
/*     */                     
/* 140 */                     Block block1 = CommandBase.getBlockByText(sender, args[9]);
/*     */                     
/* 142 */                     if (world.getBlockState(blockpos4).getBlock() != block1) {
/*     */                       continue;
/*     */                     }
/*     */                   } 
/*     */ 
/*     */                   
/* 148 */                   if (args.length > 10)
/*     */                   {
/* 150 */                     int l1 = CommandBase.parseInt(args[10]);
/* 151 */                     IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */                     
/* 153 */                     if (iblockstate.getBlock().getMetaFromState(iblockstate) != l1) {
/*     */                       continue;
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/* 160 */               } else if (k1 != blockpos2.getX() && k1 != blockpos3.getX() && j1 != blockpos2.getY() && j1 != blockpos3.getY() && i1 != blockpos2.getZ() && i1 != blockpos3.getZ()) {
/*     */                 
/* 162 */                 if (args[8].equals("hollow")) {
/*     */                   
/* 164 */                   world.setBlockState(blockpos4, Blocks.air.getDefaultState(), 2);
/* 165 */                   list.add(blockpos4);
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */             }
/*     */             
/* 172 */             TileEntity tileentity1 = world.getTileEntity(blockpos4);
/*     */             
/* 174 */             if (tileentity1 != null) {
/*     */               
/* 176 */               if (tileentity1 instanceof IInventory)
/*     */               {
/* 178 */                 ((IInventory)tileentity1).clear();
/*     */               }
/*     */               
/* 181 */               world.setBlockState(blockpos4, Blocks.barrier.getDefaultState(), (block == Blocks.barrier) ? 2 : 4);
/*     */             } 
/*     */             
/* 184 */             IBlockState iblockstate1 = block.getStateFromMeta(i);
/*     */             
/* 186 */             if (world.setBlockState(blockpos4, iblockstate1, 2)) {
/*     */               
/* 188 */               list.add(blockpos4);
/* 189 */               j++;
/*     */               
/* 191 */               if (flag) {
/*     */                 
/* 193 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 195 */                 if (tileentity != null) {
/*     */                   
/* 197 */                   nbttagcompound.setInteger("x", blockpos4.getX());
/* 198 */                   nbttagcompound.setInteger("y", blockpos4.getY());
/* 199 */                   nbttagcompound.setInteger("z", blockpos4.getZ());
/* 200 */                   tileentity.readFromNBT(nbttagcompound);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 208 */       for (BlockPos blockpos5 : list) {
/*     */         
/* 210 */         Block block2 = world.getBlockState(blockpos5).getBlock();
/* 211 */         world.notifyNeighborsRespectDebug(blockpos5, block2);
/*     */       } 
/*     */       
/* 214 */       if (j <= 0)
/*     */       {
/* 216 */         throw new CommandException("commands.fill.failed", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 220 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, j);
/* 221 */       notifyOperators(sender, this, "commands.fill.success", new Object[] { Integer.valueOf(j) });
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 226 */       throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 233 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length == 7) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : ((args.length == 9) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep", "hollow", "outline" }) : ((args.length == 10 && "replace".equals(args[8])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null))));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */