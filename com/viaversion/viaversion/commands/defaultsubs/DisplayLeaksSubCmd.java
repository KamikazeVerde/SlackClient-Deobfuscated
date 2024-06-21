/*    */ package com.viaversion.viaversion.commands.defaultsubs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import io.netty.util.ResourceLeakDetector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayLeaksSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 27 */     return "displayleaks";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 32 */     return "Try to hunt memory leaks!";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 37 */     if (ResourceLeakDetector.getLevel() != ResourceLeakDetector.Level.PARANOID) {
/* 38 */       ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
/*    */     } else {
/* 40 */       ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
/*    */     } 
/* 42 */     sendMessage(sender, "&6Leak detector is now %s", new Object[] { (ResourceLeakDetector.getLevel() == ResourceLeakDetector.Level.PARANOID) ? "&aenabled" : "&cdisabled" });
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\DisplayLeaksSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */