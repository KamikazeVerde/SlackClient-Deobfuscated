/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEffect
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "effect";
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
/*  36 */     return "commands.effect.usage";
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
/*  47 */     if (args.length < 2)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.effect.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     EntityLivingBase entitylivingbase = getEntity(sender, args[0], EntityLivingBase.class);
/*     */     
/*  55 */     if (args[1].equals("clear")) {
/*     */       
/*  57 */       if (entitylivingbase.getActivePotionEffects().isEmpty())
/*     */       {
/*  59 */         throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entitylivingbase.getCommandSenderName() });
/*     */       }
/*     */ 
/*     */       
/*  63 */       entitylivingbase.clearActivePotions();
/*  64 */       notifyOperators(sender, this, "commands.effect.success.removed.all", new Object[] { entitylivingbase.getCommandSenderName() });
/*     */     } else {
/*     */       int i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  73 */         i = parseInt(args[1], 1);
/*     */       }
/*  75 */       catch (NumberInvalidException numberinvalidexception) {
/*     */         
/*  77 */         Potion potion = Potion.getPotionFromResourceLocation(args[1]);
/*     */         
/*  79 */         if (potion == null)
/*     */         {
/*  81 */           throw numberinvalidexception;
/*     */         }
/*     */         
/*  84 */         i = potion.id;
/*     */       } 
/*     */       
/*  87 */       int j = 600;
/*  88 */       int l = 30;
/*  89 */       int k = 0;
/*     */       
/*  91 */       if (i >= 0 && i < Potion.potionTypes.length && Potion.potionTypes[i] != null) {
/*     */         
/*  93 */         Potion potion1 = Potion.potionTypes[i];
/*     */         
/*  95 */         if (args.length >= 3) {
/*     */           
/*  97 */           l = parseInt(args[2], 0, 1000000);
/*     */           
/*  99 */           if (potion1.isInstant())
/*     */           {
/* 101 */             j = l;
/*     */           }
/*     */           else
/*     */           {
/* 105 */             j = l * 20;
/*     */           }
/*     */         
/* 108 */         } else if (potion1.isInstant()) {
/*     */           
/* 110 */           j = 1;
/*     */         } 
/*     */         
/* 113 */         if (args.length >= 4)
/*     */         {
/* 115 */           k = parseInt(args[3], 0, 255);
/*     */         }
/*     */         
/* 118 */         boolean flag = true;
/*     */         
/* 120 */         if (args.length >= 5 && "true".equalsIgnoreCase(args[4]))
/*     */         {
/* 122 */           flag = false;
/*     */         }
/*     */         
/* 125 */         if (l > 0)
/*     */         {
/* 127 */           PotionEffect potioneffect = new PotionEffect(i, j, k, false, flag);
/* 128 */           entitylivingbase.addPotionEffect(potioneffect);
/* 129 */           notifyOperators(sender, this, "commands.effect.success", new Object[] { new ChatComponentTranslation(potioneffect.getEffectName(), new Object[0]), Integer.valueOf(i), Integer.valueOf(k), entitylivingbase.getCommandSenderName(), Integer.valueOf(l) });
/*     */         }
/* 131 */         else if (entitylivingbase.isPotionActive(i))
/*     */         {
/* 133 */           entitylivingbase.removePotionEffect(i);
/* 134 */           notifyOperators(sender, this, "commands.effect.success.removed", new Object[] { new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getCommandSenderName() });
/*     */         }
/*     */         else
/*     */         {
/* 138 */           throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getCommandSenderName() });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 143 */         throw new NumberInvalidException("commands.effect.notFound", new Object[] { Integer.valueOf(i) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 151 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Potion.func_181168_c()) : ((args.length == 5) ? getListOfStringsMatchingLastWord(args, new String[] { "true", "false" }) : null));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getAllUsernames() {
/* 156 */     return MinecraftServer.getServer().getAllUsernames();
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
/* 167 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */