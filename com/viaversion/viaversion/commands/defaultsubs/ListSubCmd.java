/*    */ package com.viaversion.viaversion.commands.defaultsubs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.TreeMap;
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
/*    */ public class ListSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 32 */     return "list";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 37 */     return "Shows lists of the versions from logged in players";
/*    */   }
/*    */ 
/*    */   
/*    */   public String usage() {
/* 42 */     return "list";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 47 */     Map<ProtocolVersion, Set<String>> playerVersions = new TreeMap<>((o1, o2) -> ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1));
/*    */     
/* 49 */     for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
/* 50 */       int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
/* 51 */       ProtocolVersion key = ProtocolVersion.getProtocol(playerVersion);
/* 52 */       ((Set<String>)playerVersions.computeIfAbsent(key, s -> new HashSet())).add(p.getName());
/*    */     } 
/*    */     
/* 55 */     for (Map.Entry<ProtocolVersion, Set<String>> entry : playerVersions.entrySet()) {
/* 56 */       sendMessage(sender, "&8[&6%s&8] (&7%d&8): &b%s", new Object[] { ((ProtocolVersion)entry.getKey()).getName(), Integer.valueOf(((Set)entry.getValue()).size()), entry.getValue() });
/*    */     } 
/*    */     
/* 59 */     playerVersions.clear();
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\ListSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */