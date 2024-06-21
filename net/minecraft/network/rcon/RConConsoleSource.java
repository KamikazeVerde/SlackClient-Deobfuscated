/*    */ package net.minecraft.network.rcon;
/*    */ 
/*    */ import net.minecraft.command.CommandResultStats;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class RConConsoleSource
/*    */   implements ICommandSender {
/* 15 */   private static final RConConsoleSource field_70010_a = new RConConsoleSource();
/* 16 */   private StringBuffer field_70009_b = new StringBuffer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandSenderName() {
/* 23 */     return "Rcon";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDisplayName() {
/* 31 */     return (IChatComponent)new ChatComponentText(getCommandSenderName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addChatMessage(IChatComponent component) {
/* 41 */     this.field_70009_b.append(component.getUnformattedText());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 61 */     return new BlockPos(0, 0, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vec3 getPositionVector() {
/* 70 */     return new Vec3(0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public World getEntityWorld() {
/* 79 */     return MinecraftServer.getServer().getEntityWorld();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getCommandSenderEntity() {
/* 87 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sendCommandFeedback() {
/* 95 */     return true;
/*    */   }
/*    */   
/*    */   public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\network\rcon\RConConsoleSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */