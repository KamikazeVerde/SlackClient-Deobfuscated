/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandManager;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class CommandBlockLogic
/*     */   implements ICommandSender
/*     */ {
/*  23 */   private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
/*     */ 
/*     */   
/*     */   private int successCount;
/*     */   
/*     */   private boolean trackOutput = true;
/*     */   
/*  30 */   private IChatComponent lastOutput = null;
/*     */ 
/*     */   
/*  33 */   private String commandStored = "";
/*     */ 
/*     */   
/*  36 */   private String customName = "@";
/*  37 */   private final CommandResultStats resultStats = new CommandResultStats();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSuccessCount() {
/*  44 */     return this.successCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getLastOutput() {
/*  52 */     return this.lastOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDataToNBT(NBTTagCompound tagCompound) {
/*  60 */     tagCompound.setString("Command", this.commandStored);
/*  61 */     tagCompound.setInteger("SuccessCount", this.successCount);
/*  62 */     tagCompound.setString("CustomName", this.customName);
/*  63 */     tagCompound.setBoolean("TrackOutput", this.trackOutput);
/*     */     
/*  65 */     if (this.lastOutput != null && this.trackOutput)
/*     */     {
/*  67 */       tagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
/*     */     }
/*     */     
/*  70 */     this.resultStats.writeStatsToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDataFromNBT(NBTTagCompound nbt) {
/*  78 */     this.commandStored = nbt.getString("Command");
/*  79 */     this.successCount = nbt.getInteger("SuccessCount");
/*     */     
/*  81 */     if (nbt.hasKey("CustomName", 8))
/*     */     {
/*  83 */       this.customName = nbt.getString("CustomName");
/*     */     }
/*     */     
/*  86 */     if (nbt.hasKey("TrackOutput", 1))
/*     */     {
/*  88 */       this.trackOutput = nbt.getBoolean("TrackOutput");
/*     */     }
/*     */     
/*  91 */     if (nbt.hasKey("LastOutput", 8) && this.trackOutput)
/*     */     {
/*  93 */       this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
/*     */     }
/*     */     
/*  96 */     this.resultStats.readStatsFromNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 107 */     return (permLevel <= 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommand(String command) {
/* 115 */     this.commandStored = command;
/* 116 */     this.successCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommand() {
/* 124 */     return this.commandStored;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(World worldIn) {
/* 129 */     if (worldIn.isRemote)
/*     */     {
/* 131 */       this.successCount = 0;
/*     */     }
/*     */     
/* 134 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/* 136 */     if (minecraftserver != null && minecraftserver.isAnvilFileSet() && minecraftserver.isCommandBlockEnabled()) {
/*     */       
/* 138 */       ICommandManager icommandmanager = minecraftserver.getCommandManager();
/*     */ 
/*     */       
/*     */       try {
/* 142 */         this.lastOutput = null;
/* 143 */         this.successCount = icommandmanager.executeCommand(this, this.commandStored);
/*     */       }
/* 145 */       catch (Throwable throwable) {
/*     */         
/* 147 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Executing command block");
/* 148 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Command to be executed");
/* 149 */         crashreportcategory.addCrashSectionCallable("Command", new Callable<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/* 153 */                 return CommandBlockLogic.this.getCommand();
/*     */               }
/*     */             });
/* 156 */         crashreportcategory.addCrashSectionCallable("Name", new Callable<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/* 160 */                 return CommandBlockLogic.this.getCommandSenderName();
/*     */               }
/*     */             });
/* 163 */         throw new ReportedException(crashreport);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 168 */       this.successCount = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 177 */     return this.customName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 185 */     return (IChatComponent)new ChatComponentText(getCommandSenderName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String p_145754_1_) {
/* 190 */     this.customName = p_145754_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 200 */     if (this.trackOutput && getEntityWorld() != null && !(getEntityWorld()).isRemote) {
/*     */       
/* 202 */       this.lastOutput = (new ChatComponentText("[" + timestampFormat.format(new Date()) + "] ")).appendSibling(component);
/* 203 */       updateCommand();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendCommandFeedback() {
/* 212 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 213 */     return (minecraftserver == null || !minecraftserver.isAnvilFileSet() || minecraftserver.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 218 */     this.resultStats.func_179672_a(this, type, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void updateCommand();
/*     */   
/*     */   public abstract int func_145751_f();
/*     */   
/*     */   public abstract void func_145757_a(ByteBuf paramByteBuf);
/*     */   
/*     */   public void setLastOutput(IChatComponent lastOutputMessage) {
/* 229 */     this.lastOutput = lastOutputMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrackOutput(boolean shouldTrackOutput) {
/* 234 */     this.trackOutput = shouldTrackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldTrackOutput() {
/* 239 */     return this.trackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryOpenEditCommandBlock(EntityPlayer playerIn) {
/* 244 */     if (!playerIn.capabilities.isCreativeMode)
/*     */     {
/* 246 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 250 */     if ((playerIn.getEntityWorld()).isRemote)
/*     */     {
/* 252 */       playerIn.openEditCommandBlock(this);
/*     */     }
/*     */     
/* 255 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandResultStats getCommandResultStats() {
/* 261 */     return this.resultStats;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandBlockLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */