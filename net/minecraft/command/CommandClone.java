/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClone
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  24 */     return "clone";
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
/*  42 */     return "commands.clone.usage";
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
/*  53 */     if (args.length < 9)
/*     */     {
/*  55 */       throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  59 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  60 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  61 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  62 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  63 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  64 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.func_175896_b()));
/*  65 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  67 */     if (i > 32768)
/*     */     {
/*  69 */       throw new CommandException("commands.clone.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(32768) });
/*     */     }
/*     */ 
/*     */     
/*  73 */     boolean flag = false;
/*  74 */     Block block = null;
/*  75 */     int j = -1;
/*     */     
/*  77 */     if ((args.length < 11 || (!args[10].equals("force") && !args[10].equals("move"))) && structureboundingbox.intersectsWith(structureboundingbox1))
/*     */     {
/*  79 */       throw new CommandException("commands.clone.noOverlap", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  83 */     if (args.length >= 11 && args[10].equals("move"))
/*     */     {
/*  85 */       flag = true;
/*     */     }
/*     */     
/*  88 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*     */       
/*  90 */       World world = sender.getEntityWorld();
/*     */       
/*  92 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1))
/*     */       {
/*  94 */         boolean flag1 = false;
/*     */         
/*  96 */         if (args.length >= 10)
/*     */         {
/*  98 */           if (args[9].equals("masked")) {
/*     */             
/* 100 */             flag1 = true;
/*     */           }
/* 102 */           else if (args[9].equals("filtered")) {
/*     */             
/* 104 */             if (args.length < 12)
/*     */             {
/* 106 */               throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */             }
/*     */             
/* 109 */             block = getBlockByText(sender, args[11]);
/*     */             
/* 111 */             if (args.length >= 13)
/*     */             {
/* 113 */               j = parseInt(args[12], 0, 15);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 118 */         List<StaticCloneData> list = Lists.newArrayList();
/* 119 */         List<StaticCloneData> list1 = Lists.newArrayList();
/* 120 */         List<StaticCloneData> list2 = Lists.newArrayList();
/* 121 */         LinkedList<BlockPos> linkedlist = Lists.newLinkedList();
/* 122 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*     */         
/* 124 */         for (int k = structureboundingbox.minZ; k <= structureboundingbox.maxZ; k++) {
/*     */           
/* 126 */           for (int l = structureboundingbox.minY; l <= structureboundingbox.maxY; l++) {
/*     */             
/* 128 */             for (int i1 = structureboundingbox.minX; i1 <= structureboundingbox.maxX; i1++) {
/*     */               
/* 130 */               BlockPos blockpos4 = new BlockPos(i1, l, k);
/* 131 */               BlockPos blockpos5 = blockpos4.add((Vec3i)blockpos3);
/* 132 */               IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */               
/* 134 */               if ((!flag1 || iblockstate.getBlock() != Blocks.air) && (block == null || (iblockstate.getBlock() == block && (j < 0 || iblockstate.getBlock().getMetaFromState(iblockstate) == j)))) {
/*     */                 
/* 136 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 138 */                 if (tileentity != null) {
/*     */                   
/* 140 */                   NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 141 */                   tileentity.writeToNBT(nbttagcompound);
/* 142 */                   list1.add(new StaticCloneData(blockpos5, iblockstate, nbttagcompound));
/* 143 */                   linkedlist.addLast(blockpos4);
/*     */                 }
/* 145 */                 else if (!iblockstate.getBlock().isFullBlock() && !iblockstate.getBlock().isFullCube()) {
/*     */                   
/* 147 */                   list2.add(new StaticCloneData(blockpos5, iblockstate, null));
/* 148 */                   linkedlist.addFirst(blockpos4);
/*     */                 }
/*     */                 else {
/*     */                   
/* 152 */                   list.add(new StaticCloneData(blockpos5, iblockstate, null));
/* 153 */                   linkedlist.addLast(blockpos4);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 160 */         if (flag) {
/*     */           
/* 162 */           for (BlockPos blockpos6 : linkedlist) {
/*     */             
/* 164 */             TileEntity tileentity1 = world.getTileEntity(blockpos6);
/*     */             
/* 166 */             if (tileentity1 instanceof IInventory)
/*     */             {
/* 168 */               ((IInventory)tileentity1).clear();
/*     */             }
/*     */             
/* 171 */             world.setBlockState(blockpos6, Blocks.barrier.getDefaultState(), 2);
/*     */           } 
/*     */           
/* 174 */           for (BlockPos blockpos7 : linkedlist)
/*     */           {
/* 176 */             world.setBlockState(blockpos7, Blocks.air.getDefaultState(), 3);
/*     */           }
/*     */         } 
/*     */         
/* 180 */         List<StaticCloneData> list3 = Lists.newArrayList();
/* 181 */         list3.addAll(list);
/* 182 */         list3.addAll(list1);
/* 183 */         list3.addAll(list2);
/* 184 */         List<StaticCloneData> list4 = Lists.reverse(list3);
/*     */         
/* 186 */         for (StaticCloneData commandclone$staticclonedata : list4) {
/*     */           
/* 188 */           TileEntity tileentity2 = world.getTileEntity(commandclone$staticclonedata.field_179537_a);
/*     */           
/* 190 */           if (tileentity2 instanceof IInventory)
/*     */           {
/* 192 */             ((IInventory)tileentity2).clear();
/*     */           }
/*     */           
/* 195 */           world.setBlockState(commandclone$staticclonedata.field_179537_a, Blocks.barrier.getDefaultState(), 2);
/*     */         } 
/*     */         
/* 198 */         i = 0;
/*     */         
/* 200 */         for (StaticCloneData commandclone$staticclonedata1 : list3) {
/*     */           
/* 202 */           if (world.setBlockState(commandclone$staticclonedata1.field_179537_a, commandclone$staticclonedata1.blockState, 2))
/*     */           {
/* 204 */             i++;
/*     */           }
/*     */         } 
/*     */         
/* 208 */         for (StaticCloneData commandclone$staticclonedata2 : list1) {
/*     */           
/* 210 */           TileEntity tileentity3 = world.getTileEntity(commandclone$staticclonedata2.field_179537_a);
/*     */           
/* 212 */           if (commandclone$staticclonedata2.field_179536_c != null && tileentity3 != null) {
/*     */             
/* 214 */             commandclone$staticclonedata2.field_179536_c.setInteger("x", commandclone$staticclonedata2.field_179537_a.getX());
/* 215 */             commandclone$staticclonedata2.field_179536_c.setInteger("y", commandclone$staticclonedata2.field_179537_a.getY());
/* 216 */             commandclone$staticclonedata2.field_179536_c.setInteger("z", commandclone$staticclonedata2.field_179537_a.getZ());
/* 217 */             tileentity3.readFromNBT(commandclone$staticclonedata2.field_179536_c);
/* 218 */             tileentity3.markDirty();
/*     */           } 
/*     */           
/* 221 */           world.setBlockState(commandclone$staticclonedata2.field_179537_a, commandclone$staticclonedata2.blockState, 2);
/*     */         } 
/*     */         
/* 224 */         for (StaticCloneData commandclone$staticclonedata3 : list4)
/*     */         {
/* 226 */           world.notifyNeighborsRespectDebug(commandclone$staticclonedata3.field_179537_a, commandclone$staticclonedata3.blockState.getBlock());
/*     */         }
/*     */         
/* 229 */         List<NextTickListEntry> list5 = world.func_175712_a(structureboundingbox, false);
/*     */         
/* 231 */         if (list5 != null)
/*     */         {
/* 233 */           for (NextTickListEntry nextticklistentry : list5) {
/*     */             
/* 235 */             if (structureboundingbox.isVecInside((Vec3i)nextticklistentry.position)) {
/*     */               
/* 237 */               BlockPos blockpos8 = nextticklistentry.position.add((Vec3i)blockpos3);
/* 238 */               world.scheduleBlockUpdate(blockpos8, nextticklistentry.getBlock(), (int)(nextticklistentry.scheduledTime - world.getWorldInfo().getWorldTotalTime()), nextticklistentry.priority);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 243 */         if (i <= 0)
/*     */         {
/* 245 */           throw new CommandException("commands.clone.failed", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/* 249 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 250 */         notifyOperators(sender, this, "commands.clone.success", new Object[] { Integer.valueOf(i) });
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 255 */         throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 260 */       throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 269 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? func_175771_a(args, 6, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "masked", "filtered" }) : ((args.length == 11) ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force", "move" }) : ((args.length == 12 && "filtered".equals(args[9])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null)))));
/*     */   }
/*     */ 
/*     */   
/*     */   static class StaticCloneData
/*     */   {
/*     */     public final BlockPos field_179537_a;
/*     */     public final IBlockState blockState;
/*     */     public final NBTTagCompound field_179536_c;
/*     */     
/*     */     public StaticCloneData(BlockPos p_i46037_1_, IBlockState p_i46037_2_, NBTTagCompound p_i46037_3_) {
/* 280 */       this.field_179537_a = p_i46037_1_;
/* 281 */       this.blockState = p_i46037_2_;
/* 282 */       this.field_179536_c = p_i46037_3_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandClone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */