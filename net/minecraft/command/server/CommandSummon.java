/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSummon
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  28 */     return "summon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  36 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  46 */     return "commands.summon.usage";
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
/*  57 */     if (args.length < 1)
/*     */     {
/*  59 */       throw new WrongUsageException("commands.summon.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  63 */     String s = args[0];
/*  64 */     BlockPos blockpos = sender.getPosition();
/*  65 */     Vec3 vec3 = sender.getPositionVector();
/*  66 */     double d0 = vec3.xCoord;
/*  67 */     double d1 = vec3.yCoord;
/*  68 */     double d2 = vec3.zCoord;
/*     */     
/*  70 */     if (args.length >= 4) {
/*     */       
/*  72 */       d0 = parseDouble(d0, args[1], true);
/*  73 */       d1 = parseDouble(d1, args[2], false);
/*  74 */       d2 = parseDouble(d2, args[3], true);
/*  75 */       blockpos = new BlockPos(d0, d1, d2);
/*     */     } 
/*     */     
/*  78 */     World world = sender.getEntityWorld();
/*     */     
/*  80 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  82 */       throw new CommandException("commands.summon.outOfWorld", new Object[0]);
/*     */     }
/*  84 */     if ("LightningBolt".equals(s)) {
/*     */       
/*  86 */       world.addWeatherEffect((Entity)new EntityLightningBolt(world, d0, d1, d2));
/*  87 */       notifyOperators(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } else {
/*     */       Entity entity2;
/*     */       
/*  91 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  92 */       boolean flag = false;
/*     */       
/*  94 */       if (args.length >= 5) {
/*     */         
/*  96 */         IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 4);
/*     */ 
/*     */         
/*     */         try {
/* 100 */           nbttagcompound = JsonToNBT.getTagFromJson(ichatcomponent.getUnformattedText());
/* 101 */           flag = true;
/*     */         }
/* 103 */         catch (NBTException nbtexception) {
/*     */           
/* 105 */           throw new CommandException("commands.summon.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 109 */       nbttagcompound.setString("id", s);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 114 */         entity2 = EntityList.createEntityFromNBT(nbttagcompound, world);
/*     */       }
/* 116 */       catch (RuntimeException var19) {
/*     */         
/* 118 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       } 
/*     */       
/* 121 */       if (entity2 == null)
/*     */       {
/* 123 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 127 */       entity2.setLocationAndAngles(d0, d1, d2, entity2.rotationYaw, entity2.rotationPitch);
/*     */       
/* 129 */       if (!flag && entity2 instanceof EntityLiving)
/*     */       {
/* 131 */         ((EntityLiving)entity2).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity2)), null);
/*     */       }
/*     */       
/* 134 */       world.spawnEntityInWorld(entity2);
/* 135 */       Entity entity = entity2;
/*     */       
/* 137 */       for (NBTTagCompound nbttagcompound1 = nbttagcompound; entity != null && nbttagcompound1.hasKey("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompoundTag("Riding")) {
/*     */         
/* 139 */         Entity entity1 = EntityList.createEntityFromNBT(nbttagcompound1.getCompoundTag("Riding"), world);
/*     */         
/* 141 */         if (entity1 != null) {
/*     */           
/* 143 */           entity1.setLocationAndAngles(d0, d1, d2, entity1.rotationYaw, entity1.rotationPitch);
/* 144 */           world.spawnEntityInWorld(entity1);
/* 145 */           entity.mountEntity(entity1);
/*     */         } 
/*     */         
/* 148 */         entity = entity1;
/*     */       } 
/*     */       
/* 151 */       notifyOperators(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 159 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandSummon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */