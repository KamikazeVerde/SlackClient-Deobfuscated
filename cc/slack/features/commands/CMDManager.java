/*    */ package cc.slack.features.commands;
/*    */ 
/*    */ import cc.slack.features.commands.api.CMD;
/*    */ import cc.slack.features.commands.impl.ConfigCMD;
/*    */ import cc.slack.features.commands.impl.HelpCMD;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CMDManager
/*    */ {
/* 15 */   private final List<CMD> commands = new ArrayList<>(); public List<CMD> getCommands() { return this.commands; }
/* 16 */    private final String prefix = "."; public String getPrefix() { getClass(); return "."; }
/*    */   
/*    */   public void initialize() {
/*    */     try {
/* 20 */       addCommands(new CMD[] { (CMD)new ConfigCMD(), (CMD)new HelpCMD() });
/*    */ 
/*    */     
/*    */     }
/* 24 */     catch (Exception e) {
/* 25 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void addCommands(CMD... cmds) {
/* 30 */     this.commands.addAll(Arrays.asList(cmds));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\commands\CMDManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */