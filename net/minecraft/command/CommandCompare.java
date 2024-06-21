/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandCompare
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "testforblocks";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  27 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.compare.usage";
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
/*  48 */     if (args.length < 9)
/*     */     {
/*  50 */       throw new WrongUsageException("commands.compare.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  54 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  55 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  56 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  57 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  58 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  59 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.func_175896_b()));
/*  60 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  62 */     if (i > 524288)
/*     */     {
/*  64 */       throw new CommandException("commands.compare.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(524288) });
/*     */     }
/*  66 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*     */       
/*  68 */       World world = sender.getEntityWorld();
/*     */       
/*  70 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1))
/*     */       {
/*  72 */         boolean flag = false;
/*     */         
/*  74 */         if (args.length > 9 && args[9].equals("masked"))
/*     */         {
/*  76 */           flag = true;
/*     */         }
/*     */         
/*  79 */         i = 0;
/*  80 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*  81 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*  82 */         BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */         
/*  84 */         for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; j++) {
/*     */           
/*  86 */           for (int k = structureboundingbox.minY; k <= structureboundingbox.maxY; k++) {
/*     */             
/*  88 */             for (int l = structureboundingbox.minX; l <= structureboundingbox.maxX; l++) {
/*     */               
/*  90 */               blockpos$mutableblockpos.func_181079_c(l, k, j);
/*  91 */               blockpos$mutableblockpos1.func_181079_c(l + blockpos3.getX(), k + blockpos3.getY(), j + blockpos3.getZ());
/*  92 */               boolean flag1 = false;
/*  93 */               IBlockState iblockstate = world.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */               
/*  95 */               if (!flag || iblockstate.getBlock() != Blocks.air) {
/*     */                 
/*  97 */                 if (iblockstate == world.getBlockState((BlockPos)blockpos$mutableblockpos1)) {
/*     */                   
/*  99 */                   TileEntity tileentity = world.getTileEntity((BlockPos)blockpos$mutableblockpos);
/* 100 */                   TileEntity tileentity1 = world.getTileEntity((BlockPos)blockpos$mutableblockpos1);
/*     */                   
/* 102 */                   if (tileentity != null && tileentity1 != null)
/*     */                   {
/* 104 */                     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 105 */                     tileentity.writeToNBT(nbttagcompound);
/* 106 */                     nbttagcompound.removeTag("x");
/* 107 */                     nbttagcompound.removeTag("y");
/* 108 */                     nbttagcompound.removeTag("z");
/* 109 */                     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 110 */                     tileentity1.writeToNBT(nbttagcompound1);
/* 111 */                     nbttagcompound1.removeTag("x");
/* 112 */                     nbttagcompound1.removeTag("y");
/* 113 */                     nbttagcompound1.removeTag("z");
/*     */                     
/* 115 */                     if (!nbttagcompound.equals(nbttagcompound1))
/*     */                     {
/* 117 */                       flag1 = true;
/*     */                     }
/*     */                   }
/* 120 */                   else if (tileentity != null)
/*     */                   {
/* 122 */                     flag1 = true;
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 127 */                   flag1 = true;
/*     */                 } 
/*     */                 
/* 130 */                 i++;
/*     */                 
/* 132 */                 if (flag1)
/*     */                 {
/* 134 */                   throw new CommandException("commands.compare.failed", new Object[0]);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 141 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 142 */         notifyOperators(sender, this, "commands.compare.success", new Object[] { Integer.valueOf(i) });
/*     */       }
/*     */       else
/*     */       {
/* 146 */         throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 151 */       throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 158 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? func_175771_a(args, 6, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "masked", "all" }) : null)));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandCompare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */