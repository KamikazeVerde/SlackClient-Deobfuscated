/*     */ package com.viaversion.viaversion.api.command;
/*     */ 
/*     */ import com.viaversion.viaversion.util.ChatColorUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ViaSubCommand
/*     */ {
/*     */   public abstract String name();
/*     */   
/*     */   public abstract String description();
/*     */   
/*     */   public String usage() {
/*  52 */     return name();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String permission() {
/*  61 */     return "viaversion.admin";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean execute(ViaCommandSender paramViaCommandSender, String[] paramArrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
/*  81 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String color(String s) {
/*  91 */     return ChatColorUtil.translateAlternateColorCodes(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendMessage(ViaCommandSender sender, String message, Object... args) {
/* 102 */     sender.sendMessage(color((args == null) ? message : String.format(message, args)));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\command\ViaSubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */