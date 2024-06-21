/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ 
/*     */ public class Scoreboard extends StoredObject {
/*  14 */   private final HashMap<String, List<String>> teams = new HashMap<>();
/*  15 */   private final HashSet<String> objectives = new HashSet<>();
/*  16 */   private final HashMap<String, ScoreTeam> scoreTeams = new HashMap<>();
/*  17 */   private final HashMap<String, Byte> teamColors = new HashMap<>();
/*  18 */   private final HashSet<String> scoreTeamNames = new HashSet<>();
/*     */   private String colorIndependentSidebar;
/*  20 */   private final HashMap<Byte, String> colorDependentSidebar = new HashMap<>();
/*     */   
/*     */   public Scoreboard(UserConnection user) {
/*  23 */     super(user);
/*     */   }
/*     */   
/*     */   public void addPlayerToTeam(String player, String team) {
/*  27 */     ((List<String>)this.teams.computeIfAbsent(team, key -> new ArrayList())).add(player);
/*     */   }
/*     */   
/*     */   public void setTeamColor(String team, Byte color) {
/*  31 */     this.teamColors.put(team, color);
/*     */   }
/*     */   
/*     */   public Optional<Byte> getTeamColor(String team) {
/*  35 */     return Optional.ofNullable(this.teamColors.get(team));
/*     */   }
/*     */   
/*     */   public void addTeam(String team) {
/*  39 */     this.teams.computeIfAbsent(team, key -> new ArrayList());
/*     */   }
/*     */   
/*     */   public void removeTeam(String team) {
/*  43 */     this.teams.remove(team);
/*  44 */     this.scoreTeams.remove(team);
/*  45 */     this.teamColors.remove(team);
/*     */   }
/*     */   
/*     */   public boolean teamExists(String team) {
/*  49 */     return this.teams.containsKey(team);
/*     */   }
/*     */   
/*     */   public void removePlayerFromTeam(String player, String team) {
/*  53 */     List<String> teamPlayers = this.teams.get(team);
/*  54 */     if (teamPlayers != null) teamPlayers.remove(player); 
/*     */   }
/*     */   
/*     */   public boolean isPlayerInTeam(String player, String team) {
/*  58 */     List<String> teamPlayers = this.teams.get(team);
/*  59 */     return (teamPlayers != null && teamPlayers.contains(player));
/*     */   }
/*     */   
/*     */   public boolean isPlayerInTeam(String player) {
/*  63 */     for (List<String> teamPlayers : this.teams.values()) {
/*  64 */       if (teamPlayers.contains(player)) return true; 
/*     */     } 
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public Optional<Byte> getPlayerTeamColor(String player) {
/*  70 */     Optional<String> team = getTeam(player);
/*  71 */     return team.isPresent() ? getTeamColor(team.get()) : Optional.<Byte>empty();
/*     */   }
/*     */   
/*     */   public Optional<String> getTeam(String player) {
/*  75 */     for (Map.Entry<String, List<String>> entry : this.teams.entrySet()) {
/*  76 */       if (((List)entry.getValue()).contains(player))
/*  77 */         return Optional.of(entry.getKey()); 
/*  78 */     }  return Optional.empty();
/*     */   }
/*     */   
/*     */   public void addObjective(String name) {
/*  82 */     this.objectives.add(name);
/*     */   }
/*     */   
/*     */   public void removeObjective(String name) {
/*  86 */     this.objectives.remove(name);
/*  87 */     this.colorDependentSidebar.values().remove(name);
/*  88 */     if (name.equals(this.colorIndependentSidebar)) {
/*  89 */       this.colorIndependentSidebar = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean objectiveExists(String name) {
/*  94 */     return this.objectives.contains(name);
/*     */   }
/*     */   
/*     */   public String sendTeamForScore(String score) {
/*  98 */     if (score.length() <= 16) return score; 
/*  99 */     if (this.scoreTeams.containsKey(score)) return (this.scoreTeams.get(score)).name; 
/* 100 */     int l = 16;
/* 101 */     int i = Math.min(16, score.length() - 16);
/* 102 */     String name = score.substring(i, i + l);
/* 103 */     while (this.scoreTeamNames.contains(name) || this.teams.containsKey(name)) {
/* 104 */       i--;
/* 105 */       while (score.length() - l - i > 16) {
/* 106 */         l--;
/* 107 */         if (l < 1) return score; 
/* 108 */         i = Math.min(16, score.length() - l);
/*     */       } 
/* 110 */       name = score.substring(i, i + l);
/*     */     } 
/* 112 */     String prefix = score.substring(0, i);
/* 113 */     String suffix = (i + l >= score.length()) ? "" : score.substring(i + l);
/*     */     
/* 115 */     ScoreTeam scoreTeam = new ScoreTeam(name, prefix, suffix);
/* 116 */     this.scoreTeams.put(score, scoreTeam);
/* 117 */     this.scoreTeamNames.add(name);
/*     */     
/* 119 */     PacketWrapper teamPacket = PacketWrapper.create((PacketType)ClientboundPackets1_7.TEAMS, getUser());
/* 120 */     teamPacket.write(Type.STRING, name);
/* 121 */     teamPacket.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 122 */     teamPacket.write(Type.STRING, "ViaRewind");
/* 123 */     teamPacket.write(Type.STRING, prefix);
/* 124 */     teamPacket.write(Type.STRING, suffix);
/* 125 */     teamPacket.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/* 126 */     teamPacket.write((Type)Type.SHORT, Short.valueOf((short)1));
/* 127 */     teamPacket.write(Type.STRING, name);
/* 128 */     PacketUtil.sendPacket(teamPacket, Protocol1_7_6_10TO1_8.class, true, true);
/*     */     
/* 130 */     return name;
/*     */   }
/*     */   
/*     */   public String removeTeamForScore(String score) {
/* 134 */     ScoreTeam scoreTeam = this.scoreTeams.remove(score);
/* 135 */     if (scoreTeam == null) return score; 
/* 136 */     this.scoreTeamNames.remove(scoreTeam.name);
/*     */     
/* 138 */     PacketWrapper teamPacket = PacketWrapper.create((PacketType)ClientboundPackets1_7.TEAMS, getUser());
/* 139 */     teamPacket.write(Type.STRING, scoreTeam.name);
/* 140 */     teamPacket.write((Type)Type.BYTE, Byte.valueOf((byte)1));
/* 141 */     PacketUtil.sendPacket(teamPacket, Protocol1_7_6_10TO1_8.class, true, true);
/*     */     
/* 143 */     return scoreTeam.name;
/*     */   }
/*     */   
/*     */   public String getColorIndependentSidebar() {
/* 147 */     return this.colorIndependentSidebar;
/*     */   }
/*     */   
/*     */   public HashMap<Byte, String> getColorDependentSidebar() {
/* 151 */     return this.colorDependentSidebar;
/*     */   }
/*     */   
/*     */   public void setColorIndependentSidebar(String colorIndependentSidebar) {
/* 155 */     this.colorIndependentSidebar = colorIndependentSidebar;
/*     */   }
/*     */   
/*     */   private static class ScoreTeam {
/*     */     private final String prefix;
/*     */     private final String suffix;
/*     */     private final String name;
/*     */     
/*     */     public ScoreTeam(String name, String prefix, String suffix) {
/* 164 */       this.prefix = prefix;
/* 165 */       this.suffix = suffix;
/* 166 */       this.name = name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\Scoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */