/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandExecuteAt
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "execute";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  38 */     return "commands.execute.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(final ICommandSender sender, String[] args) throws CommandException {
/*  49 */     if (args.length < 5)
/*     */     {
/*  51 */       throw new WrongUsageException("commands.execute.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     final Entity entity = getEntity(sender, args[0], Entity.class);
/*  56 */     final double d0 = parseDouble(entity.posX, args[1], false);
/*  57 */     final double d1 = parseDouble(entity.posY, args[2], false);
/*  58 */     final double d2 = parseDouble(entity.posZ, args[3], false);
/*  59 */     final BlockPos blockpos = new BlockPos(d0, d1, d2);
/*  60 */     int i = 4;
/*     */     
/*  62 */     if ("detect".equals(args[4]) && args.length > 10) {
/*     */       
/*  64 */       World world = entity.getEntityWorld();
/*  65 */       double d3 = parseDouble(d0, args[5], false);
/*  66 */       double d4 = parseDouble(d1, args[6], false);
/*  67 */       double d5 = parseDouble(d2, args[7], false);
/*  68 */       Block block = getBlockByText(sender, args[8]);
/*  69 */       int k = parseInt(args[9], -1, 15);
/*  70 */       BlockPos blockpos1 = new BlockPos(d3, d4, d5);
/*  71 */       IBlockState iblockstate = world.getBlockState(blockpos1);
/*     */       
/*  73 */       if (iblockstate.getBlock() != block || (k >= 0 && iblockstate.getBlock().getMetaFromState(iblockstate) != k))
/*     */       {
/*  75 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getCommandSenderName() });
/*     */       }
/*     */       
/*  78 */       i = 10;
/*     */     } 
/*     */     
/*  81 */     String s = buildString(args, i);
/*  82 */     ICommandSender icommandsender = new ICommandSender()
/*     */       {
/*     */         public String getCommandSenderName()
/*     */         {
/*  86 */           return entity.getCommandSenderName();
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/*  90 */           return entity.getDisplayName();
/*     */         }
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {
/*  94 */           sender.addChatMessage(component);
/*     */         }
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  98 */           return sender.canCommandSenderUseCommand(permLevel, commandName);
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/* 102 */           return blockpos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/* 106 */           return new Vec3(d0, d1, d2);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/* 110 */           return entity.worldObj;
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/* 114 */           return entity;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/* 118 */           MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 119 */           return (minecraftserver == null || minecraftserver.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"));
/*     */         }
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 123 */           entity.setCommandStat(type, amount);
/*     */         }
/*     */       };
/* 126 */     ICommandManager icommandmanager = MinecraftServer.getServer().getCommandManager();
/*     */ 
/*     */     
/*     */     try {
/* 130 */       int j = icommandmanager.executeCommand(icommandsender, s);
/*     */       
/* 132 */       if (j < 1)
/*     */       {
/* 134 */         throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { s });
/*     */       }
/*     */     }
/* 137 */     catch (Throwable var23) {
/*     */       
/* 139 */       throw new CommandException("commands.execute.failed", new Object[] { s, entity.getCommandSenderName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 146 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : ((args.length > 5 && args.length <= 8 && "detect".equals(args[4])) ? func_175771_a(args, 5, pos) : ((args.length == 9 && "detect".equals(args[4])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 157 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandExecuteAt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */