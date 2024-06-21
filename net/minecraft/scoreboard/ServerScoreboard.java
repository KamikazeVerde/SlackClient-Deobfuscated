/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*     */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class ServerScoreboard
/*     */   extends Scoreboard {
/*     */   private final MinecraftServer scoreboardMCServer;
/*  19 */   private final Set<ScoreObjective> field_96553_b = Sets.newHashSet();
/*     */   
/*     */   private ScoreboardSaveData scoreboardSaveData;
/*     */   
/*     */   public ServerScoreboard(MinecraftServer mcServer) {
/*  24 */     this.scoreboardMCServer = mcServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96536_a(Score p_96536_1_) {
/*  29 */     super.func_96536_a(p_96536_1_);
/*     */     
/*  31 */     if (this.field_96553_b.contains(p_96536_1_.getObjective()))
/*     */     {
/*  33 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_96536_1_));
/*     */     }
/*     */     
/*  36 */     func_96551_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96516_a(String p_96516_1_) {
/*  41 */     super.func_96516_a(p_96516_1_);
/*  42 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_96516_1_));
/*  43 */     func_96551_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178820_a(String p_178820_1_, ScoreObjective p_178820_2_) {
/*  48 */     super.func_178820_a(p_178820_1_, p_178820_2_);
/*  49 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_178820_1_, p_178820_2_));
/*  50 */     func_96551_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int p_96530_1_, ScoreObjective p_96530_2_) {
/*  58 */     ScoreObjective scoreobjective = getObjectiveInDisplaySlot(p_96530_1_);
/*  59 */     super.setObjectiveInDisplaySlot(p_96530_1_, p_96530_2_);
/*     */     
/*  61 */     if (scoreobjective != p_96530_2_ && scoreobjective != null)
/*     */     {
/*  63 */       if (func_96552_h(scoreobjective) > 0) {
/*     */         
/*  65 */         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
/*     */       }
/*     */       else {
/*     */         
/*  69 */         getPlayerIterator(scoreobjective);
/*     */       } 
/*     */     }
/*     */     
/*  73 */     if (p_96530_2_ != null)
/*     */     {
/*  75 */       if (this.field_96553_b.contains(p_96530_2_)) {
/*     */         
/*  77 */         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
/*     */       }
/*     */       else {
/*     */         
/*  81 */         func_96549_e(p_96530_2_);
/*     */       } 
/*     */     }
/*     */     
/*  85 */     func_96551_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/*  96 */     if (super.addPlayerToTeam(player, newTeam)) {
/*     */       
/*  98 */       ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*  99 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(scoreplayerteam, Arrays.asList(new String[] { player }, ), 3));
/* 100 */       func_96551_b();
/* 101 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
/* 115 */     super.removePlayerFromTeam(p_96512_1_, p_96512_2_);
/* 116 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(p_96512_2_, Arrays.asList(new String[] { p_96512_1_ }, ), 4));
/* 117 */     func_96551_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {
/* 125 */     super.onScoreObjectiveAdded(scoreObjectiveIn);
/* 126 */     func_96551_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96532_b(ScoreObjective p_96532_1_) {
/* 131 */     super.func_96532_b(p_96532_1_);
/*     */     
/* 133 */     if (this.field_96553_b.contains(p_96532_1_))
/*     */     {
/* 135 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3BPacketScoreboardObjective(p_96532_1_, 2));
/*     */     }
/*     */     
/* 138 */     func_96551_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96533_c(ScoreObjective p_96533_1_) {
/* 143 */     super.func_96533_c(p_96533_1_);
/*     */     
/* 145 */     if (this.field_96553_b.contains(p_96533_1_))
/*     */     {
/* 147 */       getPlayerIterator(p_96533_1_);
/*     */     }
/*     */     
/* 150 */     func_96551_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {
/* 158 */     super.broadcastTeamCreated(playerTeam);
/* 159 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 0));
/* 160 */     func_96551_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTeamUpdate(ScorePlayerTeam playerTeam) {
/* 168 */     super.sendTeamUpdate(playerTeam);
/* 169 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 2));
/* 170 */     func_96551_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96513_c(ScorePlayerTeam playerTeam) {
/* 175 */     super.func_96513_c(playerTeam);
/* 176 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 1));
/* 177 */     func_96551_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96547_a(ScoreboardSaveData p_96547_1_) {
/* 182 */     this.scoreboardSaveData = p_96547_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_96551_b() {
/* 187 */     if (this.scoreboardSaveData != null)
/*     */     {
/* 189 */       this.scoreboardSaveData.markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Packet> func_96550_d(ScoreObjective p_96550_1_) {
/* 195 */     List<Packet> list = Lists.newArrayList();
/* 196 */     list.add(new S3BPacketScoreboardObjective(p_96550_1_, 0));
/*     */     
/* 198 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 200 */       if (getObjectiveInDisplaySlot(i) == p_96550_1_)
/*     */       {
/* 202 */         list.add(new S3DPacketDisplayScoreboard(i, p_96550_1_));
/*     */       }
/*     */     } 
/*     */     
/* 206 */     for (Score score : getSortedScores(p_96550_1_))
/*     */     {
/* 208 */       list.add(new S3CPacketUpdateScore(score));
/*     */     }
/*     */     
/* 211 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96549_e(ScoreObjective p_96549_1_) {
/* 216 */     List<Packet> list = func_96550_d(p_96549_1_);
/*     */     
/* 218 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().func_181057_v()) {
/*     */       
/* 220 */       for (Packet packet : list)
/*     */       {
/* 222 */         entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 226 */     this.field_96553_b.add(p_96549_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Packet> func_96548_f(ScoreObjective p_96548_1_) {
/* 231 */     List<Packet> list = Lists.newArrayList();
/* 232 */     list.add(new S3BPacketScoreboardObjective(p_96548_1_, 1));
/*     */     
/* 234 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 236 */       if (getObjectiveInDisplaySlot(i) == p_96548_1_)
/*     */       {
/* 238 */         list.add(new S3DPacketDisplayScoreboard(i, p_96548_1_));
/*     */       }
/*     */     } 
/*     */     
/* 242 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getPlayerIterator(ScoreObjective p_96546_1_) {
/* 247 */     List<Packet> list = func_96548_f(p_96546_1_);
/*     */     
/* 249 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().func_181057_v()) {
/*     */       
/* 251 */       for (Packet packet : list)
/*     */       {
/* 253 */         entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 257 */     this.field_96553_b.remove(p_96546_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_96552_h(ScoreObjective p_96552_1_) {
/* 262 */     int i = 0;
/*     */     
/* 264 */     for (int j = 0; j < 19; j++) {
/*     */       
/* 266 */       if (getObjectiveInDisplaySlot(j) == p_96552_1_)
/*     */       {
/* 268 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 272 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\scoreboard\ServerScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */