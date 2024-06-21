/*    */ package com.viaversion.viaversion.commands.defaultsubs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import java.util.HashMap;
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
/*    */ public class PPSSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 34 */     return "pps";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 39 */     return "Shows the packets per second of online players";
/*    */   }
/*    */ 
/*    */   
/*    */   public String usage() {
/* 44 */     return "pps";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 49 */     Map<Integer, Set<String>> playerVersions = new HashMap<>();
/* 50 */     int totalPackets = 0;
/* 51 */     int clients = 0;
/* 52 */     long max = 0L;
/*    */     
/* 54 */     for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
/* 55 */       int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
/* 56 */       if (!playerVersions.containsKey(Integer.valueOf(playerVersion)))
/* 57 */         playerVersions.put(Integer.valueOf(playerVersion), new HashSet<>()); 
/* 58 */       UserConnection uc = Via.getManager().getConnectionManager().getConnectedClient(p.getUUID());
/* 59 */       if (uc != null && uc.getPacketTracker().getPacketsPerSecond() > -1L) {
/* 60 */         ((Set<String>)playerVersions.get(Integer.valueOf(playerVersion))).add(p.getName() + " (" + uc.getPacketTracker().getPacketsPerSecond() + " PPS)");
/* 61 */         totalPackets = (int)(totalPackets + uc.getPacketTracker().getPacketsPerSecond());
/* 62 */         if (uc.getPacketTracker().getPacketsPerSecond() > max) {
/* 63 */           max = uc.getPacketTracker().getPacketsPerSecond();
/*    */         }
/* 65 */         clients++;
/*    */       } 
/*    */     } 
/* 68 */     Map<Integer, Set<String>> sorted = new TreeMap<>(playerVersions);
/* 69 */     sendMessage(sender, "&4Live Packets Per Second", new Object[0]);
/* 70 */     if (clients > 1) {
/* 71 */       sendMessage(sender, "&cAverage: &f" + (totalPackets / clients), new Object[0]);
/* 72 */       sendMessage(sender, "&cHighest: &f" + max, new Object[0]);
/*    */     } 
/* 74 */     if (clients == 0) {
/* 75 */       sendMessage(sender, "&cNo clients to display.", new Object[0]);
/*    */     }
/* 77 */     for (Map.Entry<Integer, Set<String>> entry : sorted.entrySet()) {
/* 78 */       sendMessage(sender, "&8[&6%s&8]: &b%s", new Object[] { ProtocolVersion.getProtocol(((Integer)entry.getKey()).intValue()).getName(), entry.getValue() });
/* 79 */     }  sorted.clear();
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\PPSSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */