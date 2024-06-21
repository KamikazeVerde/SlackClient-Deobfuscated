/*    */ package cc.slack.features.commands.impl;
/*    */ 
/*    */ import cc.slack.features.commands.api.CMD;
/*    */ import cc.slack.features.commands.api.CMDInfo;
/*    */ import cc.slack.features.config.configManager;
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CMDInfo(name = "Config", alias = "c", description = "Save and Load Configs.")
/*    */ public class ConfigCMD
/*    */   extends CMD
/*    */ {
/*    */   public void onCommand(String[] args, String command) {
/* 17 */     switch (args.length) {
/*    */       case 0:
/* 19 */         configsMessage();
/* 20 */         commandsMessage();
/*    */         break;
/*    */       case 1:
/* 23 */         switch (args[0]) {
/*    */           case "save":
/* 25 */             configManager.saveConfig(configManager.currentConfig);
/*    */           case "load":
/*    */           case "delete":
/* 28 */             commandsMessage();
/*    */             break;
/*    */           case "list":
/* 31 */             configsMessage();
/*    */             break;
/*    */         } 
/* 34 */         commandsMessage();
/*    */         break;
/*    */ 
/*    */       
/*    */       case 2:
/* 39 */         switch (args[0]) {
/*    */           case "save":
/* 41 */             configManager.saveConfig(args[1]);
/*    */           case "load":
/* 43 */             configManager.loadConfig(args[1]);
/*    */           case "delete":
/* 45 */             configManager.delete(args[1]);
/*    */           case "list":
/* 47 */             configsMessage();
/*    */             break;
/*    */         } 
/* 50 */         commandsMessage();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void configsMessage() {
/* 58 */     PrintUtil.message("Â§fÂ§lSlack configs:");
/* 59 */     for (String str : configManager.getConfigList()) {
/* 60 */       PrintUtil.message("Â§e " + str);
/*    */     }
/*    */   }
/*    */   
/*    */   private void commandsMessage() {
/* 65 */     PrintUtil.message("Â§fÂ§lConfig commands:");
/* 66 */     PrintUtil.message("Â§f .config save [config name]");
/* 67 */     PrintUtil.message("Â§f .config load [config name]");
/* 68 */     PrintUtil.message("Â§f .config delete [config name]");
/* 69 */     PrintUtil.message("Â§f .config list");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\commands\impl\ConfigCMD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */