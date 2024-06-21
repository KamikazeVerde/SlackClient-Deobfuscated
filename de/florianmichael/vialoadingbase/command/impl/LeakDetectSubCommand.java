/*    */ package de.florianmichael.vialoadingbase.command.impl;
/*    */ 
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import io.netty.util.ResourceLeakDetector;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
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
/*    */ 
/*    */ 
/*    */ public class LeakDetectSubCommand
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 32 */     return "leakdetect";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 37 */     return "Sets ResourceLeakDetector level";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender viaCommandSender, String[] strings) {
/* 42 */     if (strings.length == 1) {
/*    */       try {
/* 44 */         ResourceLeakDetector.Level level = ResourceLeakDetector.Level.valueOf(strings[0]);
/* 45 */         ResourceLeakDetector.setLevel(level);
/* 46 */         viaCommandSender.sendMessage("Set leak detector level to " + level);
/* 47 */       } catch (IllegalArgumentException e) {
/* 48 */         viaCommandSender.sendMessage("Invalid level (" + Arrays.toString((Object[])ResourceLeakDetector.Level.values()) + ")");
/*    */       } 
/*    */     } else {
/* 51 */       viaCommandSender.sendMessage("Current leak detection level is " + ResourceLeakDetector.getLevel());
/*    */     } 
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
/* 58 */     if (args.length == 1) {
/* 59 */       return (List<String>)Arrays.<ResourceLeakDetector.Level>stream(ResourceLeakDetector.Level.values()).map(Enum::name).filter(it -> it.startsWith(paramArrayOfString[0])).collect(Collectors.toList());
/*    */     }
/* 61 */     return super.onTabComplete(sender, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\command\impl\LeakDetectSubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */