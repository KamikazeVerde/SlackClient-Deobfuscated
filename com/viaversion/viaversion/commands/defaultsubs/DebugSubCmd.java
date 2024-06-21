/*    */ package com.viaversion.viaversion.commands.defaultsubs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.api.debug.DebugHandler;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
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
/*    */ public class DebugSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 32 */     return "debug";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 37 */     return "Toggle debug mode";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 42 */     DebugHandler debug = Via.getManager().debugHandler();
/* 43 */     if (args.length == 0) {
/* 44 */       Via.getManager().debugHandler().setEnabled(!Via.getManager().debugHandler().enabled());
/* 45 */       sendMessage(sender, "&6Debug mode is now %s", new Object[] { Via.getManager().debugHandler().enabled() ? "&aenabled" : "&cdisabled" });
/* 46 */       return true;
/* 47 */     }  if (args.length == 1) {
/* 48 */       if (args[0].equalsIgnoreCase("clear")) {
/* 49 */         debug.clearPacketTypesToLog();
/* 50 */         sendMessage(sender, "&6Cleared packet types to log", new Object[0]);
/* 51 */         return true;
/* 52 */       }  if (args[0].equalsIgnoreCase("logposttransform")) {
/* 53 */         debug.setLogPostPacketTransform(!debug.logPostPacketTransform());
/* 54 */         sendMessage(sender, "&6Post transform packet logging is now %s", new Object[] { debug.logPostPacketTransform() ? "&aenabled" : "&cdisabled" });
/* 55 */         return true;
/*    */       } 
/* 57 */     } else if (args.length == 2) {
/* 58 */       if (args[0].equalsIgnoreCase("add")) {
/* 59 */         debug.addPacketTypeNameToLog(args[1].toUpperCase(Locale.ROOT));
/* 60 */         sendMessage(sender, "&6Added packet type %s to debug logging", new Object[] { args[1] });
/* 61 */         return true;
/* 62 */       }  if (args[0].equalsIgnoreCase("remove")) {
/* 63 */         debug.removePacketTypeNameToLog(args[1].toUpperCase(Locale.ROOT));
/* 64 */         sendMessage(sender, "&6Removed packet type %s from debug logging", new Object[] { args[1] });
/* 65 */         return true;
/*    */       } 
/*    */     } 
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
/* 73 */     if (args.length == 1)
/*    */     {
/* 75 */       return Arrays.asList(new String[] { "clear", "logposttransform", "add", "remove" });
/*    */     }
/* 77 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\DebugSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */