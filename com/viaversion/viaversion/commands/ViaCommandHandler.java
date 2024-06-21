/*     */ package com.viaversion.viaversion.commands;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*     */ import com.viaversion.viaversion.api.command.ViaVersionCommand;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.AutoTeamSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.DebugSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.DisplayLeaksSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.DontBugMeSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.DumpSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.HelpSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.ListSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.PPSSubCmd;
/*     */ import com.viaversion.viaversion.commands.defaultsubs.ReloadSubCmd;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public abstract class ViaCommandHandler
/*     */   implements ViaVersionCommand
/*     */ {
/*  47 */   private final Map<String, ViaSubCommand> commandMap = new HashMap<>();
/*     */   
/*     */   protected ViaCommandHandler() {
/*  50 */     registerDefaults();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSubCommand(ViaSubCommand command) {
/*  55 */     Preconditions.checkArgument(command.name().matches("^[a-z0-9_-]{3,15}$"), command.name() + " is not a valid sub-command name.");
/*  56 */     Preconditions.checkArgument(!hasSubCommand(command.name()), "ViaSubCommand " + command.name() + " does already exists!");
/*  57 */     this.commandMap.put(command.name().toLowerCase(Locale.ROOT), command);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSubCommand(String name) {
/*  62 */     return this.commandMap.containsKey(name.toLowerCase(Locale.ROOT));
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaSubCommand getSubCommand(String name) {
/*  67 */     return this.commandMap.get(name.toLowerCase(Locale.ROOT));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(ViaCommandSender sender, String[] args) {
/*  72 */     if (args.length == 0) {
/*  73 */       showHelp(sender);
/*  74 */       return false;
/*     */     } 
/*     */     
/*  77 */     if (!hasSubCommand(args[0])) {
/*  78 */       sender.sendMessage(ViaSubCommand.color("&cThis command does not exist."));
/*  79 */       showHelp(sender);
/*  80 */       return false;
/*     */     } 
/*  82 */     ViaSubCommand handler = getSubCommand(args[0]);
/*     */     
/*  84 */     if (!hasPermission(sender, handler.permission())) {
/*  85 */       sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use this command!"));
/*  86 */       return false;
/*     */     } 
/*     */     
/*  89 */     String[] subArgs = Arrays.<String>copyOfRange(args, 1, args.length);
/*  90 */     boolean result = handler.execute(sender, subArgs);
/*  91 */     if (!result) {
/*  92 */       sender.sendMessage("Usage: /viaversion " + handler.usage());
/*     */     }
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
/*  99 */     Set<ViaSubCommand> allowed = calculateAllowedCommands(sender);
/* 100 */     List<String> output = new ArrayList<>();
/*     */ 
/*     */     
/* 103 */     if (args.length == 1) {
/* 104 */       if (!args[0].isEmpty()) {
/* 105 */         for (ViaSubCommand sub : allowed) {
/* 106 */           if (sub.name().toLowerCase().startsWith(args[0].toLowerCase(Locale.ROOT))) {
/* 107 */             output.add(sub.name());
/*     */           }
/*     */         } 
/*     */       } else {
/* 111 */         for (ViaSubCommand sub : allowed) {
/* 112 */           output.add(sub.name());
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 117 */     else if (args.length >= 2 && 
/* 118 */       getSubCommand(args[0]) != null) {
/* 119 */       ViaSubCommand sub = getSubCommand(args[0]);
/* 120 */       if (!allowed.contains(sub)) {
/* 121 */         return output;
/*     */       }
/*     */       
/* 124 */       String[] subArgs = Arrays.<String>copyOfRange(args, 1, args.length);
/*     */       
/* 126 */       List<String> tab = sub.onTabComplete(sender, subArgs);
/* 127 */       Collections.sort(tab);
/* 128 */       return tab;
/*     */     } 
/*     */     
/* 131 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showHelp(ViaCommandSender sender) {
/* 141 */     Set<ViaSubCommand> allowed = calculateAllowedCommands(sender);
/* 142 */     if (allowed.isEmpty()) {
/* 143 */       sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use these commands!"));
/*     */       return;
/*     */     } 
/* 146 */     sender.sendMessage(ViaSubCommand.color("&aViaVersion &c" + Via.getPlatform().getPluginVersion()));
/* 147 */     sender.sendMessage(ViaSubCommand.color("&6Commands:"));
/* 148 */     for (ViaSubCommand cmd : allowed) {
/* 149 */       sender.sendMessage(ViaSubCommand.color(String.format("&2/viaversion %s &7- &6%s", new Object[] { cmd.usage(), cmd.description() })));
/*     */     } 
/* 151 */     allowed.clear();
/*     */   }
/*     */   
/*     */   private Set<ViaSubCommand> calculateAllowedCommands(ViaCommandSender sender) {
/* 155 */     Set<ViaSubCommand> cmds = new HashSet<>();
/* 156 */     for (ViaSubCommand sub : this.commandMap.values()) {
/* 157 */       if (hasPermission(sender, sub.permission())) {
/* 158 */         cmds.add(sub);
/*     */       }
/*     */     } 
/* 161 */     return cmds;
/*     */   }
/*     */   
/*     */   private boolean hasPermission(ViaCommandSender sender, String permission) {
/* 165 */     return (permission == null || sender.hasPermission(permission));
/*     */   }
/*     */   
/*     */   private void registerDefaults() {
/* 169 */     registerSubCommand((ViaSubCommand)new ListSubCmd());
/* 170 */     registerSubCommand((ViaSubCommand)new PPSSubCmd());
/* 171 */     registerSubCommand((ViaSubCommand)new DebugSubCmd());
/* 172 */     registerSubCommand((ViaSubCommand)new DumpSubCmd());
/* 173 */     registerSubCommand((ViaSubCommand)new DisplayLeaksSubCmd());
/* 174 */     registerSubCommand((ViaSubCommand)new DontBugMeSubCmd());
/* 175 */     registerSubCommand((ViaSubCommand)new AutoTeamSubCmd());
/* 176 */     registerSubCommand((ViaSubCommand)new HelpSubCmd());
/* 177 */     registerSubCommand((ViaSubCommand)new ReloadSubCmd());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\ViaCommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */