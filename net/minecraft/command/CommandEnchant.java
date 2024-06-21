/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEnchant
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "enchant";
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
/*  36 */     return "commands.enchant.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     int i;
/*  47 */     if (args.length < 2)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.enchant.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*  54 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  59 */       i = parseInt(args[1], 0);
/*     */     }
/*  61 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/*  63 */       Enchantment enchantment = Enchantment.getEnchantmentByLocation(args[1]);
/*     */       
/*  65 */       if (enchantment == null)
/*     */       {
/*  67 */         throw numberinvalidexception;
/*     */       }
/*     */       
/*  70 */       i = enchantment.effectId;
/*     */     } 
/*     */     
/*  73 */     int j = 1;
/*  74 */     ItemStack itemstack = entityPlayerMP.getCurrentEquippedItem();
/*     */     
/*  76 */     if (itemstack == null)
/*     */     {
/*  78 */       throw new CommandException("commands.enchant.noItem", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  82 */     Enchantment enchantment1 = Enchantment.getEnchantmentById(i);
/*     */     
/*  84 */     if (enchantment1 == null)
/*     */     {
/*  86 */       throw new NumberInvalidException("commands.enchant.notFound", new Object[] { Integer.valueOf(i) });
/*     */     }
/*  88 */     if (!enchantment1.canApply(itemstack))
/*     */     {
/*  90 */       throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  94 */     if (args.length >= 3)
/*     */     {
/*  96 */       j = parseInt(args[2], enchantment1.getMinLevel(), enchantment1.getMaxLevel());
/*     */     }
/*     */     
/*  99 */     if (itemstack.hasTagCompound()) {
/*     */       
/* 101 */       NBTTagList nbttaglist = itemstack.getEnchantmentTagList();
/*     */       
/* 103 */       if (nbttaglist != null)
/*     */       {
/* 105 */         for (int k = 0; k < nbttaglist.tagCount(); k++) {
/*     */           
/* 107 */           int l = nbttaglist.getCompoundTagAt(k).getShort("id");
/*     */           
/* 109 */           if (Enchantment.getEnchantmentById(l) != null) {
/*     */             
/* 111 */             Enchantment enchantment2 = Enchantment.getEnchantmentById(l);
/*     */             
/* 113 */             if (!enchantment2.canApplyTogether(enchantment1))
/*     */             {
/* 115 */               throw new CommandException("commands.enchant.cantCombine", new Object[] { enchantment1.getTranslatedName(j), enchantment2.getTranslatedName(nbttaglist.getCompoundTagAt(k).getShort("lvl")) });
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 122 */     itemstack.addEnchantment(enchantment1, j);
/* 123 */     notifyOperators(sender, this, "commands.enchant.success", new Object[0]);
/* 124 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 132 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getListOfPlayers()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Enchantment.func_181077_c()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getListOfPlayers() {
/* 137 */     return MinecraftServer.getServer().getAllUsernames();
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
/* 148 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandEnchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */