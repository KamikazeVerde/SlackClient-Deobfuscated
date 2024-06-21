/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTeleport
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  24 */     return "tp";
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
/*  42 */     return "commands.tp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     Entity entity;
/*  53 */     if (args.length < 1)
/*     */     {
/*  55 */       throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  59 */     int i = 0;
/*     */ 
/*     */     
/*  62 */     if (args.length != 2 && args.length != 4 && args.length != 6) {
/*     */       
/*  64 */       EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
/*     */     }
/*     */     else {
/*     */       
/*  68 */       entity = func_175768_b(sender, args[0]);
/*  69 */       i = 1;
/*     */     } 
/*     */     
/*  72 */     if (args.length != 1 && args.length != 2) {
/*     */       
/*  74 */       if (args.length < i + 3)
/*     */       {
/*  76 */         throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */       }
/*  78 */       if (entity.worldObj != null)
/*     */       {
/*  80 */         int lvt_5_2_ = i + 1;
/*  81 */         CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(entity.posX, args[i], true);
/*  82 */         CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(entity.posY, args[lvt_5_2_++], 0, 0, false);
/*  83 */         CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(entity.posZ, args[lvt_5_2_++], true);
/*  84 */         CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate(entity.rotationYaw, (args.length > lvt_5_2_) ? args[lvt_5_2_++] : "~", false);
/*  85 */         CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate(entity.rotationPitch, (args.length > lvt_5_2_) ? args[lvt_5_2_] : "~", false);
/*     */         
/*  87 */         if (entity instanceof EntityPlayerMP) {
/*     */           
/*  89 */           Set<S08PacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
/*     */           
/*  91 */           if (commandbase$coordinatearg.func_179630_c())
/*     */           {
/*  93 */             set.add(S08PacketPlayerPosLook.EnumFlags.X);
/*     */           }
/*     */           
/*  96 */           if (commandbase$coordinatearg1.func_179630_c())
/*     */           {
/*  98 */             set.add(S08PacketPlayerPosLook.EnumFlags.Y);
/*     */           }
/*     */           
/* 101 */           if (commandbase$coordinatearg2.func_179630_c())
/*     */           {
/* 103 */             set.add(S08PacketPlayerPosLook.EnumFlags.Z);
/*     */           }
/*     */           
/* 106 */           if (commandbase$coordinatearg4.func_179630_c())
/*     */           {
/* 108 */             set.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
/*     */           }
/*     */           
/* 111 */           if (commandbase$coordinatearg3.func_179630_c())
/*     */           {
/* 113 */             set.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
/*     */           }
/*     */           
/* 116 */           float f = (float)commandbase$coordinatearg3.func_179629_b();
/*     */           
/* 118 */           if (!commandbase$coordinatearg3.func_179630_c())
/*     */           {
/* 120 */             f = MathHelper.wrapAngleTo180_float(f);
/*     */           }
/*     */           
/* 123 */           float f1 = (float)commandbase$coordinatearg4.func_179629_b();
/*     */           
/* 125 */           if (!commandbase$coordinatearg4.func_179630_c())
/*     */           {
/* 127 */             f1 = MathHelper.wrapAngleTo180_float(f1);
/*     */           }
/*     */           
/* 130 */           if (f1 > 90.0F || f1 < -90.0F) {
/*     */             
/* 132 */             f1 = MathHelper.wrapAngleTo180_float(180.0F - f1);
/* 133 */             f = MathHelper.wrapAngleTo180_float(f + 180.0F);
/*     */           } 
/*     */           
/* 136 */           entity.mountEntity(null);
/* 137 */           ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(commandbase$coordinatearg.func_179629_b(), commandbase$coordinatearg1.func_179629_b(), commandbase$coordinatearg2.func_179629_b(), f, f1, set);
/* 138 */           entity.setRotationYawHead(f);
/*     */         }
/*     */         else {
/*     */           
/* 142 */           float f2 = (float)MathHelper.wrapAngleTo180_double(commandbase$coordinatearg3.func_179628_a());
/* 143 */           float f3 = (float)MathHelper.wrapAngleTo180_double(commandbase$coordinatearg4.func_179628_a());
/*     */           
/* 145 */           if (f3 > 90.0F || f3 < -90.0F) {
/*     */             
/* 147 */             f3 = MathHelper.wrapAngleTo180_float(180.0F - f3);
/* 148 */             f2 = MathHelper.wrapAngleTo180_float(f2 + 180.0F);
/*     */           } 
/*     */           
/* 151 */           entity.setLocationAndAngles(commandbase$coordinatearg.func_179628_a(), commandbase$coordinatearg1.func_179628_a(), commandbase$coordinatearg2.func_179628_a(), f2, f3);
/* 152 */           entity.setRotationYawHead(f2);
/*     */         } 
/*     */         
/* 155 */         notifyOperators(sender, (ICommand)this, "commands.tp.success.coordinates", new Object[] { entity.getCommandSenderName(), Double.valueOf(commandbase$coordinatearg.func_179628_a()), Double.valueOf(commandbase$coordinatearg1.func_179628_a()), Double.valueOf(commandbase$coordinatearg2.func_179628_a()) });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 160 */       Entity entity1 = func_175768_b(sender, args[args.length - 1]);
/*     */       
/* 162 */       if (entity1.worldObj != entity.worldObj)
/*     */       {
/* 164 */         throw new CommandException("commands.tp.notSameDimension", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 168 */       entity.mountEntity(null);
/*     */       
/* 170 */       if (entity instanceof EntityPlayerMP) {
/*     */         
/* 172 */         ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       }
/*     */       else {
/*     */         
/* 176 */         entity.setLocationAndAngles(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       } 
/*     */       
/* 179 */       notifyOperators(sender, (ICommand)this, "commands.tp.success", new Object[] { entity.getCommandSenderName(), entity1.getCommandSenderName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 187 */     return (args.length != 1 && args.length != 2) ? null : getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
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
/* 198 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */